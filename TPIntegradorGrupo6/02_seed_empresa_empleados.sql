USE empresa_empleados;

INSERT INTO empleados (nombre, apellido, dni, email, fecha_ingreso, area) VALUES
('Juan', 'Pérez', '30123456', 'juan.perez@empresa.com', '2023-01-15', 'Sistemas'),
('María', 'Gómez', '32123456', 'maria.gomez@empresa.com', '2022-03-20', 'RRHH');

INSERT INTO legajos (nro_legajo, categoria, estado, fecha_alta, observaciones, empleado_id) VALUES
('EMP-001', 'Senior', 'ACTIVO', '2023-01-15', 'Empleado modelo', 1),
('EMP-002', 'Junior', 'ACTIVO', '2022-03-20', 'Recién capacitada', 2);

