FROM java:8
VOLUME /tmp
ADD *.jar app.jar
EXPOSE 9090
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8", "-jar", "/app.jar" ]