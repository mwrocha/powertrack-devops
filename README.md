# Projeto - PowerTrack ESG

> **PowerTrack** é uma API REST desenvolvida em Java 21 com Spring Boot 3.3.4 para monitoramento de consumo energético de equipamentos industriais, contribuindo com práticas de **ESG (Environmental, Social and Governance)**. O projeto integra práticas completas de DevOps com pipeline CI/CD, containerização e orquestração.

**Integrante:** Mayke Rocha  
**Repositório:** https://github.com/mwrocha/powertrack-devops  
**Disciplina:** Navegando pelo Mundo DevOps — FIAP 2025

---

## Como Executar Localmente com Docker

### Pré-requisitos

- [Docker](https://www.docker.com/) 24+
- [Docker Compose](https://docs.docker.com/compose/) v2+

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/mwrocha/powertrack-devops.git
cd powertrack-devops

# 2. Suba os containers
docker-compose up -d

# 3. Verifique se os containers estão rodando
docker ps

# 4. Acesse o health check
curl http://localhost:8080/actuator/health
```

### Endpoints disponíveis

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|-------------|-----------|
| GET | `/api/meter-readings` | Básica | Lista todas as leituras |
| POST | `/api/meter-readings` | Básica | Registra nova leitura |
| GET | `/api/equipment` | Básica | Lista equipamentos |
| POST | `/api/equipment` | Básica | Cadastra equipamento |
| GET | `/api/energy-meter` | Básica | Lista medidores |
| GET | `/api/alerts` | Básica | Lista alertas |
| GET | `/actuator/health` | Público | Health check |

**Autenticação Basic Auth:**
- Usuário: `admin` / Senha: `123`
- Usuário: `user` / Senha: `123`

### Resetar o ambiente

```bash
docker-compose down
docker volume rm powertrack_mongo-data
docker-compose up -d
```

---

## Pipeline CI/CD

### Ferramenta utilizada: GitHub Actions

O pipeline está definido em `.github/workflows/pipeline.yml` e é acionado automaticamente em todo push para as branches `main` e `develop`.

### Etapas do Pipeline

```
┌─────────────────────────────────────────┐
│  Job 1 — Build e Testes                 │
│  Configurar Java 21                     │
│  → mvn clean package                   │
│  → mvn test                            │
│  → docker build                        │
│  → Publicar JAR como artefato          │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│  Job 2 — Deploy Staging                 │
│  Docker Compose Up (staging)            │
│  → Health Check :8080                  │
└─────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────┐
│  Job 3 — Deploy Produção                │
│  Docker Compose Up (produção)           │
│  → Health Check :80                    │
└─────────────────────────────────────────┘
```

### Resultado do Pipeline

O pipeline executa com sucesso em aproximadamente 5 minutos:
- **Build e Testes:** 1m 13s ✅
- **Deploy Staging:** 1m 52s ✅
- **Deploy Produção:** 1m 45s ✅

---

## Containerização

### Dockerfile — Multi-Stage Build

```dockerfile
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN addgroup --system powertrack && adduser --system --ingroup powertrack powertrack
COPY --from=build /app/target/*.jar app.jar
RUN chown powertrack:powertrack app.jar
USER powertrack
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Estratégias adotadas

| Estratégia | Benefício |
|-----------|-----------|
| Multi-stage build | Imagem final sem Maven/JDK — apenas JRE (~220 MB) |
| Cache de dependências | Layer separado para pom.xml — rebuild mais rápido |
| Usuário não-root | Segurança: processo roda como usuário `powertrack` |
| ENV externalizado | Configuração via variáveis de ambiente |
| Health check | Docker verifica `/actuator/health` antes de considerar container pronto |

### Docker Compose — Serviços

```yaml
services:
  mongodb:       # MongoDB 7.0 com healthcheck e volume persistente
  powertrack-app:# Spring Boot com depends_on e health check
```

**Recursos utilizados:**
- **Volumes:** `mongo-data` para persistência do banco
- **Redes:** `powertrack-network` isolando os serviços
- **Health checks:** MongoDB e App com verificação ativa
- **Variáveis de ambiente:** injetadas via `.env`

---

## Prints do Funcionamento

### 1. Containers rodando

![docker ps](prints/docker-ps.png)

Dois containers saudáveis: `powertrack-app` (porta 8080) e `powertrack-mongodb` (porta 27017).

### 2. Docker Compose Up

![docker-compose up](prints/docker-compose-up.png)

Containers `powertrack-mongodb` e `powertrack-app` iniciados com sucesso.

### 3. Health Check — Aplicação UP

![actuator health](prints/actuator-health.png)

Endpoint `/actuator/health` retornando `status: UP` com MongoDB conectado.

### 4. API funcionando com autenticação

![api equipment 200](prints/api-equipment-200.png)

`GET /api/equipment` com Basic Auth retornando `200 OK` com a lista de equipamentos.

### 5. Autenticação bloqueando acesso sem credenciais

![api equipment 401](prints/api-equipment-401.png)

`GET /api/equipment` sem autenticação retornando `401 Unauthorized`.

### 6. Pipeline CI/CD — Todos os stages verdes

![pipeline github actions](prints/pipeline-success.png)

Pipeline com 3 stages executados com sucesso: Build e Testes → Deploy Staging → Deploy Produção.

---

## Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|-----------|--------|--------|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.3.4 | Framework da aplicação |
| Spring Security | 6.x | Autenticação Basic Auth |
| Spring Data MongoDB | 4.x | Persistência NoSQL |
| MongoDB | 7.0 | Banco de dados |
| Maven | 3.9 | Build e dependências |
| Docker | 24+ | Containerização |
| Docker Compose | v2 | Orquestração local |
| GitHub Actions | — | Pipeline CI/CD |
| Eclipse Temurin | 21-JRE | Runtime Java na imagem final |
| Flapdoodle Embed MongoDB | 4.12.0 | MongoDB em memória para testes |

---

## Testes BDD — Cucumber

### Ferramentas utilizadas
- **Cucumber 7.18.0** — BDD com Gherkin em português
- **REST Assured** — Testes de API (status code + corpo JSON)
- **JSON Schema Validator** — Validação de contrato
- **Flapdoodle Embed MongoDB** — MongoDB em memória para testes

### Como executar os testes

```bash
mvn test
```

### Cenários implementados

| Feature | Cenários | Tipos |
|---------|----------|-------|
| Equipment | 3 | Listar ✅, Criar ✅, Sem auth 401 ✅ |
| Energy Meter | 3 | Listar ✅, Criar ✅, Sem auth 401 ✅ |
| Meter Reading | 4 | Listar ✅, Criar ✅, Por equipamento ✅, Sem auth 401 ✅ |
| Alert | 4 | Listar ✅, Criar ✅, Criar sem auth 401 ✅, Listar sem auth 401 ✅ |

### Resultado da execução

```
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS
```


## Checklist de Entrega

| Item | Status |
|------|--------|
| Projeto compactado em .ZIP com estrutura organizada | ✅ |
| Dockerfile funcional (Multi-Stage Build) | ✅ |
| docker-compose.yml com MongoDB + App | ✅ |
| Pipeline com etapas de Build, Teste e Deploy | ✅ |
| README.md com instruções e prints | ✅ |
| Documentação técnica com evidências (PDF) | ✅ |
| Deploy realizado nos ambientes staging e produção | ✅ |
| Testes BDD com Cucumber (14 cenários Gherkin) | ✅ |
| Validação de JSON Schema para todas as APIs | ✅ |
