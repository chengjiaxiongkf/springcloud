FROM java:8
VOLUME /tmp
ADD maven-login.jar maven-login.jar
RUN sh -c 'touch /maven-login.jar'
ENV TZ=chengjiaxiong
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /maven-login.jar" ]