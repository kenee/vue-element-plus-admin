from typing import List, Optional, Any
from pydantic import BaseModel, Field


class ResponseModel(BaseModel):
    code: int = Field(default=0, description="Response code, 0 means success")
    data: Optional[Any] = Field(default=None, description="Response data")
    message: str = Field(default="success", description="Response message")

    class Config:
        json_schema_extra = {"example": {"code": 0, "data": {}, "message": "success"}}


class ListResponse(BaseModel):
    list: List[Any]
    total: int
