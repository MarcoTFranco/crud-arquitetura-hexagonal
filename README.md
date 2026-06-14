# CRUD — Arquitetura Hexagonal com Spring Boot

> **Projeto de aprendizado.** Peguei um CRUD simples que havia desenvolvido anteriormente
> e estou refatorando de forma incremental para aplicar na prática conceitos como
> arquitetura hexagonal, boas práticas Spring Boot, testes e segurança.
> O objetivo não é um produto final, mas documentar a evolução e consolidar o conhecimento.

API REST de gerenciamento de alunos e cursos, construída com Java 17 e Spring Boot 3,
aplicando os princípios da **arquitetura hexagonal (Ports & Adapters)** para separar
o núcleo de negócio dos detalhes de infraestrutura.

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.0 |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | MySQL 8 |
| Validação | Bean Validation (Jakarta) |
| Testes | JUnit 5 + Mockito |
| Container | Docker + Docker Compose |

---

## Como rodar

### Com Docker Compose (recomendado)

```bash
docker-compose up --build
```

> **Atenção:** Se você tiver MySQL instalado localmente, a porta `3306` já estará em uso
> e o container do banco não vai subir. Nesse caso, edite o `docker-compose.yml` e troque
> a porta exposta:
> ```yaml
> ports:
>   - "3307:3306"   # era 3306:3306
> ```
> A aplicação continua funcionando normalmente — só a porta exposta ao host muda.


A API estará disponível em `http://localhost:8080`.

### Localmente

Configure o banco no `application.properties` e execute:

```bash
./mvnw spring-boot:run
```

---

## Arquitetura

O projeto segue a arquitetura hexagonal, isolando o domínio de negócio das
implementações externas (HTTP, banco de dados):

```
src/main/java/com/crud/treinando/
├── domain/          # Entidades de negócio — sem dependências externas
├── application/     # Serviços e casos de uso da aplicação
└── adapter/
    ├── input/       # Controllers e DTOs de entrada (driving adapters)
    └── output/      # Repositories e DTOs de saída  (driven adapters)
```

**Decisão de design:** o `domain` não conhece Spring, JPA nem HTTP.
Isso permite testar a lógica de negócio de forma isolada e trocar
a camada de persistência ou de transporte sem afetar o núcleo.

---

## Endpoints

### Alunos — `/api/v1/alunos`

| Método | Rota     | Descrição               | Status |
|--------|----------|-------------------------|--------|
| POST   | `/`      | Cadastra aluno          | 201    |
| GET    | `/`      | Lista alunos (paginado) | 200    |
| GET    | `/{id}`  | Busca aluno por ID      | 200    |
| PUT    | `/{id}`  | Atualiza aluno          | 200    |
| DELETE | `/{id}`  | Remove aluno            | 204    |

### Cursos — `/api/v1/cursos`

| Método | Rota | Descrição      | Status |
|--------|------|----------------|--------|
| POST   | `/`  | Cadastra curso | 201    |
| GET    | `/`  | Lista cursos   | 200    |

### Exemplo de resposta de erro padronizada

Todos os erros retornam o mesmo contrato JSON:

```json
{
  "timestamp": "2025-06-09T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Aluno com id 99 não encontrado",
  "path": "/api/v1/alunos/99"
}
```

---

## Decisões planejadas

Princípios de design que guiam a evolução do projeto — alguns já aplicados parcialmente, outros previstos no roadmap.

### Tratamento global de exceções com `@ControllerAdvice`

Em vez de tratar erros em cada controller individualmente, um `GlobalExceptionHandler`
centraliza o mapeamento de exceções para status HTTP e garante que o cliente
sempre receba um body JSON consistente — nunca um stack trace exposto.

### Constructor injection

As dependências serão injetadas via construtor, não via `@Autowired` em campo.
Isso torna as classes testáveis sem reflection, deixa as dependências explícitas
na assinatura e respeita a imutabilidade dos campos.

### Interfaces de porta (Ports & Adapters)

Cada camada se comunica através de interfaces, nunca de implementações concretas.
O `Service` dependerá de `PersistencePort`, não de `JpaRepository` diretamente —
permitindo trocar a persistência sem tocar na lógica de negócio.

### DTOs em todas as camadas

Nenhuma entidade JPA é exposta diretamente na API. `Request` e `Response` DTOs
desacoplam o contrato da API do modelo de persistência, permitindo que o schema
do banco evolua sem quebrar os clientes.

### Paginação com `Pageable`

Os endpoints de listagem aceitarão parâmetros de paginação e ordenação:

```
GET /api/v1/alunos?page=0&size=10&sort=nome,asc
```

### Testes unitários e de integração

Os serviços serão testados de forma isolada com mocks (JUnit 5 + Mockito),
e os adapters de persistência com banco real via Testcontainers — cobrindo
o que mocks não conseguem garantir.

---

## Roadmap de Evolução

Evolução incremental do projeto, com cada passo desenvolvido em branch separada.

### Fundamentos Spring Boot

- [x] **Passo 1** — Status codes e header `Location` no POST (`201 Created`)
- [x] **Passo 2** — Tratamento global de exceções com `@ControllerAdvice`
- [x] **Passo 3** — Centralização do acesso a dados via Repository
- [x] **Passo 4** — Constructor injection em services e controllers
- [x] **Passo 5** — Response DTOs desacoplados das entidades JPA
- [ ] **Passo 6** — Paginação nos endpoints de listagem
- [ ] **Passo 7** — Testes unitários com JUnit 5 + Mockito
- [ ] **Passo 8** — Revisão e limpeza de dependências do `pom.xml`

### Arquitetura Hexagonal de Verdade

- [x] **Passo 9** — Interfaces de porta (`UseCase` + `PersistencePort`) para inversão real de dependência — sem elas o projeto é apenas camadas, não hexagonal
- [ ] **Passo 10** — Exceções de domínio customizadas (`ResourceNotFoundException`, `BusinessException`) para substituir `Assert` e erros genéricos nos Services
- [ ] **Passo 11** — Completar CRUD de `Curso` (faltam `findById`, `update`, `delete`) e corrigir `CursoController` para retornar `CursoResponse` em vez da entidade JPA diretamente
- [ ] **Passo 12** — Implementar `Professor` do zero: Controller, Service, portas e CRUD completo (entidade existe no domínio mas não tem nenhum endpoint)

### Qualidade e Robustez

- [ ] **Passo 13** — Testes de integração com `@SpringBootTest` + Testcontainers (MySQL real em container) para cobrir o que Mockito não garante
- [ ] **Passo 14** — Migração de banco com Flyway: substituir `ddl-auto=create` por scripts SQL versionados (`V1__create_tables.sql`)
- [ ] **Passo 15** — Documentação automática da API com SpringDoc OpenAPI (Swagger UI em `/swagger-ui.html`)
- [ ] **Passo 16** — Mapper explícito para conversão entre camadas (`AlunoMapper`, `CursoMapper`) eliminando o acoplamento onde `AlunoRequest.toModel()` chama `AlunoService`

### Segurança e Produção

- [ ] **Passo 17** — Autenticação e autorização com Spring Security + JWT
- [ ] **Passo 18** — Auditoria automática de entidades com `@CreatedDate` e `@LastModifiedDate` (Spring Data Auditing)
- [ ] **Passo 19** — Profiles de ambiente (`dev`, `prod`) com configurações separadas via `application-{profile}.yml`