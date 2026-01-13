from fastapi import APIRouter
from app.schemas.workplace import WorkplaceTotal, Project, Dynamic, Team, RadarData
from app.schemas.common import ResponseModel
from datetime import datetime

router = APIRouter()


@router.get("/total", response_model=ResponseModel)
async def get_workplace_total():
    return ResponseModel(
        code=0, data=WorkplaceTotal(project=12, access=5600, todo=48), message="success"
    )


@router.get("/project", response_model=ResponseModel)
async def get_projects():
    return ResponseModel(
        code=0,
        data=[
            Project(
                name="Alipay",
                icon="alipay",
                message="Alipay App project",
                personal="zhang san",
                time=datetime.now().strftime("%Y-%m-%d"),
            ),
            Project(
                name="Vue",
                icon="vue",
                message="Vue Admin project",
                personal="li si",
                time=datetime.now().strftime("%Y-%m-%d"),
            ),
        ],
        message="success",
    )


@router.get("/dynamic", response_model=ResponseModel)
async def get_dynamics():
    return ResponseModel(
        code=0,
        data=[
            Dynamic(
                keys=["zhang san", "created", "project", "Alipay"],
                time=datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            ),
            Dynamic(
                keys=["li si", "updated", "project", "Vue"],
                time=datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            ),
        ],
        message="success",
    )


@router.get("/team", response_model=ResponseModel)
async def get_teams():
    return ResponseModel(
        code=0,
        data=[
            Team(name="Frontend", icon="code"),
            Team(name="Backend", icon="server"),
            Team(name="Design", icon="brush"),
        ],
        message="success",
    )


@router.get("/radar", response_model=ResponseModel)
async def get_radar_data():
    return ResponseModel(
        code=0,
        data=[
            RadarData(personal=80, team=75, max=100, name="Communication"),
            RadarData(personal=90, team=85, max=100, name="Development"),
            RadarData(personal=70, team=80, max=100, name="Management"),
            RadarData(personal=85, team=90, max=100, name="Learning"),
            RadarData(personal=75, team=70, max=100, name="Creativity"),
        ],
        message="success",
    )
