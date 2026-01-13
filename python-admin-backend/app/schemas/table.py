from typing import List, Optional
from pydantic import BaseModel


class TableDataBase(BaseModel):
    author: str
    title: str
    content: str
    importance: int = 0
    display_time: Optional[str] = None
    pageviews: int = 0


class TableDataCreate(TableDataBase):
    pass


class TableDataUpdate(BaseModel):
    author: Optional[str] = None
    title: Optional[str] = None
    content: Optional[str] = None
    importance: Optional[int] = None
    display_time: Optional[str] = None
    pageviews: Optional[int] = None


class TableData(TableDataBase):
    id: str
    createTime: str

    class Config:
        from_attributes = True


class TableListParams(BaseModel):
    pageIndex: int = 1
    pageSize: int = 10
