from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.api import menu, department, auth, table, user, role
from app.db.database import engine, Base

# Create all database tables
Base.metadata.create_all(bind=engine)

# Create FastAPI instance
app = FastAPI(
    title="Python Admin Backend",
    description="Python backend for Vue Element Plus Admin",
    version="1.0.0"
)

# Configure CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allow all origins for development
    allow_credentials=True,
    allow_methods=["*"],  # Allow all methods
    allow_headers=["*"],  # Allow all headers
)

# Include API routers
app.include_router(auth.router, prefix="/api", tags=["auth"])
app.include_router(menu.router, prefix="/api/menu", tags=["menu"])
app.include_router(department.router, prefix="/api/department", tags=["department"])
app.include_router(table.router, prefix="/api/table", tags=["table"])
app.include_router(user.router, prefix="/api/user", tags=["user"])
app.include_router(role.router, prefix="/api/role", tags=["role"])


@app.get("/")
async def root():
    return {"message": "Python Admin Backend API is running"}


@app.get("/health")
async def health_check():
    return {"status": "ok", "message": "Service is healthy"}
