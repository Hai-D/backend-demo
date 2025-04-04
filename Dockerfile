# 使用 OpenJDK 13 作为基础镜像
FROM openjdk:21

# 设置工作目录
WORKDIR /app

# 拷贝 jar 文件（注意替换为你的 jar 文件名）
COPY target/Demo-P-1.0-SNAPSHOT.jar app.jar

# 暴露端口（如果你的 Spring Boot 应用监听在 8080）
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
