SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema edulink1_LPriority
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `edulink1_LPriority` DEFAULT CHARACTER SET utf8 ;
USE `edulink1_LPriority` ;

-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`academic_performance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`academic_performance` (
  `academic_id` BIGINT NOT NULL AUTO_INCREMENT,
  `academic_level` VARCHAR(20) NOT NULL,
  `academic_grade` VARCHAR(1) NOT NULL,
  `academic_description` VARCHAR(260) NOT NULL,
  PRIMARY KEY (`academic_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`academic_grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`academic_grades` (
  `acedamic_grade_id` BIGINT NOT NULL AUTO_INCREMENT,
  `acedamic_grade_name` VARCHAR(20) NOT NULL DEFAULT '',
  `score_from` INT NOT NULL,
  `score_to` INT NOT NULL,
  `academic_id` BIGINT NOT NULL,
  PRIMARY KEY (`acedamic_grade_id`),
  INDEX `fk_academic_id_idx` (`academic_id` ASC),
  CONSTRAINT `fk_academic_id`
    FOREIGN KEY (`academic_id`)
    REFERENCES `edulink1_LPriority`.`academic_performance` (`academic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`country` (
  `country_id` BIGINT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NULL,
  `continent` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`country_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`states`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`states` (
  `state_id` BIGINT NOT NULL AUTO_INCREMENT,
  `country_id` BIGINT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`state_id`),
  INDEX `subregion_region_id` (`country_id` ASC),
  CONSTRAINT `country_id_fk`
    FOREIGN KEY (`country_id`)
    REFERENCES `edulink1_LPriority`.`country` (`country_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`school` (
  `school_id` BIGINT NOT NULL AUTO_INCREMENT,
  `school_name` VARCHAR(45) NOT NULL DEFAULT '',
  `type_of_school` VARCHAR(45) NOT NULL DEFAULT '',
  `level_of_school` VARCHAR(45) NOT NULL DEFAULT '',
  `no_of_students` INT(11) NOT NULL DEFAULT '0',
  `city` VARCHAR(45) NOT NULL DEFAULT '',
  `state_id` BIGINT NOT NULL,
  `phone_number` VARCHAR(45) NULL DEFAULT NULL,
  `fax_number` VARCHAR(45) NULL DEFAULT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `registration_date` DATE NOT NULL,
  `logo_link` VARCHAR(50) NULL DEFAULT NULL,
  `country_id` BIGINT NULL,
  `class_strength` INT(11) NULL DEFAULT '0',
  `leveling` VARCHAR(10) NULL DEFAULT 'no',
  `gender_equity` VARCHAR(10) NULL DEFAULT 'no',
  PRIMARY KEY (`school_id`),
  UNIQUE INDEX `UK_school_1` (`school_name` ASC, `type_of_school` ASC, `level_of_school` ASC, `city` ASC, `state_id` ASC),
  INDEX `fk_school_state_id_idx` (`state_id` ASC),
  INDEX `fk_school_country_id_idx` (`country_id` ASC),
  CONSTRAINT `fk_school_state_id`
    FOREIGN KEY (`state_id`)
    REFERENCES `edulink1_LPriority`.`states` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_country_id`
    FOREIGN KEY (`country_id`)
    REFERENCES `edulink1_LPriority`.`country` (`country_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`class` (
  `class_id` BIGINT NOT NULL AUTO_INCREMENT,
  `class_name` VARCHAR(45) NOT NULL DEFAULT '',
  `school_id` BIGINT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`class_id`),
  INDEX `fk_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `edulink1_LPriority`.`school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`master_grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`master_grades` (
  `master_grades_id` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_name` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`master_grades_id`),
  UNIQUE INDEX `UK_grade_1` USING BTREE (`grade_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`grade` (
  `grade_id` BIGINT NOT NULL AUTO_INCREMENT,
  `school_id` BIGINT NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `master_grades_id` BIGINT NULL,
  PRIMARY KEY (`grade_id`),
  INDEX `fk_master_grades_id_idx` (`master_grades_id` ASC),
  CONSTRAINT `fk_master_grades_id`
    FOREIGN KEY (`master_grades_id`)
    REFERENCES `edulink1_LPriority`.`master_grades` (`master_grades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`unit` (
  `unit_id` BIGINT NOT NULL AUTO_INCREMENT,
  `unit_no` BIGINT NULL,
  `unit_name` VARCHAR(45) NULL,
  PRIMARY KEY (`unit_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`lesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`lesson` (
  `lesson_id` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_id` BIGINT NULL,
  `class_id` BIGINT NULL DEFAULT NULL,
  `lesson_no` INT(11) NULL DEFAULT NULL,
  `lesson_name` VARCHAR(45) NOT NULL DEFAULT '',
  `lesson_desc` VARCHAR(300) NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` MEDIUMTEXT NULL DEFAULT NULL,
  `unit_id` BIGINT NULL,
  PRIMARY KEY (`lesson_id`),
  INDEX `fk_lesson_class1_idx` (`class_id` ASC),
  INDEX `fk_lesson_grade_idx` (`grade_id` ASC),
  INDEX `fk_unit_id_idx` (`unit_id` ASC),
  CONSTRAINT `fk_lesson_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `edulink1_LPriority`.`class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lesson_grade`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_LPriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_id`
    FOREIGN KEY (`unit_id`)
    REFERENCES `edulink1_LPriority`.`unit` (`unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`activity` (
  `activity_id` BIGINT NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT NULL,
  `activity_desc` VARCHAR(1000) NULL DEFAULT NULL,
  `created_by` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`activity_id`),
  INDEX `FK_activity_2` (`lesson_id` ASC),
  CONSTRAINT `fk_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `edulink1_LPriority`.`lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`address` (
  `address_id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(45) NOT NULL DEFAULT '',
  `country_id` BIGINT NULL,
  `state_id` BIGINT NULL DEFAULT NULL,
  `city` VARCHAR(30) NULL DEFAULT NULL,
  `zipcode` INT(11) NOT NULL DEFAULT '0',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`),
  INDEX `fk_state_id_idx` (`state_id` ASC),
  INDEX `fk_country_id_idx` (`country_id` ASC),
  CONSTRAINT `fk_state_id`
    FOREIGN KEY (`state_id`)
    REFERENCES `edulink1_LPriority`.`states` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country_id`
    FOREIGN KEY (`country_id`)
    REFERENCES `edulink1_LPriority`.`country` (`country_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`user` (
  `user_typeid` BIGINT NOT NULL AUTO_INCREMENT,
  `user_type` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_typeid`),
  UNIQUE INDEX `UK_user_1` USING BTREE (`user_type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`user_registration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`user_registration` (
  `reg_id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(5) NULL,
  `first_name` VARCHAR(45) NOT NULL DEFAULT '',
  `last_name` VARCHAR(45) NULL,
  `user_typeid` BIGINT NOT NULL DEFAULT '0',
  `password` VARCHAR(45) NULL,
  `qualification` VARCHAR(200) NULL,
  `phonenumber` BIGINT NULL,
  `email_id` VARCHAR(45) NULL,
  `parent_reg_id` BIGINT NULL,
  `address_id` BIGINT NULL,
  `school_id` BIGINT NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `experience` VARCHAR(250) NULL,
  `subjects` VARCHAR(250) NULL,
  `interestareas` VARCHAR(250) NULL,
  `projects` VARCHAR(250) NULL,
  `education` VARCHAR(250) NULL,
  `contactinfo` VARCHAR(250) NULL,
  UNIQUE INDEX `UK_registration_1` (`reg_id` ASC),
  INDEX `FK_registration_2` USING BTREE (`user_typeid` ASC),
  INDEX `FK_registration_1` USING BTREE (`reg_id` ASC),
  PRIMARY KEY (`reg_id`),
  INDEX `fk_address_idx` (`address_id` ASC),
  INDEX `fk_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_usertype`
    FOREIGN KEY (`user_typeid`)
    REFERENCES `edulink1_LPriority`.`user` (`user_typeid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `edulink1_LPriority`.`address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `edulink1_LPriority`.`school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`teacher` (
  `teacher_id` BIGINT NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT NULL,
  `status` VARCHAR(20) NULL,
  PRIMARY KEY (`teacher_id`),
  INDEX `fk_reg_id_idx` (`reg_id` ASC),
  CONSTRAINT `fk_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_LPriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`teacher_performances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`teacher_performances` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `performance` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`admin_teacher_reports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`admin_teacher_reports` (
  `admin_teacher_report_id` BIGINT NOT NULL AUTO_INCREMENT,
  `teacher_id` BIGINT NOT NULL DEFAULT '0',
  `date` DATE NULL DEFAULT NULL,
  `performance_id` BIGINT NULL,
  `choosen_option` VARCHAR(20) NULL DEFAULT NULL,
  `comments` VARCHAR(200) NULL DEFAULT NULL,
  `user_typeid` TINYINT(1) NULL,
  INDEX `performance_id_idx` (`performance_id` ASC),
  PRIMARY KEY (`admin_teacher_report_id`),
  INDEX `teacher_id_idx` (`teacher_id` ASC),
  CONSTRAINT `teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `edulink1_LPriority`.`teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `performance_id`
    FOREIGN KEY (`performance_id`)
    REFERENCES `edulink1_LPriority`.`teacher_performances` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`announcements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`announcements` (
  `announcement_id` BIGINT NOT NULL AUTO_INCREMENT,
  `annoncement_name` VARCHAR(40) NULL,
  `announce_description` VARCHAR(70) NULL DEFAULT NULL,
  `announce_date` DATE NULL,
  `school_id` BIGINT NULL,
  `contact_person` VARCHAR(30) NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`announcement_id`),
  UNIQUE INDEX `announceId` (`announcement_id` ASC),
  INDEX `fk_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_announce_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `edulink1_LPriority`.`school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`grade_classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`grade_classes` (
  `grade_class_id` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_id` BIGINT NOT NULL DEFAULT '0',
  `class_id` BIGINT NOT NULL DEFAULT '0',
  `status` VARCHAR(20) NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `FK_grade_classes_2` (`class_id` ASC),
  PRIMARY KEY (`grade_class_id`),
  INDEX `fk_grade_id_idx` (`grade_id` ASC),
  CONSTRAINT `fk_grade_id`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_LPriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `edulink1_LPriority`.`class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`grade_level`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`grade_level` (
  `grade_level_id` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_level_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`grade_level_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`section` (
  `section_id` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_class_id` BIGINT NULL,
  `grade_level_id` BIGINT NULL,
  `section` VARCHAR(20) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`section_id`),
  INDEX `fk_grade_class_id_idx` (`grade_class_id` ASC),
  INDEX `fk_grade_level_id_idx` (`grade_level_id` ASC),
  CONSTRAINT `fk_grade_class_id`
    FOREIGN KEY (`grade_class_id`)
    REFERENCES `edulink1_LPriority`.`grade_classes` (`grade_class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_level_id`
    FOREIGN KEY (`grade_level_id`)
    REFERENCES `edulink1_LPriority`.`grade_level` (`grade_level_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`class_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`class_status` (
  `cs_id` BIGINT NOT NULL AUTO_INCREMENT,
  `section_id` BIGINT NOT NULL DEFAULT '0',
  `teacher_id` BIGINT NOT NULL DEFAULT '0',
  `avail_status` VARCHAR(45) NULL,
  `description` VARCHAR(45) NOT NULL DEFAULT '',
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `editing_status` VARCHAR(45) NULL,
  PRIMARY KEY (`cs_id`),
  INDEX `FK_class_status_3` (`teacher_id` ASC),
  INDEX `fk_section_id_idx` (`section_id` ASC),
  CONSTRAINT `fk_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `edulink1_LPriority`.`section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `edulink1_LPriority`.`teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`assign_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`assign_activity` (
  `assign_activity_id` BIGINT NOT NULL AUTO_INCREMENT,
  `activity_id` BIGINT NULL DEFAULT NULL,
  `assigned_date` DATE NULL DEFAULT NULL,
  `cs_id` BIGINT NOT NULL,
  PRIMARY KEY (`assign_activity_id`),
  INDEX `FK_assign_activity_2` (`activity_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_activity_id`
    FOREIGN KEY (`activity_id`)
    REFERENCES `edulink1_LPriority`.`activity` (`activity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`assign_lessons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`assign_lessons` (
  `assign_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NULL,
  `lesson_id` BIGINT NULL,
  `assigned_date` DATE NULL DEFAULT NULL,
  `due_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`assign_id`),
  UNIQUE INDEX `assign_id` (`assign_id` ASC),
  INDEX `fk_lesson_id_idx` (`lesson_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_assign_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `edulink1_LPriority`.`lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assign_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`assignment_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`assignment_type` (
  `assignment_type_id` BIGINT NOT NULL AUTO_INCREMENT,
  `assignment_type` VARCHAR(300) NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`assignment_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`assignment` (
  `assignment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NULL,
  `lesson_id` BIGINT NOT NULL,
  `assignment_type_id` BIGINT NULL DEFAULT '0',
  `title` VARCHAR(45) NOT NULL DEFAULT '',
  `objective` VARCHAR(100) NOT NULL DEFAULT '',
  `instructions` VARCHAR(100) NOT NULL DEFAULT '',
  `date_assigned` DATE NOT NULL,
  `date_due` DATE NOT NULL,
  `created_by` BIGINT NULL,
  `used_for` VARCHAR(30) NULL DEFAULT NULL,
  `benchmark_id` BIGINT NULL,
  `performance_group_id` BIGINT NULL,
  PRIMARY KEY (`assignment_id`),
  INDEX `fk_lesson_id_idx` (`lesson_id` ASC),
  INDEX `fk_assignment_type_id_idx` (`assignment_type_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_asngt_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_asngt_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `edulink1_LPriority`.`lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assignment_type_id`
    FOREIGN KEY (`assignment_type_id`)
    REFERENCES `edulink1_LPriority`.`assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`periods`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`periods` (
  `period_id` BIGINT NOT NULL AUTO_INCREMENT,
  `period_name` VARCHAR(20) NOT NULL DEFAULT '',
  `start_time` TIME NOT NULL DEFAULT '00:00:00',
  `end_time` TIME NOT NULL DEFAULT '00:00:00',
  `grade_id` BIGINT NULL,
  PRIMARY KEY (`period_id`),
  INDEX `fk_grade_id_idx` (`grade_id` ASC),
  CONSTRAINT `fk_periods_grade_id`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_LPriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`homeroom_classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`homeroom_classes` (
  `homeroom_id` BIGINT NOT NULL AUTO_INCREMENT,
  `section_id` BIGINT NULL,
  `teacher_id` BIGINT NOT NULL DEFAULT '0',
  `period_id` BIGINT NULL,
  PRIMARY KEY (`homeroom_id`),
  INDEX `fk_section_id_idx` (`section_id` ASC),
  INDEX `fk_teacher_id_idx` (`teacher_id` ASC),
  INDEX `fk_period_id_idx` (`period_id` ASC),
  CONSTRAINT `fk_hrc_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `edulink1_LPriority`.`section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hrc_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `edulink1_LPriority`.`teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hrc_period_id`
    FOREIGN KEY (`period_id`)
    REFERENCES `edulink1_LPriority`.`periods` (`period_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`student` (
  `student_id` BIGINT NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT NOT NULL DEFAULT '0',
  `grade_id` BIGINT NOT NULL DEFAULT '0',
  `gender` VARCHAR(45) NOT NULL DEFAULT '',
  `dob` DATE NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `homeroom_id` BIGINT NULL DEFAULT '0',
  PRIMARY KEY (`student_id`),
  INDEX `fk_stud_reg_id_idx` (`reg_id` ASC),
  INDEX `fk_homeroom_id_idx` (`homeroom_id` ASC),
  CONSTRAINT `fk_stud_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_LPriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_homeroom_id`
    FOREIGN KEY (`homeroom_id`)
    REFERENCES `edulink1_LPriority`.`homeroom_classes` (`homeroom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`student_assignment_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`student_assignment_status` (
  `student_assignment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `assignment_id` BIGINT NOT NULL,
  `student_id` BIGINT NOT NULL DEFAULT '0',
  `status` VARCHAR(20) NOT NULL DEFAULT '',
  `submitdate` DATE NULL DEFAULT '9999-12-31',
  `percentage` FLOAT NULL DEFAULT NULL,
  `graded_status` VARCHAR(30) NULL DEFAULT 'Not Graded',
  `benchmark_assignment_id` INT(11) NOT NULL DEFAULT '0',
  `retest_id` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`student_assignment_id`),
  INDEX `fk_assignment_id_idx` (`assignment_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  CONSTRAINT `fk_assignment_id`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `edulink1_LPriority`.`assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_LPriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`assigned_tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`assigned_tasks` (
  `assigned_task_id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT NOT NULL,
  `performance_task_id` BIGINT NULL,
  `filepath` VARCHAR(100) NULL DEFAULT NULL,
  `writing` VARCHAR(2000) NULL DEFAULT NULL,
  `self_assessment_score` INT(11) NULL DEFAULT NULL,
  `teacher_comments` VARCHAR(400) NULL DEFAULT NULL,
  `teacher_score` INT(11) NULL DEFAULT NULL,
  INDEX `fk_student_assignment_id_idx` (`student_assignment_id` ASC),
  PRIMARY KEY (`assigned_task_id`),
  CONSTRAINT `fk_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `edulink1_LPriority`.`student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`sub_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`sub_questions` (
  `sub_question_id` BIGINT NOT NULL AUTO_INCREMENT,
  `sub_question` VARCHAR(5000) NULL DEFAULT NULL,
  PRIMARY KEY (`sub_question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`questions` (
  `question_id` BIGINT NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT NULL,
  `assignment_type_id` BIGINT NULL DEFAULT NULL,
  `question` VARCHAR(500) NULL DEFAULT NULL,
  `answer` VARCHAR(50) NULL,
  `option1` VARCHAR(50) NULL DEFAULT NULL,
  `option2` VARCHAR(50) NULL DEFAULT NULL,
  `option3` VARCHAR(50) NULL DEFAULT NULL,
  `option4` VARCHAR(50) NULL DEFAULT NULL,
  `option5` VARCHAR(50) NULL DEFAULT NULL,
  `used_for` VARCHAR(20) NOT NULL,
  `created_by` INT(11) NULL,
  `sub_question_id` BIGINT NOT NULL DEFAULT '0',
  `b_textdirections` VARCHAR(200) NULL,
  `b_voicedirectionspath` VARCHAR(100) NULL,
  `b_retelldirectionspath` VARCHAR(30) NULL,
  `b_title` VARCHAR(100) NULL,
  `b_grade_level` VARCHAR(20) NULL,
  `pt_name` VARCHAR(100) NULL,
  `pt_subject_area` VARCHAR(100) NULL,
  `pt_directions` VARCHAR(500) NULL,
  PRIMARY KEY (`question_id`),
  INDEX `lesson_id_idx` (`lesson_id` ASC),
  INDEX `sub_question_id_idx` (`sub_question_id` ASC),
  CONSTRAINT `lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `edulink1_LPriority`.`lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `assignment_type_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `edulink1_LPriority`.`assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sub_question_id`
    FOREIGN KEY (`sub_question_id`)
    REFERENCES `edulink1_LPriority`.`sub_questions` (`sub_question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`quality_of_response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`quality_of_response` (
  `qor_id` BIGINT NOT NULL AUTO_INCREMENT,
  `response` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`qor_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`assignment_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`assignment_questions` (
  `assignment_questions_id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT NULL,
  `question_id` BIGINT NULL DEFAULT NULL,
  `answer` VARCHAR(1500) NULL DEFAULT NULL,
  `max_marks` INT(11) NULL DEFAULT NULL,
  `sec_marks` INT(11) NULL DEFAULT NULL,
  `teacher_comment` VARCHAR(200) NULL DEFAULT NULL,
  `b_audio_path` VARCHAR(30) NULL,
  `b_fluencymarks` INT(11) NULL,
  `b_retell_path` INT(11) NULL,
  `b_quality_of_response_id` BIGINT NULL,
  `b_retellpath` VARCHAR(45) NULL,
  `b_count_of_errors` INT(11) NULL,
  PRIMARY KEY (`assignment_questions_id`),
  INDEX `fk_student_assignment_id_idx` (`student_assignment_id` ASC),
  INDEX `fk_question_id_idx` (`question_id` ASC),
  INDEX `fk_quality_of_response_id_idx` (`b_quality_of_response_id` ASC),
  CONSTRAINT `fk_aq_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `edulink1_LPriority`.`student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aq_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `edulink1_LPriority`.`questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aq_quality_of_response_id`
    FOREIGN KEY (`b_quality_of_response_id`)
    REFERENCES `edulink1_LPriority`.`quality_of_response` (`qor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`attendance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`attendance` (
  `attendance_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NOT NULL DEFAULT '0',
  `date` DATE NOT NULL,
  `student_id` BIGINT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `FK_attendance_1` (`cs_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  PRIMARY KEY (`attendance_id`),
  CONSTRAINT `fk_atd_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_LPriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_atd_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`citizenship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`citizenship` (
  `Citizenship_id` BIGINT NOT NULL AUTO_INCREMENT,
  `Citizenship_name` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`Citizenship_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`class_actual_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`class_actual_schedule` (
  `cas_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NOT NULL DEFAULT '0',
  `day_id` BIGINT NOT NULL DEFAULT '0',
  `period_id` BIGINT NULL DEFAULT NULL,
  INDEX `cs_id` (`cs_id` ASC, `day_id` ASC),
  INDEX `cs_id_2` (`cs_id` ASC, `day_id` ASC),
  INDEX `cs_id_3` (`cs_id` ASC),
  INDEX `day_id` (`day_id` ASC),
  INDEX `fk_period_id_idx` (`period_id` ASC),
  PRIMARY KEY (`cas_id`),
  CONSTRAINT `fk_cas_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cas_period_id`
    FOREIGN KEY (`period_id`)
    REFERENCES `edulink1_LPriority`.`periods` (`period_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`comments` (
  `comment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`comment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`gradeevents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`gradeevents` (
  `event_id` BIGINT NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`event_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`compositechart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`compositechart` (
  `composite_chart_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NOT NULL,
  `grade_event_id` BIGINT NOT NULL,
  `assignment_type_id` BIGINT NOT NULL,
  `overallgrade` FLOAT NOT NULL DEFAULT '0',
  `nooftests` INT(11) NOT NULL,
  `pointspertest` INT(11) NOT NULL,
  PRIMARY KEY (`composite_chart_id`),
  INDEX `fk_grade_event_id_idx` (`grade_event_id` ASC),
  INDEX `fk_assignment_type_id_idx` (`assignment_type_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_chrt_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chrt_grade_event_id`
    FOREIGN KEY (`grade_event_id`)
    REFERENCES `edulink1_LPriority`.`gradeevents` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chrt_assignment_type_id`
    FOREIGN KEY (`assignment_type_id`)
    REFERENCES `edulink1_LPriority`.`assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`folders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`folders` (
  `folder_id` BIGINT NOT NULL AUTO_INCREMENT,
  `foldername` VARCHAR(40) NOT NULL,
  `foldertype` VARCHAR(40) NULL DEFAULT NULL,
  `createddate` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`folder_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`days` (
  `day_id` BIGINT NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(10) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`day_id`),
  UNIQUE INDEX `UK_days_1` USING BTREE (`day` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`events` (
  `event_id` BIGINT NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(45) NULL,
  `description` VARCHAR(70) NULL,
  `announcement_date` DATE NULL DEFAULT NULL,
  `schedule_date` DATE NULL,
  `last_date` DATE NULL DEFAULT NULL,
  `school_id` BIGINT NULL DEFAULT NULL,
  `venue` VARCHAR(30) NULL DEFAULT NULL,
  `event_time` VARCHAR(30) NULL DEFAULT NULL,
  `contact_person` VARCHAR(30) NULL DEFAULT NULL,
  `create_date` DATE NULL DEFAULT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_id`),
  UNIQUE INDEX `eventId` (`event_id` ASC),
  INDEX `fk_events_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_events_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `edulink1_LPriority`.`school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`performancetask_groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`performancetask_groups` (
  `performance_group_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NULL,
  `group_name` VARCHAR(20) NULL DEFAULT NULL,
  `MaxNo_of_students` INT(11) NULL DEFAULT NULL,
  `MinNo_of_students` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`performance_group_id`),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_ptg_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`groupassigned_tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`groupassigned_tasks` (
  `gat_id` BIGINT NOT NULL AUTO_INCREMENT,
  `assignment_id` BIGINT NOT NULL,
  `groupId` BIGINT NULL,
  `performance_task_id` BIGINT NULL,
  `filepath` VARCHAR(100) NULL DEFAULT NULL,
  `contents` VARCHAR(2000) NULL DEFAULT NULL,
  `self_assessment_score` INT(11) NULL DEFAULT NULL,
  `teacher_comments` VARCHAR(400) NULL DEFAULT NULL,
  `teacher_score` INT(11) NULL DEFAULT NULL,
  INDEX `fk_assignment_id_idx` (`assignment_id` ASC),
  INDEX `fk_group_id_idx` (`groupId` ASC),
  PRIMARY KEY (`gat_id`),
  CONSTRAINT `fk_gat_assignment_id`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `edulink1_LPriority`.`assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gat_group_id`
    FOREIGN KEY (`groupId`)
    REFERENCES `edulink1_LPriority`.`performancetask_groups` (`performance_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`interest` (
  `interest_id` BIGINT NOT NULL AUTO_INCREMENT,
  `interest` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`interest_id`),
  UNIQUE INDEX `UK_interest_1` USING BTREE (`interest` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`invitations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`invitations` (
  `invitation_id` BIGINT NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT NOT NULL DEFAULT '0',
  `invitee_email` VARCHAR(45) NOT NULL DEFAULT '',
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `type_of_person` VARCHAR(45) NOT NULL DEFAULT '',
  `message` VARCHAR(150) NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `FK_invitations_1` (`reg_id` ASC),
  PRIMARY KEY (`invitation_id`),
  CONSTRAINT `fk_inv_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_LPriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`joinsentencenbenchmark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`joinsentencenbenchmark` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NOT NULL,
  `benchmark_id` INT(11) NOT NULL,
  `benchmark_assignment_id` BIGINT NOT NULL,
  `sentencestructure_assignment_id` BIGINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_section_id_idx` (`cs_id` ASC),
  INDEX `fk_sentencestructure_assignment_id_idx` (`sentencestructure_assignment_id` ASC),
  INDEX `fk_benchmark_assignment_id_idx` (`benchmark_assignment_id` ASC),
  CONSTRAINT `fk_sentencestructure_assignment_id`
    FOREIGN KEY (`sentencestructure_assignment_id`)
    REFERENCES `edulink1_LPriority`.`assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jsb_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_benchmark_assignment_id`
    FOREIGN KEY (`benchmark_assignment_id`)
    REFERENCES `edulink1_LPriority`.`assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`lesson_paths`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`lesson_paths` (
  `lesson_path_id` BIGINT NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT NOT NULL,
  `lesson_path` VARCHAR(300) NOT NULL,
  `uploaded_by` BIGINT NOT NULL,
  PRIMARY KEY (`lesson_path_id`),
  INDEX `fk_lesson_id_idx` (`lesson_id` ASC),
  CONSTRAINT `fk_paths_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `edulink1_LPriority`.`lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`minutes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`minutes` (
  `min_id` BIGINT NOT NULL AUTO_INCREMENT,
  `minute` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`min_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`rti_groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`rti_groups` (
  `rti_group_id` BIGINT NOT NULL AUTO_INCREMENT,
  `rti_group_name` VARCHAR(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`rti_group_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`register_for_class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`register_for_class` (
  `student_id` BIGINT NOT NULL AUTO_INCREMENT,
  `class_id` BIGINT NOT NULL,
  `cs_id` BIGINT NULL,
  `section_id` BIGINT NOT NULL DEFAULT '0',
  `rti_group_id` BIGINT NULL DEFAULT '0',
  `status` VARCHAR(20) NULL,
  `desktop_status` VARCHAR(20) NULL,
  `class_status` VARCHAR(30) NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` DATETIME NULL,
  `performance_group_id` BIGINT NULL,
  PRIMARY KEY (`student_id`, `class_id`),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  INDEX `fk_rti_group_id_idx` (`rti_group_id` ASC),
  INDEX `fk_section_id_idx` (`section_id` ASC),
  INDEX `fk_performance_group_id_idx` (`performance_group_id` ASC),
  INDEX `fk_rfc_class_id_idx` (`class_id` ASC),
  CONSTRAINT `fk_rfc_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_LPriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `edulink1_LPriority`.`section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_rti_group_id`
    FOREIGN KEY (`rti_group_id`)
    REFERENCES `edulink1_LPriority`.`rti_groups` (`rti_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_performance_group_id`
    FOREIGN KEY (`performance_group_id`)
    REFERENCES `edulink1_LPriority`.`performancetask_groups` (`performance_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `edulink1_LPriority`.`class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`report` (
  `report_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NULL,
  `student_id` BIGINT NOT NULL,
  `homework_perc` FLOAT NOT NULL,
  `assignment_perc` FLOAT NOT NULL,
  `acedamic_grade_id` BIGINT NOT NULL,
  `Citizenship_id` BIGINT NOT NULL,
  `comment_id` BIGINT NOT NULL,
  `presentdays` INT(11) NOT NULL,
  `absentdays` INT(11) NOT NULL,
  `excusedabsentdays` INT(11) NOT NULL,
  `tardydays` INT(11) NOT NULL,
  `excusedtardydays` INT(11) NOT NULL,
  `release_date` DATE NULL DEFAULT NULL,
  `parent_comments` VARCHAR(200) NULL DEFAULT NULL,
  `parent_comments_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  INDEX `fk_comment_id_idx` (`comment_id` ASC),
  INDEX `fk_citizenship_id_idx` (`Citizenship_id` ASC),
  INDEX `fk_academic_grade_id_idx` (`acedamic_grade_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_rpt_comment_id`
    FOREIGN KEY (`comment_id`)
    REFERENCES `edulink1_LPriority`.`comments` (`comment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_citizenship_id`
    FOREIGN KEY (`Citizenship_id`)
    REFERENCES `edulink1_LPriority`.`citizenship` (`Citizenship_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_academic_grade_id`
    FOREIGN KEY (`acedamic_grade_id`)
    REFERENCES `edulink1_LPriority`.`academic_grades` (`acedamic_grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_LPriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`benchmark_results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`benchmark_results` (
  `benchmark_results_id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT NULL DEFAULT '0',
  `median_fulency_core` INT(11) NULL DEFAULT '0',
  `median_retell_score` INT(11) NULL DEFAULT '0',
  `sentence_structure_core` FLOAT(10,2) NULL DEFAULT NULL,
  `quality_of_response_id` BIGINT NULL DEFAULT '0',
  `accuracy` FLOAT NULL DEFAULT NULL,
  `teacher_notes` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`benchmark_results_id`),
  INDEX `fk_quality_of_response_id_idx` (`quality_of_response_id` ASC),
  INDEX `fk_student_assignment_id_idx` (`student_assignment_id` ASC),
  CONSTRAINT `fk_br_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `edulink1_LPriority`.`student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_br_quality_of_response_id`
    FOREIGN KEY (`quality_of_response_id`)
    REFERENCES `edulink1_LPriority`.`quality_of_response` (`qor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`rubric_scalings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`rubric_scalings` (
  `rubric_scaling_id` BIGINT NOT NULL AUTO_INCREMENT,
  `rubric_scaling` INT(5) NOT NULL,
  PRIMARY KEY (`rubric_scaling_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`rubric_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`rubric_types` (
  `rubric_type_id` BIGINT NOT NULL AUTO_INCREMENT,
  `rubric_type` VARCHAR(50) NULL,
  PRIMARY KEY (`rubric_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`rubric`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`rubric` (
  `rubric_id` BIGINT NOT NULL AUTO_INCREMENT,
  `dimension1` VARCHAR(1000) NULL,
  `dimension2` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension3` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension4` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension1_total` INT NULL DEFAULT NULL,
  `dimension2_total` INT NULL,
  `dimension3_total` INT NULL,
  `dimension4_total` INT NULL,
  `rubric_scaling_id` BIGINT NULL,
  `rubric_type_id` BIGINT NULL,
  `created_by` BIGINT NULL DEFAULT NULL,
  `perf_task_quest_id` BIGINT NULL,
  `student_assignment_id` BIGINT NULL,
  `performance_group_id` BIGINT NULL,
  PRIMARY KEY (`rubric_id`),
  INDEX `fk_rubric_scaling_id_idx` (`rubric_scaling_id` ASC),
  INDEX `fk_rubric_type_id_idx` (`rubric_type_id` ASC),
  INDEX `fk_rubric_performance_grp_id_idx` (`performance_group_id` ASC),
  INDEX `fk_rubric_stud_assign_id_idx` (`student_assignment_id` ASC),
  INDEX `fk_rubric_perf_task_ques_id_idx` (`perf_task_quest_id` ASC),
  CONSTRAINT `fk_rubric_scaling_id`
    FOREIGN KEY (`rubric_scaling_id`)
    REFERENCES `edulink1_LPriority`.`rubric_scalings` (`rubric_scaling_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_type_id`
    FOREIGN KEY (`rubric_type_id`)
    REFERENCES `edulink1_LPriority`.`rubric_types` (`rubric_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_performance_grp_id`
    FOREIGN KEY (`performance_group_id`)
    REFERENCES `edulink1_LPriority`.`performancetask_groups` (`performance_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_stud_assign_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `edulink1_LPriority`.`student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_perf_task_ques_id`
    FOREIGN KEY (`perf_task_quest_id`)
    REFERENCES `edulink1_LPriority`.`questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`rubric_score`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`rubric_score` (
  `rubric_score_id` BIGINT NOT NULL AUTO_INCREMENT,
  `rubric_score` INT NULL,
  PRIMARY KEY (`rubric_score_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`school_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`school_schedule` (
  `school_schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
  `school_id` BIGINT NOT NULL,
  `no_of_days` INT(11) NOT NULL DEFAULT '5',
  `no_of_class_periods` INT(11) NOT NULL,
  `period_range` INT(11) NOT NULL,
  `passing_time` INT(11) NOT NULL,
  `home_room_time` INT(11) NOT NULL,
  `school_start_time` TIME NOT NULL,
  `school_end_time` TIME NOT NULL,
  `no_of_teachers` INT(11) NOT NULL,
  `no_of_sections` INT(11) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`school_schedule_id`),
  INDEX `fk_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_sched_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `edulink1_LPriority`.`school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`school_days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`school_days` (
  `school_days_id` BIGINT NOT NULL AUTO_INCREMENT,
  `day_id` BIGINT NULL,
  `school_schedule_id` BIGINT NULL DEFAULT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`school_days_id`),
  INDEX `fk_day_id_idx` (`day_id` ASC),
  INDEX `fk_school_schedule_id_idx` (`school_schedule_id` ASC),
  CONSTRAINT `fk_school_schedule_id`
    FOREIGN KEY (`school_schedule_id`)
    REFERENCES `edulink1_LPriority`.`school_schedule` (`school_schedule_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_day_id`
    FOREIGN KEY (`day_id`)
    REFERENCES `edulink1_LPriority`.`days` (`day_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`security_question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`security_question` (
  `security_question_id` BIGINT NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY USING BTREE (`security_question_id`),
  UNIQUE INDEX `UK_sec_question_1` USING BTREE (`question` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`security`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`security` (
  `security_id` BIGINT NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT NOT NULL DEFAULT '0',
  `sec_question_id` BIGINT NULL DEFAULT '0',
  `answer` VARCHAR(45) NULL DEFAULT '',
  `verification_code` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `UK_security_1` USING BTREE (`reg_id` ASC),
  INDEX `FK_security_2` (`sec_question_id` ASC),
  PRIMARY KEY (`security_id`),
  CONSTRAINT `fk_sec_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_LPriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sec_ques_id`
    FOREIGN KEY (`sec_question_id`)
    REFERENCES `edulink1_LPriority`.`security_question` (`security_question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`student_composite_chart_values`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`student_composite_chart_values` (
  `cs_id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT NOT NULL,
  `assign_id` VARCHAR(30) NOT NULL DEFAULT '0',
  `score` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cs_id`, `student_id`, `assign_id`),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  CONSTRAINT `fk_sccv_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_LPriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sccv_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_LPriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`studentgroupstatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`studentgroupstatus` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `performance_group_student_id` BIGINT NULL,
  `assignment_id` BIGINT NULL DEFAULT NULL,
  `login_status` VARCHAR(20) NULL DEFAULT NULL,
  `submit_status` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assignment_id_idx` (`assignment_id` ASC),
  CONSTRAINT `fk_sgs_assignment_id`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `edulink1_LPriority`.`assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`sub_interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`sub_interest` (
  `sub_interest_id` BIGINT NOT NULL AUTO_INCREMENT,
  `sub_interest` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sub_interest_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`teacher_subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`teacher_subjects` (
  `teacher_subject_id` BIGINT NOT NULL AUTO_INCREMENT,
  `teacher_id` BIGINT NOT NULL DEFAULT '0',
  `grade_id` BIGINT NOT NULL DEFAULT '0',
  `class_id` BIGINT NOT NULL DEFAULT '0',
  `create_date` DATE NOT NULL,
  `change_date` DATETIME NOT NULL,
  `grade_level_id` BIGINT NOT NULL,
  `no_sections_per_day` INT(11) NOT NULL,
  `no_sections_per_week` INT(11) NOT NULL,
  `other_subjects` VARCHAR(25) NULL,
  `requested_class_id` BIGINT NULL,
  `desktop_status` VARCHAR(20) NULL,
  `requested_class_status` VARCHAR(20) NULL,
  UNIQUE INDEX `UK_teacher_subjects_1` USING BTREE (`teacher_id` ASC, `grade_id` ASC, `class_id` ASC),
  INDEX `FK_teacher_subjects_2` (`grade_id` ASC),
  INDEX `FK_teacher_subjects_3` (`class_id` ASC),
  PRIMARY KEY (`teacher_subject_id`),
  CONSTRAINT `fk_subj_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `edulink1_LPriority`.`teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`user_interests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`user_interests` (
  `user_interests_id` BIGINT NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT NOT NULL DEFAULT '0',
  `interest_id` BIGINT NOT NULL DEFAULT '0',
  `sub_interest_id` BIGINT NULL DEFAULT '0',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `other_user_interest` VARCHAR(45) NULL,
  UNIQUE INDEX `UK_user_interests_1` USING BTREE (`reg_id` ASC, `interest_id` ASC, `sub_interest_id` ASC),
  INDEX `FK_user_interests_1` (`interest_id` ASC),
  INDEX `FK_user_interests_2` (`sub_interest_id` ASC),
  PRIMARY KEY (`user_interests_id`),
  CONSTRAINT `fk_inter_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_LPriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interest_id`
    FOREIGN KEY (`interest_id`)
    REFERENCES `edulink1_LPriority`.`interest` (`interest_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subinterest_id`
    FOREIGN KEY (`sub_interest_id`)
    REFERENCES `edulink1_LPriority`.`sub_interest` (`sub_interest_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `edulink1_LPriority`.`rubric_values`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_LPriority`.`rubric_values` (
  `rubric_values_id` BIGINT NOT NULL AUTO_INCREMENT,
  `rubric_score_id` BIGINT NOT NULL,
  `rubric_id` BIGINT NOT NULL,
  `dimension1_value` VARCHAR(45) NOT NULL,
  `dimension2_value` VARCHAR(45) NOT NULL,
  `dimension3_value` VARCHAR(45) NOT NULL,
  `dimension4_value` VARCHAR(45) NOT NULL,
  `total_score` INT NULL,
  PRIMARY KEY (`rubric_values_id`),
  INDEX `fk_rubric_score_id_idx` (`rubric_score_id` ASC),
  INDEX `fk_rubric_id_idx` (`rubric_id` ASC),
  CONSTRAINT `fk_rubric_score_id`
    FOREIGN KEY (`rubric_score_id`)
    REFERENCES `edulink1_LPriority`.`rubric_score` (`rubric_score_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_id`
    FOREIGN KEY (`rubric_id`)
    REFERENCES `edulink1_LPriority`.`rubric` (`rubric_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
