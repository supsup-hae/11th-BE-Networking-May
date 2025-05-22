FROM openjdk:17

# 작업 디렉토리 설정
WORKDIR /app

# 환경 변수 설정
ENV TZ=Asia/Seoul

# 애플리케이션 포트 노출
EXPOSE 8080

# JAR 파일 복사
COPY build/libs/*.jar app.jar

# 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]