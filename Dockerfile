# Stage 1: Build (使用作業指定的 Maven 與 JDK 17 Alpine 版本)
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# 將專案所有檔案複製進去並進行編譯
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run (使用輕量級的執行環境)
FROM eclipse-temurin:17-alpine
WORKDIR /app

# 從前一個階段（build）把包裝好的 jar 檔複製過來
COPY --from=build /app/target/*.jar app.jar

# 監聽 8080 連接埠
EXPOSE 8080

# 啟動指令
ENTRYPOINT ["java", "-jar", "app.jar"]