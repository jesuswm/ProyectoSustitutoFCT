CREATE DATABASE IF NOT EXISTS `proyecto_fct`;
USE `proyecto_fct`;

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT UNIQUE,
  `nombre` varchar(55) DEFAULT NULL,
  `email` varchar(55) DEFAULT NULL UNIQUE,
  `contraseña` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `amigos` (
  `id` int NOT NULL AUTO_INCREMENT UNIQUE,
  `id_amigo1` int not NULL,
  `id_amigo2` int not NULL,
  CONSTRAINT `amigos_amig1` FOREIGN KEY (`id_amigo1`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `amigos_amig2` FOREIGN KEY (`id_amigo2`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `peticiones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_solicitante` int not NULL,
  `id_solicitado` int not NULL,
  CONSTRAINT `peticiones_solicitante` FOREIGN KEY (`id_solicitante`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `peticiones_solicitado` FOREIGN KEY (`id_solicitado`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_creador` int not NULL,
  `contenido` TEXT DEFAULT NULL,
  `privado` BOOL DEFAULT true,
  `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP(),
  CONSTRAINT `post_creador` FOREIGN KEY (`id_creador`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `comentarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_post` int not NULL,
  `id_creador` int not NULL,
  `contenido` TEXT DEFAULT NULL,
  `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP(),
  CONSTRAINT `comentarios_creador` FOREIGN KEY (`id_creador`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `comentarios_post` FOREIGN KEY (`id_post`) REFERENCES `post` (`id`) ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tokens` (
  `id` int NOT NULL AUTO_INCREMENT UNIQUE,
  `id_usuario` int not NULL,
  `token` varchar(10) not NULL,
  `caducidad` DATETIME DEFAULT NULL,
  CONSTRAINT `tokens_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TRIGGER tr_dt_tokens BEFORE INSERT ON tokens FOR EACH ROW SET NEW.caducidad  = NOW() + INTERVAL 1 HOUR;