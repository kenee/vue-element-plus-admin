# Python Admin Backend

基于 FastAPI 的后端管理系统，使用 uv 进行依赖管理。

## 技术栈

- **Web 框架**: FastAPI 0.109.0
- **数据库 ORM**: SQLAlchemy 2.0.25
- **数据库迁移**: Alembic
- **认证**: JWT (python-jose)
- **密码加密**: passlib[bcrypt]
- **验证**: Pydantic 2.5.3
- **ASGI 服务器**: Uvicorn

## 环境要求

- Python 3.8+
- uv (Python 包管理器)

## 安装 uv

### macOS/Linux

```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

### Windows (PowerShell)

```powershell
powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"
```

### 使用 pip 安装

```bash
pip install uv
```

## 项目设置

### 1. 克隆项目

```bash
cd python-admin-backend
```

### 2. 创建虚拟环境

```bash
# 使用 uv 创建虚拟环境
uv venv

# 激活虚拟环境
# macOS/Linux
source .venv/bin/activate
# Windows
.venv\Scripts\activate
```

### 3. 安装依赖

```bash
# 从 pyproject.toml 安装依赖
uv pip install -e .

# 或从 requirements.txt 安装
uv pip install -r requirements.txt
```

## 开发命令

### 启动开发服务器

```bash
uv run uvicorn app.main:app --reload --host 0.0.0.0 --port 3000
```

### 数据库迁移

```bash
# 初始化迁移
alembic init alembic

# 生成迁移脚本
alembic revision --autogenerate -m "描述"

# 执行迁移
alembic upgrade head

# 回滚迁移
alembic downgrade -1
```

### 代码格式化

```bash
# 使用 black 格式化代码
black app/
```

### 类型检查

```bash
# 使用 mypy 进行类型检查
mypy app/
```

## 环境变量

复制 `.env.example` 为 `.env` 并配置环境变量：

```bash
cp .env.example .env
```

主要环境变量：

- `DATABASE_URL`: 数据库连接字符串
- `SECRET_KEY`: JWT 密钥
- `ALGORITHM`: JWT 算法（默认 HS256）
- `ACCESS_TOKEN_EXPIRE_MINUTES`: Token 过期时间

## 项目结构

```
python-admin-backend/
├── app/
│   ├── api/              # API 路由
│   │   ├── auth.py
│   │   ├── user.py
│   │   └── ...
│   ├── core/             # 核心配置
│   │   ├── config.py
│   │   └── security.py
│   ├── db/               # 数据库
│   │   └── database.py
│   ├── models/           # SQLAlchemy 模型
│   │   ├── user.py
│   │   └── ...
│   ├── schemas/          # Pydantic 模式
│   │   ├── user.py
│   │   └── ...
│   └── main.py           # FastAPI 应用入口
├── alembic/              # 数据库迁移
├── tests/                # 测试
├── requirements.txt      # 依赖列表（兼容旧版）
├── pyproject.toml        # uv 依赖管理
└── .env.example          # 环境变量示例
```

## 使用 uv 的优势

- **极快的依赖解析速度**：比 pip 快 10-100 倍
- **依赖锁定**：自动生成精确的依赖锁定文件
- **兼容 pip**：完全兼容 pip 工作流
- **现代化的项目管理**：使用 pyproject.toml 标准配置
- **虚拟环境管理**：内置虚拟环境创建和管理

## API 文档

启动服务后访问：

- Swagger UI: http://localhost:3000/docs
- ReDoc: http://localhost:3000/redoc

## 注意事项

1. **首次运行前**：确保已配置 `.env` 文件并执行数据库迁移
2. **依赖管理**：推荐使用 `uv pip install -e .` 安装项目，确保开发依赖也被安装
3. **虚拟环境**：使用 `uv venv` 创建虚拟环境，避免依赖冲突
4. **Python 版本**：确保使用 Python 3.8 或更高版本

## 故障排除

### uv 命令找不到

确保 uv 已正确安装并添加到 PATH：

```bash
# 验证安装
uv --version

# 如果找不到，需要添加到 PATH
# macOS/Linux: 添加到 ~/.bashrc 或 ~/.zshrc
export PATH="$HOME/.local/bin:$PATH"
```

### 依赖安装失败

```bash
# 清理缓存并重新安装
uv cache clean
uv pip install -e .
```

## License

MIT
