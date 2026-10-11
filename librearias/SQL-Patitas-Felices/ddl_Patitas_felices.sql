/* ============================================================
   Sistema de Gestión Web para Refugios de Animales
   Base de datos: db_patitas_felices
   Motor: SQL Server 2022
   Basado en el modelo entidad-relación (Figura 6) del documento
   ============================================================ */

-- 1. CREACIÓN DE LA BASE DE DATOS
IF DB_ID('db_patitas_felices') IS NOT NULL
BEGIN
    ALTER DATABASE db_patitas_felices SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE db_patitas_felices;
END
GO

CREATE DATABASE db_patitas_felices;
GO

USE db_patitas_felices;
GO

-- 2. TABLA: tb_clientes
CREATE TABLE tb_clientes (
    id_cliente        INT IDENTITY(1,1) PRIMARY KEY,
    tipo_documento    VARCHAR(20)  NOT NULL,
    numero_documento  VARCHAR(15)  NOT NULL,
    nombre_completo   VARCHAR(150) NOT NULL,
    telefono          VARCHAR(20)  NULL,
    correo            VARCHAR(100) NULL,
    tipo_cliente      VARCHAR(20)  NOT NULL,   -- ej: Rescatista, Propietario, Adoptante
    CONSTRAINT UQ_clientes_documento UNIQUE (tipo_documento, numero_documento)
);
GO

-- 3. TABLA: tb_administradores
CREATE TABLE tb_administradores (
    id_admin         INT IDENTITY(1,1) PRIMARY KEY,
    nombre_completo  VARCHAR(80)  NOT NULL,
    usuario          VARCHAR(50)  NOT NULL UNIQUE,
    correo           VARCHAR(100) NOT NULL UNIQUE,
    clave            VARCHAR(255) NOT NULL,     -- contraseña encriptada (hash)
    rol              VARCHAR(20)  NOT NULL,     -- ej: SuperAdmin, Administrador
    estado           VARCHAR(20)  NOT NULL DEFAULT 'Activo'  -- Activo / Inactivo
);
GO

-- 4. TABLA: tb_mascotas
CREATE TABLE tb_mascotas (
    id_mascota           INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente           INT NOT NULL,
    nombre               VARCHAR(50)  NOT NULL,
    especie               VARCHAR(20)  NOT NULL,   -- ej: Perro, Gato
    raza                 VARCHAR(50)  NULL,
    edad_meses           INT NOT NULL CHECK (edad_meses >= 0),
    descripcion_salud    NVARCHAR(MAX) NULL,
    foto_url             VARCHAR(255) NULL,
    sexo                 VARCHAR(20)  NULL,       -- Macho / Hembra
    estado               VARCHAR(20)  NOT NULL DEFAULT 'Pendiente', -- Pendiente, Disponible, Reservado, Adoptado
    fecha_ingreso         DATETIME NOT NULL DEFAULT GETDATE(),
    fecha_actualizacion   DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_mascotas_clientes FOREIGN KEY (id_cliente)
        REFERENCES tb_clientes (id_cliente)
);
GO

-- 5. TABLA: tb_solicitudes
CREATE TABLE tb_solicitudes (
    id_solicitud    INT IDENTITY(1,1) PRIMARY KEY,
    id_mascota      INT NOT NULL,
    id_cliente      INT NOT NULL,
    id_admin        INT NULL,      -- puede quedar sin asignar hasta que un admin lo atienda
    tipo_solicitud  VARCHAR(30)  NOT NULL,   -- Registro de mascota / Adopción
    fecha_registro  DATETIME NOT NULL DEFAULT GETDATE(),
    estado_tramite  VARCHAR(20)  NOT NULL DEFAULT 'Pendiente', -- Pendiente, Aprobado, Rechazado
    observacion     VARCHAR(100) NULL,
    CONSTRAINT FK_solicitudes_mascotas FOREIGN KEY (id_mascota)
        REFERENCES tb_mascotas (id_mascota),
    CONSTRAINT FK_solicitudes_clientes FOREIGN KEY (id_cliente)
        REFERENCES tb_clientes (id_cliente),
    CONSTRAINT FK_solicitudes_administradores FOREIGN KEY (id_admin)
        REFERENCES tb_administradores (id_admin)
);
GO

-- 6. TABLA: tb_adopciones
CREATE TABLE tb_adopciones (
    id_adopcion      INT IDENTITY(1,1) PRIMARY KEY,
    id_solicitud     INT NOT NULL UNIQUE,   -- 1 solicitud -> máximo 1 adopción
    fecha_adopcion   DATETIME NOT NULL DEFAULT GETDATE(),
    estado_adopcion  VARCHAR(20) NOT NULL DEFAULT 'En proceso', -- En proceso, Completada
    CONSTRAINT FK_adopciones_solicitudes FOREIGN KEY (id_solicitud)
        REFERENCES tb_solicitudes (id_solicitud)
);
GO

-- 7. ÍNDICES DE APOYO PARA CONSULTAS FRECUENTES
CREATE INDEX IX_mascotas_estado ON tb_mascotas (estado);
CREATE INDEX IX_solicitudes_estado ON tb_solicitudes (estado_tramite);
CREATE INDEX IX_solicitudes_tipo ON tb_solicitudes (tipo_solicitud);
GO

-- 8. (OPCIONAL) DATOS DE PRUEBA MÍNIMOS
INSERT INTO tb_administradores (nombre_completo, usuario, correo, clave, rol, estado)
VALUES ('Administrador General', 'admin', 'admin@patitasfelices.com', 'CAMBIAR_HASH_AQUI', 'SuperAdmin', 'Activo');

INSERT INTO tb_clientes (tipo_documento, numero_documento, nombre_completo, telefono, correo, tipo_cliente)
VALUES ('DNI', '12345678', 'Juan Pérez', '987654321', 'juan.perez@correo.com', 'Rescatista');
GO

PRINT 'Base de datos db_patitas_felices creada correctamente.';


select * from tb_solicitudes;
select * from tb_clientes;
select * from tb_mascotas;
select * from tb_adopciones;
select * from tb_administradores;