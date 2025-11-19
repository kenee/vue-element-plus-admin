# 环境配置文件说明

本项目支持多环境配置，通过 `NODE_ENV` 环境变量来加载对应的 `.env` 文件。

## 环境文件

需要创建以下环境配置文件：

- `.env.dev` - 开发环境
- `.env.test` - 测试环境  
- `.env.pro` - 生产环境
- `.env` - 默认配置（可选，作为后备）

## 创建环境文件

### 1. 开发环境 (.env.dev)

```env
# 开发环境配置
NODE_ENV=development
PORT=3000
API_PREFIX=api

# 数据库配置
DB_TYPE=mysql
DB_HOST=localhost
DB_PORT=3307
DB_USERNAME=root
DB_PASSWORD=root
DB_DATABASE=nest_admin

# JWT 配置
JWT_SECRET=dev-secret-key
JWT_EXPIRES_IN=60m

# CORS 配置
CORS_ORIGIN=http://localhost:4000
```

### 2. 测试环境 (.env.test)

```env
# 测试环境配置
NODE_ENV=test
PORT=3001
API_PREFIX=api

# 数据库配置
DB_TYPE=mysql
DB_HOST=localhost
DB_PORT=3307
DB_USERNAME=root
DB_PASSWORD=root
DB_DATABASE=nest_admin_test

# JWT 配置
JWT_SECRET=test-secret-key
JWT_EXPIRES_IN=30m

# CORS 配置
CORS_ORIGIN=http://localhost:4000
```

### 3. 生产环境 (.env.pro)

```env
# 生产环境配置
NODE_ENV=production
PORT=3000
API_PREFIX=api

# 数据库配置
DB_TYPE=mysql
DB_HOST=your-production-host
DB_PORT=3306
DB_USERNAME=your-production-username
DB_PASSWORD=your-production-password
DB_DATABASE=nest_admin_prod

# JWT 配置
JWT_SECRET=your-production-secret-key-change-this
JWT_EXPIRES_IN=24h

# CORS 配置
CORS_ORIGIN=https://your-production-domain.com
```

## 使用方法

### 启动不同环境

```bash
# 开发环境
pnpm run start:dev

# 测试环境
pnpm run start:test

# 生产环境（开发模式）
pnpm run start:pro

# 生产环境（编译后运行）
pnpm run build
pnpm run start:prod
```

### 手动指定环境

```bash
# 使用开发环境
NODE_ENV=dev pnpm run start

# 使用测试环境
NODE_ENV=test pnpm run start

# 使用生产环境
NODE_ENV=pro pnpm run start
```

## 配置加载顺序

1. 首先尝试加载 `.env.{NODE_ENV}` (例如 `.env.dev`)
2. 如果不存在，则加载 `.env` 作为后备
3. 如果都不存在，使用代码中的默认值

## 注意事项

- 所有 `.env*` 文件都应该添加到 `.gitignore` 中
- 生产环境的敏感信息（如密码、密钥）应该使用环境变量或密钥管理服务
- 不要将包含真实密码的 `.env` 文件提交到版本控制系统

