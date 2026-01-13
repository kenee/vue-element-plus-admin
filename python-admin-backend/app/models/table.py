from sqlalchemy import Column, String, Integer, DateTime, Text
from sqlalchemy.sql import func
from app.db.database import Base


class TableData(Base):
    __tablename__ = "table_data"

    id = Column(String(36), primary_key=True, index=True)
    author = Column(String(100), nullable=False)
    title = Column(String(200), nullable=False)
    content = Column(Text, nullable=False)
    importance = Column(Integer, default=0)
    display_time = Column(String(50), nullable=True)
    pageviews = Column(Integer, default=0)
    createTime = Column(DateTime(timezone=True), server_default=func.now())
