DROP DATABASE IF EXISTS empresa_empleados;

CREATE DATABASE empresa_empleados;
USE empresa_empleados;

CREATE TABLE empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(120),
    fecha_ingreso DATE,
    area VARCHAR(50),
    eliminado BOOLEAN DEFAULT FALSE
);

CREATE TABLE legajos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nro_legajo VARCHAR(20) NOT NULL UNIQUE,
    categoria VARCHAR(30),
    estado ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    fecha_alta DATE NOT NULL,
    observaciones VARCHAR(255),
    empleado_id BIGINT NOT NULL UNIQUE,
    eliminado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id) ON DELETE CASCADE
);

CREATE INDEX idx_empleado_dni ON empleados(dni);
CREATE INDEX idx_legajo_nro ON legajos(nro_legajo);

