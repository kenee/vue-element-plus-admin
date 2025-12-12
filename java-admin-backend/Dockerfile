# 第一阶段：使用GraalVM和Maven进行原生编译
FROM docker.io/graalvm/graalvm-ce:25 as builder

# 安装必要的依赖
RUN microdnf install -y maven gcc gcc-c++ zlib-devel && microdnf clean all

# 安装GraalVM native-image工具
RUN gu install native-image

# 设置工作目录
WORKDIR /app

# 复制pom.xml和源代码
COPY pom.xml .
COPY src ./src

# 执行原生编译
RUN mvn -Pnative native:compile

# 第二阶段：使用轻量级镜像运行原生应用
FROM fedora:latest

# 安装tini
RUN microdnf install -y tini && microdnf clean all

# 设置工作目录
WORKDIR /app

# 从构建阶段复制编译好的原生应用
COPY --from=builder /app/target/java-admin-backend .

# 暴露应用端口（根据实际应用配置调整）
EXPOSE 3000

# 运行原生应用，使用tini作为init进程
ENTRYPOINT ["tini", "--", "./java-admin-backend"]