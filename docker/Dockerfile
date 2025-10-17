FROM openjdk:8-jre-slim-buster

# 工作目录
RUN mkdir -p /app
WORKDIR /app

# 暴露应用端口
EXPOSE 8888

# 复制本地 Maven/Gradle 打好的 jar 包
COPY ./aikes-3.9.3.jar aikes-3.9.3.jar

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/aikes-3.9.3.jar"]
