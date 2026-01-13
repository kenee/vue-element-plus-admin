from sqlalchemy import Column, String, Integer, DateTime, Text
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.db.database import Base


class Department(Base):
    __tablename__ = "sys_department"

    id = Column(String(36), primary_key=True, index=True)
    parent_id = Column(String(36), nullable=True)
    name = Column(String(100), nullable=False)
    sort = Column(Integer, server_default="0")
    status = Column(Integer, server_default="1")
    remark = Column(Text, nullable=True)
    created_at = Column(DateTime, server_default=func.now())
    updated_at = Column(DateTime, server_default=func.now(), onupdate=func.now())
