from fastapi import APIRouter, Depends, HTTPException, status, Request
from sqlalchemy.orm import Session, joinedload
from app.db.database import get_db
from app.core.security import verify_password, create_access_token, decode_access_token
from app.models.user import User as UserModel
from app.models.role import Role as RoleModel
from app.schemas.auth import LoginRequest, LoginResponse

router = APIRouter()


@router.post("/login")
async def login(login_data: LoginRequest, db: Session = Depends(get_db)):
    user = (
        db.query(UserModel)
        .options(joinedload(UserModel.roles))
        .filter(UserModel.username == login_data.username)
        .first()
    )
    if not user or not verify_password(login_data.password, str(user.password)):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect username or password",
            headers={"WWW-Authenticate": "Bearer"},
        )

    access_token = create_access_token(data={"sub": user.username})

    role_value = ""
    role_id = ""
    if user.roles and len(user.roles) > 0:
        role_value = user.roles[0].role_value
        role_id = user.roles[0].id

    user_dict = {
        "username": user.username,
        "role": role_value,
        "roleId": role_id,
        "access_token": access_token,
    }

    return LoginResponse(code=0, data=user_dict, message="success")


@router.get("/logout")
async def logout():
    return {"code": 0, "data": None, "message": "success"}
