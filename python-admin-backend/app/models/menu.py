from sqlalchemy import Column, String, Integer, DateTime, Text
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from sqlalchemy.dialects.mysql import JSON
from app.db.database import Base
from app.models import sys_role_menu


class Menu(Base):
    __tablename__ = "sys_menu"

    id = Column(String(36), primary_key=True, index=True)
    parent_id = Column(String(36), nullable=True)
    path = Column(String(255), nullable=True)
    component = Column(String(255), nullable=True)
    redirect = Column(String(255), nullable=True)
    title = Column(String(100), nullable=False)
    name = Column(String(100), nullable=True)
    icon = Column(String(50), nullable=True)
    meta = Column(JSON, nullable=True)
    type = Column(Integer, server_default="0")
    status = Column(Integer, server_default="1")
    permission = Column(String(100), nullable=True)
    sort = Column(Integer, server_default="0")
    created_at = Column(DateTime, server_default=func.now())
    updated_at = Column(DateTime, server_default=func.now(), onupdate=func.now())
    roles = relationship("Role", secondary=sys_role_menu, back_populates="menus")
