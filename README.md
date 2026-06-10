# CRUD — Arquitetura Hexagonal com Spring Boot

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

## Decisões técnicas

### Tratamento global de exceções com `@ControllerAdvice`

Em vez de tratar erros em cada controller individualmente, um `GlobalExceptionHandler`
centraliza o mapeamento de exceções para status HTTP e garante que o cliente
sempre receba um body JSON consistente — nunca um stack trace exposto.

### Constructor injection

As dependências são injetadas via construtor, não via `@Autowired` em campo.
Isso torna as classes testáveis sem reflection, deixa as dependências explícitas
na assinatura e respeita a imutabilidade dos campos.

### DTOs em todas as camadas

Nenhuma entidade JPA é exposta diretamente na API. `Request` e `Response` DTOs
desacoplam o contrato da API do modelo de persistência, permitindo que o schema
do banco evolua sem quebrar os clientes.

### Paginação com `Pageable`

Os endpoints de listagem aceitam parâmetros de paginação e ordenação:

```
GET /api/v1/alunos?page=0&size=10&sort=nome,asc
```

### Testes unitários com Mockito

Os serviços são testados de forma isolada com mocks do repositório,
cobrindo os caminhos de sucesso e os casos de erro (recurso não encontrado,
dados inválidos).

---

## Roadmap de Evolução

Evolução incremental do projeto, com cada passo desenvolvido em branch separada.

- [ ] **Passo 1** — Status codes e header `Location` no POST (`201 Created`)
- [ ] **Passo 2** — Tratamento global de exceções com `@ControllerAdvice`
- [ ] **Passo 3** — Centralização do acesso a dados via Repository
- [ ] **Passo 4** — Constructor injection em services e controllers
- [ ] **Passo 5** — Response DTOs desacoplados das entidades JPA
- [ ] **Passo 6** — Paginação nos endpoints de listagem
- [ ] **Passo 7** — Testes unitários com JUnit 5 + Mockito
- [ ] **Passo 8** — Revisão e limpeza de dependências do `pom.xml`
