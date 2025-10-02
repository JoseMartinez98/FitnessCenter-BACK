# 🏋️ FitnessCenter-BACK

[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com/es/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Build-Maven-orange.svg)](https://maven.apache.org/)

El backend de **FitnessCenter** es una robusta API RESTful desarrollada con **Spring Boot** para gestionar todas las operaciones de un centro de fitness, incluyendo la administración de usuarios, clases, horarios y reservas.

## 🚀 Tecnologías Principales

Este proyecto está construido con un enfoque en la arquitectura moderna y las mejores prácticas de desarrollo.

* **Framework:** [Spring Boot 3.x]
* **Lenguaje:** [Java 17+]
* **Base de Datos:** [ MySQL / H2]
* **Persistencia:** Spring Data JPA / Hibernate
* **Seguridad:** [Spring Security (JWT/OAuth2)] - *Ajustar según se use*
* **Build Tool:** Maven

## ✨ Características (Endpoints Principales)

La API proporciona los siguientes módulos funcionales:

| Módulo | Descripción | Endpoints Típicos |
| :--- | :--- | :--- |
| **Autenticación** | Registro y login de usuarios. | `/api/v1/auth/register`, `/api/v1/auth/login` |
| **Usuarios** | Gestión de perfiles, roles y miembros. | `/api/v1/users`, `/api/v1/users/{id}` |
| **Clases** | Creación, edición y listado de clases (Ej. Yoga, Spinning). | `/api/v1/classes`, `/api/v1/classes/{id}` |

## ⚙️ Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

1.  **Java Development Kit (JDK):** [Versión 17] o superior.
2.  **Maven:** Para la gestión de dependencias y compilación.
3.  **Base de Datos:** Un servicio de base de datos.

## 🛠️ Instalación y Configuración

Sigue estos pasos para tener una copia del proyecto funcionando localmente:

### 1. Clonar el Repositorio

git clone [https://github.com/JoseMartinez98/FitnessCenter-BACK.git](https://github.com/JoseMartinez98/FitnessCenter-BACK.git)
cd FitnessCenter-BACK

### 2. Configurar la Base de Datos
Crea un archivo llamado application.properties o application.yml en la carpeta src/main/resources/ (si no existe) y configura la conexión a tu base de datos.

Ejemplo (application.properties con MySQL):

Properties

spring.datasource.url=jdbc:mysql://localhost:5432/[nombre_de_tu_db]
spring.datasource.username=[tu_usuario_db]
spring.datasource.password=[tu_contraseña_db]
spring.jpa.hibernate.ddl-auto=update
¡IMPORTANTE! : Usando
Spring Security o JWT, recuerda configurar las claves secretas en este archivo.

