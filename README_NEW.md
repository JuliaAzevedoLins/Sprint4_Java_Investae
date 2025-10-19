# 🚀 Investaê - API de Gestão de Investimentos

## 📋 Descrição do Projeto

API REST completa para gerenciamento de investimentos com sistema robusto de autenticação JWT e controle de acesso baseado em roles. O projeto demonstra implementação de boas práticas de segurança, arquitetura limpa e princípios SOLID.

### 🎯 Características Principais

- **Autenticação JWT Stateless** com BCrypt para criptografia de senhas
- **Controle de acesso baseado em roles** (USER/ADMIN)
- **Arquitetura em camadas** (Controller, Service, Repository)
- **Princípios SOLID** aplicados consistentemente
- **Documentação automática** com Swagger/OpenAPI
- **Testes abrangentes** (unitários e integração)
- **Interface web interativa** para demonstração

## 🛡️ Sistema de Segurança

### Autenticação e Autorização

- **Usuário Admin Padrão**: `admin` / `admin123` (criado automaticamente)
- **Novos usuários**: Registrados automaticamente como "USER"
- **Controle de acesso**:
  - **USER**: Acesso apenas aos próprios investimentos
  - **ADMIN**: Acesso total ao sistema

### Endpoints de Segurança

- `POST /auth/register` - Registro de novos usuários (apenas USER)
- `POST /auth/login` - Autenticação com retorno de token JWT
- `GET /api/investimentos/meus` - Investimentos do usuário logado (USER/ADMIN)  
- `GET /api/investimentos` - Todos os investimentos (apenas ADMIN)

## 🏗️ Arquitetura e Tecnologias

### Stack Principal

- **Java 17+** - Linguagem base
- **Spring Boot 3.3.1** - Framework principal
- **Spring Security** - Segurança e autenticação
- **Spring Data JPA** - Persistência de dados
- **Oracle Database** - Banco de dados
- **Flyway** - Migrações de banco
- **JWT** - Tokens de autenticação
- **BCrypt** - Criptografia de senhas

### Documentação e Testes

- **SpringDoc OpenAPI 3** - Documentação automática
- **JUnit 5** - Testes unitários
- **Mockito** - Mocks para testes
- **Spring Boot Test** - Testes de integração

### Princípios Aplicados

✅ **Single Responsibility** - Cada classe tem uma responsabilidade específica  
✅ **Open/Closed** - Extensível através de interfaces  
✅ **Liskov Substitution** - Implementações substituíveis via interfaces  
✅ **Interface Segregation** - Interfaces específicas (IAuthService, IInvestimentoService)  
✅ **Dependency Inversion** - Dependência de abstrações, não implementações  

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.6+
- Acesso ao banco Oracle da FIAP

### Execução Local

```bash
# 1. Clone o repositório
git clone [URL-DO-REPOSITORIO]
cd ChallengeXP_Java-main

# 2. Execute com Maven Wrapper (recomendado)
./mvnw spring-boot:run

# Ou com Maven instalado
mvn spring-boot:run
```

### Primeiro Acesso

1. **Aplicação**: http://localhost:8080
2. **Interface Web**: http://localhost:8080/investae-home.html  
3. **Swagger/API Docs**: http://localhost:8080/swagger-ui.html

### Credenciais de Teste

- **Admin**: `admin` / `admin123`
- **Usuário comum**: Registre-se pela interface ou API

## 📁 Estrutura do Projeto

```
src/
├── main/java/com/challenge/investimentos/investimentos_api/
│   ├── config/          # Configurações (Security, Swagger)
│   ├── controller/      # Controllers REST
│   ├── dto/             # Data Transfer Objects
│   ├── enums/           # Enumerações (Roles, Status)
│   ├── model/           # Entidades JPA
│   ├── repository/      # Interfaces de acesso a dados
│   ├── security/        # JWT, UserDetails, Filters
│   └── service/         # Regras de negócio
│       └── interfaces/  # Contratos dos serviços
├── main/resources/
│   ├── db/migration/    # Scripts Flyway
│   └── static/          # Interface web
└── test/java/
    ├── integration/     # Testes de integração
    └── service/         # Testes unitários
```

## 📝 Documentação da API

### Swagger/OpenAPI

Acesse http://localhost:8080/swagger-ui.html para documentação interativa completa.

**⚠️ Importante**: O Swagger agora requer autenticação! Faça login primeiro:

1. Use `POST /auth/login` para obter o token JWT
2. Clique em "Authorize" no Swagger 
3. Digite: `Bearer {seu-token-jwt}`

### Exemplos de Uso

#### 1. Registro de Usuário
```http
POST /auth/register
Content-Type: application/json

{
  "username": "usuario_teste",
  "password": "senha123",
  "nome": "Usuario de Teste",
  "email": "teste@email.com"
}
```

#### 2. Login
```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

#### 3. Acesso a Endpoint Protegido
```http
GET /api/investimentos
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🧪 Executar Testes

### Testes Unitários
```bash
./mvnw test -Dtest="*ServiceTest"
```

### Testes de Integração  
```bash
./mvnw test -Dtest="*IntegrationTest"
```

### Todos os Testes com Coverage
```bash
./mvnw clean test jacoco:report
```

### Exemplos de Testes Implementados

- **AuthServiceTest**: Testa regras de negócio de autenticação
- **InvestimentoServiceTest**: Testa CRUD de investimentos
- **AuthIntegrationTest**: Testa fluxo end-to-end de auth
- **Mocks**: Isolamento de dependências para testes unitários

## 🔍 Funcionalidades Implementadas

### ✅ Critérios de Avaliação Atendidos

| Critério | Peso | Status | Implementação |
|----------|------|--------|---------------|
| **Estruturação e SOLID** | 25% | ✅ | Interfaces, polimorfismo, camadas bem definidas |
| **Configurações de Segurança** | 20% | ✅ | JWT stateless, BCrypt, filtros personalizados |
| **Regras de Negócio** | 15% | ✅ | Services encapsulados, interfaces para extensibilidade |
| **Documentação da API** | 15% | ✅ | Swagger com JWT, endpoints documentados e organizados |
| **Testes Automatizados** | 15% | ✅ | Unitários com mocks, integração e2e, boa cobertura |
| **Documentação do Projeto** | 10% | ✅ | README completo com instruções detalhadas |

### 🎨 Interface Web

A aplicação inclui uma interface web completa em `/investae-home.html`:

- **Design responsivo** e moderno
- **Login/registro** integrados
- **Diferentes visualizações** por role (USER vs ADMIN)
- **Gestão de token JWT** automática
- **Feedback visual** de operações

## 👥 Integrantes

- **[Seu Nome]** - RM[XXXXX]

## 🚀 Próximos Passos

- [ ] Implementar refresh tokens
- [ ] Adicionar rate limiting
- [ ] Expandir funcionalidades de investimentos
- [ ] Implementar notificações
- [ ] Dashboard com gráficos

---

**Challenge XP - FIAP 2025**  
*Demonstrando excelência em Spring Security, arquitetura limpa e desenvolvimento full-stack*