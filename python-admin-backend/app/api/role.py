from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from typing import List, Optional
from app.db.database import get_db
from app.models.role import Role as RoleModel
from app.schemas.role import Role, RoleCreate, RoleUpdate
from app.schemas.common import ResponseModel, ListResponse

router = APIRouter()


@router.get("", response_model=ResponseModel)
async def get_roles(
    roleName: Optional[str] = Query(None),
    pageIndex: int = Query(1, ge=1),
    pageSize: int = Query(10, ge=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(RoleModel)

    if roleName:
        query = query.filter(RoleModel.role_name.like(f"%{roleName}%"))

    total = query.count()
    roles = query.offset((pageIndex - 1) * pageSize).limit(pageSize).all()

    # Convert SQLAlchemy models to dictionaries
    role_list = []
    for role in roles:
        role_dict = {
            "id": role.id,
            "role_name": role.role_name,
            "role_value": role.role_value,
            "status": role.status,
            "remark": role.remark,
            "created_at": role.created_at.isoformat() if role.created_at else None,
            "updated_at": role.updated_at.isoformat() if role.updated_at else None
        }
        role_list.append(role_dict)

    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": role_list,
            "total": total
        },
        message="success"
    )


@router.post("", response_model=ResponseModel)
async def create_role(role: RoleCreate, db: Session = Depends(get_db)):
    existing_role = db.query(RoleModel).filter(RoleModel.roleName == role.roleName).first()
    if existing_role:
        raise HTTPException(status_code=400, detail="Role name already exists")

    db_role = RoleModel(**role.model_dump())
    db.add(db_role)
    db.commit()
    db.refresh(db_role)

    return ResponseModel(code=0, data=db_role, message="success")


@router.get("/{id}", response_model=ResponseModel)
async def get_role(id: int, db: Session = Depends(get_db)):
    role = db.query(RoleModel).filter(RoleModel.id == id).first()
    if not role:
        raise HTTPException(status_code=404, detail="Role not found")

    return ResponseModel(code=0, data=role, message="success")


@router.patch("/{id}", response_model=ResponseModel)
async def update_role(id: int, role: RoleUpdate, db: Session = Depends(get_db)):
    db_role = db.query(RoleModel).filter(RoleModel.id == id).first()
    if not db_role:
        raise HTTPException(status_code=404, detail="Role not found")

    update_data = role.model_dump(exclude_unset=True)
    for field, value in update_data.items():
        setattr(db_role, field, value)

    db.commit()
    db.refresh(db_role)

    return ResponseModel(code=0, data=db_role, message="success")


@router.delete("/{id}", response_model=ResponseModel)
async def delete_role(id: int, db: Session = Depends(get_db)):
    role = db.query(RoleModel).filter(RoleModel.id == id).first()
    if not role:
        raise HTTPException(status_code=404, detail="Role not found")

    db.delete(role)
    db.commit()

    return ResponseModel(code=0, data=None, message="success")
