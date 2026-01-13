from typing import List, Optional, Dict, Any
from sqlalchemy.orm import Session, joinedload
from sqlalchemy import and_, or_
from app.models.menu import Menu as MenuModel
from app.models.role import Role as RoleModel
from app.models.user import User as UserModel
import json


def convert_menu_to_dict(menu: MenuModel) -> Dict[str, Any]:
    """Convert menu entity to dict with proper meta structure"""
    result = {
        "id": menu.id,
        "path": menu.path or "",
        "component": menu.component or "#",
        "redirect": menu.redirect or "",
        "name": menu.name or "",
        "title": menu.title,
        "type": menu.type,
        "parentId": menu.parent_id,
        "status": menu.status,
        "permission": menu.permission,
        "sort": menu.sort,
    }

    # Build meta object
    meta = {"title": menu.title}
    if menu.icon:
        meta["icon"] = menu.icon
    if menu.meta and isinstance(menu.meta, dict):
        meta.update(menu.meta)

    # Add permission to meta if exists
    if menu.permission:
        permissions = menu.permission.split(",")
        permissions = [p.strip() for p in permissions if p.strip()]
        if permissions:
            meta["permission"] = permissions

    result["meta"] = meta
    return result


def build_menu_tree(menus: List[MenuModel]) -> List[Dict[str, Any]]:
    """Build tree structure from flat menu list"""
    menu_map = {menu.id: menu for menu in menus}
    root_menus: List[Dict[str, Any]] = []

    for menu in menus:
        menu_dict = convert_menu_to_dict(menu)

        if menu.parent_id and menu.parent_id in menu_map:
            parent = menu_dict
            if "children" not in parent:
                parent["children"] = []
            parent["children"].append(menu_dict)
        else:
            root_menus.append(menu_dict)

    return root_menus


async def get_routes_by_user(user_id: str, db: Session) -> List[Dict[str, Any]]:
    """Get routes for a user based on their roles"""
    # Get user with roles
    user = (
        db.query(UserModel)
        .options(joinedload(UserModel.roles))
        .filter(UserModel.id == user_id)
        .first()
    )
    if not user or not user.roles or len(user.roles) == 0:
        return []

    # Collect all menu IDs from user's roles
    menu_id_set = set()
    for role in user.roles:
        if role.menus and len(role.menus) > 0:
            for menu in role.menus:
                menu_id_set.add(menu.id)

    if not menu_id_set:
        return []

    # Get menus from database
    from sqlalchemy import func

    menus = (
        db.query(MenuModel)
        .filter(and_(MenuModel.id.in_(list(menu_id_set)), MenuModel.status == 1))  # Only return enabled menus
        .order_by(MenuModel.sort.asc(), MenuModel.created_at.asc())
        .all()
    )

    return convert_menus_to_routes(menus)


def convert_menus_to_routes(menus: List[MenuModel]) -> List[Dict[str, Any]]:
    """Convert database menus to frontend route format"""
    menu_map = {menu.id: menu for menu in menus}
    root_routes: List[Dict[str, Any]] = []
    name_set = set()

    # First pass: create all route nodes
    for menu in menus:
        meta = {}
        if menu.meta and isinstance(menu.meta, dict):
            meta = {**menu.meta}
            if "title" not in meta or not meta["title"]:
                meta["title"] = menu.title
        else:
            meta["title"] = menu.title

        if menu.icon:
            meta["icon"] = menu.icon

        # Handle permissions
        if menu.permission:
            permissions = menu.permission.split(",")
            permissions = [p.strip() for p in permissions if p.strip()]
            if permissions:
                meta["permission"] = permissions

        # Generate unique name
        name = menu.name or generate_menu_name(menu.path or "", menu.title)
        unique_name = name
        counter = 1
        while unique_name in name_set:
            unique_name = f"{name}{counter}"
            counter += 1
        name_set.add(unique_name)

        # Handle redirect
        redirect = menu.redirect or ""

        route = {
            "name": unique_name,
            "path": menu.path or "",
            "component": menu.component or "#",
            "redirect": redirect,
            "meta": meta,
            "children": [],
        }

        menu_map[menu.id] = route

    # Second pass: build tree structure
    for menu in menus:
        route = menu_map[menu.id]
        if menu.parent_id and menu.parent_id in menu_map:
            parent = menu_map[menu.parent_id]
            if "children" not in parent:
                parent["children"] = []
            parent["children"].append(route)
        else:
            root_routes.append(route)

    return filter_hidden_menus(root_routes)


def filter_hidden_menus(routes: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
    """Recursively filter out hidden menus and handle directory redirects"""
    return list(
        filter(
            lambda r: not r.get("meta", {}).get("hidden", False),
            map(lambda r: process_route_children(r), routes),
        )
    )


def process_route_children(route: Dict[str, Any]) -> Dict[str, Any]:
    """Process route children recursively"""
    if route.get("children") and len(route["children"]) > 0:
        route["children"] = filter_hidden_menus(route["children"])

        if not route.get("redirect") and len(route["children"]) > 0:
            first_child = route["children"][0]
            if first_child.get("path"):
                parent_path = route.get("path", "")
                child_path = first_child["path"]
                if child_path.startswith("/"):
                    redirect = child_path
                else:
                    redirect = f"{parent_path}/{child_path}"
                route["redirect"] = redirect

    return route


def generate_menu_name(path: str, title: Optional[str] = None) -> str:
    """Generate name from path"""
    if not path:
        return title or "" if title else ""

    # Remove leading and trailing slashes
    clean_path = path.strip("/")

    # Convert to PascalCase
    parts = clean_path.split("/" or "-")
    if not parts:
        return title or "" if title else ""

    def to_pascal_case(part: str) -> str:
        cleaned = "".join(c for c in part if c.isalnum())
        if cleaned:
            return cleaned[0].upper() + cleaned[1:]
        return part

    return "".join(to_pascal_case(p) for p in parts)
