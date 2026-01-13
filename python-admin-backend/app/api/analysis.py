from fastapi import APIRouter
from app.schemas.analysis import AnalysisTotal, UserAccessSource, WeeklyUserActivity, MonthlySales
from app.schemas.common import ResponseModel

router = APIRouter()


@router.get("/total", response_model=ResponseModel)
async def get_analysis_total():
    return ResponseModel(
        code=0,
        data=AnalysisTotal(users=1234, messages=5678, moneys=99999, shoppings=8888),
        message="success",
    )


@router.get("/userAccessSource", response_model=ResponseModel)
async def get_user_access_source():
    return ResponseModel(
        code=0,
        data=[
            UserAccessSource(value=3350, name="Direct"),
            UserAccessSource(value=3100, name="Email"),
            UserAccessSource(value=2340, name="Ad Networks"),
            UserAccessSource(value=1350, name="Video Ads"),
            UserAccessSource(value=2480, name="Search Engines"),
        ],
        message="success",
    )


@router.get("/weeklyUserActivity", response_model=ResponseModel)
async def get_weekly_user_activity():
    return ResponseModel(
        code=0,
        data=[
            WeeklyUserActivity(value=150, name="Mon"),
            WeeklyUserActivity(value=230, name="Tue"),
            WeeklyUserActivity(value=224, name="Wed"),
            WeeklyUserActivity(value=218, name="Thu"),
            WeeklyUserActivity(value=135, name="Fri"),
            WeeklyUserActivity(value=147, name="Sat"),
            WeeklyUserActivity(value=260, name="Sun"),
        ],
        message="success",
    )


@router.get("/monthlySales", response_model=ResponseModel)
async def get_monthly_sales():
    return ResponseModel(
        code=0,
        data=[
            MonthlySales(name="Jan", estimate=1000, actual=1200),
            MonthlySales(name="Feb", estimate=1100, actual=950),
            MonthlySales(name="Mar", estimate=1300, actual=1450),
            MonthlySales(name="Apr", estimate=1200, actual=1100),
            MonthlySales(name="May", estimate=1400, actual=1350),
            MonthlySales(name="Jun", estimate=1500, actual=1600),
        ],
        message="success",
    )
