# Patitas Felices

Sistema web para la gestión y adopción de mascotas, desarrollado en Java EE con JSP, Servlets y base de datos SQL Server. El proyecto permite administrar mascotas, solicitudes de adopción, usuarios y autenticación de administradores, además de gestionar imágenes mediante Cloudinary.

## Descripción del proyecto

Patitas Felices es una aplicación web orientada a organizaciones o administradores que desean gestionar animales en adopción desde un panel privado. La plataforma contempla dos grandes áreas:

- Área pública: usuarios pueden consultar mascotas disponibles y enviar solicitudes de adopción.
- Área administrativa: los administradores pueden gestionar mascotas, usuarios, solicitudes y autenticación.

## Tecnologías utilizadas

- Java 11+
- Java EE / Servlets
- JSP / JSTL
- HTML, CSS y JavaScript
- SQL Server
- Apache NetBeans
- Apache Tomcat / GlassFish (entorno web Java EE)
- Cloudinary para almacenamiento de imágenes
- JDBC y bibliotecas externas ubicadas en `librearias/`

## Características principales

- Registro y autenticación de administradores.
- Dashboard con resumen administrativo.
- Gestión de mascotas en adopción.
- Carga y visualización de imágenes de mascotas.
- Solicitudes de adopción por parte de usuarios.
- Módulo de administración de clientes y usuarios.
- Interfaz pública para consulta y adopción.
- Arquitectura basada en DAO, servicios y modelos.
- Configuración externa de credenciales mediante `app.properties`.

## Estructura del proyecto

```text
Patitas-felices/
├── build.xml
├── .gitignore
├── librearias/
│   ├── SQL-Patitas-Felices/
│   ├── *.jar
├── nbproject/
├── src/
│   ├── conf/
│   └── java/
│       └── com/
│           └── patitasfelices/
│               ├── config/
│               ├── controlador/
│               ├── dao/
│               ├── excepciones/
│               ├── filtro/
│               ├── listener/
│               ├── modelo/
│               ├── servicio/
│               ├── utils/
│               └── app.properties.example
├── web/
│   ├── META-INF/
│   ├── WEB-INF/
│   │   ├── fragmentos/
│   │   ├── vistas/
│   │   └── web.xml
│   ├── assets/
│   ├── recursos/
│   ├── index.jsp
│   └── ...
└── README.md
```

## Requisitos previos

Antes de ejecutar el proyecto necesitas tener instalado lo siguiente:

- Java JDK 11 o superior
- Apache NetBeans o cualquier IDE compatible con Java EE
- Servidor web Java EE (Tomcat, GlassFish o similar)
- SQL Server
- Acceso a Cloudinary para almacenamiento de imágenes

## Base de datos

El repositorio incluye scripts SQL para la base de datos en:

- `librearias/SQL-Patitas-Felices/ddl_Patitas_felices.sql`
- `librearias/SQL-Patitas-Felices/cambio_clave_administrador.sql`
- `librearias/SQL-Patitas-Felices/patitas_felices_borrar_registros.sql`

Debes crear la base de datos y ejecutar los scripts correspondientes antes de levantar la aplicación.

## Configuración de variables de entorno

Existe un ejemplo de configuración en:

- `src/java/app.properties.example`

Copia este archivo y configura tus propios valores para la conexión a la BD y Cloudinary:

```properties
# Base de Datos SQL Server
db.url=jdbc:sqlserver://localhost:1433;databaseName=db_patitas_felices;encrypt=true;trustServerCertificate=true
db.user=TU_USUARIO
db.pass=TU_CLAVE

# Cloudinary
cloudinary.name=TU_CLOUD_NAME
cloudinary.key=TU_API_KEY
cloudinary.secret=TU_API_SECRET
```

Asegúrate de no subir credenciales reales al repositorio.

## Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Mrj4ck2005/Patitas-felices.git
cd Patitas-felices
```

### 2. Configurar la base de datos

- Crear la base de datos SQL Server.
- Ejecutar los scripts del directorio `librearias/SQL-Patitas-Felices/`.

### 3. Configurar `app.properties`

- Renombra `src/java/app.properties.example` a `src/java/app.properties`.
- Ajusta los valores reales de conexión y Cloudinary.

### 4. Importar el proyecto

- Abrir el proyecto en NetBeans.
- Compilar con la herramienta del IDE.
- Publicar o ejecutar en un servidor Java EE compatible.

### 5. Iniciar la aplicación

La aplicación se despliega normalmente desde la ruta de la aplicación web, y el archivo `web.xml` ya define la vista inicial:

```xml
<welcome-file>index.jsp</welcome-file>
```

## Módulos principales

### Administración

- Dashboard administrativo
- Login de administrador
- Gestión de mascotas
- Gestión de solicitudes
- Visualización de datos del sistema

### Público

- Vista principal del sitio
- Información de mascotas disponibles
- Formulario para adoptar
- Formulario de entrega de mascota en adopción

## Dependencias

El proyecto usa varias librerías JAR almacenadas en `librearias/`, entre ellas:

- `mssql-jdbc-13.4.0.jre11.jar`
- `mysql-connector-j-8.3.0.jar`
- `jbcrypt-0.4.jar`
- `cloudinary-core-1.36.0.jar`
- `cloudinary-http44-1.38.0.jar`
- `jstl-1.2.jar`
- `json-20231013.jar`

## Convenios y arquitectura

El proyecto sigue una estructura organizada por capas:

- `config`: configuración general de la aplicación y conexión a BD.
- `controlador`: servlets que gestionan las peticiones HTTP.
- `dao`: acceso a datos e interfaces de repositorio.
- `modelo`: entidades del dominio.
- `servicio`: lógica de negocio.
- `filtro`: seguridad y manejo de codificación.
- `listener`: eventos de inicialización de la aplicación.
- `utils`: validaciones y utilidades reutilizables.

## Estado del proyecto

Este proyecto está orientado a un sistema funcional de adopción de mascotas con administración web, y se encuentra en desarrollo/uso académico o interno según la estructura y la lógica implementada.

## Autor

- Mrj4ck2005

## Licencia

Este repositorio no especifica una licencia explícita en el archivo principal del proyecto. Si deseas publicar o reutilizar el código, es recomendable revisar la política del autor y agregar una licencia adecuada antes de uso comercial o distribuido.

## Contacto

Si deseas colaborar o ampliar la funcionalidad del proyecto, puedes hacerlo a través del repositorio de GitHub o contactando con el autor del proyecto.

---

¡Gracias por visitar Patitas Felices!
