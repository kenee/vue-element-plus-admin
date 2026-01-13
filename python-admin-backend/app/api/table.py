from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from typing import List
from app.db.database import get_db
from app.models.table import TableData as TableDataModel
from app.schemas.table import TableData, TableDataCreate, TableDataUpdate
from app.schemas.common import ResponseModel, ListResponse

router = APIRouter()


@router.get("/example/list", response_model=ResponseModel)
async def get_table_list(
    pageIndex: int = Query(1, ge=1),
    pageSize: int = Query(10, ge=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(TableDataModel)

    total = query.count()
    table_data_list = query.offset((pageIndex - 1) * pageSize).limit(pageSize).all()

    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": table_data_list,
            "total": total
        },
        message="success"
    )


@router.get("/example/detail", response_model=ResponseModel)
async def get_table_detail(id: str = Query(...), db: Session = Depends(get_db)):
    table_data = db.query(TableDataModel).filter(TableDataModel.id == id).first()
    if not table_data:
        raise HTTPException(status_code=404, detail="Table data not found")

    return ResponseModel(code=0, data=table_data, message="success")


@router.post("/example/save", response_model=ResponseModel)
async def save_table_data(table_data: TableDataCreate, db: Session = Depends(get_db)):
    db_table_data = TableDataModel(**table_data.model_dump())
    db.add(db_table_data)
    db.commit()
    db.refresh(db_table_data)

    return ResponseModel(code=0, data=db_table_data, message="success")


@router.post("/example/delete", response_model=ResponseModel)
async def delete_table_data(ids: List[str], db: Session = Depends(get_db)):
    table_data_list = db.query(TableDataModel).filter(TableDataModel.id.in_(ids)).all()
    for item in table_data_list:
        db.delete(item)
    db.commit()

    return ResponseModel(code=0, data=None, message="success")


@router.get("/card/list", response_model=ResponseModel)
async def get_card_table_list(
    pageIndex: int = Query(1, ge=1),
    pageSize: int = Query(10, ge=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(TableDataModel)

    total = query.count()
    table_data_list = query.offset((pageIndex - 1) * pageSize).limit(pageSize).all()

    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": table_data_list,
            "total": total
        },
        message="success"
    )


@router.get("/example/treeList", response_model=ResponseModel)
async def get_tree_table_list(
    pageIndex: int = Query(1, ge=1),
    pageSize: int = Query(10, ge=1, le=100),
    db: Session = Depends(get_db),
):
    query = db.query(TableDataModel)

    total = query.count()
    table_data_list = query.offset((pageIndex - 1) * pageSize).limit(pageSize).all()

    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": table_data_list,
            "total": total
        },
        message="success"
    )
