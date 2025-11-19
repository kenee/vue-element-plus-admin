#!/bin/bash

# 创建环境配置文件脚本

echo "正在创建环境配置文件..."

# 创建 .env.dev
cat > .env.dev << 'EOF'
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
EOF

# 创建 .env.test
cat > .env.test << 'EOF'
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
EOF

# 创建 .env.pro
cat > .env.pro << 'EOF'
# 生产环境配置
NODE_ENV=production
PORT=3000
API_PREFIX=api

# 数据库配置
DB_TYPE=mysql
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=root
DB_PASSWORD=your-production-password
DB_DATABASE=nest_admin_prod

# JWT 配置
JWT_SECRET=your-production-secret-key-change-this
JWT_EXPIRES_IN=24h

# CORS 配置
CORS_ORIGIN=https://your-production-domain.com
EOF

echo "✅ 环境配置文件创建完成！"
echo ""
echo "已创建以下文件："
echo "  - .env.dev (开发环境)"
echo "  - .env.test (测试环境)"
echo "  - .env.pro (生产环境)"
echo ""
echo "⚠️  请根据实际情况修改生产环境配置文件 (.env.pro) 中的敏感信息！"

