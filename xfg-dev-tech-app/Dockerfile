# 基础镜像 openjdk:8-jre-slim openjdk:8-jdk-alpine 8-jdk-alpine
FROM openjdk:8-jdk

# 作者
MAINTAINER xiaofuge

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/xfg-dev-tech-app.jar /xfg-dev-tech-app.jar

# arthas https://hub.docker.com/r/hengyunabc/arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

## 在镜像运行为容器后执行的命令
ENTRYPOINT ["sh","-c","java -Djava.security.egd=file:/dev/./urandom -jar $JAVA_OPTS /xfg-dev-tech-app.jar $PARAMS"]

# 暴露容器的端口
EXPOSE 8989