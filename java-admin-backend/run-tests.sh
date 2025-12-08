#!/bin/bash

# Java Admin Backend - 测试运行脚本
# 用于运行所有测试并生成报告

echo "================================"
echo "Java Admin Backend - 测试套件"
echo "================================"
echo ""

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}错误: Maven 未安装${NC}"
    echo "请先安装 Maven: https://maven.apache.org/install.html"
    exit 1
fi

echo -e "${YELLOW}1. 清理之前的构建...${NC}"
mvn clean

echo ""
echo -e "${YELLOW}2. 编译项目...${NC}"
mvn compile -DskipTests

if [ $? -ne 0 ]; then
    echo -e "${RED}编译失败！${NC}"
    exit 1
fi

echo ""
echo -e "${YELLOW}3. 运行单元测试...${NC}"
mvn test

TEST_RESULT=$?

echo ""
echo -e "${YELLOW}4. 生成测试覆盖率报告...${NC}"
mvn jacoco:report

echo ""
echo "================================"
if [ $TEST_RESULT -eq 0 ]; then
    echo -e "${GREEN}✓ 所有测试通过！${NC}"
    echo ""
    echo "测试报告位置:"
    echo "  - Surefire 报告: target/surefire-reports/"
    echo "  - JaCoCo 覆盖率: target/site/jacoco/index.html"
    echo ""
    echo "查看覆盖率报告:"
    echo "  open target/site/jacoco/index.html"
else
    echo -e "${RED}✗ 测试失败！${NC}"
    echo ""
    echo "查看详细错误:"
    echo "  cat target/surefire-reports/*.txt"
    exit 1
fi
echo "================================"
