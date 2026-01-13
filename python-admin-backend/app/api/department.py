from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from typing import List, Dict, Any, Optional
from app.db.database import get_db
from app.models.department import Department as DepartmentModel
from app.schemas.department import (
    DepartmentCreate,
    DepartmentUpdate,
)
from app.schemas.common import ResponseModel, ListResponse

router = APIRouter()


def build_department_tree(departments: List[DepartmentModel]) -> List[Dict[str, Any]]:
    """Build tree structure from flat department list"""
    department_map = {}
    root_departments = []
    
    # First pass: create all department nodes without children field
    for dept in departments:
        department = {
            "id": dept.id,
            "departmentName": dept.name,
            "parentId": dept.parent_id,
            "status": dept.status,
            "sort": dept.sort,
            "remark": dept.remark,
            "createTime": dept.created_at.isoformat() if dept.created_at else ""
            # Note: Don't add children field by default
        }
        department_map[dept.id] = department
    
    # Second pass: build tree structure and add children field only when needed
    for dept in departments:
        department = department_map[dept.id]
        if dept.parent_id and dept.parent_id in department_map:
            # Has parent, add to parent's children
            parent = department_map[dept.parent_id]
            # Ensure parent has children field
            if "children" not in parent:
                parent["children"] = []
            parent["children"].append(department)
        else:
            # Root department
            root_departments.append(department)
    
    return root_departments


@router.get("", response_model=ResponseModel)
async def get_departments(
    db: Session = Depends(get_db),
):
    """Get all departments as tree structure"""
    # Get all departments ordered by sort and created_at
    departments = (
        db.query(DepartmentModel)
        .order_by(DepartmentModel.sort.asc(), DepartmentModel.created_at.asc())
        .all()
    )
    
    # Build tree structure
    department_tree = build_department_tree(departments)
    
    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": department_tree,
            "total": len(departments)
        },
        message="success"
    )


@router.post("", response_model=ResponseModel)
async def create_department(
    department: DepartmentCreate,
    db: Session = Depends(get_db),
):
    """Create a new department"""
    # Convert departmentName to name
    department_data = department.model_dump()
    if "departmentName" in department_data:
        department_data["name"] = department_data.pop("departmentName")
    
    # Remove children if exists
    if "children" in department_data:
        del department_data["children"]
    
    # Create department
    db_department = DepartmentModel(**department_data)
    db.add(db_department)
    db.commit()
    db.refresh(db_department)
    
    # Convert to response format
    response_data = {
        "id": db_department.id,
        "departmentName": db_department.name,
        "parentId": db_department.parent_id,
        "status": db_department.status,
        "sort": db_department.sort,
        "remark": db_department.remark,
        "createTime": db_department.created_at.isoformat() if db_department.created_at else ""
        # Note: Don't add children field for new departments (they won't have children yet)
    }
    
    return ResponseModel(code=0, data=response_data, message="success")


@router.get("/{id}", response_model=ResponseModel)
async def get_department(
    id: str,
    db: Session = Depends(get_db),
):
    """Get a department by ID"""
    department = db.query(DepartmentModel).filter(DepartmentModel.id == id).first()
    if not department:
        raise HTTPException(status_code=404, detail="Department not found")
    
    # Convert to response format
    response_data = {
        "id": department.id,
        "departmentName": department.name,
        "parentId": department.parent_id,
        "status": department.status,
        "sort": department.sort,
        "remark": department.remark,
        "createTime": department.created_at.isoformat() if department.created_at else "",
        "children": []
    }
    
    return ResponseModel(code=0, data=response_data, message="success")


@router.patch("/{id}", response_model=ResponseModel)
async def update_department(
    id: str,
    department: DepartmentUpdate,
    db: Session = Depends(get_db),
):
    """Update a department"""
    db_department = db.query(DepartmentModel).filter(DepartmentModel.id == id).first()
    if not db_department:
        raise HTTPException(status_code=404, detail="Department not found")
    
    # Convert departmentName to name
    department_data = department.model_dump(exclude_unset=True)
    if "departmentName" in department_data:
        department_data["name"] = department_data.pop("departmentName")
    
    # Remove children and createTime if exists
    if "children" in department_data:
        del department_data["children"]
    if "createTime" in department_data:
        del department_data["createTime"]
    
    # Update department
    for field, value in department_data.items():
        if hasattr(db_department, field):
            setattr(db_department, field, value)
    
    db.commit()
    db.refresh(db_department)
    
    # Convert to response format
    response_data = {
        "id": db_department.id,
        "departmentName": db_department.name,
        "parentId": db_department.parent_id,
        "status": db_department.status,
        "sort": db_department.sort,
        "remark": db_department.remark,
        "createTime": db_department.created_at.isoformat() if db_department.created_at else "",
        "children": []
    }
    
    return ResponseModel(code=0, data=response_data, message="success")


@router.delete("/{id}", response_model=ResponseModel)
async def delete_department(
    id: str,
    db: Session = Depends(get_db),
):
    """Delete a department"""
    department = db.query(DepartmentModel).filter(DepartmentModel.id == id).first()
    if not department:
        raise HTTPException(status_code=404, detail="Department not found")
    
    db.delete(department)
    db.commit()
    
    return ResponseModel(code=0, data=None, message="success")


@router.post("/delete", response_model=ResponseModel)
async def delete_departments(
    body: Dict[str, List[str]],
    db: Session = Depends(get_db),
):
    """Delete multiple departments"""
    ids = body.get("ids", [])
    if not ids:
        return ResponseModel(code=0, data=None, message="success")
    
    # Delete all departments with given ids
    db.query(DepartmentModel).filter(DepartmentModel.id.in_(ids)).delete()
    db.commit()
    
    return ResponseModel(code=0, data=None, message="success")
