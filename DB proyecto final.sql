CREATE DATABASE terabyte_technology;
USE terabyte_technology;

CREATE TABLE roles (
	rol_id INT PRIMARY KEY NOT NULL,
    descripcion_rol VARCHAR(150) NOT NULL
);

CREATE TABLE usuarios (
	cedula_usuario INT PRIMARY KEY NOT NULL,
    nombre_usuario VARCHAR(150)  NOT NULL,
    correo_usuario VARCHAR(150)  NOT NULL UNIQUE,
    telefono_usuario INT  NOT NULL UNIQUE,
    rol_id INT  NOT NULL,
    CONSTRAINT fk_usuarios_roles foreign key (rol_id) references roles(rol_id) ON DELETE RESTRICT on update CASCADE
);

CREATE TABLE clientes (
	cedula_cliente INT PRIMARY KEY NOT NULL,
    nombre_cliente VARCHAR(150) NOT NULL,
    telefono_cliente INT not null UNIQUE,
    correo_cliente VARCHAR(150) UNIQUE
);

CREATE TABLE tipos_equipo (
	tipo_equipo_id INT PRIMARY KEY auto_increment NOT NULL,
    tipo_equipo_descripcion VARCHAR(50)
);

CREATE TABLE tipos_servicio (
    tipo_servicio_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tipo_servicio_descripcion VARCHAR(50)
);


CREATE TABLE piezas(
	pieza_id INT PRIMARY KEY auto_increment  NOT NULL,
	pieza_descripcion VARCHAR(50)
);

CREATE TABLE estados_de_servicios (
	estado_servicio_id INT PRIMARY KEY NOT NULL,
    estado_servicio_descripcion VARCHAR(50)
);




CREATE TABLE servicios(
	servicio_id INT PRIMARY KEY auto_increment NOT NULL,
    cedula_cliente INT NOT NULL,
    tipo_equipo_id INT NOT NULL,
    tipo_servicio_id INT NOT NULL,
    estado_servicio_id INT NOT NULL,
    numero_serie_equipo varchar(500) NOT NULL,
    fecha_ingreso_equipo DATE NOT NULL,
    ruta_imagen VARCHAR(1024) NOT NULL,
    descripcion_problema VARCHAR(500 ) NOT NULL,
    CONSTRAINT fk_cedula_cliente FOREIGN KEY (cedula_cliente) references clientes(cedula_cliente) ON DELETE RESTRICT on update CASCADE,
    CONSTRAINT fk_tipo_equipo FOREIGN KEY (tipo_equipo_id) references tipos_equipo(tipo_equipo_id) ON DELETE RESTRICT on update CASCADE,
    CONSTRAINT fk_tipo_servicio FOREIGN KEY (tipo_servicio_id) references tipos_servicio(tipo_servicio_id) ON DELETE RESTRICT on update CASCADE,
    CONSTRAINT fk_estado_servicio FOREIGN KEY (estado_servicio_id) references estados_de_servicios(estado_servicio_id) ON DELETE RESTRICT on update CASCADE
);


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
);

CREATE VIEW vista_servicios_con_piezas 
AS
SELECT 
	sp.servicio_id,
    p.pieza_descripcion
FROM servicios_piezas sp
INNER JOIN piezas p ON sp.pieza_id = p.pieza_id;


CREATE TABLE facturas (
    factura_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    servicio_id INT NOT NULL,
    observaciones_satisfaccion_usuario VARCHAR(500),
    CONSTRAINT fk_facturas_servicios 
        FOREIGN KEY (servicio_id) REFERENCES servicios(servicio_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);


INSERT INTO roles (rol_id, descripcion_rol) VALUES
(1, 'Administrador'),
(2, 'Técnico'),
(3, 'Recepcionista');

INSERT INTO usuarios (cedula_usuario, nombre_usuario, correo_usuario, telefono_usuario, rol_id) VALUES
(1001, 'David Martínez', 'carlos.martinez@terabyte.com', 88888888, 1),
(1002, 'Lucyna Gómez', 'laura.gomez@terabyte.com', 88888888, 2),
(1003, 'Andrés Rojas', 'andres.rojas@terabyte.com', 88888888, 3);

INSERT INTO clientes (cedula_cliente, nombre_cliente, telefono_cliente, correo_cliente) VALUES
(2001, 'Juan Pérez', 1102223344, 'juan.perez@gmail.com'),
(2002, 'María Torres', 3129988776, 'maria.torres@hotmail.com'),
(2003, 'Luis Castillo', 3147778899, 'luis.castillo@yahoo.com');

INSERT INTO tipos_equipo (tipo_equipo_descripcion) VALUES
('Laptop'),
('PC de Escritorio'),
('Impresora'),
('Tablet'),
('Teléfono móvil');
 
INSERT INTO tipos_servicio (tipo_servicio_descripcion) VALUES
('Mantenimiento preventivo'),
('Reparación de hardware'),
('Instalación de software'),
('Diagnóstico'),
('Actualización del sistema');

INSERT INTO piezas (pieza_descripcion) VALUES
('Disco duro SSD 512GB'),
('Memoria RAM 8GB DDR4'),
('Fuente de poder 500W'),
('Pantalla LCD 15.6"'),
('Teclado retroiluminado'),
('Placa madre ASUS Prime');


INSERT INTO estados_de_servicios (estado_servicio_id, estado_servicio_descripcion) VALUES
(1, 'Pendiente'),
(2, 'En proceso'),
(3, 'Finalizado'),
(4, 'Entregado');


INSERT INTO servicios (
    cedula_cliente, tipo_equipo_id, tipo_servicio_id,
    estado_servicio_id, numero_serie_equipo, fecha_ingreso_equipo,
    ruta_imagen, descripcion_problema
) VALUES
(2001, 1, 2, 2, 'SN-LT-20251028-001', '2025-10-25', 'https://media.istockphoto.com/id/185291412/photo/laptop-45-degree-open.jpg?s=2048x2048&w=is&k=20&c=JdCzT8Q1mwepn5iH8WEOz05TprI5h7RVLCzRP5x1dVA=', 'No enciende la laptop, posible problema de placa madre'),
(2002, 2, 1, 3, 'SN-PC-20251028-002', '2025-10-20', 'https://i.ytimg.com/vi/5-CYF2Oy9GU/maxresdefault.jpg', 'Se quemo la cosa'),
(2003, 3, 4, 1, 'SN-IMP-20251028-003', '2025-10-27', 'https://mediaserver.goepson.com/ImConvServlet/imconv/61dcb6a700968d5fe27870dc9e72d7151805d623/1200Wx1200H?use=banner&hybrisId=B2C&assetDescr=L8050_aberta', 'Impresora no imprime, posible atasco de papel');


INSERT INTO facturas (servicio_id, observaciones_satisfaccion_usuario) VALUES
(1, 'Cliente satisfecho, equipo funcionando correctamente.'),
(2, 'El cliente agradeció el mantenimiento.'),
(3, 'Cliente a la espera de confirmación del diagnóstico.');


