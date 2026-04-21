# ============================================================
# PowerTrack - Dockerfile Multi-Stage Build
# Java 21 + Spring Boot 3.3.4 + MongoDB
# ============================================================

# ============================
# Stage 1: Build
# ============================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila sem rodar testes
COPY src ./src
RUN mvn clean package -DskipTests

# ============================
# Stage 2: Runtime
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Cria usuário não-root por boas práticas de segurança
RUN addgroup --system powertrack && adduser --system --ingroup powertrack powertrack

# Copia o JAR gerado no stage de build
COPY --from=build /app/target/*.jar app.jar

# Ajusta permissões
RUN chown powertrack:powertrack app.jar

USER powertrack

EXPOSE 8080

# Variável de ambiente com perfil padrão (pode ser sobrescrita pelo Compose)
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]
