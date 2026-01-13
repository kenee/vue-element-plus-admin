from typing import List, Optional
from pydantic import BaseModel
import json


class MenuBase(BaseModel):
    title: str
    path: Optional[str] = None
    component: Optional[str] = None
    redirect: Optional[str] = None
    name: Optional[str] = None
    icon: Optional[str] = None
    meta: Optional[dict] = None
    type: Optional[int] = 0
    parentId: Optional[str] = None
    status: Optional[int] = 1
    permission: Optional[str] = None
    sort: Optional[int] = 0


class MenuCreate(MenuBase):
    pass


class MenuUpdate(MenuBase):
    pass


class Menu(MenuBase):
    id: str
    created_at: Optional[str] = None
    updated_at: Optional[str] = None
    children: Optional[List["Menu"]] = None
    permissionList: Optional[List[dict]] = None

    class Config:
        from_attributes = True
