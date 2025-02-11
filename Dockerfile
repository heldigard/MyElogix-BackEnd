FROM gradle:8.12.0-jdk21 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --warning-mode all


FROM bellsoft/liberica-openjdk-debian:21.0.5

# Timezone configuration
ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Java timezone configuration
ENV JAVA_OPTS="-Duser.timezone=America/Bogota -XX:+UnlockExperimentalVMOptions -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/spring-boot-application.jar"]
