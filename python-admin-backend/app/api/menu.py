from fastapi import APIRouter, Depends, HTTPException, Request, Query
from sqlalchemy.orm import Session, joinedload
from typing import List, Optional
from app.db.database import get_db
from app.models.menu import Menu as MenuModel
from app.models.user import User as UserModel
from app.schemas.menu import Menu, MenuCreate, MenuUpdate
from app.schemas.common import ResponseModel, ListResponse
from app.menu.service import get_routes_by_user, build_menu_tree
from app.core.security import decode_access_token

router = APIRouter()


@router.get("/routes", response_model=ResponseModel)
async def get_routes(request: Request, db: Session = Depends(get_db)):
    """Get routes for current user based on JWT token"""
    # Get user ID from JWT token
    auth_header = request.headers.get("authorization")
    if not auth_header:
        return ResponseModel(code=0, data=[], message="success")

    # Extract token (handle both "Bearer token" and just "token" formats)
    token = auth_header
    if auth_header.startswith("Bearer "):
        token = auth_header[7:]

    # Decode token to get user info
    token_data = decode_access_token(token)
    if not token_data or "sub" not in token_data:
        return ResponseModel(code=0, data=[], message="success")

    user_id = token_data["sub"]
    routes = await get_routes_by_user(user_id, db)

    return ResponseModel(code=0, data=routes, message="success")


@router.get("", response_model=ResponseModel)
async def get_menus(title: Optional[str] = Query(None), db: Session = Depends(get_db)):
    query = db.query(MenuModel)

    if title:
        query = query.filter(MenuModel.title.like(f"%{title}%"))

    menus = query.order_by(MenuModel.sort, MenuModel.created_at).all()

    # Convert to tree structure
    menu_list = build_menu_tree(menus)

    # Return response in the format expected by frontend: {"code": 0, "data": {"list": [...], "total": ...}, "message": "success"}
    return ResponseModel(
        code=0,
        data={
            "list": menu_list,
            "total": len(menu_list)
        },
        message="success"
    )


@router.post("", response_model=ResponseModel)
async def create_menu(menu: MenuCreate, db: Session = Depends(get_db)):
    import uuid

    db_menu = MenuModel(
        id=str(uuid.uuid4()),
        title=menu.title,
        path=menu.path,
        component=menu.component,
        redirect=menu.redirect,
        name=menu.name,
        icon=menu.icon,
        meta=menu.meta,
        type=menu.type,
        status=menu.status,
        permission=menu.permission,
        sort=menu.sort,
        parent_id=menu.parentId,
    )
    db.add(db_menu)
    db.commit()
    db.refresh(db_menu)

    return ResponseModel(code=0, data=db_menu, message="success")


@router.get("/{id}", response_model=ResponseModel)
async def get_menu(id: str, db: Session = Depends(get_db)):
    menu = (
        db.query(MenuModel).options(joinedload(MenuModel.roles)).filter(MenuModel.id == id).first()
    )
    if not menu:
        raise HTTPException(status_code=404, detail="Menu not found")

    return ResponseModel(code=0, data=menu, message="success")


@router.patch("/{id}", response_model=ResponseModel)
async def update_menu(id: str, menu: MenuUpdate, db: Session = Depends(get_db)):
    db_menu = db.query(MenuModel).filter(MenuModel.id == id).first()
    if not db_menu:
        raise HTTPException(status_code=404, detail="Menu not found")

    update_data = menu.model_dump(exclude_unset=True)

    # Remove fields that shouldn't be updated
    for field in ["id", "createTime", "updateTime", "created_at", "updated_at"]:
        update_data.pop(field, None)

    # Remove children field to avoid TypeORM error
    if "children" in update_data:
        del update_data["children"]

    for field, value in update_data.items():
        if hasattr(db_menu, field):
            setattr(db_menu, field, value)

    db.commit()
    db.refresh(db_menu)

    return ResponseModel(code=0, data=db_menu, message="success")


@router.delete("/{id}", response_model=ResponseModel)
async def delete_menu(id: str, db: Session = Depends(get_db)):
    menu = db.query(MenuModel).filter(MenuModel.id == id).first()
    if not menu:
        raise HTTPException(status_code=404, detail="Menu not found")

    db.delete(menu)
    db.commit()

    return ResponseModel(code=0, data=None, message="success")
