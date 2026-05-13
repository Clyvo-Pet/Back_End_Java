# Clyvo Pet — Backend Java

Sistema de gestao para clinica veterinaria desenvolvido com Java e Spring Boot.

---

## Descricao do Projeto

O Clyvo Pet e uma plataforma digital voltada ao gerenciamento de clinicas veterinarias e pet shops. O sistema permite o cadastro de usuarios, pets, consultas, assinaturas de planos, produtos e pedidos, oferecendo uma API REST completa para integracao com aplicacoes mobile e web.

---

## Beneficios para o Negocio

- Centralizacao do gerenciamento de pets e seus donos em uma unica plataforma
- Agendamento inteligente de consultas com validacao de conflitos de data
- Sistema de assinaturas com desconto automatico aplicado nos pedidos
- Dashboard de saude dos pets com alertas de vacinacao
- API documentada com Swagger para integracao rapida com frontends
- Infraestrutura conteinerizada com Docker para deploy simples e escalavel

---

## Desenho Macro da Arquitetura

```
[Usuario / Mobile App]
        |
        v
[API REST - Spring Boot :8080]
        |
        v
[Oracle Database XE :1521]

Infraestrutura na Azure:
+---------------------------+
|  VM Linux (Ubuntu 22.04)  |
|  +---------------------+  |
|  | Container: clyvo-app|  |  <- Spring Boot
|  +---------------------+  |
|  +---------------------+  |
|  | Container: oracle   |  |  <- Oracle XE
|  +---------------------+  |
|  Volume: clyvo-oracle-data|
+---------------------------+
```

---

## Rotas da API

### Usuarios
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/users | Criar usuario |
| GET | /api/users | Listar usuarios |
| GET | /api/users/{id} | Buscar por ID |
| GET | /api/users/search?name= | Buscar por nome |
| PUT | /api/users/{id} | Atualizar usuario |
| DELETE | /api/users/{id} | Deletar usuario |
| GET | /api/users/{id}/dashboard | Dashboard do usuario |

### Pets
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/pets | Cadastrar pet |
| GET | /api/pets | Listar pets |
| GET | /api/pets/{id} | Buscar por ID |
| GET | /api/pets/user/{userId} | Pets por usuario |
| GET | /api/pets/species/{species} | Pets por especie |
| PUT | /api/pets/{id} | Atualizar pet |
| DELETE | /api/pets/{id} | Remover pet |

### Consultas
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/appointments | Agendar consulta |
| GET | /api/appointments/{id} | Buscar por ID |
| GET | /api/appointments/pet/{petId} | Consultas por pet |
| GET | /api/appointments/status/{status} | Consultas por status |
| PATCH | /api/appointments/{id}/status | Atualizar status |
| DELETE | /api/appointments/{id}/cancel | Cancelar consulta |

### Produtos
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/products | Criar produto |
| GET | /api/products | Listar produtos |
| GET | /api/products/{id} | Buscar por ID |
| GET | /api/products/search?name= | Buscar por nome |
| GET | /api/products/category/{category} | Por categoria |
| PUT | /api/products/{id} | Atualizar produto |
| DELETE | /api/products/{id} | Remover produto |

### Pedidos
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/orders | Criar pedido |
| GET | /api/orders/{id} | Buscar por ID |
| GET | /api/orders/user/{userId} | Pedidos por usuario |
| GET | /api/orders/status/{status} | Pedidos por status |

### Assinaturas e Planos
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/signatures | Criar assinatura |
| GET | /api/signatures/{id} | Buscar por ID |
| GET | /api/signatures/user/{userId} | Assinaturas por usuario |
| PATCH | /api/signatures/{id}/cancel | Cancelar assinatura |
| POST | /api/plans | Criar plano |
| GET | /api/plans/{id} | Buscar plano |
| PUT | /api/plans/{id} | Atualizar plano |

### Fichas Medicas
| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | /api/medical-files | Criar ficha medica |
| GET | /api/medical-files/{id} | Buscar por ID |
| GET | /api/medical-files/pet/{petId} | Ficha por pet |
| PUT | /api/medical-files/{id} | Atualizar ficha |
| DELETE | /api/medical-files/{id} | Remover ficha |

---

## Instalacao da Solucao (How To)

### Pre-requisitos
- Docker instalado
- Docker Compose instalado
- Git instalado

### Passo a passo

**1. Clonar o repositorio**
```bash
git clone https://github.com/Clyvo-Pet/Back_End_Java.git
cd Back_End_Java
```

**2. Gerar o JAR da aplicacao**
```bash
mvn clean package -DskipTests
```

**3. Subir os containers**
```bash
docker compose up -d
```

**4. Aguardar o Oracle inicializar** (pode levar 2 a 3 minutos na primeira vez)
```bash
docker compose logs -f oracle
```

**5. Acessar a aplicacao**
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

**6. Verificar containers rodando**
```bash
docker compose ps
```

**7. Parar os containers**
```bash
docker compose down
```

---

## Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ClyvoPet-0.0.1-SNAPSHOT.jar app.jar

RUN addgroup -S clyvo && adduser -S clyvo -G clyvo
USER clyvo

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Docker Compose

```yaml
version: '3.8'

services:

  oracle:
    image: gvenzl/oracle-xe:21-slim
    container_name: clyvo-oracle
    environment:
      ORACLE_PASSWORD: 150606
      ORACLE_DATABASE: XEPDB1
    ports:
      - "1521:1521"
    volumes:
      - oracle-data:/opt/oracle/oradata
    healthcheck:
      test: [ "CMD", "healthcheck.sh" ]
      interval: 30s
      timeout: 10s
      retries: 10
      start_period: 120s
    restart: unless-stopped

  app:
    build: ../../DevOps/devops
    container_name: clyvo-app
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:oracle:thin:@oracle:1521/XEPDB1
      SPRING_DATASOURCE_USERNAME: system
      SPRING_DATASOURCE_PASSWORD: 150606
      SPRING_JPA_HIBERNATE_DDL_AUTO: none
      SPRING_JPA_DATABASE_PLATFORM: org.hibernate.dialect.OracleDialect
    depends_on:
      oracle:
        condition: service_healthy
    restart: unless-stopped

volumes:
  oracle-data:
    name: clyvo-oracle-data
```

---

## Script Azure CLI

O script completo esta no arquivo `azure-cli-script.sh` na raiz do repositorio.

**Para executar:**
```bash
az login
chmod +x azure-cli-script.sh
./azure-cli-script.sh
```

**Para deletar a VM ao final:**
```bash
az group delete --name clyvo-pet-rg --yes --no-wait
```

---

## Integrantes

| Nome | RM |
|------|----|
| Delesporte, Enrico | 565760 |
| Dias, Vitor | 565422 |
| Modesto, Felippe | 561810 |

---

## Links

- Repositorio GitHub: https://github.com/Clyvo-Pet/Back_End_Java
- Swagger (local): http://localhost:8080/swagger-ui.html
- Swagger (nuvem): http://<IP_DA_VM>:8080/swagger-ui.html
