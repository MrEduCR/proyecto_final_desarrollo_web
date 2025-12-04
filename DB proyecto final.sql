DROP DATABASE IF EXISTS terabyte_technology;
CREATE DATABASE terabyte_technology CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
USE terabyte_technology;

CREATE TABLE roles (
    rol_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    descripcion_rol VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE usuarios ( 
    cedula_usuario INT PRIMARY KEY NOT NULL,
    nombre_usuario VARCHAR(150) NOT NULL,
    correo_usuario VARCHAR(150) NOT NULL UNIQUE,
    contrasenia_usuario VARCHAR(100) NOT NULL,
    telefono_usuario VARCHAR(20) NOT NULL UNIQUE,
    rol_id INT NOT NULL,
    CONSTRAINT fk_usuarios_roles
        FOREIGN KEY (rol_id) REFERENCES roles(rol_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE clientes (
    cedula_cliente INT PRIMARY KEY NOT NULL,
    nombre_cliente VARCHAR(150) NOT NULL,
    telefono_cliente VARCHAR(20) NOT NULL UNIQUE,
    correo_cliente VARCHAR(150) UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tipos_equipo (
    tipo_equipo_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tipo_equipo_descripcion VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tipos_servicio (
    tipo_servicio_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tipo_servicio_descripcion VARCHAR(50),
    precio_base DECIMAL(10,2) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE piezas (
    pieza_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    pieza_descripcion VARCHAR(50),
    precio_pieza DECIMAL(10,2) NOT NULL,
    ruta_imagen VARCHAR(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE estados_de_servicios (
    estado_servicio_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    estado_servicio_descripcion VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE servicios (
    servicio_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    cedula_cliente INT NOT NULL,
    tipo_equipo_id INT NOT NULL,
    tipo_servicio_id INT NOT NULL,
    estado_servicio_id INT NOT NULL,
    numero_serie_equipo VARCHAR(500) NOT NULL,
    fecha_ingreso_equipo DATE NOT NULL,
    ruta_imagen VARCHAR(1024) NOT NULL,
    descripcion_problema VARCHAR(500) NOT NULL,
    CONSTRAINT fk_cedula_cliente
        FOREIGN KEY (cedula_cliente) REFERENCES clientes(cedula_cliente)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_tipo_equipo
        FOREIGN KEY (tipo_equipo_id) REFERENCES tipos_equipo(tipo_equipo_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_tipo_servicio
        FOREIGN KEY (tipo_servicio_id) REFERENCES tipos_servicio(tipo_servicio_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_estado_servicio
        FOREIGN KEY (estado_servicio_id) REFERENCES estados_de_servicios(estado_servicio_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE servicios_piezas (
    servicio_id INT NOT NULL,
    pieza_id INT NOT NULL,
    PRIMARY KEY (servicio_id, pieza_id),
    CONSTRAINT fk_servicios_piezas_servicios
        FOREIGN KEY (servicio_id) REFERENCES servicios(servicio_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_servicios_piezas_piezas
        FOREIGN KEY (pieza_id) REFERENCES piezas(pieza_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE facturas (
    factura_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    servicio_id INT NOT NULL,
    observaciones_satisfaccion_usuario VARCHAR(500),
    subtotal_piezas DECIMAL(10,2) NOT NULL DEFAULT 0,
    subtotal_mano_obra DECIMAL(10,2) NOT NULL DEFAULT 0,
    descuento_aplicado DECIMAL(10,2) NOT NULL DEFAULT 0,
    impuestos_aplicados DECIMAL(10,2) NOT NULL DEFAULT 0,
    total_final DECIMAL(10,2) NOT NULL DEFAULT 0,
    fecha_emision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_facturas_servicios
        FOREIGN KEY (servicio_id) REFERENCES servicios(servicio_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE VIEW vista_servicios_con_piezas AS
SELECT 
    sp.servicio_id,
    p.pieza_descripcion,
    p.precio_pieza,
    p.ruta_imagen
FROM servicios_piezas sp
INNER JOIN piezas p ON sp.pieza_id = p.pieza_id;

CREATE OR REPLACE VIEW vista_facturas_detalle_completo AS
SELECT
    f.factura_id,
    f.servicio_id,
    s.cedula_cliente,
    c.nombre_cliente,
    f.observaciones_satisfaccion_usuario,
    s.numero_serie_equipo,
    s.fecha_ingreso_equipo,
    s.ruta_imagen AS servicio_ruta_imagen,
    s.descripcion_problema,
    p.pieza_id,
    p.pieza_descripcion,
    p.precio_pieza,
    p.ruta_imagen AS pieza_ruta_imagen,
    SUM(p.precio_pieza) OVER (PARTITION BY f.factura_id) AS total_piezas
FROM facturas f
INNER JOIN servicios s ON f.servicio_id = s.servicio_id
INNER JOIN clientes c ON s.cedula_cliente = c.cedula_cliente
LEFT JOIN servicios_piezas sp ON s.servicio_id = sp.servicio_id
LEFT JOIN piezas p ON sp.pieza_id = p.pieza_id;

-- Insertar datos iniciales

INSERT INTO roles (descripcion_rol) VALUES
('ADMIN'),
('TECNICO');

INSERT INTO usuarios (cedula_usuario, nombre_usuario, correo_usuario, contrasenia_usuario, telefono_usuario, rol_id) VALUES
(1001, 'David Martínez', 'carlos.martinez@terabyte.com', '$2a$10$XJjc8A0w1qBmhifaCxOsHeV82LD0hjCkbt1VN6egD/7QJofuPLDeW','88888880', 1),
(1002, 'Lucyna Gómez', 'laura.gomez@terabyte.com','$2a$10$XJjc8A0w1qBmhifaCxOsHeV82LD0hjCkbt1VN6egD/7QJofuPLDeW', '88888881', 1),
(1003, 'Andrés Rojas', 'andres.rojas@terabyte.com','$2a$10$XJjc8A0w1qBmhifaCxOsHeV82LD0hjCkbt1VN6egD/7QJofuPLDeW', '88888882', 2);

INSERT INTO clientes (cedula_cliente, nombre_cliente, telefono_cliente, correo_cliente) VALUES
(2001, 'Juan Pérez', '1102223344', 'juan.perez@gmail.com'),
(2002, 'María Torres', '3129988776', 'maria.torres@hotmail.com'),
(2003, 'Luis Castillo', '3147778899', 'luis.castillo@yahoo.com');

INSERT INTO tipos_equipo (tipo_equipo_descripcion) VALUES
('Laptop'),
('PC de Escritorio'),
('Impresora'),
('Tablet'),
('Teléfono móvil');

INSERT INTO tipos_servicio (tipo_servicio_descripcion, precio_base) VALUES
('Mantenimiento preventivo', 15000),
('Reparación de hardware', 25000),
('Instalación de software', 18000),
('Diagnóstico', 12000),
('Actualización del sistema', 20000);

INSERT INTO piezas (pieza_descripcion, precio_pieza, ruta_imagen) VALUES
('Disco duro SSD 512GB', 32000.00, 'https://media.istockphoto.com/id/1410783528/es/foto/el-disco-ssd-m2-se-cierra-sobre-fondo-oscuro.jpg?s=2048x2048&w=is&k=20&c=r2PqYuCjB5pn4kCqWTBRudqTr1qOnUu0TjR3sLjp3Lc='),
('Memoria RAM 8GB DDR4', 18000.00, 'https://media.istockphoto.com/id/157721899/es/foto/macho-parte-de-la-instalaci%C3%B3n-de-memoria-en-placa-de-ordenador.jpg?s=2048x2048&w=is&k=20&c=pcIjoWX_1UZohSS0NaJnD8OnDzvgU5QEw_PW7VO1LYk='),
('Fuente de poder 500W', 25000.00, 'https://media.istockphoto.com/id/1250266807/es/foto/unidad-de-fuente-de-alimentaci%C3%B3n-del-ordenador.jpg?s=2048x2048&w=is&k=20&c=mI-hGhfB75vNefD9xIRtyAZIFVB2zgPuMlCxZtyBhps='),
('Pantalla LCD 15.6 pulgadas"', 55000.00, 'https://media.istockphoto.com/id/1002728980/es/vector/amplia-maqueta-del-monitor-de-tv.jpg?s=2048x2048&w=is&k=20&c=Z-WUa_l6SJAPDrJx_o_Jw_AMQodgWQsb3H-zhZmfnjM='),
('Teclado retroiluminado', 15000.00, 'https://media.istockphoto.com/id/1396231106/es/foto/teclado-para-juegos-con-retroiluminaci%C3%B3n.jpg?s=2048x2048&w=is&k=20&c=5YGocOhGIes-Jtq5ZxTTgc2HjfiVCgbgSG6fExgvBd0='),
('Placa madre', 80000.00, 'https://media.istockphoto.com/id/1174088603/es/foto/placa-de-circuito.jpg?s=2048x2048&w=is&k=20&c=zvcRPiWw2hkRgvDUwIDkyJQhgOEafAN2i355N_GjSNM=');

INSERT INTO estados_de_servicios (estado_servicio_descripcion) VALUES
('Pendiente'),
('En proceso'),
('Finalizado');

INSERT INTO servicios (
    cedula_cliente, tipo_equipo_id, tipo_servicio_id,
    estado_servicio_id, numero_serie_equipo, fecha_ingreso_equipo,
    ruta_imagen, descripcion_problema
) VALUES
(2001, 1, 2, 2, 'SN-LT-20251028-001', '2025-10-25', 'https://media.istockphoto.com/id/185291412/photo/laptop-45-degree-open.jpg', 'No enciende la laptop, posible problema de placa madre'),
(2002, 2, 1, 3, 'SN-PC-20251028-002', '2025-10-20', 'https://i.ytimg.com/vi/5-CYF2Oy9GU/maxresdefault.jpg', 'Se quemo la cosa mop'),
(2003, 3, 4, 1, 'SN-IMP-20251028-003', '2025-10-27', 'https://mediaserver.goepson.com/ImConvServlet/imconv/61dcb6a700968d5fe27870dc9e72d7151805d623/1200Wx1200H', 'Impresora no imprime, posible atasco de papel');

INSERT INTO servicios_piezas (
    servicio_id, pieza_id
) VALUES (1,2),(1,3),(2,2),(3,3);
