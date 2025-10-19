Adições rápidas (JWT e testes)

- Endpoints de autenticação:
  - POST /auth/register  (body JSON: {"username":"u","password":"p"})
  - POST /auth/login     (body JSON: {"username":"u","password":"p"})
- Para acessar endpoints protegidos, inclua header:

  Authorization: Bearer <token>

- Propriedades JWT em `src/main/resources/application.properties`:
  - security.jwt.secret (apenas dev)
  - security.jwt.expiration (ms)

- Rodar testes:
  - ./mvnw test

Notas:
- A chave em application.properties é de desenvolvimento. Em produção, usar variáveis de ambiente/sistemas de segredos.
