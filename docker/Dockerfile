FROM openjdk:8-jre-slim-buster

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 工作目录
RUN mkdir -p /app
WORKDIR /app

# 设置环境变量
ENV LOG_PATH=/app/logs
ENV TZ=Asia/Shanghai


# 创建日志目录
RUN mkdir -p ${LOG_PATH}

# 暴露应用端口
EXPOSE 8081

# 复制本地 Maven/Gradle 打好的 jar 包
COPY ./aikes-3.9.4.jar aikes-3.9.4.jar

# 启动命令
ENTRYPOINT ["java", "-Duser.timezone=Asia/Shanghai", "-jar", "/app/aikes-3.9.4.jar"]
