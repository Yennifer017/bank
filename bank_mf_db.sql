DROP SCHEMA WebBanco;
CREATE SCHEMA WebBanco;
USE WebBanco;

CREATE TABLE usuario (
  codigo INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  direccion VARCHAR(250) NOT NULL,
  noIdentificacion VARCHAR(15) NOT NULL,
  sexo VARCHAR(10) NOT NULL,
  password VARCHAR(40) NOT NULL,
  tipoUsuario INT NOT NULL, /* 1-cliente 2-cajero 3-gerente*/
  PRIMARY KEY (codigo));


CREATE TABLE turno (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  horaEntrada TIME NOT NULL,
  horaSalida TIME NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE empleado (
  codigoUsuario INT NOT NULL,
  idTurno INT NOT NULL,
  PRIMARY KEY (codigoUsuario),
  CONSTRAINT FK_EMPLEADO_TO_USUARIO
    FOREIGN KEY (codigoUsuario)
    REFERENCES usuario (codigo)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT FK_EMPLEADO_TO_TURNO
    FOREIGN KEY (idTurno)
    REFERENCES turno (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE cliente (
  codigoUsuario INT NOT NULL,
  birth DATE NOT NULL,
  pdfDPI MEDIUMBLOB NOT NULL,
  PRIMARY KEY (codigoUsuario),
  CONSTRAINT FK_CLIENTE_TO_USUARIO
    FOREIGN KEY (codigoUsuario)
    REFERENCES usuario (codigo)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE cuenta (
  codigo INT NOT NULL AUTO_INCREMENT,
  codigoCliente INT NOT NULL,
  fechaCreacion DATE NOT NULL,
  saldo FLOAT NOT NULL,
  PRIMARY KEY (codigo),
  CONSTRAINT FK_CUENTA_TO_CLIENTE
    FOREIGN KEY (codigoCliente)
    REFERENCES cliente (codigoUsuario)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE limite (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(60) NOT NULL,
  valor FLOAT NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE transaccion (
  codigo INT NOT NULL AUTO_INCREMENT,
  codCuenta INT NOT NULL, /*el cliente lo proporcionaria no es necesario realiza una busqueda*/
  tipo VARCHAR(15) NOT NULL, /*credito (+) o debito (-)*/
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  monto FLOAT NOT NULL,
  codCajero INT NOT NULL,
  /*saldoCuenta FLOAT NOT NULL, ---------- no es necesario no aporta nada*/
  PRIMARY KEY (codigo),
  CONSTRAINT FK_TRANSACCION_TO_CUENTA
    FOREIGN KEY (codCuenta)
    REFERENCES cuenta (codigo)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT FK_TRANSACCION_TO_EMPLEADO
    FOREIGN KEY (codCajero)
    REFERENCES empleado (codigoUsuario)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE cambioRealizado (
  id INT NOT NULL AUTO_INCREMENT,
  codGerente INT NOT NULL,
  codUsuarioModificado INT NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_CAMBIO_TO_EMPLEADO
    FOREIGN KEY (codGerente)
    REFERENCES empleado (codigoUsuario)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT FK_CAMBIO_TO_USUARIO
    FOREIGN KEY (codUsuarioModificado)
    REFERENCES usuario (codigo)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE cuentaAsociada (
  codCliente INT NOT NULL,    
  codCuenta INT NOT NULL,
  PRIMARY KEY (codCliente, codCuenta),
  CONSTRAINT FK_CUENTAASO_TO_CLIENTE
    FOREIGN KEY (codCliente)
    REFERENCES cliente (codigoUsuario)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT FK_CUENTAASO_TO_CUENTA
    FOREIGN KEY (codCuenta)
    REFERENCES cuenta (codigo)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE solicitudAsociacion (
  id INT NOT NULL AUTO_INCREMENT,
  codCliente INT NOT NULL,
  codCuenta INT NOT NULL,
  fecha DATE NOT NULL,
  estado INT NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT FK_SOLICITUD_TO_CLIENTE
    FOREIGN KEY (codCliente)
    REFERENCES cliente (codigoUsuario)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT FK_SOLICITUD_TO_CUENTA
    FOREIGN KEY (codCuenta)
    REFERENCES cuenta (codigo)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);
    
/*inicializacion de turnos*/    
INSERT INTO turno VALUES(1, 'Matutino', '6:00', '14:30');
INSERT INTO turno VALUES(2, 'Vespertino', '13:00', '22:00');
INSERT INTO turno VALUES(3, 'Toda Hora', '00:00', '23:59:59');
/*usuario por defecto*/
INSERT INTO usuario VALUES (101, 'Banca Virtual', 'dir101', '101', 'na', 'bed4ed7c66daaee3451bffb7291c5dec', 2);
INSERT INTO empleado VALUES (101, 3);
/*limites de reportes*/
INSERT INTO limite VALUES(1, 'Limite reporte 2 (Transacciones)', 0);
INSERT INTO limite VALUES(2, 'Limite reporte 3 (Transacciones sumadas)', 0);

/*inicializacion de un gerente */
INSERT INTO usuario VALUES (1, 'gerente_ydl', 'ciudad', '017', 'femenino', 'f50b40b30409574bc9a15e2fc43e3fa3', 3);
INSERT INTO empleado(codigoUsuario, idTurno) VALUES(1, 2);

/* insercion de un cajero */
INSERT INTO usuario VALUES (3, 'cajero', 'ciudad', '111', 'femenino', '25d55ad283aa400af464c76d713c07ad', 2);
INSERT INTO empleado(codigoUsuario, idTurno) VALUES(3, 2);


/*creacion de cuentas */
INSERT INTO cuenta(codigoCliente, fechaCreacion, saldo) VALUES('102', '2023-09-18', '1000');


/*****************CONSULTAS****************/
SELECT cambioRealizado.fecha, cambioRealizado.hora, gerente.noIdentificacion AS identificacionGerente
  FROM cambioRealizado 
  JOIN usuario cliente
  ON cambioRealizado.codUsuarioModificado = cliente.codigo 
  JOIN usuario gerente
  ON cambioRealizado.codGerente = gerente.codigo
  WHERE cambioRealizado.codUsuarioModificado = 3 
  ORDER BY fecha;