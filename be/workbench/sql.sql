SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `billonair` DEFAULT CHARACTER SET latin1 ;
USE `billonair` ;

-- -----------------------------------------------------
-- Table `billonair`.`utenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billonair`.`utenti` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `cognome` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `billonair`.`queries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billonair`.`queries` (
  `query` LONGTEXT NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `username_idx` (`username` ASC),
  CONSTRAINT `username`
    FOREIGN KEY (`username`)
    REFERENCES `billonair`.`utenti` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `billonair`.`stanze`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billonair`.`stanze` (
  `idStanza` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idStanza`, `username`),
  INDEX `username_idx` (`username` ASC),
  INDEX `username_idx2` (`username` ASC),
  INDEX `user_idx` (`username` ASC),
  INDEX `user_idx2` (`username` ASC),
  INDEX `username_idx22` (`username` ASC),
  INDEX `user_idx22` (`username` ASC),
  CONSTRAINT `user`
    FOREIGN KEY (`username`)
    REFERENCES `billonair`.`utenti` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `billonair`.`spesestanza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `billonair`.`spesestanza` (
  `idSpesa` INT(11) NOT NULL AUTO_INCREMENT,
  `creditore` VARCHAR(45) NOT NULL,
  `debitore` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `dovuto` DECIMAL(10,2) NOT NULL,
  `data` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `idStanza` INT(11) NOT NULL,
  PRIMARY KEY (`idSpesa`, `creditore`,`debitore`),
  CONSTRAINT `stanza`
    FOREIGN KEY (`idStanza`)
    REFERENCES `billonair`.`stanze` (`idStanza`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

