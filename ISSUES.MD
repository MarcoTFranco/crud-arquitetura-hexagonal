# Issues — CRUD Arquitetura Hexagonal

Problemas identificados e melhorias planejadas para o projeto.
Cada issue está classificada por área e tem contexto de impacto arquitetural.

---

## Em aberto

### [ISSUE-01] Centralização do acesso a dados via Repository
**Área:** Arquitetura
**Passo do roadmap:** 3
Os services acessam o banco de formas inconsistentes. O acesso precisa passar exclusivamente pelos repositories, preparando o terreno para a introdução das portas.

---

### [ISSUE-02] Constructor injection em services e controllers
**Área:** Boas práticas Spring
**Passo do roadmap:** 4
Dependências injetadas via `@Autowired` em campo tornam os testes mais difíceis e escondem as dependências reais da classe. A injeção via construtor deixa o contrato explícito e permite testes sem reflection.

---

### [ISSUE-03] Response DTOs desacoplados das entidades JPA
**Área:** Arquitetura
**Passo do roadmap:** 5
Entidades JPA estão sendo retornadas diretamente pela API em alguns pontos. Isso acopla o contrato HTTP ao modelo de persistência — qualquer mudança no banco pode quebrar os clientes.

---

### [ISSUE-04] Paginação nos endpoints de listagem
**Área:** API
**Passo do roadmap:** 6
Os endpoints de listagem retornam todos os registros sem paginação. Em produção isso causa problemas de performance e memória.

---

### [ISSUE-05] Testes unitários com JUnit 5 + Mockito
**Área:** Qualidade
**Passo do roadmap:** 7
Sem testes unitários nos services, qualquer refatoração é feita sem rede de segurança.

---

### [ISSUE-06] Revisão de dependências do pom.xml
**Área:** Manutenção
**Passo do roadmap:** 8
O `pom.xml` pode conter dependências desnecessárias ou versões desatualizadas herdadas do projeto original.

---

### [ISSUE-07] Interfaces de porta (UseCase + PersistencePort)
**Área:** Arquitetura Hexagonal
**Passo do roadmap:** 9
Sem interfaces de porta, o projeto tem camadas mas não é verdadeiramente hexagonal. O service conhece o JpaRepository diretamente — isso inverte a dependência errada e impede a troca de infraestrutura sem tocar no domínio.

---

### [ISSUE-08] Exceções de domínio customizadas
**Área:** Domínio
**Passo do roadmap:** 10
Os services lançam exceções genéricas. Exceções de domínio (`ResourceNotFoundException`, `BusinessException`) tornam o comportamento explícito e permitem que o `GlobalExceptionHandler` mapeie erros com precisão.

---

### [ISSUE-09] CRUD de Curso incompleto
**Área:** Funcionalidade
**Passo do roadmap:** 11
`Curso` só tem `POST` e `GET` (listagem). Faltam `findById`, `update` e `delete`. Além disso, `CursoController` retorna a entidade JPA diretamente em vez de `CursoResponse`.

---

### [ISSUE-10] Entidade Professor sem endpoints
**Área:** Funcionalidade
**Passo do roadmap:** 12
`Professor` existe no domínio mas não tem controller, service nem endpoints. A entidade está "morta" no sistema.

---

### [ISSUE-11] Testes de integração com Testcontainers
**Área:** Qualidade
**Passo do roadmap:** 13
Mockito não garante que as queries JPA funcionam com o banco real. Testcontainers sobe um MySQL em container durante os testes de integração, cobrindo o que os mocks não conseguem validar.

---

### [ISSUE-12] Migração de banco com Flyway
**Área:** Infraestrutura
**Passo do roadmap:** 14
O projeto usa `ddl-auto=create`, que recria o schema a cada inicialização. Isso é aceitável em dev mas inviável em produção — Flyway introduz migrações versionadas e rastreáveis.

---

### [ISSUE-13] Documentação automática com SpringDoc OpenAPI
**Área:** API
**Passo do roadmap:** 15
Sem Swagger UI, a API só pode ser explorada lendo o código ou pela tabela do README. SpringDoc gera a documentação interativa automaticamente a partir dos controllers.

---

### [ISSUE-14] Mapper explícito entre camadas
**Área:** Arquitetura
**Passo do roadmap:** 16
`AlunoRequest.toModel()` chama `AlunoService` — um DTO não deveria conhecer um service. Um mapper dedicado (`AlunoMapper`, `CursoMapper`) elimina esse acoplamento invertido.

---

### [ISSUE-15] Autenticação e autorização com Spring Security + JWT
**Área:** Segurança
**Passo do roadmap:** 17
Todos os endpoints estão públicos. Sem autenticação, qualquer cliente pode criar, alterar ou remover registros.

---

### [ISSUE-16] Auditoria de entidades com Spring Data Auditing
**Área:** Qualidade
**Passo do roadmap:** 18
Não há registro de quando um recurso foi criado ou alterado. `@CreatedDate` e `@LastModifiedDate` adicionam isso de forma automática.

---

### [ISSUE-17] Profiles de ambiente (dev/prod)
**Área:** Infraestrutura
**Passo do roadmap:** 19
Configurações de dev e produção estão misturadas no mesmo `application.properties`. Profiles separados evitam que configurações de desenvolvimento vazem para produção.

---

### [ISSUE-18] Validação de duplicidade de curso
**Área:** Regra de negócio / Domínio
**Passo do roadmap:** —
Não há nenhuma validação que impeça o cadastro de cursos com o mesmo nome. O sistema aceita múltiplas inserções idênticas sem erro, corrompendo silenciosamente os dados.

**Impacto arquitetural:** essa regra pertence ao domínio ou ao service — não ao controller. A verificação deve acontecer antes de persistir, consultando se já existe um curso com aquele nome.

---