from fastapi import APIRouter

router = APIRouter()


@router.get("/upload-url")
async def get_upload_url():
    return {"code": 0, "data": {"url": "/api/upload"}, "message": "success"}
