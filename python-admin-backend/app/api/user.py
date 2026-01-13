from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session, joinedload
from typing import List, Optional
from app.db.database import get_db
from app.models.user import User as UserModel
from app.models.role import Role as RoleModel
from app.schemas.user import User, UserCreate, UserUpdate, UserListParams
from app.core.security import get_password_hash
from app.schemas.common import ResponseModel, ListResponse

router = APIRouter()


@router.get("", response_model=ResponseModel)
async def get_users(
    page: int = Query(1, ge=1, alias="pageIndex"),
    pageSize: int = Query(10, ge=1, le=100),
    deptId: Optional[str] = Query(None),
    db: Session = Depends(get_db),
    username: Optional[str] = Query(None),
    account: Optional[str] = Query(None),
):
    query = db.query(UserModel).options(joinedload(UserModel.roles))

    if deptId:
        query = query.filter(UserModel.dept_id == deptId)
    if username:
        query = query.filter(UserModel.username.like(f"%{username}%"))
    if account:
        query = query.filter(UserModel.username.like(f"%{account}%"))

    total = query.count()
    users = query.offset((page - 1) * pageSize).limit(pageSize).all()

    # Convert SQLAlchemy models to dictionaries
    user_list = []
    for user in users:
        user_dict = {
            "id": user.id,
            "username": user.username,
            "email": user.email,
            "nickname": user.nickname,
            "deptId": user.dept_id,
            "created_at": user.created_at.isoformat() if user.created_at else None,
            "updated_at": user.updated_at.isoformat() if user.updated_at else None,
            "roles": [
                {"id": role.id, "role_name": role.role_name, "role_value": role.role_value}
                for role in user.roles
            ] if user.roles else None
        }
        user_list.append(user_dict)
    
    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": user_list,
            "total": total
        },
        message="success"
    )


@router.post("", response_model=ResponseModel)
async def create_user(user: UserCreate, db: Session = Depends(get_db)):
    existing_user = db.query(UserModel).filter(UserModel.username == user.username).first()
    if existing_user:
        raise HTTPException(status_code=400, detail="Username already exists")

    hashed_password = get_password_hash(user.password)
    import uuid

    db_user = UserModel(
        id=str(uuid.uuid4()),
        username=user.username,
        password=hashed_password,
        email=user.email,
        nickname=user.nickname,
        dept_id=user.deptId if user.deptId else None,
    )

    db.add(db_user)
    db.commit()
    db.refresh(db_user)

    return ResponseModel(code=0, data=db_user, message="success")


@router.get("/{id}", response_model=ResponseModel)
async def get_user(id: str, db: Session = Depends(get_db)):
    user = (
        db.query(UserModel).options(joinedload(UserModel.roles)).filter(UserModel.id == id).first()
    )
    if not user:
        raise HTTPException(status_code=404, detail="User not found")

    return ResponseModel(code=0, data=user, message="success")


@router.patch("/{id}", response_model=ResponseModel)
async def update_user(id: str, user: UserUpdate, db: Session = Depends(get_db)):
    db_user = db.query(UserModel).filter(UserModel.id == id).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")

    update_data = user.model_dump(exclude_unset=True)
    if "password" in update_data:
        update_data["password"] = get_password_hash(update_data["password"])

    for field, value in update_data.items():
        if hasattr(db_user, field):
            setattr(db_user, field, value)

    db.commit()
    db.refresh(db_user)

    return ResponseModel(code=0, data=db_user, message="success")


@router.delete("/{id}", response_model=ResponseModel)
async def delete_user(id: str, db: Session = Depends(get_db)):
    user = db.query(UserModel).filter(UserModel.id == id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")

    db.delete(user)
    db.commit()

    return ResponseModel(code=0, data=None, message="success")


@router.post("/delete", response_model=ResponseModel)
async def delete_users(ids: List[str], db: Session = Depends(get_db)):
    users = db.query(UserModel).filter(UserModel.id.in_(ids)).all()
    for user in users:
        db.delete(user)
    db.commit()

    return ResponseModel(code=0, data=None, message="success")
