# Spring Neo4j Demo

REST API project developed in Java using Spring Boot 3 and Neo4j graph database.

## Requirements

- Java 17
- Spring Boot 3.x.x
- Apache Maven 3.8.6

## Libraries

- [spring-common-parent](https://github.com/erebelo/spring-common-parent): Manages the Spring Boot version and provide common configurations for plugins and formatting.

## Run App

- Set the following environment variables: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, and `DB_PASSWORD`.
  - For spring profile other than local and test, also set `SSL_CERT_PATH`.
- Run the `SpringNeo4jDemoApplication` class as Java Application.

## Collection

[Project Collection](https://github.com/erebelo/spring-neo4j-demo/tree/main/collection)

## DB Setup

[Neo4j Server Setup](https://github.com/erebelo/spring-neo4j-demo/tree/main/db-setup)

## Diagram

[Entity Relationship Diagram](https://github.com/erebelo/spring-neo4j-demo/tree/main/db-setup/Entity%20Relationship%20Diagram.png)

## Script

[Neo4j Demo Script](https://github.com/erebelo/spring-neo4j-demo/tree/main/db-setup/neo4j_demo_script.sql)
