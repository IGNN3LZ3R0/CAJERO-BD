create database Banco
use Banco
CREATE TABLE Usuarios (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    nombre_de_usuario VARCHAR(50),
    contraseña_encriptada VARCHAR(255),
    saldo_actual DECIMAL(10, 2) 
);

CREATE TABLE Transacciones (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    tipo_de_transaccion VARCHAR(50),
    monto DECIMAL(10, 2),
    fecha_y_hora DATETIME,
    FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)
);

-- Insertar valores en la tabla de Usuarios
INSERT INTO Usuarios (nombre, nombre_de_usuario, contraseña_encriptada, saldo_actual)
VALUES 
    ('Lady Marin', 'Lady', '0123456789', 200.00),
    ('Yuverly Verdezoto', 'Hidokun', '09052003yv', 200.00);

Select * from Usuarios
