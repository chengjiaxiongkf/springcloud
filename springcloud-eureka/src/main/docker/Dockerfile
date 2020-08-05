FROM java:8
VOLUME /tmp
ADD springcloud-eureka.jar springcloud-eureka.jar
RUN sh -c 'touch /springcloud-eureka.jar'
ENV TZ=chengjiaxiong
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /springcloud-eureka.jar" ]