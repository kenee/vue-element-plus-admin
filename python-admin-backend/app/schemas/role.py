from typing import List, Optional
from pydantic import BaseModel


class RoleBase(BaseModel):
    roleName: str
    roleValue: str
    status: int = 1
    remark: Optional[str] = ""


class RoleCreate(RoleBase):
    pass


class RoleUpdate(BaseModel):
    roleName: Optional[str] = None
    roleValue: Optional[str] = None
    status: Optional[int] = None
    remark: Optional[str] = None


class Role(RoleBase):
    id: str
    createTime: str
    menu: Optional[List[dict]] = None

    class Config:
        from_attributes = True


class RoleListParams(BaseModel):
    roleName: Optional[str] = None
    pageIndex: int = 1
    pageSize: int = 10
