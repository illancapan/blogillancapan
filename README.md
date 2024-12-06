# Blog Personal (Portafolio)

Este es un proyecto de blog RESTful desarrollado como parte de mi portafolio personal. El backend está creado con **Spring Boot** y **Gradle** como sistema de construcción. El servicio REST permite la creación y gestión de artículos y comentarios, con un enfoque en **Test Driven Development (TDD)** utilizando **JUnit** y **Mockito**. La arquitectura sigue los principios de **SOLID** y utiliza un **modelo anémico**.

## Funcionalidades Principales

- **Gestión de Artículos**: Crear, editar y eliminar artículos (próximamente).
- **Comentarios**: Los usuarios podrán comentar en los artículos (próximamente).
- **Autenticación**: Los usuarios podrán registrarse e iniciar sesión usando su cuenta de **GitHub** (próximamente).
- **Administrador**: El administrador puede crear y gestionar artículos.
- **Frontend**: Interacción con el backend utilizando **React** para los usuarios finales (próximamente).

## Tecnologías Utilizadas

- **Java** 17+
- **Spring Boot** 3.3.5
- **Gradle** para gestión de dependencias y construcción del proyecto
- **JUnit** y **Mockito** para pruebas unitarias (TDD)
- **PostgreSQL** o **H2** (dependiendo del entorno) para la base de datos
- **React** para el frontend (próximamente)
- **GitHub OAuth** para autenticación de usuarios (próximamente)

## Endpoints Disponibles

Actualmente, se ha implementado un controlador básico para la autenticación de usuarios:

### `/api/users`

- **POST /api/users/register**: Registra un nuevo usuario.

Este endpoint permite a los usuarios registrarse en el sistema, lo cual es el primer paso hacia la funcionalidad completa de autenticación.

**Ejemplo de request:**

```json
{
  "username": "usuario123",
  "email": "usuario@example.com",
  "password": "contraseñaSegura"
}
```