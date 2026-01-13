from typing import Optional, List
from pydantic import BaseModel


class AnalysisTotal(BaseModel):
    users: int
    messages: int
    moneys: int
    shoppings: int


class UserAccessSource(BaseModel):
    value: int
    name: str


class WeeklyUserActivity(BaseModel):
    value: int
    name: str


class MonthlySales(BaseModel):
    name: str
    estimate: int
    actual: int
