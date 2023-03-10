-- MySQL Script generated by MySQL Workbench
-- Tue Jan  3 18:52:32 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

drop schema dbTest;

create database dbTest;
use dbTest;

/*
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
*/

-- -----------------------------------------------------
-- Schema GamingWorld
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table Account
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Account (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(32) NOT NULL,
  email VARCHAR(50) NOT NULL,
  dataIscrizione DATE NOT NULL,
  PRIMARY KEY (username)
);


-- -----------------------------------------------------
-- Table Amicizia
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Amicizia (
  accountRichiedente VARCHAR(50) NOT NULL,
  accountAccettante VARCHAR(50) NOT NULL,
  data DATE,
  INDEX accountRichiedente_idx (accountRichiedente ASC) VISIBLE,
  INDEX accountAccettante_idx (accountAccettante ASC) VISIBLE,
  PRIMARY KEY (accountRichiedente, accountAccettante),
  CONSTRAINT accountRichiedente
    FOREIGN KEY (accountRichiedente)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT accountAccettante
    FOREIGN KEY (accountAccettante)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
;


-- TRIGGERS FOR AMICIZIA
/*
DELIMITER //
CREATE TRIGGER differentUsername BEFORE INSERT ON Amicizia for each row 
begin 
IF NEW.accountAccettante = NEW.accountRichiedente THEN SET NEW.accountAccettante = NULL;
end if;
end // 
DELIMITER ; 
*/

#MOD
DELIMITER //
CREATE TRIGGER validFriendShip BEFORE INSERT ON Amicizia for each row 
begin 
IF NEW.accountAccettante = NEW.accountRichiedente OR 
EXISTS(SELECT * FROM Amicizia as A2 WHERE NEW.accountAccettante = A2.accountRichiedente AND NEW.accountRichiedente = A2.accountAccettante) 
THEN SET NEW.accountAccettante = NULL;
end if;
end // 
DELIMITER ; 



-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Report (
  Reported VARCHAR(50) NOT NULL,
  Reporter VARCHAR(50) NOT NULL,
  data DATE NOT NULL,
  motivo ENUM("hacking", "bullismo", "spam") NOT NULL,
  INDEX reported_idx (Reported ASC) VISIBLE,
  INDEX reporter_idx (Reporter ASC) VISIBLE,
  PRIMARY KEY (Reported, Reporter, data),
  CONSTRAINT reported
    FOREIGN KEY (Reported)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT reporter
    FOREIGN KEY (Reporter)
    REFERENCES Account (username)
    ON DELETE CASCADE -- CHIEDERE
    ON UPDATE CASCADE
);



DELIMITER //
CREATE TRIGGER differentUsernameReport BEFORE INSERT ON Report for each row 
begin 
IF NEW.reported = NEW.reporter THEN signal sqlstate '45000';
end if;
end // 
DELIMITER ; 



-- -----------------------------------------------------
-- Table Developer
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Developer (
  idDeveloper INT UNSIGNED NOT NULL AUTO_INCREMENT,
  sedeLegale VARCHAR(100) NOT NULL,
  nome VARCHAR(60) NOT NULL,
  PRIMARY KEY (idDeveloper)
);


-- -----------------------------------------------------
-- Table Publisher
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Publisher (
  idPublisher INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  sedeLegale VARCHAR(100) NOT NULL,
  numberOfGames INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (idPublisher))
;


-- -----------------------------------------------------
-- Table Categoria
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Categoria (
  nome VARCHAR(20) NOT NULL,
  PRIMARY KEY (nome))
;


-- -----------------------------------------------------
-- Table Gioco
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Gioco (
  idGioco INT AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  memoriaOccupata INT UNSIGNED NOT NULL DEFAULT 0,
  descrizione LONGTEXT NULL,
  pageURL VARCHAR(150) NULL,
  idPublisher INT UNSIGNED NOT NULL,
  PRIMARY KEY (idGioco),
  INDEX publisher_idx (idPublisher ASC) VISIBLE,
  CONSTRAINT publisher
    FOREIGN KEY (idPublisher)
    REFERENCES Publisher (idPublisher)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Trigger Per aumentare
CREATE TRIGGER increaseNumberOfGames 
AFTER INSERT ON Gioco 
FOR EACH ROW
UPDATE Publisher as P SET numberOfGames = numberOfGames + 1 WHERE NEW.idPublisher = P.idPublisher;

-- Trigger Per diminuire
CREATE TRIGGER decreaseNumberOfGames
AFTER DELETE ON Gioco
FOR EACH ROW
UPDATE Publisher as P SET numberOfGames = numberOfGames - 1 WHERE OLD.idPublisher = P.idPublisher;


-- -----------------------------------------------------
-- Table Possiede
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Possiede (
  username VARCHAR(50) NOT NULL,
  idGioco INT NOT NULL,
  dataAcquisto DATETIME NOT NULL,
  oreGiocate DOUBLE NOT NULL DEFAULT 0,
  INDEX username_idx (username ASC) VISIBLE,
  INDEX gioco_idx (idGioco ASC) VISIBLE,
  PRIMARY KEY (username, idGioco),
  CONSTRAINT account
    FOREIGN KEY (username)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT gioco
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Team
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Team (
  nome VARCHAR(50) NOT NULL,
--  username1 VARCHAR(50) NOT NULL,
--  username2 VARCHAR(50) NOT NULL,
--  username3 VARCHAR(50) NOT NULL,
--  username4 VARCHAR(50) NOT NULL,
--  username5 VARCHAR(50) NOT NULL,
  PRIMARY KEY (nome)
/*  INDEX username1_idx (username1 ASC) VISIBLE,
  INDEX username2_idx (username2 ASC) VISIBLE,
  INDEX username3_idx (username3 ASC) VISIBLE,
  INDEX username4_idx (username4 ASC) VISIBLE,
  INDEX username5_idx (username5 ASC) VISIBLE,
  CONSTRAINT username1
    FOREIGN KEY (username1)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT username2
    FOREIGN KEY (username2)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT username3
    FOREIGN KEY (username3)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT username4
    FOREIGN KEY (username4)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT username5
    FOREIGN KEY (username5)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE*/
);



CREATE TABLE IF NOT EXISTS Compone (
	nome VARCHAR(50) NOT NULL,
    CONSTRAINT nome 
    FOREIGN KEY (nome) references Team (nome)
	ON DELETE CASCADE
    ON UPDATE CASCADE,
    componente VARCHAR(50) ,
    CONSTRAINT componente 
    FOREIGN KEY (componente) references Account (username)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);


DELIMITER //
CREATE TRIGGER componeMaxMemebers BEFORE INSERT ON Compone for each row 
begin 
IF (SELECT Count(*) FROM Compone as C2 WHERE new.nome = C2.nome) = 5 THEN signal sqlstate '45000';
end if;
end // 
DELIMITER ; 
-- -----------------------------------------------------
-- Table TeamGame
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS TeamGame (
  idGioco INT NOT NULL,
  PRIMARY KEY (idGioco),
  CONSTRAINT team_game
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Match
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Match` (
  idMatch INT UNSIGNED NOT NULL AUTO_INCREMENT,
  idGioco INT NOT NULL,
  team1 VARCHAR(50) NOT NULL,
  team2 VARCHAR(50) NOT NULL,
  teamVincitore VARCHAR(50) NOT NULL,
  durata DOUBLE UNSIGNED NOT NULL,
  data DATE NOT NULL,
  INDEX team1_idx (team1 ASC) VISIBLE,
  INDEX team2_idx (team2 ASC) VISIBLE,
  INDEX winningTeam_idx (teamVincitore ASC) VISIBLE,
  PRIMARY KEY (idMatch, idGioco),
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
  CONSTRAINT match_gioco
    FOREIGN KEY (idGioco)
    REFERENCES TeamGame (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT team1
    FOREIGN KEY (team1)
    REFERENCES Team (nome)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT team2
    FOREIGN KEY (team2)
    REFERENCES Team (nome)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT winningTeam
    FOREIGN KEY (teamVincitore)
    REFERENCES Team (nome)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table ServerGame
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ServerGame (
  idGioco INT NOT NULL,
  PRIMARY KEY (idGioco),
  CONSTRAINT server_game
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Server
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Server (
  idServer INT UNSIGNED AUTO_INCREMENT,
  idGioco INT NOT NULL,
  latenzaMedia INT UNSIGNED NOT NULL,
  PRIMARY KEY (idServer),
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
  CONSTRAINT server_gioco
    FOREIGN KEY (idGioco)
    REFERENCES ServerGame (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Salva
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Salva (
  username VARCHAR(50) NOT NULL,
  idServer INT UNSIGNED NOT NULL,
  nome VARCHAR(100) NOT NULL,
  INDEX idServer_idx (idServer ASC) VISIBLE,
  INDEX username_idx (username ASC) VISIBLE,
  PRIMARY KEY (username, idServer),
  CONSTRAINT idServer
    FOREIGN KEY (idServer)
    REFERENCES Server (idServer)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT salva_account
    FOREIGN KEY (username)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Sviluppa
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Sviluppa (
  idDeveloper INT UNSIGNED NOT NULL,
  idGioco INT NOT NULL,
  ruolo VARCHAR(50) NOT NULL,
  INDEX idDeveloper_idx (idDeveloper ASC) VISIBLE,
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
  PRIMARY KEY (idDeveloper, idGioco),
  CONSTRAINT idDeveloper
    FOREIGN KEY (idDeveloper)
    REFERENCES Developer (idDeveloper)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT gioco_sviluppo
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Appartiene
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Appartiene (
  idGioco INT NOT NULL,
  nomeCategoria VARCHAR(50) NOT NULL,
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
  INDEX categoria_idx (nomeCategoria ASC) VISIBLE,
  PRIMARY KEY (idGioco, nomeCategoria),
  CONSTRAINT appartiene_gioco
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT categoria
    FOREIGN KEY (nomeCategoria)
    REFERENCES Categoria (nome)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Changelog
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Changelog (
  version INT UNSIGNED NOT NULL AUTO_INCREMENT,
  idGioco INT NOT NULL,
  descrizione LONGTEXT NOT NULL,
  URL VARCHAR(100) NOT NULL,
  specifiche VARCHAR(60) NOT NULL,
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
  PRIMARY KEY (version, idGioco),
  CONSTRAINT changelog_gioco
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Achievement
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Achievement (
  nome VARCHAR(100) NOT NULL,
  idGioco INT NOT NULL,
  descrizione LONGTEXT NOT NULL,
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
   PRIMARY KEY (nome, idGioco),
  CONSTRAINT achievement_gioco
    FOREIGN KEY (idGioco)
    REFERENCES Gioco (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table Sblocca
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Sblocca (
  username VARCHAR(50) NOT NULL,
  nomeAchievement VARCHAR(100) NOT NULL,
  idGioco INT NOT NULL,
  INDEX username_idx (username ASC) VISIBLE,
  INDEX idGioco_idx (idGioco ASC) VISIBLE,
  INDEX nomeAchievement_idx (nomeAchievement ASC) VISIBLE,
  PRIMARY KEY (username, nomeAchievement, idGioco),
  CONSTRAINT username
    FOREIGN KEY (username)
    REFERENCES Account (username)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT nomeAchievement
    FOREIGN KEY (nomeAchievement)
    REFERENCES Achievement (nome)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT sblocca_gioco
    FOREIGN KEY (idGioco)
    REFERENCES Achievement (idGioco)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);



-- CREATE TRIGGER SBLOCCA
DELIMITER //
CREATE TRIGGER sbloccaSePossiede BEFORE INSERT ON Sblocca for each row 
begin 
IF NOT EXISTS(SELECT * FROM Possiede as P WHERE P.idGioco = NEW.idGioco AND NEW.username = P.username) THEN signal sqlstate '45000';
end if;
end // 
DELIMITER ; 