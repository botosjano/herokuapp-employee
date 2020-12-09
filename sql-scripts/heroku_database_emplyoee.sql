DROP DATABASE  IF EXISTS `heroku_a3a06449ace0e94`;

CREATE DATABASE  IF NOT EXISTS `heroku_a3a06449ace0e94`;
USE `heroku_a3a06449ace0e94`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE utf8_hungarian_ci;

--
-- Data for table `employee`
--

INSERT INTO `employee` VALUES 
	(1,'Túró','Rudi','trudolf@gmail.com'),
	(2,'Pop','Simon','popsimi@citromail.hu'),
	(3,'Lüke','Tóni','ltoni@freemail.hu'),
	(4,'Balaton','Boglár','bbogi@gmail.com'),
	(5,'Ham','Miska','hammisi@freemailhu'),
    (6,'Bors','Menta','bmenta@gmail.com');

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `activation` varchar(50),
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE utf8_hungarian_ci;

--
-- Dumping data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: http://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: fun123
--

INSERT INTO `user` (username,password,first_name,last_name,email,activation,enabled)
VALUES 
('user','$2a$10$BxWx8Eur062/n/46VyGVaOk5rLvE58OFExbmv0tK8vtV/OjIvDuOy','Kiss','Pista','kpista@freemail.hu','',1),
('manager','$2a$10$BxWx8Eur062/n/46VyGVaOk5rLvE58OFExbmv0tK8vtV/OjIvDuOy','Nagy','Gábor','ngabi@gmail.com','',1),
('admin','$2a$10$BxWx8Eur062/n/46VyGVaOk5rLvE58OFExbmv0tK8vtV/OjIvDuOy','Szabó','Géza','szgeeza@hotmail.com','',1);


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(11, 11),
(21, 21)