CREATE DATABASE `projectocitas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

Use projectocitas;

CREATE TABLE `especialidad` (
  `especialidad_id` int NOT NULL AUTO_INCREMENT,
  `especialidad_nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`especialidad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `localidad` (
  `localidad_id` int NOT NULL AUTO_INCREMENT,
  `localidad_nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`localidad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `perfiles` (
  `perfil_id` int NOT NULL AUTO_INCREMENT,
  `perfil_nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`perfil_id`),
  UNIQUE KEY `ID_UNIQUE` (`perfil_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `permisos` (
  `permisos_id` int NOT NULL AUTO_INCREMENT,
  `permisos_nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`permisos_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `permisosperfil` (
  `perfil_id` int NOT NULL,
  `permiso_id` int NOT NULL,
  PRIMARY KEY (`perfil_id`,`permiso_id`),
  KEY `fk_permiso_idx` (`permiso_id`),
  CONSTRAINT `fk_perfil` FOREIGN KEY (`perfil_id`) REFERENCES `perfiles` (`perfil_id`),
  CONSTRAINT `fk_permiso` FOREIGN KEY (`permiso_id`) REFERENCES `permisos` (`permisos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuario` (
  `usuario_id` varchar(10) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `clave` varchar(45) DEFAULT NULL,
  `perfil_id` int DEFAULT NULL,
  `estado` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  KEY `fk_perfil_idx` (`perfil_id`),
  CONSTRAINT `fk_perfil_usuario` FOREIGN KEY (`perfil_id`) REFERENCES `perfiles` (`perfil_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `medico` (
  `medico_id` varchar(10) NOT NULL,
  `costoconsulta` varchar(6) DEFAULT NULL,
  `localidad` int DEFAULT NULL,
  `especialidad` int DEFAULT NULL,
  `horariosemanal` varchar(99) DEFAULT NULL,
  `frecuencia` int DEFAULT NULL,
  `foto` VARCHAR(255) DEFAULT NULL,
  `presentacion` TEXT DEFAULT NULL,
  PRIMARY KEY (`medico_id`),
  KEY `fk_medico_localidad_idx` (`localidad`),
  CONSTRAINT `fk_medico_localidad` FOREIGN KEY (`localidad`) REFERENCES `localidad` (`localidad_id`),
  CONSTRAINT `fk_medico_especialidad` FOREIGN KEY (`especialidad`) REFERENCES `especialidad` (`especialidad_id`),
  CONSTRAINT `fk_medico_usuario` FOREIGN KEY (`medico_id`) REFERENCES `usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cita` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `hora_inicio` TIME NOT NULL,
  `estado` VARCHAR(20) NOT NULL, 
  `medico_id` VARCHAR(10) NOT NULL,
  `paciente_id` VARCHAR(10) DEFAULT NULL, 

  PRIMARY KEY (`id`),
  FOREIGN KEY (`medico_id`) REFERENCES `medico`(`medico_id`),
  FOREIGN KEY (`paciente_id`) REFERENCES `usuario`(`usuario_id`)
);

