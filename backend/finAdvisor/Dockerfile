# Используем мультиархитектурный базовый образ с Java 17 и Maven
FROM maven:3.8.4-openjdk-17 AS build

# Копируем исходный код в контейнер
COPY ./src /app/finAdvisor/src
COPY ./pom.xml /app/finAdvisor/pom.xml
WORKDIR /app/finAdvisor

# Собираем приложение с помощью Maven
RUN mvn clean package

# Базовый образ для запуска Spring Boot приложения на мультиархитектурной платформе
FROM openjdk:17-jdk-slim

# Копируем JAR-файл из предыдущего этапа сборки
COPY --from=build /app/finAdvisor/target/*.jar /app.jar

# Запускаем Spring Boot приложение
CMD ["java", "-jar", "/app.jar"]
