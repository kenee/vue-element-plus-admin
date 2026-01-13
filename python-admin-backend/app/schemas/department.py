from typing import List, Optional
from pydantic import BaseModel


class DepartmentBase(BaseModel):
    departmentName: str
    status: int = 1
    sort: int = 0
    remark: Optional[str] = ""


class DepartmentCreate(DepartmentBase):
    parentId: Optional[str] = None


class DepartmentUpdate(BaseModel):
    departmentName: Optional[str] = None
    status: Optional[int] = None
    sort: Optional[int] = None
    remark: Optional[str] = None
    parentId: Optional[str] = None


class Department(DepartmentBase):
    id: str
    createTime: str
    children: Optional[List["Department"]] = None

    class Config:
        from_attributes = True


class DepartmentListParams(BaseModel):
    name: Optional[str] = None
    status: Optional[int] = None
    pageIndex: int = 1
    pageSize: int = 10
