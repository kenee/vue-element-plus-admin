from sqlalchemy import Column, String, Integer, DateTime, Text, ForeignKey
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.db.database import Base


class Dictionary(Base):
    __tablename__ = "sys_dictionary"

    id = Column(String(36), primary_key=True, index=True)
    dict_name = Column(String(100), nullable=False)
    dict_code = Column(String(100), unique=True, nullable=False)
    status = Column(Integer, server_default="1")
    remark = Column(Text, nullable=True)
    created_at = Column(DateTime, server_default=func.now())
    updated_at = Column(DateTime, server_default=func.now(), onupdate=func.now())
    items = relationship("DictionaryItem", backref="dictionary", cascade="all, delete-orphan")


class DictionaryItem(Base):
    __tablename__ = "sys_dictionary_item"

    id = Column(String(36), primary_key=True, index=True)
    dict_id = Column(String(36), ForeignKey("sys_dictionary.id"), nullable=False)
    label = Column(String(100), nullable=False)
    value = Column(String(100), nullable=False)
    sort = Column(Integer, server_default="0")
    status = Column(Integer, server_default="1")
    created_at = Column(DateTime, server_default=func.now())
    updated_at = Column(DateTime, server_default=func.now(), onupdate=func.now())
