# CustomerConnect

Bem-vindo ao **CustomerConnect**, um projeto envolvente e prático projetado para ajudá-lo a dominar os fundamentos do Spring Boot e do Spring Data JPA. Neste projeto, você criará um robusto Sistema de Gerenciamento de Clientes que executa operações CRUD em uma entidade Cliente.

## Regras de Negócio

### Dados Cadastrais
Precisamos das seguintes informações principais de nossos clientes:
- **Nome Completo**
- **CPF**
- **Email**
- **Telefone Celular**
- **Data de registro e atualização** do cliente no sistema para fins de auditoria.

### Cadastro Único
- Garantir que não existam clientes com ID, CPF ou email repetidos.

### Busca Flexível e Personalizada
- Permitir **paginação** e **ordenação** de resultados.
- Permitir **busca por CPF** e/ou **email**.

## Endpoints REST

### `POST /customers`
Endpoint para o cadastro de clientes.

#### Parâmetros Requeridos
- `fullName` (string): Nome completo do cliente.
- `cpf` (string): CPF do cliente.
- `email` (string): Email do cliente.
- `phoneNumber` (string): Telefone celular.

#### Retorno
- `customerId` (string): Identificador único do cliente no sistema.

---

### `GET /customers`
Endpoint para consulta de clientes cadastrados.

#### Parâmetros de Consulta
- `page` (integer): Número da página.
- `pageSize` (integer): Quantidade de itens retornados por página.
- `orderBy` (string): Ordenação dos clientes pela data de criação.
- `email` (string): Busca cliente pelo email.
- `cpf` (string): Busca cliente pelo CPF.

#### Retorno
- Dados dos clientes e informações de paginação.

---

### `PUT /customers/{customerId}`
Endpoint para atualizar as informações de um cliente.

#### Parâmetros Requeridos
- `fullName` (string): Nome completo do cliente.
- `email` (string): Email do cliente.
- `phoneNumber` (string): Telefone celular.

---

### `DELETE /customers/{customerId}`
Endpoint para excluir um cliente do cadastro.

---

## Tecnologias Utilizadas
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database** (para testes e desenvolvimento)
- **Maven** (gerenciador de dependências)

---

## Como Executar o Projeto
1. Clone este repositório:
```bash
git clone git@github.com:johnenderson/customerConnect.git
cd customerconnect
```
2. Execute o projeto:
```bash
mvn spring-boot:run
```