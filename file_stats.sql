DROP SCHEMA IF EXISTS `file_stats`;
CREATE SCHEMA `file_stats` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `file_stats`;

-- -----------------------------------------------------
-- Table `file_stats`.`stats`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `file_stats`.`file_stats` ;

CREATE  TABLE IF NOT EXISTS `file_stats`.`file_stats` (
  `file_id` INT NOT NULL AUTO_INCREMENT,
  `file_name` TEXT NOT NULL ,
  PRIMARY KEY (`file_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- -----------------------------------------------------
-- Table `line_stats`.`stats`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `file_stats`.`line_stats` ;

CREATE  TABLE IF NOT EXISTS `file_stats`.`line_stats` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `file_id` INT NOT NULL ,
  `line_id` INT NOT NULL ,
  `longest_word` TEXT NOT NULL ,
  `shortest_word` TEXT NOT NULL ,
  `line_length` INT NOT NULL ,
  `avg_word_length` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`file_id`) REFERENCES `file_stats`(`file_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;