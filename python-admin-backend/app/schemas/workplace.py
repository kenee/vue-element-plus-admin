from typing import List, Optional
from pydantic import BaseModel
from datetime import datetime


class WorkplaceTotal(BaseModel):
    project: int
    access: int
    todo: int


class Project(BaseModel):
    name: str
    icon: str
    message: str
    personal: str
    time: datetime | int | str


class Dynamic(BaseModel):
    keys: List[str]
    time: datetime | int | str


class Team(BaseModel):
    name: str
    icon: str


class RadarData(BaseModel):
    personal: int
    team: int
    max: int
    name: str
