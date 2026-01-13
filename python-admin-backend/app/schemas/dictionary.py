from typing import List, Dict, Any, Optional
from pydantic import BaseModel


class DictionaryItemBase(BaseModel):
    label: str
    value: str
    sort: int = 0
    status: int = 1


class DictionaryItemCreate(DictionaryItemBase):
    dict_id: str


class DictionaryItem(DictionaryItemBase):
    id: str
    dict_id: str
    created_at: Any
    updated_at: Any

    class Config:
        from_attributes = True


class DictionaryBase(BaseModel):
    dict_name: str
    dict_code: str
    status: int = 1
    remark: Optional[str] = None


class DictionaryCreate(DictionaryBase):
    items: Optional[List[DictionaryItemCreate]] = []


class DictionaryUpdate(BaseModel):
    dict_name: Optional[str] = None
    dict_code: Optional[str] = None
    status: Optional[int] = None
    remark: Optional[str] = None
    items: Optional[List[DictionaryItemCreate]] = None


class Dictionary(DictionaryBase):
    id: str
    created_at: Any
    updated_at: Any
    items: List[DictionaryItem] = []

    class Config:
        from_attributes = True


class DictionaryList(BaseModel):
    items: List[Dict[str, Any]]


class DictionaryOne(BaseModel):
    data: Dict[str, Any]
