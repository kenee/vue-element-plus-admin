from sqlalchemy import Column, String, Integer, DateTime
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.db.database import Base
from app.models import sys_user_role


class User(Base):
    __tablename__ = "sys_user"

    id = Column(String(36), primary_key=True, index=True)
    username = Column(String(50), unique=True, index=True, nullable=False)
    password = Column(String(100))
    email = Column(String(100), nullable=True)
    nickname = Column(String(50), nullable=True)
    dept_id = Column(String(36), nullable=True)
    status = Column(Integer, server_default="1")
    created_at = Column(DateTime, server_default=func.now())
    updated_at = Column(DateTime, server_default=func.now(), onupdate=func.now())
    roles = relationship("Role", secondary=sys_user_role)
