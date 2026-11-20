# ProjetoMarketplace — Sistema de Gestão Integrada

Aplicação web full-stack desenvolvida em **Java 21** e **Spring Boot 3.2.4** para gestão operacional de marketplaces, clientes, vendedores, catálogo de produtos, pedidos, pagamentos, logística e atendimento ao cliente.

## Arquitetura e Tecnologias

* **Backend:** Java 21, Spring Boot 3.2.4 (Web, Data JPA, Security)
* **Frontend:** Thymeleaf, Bootstrap 5 (Tema Dark responsivo)
* **Banco de Dados:** H2 Database (persistência em arquivo local com console web habilitado)
* **Processamento de Dados:** Apache POI (importação automatizada e idempotente de planilhas `.xlsx` em lote)
* **Build:** Maven

### Estrutura de Pacotes (`projeto.marketplace`)
* `carga`: Rotinas automáticas de inicialização (`ImportadorExcel` e `CargaUsuarios`).
* `infraestrutura.config`: Mapeamento direto de rotas web (`ConfiguracaoRotasWeb`).
* `infraestrutura.controller.rest`: Endpoints JSON (`MarketplaceRestController`).
* `infraestrutura.controller.web`: Controladores MVC para renderização das telas CRUD.
* `infraestrutura.model`: Entidades JPA com mapeamento relacional bidirecional.
* `infraestrutura.repository`: Interfaces Spring Data JPA.
* `infraestrutura.security`: Autenticação BCrypt e controle de acesso baseado em perfis (`ConfiguracaoSeguranca` e `AutenticacaoUsuarioService`).
* `infraestrutura.service`: Regras de negócio, transações e métricas do dashboard.

---

## Pré-requisitos

* **JDK 21** instalado e configurado na variável de ambiente `JAVA_HOME`.
* **Maven 3.8+** (ou utilização do Maven Wrapper `./mvnw` incluído no projeto).

---

## Instruções de Execução

### Opção 1: Via Maven (Linha de Comando)
Na pasta raiz do projeto, execute:
```bash
mvn spring-boot:run