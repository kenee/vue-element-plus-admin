from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session, joinedload
from typing import List, Optional
from app.db.database import get_db
from app.models.dictionary import Dictionary as DictionaryModel, DictionaryItem
from app.schemas.dictionary import (
    Dictionary,
    DictionaryCreate,
    DictionaryUpdate,
    DictionaryList,
    DictionaryOne,
)
from app.schemas.common import ResponseModel

router = APIRouter()


@router.get("/list", response_model=ResponseModel)
async def get_dict_list(db: Session = Depends(get_db)):
    dictionaries = db.query(DictionaryModel).options(joinedload(DictionaryModel.items)).all()

    result = {}
    for dictionary in dictionaries:
        result[dictionary.dict_code] = [
            {"value": int(item.value), "label": item.label} for item in dictionary.items
        ]

    return ResponseModel(code=0, data=result, message="success")


@router.get("/one")
async def get_dict_one(code: Optional[str] = Query(None), db: Session = Depends(get_db)):
    if not code:
        return ResponseModel(
            code=0,
            data={"status": [{"label": "Active", "value": 1}, {"label": "Inactive", "value": 0}]},
            message="success",
        )

    dictionary = (
        db.query(DictionaryModel)
        .options(joinedload(DictionaryModel.items))
        .filter(DictionaryModel.dict_code == code)
        .first()
    )

    if not dictionary:
        return ResponseModel(code=0, data={}, message="success")

    return ResponseModel(
        code=0,
        data={code: [{"value": int(item.value), "label": item.label} for item in dictionary.items]},
        message="success",
    )


@router.get("/{id}", response_model=ResponseModel)
async def get_dictionary(id: str, db: Session = Depends(get_db)):
    dictionary = (
        db.query(DictionaryModel)
        .options(joinedload(DictionaryModel.items))
        .filter(DictionaryModel.id == id)
        .first()
    )

    if not dictionary:
        raise HTTPException(status_code=404, detail="Dictionary not found")

    return ResponseModel(code=0, data=dictionary, message="success")


@router.post("", response_model=ResponseModel)
async def create_dictionary(dictionary: DictionaryCreate, db: Session = Depends(get_db)):
    import uuid

    existing_dict = (
        db.query(DictionaryModel).filter(DictionaryModel.dict_code == dictionary.dict_code).first()
    )

    if existing_dict:
        raise HTTPException(status_code=400, detail="Dictionary code already exists")

    db_dictionary = DictionaryModel(
        id=str(uuid.uuid4()),
        dict_name=dictionary.dict_name,
        dict_code=dictionary.dict_code,
        status=dictionary.status,
        remark=dictionary.remark,
    )

    db.add(db_dictionary)
    db.flush()

    if dictionary.items:
        for item_data in dictionary.items:
            db_item = DictionaryItem(
                id=str(uuid.uuid4()),
                dict_id=db_dictionary.id,
                label=item_data.label,
                value=item_data.value,
                sort=item_data.sort,
                status=item_data.status,
            )
            db.add(db_item)

    db.commit()
    db.refresh(db_dictionary)

    return ResponseModel(code=0, data=db_dictionary, message="success")


@router.patch("/{id}", response_model=ResponseModel)
async def update_dictionary(id: str, dictionary: DictionaryUpdate, db: Session = Depends(get_db)):
    db_dictionary = (
        db.query(DictionaryModel)
        .options(joinedload(DictionaryModel.items))
        .filter(DictionaryModel.id == id)
        .first()
    )

    if not db_dictionary:
        raise HTTPException(status_code=404, detail="Dictionary not found")

    update_data = dictionary.model_dump(exclude_unset=True, exclude={"items"})

    for field, value in update_data.items():
        if hasattr(db_dictionary, field):
            setattr(db_dictionary, field, value)

    if dictionary.items is not None:
        for item in db_dictionary.items:
            db.delete(item)

        for item_data in dictionary.items:
            import uuid

            db_item = DictionaryItem(
                id=str(uuid.uuid4()),
                dict_id=db_dictionary.id,
                label=item_data.label,
                value=item_data.value,
                sort=item_data.sort,
                status=item_data.status,
            )
            db.add(db_item)

    db.commit()
    db.refresh(db_dictionary)

    return ResponseModel(code=0, data=db_dictionary, message="success")


@router.delete("/{id}", response_model=ResponseModel)
async def delete_dictionary(id: str, db: Session = Depends(get_db)):
    dictionary = db.query(DictionaryModel).filter(DictionaryModel.id == id).first()

    if not dictionary:
        raise HTTPException(status_code=404, detail="Dictionary not found")

    db.delete(dictionary)
    db.commit()

    return ResponseModel(code=0, data=None, message="success")
