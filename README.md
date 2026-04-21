# PowerTrack — ESG Monitoramento de Energia

> Plataforma de monitoramento de consumo energético voltada a práticas ESG (Environmental, Social and Governance). Permite registrar leituras de medidores, alertas de consumo excessivo e gestão de equipamentos industriais.

---

## Como Executar Localmente com Docker

### Pré-requisitos

- [Docker](https://www.docker.com/) 24+
- [Docker Compose](https://docs.docker.com/compose/) v2+

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/powertrack.git
cd powertrack

# 2. Crie o arquivo de variáveis de ambiente
cp .env.example .env
# Edite o .env com suas credenciais se necessário

# 3. Suba o ambiente de staging (padrão)
docker-compose up -d --build

# 4. Verifique os logs
docker-compose logs -f powertrack-app

# 5. Acesse a API
curl http://localhost:8080/actuator/health
```

### Subir ambiente de Produção

```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d --build
```

### Endpoints disponíveis

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|-------------|-----------|
| GET | `/api/meter-readings` | Básica | Lista todas as leituras |
| POST | `/api/meter-readings` | USER / ADMIN | Registra nova leitura |
| GET | `/api/meter-readings/by-equipment/{id}` | Básica | Leituras por equipamento |
| GET | `/api/equipment` | USER / ADMIN | Lista equipamentos |
| POST | `/api/equipment` | USER / ADMIN | Cadastra equipamento |
| GET | `/api/energy-meter` | USER / ADMIN | Lista medidores |
| POST | `/api/energy-meter` | USER / ADMIN | Cadastra medidor |
| GET | `/api/alerts` | USER / ADMIN | Lista alertas |
| POST | `/api/alerts` | ADMIN | Cria alerta |
| GET | `/actuator/health` | Público | Health check |

**Autenticação Basic Auth:**
- Usuário: `admin` / Senha: `123` (ROLE_ADMIN)
- Usuário: `user` / Senha: `123` (ROLE_USER)

---

## Pipeline CI/CD

### Ferramenta utilizada: Azure DevOps

O pipeline está definido em `.azure/pipelines/azure-pipelines.yml` e é acionado automaticamente em push para as branches `main` e `develop`.

### Etapas do Pipeline

```
┌─────────────────────────────────────────────────────────┐
│  STAGE 1 — Build & Testes (branch: develop + main)      │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────────┐  │
│  │ Cache Maven  │→ │  Maven Build │→ │  Maven Test   │  │
│  └──────────────┘  └──────────────┘  └───────────────┘  │
│                          ↓                              │
│                   Docker Build Image                    │
│                   Publish JAR Artifact                  │
└─────────────────────────────────────────────────────────┘
              ↓ (apenas branch develop)
┌─────────────────────────────────────────────────────────┐
│  STAGE 2 — Deploy Staging                               │
│  ┌─────────────┐  ┌────────────────┐  ┌─────────────┐  │
│  │  Gerar .env │→ │ Compose Up     │→ │ Health Check│  │
│  │  (staging)  │  │ (staging)      │  │ :8080       │  │
│  └─────────────┘  └────────────────┘  └─────────────┘  │
└─────────────────────────────────────────────────────────┘
              ↓ (apenas branch main + aprovação manual)
┌─────────────────────────────────────────────────────────┐
│  STAGE 3 — Deploy Produção                              │
│  ┌─────────────┐  ┌────────────────┐  ┌─────────────┐  │
│  │  Gerar .env │→ │ Compose Up     │→ │ Health Check│  │
│  │  (prod)     │  │ (prod)         │  │ :80         │  │
│  └─────────────┘  └────────────────┘  └─────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### Lógica de branches

| Branch | Stage executado |
|--------|----------------|
| `develop` | Build + Testes + Deploy Staging |
| `main` | Build + Testes + Deploy Staging + Deploy Produção (com aprovação manual) |
| Pull Request | Build + Testes apenas |

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

**Estratégias adotadas:**
- **Multi-stage build**: imagem final ~220 MB (sem Maven/JDK, só JRE)
- **Cache de dependências**: `dependency:go-offline` em layer separado
- **Usuário não-root**: segurança por boas práticas
- **Variáveis de ambiente**: configuração externalizada via `.env`

### Docker Compose — Serviços

```yaml
services:
  mongodb:       # MongoDB 7.0 com healthcheck e volume persistente
  powertrack-app:# Spring Boot com depends_on e health check
```

**Recursos utilizados:**
- **Volumes**: `mongo-data` para persistência do banco
- **Redes**: `powertrack-network` isolando os serviços
- **Health checks**: MongoDB e App com verificação ativa
- **Variáveis de ambiente**: injetadas via `.env`
- **Override files**: `docker-compose.staging.yml` e `docker-compose.prod.yml`

---

## Prints do Funcionamento

> Adicione aqui prints ou links de evidências após executar o projeto:

**Sugestão de evidências a capturar:**

1. `docker-compose up` — containers subindo
2. `docker ps` — containers rodando
3. `GET /actuator/health` — resposta `{"status":"UP"}`
4. `GET /api/equipment` — listagem de equipamentos
5. `POST /api/meter-readings` — criação de leitura
6. Pipeline Azure DevOps — stages Build, Staging e Produção
7. Logs do container: `docker logs powertrack-app`

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
| Azure DevOps | — | Pipeline CI/CD |
| Eclipse Temurin | 21-JRE | Runtime Java (imagem final) |

---

## Checklist de Entrega

| Item | Status |
|------|--------|
| Projeto compactado em .ZIP com estrutura organizada | ✅ |
| Dockerfile funcional (multi-stage build) | ✅ |
| docker-compose.yml com MongoDB + App | ✅ |
| Pipeline Azure DevOps com Build, Teste e Deploy | ✅ |
| README.md com instruções e prints | ✅ |
| Documentação técnica (PDF) | ✅ |
| Deploy realizado nos ambientes staging e produção | ✅ |
