from typing import List, Optional
from pydantic import BaseModel, EmailStr


class UserLogin(BaseModel):
    username: str
    password: str


class UserBase(BaseModel):
    username: str
    email: Optional[EmailStr] = None
    nickname: Optional[str] = None
    role: Optional[str] = "user"
    roleId: Optional[str] = None
    deptId: Optional[str] = None


class UserCreate(UserBase):
    password: str


class UserUpdate(BaseModel):
    username: Optional[str] = None
    email: Optional[EmailStr] = None
    nickname: Optional[str] = None
    password: Optional[str] = None
    role: Optional[str] = None
    roleId: Optional[str] = None
    deptId: Optional[str] = None


class User(UserBase):
    id: str
    created_at: Optional[str] = None
    updated_at: Optional[str] = None
    roles: Optional[List[dict]] = None

    class Config:
        from_attributes = True


class UserListParams(BaseModel):
    page: int = 1
    pageSize: int = 10
    username: Optional[str] = None
    account: Optional[str] = None
    deptId: Optional[str] = None
