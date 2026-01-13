from sqlalchemy import Column, String, Integer, Text, DateTime
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.db.database import Base
from app.models import sys_role_menu


class Role(Base):
    __tablename__ = "sys_role"

    id = Column(String(36), primary_key=True, index=True)
    role_name = Column(String(50), nullable=False)
    role_value = Column(String(50), unique=True, nullable=False)
    status = Column(Integer, server_default="1")
    remark = Column(Text, nullable=True)
    created_at = Column(DateTime, server_default=func.now())
    updated_at = Column(DateTime, server_default=func.now(), onupdate=func.now())
    users = relationship("User", secondary="sys_user_role")
    menus = relationship("Menu", secondary=sys_role_menu)
