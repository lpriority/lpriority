SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema edulink1_lpriority
-- -----------------------------------------------------
Drop Database `edulink1_lpriority` ;
CREATE SCHEMA IF NOT EXISTS `edulink1_lpriority` DEFAULT CHARACTER SET latin1 ;
USE `edulink1_lpriority` ;

-- -----------------------------------------------------
-- Table `academic_performance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `academic_performance` (
  `academic_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `academic_level` VARCHAR(20) NOT NULL,
  `academic_grade` VARCHAR(1) NOT NULL,
  `academic_description` VARCHAR(260) NOT NULL,
  PRIMARY KEY (`academic_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `academic_grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `academic_grades` (
  `acedamic_grade_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `acedamic_grade_name` VARCHAR(20) NOT NULL,
  `score_from` INT(11) NOT NULL,
  `score_to` INT(11) NOT NULL,
  `academic_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`acedamic_grade_id`),
  INDEX `fk_academic_id_idx` (`academic_id` ASC),
  UNIQUE INDEX `acedamic_grade_name_UNIQUE` (`acedamic_grade_name` ASC),
  CONSTRAINT `fk_academic_id`
    FOREIGN KEY (`academic_id`)
    REFERENCES `academic_performance` (`academic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `country` (
  `country_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NOT NULL,
  `continent` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE INDEX `country_UNIQUE` (`country` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `states`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `states` (
  `state_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `country_id` BIGINT(20) NULL DEFAULT NULL,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`state_id`),
  INDEX `subregion_region_id` (`country_id` ASC),
  CONSTRAINT `states_ibfk_1`
    FOREIGN KEY (`country_id`)
    REFERENCES `country` (`country_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `school_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_type` (
  `school_type_id` BIGINT NOT NULL AUTO_INCREMENT,
  `school_type_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`school_type_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school_level`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_level` (
  `school_level_id` BIGINT NOT NULL AUTO_INCREMENT,
  `school_level_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`school_level_id`),
  UNIQUE INDEX `school_level_name_UNIQUE` (`school_level_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school` (
  `school_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `school_name` VARCHAR(45) NOT NULL DEFAULT '',
  `school_type_id` BIGINT NOT NULL,
  `school_level_id` BIGINT NOT NULL,
  `no_of_students` INT(11) NOT NULL DEFAULT '0',
  `city` VARCHAR(45) NOT NULL DEFAULT '',
  `state_id` BIGINT(20) NOT NULL,
  `phone_number` VARCHAR(45) NULL DEFAULT NULL,
  `fax_number` VARCHAR(45) NULL DEFAULT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `registration_date` DATE NOT NULL,
  `logo_link` VARCHAR(50) NULL DEFAULT NULL,
  `class_strength` INT(11) NULL DEFAULT NULL,
  `leveling` VARCHAR(10) NULL DEFAULT NULL,
  `gender_equity` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`school_id`),
  UNIQUE INDEX `UK_school_1` (`school_name` ASC, `school_type_id` ASC, `school_level_id` ASC, `city` ASC, `state_id` ASC),
  INDEX `fk_school_state_id_idx` (`state_id` ASC),
  INDEX `fk_school_type_id_idx` (`school_type_id` ASC),
  INDEX `fk_school_level_id_idx` (`school_level_id` ASC),
  CONSTRAINT `fk_school_state_id`
    FOREIGN KEY (`state_id`)
    REFERENCES `states` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_type_id`
    FOREIGN KEY (`school_type_id`)
    REFERENCES `school_type` (`school_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_level_id`
    FOREIGN KEY (`school_level_id`)
    REFERENCES `school_level` (`school_level_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `class` (
  `class_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `class_name` VARCHAR(45) NOT NULL DEFAULT '',
  `school_id` BIGINT(20) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`class_id`),
  INDEX `fk_school_id_idx` (`school_id` ASC),
  UNIQUE INDEX `class_name_UNIQUE` (`class_name` ASC, `school_id` ASC),
  CONSTRAINT `fk_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `master_grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `master_grades` (
  `master_grades_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `grade_name` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`master_grades_id`),
  UNIQUE INDEX `UK_grade_1` USING BTREE (`grade_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grade` (
  `grade_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `school_id` BIGINT(20) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `master_grades_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  INDEX `fk_master_grades_id_idx` (`master_grades_id` ASC),
  CONSTRAINT `fk_master_grades_id`
    FOREIGN KEY (`master_grades_id`)
    REFERENCES `master_grades` (`master_grades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `unit` (
  `unit_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `unit_no` BIGINT(20) NULL DEFAULT NULL,
  `unit_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`unit_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lesson` (
  `lesson_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `grade_id` BIGINT(20) NULL DEFAULT NULL,
  `class_id` BIGINT(20) NULL DEFAULT NULL,
  `lesson_no` INT(11) NULL DEFAULT NULL,
  `lesson_name` VARCHAR(45) NOT NULL DEFAULT '',
  `lesson_desc` VARCHAR(300) NULL DEFAULT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` MEDIUMTEXT NULL DEFAULT NULL,
  `unit_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`lesson_id`),
  INDEX `fk_lesson_class1_idx` (`class_id` ASC),
  INDEX `fk_lesson_grade_idx` (`grade_id` ASC),
  INDEX `fk_unit_id_idx` (`unit_id` ASC),
  CONSTRAINT `fk_lesson_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lesson_grade`
    FOREIGN KEY (`grade_id`)
    REFERENCES `grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_id`
    FOREIGN KEY (`unit_id`)
    REFERENCES `unit` (`unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `activity` (
  `activity_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT(20) NOT NULL,
  `activity_desc` VARCHAR(1000) NOT NULL,
  `created_by` BIGINT(20) NOT NULL,
  PRIMARY KEY (`activity_id`),
  INDEX `FK_activity_2` (`lesson_id` ASC),
  CONSTRAINT `fk_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `address` (
  `address_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(45) NOT NULL DEFAULT '',
  `state_id` BIGINT(20) NULL DEFAULT NULL,
  `city` VARCHAR(30) NULL DEFAULT NULL,
  `zipcode` INT(11) NOT NULL DEFAULT '0',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`),
  INDEX `fk_state_id_idx` (`state_id` ASC),
  CONSTRAINT `fk_state_id`
    FOREIGN KEY (`state_id`)
    REFERENCES `states` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teacher_performances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teacher_performances` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `performance` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `performance_UNIQUE` (`performance` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_typeid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_type` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_typeid`),
  UNIQUE INDEX `UK_user_1` USING BTREE (`user_type` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user_registration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_registration` (
  `reg_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(5) NULL DEFAULT NULL,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `user_typeid` BIGINT(20) NOT NULL DEFAULT '0',
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `qualification` VARCHAR(200) NULL DEFAULT NULL,
  `phonenumber` VARCHAR(45) NULL,
  `email_id` VARCHAR(45) NOT NULL,
  `parent_reg_id` BIGINT(20) NULL,
  `address_id` BIGINT(20) NULL DEFAULT NULL,
  `school_id` BIGINT(20) NULL DEFAULT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `experience` VARCHAR(250) NULL DEFAULT NULL,
  `subjects` VARCHAR(250) NULL DEFAULT NULL,
  `interestareas` VARCHAR(250) NULL DEFAULT NULL,
  `projects` VARCHAR(250) NULL DEFAULT NULL,
  `education` VARCHAR(250) NULL DEFAULT NULL,
  `contactinfo` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`reg_id`),
  UNIQUE INDEX `UK_registration_1` (`reg_id` ASC),
  INDEX `FK_registration_2` USING BTREE (`user_typeid` ASC),
  INDEX `FK_registration_1` USING BTREE (`reg_id` ASC),
  INDEX `fk_address_idx` (`address_id` ASC),
  INDEX `school_id` (`school_id` ASC),
  CONSTRAINT `fk_address`
    FOREIGN KEY (`address_id`)
    REFERENCES `address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usertype`
    FOREIGN KEY (`user_typeid`)
    REFERENCES `user` (`user_typeid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_registration_ibfk_1`
    FOREIGN KEY (`school_id`)
    REFERENCES `school` (`school_id`),
  CONSTRAINT `user_registration_ibfk_2`
    FOREIGN KEY (`school_id`)
    REFERENCES `school` (`school_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teacher` (
  `teacher_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT(20) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`teacher_id`),
  INDEX `fk_reg_id_idx` (`reg_id` ASC),
  UNIQUE INDEX `reg_id_UNIQUE` (`reg_id` ASC),
  CONSTRAINT `fk_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `admin_teacher_reports`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `admin_teacher_reports` (
  `admin_teacher_report_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` BIGINT(20) NOT NULL DEFAULT '0',
  `date` DATE NOT NULL,
  `performance_id` BIGINT(20) NOT NULL,
  `choosen_option` VARCHAR(20) NOT NULL,
  `comments` VARCHAR(200) NOT NULL,
  `user_typeid` TINYINT(1) NOT NULL,
  PRIMARY KEY (`admin_teacher_report_id`),
  INDEX `performance_id_idx` (`performance_id` ASC),
  INDEX `teacher_id_idx` (`teacher_id` ASC),
  CONSTRAINT `performance_id`
    FOREIGN KEY (`performance_id`)
    REFERENCES `teacher_performances` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `announcements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `announcements` (
  `announcement_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `annoncement_name` VARCHAR(40) NOT NULL,
  `announce_description` VARCHAR(70) NOT NULL,
  `announce_date` DATE NOT NULL,
  `school_id` BIGINT(20) NOT NULL,
  `contact_person` VARCHAR(30) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`announcement_id`),
  UNIQUE INDEX `announceId` (`announcement_id` ASC),
  INDEX `fk_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_announce_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `grade_classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grade_classes` (
  `grade_class_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `grade_id` BIGINT(20) NOT NULL DEFAULT '0',
  `class_id` BIGINT(20) NOT NULL DEFAULT '0',
  `status` VARCHAR(20) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`grade_class_id`),
  INDEX `FK_grade_classes_2` (`class_id` ASC),
  INDEX `fk_grade_id_idx` (`grade_id` ASC),
  CONSTRAINT `fk_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_id`
    FOREIGN KEY (`grade_id`)
    REFERENCES `grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `grade_level`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grade_level` (
  `grade_level_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `grade_level_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`grade_level_id`),
  UNIQUE INDEX `grade_level_name_UNIQUE` (`grade_level_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `section` (
  `section_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `grade_class_id` BIGINT(20) NOT NULL,
  `grade_level_id` BIGINT(20) NOT NULL,
  `section` VARCHAR(20) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`section_id`),
  INDEX `fk_grade_class_id_idx` (`grade_class_id` ASC),
  INDEX `fk_grade_level_id_idx` (`grade_level_id` ASC),
  CONSTRAINT `fk_grade_class_id`
    FOREIGN KEY (`grade_class_id`)
    REFERENCES `grade_classes` (`grade_class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_level_id`
    FOREIGN KEY (`grade_level_id`)
    REFERENCES `grade_level` (`grade_level_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `class_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `class_status` (
  `cs_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `section_id` BIGINT(20) NOT NULL DEFAULT '0',
  `teacher_id` BIGINT(20) NOT NULL DEFAULT '0',
  `avail_status` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(45) NOT NULL DEFAULT '',
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `editing_status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`cs_id`),
  INDEX `FK_class_status_3` (`teacher_id` ASC),
  INDEX `fk_section_id_idx` (`section_id` ASC),
  CONSTRAINT `fk_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `assign_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assign_activity` (
  `assign_activity_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `activity_id` BIGINT(20) NOT NULL,
  `assigned_date` DATE NOT NULL,
  `cs_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`assign_activity_id`),
  INDEX `FK_assign_activity_2` (`activity_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_activity_id`
    FOREIGN KEY (`activity_id`)
    REFERENCES `activity` (`activity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `assign_lessons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assign_lessons` (
  `assign_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NOT NULL,
  `lesson_id` BIGINT(20) NOT NULL,
  `assigned_date` DATE NOT NULL,
  `due_date` DATE NOT NULL,
  PRIMARY KEY (`assign_id`),
  UNIQUE INDEX `assign_id` (`assign_id` ASC),
  INDEX `fk_lesson_id_idx` (`lesson_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_assign_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assign_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `assignment_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment_type` (
  `assignment_type_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `assignment_type` VARCHAR(50) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`assignment_type_id`),
  UNIQUE INDEX `assignment_type_UNIQUE` (`assignment_type` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment` (
  `assignment_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NULL DEFAULT NULL,
  `lesson_id` BIGINT(20) NOT NULL,
  `assignment_type_id` BIGINT(20) NULL DEFAULT '0',
  `title` VARCHAR(45) NOT NULL DEFAULT '',
  `objective` VARCHAR(100) NOT NULL DEFAULT '',
  `instructions` VARCHAR(100) NOT NULL DEFAULT '',
  `date_assigned` DATE NOT NULL,
  `date_due` DATE NOT NULL,
  `created_by` BIGINT(20) NULL DEFAULT NULL,
  `used_for` VARCHAR(30) NULL DEFAULT NULL,
  `benchmark_id` BIGINT(20) NULL DEFAULT NULL,
  `performance_group_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`assignment_id`),
  INDEX `fk_lesson_id_idx` (`lesson_id` ASC),
  INDEX `fk_assignment_type_id_idx` (`assignment_type_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_asngt_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_asngt_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assignment_type_id`
    FOREIGN KEY (`assignment_type_id`)
    REFERENCES `assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `periods`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periods` (
  `period_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `period_name` VARCHAR(20) NOT NULL DEFAULT '',
  `start_time` TIME NOT NULL DEFAULT '00:00:00',
  `end_time` TIME NOT NULL DEFAULT '00:00:00',
  `grade_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`period_id`),
  INDEX `fk_grade_id_idx` (`grade_id` ASC),
  CONSTRAINT `fk_periods_grade_id`
    FOREIGN KEY (`grade_id`)
    REFERENCES `grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `homeroom_classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeroom_classes` (
  `homeroom_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `section_id` BIGINT(20) NULL DEFAULT NULL,
  `teacher_id` BIGINT(20) NOT NULL DEFAULT '0',
  `period_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`homeroom_id`),
  INDEX `fk_section_id_idx` (`section_id` ASC),
  INDEX `fk_teacher_id_idx` (`teacher_id` ASC),
  INDEX `fk_period_id_idx` (`period_id` ASC),
  CONSTRAINT `fk_hrc_period_id`
    FOREIGN KEY (`period_id`)
    REFERENCES `periods` (`period_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hrc_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_hrc_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student` (
  `student_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT(20) NOT NULL DEFAULT '0',
  `grade_id` BIGINT(20) NOT NULL DEFAULT '0',
  `gender` VARCHAR(45) NOT NULL DEFAULT '',
  `dob` DATE NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `homeroom_id` BIGINT(20) NULL DEFAULT '0',
  PRIMARY KEY (`student_id`),
  INDEX `fk_stud_reg_id_idx` (`reg_id` ASC),
  INDEX `fk_homeroom_id_idx` (`homeroom_id` ASC),
  CONSTRAINT `fk_homeroom_id`
    FOREIGN KEY (`homeroom_id`)
    REFERENCES `homeroom_classes` (`homeroom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stud_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `student_assignment_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_assignment_status` (
  `student_assignment_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `assignment_id` BIGINT(20) NOT NULL,
  `student_id` BIGINT(20) NOT NULL DEFAULT '0',
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
    REFERENCES `assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `assigned_tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assigned_tasks` (
  `assigned_task_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT(20) NOT NULL,
  `performance_task_id` BIGINT(20) NULL DEFAULT NULL,
  `filepath` VARCHAR(100) NULL DEFAULT NULL,
  `writing` VARCHAR(2000) NULL DEFAULT NULL,
  `self_assessment_score` INT(11) NULL DEFAULT NULL,
  `teacher_comments` VARCHAR(400) NULL DEFAULT NULL,
  `teacher_score` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`assigned_task_id`),
  INDEX `fk_student_assignment_id_idx` (`student_assignment_id` ASC),
  CONSTRAINT `fk_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quality_of_response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quality_of_response` (
  `qor_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `response` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`qor_id`),
  UNIQUE INDEX `response_UNIQUE` (`response` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sub_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sub_questions` (
  `sub_question_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `sub_question` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`sub_question_id`),
  UNIQUE INDEX `sub_question_UNIQUE` (`sub_question` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `questions` (
  `question_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT(20) NOT NULL,
  `assignment_type_id` BIGINT(20) NOT NULL,
  `question` VARCHAR(500) NOT NULL,
  `answer` VARCHAR(50) NULL,
  `option1` VARCHAR(50) NULL DEFAULT NULL,
  `option2` VARCHAR(50) NULL DEFAULT NULL,
  `option3` VARCHAR(50) NULL DEFAULT NULL,
  `option4` VARCHAR(50) NULL DEFAULT NULL,
  `option5` VARCHAR(50) NULL DEFAULT NULL,
  `used_for` VARCHAR(20) NOT NULL,
  `created_by` INT(11) NOT NULL,
  `sub_question_id` BIGINT(20) NULL DEFAULT '0',
  `b_textdirections` VARCHAR(200) NULL DEFAULT NULL,
  `b_voicedirectionspath` VARCHAR(100) NULL DEFAULT NULL,
  `b_retelldirectionspath` VARCHAR(30) NULL DEFAULT NULL,
  `b_title` VARCHAR(100) NULL DEFAULT NULL,
  `b_grade_level` VARCHAR(20) NULL DEFAULT NULL,
  `pt_name` VARCHAR(100) NULL DEFAULT NULL,
  `pt_subject_area` VARCHAR(100) NULL DEFAULT NULL,
  `pt_directions` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `lesson_id_idx` (`lesson_id` ASC),
  INDEX `sub_question_id_idx` (`sub_question_id` ASC),
  CONSTRAINT `assignment_type_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sub_question_id`
    FOREIGN KEY (`sub_question_id`)
    REFERENCES `sub_questions` (`sub_question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `assignment_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment_questions` (
  `assignment_questions_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT(20) NULL DEFAULT NULL,
  `question_id` BIGINT(20) NULL DEFAULT NULL,
  `answer` VARCHAR(1500) NULL DEFAULT NULL,
  `max_marks` INT(11) NULL DEFAULT NULL,
  `sec_marks` INT(11) NULL DEFAULT NULL,
  `teacher_comment` VARCHAR(200) NULL DEFAULT NULL,
  `b_audio_path` VARCHAR(30) NULL DEFAULT NULL,
  `b_fluencymarks` INT(11) NULL DEFAULT NULL,
  `b_retell_path` INT(11) NULL DEFAULT NULL,
  `b_quality_of_response_id` BIGINT(20) NULL DEFAULT NULL,
  `b_retellpath` VARCHAR(45) NULL DEFAULT NULL,
  `b_count_of_errors` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`assignment_questions_id`),
  INDEX `fk_student_assignment_id_idx` (`student_assignment_id` ASC),
  INDEX `fk_question_id_idx` (`question_id` ASC),
  INDEX `fk_quality_of_response_id_idx` (`b_quality_of_response_id` ASC),
  CONSTRAINT `fk_aq_quality_of_response_id`
    FOREIGN KEY (`b_quality_of_response_id`)
    REFERENCES `quality_of_response` (`qor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aq_question_id`
    FOREIGN KEY (`question_id`)
    REFERENCES `questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aq_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `attendance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `attendance` (
  `attendance_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NOT NULL DEFAULT '0',
  `date` DATE NOT NULL,
  `student_id` BIGINT(20) NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`attendance_id`),
  INDEX `FK_attendance_1` (`cs_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  CONSTRAINT `fk_atd_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_atd_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `benchmark_results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `benchmark_results` (
  `benchmark_results_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT(20) NOT NULL DEFAULT '0',
  `median_fulency_core` INT(11) NULL DEFAULT '0',
  `median_retell_score` INT(11) NULL DEFAULT '0',
  `sentence_structure_core` FLOAT(10,2) NULL DEFAULT NULL,
  `quality_of_response_id` BIGINT(20) NULL DEFAULT '0',
  `accuracy` FLOAT NULL DEFAULT NULL,
  `teacher_notes` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`benchmark_results_id`),
  INDEX `fk_quality_of_response_id_idx` (`quality_of_response_id` ASC),
  INDEX `fk_student_assignment_id_idx` (`student_assignment_id` ASC),
  CONSTRAINT `fk_br_quality_of_response_id`
    FOREIGN KEY (`quality_of_response_id`)
    REFERENCES `quality_of_response` (`qor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_br_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `citizenship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `citizenship` (
  `Citizenship_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Citizenship_name` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`Citizenship_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `class_actual_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `class_actual_schedule` (
  `cas_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NOT NULL DEFAULT '0',
  `day_id` BIGINT(20) NOT NULL DEFAULT '0',
  `period_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`cas_id`),
  INDEX `cs_id` (`cs_id` ASC, `day_id` ASC),
  INDEX `cs_id_2` (`cs_id` ASC, `day_id` ASC),
  INDEX `cs_id_3` (`cs_id` ASC),
  INDEX `day_id` (`day_id` ASC),
  INDEX `fk_period_id_idx` (`period_id` ASC),
  CONSTRAINT `fk_cas_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cas_period_id`
    FOREIGN KEY (`period_id`)
    REFERENCES `periods` (`period_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`comment_id`),
  UNIQUE INDEX `comment_UNIQUE` (`comment` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `gradeevents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gradeevents` (
  `event_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`event_id`),
  UNIQUE INDEX `event_name_UNIQUE` (`event_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `compositechart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compositechart` (
  `composite_chart_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NOT NULL,
  `grade_event_id` BIGINT(20) NOT NULL,
  `assignment_type_id` BIGINT(20) NOT NULL,
  `overallgrade` FLOAT NOT NULL DEFAULT '0',
  `nooftests` INT(11) NOT NULL,
  `pointspertest` INT(11) NOT NULL,
  PRIMARY KEY (`composite_chart_id`),
  INDEX `fk_grade_event_id_idx` (`grade_event_id` ASC),
  INDEX `fk_assignment_type_id_idx` (`assignment_type_id` ASC),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_chrt_assignment_type_id`
    FOREIGN KEY (`assignment_type_id`)
    REFERENCES `assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chrt_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chrt_grade_event_id`
    FOREIGN KEY (`grade_event_id`)
    REFERENCES `gradeevents` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `days` (
  `day_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(10) NOT NULL DEFAULT '',
  `create_date` DATE NOT NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`day_id`),
  UNIQUE INDEX `UK_days_1` USING BTREE (`day` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `events` (
  `event_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `event_name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(70) NOT NULL,
  `announcement_date` DATE NOT NULL,
  `schedule_date` DATE NOT NULL,
  `last_date` DATE NOT NULL,
  `school_id` BIGINT(20) NOT NULL,
  `venue` VARCHAR(30) NOT NULL,
  `event_time` VARCHAR(30) NOT NULL,
  `contact_person` VARCHAR(30) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_id`),
  UNIQUE INDEX `eventId` (`event_id` ASC),
  INDEX `fk_events_school_id_idx` (`school_id` ASC),
  CONSTRAINT `fk_events_school_id`
    FOREIGN KEY (`school_id`)
    REFERENCES `school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `folders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `folders` (
  `folder_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `foldername` VARCHAR(40) NOT NULL,
  `foldertype` VARCHAR(40) NOT NULL,
  `createddate` DATE NULL,
  PRIMARY KEY (`folder_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `performancetask_groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `performancetask_groups` (
  `performance_group_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NULL DEFAULT NULL,
  `group_name` VARCHAR(20) NULL DEFAULT NULL,
  `MaxNo_of_students` INT(11) NULL DEFAULT NULL,
  `MinNo_of_students` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`performance_group_id`),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  CONSTRAINT `fk_ptg_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `groupassigned_tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `groupassigned_tasks` (
  `gat_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `assignment_id` BIGINT(20) NOT NULL,
  `groupId` BIGINT(20) NULL,
  `performance_task_id` BIGINT(20) NULL,
  `filepath` VARCHAR(100) NULL DEFAULT NULL,
  `contents` VARCHAR(2000) NULL DEFAULT NULL,
  `self_assessment_score` INT(11) NULL DEFAULT NULL,
  `teacher_comments` VARCHAR(400) NULL DEFAULT NULL,
  `teacher_score` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`gat_id`),
  INDEX `fk_assignment_id_idx` (`assignment_id` ASC),
  INDEX `fk_group_id_idx` (`groupId` ASC),
  CONSTRAINT `fk_gat_assignment_id`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gat_group_id`
    FOREIGN KEY (`groupId`)
    REFERENCES `performancetask_groups` (`performance_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `interest` (
  `interest_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `interest` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`interest_id`),
  UNIQUE INDEX `UK_interest_1` USING BTREE (`interest` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 105
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `invitations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invitations` (
  `invitation_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT(20) NOT NULL DEFAULT '0',
  `invitee_email` VARCHAR(45) NOT NULL DEFAULT '',
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `user_typeid` BIGINT(20) NOT NULL,
  `message` VARCHAR(150) NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`invitation_id`),
  INDEX `FK_invitations_1` (`reg_id` ASC),
  INDEX `fk_user_typeid_idx` (`user_typeid` ASC),
  CONSTRAINT `fk_inv_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_typeid`
    FOREIGN KEY (`user_typeid`)
    REFERENCES `user` (`user_typeid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `joinsentencenbenchmark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joinsentencenbenchmark` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NOT NULL,
  `benchmark_id` INT(11) NOT NULL,
  `benchmark_assignment_id` BIGINT(20) DEFAULT NULL,
  `sentencestructure_assignment_id` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_section_id_idx` (`cs_id` ASC),
  INDEX `fk_sentencestructure_assignment_id_idx` (`sentencestructure_assignment_id` ASC),
  INDEX `fk_benchmark_assignment_id_idx` (`benchmark_assignment_id` ASC),
  CONSTRAINT `fk_benchmark_assignment_id`
    FOREIGN KEY (`benchmark_assignment_id`)
    REFERENCES `assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jsb_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sentencestructure_assignment_id`
    FOREIGN KEY (`sentencestructure_assignment_id`)
    REFERENCES `assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `lesson_paths`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lesson_paths` (
  `lesson_path_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT(20) NOT NULL,
  `lesson_path` VARCHAR(300) NOT NULL,
  `uploaded_by` BIGINT(20) NOT NULL,
  PRIMARY KEY (`lesson_path_id`),
  INDEX `fk_lesson_id_idx` (`lesson_id` ASC),
  CONSTRAINT `fk_paths_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `minutes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `minutes` (
  `min_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `minute` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`min_id`),
  UNIQUE INDEX `minute_UNIQUE` (`minute` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rti_groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rti_groups` (
  `rti_group_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rti_group_name` VARCHAR(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`rti_group_id`),
  UNIQUE INDEX `rti_group_name_UNIQUE` (`rti_group_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `register_for_class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `register_for_class` (
  `student_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `class_id` BIGINT(20) NOT NULL,
  `cs_id` BIGINT(20) NULL DEFAULT NULL,
  `section_id` BIGINT(20) NULL DEFAULT NULL,
  `rti_group_id` BIGINT(20) NULL DEFAULT '0',
  `status` VARCHAR(20) NULL DEFAULT NULL,
  `desktop_status` VARCHAR(20) NULL DEFAULT NULL,
  `class_status` VARCHAR(30) NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `performance_group_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`, `class_id`),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  INDEX `fk_rti_group_id_idx` (`rti_group_id` ASC),
  INDEX `fk_section_id_idx` (`section_id` ASC),
  INDEX `fk_performance_group_id_idx` (`performance_group_id` ASC),
  INDEX `fk_rfc_class_id_idx` (`class_id` ASC),
  CONSTRAINT `fk_rfc_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_performance_group_id`
    FOREIGN KEY (`performance_group_id`)
    REFERENCES `performancetask_groups` (`performance_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_rti_group_id`
    FOREIGN KEY (`rti_group_id`)
    REFERENCES `rti_groups` (`rti_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_section_id`
    FOREIGN KEY (`section_id`)
    REFERENCES `section` (`section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `report` (
  `report_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT(20) NULL DEFAULT NULL,
  `student_id` BIGINT(20) NOT NULL,
  `homework_perc` FLOAT NOT NULL,
  `assignment_perc` FLOAT NOT NULL,
  `acedamic_grade_id` BIGINT(20) NOT NULL,
  `Citizenship_id` BIGINT(20) NOT NULL,
  `comment_id` BIGINT(20) NOT NULL,
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
  CONSTRAINT `fk_rpt_academic_grade_id`
    FOREIGN KEY (`acedamic_grade_id`)
    REFERENCES `academic_grades` (`acedamic_grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_citizenship_id`
    FOREIGN KEY (`Citizenship_id`)
    REFERENCES `citizenship` (`Citizenship_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_comment_id`
    FOREIGN KEY (`comment_id`)
    REFERENCES `comments` (`comment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rubric_scalings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rubric_scalings` (
  `rubric_scaling_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rubric_scaling` INT(5) NOT NULL,
  PRIMARY KEY (`rubric_scaling_id`),
  UNIQUE INDEX `rubric_scaling_UNIQUE` (`rubric_scaling` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rubric_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rubric_types` (
  `rubric_type_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rubric_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`rubric_type_id`),
  UNIQUE INDEX `rubric_type_UNIQUE` (`rubric_type` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rubric`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rubric` (
  `rubric_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dimension1` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension2` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension3` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension4` VARCHAR(1000) NULL DEFAULT NULL,
  `dimension1_total` INT(11) NULL DEFAULT NULL,
  `dimension2_total` INT(11) NULL DEFAULT NULL,
  `dimension3_total` INT(11) NULL DEFAULT NULL,
  `dimension4_total` INT(11) NULL DEFAULT NULL,
  `rubric_scaling_id` BIGINT(20) NULL DEFAULT NULL,
  `rubric_type_id` BIGINT(20) NULL DEFAULT NULL,
  `created_by` BIGINT(20) NULL DEFAULT NULL,
  `perf_task_quest_id` BIGINT(20) NULL DEFAULT NULL,
  `student_assignment_id` BIGINT(20) NULL DEFAULT NULL,
  `performance_group_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`rubric_id`),
  INDEX `fk_rubric_scaling_id_idx` (`rubric_scaling_id` ASC),
  INDEX `fk_rubric_type_id_idx` (`rubric_type_id` ASC),
  INDEX `fk_rubric_performance_grp_id_idx` (`performance_group_id` ASC),
  INDEX `fk_rubric_stud_assign_id_idx` (`student_assignment_id` ASC),
  INDEX `fk_rubric_perf_task_ques_id_idx` (`perf_task_quest_id` ASC),
  CONSTRAINT `fk_rubric_performance_grp_id`
    FOREIGN KEY (`performance_group_id`)
    REFERENCES `performancetask_groups` (`performance_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_perf_task_ques_id`
    FOREIGN KEY (`perf_task_quest_id`)
    REFERENCES `questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_scaling_id`
    FOREIGN KEY (`rubric_scaling_id`)
    REFERENCES `rubric_scalings` (`rubric_scaling_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_stud_assign_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_type_id`
    FOREIGN KEY (`rubric_type_id`)
    REFERENCES `rubric_types` (`rubric_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rubric_score`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rubric_score` (
  `rubric_score_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rubric_score` INT(11) NOT NULL,
  PRIMARY KEY (`rubric_score_id`),
  UNIQUE INDEX `rubric_score_UNIQUE` (`rubric_score` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rubric_values`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rubric_values` (
  `rubric_values_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rubric_score_id` BIGINT(20) NOT NULL,
  `rubric_id` BIGINT(20) NOT NULL,
  `dimension1_value` VARCHAR(45) NOT NULL,
  `dimension2_value` VARCHAR(45) NOT NULL,
  `dimension3_value` VARCHAR(45) NOT NULL,
  `dimension4_value` VARCHAR(45) NOT NULL,
  `total_score` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`rubric_values_id`),
  INDEX `fk_rubric_score_id_idx` (`rubric_score_id` ASC),
  INDEX `fk_rubric_id_idx` (`rubric_id` ASC),
  CONSTRAINT `fk_rubric_id`
    FOREIGN KEY (`rubric_id`)
    REFERENCES `rubric` (`rubric_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_score_id`
    FOREIGN KEY (`rubric_score_id`)
    REFERENCES `rubric_score` (`rubric_score_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `school_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_schedule` (
  `school_schedule_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `school_id` BIGINT(20) NOT NULL,
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
    REFERENCES `school` (`school_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `school_days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `school_days` (
  `school_days_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `day_id` BIGINT(20) NULL DEFAULT NULL,
  `school_schedule_id` BIGINT(20) NULL DEFAULT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`school_days_id`),
  INDEX `fk_day_id_idx` (`day_id` ASC),
  INDEX `fk_school_schedule_id_idx` (`school_schedule_id` ASC),
  CONSTRAINT `fk_day_id`
    FOREIGN KEY (`day_id`)
    REFERENCES `days` (`day_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_schedule_id`
    FOREIGN KEY (`school_schedule_id`)
    REFERENCES `school_schedule` (`school_schedule_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `security_question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `security_question` (
  `security_question_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(45) NOT NULL DEFAULT '',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY USING BTREE (`security_question_id`),
  UNIQUE INDEX `UK_sec_question_1` USING BTREE (`question` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `security`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `security` (
  `security_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT(20) NOT NULL DEFAULT '0',
  `sec_question_id` BIGINT(20) NULL DEFAULT NULL,
  `answer` VARCHAR(45) NULL DEFAULT NULL,
  `verification_code` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`security_id`),
  UNIQUE INDEX `UK_security_1` USING BTREE (`reg_id` ASC),
  INDEX `FK_security_2` (`sec_question_id` ASC),
  CONSTRAINT `fk_sec_ques_id`
    FOREIGN KEY (`sec_question_id`)
    REFERENCES `security_question` (`security_question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sec_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `student_composite_chart_values`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_composite_chart_values` (
  `cs_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT(20) NOT NULL,
  `assign_id` VARCHAR(30) NOT NULL DEFAULT '0',
  `score` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cs_id`, `student_id`, `assign_id`),
  INDEX `fk_cs_id_idx` (`cs_id` ASC),
  INDEX `fk_student_id_idx` (`student_id` ASC),
  CONSTRAINT `fk_sccv_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sccv_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `studentgroupstatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `studentgroupstatus` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `performance_group_student_id` BIGINT(20) NULL DEFAULT NULL,
  `assignment_id` BIGINT(20) NULL DEFAULT NULL,
  `login_status` VARCHAR(20) NULL DEFAULT NULL,
  `submit_status` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assignment_id_idx` (`assignment_id` ASC),
  CONSTRAINT `fk_sgs_assignment_id`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `assignment` (`assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sub_interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sub_interest` (
  `sub_interest_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `sub_interest` VARCHAR(45) NOT NULL,
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `interest_id` BIGINT NOT NULL,
  PRIMARY KEY (`sub_interest_id`),
  INDEX `fk_interest_id_idx` (`interest_id` ASC),
  CONSTRAINT `fk_interest_id`
    FOREIGN KEY (`interest_id`)
    REFERENCES `interest` (`interest_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 229
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teacher_subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teacher_subjects` (
  `teacher_subject_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` BIGINT(20) NOT NULL DEFAULT '0',
  `grade_id` BIGINT(20) NOT NULL DEFAULT '0',
  `class_id` BIGINT(20) NOT NULL DEFAULT '0',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `grade_level_id` BIGINT(20) NOT NULL,
  `no_sections_per_day` INT(11) NOT NULL,
  `no_sections_per_week` INT(11) NOT NULL,
  `other_subjects` VARCHAR(25) NULL DEFAULT NULL,
  `requested_class_id` BIGINT(20) NULL DEFAULT NULL,
  `desktop_status` VARCHAR(20) NULL DEFAULT NULL,
  `requested_class_status` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`teacher_subject_id`),
  UNIQUE INDEX `UK_teacher_subjects_1` USING BTREE (`teacher_id` ASC, `grade_id` ASC, `class_id` ASC),
  INDEX `FK_teacher_subjects_2` (`grade_id` ASC),
  INDEX `FK_teacher_subjects_3` (`class_id` ASC),
  CONSTRAINT `fk_subj_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `user_interests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_interests` (
  `user_interests_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `reg_id` BIGINT(20) NOT NULL DEFAULT '0',
  `interest_id` BIGINT(20) NULL DEFAULT '0',
  `sub_interest_id` BIGINT(20) NULL DEFAULT '0',
  `create_date` DATE NULL,
  `change_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `other_user_interest` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_interests_id`),
  UNIQUE INDEX `UK_user_interests_1` USING BTREE (`reg_id` ASC, `sub_interest_id` ASC),
  INDEX `FK_user_interests_2` (`sub_interest_id` ASC),
  CONSTRAINT `fk_user_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_interest_id`
    FOREIGN KEY (`interest_id`)
    REFERENCES `interest` (`interest_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subinterest_id`
    FOREIGN KEY (`sub_interest_id`)
    REFERENCES `sub_interest` (`sub_interest_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


insert into user_registration (email_id,password,user_typeid,create_date,
change_date) values('ian@gmail.com',md5('adminadmin'), 1, current_date, current_timestamp);
-- -----------------------------------------------------------------------------------

INSERT INTO `master_grades` (`master_grades_id`, `grade_name`,`create_date`,`change_date`) VALUES
(1, 'First', '2009-07-16', '2009-07-16 04:44:53'),
(2, 'Second', '2009-07-16', '2009-07-16 04:45:04'),
(3, 'Third', '2009-07-16', '2009-07-16 04:45:13'),
(4, 'Fourth', '2009-07-16', '2009-07-16 04:45:22'),
(5, 'Fifth', '2009-07-16', '2009-07-16 04:45:35'),
(6, 'Sixth', '2009-07-16', '2009-07-16 04:45:44'),
(7, 'Seventh', '2009-07-16', '2009-07-16 04:45:54'),
(8, 'Eighth', '2009-07-16', '2009-09-08 10:31:55'),
(9, 'Ninth', '2009-07-16', '2009-07-16 04:46:13'),
(10, 'Tenth', '2009-07-16', '2009-07-16 04:46:22'),
(11, 'Eleventh', '2009-07-16', '2009-07-16 04:46:31'),
(12, 'Twelfth', '2009-07-16', '2009-08-10 11:06:12'),
(13, 'Kindergarten', '2009-07-16', '2009-07-16 04:44:41'),
(14, 'Others', '2009-07-16', '2009-07-16 04:46:55');
-- -------------------------------------------------------------------------------

INSERT INTO `academic_performance` (`academic_id`, `academic_level`, `academic_grade`, `academic_description`) VALUES
(1, 'Exceeding', 'A', 'The student consistently exceeds the performance standards for the grade-level.  The student with relative ease, grasps applies generalizes, and extends key concepts, processes, and skills consistently and independently.'),
(2, 'Meeting', 'B', 'The student consistently meets the performance standards for the grade-level.  The student, with limited errors, grasps key concepts, processes and skills for the grade-level and understands and applies them effectively.'),
(3, 'Inconsistent Progres', 'C', 'The student is progressing toward meeting the performance standard for the grade-level.  The student is beginning to grasp key concepts, processes and skills for the grade-level, but demonstrates inconsistent understanding and application of concepts.'),
(4, 'Limited Progress', 'D', 'The student is making some progress toward meeting the performance standard.  The student is not demonstrating understanding of grade-level key concepts, processes and skills and requires additional time and support.'),
(5, 'Not Met', 'F', 'The student has not yet met the performance standard.');
-- -------------------------------------------------------------------------------

INSERT INTO `academic_grades` (`acedamic_grade_id`, `acedamic_grade_name`, `score_from`, `score_to`, `academic_id`) VALUES
(1, 'A+', 97, 100, 1),
(2, 'A', 94, 96, 1),
(3, 'A-', 90, 93, 1),
(4, 'B+', 87, 89, 2),
(5, 'B', 84, 86, 2),
(6, 'B-', 80, 83, 2),
(7, 'C+', 77, 79, 3),
(8, 'C', 74, 76, 3),
(9, 'C-', 70, 73, 3),
(10, 'D+', 67, 69, 4),
(11, 'D', 64, 66, 4),
(12, 'D-', 60, 63, 4),
(13, 'F', 50, 59, 5),
(14, 'I', 40, 49, 5),
(15, 'N', 30, 39, 5),
(16, 'P', 0, 29, 5);

-- -----------------------------------------------------------------------------

INSERT INTO `teacher_performances` (`id`, `performance`) VALUES
(1, 'Plan and Create Curriculum'),
(2, 'Assign Units, Lessons, Assessments '),
(3, 'Attendance '),
(4, 'Assign Grades '),
(5, 'Assign and Manage Homework '),
(6, 'Attendance Reports completed '),
(7, 'Test Preparation and Grades '),
(9, 'Keeps Parents and Students Informed ');

-- ---------------------------------------------------------------------------
INSERT INTO `grade_level` (`grade_level_id`, `grade_level_name`) VALUES 
(1, 'above grade level'),
(2, 'at grade level'),
(3, 'below grade level');

-- --------------------------------------------------------------------------
INSERT INTO `gradeevents` (`event_id`, `event_name`) VALUES
(1, 'Homeworks'),
(2, 'Assessments'),
(3, 'Class Works');

-- -----------------------------------------------------------------

INSERT INTO `interest` (`interest_id`,`interest`,`create_date`, `change_date`) VALUES
(1, 'areas of interest', '2009-05-28', '2009-05-28 11:54:14'),
(2, 'sports offered', '2009-05-29', '2009-05-29 10:11:02'),
(3, 'extra-curricular activities offered', '2009-05-29', '2009-05-29 10:12:17'),
(4, 'scholastic areas of interest', '2009-05-29', '2009-05-29 10:16:35'),
(5, 'personal areas of interest', '2009-05-29', '2009-05-29 10:17:00'),
(6, 'current subject areas', '2009-05-29', '2009-05-29 10:18:08'),
(7, 'edu-link subject areas', '2009-05-29', '2009-05-29 10:18:42'),
(8, 'sport activities', '2009-05-29', '2009-05-29 10:19:06');


-- -----------------------------------------------------------------
INSERT INTO `sub_interest` (`sub_interest_id`, `sub_interest`, `create_date`, `change_date` ,`interest_id`) VALUES
(101, 'psychology', '2010-01-06', '2010-01-06 04:56:20', 1 ),
(102, 'sociology', '2010-01-06', '2010-01-06 04:56:20', 1),
(103, 'child psychology', '2010-01-06', '2010-01-06 04:56:20', 1),
(104, 'criminal justice', '2010-01-06', '2010-01-06 04:56:20', 1),
(105, 'management', '2010-01-06', '2010-01-06 04:56:20', 1),
(106, 'laws', '2010-01-06', '2010-01-06 04:56:20', 1),
(107, 'current affairs', '2010-01-06', '2010-01-06 04:56:20', 1),
(108, 'child developement', '2010-01-06', '2010-01-06 04:56:20', 1),
(109, 'art', '2010-01-06', '2010-01-06 04:56:20', 1),
(110, 'literature', '2010-01-06', '2010-01-06 04:56:22', 1),
(111, 'information system', '2010-01-06', '2010-01-06 04:58:56', 1),
(112, 'quantum mechanics', '2010-01-06', '2010-01-06 04:58:56', 1),
(113, 'leadership', '2010-01-06', '2010-01-06 04:58:56', 5 ),
(114, 'theater', '2010-01-06', '2010-01-06 04:58:56', 5),
(115, 'current events', '2010-01-06', '2010-01-06 04:58:56', 5),
(116, 'fashion', '2010-01-06', '2010-01-06 04:58:56', 5),
(117, 'collecting', '2010-01-06', '2010-01-06 04:58:56', 5),
(118, 'travel', '2010-01-06', '2010-01-06 04:58:56', 5),
(119, 'music', '2010-01-06', '2010-01-06 04:58:56',4),
(120, 'extend education', '2010-01-06', '2010-01-06 04:58:58',4),
(121, 'computer science', '2010-01-06', '2010-01-06 05:03:06', 4),
(122, 'web design ', '2010-01-06', '2010-01-06 05:03:06', 4),
(123, 'pta', '2010-01-06', '2012-03-15 08:58:46',4),
(124, 'scouting', '2010-01-06', '2012-03-15 08:59:31',4),
(125, 'mentoring', '2010-01-06', '2012-03-15 08:59:52',4),
(126, 'sports', '2010-01-06', '2012-03-15 09:00:07',4),
(127, 'arts', '2010-01-06', '2012-03-15 09:00:22', 4),
(128, 'teachersaide', '2010-01-06', '2012-03-15 09:08:18',4),
(129, 'clubs', '2010-01-06', '2012-03-15 09:01:43',4),
(130, 'band', '2010-01-06', '2012-03-15 09:01:52',4),
(131, 'office staff', '2010-01-06', '2012-03-15 09:02:19',4),
(132, 'youngrepublican', '2010-01-06', '2012-03-15 09:50:46', 3),
(133, 'youngdemocrat', '2010-01-06', '2012-03-15 09:51:05', 3),
(134, 'key', '2010-01-06', '2012-03-15 09:28:38', 3),
(135, 'environmental', '2010-01-06', '2012-03-15 09:28:48', 3),
(136, 'studentgovt', '2010-01-06', '2012-03-15 09:53:33', 3),
(137, 'debate', '2010-01-06', '2012-03-15 09:29:13', 3),
(138, 'academic', '2010-01-06', '2012-03-15 09:29:22', 3),
(139, 'chess', '2010-01-06', '2012-03-15 09:29:32', 3),
(140, 'surf', '2010-01-06', '2012-03-15 09:40:53', 3),
(141, 'skate', '2010-01-06', '2012-03-15 09:48:43', 3),
(143, 'thesbian', '2010-01-06', '2012-03-15 09:49:08', 3),
(144, 'nhonorsociety', '2010-01-06', '2012-03-15 09:50:35', 3),
(145, 'yearbook', '2010-01-06', '2012-03-15 09:51:41', 3),
(146, 'russian', '2010-01-06', '2012-03-15 09:52:09', 3),
(147, 'betta', '2010-01-06', '2012-03-15 09:52:28', 3),
(148, 'basketball', '2010-01-06', '2012-03-15 09:13:03',2),
(149, 'boxing', '2010-01-06', '2012-03-15 09:13:17',8),
(150, 'baseball', '2010-01-06', '2012-03-15 09:13:27',2),
(151, 'cycling', '2010-01-06', '2012-03-15 09:14:05',2),
(152, 'football', '2010-01-06', '2012-03-15 09:15:06',2),
(153, 'fishing ', '2010-01-06', '2010-01-06 05:21:09',8),
(154, 'hunting ', '2010-01-06', '2010-01-06 05:21:09',8),
(155, 'hiking ', '2010-01-06', '2010-01-06 05:21:09',8),
(156, 'softball', '2010-01-06', '2012-03-15 09:15:25',2),
(157, 'skating ', '2010-01-06', '2010-01-06 05:21:09',8),
(158, 'wrestling', '2010-01-06', '2012-03-15 09:15:35',2),
(159, 'weight lifting', '2010-01-06', '2012-03-15 09:15:51',2),
(160, 'racketball', '2010-01-06', '2012-03-15 09:20:48',2),
(161, 'martial art', '2010-01-06', '2012-03-15 09:20:25',2),
(162, 'swimming', '2010-01-06', '2012-03-15 09:18:27',2),
(163, 'cross country', '2010-01-06', '2012-03-15 09:20:08',2),
(164, 'soccer', '2010-01-06', '2012-03-15 09:21:14',2),
(165, 'track field', '2010-01-06', '2012-03-15 09:21:22',2),
(166, 'tennis', '2010-01-06', '2012-03-15 09:21:34',2),
(167, 'hockey', '2010-01-06', '2012-03-15 09:21:43',2),
(168, 'lacrose', '2010-01-06', '2012-03-15 09:22:00',2),
(169, 'crew', '2010-01-06', '2012-03-15 09:22:11',2),
(170, 'golf', '2010-01-06', '2012-03-15 09:22:20',2),
(171, 'field hockey', '2010-01-06', '2012-03-15 09:22:37',2),
(172, 'skiing', '2010-01-06', '2012-03-15 09:22:45',2),
(174, 'english ', '2010-01-06', '2010-01-06 05:21:09',7),
(175, 'history ', '2010-01-06', '2010-01-06 05:21:09',7),
(176, 'pe ', '2010-01-06', '2010-01-06 05:21:09',7),
(177, 'computers', '2010-01-06', '2012-03-15 09:05:21', 5),
(178, 'chemistry', '2010-01-06', '2012-03-15 09:53:07', 3),
(179, 'biology ', '2010-01-06', '2010-01-06 05:21:09',4),
(180, 'physics ', '2010-01-06', '2010-01-06 05:21:09',4),
(181, 'ushistory ', '2010-01-06', '2010-01-06 05:21:09',7),
(182, 'government ', '2010-01-06', '2010-01-06 05:21:09',7),
(183, 'reading', '2010-01-06', '2012-03-15 09:04:37', 5),
(184, 'spanish', '2010-01-06', '2012-03-15 09:04:55', 3),
(185, 'german', '2010-01-06', '2012-03-15 09:52:55', 3),
(188, 'economics ', '2010-01-06', '2010-01-06 05:21:09',7),
(190, 'shop', '2010-01-06', '2010-01-06 05:25:31',7),
(191, 'cooking', '2010-01-06', '2012-03-15 09:06:05', 5),
(192, 'politics ', '2010-01-06', '2010-01-06 05:25:52',7),
(193, 'speech ', '2010-01-06', '2010-01-06 05:21:10',7),
(194, 'coin collecting ', '2010-01-06', '2010-01-06 05:21:10',7),
(195, 'bird watching ', '2010-01-06', '2010-01-06 05:21:10',7),
(196, 'stamp collecting ', '2010-01-06', '2010-01-06 05:21:10',7),
(197, 'religion ', '2010-01-06', '2010-01-06 05:21:10',7),
(199, 'home economics ', '2010-01-06', '2010-01-06 05:21:10',7),
(200, 'calculus ', '2010-01-06', '2010-01-06 05:21:10',7),
(201, 'algebra ii ', '2010-01-06', '2010-01-06 05:21:10',7);

-- -----------------------------------------------------------------
INSERT INTO `quality_of_response` (`qor_id`, `response`) VALUES
(1, 'provides 2 or fewer details'),
(2, 'provides 3 or more details'),
(3, 'provides 3 or more details in a meaningful sequence'),
(4, 'provides 3 or more details in a meaningful sequence that captures a main idea');

-- -----------------------------------------------------------------
INSERT INTO `rti_groups` (`rti_group_id`, `rti_group_name`) VALUES
(1, 'Fluency sub group'),
(2, 'Comprehension sub group'),
(3, 'Grade level sub group');
-- -----------------------------------------------------------------

INSERT INTO `rubric_types` (`rubric_type_id`, `rubric_type`) VALUES
(1, 'General Holistic'),
(2, 'Specific Holistic'),
(3, 'General Analytic'),
(4, 'Specific Analytic');
-- -----------------------------------------------------------------

INSERT INTO `assignment_type` (`assignment_type_id`, `assignment_type`,`create_date`, `change_date`) VALUES
(1, 'Essay', '2009-05-28', '2013-01-03 10:33:28'),
(2, 'Short Que', '2009-05-28', '2012-11-28 09:38:46'),
(3, 'Multiple Choices', '2009-05-28', '2012-11-28 09:38:46'),
(4, 'Fill in the Blanks', '2009-05-28', '2012-11-28 09:38:46'),
(5, 'True or False', '2009-05-28', '2012-11-28 09:38:46'),
(6, 'others', '2009-05-28', '2013-01-07 09:32:40'),
(7, 'Sentence Structure', '2009-05-28', '2013-03-05 13:17:45'),
(8, 'Benchmark Tests', '2009-05-28', '2013-05-27 05:26:59'),
(9, 'project work', '2009-05-28', '2013-07-04 08:50:51'),
(10, 'Lesson work', '2009-05-28', '2013-07-09 07:57:41'),
(11, 'Activity work', '2009-05-28', '2013-07-09 07:57:41'),
(12, 'Participat\r\nion', '2009-05-28', '2013-07-09 12:45:03'),
(13, 'Performance Task', '2013-12-20', '2013-12-20 12:52:01'),
(14, 'JAC Template', '2014-05-15', '2014-05-16 04:59:51');


-- -----------------------------------------------------------------
INSERT INTO `citizenship` (`citizenship_id`, `citizenship_name`) VALUES
(1, 'excellent'),
(2, 'satisfactory'),
(3, 'not Satisfactory'),
(4, 'unsatisfactory');
-- -----------------------------------------------------------------
INSERT INTO `comments` (`comment_id`, `comment`) VALUES
(1, 'Attendance affecting grade'),
(2, 'Often absent on test/lab days'),
(3, 'New to class'),
(4, 'Needs make-up test/lab/essay'),
(5, 'Poor test/quiz grades'),
(6, 'Classwork incomplete'),
(7, 'Homework incomplete'),
(8, 'Does not follow directions'),
(9, 'Weak study skills'),
(10, 'Poor participation'),
(11, 'required materials not present'),
(12, 'Not working to ability'),
(13, 'Failed to meet requirements'),
(14, 'Poor work quality'),
(15, 'Inconsistent work habits'),
(16, 'Disruptive in class'),
(17, 'Rude/irresponsible'),
(18, 'Poor attitude'),
(19, 'Often falls asleep in class'),
(20, 'Inattentive/uninvolved'),
(21, 'Suggest possible level change'),
(22, 'Needs weekly monitoring'),
(23, 'Needs required P.E. Uniform'),
(24, 'Please contact teacher'),
(25, 'Helpful in class'),
(26, 'Behavior is improving'),
(27, 'Stays focused and on-task'),
(28, 'Puts forth extra effort'),
(29, 'Positive attitude'),
(30, 'Follows requirements'),
(31, 'Good class input'),
(32, 'Outstanding study skills'),
(33, 'See Learning Priority assignments for grade details'),
(34, 'Student is in danger of failing'),
(35, 'Recommend CP per the terms of the signed syllabus/petition');

-- -----------------------------------------------------------------
INSERT INTO `country` (`country_id`, `country`, `continent`) VALUES
(4, 'Afghanistan', 'AS'),
(8, 'Albania', 'EU'),
(10, 'Antarctica', 'AN'),
(12, 'Algeria', 'AF'),
(16, 'American Samoa', 'OC'),
(20, 'Andorra', 'EU'),
(24, 'Angola', 'AF'),
(28, 'Antigua and Barbuda', 'NA'),
(31, 'Azerbaijan', 'AS'),
(32, 'Argentina', 'SA'),
(36, 'Australia', 'OC'),
(40, 'Austria', 'EU'),
(44, 'Bahamas', 'NA'),
(48, 'Bahrain', 'AS'),
(50, 'Bangladesh', 'AS'),
(51, 'Armenia', 'AS'),
(52, 'Barbados', 'NA'),
(56, 'Belgium', 'EU'),
(60, 'Bermuda', 'NA'),
(64, 'Bhutan', 'AS'),
(68, 'Bolivia', 'SA'),
(70, 'Bosnia and Herzegovina', 'EU'),
(72, 'Botswana', 'AF'),
(74, 'Bouvet Island', 'AN'),
(76, 'Brazil', 'SA'),
(84, 'Belize', 'NA'),
(86, 'British Indian Ocean Territory', 'AS'),
(90, 'Solomon Islands', 'OC'),
(92, 'British Virgin Islands', 'NA'),
(96, 'Brunei', 'AS'),
(100, 'Bulgaria', 'EU'),
(104, 'Myanmar', 'AS'),
(108, 'Burundi', 'AF'),
(112, 'Belarus', 'EU'),
(116, 'Cambodia', 'AS'),
(120, 'Cameroon', 'AF'),
(124, 'Canada', 'NA'),
(132, 'Cape Verde', 'AF'),
(136, 'Cayman Islands', 'NA'),
(140, 'Central African Republic', 'AF'),
(144, 'Sri Lanka', 'AS'),
(148, 'Chad', 'AF'),
(152, 'Chile', 'SA'),
(156, 'China', 'AS'),
(158, 'Taiwan', 'AS'),
(162, 'Christmas Island', 'AS'),
(166, 'Cocos Islands', 'AS'),
(170, 'Colombia', 'SA'),
(174, 'Comoros', 'AF'),
(175, 'Mayotte', 'AF'),
(178, 'Republic of the Congo', 'AF'),
(180, 'Democratic Republic of the Congo', 'AF'),
(184, 'Cook Islands', 'OC'),
(188, 'Costa Rica', 'NA'),
(191, 'Croatia', 'EU'),
(192, 'Cuba', 'NA'),
(196, 'Cyprus', 'EU'),
(203, 'Czech Republic', 'EU'),
(204, 'Benin', 'AF'),
(208, 'Denmark', 'EU'),
(212, 'Dominica', 'NA'),
(214, 'Dominican Republic', 'NA'),
(218, 'Ecuador', 'SA'),
(222, 'El Salvador', 'NA'),
(226, 'Equatorial Guinea', 'AF'),
(231, 'Ethiopia', 'AF'),
(232, 'Eritrea', 'AF'),
(233, 'Estonia', 'EU'),
(234, 'Faroe Islands', 'EU'),
(238, 'Falkland Islands', 'SA'),
(239, 'South Georgia and the South Sandwich Islands', 'AN'),
(242, 'Fiji', 'OC'),
(246, 'Finland', 'EU'),
(248, 'Aland Islands', 'EU'),
(250, 'France', 'EU'),
(254, 'French Guiana', 'SA'),
(258, 'French Polynesia', 'OC'),
(260, 'French Southern Territories', 'AN'),
(262, 'Djibouti', 'AF'),
(266, 'Gabon', 'AF'),
(268, 'Georgia', 'AS'),
(270, 'Gambia', 'AF'),
(275, 'Palestinian Territory', 'AS'),
(276, 'Germany', 'EU'),
(288, 'Ghana', 'AF'),
(292, 'Gibraltar', 'EU'),
(296, 'Kiribati', 'OC'),
(300, 'Greece', 'EU'),
(304, 'Greenland', 'NA'),
(308, 'Grenada', 'NA'),
(312, 'Guadeloupe', 'NA'),
(316, 'Guam', 'OC'),
(320, 'Guatemala', 'NA'),
(324, 'Guinea', 'AF'),
(328, 'Guyana', 'SA'),
(332, 'Haiti', 'NA'),
(334, 'Heard Island and McDonald Islands', 'AN'),
(336, 'Vatican', 'EU'),
(340, 'Honduras', 'NA'),
(344, 'Hong Kong', 'AS'),
(348, 'Hungary', 'EU'),
(352, 'Iceland', 'EU'),
(356, 'India', 'AS'),
(360, 'Indonesia', 'AS'),
(364, 'Iran', 'AS'),
(368, 'Iraq', 'AS'),
(372, 'Ireland', 'EU'),
(376, 'Israel', 'AS'),
(380, 'Italy', 'EU'),
(384, 'Ivory Coast', 'AF'),
(388, 'Jamaica', 'NA'),
(392, 'Japan', 'AS'),
(398, 'Kazakhstan', 'AS'),
(400, 'Jordan', 'AS'),
(404, 'Kenya', 'AF'),
(408, 'North Korea', 'AS'),
(410, 'South Korea', 'AS'),
(414, 'Kuwait', 'AS'),
(417, 'Kyrgyzstan', 'AS'),
(418, 'Laos', 'AS'),
(422, 'Lebanon', 'AS'),
(426, 'Lesotho', 'AF'),
(428, 'Latvia', 'EU'),
(430, 'Liberia', 'AF'),
(434, 'Libya', 'AF'),
(438, 'Liechtenstein', 'EU'),
(440, 'Lithuania', 'EU'),
(442, 'Luxembourg', 'EU'),
(446, 'Macao', 'AS'),
(450, 'Madagascar', 'AF'),
(454, 'Malawi', 'AF'),
(458, 'Malaysia', 'AS'),
(462, 'Maldives', 'AS'),
(466, 'Mali', 'AF'),
(470, 'Malta', 'EU'),
(474, 'Martinique', 'NA'),
(478, 'Mauritania', 'AF'),
(480, 'Mauritius', 'AF'),
(484, 'Mexico', 'NA'),
(492, 'Monaco', 'EU'),
(496, 'Mongolia', 'AS'),
(498, 'Moldova', 'EU'),
(499, 'Montenegro', 'EU'),
(500, 'Montserrat', 'NA'),
(504, 'Morocco', 'AF'),
(508, 'Mozambique', 'AF'),
(512, 'Oman', 'AS'),
(516, 'Namibia', 'AF'),
(520, 'Nauru', 'OC'),
(524, 'Nepal', 'AS'),
(528, 'Netherlands', 'EU'),
(530, 'Netherlands Antilles', 'NA'),
(533, 'Aruba', 'NA'),
(540, 'New Caledonia', 'OC'),
(548, 'Vanuatu', 'OC'),
(554, 'New Zealand', 'OC'),
(558, 'Nicaragua', 'NA'),
(562, 'Niger', 'AF'),
(566, 'Nigeria', 'AF'),
(570, 'Niue', 'OC'),
(574, 'Norfolk Island', 'OC'),
(578, 'Norway', 'EU'),
(580, 'Northern Mariana Islands', 'OC'),
(581, 'United States Minor Outlying Islands', 'OC'),
(583, 'Micronesia', 'OC'),
(584, 'Marshall Islands', 'OC'),
(585, 'Palau', 'OC'),
(586, 'Pakistan', 'AS'),
(591, 'Panama', 'NA'),
(598, 'Papua New Guinea', 'OC'),
(600, 'Paraguay', 'SA'),
(604, 'Peru', 'SA'),
(608, 'Philippines', 'AS'),
(612, 'Pitcairn', 'OC'),
(616, 'Poland', 'EU'),
(620, 'Portugal', 'EU'),
(624, 'Guinea-Bissau', 'AF'),
(626, 'East Timor', 'OC'),
(630, 'Puerto Rico', 'NA'),
(634, 'Qatar', 'AS'),
(638, 'Reunion', 'AF'),
(642, 'Romania', 'EU'),
(643, 'Russia', 'EU'),
(646, 'Rwanda', 'AF'),
(652, 'Saint Barthélemy', 'NA'),
(654, 'Saint Helena', 'AF'),
(659, 'Saint Kitts and Nevis', 'NA'),
(660, 'Anguilla', 'NA'),
(662, 'Saint Lucia', 'NA'),
(663, 'Saint Martin', 'NA'),
(666, 'Saint Pierre and Miquelon', 'NA'),
(670, 'Saint Vincent and the Grenadines', 'NA'),
(674, 'San Marino', 'EU'),
(678, 'Sao Tome and Principe', 'AF'),
(682, 'Saudi Arabia', 'AS'),
(686, 'Senegal', 'AF'),
(688, 'Serbia', 'EU'),
(690, 'Seychelles', 'AF'),
(694, 'Sierra Leone', 'AF'),
(702, 'Singapore', 'AS'),
(703, 'Slovakia', 'EU'),
(704, 'Vietnam', 'AS'),
(705, 'Slovenia', 'EU'),
(706, 'Somalia', 'AF'),
(710, 'South Africa', 'AF'),
(716, 'Zimbabwe', 'AF'),
(724, 'Spain', 'EU'),
(732, 'Western Sahara', 'AF'),
(736, 'Sudan', 'AF'),
(740, 'Suriname', 'SA'),
(744, 'Svalbard and Jan Mayen', 'EU'),
(748, 'Swaziland', 'AF'),
(752, 'Sweden', 'EU'),
(756, 'Switzerland', 'EU'),
(760, 'Syria', 'AS'),
(762, 'Tajikistan', 'AS'),
(764, 'Thailand', 'AS'),
(768, 'Togo', 'AF'),
(772, 'Tokelau', 'OC'),
(776, 'Tonga', 'OC'),
(780, 'Trinidad and Tobago', 'NA'),
(784, 'United Arab Emirates', 'AS'),
(788, 'Tunisia', 'AF'),
(792, 'Turkey', 'AS'),
(795, 'Turkmenistan', 'AS'),
(796, 'Turks and Caicos Islands', 'NA'),
(798, 'Tuvalu', 'OC'),
(800, 'Uganda', 'AF'),
(804, 'Ukraine', 'EU'),
(807, 'Macedonia', 'EU'),
(818, 'Egypt', 'AF'),
(826, 'United Kingdom', 'EU'),
(831, 'Guernsey', 'EU'),
(832, 'Jersey', 'EU'),
(833, 'Isle of Man', 'EU'),
(834, 'Tanzania', 'AF'),
(840, 'United States', 'NA'),
(850, 'U.S. Virgin Islands', 'NA'),
(854, 'Burkina Faso', 'AF'),
(855, 'Kosovo', 'EU'),
(858, 'Uruguay', 'SA'),
(860, 'Uzbekistan', 'AS'),
(862, 'Venezuela', 'SA'),
(876, 'Wallis and Futuna', 'OC'),
(882, 'Samoa', 'OC'),
(887, 'Yemen', 'AS'),
(891, 'Serbia and Montenegro', 'EU'),
(894, 'Zambia', 'AF');
-- -----------------------------------------------------------------
INSERT INTO `states` (`state_id`, `country_id`, `name`) VALUES
(1, 4, 'Badakhshan'),
(2, 4, 'Badghis Province'),
(3, 4, 'Baghlān'),
(4, 4, 'Bāmīān'),
(5, 4, 'Farah'),
(6, 4, 'Faryab Province'),
(7, 4, 'Ghaznī'),
(8, 4, 'Ghowr'),
(9, 4, 'Helmand Province'),
(10, 4, 'Herat Province'),
(11, 4, 'Kabul'),
(12, 4, 'Kāpīsā'),
(13, 4, 'Lowgar'),
(14, 4, 'Nangarhār'),
(15, 4, 'Nīmrūz'),
(16, 4, 'Orūzgān'),
(17, 4, 'Kandahār'),
(18, 4, 'Kunduz Province'),
(19, 4, 'Takhār'),
(20, 4, 'Vardak'),
(21, 4, 'Zabul Province'),
(22, 4, 'Paktīkā'),
(23, 4, 'Balkh'),
(24, 4, 'Jowzjān'),
(25, 4, 'Samangān'),
(26, 4, 'Sar-e Pol'),
(27, 4, 'Konar'),
(28, 4, 'Laghmān'),
(29, 4, 'Paktia Province'),
(30, 4, 'Khowst'),
(31, 4, 'Nūrestān'),
(32, 4, 'Parvān'),
(33, 4, 'Dāykondī'),
(34, 4, 'Panjshīr'),
(35, 8, 'Berat'),
(36, 8, 'Dibër'),
(37, 8, 'Durrës'),
(38, 8, 'Elbasan'),
(39, 8, 'Fier'),
(40, 8, 'Gjirokastër'),
(41, 8, 'Korçë'),
(42, 8, 'Kukës'),
(43, 8, 'Lezhë'),
(44, 8, 'Shkodër'),
(45, 8, 'Tiranë'),
(46, 8, 'Vlorë'),
(47, 12, 'Alger'),
(48, 12, 'Batna'),
(49, 12, 'Constantine'),
(50, 12, 'Médéa'),
(51, 12, 'Mostaganem'),
(52, 12, 'Oran'),
(53, 12, 'Saïda'),
(54, 12, 'Sétif'),
(55, 12, 'Tiaret'),
(56, 12, 'Tizi Ouzou'),
(57, 12, 'Tlemcen'),
(58, 12, 'Bejaïa'),
(59, 12, 'Biskra'),
(60, 12, 'Blida'),
(61, 12, 'Bouira'),
(62, 12, 'Djelfa'),
(63, 12, 'Guelma'),
(64, 12, 'Jijel'),
(65, 12, 'Laghouat'),
(66, 12, 'Mascara'),
(67, 12, 'Mʼsila'),
(68, 12, 'Oum el Bouaghi'),
(69, 12, 'Sidi Bel Abbès'),
(70, 12, 'Skikda'),
(71, 12, 'Tébessa'),
(72, 12, 'Adrar'),
(73, 12, 'Aïn Defla'),
(74, 12, 'Aïn Temouchent'),
(75, 12, 'Annaba'),
(76, 12, 'Béchar'),
(77, 12, 'Bordj Bou Arréridj'),
(78, 12, 'Boumerdes'),
(79, 12, 'Chlef'),
(80, 12, 'El Bayadh'),
(81, 12, 'El Oued'),
(82, 12, 'El Tarf'),
(83, 12, 'Ghardaïa'),
(84, 12, 'Illizi'),
(85, 12, 'Khenchela'),
(86, 12, 'Mila'),
(87, 12, 'Naama النعامة'),
(88, 12, 'Ouargla'),
(89, 12, 'Relizane'),
(90, 12, 'Souk Ahras'),
(91, 12, 'Tamanghasset'),
(92, 12, 'Tindouf'),
(93, 12, 'Tipaza'),
(94, 12, 'Tissemsilt'),
(95, 16, 'American Samoa'),
(96, 20, 'Parròquia de Canillo'),
(97, 20, 'Parròquia dʼEncamp'),
(98, 20, 'Parròquia de la Massana'),
(99, 20, 'Parròquia dʼOrdino'),
(100, 20, 'Parròquia de Sant Julià de Lòria'),
(101, 20, 'Parròquia dʼAndorra la Vella'),
(102, 20, 'Parròquia dʼEscaldes-Engordany'),
(103, 24, 'Benguela'),
(104, 24, 'Bié'),
(105, 24, 'Cabinda'),
(106, 24, 'Cuando Cubango'),
(107, 24, 'Cuanza Norte'),
(108, 24, 'Cuanza Sul'),
(109, 24, 'Cunene'),
(110, 24, 'Huambo'),
(111, 24, 'Huíla'),
(112, 24, 'Luanda'),
(113, 24, 'Malanje'),
(114, 24, 'Namibe'),
(115, 24, 'Moxico'),
(116, 24, 'Uíge'),
(117, 24, 'Zaire'),
(118, 24, 'Lunda Norte'),
(119, 24, 'Lunda Sul'),
(120, 24, 'Bengo'),
(121, 28, 'Redonda'),
(122, 28, 'Barbuda'),
(123, 28, 'Saint George'),
(124, 28, 'Saint John'),
(125, 28, 'Saint Mary'),
(126, 28, 'Saint Paul'),
(127, 28, 'Saint Peter'),
(128, 28, 'Saint Philip'),
(129, 31, 'Abşeron'),
(130, 31, 'Ağcabǝdi'),
(131, 31, 'Ağdam'),
(132, 31, 'Ağdaş'),
(133, 31, 'Ağstafa'),
(134, 31, 'Ağsu'),
(135, 31, 'Əli Bayramli'),
(136, 31, 'Astara'),
(137, 31, 'Baki'),
(138, 31, 'Balakǝn'),
(139, 31, 'Bǝrdǝ'),
(140, 31, 'Beylǝqan'),
(141, 31, 'Bilǝsuvar'),
(142, 31, 'Cǝbrayıl'),
(143, 31, 'Cǝlilabad'),
(144, 31, 'Daşkǝsǝn'),
(145, 31, 'Dǝvǝçi'),
(146, 31, 'Füzuli'),
(147, 31, 'Gǝdǝbǝy'),
(148, 31, 'Gǝncǝ'),
(149, 31, 'Goranboy'),
(150, 31, 'Göyçay'),
(151, 31, 'Hacıqabul'),
(152, 31, 'İmişli'),
(153, 31, 'İsmayıllı'),
(154, 31, 'Kǝlbǝcǝr'),
(155, 31, 'Kürdǝmir'),
(156, 31, 'Laçın'),
(157, 31, 'Lǝnkǝran'),
(158, 31, 'Lǝnkǝran Şǝhǝri'),
(159, 31, 'Lerik'),
(160, 31, 'Masallı'),
(161, 31, 'Mingǝcevir'),
(162, 31, 'Naftalan'),
(163, 31, 'Nakhichevan'),
(164, 31, 'Neftçala'),
(165, 31, 'Oğuz'),
(166, 31, 'Qǝbǝlǝ'),
(167, 31, 'Qǝx'),
(168, 31, 'Qazax'),
(169, 31, 'Qobustan'),
(170, 31, 'Quba'),
(171, 31, 'Qubadlı'),
(172, 31, 'Qusar'),
(173, 31, 'Saatlı'),
(174, 31, 'Sabirabad'),
(175, 31, 'Şǝki'),
(176, 31, 'Şǝki'),
(177, 31, 'Salyan'),
(178, 31, 'Şamaxı'),
(179, 31, 'Şǝmkir'),
(180, 31, 'Samux'),
(181, 31, 'Siyǝzǝn'),
(182, 31, 'Sumqayit'),
(183, 31, 'Şuşa'),
(184, 31, 'Şuşa Şəhəri'),
(185, 31, 'Tǝrtǝr'),
(186, 31, 'Tovuz'),
(187, 31, 'Ucar'),
(188, 31, 'Xaçmaz'),
(189, 31, 'Xankǝndi'),
(190, 31, 'Xanlar'),
(191, 31, 'Xızı'),
(192, 31, 'Xocalı'),
(193, 31, 'Xocavǝnd'),
(194, 31, 'Yardımlı'),
(195, 31, 'Yevlax'),
(196, 31, 'Yevlax'),
(197, 31, 'Zǝngilan'),
(198, 31, 'Zaqatala'),
(199, 31, 'Zǝrdab'),
(200, 32, 'Buenos Aires'),
(201, 32, 'Catamarca'),
(202, 32, 'Chaco'),
(203, 32, 'Chubut'),
(204, 32, 'Córdoba'),
(205, 32, 'Corrientes'),
(206, 32, 'Distrito Federal'),
(207, 32, 'Entre Ríos'),
(208, 32, 'Formosa'),
(209, 32, 'Jujuy'),
(210, 32, 'La Pampa'),
(211, 32, 'La Rioja'),
(212, 32, 'Mendoza'),
(213, 32, 'Misiones'),
(214, 32, 'Neuquén'),
(215, 32, 'Río Negro'),
(216, 32, 'Salta'),
(217, 32, 'San Juan'),
(218, 32, 'San Luis'),
(219, 32, 'Santa Cruz'),
(220, 32, 'Santa Fe'),
(221, 32, 'Santiago del Estero'),
(222, 32, 'Tierra del Fuego, Antártida e Islas del Atlán'),
(223, 32, 'Tucumán'),
(224, 36, 'Australian Capital Territory'),
(225, 36, 'New South Wales'),
(226, 36, 'Northern Territory'),
(227, 36, 'Queensland'),
(228, 36, 'South Australia'),
(229, 36, 'Tasmania'),
(230, 36, 'Victoria'),
(231, 36, 'Western Australia'),
(232, 40, 'Burgenland'),
(233, 40, 'Carinthia'),
(234, 40, 'Lower Austria'),
(235, 40, 'Upper Austria'),
(236, 40, 'Salzburg'),
(237, 40, 'Styria'),
(238, 40, 'Tyrol'),
(239, 40, 'Vorarlberg'),
(240, 40, 'Vienna'),
(241, 44, 'Bimini'),
(242, 44, 'Cat Island'),
(243, 44, 'Inagua'),
(244, 44, 'Long Island'),
(245, 44, 'Mayaguana'),
(246, 44, 'Ragged Island'),
(247, 44, 'Harbour Island, Eleuthera'),
(248, 44, 'North Abaco'),
(249, 44, 'Acklins'),
(250, 44, 'City of Freeport, Grand Bahama'),
(251, 44, 'South Andros'),
(252, 44, 'Hope Town, Abaco'),
(253, 44, 'Mangrove Cay, Andros'),
(254, 44, 'Mooreʼs Island, Abaco'),
(255, 44, 'Rum Cay'),
(256, 44, 'North Andros'),
(257, 44, 'North Eleuthera'),
(258, 44, 'South Eleuthera'),
(259, 44, 'South Abaco'),
(260, 44, 'San Salvador'),
(261, 44, 'Berry Islands'),
(262, 44, 'Black Point, Exuma'),
(263, 44, 'Central Abaco'),
(264, 44, 'Central Andros'),
(265, 44, 'Central Eleuthera'),
(266, 44, 'Crooked Island'),
(267, 44, 'East Grand Bahama'),
(268, 44, 'Exuma'),
(269, 44, 'Grand Cay, Abaco'),
(270, 44, 'Spanish Wells, Eleuthera'),
(271, 44, 'West Grand Bahama'),
(272, 48, 'Southern Governate'),
(273, 48, 'Northern Governate'),
(274, 48, 'Capital Governate'),
(275, 48, 'Central Governate'),
(276, 48, 'Muharraq Governate'),
(277, 50, 'BG80'),
(278, 50, 'Dhaka'),
(279, 50, 'Khulna'),
(280, 50, 'Rājshāhi'),
(281, 50, 'Chittagong'),
(282, 50, 'Barisāl'),
(283, 50, 'Sylhet'),
(284, 51, 'Aragatsotn'),
(285, 51, 'Ararat'),
(286, 51, 'Armavir'),
(287, 51, 'Gegharkʼunikʼ'),
(288, 51, 'Kotaykʼ'),
(289, 51, 'Lorri'),
(290, 51, 'Shirak'),
(291, 51, 'Syunikʼ'),
(292, 51, 'Tavush'),
(293, 51, 'Vayotsʼ Dzor'),
(294, 51, 'Yerevan'),
(295, 52, 'Christ Church'),
(296, 52, 'Saint Andrew'),
(297, 52, 'Saint George'),
(298, 52, 'Saint James'),
(299, 52, 'Saint John'),
(300, 52, 'Saint Joseph'),
(301, 52, 'Saint Lucy'),
(302, 52, 'Saint Michael'),
(303, 52, 'Saint Peter'),
(304, 52, 'Saint Philip'),
(305, 52, 'Saint Thomas'),
(306, 56, 'Bruxelles-Capitale'),
(307, 56, 'Flanders'),
(308, 56, 'Wallonia'),
(309, 60, 'Devonshire'),
(310, 60, 'Hamilton (parish)'),
(311, 60, 'Hamilton (city)'),
(312, 60, 'Paget'),
(313, 60, 'Pembroke'),
(314, 60, 'Saint Georgeʼs (parish)'),
(315, 60, 'Saint Georgeʼs (city)'),
(316, 60, 'Sandys'),
(317, 60, 'Smithʼs'),
(318, 60, 'Southampton'),
(319, 60, 'Warwick'),
(320, 64, 'Bumthang'),
(321, 64, 'Chhukha'),
(322, 64, 'Chirang'),
(323, 64, 'Daga'),
(324, 64, 'Geylegphug'),
(325, 64, 'Ha'),
(326, 64, 'Lhuntshi'),
(327, 64, 'Mongar'),
(328, 64, 'Paro District'),
(329, 64, 'Pemagatsel'),
(330, 64, 'Samchi'),
(331, 64, 'Samdrup Jongkhar District'),
(332, 64, 'Shemgang'),
(333, 64, 'Tashigang'),
(334, 64, 'Thimphu'),
(335, 64, 'Tongsa'),
(336, 64, 'Wangdi Phodrang'),
(337, 68, 'Chuquisaca'),
(338, 68, 'Cochabamba'),
(339, 68, 'El Beni'),
(340, 68, 'La Paz'),
(341, 68, 'Oruro'),
(342, 68, 'Pando'),
(343, 68, 'Potosí'),
(344, 68, 'Santa Cruz'),
(345, 68, 'Tarija'),
(346, 70, 'Federation of Bosnia and Herzegovina'),
(347, 70, 'Republika Srpska'),
(348, 70, 'Brčko'),
(349, 72, 'Central'),
(350, 72, 'Chobe'),
(351, 72, 'Ghanzi'),
(352, 72, 'Kgalagadi'),
(353, 72, 'Kgatleng'),
(354, 72, 'Kweneng'),
(355, 72, 'Ngamiland'),
(356, 72, 'North East'),
(357, 72, 'South East'),
(358, 72, 'Southern'),
(359, 72, 'North West'),
(360, 76, 'Acre'),
(361, 76, 'Alagoas'),
(362, 76, 'Amapá'),
(363, 76, 'Estado do Amazonas'),
(364, 76, 'Bahia'),
(365, 76, 'Ceará'),
(366, 76, 'Distrito Federal'),
(367, 76, 'Espírito Santo'),
(368, 76, 'Fernando de Noronha'),
(369, 76, 'Goias'),
(370, 76, 'Mato Grosso do Sul'),
(371, 76, 'Maranhão'),
(372, 76, 'Mato Grosso'),
(373, 76, 'Minas Gerais'),
(374, 76, 'Pará'),
(375, 76, 'Paraíba'),
(376, 76, 'Paraná'),
(377, 76, 'Pernambuco'),
(378, 76, 'Piauí'),
(379, 76, 'State of Rio de Janeiro'),
(380, 76, 'Rio Grande do Norte'),
(381, 76, 'Rio Grande do Sul'),
(382, 76, 'Rondônia'),
(383, 76, 'Roraima'),
(384, 76, 'Santa Catarina'),
(385, 76, 'São Paulo'),
(386, 76, 'Sergipe'),
(387, 76, 'Estado de Goiás'),
(388, 76, 'Pernambuco'),
(389, 76, 'Tocantins'),
(390, 84, 'Belize'),
(391, 84, 'Cayo'),
(392, 84, 'Corozal'),
(393, 84, 'Orange Walk'),
(394, 84, 'Stann Creek'),
(395, 84, 'Toledo'),
(396, 86, 'British Indian Ocean Territory'),
(397, 90, 'Malaita'),
(398, 90, 'Western'),
(399, 90, 'Central'),
(400, 90, 'Guadalcanal'),
(401, 90, 'Isabel'),
(402, 90, 'Makira'),
(403, 90, 'Temotu'),
(404, 90, 'Central Province'),
(405, 90, 'Choiseul'),
(406, 90, 'Rennell and Bellona'),
(407, 90, 'Rennell and Bellona'),
(408, 92, 'British Virgin Islands'),
(409, 96, 'Belait'),
(410, 96, 'Brunei and Muara'),
(411, 96, 'Temburong'),
(412, 96, 'Tutong'),
(413, 100, 'Burgas'),
(414, 100, 'Grad'),
(415, 100, 'Khaskovo'),
(416, 100, 'Lovech'),
(417, 100, 'Mikhaylovgrad'),
(418, 100, 'Plovdiv'),
(419, 100, 'Razgrad'),
(420, 100, 'Sofiya'),
(421, 100, 'Varna'),
(422, 100, 'Blagoevgrad'),
(423, 100, 'Burgas'),
(424, 100, 'Dobrich'),
(425, 100, 'Gabrovo'),
(426, 100, 'Oblast Sofiya-Grad'),
(427, 100, 'Khaskovo'),
(428, 100, 'Kŭrdzhali'),
(429, 100, 'Kyustendil'),
(430, 100, 'Lovech'),
(431, 100, 'Montana'),
(432, 100, 'Pazardzhit'),
(433, 100, 'Pernik'),
(434, 100, 'Pleven'),
(435, 100, 'Plovdiv'),
(436, 100, 'Razgrad'),
(437, 100, 'Ruse'),
(438, 100, 'Shumen'),
(439, 100, 'Silistra'),
(440, 100, 'Sliven'),
(441, 100, 'Smolyan'),
(442, 100, 'Sofiya'),
(443, 100, 'Stara Zagora'),
(444, 100, 'Tŭrgovishte'),
(445, 100, 'Varna'),
(446, 100, 'Veliko Tŭrnovo'),
(447, 100, 'Vidin'),
(448, 100, 'Vratsa'),
(449, 100, 'Yambol'),
(450, 104, 'Rakhine State'),
(451, 104, 'Chin State'),
(452, 104, 'Ayeyarwady'),
(453, 104, 'Kachin State'),
(454, 104, 'Kayin State'),
(455, 104, 'Kayah State'),
(456, 104, 'Magwe'),
(457, 104, 'Mandalay'),
(458, 104, 'Pegu'),
(459, 104, 'Sagain'),
(460, 104, 'Shan State'),
(461, 104, 'Tanintharyi'),
(462, 104, 'Mon State'),
(463, 104, 'Rangoon'),
(464, 104, 'Magway'),
(465, 104, 'Bago'),
(466, 104, 'Yangon'),
(467, 108, 'Bujumbura'),
(468, 108, 'Bubanza'),
(469, 108, 'Bururi'),
(470, 108, 'Cankuzo'),
(471, 108, 'Cibitoke'),
(472, 108, 'Gitega'),
(473, 108, 'Karuzi'),
(474, 108, 'Kayanza'),
(475, 108, 'Kirundo'),
(476, 108, 'Makamba'),
(477, 108, 'Muyinga'),
(478, 108, 'Ngozi'),
(479, 108, 'Rutana'),
(480, 108, 'Ruyigi'),
(481, 108, 'Muramvya'),
(482, 108, 'Mwaro'),
(483, 112, 'Brestskaya Voblastsʼ'),
(484, 112, 'Homyelʼskaya Voblastsʼ'),
(485, 112, 'Hrodzyenskaya Voblastsʼ'),
(486, 112, 'Mahilyowskaya Voblastsʼ'),
(487, 112, 'Horad Minsk'),
(488, 112, 'Minskaya Voblastsʼ'),
(489, 112, 'Vitsyebskaya Voblastsʼ'),
(490, 116, 'Krŏng Preăh Seihânŭ'),
(491, 116, 'Kâmpóng Cham'),
(492, 116, 'Kâmpóng Chhnăng'),
(493, 116, 'Khétt Kâmpóng Spœ'),
(494, 116, 'Kâmpóng Thum'),
(495, 116, 'Kândal'),
(496, 116, 'Kaôh Kŏng'),
(497, 116, 'Krâchéh'),
(498, 116, 'Môndól Kiri'),
(499, 116, 'Phnum Penh'),
(500, 116, 'Poŭthĭsăt'),
(501, 116, 'Preăh Vihéar'),
(502, 116, 'Prey Vêng'),
(503, 116, 'Stœ̆ng Trêng'),
(504, 116, 'Svay Riĕng'),
(505, 116, 'Takêv'),
(506, 116, 'Kâmpôt'),
(507, 116, 'Phnum Pénh'),
(508, 116, 'Rôtânăh Kiri'),
(509, 116, 'Siĕm Réab'),
(510, 116, 'Bantéay Méan Cheăy'),
(511, 116, 'Kêb'),
(512, 116, 'Ŏtdâr Méan Cheăy'),
(513, 116, 'Preăh Seihânŭ'),
(514, 116, 'Bătdâmbâng'),
(515, 116, 'Palĭn'),
(516, 120, 'Est'),
(517, 120, 'Littoral'),
(518, 120, 'North-West Province'),
(519, 120, 'Ouest'),
(520, 120, 'South-West Province'),
(521, 120, 'Adamaoua'),
(522, 120, 'Centre'),
(523, 120, 'Extreme-Nord'),
(524, 120, 'North Province'),
(525, 120, 'South Province'),
(526, 124, 'Alberta'),
(527, 124, 'British Columbia'),
(528, 124, 'Manitoba'),
(529, 124, 'New Brunswick'),
(530, 124, 'Newfoundland and Labrador'),
(531, 124, 'Nova Scotia'),
(532, 124, 'Ontario'),
(533, 124, 'Prince Edward Island'),
(534, 124, 'Quebec'),
(535, 124, 'Saskatchewan'),
(536, 124, 'Yukon'),
(537, 124, 'Northwest Territories'),
(538, 124, 'Nunavut'),
(539, 132, 'Boa Vista'),
(540, 132, 'Brava'),
(541, 132, 'Maio'),
(542, 132, 'Paul'),
(543, 132, 'Praia'),
(544, 132, 'Ribeira Grande'),
(545, 132, 'Sal'),
(546, 132, 'Santa Catarina   '),
(547, 132, 'São Nicolau'),
(548, 132, 'São Vicente'),
(549, 132, 'Tarrafal '),
(550, 132, 'Mosteiros'),
(551, 132, 'Praia'),
(552, 132, 'Santa Catarina'),
(553, 132, 'Santa Cruz'),
(554, 132, 'São Domingos'),
(555, 132, 'São Filipe'),
(556, 132, 'São Miguel'),
(557, 132, 'Tarrafal'),
(558, 136, 'Creek'),
(559, 136, 'Eastern'),
(560, 136, 'Midland'),
(561, 136, 'South Town'),
(562, 136, 'Spot Bay'),
(563, 136, 'Stake Bay'),
(564, 136, 'West End'),
(565, 136, 'Western'),
(566, 140, 'Bamingui-Bangoran'),
(567, 140, 'Basse-Kotto'),
(568, 140, 'Haute-Kotto'),
(569, 140, 'Mambéré-Kadéï'),
(570, 140, 'Haut-Mbomou'),
(571, 140, 'Kémo'),
(572, 140, 'Lobaye'),
(573, 140, 'Mbomou'),
(574, 140, 'Nana-Mambéré'),
(575, 140, 'Ouaka'),
(576, 140, 'Ouham'),
(577, 140, 'Ouham-Pendé'),
(578, 140, 'Vakaga'),
(579, 140, 'Nana-Grébizi'),
(580, 140, 'Sangha-Mbaéré'),
(581, 140, 'Ombella-Mpoko'),
(582, 140, 'Bangui'),
(583, 144, 'Central'),
(584, 144, 'North Central'),
(585, 144, 'North Eastern'),
(586, 144, 'North Western'),
(587, 144, 'Sabaragamuwa'),
(588, 144, 'Southern'),
(589, 144, 'Uva'),
(590, 144, 'Western'),
(591, 148, 'Batha'),
(592, 148, 'Biltine'),
(593, 148, 'Borkou-Ennedi-Tibesti'),
(594, 148, 'Chari-Baguirmi'),
(595, 148, 'Guéra'),
(596, 148, 'Kanem'),
(597, 148, 'Lac'),
(598, 148, 'Logone Occidental'),
(599, 148, 'Logone Oriental'),
(600, 148, 'Mayo-Kébbi'),
(601, 148, 'Moyen-Chari'),
(602, 148, 'Ouaddaï'),
(603, 148, 'Salamat'),
(604, 148, 'Tandjilé'),
(605, 152, 'Vaaraíso'),
(606, 152, 'Aisén del General Carlos Ibáñez del Campo'),
(607, 152, 'Antofagasta'),
(608, 152, 'Araucanía'),
(609, 152, 'Atacama'),
(610, 152, 'Bío-Bío'),
(611, 152, 'Coquimbo'),
(612, 152, 'Libertador General Bernardo OʼHiggins'),
(613, 152, 'Los Lagos'),
(614, 152, 'Magallanes y Antártica Chilena'),
(615, 152, 'Maule'),
(616, 152, 'Región Metropolitana'),
(617, 152, 'Tarapaca'),
(618, 152, 'Los Lagos'),
(619, 152, 'Tarapacá'),
(620, 152, 'Región de Arica y Parinacota'),
(621, 152, 'Región de Los Ríos'),
(622, 156, 'Anhui'),
(623, 156, 'Zhejiang'),
(624, 156, 'Jiangxi'),
(625, 156, 'Jiangsu'),
(626, 156, 'Jilin'),
(627, 156, 'Qinghai'),
(628, 156, 'Fujian'),
(629, 156, 'Heilongjiang'),
(630, 156, 'Henan'),
(631, 156, 'disputed'),
(632, 156, 'Hebei'),
(633, 156, 'Hunan Province'),
(634, 156, 'Hubei'),
(635, 156, 'Xinjiang'),
(636, 156, 'Xizang'),
(637, 156, 'Gansu'),
(638, 156, 'Guangxi'),
(639, 156, 'Guizhou'),
(640, 156, 'Liaoning Province'),
(641, 156, 'Inner Mongolia'),
(642, 156, 'Ningxia'),
(643, 156, 'Beijing'),
(644, 156, 'Shanghai'),
(645, 156, 'Shanxi'),
(646, 156, 'Shandong'),
(647, 156, 'Shaanxi'),
(648, 156, 'Tianjin'),
(649, 156, 'Yunnan Province'),
(650, 156, 'Guangdong'),
(651, 156, 'Hainan Province'),
(652, 156, 'Sichuan'),
(653, 156, 'Chongqing'),
(654, 156, 'PF99'),
(655, 158, 'Fukien'),
(656, 158, 'Kaohsiung'),
(657, 158, 'Taipei'),
(658, 158, 'Taiwan'),
(659, 162, 'Christmas Island'),
(660, 166, 'Cocos (Keeling) Islands'),
(661, 170, 'Amazonas'),
(662, 170, 'Antioquia'),
(663, 170, 'Arauca'),
(664, 170, 'Atlántico'),
(665, 170, 'Bolívar'),
(666, 170, 'Boyacá'),
(667, 170, 'Caldas'),
(668, 170, 'Caquetá'),
(669, 170, 'Cauca'),
(670, 170, 'Cesar'),
(671, 170, 'Chocó'),
(672, 170, 'Córdoba'),
(673, 170, 'Guaviare'),
(674, 170, 'Guainía'),
(675, 170, 'Huila'),
(676, 170, 'La Guajira'),
(677, 170, 'Magdalena'),
(678, 170, 'Meta'),
(679, 170, 'Nariño'),
(680, 170, 'Norte de Santander'),
(681, 170, 'Putumayo'),
(682, 170, 'Quindío'),
(683, 170, 'Risaralda'),
(684, 170, 'Archipiélago de San Andrés, Providencia y San'),
(685, 170, 'Santander'),
(686, 170, 'Sucre'),
(687, 170, 'Tolima'),
(688, 170, 'Valle del Cauca'),
(689, 170, 'Vaupés'),
(690, 170, 'Vichada'),
(691, 170, 'Casanare'),
(692, 170, 'Cundinamarca'),
(693, 170, 'Bogota D.C.'),
(694, 170, 'Bolívar'),
(695, 170, 'Boyacá'),
(696, 170, 'Caldas'),
(697, 170, 'Magdalena'),
(698, 174, 'Anjouan'),
(699, 174, 'Grande Comore'),
(700, 174, 'Mohéli'),
(701, 175, 'Mayotte'),
(702, 178, 'Bouenza'),
(703, 178, 'CF03'),
(704, 178, 'Kouilou'),
(705, 178, 'Lékoumou'),
(706, 178, 'Likouala'),
(707, 178, 'Niari'),
(708, 178, 'Plateaux'),
(709, 178, 'Sangha'),
(710, 178, 'Pool'),
(711, 178, 'Brazzaville'),
(712, 178, 'Cuvette'),
(713, 178, 'Cuvette-Ouest'),
(714, 178, 'Pointe-Noire'),
(715, 180, 'Bandundu'),
(716, 180, 'Équateur'),
(717, 180, 'Kasaï-Occidental'),
(718, 180, 'Kasaï-Oriental'),
(719, 180, 'Katanga'),
(720, 180, 'Kinshasa'),
(721, 180, 'Bas-Congo'),
(722, 180, 'Orientale'),
(723, 180, 'Maniema'),
(724, 180, 'Nord-Kivu'),
(725, 180, 'Sud-Kivu'),
(726, 184, 'Cook Islands'),
(727, 188, 'Alajuela'),
(728, 188, 'Cartago'),
(729, 188, 'Guanacaste'),
(730, 188, 'Heredia'),
(731, 188, 'Limón'),
(732, 188, 'Puntarenas'),
(733, 188, 'San José'),
(734, 191, 'Bjelovarsko-Bilogorska'),
(735, 191, 'Brodsko-Posavska'),
(736, 191, 'Dubrovačko-Neretvanska'),
(737, 191, 'Istarska'),
(738, 191, 'Karlovačka'),
(739, 191, 'Koprivničko-Križevačka'),
(740, 191, 'Krapinsko-Zagorska'),
(741, 191, 'Ličko-Senjska'),
(742, 191, 'Međimurska'),
(743, 191, 'Osječko-Baranjska'),
(744, 191, 'Požeško-Slavonska'),
(745, 191, 'Primorsko-Goranska'),
(746, 191, 'Šibensko-Kniniska'),
(747, 191, 'Sisačko-Moslavačka'),
(748, 191, 'Splitsko-Dalmatinska'),
(749, 191, 'Varaždinska'),
(750, 191, 'Virovitičk-Podravska'),
(751, 191, 'Vukovarsko-Srijemska'),
(752, 191, 'Zadarska'),
(753, 191, 'Zagrebačka'),
(754, 191, 'Grad Zagreb'),
(755, 192, 'Pinar del Río'),
(756, 192, 'Ciudad de La Habana'),
(757, 192, 'Matanzas'),
(758, 192, 'Isla de la Juventud'),
(759, 192, 'Camagüey'),
(760, 192, 'Ciego de Ávila'),
(761, 192, 'Cienfuegos'),
(762, 192, 'Granma'),
(763, 192, 'Guantánamo'),
(764, 192, 'La Habana'),
(765, 192, 'Holguín'),
(766, 192, 'Las Tunas'),
(767, 192, 'Sancti Spíritus'),
(768, 192, 'Santiago de Cuba'),
(769, 192, 'Villa Clara'),
(770, 196, 'Famagusta'),
(771, 196, 'Kyrenia'),
(772, 196, 'Larnaca'),
(773, 196, 'Nicosia'),
(774, 196, 'Limassol'),
(775, 196, 'Paphos'),
(776, 203, 'Hradec Kralove'),
(777, 203, 'Jablonec nad Nisou'),
(778, 203, 'Jiein'),
(779, 203, 'Jihlava'),
(780, 203, 'Kolin'),
(781, 203, 'Liberec'),
(782, 203, 'Melnik'),
(783, 203, 'Mlada Boleslav'),
(784, 203, 'Nachod'),
(785, 203, 'Nymburk'),
(786, 203, 'Pardubice'),
(787, 203, 'Hlavní Mesto Praha'),
(788, 203, 'Semily'),
(789, 203, 'Trutnov'),
(790, 203, 'South Moravian Region'),
(791, 203, 'Jihočeský Kraj'),
(792, 203, 'Vysočina'),
(793, 203, 'Karlovarský Kraj'),
(794, 203, 'Královéhradecký Kraj'),
(795, 203, 'Liberecký Kraj'),
(796, 203, 'Olomoucký Kraj'),
(797, 203, 'Moravskoslezský Kraj'),
(798, 203, 'Pardubický Kraj'),
(799, 203, 'Plzeňský Kraj'),
(800, 203, 'Středočeský Kraj'),
(801, 203, 'Ústecký Kraj'),
(802, 203, 'Zlínský Kraj'),
(803, 204, 'Atakora'),
(804, 204, 'Atlantique'),
(805, 204, 'Alibori'),
(806, 204, 'Borgou'),
(807, 204, 'Collines'),
(808, 204, 'Kouffo'),
(809, 204, 'Donga'),
(810, 204, 'Littoral'),
(811, 204, 'Mono'),
(812, 204, 'Ouémé'),
(813, 204, 'Plateau'),
(814, 204, 'Zou'),
(815, 208, 'Århus'),
(816, 208, 'Bornholm'),
(817, 208, 'Frederiksborg'),
(818, 208, 'Fyn'),
(819, 208, 'Copenhagen city'),
(820, 208, 'København'),
(821, 208, 'Nordjylland'),
(822, 208, 'Ribe'),
(823, 208, 'Ringkøbing'),
(824, 208, 'Roskilde'),
(825, 208, 'Sønderjylland'),
(826, 208, 'Storstrøm'),
(827, 208, 'Vejle'),
(828, 208, 'Vestsjælland'),
(829, 208, 'Viborg'),
(830, 208, 'Fredriksberg'),
(831, 208, 'Capital Region'),
(832, 208, 'Central Jutland'),
(833, 208, 'North Jutland'),
(834, 208, 'Region Zealand'),
(835, 208, 'Region South Denmark'),
(836, 212, 'Saint Andrew'),
(837, 212, 'Saint David'),
(838, 212, 'Saint George'),
(839, 212, 'Saint John'),
(840, 212, 'Saint Joseph'),
(841, 212, 'Saint Luke'),
(842, 212, 'Saint Mark'),
(843, 212, 'Saint Patrick'),
(844, 212, 'Saint Paul'),
(845, 212, 'Saint Peter'),
(846, 214, 'Azua'),
(847, 214, 'Baoruco'),
(848, 214, 'Barahona'),
(849, 214, 'Dajabón'),
(850, 214, 'Duarte'),
(851, 214, 'Espaillat'),
(852, 214, 'Independencia'),
(853, 214, 'La Altagracia'),
(854, 214, 'Elías Piña'),
(855, 214, 'La Romana'),
(856, 214, 'María Trinidad Sánchez'),
(857, 214, 'Monte Cristi'),
(858, 214, 'Pedernales'),
(859, 214, 'Puerto Plata'),
(860, 214, 'Salcedo'),
(861, 214, 'Samaná'),
(862, 214, 'Sánchez Ramírez'),
(863, 214, 'San Juan'),
(864, 214, 'San Pedro de Macorís'),
(865, 214, 'Santiago'),
(866, 214, 'Santiago Rodríguez'),
(867, 214, 'Valverde'),
(868, 214, 'El Seíbo'),
(869, 214, 'Hato Mayor'),
(870, 214, 'La Vega'),
(871, 214, 'Monseñor Nouel'),
(872, 214, 'Monte Plata'),
(873, 214, 'San Cristóbal'),
(874, 214, 'Distrito Nacional'),
(875, 214, 'Peravia'),
(876, 214, 'San José de Ocoa'),
(877, 214, 'Santo Domingo'),
(878, 218, 'Galápagos'),
(879, 218, 'Azuay'),
(880, 218, 'Bolívar'),
(881, 218, 'Cañar'),
(882, 218, 'Carchi'),
(883, 218, 'Chimborazo'),
(884, 218, 'Cotopaxi'),
(885, 218, 'El Oro'),
(886, 218, 'Esmeraldas'),
(887, 218, 'Guayas'),
(888, 218, 'Imbabura'),
(889, 218, 'Loja'),
(890, 218, 'Los Ríos'),
(891, 218, 'Manabí'),
(892, 218, 'Morona-Santiago'),
(893, 218, 'Napo'),
(894, 218, 'Pastaza'),
(895, 218, 'Pichincha'),
(896, 218, 'Tungurahua'),
(897, 218, 'Zamora-Chinchipe'),
(898, 218, 'Sucumbios'),
(899, 218, 'Napo'),
(900, 218, 'Orellana'),
(901, 218, 'Provincia de Santa Elena'),
(902, 218, 'Provincia de Santo Domingo de los Tsáchilas'),
(903, 222, 'Ahuachapán'),
(904, 222, 'Cabañas'),
(905, 222, 'Chalatenango'),
(906, 222, 'Cuscatlán'),
(907, 222, 'La Libertad'),
(908, 222, 'La Paz'),
(909, 222, 'La Unión'),
(910, 222, 'Morazán'),
(911, 222, 'San Miguel'),
(912, 222, 'San Salvador'),
(913, 222, 'Santa Ana'),
(914, 222, 'San Vicente'),
(915, 222, 'Sonsonate'),
(916, 222, 'Usulután'),
(917, 226, 'Annobón'),
(918, 226, 'Bioko Norte'),
(919, 226, 'Bioko Sur'),
(920, 226, 'Centro Sur'),
(921, 226, 'Kié-Ntem'),
(922, 226, 'Litoral'),
(923, 226, 'Wele-Nzas'),
(924, 231, 'Arsi'),
(925, 231, 'Gonder'),
(926, 231, 'Bale'),
(927, 231, 'Eritrea'),
(928, 231, 'Gamo Gofa'),
(929, 231, 'Gojam'),
(930, 231, 'Harerge'),
(931, 231, 'Ilubabor'),
(932, 231, 'Kefa'),
(933, 231, 'Addis Abeba'),
(934, 231, 'Sidamo'),
(935, 231, 'Tigray'),
(936, 231, 'Welega Kifle Hager'),
(937, 231, 'Welo'),
(938, 231, 'Adis Abeba'),
(939, 231, 'Asosa'),
(940, 231, 'Borena '),
(941, 231, 'Debub Gonder'),
(942, 231, 'Debub Shewa'),
(943, 231, 'Debub Welo'),
(944, 231, 'Dire Dawa'),
(945, 231, 'Gambela'),
(946, 231, 'Metekel'),
(947, 231, 'Mirab Gojam'),
(948, 231, 'Mirab Harerge'),
(949, 231, 'Mirab Shewa'),
(950, 231, 'Misrak Gojam'),
(951, 231, 'Misrak Harerge'),
(952, 231, 'Nazret'),
(953, 231, 'Ogaden'),
(954, 231, 'Omo'),
(955, 231, 'Semen Gonder'),
(956, 231, 'Semen Shewa'),
(957, 231, 'Semen Welo'),
(958, 231, 'Tigray'),
(959, 231, 'Bale'),
(960, 231, 'Gamo Gofa'),
(961, 231, 'Ilubabor'),
(962, 231, 'Kefa'),
(963, 231, 'Sidamo'),
(964, 231, 'Welega'),
(965, 231, 'Ādīs Ābeba'),
(966, 231, 'Āfar'),
(967, 231, 'Āmara'),
(968, 231, 'Bīnshangul Gumuz'),
(969, 231, 'Dirē Dawa'),
(970, 231, 'Gambēla Hizboch'),
(971, 231, 'Hārerī Hizb'),
(972, 231, 'Oromīya'),
(973, 231, 'Sumalē'),
(974, 231, 'Tigray'),
(975, 231, 'YeDebub Bihēroch Bihēreseboch na Hizboch'),
(976, 232, 'Ānseba'),
(977, 232, 'Debub'),
(978, 232, 'Debubawī Kʼeyih Bahrī'),
(979, 232, 'Gash Barka'),
(980, 232, 'Maʼākel'),
(981, 232, 'Semēnawī Kʼeyih Bahrī'),
(982, 233, 'Harjumaa'),
(983, 233, 'Hiiumaa'),
(984, 233, 'Ida-Virumaa'),
(985, 233, 'Järvamaa'),
(986, 233, 'Jõgevamaa'),
(987, 233, 'Läänemaa'),
(988, 233, 'Lääne-Virumaa'),
(989, 233, 'Pärnumaa'),
(990, 233, 'Põlvamaa'),
(991, 233, 'Raplamaa'),
(992, 233, 'Saaremaa'),
(993, 233, 'Tartumaa'),
(994, 233, 'Valgamaa'),
(995, 233, 'Viljandimaa'),
(996, 233, 'Võrumaa'),
(997, 234, 'Norðoyar region'),
(998, 234, 'Eysturoy region'),
(999, 234, 'Sandoy region'),
(1000, 234, 'Streymoy region'),
(1001, 234, 'Suðuroy region'),
(1002, 234, 'Vágar region'),
(1003, 238, 'Falkland Islands (Islas Malvinas)'),
(1004, 239, 'South Georgia and The South Sandwich Islands '),
(1005, 242, 'Central'),
(1006, 242, 'Eastern'),
(1007, 242, 'Northern'),
(1008, 242, 'Rotuma'),
(1009, 242, 'Western'),
(1010, 246, 'Åland'),
(1011, 246, 'Hame'),
(1012, 246, 'Keski-Suomi'),
(1013, 246, 'Kuopio'),
(1014, 246, 'Kymi'),
(1015, 246, 'Lapponia'),
(1016, 246, 'Mikkeli'),
(1017, 246, 'Oulu'),
(1018, 246, 'Pohjois-Karjala'),
(1019, 246, 'Turku ja Pori'),
(1020, 246, 'Uusimaa'),
(1021, 246, 'Vaasa'),
(1022, 246, 'Southern Finland'),
(1023, 246, 'Eastern Finland'),
(1024, 246, 'Western Finland'),
(1025, 250, 'Aquitaine'),
(1026, 250, 'Auvergne'),
(1027, 250, 'Basse-Normandie'),
(1028, 250, 'Bourgogne'),
(1029, 250, 'Brittany'),
(1030, 250, 'Centre'),
(1031, 250, 'Champagne-Ardenne'),
(1032, 250, 'Corsica'),
(1033, 250, 'Franche-Comté'),
(1034, 250, 'Haute-Normandie'),
(1035, 250, 'Île-de-France'),
(1036, 250, 'Languedoc-Roussillon'),
(1037, 250, 'Limousin'),
(1038, 250, 'Lorraine'),
(1039, 250, 'Midi-Pyrénées'),
(1040, 250, 'Nord-Pas-de-Calais'),
(1041, 250, 'Pays de la Loire'),
(1042, 250, 'Picardie'),
(1043, 250, 'Poitou-Charentes'),
(1044, 250, 'Provence-Aes-Côte dʼAzur'),
(1045, 250, 'Rhône-Aes'),
(1046, 250, 'Alsace'),
(1047, 254, 'Guyane'),
(1048, 260, 'Saint-Paul-et-Amsterdam'),
(1049, 260, 'Crozet'),
(1050, 260, 'Kerguelen'),
(1051, 260, 'Terre Adélie'),
(1052, 260, 'Îles Éparses'),
(1053, 262, 'Ali Sabieh'),
(1054, 262, 'Dikhil   '),
(1055, 262, 'Djibouti  '),
(1056, 262, 'Obock'),
(1057, 262, 'Tadjourah'),
(1058, 262, 'Dikhil'),
(1059, 262, 'Djibouti'),
(1060, 262, 'Arta'),
(1061, 266, 'Estuaire'),
(1062, 266, 'Haut-Ogooué'),
(1063, 266, 'Moyen-Ogooué'),
(1064, 266, 'Ngounié'),
(1065, 266, 'Nyanga'),
(1066, 266, 'Ogooué-Ivindo'),
(1067, 266, 'Ogooué-Lolo'),
(1068, 266, 'Ogooué-Maritime'),
(1069, 266, 'Woleu-Ntem'),
(1070, 268, 'Ajaria'),
(1071, 268, 'Tʼbilisi'),
(1072, 268, 'Abkhazia'),
(1073, 268, 'Kvemo Kartli'),
(1074, 268, 'Kakheti'),
(1075, 268, 'Guria'),
(1076, 268, 'Imereti'),
(1077, 268, 'Shida Kartli'),
(1078, 268, 'Mtskheta-Mtianeti'),
(1079, 268, 'Racha-Lechkhumi and Kvemo Svaneti'),
(1080, 268, 'Samegrelo and Zemo Svaneti'),
(1081, 268, 'Samtskhe-Javakheti'),
(1082, 270, 'Banjul'),
(1083, 270, 'Lower River'),
(1084, 270, 'Central River'),
(1085, 270, 'Upper River'),
(1086, 270, 'Western'),
(1087, 270, 'North Bank'),
(1088, 275, 'Gaza Strip'),
(1089, 275, 'West Bank'),
(1090, 276, 'Baden-Württemberg'),
(1091, 276, 'Bavaria'),
(1092, 276, 'Bremen'),
(1093, 276, 'Hamburg'),
(1094, 276, 'Hesse'),
(1095, 276, 'Lower Saxony'),
(1096, 276, 'North Rhine-Westphalia'),
(1097, 276, 'Rhineland-Palatinate'),
(1098, 276, 'Saarland'),
(1099, 276, 'Schleswig-Holstein'),
(1100, 276, 'Brandenburg'),
(1101, 276, 'Mecklenburg-Vorpommern'),
(1102, 276, 'Saxony'),
(1103, 276, 'Saxony-Anhalt'),
(1104, 276, 'Thuringia'),
(1105, 276, 'Berlin'),
(1106, 288, 'Greater Accra'),
(1107, 288, 'Ashanti'),
(1108, 288, 'Brong-Ahafo Region'),
(1109, 288, 'Central'),
(1110, 288, 'Eastern'),
(1111, 288, 'Northern'),
(1112, 288, 'Volta'),
(1113, 288, 'Western'),
(1114, 288, 'Upper East'),
(1115, 288, 'Upper West'),
(1116, 292, 'Gibraltar'),
(1117, 296, 'Line Islands'),
(1118, 296, 'Gilbert Islands'),
(1119, 296, 'Phoenix Islands'),
(1120, 300, 'Mount Athos'),
(1121, 300, 'East Macedonia and Thrace'),
(1122, 300, 'Central Macedonia'),
(1123, 300, 'West Macedonia'),
(1124, 300, 'Thessaly'),
(1125, 300, 'Epirus'),
(1126, 300, 'Ionian Islands'),
(1127, 300, 'West Greece'),
(1128, 300, 'Central Greece'),
(1129, 300, 'Peloponnese'),
(1130, 300, 'Attica'),
(1131, 300, 'North Aegean'),
(1132, 300, 'South Aegean'),
(1133, 300, 'Crete'),
(1134, 304, 'Nordgrønland'),
(1135, 304, 'Østgrønland'),
(1136, 304, 'Vestgrønland'),
(1137, 308, 'Saint Andrew'),
(1138, 308, 'Saint David'),
(1139, 308, 'Saint George'),
(1140, 308, 'Saint John'),
(1141, 308, 'Saint Mark'),
(1142, 308, 'Saint Patrick'),
(1143, 312, 'Guadeloupe'),
(1144, 316, 'Guam'),
(1145, 320, 'Alta Verapaz'),
(1146, 320, 'Baja Verapaz'),
(1147, 320, 'Chimaltenango'),
(1148, 320, 'Chiquimula'),
(1149, 320, 'El Progreso'),
(1150, 320, 'Escuintla'),
(1151, 320, 'Guatemala'),
(1152, 320, 'Huehuetenango'),
(1153, 320, 'Izabal'),
(1154, 320, 'Jalapa'),
(1155, 320, 'Jutiapa'),
(1156, 320, 'Petén'),
(1157, 320, 'Quetzaltenango'),
(1158, 320, 'Quiché'),
(1159, 320, 'Retalhuleu'),
(1160, 320, 'Sacatepéquez'),
(1161, 320, 'San Marcos'),
(1162, 320, 'Santa Rosa'),
(1163, 320, 'Sololá'),
(1164, 320, 'Suchitepéquez'),
(1165, 320, 'Totonicapán'),
(1166, 320, 'Zacapa'),
(1167, 324, 'Beyla'),
(1168, 324, 'Boffa'),
(1169, 324, 'Boké'),
(1170, 324, 'Conakry'),
(1171, 324, 'Dabola'),
(1172, 324, 'Dalaba'),
(1173, 324, 'Dinguiraye'),
(1174, 324, 'Faranah'),
(1175, 324, 'Forécariah'),
(1176, 324, 'Fria'),
(1177, 324, 'Gaoual'),
(1178, 324, 'Guéckédou'),
(1179, 324, 'Kankan'),
(1180, 324, 'Kérouané'),
(1181, 324, 'Kindia'),
(1182, 324, 'Kissidougou'),
(1183, 324, 'Koundara'),
(1184, 324, 'Kouroussa'),
(1185, 324, 'Macenta'),
(1186, 324, 'Mali'),
(1187, 324, 'Mamou'),
(1188, 324, 'Pita'),
(1189, 324, 'Siguiri'),
(1190, 324, 'Télimélé'),
(1191, 324, 'Tougué'),
(1192, 324, 'Yomou'),
(1193, 324, 'Coyah'),
(1194, 324, 'Dubréka'),
(1195, 324, 'Kankan'),
(1196, 324, 'Koubia'),
(1197, 324, 'Labé'),
(1198, 324, 'Lélouma'),
(1199, 324, 'Lola'),
(1200, 324, 'Mandiana'),
(1201, 324, 'Nzérékoré'),
(1202, 324, 'Siguiri'),
(1203, 328, 'Barima-Waini'),
(1204, 328, 'Cuyuni-Mazaruni'),
(1205, 328, 'Demerara-Mahaica'),
(1206, 328, 'East Berbice-Corentyne'),
(1207, 328, 'Essequibo Islands-West Demerara'),
(1208, 328, 'Mahaica-Berbice'),
(1209, 328, 'Pomeroon-Supenaam'),
(1210, 328, 'Potaro-Siparuni'),
(1211, 328, 'Upper Demerara-Berbice'),
(1212, 328, 'Upper Takutu-Upper Essequibo'),
(1213, 332, 'Nord-Ouest'),
(1214, 332, 'Artibonite'),
(1215, 332, 'Centre'),
(1216, 332, 'Nord'),
(1217, 332, 'Nord-Est'),
(1218, 332, 'Ouest'),
(1219, 332, 'Sud'),
(1220, 332, 'Sud-Est'),
(1221, 332, 'GrandʼAnse'),
(1222, 332, 'Nippes'),
(1223, 336, 'Vatican City'),
(1224, 340, 'Atlántida'),
(1225, 340, 'Choluteca'),
(1226, 340, 'Colón'),
(1227, 340, 'Comayagua'),
(1228, 340, 'Copán'),
(1229, 340, 'Cortés'),
(1230, 340, 'El Paraíso'),
(1231, 340, 'Francisco Morazán'),
(1232, 340, 'Gracias a Dios'),
(1233, 340, 'Intibucá'),
(1234, 340, 'Islas de la Bahía'),
(1235, 340, 'La Paz'),
(1236, 340, 'Lempira'),
(1237, 340, 'Ocotepeque'),
(1238, 340, 'Olancho'),
(1239, 340, 'Santa Bárbara'),
(1240, 340, 'Valle'),
(1241, 340, 'Yoro'),
(1242, 344, 'Hong Kong'),
(1243, 348, 'Bács-Kiskun'),
(1244, 348, 'Baranya'),
(1245, 348, 'Békés'),
(1246, 348, 'Borsod-Abaúj-Zemplén'),
(1247, 348, 'Budapest'),
(1248, 348, 'Csongrád'),
(1249, 348, 'Fejér'),
(1250, 348, 'Győr-Moson-Sopron'),
(1251, 348, 'Hajdú-Bihar'),
(1252, 348, 'Heves'),
(1253, 348, 'Komárom-Esztergom'),
(1254, 348, 'Nógrád'),
(1255, 348, 'Pest'),
(1256, 348, 'Somogy'),
(1257, 348, 'Szabolcs-Szatmár-Bereg'),
(1258, 348, 'Jász-Nagykun-Szolnok'),
(1259, 348, 'Tolna'),
(1260, 348, 'Vas'),
(1261, 348, 'Veszprém'),
(1262, 348, 'Zala'),
(1263, 352, 'Borgarfjardarsysla'),
(1264, 352, 'Dalasysla'),
(1265, 352, 'Eyjafjardarsysla'),
(1266, 352, 'Gullbringusysla'),
(1267, 352, 'Hafnarfjördur'),
(1268, 352, 'Husavik'),
(1269, 352, 'Isafjördur'),
(1270, 352, 'Keflavik'),
(1271, 352, 'Kjosarsysla'),
(1272, 352, 'Kopavogur'),
(1273, 352, 'Myrasysla'),
(1274, 352, 'Neskaupstadur'),
(1275, 352, 'Nordur-Isafjardarsysla'),
(1276, 352, 'Nordur-Mulasysla'),
(1277, 352, 'Nordur-Tingeyjarsysla'),
(1278, 352, 'Olafsfjördur'),
(1279, 352, 'Rangarvallasysla'),
(1280, 352, 'Reykjavík'),
(1281, 352, 'Saudarkrokur'),
(1282, 352, 'Seydisfjordur'),
(1283, 352, 'Siglufjordur'),
(1284, 352, 'Skagafjardarsysla'),
(1285, 352, 'Snafellsnes- og Hnappadalssysla'),
(1286, 352, 'Strandasysla'),
(1287, 352, 'Sudur-Mulasysla'),
(1288, 352, 'Sudur-Tingeyjarsysla'),
(1289, 352, 'Vestmannaeyjar'),
(1290, 352, 'Vestur-Bardastrandarsysla'),
(1291, 352, 'Vestur-Hunavatnssysla'),
(1292, 352, 'Vestur-Isafjardarsysla'),
(1293, 352, 'Vestur-Skaftafellssysla'),
(1294, 352, 'East'),
(1295, 352, 'Capital Region'),
(1296, 352, 'Northeast'),
(1297, 352, 'Northwest'),
(1298, 352, 'South'),
(1299, 352, 'Southern Peninsula'),
(1300, 352, 'Westfjords'),
(1301, 352, 'West'),
(1302, 356, 'Andaman and Nicobar Islands'),
(1303, 356, 'Andhra Pradesh'),
(1304, 356, 'Assam'),
(1305, 356, 'Bihar'),
(1306, 356, 'Chandīgarh'),
(1307, 356, 'Dādra and Nagar Haveli'),
(1308, 356, 'NCT'),
(1309, 356, 'Gujarāt'),
(1310, 356, 'Haryana'),
(1311, 356, 'Himāchal Pradesh'),
(1312, 356, 'Kashmir'),
(1313, 356, 'Kerala'),
(1314, 356, 'Laccadives'),
(1315, 356, 'Madhya Pradesh '),
(1316, 356, 'Mahārāshtra'),
(1317, 356, 'Manipur'),
(1318, 356, 'Meghālaya'),
(1319, 356, 'Karnātaka'),
(1320, 356, 'Nāgāland'),
(1321, 356, 'Orissa'),
(1322, 356, 'Pondicherry'),
(1323, 356, 'Punjab'),
(1324, 356, 'State of Rājasthān'),
(1325, 356, 'Tamil Nādu'),
(1326, 356, 'Tripura'),
(1327, 356, 'Uttar Pradesh'),
(1328, 356, 'Bengal'),
(1329, 356, 'Sikkim'),
(1330, 356, 'Arunāchal Pradesh'),
(1331, 356, 'Mizoram'),
(1332, 356, 'Daman and Diu'),
(1333, 356, 'Goa'),
(1334, 356, 'Bihār'),
(1335, 356, 'Madhya Pradesh'),
(1336, 356, 'Uttar Pradesh'),
(1337, 356, 'Chhattisgarh'),
(1338, 356, 'Jharkhand'),
(1339, 356, 'Uttarakhand'),
(1340, 360, 'Aceh'),
(1341, 360, 'Bali'),
(1342, 360, 'Bengkulu'),
(1343, 360, 'Jakarta Raya'),
(1344, 360, 'Jambi'),
(1345, 360, 'Jawa Barat'),
(1346, 360, 'Central Java'),
(1347, 360, 'East Java'),
(1348, 360, 'Yogyakarta '),
(1349, 360, 'West Kalimantan'),
(1350, 360, 'South Kalimantan'),
(1351, 360, 'Kalimantan Tengah'),
(1352, 360, 'East Kalimantan'),
(1353, 360, 'Lampung'),
(1354, 360, 'Nusa Tenggara Barat'),
(1355, 360, 'East Nusa Tenggara'),
(1356, 360, 'Central Sulawesi'),
(1357, 360, 'Sulawesi Tenggara'),
(1358, 360, 'Sulawesi Utara'),
(1359, 360, 'West Sumatra'),
(1360, 360, 'Sumatera Selatan'),
(1361, 360, 'North Sumatra'),
(1362, 360, 'Timor Timur'),
(1363, 360, 'Maluku '),
(1364, 360, 'Maluku Utara'),
(1365, 360, 'West Java'),
(1366, 360, 'North Sulawesi'),
(1367, 360, 'South Sumatra'),
(1368, 360, 'Banten'),
(1369, 360, 'Gorontalo'),
(1370, 360, 'Bangka-Belitung'),
(1371, 360, 'Papua'),
(1372, 360, 'Riau'),
(1373, 360, 'South Sulawesi'),
(1374, 360, 'Irian Jaya Barat'),
(1375, 360, 'Riau Islands'),
(1376, 360, 'Sulawesi Barat'),
(1377, 364, 'Āz̄ārbāyjān-e Gharbī'),
(1378, 364, 'Chahār Maḩāll va Bakhtīārī'),
(1379, 364, 'Sīstān va Balūchestān'),
(1380, 364, 'Kohgīlūyeh va Būyer Aḩmad'),
(1381, 364, 'Fārs Province'),
(1382, 364, 'Gīlān'),
(1383, 364, 'Hamadān'),
(1384, 364, 'Īlām'),
(1385, 364, 'Hormozgān Province'),
(1386, 364, 'Kerman'),
(1387, 364, 'Kermānshāh'),
(1388, 364, 'Khūzestān'),
(1389, 364, 'Kordestān'),
(1390, 364, 'Mazandaran'),
(1391, 364, 'Markazi'),
(1392, 364, 'Zanjan'),
(1393, 364, 'Bushehr Province'),
(1394, 364, 'Lorestān'),
(1395, 364, 'Markazi'),
(1396, 364, 'Semnān Province'),
(1397, 364, 'Tehrān'),
(1398, 364, 'Zanjan'),
(1399, 364, 'Eşfahān'),
(1400, 364, 'Kermān'),
(1401, 364, 'Ostan-e Khorasan-e Razavi'),
(1402, 364, 'Yazd'),
(1403, 364, 'Ardabīl'),
(1404, 364, 'Āz̄ārbāyjān-e Sharqī'),
(1405, 364, 'Markazi Province'),
(1406, 364, 'Māzandarān Province'),
(1407, 364, 'Zanjan Province'),
(1408, 364, 'Golestān'),
(1409, 364, 'Qazvīn'),
(1410, 364, 'Qom'),
(1411, 364, 'Yazd'),
(1412, 364, 'Khorāsān-e Jonūbī'),
(1413, 364, 'Razavi Khorasan Province'),
(1414, 364, 'Khorāsān-e Shomālī'),
(1415, 368, 'Anbar'),
(1416, 368, 'Al Başrah'),
(1417, 368, 'Al Muthanná'),
(1418, 368, 'Al Qādisīyah'),
(1419, 368, 'As Sulaymānīyah'),
(1420, 368, 'Bābil'),
(1421, 368, 'Baghdād'),
(1422, 368, 'Dahūk'),
(1423, 368, 'Dhi Qar'),
(1424, 368, 'Diyala'),
(1425, 368, 'Arbīl'),
(1426, 368, 'Karbalāʼ'),
(1427, 368, 'At Taʼmīm'),
(1428, 368, 'Maysan'),
(1429, 368, 'Nīnawá'),
(1430, 368, 'Wāsiţ'),
(1431, 368, 'An Najaf'),
(1432, 368, 'Şalāḩ ad Dīn'),
(1433, 372, 'Carlow'),
(1434, 372, 'Cavan'),
(1435, 372, 'County Clare'),
(1436, 372, 'Cork'),
(1437, 372, 'Donegal'),
(1438, 372, 'Galway'),
(1439, 372, 'County Kerry'),
(1440, 372, 'County Kildare'),
(1441, 372, 'County Kilkenny'),
(1442, 372, 'Leitrim'),
(1443, 372, 'Laois'),
(1444, 372, 'Limerick'),
(1445, 372, 'County Longford'),
(1446, 372, 'County Louth'),
(1447, 372, 'County Mayo'),
(1448, 372, 'County Meath'),
(1449, 372, 'Monaghan'),
(1450, 372, 'County Offaly'),
(1451, 372, 'County Roscommon'),
(1452, 372, 'County Sligo'),
(1453, 372, 'County Waterford'),
(1454, 372, 'County Westmeath'),
(1455, 372, 'County Wexford'),
(1456, 372, 'County Wicklow'),
(1457, 372, 'Dún Laoghaire-Rathdown'),
(1458, 372, 'Fingal County'),
(1459, 372, 'Tipperary North Riding'),
(1460, 372, 'South Dublin'),
(1461, 372, 'Tipperary South Riding'),
(1462, 376, 'HaDarom'),
(1463, 376, 'HaMerkaz'),
(1464, 376, 'Northern District'),
(1465, 376, 'H̱efa'),
(1466, 376, 'Tel Aviv'),
(1467, 376, 'Yerushalayim'),
(1468, 380, 'Abruzzo'),
(1469, 380, 'Basilicate'),
(1470, 380, 'Calabria'),
(1471, 380, 'Campania'),
(1472, 380, 'Emilia-Romagna'),
(1473, 380, 'Friuli'),
(1474, 380, 'Lazio'),
(1475, 380, 'Liguria'),
(1476, 380, 'Lombardy'),
(1477, 380, 'The Marches'),
(1478, 380, 'Molise'),
(1479, 380, 'Piedmont'),
(1480, 380, 'Apulia'),
(1481, 380, 'Sardinia'),
(1482, 380, 'Sicily'),
(1483, 380, 'Tuscany'),
(1484, 380, 'Trentino-Alto Adige'),
(1485, 380, 'Umbria'),
(1486, 380, 'Aosta Valley'),
(1487, 380, 'Veneto'),
(1488, 384, 'Vaaraíso Region'),
(1489, 384, 'Antofagasta Region'),
(1490, 384, 'Araucanía Region'),
(1491, 384, 'Atacama Region'),
(1492, 384, 'Biobío Region'),
(1493, 384, 'Coquimbo Region'),
(1494, 384, 'Maule Region'),
(1495, 384, 'Santiago Metropolitan Region'),
(1496, 384, 'Danane'),
(1497, 384, 'Divo'),
(1498, 384, 'Ferkessedougou'),
(1499, 384, 'Gagnoa'),
(1500, 384, 'Katiola'),
(1501, 384, 'Korhogo'),
(1502, 384, 'Odienne'),
(1503, 384, 'Seguela'),
(1504, 384, 'Touba'),
(1505, 384, 'Bongouanou'),
(1506, 384, 'Issia'),
(1507, 384, 'Lakota'),
(1508, 384, 'Mankono'),
(1509, 384, 'Oume'),
(1510, 384, 'Soubre'),
(1511, 384, 'Tingrela'),
(1512, 384, 'Zuenoula'),
(1513, 384, 'Bangolo'),
(1514, 384, 'Beoumi'),
(1515, 384, 'Bondoukou'),
(1516, 384, 'Bouafle'),
(1517, 384, 'Bouake'),
(1518, 384, 'Daloa'),
(1519, 384, 'Daoukro'),
(1520, 384, 'Duekoue'),
(1521, 384, 'Grand-Lahou'),
(1522, 384, 'Man'),
(1523, 384, 'Mbahiakro'),
(1524, 384, 'Sakassou'),
(1525, 384, 'San Pedro'),
(1526, 384, 'Sassandra'),
(1527, 384, 'Sinfra'),
(1528, 384, 'Tabou'),
(1529, 384, 'Tanda'),
(1530, 384, 'Tiassale'),
(1531, 384, 'Toumodi'),
(1532, 384, 'Vavoua'),
(1533, 384, 'Abidjan'),
(1534, 384, 'Aboisso'),
(1535, 384, 'Adiake'),
(1536, 384, 'Alepe'),
(1537, 384, 'Bocanda'),
(1538, 384, 'Dabou'),
(1539, 384, 'Dimbokro'),
(1540, 384, 'Grand-Bassam'),
(1541, 384, 'Guiglo'),
(1542, 384, 'Jacqueville'),
(1543, 384, 'Tiebissou'),
(1544, 384, 'Toulepleu'),
(1545, 384, 'Yamoussoukro'),
(1546, 384, 'Agnéby'),
(1547, 384, 'Bafing'),
(1548, 384, 'Bas-Sassandra'),
(1549, 384, 'Denguélé'),
(1550, 384, 'Dix-Huit Montagnes'),
(1551, 384, 'Fromager'),
(1552, 384, 'Haut-Sassandra'),
(1553, 384, 'Lacs'),
(1554, 384, 'Lagunes'),
(1555, 384, 'Marahoué'),
(1556, 384, 'Moyen-Cavally'),
(1557, 384, 'Moyen-Comoé'),
(1558, 384, 'Nʼzi-Comoé'),
(1559, 384, 'Savanes'),
(1560, 384, 'Sud-Bandama'),
(1561, 384, 'Sud-Comoé'),
(1562, 384, 'Vallée du Bandama'),
(1563, 384, 'Worodougou'),
(1564, 384, 'Zanzan'),
(1565, 388, 'Clarendon'),
(1566, 388, 'Hanover Parish'),
(1567, 388, 'Manchester'),
(1568, 388, 'Portland'),
(1569, 388, 'Saint Andrew'),
(1570, 388, 'Saint Ann'),
(1571, 388, 'Saint Catherine'),
(1572, 388, 'St. Elizabeth'),
(1573, 388, 'Saint James'),
(1574, 388, 'Saint Mary'),
(1575, 388, 'Saint Thomas'),
(1576, 388, 'Trelawny'),
(1577, 388, 'Westmoreland'),
(1578, 388, 'Kingston'),
(1579, 392, 'Aichi'),
(1580, 392, 'Akita'),
(1581, 392, 'Aomori'),
(1582, 392, 'Chiba'),
(1583, 392, 'Ehime'),
(1584, 392, 'Fukui'),
(1585, 392, 'Fukuoka'),
(1586, 392, 'Fukushima'),
(1587, 392, 'Gifu'),
(1588, 392, 'Gumma'),
(1589, 392, 'Hiroshima'),
(1590, 392, 'Hokkaidō'),
(1591, 392, 'Hyōgo'),
(1592, 392, 'Ibaraki'),
(1593, 392, 'Ishikawa'),
(1594, 392, 'Iwate'),
(1595, 392, 'Kagawa'),
(1596, 392, 'Kagoshima'),
(1597, 392, 'Kanagawa'),
(1598, 392, 'Kōchi'),
(1599, 392, 'Kumamoto'),
(1600, 392, 'Kyōto'),
(1601, 392, 'Mie'),
(1602, 392, 'Miyagi'),
(1603, 392, 'Miyazaki'),
(1604, 392, 'Nagano'),
(1605, 392, 'Nagasaki'),
(1606, 392, 'Nara'),
(1607, 392, 'Niigata'),
(1608, 392, 'Ōita'),
(1609, 392, 'Okayama'),
(1610, 392, 'Ōsaka'),
(1611, 392, 'Saga'),
(1612, 392, 'Saitama'),
(1613, 392, 'Shiga'),
(1614, 392, 'Shimane'),
(1615, 392, 'Shizuoka'),
(1616, 392, 'Tochigi'),
(1617, 392, 'Tokushima'),
(1618, 392, 'Tōkyō'),
(1619, 392, 'Tottori'),
(1620, 392, 'Toyama'),
(1621, 392, 'Wakayama'),
(1622, 392, 'Yamagata'),
(1623, 392, 'Yamaguchi'),
(1624, 392, 'Yamanashi'),
(1625, 392, 'Okinawa'),
(1626, 398, 'Almaty'),
(1627, 398, 'Almaty Qalasy'),
(1628, 398, 'Aqmola'),
(1629, 398, 'Aqtöbe'),
(1630, 398, 'Astana Qalasy'),
(1631, 398, 'Atyraū'),
(1632, 398, 'Batys Qazaqstan'),
(1633, 398, 'Bayqongyr Qalasy'),
(1634, 398, 'Mangghystaū'),
(1635, 398, 'Ongtüstik Qazaqstan'),
(1636, 398, 'Pavlodar'),
(1637, 398, 'Qaraghandy'),
(1638, 398, 'Qostanay'),
(1639, 398, 'Qyzylorda'),
(1640, 398, 'East Kazakhstan'),
(1641, 398, 'Soltüstik Qazaqstan'),
(1642, 398, 'Zhambyl'),
(1643, 400, 'Balqa'),
(1644, 400, 'Ma’an'),
(1645, 400, 'Karak'),
(1646, 400, 'Al Mafraq'),
(1647, 400, 'Tafielah'),
(1648, 400, 'Az Zarqa'),
(1649, 400, 'Irbid'),
(1650, 400, 'Mafraq'),
(1651, 400, 'Amman'),
(1652, 400, 'Zarqa'),
(1653, 400, 'Irbid'),
(1654, 400, 'Ma’an'),
(1655, 400, 'Ajlun'),
(1656, 400, 'Aqaba'),
(1657, 400, 'Jerash'),
(1658, 400, 'Madaba'),
(1659, 404, 'Central'),
(1660, 404, 'Coast'),
(1661, 404, 'Eastern'),
(1662, 404, 'Nairobi Area'),
(1663, 404, 'North-Eastern'),
(1664, 404, 'Nyanza'),
(1665, 404, 'Rift Valley'),
(1666, 404, 'Western'),
(1667, 408, 'Chagang-do'),
(1668, 408, 'Hamgyŏng-namdo'),
(1669, 408, 'Hwanghae-namdo'),
(1670, 408, 'Hwanghae-bukto'),
(1671, 408, 'Kaesŏng-si'),
(1672, 408, 'Gangwon'),
(1673, 408, 'Pʼyŏngan-bukto'),
(1674, 408, 'Pʼyŏngyang-si'),
(1675, 408, 'Yanggang-do'),
(1676, 408, 'Nampʼo-si'),
(1677, 408, 'Pʼyŏngan-namdo'),
(1678, 408, 'Hamgyŏng-bukto'),
(1679, 408, 'Najin Sŏnbong-si'),
(1680, 410, 'Jeju'),
(1681, 410, 'North Jeolla'),
(1682, 410, 'North Chungcheong'),
(1683, 410, 'Gangwon'),
(1684, 410, 'Busan'),
(1685, 410, 'Seoul'),
(1686, 410, 'Incheon'),
(1687, 410, 'Gyeonggi'),
(1688, 410, 'North Gyeongsang'),
(1689, 410, 'Daegu'),
(1690, 410, 'South Jeolla'),
(1691, 410, 'South Chungcheong'),
(1692, 410, 'Gwangju'),
(1693, 410, 'Daejeon'),
(1694, 410, 'South Gyeongsang'),
(1695, 410, 'Ulsan'),
(1696, 414, 'Muḩāfaz̧atalWafrah'),
(1697, 414, 'Al ‘Āşimah'),
(1698, 414, 'Al Aḩmadī'),
(1699, 414, 'Al Jahrāʼ'),
(1700, 414, 'Al Farwaniyah'),
(1701, 414, 'Ḩawallī'),
(1702, 417, 'Bishkek'),
(1703, 417, 'Chüy'),
(1704, 417, 'Jalal-Abad'),
(1705, 417, 'Naryn'),
(1706, 417, 'Talas'),
(1707, 417, 'Ysyk-Köl'),
(1708, 417, 'Osh'),
(1709, 417, 'Batken'),
(1710, 418, 'Attapu'),
(1711, 418, 'Champasak'),
(1712, 418, 'Houaphan'),
(1713, 418, 'Khammouan'),
(1714, 418, 'Louang Namtha'),
(1715, 418, 'Louangphrabang'),
(1716, 418, 'Oudômxai'),
(1717, 418, 'Phongsali'),
(1718, 418, 'Saravan'),
(1719, 418, 'Savannakhet'),
(1720, 418, 'Vientiane'),
(1721, 418, 'Xiagnabouli'),
(1722, 418, 'Xiangkhoang'),
(1723, 418, 'Khammouan'),
(1724, 418, 'Loungnamtha'),
(1725, 418, 'Louangphabang'),
(1726, 418, 'Phôngsali'),
(1727, 418, 'Salavan'),
(1728, 418, 'Savannahkhét'),
(1729, 418, 'Bokèo'),
(1730, 418, 'Bolikhamxai'),
(1731, 418, 'Viangchan'),
(1732, 418, 'Xékong'),
(1733, 418, 'Viangchan'),
(1734, 422, 'Béqaa'),
(1735, 422, 'Liban-Nord'),
(1736, 422, 'Beyrouth'),
(1737, 422, 'Mont-Liban'),
(1738, 422, 'Liban-Sud'),
(1739, 422, 'Nabatîyé'),
(1740, 422, 'Béqaa'),
(1741, 422, 'Liban-Nord'),
(1742, 422, 'Aakkâr'),
(1743, 422, 'Baalbek-Hermel'),
(1744, 426, 'Balzers Commune'),
(1745, 426, 'Eschen Commune'),
(1746, 426, 'Gamprin Commune'),
(1747, 426, 'Mauren Commune'),
(1748, 426, 'Planken Commune'),
(1749, 426, 'Ruggell Commune'),
(1750, 426, 'Berea District'),
(1751, 426, 'Butha-Buthe District'),
(1752, 426, 'Leribe District'),
(1753, 426, 'Mafeteng'),
(1754, 426, 'Maseru'),
(1755, 426, 'Mohaleʼs Hoek'),
(1756, 426, 'Mokhotlong'),
(1757, 426, 'Qachaʼs Nek'),
(1758, 426, 'Quthing District'),
(1759, 426, 'Thaba-Tseka District'),
(1760, 428, 'Dobeles Rajons'),
(1761, 428, 'Aizkraukles Rajons'),
(1762, 428, 'Alūksnes Rajons'),
(1763, 428, 'Balvu Rajons'),
(1764, 428, 'Bauskas Rajons'),
(1765, 428, 'Cēsu Rajons'),
(1766, 428, 'Daugavpils'),
(1767, 428, 'Daugavpils Rajons'),
(1768, 428, 'Dobeles Rajons'),
(1769, 428, 'Gulbenes Rajons'),
(1770, 428, 'Jēkabpils Rajons'),
(1771, 428, 'Jelgava'),
(1772, 428, 'Jelgavas Rajons'),
(1773, 428, 'Jūrmala'),
(1774, 428, 'Krāslavas Rajons'),
(1775, 428, 'Kuldīgas Rajons'),
(1776, 428, 'Liepāja'),
(1777, 428, 'Liepājas Rajons'),
(1778, 428, 'Limbažu Rajons'),
(1779, 428, 'Ludzas Rajons'),
(1780, 428, 'Madonas Rajons'),
(1781, 428, 'Ogres Rajons'),
(1782, 428, 'Preiļu Rajons'),
(1783, 428, 'Rēzekne'),
(1784, 428, 'Rēzeknes Rajons'),
(1785, 428, 'Rīga'),
(1786, 428, 'Rīgas Rajons'),
(1787, 428, 'Saldus Rajons'),
(1788, 428, 'Talsu Rajons'),
(1789, 428, 'Tukuma Rajons'),
(1790, 428, 'Valkas Rajons'),
(1791, 428, 'Valmieras Rajons'),
(1792, 428, 'Ventspils'),
(1793, 428, 'Ventspils Rajons'),
(1794, 430, 'Bong'),
(1795, 430, 'Grand Jide'),
(1796, 430, 'Grand Cape Mount'),
(1797, 430, 'Lofa'),
(1798, 430, 'Nimba'),
(1799, 430, 'Sinoe'),
(1800, 430, 'Grand Bassa County'),
(1801, 430, 'Grand Cape Mount'),
(1802, 430, 'Maryland'),
(1803, 430, 'Montserrado'),
(1804, 430, 'Bomi'),
(1805, 430, 'Grand Kru'),
(1806, 430, 'Margibi'),
(1807, 430, 'River Cess'),
(1808, 430, 'Grand Gedeh'),
(1809, 430, 'Lofa'),
(1810, 430, 'Gbarpolu'),
(1811, 430, 'River Gee'),
(1812, 434, 'Al Abyār'),
(1813, 434, 'Al ‘Azīzīyah'),
(1814, 434, 'Al Bayḑā’'),
(1815, 434, 'Al Jufrah'),
(1816, 434, 'Al Jumayl'),
(1817, 434, 'Al Kufrah'),
(1818, 434, 'Al Marj'),
(1819, 434, 'Al Qarābūll'),
(1820, 434, 'Al Qubbah'),
(1821, 434, 'Al Ajaylāt'),
(1822, 434, 'Ash Shāţiʼ'),
(1823, 434, 'Az Zahra’'),
(1824, 434, 'Banī Walīd'),
(1825, 434, 'Bin Jawwād'),
(1826, 434, 'Ghāt'),
(1827, 434, 'Jādū'),
(1828, 434, 'Jālū'),
(1829, 434, 'Janzūr'),
(1830, 434, 'Masallatah'),
(1831, 434, 'Mizdah'),
(1832, 434, 'Murzuq'),
(1833, 434, 'Nālūt'),
(1834, 434, 'Qamīnis'),
(1835, 434, 'Qaşr Bin Ghashīr'),
(1836, 434, 'Sabhā'),
(1837, 434, 'Şabrātah'),
(1838, 434, 'Shaḩḩāt'),
(1839, 434, 'Şurmān'),
(1840, 434, 'Tajura’ '),
(1841, 434, 'Tarhūnah'),
(1842, 434, 'Ţubruq'),
(1843, 434, 'Tūkrah'),
(1844, 434, 'Zlīţan'),
(1845, 434, 'Zuwārah'),
(1846, 434, 'Ajdābiyā'),
(1847, 434, 'Al Fātiḩ'),
(1848, 434, 'Al Jabal al Akhḑar'),
(1849, 434, 'Al Khums'),
(1850, 434, 'An Nuqāţ al Khams'),
(1851, 434, 'Awbārī'),
(1852, 434, 'Az Zāwiyah'),
(1853, 434, 'Banghāzī'),
(1854, 434, 'Darnah'),
(1855, 434, 'Ghadāmis'),
(1856, 434, 'Gharyān'),
(1857, 434, 'Mişrātah'),
(1858, 434, 'Sawfajjīn'),
(1859, 434, 'Surt'),
(1860, 434, 'Ţarābulus'),
(1861, 434, 'Yafran'),
(1862, 438, 'Balzers'),
(1863, 438, 'Eschen'),
(1864, 438, 'Gamprin'),
(1865, 438, 'Mauren'),
(1866, 438, 'Planken'),
(1867, 438, 'Ruggell'),
(1868, 438, 'Schaan'),
(1869, 438, 'Schellenberg'),
(1870, 438, 'Triesen'),
(1871, 438, 'Triesenberg'),
(1872, 438, 'Vaduz'),
(1873, 440, 'Alytaus Apskritis'),
(1874, 440, 'Kauno Apskritis'),
(1875, 440, 'Klaipėdos Apskritis'),
(1876, 440, 'Marijampolės Apskritis'),
(1877, 440, 'Panevėžio Apskritis'),
(1878, 440, 'Šiaulių Apskritis'),
(1879, 440, 'Tauragės Apskritis'),
(1880, 440, 'Telšių Apskritis'),
(1881, 440, 'Utenos Apskritis'),
(1882, 440, 'Vilniaus Apskritis'),
(1883, 442, 'Diekirch'),
(1884, 442, 'Grevenmacher'),
(1885, 442, 'Luxembourg'),
(1886, 446, 'Ilhas'),
(1887, 446, 'Macau'),
(1888, 450, 'Antsiranana'),
(1889, 450, 'Fianarantsoa'),
(1890, 450, 'Mahajanga'),
(1891, 450, 'Toamasina'),
(1892, 450, 'Antananarivo'),
(1893, 450, 'Toliara'),
(1894, 454, 'Chikwawa'),
(1895, 454, 'Chiradzulu'),
(1896, 454, 'Chitipa'),
(1897, 454, 'Thyolo'),
(1898, 454, 'Dedza'),
(1899, 454, 'Dowa'),
(1900, 454, 'Karonga'),
(1901, 454, 'Kasungu'),
(1902, 454, 'Lilongwe'),
(1903, 454, 'Mangochi'),
(1904, 454, 'Mchinji'),
(1905, 454, 'Mzimba'),
(1906, 454, 'Ntcheu'),
(1907, 454, 'Nkhata Bay'),
(1908, 454, 'Nkhotakota'),
(1909, 454, 'Nsanje'),
(1910, 454, 'Ntchisi'),
(1911, 454, 'Rumphi'),
(1912, 454, 'Salima'),
(1913, 454, 'Zomba'),
(1914, 454, 'Blantyre'),
(1915, 454, 'Mwanza'),
(1916, 454, 'Balaka'),
(1917, 454, 'Likoma'),
(1918, 454, 'Machinga'),
(1919, 454, 'Mulanje'),
(1920, 454, 'Phalombe'),
(1921, 458, 'Johor'),
(1922, 458, 'Kedah'),
(1923, 458, 'Kelantan'),
(1924, 458, 'Melaka'),
(1925, 458, 'Negeri Sembilan'),
(1926, 458, 'Pahang'),
(1927, 458, 'Perak'),
(1928, 458, 'Perlis'),
(1929, 458, 'Pulau Pinang'),
(1930, 458, 'Sarawak'),
(1931, 458, 'Selangor'),
(1932, 458, 'Terengganu'),
(1933, 458, 'Kuala Lumpur'),
(1934, 458, 'Federal Territory of Labuan'),
(1935, 458, 'Sabah'),
(1936, 458, 'Putrajaya'),
(1937, 462, 'Maale'),
(1938, 462, 'Seenu'),
(1939, 462, 'Alifu Atholhu'),
(1940, 462, 'Lhaviyani Atholhu'),
(1941, 462, 'Vaavu Atholhu'),
(1942, 462, 'Laamu'),
(1943, 462, 'Haa Alifu Atholhu'),
(1944, 462, 'Thaa Atholhu'),
(1945, 462, 'Meemu Atholhu'),
(1946, 462, 'Raa Atholhu'),
(1947, 462, 'Faafu Atholhu'),
(1948, 462, 'Dhaalu Atholhu'),
(1949, 462, 'Baa Atholhu'),
(1950, 462, 'Haa Dhaalu Atholhu'),
(1951, 462, 'Shaviyani Atholhu'),
(1952, 462, 'Noonu Atholhu'),
(1953, 462, 'Kaafu Atholhu'),
(1954, 462, 'Gaafu Alifu Atholhu'),
(1955, 462, 'Gaafu Dhaalu Atholhu'),
(1956, 462, 'Gnyaviyani Atoll'),
(1957, 462, 'Alifu'),
(1958, 462, 'Baa'),
(1959, 462, 'Dhaalu'),
(1960, 462, 'Faafu'),
(1961, 462, 'Gaafu Alifu'),
(1962, 462, 'Gaafu Dhaalu'),
(1963, 462, 'Haa Alifu'),
(1964, 462, 'Haa Dhaalu'),
(1965, 462, 'Kaafu'),
(1966, 462, 'Lhaviyani'),
(1967, 462, 'Maale'),
(1968, 462, 'Meemu'),
(1969, 462, 'Gnaviyani'),
(1970, 462, 'Noonu'),
(1971, 462, 'Raa'),
(1972, 462, 'Shaviyani'),
(1973, 462, 'Thaa'),
(1974, 462, 'Vaavu'),
(1975, 466, 'Bamako'),
(1976, 466, 'Gao'),
(1977, 466, 'Kayes'),
(1978, 466, 'Mopti'),
(1979, 466, 'Ségou'),
(1980, 466, 'Sikasso'),
(1981, 466, 'Koulikoro'),
(1982, 466, 'Tombouctou'),
(1983, 466, 'Gao'),
(1984, 466, 'Kidal'),
(1985, 470, 'Malta'),
(1986, 474, 'Martinique'),
(1987, 478, 'Nouakchott'),
(1988, 478, 'Hodh Ech Chargui'),
(1989, 478, 'Hodh El Gharbi'),
(1990, 478, 'Assaba'),
(1991, 478, 'Gorgol'),
(1992, 478, 'Brakna'),
(1993, 478, 'Trarza'),
(1994, 478, 'Adrar'),
(1995, 478, 'Dakhlet Nouadhibou'),
(1996, 478, 'Tagant'),
(1997, 478, 'Guidimaka'),
(1998, 478, 'Tiris Zemmour'),
(1999, 478, 'Inchiri'),
(2000, 480, 'Black River'),
(2001, 480, 'Flacq'),
(2002, 480, 'Grand Port'),
(2003, 480, 'Moka'),
(2004, 480, 'Pamplemousses'),
(2005, 480, 'Plaines Wilhems'),
(2006, 480, 'Port Louis'),
(2007, 480, 'Rivière du Rempart'),
(2008, 480, 'Savanne'),
(2009, 480, 'Agalega Islands'),
(2010, 480, 'Cargados Carajos'),
(2011, 480, 'Rodrigues'),
(2012, 484, 'Aguascalientes'),
(2013, 484, 'Baja California'),
(2014, 484, 'Baja California Sur'),
(2015, 484, 'Campeche'),
(2016, 484, 'Chiapas'),
(2017, 484, 'Chihuahua'),
(2018, 484, 'Coahuila'),
(2019, 484, 'Colima'),
(2020, 484, 'The Federal District'),
(2021, 484, 'Durango'),
(2022, 484, 'Guanajuato'),
(2023, 484, 'Guerrero'),
(2024, 484, 'Hidalgo'),
(2025, 484, 'Jalisco'),
(2026, 484, 'México'),
(2027, 484, 'Michoacán'),
(2028, 484, 'Morelos'),
(2029, 484, 'Nayarit'),
(2030, 484, 'Nuevo León'),
(2031, 484, 'Oaxaca'),
(2032, 484, 'Puebla'),
(2033, 484, 'Querétaro'),
(2034, 484, 'Quintana Roo'),
(2035, 484, 'San Luis Potosí'),
(2036, 484, 'Sinaloa'),
(2037, 484, 'Sonora'),
(2038, 484, 'Tabasco'),
(2039, 484, 'Tamaulipas'),
(2040, 484, 'Tlaxcala'),
(2041, 484, 'Veracruz-Llave'),
(2042, 484, 'Yucatán');
INSERT INTO `states` (`state_id`, `country_id`, `name`) VALUES
(2043, 484, 'Zacatecas'),
(2044, 492, 'Monaco'),
(2045, 496, 'Arhangay'),
(2046, 496, 'Bayanhongor'),
(2047, 496, 'Bayan-Ölgiy'),
(2048, 496, 'East Aimak'),
(2049, 496, 'East Gobi Aymag'),
(2050, 496, 'Middle Govĭ'),
(2051, 496, 'Dzavhan'),
(2052, 496, 'Govĭ-Altay'),
(2053, 496, 'Hentiy'),
(2054, 496, 'Hovd'),
(2055, 496, 'Hövsgöl'),
(2056, 496, 'South Gobi Aimak'),
(2057, 496, 'South Hangay'),
(2058, 496, 'Selenge'),
(2059, 496, 'Sühbaatar'),
(2060, 496, 'Central Aimak'),
(2061, 496, 'Uvs'),
(2062, 496, 'Ulaanbaatar'),
(2063, 496, 'Bulgan'),
(2064, 496, 'Darhan Uul'),
(2065, 496, 'Govĭ-Sumber'),
(2066, 496, 'Orhon'),
(2067, 498, 'Ungheni Judetul'),
(2068, 498, 'Balti'),
(2069, 498, 'Cahul'),
(2070, 498, 'Stinga Nistrului'),
(2071, 498, 'Edinet'),
(2072, 498, 'Găgăuzia'),
(2073, 498, 'Lapusna'),
(2074, 498, 'Orhei'),
(2075, 498, 'Soroca'),
(2076, 498, 'Tighina'),
(2077, 498, 'Ungheni'),
(2078, 498, 'Chişinău'),
(2079, 498, 'Stînga Nistrului'),
(2080, 498, 'Raionul Anenii Noi'),
(2081, 498, 'Bălţi'),
(2082, 498, 'Raionul Basarabeasca'),
(2083, 498, 'Bender'),
(2084, 498, 'Raionul Briceni'),
(2085, 498, 'Raionul Cahul'),
(2086, 498, 'Raionul Cantemir'),
(2087, 498, 'Călăraşi'),
(2088, 498, 'Căuşeni'),
(2089, 498, 'Raionul Cimişlia'),
(2090, 498, 'Raionul Criuleni'),
(2091, 498, 'Raionul Donduşeni'),
(2092, 498, 'Raionul Drochia'),
(2093, 498, 'Dubăsari'),
(2094, 498, 'Raionul Edineţ'),
(2095, 498, 'Raionul Făleşti'),
(2096, 498, 'Raionul Floreşti'),
(2097, 498, 'Raionul Glodeni'),
(2098, 498, 'Raionul Hînceşti'),
(2099, 498, 'Raionul Ialoveni'),
(2100, 498, 'Raionul Leova'),
(2101, 498, 'Raionul Nisporeni'),
(2102, 498, 'Raionul Ocniţa'),
(2103, 498, 'Raionul Orhei'),
(2104, 498, 'Raionul Rezina'),
(2105, 498, 'Raionul Rîşcani'),
(2106, 498, 'Raionul Sîngerei'),
(2107, 498, 'Raionul Şoldăneşti'),
(2108, 498, 'Raionul Soroca'),
(2109, 498, 'Ştefan-Vodă'),
(2110, 498, 'Raionul Străşeni'),
(2111, 498, 'Raionul Taraclia'),
(2112, 498, 'Raionul Teleneşti'),
(2113, 498, 'Raionul Ungheni'),
(2114, 499, 'Opština Andrijevica'),
(2115, 499, 'Opština Bar'),
(2116, 499, 'Opština Berane'),
(2117, 499, 'Opština Bijelo Polje'),
(2118, 499, 'Opština Budva'),
(2119, 499, 'Opština Cetinje'),
(2120, 499, 'Opština Danilovgrad'),
(2121, 499, 'Opština Herceg Novi'),
(2122, 499, 'Opština Kolašin'),
(2123, 499, 'Opština Kotor'),
(2124, 499, 'Opština Mojkovac'),
(2125, 499, 'Opština Nikšić'),
(2126, 499, 'Opština Plav'),
(2127, 499, 'Opština Pljevlja'),
(2128, 499, 'Opština Plužine'),
(2129, 499, 'Opština Podgorica'),
(2130, 499, 'Opština Rožaje'),
(2131, 499, 'Opština Šavnik'),
(2132, 499, 'Opština Tivat'),
(2133, 499, 'Opština Ulcinj'),
(2134, 499, 'Opština Žabljak'),
(2135, 500, 'Saint Anthony'),
(2136, 500, 'Saint Georges'),
(2137, 500, 'Saint Peter'),
(2138, 504, 'Agadir'),
(2139, 504, 'Al Hoceïma'),
(2140, 504, 'Azizal'),
(2141, 504, 'Ben Slimane'),
(2142, 504, 'Beni Mellal'),
(2143, 504, 'Boulemane'),
(2144, 504, 'Casablanca'),
(2145, 504, 'Chaouen'),
(2146, 504, 'El Jadida'),
(2147, 504, 'El Kelaa des Srarhna'),
(2148, 504, 'Er Rachidia'),
(2149, 504, 'Essaouira'),
(2150, 504, 'Fes'),
(2151, 504, 'Figuig'),
(2152, 504, 'Kenitra'),
(2153, 504, 'Khemisset'),
(2154, 504, 'Khenifra'),
(2155, 504, 'Khouribga'),
(2156, 504, 'Marrakech'),
(2157, 504, 'Meknes'),
(2158, 504, 'Nador'),
(2159, 504, 'Ouarzazate'),
(2160, 504, 'Oujda'),
(2161, 504, 'Rabat-Sale'),
(2162, 504, 'Safi'),
(2163, 504, 'Settat'),
(2164, 504, 'Tanger'),
(2165, 504, 'Tata'),
(2166, 504, 'Taza'),
(2167, 504, 'Tiznit'),
(2168, 504, 'Guelmim'),
(2169, 504, 'Ifrane'),
(2170, 504, 'Laayoune'),
(2171, 504, 'Tan-Tan'),
(2172, 504, 'Taounate'),
(2173, 504, 'Sidi Kacem'),
(2174, 504, 'Taroudannt'),
(2175, 504, 'Tetouan'),
(2176, 504, 'Larache'),
(2177, 504, 'Grand Casablanca'),
(2178, 504, 'Fès-Boulemane'),
(2179, 504, 'Marrakech-Tensift-Al Haouz'),
(2180, 504, 'Meknès-Tafilalet'),
(2181, 504, 'Rabat-Salé-Zemmour-Zaër'),
(2182, 504, 'Chaouia-Ouardigha'),
(2183, 504, 'Doukkala-Abda'),
(2184, 504, 'Gharb-Chrarda-Beni Hssen'),
(2185, 504, 'Guelmim-Es Smara'),
(2186, 504, 'Oriental'),
(2187, 504, 'Souss-Massa-Drâa'),
(2188, 504, 'Tadla-Azilal'),
(2189, 504, 'Tanger-Tétouan'),
(2190, 504, 'Taza-Al Hoceima-Taounate'),
(2191, 504, 'Laâyoune-Boujdour-Sakia El Hamra'),
(2192, 508, 'Cabo Delgado'),
(2193, 508, 'Gaza'),
(2194, 508, 'Inhambane'),
(2195, 508, 'Maputo Province'),
(2196, 508, 'Sofala'),
(2197, 508, 'Nampula'),
(2198, 508, 'Niassa'),
(2199, 508, 'Tete'),
(2200, 508, 'Zambézia'),
(2201, 508, 'Manica'),
(2202, 508, 'Maputo'),
(2203, 512, 'Ad Dākhilīyah'),
(2204, 512, 'Al Bāţinah'),
(2205, 512, 'Al Wusţá'),
(2206, 512, 'Ash Sharqīyah'),
(2207, 512, 'Masqaţ'),
(2208, 512, 'Musandam'),
(2209, 512, 'Z̧ufār'),
(2210, 512, 'Az̧ Z̧āhirah'),
(2211, 512, 'Muḩāfaz̧at al Buraymī'),
(2212, 516, 'Bethanien'),
(2213, 516, 'Caprivi Oos'),
(2214, 516, 'Kaokoland'),
(2215, 516, 'Otjiwarongo'),
(2216, 516, 'Outjo'),
(2217, 516, 'Owambo'),
(2218, 516, 'Khomas'),
(2219, 516, 'Kavango'),
(2220, 516, 'Caprivi'),
(2221, 516, 'Erongo'),
(2222, 516, 'Hardap'),
(2223, 516, 'Karas'),
(2224, 516, 'Kunene'),
(2225, 516, 'Ohangwena'),
(2226, 516, 'Okavango'),
(2227, 516, 'Omaheke'),
(2228, 516, 'Omusati'),
(2229, 516, 'Oshana'),
(2230, 516, 'Oshikoto'),
(2231, 516, 'Otjozondjupa'),
(2232, 520, 'Aiwo'),
(2233, 520, 'Anabar'),
(2234, 520, 'Anetan'),
(2235, 520, 'Anibare'),
(2236, 520, 'Baiti'),
(2237, 520, 'Boe'),
(2238, 520, 'Buada'),
(2239, 520, 'Denigomodu'),
(2240, 520, 'Ewa'),
(2241, 520, 'Ijuw'),
(2242, 520, 'Meneng'),
(2243, 520, 'Nibok'),
(2244, 520, 'Uaboe'),
(2245, 520, 'Yaren'),
(2246, 524, 'Bāgmatī'),
(2247, 524, 'Bherī'),
(2248, 524, 'Dhawalāgiri'),
(2249, 524, 'Gandakī'),
(2250, 524, 'Janakpur'),
(2251, 524, 'Karnālī'),
(2252, 524, 'Kosī'),
(2253, 524, 'Lumbinī'),
(2254, 524, 'Mahākālī'),
(2255, 524, 'Mechī'),
(2256, 524, 'Nārāyanī'),
(2257, 524, 'Rāptī'),
(2258, 524, 'Sagarmāthā'),
(2259, 524, 'Setī'),
(2260, 528, 'Provincie Drenthe'),
(2261, 528, 'Provincie Friesland'),
(2262, 528, 'Gelderland'),
(2263, 528, 'Groningen'),
(2264, 528, 'Limburg'),
(2265, 528, 'North Brabant'),
(2266, 528, 'North Holland'),
(2267, 528, 'Utrecht'),
(2268, 528, 'Zeeland'),
(2269, 528, 'South Holland'),
(2270, 528, 'Overijssel'),
(2271, 528, 'Flevoland'),
(2272, 530, 'Netherlands Antilles'),
(2273, 533, 'Aruba'),
(2274, 548, 'Ambrym'),
(2275, 548, 'Aoba/Maéwo'),
(2276, 548, 'Torba'),
(2277, 548, 'Éfaté'),
(2278, 548, 'Épi'),
(2279, 548, 'Malakula'),
(2280, 548, 'Paama'),
(2281, 548, 'Pentecôte'),
(2282, 548, 'Sanma'),
(2283, 548, 'Shepherd'),
(2284, 548, 'Tafea'),
(2285, 548, 'Malampa'),
(2286, 548, 'Penama'),
(2287, 548, 'Shefa'),
(2288, 554, 'Tasman'),
(2289, 554, 'Auckland'),
(2290, 554, 'Bay of Plenty'),
(2291, 554, 'Canterbury'),
(2292, 554, 'Gisborne'),
(2293, 554, 'Hawkeʼs Bay'),
(2294, 554, 'Manawatu-Wanganui'),
(2295, 554, 'Marlborough'),
(2296, 554, 'Nelson'),
(2297, 554, 'Northland'),
(2298, 554, 'Otago'),
(2299, 554, 'Southland'),
(2300, 554, 'Taranaki'),
(2301, 554, 'Waikato'),
(2302, 554, 'Wellington'),
(2303, 554, 'West Coast'),
(2304, 558, 'Boaco'),
(2305, 558, 'Carazo'),
(2306, 558, 'Chinandega'),
(2307, 558, 'Chontales'),
(2308, 558, 'Estelí'),
(2309, 558, 'Granada'),
(2310, 558, 'Jinotega'),
(2311, 558, 'León'),
(2312, 558, 'Madriz'),
(2313, 558, 'Managua'),
(2314, 558, 'Masaya'),
(2315, 558, 'Matagaa'),
(2316, 558, 'Nueva Segovia'),
(2317, 558, 'Río San Juan'),
(2318, 558, 'Rivas'),
(2319, 558, 'Ogun State'),
(2320, 558, 'Atlántico Norte'),
(2321, 558, 'Atlántico Sur'),
(2322, 562, 'Agadez'),
(2323, 562, 'Diffa'),
(2324, 562, 'Dosso'),
(2325, 562, 'Maradi'),
(2326, 562, 'Tahoua'),
(2327, 562, 'Zinder'),
(2328, 562, 'Niamey'),
(2329, 562, 'Tillabéri'),
(2330, 566, 'Lagos'),
(2331, 566, 'Abuja Federal Capital Territory'),
(2332, 566, 'Ogun'),
(2333, 566, 'Akwa Ibom'),
(2334, 566, 'Cross River'),
(2335, 566, 'Kaduna'),
(2336, 566, 'Katsina'),
(2337, 566, 'Anambra'),
(2338, 566, 'Benue'),
(2339, 566, 'Borno'),
(2340, 566, 'Imo'),
(2341, 566, 'Kano'),
(2342, 566, 'Kwara'),
(2343, 566, 'Niger'),
(2344, 566, 'Oyo'),
(2345, 566, 'Adamawa'),
(2346, 566, 'Delta'),
(2347, 566, 'Edo'),
(2348, 566, 'Jigawa'),
(2349, 566, 'Kebbi'),
(2350, 566, 'Kogi'),
(2351, 566, 'Osun'),
(2352, 566, 'Taraba'),
(2353, 566, 'Yobe'),
(2354, 566, 'Abia'),
(2355, 566, 'Bauchi'),
(2356, 566, 'Enugu'),
(2357, 566, 'Ondo'),
(2358, 566, 'Plateau'),
(2359, 566, 'Rivers'),
(2360, 566, 'Sokoto'),
(2361, 566, 'Bayelsa'),
(2362, 566, 'Ebonyi'),
(2363, 566, 'Ekiti'),
(2364, 566, 'Gombe'),
(2365, 566, 'Nassarawa'),
(2366, 566, 'Zamfara'),
(2367, 570, 'Niue'),
(2368, 574, 'Norfolk Island'),
(2369, 578, 'Svalbard'),
(2370, 578, 'Akershus'),
(2371, 578, 'Aust-Agder'),
(2372, 578, 'Buskerud'),
(2373, 578, 'Finnmark'),
(2374, 578, 'Hedmark'),
(2375, 578, 'Hordaland'),
(2376, 578, 'Møre og Romsdal'),
(2377, 578, 'Nordland'),
(2378, 578, 'Nord-Trøndelag'),
(2379, 578, 'Oppland'),
(2380, 578, 'Oslo county'),
(2381, 578, 'Østfold'),
(2382, 578, 'Rogaland'),
(2383, 578, 'Sogn og Fjordane'),
(2384, 578, 'Sør-Trøndelag'),
(2385, 578, 'Telemark'),
(2386, 578, 'Troms'),
(2387, 578, 'Vest-Agder'),
(2388, 578, 'Vestfold'),
(2389, 583, 'Kosrae'),
(2390, 583, 'Pohnpei'),
(2391, 583, 'Chuuk'),
(2392, 583, 'Yap'),
(2393, 584, 'Marshall Islands'),
(2394, 585, 'State of Ngeremlengui'),
(2395, 586, 'Federally Administered Tribal Areas'),
(2396, 586, 'Balochistān'),
(2397, 586, 'North West Frontier Province'),
(2398, 586, 'Punjab'),
(2399, 586, 'Sindh'),
(2400, 586, 'Azad Kashmir'),
(2401, 586, 'Gilgit-Baltistan'),
(2402, 586, 'Islāmābād'),
(2403, 591, 'Bocas del Toro'),
(2404, 591, 'Chiriquí'),
(2405, 591, 'Coclé'),
(2406, 591, 'Colón'),
(2407, 591, 'Darién'),
(2408, 591, 'Herrera'),
(2409, 591, 'Los Santos'),
(2410, 591, 'Panamá'),
(2411, 591, 'San Blas'),
(2412, 591, 'Veraguas'),
(2413, 598, 'Central'),
(2414, 598, 'Gulf'),
(2415, 598, 'Milne Bay'),
(2416, 598, 'Northern'),
(2417, 598, 'Southern Highlands'),
(2418, 598, 'Western'),
(2419, 598, 'Bougainville'),
(2420, 598, 'Chimbu'),
(2421, 598, 'Eastern Highlands'),
(2422, 598, 'East New Britain'),
(2423, 598, 'East Sepik'),
(2424, 598, 'Madang'),
(2425, 598, 'Manus'),
(2426, 598, 'Morobe'),
(2427, 598, 'New Ireland'),
(2428, 598, 'Western Highlands'),
(2429, 598, 'West New Britain'),
(2430, 598, 'Sandaun'),
(2431, 598, 'Enga'),
(2432, 598, 'National Capital'),
(2433, 600, 'Alto Paraná'),
(2434, 600, 'Amambay'),
(2435, 600, 'Caaguazú'),
(2436, 600, 'Caazapá'),
(2437, 600, 'Central'),
(2438, 600, 'Concepción'),
(2439, 600, 'Cordillera'),
(2440, 600, 'Guairá'),
(2441, 600, 'Itapúa'),
(2442, 600, 'Misiones'),
(2443, 600, 'Ñeembucú'),
(2444, 600, 'Paraguarí'),
(2445, 600, 'Presidente Hayes'),
(2446, 600, 'San Pedro'),
(2447, 600, 'Canindeyú'),
(2448, 600, 'Asunción'),
(2449, 600, 'Departamento de Alto Paraguay'),
(2450, 600, 'Boquerón'),
(2451, 604, 'Amazonas'),
(2452, 604, 'Ancash'),
(2453, 604, 'Apurímac'),
(2454, 604, 'Arequipa'),
(2455, 604, 'Ayacucho'),
(2456, 604, 'Cajamarca'),
(2457, 604, 'Callao'),
(2458, 604, 'Cusco'),
(2459, 604, 'Huancavelica'),
(2460, 604, 'Huanuco'),
(2461, 604, 'Ica'),
(2462, 604, 'Junín'),
(2463, 604, 'La Libertad'),
(2464, 604, 'Lambayeque'),
(2465, 604, 'Lima'),
(2466, 604, 'Provincia de Lima'),
(2467, 604, 'Loreto'),
(2468, 604, 'Madre de Dios'),
(2469, 604, 'Moquegua'),
(2470, 604, 'Pasco'),
(2471, 604, 'Piura'),
(2472, 604, 'Puno'),
(2473, 604, 'San Martín'),
(2474, 604, 'Tacna'),
(2475, 604, 'Tumbes'),
(2476, 604, 'Ucayali'),
(2477, 608, 'Abra'),
(2478, 608, 'Agusan del Norte'),
(2479, 608, 'Agusan del Sur'),
(2480, 608, 'Aklan'),
(2481, 608, 'Albay'),
(2482, 608, 'Antique'),
(2483, 608, 'Bataan'),
(2484, 608, 'Batanes'),
(2485, 608, 'Batangas'),
(2486, 608, 'Benguet'),
(2487, 608, 'Bohol'),
(2488, 608, 'Bukidnon'),
(2489, 608, 'Bulacan'),
(2490, 608, 'Cagayan'),
(2491, 608, 'Camarines Norte'),
(2492, 608, 'Camarines Sur'),
(2493, 608, 'Camiguin'),
(2494, 608, 'Capiz'),
(2495, 608, 'Catanduanes'),
(2496, 608, 'Cebu'),
(2497, 608, 'Basilan'),
(2498, 608, 'Eastern Samar'),
(2499, 608, 'Davao del Sur'),
(2500, 608, 'Davao Oriental'),
(2501, 608, 'Ifugao'),
(2502, 608, 'Ilocos Norte'),
(2503, 608, 'Ilocos Sur'),
(2504, 608, 'Iloilo'),
(2505, 608, 'Isabela'),
(2506, 608, 'Laguna'),
(2507, 608, 'Lanao del Sur'),
(2508, 608, 'La Union'),
(2509, 608, 'Leyte'),
(2510, 608, 'Marinduque'),
(2511, 608, 'Masbate'),
(2512, 608, 'Occidental Mindoro'),
(2513, 608, 'Oriental Mindoro'),
(2514, 608, 'Misamis Oriental'),
(2515, 608, 'Mountain Province'),
(2516, 608, 'Negros Oriental'),
(2517, 608, 'Nueva Ecija'),
(2518, 608, 'Nueva Vizcaya'),
(2519, 608, 'Palawan'),
(2520, 608, 'Pampanga'),
(2521, 608, 'Pangasinan'),
(2522, 608, 'Rizal'),
(2523, 608, 'Romblon'),
(2524, 608, 'Samar'),
(2525, 608, 'Maguindanao'),
(2526, 608, 'Cotabato City'),
(2527, 608, 'Sorsogon'),
(2528, 608, 'Southern Leyte'),
(2529, 608, 'Sulu'),
(2530, 608, 'Surigao del Norte'),
(2531, 608, 'Surigao del Sur'),
(2532, 608, 'Tarlac'),
(2533, 608, 'Zambales'),
(2534, 608, 'Zamboanga del Norte'),
(2535, 608, 'Zamboanga del Sur'),
(2536, 608, 'Northern Samar'),
(2537, 608, 'Quirino'),
(2538, 608, 'Siquijor'),
(2539, 608, 'South Cotabato'),
(2540, 608, 'Sultan Kudarat'),
(2541, 608, 'Kalinga'),
(2542, 608, 'Apayao'),
(2543, 608, 'Tawi-Tawi'),
(2544, 608, 'Angeles'),
(2545, 608, 'Bacolod City'),
(2546, 608, 'Compostela Valley'),
(2547, 608, 'Baguio'),
(2548, 608, 'Davao del Norte'),
(2549, 608, 'Butuan'),
(2550, 608, 'Guimaras'),
(2551, 608, 'Lanao del Norte'),
(2552, 608, 'Misamis Occidental'),
(2553, 608, 'Caloocan'),
(2554, 608, 'Cavite'),
(2555, 608, 'Cebu City'),
(2556, 608, 'Cotabato'),
(2557, 608, 'Dagupan'),
(2558, 608, 'Cagayan de Oro'),
(2559, 608, 'Iligan'),
(2560, 608, 'Davao'),
(2561, 608, 'Las Piñas'),
(2562, 608, 'Malabon'),
(2563, 608, 'General Santos'),
(2564, 608, 'Muntinlupa'),
(2565, 608, 'Iloilo City'),
(2566, 608, 'Navotas'),
(2567, 608, 'Parañaque'),
(2568, 608, 'Quezon City'),
(2569, 608, 'Lapu-Lapu'),
(2570, 608, 'Taguig'),
(2571, 608, 'Valenzuela'),
(2572, 608, 'Lucena'),
(2573, 608, 'Mandaue'),
(2574, 608, 'Manila'),
(2575, 608, 'Zamboanga Sibugay'),
(2576, 608, 'Naga'),
(2577, 608, 'Olongapo'),
(2578, 608, 'Ormoc'),
(2579, 608, 'Santiago'),
(2580, 608, 'Pateros'),
(2581, 608, 'Pasay'),
(2582, 608, 'Puerto Princesa'),
(2583, 608, 'Quezon'),
(2584, 608, 'Tacloban'),
(2585, 608, 'Zamboanga City'),
(2586, 608, 'Aurora'),
(2587, 608, 'Negros Occidental'),
(2588, 608, 'Biliran'),
(2589, 608, 'Makati City'),
(2590, 608, 'Sarangani'),
(2591, 608, 'Mandaluyong City'),
(2592, 608, 'Marikina'),
(2593, 608, 'Pasig'),
(2594, 608, 'San Juan'),
(2595, 612, 'Pitcairn Islands'),
(2596, 616, 'Biala Podlaska'),
(2597, 616, 'Bialystok'),
(2598, 616, 'Bielsko'),
(2599, 616, 'Bydgoszcz'),
(2600, 616, 'Chelm'),
(2601, 616, 'Ciechanow'),
(2602, 616, 'Czestochowa'),
(2603, 616, 'Elblag'),
(2604, 616, 'Gdansk'),
(2605, 616, 'Gorzow'),
(2606, 616, 'Jelenia Gora'),
(2607, 616, 'Kalisz'),
(2608, 616, 'Katowice'),
(2609, 616, 'Kielce'),
(2610, 616, 'Konin'),
(2611, 616, 'Koszalin'),
(2612, 616, 'Krakow'),
(2613, 616, 'Krosno'),
(2614, 616, 'Legnica'),
(2615, 616, 'Leszno'),
(2616, 616, 'Lodz'),
(2617, 616, 'Lomza'),
(2618, 616, 'Lublin'),
(2619, 616, 'Nowy Sacz'),
(2620, 616, 'Olsztyn'),
(2621, 616, 'Opole'),
(2622, 616, 'Ostroleka'),
(2623, 616, 'Pita'),
(2624, 616, 'Piotrkow'),
(2625, 616, 'Plock'),
(2626, 616, 'Poznan'),
(2627, 616, 'Przemysl'),
(2628, 616, 'Radom'),
(2629, 616, 'Rzeszow'),
(2630, 616, 'Siedlce'),
(2631, 616, 'Sieradz'),
(2632, 616, 'Skierniewice'),
(2633, 616, 'Slupsk'),
(2634, 616, 'Suwalki'),
(2635, 616, 'Szczecin'),
(2636, 616, 'Tarnobrzeg'),
(2637, 616, 'Tarnow'),
(2638, 616, 'Torufi'),
(2639, 616, 'Walbrzych'),
(2640, 616, 'Warszawa'),
(2641, 616, 'Wloclawek'),
(2642, 616, 'Wroclaw'),
(2643, 616, 'Zamosc'),
(2644, 616, 'Zielona Gora'),
(2645, 616, 'Lower Silesian Voivodeship'),
(2646, 616, 'Kujawsko-Pomorskie Voivodship'),
(2647, 616, 'Łódź Voivodeship'),
(2648, 616, 'Lublin Voivodeship'),
(2649, 616, 'Lubusz Voivodship'),
(2650, 616, 'Lesser Poland Voivodeship'),
(2651, 616, 'Masovian Voivodeship'),
(2652, 616, 'Opole Voivodeship'),
(2653, 616, 'Subcarpathian Voivodeship'),
(2654, 616, 'Podlasie Voivodship'),
(2655, 616, 'Pomeranian Voivodeship'),
(2656, 616, 'Silesian Voivodeship'),
(2657, 616, 'Świętokrzyskie Voivodship'),
(2658, 616, 'Warmian-Masurian Voivodeship'),
(2659, 616, 'Greater Poland Voivodeship'),
(2660, 616, 'West Pomeranian Voivodeship'),
(2661, 620, 'Aveiro'),
(2662, 620, 'Beja'),
(2663, 620, 'Braga'),
(2664, 620, 'Bragança'),
(2665, 620, 'Castelo Branco'),
(2666, 620, 'Coimbra'),
(2667, 620, 'Évora'),
(2668, 620, 'Faro'),
(2669, 620, 'Madeira'),
(2670, 620, 'Guarda'),
(2671, 620, 'Leiria'),
(2672, 620, 'Lisbon'),
(2673, 620, 'Portalegre'),
(2674, 620, 'Porto'),
(2675, 620, 'Santarém'),
(2676, 620, 'Setúbal'),
(2677, 620, 'Viana do Castelo'),
(2678, 620, 'Vila Real'),
(2679, 620, 'Viseu'),
(2680, 620, 'Azores'),
(2681, 624, 'Bafatá'),
(2682, 624, 'Quinara'),
(2683, 624, 'Oio'),
(2684, 624, 'Bolama'),
(2685, 624, 'Cacheu'),
(2686, 624, 'Tombali'),
(2687, 624, 'Gabú'),
(2688, 624, 'Bissau'),
(2689, 624, 'Biombo'),
(2690, 626, 'Bobonaro'),
(2691, 630, 'Puerto Rico'),
(2692, 634, 'Ad Dawḩah'),
(2693, 634, 'Al Ghuwayrīyah'),
(2694, 634, 'Al Jumaylīyah'),
(2695, 634, 'Al Khawr'),
(2696, 634, 'Al Wakrah Municipality'),
(2697, 634, 'Ar Rayyān'),
(2698, 634, 'Jarayan al Batinah'),
(2699, 634, 'Madīnat ash Shamāl'),
(2700, 634, 'Umm Şalāl'),
(2701, 634, 'Al Wakrah'),
(2702, 634, 'Jarayān al Bāţinah'),
(2703, 634, 'Umm Sa‘īd'),
(2704, 638, 'Réunion'),
(2705, 642, 'Alba'),
(2706, 642, 'Arad'),
(2707, 642, 'Argeş'),
(2708, 642, 'Bacău'),
(2709, 642, 'Bihor'),
(2710, 642, 'Bistriţa-Năsăud'),
(2711, 642, 'Botoşani'),
(2712, 642, 'Brăila'),
(2713, 642, 'Braşov'),
(2714, 642, 'Bucureşti'),
(2715, 642, 'Buzău'),
(2716, 642, 'Caraş-Severin'),
(2717, 642, 'Cluj'),
(2718, 642, 'Constanţa'),
(2719, 642, 'Covasna'),
(2720, 642, 'Dâmboviţa'),
(2721, 642, 'Dolj'),
(2722, 642, 'Galaţi'),
(2723, 642, 'Gorj'),
(2724, 642, 'Harghita'),
(2725, 642, 'Hunedoara'),
(2726, 642, 'Ialomiţa'),
(2727, 642, 'Iaşi'),
(2728, 642, 'Maramureş'),
(2729, 642, 'Mehedinţi'),
(2730, 642, 'Mureş'),
(2731, 642, 'Neamţ'),
(2732, 642, 'Olt'),
(2733, 642, 'Prahova'),
(2734, 642, 'Sălaj'),
(2735, 642, 'Satu Mare'),
(2736, 642, 'Sibiu'),
(2737, 642, 'Suceava'),
(2738, 642, 'Teleorman'),
(2739, 642, 'Timiş'),
(2740, 642, 'Tulcea'),
(2741, 642, 'Vaslui'),
(2742, 642, 'Vâlcea'),
(2743, 642, 'Judeţul Vrancea'),
(2744, 642, 'Călăraşi'),
(2745, 642, 'Giurgiu'),
(2746, 642, 'Ilfov'),
(2747, 643, 'Adygeya'),
(2748, 643, 'Altay'),
(2749, 643, 'Altayskiy Kray'),
(2750, 643, 'Amur'),
(2751, 643, 'Arkhangelskaya oblast'),
(2752, 643, 'Astrakhan'),
(2753, 643, 'Bashkortostan'),
(2754, 643, 'Belgorod'),
(2755, 643, 'Brjansk'),
(2756, 643, 'Buryatiya'),
(2757, 643, 'Chechnya'),
(2758, 643, 'Tsjeljabinsk'),
(2759, 643, 'Zabaïkalski Kray'),
(2760, 643, 'Chukotskiy Avtonomnyy Okrug'),
(2761, 643, 'Chuvashia'),
(2762, 643, 'Dagestan'),
(2763, 643, 'Ingushetiya'),
(2764, 643, 'Irkutsk'),
(2765, 643, 'Ivanovo'),
(2766, 643, 'Kabardino-Balkariya'),
(2767, 643, 'Kaliningrad'),
(2768, 643, 'Kalmykiya'),
(2769, 643, 'Kaluga'),
(2770, 643, 'Karachayevo-Cherkesiya'),
(2771, 643, 'Kareliya'),
(2772, 643, 'Kemerovo'),
(2773, 643, 'Khabarovsk Krai'),
(2774, 643, 'Khakasiya'),
(2775, 643, 'Khanty-Mansiyskiy Avtonomnyy Okrug'),
(2776, 643, 'Kirov'),
(2777, 643, 'Komi'),
(2778, 643, 'Kostroma'),
(2779, 643, 'Krasnodarskiy Kray'),
(2780, 643, 'Kurgan'),
(2781, 643, 'Kursk'),
(2782, 643, 'Leningradskaya Oblastʼ'),
(2783, 643, 'Lipetsk'),
(2784, 643, 'Magadan'),
(2785, 643, 'Mariy-El'),
(2786, 643, 'Mordoviya'),
(2787, 643, 'Moskovskaya Oblastʼ'),
(2788, 643, 'Moscow'),
(2789, 643, 'Murmansk Oblast'),
(2790, 643, 'Nenetskiy Avtonomnyy Okrug'),
(2791, 643, 'Nizjnij Novgorod'),
(2792, 643, 'Novgorod'),
(2793, 643, 'Novosibirsk'),
(2794, 643, 'Omsk'),
(2795, 643, 'Orenburg'),
(2796, 643, 'Orjol'),
(2797, 643, 'Penza'),
(2798, 643, 'Primorskiy Kray'),
(2799, 643, 'Pskov'),
(2800, 643, 'Rostov'),
(2801, 643, 'Rjazan'),
(2802, 643, 'Sakha'),
(2803, 643, 'Sakhalin'),
(2804, 643, 'Samara'),
(2805, 643, 'Sankt-Peterburg'),
(2806, 643, 'Saratov'),
(2807, 643, 'Severnaya Osetiya-Alaniya'),
(2808, 643, 'Smolensk'),
(2809, 643, 'Stavropolʼskiy Kray'),
(2810, 643, 'Sverdlovsk'),
(2811, 643, 'Tambov'),
(2812, 643, 'Tatarstan'),
(2813, 643, 'Tomsk'),
(2814, 643, 'Tula'),
(2815, 643, 'Tverskaya Oblast’'),
(2816, 643, 'Tjumen'),
(2817, 643, 'Tyva'),
(2818, 643, 'Udmurtiya'),
(2819, 643, 'Uljanovsk'),
(2820, 643, 'Vladimir'),
(2821, 643, 'Volgograd'),
(2822, 643, 'Vologda'),
(2823, 643, 'Voronezj'),
(2824, 643, 'Yamalo-Nenetskiy Avtonomnyy Okrug'),
(2825, 643, 'Jaroslavl'),
(2826, 643, 'Jewish Autonomous Oblast'),
(2827, 643, 'Perm'),
(2828, 643, 'Krasnoyarskiy Kray'),
(2829, 643, 'Kamtsjatka'),
(2830, 643, 'RSJA'),
(2831, 646, 'Eastern Province'),
(2832, 646, 'Kigali City'),
(2833, 646, 'Northern Province'),
(2834, 646, 'Western Province'),
(2835, 646, 'Southern Province'),
(2836, 654, 'Ascension'),
(2837, 654, 'Saint Helena'),
(2838, 654, 'Tristan da Cunha'),
(2839, 659, 'Christ Church Nichola Town'),
(2840, 659, 'Saint Anne Sandy Point'),
(2841, 659, 'Saint George Basseterre'),
(2842, 659, 'Saint George Gingerland'),
(2843, 659, 'Saint James Windwa'),
(2844, 659, 'Saint John Capesterre'),
(2845, 659, 'Saint John Figtree'),
(2846, 659, 'Saint Mary Cayon'),
(2847, 659, 'Saint Paul Capesterre'),
(2848, 659, 'Saint Paul Charlestown'),
(2849, 659, 'Saint Peter Basseterre'),
(2850, 659, 'Saint Thomas Lowland'),
(2851, 659, 'Saint Thomas Middle Island'),
(2852, 659, 'Trinity Palmetto Point'),
(2853, 660, 'Anguilla'),
(2854, 662, 'Anse-la-Raye'),
(2855, 662, 'Dauphin'),
(2856, 662, 'Castries'),
(2857, 662, 'Choiseul'),
(2858, 662, 'Dennery'),
(2859, 662, 'Gros-Islet'),
(2860, 662, 'Laborie'),
(2861, 662, 'Micoud'),
(2862, 662, 'Soufrière'),
(2863, 662, 'Vieux-Fort'),
(2864, 662, 'Praslin'),
(2865, 666, 'Saint-Pierre-et-Miquelon'),
(2866, 670, 'Charlotte'),
(2867, 670, 'Saint Andrew'),
(2868, 670, 'Saint David'),
(2869, 670, 'Saint George'),
(2870, 670, 'Saint Patrick'),
(2871, 670, 'Grenadines'),
(2872, 674, 'Acquaviva'),
(2873, 674, 'Chiesanuova'),
(2874, 674, 'Domagnano'),
(2875, 674, 'Faetano'),
(2876, 674, 'Fiorentino'),
(2877, 674, 'Borgo Maggiore'),
(2878, 674, 'San Marino'),
(2879, 674, 'Montegiardino'),
(2880, 674, 'Serravalle'),
(2881, 678, 'Príncipe'),
(2882, 678, 'Príncipe'),
(2883, 678, 'São Tomé'),
(2884, 682, 'Al Bāḩah'),
(2885, 682, 'Al Madīnah'),
(2886, 682, 'Ash Sharqīyah'),
(2887, 682, 'Al Qaşīm'),
(2888, 682, 'Ar Riyāḑ'),
(2889, 682, '‘Asīr'),
(2890, 682, 'Ḩāʼil'),
(2891, 682, 'Makkah'),
(2892, 682, 'Northern Borders Region'),
(2893, 682, 'Najrān'),
(2894, 682, 'Jīzān'),
(2895, 682, 'Tabūk'),
(2896, 682, 'Al Jawf'),
(2897, 686, 'Dakar'),
(2898, 686, 'Diourbel'),
(2899, 686, 'Saint-Louis'),
(2900, 686, 'Tambacounda'),
(2901, 686, 'Thiès'),
(2902, 686, 'Louga'),
(2903, 686, 'Fatick'),
(2904, 686, 'Kaolack'),
(2905, 686, 'Kolda Region'),
(2906, 686, 'Ziguinchor'),
(2907, 686, 'Louga'),
(2908, 686, 'Saint-Louis'),
(2909, 686, 'Matam'),
(2910, 688, 'Autonomna Pokrajina Vojvodina'),
(2911, 690, 'Anse aux Pins'),
(2912, 690, 'Anse Boileau'),
(2913, 690, 'Anse Etoile'),
(2914, 690, 'Anse Louis'),
(2915, 690, 'Anse Royale'),
(2916, 690, 'Baie Lazare'),
(2917, 690, 'Baie Sainte Anne'),
(2918, 690, 'Beau Vallon'),
(2919, 690, 'Bel Air'),
(2920, 690, 'Bel Ombre'),
(2921, 690, 'Cascade'),
(2922, 690, 'Glacis'),
(2923, 690, 'Saint Thomas Middle Island Parish'),
(2924, 690, 'Grand Anse Praslin'),
(2925, 690, 'Trinity Palmetto Point Parish'),
(2926, 690, 'La Riviere Anglaise'),
(2927, 690, 'Mont Buxton'),
(2928, 690, 'Mont Fleuri'),
(2929, 690, 'Plaisance'),
(2930, 690, 'Pointe Larue'),
(2931, 690, 'Port Glaud'),
(2932, 690, 'Saint Louis'),
(2933, 690, 'Takamaka'),
(2934, 690, 'Anse aux Pins'),
(2935, 690, 'Inner Islands'),
(2936, 690, 'English River'),
(2937, 690, 'Port Glaud'),
(2938, 690, 'Baie Lazare'),
(2939, 690, 'Beau Vallon'),
(2940, 690, 'Bel Ombre'),
(2941, 690, 'Glacis'),
(2942, 690, 'Grand Anse Mahe'),
(2943, 690, 'Grand Anse Praslin'),
(2944, 690, 'Inner Islands'),
(2945, 690, 'English River'),
(2946, 690, 'Mont Fleuri'),
(2947, 690, 'Plaisance'),
(2948, 690, 'Pointe Larue'),
(2949, 690, 'Port Glaud'),
(2950, 690, 'Takamaka'),
(2951, 690, 'Au Cap'),
(2952, 690, 'Les Mamelles'),
(2953, 690, 'Roche Caiman'),
(2954, 694, 'Eastern Province'),
(2955, 694, 'Northern Province'),
(2956, 694, 'Southern Province'),
(2957, 694, 'Western Area'),
(2958, 702, 'Singapore'),
(2959, 703, 'Banskobystrický'),
(2960, 703, 'Bratislavský'),
(2961, 703, 'Košický'),
(2962, 703, 'Nitriansky'),
(2963, 703, 'Prešovský'),
(2964, 703, 'Trenčiansky'),
(2965, 703, 'Trnavský'),
(2966, 703, 'Žilinský'),
(2967, 704, 'An Giang'),
(2968, 704, 'Bắc Thái Tỉnh'),
(2969, 704, 'Bến Tre'),
(2970, 704, 'Cao Bang'),
(2971, 704, 'Cao Bằng'),
(2972, 704, 'Ten Bai'),
(2973, 704, 'Ðồng Tháp'),
(2974, 704, 'Hà Bắc Tỉnh'),
(2975, 704, 'Hải Hưng Tỉnh'),
(2976, 704, 'Hải Phòng'),
(2977, 704, 'Hoa Binh'),
(2978, 704, 'Ha Tay'),
(2979, 704, 'Hồ Chí Minh'),
(2980, 704, 'Kiến Giang'),
(2981, 704, 'Lâm Ðồng'),
(2982, 704, 'Long An'),
(2983, 704, 'Minh Hải Tỉnh'),
(2984, 704, 'Thua Thien-Hue'),
(2985, 704, 'Quang Nam'),
(2986, 704, 'Kon Tum'),
(2987, 704, 'Quảng Nam-Ðà Nẵng Tỉnh'),
(2988, 704, 'Quảng Ninh'),
(2989, 704, 'Sông Bé Tỉnh'),
(2990, 704, 'Sơn La'),
(2991, 704, 'Tây Ninh'),
(2992, 704, 'Thanh Hóa'),
(2993, 704, 'Thái Bình'),
(2994, 704, 'Nin Thuan'),
(2995, 704, 'Tiền Giang'),
(2996, 704, 'Vinh Phú Tỉnh'),
(2997, 704, 'Lạng Sơn'),
(2998, 704, 'Binh Thuan'),
(2999, 704, 'Long An'),
(3000, 704, 'Ðồng Nai'),
(3001, 704, 'Ha Nội'),
(3002, 704, 'Bà Rịa-Vũng Tàu'),
(3003, 704, 'Bình Ðịnh'),
(3004, 704, 'Bình Thuận'),
(3005, 704, 'Gia Lai'),
(3006, 704, 'Hà Giang'),
(3007, 704, 'Hà Tây'),
(3008, 704, 'Hà Tĩnh'),
(3009, 704, 'Hòa Bình'),
(3010, 704, 'Khánh Hòa'),
(3011, 704, 'Kon Tum'),
(3012, 704, 'Nam Hà Tỉnh'),
(3013, 704, 'Nghệ An'),
(3014, 704, 'Ninh Bình'),
(3015, 704, 'Ninh Thuận'),
(3016, 704, 'Phú Yên'),
(3017, 704, 'Quảng Bình'),
(3018, 704, 'Quảng Ngãi'),
(3019, 704, 'Quảng Trị'),
(3020, 704, 'Sóc Trăng'),
(3021, 704, 'Thừa Thiên-Huế'),
(3022, 704, 'Trà Vinh'),
(3023, 704, 'Tuyên Quang'),
(3024, 704, 'Vĩnh Long'),
(3025, 704, 'Yên Bái'),
(3026, 704, 'Bắc Giang'),
(3027, 704, 'Bắc Kạn'),
(3028, 704, 'Bạc Liêu'),
(3029, 704, 'Bắc Ninh'),
(3030, 704, 'Bình Dương'),
(3031, 704, 'Bình Phước'),
(3032, 704, 'Cà Mau'),
(3033, 704, 'Ðà Nẵng'),
(3034, 704, 'Hải Dương'),
(3035, 704, 'Hà Nam'),
(3036, 704, 'Hưng Yên'),
(3037, 704, 'Nam Ðịnh'),
(3038, 704, 'Phú Thọ'),
(3039, 704, 'Quảng Nam'),
(3040, 704, 'Thái Nguyên'),
(3041, 704, 'Vĩnh Phúc'),
(3042, 704, 'Cần Thơ'),
(3043, 704, 'Ðắc Lắk'),
(3044, 704, 'Lai Châu'),
(3045, 704, 'Lào Cai'),
(3046, 705, 'Notranjska'),
(3047, 705, 'Koroška'),
(3048, 705, 'Štajerska'),
(3049, 705, 'Prekmurje'),
(3050, 705, 'Primorska'),
(3051, 705, 'Gorenjska'),
(3052, 705, 'Dolenjska'),
(3053, 706, 'Bakool'),
(3054, 706, 'Banaadir'),
(3055, 706, 'Bari'),
(3056, 706, 'Bay'),
(3057, 706, 'Galguduud'),
(3058, 706, 'Gedo'),
(3059, 706, 'Hiiraan'),
(3060, 706, 'Middle Juba'),
(3061, 706, 'Lower Juba'),
(3062, 706, 'Mudug'),
(3063, 706, 'Sanaag'),
(3064, 706, 'Middle Shabele'),
(3065, 706, 'Shabeellaha Hoose'),
(3066, 706, 'Nugaal'),
(3067, 706, 'Togdheer'),
(3068, 706, 'Woqooyi Galbeed'),
(3069, 706, 'Awdal'),
(3070, 706, 'Sool'),
(3071, 710, 'KwaZulu-Natal'),
(3072, 710, 'Free State'),
(3073, 710, 'Eastern Cape'),
(3074, 710, 'Gauteng'),
(3075, 710, 'Mpumalanga'),
(3076, 710, 'Northern Cape'),
(3077, 710, 'Limpopo'),
(3078, 710, 'North-West'),
(3079, 710, 'Western Cape'),
(3080, 716, 'Manicaland'),
(3081, 716, 'Midlands'),
(3082, 716, 'Mashonaland Central'),
(3083, 716, 'Mashonaland East'),
(3084, 716, 'Mashonaland West'),
(3085, 716, 'Matabeleland North'),
(3086, 716, 'Matabeleland South'),
(3087, 716, 'Masvingo'),
(3088, 716, 'Bulawayo'),
(3089, 716, 'Harare'),
(3090, 724, 'Ceuta'),
(3091, 724, 'Balearic Islands'),
(3092, 724, 'La Rioja'),
(3093, 724, 'Autonomous Region of Madrid'),
(3094, 724, 'Murcia'),
(3095, 724, 'Navarre'),
(3096, 724, 'Asturias'),
(3097, 724, 'Cantabria'),
(3098, 724, 'Andalusia'),
(3099, 724, 'Aragon'),
(3100, 724, 'Canary Islands'),
(3101, 724, 'Castille-La Mancha'),
(3102, 724, 'Castille and León'),
(3103, 724, 'Catalonia'),
(3104, 724, 'Extremadura'),
(3105, 724, 'Galicia'),
(3106, 724, 'Basque Country'),
(3107, 724, 'Valencia'),
(3108, 732, 'Western Sahara'),
(3109, 736, 'Al Wilāyah al Wusţá'),
(3110, 736, 'Al Wilāyah al Istiwāʼīyah'),
(3111, 736, 'Khartoum'),
(3112, 736, 'Ash Shamaliyah'),
(3113, 736, 'Al Wilāyah ash Sharqīyah'),
(3114, 736, 'Ba?r al Ghazal Wilayat'),
(3115, 736, 'Darfur Wilayat'),
(3116, 736, 'Kurdufan Wilayat'),
(3117, 736, 'Upper Nile'),
(3118, 736, 'Red Sea'),
(3119, 736, 'Lakes'),
(3120, 736, 'Al Jazirah'),
(3121, 736, 'Al Qadarif'),
(3122, 736, 'Unity'),
(3123, 736, 'White Nile'),
(3124, 736, 'Blue Nile'),
(3125, 736, 'Northern'),
(3126, 736, 'Central Equatoria'),
(3127, 736, 'Gharb al Istiwāʼīyah'),
(3128, 736, 'Western Bahr al Ghazal'),
(3129, 736, 'Gharb Dārfūr'),
(3130, 736, 'Gharb Kurdufān'),
(3131, 736, 'Janūb Dārfūr'),
(3132, 736, 'Janūb Kurdufān'),
(3133, 736, 'Junqalī'),
(3134, 736, 'Kassalā'),
(3135, 736, 'Nahr an Nīl'),
(3136, 736, 'Shamāl Baḩr al Ghazāl'),
(3137, 736, 'Shamāl Dārfūr'),
(3138, 736, 'Shamāl Kurdufān'),
(3139, 736, 'Eastern Equatoria'),
(3140, 736, 'Sinnār'),
(3141, 736, 'Warab'),
(3142, 740, 'Brokopondo'),
(3143, 740, 'Commewijne'),
(3144, 740, 'Coronie'),
(3145, 740, 'Marowijne'),
(3146, 740, 'Nickerie'),
(3147, 740, 'Para'),
(3148, 740, 'Paramaribo'),
(3149, 740, 'Saramacca'),
(3150, 740, 'Sipaliwini'),
(3151, 740, 'Wanica'),
(3152, 748, 'Hhohho'),
(3153, 748, 'Lubombo'),
(3154, 748, 'Manzini'),
(3155, 748, 'Shiselweni'),
(3156, 752, 'Blekinge'),
(3157, 752, 'Gävleborg'),
(3158, 752, 'Gotland'),
(3159, 752, 'Halland'),
(3160, 752, 'Jämtland'),
(3161, 752, 'Jönköping'),
(3162, 752, 'Kalmar'),
(3163, 752, 'Dalarna'),
(3164, 752, 'Kronoberg'),
(3165, 752, 'Norrbotten'),
(3166, 752, 'Örebro'),
(3167, 752, 'Östergötland'),
(3168, 752, 'Södermanland'),
(3169, 752, 'Uppsala'),
(3170, 752, 'Värmland'),
(3171, 752, 'Västerbotten'),
(3172, 752, 'Västernorrland'),
(3173, 752, 'Västmanland'),
(3174, 752, 'Stockholm'),
(3175, 752, 'Skåne'),
(3176, 752, 'Västra Götaland'),
(3177, 756, 'Aargau'),
(3178, 756, 'Appenzell Innerrhoden'),
(3179, 756, 'Appenzell Ausserrhoden'),
(3180, 756, 'Bern'),
(3181, 756, 'Basel-Landschaft'),
(3182, 756, 'Kanton Basel-Stadt'),
(3183, 756, 'Fribourg'),
(3184, 756, 'Genève'),
(3185, 756, 'Glarus'),
(3186, 756, 'Graubünden'),
(3187, 756, 'Jura'),
(3188, 756, 'Luzern'),
(3189, 756, 'Neuchâtel'),
(3190, 756, 'Nidwalden'),
(3191, 756, 'Obwalden'),
(3192, 756, 'Kanton St. Gallen'),
(3193, 756, 'Schaffhausen'),
(3194, 756, 'Solothurn'),
(3195, 756, 'Schwyz'),
(3196, 756, 'Thurgau'),
(3197, 756, 'Ticino'),
(3198, 756, 'Uri'),
(3199, 756, 'Vaud'),
(3200, 756, 'Valais'),
(3201, 756, 'Zug'),
(3202, 756, 'Zürich'),
(3203, 760, 'Al-Hasakah'),
(3204, 760, 'Latakia'),
(3205, 760, 'Quneitra'),
(3206, 760, 'Ar-Raqqah'),
(3207, 760, 'As-Suwayda'),
(3208, 760, 'Daraa'),
(3209, 760, 'Deir ez-Zor'),
(3210, 760, 'Rif-dimashq'),
(3211, 760, 'Aleppo'),
(3212, 760, 'Hama Governorate'),
(3213, 760, 'Homs'),
(3214, 760, 'Idlib'),
(3215, 760, 'Damascus City'),
(3216, 760, 'Tartus'),
(3217, 762, 'Gorno-Badakhshan'),
(3218, 762, 'Khatlon'),
(3219, 762, 'Sughd'),
(3220, 762, 'Dushanbe'),
(3221, 762, 'Region of Republican Subordination'),
(3222, 764, 'Mae Hong Son'),
(3223, 764, 'Chiang Mai'),
(3224, 764, 'Chiang Rai'),
(3225, 764, 'Nan'),
(3226, 764, 'Lamphun'),
(3227, 764, 'Lampang'),
(3228, 764, 'Phrae'),
(3229, 764, 'Tak'),
(3230, 764, 'Sukhothai'),
(3231, 764, 'Uttaradit'),
(3232, 764, 'Kamphaeng Phet'),
(3233, 764, 'Phitsanulok'),
(3234, 764, 'Phichit'),
(3235, 764, 'Phetchabun'),
(3236, 764, 'Uthai Thani'),
(3237, 764, 'Nakhon Sawan'),
(3238, 764, 'Nong Khai'),
(3239, 764, 'Loei'),
(3240, 764, 'Sakon Nakhon'),
(3241, 764, 'Nakhon Phanom'),
(3242, 764, 'Khon Kaen'),
(3243, 764, 'Kalasin'),
(3244, 764, 'Maha Sarakham'),
(3245, 764, 'Roi Et'),
(3246, 764, 'Chaiyaphum'),
(3247, 764, 'Nakhon Ratchasima'),
(3248, 764, 'Buriram'),
(3249, 764, 'Surin'),
(3250, 764, 'Sisaket'),
(3251, 764, 'Narathiwat'),
(3252, 764, 'Chai Nat'),
(3253, 764, 'Sing Buri'),
(3254, 764, 'Lop Buri'),
(3255, 764, 'Ang Thong'),
(3256, 764, 'Phra Nakhon Si Ayutthaya'),
(3257, 764, 'Sara Buri'),
(3258, 764, 'Nonthaburi'),
(3259, 764, 'Pathum Thani'),
(3260, 764, 'Bangkok'),
(3261, 764, 'Phayao'),
(3262, 764, 'Samut Prakan'),
(3263, 764, 'Nakhon Nayok'),
(3264, 764, 'Chachoengsao'),
(3265, 764, 'Chon Buri'),
(3266, 764, 'Rayong'),
(3267, 764, 'Chanthaburi'),
(3268, 764, 'Trat'),
(3269, 764, 'Kanchanaburi'),
(3270, 764, 'Suphan Buri'),
(3271, 764, 'Ratchaburi'),
(3272, 764, 'Nakhon Pathom'),
(3273, 764, 'Samut Songkhram'),
(3274, 764, 'Samut Sakhon'),
(3275, 764, 'Phetchaburi'),
(3276, 764, 'Prachuap Khiri Khan'),
(3277, 764, 'Chumphon'),
(3278, 764, 'Ranong'),
(3279, 764, 'Surat Thani'),
(3280, 764, 'Phangnga'),
(3281, 764, 'Phuket'),
(3282, 764, 'Krabi'),
(3283, 764, 'Nakhon Si Thammarat'),
(3284, 764, 'Trang'),
(3285, 764, 'Phatthalung'),
(3286, 764, 'Satun'),
(3287, 764, 'Songkhla'),
(3288, 764, 'Pattani'),
(3289, 764, 'Yala'),
(3290, 764, 'Yasothon'),
(3291, 764, 'Nakhon Phanom'),
(3292, 764, 'Prachin Buri'),
(3293, 764, 'Ubon Ratchathani'),
(3294, 764, 'Udon Thani'),
(3295, 764, 'Amnat Charoen'),
(3296, 764, 'Mukdahan'),
(3297, 764, 'Nong Bua Lamphu'),
(3298, 764, 'Sa Kaeo'),
(3299, 768, 'Amlame'),
(3300, 768, 'Aneho'),
(3301, 768, 'Atakpame'),
(3302, 768, 'Bafilo'),
(3303, 768, 'Bassar'),
(3304, 768, 'Dapaong'),
(3305, 768, 'Kante'),
(3306, 768, 'Klouto'),
(3307, 768, 'Lama-Kara'),
(3308, 768, 'Lome'),
(3309, 768, 'Mango'),
(3310, 768, 'Niamtougou'),
(3311, 768, 'Notse'),
(3312, 768, 'Kpagouda'),
(3313, 768, 'Badou'),
(3314, 768, 'Sotouboua'),
(3315, 768, 'Tabligbo'),
(3316, 768, 'Tsevie'),
(3317, 768, 'Tchamba'),
(3318, 768, 'Tchaoudjo'),
(3319, 768, 'Vogan'),
(3320, 768, 'Centrale'),
(3321, 768, 'Kara'),
(3322, 768, 'Maritime'),
(3323, 768, 'Plateaux'),
(3324, 768, 'Savanes'),
(3325, 772, 'Tokelau'),
(3326, 776, 'Ha‘apai'),
(3327, 776, 'Tongatapu'),
(3328, 776, 'Vava‘u'),
(3329, 780, 'Port of Spain'),
(3330, 780, 'San Fernando'),
(3331, 780, 'Chaguanas'),
(3332, 780, 'Arima'),
(3333, 780, 'Point Fortin Borough'),
(3334, 780, 'Couva-Tabaquite-Taaro'),
(3335, 780, 'Diego Martin'),
(3336, 780, 'Penal-Debe'),
(3337, 780, 'Princes Town'),
(3338, 780, 'Rio Claro-Mayaro'),
(3339, 780, 'San Juan-Laventille'),
(3340, 780, 'Sangre Grande'),
(3341, 780, 'Siparia'),
(3342, 780, 'Tunapuna-Piarco'),
(3343, 784, 'Abū Z̧aby'),
(3344, 784, 'Ajman'),
(3345, 784, 'Dubayy'),
(3346, 784, 'Al Fujayrah'),
(3347, 784, 'Raʼs al Khaymah'),
(3348, 784, 'Ash Shāriqah'),
(3349, 784, 'Umm al Qaywayn'),
(3350, 788, 'Tunis al Janubiyah Wilayat'),
(3351, 788, 'Al Qaşrayn'),
(3352, 788, 'Al Qayrawān'),
(3353, 788, 'Jundūbah'),
(3354, 788, 'Kef'),
(3355, 788, 'Al Mahdīyah'),
(3356, 788, 'Al Munastīr'),
(3357, 788, 'Bājah'),
(3358, 788, 'Banzart'),
(3359, 788, 'Nābul'),
(3360, 788, 'Silyānah'),
(3361, 788, 'Sūsah'),
(3362, 788, 'Bin ‘Arūs'),
(3363, 788, 'Madanīn'),
(3364, 788, 'Qābis'),
(3365, 788, 'Qafşah'),
(3366, 788, 'Qibilī'),
(3367, 788, 'Şafāqis'),
(3368, 788, 'Sīdī Bū Zayd'),
(3369, 788, 'Taţāwīn'),
(3370, 788, 'Tawzar'),
(3371, 788, 'Tūnis'),
(3372, 788, 'Zaghwān'),
(3373, 788, 'Ariana'),
(3374, 788, 'Manouba'),
(3375, 792, 'Adıyaman'),
(3376, 792, 'Afyonkarahisar'),
(3377, 792, 'Ağrı Province'),
(3378, 792, 'Amasya Province'),
(3379, 792, 'Antalya Province'),
(3380, 792, 'Artvin Province'),
(3381, 792, 'Aydın Province'),
(3382, 792, 'Balıkesir Province'),
(3383, 792, 'Bilecik Province'),
(3384, 792, 'Bingöl Province'),
(3385, 792, 'Bitlis Province'),
(3386, 792, 'Bolu Province'),
(3387, 792, 'Burdur Province'),
(3388, 792, 'Bursa'),
(3389, 792, 'Çanakkale Province'),
(3390, 792, 'Çorum Province'),
(3391, 792, 'Denizli Province'),
(3392, 792, 'Diyarbakır'),
(3393, 792, 'Edirne Province'),
(3394, 792, 'Elazığ'),
(3395, 792, 'Erzincan Province'),
(3396, 792, 'Erzurum Province'),
(3397, 792, 'Eskişehir Province'),
(3398, 792, 'Giresun Province'),
(3399, 792, 'Hatay Province'),
(3400, 792, 'Mersin Province'),
(3401, 792, 'Isparta Province'),
(3402, 792, 'Istanbul'),
(3403, 792, 'İzmir'),
(3404, 792, 'Kastamonu Province'),
(3405, 792, 'Kayseri Province'),
(3406, 792, 'Kırklareli Province'),
(3407, 792, 'Kırşehir Province'),
(3408, 792, 'Kocaeli Province'),
(3409, 792, 'Kütahya Province'),
(3410, 792, 'Malatya Province'),
(3411, 792, 'Manisa Province'),
(3412, 792, 'Kahramanmaraş Province'),
(3413, 792, 'Muğla Province'),
(3414, 792, 'Muş Province'),
(3415, 792, 'Nevşehir'),
(3416, 792, 'Ordu'),
(3417, 792, 'Rize'),
(3418, 792, 'Sakarya Province'),
(3419, 792, 'Samsun Province'),
(3420, 792, 'Sinop Province'),
(3421, 792, 'Sivas Province'),
(3422, 792, 'Tekirdağ Province'),
(3423, 792, 'Tokat'),
(3424, 792, 'Trabzon Province'),
(3425, 792, 'Tunceli Province'),
(3426, 792, 'Şanlıurfa Province'),
(3427, 792, 'Uşak Province'),
(3428, 792, 'Van Province'),
(3429, 792, 'Yozgat Province'),
(3430, 792, 'Ankara Province'),
(3431, 792, 'Gümüşhane'),
(3432, 792, 'Hakkâri Province'),
(3433, 792, 'Konya Province'),
(3434, 792, 'Mardin Province'),
(3435, 792, 'Niğde'),
(3436, 792, 'Siirt Province'),
(3437, 792, 'Aksaray Province'),
(3438, 792, 'Batman Province'),
(3439, 792, 'Bayburt'),
(3440, 792, 'Karaman Province'),
(3441, 792, 'Kırıkkale Province'),
(3442, 792, 'Şırnak Province'),
(3443, 792, 'Adana Province'),
(3444, 792, 'Çankırı Province'),
(3445, 792, 'Gaziantep Province'),
(3446, 792, 'Kars'),
(3447, 792, 'Zonguldak'),
(3448, 792, 'Ardahan Province'),
(3449, 792, 'Bartın Province'),
(3450, 792, 'Iğdır Province'),
(3451, 792, 'Karabük'),
(3452, 792, 'Kilis Province'),
(3453, 792, 'Osmaniye Province'),
(3454, 792, 'Yalova Province'),
(3455, 792, 'Düzce'),
(3456, 795, 'Ahal'),
(3457, 795, 'Balkan'),
(3458, 795, 'Daşoguz'),
(3459, 795, 'Lebap'),
(3460, 795, 'Mary'),
(3461, 796, 'Turks and Caicos Islands'),
(3462, 798, 'Tuvalu'),
(3463, 800, 'Eastern Region'),
(3464, 800, 'Northern Region'),
(3465, 800, 'Central Region'),
(3466, 800, 'Western Region'),
(3467, 804, 'Cherkasʼka Oblastʼ'),
(3468, 804, 'Chernihivsʼka Oblastʼ'),
(3469, 804, 'Chernivetsʼka Oblastʼ'),
(3470, 804, 'Dnipropetrovsʼka Oblastʼ'),
(3471, 804, 'Donetsʼka Oblastʼ'),
(3472, 804, 'Ivano-Frankivsʼka Oblastʼ'),
(3473, 804, 'Kharkivsʼka Oblastʼ'),
(3474, 804, 'Kherson Oblast'),
(3475, 804, 'Khmelʼnytsʼka Oblastʼ'),
(3476, 804, 'Kirovohradsʼka Oblastʼ'),
(3477, 804, 'Avtonomna Respublika Krym'),
(3478, 804, 'Misto Kyyiv'),
(3479, 804, 'Kiev Oblast'),
(3480, 804, 'Luhansʼka Oblastʼ'),
(3481, 804, 'Lʼvivsʼka Oblastʼ'),
(3482, 804, 'Mykolayivsʼka Oblastʼ'),
(3483, 804, 'Odessa Oblast'),
(3484, 804, 'Poltava Oblast'),
(3485, 804, 'Rivnensʼka Oblastʼ'),
(3486, 804, 'Misto Sevastopol'),
(3487, 804, 'Sumy Oblast'),
(3488, 804, 'Ternopilʼsʼka Oblastʼ'),
(3489, 804, 'Vinnytsʼka Oblastʼ'),
(3490, 804, 'Volynsʼka Oblastʼ'),
(3491, 804, 'Zakarpatsʼka Oblastʼ'),
(3492, 804, 'Zaporizʼka Oblastʼ'),
(3493, 804, 'Zhytomyrsʼka Oblastʼ'),
(3494, 807, 'Macedonia, The Former Yugoslav Republic of'),
(3495, 807, 'Aračinovo'),
(3496, 807, 'Bač'),
(3497, 807, 'Belčišta'),
(3498, 807, 'Berovo'),
(3499, 807, 'Bistrica'),
(3500, 807, 'Bitola'),
(3501, 807, 'Blatec'),
(3502, 807, 'Bogdanci'),
(3503, 807, 'Opstina Bogomila'),
(3504, 807, 'Bogovinje'),
(3505, 807, 'Bosilovo'),
(3506, 807, 'Brvenica'),
(3507, 807, 'Čair'),
(3508, 807, 'Capari'),
(3509, 807, 'Čaška'),
(3510, 807, 'Čegrana'),
(3511, 807, 'Centar'),
(3512, 807, 'Centar Župa'),
(3513, 807, 'Češinovo'),
(3514, 807, 'Čučer-Sandevo'),
(3515, 807, 'Debar'),
(3516, 807, 'Delčevo'),
(3517, 807, 'Delogoždi'),
(3518, 807, 'Demir Hisar'),
(3519, 807, 'Demir Kapija'),
(3520, 807, 'Dobruševo'),
(3521, 807, 'Dolna Banjica'),
(3522, 807, 'Dolneni'),
(3523, 807, 'Opstina Gjorce Petrov'),
(3524, 807, 'Drugovo'),
(3525, 807, 'Džepčište'),
(3526, 807, 'Gazi Baba'),
(3527, 807, 'Gevgelija'),
(3528, 807, 'Gostivar'),
(3529, 807, 'Gradsko'),
(3530, 807, 'Ilinden'),
(3531, 807, 'Izvor'),
(3532, 807, 'Jegunovce'),
(3533, 807, 'Kamenjane'),
(3534, 807, 'Karbinci'),
(3535, 807, 'Karpoš'),
(3536, 807, 'Kavadarci'),
(3537, 807, 'Kičevo'),
(3538, 807, 'Kisela Voda'),
(3539, 807, 'Klečevce'),
(3540, 807, 'Kočani'),
(3541, 807, 'Konče'),
(3542, 807, 'Kondovo'),
(3543, 807, 'Konopište'),
(3544, 807, 'Kosel'),
(3545, 807, 'Kratovo'),
(3546, 807, 'Kriva Palanka'),
(3547, 807, 'Krivogaštani'),
(3548, 807, 'Kruševo'),
(3549, 807, 'Kukliš'),
(3550, 807, 'Kukurečani'),
(3551, 807, 'Kumanovo'),
(3552, 807, 'Labuništa'),
(3553, 807, 'Opstina Lipkovo'),
(3554, 807, 'Lozovo'),
(3555, 807, 'Lukovo'),
(3556, 807, 'Makedonska Kamenica'),
(3557, 807, 'Makedonski Brod'),
(3558, 807, 'Mavrovi Anovi'),
(3559, 807, 'Mešeišta'),
(3560, 807, 'Miravci'),
(3561, 807, 'Mogila'),
(3562, 807, 'Murtino'),
(3563, 807, 'Negotino'),
(3564, 807, 'Negotino-Pološko'),
(3565, 807, 'Novaci'),
(3566, 807, 'Novo Selo'),
(3567, 807, 'Obleševo'),
(3568, 807, 'Ohrid'),
(3569, 807, 'Orašac'),
(3570, 807, 'Orizari'),
(3571, 807, 'Oslomej'),
(3572, 807, 'Pehčevo'),
(3573, 807, 'Petrovec'),
(3574, 807, 'Plasnica'),
(3575, 807, 'Podareš'),
(3576, 807, 'Prilep'),
(3577, 807, 'Probištip'),
(3578, 807, 'Radoviš'),
(3579, 807, 'Opstina Rankovce'),
(3580, 807, 'Resen'),
(3581, 807, 'Rosoman'),
(3582, 807, 'Opština Rostuša'),
(3583, 807, 'Samokov'),
(3584, 807, 'Saraj'),
(3585, 807, 'Šipkovica'),
(3586, 807, 'Sopište'),
(3587, 807, 'Sopotnica'),
(3588, 807, 'Srbinovo'),
(3589, 807, 'Staravina'),
(3590, 807, 'Star Dojran'),
(3591, 807, 'Staro Nagoričane'),
(3592, 807, 'Štip'),
(3593, 807, 'Struga'),
(3594, 807, 'Strumica'),
(3595, 807, 'Studeničani'),
(3596, 807, 'Šuto Orizari'),
(3597, 807, 'Sveti Nikole'),
(3598, 807, 'Tearce'),
(3599, 807, 'Tetovo'),
(3600, 807, 'Topolčani'),
(3601, 807, 'Valandovo'),
(3602, 807, 'Vasilevo'),
(3603, 807, 'Veles'),
(3604, 807, 'Velešta'),
(3605, 807, 'Vevčani'),
(3606, 807, 'Vinica'),
(3607, 807, 'Vitolište'),
(3608, 807, 'Vraneštica'),
(3609, 807, 'Vrapčište'),
(3610, 807, 'Vratnica'),
(3611, 807, 'Vrutok'),
(3612, 807, 'Zajas'),
(3613, 807, 'Zelenikovo'),
(3614, 807, 'Želino'),
(3615, 807, 'Žitoše'),
(3616, 807, 'Zletovo'),
(3617, 807, 'Zrnovci'),
(3618, 818, 'Ad Daqahlīyah'),
(3619, 818, 'Al Baḩr al Aḩmar'),
(3620, 818, 'Al Buḩayrah'),
(3621, 818, 'Al Fayyūm'),
(3622, 818, 'Al Gharbīyah'),
(3623, 818, 'Alexandria'),
(3624, 818, 'Al Ismā‘īlīyah'),
(3625, 818, 'Al Jīzah'),
(3626, 818, 'Al Minūfīyah'),
(3627, 818, 'Al Minyā'),
(3628, 818, 'Al Qāhirah'),
(3629, 818, 'Al Qalyūbīyah'),
(3630, 818, 'Al Wādī al Jadīd'),
(3631, 818, 'Eastern Province'),
(3632, 818, 'As Suways'),
(3633, 818, 'Aswān'),
(3634, 818, 'Asyūţ'),
(3635, 818, 'Banī Suwayf'),
(3636, 818, 'Būr Sa‘īd'),
(3637, 818, 'Dumyāţ'),
(3638, 818, 'Kafr ash Shaykh'),
(3639, 818, 'Maţrūḩ'),
(3640, 818, 'Qinā'),
(3641, 818, 'Sūhāj'),
(3642, 818, 'Janūb Sīnāʼ'),
(3643, 818, 'Shamāl Sīnāʼ'),
(3644, 818, 'Luxor'),
(3645, 818, 'Helwan'),
(3646, 818, '6th of October'),
(3647, 826, 'England'),
(3648, 826, 'Northern Ireland'),
(3649, 826, 'Scotland'),
(3650, 826, 'Wales'),
(3651, 831, 'Guernsey'),
(3652, 833, 'Isle of Man'),
(3653, 834, 'Arusha'),
(3654, 834, 'Pwani'),
(3655, 834, 'Dodoma'),
(3656, 834, 'Iringa'),
(3657, 834, 'Kigoma'),
(3658, 834, 'Kilimanjaro'),
(3659, 834, 'Lindi'),
(3660, 834, 'Mara'),
(3661, 834, 'Mbeya'),
(3662, 834, 'Morogoro'),
(3663, 834, 'Mtwara'),
(3664, 834, 'Mwanza'),
(3665, 834, 'Pemba North'),
(3666, 834, 'Ruvuma'),
(3667, 834, 'Shinyanga'),
(3668, 834, 'Singida'),
(3669, 834, 'Tabora'),
(3670, 834, 'Tanga'),
(3671, 834, 'Kagera'),
(3672, 834, 'Pemba South'),
(3673, 834, 'Zanzibar Central/South'),
(3674, 834, 'Zanzibar North'),
(3675, 834, 'Dar es Salaam'),
(3676, 834, 'Rukwa'),
(3677, 834, 'Zanzibar Urban/West'),
(3678, 834, 'Arusha'),
(3679, 834, 'Manyara'),
(3680, 840, 'Alaska'),
(3681, 840, 'Alabama'),
(3682, 840, 'Arkansas'),
(3683, 840, 'Arizona'),
(3684, 840, 'California'),
(3685, 840, 'Colorado'),
(3686, 840, 'Connecticut'),
(3687, 840, 'District of Columbia'),
(3688, 840, 'Delaware'),
(3689, 840, 'Florida'),
(3690, 840, 'Georgia'),
(3691, 840, 'Hawaii'),
(3692, 840, 'Iowa'),
(3693, 840, 'Idaho'),
(3694, 840, 'Illinois'),
(3695, 840, 'Indiana'),
(3696, 840, 'Kansas'),
(3697, 840, 'Kentucky'),
(3698, 840, 'Louisiana'),
(3699, 840, 'Massachusetts'),
(3700, 840, 'Maryland'),
(3701, 840, 'Maine'),
(3702, 840, 'Michigan'),
(3703, 840, 'Minnesota'),
(3704, 840, 'Missouri'),
(3705, 840, 'Mississippi'),
(3706, 840, 'Montana'),
(3707, 840, 'North Carolina'),
(3708, 840, 'North Dakota'),
(3709, 840, 'Nebraska'),
(3710, 840, 'New Hampshire'),
(3711, 840, 'New Jersey'),
(3712, 840, 'New Mexico'),
(3713, 840, 'Nevada'),
(3714, 840, 'New York'),
(3715, 840, 'Ohio'),
(3716, 840, 'Oklahoma'),
(3717, 840, 'Oregon'),
(3718, 840, 'Pennsylvania'),
(3719, 840, 'Rhode Island'),
(3720, 840, 'South Carolina'),
(3721, 840, 'South Dakota'),
(3722, 840, 'Tennessee'),
(3723, 840, 'Texas'),
(3724, 840, 'Utah'),
(3725, 840, 'Virginia'),
(3726, 840, 'Vermont'),
(3727, 840, 'Washington'),
(3728, 840, 'Wisconsin'),
(3729, 840, 'West Virginia'),
(3730, 840, 'Wyoming'),
(3731, 850, 'Virgin Islands'),
(3732, 854, 'Boucle du Mouhoun'),
(3733, 854, 'Cascades'),
(3734, 854, 'Centre'),
(3735, 854, 'Centre-Est'),
(3736, 854, 'Centre-Nord'),
(3737, 854, 'Centre-Ouest'),
(3738, 854, 'Centre-Sud'),
(3739, 854, 'Est'),
(3740, 854, 'Hauts-Bassins'),
(3741, 854, 'Nord'),
(3742, 854, 'Plateau-Central'),
(3743, 854, 'Sahel'),
(3744, 854, 'Sud-Ouest'),
(3745, 855, 'Komuna e Deçanit'),
(3746, 855, 'Komuna e Dragashit'),
(3747, 855, 'Komuna e Ferizajt'),
(3748, 855, 'Komuna e Fushë Kosovës'),
(3749, 855, 'Komuna e Gjakovës'),
(3750, 855, 'Komuna e Gjilanit'),
(3751, 855, 'Komuna e Drenasit'),
(3752, 855, 'Komuna e Istogut'),
(3753, 855, 'Komuna e Kaçanikut'),
(3754, 855, 'Komuna e Kamenicës'),
(3755, 855, 'Komuna e Klinës'),
(3756, 855, 'Komuna e Leposaviqit'),
(3757, 855, 'Komuna e Lipjanit'),
(3758, 855, 'Komuna e Malisheves'),
(3759, 855, 'Komuna e Mitrovicës'),
(3760, 855, 'Komuna e Novobërdës'),
(3761, 855, 'Komuna e Obiliqit'),
(3762, 855, 'Komuna e Pejës'),
(3763, 855, 'Komuna e Podujevës'),
(3764, 855, 'Komuna e Prishtinës'),
(3765, 855, 'Komuna e Prizrenit'),
(3766, 855, 'Komuna e Rahovecit'),
(3767, 855, 'Komuna e Shtërpcës'),
(3768, 855, 'Komuna e Shtimes'),
(3769, 855, 'Komuna e Skenderajt'),
(3770, 855, 'Komuna e Thërandës'),
(3771, 855, 'Komuna e Vitisë'),
(3772, 855, 'Komuna e Vushtrrisë'),
(3773, 855, 'Komuna e Zubin Potokut'),
(3774, 855, 'Komuna e Zveçanit'),
(3775, 858, 'Artigas Department'),
(3776, 858, 'Canelones Department'),
(3777, 858, 'Cerro Largo Department'),
(3778, 858, 'Colonia Department'),
(3779, 858, 'Durazno'),
(3780, 858, 'Flores'),
(3781, 858, 'Florida Department'),
(3782, 858, 'Lavalleja Department'),
(3783, 858, 'Maldonado Department'),
(3784, 858, 'Montevideo'),
(3785, 858, 'Paysandú'),
(3786, 858, 'Río Negro'),
(3787, 858, 'Rivera'),
(3788, 858, 'Rocha'),
(3789, 858, 'Salto'),
(3790, 858, 'San José'),
(3791, 858, 'Soriano Department'),
(3792, 858, 'Tacuarembó'),
(3793, 858, 'Treinta y Tres'),
(3794, 860, 'Andijon'),
(3795, 860, 'Buxoro'),
(3796, 860, 'Farg ona'),
(3797, 860, 'Xorazm'),
(3798, 860, 'Namangan'),
(3799, 860, 'Navoiy'),
(3800, 860, 'Qashqadaryo'),
(3801, 860, 'Karakaakstan'),
(3802, 860, 'Samarqand'),
(3803, 860, 'Surxondaryo'),
(3804, 860, 'Toshkent Shahri'),
(3805, 860, 'Toshkent'),
(3806, 860, 'Jizzax'),
(3807, 860, 'Sirdaryo'),
(3808, 862, 'Amazonas'),
(3809, 862, 'Anzoátegui'),
(3810, 862, 'Apure'),
(3811, 862, 'Aragua'),
(3812, 862, 'Barinas'),
(3813, 862, 'Bolívar'),
(3814, 862, 'Carabobo'),
(3815, 862, 'Cojedes'),
(3816, 862, 'Delta Amacuro'),
(3817, 862, 'Distrito Federal'),
(3818, 862, 'Falcón'),
(3819, 862, 'Guárico'),
(3820, 862, 'Lara'),
(3821, 862, 'Mérida'),
(3822, 862, 'Miranda'),
(3823, 862, 'Monagas'),
(3824, 862, 'Isla Margarita'),
(3825, 862, 'Portuguesa'),
(3826, 862, 'Sucre'),
(3827, 862, 'Táchira'),
(3828, 862, 'Trujillo'),
(3829, 862, 'Yaracuy'),
(3830, 862, 'Zulia'),
(3831, 862, 'Dependencias Federales'),
(3832, 862, 'Distrito Capital'),
(3833, 862, 'Vargas'),
(3834, 882, 'A‘ana'),
(3835, 882, 'Aiga-i-le-Tai'),
(3836, 882, 'Atua'),
(3837, 882, 'Fa‘asaleleaga'),
(3838, 882, 'Gaga‘emauga'),
(3839, 882, 'Va‘a-o-Fonoti'),
(3840, 882, 'Gagaifomauga'),
(3841, 882, 'Palauli'),
(3842, 882, 'Satupa‘itea'),
(3843, 882, 'Tuamasaga'),
(3844, 882, 'Vaisigano'),
(3845, 887, 'Abyan'),
(3846, 887, '‘Adan'),
(3847, 887, 'Al Mahrah'),
(3848, 887, 'Ḩaḑramawt'),
(3849, 887, 'Shabwah'),
(3850, 887, 'San’a’'),
(3851, 887, 'Ta’izz'),
(3852, 887, 'Al Ḩudaydah'),
(3853, 887, 'Dhamar'),
(3854, 887, 'Al Maḩwīt'),
(3855, 887, 'Dhamār'),
(3856, 887, 'Maʼrib'),
(3857, 887, 'Şa‘dah'),
(3858, 887, 'Şan‘āʼ'),
(3859, 887, 'Aḑ Ḑāli‘'),
(3860, 887, 'Omran'),
(3861, 887, 'Al Bayḑāʼ'),
(3862, 887, 'Al Jawf'),
(3863, 887, 'Ḩajjah'),
(3864, 887, 'Ibb'),
(3865, 887, 'Laḩij'),
(3866, 887, 'Ta‘izz'),
(3867, 887, 'Amanat Al Asimah'),
(3868, 887, 'Muḩāfaz̧at Raymah'),
(3869, 891, 'Crna Gora (Montenegro)'),
(3870, 891, 'Srbija (Serbia)'),
(3871, 894, 'North-Western'),
(3872, 894, 'Copperbelt'),
(3873, 894, 'Western'),
(3874, 894, 'Southern'),
(3875, 894, 'Central'),
(3876, 894, 'Eastern'),
(3877, 894, 'Northern'),
(3878, 894, 'Luapula'),
(3879, 894, 'Lusaka');

-- -----------------------------------------------------------------

INSERT INTO `days` (`day_id`, `day`,`create_date`, `change_date`) VALUES
(1, 'mon', '2009-05-29', '2009-05-29 10:49:06'),
(2, 'tue', '2009-05-29', '2009-05-29 10:49:59'),
(3, 'wen', '2009-05-29', '2009-07-24 10:42:50'),
(4, 'thu', '2009-05-29', '2009-05-29 10:50:13'),
(5, 'fri', '2009-05-29', '2009-05-29 10:50:19'),
(6, 'sat', '2009-05-29', '2009-05-29 10:50:23'),
(7, 'sun', '2009-05-29', '2009-05-29 10:50:27');

-- -----------------------------------------------------------------

INSERT INTO `minutes` (`min_id`, `minute`) VALUES
(1, '00'),
(2, '01'),
(3, '02'),
(4, '03'),
(5, '04'),
(6, '05'),
(7, '06'),
(8, '07'),
(9, '08'),
(10, '09'),
(11, '10'),
(12, '11'),
(13, '12'),
(14, '13'),
(15, '14'),
(16, '15'),
(17, '16'),
(18, '17'),
(19, '18'),
(20, '19'),
(21, '20'),
(22, '21'),
(23, '22'),
(24, '23'),
(25, '24'),
(26, '25'),
(27, '26'),
(28, '27'),
(29, '28'),
(30, '29'),
(31, '30'),
(32, '31'),
(33, '32'),
(34, '33'),
(35, '34'),
(36, '35'),
(37, '36'),
(38, '37'),
(39, '38'),
(40, '39'),
(41, '40'),
(42, '41'),
(43, '42'),
(44, '43'),
(45, '44'),
(46, '45'),
(47, '46'),
(48, '47'),
(49, '48'),
(50, '49'),
(51, '50'),
(52, '51'),
(53, '52'),
(54, '53'),
(55, '54'),
(56, '55'),
(57, '56'),
(58, '57'),
(59, '58'),
(60, '59');
-- -----------------------------------------------------------------

INSERT INTO `security_question` (`security_question_id`, `question`,`create_date`,`change_date`) VALUES
(1, 'what is your pet name?', '2009-06-02', '2009-06-02 05:12:26'),
(2, 'what is your favourite dish?', '2009-06-02', '2009-06-02 05:12:46'),
(3, 'what is your first school name?', '2009-06-02', '2009-06-02 05:13:17'),
(4, 'what is your mother''s maiden name?', '2009-06-02', '2009-06-02 05:13:44');
-- -----------------------------------------------------------------
INSERT INTO `user` (`user_typeid`, `user_type`,`create_date`, `change_date`) VALUES

(1, 'appmanager', '2009-05-28', '2009-05-28 11:02:33'),
(2, 'admin', '2009-05-28', '2009-05-28 11:02:33'),
(3, 'teacher', '2009-05-28', '2009-05-28 11:02:47'),
(4, 'parent', '2009-05-28', '2009-05-28 11:02:58'),
(5, 'student above 13', '2009-05-28', '2009-06-11 05:30:23'),
(6, 'student below 13', '2009-06-11', '2009-06-11 05:31:31');


-- ----------------------------------------------------------------
INSERT INTO `school_type` (`school_type_id`, `school_type_name`) VALUES
(1, 'Public'),(2, 'Private'),(3, 'Chartered'),(4, 'Home'), (5, 'Other'); 

-- -----------------------------------------------------------------------

insert into `school_level` (`school_level_id`, `school_level_name`) values 
(1, 'Elementary'),(2, 'Middle School'),(3, 'Junior High School'),(4, 'High School'), (5, 'Home School'), (6, 'Other'); 

-- Changes made to teacher_subjects table

ALTER TABLE teacher_subjects ADD CONSTRAINT fk_teacSub_grade_id FOREIGN KEY  (grade_id) REFERENCES grade(grade_id);

ALTER TABLE teacher_subjects ADD CONSTRAINT fk_teacSub_class_id FOREIGN KEY  (class_id) REFERENCES class(class_id);

ALTER TABLE teacher_subjects ADD CONSTRAINT fk_teacSub_grade_level_id FOREIGN KEY  (grade_level_id) REFERENCES grade_level(grade_level_id);

-- Changes made to register_for_class table

ALTER TABLE register_for_class
DROP FOREIGN KEY fk_rfc_class_id;

ALTER TABLE register_for_class drop column class_id;

ALTER TABLE `register_for_class` 
ADD COLUMN `grade_class_id` BIGINT(20) NULL , 
ADD CONSTRAINT `FK_grade_class_id_2` FOREIGN KEY (`grade_class_id`) 
REFERENCES `grade_classes`(`grade_class_id`);

ALTER TABLE `register_for_class` 
ADD COLUMN `grade_level_id` BIGINT(20) NOT NULL , 
ADD CONSTRAINT `FK_grade_level_id_2` FOREIGN KEY (`grade_level_id`)
REFERENCES `grade_level`(`grade_level_id`);

-- Changes made to school_days table
ALTER TABLE school_days
DROP FOREIGN KEY fk_school_schedule_id;

ALTER TABLE school_days drop column school_schedule_id;

ALTER TABLE `school_days` 
ADD COLUMN `school_id` BIGINT(20) NULL , 
ADD CONSTRAINT `FK_school_id_2` FOREIGN KEY (`school_id`) 
REFERENCES `school`(`school_id`); 

ALTER TABLE `school_days` 
ADD COLUMN `status` VARCHAR(20) NULL ;

alter table school add column promot_startdate date null;

alter table school add column promot_enddate date null;

alter table school add column school_abbr varchar(50);

alter table school add constraint sch_abbr_uni unique(school_abbr); 

CREATE TABLE IF NOT EXISTS `district` (
  `district_id` BIGINT NOT NULL AUTO_INCREMENT,
  `district_name` varchar(30) NOT NULL DEFAULT '',
  `no_schools` BIGINT  DEFAULT '0',
  `logo_link` varchar(50) DEFAULT '',
  `address` varchar(30) NOT NULL DEFAULT '',
 
  `state_id` BIGINT NULL,
  `city` varchar(30) NOT NULL DEFAULT '',
  `phone_number` varchar(30) DEFAULT '',
  `fax_number` varchar(30) DEFAULT '',
  PRIMARY KEY (`district_id`),
   CONSTRAINT `fk_state_ids`
    FOREIGN KEY (`state_id`)
    REFERENCES `states` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

 alter table school add column district_id bigint default null;

 ALTER TABLE SCHOOL ADD CONSTRAINT fk_school_dist_id FOREIGN KEY  (district_id) REFERENCES district(district_id);

 alter table user_registration add column district_id bigint default null;

 ALTER TABLE user_registration ADD CONSTRAINT fk_userregis_dist_id FOREIGN KEY  (district_id) REFERENCES district(district_id);

 alter table user_registration add column status varchar(20) not null; 
 
 update user_registration set status='active';
 
 alter table register_for_class modify section_id bigint default null;
 
 --  Added on 3/23/2015
 
 ALTER TABLE register_for_class modify status varchar(20) default '';
 ALTER TABLE register_for_class modify class_status varchar(30) default 'alive';
 
 -- Added on 3/24/2015
 
 ALTER TABLE register_for_class DROP PRIMARY KEY, ADD PRIMARY KEY(student_id, grade_class_id);
 
 -- Added on 3/26/2015
 
 ALTER TABLE `edulink1_lpriority`.`class_actual_schedule` 
	ADD CONSTRAINT `fk_day_id1`
  FOREIGN KEY (`day_id`)
  REFERENCES `edulink1_lpriority`.`days` (`day_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

 
 -- Added on 3/31/2015
 
ALTER TABLE `edulink1_lpriority`.`unit` 
ADD COLUMN `grade_class_id` BIGINT NOT NULL AFTER `unit_id`,
ADD COLUMN `created_by` BIGINT NOT NULL AFTER `grade_class_id`,
ADD INDEX `fk_grade_class_id1_idx` (`grade_class_id` ASC),
ADD INDEX `fk_created_by_reg_id_idx` (`created_by` ASC);
ALTER TABLE `edulink1_lpriority`.`unit` 
ADD CONSTRAINT `fk_grade_class_id1`
  FOREIGN KEY (`grade_class_id`)
  REFERENCES `edulink1_lpriority`.`grade_classes` (`grade_class_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_created_by_reg_id`
  FOREIGN KEY (`created_by`)
  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
ALTER TABLE `edulink1_lpriority`.`lesson` 
DROP FOREIGN KEY `fk_unit_id`,
DROP FOREIGN KEY `fk_lesson_grade`,
DROP FOREIGN KEY `fk_lesson_class1`;
ALTER TABLE `edulink1_lpriority`.`lesson` 
DROP COLUMN `created_by`,
DROP COLUMN `class_id`,
DROP COLUMN `grade_id`,
CHANGE COLUMN `unit_id` `unit_id` BIGINT(20) NOT NULL AFTER `lesson_id`,
DROP INDEX `fk_lesson_grade_idx` ,
DROP INDEX `fk_lesson_class1_idx` ;
ALTER TABLE `edulink1_lpriority`.`lesson` 
ADD CONSTRAINT `fk_unit_id`
  FOREIGN KEY (`unit_id`)
  REFERENCES `edulink1_lpriority`.`unit` (`unit_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
ALTER TABLE `edulink1_lpriority`.`admin_teacher_reports` 
CHANGE COLUMN `user_typeid` `user_typeid` BIGINT NOT NULL ;

ALTER TABLE `edulink1_lpriority`.`admin_teacher_reports` 
ADD INDEX `fk_user_typeid1_idx` (`user_typeid` ASC);
ALTER TABLE `edulink1_lpriority`.`admin_teacher_reports` 
ADD CONSTRAINT `fk_user_typeid1`
  FOREIGN KEY (`user_typeid`)
  REFERENCES `edulink1_lpriority`.`user` (`user_typeid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Added on 4/6/2015

CREATE TABLE `edulink1_lpriority`.`teacher_reports` (
`report_id` BIGINT NOT NULL,
PRIMARY KEY (`report_id`));

ALTER TABLE `edulink1_lpriority`.`admin_teacher_reports` 
DROP FOREIGN KEY `performance_id`;
ALTER TABLE `edulink1_lpriority`.`admin_teacher_reports` 
DROP COLUMN `comments`,
DROP COLUMN `choosen_option`,
DROP COLUMN `performance_id`,
CHANGE COLUMN `admin_teacher_report_id` `admin_teacher_report_id` BIGINT(20) NOT NULL ,
DROP INDEX `performance_id_idx` ;


ALTER TABLE `edulink1_lpriority`.`teacher_reports` 
ADD COLUMN `admin_teacher_report_id` BIGINT NOT NULL AFTER `report_id`,
ADD COLUMN `performance_id` BIGINT(20) NOT NULL AFTER `admin_teacher_report_id`,
ADD COLUMN `choosen_option` VARCHAR(20) NOT NULL AFTER `performance_id`,
ADD COLUMN `comments` VARCHAR(200) NOT NULL AFTER `choosen_option`;

ALTER TABLE `edulink1_lpriority`.`teacher_reports` 
ADD INDEX `fk_admin_teacher_report_id_idx` (`admin_teacher_report_id` ASC);
ALTER TABLE `edulink1_lpriority`.`teacher_reports` 
ADD CONSTRAINT `fk_admin_teacher_report_id`
  FOREIGN KEY (`admin_teacher_report_id`)
  REFERENCES `edulink1_lpriority`.`admin_teacher_reports` (`admin_teacher_report_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
ALTER TABLE `edulink1_lpriority`.`teacher_reports` 
ADD INDEX `fk_performance_id_idx` (`performance_id` ASC);
ALTER TABLE `edulink1_lpriority`.`teacher_reports` 
ADD CONSTRAINT `fk_performance_id`
  FOREIGN KEY (`performance_id`)
  REFERENCES `edulink1_lpriority`.`teacher_performances` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`activity` 
ADD INDEX `fk_user_reg_id2_idx` (`created_by` ASC);
ALTER TABLE `edulink1_lpriority`.`activity` 
ADD CONSTRAINT `fk_user_reg_id2`
  FOREIGN KEY (`created_by`)
  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `edulink1_lpriority`.`teacher_reports` 
CHANGE COLUMN `report_id` `report_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `edulink1_lpriority`.`admin_teacher_reports` 
CHANGE COLUMN `admin_teacher_report_id` `admin_teacher_report_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;


-- Added on 4/9/2015

ALTER TABLE `edulink1_lpriority`.`unit` ADD UNIQUE INDEX(grade_class_id, unit_name);

ALTER TABLE `edulink1_lpriority`.`lesson` ADD UNIQUE INDEX(unit_id, lesson_name);

-- Added on 4/10/2015

ALTER TABLE `edulink1_lpriority`.`register_for_class` 
CHANGE COLUMN `class_status` `class_status` VARCHAR(30) NULL DEFAULT 'alive' ;



--  Added on 4/13/2015

ALTER TABLE `edulink1_lpriority`.`questions` 
DROP FOREIGN KEY `sub_question_id`;
ALTER TABLE `edulink1_lpriority`.`questions` 
CHANGE COLUMN `sub_question_id` `sub_question_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`questions` 
ADD CONSTRAINT `sub_question_id`
  FOREIGN KEY (`sub_question_id`)
  REFERENCES `edulink1_lpriority`.`sub_questions` (`sub_question_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
 
  
-- Added on 4/13/2015 by Prasad BHVN

-- -----------------------------------------------------
-- Alter Table `assignment_type`
-- -----------------------------------------------------
ALTER TABLE `assignment_type` ADD `used_for` varchar(50) NULL;

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='all' WHERE `assignment_type_id`='1';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='all' WHERE `assignment_type_id`='2';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='all' WHERE `assignment_type_id`='3';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='all' WHERE `assignment_type_id`='4';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='all' WHERE `assignment_type_id`='5';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='all' WHERE `assignment_type_id`='6';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='rti' WHERE `assignment_type_id`='7';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='rti' WHERE `assignment_type_id`='8';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='assessments' WHERE `assignment_type_id`='13';

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='homeworks' WHERE `assignment_type_id`='14';


-- -----------------------------------------------------
-- Alter Table `questions`
-- ----------------------------------------------------- 

ALTER TABLE `edulink1_lpriority`.`questions` DROP FOREIGN KEY `assignment_type_id` ;

ALTER TABLE `edulink1_lpriority`.`questions` 
  ADD CONSTRAINT `assignment_type_id`
  FOREIGN KEY (`assignment_type_id` )
  REFERENCES `edulink1_lpriority`.`assignment_type` (`assignment_type_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- --------------------------------------------------

  
  -- Added on 4/13/2015 by Lalitha 

ALTER TABLE `edulink1_lpriority`.`activity` 
 DROP FOREIGN KEY `fk_user_reg_id2`;

ALTER TABLE `edulink1_lpriority`.`activity` 
	ADD CONSTRAINT `fk_created_by2`
  	FOREIGN KEY (`created_by`)
 	REFERENCES `edulink1_lpriority`.`user` (`user_typeid`)
  	ON DELETE NO ACTION
  	ON UPDATE NO ACTION;
  	
  	
ALTER TABLE `edulink1_lpriority`.`lesson` 
ADD COLUMN `created_by` BIGINT NULL DEFAULT NULL AFTER `change_date`,
ADD INDEX `fk_created_by3_idx` (`created_by` ASC);
ALTER TABLE `edulink1_lpriority`.`lesson` 
ADD CONSTRAINT `fk_created_by3`
  FOREIGN KEY (`created_by`)
  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  	

 -- Added on 4/16/2015 by Lalitha 
 
ALTER TABLE `edulink1_lpriority`.`assign_lessons` 
DROP FOREIGN KEY `fk_assign_cs_id`;
ALTER TABLE `edulink1_lpriority`.`assign_lessons` 
ADD CONSTRAINT `fk_assign_cs_id`
  FOREIGN KEY (`cs_id`)
  REFERENCES `edulink1_lpriority`.`class_status` (`cs_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- --------------------------------------------------
-- 17-april-2015 By PRASAD
--  -------------------
-- -----------------------------------------------------
-- Table `jac_question_file`
-- -----------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`jac_question_file` (
  `jac_question_file_id` BIGINT NOT NULL AUTO_INCREMENT,
  `lesson_id` BIGINT NOT NULL,
  `reg_id` BIGINT NOT NULL,
  `filename` VARCHAR(45) NOT NULL,
  `used_for` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`jac_question_file_id`),
  INDEX `lesson_id_idx` (`lesson_id` ASC),
  INDEX `fk_reg_id_idx` (`reg_id` ASC),
  INDEX `fk_used_for_idx` (`used_for` ASC),
  CONSTRAINT `jac_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `edulink1_lpriority`.`lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `jac_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `jac_used_for`
    FOREIGN KEY (`used_for`)
    REFERENCES `edulink1_lpriority`.`assignment_type` (`assignment_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `jac_template`
-- -----------------------------------------------------
CREATE TABLE `edulink1_lpriority`.`jac_template` (
  `jac_template_id` BIGINT NOT NULL AUTO_INCREMENT,
  `no_of_questions` BIGINT NOT NULL,
  `title_name` VARCHAR(45) NOT NULL,
  `jac_question_file_id` BIGINT NOT NULL,
  PRIMARY KEY (`jac_template_id`),
  INDEX `fk_jac_question_file_id_idx` (`jac_question_file_id` ASC),
  CONSTRAINT `fk_jac_question_file_id`
    FOREIGN KEY (`jac_question_file_id`)
    REFERENCES `edulink1_lpriority`.`jac_question_file` (`jac_question_file_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `Added new column to `questions` table
-- -----------------------------------------------------
ALTER TABLE `edulink1_lpriority`.`questions` 
ADD COLUMN `jac_template_id` BIGINT NULL AFTER `pt_directions`,
ADD INDEX `fk_jac_template_id_idx` (`jac_template_id` ASC);
ALTER TABLE `edulink1_lpriority`.`questions` 
ADD CONSTRAINT `fk_jac_template_id`
  FOREIGN KEY (`jac_template_id`)
  REFERENCES `edulink1_lpriority`.`jac_template` (`jac_template_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- -----------------------------------------------------
-- Table `Added new column to `questions` table - PRASADBHVN & SANTHOSH - 20/04/2015
-- -----------------------------------------------------
  ALTER TABLE `edulink1_lpriority`.`jac_question_file` DROP FOREIGN KEY `jac_used_for` ;
  ALTER TABLE `edulink1_lpriority`.`questions` CHANGE COLUMN `question` `question` VARCHAR(500) NULL DEFAULT NULL  ;


  -- Added by Lalitha
  
  ALTER TABLE assign_lessons ADD UNIQUE KEY `uk_cs_lesson_id` (cs_id, lesson_id);
  
  -- Added by Anusuya
  
  ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
	CHANGE COLUMN `benchmark_assignment_id` `benchmark_assignment_id` INT(11) NULL DEFAULT NULL ,
	CHANGE COLUMN `retest_id` `retest_id` INT(11) NULL DEFAULT NULL ;
	
  -- Added by Lalitha
	ALTER TABLE `edulink1_lpriority`.`class` 
		DROP INDEX `class_name_UNIQUE` ,
		ADD UNIQUE INDEX `class_name_UNIQUE` (`class_name` ASC, `school_id` ASC);
		
	ALTER TABLE `edulink1_lpriority`.`grade` 
		ADD UNIQUE INDEX `fk_unique_grades` (`school_id` ASC, `master_grades_id` ASC);
		
  -- Added by Lalitha on 4/23/2015
  
ALTER TABLE `edulink1_lpriority`.`sub_questions` 
	ADD COLUMN `no_of_options` INT NOT NULL AFTER `sub_question`;

-- Added by Lalitha on 4/24/2015	
	ALTER TABLE `edulink1_lpriority`.`assignment` 
		DROP FOREIGN KEY `fk_asngt_cs_id`,
		DROP FOREIGN KEY `fk_assignment_type_id`;
	ALTER TABLE `edulink1_lpriority`.`assignment` 
		CHANGE COLUMN `cs_id` `cs_id` BIGINT(20) NOT NULL ,
		CHANGE COLUMN `assignment_type_id` `assignment_type_id` BIGINT(20) NOT NULL ,
		CHANGE COLUMN `objective` `objective` VARCHAR(100) NULL DEFAULT NULL ;
	ALTER TABLE `edulink1_lpriority`.`assignment` 
		ADD CONSTRAINT `fk_asngt_cs_id`
 		FOREIGN KEY (`cs_id`)
  		REFERENCES `edulink1_lpriority`.`class_status` (`cs_id`)
  		ON DELETE NO ACTION
  		ON UPDATE NO ACTION,
		ADD CONSTRAINT `fk_assignment_type_id`
 		FOREIGN KEY (`assignment_type_id`)
  		REFERENCES `edulink1_lpriority`.`assignment_type` (`assignment_type_id`)
 		ON DELETE NO ACTION
 		ON UPDATE NO ACTION;
  
  
-- Added by Lalitha on 4/27/2015	
  ALTER TABLE `edulink1_lpriority`.`assignment_questions` 
	ADD COLUMN `errors_address` VARCHAR(100) NULL DEFAULT NULL AFTER `b_count_of_errors`,
	ADD COLUMN `words_read` INT(11) NULL DEFAULT NULL AFTER `errors_address`;

-- Added by prasad on 4/28/2015
ALTER TABLE `edulink1_lpriority`.`jac_question_file` 
CHANGE COLUMN `filename` `filename` VARCHAR(200) NOT NULL ;

-- --------------------------------------------------------
-- Added by santosh on 5/05/2015

INSERT INTO `edulink1_lpriority`.`rubric_scalings` (`rubric_scaling`) VALUES (3);

INSERT INTO `edulink1_lpriority`.`rubric_scalings` (`rubric_scaling`) VALUES (4);

INSERT INTO `edulink1_lpriority`.`rubric_scalings` (`rubric_scaling`) VALUES (5);

INSERT INTO `edulink1_lpriority`.`rubric_scalings` (`rubric_scaling`) VALUES (6);

ALTER TABLE `edulink1_lpriority`.`rubric` ADD COLUMN `score` VARCHAR(45) NULL  AFTER `dimension4` ;

-- ----------------------------------------------------------

-- Added by christopher on 05/06/2015

ALTER TABLE `edulink1_lpriority`.`teacher_subjects` 
DROP COLUMN `requested_class_id`;

ALTER TABLE `edulink1_lpriority`.`register_for_class` 
CHANGE COLUMN `class_status` `class_status` VARCHAR(30) NOT NULL DEFAULT 'alive' ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`student_id`, `grade_class_id`, `status`, `class_status`);

ALTER TABLE `edulink1_lpriority`.`register_for_class` 
DROP FOREIGN KEY `fk_rfc_rti_group_id`;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
CHANGE COLUMN `rti_group_id` `rti_group_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD CONSTRAINT `fk_rfc_rti_group_id`
  FOREIGN KEY (`rti_group_id`)
  REFERENCES `edulink1_lpriority`.`rti_groups` (`rti_group_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- Added by Lalitha on 5/6/2015
ALTER TABLE `edulink1_lpriority`.`assignment` CHANGE COLUMN `objective` `assign_status` VARCHAR(10) NOT NULL  ;
-- ----------------------------------------------------------
-- Added by christopher on 05/08/2015

CREATE TABLE `k1_tests` (
`set_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
`cs_id` BIGINT(20) NOT NULL DEFAULT '0',
`sets` VARCHAR(200) NULL DEFAULT NULL,
`test_date` date DEFAULT NULL,
`test_type` varchar(10) DEFAULT NULL,
`due_date` date DEFAULT NULL,
`title` varchar(30) DEFAULT NULL,
`set_type` VARCHAR(10) NOT NULL,
PRIMARY KEY (`set_id`),
KEY `fk_k1_section_id` (`cs_id`),
CONSTRAINT `fk_k1_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;


CREATE TABLE `k1_test_marks` (
`k1_test_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
`set_id` BIGINT(20) NOT NULL DEFAULT '0',
`student_id` BIGINT(20) NOT NULL DEFAULT '0',
`test_status` varchar(20) NOT NULL,
`graded_status` varchar(20) NOT NULL,
`marks_graded` varchar(60) DEFAULT NULL,
`score` BIGINT(20) DEFAULT NULL,
`submission_date` date DEFAULT NULL,
PRIMARY KEY (`k1_test_id`),
KEY `fk_set_id` (`set_id`),
KEY `fk_student_id` (`student_id`),
CONSTRAINT `fk_set_id` FOREIGN KEY (`set_id`) REFERENCES `k1_tests` (`set_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_k1_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8; 
-- ----------------------------------------------------------

-- Added by Santosh on 08-05-2015

ALTER TABLE `edulink1_lpriority`.`assigned_tasks` ADD COLUMN `calculations` LONGTEXT NULL  AFTER `writing` , 
ADD COLUMN `uploadarea` MEDIUMTEXT NULL  AFTER `calculations` , ADD COLUMN `audiofile` VARCHAR(100) NULL  AFTER `uploadarea` , 
ADD COLUMN `chatcontents` LONGTEXT NULL  AFTER `audiofile` , CHANGE COLUMN `performance_task_id` `performance_task_id` BIGINT(20) NOT NULL  , 
  ADD CONSTRAINT `fk_qusetion_id`
  FOREIGN KEY (`performance_task_id` )
  REFERENCES `edulink1_lpriority`.`questions` (`question_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_qusetion_id` (`performance_task_id` ASC) , RENAME TO  `edulink1_lpriority`.`assigned_ptasks` ;

-- ------

CREATE  TABLE `edulink1_lpriority`.`performance_group_students` (
  `performance_group_students_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `performance_group_id` BIGINT NOT NULL ,
  `student_id` BIGINT NOT NULL ,
  `status` VARCHAR(45) NULL ,
  PRIMARY KEY (`performance_group_students_id`) ,
  INDEX `fk_performance_group` (`performance_group_id` ASC) ,
  INDEX `fk_per_student_id` (`student_id` ASC) ,
  CONSTRAINT `fk_performance_group`
    FOREIGN KEY (`performance_group_id` )
    REFERENCES `edulink1_lpriority`.`performancetask_groups` (`performance_group_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_per_student_id`
    FOREIGN KEY (`student_id` )
    REFERENCES `edulink1_lpriority`.`student` (`student_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- --------------------------------------------------------------------------------
-- Added by Santosh on 08-05-2015

ALTER TABLE `edulink1_lpriority`.`performancetask_groups` 
ADD UNIQUE INDEX `fk_unique_group` (`cs_id` ASC, `group_name` ASC) ;

-- ---------------------------------------------------------------------------------
-- Added by Santosh on 12-05-2015

ALTER TABLE `edulink1_lpriority`.`student_assignment_status` DROP FOREIGN KEY `fk_student_id` ;
ALTER TABLE `edulink1_lpriority`.`student_assignment_status` CHANGE COLUMN `student_id` `student_id` BIGINT(20) NULL DEFAULT '0'  , 
  ADD CONSTRAINT `fk_student_id`
  FOREIGN KEY (`student_id` )
  REFERENCES `edulink1_lpriority`.`student` (`student_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`student_assignment_status` ADD COLUMN `performance_group_id` BIGINT(20) NULL  AFTER `student_id` , 
  ADD CONSTRAINT `fk_performance_groups`
  FOREIGN KEY (`performance_group_id` )
  REFERENCES `edulink1_lpriority`.`performancetask_groups` (`performance_group_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_performance_group` (`performance_group_id` ASC) ;

ALTER TABLE `edulink1_lpriority`.`student_assignment_status` ADD COLUMN `academic_grade_id` BIGINT(20) NULL  AFTER `retest_id` , 
  ADD CONSTRAINT `fk_academic_grade_id`
  FOREIGN KEY (`academic_grade_id` )
  REFERENCES `edulink1_lpriority`.`academic_grades` (`acedamic_grade_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_academic_grade_id` (`academic_grade_id` ASC) ;

-- ---------------------------------------------------------------------------------

-- Added by Lalitha on 5/8/2015
	ALTER TABLE `edulink1_lpriority`.`assignment_questions` 
		DROP COLUMN `b_retell_path`,
		CHANGE COLUMN `b_retellpath` `b_retell_path` VARCHAR(45) NULL DEFAULT NULL ;
-- Added by Lalitha on 5/14/2015
	ALTER TABLE `edulink1_lpriority`.`grade_classes` 
		ADD UNIQUE INDEX `unique_key` (`grade_id` ASC, `class_id` ASC, `status` ASC);
-- ------------------------------------------------------------------------------------
-- Added by Santosh on 15-05-2015

  ALTER TABLE `edulink1_lpriority`.`rubric_values` DROP FOREIGN KEY `fk_rubric_id` , DROP FOREIGN KEY `fk_rubric_score_id` ;
  ALTER TABLE `edulink1_lpriority`.`rubric_values` CHANGE COLUMN `rubric_score_id` `user_type_id` BIGINT(20) NOT NULL  , 
  CHANGE COLUMN `rubric_id` `assigned_task_id` BIGINT(20) NOT NULL  , 
  ADD CONSTRAINT `fk_assigned_task_id`
  FOREIGN KEY (`assigned_task_id` )
  REFERENCES `edulink1_lpriority`.`assigned_ptasks` (`assigned_task_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_user_type_id`
  FOREIGN KEY (`user_type_id` )
  REFERENCES `edulink1_lpriority`.`user` (`user_typeid` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  ALTER TABLE `edulink1_lpriority`.`rubric` DROP FOREIGN KEY `fk_rubric_stud_assign_id` , DROP FOREIGN KEY `fk_rubric_performance_grp_id` ;
  ALTER TABLE `edulink1_lpriority`.`rubric` DROP COLUMN `performance_group_id` , DROP COLUMN `student_assignment_id`, 
  DROP INDEX `fk_rubric_stud_assign_id_idx`, 
  DROP INDEX `fk_rubric_performance_grp_id_idx` ;
		
-- ----------------------------------------------------------------------------------
-- Added by Santosh on 19-05-2015

  CREATE TABLE `ptask_files` (
  `file_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `performance_task_id` BIGINT(20) NOT NULL,
  `filename` varchar(1000) NOT NULL,
  `created_by` BIGINT(20) NOT NULL,
  PRIMARY KEY (`file_id`)
  ) ;

  ALTER TABLE `edulink1_lpriority`.`ptask_files` 
  ADD INDEX `fk_performance_taks_id_idx` (`performance_task_id` ASC);
  ALTER TABLE `edulink1_lpriority`.`ptask_files` 
  ADD CONSTRAINT `fk_performance_taks_id`
  FOREIGN KEY (`performance_task_id`)
  REFERENCES `edulink1_lpriority`.`questions` (`question_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
 
 -- -------------------------------------------------------------------------------------
 
  
  				
-- Added by Lalitha on 5/18/2015

ALTER TABLE `edulink1_lpriority`.`register_for_class` 
DROP FOREIGN KEY `fk_rfc_section_id`;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
CHANGE COLUMN `section_id` `section_id` BIGINT(20) NULL DEFAULT '0' ;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD CONSTRAINT `fk_rfc_section_id`
  FOREIGN KEY (`section_id`)
  REFERENCES `edulink1_lpriority`.`section` (`section_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD COLUMN `teacher_id` BIGINT(20) NULL DEFAULT NULL AFTER `grade_level_id`;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD INDEX `fk_rfc_teacher_id _idx` (`teacher_id` ASC);
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD CONSTRAINT `fk_rfc_teacher_id `
  FOREIGN KEY (`teacher_id`)
  REFERENCES `edulink1_lpriority`.`teacher` (`teacher_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- ----------------------------------------------------------------------------------------
-- Added by Santosh on 21-05-2015

  ALTER TABLE `edulink1_lpriority`.`rubric_values` CHANGE COLUMN `dimension1_value` `dimension1_value` BIGINT(20) NOT NULL  , 
  CHANGE COLUMN `dimension2_value` `dimension2_value` BIGINT(20) NOT NULL  , 
  CHANGE COLUMN `dimension3_value` `dimension3_value` BIGINT(20) NOT NULL  , 
  CHANGE COLUMN `dimension4_value` `dimension4_value` BIGINT(20) NOT NULL  ;

-- ----------------------------------------------------------------------------------------
-- Added by Christopher on 26-05-2015
  
ALTER TABLE `edulink1_lpriority`.`assignment` 
DROP FOREIGN KEY `fk_asngt_lesson_id`;
ALTER TABLE `edulink1_lpriority`.`assignment` 
CHANGE COLUMN `lesson_id` `lesson_id` BIGINT(20) NULL ,
CHANGE COLUMN `instructions` `instructions` VARCHAR(100) NULL DEFAULT '' ;
ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD CONSTRAINT `fk_asngt_lesson_id`
  FOREIGN KEY (`lesson_id`)
  REFERENCES `edulink1_lpriority`.`lesson` (`lesson_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  CREATE TABLE `k1_sets` (
  `k1_set_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set` varchar(200) NOT NULL,
  `set_type` varchar(20) NOT NULL,
  `master_grade_id` bigint(20) NOT NULL,
  `part_type` varchar(20) DEFAULT 'Null',
  `set_name` varchar(10) DEFAULT NULL,
  `created_date` date NOT NULL,
  PRIMARY KEY (`k1_set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `assign_k1_tests` (
  `assign_k1_test_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_id` bigint(20) NOT NULL DEFAULT '0',
  `marks_graded` varchar(60) DEFAULT NULL,
  `student_assignment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`assign_k1_test_id`),
  KEY `fk_set_id` (`set_id`),
  KEY `fk_student_assignment_id_idx` (`student_assignment_id`),
  CONSTRAINT `fk_k1_set_id` FOREIGN KEY (`set_id`) REFERENCES `k1_sets` (`k1_set_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_k1_student_test_id` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `edulink1_lpriority`.`assign_k1_tests` 
CHANGE COLUMN `marks_graded` `marks_graded` VARCHAR(300) NULL DEFAULT NULL ;

ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD COLUMN `test_type` VARCHAR(20) NULL DEFAULT NULL AFTER `performance_group_id`;	

-- ----------------------------------------------------------------------------------------
-- Added by Christopher on 28-05-2015

ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
CHANGE COLUMN `submitdate` `submitdate` DATE NULL DEFAULT NULL ;

insert into assignment_type(assignment_type_id, assignment_type, create_date, change_date, used_for) values(15, 'Early Literacy Letter', '2014-05-15', '2015-05-27 18:53:09', 'rti_early_literacy'),
(16, 'Early Literacy Word', '2014-05-15', '2015-05-27 18:53:09', 'rti_early_literacy');

-- ------------------------------------------------------------------------------------------
-- Added by Santosh on 28-05-2015

  CREATE TABLE `edulink1_lpriority`.`group_performance_temp` (
  `performance_group_students_id` BIGINT(20) NOT NULL,
  `assigned_task_id` BIGINT(20) NOT NULL,
  `writing_area_status` VARCHAR(45) NULL DEFAULT 'unlocked',
  `image_area_status` VARCHAR(45) NULL DEFAULT 'unlocked',
  `calculation_area_status` VARCHAR(45) NULL DEFAULT 'unlocked',
  `dim1_value` BIGINT(20) NULL,
  `dim2_value` BIGINT(20) NULL,
  `dim3_value` BIGINT(20) NULL,
  `dim4_value` BIGINT(20) NULL,
  `total` BIGINT(20) NULL,
  `chat_login_status` VARCHAR(45) NULL,
  INDEX `PRIMARY_KEY` (`performance_group_students_id` ASC, `assigned_task_id` ASC),
  INDEX `fk__idx` (`assigned_task_id` ASC),
  CONSTRAINT `fk_per_group_student_id`
    FOREIGN KEY (`performance_group_students_id`)
    REFERENCES `edulink1_lpriority`.`performance_group_students` (`performance_group_students_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assigned_ptask_id`
   FOREIGN KEY (`assigned_task_id`)
    REFERENCES `edulink1_lpriority`.`assigned_ptasks` (`assigned_task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);    

    drop table `edulink1_lpriority`.`studentgroupstatus`;
    drop table `edulink1_lpriority`.`groupassigned_tasks`;
-- -----------------------------------------------------------------------------------------------
-- Added by Santosh on 29-05-2015

    ALTER TABLE `edulink1_lpriority`.`assigned_ptasks` CHANGE COLUMN `writing` `writing` LONGTEXT NULL DEFAULT NULL  
    , CHANGE COLUMN `uploadarea` `uploadarea` LONGTEXT NULL DEFAULT NULL  ;

-- -----------------------------------------------------------------------------------------------    
-- Added by Santosh on 01-06-2015

    ALTER TABLE `edulink1_lpriority`.`group_performance_temp` ADD COLUMN `permission_status` VARCHAR(45) NULL  AFTER `chat_login_status` ;

-- ----------------------------------------------------------------------------------------------- 
 -- Added by Anusuya on 10-06-2015
    
  alter table assignment_questions add column b_retellmarks int(11);
  
  ---------------------------------------------------------------------------------------------------
  
  -- Added by Lalitha on 6/5/2015

    ALTER TABLE `edulink1_lpriority`.`school` ADD UNIQUE INDEX `school_name_UNIQUE` (`school_name` ASC);
    
-- ----------------------------------------------------------------------------------------------

-- Added by Lalitha on 6/8/2015

   ALTER TABLE `edulink1_lpriority`.`school_schedule`ADD UNIQUE INDEX `unique_school_id` (`school_id` ASC);
-- ----------------------------------------------------------------------------------------
-- Added by Christopher on 15-06-2015

INSERT INTO `k1_sets` VALUES (1,'M S F L R N H V W Z B C D G P T J K Y X Q I O A U E','Letter',13,'PART I','Upper Case','2015-06-01'),(2,'m s f l r n h v w z b c d g p t j k y x q i o a u e','Letter',13,'PART II','Lower Case','2015-06-01'),(3,'I see my like a to and go is here for have said the play she are he this from you that was with they of his as first me one look we find many','Word',13,'K1Words','Words','2015-06-01'),(4,'the of a to you was are they from have','Word',1,'PART I','A','2015-06-01'),(5,'one what were there your their sold do many some','Word',1,'PART I','B','2015-06-01'),(6,'would other into two could been who people only find','Word',1,'PART I','C','2015-06-01'),(7,'water very word where most through another come work does','Word',1,'PART I','D','2015-06-01'),(8,'put again old great should give something thought both oftern','Word',1,'PART I','E','2015-06-01'),(9,'world want different together school once enough sometimes four head','Word',1,'PART I','F','2015-06-01'),(10,'above kind almost earth mother country father eyes today sure','Word',1,'PART II','G','2015-06-01'),(11,'told young heard answer against learn toward money move done','Word',1,'PART II','H','2015-06-01'),(12,'group true half cold course front early brought though become','Word',1,'PART II','I','2015-06-01'),(13,'behind ready built hold piece talk blue instead either friend','Word',1,'PART II','J','2015-06-01'),(14,'already warm mind says heavy beautiful everyone watch hour carry','Word',1,'PART II','K','2015-06-01'),(15,'although heart wild weather someone won field gold build walk','Word',1,'PART II','L','2015-06-01');

-- ----------------------------------------------------------------------------------------------
-- Added Santosh on 23-06-2015

	ALTER TABLE `edulink1_lpriority`.`report` ADD COLUMN `performance_perc` FLOAT NOT NULL  AFTER `assignment_perc` ;

-- ------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 6/20/2015
	
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	ADD UNIQUE INDEX `unique_key` (`title` ASC, `cs_id` ASC, `used_for` ASC);

-- Added by Lalitha on 6/22/2015

	ALTER TABLE `edulink1_lpriority`.`user_registration` 
	ADD UNIQUE INDEX `unique_email_id` (`email_id` ASC);


-- ----------------------------------------------------------------------------------------------------------
  
-- Added by Lalitha on 6/25/2015

   	update assignment_type set used_for = 'classworks' where used_for = null;
   	
	update gradeevents set event_name = 'assessments' where event_id = 2;
	update gradeevents set event_name = 'homeworks' where event_id = 1;
	update gradeevents set event_name = 'classworks' where event_id = 3;
-- ----------------------------------------------------------------------------------------------------------
-- Added by Santosh on 30-06-2015

   ALTER TABLE `edulink1_lpriority`.`benchmark_results` CHANGE COLUMN `median_fulency_core` `median_fluency_score` INT(11) NULL DEFAULT '0'  , 
   CHANGE COLUMN `sentence_structure_core` `sentence_structure_score` FLOAT(10,2) NULL DEFAULT NULL  ;
   
--  -----------------------------------------------------------------------------------------------------------
-- Added by Christopher on 06 July 2015
 
CREATE TABLE `phonic_sections` (
  `phonic_section_id` bigint(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`phonic_section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `phonic_sections` VALUES (1,'Consonant sounds and names'),(2,'Short vowel sounds'),(3,'Reading words with phonics patterns');

CREATE TABLE `phonic_groups` (
  `id` bigint(10) NOT NULL,
  `phonic_section_id` bigint(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_phonic_section_id_idx` (`phonic_section_id`),
  CONSTRAINT `fk_phonic_section_id` FOREIGN KEY (`phonic_section_id`) REFERENCES `phonic_sections` (`phonic_section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `phonic_groups` VALUES (1,1,'Continuous Sounds','m s f l r n h v w z'),(2,1,'Stop Sounds','b d g p t j k y x q'),(3,2,'Short Vowels','i o a u e'),(4,3,'Short','van mop fell sun fix'),(5,3,'cd','chin bath when shut song'),(6,3,'c blend','left must frog flip snack'),(7,3,'final e','fine hope cute kite rake'),(8,3,'lvd','soap leak pain feed ray'),(9,3,'r-c','burn fork dirt part serve'),(10,3,'ovd','coin soon round lawn foot'),(11,3,'inflec','rested stayed passes making ripped'),(12,3,'affixes','distrust useful unfair hardship nonsense'),(13,3,'2-syl','silent ladder napkin polite cactus'),(14,3,'3+syl','volcano potato electric frequently combination'),(15,3,'4+syl','unflavored intelligent organization convertible representative');



CREATE TABLE `student_phonic_test_marks` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` bigint(20) NOT NULL,
  `group_id` bigint(10) NOT NULL,
  `max_marks` int(11) DEFAULT '0',
  `sec_marks` int(11) DEFAULT '0',
  `marks_string` varchar(50) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_test_id2` (`student_assignment_id`),
  KEY `fk_phonic_group_id1` (`group_id`),
  CONSTRAINT `fk_phonic_group_id1` FOREIGN KEY (`group_id`) REFERENCES `phonic_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_phonic_student_assignment_id` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `edulink1_lpriority`.`assignment_type` (`assignment_type_id`, `assignment_type`, `create_date`, `change_date`, `used_for`) VALUES (17, 'Phonic Skill Test', '2015-07-06', '2015-07-06 18:53:09', 'Phonic_Skill_Test');
   
-- ----------------------------------------------------------------------------------------------------------  
-- Added by Santosh on 07 July 2015	
	
  ALTER TABLE `edulink1_lpriority`.`activity` DROP FOREIGN KEY `fk_created_by2` ;

  ALTER TABLE `edulink1_lpriority`.`activity` 
  ADD CONSTRAINT `fk_create_by`
  FOREIGN KEY (`created_by` )
  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
-- ----------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------------------------
-- Added by Santosh on 14th July 2015	

CREATE  TABLE `edulink1_lpriority`.`parent_lastseen` (
  `parent_lastseen_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `parent_id` BIGINT NOT NULL ,
  `last_logged_in` DATETIME NULL ,
  `last_logged_out` DATETIME NULL ,
  `last_seen_feature` VARCHAR(100) NULL DEFAULT '---' ,
  PRIMARY KEY (`parent_lastseen_id`) ,
  INDEX `fk_parent_last_id` (`parent_id` ASC) ,
  CONSTRAINT `fk_parent_last_id`
    FOREIGN KEY (`parent_id` )
    REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- ----------------------------------------------------------------------------------------------------

-- Added by Lalitha on 7/16/2015

ALTER TABLE `edulink1_lpriority`.`register_for_class` 
CHANGE COLUMN `grade_level_id` `grade_level_id` BIGINT(20) NULL ;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD CONSTRAINT `fk_rfc_grade_level_id`
  FOREIGN KEY (`grade_level_id`)
  REFERENCES `edulink1_lpriority`.`grade_level` (`grade_level_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- -----------------------------------------------------------------------------------------------------
-- Added by Christopher on 16 July 2015
  
  ALTER TABLE `edulink1_lpriority`.`student_phonic_test_marks` 
CHANGE COLUMN `comments` `comments` VARCHAR(500) NULL DEFAULT NULL ;

-- ------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 7/17/2015

DROP TABLE If exists `edulink1_lpriority`.`student_composite_chart_values`;
DROP TABLE If exists `edulink1_lpriority`.`student_composite_activity_score`;
DROP TABLE If exists `edulink1_lpriority`.`student_composite_lesson_score`;
DROP TABLE If exists `edulink1_lpriority`.`student_composite_project_score`;

CREATE TABLE `student_composite_activity_score` (
  `student_id` bigint(20) NOT NULL,
  `assign_activity_id` bigint(20) NOT NULL,
  `score` int(11) DEFAULT '0',
  `academic_grade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`student_id`,`assign_activity_id`),
  KEY `fk_student3_id_idx` (`student_id`),
  KEY `fk_scc_activity4_id_idx` (`assign_activity_id`),
  KEY `fk_sccvv_academic_grade_id_idx` (`academic_grade_id`),
  CONSTRAINT `fk_sccsvv_academic_grade_id` FOREIGN KEY (`academic_grade_id`) REFERENCES `academic_grades` (`acedamic_grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sccvv_academic_grade_id` FOREIGN KEY (`academic_grade_id`) REFERENCES `academic_grades` (`acedamic_grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sccvv_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sccv_assign_activity_id` FOREIGN KEY (`assign_activity_id`) REFERENCES `assign_activity` (`assign_activity_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `student_composite_lesson_score` (
  `student_id` bigint(20) NOT NULL,
  `assign_lesson_id` bigint(20) NOT NULL,
  `score` int(11) DEFAULT '0',
  `academic_grade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`student_id`,`assign_lesson_id`),
  KEY `fk_student_id_idx` (`student_id`),
  KEY `fk_scc_lesson_id_idx` (`assign_lesson_id`),
  KEY `fk_sccl_academic_grade_id_idx` (`academic_grade_id`),
  CONSTRAINT `fk_sccv_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_scc_assign_lesson_id` FOREIGN KEY (`assign_lesson_id`) REFERENCES `assign_lessons` (`assign_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `student_composite_project_score` (
  `cs_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) NOT NULL,
  `score` int(11) DEFAULT '0',
  `academic_grade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`cs_id`,`student_id`),
  KEY `fk_p_cs_id_idx` (`cs_id`),
  KEY `fk_p_student_id_idx` (`student_id`),
  KEY `fk_sccsl_academic_grade_id_idx` (`academic_grade_id`),
  CONSTRAINT `fk_p_sccv_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_p_sccv_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sccasvv_academic_grade_id` FOREIGN KEY (`academic_grade_id`) REFERENCES `academic_grades` (`acedamic_grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

ALTER TABLE `edulink1_lpriority`.`school` 
DROP INDEX `sch_abbr_uni` ,
DROP INDEX `school_name_UNIQUE` ;

-- Added by Lalitha on 7/20/2015

ALTER TABLE `edulink1_lpriority`.`school` 
CHANGE COLUMN `logo_link` `logo_link` VARCHAR(60) NULL DEFAULT NULL ;

-- Added by Lalitha on 7/21/2015

CREATE TABLE `bm_categories` (
  `bm_category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bm_category` varchar(30) NOT NULL DEFAULT '',
  `bm_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`bm_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO bm_categories(bm_category_id, bm_category, bm_name) VALUES
(1, 'beginning', 'benchmark1'),
(2, 'middle', 'benchmark2'),
(3, 'end', 'benchmark3');

ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
DROP FOREIGN KEY `fk_jsb_cs_id`;
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
ADD INDEX `fk_jsb_cs_id_idx` (`cs_id` ASC),
DROP INDEX `fk_section_id_idx` ;
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
ADD CONSTRAINT `fk_jsb_cs_id`
  FOREIGN KEY (`cs_id`)
  REFERENCES `edulink1_lpriority`.`class_status` (`cs_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
CHANGE COLUMN `benchmark_id` `benchmark_id` BIGINT(20) NOT NULL ;


ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
DROP FOREIGN KEY `fk_sentencestructure_assignment_id`;
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
CHANGE COLUMN `sentencestructure_assignment_id` `sentencestructure_assignment_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
ADD CONSTRAINT `fk_sentencestructure_assignment_id`
  FOREIGN KEY (`sentencestructure_assignment_id`)
  REFERENCES `edulink1_lpriority`.`assignment` (`assignment_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- -----------------------------------------------------------------------------------------------------
-- Added by Christopher on 23 July 2015

ALTER TABLE `edulink1_lpriority`.`student_phonic_test_marks` 
DROP FOREIGN KEY `fk_phonic_group_id1`;

ALTER TABLE `edulink1_lpriority`.`phonic_groups` 
CHANGE COLUMN `id` `group_id` BIGINT(10) NOT NULL ;

ALTER TABLE `edulink1_lpriority`.`student_phonic_test_marks` 
ADD CONSTRAINT `fk_phonic_group_id`
  FOREIGN KEY (`group_id`)
  REFERENCES `edulink1_lpriority`.`phonic_groups` (`group_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- --------------------------------------------------------------------------------------------------------
  
-- Added by Lalitha on 7/27/2015

ALTER TABLE `edulink1_lpriority`.`register_for_class` 
DROP FOREIGN KEY `fk_rfc_section_id`;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
CHANGE COLUMN `section_id` `section_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD CONSTRAINT `fk_rfc_section_id`
  FOREIGN KEY (`section_id`)
  REFERENCES `edulink1_lpriority`.`section` (`section_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- -----------------------------------------------------------------------------------------------------

-- Added by Lalitha on 8/14/2015	

ALTER TABLE `edulink1_lpriority`.`questions` CHANGE COLUMN `question` `question` LONGTEXT NULL DEFAULT NULL;

-- ------------------------------------------------------------------------------------------------------
-- Added by Santosh on 20/08/2015

UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='classworks' WHERE `assignment_type_id`='9';
UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='classworks' WHERE `assignment_type_id`='10';
UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='classworks' WHERE `assignment_type_id`='11';
UPDATE `edulink1_lpriority`.`assignment_type` SET `used_for`='classworks' WHERE `assignment_type_id`='12';

-- ------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 8/22/2015	

ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
DROP FOREIGN KEY `fk_benchmark_assignment_id`;
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
CHANGE COLUMN `benchmark_assignment_id` `benchmark_assignment_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`joinsentencenbenchmark` 
ADD CONSTRAINT `fk_benchmark_assignment_id`
  FOREIGN KEY (`benchmark_assignment_id`)
  REFERENCES `edulink1_lpriority`.`assignment` (`assignment_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
-- --------------------------------------------------------------------------------------------------------
-- Sprint 9 Modifications
-- Added by Christopher on 10/14/2015

	ALTER TABLE `edulink1_lpriority`.`assignment` 
	ADD COLUMN `record_time` INT NULL DEFAULT 0 AFTER `test_type`;
-- --------------------------------------------------------------------------------------------------------
-- Added by Santosh on 10/16/2015

	ALTER TABLE `edulink1_lpriority`.`student` ADD COLUMN `grade_status` VARCHAR(45) NOT NULL DEFAULT 'active'  AFTER `homeroom_id` ;
-- --------------------------------------------------------------------------------------------------------
-- Added by Anusuya on 10/20/2015

 CREATE TABLE IF NOT EXISTS `legend` (
  `legend_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `legend_name` varchar(40) NOT NULL,
  `legend_value` bigint(20) NOT NULL,
  PRIMARY KEY (`legend_id`),
  UNIQUE KEY `unique_legend_name` (`legend_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- Dumping data for table `legend`
INSERT INTO `legend` (`legend_id`, `legend_name`, `legend_value`) VALUES
(1, 'Outstanding', 4),
(2, 'Proficient', 3),
(3, 'Approaching Proficient', 2),
(4, 'Not Proficient', 1);

-- Table structure for table `legend_criteria`
CREATE TABLE IF NOT EXISTS `legend_criteria` (
  `legend_criteria_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `legend_criteria_name` varchar(20) NOT NULL,
  PRIMARY KEY (`legend_criteria_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

-- Dumping data for table `legend_criteria`

INSERT INTO `legend_criteria` (`legend_criteria_id`, `legend_criteria_name`) VALUES
(1, 'Communication'),
(2, 'Collaboration'),
(3, 'Critical Thinking'),
(4, 'Creativity'),
(5, 'Caring'),
(6, 'Literacies');

-- Table structure for table `legend_sub_criteria`

CREATE TABLE IF NOT EXISTS `legend_sub_criteria` (
  `legend_sub_criteria_id` bigint(20) NOT NULL,
  `legend_criteria_id` bigint(20) NOT NULL,
  `legend_sub_criteria_name` varchar(100) NOT NULL,
  PRIMARY KEY (`legend_sub_criteria_id`),
  UNIQUE KEY `legend_sub_criteria_name_UNIQUE` (`legend_sub_criteria_name`,`legend_criteria_id`),
  KEY `fk_rptcategory_id_idx` (`legend_criteria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table `legend_sub_criteria`

INSERT INTO `legend_sub_criteria` (`legend_sub_criteria_id`, `legend_criteria_id`, `legend_sub_criteria_name`) VALUES
(29, 5, 'Citizenship'),
(30, 5, 'Commitment'),
(4, 1, 'Communicating in diverse environments'),
(27, 5, 'Compassion'),
(18, 3, 'Constructing arguments'),
(8, 2, 'Cooperation'),
(24, 4, 'Creative production & innovation'),
(5, 1, 'Delivering oral presentations'),
(26, 5, 'Empathy'),
(1, 1, 'Engaging in conversations and discussions'),
(9, 2, 'Flexibility'),
(36, 6, 'History/Social Studies'),
(21, 4, 'Idea Design & Refinement'),
(20, 4, 'Idea generation'),
(14, 3, 'Information & Discovery'),
(15, 3, 'Interpretation  & Analysis'),
(7, 2, 'Leadership & Initiative'),
(3, 1, 'Listening'),
(33, 6, 'Mathematics'),
(22, 4, 'Openness & courage to explore'),
(17, 3, 'Problem solving/Solution Finding'),
(32, 6, 'Reading'),
(16, 3, 'Reasoning'),
(10, 2, 'Responsibility & productivity'),
(12, 2, 'Responsiveness'),
(34, 6, 'Science'),
(6, 1, 'Self Regulation/ Reflection'),
(13, 2, 'Self Regulation/ Reflection'),
(19, 3, 'Self Regulation/ Reflection'),
(25, 4, 'Self Regulation/ Reflection'),
(28, 5, 'Service'),
(35, 6, 'Technology'),
(11, 2, 'Use of tech tools for synchronous /Asynchronous Collaboration'),
(2, 1, 'Using 21st century communication tools'),
(23, 4, 'Works creatively with others'),
(31, 6, 'Writing');

-- Table structure for table `learning_indicator`
CREATE TABLE IF NOT EXISTS `learning_indicator` (
  `learning_indicator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `report_date` date NOT NULL,
  `create_date` date NOT NULL,
  PRIMARY KEY (`learning_indicator_id`),
  KEY `fk_rpt_cs_id_idx` (`cs_id`),
  KEY `fk_rpt_student_id_idx` (`student_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- Table structure for table `learning_indicator_values`
CREATE TABLE IF NOT EXISTS `learning_indicator_values` (
  `learning_values_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `learning_indicator_id` bigint(20) NOT NULL,
  `sub_criteria_id` bigint(20) NOT NULL,
  `legend_id` bigint(20) NOT NULL,
  `notes` varchar(100) NOT NULL,
  PRIMARY KEY (`learning_values_id`),
  KEY `fk_rpt_legend_id_idx` (`legend_id`),
  KEY `fk_rpt_sub_criteria_id_idx` (`sub_criteria_id`),
  KEY `fk_li_learning_indicator_id_idx` (`learning_indicator_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=144 ;

  
  ALTER TABLE `learning_indicator`
  ADD CONSTRAINT `fk_l_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_l_student_id` FOREIGN KEY (`student_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

 
  ALTER TABLE `learning_indicator_values`
  ADD CONSTRAINT `fk_learning_indicator_id` FOREIGN KEY (`learning_indicator_id`) REFERENCES `learning_indicator` (`learning_indicator_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_legend_id` FOREIGN KEY (`legend_id`) REFERENCES `legend` (`legend_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_sub_criteria_id` FOREIGN KEY (`sub_criteria_id`) REFERENCES `legend_sub_criteria` (`legend_sub_criteria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
  
  
  
  ALTER TABLE `legend_sub_criteria`
  ADD CONSTRAINT `fk_legend_criteria` FOREIGN KEY (`legend_criteria_id`) REFERENCES `legend_criteria` (`legend_criteria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
  
  
  ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
  DROP FOREIGN KEY `fk_l_student_id`;


 ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
 ADD CONSTRAINT `fk_l_student_id`
 FOREIGN KEY (`student_id`)
 REFERENCES `edulink1_lpriority`.`student` (`student_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;
 
 -- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

 -- Added by Christopher on 21/20/2015

	ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
	ADD COLUMN `last_saved_set` BIGINT(20) NULL AFTER `academic_grade_id`,
	ADD INDEX `fk_phonic_set_id_idx` (`last_saved_set` ASC);
	ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
	ADD CONSTRAINT `fk_phonic_set_id`
	  FOREIGN KEY (`last_saved_set`)
	  REFERENCES `edulink1_lpriority`.`phonic_groups` (`group_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

-- -------------------------------------------------------------------------------------------------------------

-- Added by Anusuya on 26-10-2015

	  ALTER TABLE `edulink1_lpriority`.`learning_indicator_values` 
      CHANGE COLUMN `notes` `notes` VARCHAR(1000) NOT NULL ;

      
-- Added by Lalitha on 10/27/2015	
INSERT INTO `phonic_groups` (`group_id`, `phonic_section_id`, `title`, `question`) VALUES  (16,2,'Long Vowels','i o a u e'); 

-- Added by Santosh on 28/10/2015	
ALTER TABLE `edulink1_lpriority`.`questions` CHANGE COLUMN `pt_subject_area` `pt_subject_area` LONGTEXT NULL DEFAULT NULL  , CHANGE COLUMN `pt_directions` `pt_directions` LONGTEXT NULL DEFAULT NULL  ;

-- Added by Lalitha on 11/5/2015	

ALTER TABLE `edulink1_lpriority`.`performancetask_groups` 
CHANGE COLUMN `group_name` `group_name` VARCHAR(50) NULL DEFAULT NULL ;

ALTER TABLE `edulink1_lpriority`.`user_registration` 
DROP INDEX `unique_email_id` ,
ADD UNIQUE INDEX `unique_email_id` (`email_id` ASC, `school_id` ASC);

-- ---------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 05/01/2016

ALTER TABLE `edulink1_lpriority`.`sub_questions` 
DROP INDEX `sub_question_UNIQUE` ;

ALTER TABLE `edulink1_lpriority`.`sub_questions` 
ADD COLUMN `assignment_type_id` BIGINT(20) NOT NULL AFTER `no_of_options`,
ADD COLUMN `lesson_id` BIGINT(20) NOT NULL AFTER `assignment_type_id`,
ADD COLUMN `used_for` VARCHAR(45) NOT NULL AFTER `lesson_id`,
ADD COLUMN `created_by` BIGINT(20) NOT NULL AFTER `used_for`;
-- -------------------------------------------------------------------------------------------------------------------------


-- Added by Lalitha on 01/05/2015

Drop table joinsentencenbenchmark;

ALTER TABLE `edulink1_lpriority`.`benchmark_results` 
DROP COLUMN `sentence_structure_score`;

-- -------------------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 20/02/2016

INSERT INTO `edulink1_lpriority`.`assignment_type` (`assignment_type_id`, `assignment_type`, `create_date`, `change_date`, `used_for`) VALUES (18, 'Reading Fluency Learning Practice', '2016-01-25', '2016-02-08 16:29:23', 'RFLP');

CREATE TABLE `rflp_rubric` (
  `rflp_rubric_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rflp_rubric_value` bigint(20) NOT NULL,
  `rflp_desc` longtext,
   PRIMARY KEY (`rflp_rubric_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
 INSERT INTO `rflp_rubric` (`rflp_rubric_value`, `rflp_desc`) VALUES (3,'Sentence demonstrates knowledge of the word and excellent grammar and expression.'),(2,'Sentence demonstrates knowledge of the word and good grammar and expression.'),(1,'Sentence demonstrates knowledge of the word with poor grammar and/or expression.'),(0,'Sentence does not demonstrate knowledge of the word.');

 
CREATE TABLE `rflp_test` (
  `rflp_test_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` bigint(20) DEFAULT NULL,
  `total_avg_score` decimal(2,1) DEFAULT NULL,
  `teacher_comment` varchar(200) DEFAULT NULL,
  `date_due` date NOT NULL,
  PRIMARY KEY (`rflp_test_id`),
  KEY `fk_student_assignment_id_idxx` (`student_assignment_id`),
  CONSTRAINT `fk_aq_student_assignment_id_idx` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


CREATE TABLE `rflp_practice` (
  `rflp_practice_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint(20) NOT NULL,
  `word_num` bigint(20) NOT NULL,
  `error_word` varchar(100) NOT NULL,
  `student_sentence` longtext,
  `written_rubric_score` bigint(20) DEFAULT NULL,
  `oral_rubric_score` bigint(20) DEFAULT NULL,
  `rflp_test_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rflp_practice_id`),
  KEY `lesson_id_rflp` (`lesson_id`),
  KEY `rflp_test_id_idx` (`rflp_test_id`),
  CONSTRAINT `rflp_test_id` FOREIGN KEY (`rflp_test_id`) REFERENCES `rflp_test` (`rflp_test_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lesson_id_rflp` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;


-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 2/4/2016

update `bm_categories` set bm_name='Fluency Reading1' where bm_category_id=1;

update `bm_categories` set bm_name='Fluency Reading2' where bm_category_id=2;

update `bm_categories` set bm_name='Fluency Reading3' where bm_category_id=3;

update assignment_type set assignment_type='Fluency Reading' where assignment_type_id=8;


ALTER TABLE `edulink1_lpriority`.`questions` 
CHANGE COLUMN `option1` `option1` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `option2` `option2` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `option3` `option3` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `option4` `option4` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `option5` `option5` VARCHAR(100) NULL DEFAULT NULL ;

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Added by Lalitha on 3/9/2016

alter table learning_indicator add column status varchar(10);

alter table learning_indicator_values add teacher_comment VARCHAR(500);

ALTER TABLE `learning_indicator_values` 
ADD COLUMN `teacher_score` BIGINT(20) NULL AFTER
`notes`, ADD INDEX `fk_teacher_score_idx`
(`teacher_score` ASC);
ALTER TABLE `edulink1_lpriority`.`learning_indicator_values` 
ADD CONSTRAINT `fk_teacher_score` FOREIGN KEY (`teacher_score`)
  REFERENCES `edulink1_lpriority`.`legend` (`legend_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `edulink1_lpriority`.`legend` DROP INDEX `unique_legend_name` ;

update legend set legend_name='Not Proficient', legend_value=1 where legend_id=1;
update legend set legend_name='Approaching Proficient', legend_value=2 where legend_id=2;
update legend set legend_name='Proficient', legend_value=3 where legend_id=3;
update legend set legend_name='Outstanding', legend_value=4 where legend_id=4;

ALTER TABLE `edulink1_lpriority`.`legend` 
ADD UNIQUE INDEX `unique_legend_name` (`legend_name` ASC);

-- ---------------------------------------------------------------------------------------------
alter table legend add column color varchar(20);
update legend set color='#ff0000' where legend_id=1;
update legend set color='#ffff00' where legend_id=2;
update legend set color='#0FC30C' where legend_id=3;
update legend set color='#f1c232' where legend_id=4;	
-- ----------------- Added by Anusuya on 18/3/2016------------------------------------------------


-- Added by Lalitha on 4/4/2016

 ALTER TABLE `edulink1_lpriority`.`learning_indicator_values` 
DROP FOREIGN KEY `fk_legend_id`;
ALTER TABLE `edulink1_lpriority`.`learning_indicator_values` 
CHANGE COLUMN `legend_id` `legend_id` BIGINT(20) NULL ,
CHANGE COLUMN `notes` `notes` VARCHAR(1000) NULL ;
ALTER TABLE `edulink1_lpriority`.`learning_indicator_values` 
ADD CONSTRAINT `fk_legend_id`
  FOREIGN KEY (`legend_id`)
  REFERENCES `edulink1_lpriority`.`legend` (`legend_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  CREATE TABLE `bm_ss_cut_off_marks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_id` bigint(20) NOT NULL,
  `bm_category_id` bigint(20) NOT NULL,
  `fluency_cut_off` int(11) NOT NULL,
  `retell_cut_off` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`grade_id`,`bm_category_id`),
  KEY `fk_grade_id_idx` (`grade_id`),
  KEY `fk_bm_benchamrk_id_idx` (`bm_category_id`),
  CONSTRAINT `fk_bm_category_id` FOREIGN KEY (`bm_category_id`) REFERENCES `bm_categories` (`bm_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_bm_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- -----------------------------------------------------------------------------------------------------

-- Added by Lalitha on 4/15/2016

Insert into assignment_type VALUES ('19', 'Comprehension', '2016-04-07', '2016-04-07 15:51:23', 'rti');

-- Added by Lalitha on 4/18/2016

ALTER TABLE `edulink1_lpriority`.`sub_questions` 
CHANGE COLUMN `sub_question` `sub_question` LONGTEXT NOT NULL ;

-- Added by Lalitha on 5/10/2016

-- Updated Early Literacy sets for 1 Grade students

delete from edulink1_lpriority.assign_k1_tests;

delete FROM edulink1_lpriority.student_assignment_status where 
assignment_id in (SELECT assignment_id FROM edulink1_lpriority.assignment where assignment_type_id in (15,16));

Delete FROM edulink1_lpriority.assignment where assignment_type_id in (15,16);

Delete FROM edulink1_lpriority.k1_sets where k1_set_id not in (1,2,3);

INSERT INTO `k1_sets`(`set`,
`set_type`,`master_grade_id`,`set_name`, `created_date`)
VALUES('see the is up have I a there can an', 'Word', '1', 'List 1', '2016-05-10'), 
('am and did in it had him said has at', 'Word', '1', 'List 2', '2016-05-10'), 
('call look was what big got to ask of as', 'Word', '1', 'List 3', '2016-05-10'), 
('he his just down if its red help then six', 'Word', '1', 'List 4', '2016-05-10'), 
('get that this for out jump little went will when', 'Word', '1', 'List 5', '2016-05-10'), 
('girl her with an they but do she write circle', 'Word', '1', 'List 6', '2016-05-10'), 
('yes all were ride walk we well word letter sound', 'Word', '1', 'List 7', '2016-05-10'), 
('make you go like after over them sentence same different', 'Word', '1', 'List 8', '2016-05-10'),
('or two be green take does many other people number', 'Word', '1', 'List 9', '2016-05-10'), 
('every could boy some going first tell next last finally', 'Word', '1', 'List 10', '2016-05-10'), 
('here day way sleep don’t greater less more than between', 'Word', '1', 'List 11', '2016-05-10'), 
('my too came me right wrong correct incorrect answer', 'Word', '1', 'List 12', '2016-05-10'), 
('no their away saw blue question direction why because which', 'Word', '1', 'List 13', '2016-05-10'), 
('one very good now thing something everything anything together nothing', 'Word', '1', 'List 14', '2016-05-10'),  
('are brown how about around long friend school teacher children', 'Word', '1', 'List 15', '2016-05-10'), 
('know want by into before yellow who picture follow problem', 'Word', '1', 'List 16', '2016-05-10'), 
('old any from water come under underline belong been above', 'Word', '1', 'List 17', '2016-05-10'), 
('five would pretty your think even odd find again both', 'Word', '1', 'List 18', '2016-05-10'), 
('four put where complete set line shape pattern large square', 'Word', '1', 'List 19', '2016-05-10'),
('enough laugh maybe without surprise awesome exciting favorite through second', 'Word', '1', 'List 20', '2016-05-10'), 
('high every near add food between own below country plant', 'Word', '1', 'List 21', '2016-05-10'), 
('last school father keep tree never start city earth eye', 'Word', '1', 'List 22', '2016-05-10'), 
('light thought head under story saw left don’t few while', 'Word', '1', 'List 23', '2016-05-10'), 
('along might close something seem next hard open example begin', 'Word', '1', 'List 24', '2016-05-10'), 
('life always those both paper together got group often run', 'Word', '1', 'List 25', '2016-05-10'), 
('important until children side feet car mile night walk white', 'Word', '1', 'List 26', '2016-05-10'), 
('sea began grow took river four carry state once book', 'Word', '1', 'List 27', '2016-05-10'), 
('hear stop without second later miss idea enough eat facet', 'Word', '1', 'List 28', '2016-05-10'), 
('watch far Indian really almost let above girl sometimes mountain', 'Word', '1', 'List 29', '2016-05-10'),
('cut young talk soon list song being leave family it’s', 'Word', '1', 'List 30', '2016-05-10'), 
('body music color stand sun question fish area mark dog', 'Word', '1', 'List 31', '2016-05-10'), 
('horse birds problem complete room knew since ever piece told', 'Word', '1', 'List 32', '2016-05-10'), 
('usually didn’t friends easy heard order red door sure become', 'Word', '1', 'List 33', '2016-05-10'), 
('top ship across today during short better best however low', 'Word', '1', 'List 34', '2016-05-10'), 
('hours black products happened whole measure remember early waves reached', 'Word', '1', 'List 35', '2016-05-10'), 
('listen wind rock space covered fast several hold himself toward', 'Word', '1', 'List 36', '2016-05-10'), 
('five step morning passed vowel true hundred against pattern numeral', 'Word', '1', 'List 37', '2016-05-10'), 
('table north slowly money map busy pulled draw voice seen', 'Word', '1', 'List 38', '2016-05-10'), 
('cold cried plan notice south sing war ground fall king', 'Word', '1', 'List 39', '2016-05-10'), 
('town I’ll unit figure certain field travel wood fire upon', 'Word', '1', 'List 40', '2016-05-10');


-- Added by Lalitha on 5/4/2016

ALTER TABLE `edulink1_lpriority`.`assignment` 
DROP INDEX `unique_key` ,
ADD UNIQUE INDEX `unique_key` (`title` ASC, `cs_id` ASC, `used_for` ASC, `assign_status` ASC);

-- Added by Lalitha on 5/9/2016

ALTER TABLE `edulink1_lpriority`.`assignment_questions` 
CHANGE COLUMN `answer` `answer` LONGTEXT NULL DEFAULT NULL ;



-- ----------------- Sprint 12 DB changes ----------------------------------------------

CREATE TABLE `grading_types` (
  `grading_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grading_type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`grading_type_id`),
  UNIQUE KEY `grading_type_UNIQUE` (`grading_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT into grading_types(grading_type_id, grading_type) VALUES
('1', 'Teacher Grading'),
('2', 'Self Grading'),
('3', 'Peer Grading');



CREATE TABLE `reading_types` (
  `reading_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reading_type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`reading_type_id`),
  UNIQUE KEY `reading_type_UNIQUE` (`reading_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT into reading_types(reading_type_id, reading_type) VALUES
('1', 'Accuracy'),
('2', 'Fluency'),
('3', 'Retell');

CREATE TABLE `fluency_marks` (
  `fluency_marks_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignment_questions_id` bigint(20) NOT NULL,
  `grading_type_id` bigint(20) NOT NULL DEFAULT '1',
  `reading_type_id` bigint(20) NOT NULL DEFAULT '1',
  `words_read` int(20) DEFAULT NULL,
  `count_of_errors` int(20) DEFAULT NULL,
  `marks` int(20) DEFAULT NULL,
  `quality_of_response_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fluency_marks_id`),
  KEY `fk_assignment_questions_id_idx` (`assignment_questions_id`),
  KEY `fk_grading_type_idx` (`grading_type_id`),
  KEY `fk_reading_type_idx` (`reading_type_id`),
  KEY `fk_qor_id_idx` (`quality_of_response_id`),
  CONSTRAINT `fk_qor_id` FOREIGN KEY (`quality_of_response_id`) REFERENCES `quality_of_response` (`qor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assignment_questions_id` FOREIGN KEY (`assignment_questions_id`) REFERENCES `assignment_questions` (`assignment_questions_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grading_type` FOREIGN KEY (`grading_type_id`) REFERENCES `grading_types` (`grading_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reading_type` FOREIGN KEY (`reading_type_id`) REFERENCES `reading_types` (`reading_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12912 DEFAULT CHARSET=latin1;

CREATE TABLE `fluency_marks_details` (
  `fluency_marks_details_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fluency_marks_id` bigint(20) DEFAULT NULL,
  `errors_address` longtext,
  `comments` longtext,
  PRIMARY KEY (`fluency_marks_details_id`),
  KEY `fk_fluency_marks_id_idx` (`fluency_marks_id`),
  CONSTRAINT `fk_fluency_marks_id` FOREIGN KEY (`fluency_marks_id`) REFERENCES `fluency_marks` (`fluency_marks_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `edulink1_lpriority`.`assignment_questions` 
DROP FOREIGN KEY `fk_aq_quality_of_response_id`;
ALTER TABLE `edulink1_lpriority`.`assignment_questions` 
DROP COLUMN `b_retellmarks`,
DROP COLUMN `words_read`,
DROP COLUMN `errors_address`,
DROP COLUMN `b_count_of_errors`,
DROP COLUMN `b_quality_of_response_id`,
DROP COLUMN `b_fluencymarks`,
DROP INDEX `fk_quality_of_response_id_idx` ;


-- -----------------added by Anusuya 20-5-2016-------------------------------------------------------

alter table benchmark_results add median_accuracy_score int(11) default 0 after median_fluency_score;

-- --------------------------------------------------------------------------------------------------

alter table fluency_marks_details add column error_word varchar(50) after errors_address;

-- Added by Lalitha on 6/2/2016

ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
ADD COLUMN `peer_review_by` BIGINT(20) NULL AFTER `quality_of_response_id`,
ADD INDEX `fk_peer_review_id_idx` (`peer_review_by` ASC);
ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
ADD CONSTRAINT `fk_peer_review_id`
  FOREIGN KEY (`peer_review_by`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  -- Added by Christopher on 14/6/2016 ----------------------------------------------------------------
  
ALTER TABLE `edulink1_lpriority`.`rflp_practice` 
DROP FOREIGN KEY `lesson_id_rflp`;
ALTER TABLE `edulink1_lpriority`.`rflp_practice` 
DROP COLUMN `lesson_id`,
DROP INDEX `lesson_id_rflp` ;

-- Added by Anusuya on 16/6/2016 ----------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`rflp_test` 
ADD COLUMN `grading_types_id` BIGINT(20) NULL AFTER `date_due`,
ADD INDEX `fk_gradingTypesId_idx` (`grading_types_id` ASC);
ALTER TABLE `edulink1_lpriority`.`rflp_test` 
ADD CONSTRAINT `fk_gradingTypesId`
  FOREIGN KEY (`grading_types_id`)
  REFERENCES `edulink1_lpriority`.`grading_types` (`grading_type_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Added by Lalitha on 6/20/2016

  update edulink1_lpriority.assignment_type set assignment_type='Spelling' where assignment_type='Jac Template';
 
  ALTER TABLE `edulink1_lpriority`.`questions` 
	ADD COLUMN `num_of_options` INT(12) NULL AFTER `answer`;

-- Added by Anusuya 23-06-2016----------------------------------------------------------------------------------------------------
ALTER TABLE `edulink1_lpriority`.`rflp_test` 
ADD COLUMN `peer_review_by` BIGINT(20) NULL AFTER `grading_types_id`,
ADD INDEX `fk_peer_review_idx` (`peer_review_by` ASC);
ALTER TABLE `edulink1_lpriority`.`rflp_test` 
ADD CONSTRAINT `fk_peer_review`
  FOREIGN KEY (`peer_review_by`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  -- Added by Lalitha on 6/24/2016
  
  CREATE TABLE `language` (
  `language_id` bigint(20) NOT NULL,
  `language` varchar(45) NOT NULL,
  PRIMARY KEY (`language_id`),
  UNIQUE KEY `unique_key` (`language`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	INSERT INTO `edulink1_lpriority`.`language`
	(`language_id`,
	`language`)
	VALUES
	(1,'English'),
	(2, 'Spanish');

CREATE TABLE `benchmark_directions` (
  `benchmark_directions_id` bigint(20) NOT NULL,
  `language_id` bigint(20) NOT NULL,
  `fluency_directions` varchar(200) NOT NULL,
  `retell_directions` varchar(200) NOT NULL,
  PRIMARY KEY (`benchmark_directions_id`),
  UNIQUE KEY `unique_key` (`language_id`),
  KEY `fk_language_id_idx` (`language_id`),
  CONSTRAINT `fk_language_id` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `edulink1_lpriority`.`benchmark_directions`
(`benchmark_directions_id`, `language_id`, `fluency_directions`, `retell_directions`)
VALUES
(1,	1,	'I would like you to read a story. Please do your best reading. If you do not know a word, try your best or skip it. Keep reading until time is up. Be ready to tell all about story when you finish.',	'Now tell me as much as you can about the'),
(2,	2,	'Me gustaría a leer una historia. Por favor, haga su mejor lectura. Si usted no sabe una palabra, haz todo lo posible o evitarlo. Sigue leyendo hasta que se acabe el tiempo.',	'Ahora dime lo más que pueda acerca de la');


ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD COLUMN `benchmark_directions_id` BIGINT(20) NULL AFTER `record_time`,
ADD INDEX `fk_benchmark_directions_id_idx` (`benchmark_directions_id` ASC);
ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD CONSTRAINT `fk_benchmark_directions_id`
  FOREIGN KEY (`benchmark_directions_id`)
  REFERENCES `edulink1_lpriority`.`benchmark_directions` (`benchmark_directions_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
update assignment set benchmark_directions_id = 1 where assignment_type_id=8;

-- added by anusuya 29-06-2016 ------------------------------------------------------------------------------------------------------
  
alter table student_assignment_status add column self_graded_status varchar(30) null;
alter table student_assignment_status add column peer_graded_status varchar(30) null;

ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
DROP FOREIGN KEY `fk_peer_review_id`;
ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
DROP COLUMN `peer_review_by`,
DROP INDEX `fk_peer_review_id_idx` ;

ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
ADD COLUMN `peer_review_by` BIGINT(20) NULL AFTER `peer_graded_status`,
ADD INDEX `fk_peer_review_id_idx` (`peer_review_by` ASC);
ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
ADD CONSTRAINT `fk_peer_review_id`
  FOREIGN KEY (`peer_review_by`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
-- -------------------------------------------------------------------------------------------------------------

  -- Added by Lalitha on 7/7/2016
  ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
ADD UNIQUE INDEX `unique_key` (`assignment_questions_id` ASC, `grading_type_id` ASC, `reading_type_id` ASC);

-- Sprint 13 Db Changes

-- Added by Lalitha on 7/8/2016

CREATE TABLE `edulink1_lpriority`.`student_ptask_evidence` (
  `student_ptask_evidence_id` BIGINT(20) NOT NULL,
  `assigned_ptask_id` BIGINT(20) NOT NULL,
  `evidence` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`student_ptask_evidence_id`),
  INDEX `pk_assigned_p_task_id_idx` (`assigned_ptask_id` ASC),
  CONSTRAINT `pk_assigned_p_task_id`
    FOREIGN KEY (`assigned_ptask_id`)
    REFERENCES `edulink1_lpriority`.`assigned_ptasks` (`assigned_task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- Added by Lalitha on 7/12/2016
    
	ALTER TABLE `edulink1_lpriority`.`student_ptask_evidence` 
	ADD COLUMN `ptask_group_student_id` BIGINT(20) NULL AFTER `evidence`,
	ADD INDEX `fk_ptask_group_student_id_idx` (`ptask_group_student_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`student_ptask_evidence` 
	ADD CONSTRAINT `fk_ptask_group_student_id`
	 FOREIGN KEY (`ptask_group_student_id`)
	 REFERENCES `edulink1_lpriority`.`performance_group_students` (`performance_group_students_id`)
	 ON DELETE NO ACTION
	 ON UPDATE NO ACTION;
    
-- Added by Lalitha on 7/13/2016 
	ALTER TABLE `edulink1_lpriority`.`student` 
	ADD COLUMN `student_sc_id` BIGINT NULL AFTER `grade_status`;
	
	ALTER TABLE `edulink1_lpriority`.`school` 
	CHANGE COLUMN `school_abbr` `school_abbr` VARCHAR(50) NOT NULL ;
	

-- Added by Lalitha on 7/14/2016 for Accuracy Reading
    
INSERT INTO `edulink1_lpriority`.`assignment_type`
(`assignment_type_id`,
`assignment_type`,
`create_date`,
`change_date`,
`used_for`)
VALUES(20, 'Accuracy Reading', '2016-07-14', '2016-07-14 20:51:23',	'rti');

CREATE TABLE `asstype_readtype_relation` (
  `asstype_readtype_relation_id` bigint(20) NOT NULL,
  `assignment_type_id` bigint(20) NOT NULL,
  `reading_types_id` bigint(20) NOT NULL,
  PRIMARY KEY (`asstype_readtype_relation_id`),
  UNIQUE KEY `unique_key` (`assignment_type_id`,`reading_types_id`),
  KEY `fk_assignment_type_id_idx` (`assignment_type_id`),
  KEY `fk_reading_type_id_idx` (`reading_types_id`),
  CONSTRAINT `fk_assignment_type_id_rel` FOREIGN KEY (`assignment_type_id`) REFERENCES `assignment_type` (`assignment_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reading_type_id_rel` FOREIGN KEY (`reading_types_id`) REFERENCES `reading_types` (`reading_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `edulink1_lpriority`.`asstype_readtype_relation`
(`asstype_readtype_relation_id`,
`assignment_type_id`,
`reading_types_id`)
VALUES
('1', '8', '2'),
('2', '8', '3'),
('3', '20', '1');


Delete from fluency_marks_details where fluency_marks_id in (select fluency_marks_id from fluency_marks where reading_type_id=1);

Delete from fluency_marks where reading_type_id=1;

	
-- Added By Anusuya on 7/18/2017

	ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
    ADD COLUMN `peer_submitdate` DATE NULL DEFAULT NULL AFTER `peer_review_by`;
    
-- Added by Lalitha on 7/18/2016
    ALTER TABLE `edulink1_lpriority`.`benchmark_results` 
	DROP COLUMN `median_accuracy_score`;

    

 -- Added by Christopher 18/07/2016  
 
    ALTER TABLE `edulink1_lpriority`.`phonic_groups` 
	ADD COLUMN `group_order` BIGINT(10) NULL AFTER `question`;
	
	insert into phonic_groups (group_id,phonic_section_id,title,question,group_order) values(17,3,'CVC','hug tip cob wet jam',2);
	
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='1' WHERE `group_id`='1';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='2' WHERE `group_id`='2';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='1' WHERE `group_id`='3';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='2' WHERE `group_id`='16';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='1' WHERE `group_id`='4';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='3' WHERE `group_id`='5';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='4' WHERE `group_id`='6';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='5' WHERE `group_id`='7';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='6' WHERE `group_id`='8';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='7' WHERE `group_id`='9';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='8' WHERE `group_id`='10';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='9' WHERE `group_id`='11';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='10' WHERE `group_id`='12';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='11' WHERE `group_id`='13';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='12' WHERE `group_id`='14';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='13' WHERE `group_id`='15';
	UPDATE `edulink1_lpriority`.`phonic_groups` SET `group_order`='2' WHERE `group_id`='17';
	
	ALTER TABLE `edulink1_lpriority`.`rflp_practice` 
    ADD COLUMN `unknown_word` VARCHAR(5) NULL DEFAULT NULL AFTER `oral_rubric_score`;

	ALTER TABLE `edulink1_lpriority`.`fluency_marks_details` 
	ADD COLUMN `unknown_word` VARCHAR(5) NULL DEFAULT NULL AFTER `comments`;
	
-- -------------------------------------------------------------------------------------------------------------------------
 -- Added by Christopher 05/08/2016  
 
   CREATE TABLE `edulink1_lpriority`.`fluency_added_words` (
  `added_word_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fluency_marks_id` BIGINT(20) NOT NULL,
  `word_index` BIGINT(20) NOT NULL,
  `added_word` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`added_word_id`),
  INDEX `fk_fluency_marks_ida_idx` (`fluency_marks_id` ASC),
  CONSTRAINT `fk_fluency_marks_ida`
    FOREIGN KEY (`fluency_marks_id`)
    REFERENCES `edulink1_lpriority`.`fluency_marks` (`fluency_marks_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- ---------------------------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 8/8/2016

	ALTER TABLE `edulink1_lpriority`.`bm_categories` 
	ADD COLUMN `is_fluency_test` INT NOT NULL AFTER `bm_name`;
	
	update bm_categories set is_fluency_test=1;
	
	INSERT INTO `edulink1_lpriority`.`bm_categories`
	(`bm_category_id`,
	`bm_category`,
	`bm_name`,
	`is_fluency_test`)
	VALUES
	('4', 'end', 'Fluency Reading4',1),
	('5', '', 'Practice',0),
	('6', '', 'Progress Monitoring',0);	
	
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	ADD INDEX `fk_benchmark_id_idx` (`benchmark_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	ADD CONSTRAINT `fk_benchmark_id`
	  FOREIGN KEY (`benchmark_id`)
	  REFERENCES `edulink1_lpriority`.`bm_categories` (`bm_category_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;			

 	Update assignment set benchmark_id=5 where benchmark_id=4;
 	
 	-- Added by Anusuya on 8/8/2016
 	
   ALTER TABLE `edulink1_lpriority`.`user_registration` 
   ADD COLUMN `parent_reg_id2` BIGINT(20) NULL DEFAULT NULL AFTER `parent_reg_id`;
   
   -- -----------------------------------------------------------------------------------------------------------------
    -- Added by Christopher 10/08/2016  
   
    ALTER TABLE `edulink1_lpriority`.`k1_sets` 
	CHANGE COLUMN `k1_set_id` `k1_set_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	CHANGE COLUMN `set` `sets` VARCHAR(200) NOT NULL,
	CHANGE COLUMN `master_grade_id` `master_grade_id` BIGINT(20) NULL, 
	ADD COLUMN `list_type` VARCHAR(45) NOT NULL AFTER `set_type`,
	ADD COLUMN `cs_id` BIGINT(20) NULL DEFAULT NULL AFTER `list_type`;
	
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='1';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='2';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='3';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='16';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='17';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='18';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='19';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='20';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='21';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='22';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='23';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='24';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='25';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='26';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='28';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='27';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='30';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='29';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='31';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='32';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='33';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='34';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='35';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='36';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='37';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='38';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='39';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='40';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='41';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='42';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='43';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='44';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='45';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='46';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='47';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='48';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='49';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='50';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='51';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='52';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='53';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='54';
	UPDATE `edulink1_lpriority`.`k1_sets` SET `list_type`='System' WHERE `k1_set_id`='55';

-- ---------------------------------------------------------------------------------------------------------
	
	-- Added by Lalitha on 8/10/2016
   
   -- below columns are for handling assign and due dates of early literacy tests which are automatically assigned by the system. 
   -- they are added here as they are different for different students in case of early literacy tests which are automatically assigned
   
	ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
		ADD COLUMN `test_assign_date` DATE NULL DEFAULT NULL AFTER `peer_submitdate`,
		ADD COLUMN `test_due_date` DATE NULL DEFAULT NULL AFTER `test_assign_date`;
	
   ALTER TABLE `edulink1_lpriority`.`assignment` 
		ADD COLUMN `elt_test_type_id` BIGINT(20) NULL DEFAULT NULL AFTER `benchmark_directions_id`;


	CREATE TABLE `edulink1_lpriority`.`elt_test_types` (
		`elt_test_type_id` BIGINT(20) NOT NULL,
		`elt_test_type` VARCHAR(45) NOT NULL,
		PRIMARY KEY (`elt_test_type_id`),
		UNIQUE INDEX `elt_test_type_UNIQUE` (`elt_test_type` ASC));
	  
	INSERT INTO `edulink1_lpriority`.`elt_test_types`
		(`elt_test_type_id`, `elt_test_type`)VALUES
		(2,	'automatic'), (1,'normal');
		
	  ALTER TABLE `edulink1_lpriority`.`assignment` 
		ADD INDEX `fk_elt_test_type_idx` (`elt_test_type_id` ASC);
		ALTER TABLE `edulink1_lpriority`.`assignment` 
		ADD CONSTRAINT `fk_elt_test_type`
		  FOREIGN KEY (`elt_test_type_id`)
		  REFERENCES `edulink1_lpriority`.`elt_test_types` (`elt_test_type_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION;
  
		  
  CREATE TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` (
  `k1_auto_assigned_set_id` BIGINT(10) NOT NULL,
  `cs_id` BIGINT(20) NOT NULL,
  `set_id` BIGINT(20) NOT NULL,
  `next_auto_set_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`k1_auto_assigned_set_id`),
  INDEX `fk_auto_cs_id_idx` (`cs_id` ASC),
  INDEX `fk_auto_set_id_idx` (`set_id` ASC),
  CONSTRAINT `fk_auto_cs_id`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_lpriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auto_set_id`
    FOREIGN KEY (`set_id`)
    REFERENCES `edulink1_lpriority`.`k1_sets` (`k1_set_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
	ADD INDEX `fk_aa_set_id_idx` (`next_auto_set_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
	ADD CONSTRAINT `fk_aa_set_id`
	  FOREIGN KEY (`next_auto_set_id`)
	  REFERENCES `edulink1_lpriority`.`k1_sets` (`k1_set_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

	  
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	ADD COLUMN `auto_elt_set_id` BIGINT(20) NULL AFTER `elt_test_type_id`,
	ADD INDEX `fk_auto_a_set_id_idx` (`auto_elt_set_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	ADD CONSTRAINT `fk_auto_a_set_id`
	  FOREIGN KEY (`auto_elt_set_id`)
	  REFERENCES `edulink1_lpriority`.`k1_auto_assigned_sets` (`k1_auto_assigned_set_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;	  
		
-- --------------------------------------------------------------------------------------------------------------
-- Added by Christopher 10/08/2016  

ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
ADD COLUMN `record_time` INT NOT NULL AFTER `next_auto_set_id`;

ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
CHANGE COLUMN `k1_auto_assigned_set_id` `k1_auto_assigned_set_id` BIGINT(10) NOT NULL AUTO_INCREMENT ;

-- -------------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 8/13/2016

ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
DROP FOREIGN KEY `fk_aa_set_id`;
ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
CHANGE COLUMN `next_auto_set_id` `next_auto_set_id` BIGINT(20) NULL ;
ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
ADD CONSTRAINT `fk_aa_set_id`
  FOREIGN KEY (`next_auto_set_id`)
  REFERENCES `edulink1_lpriority`.`k1_sets` (`k1_set_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  -- -----------------------------------------------------------------------------------------------------------
  
  -- Added by Lalitha on 8/17/2016
  
  
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	DROP FOREIGN KEY `fk_elt_test_type`;
	
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	CHANGE COLUMN `elt_test_type_id` `elt_test_type_id` INT(11) NULL DEFAULT NULL ;
	
	DROP TABLE `edulink1_lpriority`.`elt_test_types`;
	
	 -- Added by Lalitha on 8/22/2016
	ALTER TABLE `edulink1_lpriority`.`assignment` 
	DROP COLUMN `elt_test_type_id`,
	DROP INDEX `fk_elt_test_type_idx` ;

-- Added By Anusya---------------------------------------------------------

	alter table fluency_marks add column accuracy_score float after marks;

-- -----------------------------------------------------------------------
-- Added by Lalitha on 9/5/2016
	
	ALTER TABLE `edulink1_lpriority`.`teacher` 
	ADD COLUMN `teacher_sc_id` BIGINT NULL AFTER `status`;
	
-- Added By Anusuya on 9/16/2016

	alter table fluency_marks add column comment varchar(400);
    insert into quality_of_response values(0,'provides zero details');
    update quality_of_response set qor_id=0 where qor_id=5;    
    
-- Added by Lalitha on 9/17/2016
ALTER TABLE `edulink1_lpriority`.`phonic_groups` 
ADD COLUMN `status` VARCHAR(15) NOT NULL DEFAULT 'active' AFTER `group_order`;

ALTER TABLE `edulink1_lpriority`.`phonic_groups` 
CHANGE COLUMN `group_id` `group_id` BIGINT(10) NOT NULL AUTO_INCREMENT ;

-- Added by Lalitha on 9/19/2016
ALTER TABLE `edulink1_lpriority`.`unit` 
DROP INDEX `grade_class_id` ,
ADD UNIQUE INDEX `grade_class_id` (`grade_class_id` ASC, `unit_name` ASC, `created_by` ASC);

-- --------------------------------------------------------------------------------------------------------
-- Added by Christopher on 20/09/2016

ALTER TABLE `edulink1_lpriority`.`fluency_marks_details` 
ADD COLUMN `skipped_word` VARCHAR(5) NULL DEFAULT NULL AFTER `unknown_word`;

ALTER TABLE `edulink1_lpriority`.`rflp_practice` 
ADD COLUMN `skipped_word` VARCHAR(5) NULL DEFAULT NULL AFTER `unknown_word`;


-- --------------------------------------------------------------------------------------------------------

-- need to be executed from here.

-- Added by Lalitha on 9/30/2016

ALTER TABLE `edulink1_lpriority`.`student` 
ADD CONSTRAINT `fk_student_grade_id`
  FOREIGN KEY (`grade_id`)
  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
 --  Added by Anusuya on 10/03/2016 -----------------
 
 alter table fluency_added_words add column word_type int(10) after added_word;

 
 -- Added by Lalitha on 10/5/2016

ALTER TABLE `edulink1_lpriority`.`k1_sets` 
ADD COLUMN `status` VARCHAR(45) NOT NULL DEFAULT 'active' AFTER `created_date`;

ALTER TABLE `edulink1_lpriority`.`k1_sets` 
ADD UNIQUE INDEX `unique_key` (`set_name` ASC, `cs_id` ASC, `master_grade_id` ASC);

-- -----Added by Anusuya on 10/17/2016---------------------------------

ALTER TABLE `edulink1_lpriority`.`phonic_sections` 
ADD COLUMN `language_id` BIGINT(20) NULL AFTER `name`,
ADD INDEX `fk_language_id2_idx` (`language_id` ASC);
ALTER TABLE `edulink1_lpriority`.`phonic_sections` 
ADD CONSTRAINT `fk_language_id2`
 FOREIGN KEY (`language_id`)
 REFERENCES `edulink1_lpriority`.`language` (`language_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;

update phonic_sections set language_id=1 where phonic_section_id in (1,2,3);

INSERT INTO `phonic_sections` VALUES (4,'Sonido de las vocales',2),(5,'Sonido de las consonantes',2),(6,'Sonido de letras dobles',2),(7,'Lectura de Palabras con Patrones Fonéticos',2);

ALTER TABLE `edulink1_lpriority`.`phonic_groups` 
CHANGE COLUMN `question` `question` VARCHAR(500) NULL DEFAULT NULL ;

INSERT INTO `edulink1_lpriority`.`phonic_groups`
(`phonic_section_id`,`title`,`question`,`group_order`,`status`) VALUES
(4,'Sonido de las vocales','i o a u e',1,'active'),
(5,'Sonidos continuos','l m n s j y f z w r ñ v',1,'active'),
(5,'Sonidos interrumpidos','t b k p d c q g',2,'active'),
(6,'Sonido de letras dobles','ch rr ll x',1,'active'),
(7,'Dos y tres fonemas(cv, vc, vcv, cvc)','lo di as un el dan vez uña Ajo oye par ojo sin sal con cal',1,'active'),
(7,'Cuatro fonemas(cvcv, vccv, vvcv, vcvc)','tomo hecho este oigo arroz lazo ramo taza imán arco',2,'active'),
(7,'Palabras polisilábicas Combinacion q+u','queja porque paquete quince queso',3,'active'),
(7,'Grupos consonanticos(bl, cl, fr, pl, dr)','blusa bicicleta complacer frondoso padre problema',4,'active'),
(7,'Fonemas(g, j, r, h, ll)','gente aguila ahora agujero llovizna ahorra agua hizo callos rimas goma ciudad humedad ferrocarril desarrollar',5,'active'),
(7,'Diptongos y diéresis(ei, au, ue, ua, ü)','reina gaucho pueblo enjuagar cigüeña paragüero cuidado huesos ruido traigo miedo',6,'active'),
(7,'Acentuación(hiatos, verbos)','cortó cobró respetó vivíamos carnicería',7,'active'),
(7,'Fonema x','existir mixto exponer existencia exhibiciones',8,'active'),
(7,'Combinaciones(bs, dr, st, nst, nstr)','obsequiar obstáculo instrumento circunstancias madriguera',9,'active'),
(7,'Sufijos(-ina, -oso, -able, -ísimo)','cristalina trabajoso comunicable tardísimo facilisimo',10,'active'),
(7,'Prefijos','resoplido anochecer deshacer promover conllevar',11,'active'),
(7,'Compuestos','cumpleaños ciempiés rascacielos picaflor cortaplumas',12,'active'),
(7,'Sufijos(-ción, -mente)','contaminación representación continuación preocupación eficazmente científicamente plácidamente aproximadamente',13,'active');


CREATE TABLE `edulink1_lpriority`.`bpst_types` (
  `bpst_type_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bpst_type` VARCHAR(45) NOT NULL,
  `desc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`bpst_type_id`));

INSERT INTO `edulink1_lpriority`.`bpst_types`
(`bpst_type_id`,
`bpst_type`,
`desc`)
VALUES
(1,'BPST-I','k-2'),
(2,'BPST-II','3-5');
  
  CREATE TABLE `edulink1_lpriority`.`bpst_groups` (
  `bpst_groups_id` BIGINT(20) NOT NULL,
  `bpst_type_id` BIGINT(20) NOT NULL,
  `phonic_group_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`bpst_groups_id`),
  INDEX `fk_bpst_type_id_idx` (`bpst_type_id` ASC),
  INDEX `fk_ph_grp_id_idx` (`phonic_group_id` ASC),
  CONSTRAINT `fk_bpst_type_id`
    FOREIGN KEY (`bpst_type_id`)
    REFERENCES `edulink1_lpriority`.`bpst_types` (`bpst_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ph_grp_id`
    FOREIGN KEY (`phonic_group_id`)
    REFERENCES `edulink1_lpriority`.`phonic_groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
ALTER TABLE `edulink1_lpriority`.`bpst_groups` 
CHANGE COLUMN `bpst_groups_id` `bpst_groups_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

INSERT INTO `edulink1_lpriority`.`bpst_groups`
(`phonic_group_id`, `bpst_type_id`)
VALUES(40,2),
(41,2),
(42,2),
(43,2),
(44,2),
(45,2),
(46,2),
(47,2),
(48,2),
(49,2),
(50,2),
(51,2),
(35,1),
(36,1),
(37,1),
(38,1),
(39,1),
(40,1),
(41,1),
(42,1),
(43,1),
(44,1),
(45,1),
(46,1),
(47,1),
(48,1),
(49,1),
(50,1),
(51,1);

-- -----------------------------------------------------------------------
-- Added By Anusuya on 27-10-2016

ALTER TABLE `edulink1_lpriority`.`legend` 
ADD COLUMN `isdefault` VARCHAR(20) NULL AFTER `color`;


CREATE TABLE `edulink1_lpriority`.`le_rubrics` (
  `le_rubric_id` BIGINT(20) NOT NULL,
  `grade_id` BIGINT(20) NOT NULL,
  `legend_sub_criteria_id` BIGINT(20) NOT NULL,
  `legend_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`le_rubric_id`),
  INDEX `le_grade_id_fk_idx` (`grade_id` ASC),
  INDEX `le_legend_dub_criteria_id_fk_idx` (`legend_sub_criteria_id` ASC),
  INDEX `le_legend_id_fk_idx` (`legend_id` ASC),
  CONSTRAINT `le_grade_id_fk`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `le_legend_dub_criteria_id_fk`
    FOREIGN KEY (`legend_sub_criteria_id`)
    REFERENCES `edulink1_lpriority`.`legend_sub_criteria` (`legend_sub_criteria_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `le_legend_id_fk`
    FOREIGN KEY (`legend_id`)
    REFERENCES `edulink1_lpriority`.`legend` (`legend_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    update legend set isdefault='yes';
    
    ALTER TABLE `edulink1_lpriority`.`legend` 
CHANGE COLUMN `legend_name` `legend_name` LONGTEXT NOT NULL ,
DROP INDEX `unique_legend_name` ;

-- -------------------------------------------------------------------------------------------
-- Added By Christopher on 27-10-2016

	ALTER TABLE `edulink1_lpriority`.`phonic_groups` 
ADD COLUMN `directions` VARCHAR(5) NULL DEFAULT 'yes' AFTER `status`;
		
-- ---------------------------------------------------------------------------------------------

UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='5';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='6';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='7';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='8';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='9';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='10';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='11';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='12';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='13';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='14';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='yes' WHERE `group_id`='15';
UPDATE `edulink1_lpriority`.`phonic_groups` SET `directions`='no' WHERE `group_id`='17';
-- ----------------------------------------------------------------------------------------------

-- Added By anusuya on 28-10-2016

ALTER TABLE `edulink1_lpriority`.`legend` 
ADD COLUMN `legend_sub_criteria_id` BIGINT(20) NULL DEFAULT NULL AFTER `isdefault`;

ALTER TABLE `edulink1_lpriority`.`legend` 
ADD INDEX `fk_legend_sub_criteria_ids_idx` (`legend_sub_criteria_id` ASC);
ALTER TABLE `edulink1_lpriority`.`legend` 
ADD CONSTRAINT `fk_legend_sub_criteria_ids`
  FOREIGN KEY (`legend_sub_criteria_id`)
  REFERENCES `edulink1_lpriority`.`legend_sub_criteria` (`legend_sub_criteria_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

 -- --------------------------------------------------------------------------------------------------
 -- Added By Christopher on 27-10-2016
 
ALTER TABLE `edulink1_lpriority`.`k1_sets` 
CHANGE COLUMN `set_name` `set_name` VARCHAR(20) NULL DEFAULT NULL ;

-- ---------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`le_rubrics` 
CHANGE COLUMN `le_rubric_id` `le_rubric_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

-- Added by Lalitha on 11/8/2016

CREATE TABLE `edulink1_lpriority`.`lp_news` (
  `lp_news_id` BIGINT(20) NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `link` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`lp_news_id`));
  
ALTER TABLE `edulink1_lpriority`.`lp_news` 
ADD UNIQUE INDEX `title_UNIQUE` (`title` ASC);

ALTER TABLE `edulink1_lpriority`.`lp_news` 
ADD UNIQUE INDEX `link_UNIQUE` (`link` ASC);

ALTER TABLE `edulink1_lpriority`.`lp_news` 
ADD COLUMN `status` VARCHAR(45) NOT NULL AFTER `link`;

ALTER TABLE `edulink1_lpriority`.`lp_news` 
CHANGE COLUMN `link` `url_link` VARCHAR(500) NOT NULL ;

ALTER TABLE `edulink1_lpriority`.`lp_news` 
CHANGE COLUMN `status` `status` VARCHAR(45) NOT NULL DEFAULT 'active' ;

INSERT INTO `edulink1_lpriority`.`lp_news`
(`lp_news_id`,`title`,`url_link`,`status`)
VALUES
('1', 'LP and the Rio School district focus on reading', 'https://www.linkedin.com/pulse/learning-priority-rio-sd-focus-reading-ian-rescigno?trk=prof-post', 'active'),
('2', 'LP at Rio Vista', 'https://twitter.com/mrgbulldogs/status/785874348603092992/video/1?cn=bWVudGlvbg%3D%3D&refsrc=email', 'active'),
('3', '21st Century Learning', 'https://www.linkedin.com/pulse/article/21st-century-learning-ian-rescigno-5950324339558604800/edit', 'active'),
('4', 'LP in the Rio classes', 'https://twitter.com/Learnpriority/status/625889775082274816', 'active'),
('5', 'Great Day with LP', 'https://twitter.com/Learnpriority/status/523192624749228032', 'active'),
('6', 'Rio Kids on LP', 'https://twitter.com/Learnpriority/status/521332456960823296', 'active'),
('7', 'Reading,  Reading, Reading', 'http://rioschools.org/blog/reading-reading-reading/', 'active');

ALTER TABLE `edulink1_lpriority`.`lp_news` 
CHANGE COLUMN `lp_news_id` `lp_news_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;



-- Added by Lalitha on 11/11/2016

INSERT INTO `legend` (`legend_id`, `legend_name`, `legend_value`, `color`, `isdefault`, `legend_sub_criteria_id`) VALUES 
(5,'Rarely uses interpersonal skills that are necessary for effective communication. \nRarely responds to questions during conversations. When attempting to clarify, verify, or challenge ideas, often comes across as argumentative or defensive; rarely participates relevantly in conversations.\nRarely delivers feedback in a manner that makes the recipients feel respected.\nOften shows negativity. ',1,NULL,'no',1),(6,'Is beginning to apply effective interpersonal skills during conversations to build positive relationships with collaborators.\nPoses questions that contribute to the discussion, but questions sometimes do not closely link to the ideas shared by collaborators; inconsistently clarifies, verifies, or challenges ideas; and sometimes participates relevantly in conversations.\nDelivers feedback, but sometimes comes across as lacking respect when attempting to be direct.\nIs usually positive, but inconsistently lets collaborators know that their opinions and ideas are valued.',2,NULL,'no',1),(7,'Applies effective interpersonal skills during conversations to build positive relationships with collaborators.\nPoses questions that connect to ideas shared during discussion, Usually clarifies, verifies, or challenges ideas and conclusions with diplomacy, without coming across as argumentative or defensive; consistently participates relevantly in conversations.\nIs beginning to deliver feedback in a manner that makes the recipients feel respected; can be direct, but shows care and respect.\nIs usually positive and is beginning to express that collaborators’ opinions and ideas are valued.',3,NULL,'no',1),(8,'Asks probing questions that lead to greater understanding and help the collaborators think more deeply about the discussion topic.\nConsistently delivers feedback in a manner that makes the recipients feel respected.\nConsistently communicates positively and indicates that collaborators’ opinions and ideas are valued.',4,NULL,'no',1),(9,'Shows an understanding of digital media and environments and how they can be used for communication, but rarely uses the tools for communication.\n',1,NULL,'no',2),(10,'\n\nDevelops messages to communicate using digital media and environments, but the messages lack clarity, focus, and specificity.\nUses a format, level of formality, and style that is inappropriate based on the communication purpose and channel messages when communicating using 21st century tools.\n',2,NULL,'no',2),(11,'Usually develops clear, focused, concise, and specific messages when communicating using digital media and environments (i.e., telecommunications and online resources for asynchronous and synchronous communication). Messages are usually crafted so that both the sender and the receiver understand the same information as a result of the communication.\nUsually uses a format, level of formality, and style that is appropriate based on the communication purpose and channel when communicating using 21st century tools.',3,NULL,'no',2),(12,'Consistently develops a clear, focused, and specific message when communicating using 21st century tools; both the sender and the receiver understand the same information as a result of the communication.\nConsistently uses a format, level of formality, and style that is appropriate based on the communication purpose and channel when communicating using digital media and environments.',4,NULL,'no',2),(13,'Is building a foundation to listen effectively: connects the information shared by the speaker to own background knowledge and experience, but often confuses the key points the speaker is trying to convey; draws inferences and conclusions that show minimal understanding.\nRarely asks questions to gain clarification about intended message; is building a foundation to do so, with confidence.\nListens inattentively, demonstrates a lack of interest in the speaker’s message, and appears to be disengaged.\nRarely honors agreed-upon discussion norms related to listening (e.g., listens to others with care, speaks one at a time, and “shares the air”). ',1,NULL,'no',3),(14,'Is beginning to use strategies to listen effectively: connects the information shared by the speaker to own background knowledge and experience, but sometimes confuses the key points the speaker is trying to convey; draws inferences and conclusions that show some understanding.\nSometimes asks questions to gain clarification about the intended message.\nIs beginning to show ability to listen actively and attentively; demonstrates interest in the speaker’s message, but does not provide verbal or nonverbal feedback to indicate that the message was received and to show understanding.\nHonors agreed-upon discussion norms related to listening (e.g., listens to others with care, speaks one at a time, and “shares the air”) with reminders.',2,NULL,'no',3),(15,'Uses strategies to listen effectively: connects the information shared by the speaker to own background knowledge and experience, identifies the key points the speaker is trying to convey, and draws logical inferences and conclusions that show understanding.\nFrequently asks clarifying questions about the speaker’s intended message.\nUsually listens actively and attentively, demonstrates interest in the speaker’s message, and is beginning to provide verbal or nonverbal feedback to indicate that the message was received and to show understanding.\nRecognizes and usually honors agreed-upon discussion norms related to listening (e.g., listens to others with care, speaks one at a time, and “shares the air”).',3,NULL,'no',3),(16,'When listening, deciphers meaning, including the speaker’s values and attitudes.\nListens actively and attentively, demonstrates interest in the speaker’s message, and provides verbal or nonverbal feedback to indicate that the message was received; shows understanding.\nConsistently honors established norms related to listening (e.g., listens to others with care, speaks one at a time, and “shares the air”).',4,NULL,'no',3),(17,'\n\nRarely communicates with learners from other cultures; shows limited understanding of the perspectives of the world that learners from other cultures bring to the table.',1,NULL,'no',4),(18,'Is beginning to develop cultural understanding by communicating with learners from other cultures; shows some understanding of the perspectives of the world that learners from other cultures bring to the table.\nIs building a foundation to communicate effectively in diverse environments (including multi-lingual); is beginning to show awareness that use of colloquialisms, jargon, and slang make it difficult for collaborators to understand what the student is trying to communicate.',2,NULL,'no',4),(19,'Develops cultural understanding by communicating with learners from other cultures; shows understanding of the perspectives of the world that learners from other cultures bring to the table.\nIs beginning to communicate effectively in diverse environments (including multi-lingual). Shows understanding that the use of colloquialisms, jargon, or slang makes it difficult for some learners from other cultures to understand what the student is trying to communicate.',3,NULL,'no',4),(20,'Bridges cultural differences to communicate effectively with learners from other cultures during collaborative experiences; compromises and shows respect and openness to those whose views on the world may be different, while working toward a common goal.',4,NULL,'no',4),(21,'Presents claims and findings; presentation lacks focus and clarity; and communicates a limited amount of descriptions, facts, details, and examples. Does not look at audience, uses a volume that is too low to be heard, and pronounces several words incorrectly.\nUse of digital media detracts from the presentation and diminishes audience understanding and interest.\nWhen delivering oral presentations, is able to accurately answer few grade-level appropriate questions to demonstrate conceptual understanding and knowledge. Shows a lack of confidence when fielding questions during presentations; offers responses to few questions, but responses lack clarity.',1,NULL,'no',5),(22,'Presents claims and findings; presentation is somewhat focused and clear. Added descriptions, facts, details, and examples would enhance the quality of the presentation. Makes some eye contact and speaks loudly enough for some audience members, but mispronounces some words.\nUse of digital media somewhat enhances audience understanding and adds interest.\nWhen delivering oral presentations, is able to accurately answer some grade-level appropriate questions to demonstrate conceptual understanding and knowledge. Is beginning to show confidence when fielding questions during presentations; offers clear responses to some questions.',2,NULL,'no',5),(23,'Presents claims and findings; communicates in a focused, clear manner with an appropriate amount of descriptions, facts, details, and examples. Uses appropriate eye contact, adequate volume, and clear pronunciation.\nUse of digital media enhances audience understanding and adds interest.\nWhen delivering oral presentations, accurately and confidently fields grade-level appropriate questions to demonstrate conceptual understanding and knowledge.',3,NULL,'no',5),(24,'Discusses presentation topic with passion and excitement; generates a high level of interest from the audience.',4,NULL,'no',5),(25,'\n\nRarely reflects accurately on the level of success of communications. (Where was his/her communication strong? Where was it weak? How much support did he/she need? What improvements could be made in communication next time?)',1,NULL,'no',6),(26,'\n\nSometimes reflects accurately on the level of success of communications. (Where was his/her communication strong? Where was it weak? How much support did he/she need? What improvements could be made in communication next time?)',2,NULL,'no',6),(27,'Consistently reflects accurately on the level of success of communications. (Where was his/her communication strong? Where was it weak? How much support did he/she need? What improvements could be made in communication next time?)',3,NULL,'no',6),(28,'Accurately identifies underlying causes that influence communication challenges or breakdowns; consistently identifies action items to improve communication.',4,NULL,'no',6),(29,'Frequently misunderstands the scope and importance of the group’s work.\nRefrains from contributing to discussion about the group’s goals and deadlines. \nPlays a passive role in carrying out the work of the group; tends to be an observer rather than taking the initiative.',1,NULL,'no',7),(30,'Demonstrates a limited understanding of the scope and importance of the group’s work.\nOffers minor contributions to the establishment of the group’s goals and deadlines. \nFulfills roles and responsibilities with regular prompting and coaching.',2,NULL,'no',7),(31,'Demonstrates a clear understanding of the scope and importance of the group’s work.\nPlays an active role and offers significant contributions to the establishment of the group’s goals and deadlines. \nFulfills roles and responsibilities with little prompting or coaching.',3,NULL,'no',7),(32,'Provides leadership to the group in defining the mission and vision for the work. Clearly articulates the team’s goals, thoughtfully organizes and divides the work, checks on progress, or provides focus and direction for the project. Shares leadership; knows when to lead and when to follow.\nShows a willingness to challenge the mission and vision for the team’s work; diplomatically shares position on the mission and vision.',4,NULL,'no',7),(33,'Rarely follows agreed-upon norms for respectful discussions and decision-making.',1,NULL,'no',8),(34,'Periodically, but not consistently, follows agreed-upon norms for respectful discussions and decision-making.',2,NULL,'no',8),(35,'Consistently follows agreed-upon norms for respectful discussions and decision-making; consistently accepts and fulfills individual role within group.',3,NULL,'no',8),(36,'Consistently works to address challenges in the group through discussion and consensus-building activities; shows understanding of the learning needs of group members.',4,NULL,'no',8),(37,'Demonstrates a willingness to listen to the ideas and opinions expressed by group members, but does not always show understanding; explains his/her own ideas and opinions, but rarely shows a willingness to change his/her ideas or opinions based on the information exchanged.',1,NULL,'no',9),(38,'Shows understanding of the various ideas and opinions of group members; sometimes shows a willingness to change his/her ideas or opinions based on the information exchanged.',2,NULL,'no',9),(39,'Acknowledges and shows understanding of the diverse ideas and opinions expressed by group members; consistently shows a willingness to change his/her ideas or opinions based on the information exchanged.',3,NULL,'no',9),(40,'Compares and contrasts diverse ideas and opinions shared by team members; identifies points of agreement and disagreement; can look at ideas from multiple perspectives.',4,NULL,'no',9),(41,'Shows a lack of willingness to accept responsibilities.\nRarely helps others in need.\nIs rarely well-prepared for group work; consistently submits work late.\nPerforms work that is often not related or is unimportant to the assignment; submits work that is incomplete and does not meet specifications for assigned task. \nRarely prioritizes and tracks the group’s progress toward established goals and deadlines.',1,NULL,'no',10),(42,'Shows a willingness to accept responsibilities.\nSometimes helps others in need.\nIs sometimes well-prepared for group work; completes some individual action items on time.\nProducts may be lacking in quality; periodically, but not consistently, meets specifications for assigned tasks. \nPeriodically prioritizes and tracks the group’s progress toward established goals and deadlines.',2,NULL,'no',10),(43,'Accepts responsibilities with a positive attitude.\nAssists others as needed; values opinions and skills of all group members.\nIs often well-prepared for group work; completes all individual action items on time. \nSubmits high-quality products; regularly meets specifications for assigned tasks.\nConsistently prioritizes and tracks the group’s progress toward established goals and deadlines.',3,NULL,'no',10),(44,'Shows excitement about the task at hand; inspires and motivates the group.\nFrequently produces large quantities of high-quality individual work; connects this work to the work of others in ways that improve the group’s overall work.\nConsistently and accurately monitors individual and team progress toward goals, making sufficient corrections and adjustments when needed. \nProvides suggestions regarding resources that can assist others in their work.',4,NULL,'no',10),(45,'Shows a lack of awareness of the range of current technological tools available and how they can be used for collaboration; is beginning to demonstrate skill and confidence in the use of the tools selected, but does not actually use the tools to assist in completion of tasks.\nRefrains from collaborating asynchronously using technological tools.',1,NULL,'no',11),(46,'Is beginning to show awareness of the range of current technological tools available and how they can be used for collaboration; uses tools selected to complete the tasks, but often uses the selected tools in a manner that decreases the team’s productivity.\nShows a lack of comfort and confidence in collaborating asynchronously using technological tools, but can be guided to do so.',2,NULL,'no',11),(47,'Shows awareness of the range of current technological tools available and how they can be used for collaboration, effectively uses tools selected to complete the tasks, and is beginning to use the selected tools appropriately and responsibly in a manner that increases the team’s productivity.\nIs beginning to demonstrate comfort and confidence in collaborating asynchronously using technological tools.',3,NULL,'no',11),(48,'Consistently uses selected tools appropriately and responsibly in a manner that enhances the group’s productivity.\n\n\nWhen collaborating asynchronously using technological tools, consistently uses effective communication strategies to appropriately exchange information and read, interpret, and respond to collaborators’ work using an appropriate tone.',4,NULL,'no',11),(49,'Refrains from offering feedback.\nResponds to constructive feedback with a negative and/or disengaged attitude. Delivery of or response to constructive criticism limits the group’s ability to produce high-quality work (e.g., becomes defensive or provides vague, confusing commentary).',1,NULL,'no',12),(50,'Is beginning to show confidence in offering feedback to team members; feedback is sometimes well-received.\nSometimes accepts constructive feedback; shows minimal appreciation for constructive feedback.',2,NULL,'no',12),(51,'Consistently provides constructive feedback; delivers feedback effectively in a manner that is well-received by the recipients.\nProactively seeks feedback; consistently accepts and shows appreciation for constructive feedback.',3,NULL,'no',12),(52,'Shows a high comfort level in providing and receiving feedback; displays curiosity about the quality of work and seeks helpful, descriptive feedback from peers, the teacher, and experts involved; and provides and receives feedback in ways that advance the group’s ability to produce high-quality work.',4,NULL,'no',12),(53,'Rarely engages in self-critique or reflection on collaboration strengths and areas in need of improvement. Shows an inability to describe learning as a result of collaboration experience. (Where was the student’s collaboration strong? Where was it weak? How much support did he/she need? What improvements could be made in collaboration next time?)',1,NULL,'no',13),(54,'Sometimes engages in self-critique and reflection on collaboration strengths and areas in need of improvement. Describes learning as a result of collaboration experience, but description lacks clarity. (Where was the student’s collaboration strong? Where was it weak? How much support did he/she need? What improvements could be made in collaboration next time?)',2,NULL,'no',13),(55,'Consistently engages in self-critique and reflection on collaboration strengths and areas in need of improvement. Clearly describes learning as a result of collaboration experience. (Where was the student’s collaboration strong? Where was it weak? How much support did he/she need? What improvements could be made in collaboration next time?)',3,NULL,'no',13),(56,'Is highly reflective and shows a strong capacity for self-critique.',4,NULL,'no',13),(57,'Shows an inability to grasp the problem, investigation, or challenge; rarely seeks clarity and understanding.\nCreates a small number of questions connected to the problem, investigation, or challenge, but questions are unclear. Student is building the foundation to refine and improve questions and to identify a key question or prioritized set of questions on which to focus. \nAttempts to gather information from sources, but information is very limited or is not relevant to the inquiry questions.',1,NULL,'no',14),(58,'Defines the problem, investigation, or challenge, but explanation lacks clarity; seeks clarity and understanding at times, but sometimes moves forward without sufficient understanding.\nIs beginning to formulate clear questions, but questions are limited and are sometimes not closely related to the problem, investigation, or challenge. Somewhat effectively, refines and improves questions and identifies a key question or a prioritized set of questions on which to focus.  \nConducts research and gathers information from a limited number of sources; somewhat effectively selects relevant, trustworthy information.',2,NULL,'no',14),(59,'Clearly defines the problem, investigation, or challenge; continuously seeks clarity and understanding.\nCreates additional questions related to the problem, investigation, or challenge. Effectively refines and improves questions and identifies a key question or set of questions to investigate.  \nConducts efficient research and gathers relevant information from multiple sources; effectively assesses the credibility and accuracy of each source.',3,NULL,'no',14),(60,'Provides a thorough description of the problem, investigation, or challenge; explains why the inquiry is relevant and necessary. \nGenerates powerful questions closely related to the problem, investigation, or challenge (i.e., open-ended; requiring deep thought).\nUses advanced searches effectively. Gathers relevant information from multiple trustworthy sources. ',4,NULL,'no',14),(61,'Builds background knowledge from a single or very limited number of sources.\nShows an understanding of the concept of point of view; is building a foundation to describe sources’ points of view.\nExplains point of view; is building a foundation to explain the difference between their point of view and that of sources.\nShows understanding of the components of an argument; is building a foundation to provide a basic assessment of the argument and claims presented by sources.',1,NULL,'no',15),(62,'With support, integrates information from a limited number of sources on the inquiry topic to build background knowledge. \nIs beginning to describe sources’ points of view, but struggles to analyze how the sources address conflicting viewpoints.\nExplains the difference between their point of view and that of sources, but explanation is somewhat unclear.\nProvides a basic assessment of the argument and claims presented by sources, but is unable to assess whether the reasoning is sound, if the evidence is helpful to the argument, or if a sufficient amount of evidence is provided.\nIs beginning to recognize when irrelevant (i.e., unrelated, unimportant) evidence is introduced.',2,NULL,'no',15),(63,'Integrates information from multiple sources on the inquiry topic to build background knowledge, with independence. \nAccurately determines sources’ points of view and analyzes how the sources address conflicting viewpoints. \nClearly explains the difference between his/her point of view and that of sources. \nIs laying the foundation to thoroughly and accurately assess and describe arguments and claims provided by sources. Assesses whether the reasoning is sound, if the evidence is helpful to the argument, and if a sufficient amount of evidence is provided.\nConsistently recognizes when irrelevant (i.e., unrelated; unimportant) evidence is introduced.',3,NULL,'no',15),(64,'Integrates information from a large number of sources on the inquiry topic to build background knowledge. \nThoroughly evaluates sources’ points of view and detects bias, when present; clearly analyzes how sources address conflicting viewpoints. \nShows openness or ability to look at information from different viewpoints, even viewpoints that challenge his/her point of view.\nThoroughly and accurately assesses and describes arguments and claims provided by sources. Assesses whether the reasoning is sound, if the evidence is helpful to the argument, and if a sufficient amount of evidence is provided; identifies false statements.',4,NULL,'no',15),(65,'Defines “assumption”; is building a foundation to explain assumptions (what student believes, but has not proven) about the problem, investigation, or challenge.\nDefines “inference”; is building a foundation to draw inferences from the inquiry experience.',1,NULL,'no',16),(66,'Explains assumptions (what student believes, but has not proven) about the problem, investigation, or challenge, but explanation is somewhat unclear and does not include supportive details.\nIs beginning to show ability to describe inferences from the inquiry experience, but inferences are somewhat insignificant.',2,NULL,'no',16),(67,'Clearly explains and justifies assumptions (what student believes, but has not proven) about the problem, investigation, or challenge. \nConsistently makes clear inferences from the inquiry experience.',3,NULL,'no',16),(68,'Explains assumptions (what student believes, but has not proven) about the problem, investigation, or challenge; justifies assumptions with sensible evidence.\nDraws deep inferences from the inquiry experience.\nDemonstrates ethical reasoning and judgment by clearly sharing perspectives on why the proposed course of action is morally the best decision.',4,NULL,'no',16),(69,'Is hesitant to share ideas about how to best solve the problem, meet the challenge, or answer the inquiry question.\nIs beginning to use systems thinking in problem solving. Describes the function of the whole system; names all of the parts; and describes the function of each part, but is unable to predict what will happen if a part is missing.\nShows an inability to test ideas, assess the outcome, and decide if a new solution is necessary.',1,NULL,'no',17),(70,'Shares ideas about how to best solve the problem, meet the challenge, or answer the inquiry question, but the explanation is somewhat unclear.\nUses systems thinking in problem solving. Describes the function of the whole system; names all of the parts; and describes the function of each part and predicts what would happen if a part is missing, but is unable to describe the subsystems.\nTests ideas. With assistance, assesses the outcome, but the explanation may be somewhat unclear. Is beginning to show ability to assess results and decide if a new solution is necessary. ',2,NULL,'no',17),(71,'Clearly explains ideas about how to best solve the problem, meet the challenge, or answer the inquiry question; clearly describes why their ideas make sense.\nUses systems thinking in problem solving. Describes the function of the whole system, describes the subsystems, and describes how a change in the subsystems influences the entire system.\nTests ideas. With little assistance, assesses and clearly describes the outcome and decides if a new solution is necessary.',3,NULL,'no',17),(72,'Ideas about how to best solve the problem, meet the challenge, or answer the inquiry question are very convincing.\nWhen using systems thinking in problem solving, defines if the system is in equilibrium or is changing; explains how the system interacts with another system.\nTests ideas with patience, precision, and accuracy; Carefully assesses outcome and draws logical conclusions about next steps.',4,NULL,'no',17),(73,'Restates facts rather than stating actual opinion about the inquiry question.\nDefines “proof”; is building a foundation to gather proof from sources to support opinion.\nIs beginning to explain the opinion of sources; is building a foundation to compare and contrast personal opinion from differing opinions.\nDefines “reason,” “evidence,” and “opposing claim”; is building a foundation to organize reasons and evidence to support opinion and recognize opposing claims.',1,NULL,'no',18),(74,'States opinion about inquiry question, but explanation is somewhat unclear.\nWith assistance, gathers proof (evidence) from sources to support opinion, but proof is somewhat weak; sources are at times inaccurate or not trustworthy.\nCompares and contrasts personal opinion from differing opinions, but explanation is somewhat unclear.\nIs beginning to show ability to organize reasons and evidence and recognize opposing claims, with support.',2,NULL,'no',18),(75,'Clearly states opinion about inquiry question. \nWith little assistance, gathers proof (evidence) from sources to support opinion; uses accurate, correct, credible, and trustworthy sources to support opinion.\nClearly compares and contrasts personal opinion from differing opinions.\nWith minimal support, clearly organizes reasons and evidence and recognizes opposing claims.',3,NULL,'no',18),(76,'States opinion about inquiry question in a very articulate, convincing way.\nIndependently gathers proof (evidence) from sources to support opinion; gathers very accurate, correct, credible, and trustworthy sources.\nVery persuasively, articulately, and clearly compares and contrasts personal opinion from differing opinions.\nOrganizes reasons and evidence in a very well-organized, logical order. ',4,NULL,'no',18),(77,'Shows understanding that there are critical thinking skills, and that with practice, students can improve these skills. Is building a foundation to define the critical thinking skills assessed in the unit in his/her own words.\nShows an inability to monitor his/her thought process and articulate or describe strengths and weaknesses in thinking during the inquiry experience.\nDescribes personal point of view and the points of view explored during the inquiry experience. Is building a foundation to describe how his/her points of view compare and contrast with other points of view explored during the inquiry experience.\nShows an inability to show awareness of how the ability to recognize and analyze points of view impacted the critical thinking process.\nRarely assesses one’s own critical thinking dispositions with accuracy. (Does the student continuously seek clarity and understanding? Use accuracy and detail? Dedicate enough time and effort to thinking? Reflect on the amount of support needed during the critical thinking process?)',1,NULL,'no',19),(78,'Explains the critical thinking skills assessed in the unit in his/her own words, but explanation is somewhat unclear.\nIs beginning to show ability to monitor his/her thought process and articulate or describe perceived strengths and weaknesses in thinking during the inquiry experience, but is unable to describe how assumptions impacted the ability to think critically in the investigation. \nDescribes how inferences were helpful in the investigation, but explanation is somewhat unclear.\nDescribes how his/her points of view compare and contrast with other points of view explored during the inquiry experience, but explanation is somewhat unclear.\nIs beginning to show awareness of how the ability to recognize and analyze points of view impacted the critical thinking process, but description is somewhat unclear.\nSometimes assesses one’s own critical thinking dispositions with accuracy. (Does the student continuously seek clarity and understanding? Use accuracy and detail? Dedicate enough time and effort to thinking? Reflect on the amount of support needed during the critical thinking process?)',2,NULL,'no',19),(79,'Clearly explains the critical thinking skills assessed in the unit in his/her own words.\nMonitors his/her thought process and articulates or describes strengths and weaknesses in thinking during parts of the inquiry experience. Describes how assumptions impacted the ability to think critically in the investigation.\nDescribes how inferences were helpful in the investigation.\nDescribes how his/her points of view compare and contrast with other points of view explored during the inquiry experience. \nShows awareness of how the ability to recognize and analyze points of view impacted the critical thinking process.\nOften assesses one’s own critical thinking dispositions with accuracy. (Does the student continuously seek clarity and understanding? Use accuracy and detail? Dedicate enough time and effort to thinking? Reflect on the amount of support needed during the critical thinking process?)',3,NULL,'no',19),(80,'Consistently monitors his/her thought process and clearly articulates strengths and weaknesses in thinking during the inquiry experience.\nRecognizes and articulates his/her egocentric thinking, when practiced, as well as egocentric thinking on the part of others.',4,NULL,'no',19),(81,'Shows an inability to grasp the problem, investigation, or challenge.\nGenerates few ideas; offers ideas that are often vague and relate loosely to the challenge.',1,NULL,'no',20),(82,'Defines the problem, investigation, or challenge, but explanation lacks clarity and may impact idea generation.\nCommunicates ideas to meet the challenge, but the volume is not sufficient to spark a creative process; ideas are somewhat connected to the challenge at hand.',2,NULL,'no',20),(83,'Clearly defines the problem, investigation, or challenge in a manner that builds a framework for idea generation.\nGenerates a sufficient volume of clear ideas to meet the challenge; ideas closely address the challenge and are sufficiently detailed to spark a creative process.',3,NULL,'no',20),(84,'Generates a high volume of clear ideas to meet the challenge.\nTakes an original, unique, and imaginative approach to idea generation; offers ideas that are broad in their diversity.\nShows skill in asking open-ended questions that lead to the generation of original ideas.\nAsks, “Is my idea really new?” Clearly explains information acquired from researching precedents.',4,NULL,'no',20),(85,'Reviews ideas without evidence of categorization or prioritization; selects an idea, but is unable to provide a rationale for the decision.\n',1,NULL,'no',21),(86,'Is beginning to demonstrate the ability to use organizational techniques such as categorization, prioritization, and classification to evaluate ideas; selects the best idea, but rationale for decision lacks clarity.\nReviews feedback and translates feedback into “next steps,” but is unable use feedback to improve the quality of the idea.',2,NULL,'no',21),(87,'Effectively uses organizational techniques such as categorization, prioritization, and classification to evaluate ideas; uses results to select the best idea and provides a clear rationale for decision.\nReviews feedback, translates feedback into logical “next steps,” and makes revisions that improve the quality of the idea.',3,NULL,'no',21),(88,'Sorts, arranges, categorizes, and prioritizes ideas in ways that turn options into creatively productive outcomes. \nMakes revisions that significantly improve the quality of ideas.',4,NULL,'no',21),(89,'Displays low tolerance for ambiguity.\nShares an unclear vision of the end product or performance. \nSometimes perseveres when presented with challenges.',1,NULL,'no',22),(90,'Is beginning to display tolerance for ambiguity.\nShows a somewhat clear vision of the end product or performance.\nUsually perseveres when presented with challenges.\n',2,NULL,'no',22),(91,'Demonstrates openness to ambiguity in exploring ideas.\nShares a clear vision of the end product or performance.\nConsistently perseveres when presented with challenges, shows resilience in situations in which failure is part of the experience, and shows confidence and an ability to take risks.',3,NULL,'no',22),(92,'Is curious, flexible, and open to ambiguity in exploring ideas.\nDisplays sufficient resilience when confronted with production challenges/setbacks. Is confident and able to take calculated risks and adapt plans.\nConsistently challenges existing boundaries and ideas.',4,NULL,'no',22),(93,'Almost always works in isolation; is hesitant to communicate ideas and provide feedback to others.',1,NULL,'no',23),(94,'Works collaboratively with others. Is beginning to communicate ideas and feedback to others, but sometimes struggles to make connections between or to build upon others’ ideas to generate new and unique insights.',2,NULL,'no',23),(95,'Works collaboratively with others. Communicates ideas and feedback to others effectively; usually makes connections between and builds upon others’ ideas to generate new and unique insights.',3,NULL,'no',23),(96,'Works collaboratively with others. Communicates ideas and feedback to others effectively; often makes connections between and builds upon others’ ideas to generate new and unique insights.',4,NULL,'no',23),(97,'Creates a product or performance that has a vague or incomplete connection to the task. Provides an explanation of the innovation process and how the product addresses the challenge, but explanation is unclear.\nProduct is not considered to be useful or unique by the broad, target audience and is not considered to be creative by experts. (May simply replicate an existing product.)\nShows an inability to reflect on the quality of work.',1,NULL,'no',24),(98,'Uses ideas to create products or performances that are directly related to the challenge or problem. Provides a somewhat clear explanation of the innovation process and how the product addresses the challenge.\nProduct is considered to be somewhat valuable and unique by the broad, target audience and is considered by experts to be somewhat creative.\nReflects with minimal accuracy on the quality of work.',2,NULL,'no',24),(99,'Uses ideas to create a product or performance that is directly related to the challenge or problem. Provides a clear explanation of the innovation process and how the product addresses the challenge.\nProduct is considered to be valuable and unique by the broad, target audience and is considered by experts to be creative. \nReflects with accuracy on the quality of work.',3,NULL,'no',24),(100,'Always exhibits diligence and ethical behavior in producing creative works. \nUses convergent thinking skills and/or design thinking strategies as appropriate to develop creative ideas into tangible solutions or contributions. \nDemonstrates a high degree of adaptability in the production of creative products or performances (e.g., making do with what is at hand to reach goals.',4,NULL,'no',24),(101,'Rarely analyzes and questions one’s own creativity and innovation with accuracy. (Does the student assess the quality of his/her ideas? Show perseverance? Dedicate enough time and effort to the creative process? Reflect on the amount of support needed during the creative process?)',1,NULL,'no',25),(102,'Sometimes analyzes and questions one’s own creativity and innovation with accuracy. (Does the student assess the quality of his/her ideas? Show perseverance? Dedicate enough time and effort to the creative process? Reflect on the amount of support needed during the creative process?)',2,NULL,'no',25),(103,'Often analyzes and questions one’s own creativity and innovation with accuracy. (Does the student assess the quality of his/her ideas? Show perseverance? Dedicate enough time and effort to the creative process? Reflect on the amount of support needed during the creative process?)',3,NULL,'no',25),(104,'Is highly reflective and shows a strong capacity for self-critique.',4,NULL,'no',25);

-- Updated on the server
-- ############################################################################################################################################ 
-- ############################################################################################################################################ 

-- New Changes to be updated from 11/11/2016

-- Added by Christopher on 14/11/2016

ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD COLUMN `language_id` BIGINT(20) NULL DEFAULT 1 AFTER `auto_elt_set_id`,
ADD INDEX `fk_language_id3_idx` (`language_id` ASC);
ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD CONSTRAINT `fk_language_id3`
  FOREIGN KEY (`language_id`)
  REFERENCES `edulink1_lpriority`.`language` (`language_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- Added by Lalitha on 11/14/2016
    update assignment set language_id=2 where benchmark_directions_id=2;
    
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Fluency 1 - September%' and cs_id=534;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Fluidez%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Fluidez 1%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Friday 10/7/16%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Jueves 29%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'kfdfg%' and cs_id=536;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Mi amiga Judy%' and cs_id=541;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Monday 09/26/16%' and cs_id=529;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Monday 10/10/16%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Monday 10/3/16%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Monday 26 La Economía%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=526;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=524;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=512;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=527;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=520;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=513;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=541;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=536;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=517;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=518;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=506;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice%' and cs_id=514;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice Spanish%' and cs_id=515;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Practice Spanish%' and cs_id=511;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=524;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=513;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=520;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=527;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=536;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=517;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=511;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=518;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=514;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=521;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency%' and cs_id=508;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1%' and cs_id=526;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1%' and cs_id=515;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1%' and cs_id=512;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1%' and cs_id=506;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1%' and cs_id=540;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1 - September%' and cs_id=540;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1 - September%' and cs_id=538;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 1 - September%' and cs_id=542;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency 4%' and cs_id=511;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=521;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=508;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=511;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=506;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=513;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=540;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency- Retake%' and cs_id=536;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish Fluency1%' and cs_id=536;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish I-Retake%' and cs_id=511;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Spanish- Retake1%' and cs_id=511;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Thursday 09/29/16%' and cs_id=529;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Thursday 10/6/16%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Tuesday 09/27/16%' and cs_id=529;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Tuesday 10/11/16%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Tuesday 10/4/16 %' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Tuesday 27 El Arte%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Una Casita para el Perro%' and cs_id=541;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Viernes 30%' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Wednesday 09/28/16%' and cs_id=529;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Wednesday 10/5/16 %' and cs_id=533;
	update  edulink1_lpriority.assignment set language_id=2 where  assign_status='active' and assignment_type_id=18 and title like 'Wednesday 28%' and cs_id=533;
	

-- Updated on the server
-- ############################################################################################################################################ 
-- ############################################################################################################################################ 

-- ---------------------------------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 18/11/2016 
	
	CREATE TABLE `edulink1_lpriority`.`math_quiz` (
  `math_quiz_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fraction` VARCHAR(45) NOT NULL,
  `word` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`math_quiz_id`));
ALTER TABLE `edulink1_lpriority`.`math_quiz` 
ADD COLUMN `status` VARCHAR(45) NOT NULL DEFAULT 'active' AFTER `word`;

CREATE TABLE `edulink1_lpriority`.`math_conversion_types` (
  `conversion_type_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `conversion_type` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`conversion_type_id`));
  
CREATE TABLE `edulink1_lpriority`.`math_quiz_questions` (
  `quiz_questions_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `math_quiz_id` BIGINT(20) NOT NULL,
  `conversion_type_id` BIGINT(20) NOT NULL,
  `actual_answer` VARCHAR(45) NOT NULL,
  `is_blank` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`quiz_questions_id`),
  INDEX `fk_quiz_id_idx` (`math_quiz_id` ASC),
  INDEX `fk_conv_id_idx` (`conversion_type_id` ASC),
  CONSTRAINT `fk_quiz_id`
    FOREIGN KEY (`math_quiz_id`)
    REFERENCES `edulink1_lpriority`.`math_quiz` (`math_quiz_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conv_id`
    FOREIGN KEY (`conversion_type_id`)
    REFERENCES `edulink1_lpriority`.`math_conversion_types` (`conversion_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	CREATE TABLE `edulink1_lpriority`.`student_math_assess_marks` (
  `student_math_assess_marks_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT(20) NOT NULL,
  `quiz_question_id` BIGINT(20) NOT NULL,
  `answer` VARCHAR(45) NULL,
  `mark` INT(12) NULL,
  PRIMARY KEY (`student_math_assess_marks_id`),
  INDEX `fk_sas_id_idx` (`student_assignment_id` ASC),
  INDEX `fk_quz_que_id_idx` (`quiz_question_id` ASC),
  CONSTRAINT `fk_sas_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `edulink1_lpriority`.`student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_quz_que_id`
    FOREIGN KEY (`quiz_question_id`)
    REFERENCES `edulink1_lpriority`.`math_quiz_questions` (`quiz_questions_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
INSERT INTO `edulink1_lpriority`.`math_conversion_types`
(`conversion_type_id`,
`conversion_type`,
`status`)
VALUES
('1', 'Fraction', 'active'),
('2', 'Word', 'active'),
('3', 'Decimal', 'active'),
('4', 'Percentage', 'active'),
('5', 'Rounded%', 'active'),
('6', 'Equivalent Fraction1', 'active'),
('7', 'Equivalent Fraction2', 'active'),
('8', 'Equivalent Fraction3', 'active'),
('9', 'Equivalent Fraction4', 'active');

-- ----------------------------------------------------------------------------------------
-- Added by Anusuya on 21-11-2016 ---------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
ADD COLUMN `legend_criteria_id` BIGINT(20) NULL AFTER `status`,
ADD INDEX `fk_legend_criteria_idss_idx` (`legend_criteria_id` ASC);
ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
ADD CONSTRAINT `fk_legend_criteria_idss`
  FOREIGN KEY (`legend_criteria_id`)
  REFERENCES `edulink1_lpriority`.`legend_criteria` (`legend_criteria_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
 -- Added By Anusuya on 24-11-2016--------------------------------------------------------------
  
  ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
ADD COLUMN `legend_id` BIGINT(20) NULL AFTER `legend_criteria_id`,
ADD INDEX `fk_le_legend_ids_idx` (`legend_id` ASC);
ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
ADD CONSTRAINT `fk_le_legend_ids`
  FOREIGN KEY (`legend_id`)
  REFERENCES `edulink1_lpriority`.`legend` (`legend_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

   
  ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
DROP FOREIGN KEY `fk_le_legend_ids`;
ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
CHANGE COLUMN `legend_id` `legend_id` BIGINT(20) NULL DEFAULT 0 ;
ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
ADD CONSTRAINT `fk_le_legend_ids`
  FOREIGN KEY (`legend_id`)
  REFERENCES `edulink1_lpriority`.`legend` (`legend_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  -- Added By Anusuya on 25-11-2016--------------------------------------------------------------
  
  ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
DROP FOREIGN KEY `fk_le_legend_ids`;
ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
DROP COLUMN `legend_id`,
ADD COLUMN `le_color` VARCHAR(20) NULL DEFAULT NULL AFTER `legend_criteria_id`,
DROP INDEX `fk_le_legend_ids_idx` ;

ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
CHANGE COLUMN `le_color` `le_color` FLOAT NULL DEFAULT NULL ;

ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
CHANGE COLUMN `le_color` `le_score` FLOAT NULL DEFAULT NULL ;

-- Added by Christopher on 25/11/2016 ------------------------------------------------------------------

UPDATE `edulink1_lpriority`.`math_conversion_types` SET `conversion_type`='Rounded' WHERE `conversion_type_id`='5';

ALTER TABLE `edulink1_lpriority`.`math_quiz_questions` 
CHANGE COLUMN `actual_answer` `actual_answer` VARCHAR(45) NULL ;

ALTER TABLE `edulink1_lpriority`.`math_quiz` 
DROP COLUMN `word`,
ADD COLUMN `cs_id` BIGINT(20) NOT NULL AFTER `fraction`;

-- Added By Anusuya on 28-11-2016-----------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`learning_indicator_values` 
ADD COLUMN `submit_status` VARCHAR(45) NULL DEFAULT NULL AFTER `teacher_comment`;

-- Added by Lalitha on 11/28/2016

	ALTER TABLE `edulink1_lpriority`.`legend` 
	ADD COLUMN `created_by` BIGINT(20) NULL DEFAULT NULL AFTER `legend_sub_criteria_id`,
	ADD INDEX `fk_reg_id2_idx` (`created_by` ASC);
	ALTER TABLE `edulink1_lpriority`.`legend` 
	ADD CONSTRAINT `fk_reg_id2`
	  FOREIGN KEY (`created_by`)
	  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;
		
	ALTER TABLE `edulink1_lpriority`.`le_rubrics` 
		ADD COLUMN `status` VARCHAR(45) NOT NULL DEFAULT 'active' AFTER `legend_id`;


	ALTER TABLE `edulink1_lpriority`.`legend` 
		ADD COLUMN `status` VARCHAR(45) NULL DEFAULT 'active' AFTER `created_by`;
		
		
-- Added by Christopher on 28/11/2016
INSERT INTO `edulink1_lpriority`.`assignment_type` (`assignment_type_id`, `assignment_type`, `create_date`, `change_date`, `used_for`) VALUES ('21', 'Math Assessment', '2016-07-14', '2016-07-14 20:51:23', 'mathAssessment');

-- -------------------------------------------------------------------

-- Added by Lalitha on 11/29/2016

ALTER TABLE `edulink1_lpriority`.`gradeevents` 
ADD COLUMN `is_composite` VARCHAR(5) NULL AFTER `event_name`;

update gradeevents set is_composite = 'true';

INSERT INTO `edulink1_lpriority`.`gradeevents`
(`event_id`,`event_name`,`is_composite`)
VALUES ('4', 'rti', 'false');

-- Added by Christopher on 29/11/2016

ALTER TABLE `edulink1_lpriority`.`math_quiz` 
CHANGE COLUMN `cs_id` `cs_id` BIGINT(20) NULL DEFAULT 0 ;

delete from learning_indicator_values;
delete from learning_indicator;
delete from legend_sub_criteria where legend_sub_criteria_id in(34,36);

ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria` 
CHANGE COLUMN `legend_sub_criteria_id` `legend_sub_criteria_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

insert into legend_sub_criteria(legend_criteria_id,legend_sub_criteria_name) values(6,'Global');


-- --------------------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 30/11/2016

ALTER TABLE `edulink1_lpriority`.`assignment` 
ADD COLUMN `math_quiz_ids` VARCHAR(200) NULL DEFAULT NULL ;

-- --------------------------------------------------------------------------------------------------------------------------------

	
	CREATE TABLE `edulink1_lpriority`.`iol_report` (
	  `iol_report_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	  `cs_id` BIGINT(20) NOT NULL,
	  `student_id` BIGINT(20) NOT NULL,
	  `status` VARCHAR(45) NULL DEFAULT NULL,
	  `report_date` DATE NULL DEFAULT NULL,
	  PRIMARY KEY (`iol_report_id`));

	ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
		DROP FOREIGN KEY `fk_l_student_id`,
		DROP FOREIGN KEY `fk_l_cs_id`;
		ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
		DROP COLUMN `student_id`,
		DROP COLUMN `cs_id`,
		DROP INDEX `fk_rpt_student_id_idx` ,
		DROP INDEX `fk_rpt_cs_id_idx` ;
		
	ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
		ADD COLUMN `iol_report_id` BIGINT(20) NULL AFTER `le_score`,
		ADD INDEX `fk_iol_rpt_id_idx` (`iol_report_id` ASC);
		ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
		ADD CONSTRAINT `fk_iol_rpt_id`
		  FOREIGN KEY (`iol_report_id`)
		  REFERENCES `edulink1_lpriority`.`iol_report` (`iol_report_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION;
		  
		  		  
 -- -------------------------------------------------------------------------------------------------------------------------
 -- #######################################################################################################################
 
		  -- Added by Lalitha on 12/12/2016
		  
		  UPDATE `edulink1_lpriority`.`math_conversion_types`
			SET
			`conversion_type` = 'Rounded %'
			WHERE `conversion_type_id` = 5 ;
			
        Delete FROM edulink1_lpriority.student_math_assess_marks where quiz_question_id in
			(SELECT quiz_question_id FROM edulink1_lpriority.math_quiz_questions where conversion_type_id in (8,9));
		DELETE FROM edulink1_lpriority.math_quiz_questions where conversion_type_id in (8,9);
		DELETE FROM edulink1_lpriority.math_conversion_types where conversion_type_id in (8,9);
		
		
	-- Added by Lalitha on 12/15/2016
	
		ALTER TABLE `edulink1_lpriority`.`sub_questions` 
			ADD COLUMN `title` VARCHAR(100) NULL DEFAULT NULL AFTER `created_by`;
			
	-- Added by Lalitha on 12/19/2016
	
		UPDATE `edulink1_lpriority`.`math_conversion_types`
			SET
			`conversion_type` = 'Rounded'
		WHERE `conversion_type_id` = 5 ;
			
			-- Updated in the server so far 
			
		-- Added by Lalitha on 12/28/2016
		UPDATE `edulink1_lpriority`.`bm_categories`
	 	 SET `bm_category` = 'middle'
		WHERE `bm_category_id` in (2,3);
			
		INSERT INTO `edulink1_lpriority`.`bm_ss_cut_off_marks`
		(`grade_id`,
		`bm_category_id`,
		`fluency_cut_off`,
		`retell_cut_off`)
		VALUES
		(2,6,0,0),
		(5,6,0,0),
		(6,6,0,0),
		(8,6,0,0),
		(11,6,0,0),
		(12,6,0,0),
		(15,6,0,0),
		(16,6,0,0),
		(18,6,0,0),
		(21,6,0,0),
		(24,6,0,0),
		(25,6,0,0),
		(26,6,0,0),
		(27,6,0,0),
		(28,6,0,0),
		(31,6,0,0),
		(32,6,0,0),
		(36,6,0,0),
		(39,6,0,0),
		(41,6,0,0),
		(42,6,0,0);
		
		-- updated so far

-- --------------------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 30/11/2016
		
UPDATE `edulink1_lpriority`.`legend` SET `color`='#fc7171' WHERE `legend_id`='1';
UPDATE `edulink1_lpriority`.`legend` SET `color`='#ffff8c' WHERE `legend_id`='2';
UPDATE `edulink1_lpriority`.`legend` SET `color`='#83db81' WHERE `legend_id`='3';
UPDATE `edulink1_lpriority`.`legend` SET `color`='#fee9ab' WHERE `legend_id`='4';

-- --------------------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 03/01/2017

ALTER TABLE `edulink1_lpriority`.`student_ptask_evidence` 
CHANGE COLUMN `student_ptask_evidence_id` `student_ptask_evidence_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

-- --------------------------------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 1/10/2017

	Update math_quiz_questions set actual_answer =33.33 where quiz_questions_id= '13';
	Update math_quiz_questions set actual_answer =16.67 where quiz_questions_id= '40';
	Update math_quiz_questions set actual_answer =14.29 where quiz_questions_id= '49';
	Update math_quiz_questions set actual_answer =12.5 where quiz_questions_id= '58';
	Update math_quiz_questions set actual_answer =11.11 where quiz_questions_id= '67';
	Update math_quiz_questions set actual_answer =66.67 where quiz_questions_id= '85';
	Update math_quiz_questions set actual_answer =28.57 where quiz_questions_id= '103';
	Update math_quiz_questions set actual_answer =22.22 where quiz_questions_id= '112';
	Update math_quiz_questions set actual_answer =42.86 where quiz_questions_id= '139';
	Update math_quiz_questions set actual_answer =37.5 where quiz_questions_id= '148';
	Update math_quiz_questions set actual_answer =57.14 where quiz_questions_id= '166';
	Update math_quiz_questions set actual_answer =83.33 where quiz_questions_id= '184';
	Update math_quiz_questions set actual_answer =62.5 where quiz_questions_id= '202';
	Update math_quiz_questions set actual_answer =55.56 where quiz_questions_id= '211';
	Update math_quiz_questions set actual_answer =85.71 where quiz_questions_id= '220';
	Update math_quiz_questions set actual_answer =87.5 where quiz_questions_id= '229';
	Update math_quiz_questions set actual_answer =77.78 where quiz_questions_id= '238';
	Update math_quiz_questions set actual_answer =88.89 where quiz_questions_id= '247';
	Update math_quiz_questions set actual_answer =71.43 where quiz_questions_id= '302';
	
			


-- Added By Anusuya on 1/18/2017--------------------------------------------------

  CREATE TABLE `edulink1_lpriority`.`mulyr_legend` (
  `mulyr_legend_id` BIGINT NOT NULL AUTO_INCREMENT,
  `mulyr_legend_name` VARCHAR(45) NOT NULL,
  `mulyr_legend_color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`mulyr_legend_id`));
  
  insert into mulyr_legend values(1,'Not Met','red'),(2,'Almost Met','yellow'),(3,'Met','green'),(4,'Exceeds','green');
  
  update mulyr_legend set mulyr_legend_color='lime' where mulyr_legend_id in (3,4);
  
  
  -- Added by Lalitha on 1/24/2016
  
  UPDATE `edulink1_lpriority`.`math_conversion_types` SET `conversion_type`='Rounded Percentage' WHERE `conversion_type_id`='5';
  UPDATE `edulink1_lpriority`.`math_conversion_types` SET `conversion_type`='Simplified Fraction' WHERE `conversion_type_id`='1';

-- updated so far

 -- Added By Anusuya on 1/25/2017 ---------------------------------------------------------------------------------
 
  CREATE TABLE `edulink1_lpriority`.`trimester` (
  `trimester_id` BIGINT NOT NULL AUTO_INCREMENT,
  `trimester_name` VARCHAR(45) NULL,
  PRIMARY KEY (`trimester_id`));
    
  insert into trimester values(1,'trimester one'),(2,'trimester two'),(3,'trimester three');
  
  ALTER TABLE `edulink1_lpriority`.`iol_report` 
 	ENGINE = InnoDB ,
 	ADD COLUMN `trimester_id` BIGINT NOT NULL AFTER `report_date`;
 
 ALTER TABLE `edulink1_lpriority`.`iol_report` 
	CHANGE COLUMN `trimester_id` `trimester_id` BIGINT(20) NULL ;
	update  edulink1_lpriority.iol_report  set trimester_id = null;

	ALTER TABLE `edulink1_lpriority`.`trimester` 
	ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`iol_report` 
ADD CONSTRAINT `fk_trimester_id`
  FOREIGN KEY (`trimester_id`)
  REFERENCES `edulink1_lpriority`.`trimester` (`trimester_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  -- Added by Lalitha on 1/27/2017
  
	ALTER TABLE `edulink1_lpriority`.`iol_report` 
	ADD INDEX `fk_iol_cs_id_idx` (`cs_id` ASC),
	ADD INDEX `fk_iol_student_id_idx` (`student_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`iol_report` 
	ADD CONSTRAINT `fk_iol_cs_id`
	  FOREIGN KEY (`cs_id`)
	  REFERENCES `edulink1_lpriority`.`class_status` (`cs_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION,
	ADD CONSTRAINT `fk_iol_student_id`
	  FOREIGN KEY (`student_id`)
	  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

	ALTER TABLE `edulink1_lpriority`.`learning_indicator` 
	ADD CONSTRAINT `fk_li_iol_report_id`
	  FOREIGN KEY (`iol_report_id`)
	  REFERENCES `edulink1_lpriority`.`iol_report` (`iol_report_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

  
  
 -- ---------------------------------------------------------------------------------------------------------------
 
	  -- added by Lalitha on 1/31/2017
	    Update math_quiz_questions set actual_answer= '4/8' where quiz_questions_id='318';
		Update math_quiz_questions set actual_answer= '8/16' where quiz_questions_id='319';
		Update math_quiz_questions set actual_answer= '2/4' where quiz_questions_id='326';
		Update math_quiz_questions set actual_answer= '2/4' where quiz_questions_id='332';
		Update math_quiz_questions set actual_answer= '3/6' where quiz_questions_id='333';
		Update math_quiz_questions set actual_answer= '2/8' where quiz_questions_id='339';
		Update math_quiz_questions set actual_answer= '3/12' where quiz_questions_id='340';
		Update math_quiz_questions set actual_answer= '2/16' where quiz_questions_id='346';
		Update math_quiz_questions set actual_answer= '3/24' where quiz_questions_id='347';
		Update math_quiz_questions set actual_answer= '2/10' where quiz_questions_id='353';
		Update math_quiz_questions set actual_answer= '3/15' where quiz_questions_id='354';
		
		ALTER TABLE `edulink1_lpriority`.`le_rubrics` 
		ENGINE = InnoDB ;

		ALTER TABLE `edulink1_lpriority`.`le_rubrics` 
		ADD CONSTRAINT `fk_lr_grade_id`
		  FOREIGN KEY (`grade_id`)
		  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION,
		ADD CONSTRAINT `fk_lr_lgd_sub_cri_id`
		  FOREIGN KEY (`legend_sub_criteria_id`)
		  REFERENCES `edulink1_lpriority`.`legend_sub_criteria` (`legend_sub_criteria_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION,
		ADD CONSTRAINT `fk_lr_lgd_id`
		  FOREIGN KEY (`legend_id`)
		  REFERENCES `edulink1_lpriority`.`legend` (`legend_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION;

-- Added by Lalitha on 2/1/2017

	  CREATE TABLE `edulink1_lpriority`.`caaspp_types` (
	  `caaspp_types_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	  `caaspp_type` VARCHAR(45) NOT NULL,
	  PRIMARY KEY (`caaspp_types_id`));
  
		CREATE TABLE `edulink1_lpriority`.`caaspp_scores` (
		  `caaspp_scores_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
		  `teacher_id` BIGINT(20) NOT NULL,
		  `student_id` BIGINT(20) NOT NULL,
		  `grade_id` BIGINT(20) NOT NULL,
		  `caaspp_type_id` BIGINT(20) NOT NULL,
		  `caaspp_score` FLOAT NOT NULL,
		  PRIMARY KEY (`caaspp_scores_id`),
		  INDEX `caaspp_teacher_id_idx` (`teacher_id` ASC),
		  INDEX `caaspp_student_id_idx` (`student_id` ASC),
		  INDEX `caaspp_grade_id_idx` (`grade_id` ASC),
		  CONSTRAINT `caaspp_teacher_id`
		    FOREIGN KEY (`teacher_id`)
		    REFERENCES `edulink1_lpriority`.`teacher` (`teacher_id`)
		    ON DELETE NO ACTION
		    ON UPDATE NO ACTION,
		  CONSTRAINT `caaspp_student_id`
		    FOREIGN KEY (`student_id`)
		    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
		    ON DELETE NO ACTION
		    ON UPDATE NO ACTION,
		  CONSTRAINT `caaspp_grade_id`
		    FOREIGN KEY (`grade_id`)
		    REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
		    ON DELETE NO ACTION
		    ON UPDATE NO ACTION);
			
			ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
			ADD INDEX `caaspp_type_id_idx` (`caaspp_type_id` ASC);
			ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
			ADD CONSTRAINT `caaspp_type_id`
			  FOREIGN KEY (`caaspp_type_id`)
			  REFERENCES `edulink1_lpriority`.`caaspp_types` (`caaspp_types_id`)
			  ON DELETE NO ACTION
			  ON UPDATE NO ACTION;
			  
	    INSERT INTO `edulink1_lpriority`.`caaspp_types`
		(`caaspp_types_id`,
		`caaspp_type`)
		VALUES
		 (1,'ELA Scaled Level'),(2,	'Math Scaled Level');
		 
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `grade_id` ASC, `caaspp_type_id` ASC);
		
		-- Added by Lalitha on 2/3/2017
		
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		DROP INDEX `unique_key` ,
		ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `grade_id` ASC, `caaspp_type_id` ASC, `teacher_id` ASC);
		
		-- Updated on the server from here
		
		ALTER TABLE `edulink1_lpriority`.`academic_grades` 
		DROP INDEX `acedamic_grade_name_UNIQUE` ,
		ADD UNIQUE INDEX `acedamic_grade_name_UNIQUE` (`acedamic_grade_name` ASC, `score_from` ASC, `score_to` ASC);


		update  edulink1_lpriority.academic_grades set acedamic_grade_name ='F'  where acedamic_grade_id >13;
		
		--updated on the server to here
		
		
		-- Added by Lalitha on 2/9/2017
		
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		ADD COLUMN `class_id` BIGINT(20) NOT NULL AFTER `caaspp_score`,
		DROP INDEX `unique_key` ,
		ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `grade_id` ASC, `caaspp_type_id` ASC, `class_id` ASC),
		ADD INDEX `caaspp_class_id_idx` (`class_id` ASC);
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		ADD CONSTRAINT `caaspp_class_id`
		  FOREIGN KEY (`class_id`)
		  REFERENCES `edulink1_lpriority`.`class` (`class_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION;
		  
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		DROP FOREIGN KEY `caaspp_teacher_id`;
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		CHANGE COLUMN `teacher_id` `teacher_id` BIGINT(20) NULL ;
		ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
		ADD CONSTRAINT `caaspp_teacher_id`
		  FOREIGN KEY (`teacher_id`)
		  REFERENCES `edulink1_lpriority`.`teacher` (`teacher_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION;
		  
		  
 -- --------------------------------------------------------------------------------------------------------------------	
 -- Added by Anusuya on 10/2/2017 --------------------------------------------------------------------------------------
    
   CREATE TABLE `edulink1_lpriority`.`fluency_error_types` (
  `error_type_id` BIGINT NOT NULL AUTO_INCREMENT,
  `error_type` VARCHAR(45) NULL,
  PRIMARY KEY (`error_type_id`));
  
  insert into fluency_error_types values(1,'Error Word'),(2,'Skipped Word'),(3,'Self Corrected Word'),(4,'Added Word');
  
  ALTER TABLE `edulink1_lpriority`.`fluency_error_types` 
  ADD COLUMN `error_color` VARCHAR(45) NULL AFTER `error_type`;
  
  update fluency_error_types set error_color="#FF00FF" where error_type_id=1;
update fluency_error_types set error_color="#0000FF" where error_type_id=2;
update fluency_error_types set error_color="#008000" where error_type_id=3;
update fluency_error_types set error_color="#FF00FF" where error_type_id=4;
		
insert into fluency_error_types values(5,'UnKnownWord','#642EFE');

 -- Added by Anusuya on 20/2/2017 --------------------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`stem_paths` (
  `stem_path_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_path_desc` LONGTEXT NULL,
  PRIMARY KEY (`stem_path_id`));
  
  insert into stem_paths values(1,'Adopted Science curriculum with cross-curriculum planning'),
  (2,'Research from online (teacher finds a premade unit)'),
  (3,'Create from scratch - then add the design principles');
  
  CREATE TABLE `edulink1_lpriority`.`stem_unit` (
  `stem_unit_id` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_class_id` BIGINT NOT NULL,
  `stem_path_id` BIGINT NOT NULL,
  `created_by` BIGINT NOT NULL,
  `stem_unit_name` VARCHAR(45) NULL DEFAULT NULL,
  `stem_unit_desc` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`stem_unit_id`),
  INDEX `fk_stem_grade_class_id_idx` (`grade_class_id` ASC),
  INDEX `fk_stem_path_ids_idx` (`stem_path_id` ASC),
  INDEX `fk_stem_created_by_idx` (`created_by` ASC),
  CONSTRAINT `fk_stem_grade_class_id`
    FOREIGN KEY (`grade_class_id`)
    REFERENCES `edulink1_lpriority`.`grade_classes` (`grade_class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_path_ids`
    FOREIGN KEY (`stem_path_id`)
    REFERENCES `edulink1_lpriority`.`stem_paths` (`stem_path_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_created_by`
    FOREIGN KEY (`created_by`)
    REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    CREATE TABLE `edulink1_lpriority`.`stem_questions_type` (
  `stem_ques_typeid` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_ques_type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`stem_ques_typeid`));


  CREATE TABLE `edulink1_lpriority`.`stem_questions` (
  `stem_question_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_unit_id` BIGINT NOT NULL,
  `stem_ques_typeid` BIGINT NOT NULL,
  `stem_question` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`stem_question_id`),
  INDEX `fk_stem_ques_typeids_idx` (`stem_ques_typeid` ASC),
  INDEX `fk_stem_unit_ids_idx` (`stem_unit_id` ASC),
  CONSTRAINT `fk_stem_ques_typeids`
    FOREIGN KEY (`stem_ques_typeid`)
    REFERENCES `edulink1_lpriority`.`stem_questions_type` (`stem_ques_typeid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_unit_ids`
    FOREIGN KEY (`stem_unit_id`)
    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    CREATE TABLE `edulink1_lpriority`.`stem_areas` (
  `stem_area_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_area` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`stem_area_id`));
  
  ALTER TABLE `edulink1_lpriority`.`iol_report` 
ADD COLUMN `is_mul_yr_rpt` VARCHAR(45) NULL DEFAULT NULL AFTER `trimester_id`;

-- Created By Anusuya on 1/3/2017 ----------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`stem_grade_strands` (
  `stem_grade_strand_id` BIGINT NOT NULL AUTO_INCREMENT,
  `master_grades_id` BIGINT NOT NULL,
  `stem_area_id` BIGINT NOT NULL,
  `stem_strand_title` VARCHAR(100) NULL,
  `added_desc` LONGTEXT NULL,
  PRIMARY KEY (`stem_grade_strand_id`),
  INDEX `fk_stem_master_grade_id_idx` (`master_grades_id` ASC),
  INDEX `fk_stem_area_idss_idx` (`stem_area_id` ASC),
  CONSTRAINT `fk_stem_master_grade_id`
    FOREIGN KEY (`master_grades_id`)
    REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_area_idss`
    FOREIGN KEY (`stem_area_id`)
    REFERENCES `edulink1_lpriority`.`stem_areas` (`stem_area_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
CREATE TABLE `edulink1_lpriority`.`stem_strand_concepts` (
  `stem_str_cpt_id` INT NOT NULL AUTO_INCREMENT,
  `stem_grade_strand_id` BIGINT NOT NULL,
  `stem_concept_desc` LONGTEXT NULL,
  PRIMARY KEY (`stem_str_cpt_id`),
  INDEX `fk_stem_grade_strand_ids_idx` (`stem_grade_strand_id` ASC),
  CONSTRAINT `fk_stem_grade_strand_ids`
    FOREIGN KEY (`stem_grade_strand_id`)
    REFERENCES `edulink1_lpriority`.`stem_grade_strands` (`stem_grade_strand_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    ALTER TABLE `edulink1_lpriority`.`stem_strand_concepts` 
CHANGE COLUMN `stem_str_cpt_id` `stem_str_cpt_id` BIGINT NOT NULL AUTO_INCREMENT ;

CREATE TABLE `edulink1_lpriority`.`stem_str_cpt_details` (
  `stem_str_cpt_detailsid` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_str_cpt_id` BIGINT NOT NULL,
  `concept_det_desc` LONGTEXT NULL,
  PRIMARY KEY (`stem_str_cpt_detailsid`),
  INDEX `fk_stem_strand_concept_ids_idx` (`stem_str_cpt_id` ASC),
  CONSTRAINT `fk_stem_strand_concept_ids`
    FOREIGN KEY (`stem_str_cpt_id`)
    REFERENCES `edulink1_lpriority`.`stem_strand_concepts` (`stem_str_cpt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `edulink1_lpriority`.`stem_unit` 
ADD COLUMN `stem_notes` LONGTEXT NULL DEFAULT NULL AFTER `stem_unit_desc`;

-- Created by Anusuya on 6/3/2017 -----------------------------------------------

insert into stem_questions_type values(1,'Essential Questions'),(2,'Unit Questions');

insert into stem_areas values(1,'Science'),(2,'Engineering'),(3,'Technology'),(4,'Math');

ALTER TABLE `edulink1_lpriority`.`stem_strand_concepts` 
CHANGE COLUMN `stem_concept_desc` `stem_concept` LONGTEXT NULL DEFAULT NULL ;

ALTER TABLE `edulink1_lpriority`.`stem_strand_concepts` 
ADD COLUMN `stem_concept_desc` LONGTEXT NULL DEFAULT NULL AFTER `stem_concept`;

-- Added By Anusuya on 7/3/2017--------------------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`unit_stem_strands` (
  `unit_stem_strands_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_unit_id` BIGINT NOT NULL,
  `stem_area_id` BIGINT NULL,
  `stem_grade_strand_id` BIGINT NULL,
  `narrative` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`unit_stem_strands_id`),
  INDEX `fk_stem_unit_idss_idx` (`stem_unit_id` ASC),
  INDEX `fk_stem_areas_idss_idx` (`stem_area_id` ASC),
  INDEX `fk_stem_grade_strandsidss_idx` (`stem_grade_strand_id` ASC),
  CONSTRAINT `fk_stem_unit_idss`
    FOREIGN KEY (`stem_unit_id`)
    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_areas_idss`
    FOREIGN KEY (`stem_area_id`)
    REFERENCES `edulink1_lpriority`.`stem_areas` (`stem_area_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_grade_strandsidss`
    FOREIGN KEY (`stem_grade_strand_id`)
    REFERENCES `edulink1_lpriority`.`stem_grade_strands` (`stem_grade_strand_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    CREATE TABLE `edulink1_lpriority`.`unit_stem_content_quests` (
  `unit_stem_cont_quesid` BIGINT NOT NULL AUTO_INCREMENT,
  `unit_stem_strands_id` BIGINT NOT NULL,
  `content_question` LONGTEXT NULL,
  PRIMARY KEY (`unit_stem_cont_quesid`),
  INDEX `fk_unit_stem_strands_idss_idx` (`unit_stem_strands_id` ASC),
  CONSTRAINT `fk_unit_stem_strands_idss`
    FOREIGN KEY (`unit_stem_strands_id`)
    REFERENCES `edulink1_lpriority`.`unit_stem_strands` (`unit_stem_strands_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    CREATE TABLE `edulink1_lpriority`.`unit_stem_concepts` (
  `unit_stem_concepts_id` BIGINT NOT NULL AUTO_INCREMENT,
  `unit_stem_strands_id` BIGINT NOT NULL,
  `stem_str_cpt_id` BIGINT NOT NULL,
  PRIMARY KEY (`unit_stem_concepts_id`),
  INDEX `fk_unit_stem_strand_idsss_idx` (`unit_stem_strands_id` ASC),
  INDEX `fk_unit_stem_str_cpt_idss_idx` (`stem_str_cpt_id` ASC),
  CONSTRAINT `fk_unit_stem_strand_idsss`
    FOREIGN KEY (`unit_stem_strands_id`)
    REFERENCES `edulink1_lpriority`.`unit_stem_strands` (`unit_stem_strands_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_stem_str_cpt_idss`
    FOREIGN KEY (`stem_str_cpt_id`)
    REFERENCES `edulink1_lpriority`.`stem_strand_concepts` (`stem_str_cpt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    -- -----------------------------------------------------------------------------------------------------------




insert into stem_grade_strands values(1,6,1,'Strand 1: Inquiry Process',NULL),
                                     (2,6,1,'Strand 2: History and Nature of Science',NULL),
                                     (3,6,1,'Strand 3: Science in Personal and Social Perspectives',NULL),
									 (4,6,1,'Strand 4: Life Science',NULL),
                                     (5,6,1,'Strand 5: Physical Science',NULL),
									 (6,6,1,'Strand 6: Earth and Space Science',NULL);
									 

insert into stem_strand_concepts values(1,1,'S1C1PO1','Differentiate among a question, hypothesis, and prediction.'),
									   (2,1,'S1C1PO2','Formulate questions based on observations that lead to the development of a hypothesis.'),
                                       (3,1,'S1C1PO3','Locate research information, not limited to a single source, for use in the design of a controlled investigation.'),
									   (4,1,'S1C2PO1','Demonstrate safe behavior and appropriate procedures (e.g., use and care of technology, materials, organisms) in all science inquiry.'),
									   (5,1,'S1C2PO2','Design an investigation to test individual variables using scientific processes.'),
									   (6,1,'S1C2PO3','Conduct a controlled investigation using scientific processes.'),
									   (7,1,'S1C2PO4','Perform measurements using appropriate scientific tools (e.g., balances, microscopes, probes, micrometers).'),
                                       (8,1,'S1C2PO5','Keep a record of observations, notes, sketches, questions, and ideas using tools such as written and/or computer logs.'),
                                       (9,1,'S1C3PO1','Analyze data obtained in a scientific investigation to identify trends.'),
                                       (10,1,'S1C3PO2','Form a logical argument about a correlation between variables or sequence of events (e.g., construct a cause-and-effect chain that explains a sequence of events).'), 
									   (11,1,'S1C3PO3','Evaluate the observations and data reported by others'),
									   (12,1,'S1C3PO4','Interpret simple tables and graphs produced by others.'),
                                       (13,1,'S1C3PO5','Analyze the results from previous and/or similar investigations to verify the results of the current investigation.'),
                                       (14,1,'S1C3PO6','Formulate new questions based on the results of a completed investigation.'),
                                       (15,1,'S1C4PO1','Choose an appropriate graphic representation for collected data: '), 
                                       (16,1,'S1C4PO2','Display data collected from a controlled investigation.'), 
                                       (17,1,'S1C4PO3','Communicate the results of an investigation with appropriate use of qualitative and quantitative information.'), 
									   (18,1,'S1C4PO4','Create a list of instructions that others can follow in carrying out a procedure (without the use of personal pronouns).'),
									   (19,1,'S1C4PO5','Communicate the results and conclusion of the investigation.');

									   
insert into stem_strand_concepts values(20,2,'S2C1PO1','Identify how diverse people and/or cultures, past and present, have made important contributions to scientific innovations (e.g., Jacques Cousteau [inventor, marine explorer], supports Strand 4; William Beebe [scientist], supports Strand 4; Thor Heyerdahl [anthropologist], supports Strand 6).'),
									   (21,2,'S2C1PO2','Describe how a major milestone in science or technology has revolutionized the thinking of the time (e.g., Cell Theory, sonar, SCUBA, underwater robotics).'),
                                       (22,2,'S2C1PO3','Analyze the impact of a major scientific development occurring within the past decade.'),
									   (23,2,'S2C1PO4','Describe the use of technology in science-related careers.'),
									   (24,2,'S2C2PO1','Describe how science is an ongoing process that changes in response to new information and discoveries.'),
									   (25,2,'S2C2PO2','Describe how scientific knowledge is subject to change as new information and/or technology challenges prevailing theories.'),
									   (26,2,'S2C2PO3','Apply the following scientific processes to other problem solving or decision making situations:');
                                      


insert into stem_strand_concepts values(27,3,'S3C1PO1','Evaluate the effects of the following natural hazards:'),
									   (28,3,'S3C1PO2','Describe how people plan for, and respond to, the following natural disasters:'),
                                       (29,3,'S3C2PO1','Propose viable methods of responding to an identified need or problem.'),
									   (30,3,'S3C2PO2','Compare possible solutions to best address an identified need or problem.'),
									   (31,3,'S3C2PO3','Design and construct a solution to an identified need or problem using simple classroom materials.'),
									   (32,3,'S3C2PO4','Describe a technological discovery that influences science.');
									   
                                      
insert into stem_strand_concepts values(33,4,'S4C1PO1','Explain the importance of water to organisms.'),
									   (34,4,'S4C1PO2','Describe the basic structure of a cell, including:'),
                                       (35,4,'S4C1PO3','Describe the function of each of the following cell parts:'),
									   (36,4,'S4C1PO4','Differentiate between plant and animal cells.'),
									   (37,4,'S4C1PO5','Explain the hierarchy of cells, tissues, organs, and systems.'),
									   (38,4,'S4C1PO6','Relate the following structures of living organisms to their functions: '),
                                       (39,4,'S4C1PO7','Describe how the various systems of living organisms work together to perform a vital function:'), 
                                       (40,4,'S4C3PO1','Explain that sunlight is the major source of energy for most ecosystems.'), 
                                       (41,4,'S4C3PO2','Describe how the following environmental conditions affect the quality of life:');
									   
                         
insert into stem_strand_concepts values(46,6,'S6C1PO1','Describe the properties and the composition of the layers of the atmosphere.'),
									   (47,6,'S6C1PO2','Explain the composition, properties, and structure of the Earth’s lakes and rivers.'),
                                       (48,6,'S6C1PO3','Explain the composition, properties, and structures of the oceans’ zones and layers.'),
									   (49,6,'S6C1PO4','Analyze the interactions between the Earth’s atmosphere and the Earth’s bodies of water (water cycle).'),
                                       (50,6,'S6C1PO5','Describe ways scientists explore the Earth’s atmosphere and bodies of water.'),
                                       (51,6,'S6C2PO1','Explain how water is cycled in nature.'),
									   (52,6,'S6C2PO2','Identify the distribution of water within or among the following:'),
                                       (53,6,'S6C2PO3','Analyze the effects that bodies of water have on the climate of a region.'),
                                       (54,6,'S6C2PO4','Analyze the following factors that affect climate:'),
									   (55,6,'S6C2PO5','Analyze the impact of large-scale weather systems on the local weather.'),
                                       (56,6,'S6C2PO6','Create a weather system model that includes:');


                                       
-- Added By anusuya 7/3/2014 time : 2:41 pm -----------------------------------------------------------------------------------------------------------------------------------------------------

insert into stem_str_cpt_details values(1,15,'line graph'),(2,15,'double bar graph'),(3,15,'stem and leaf plot'),(4,15,'histogram');
                                      
insert into stem_str_cpt_details values(5,26,'observing'),
                                       (6,26,'questioning'),
                                       (7,26,'communicating'),
                                       (8,26,'comparing'),
                                       (9,26,'measuring'),
                                       (10,26,'classifying'),
                                       (11,26,'predicting'),
                                       (12,26,'organizing data'),
									   (13,26,'inferring'),
                                       (14,26,'generating hypotheses '),
                                       (15,26,'identifying variables');
                                       
                                       
 insert into stem_str_cpt_details values(16,27,'sandstorm'),
                                       (17,27,'hurricane'),
                                       (18,27,'tornado'),
                                       (19,27,'ultraviolet light'),
                                       (20,27,'lightning-caused fire');
                                       
                                       
insert into stem_str_cpt_details values(21,28,'drought'),
                                       (22,28,'flooding'),
                                       (23,28,'tornadoes');
                                       
insert into stem_str_cpt_details values(24,34,'cell wall'),
                                       (25,34,'cell wall'),
                                       (26,34,'nucleus');

insert into stem_str_cpt_details values(27,35,'cell wall'),
                                       (28,35,'cell wall'),
                                       (29,35,'nucleus');
                                      
                                       
                                       
 
 CREATE TABLE `edulink1_lpriority`.`stem_concept_sub_category` (
  `stem_cpt_sub_categoryid` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_str_cpt_detailsid` BIGINT NOT NULL,
  `sub_category_desc` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`stem_cpt_sub_categoryid`),
  INDEX `fk_stem_str_cpt_detailsidss_idx` (`stem_str_cpt_detailsid` ASC),
  CONSTRAINT `fk_stem_str_cpt_detailsidss`
    FOREIGN KEY (`stem_str_cpt_detailsid`)
    REFERENCES `edulink1_lpriority`.`stem_str_cpt_details` (`stem_str_cpt_detailsid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
insert into stem_str_cpt_details values(30,38,'Animals'),
                                       (31,38,'Plants');

insert into stem_concept_sub_category values(1,30,'respiration – gills, lungs'),
                                            (2,30,'digestion – stomach, intestines'),
                                            (3,30,'circulation – heart, veins, arteries, capillaries'),
											(4,30,'locomotion – muscles, skeleton');
                                            

insert into stem_concept_sub_category values(5,31,'transpiration – stomata, roots, xylem, phloem'),
                                            (6,31,'absorption – roots, xylem, phloem'),
                                            (7,31,'response to stimulus (phototropism, hydrotropism, geotropism) – roots, xylem, phloem');
                                            
                                
insert into stem_str_cpt_details values(32,39,'respiratory and circulatory'),
                                       (33,39,'muscular and skeletal'),
									   (34,39,'digestive and excretory');
									   
insert into stem_str_cpt_details values(35,41,'water quality'),
                                       (36,41,'climate'),
									   (37,41,'population density'),
                                       (38,41,'smog');
                                       
                                       
-- -------------------------------------------------------------------------------------------------------------------------------------------------   
-- Added By Santosh 7/3/2014   
                                       
insert into stem_questions_type values(3,'Content Questions');

-- Added by Anusuya on 8/3/2017-------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`unit_stem_content_activities` (
  `unit_stem_content_activityid` BIGINT NOT NULL AUTO_INCREMENT,
  `unit_stem_cont_quesid` BIGINT NOT NULL,
  `activity_desc` LONGTEXT NULL DEFAULT NULL,
  `activity_link` LONGTEXT NULL DEFAULT NULL,
  `refer_activity_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`unit_stem_content_activityid`),
  INDEX `fk_unit_stem_cont_quesids_idx` (`unit_stem_cont_quesid` ASC),
  CONSTRAINT `fk_unit_stem_cont_quesids`
    FOREIGN KEY (`unit_stem_cont_quesid`)
    REFERENCES `edulink1_lpriority`.`unit_stem_content_quests` (`unit_stem_cont_quesid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


insert into stem_grade_strands values(7,6,3,'Empowered Learner','Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences. Students:'),
                                     (8,6,3,'Digital Citizen',' Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical. Students:'),
                                     (9,6,3,'Knowledge Constructor ','Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. Students:'),
                                     (10,6,3,'Innovative Designer','Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions. Students: '),
                                     (11,6,3,'Computational Thinker ','Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. Students: '), 
									 (12,6,3,'Creative Communicator ','Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals. Students:'),
                                     (13,6,3,'Global Collaborator ','Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally. Students: ');
    
insert into stem_strand_concepts values(57,7,'1a.','articulates and set personal learning goals, develop strategies leveraging technology to achieve them and reflect on the learning process itself to improve learning outcomes. '),
									   (58,7,'1b.','build networks and customize their learning environments in ways that support the learning process.'),
                                       (59,7,'1c.','use technology to seek feedback that informs and improves their practice and to demonstrate their learning in a variety of ways. '),
                                       (60,7,'1d.','understands the fundamental concepts of technology operations, demonstrate the ability to choose, use and troubleshoot current technologies and are able to transfer their knowledge to explore emerging technologies.');
                                  
insert into stem_strand_concepts values(61,8,'2a.','cultivate and manage their digital identity and reputation and are aware of the permanence of their actions in the digital world. '),
									   (62,8,'2b.','engage in positive, safe, legal and ethical behavior when using technology, including social interactions online or when using networked devices.'),
                                       (63,8,'2c.','demonstrate an understanding of and respect for the rights and obligations of using and sharing intellectual property. '),
                                       (64,8,'2d.','manage their personal data to maintain digital privacy and security and are aware of data-collection technology used to track their navigation online');
                                       
                                       
insert into stem_strand_concepts values(65,9,'3a.','plan and employ effective research strategies to locate information and other resources for their intellectual or creative pursuits.'),
									   (66,9,'3b.','evaluate the accuracy, perspective, credibility and relevance of information, media, data or other resources. '),
                                       (67,9,'3c.','curate information from digital resources using a variety of tools and methods to create collections of artifacts that demonstrate meaningful connections or conclusions. '),
                                       (68,9,'3d.','build knowledge by actively exploring real-world issues and problems, developing ideas and theories and pursuing answers and solutions.');
                                       
    
 insert into stem_strand_concepts values(69,10,'4a.','know and use a deliberate design process for generating ideas, testing theories, creating innovative artifacts or solving authentic problems. '),
									   (70,10,'4b.','select and use digital tools to plan and manage a design process that considers design constraints and calculated risks. '),
                                       (71,10,'4c.','develop, test and refine prototypes as part of a cyclical design process. '),
                                       (72,10,'4d.','exhibit a tolerance for ambiguity, perseverance and the capacity to work with open-ended problems.');
                                       
                            
insert into stem_strand_concepts values(73,11,'5a.','formulate problem definitions suited for technology assisted methods such as data analysis, abstract models and algorithmic thinking in exploring and finding solutions. '),
									   (74,11,'5b.','collect data or identify relevant data sets, use digital tools to analyze them, and represent data in various ways to facilitate problem-solving and decision-making. '),
                                       (75,11,'5c.','break problems into component parts, extract key information, and develop descriptive models to understand complex systems or facilitate problem-solving. '),
                                       (76,11,'5d.','understand how automation works and use algorithmic thinking to develop a sequence of steps to create and test automated solutions.');
                         
 insert into stem_strand_concepts values(77,12,'6a.','choose the appropriate platforms and tools for meeting the desired objectives of their creation or communication. '),
									   (78,12,'6b.','create original works or responsibly repurpose or remix digital resources into new creations. '),
                                       (79,12,'6c.','communicate complex ideas clearly and effectively by creating or using a variety of digital objects such as visualizations, models or simulations. '),
                                       (80,12,'6d.','publish or present content that customizes the message and medium for their intended audiences.');
                                       
                                       
insert into stem_strand_concepts values(81,13,'7a.','use digital tools to connect with learners from a variety of backgrounds and cultures, engaging with them in ways that broaden mutual understanding and learning. '),
									   (82,13,'7b.','use collaborative technologies to work with others, including peers, experts or community members, to examine issues and problems from multiple viewpoints.'),
                                       (83,13,'7c.','contribute constructively to project teams, assuming various roles and responsibilities to work effectively toward a common goal. '),
                                       (84,13,'7d.','explore local and global issues and use collaborative technologies to work with others to investigate solutions.');
                                       

insert into stem_str_cpt_details values(44,52,'atmosphere'),(45,52,'lithosphere'),(46,52,'hydrosphere');

insert into stem_str_cpt_details values(47,54,'ocean currents'),(48,54,'elevation'),(49,54,'location');

insert into stem_str_cpt_details values(50,56,'the Sun'),(51,56,'the atmosphere'),(52,56,'bodies of water');

-- Added By Anusuya on 10/3/2017 -----------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`stem_unit` 
ADD COLUMN `is_shared` VARCHAR(45) NULL DEFAULT NULL AFTER `stem_notes`;

-- ----------------------------------------------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 3/10/2017

CREATE TABLE `stem_performance_indicator` (
  `performance_indicator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `master_grade_id` bigint(20) NOT NULL,
  `legend_id` bigint(20) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`performance_indicator_id`),
  KEY `fk_stem_mast_grade_id_idx` (`master_grade_id`),
  KEY `fk_performance_area_id_idx` (`legend_id`),
  CONSTRAINT `fk_performance_area_id` FOREIGN KEY (`legend_id`) REFERENCES `legend` (`legend_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_mast_grade_id` FOREIGN KEY (`master_grade_id`) REFERENCES `master_grades` (`master_grades_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `stem_unit_performance_indicator` (
  `stem_unit_performance_indicator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `performance_indicator_id` bigint(20) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`stem_unit_performance_indicator_id`),
  KEY `fk_stem_perf_ind_id_idx` (`performance_indicator_id`),
  CONSTRAINT `fk_stem_perf_ind_id` FOREIGN KEY (`performance_indicator_id`) REFERENCES `stem_performance_indicator` (`performance_indicator_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Added By Anusuya on 13/3/2017--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`stem_strategies` (
  `stem_strategies_id` BIGINT NOT NULL AUTO_INCREMENT,
  `strategies_desc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`stem_strategies_id`));

CREATE TABLE `edulink1_lpriority`.`unit_stem_strategies` (
  `unit_stem_strategies_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stem_unit_id` BIGINT NOT NULL,
  `stem_strategies_id` BIGINT NOT NULL,
  PRIMARY KEY (`unit_stem_strategies_id`),
  INDEX `fk_stem_unit_idss_idx` (`stem_unit_id` ASC),
  INDEX `fk_stem_strategies_ids_idx` (`stem_strategies_id` ASC),
  CONSTRAINT `fk_stem_unit_ids1`
    FOREIGN KEY (`stem_unit_id`)
    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_strategies_ids`
    FOREIGN KEY (`stem_strategies_id`)
    REFERENCES `edulink1_lpriority`.`stem_strategies` (`stem_strategies_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  ALTER TABLE `edulink1_lpriority`.`unit_stem_content_activities` 
  ADD COLUMN `file_name` VARCHAR(45) NULL DEFAULT NULL AFTER `refer_activity_id`;
  
  -- Added by Lalitha on 3/13/2017
  
  ALTER TABLE `edulink1_lpriority`.`stem_unit_performance_indicator` 
	ADD COLUMN `stem_unit_id` BIGINT(20) NOT NULL AFTER `status`,
	ADD INDEX `fk_stem_unit_id1_idx` (`stem_unit_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`stem_unit_performance_indicator` 
	ADD CONSTRAINT `fk_stem_unit_id1`
	  FOREIGN KEY (`stem_unit_id`)
	  REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

-- Added By Anusuya on 14/3/1017 ------------------------------------------------------------------------------

	  insert into stem_strategies values(1,'Learners grouped/paired for specific purposes','yes'),
                                  (2,'Targeted teacher support for particular groups/individuals','yes'),
								  (3,'Extension tasks: opportunity for more complex tasks/thinking','yes'),
								  (4,'Learners have opportunity to ask questions/pursue misconceptions','yes'),
								  (5,'Targeted/different levels of teacher questioning','yes'), 
								  (6,'Extension tasks: advancing learning intention or next grade level target','yes'), 
                                  (7,'Adult support','yes'), 
                                  (8,'Modified tasks','yes'), 
                                  (9,'Frequent opportunity for purposeful talk','yes'),    
                                  (10,'Resources adapted','yes'),  
                                  (11,'Writing frame/question stems provided','yes'),
                                  (12,'Varied learning styles incorporated','yes'),
								  (13,'Speaking frame/question stems provided','yes'),
                                  (14,'Learners have personal targets','yes');
                                  
-- Added by Santosh on 15/3/2017

  ALTER TABLE `edulink1_lpriority`.`stem_unit_performance_indicator` 
	DROP FOREIGN KEY `fk_stem_perf_ind_id`;
	
	ALTER TABLE `edulink1_lpriority`.`stem_unit_performance_indicator` 
	DROP COLUMN `performance_indicator_id`,
	DROP INDEX `fk_stem_perf_ind_id_idx` ;

	ALTER TABLE `edulink1_lpriority`.`stem_unit_performance_indicator` ADD COLUMN `legend_sub_criteria_id` BIGINT(20) NOT NULL  AFTER `stem_unit_id` , 
	ADD CONSTRAINT `fk_legend_sub_criteria_id1`
  	FOREIGN KEY (`legend_sub_criteria_id` )
  	REFERENCES `edulink1_lpriority`.`legend_sub_criteria` (`legend_sub_criteria_id` )
  	ON DELETE NO ACTION
  	ON UPDATE NO ACTION
	, ADD INDEX `fk_legend_sub_criteria_id1` (`legend_sub_criteria_id` ASC) ;    
                              
-- Added By Anusuya on 16/3/2017------------------------------------------------------------------------------------------------------------------
  
CREATE TABLE `edulink1_lpriority`.`assign_stem_unit` (
  `assign_stem_id` BIGINT NOT NULL AUTO_INCREMENT,
  `cs_id` BIGINT NOT NULL,
  `stem_unit_id` BIGINT NOT NULL,
  `assigned_date` DATE NULL,
  `due_date` DATE NULL,
  PRIMARY KEY (`assign_stem_id`),
  INDEX `fk_stem_cs_ids_idx` (`cs_id` ASC),
  INDEX `fk_stem_unit_ids2_idx` (`stem_unit_id` ASC),
  CONSTRAINT `fk_stem_cs_ids`
    FOREIGN KEY (`cs_id`)
    REFERENCES `edulink1_lpriority`.`class_status` (`cs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_unit_ids2`
    FOREIGN KEY (`stem_unit_id`)
    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	
--  Added By Christopher on 17/3/2017------------------------------------------------------------------------------------------------------------------------

    ALTER TABLE `edulink1_lpriority`.`stem_unit` ADD COLUMN `src_stem_unit_id` BIGINT(20) ZEROFILL NULL AFTER `is_shared`;
    ALTER TABLE `edulink1_lpriority`.`stem_unit` ADD COLUMN `url_links` LONGTEXT NULL AFTER `src_stem_unit_id`;

-- --------------------------------------------------------------------------------------------------------------------------------------------------

    
    -- Added by Lalitha on 3/20/2016
      
CREATE TABLE `edulink1_lpriority`.`formative_assessment_main_cate` (
  `formative_assessment_main_cate_id` BIGINT(20) NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`formative_assessment_main_cate_id`));
ALTER TABLE `edulink1_lpriority`.`formative_assessment_main_cate` 
CHANGE COLUMN `formative_assessment_main_cate_id` `formative_assessment_main_cate_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;


CREATE TABLE `edulink1_lpriority`.`formative_assessment_sub_cate` (
  `formative_assessment_sub_cate_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `formative_assessment_main_cate_id` BIGINT(20) NOT NULL,
  `sub_category` VARCHAR(45) NULL,
  PRIMARY KEY (`formative_assessment_sub_cate_id`),
  INDEX `fk_formative_assessment_main_cate_id_idx` (`formative_assessment_main_cate_id` ASC),
  CONSTRAINT `fk_formative_assessment_main_cate_id`
    FOREIGN KEY (`formative_assessment_main_cate_id`)
    REFERENCES `edulink1_lpriority`.`formative_assessment_main_cate` (`formative_assessment_main_cate_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	CREATE TABLE `edulink1_lpriority`.`formative_assessment_rubric` (
  `formative_assessment_rubric_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `formative_assessment_sub_cate_id` BIGINT(20) NOT NULL,
  `score` INT NOT NULL,
  `description` LONGTEXT NOT NULL,
  PRIMARY KEY (`formative_assessment_rubric_id`));

  ALTER TABLE `edulink1_lpriority`.`formative_assessment_rubric` 
ADD INDEX `fk_formative_assessment_sub_cate_id_idx` (`formative_assessment_sub_cate_id` ASC);
ALTER TABLE `edulink1_lpriority`.`formative_assessment_rubric` 
ADD CONSTRAINT `fk_formative_assessment_sub_cate_id`
  FOREIGN KEY (`formative_assessment_sub_cate_id`)
  REFERENCES `edulink1_lpriority`.`formative_assessment_sub_cate` (`formative_assessment_sub_cate_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  CREATE TABLE `edulink1_lpriority`.`formative_assessments` (
  `formative_assessments_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `instructions` VARCHAR(45) NOT NULL,
  `assessment_type` BIGINT(20) NOT NULL,
  PRIMARY KEY (`formative_assessments_id`),
  INDEX `fk_assignment_type_fa_idx` (`assessment_type` ASC),
  CONSTRAINT `fk_assignment_type_fa`
    FOREIGN KEY (`assessment_type`)
    REFERENCES `edulink1_lpriority`.`assignment_type` (`assignment_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	
	CREATE TABLE `edulink1_lpriority`.`formative_assessment_keywords` (
  `formative_assessment_keywords_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `formative_assessment_id` BIGINT(20) NOT NULL,
  `keyword` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`formative_assessment_keywords_id`),
  INDEX `fk_formative_assessment_id_idx` (`formative_assessment_id` ASC),
  CONSTRAINT `fk_formative_assessment_id`
    FOREIGN KEY (`formative_assessment_id`)
    REFERENCES `edulink1_lpriority`.`formative_assessments` (`formative_assessments_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	CREATE TABLE `edulink1_lpriority`.`formative_assessments_grade_wise` (
  `formative_assessments_grade_wise_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `master_grades_id` BIGINT(20) NOT NULL,
  `formative_assessment_id` BIGINT(20) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`formative_assessments_grade_wise_id`),
  INDEX `fk_fa_master_grades_id_idx` (`master_grades_id` ASC),
  INDEX `fk_fa_assessment_id_idx` (`formative_assessment_id` ASC),
  CONSTRAINT `fk_fa_master_grades_id`
    FOREIGN KEY (`master_grades_id`)
    REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fa_assessment_id`
    FOREIGN KEY (`formative_assessment_id`)
    REFERENCES `edulink1_lpriority`.`formative_assessments` (`formative_assessments_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	CREATE TABLE `edulink1_lpriority`.`formative_assessments_unit_wise` (
  `grade_formative_assessments_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `stem_unit_id` BIGINT(20) NOT NULL,
  `formative_assessment_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`grade_formative_assessments_id`),
  INDEX `fk_stem_unit_id2_idx` (`stem_unit_id` ASC),
  INDEX `fk_formative_assessment_id2_idx` (`formative_assessment_id` ASC),
  CONSTRAINT `fk_formative_assessment_id2`
    FOREIGN KEY (`formative_assessment_id`)
    REFERENCES `edulink1_lpriority`.`formative_assessments` (`formative_assessments_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_unit_id2`
    FOREIGN KEY (`stem_unit_id`)
    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	ALTER TABLE `edulink1_lpriority`.`formative_assessments_unit_wise` 
CHANGE COLUMN `grade_formative_assessments_id` `formative_assessments_unit_wise_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

-- Sample data for formative assessments types

INSERT INTO `edulink1_lpriority`.`assignment_type`
(`assignment_type_id`,
`assignment_type`,
`used_for`)
VALUES
('22','Videos','formative_assessments'),
('23','Self-management/Self-direction','formative_assessments'),
('24','Research','formative_assessments');

-- Added by Santosh on 20/03/2017

ALTER TABLE `edulink1_lpriority`.`formative_assessments_unit_wise` ADD COLUMN `status` VARCHAR(45) NOT NULL  AFTER `formative_assessment_id` ;

ALTER TABLE `edulink1_lpriority`.`formative_assessments` CHANGE COLUMN `description` `description` LONGTEXT NOT NULL  ;

ALTER TABLE `edulink1_lpriority`.`formative_assessments` CHANGE COLUMN `instructions` `instructions` LONGTEXT NOT NULL  ;

ALTER TABLE `edulink1_lpriority`.`formative_assessments` CHANGE COLUMN `title` `title` LONGTEXT NOT NULL  ;

-- Path1 tables 

CREATE  TABLE `edulink1_lpriority`.`ipal_resources` (
  `ipal_resources_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `master_grade_id` BIGINT(20) NOT NULL ,
  `resource_link` LONGTEXT NOT NULL ,
  PRIMARY KEY (`ipal_resources_id`) ,
  INDEX `fk_ipal_master_grade_id` (`master_grade_id` ASC) ,
  CONSTRAINT `fk_ipal_master_grade_id`
    FOREIGN KEY (`master_grade_id` )
    REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `edulink1_lpriority`.`ipal_resources` (`ipal_resources_id`, `master_grade_id`, `resource_link`) VALUES (1, 6, 'https://schoolweb.dysart.org/iPlan/PacingGuide.aspx?c=25');

ALTER TABLE `edulink1_lpriority`.`ipal_resources` ADD COLUMN `status` VARCHAR(45) NOT NULL DEFAULT 'active'  AFTER `resource_link` ;

-- Additional STEM Areas tables 

ALTER TABLE `edulink1_lpriority`.`stem_areas` ADD COLUMN `is_other_stem` VARCHAR(10) NOT NULL DEFAULT 'No'  AFTER `stem_area` , CHANGE COLUMN `stem_area` `stem_area` VARCHAR(45) NOT NULL  ;

INSERT INTO `edulink1_lpriority`.`stem_areas` (`stem_area_id`, `stem_area`, `is_other_stem`) VALUES (5, 'Language Arts', 'Yes');

INSERT INTO `edulink1_lpriority`.`stem_areas` (`stem_area_id`, `stem_area`, `is_other_stem`) VALUES (6, 'Social Studies', 'Yes');

INSERT INTO `edulink1_lpriority`.`stem_areas` (`stem_area_id`, `stem_area`, `is_other_stem`) VALUES (7, 'Arts', 'Yes');


-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Added by Lalitha on 3/30/2017

CREATE  TABLE `edulink1_lpriority`.`column_headers` (
  `column_headers_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `column_name` VARCHAR(200) NOT NULL ,
  `datatype` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`column_headers_id`) )
ENGINE = InnoDB;

INSERT INTO `edulink1_lpriority`.`column_headers` (`column_headers_id`, `column_name`, `datatype`) VALUES (1, 'Explanation', 'textarea');
INSERT INTO `edulink1_lpriority`.`column_headers` (`column_headers_id`, `column_name`, `datatype`) VALUES (2, 'Comments', 'textarea');
ALTER TABLE `edulink1_lpriority`.`column_headers` RENAME TO  `edulink1_lpriority`.`column_headers` ;

CREATE TABLE `formative_assessment_column_headers` (
  `formative_assessment_column_headers_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `formative_assessment_id` bigint(20) NOT NULL,
  `column_header_id` bigint(20) NOT NULL,
  PRIMARY KEY (`formative_assessment_column_headers_id`),
  KEY `fk_column_header_id` (`column_header_id`),
  KEY `fk_formative_asses_id` (`formative_assessment_id`),
  CONSTRAINT `fk_formative_asses_id` FOREIGN KEY (`formative_assessment_id`) REFERENCES `formative_assessments` (`formative_assessments_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_column_header_id` FOREIGN KEY (`column_header_id`) REFERENCES `column_headers` (`column_headers_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;

ALTER TABLE `edulink1_lpriority`.`formative_assessment_sub_cate` DROP FOREIGN KEY `fk_formative_assessment_main_cate_id` ;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_sub_cate` DROP COLUMN `formative_assessment_main_cate_id` 
, DROP INDEX `fk_formative_assessment_main_cate_id_idx` ;

ALTER TABLE `edulink1_lpriority`.`formative_assessment_sub_cate` RENAME TO  `edulink1_lpriority`.`formative_assessment_categories` ;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_categories` CHANGE COLUMN `sub_category` `sub_category` VARCHAR(45) NOT NULL  ;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_categories` CHANGE COLUMN `sub_category` `category` VARCHAR(45) NOT NULL  ;
drop table `edulink1_lpriority`.`formative_assessment_main_cate`;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_rubric` DROP FOREIGN KEY `fk_formative_assessment_sub_cate_id` ;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_rubric` 
DROP INDEX `fk_formative_assessment_sub_cate_id_idx` ;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_rubric` CHANGE COLUMN `formative_assessment_sub_cate_id` `formative_assessment_category_id` BIGINT(20) NOT NULL  ;
ALTER TABLE `edulink1_lpriority`.`formative_assessment_categories` CHANGE COLUMN `formative_assessment_sub_cate_id` `formative_assessment_category_id` BIGINT(20) NOT NULL AUTO_INCREMENT  ;

ALTER TABLE `edulink1_lpriority`.`formative_assessment_rubric` 
  ADD CONSTRAINT `fk_formative_assessment_category_id`
  FOREIGN KEY (`formative_assessment_category_id` )
  REFERENCES `edulink1_lpriority`.`formative_assessment_categories` (`formative_assessment_category_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_formative_assessment_category_id` (`formative_assessment_category_id` ASC) ;

CREATE  TABLE `edulink1_lpriority`.`formative_assessment_wise_categories` (
  `formative_assessment_wise_categories_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `formative_assessment_id` BIGINT(20) NOT NULL ,
  `format_assessment_category_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`formative_assessment_wise_categories_id`) ,
  INDEX `fk_formative_ass_id` (`formative_assessment_id` ASC) ,
  INDEX `fk_formative_ass_cate_id` (`format_assessment_category_id` ASC) ,
  CONSTRAINT `fk_formative_ass_id`
    FOREIGN KEY (`formative_assessment_id` )
    REFERENCES `edulink1_lpriority`.`formative_assessments` (`formative_assessments_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_formative_ass_cate_id`
    FOREIGN KEY (`format_assessment_category_id` )
    REFERENCES `edulink1_lpriority`.`formative_assessment_categories` (`formative_assessment_category_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `edulink1_lpriority`.`column_headers` CHANGE COLUMN `datatype` `data_type` VARCHAR(45) NOT NULL  ;

-- Added By Anusuya on 3/4/2017 ------------------------------------------------------------------------------

delete from column_headers;


insert into column_headers values(1,'Data Observed','textbox'),
                                 (2,'Proficiency;Consistent,Attempted,Developing','textarea'),
								 (3,'Comments','textarea'), 
								 (4,'Explanation','textarea'),    
                                 (5,'Always','checkbox'),
                                 (6,'Sometimes','checkbox'), 
                                 (7,'Rarely/Never','checkbox'),   
                                 (8,'Consistently','checkbox'),
								 (9,'Comment','textarea'),
                                 (10,'Proficient or Developing','textarea'),
                                 (11,'Always, Often, or Seldom','textarea'),
								 (12,'Example','textarea'),
                                 (13,'OK','checkbox'),
								 (14,'Date','textbox'),
                                 (15,'Comment/Specific Example','textarea'),
								 (16,'Hardly Ever','checkbox'),
								 (17,'Goal','textarea'),
                                 (18,'Date Completed','textbox'),
                                 (19,'Notes','textarea'),
								 (20,'Examples/Goals/Comments','textarea'),
                                 (21,'Evident','checkbox'),
                                 (22,'Developing','checkbox'),
								 (23,'Not Present','checkbox'), 
                                 (24,'Comments/Feedback','textarea');

-- Added by Lalitha on 4/3/2017

ALTER TABLE `edulink1_lpriority`.`formative_assessment_categories` ADD COLUMN `formative_assessment_id` BIGINT(20) NOT NULL  AFTER `category` , 
  ADD CONSTRAINT `fk_for_ass_id`
  FOREIGN KEY (`formative_assessment_id` )
  REFERENCES `edulink1_lpriority`.`formative_assessments` (`formative_assessments_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_for_ass_id` (`formative_assessment_id` ASC) ;

drop table `edulink1_lpriority`.`formative_assessment_wise_categories`;

-- Added By anusuya on 4/7/2017 ---------------------------------------------------------------------------------------------------------------

insert into assignment_type values(25,'Critical Thinking','2017-04-07',current_timestamp(),'formative_assessments');

update column_headers set column_name='Proficiency; Consistent, Attempted, Developing' where column_headers_id=2;

ALTER TABLE `edulink1_lpriority`.`formative_assessment_categories` 
CHANGE COLUMN `category` `category` LONGTEXT NOT NULL ;

insert into assignment_type values(26,'Electronic Publications','2017-04-07',current_timestamp,'formative_assessments');

update column_headers set column_name='Date Observed' where column_headers_id=1;

insert into assignment_type values(27,'Problem Solving','2017-04-10',current_timestamp,'formative_assessments');

insert into assignment_type values(28,'Writing','2017-04-10',current_timestamp,'formative_assessments');

-- Added By Anusuya on 4/11/2017 ---------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`formative_assessments` 
DROP FOREIGN KEY `fk_assignment_type_fa`;
ALTER TABLE `edulink1_lpriority`.`formative_assessments` 
CHANGE COLUMN `assessment_type` `assessment_type` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`formative_assessments` 
ADD CONSTRAINT `fk_assignment_type_fa`
  FOREIGN KEY (`assessment_type`)
  REFERENCES `edulink1_lpriority`.`assignment_type` (`assignment_type_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;   
  
  
insert into assignment_type values(29,'Dates Observed','2017-04-11',current_timestamp,'formative_assessments');

-- updated on the so far

-- Added by Santosh on 4/13/2017 ------

INSERT INTO `edulink1_lpriority`.`assignment_type` (`assignment_type`, `used_for`) VALUES ('Math Game', 'math_game');

-- Added By Anusuya on 19/4/2017------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`game_level` (
  `game_level_id` BIGINT NOT NULL AUTO_INCREMENT,
  `level` VARCHAR(45) NULL,
  PRIMARY KEY (`game_level_id`));
  
  CREATE TABLE `edulink1_lpriority`.`math_game_scores` (
  `math_game_score_id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_assignment_id` BIGINT NOT NULL,
  `game_level_id` BIGINT NOT NULL,
  `time_taken` VARCHAR(45) NULL,
  `no_of_attempts` BIGINT NULL,
  `no_of_corrects` BIGINT NULL,
  `no_of_incorrects` BIGINT NULL,
  PRIMARY KEY (`math_game_score_id`),
  INDEX `fk_game_student_assignment_id_idx` (`student_assignment_id` ASC),
  INDEX `fk_game_level_id_idx` (`game_level_id` ASC),
  CONSTRAINT `fk_game_student_assignment_id`
    FOREIGN KEY (`student_assignment_id`)
    REFERENCES `edulink1_lpriority`.`student_assignment_status` (`student_assignment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_level_id`
    FOREIGN KEY (`game_level_id`)
    REFERENCES `edulink1_lpriority`.`game_level` (`game_level_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- Added by Santosh on 19-04-2017------------------------

    INSERT INTO `edulink1_lpriority`.`game_level` (`level`) VALUES ('1');

	INSERT INTO `edulink1_lpriority`.`game_level` (`level`) VALUES ('2');
	
	INSERT INTO `edulink1_lpriority`.`game_level` (`level`) VALUES ('3');


    

-- --------------------------------------------------------------------------------------------------------------------------------------

	-- Added by Lalitha on 4/2/2017
	
	ALTER TABLE `edulink1_lpriority`.`math_game_scores` ADD COLUMN `status` VARCHAR(45) NOT NULL DEFAULT 'pending'  AFTER `no_of_incorrects` ;

-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Added by Christopher on 04/04/2017
		
ALTER TABLE `edulink1_lpriority`.`stem_grade_strands` 
ADD COLUMN `state_id` BIGINT(20) NULL AFTER `added_desc`;

update edulink1_lpriority.stem_grade_strands set state_id=3683;

ALTER TABLE `edulink1_lpriority`.`stem_grade_strands` 
CHANGE COLUMN `state_id` `state_id` BIGINT(20) NOT NULL ,
ADD INDEX `fk_stem_grade_state_ids_idx` (`state_id` ASC);
ALTER TABLE `edulink1_lpriority`.`stem_grade_strands` 
ADD CONSTRAINT `fk_stem_grade_state_ids`
  FOREIGN KEY (`state_id`)
  REFERENCES `edulink1_lpriority`.`states` (`state_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
-- -------------------------------------------------------------------------------------------------------------------------------------------
-- Added by Lalitha on 5/9/2017
  UPDATE `edulink1_lpriority`.`assignment_type` SET `assignment_type`='Gear Game' WHERE `assignment_type_id`='30';
  
  -- Added by Lalitha on 5/25/2017
update legend set status='inactive' where legend_id between 5 and 106;

-- Added by Lalitha on 5/29/2017

DELETE FROM `edulink1_lpriority`.`unit_stem_strands` WHERE `unit_stem_strands_id`='30';

ALTER TABLE `edulink1_lpriority`.`stem_unit` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`unit_stem_strands` ENGINE = InnoDB , 
  ADD CONSTRAINT `fk_stem_unit_id_uss`
  FOREIGN KEY (`stem_unit_id` )
  REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_stem_area_id_uss`
  FOREIGN KEY (`stem_area_id` )
  REFERENCES `edulink1_lpriority`.`stem_areas` (`stem_area_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_stem_grade_strnd_id_uss`
  FOREIGN KEY (`stem_grade_strand_id` )
  REFERENCES `edulink1_lpriority`.`stem_grade_strands` (`stem_grade_strand_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_stem_unit_id_uss` (`stem_unit_id` ASC) 
, ADD INDEX `fk_stem_area_id_uss` (`stem_area_id` ASC) 
, ADD INDEX `fk_stem_grade_strnd_id_uss` (`stem_grade_strand_id` ASC) ;

ALTER TABLE `edulink1_lpriority`.`unit_stem_concepts` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`unit_stem_concepts` 
  ADD CONSTRAINT `fk_unit_stem_strands_id_usc`
  FOREIGN KEY (`unit_stem_strands_id` )
  REFERENCES `edulink1_lpriority`.`unit_stem_strands` (`unit_stem_strands_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_unit_stem_strands_id_usc` (`unit_stem_strands_id` ASC) ;


ALTER TABLE `edulink1_lpriority`.`stem_paths` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`stem_questions` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`stem_questions_type` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`stem_questions` 
  ADD CONSTRAINT `fk_unit_id_sq`
  FOREIGN KEY (`stem_unit_id` )
  REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_stem_qu_type_id_sq`
  FOREIGN KEY (`stem_ques_typeid` )
  REFERENCES `edulink1_lpriority`.`stem_questions_type` (`stem_ques_typeid` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_unit_id_sq` (`stem_unit_id` ASC) 
, ADD INDEX `fk_stem_qu_type_id_sq` (`stem_ques_typeid` ASC) ;

ALTER TABLE `edulink1_lpriority`.`stem_strategies` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`stem_unit` CHANGE COLUMN `src_stem_unit_id` `src_stem_unit_id` BIGINT(20) NULL DEFAULT NULL  , 
  ADD CONSTRAINT `fk_grade_class_id_su`
  FOREIGN KEY (`grade_class_id` )
  REFERENCES `edulink1_lpriority`.`grade_classes` (`grade_class_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_stem_path_id_su`
  FOREIGN KEY (`stem_path_id` )
  REFERENCES `edulink1_lpriority`.`stem_paths` (`stem_path_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_created_by_su`
 FOREIGN KEY (`created_by` )
  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_grade_class_id_su` (`grade_class_id` ASC) 
, ADD INDEX `fk_stem_path_id_su` (`stem_path_id` ASC) 
, ADD INDEX `fk_created_by_su` (`created_by` ASC) ;

ALTER TABLE `edulink1_lpriority`.`stem_unit_performance_indicator` 
  ADD CONSTRAINT `fk_stem_unit_id`
  FOREIGN KEY (`stem_unit_id` )
  REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_stem_unit_id` (`stem_unit_id` ASC) ;


ALTER TABLE `edulink1_lpriority`.`unit_stem_concepts` 
  ADD CONSTRAINT `fk_stem_str_cpt_id_usc`
  FOREIGN KEY (`stem_str_cpt_id` )
  REFERENCES `edulink1_lpriority`.`stem_strand_concepts` (`stem_str_cpt_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_stem_str_cpt_id_usc` (`stem_str_cpt_id` ASC) ;

ALTER TABLE `edulink1_lpriority`.`unit_stem_content_activities` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`unit_stem_content_quests` ENGINE = InnoDB , 
  ADD CONSTRAINT `fk_unit_stem_strands_id_uscq`
  FOREIGN KEY (`unit_stem_strands_id` )
  REFERENCES `edulink1_lpriority`.`unit_stem_strands` (`unit_stem_strands_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_unit_stem_strands_id_uscq` (`unit_stem_strands_id` ASC) ;

ALTER TABLE `edulink1_lpriority`.`unit_stem_strategies` ENGINE = InnoDB , 
  ADD CONSTRAINT `fk_stem_unit_id_uss1`
  FOREIGN KEY (`stem_unit_id` )
  REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_stem_strategies_id_uss1`
  FOREIGN KEY (`stem_strategies_id` )
  REFERENCES `edulink1_lpriority`.`stem_strategies` (`stem_strategies_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_stem_unit_id_uss1` (`stem_unit_id` ASC) 
, ADD INDEX `fk_stem_strategies_id_uss1` (`stem_strategies_id` ASC) ;

-- updated so far on the server

-- Added by Lalitha on 5/30/2017	

CREATE TABLE `edulink1_lpriority`.`stem_unit_activity` (
  `stem_activity_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `stem_unit_id` BIGINT(20) NOT NULL,
  `stem_area_id` BIGINT(20) NOT NULL,
  `activity_desc` LONGTEXT NULL,
  `activity_link` LONGTEXT NULL,
  `refer_activity_id` BIGINT(20) NULL,
  `file_name` VARCHAR(45) NULL,
  PRIMARY KEY (`stem_activity_id`),
  INDEX `fk_stem_unit_id_sua_idx` (`stem_unit_id` ASC),
  INDEX `fk_stem_area_id_sua_idx` (`stem_area_id` ASC),
  CONSTRAINT `fk_stem_unit_id_sua`
    FOREIGN KEY (`stem_unit_id`)
    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stem_area_id_sua`
    FOREIGN KEY (`stem_area_id`)
    REFERENCES `edulink1_lpriority`.`stem_areas` (`stem_area_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
	DELETE FROM edulink1_lpriority.unit_stem_content_activities where unit_stem_cont_quesid not in (Select unit_stem_cont_quesid from unit_stem_content_quests);
	
	INSERT INTO stem_unit_activity 
	SELECT a.unit_stem_content_activityid,c.stem_unit_id, c.stem_area_id, a.activity_desc,a.activity_link,a.refer_activity_id,a.file_name FROM edulink1_lpriority.unit_stem_content_activities a,unit_stem_content_quests b, unit_stem_strands c 
	where a.unit_stem_cont_quesid= b.unit_stem_cont_quesid and b.unit_stem_strands_id=c.unit_stem_strands_id ;

	
	-- Added by Lalitha on 6/1/2017
	
	CREATE TABLE `edulink1_lpriority`.`stem_concept_sub_category_items` (
	  `stem_concept_sub_category_item_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	  `stem_cpt_sub_categoryid` BIGINT(20) NOT NULL,
	  `item_desc` LONGTEXT NULL,
	  PRIMARY KEY (`stem_concept_sub_category_item_id`),
	  INDEX `fk_stem_cpt_sub_categoryid_scsci_idx` (`stem_cpt_sub_categoryid` ASC),
	  CONSTRAINT `fk_stem_cpt_sub_categoryid_scsci`
	    FOREIGN KEY (`stem_cpt_sub_categoryid`)
	    REFERENCES `edulink1_lpriority`.`stem_concept_sub_category` (`stem_cpt_sub_categoryid`)
	    ON DELETE NO ACTION
	    ON UPDATE NO ACTION);
	    
-- Added By Anusuya on 6/5/2017 --

	    CREATE TABLE `edulink1_lpriority`.`assign_legend_rubrics` (
  `assign_legend_rubricId` BIGINT NOT NULL AUTO_INCREMENT,
  `grade_id` BIGINT NOT NULL,
  `refer_reg_id` BIGINT NULL DEFAULT NULL,
  `created_by` BIGINT NOT NULL,
  PRIMARY KEY (`assign_legend_rubricId`),
  INDEX `fk_le_grade_ids_idx` (`grade_id` ASC),
  INDEX `fk_refer_reg_id_idx` (`refer_reg_id` ASC),
  INDEX `fk_created_reg_id_idx` (`created_by` ASC),
  CONSTRAINT `fk_le_grade_ids`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_refer_reg_id`
    FOREIGN KEY (`refer_reg_id`)
    REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_created_reg_id`
    FOREIGN KEY (`created_by`)
    REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    ALTER TABLE `edulink1_lpriority`.`assign_legend_rubrics` 
ADD COLUMN `trimester_id` BIGINT NOT NULL AFTER `created_by`,
ADD INDEX `fk_trimester_idss_idx` (`trimester_id` ASC);
ALTER TABLE `edulink1_lpriority`.`assign_legend_rubrics` 
ADD CONSTRAINT `fk_trimester_idss`
  FOREIGN KEY (`trimester_id`)
  REFERENCES `edulink1_lpriority`.`trimester` (`trimester_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  -- Added by Lalitha on 6/5/2017
	    
	    CREATE TABLE `edulink1_lpriority`.`unit_stem_areas` (
		  `unit_stem_areas_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
		  `stem_unit_id` BIGINT(20) NOT NULL,
		  PRIMARY KEY (`unit_stem_areas_id`),
		  INDEX `fk_stem_unit_id_idx` (`stem_unit_id` ASC),
		  CONSTRAINT `fk_stem_unit_id_usa`
		    FOREIGN KEY (`stem_unit_id`)
		    REFERENCES `edulink1_lpriority`.`stem_unit` (`stem_unit_id`)
		    ON DELETE NO ACTION
		    ON UPDATE NO ACTION)
		ENGINE = InnoDB
		DEFAULT CHARACTER SET = latin1;
		
		ALTER TABLE `edulink1_lpriority`.`unit_stem_areas` 
			ADD COLUMN `stem_area_id` BIGINT(20) NULL AFTER `stem_unit_id`,
			ADD INDEX `fk_stem_area_id_usa_idx` (`stem_area_id` ASC);
			ALTER TABLE `edulink1_lpriority`.`unit_stem_areas` 
			ADD CONSTRAINT `fk_stem_area_id_usa`
			  FOREIGN KEY (`stem_area_id`)
			  REFERENCES `edulink1_lpriority`.`stem_areas` (`stem_area_id`)
			  ON DELETE NO ACTION
			  ON UPDATE NO ACTION;

	  ALTER TABLE `edulink1_lpriority`.`unit_stem_strands` 
		ADD COLUMN `unit_stem_areas_id` BIGINT(20) NULL AFTER `narrative`,
		ADD INDEX `fk_unit_stem_areas_id_idx` (`unit_stem_areas_id` ASC);
		ALTER TABLE `edulink1_lpriority`.`unit_stem_strands` 
		ADD CONSTRAINT `fk_unit_stem_areas_id`
		  FOREIGN KEY (`unit_stem_areas_id`)
		  REFERENCES `edulink1_lpriority`.`unit_stem_areas` (`unit_stem_areas_id`)
		  ON DELETE NO ACTION
		  ON UPDATE NO ACTION;
		  
		  ALTER TABLE `edulink1_lpriority`.`unit_stem_areas` 
			ADD UNIQUE INDEX `unique_key` (`stem_unit_id` ASC, `stem_area_id` ASC);

		  
		  INSERT INTO unit_stem_areas(stem_unit_id,stem_area_id) SELECT distinct stem_unit_id,stem_area_id FROM edulink1_lpriority.unit_stem_strands;
		  
		  SELECT unit_stem_areas_id,unit_stem_strands_id FROM edulink1_lpriority.unit_stem_strands uss, unit_stem_areas usa where uss.stem_unit_id = usa.stem_unit_id and 
			uss.stem_area_id = usa.stem_area_id ;

			-- Example to run
			-- update unit_stem_strands set unit_stem_areas_id=1 where unit_stem_strands_id=5;
			
		ALTER TABLE `edulink1_lpriority`.`unit_stem_content_quests` 
			ADD COLUMN `unit_stem_area_id` BIGINT(20) NULL AFTER `content_question`,
			ADD INDEX `fk_unit_stem_area_id_idx` (`unit_stem_area_id` ASC);
			ALTER TABLE `edulink1_lpriority`.`unit_stem_content_quests` 
			ADD CONSTRAINT `fk_unit_stem_area_id`
			  FOREIGN KEY (`unit_stem_area_id`)
			  REFERENCES `edulink1_lpriority`.`unit_stem_areas` (`unit_stem_areas_id`)
			  ON DELETE NO ACTION
			  ON UPDATE NO ACTION;
			  
			  -- SELECT usq.*, uss.unit_stem_areas_id FROM edulink1_lpriority.unit_stem_content_quests usq, unit_stem_strands uss where 
				-- usq.unit_stem_strands_id = uss.unit_stem_strands_id  ;
				--update unit_stem_content_quests set unit_stem_area_id=1 where unit_stem_cont_quesid=25;
				
			  
		 ALTER TABLE `edulink1_lpriority`.`stem_unit_activity` 
			ADD COLUMN `unit_stem_area_id` BIGINT(20) NULL AFTER `file_name`,
			ADD INDEX `fk_unit_stem_area_id_sua_idx` (`unit_stem_area_id` ASC);
			ALTER TABLE `edulink1_lpriority`.`stem_unit_activity` 
			ADD CONSTRAINT `fk_unit_stem_area_id_sua`
			  FOREIGN KEY (`unit_stem_area_id`)
			  REFERENCES `edulink1_lpriority`.`unit_stem_areas` (`unit_stem_areas_id`)
			  ON DELETE NO ACTION
			  ON UPDATE NO ACTION;
			  
  -- Added by Lalitha on 6/12/2017
			  
	  ALTER TABLE `edulink1_lpriority`.`stem_unit_activity` 
		DROP FOREIGN KEY `fk_stem_unit_id_sua`,
		DROP FOREIGN KEY `fk_stem_area_id_sua`;
		ALTER TABLE `edulink1_lpriority`.`stem_unit_activity` 
		DROP COLUMN `stem_area_id`,
		DROP COLUMN `stem_unit_id`,
		DROP INDEX `fk_stem_area_id_sua_idx` ,
		DROP INDEX `fk_stem_unit_id_sua_idx` ;

	DROP TABLE `edulink1_lpriority`.`unit_stem_content_activities`;
	ALTER TABLE `edulink1_lpriority`.`unit_stem_content_quests` 
		DROP FOREIGN KEY `fk_unit_stem_strands_id_uscq`;
		ALTER TABLE `edulink1_lpriority`.`unit_stem_content_quests` 
		DROP COLUMN `unit_stem_strands_id`,
		DROP INDEX `fk_unit_stem_strands_idss_idx` ;

	ALTER TABLE `edulink1_lpriority`.`unit_stem_strands` 
		DROP FOREIGN KEY `fk_stem_unit_id_uss`,
		DROP FOREIGN KEY `fk_stem_area_id_uss`;
		ALTER TABLE `edulink1_lpriority`.`unit_stem_strands` 
		DROP COLUMN `stem_area_id`,
		DROP COLUMN `stem_unit_id`,
		DROP INDEX `fk_stem_area_id_uss` ,
		DROP INDEX `fk_stem_unit_id_uss` ,
		DROP INDEX `fk_stem_areas_idss_idx` ,
		DROP INDEX `fk_stem_unit_idss_idx` ;
		
		-- Added by Lalitha on 6/13/2017
		
		ALTER TABLE `edulink1_lpriority`.`stem_grade_strands` 
CHANGE COLUMN `stem_strand_title` `stem_strand_title` LONGTEXT NULL DEFAULT NULL ;

-- Added by Lalitha on 6/14/2017
ALTER TABLE `edulink1_lpriority`.`stem_grade_strands` 
CHANGE COLUMN `stem_strand_title` `stem_strand_title` LONGTEXT NOT NULL ;

-- ------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------

-- updated so far in the server

-- Added by Santosh on 6/23/2017	

ALTER TABLE `edulink1_lpriority`.`legend` 
ADD COLUMN `district_id` BIGINT(20) NULL AFTER `status`,
ADD INDEX `fk_school_district_id_idx` (`district_id` ASC);
ALTER TABLE `edulink1_lpriority`.`legend` 
ADD CONSTRAINT `fk_school_district_id`
 FOREIGN KEY (`district_id`)
 REFERENCES `edulink1_lpriority`.`district` (`district_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;
 
 update edulink1_lpriority.legend  set district_id=2 where isdefault='stem';
 
 
 -- Added By Anusuya on 29/6/2017
 
 CREATE TABLE `edulink1_lpriority`.`goal_setting_types` (
  `goal_type_id` BIGINT NOT NULL AUTO_INCREMENT,
  `goal_type_desc` VARCHAR(45) NULL,
  PRIMARY KEY (`goal_type_id`));
  
  
 CREATE TABLE `edulink1_lpriority`.`student_goal_scores` (
  `student-goal_is` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT NOT NULL,
  `goal_type_id` BIGINT NOT NULL,
  `previous_year_score` BIGINT NULL,
  PRIMARY KEY (`student-goal_is`),
  INDEX `fk_student_id_goal_idx` (`student_id` ASC),
  INDEX `fk_goal_type_ids_idx` (`goal_type_id` ASC),
  CONSTRAINT `fk_student_id_goal`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_goal_type_ids`
    FOREIGN KEY (`goal_type_id`)
    REFERENCES `edulink1_lpriority`.`goal_setting_types` (`goal_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    CREATE TABLE `edulink1_lpriority`.`goal_strategies` (
  `goal_strategies_id` BIGINT NOT NULL AUTO_INCREMENT,
  `goal_type_id` BIGINT NOT NULL,
  `goal_strategies_desc` VARCHAR(45) NULL,
  PRIMARY KEY (`goal_strategies_id`),
  INDEX `fk_goal_type_idss_idx` (`goal_type_id` ASC),
  CONSTRAINT `fk_goal_type_idss`
    FOREIGN KEY (`goal_type_id`)
    REFERENCES `edulink1_lpriority`.`goal_setting_types` (`goal_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    ALTER TABLE `edulink1_lpriority`.`student_goal_scores` 
CHANGE COLUMN `student-goal_is` `student_goal_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

-- Added by Lalitha on 6/30/2017
CREATE TABLE `edulink1_lpriority`.`academic_year` (
 `academic_year_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
 `academic_year` VARCHAR(45) NOT NULL,
 `start_date` DATE NOT NULL,
 `end_date` DATE NOT NULL,
 PRIMARY KEY (`academic_year_id`));
ALTER TABLE `edulink1_lpriority`.`academic_year` 
ADD COLUMN `is_curren_year` VARCHAR(5) NOT NULL DEFAULT 'NO' AFTER `end_date`;

-- Added by Lalitha on 6/30/2017

INSERT INTO `edulink1_lpriority`.`academic_year` (`academic_year`, `start_date`, `end_date`, `is_curren_year`) VALUES ('2016-17', '2016-07-31', '2017-08-01', 'NO');

INSERT INTO `edulink1_lpriority`.`academic_year` (`academic_year`, `start_date`, `end_date`, `is_curren_year`) VALUES ('2017-18', '2017-07-31', '2018-08-01', 'YES');


-- Added By Anusuya on 5/7/2017 --------------------------------------------------------------------------------------------

insert into caaspp_types values(3,'Star Reading');

ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
ADD COLUMN `boy_score` FLOAT NULL DEFAULT NULL AFTER `class_id`,
ADD COLUMN `tri1_score` FLOAT NULL DEFAULT NULL AFTER `boy_score`,
ADD COLUMN `tri2_score` FLOAT NULL DEFAULT NULL AFTER `tri1_score`,
ADD COLUMN `eoy_score` FLOAT NULL DEFAULT NULL AFTER `tri2_score`;


drop table student_goal_scores;
drop table goal_setting_types
drop table goal_strategies

ALTER TABLE `edulink1_lpriority`.`caaspp_types` ENGINE = InnoDB ;

CREATE TABLE `edulink1_lpriority`.`goal_strategies` (
 `goal_strategies_id` BIGINT NOT NULL AUTO_INCREMENT,
 `caaspp_types_id` BIGINT NOT NULL,
 `goal_strategies_desc` LONGTEXT NULL DEFAULT NULL,
 PRIMARY KEY (`goal_strategies_id`),
 INDEX `fks_caaspp_types_idsss_idx` (`caaspp_types_id` ASC),
 CONSTRAINT `fks_caaspp_types_idsss`
   FOREIGN KEY (`caaspp_types_id`)
   REFERENCES `edulink1_lpriority`.`caaspp_types` (`caaspp_types_id`)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION);


CREATE TABLE `edulink1_lpriority`.`student_goal_stategies` (
  `stud_goal_strategies_id` BIGINT NOT NULL AUTO_INCREMENT,
  `caaspp_scores_id` BIGINT NOT NULL,
  `goal_strategies_id` BIGINT NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`stud_goal_strategies_id`),
  INDEX `fks_caaspp_scores_id_idx` (`caaspp_scores_id` ASC),
  INDEX `fks_goal_strategies_ids_idx` (`goal_strategies_id` ASC),
  CONSTRAINT `fks_caaspp_scores_id`
    FOREIGN KEY (`caaspp_scores_id`)
    REFERENCES `edulink1_lpriority`.`caaspp_scores` (`caaspp_scores_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fks_goal_strategies_ids`
    FOREIGN KEY (`goal_strategies_id`)
    REFERENCES `edulink1_lpriority`.`goal_strategies` (`goal_strategies_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	ALTER TABLE `edulink1_lpriority`.`student_goal_stategies` 
CHANGE COLUMN `status` `stud_own_strategies_desc` LONGTEXT NULL DEFAULT NULL ;

-- Added by Lalitha on 5/7/2017

ALTER TABLE `edulink1_lpriority`.`announcements` ADD COLUMN `created_for` BIGINT(20) NULL  AFTER `change_date` , 
  ADD CONSTRAINT `fk_user_type_id_anonce`
  FOREIGN KEY (`created_for` )
  REFERENCES `edulink1_lpriority`.`user` (`user_typeid` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  ADD INDEX `fk_user_type_id_anonce` (`created_for` ASC) ;


-- Added by Lalitha on 6/7/2017

  ALTER TABLE `edulink1_lpriority`.`announcements` ADD COLUMN `file_name` VARCHAR(300) NULL  AFTER `created_for` ;
  
-- Added By Anusuya on 6/7/2017 ------------------------------------------------------------------------------------------ 

insert into goal_strategies values(1,1,'Meet grade level AR goal'),
								  (2,1,'Read out loud to someone every day for 30 minutes'),
                                  (3,1,'Ask for help if something doesn’t make sense'),
								  (4,1,'Stay on task during class'),
								  (5,1,'Check out and READ as many books as possible'),
                                  (6,1,'Ready every day for at least 30 minutes, including weekends'),
								  (7,1,'After every two pages you read, ask yourself, “Did I understand what I read?”'),
								  (8,1,'Read chapter books');


	    
insert into goal_strategies values(9,2,'Complete all of my math work'),
								  (10,2,'Ask for help if I don’t understand something'),
                                  (11,2,'Use online resources like Khan Academy, Sokikom or Sumdog'),
								  (12,2,'Stay on task, especially during math'),
								  (13,2,'Practice math facts at home using flash cards'),
                                  (14,2,'Watch tutorials on mathquickclips.com'),
								  (15,2,'Ask my teacher for help or even extra practice that can be done at home');


	    
ALTER TABLE `edulink1_lpriority`.`student_goal_stategies` 
ADD COLUMN `goal_count` BIGINT NULL AFTER `stud_own_strategies_desc`;

-- Added By Anusuya on 13/7/2017 ----------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
DROP COLUMN `eoy_score`,
DROP COLUMN `tri2_score`,
DROP COLUMN `tri1_score`,
DROP COLUMN `boy_score`;

CREATE TABLE `edulink1_lpriority`.`star_scores` (
  `star_scores_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT(20) NOT NULL,
  `grade_id` BIGINT(20) NOT NULL,
  `trimester_id` BIGINT(20) NOT NULL,
  `teacher_id` BIGINT(20) NULL,
  `score` FLOAT NOT NULL,
  PRIMARY KEY (`star_scores_id`),
  INDEX `fk_student_id_ss_idx` (`student_id` ASC),
  INDEX `fk_teacher_id_ss_idx` (`teacher_id` ASC),
  INDEX `fk_trimester_id_ss_idx` (`trimester_id` ASC),
  INDEX `fk_grade_id_ss_idx` (`grade_id` ASC),
  CONSTRAINT `fk_student_id_ss`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_id_ss`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `edulink1_lpriority`.`teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trimester_id_ss`
    FOREIGN KEY (`trimester_id`)
    REFERENCES `edulink1_lpriority`.`trimester` (`trimester_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_id_ss`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


ALTER TABLE `edulink1_lpriority`.`star_scores` 
ADD COLUMN `caaspp_types_id` BIGINT(20) NULL AFTER `score`,
ADD INDEX `fk_caaspp_types_id_ss_idx` (`caaspp_types_id` ASC);
ALTER TABLE `edulink1_lpriority`.`star_scores` 
ADD CONSTRAINT `fk_caaspp_types_id_ss`
  FOREIGN KEY (`caaspp_types_id`)
  REFERENCES `edulink1_lpriority`.`caaspp_types` (`caaspp_types_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  ALTER TABLE `edulink1_lpriority`.`student_goal_stategies` 
DROP FOREIGN KEY `fks_goal_strategies_ids`;
ALTER TABLE `edulink1_lpriority`.`student_goal_stategies` 
DROP COLUMN `goal_count`,
DROP COLUMN `goal_strategies_id`,
CHANGE COLUMN `stud_goal_strategies_id` `stud_own_strategies_id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
DROP INDEX `fks_goal_strategies_ids_idx` , RENAME TO  `edulink1_lpriority`.`stud_caaspp_own_stategies` ;

CREATE TABLE `edulink1_lpriority`.`student_star_strategies` (
  `stud_star_strategy_id` BIGINT NOT NULL AUTO_INCREMENT,
  `star_scores_id` BIGINT NOT NULL,
  `goal_strategies_id` BIGINT NOT NULL,
  PRIMARY KEY (`stud_star_strategy_id`),
  INDEX `fk_star_goal_strategy_id_idx` (`goal_strategies_id` ASC),
  INDEX `fk_star_scores_ids_idx` (`star_scores_id` ASC),
  CONSTRAINT `fk_star_goal_strategy_id`
    FOREIGN KEY (`goal_strategies_id`)
    REFERENCES `edulink1_lpriority`.`goal_strategies` (`goal_strategies_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_star_scores_ids`
    FOREIGN KEY (`star_scores_id`)
    REFERENCES `edulink1_lpriority`.`star_scores` (`star_scores_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- Added By Anusuya on 14/7/2017  ---------------------------------------------------------------------------------------------------------------------------------------------
	    
ALTER TABLE `edulink1_lpriority`.`student_star_strategies` 
ADD COLUMN `goal_count` BIGINT NULL AFTER `goal_strategies_id`;

ALTER TABLE `edulink1_lpriority`.`stud_caaspp_own_stategies` 
ADD COLUMN `goal_count` BIGINT NULL AFTER `stud_own_strategies_desc`;


-- Added By Anusuya on 20/7/2017 ----------------------------------------------------------------------------------------------------------------------

alter table trimester add column order_id bigint(20);
update trimester set order_id=1 where trimester_id=4;
update trimester set order_id=2 where trimester_id=1;
update trimester set order_id=3 where trimester_id=2;
update trimester set order_id=4 where trimester_id=3;

alter table star_scores add column test_date varchar(20);

-- ----------------------------------------------------------------------------------------------------------------------------------------------------


insert into trimester values(4,'Boy');
-- Added by Santosh on 19/07/2017
insert into caaspp_types values(4,'Star Math');

-- Added by Lalitha on 7/11/2017

CREATE TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` (
  `legend_sub_criteria_district_wise_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `district_id` BIGINT(20) NULL,
  `master_grade_id` BIGINT(20) NULL,
  `legend_sub_criteria_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`legend_sub_criteria_district_wise_id`),
  INDEX `fk_legend_suc_cri_dw_id_idx` (`legend_sub_criteria_id` ASC),
  INDEX `fk_district_id_dw_idx` (`district_id` ASC),
  INDEX `fk_masters_grade_id_dw_idx` (`master_grade_id` ASC),
  CONSTRAINT `fk_legend_suc_cri_dw_id`
    FOREIGN KEY (`legend_sub_criteria_id`)
    REFERENCES `edulink1_lpriority`.`legend_sub_criteria` (`legend_sub_criteria_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_district_id_dw`
    FOREIGN KEY (`district_id`)
    REFERENCES `edulink1_lpriority`.`district` (`district_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_masters_grade_id_dw`
    FOREIGN KEY (`master_grade_id`)
    REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` 
	DROP FOREIGN KEY `fk_masters_grade_id_dw`;
	ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` 
	CHANGE COLUMN `master_grade_id` `master_grade_id_from` BIGINT(20) NULL DEFAULT NULL ,
	ADD COLUMN `master_grade_id_to` BIGINT(20) NULL AFTER `master_grade_id_from`,
	ADD INDEX `fk_masters_grade_id_to_dw_idx` (`master_grade_id_to` ASC);
	ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` 
	ADD CONSTRAINT `fk_masters_grade_id_dw`
	  FOREIGN KEY (`master_grade_id_from`)
	  REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION,
	ADD CONSTRAINT `fk_masters_grade_id_to_dw`
	  FOREIGN KEY (`master_grade_id_to`)
	  REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;
	  
ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` 
	DROP FOREIGN KEY `fk_masters_grade_id_dw`,
	DROP FOREIGN KEY `fk_masters_grade_id_to_dw`;
	ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` 
	DROP COLUMN `master_grade_id_to`,
	CHANGE COLUMN `master_grade_id_from` `master_grade_id` BIGINT(20) NULL DEFAULT NULL ,
	DROP INDEX `fk_masters_grade_id_to_dw_idx` ;
	ALTER TABLE `edulink1_lpriority`.`legend_sub_criteria_district_wise` 
	ADD CONSTRAINT `fk_masters_grade_id_dw`
	  FOREIGN KEY (`master_grade_id`)
	  REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;


INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise`
(`legend_sub_criteria_district_wise_id`,
`district_id`,
`master_grade_id`,
`legend_sub_criteria_id`)
VALUES
(1,2,1,1),
(2,2,1,2),
(3,2,1,3),
(4,2,1,4),
(5,2,1,5),
(6,2,1,6),
(7,2,1,7),
(8,2,1,8),
(9,2,1,9),
(10,2,1,10),
(11,2,1,11),
(12,2,1,12),
(13,2,1,13),
(14,2,1,14),
(15,2,1,15),
(16,2,1,16),
(17,2,1,17),
(18,2,1,18),
(19,2,1,19),
(20,2,1,20),
(21,2,1,21),
(22,2,1,22),
(23,2,1,23),
(24,2,1,24),
(25,2,1,25),
(26,2,1,26),
(27,2,1,27),
(28,2,1,28),
(29,2,1,29),
(30,2,1,30),
(31,2,2,1),
(32,2,2,2),
(33,2,2,3),
(34,2,2,4),
(35,2,2,5),
(36,2,2,6),
(37,2,2,7),
(38,2,2,8),
(39,2,2,9),
(40,2,2,10),
(41,2,2,11),
(42,2,2,12),
(43,2,2,13),
(44,2,2,14),
(45,2,2,15),
(46,2,2,16),
(47,2,2,17),
(48,2,2,18),
(49,2,2,19),
(50,2,2,20),
(51,2,2,21),
(52,2,2,22),
(53,2,2,23),
(54,2,2,24),
(55,2,2,25),
(56,2,2,26),
(57,2,2,27),
(58,2,2,28),
(59,2,2,29),
(60,2,2,30),
(61,2,3,1),
(62,2,3,2),
(63,2,3,3),
(64,2,3,4),
(65,2,3,5),
(66,2,3,6),
(67,2,3,7),
(68,2,3,8),
(69,2,3,9),
(70,2,3,10),
(71,2,3,11),
(72,2,3,12),
(73,2,3,13),
(74,2,3,14),
(75,2,3,15),
(76,2,3,16),
(77,2,3,17),
(78,2,3,18),
(79,2,3,19),
(80,2,3,20),
(81,2,3,21),
(82,2,3,22),
(83,2,3,23),
(84,2,3,24),
(85,2,3,25),
(86,2,3,26),
(87,2,3,27),
(88,2,3,28),
(89,2,3,29),
(90,2,3,30),
(91,2,4,1),
(92,2,4,2),
(93,2,4,3),
(94,2,4,4),
(95,2,4,5),
(96,2,4,6),
(97,2,4,7),
(98,2,4,8),
(99,2,4,9),
(100,2,4,10),
(101,2,4,11),
(102,2,4,12),
(103,2,4,13),
(104,2,4,14),
(105,2,4,15),
(106,2,4,16),
(107,2,4,17),
(108,2,4,18),
(109,2,4,19),
(110,2,4,20),
(111,2,4,21),
(112,2,4,22),
(113,2,4,23),
(114,2,4,24),
(115,2,4,25),
(116,2,4,26),
(117,2,4,27),
(118,2,4,28),
(119,2,4,29),
(120,2,4,30),
(121,2,5,1),
(122,2,5,2),
(123,2,5,3),
(124,2,5,4),
(125,2,5,5),
(126,2,5,6),
(127,2,5,7),
(128,2,5,8),
(129,2,5,9),
(130,2,5,10),
(131,2,5,11),
(132,2,5,12),
(133,2,5,13),
(134,2,5,14),
(135,2,5,15),
(136,2,5,16),
(137,2,5,17),
(138,2,5,18),
(139,2,5,19),
(140,2,5,20),
(141,2,5,21),
(142,2,5,22),
(143,2,5,23),
(144,2,5,24),
(145,2,5,25),
(146,2,5,26),
(147,2,5,27),
(148,2,5,28),
(149,2,5,29),
(150,2,5,30),
(151,2,6,1),
(152,2,6,2),
(153,2,6,3),
(154,2,6,4),
(155,2,6,5),
(156,2,6,6),
(157,2,6,7),
(158,2,6,8),
(159,2,6,9),
(160,2,6,10),
(161,2,6,11),
(162,2,6,12),
(163,2,6,13),
(164,2,6,14),
(165,2,6,15),
(166,2,6,16),
(167,2,6,17),
(168,2,6,18),
(169,2,6,19),
(170,2,6,20),
(171,2,6,21),
(172,2,6,22),
(173,2,6,23),
(174,2,6,24),
(175,2,6,25),
(176,2,6,26),
(177,2,6,27),
(178,2,6,28),
(179,2,6,29),
(180,2,6,30),
(181,2,13,1),
(182,2,13,2),
(183,2,13,3),
(184,2,13,4),
(185,2,13,5),
(186,2,13,6),
(187,2,13,7),
(188,2,13,8),
(189,2,13,9),
(190,2,13,10),
(191,2,13,11),
(192,2,13,12),
(193,2,13,13),
(194,2,13,14),
(195,2,13,15),
(196,2,13,16),
(197,2,13,17),
(198,2,13,18),
(199,2,13,19),
(200,2,13,20),
(201,2,13,21),
(202,2,13,22),
(203,2,13,23),
(204,2,13,24),
(205,2,13,25),
(206,2,13,26),
(207,2,13,27),
(208,2,13,28),
(209,2,13,29),
(210,2,13,30);	    

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria`
(`legend_sub_criteria_id`,
`legend_criteria_id`,
`legend_sub_criteria_name`)
VALUES
(38,1,'Basic Communication'),
(39,1,'Using Communication Tools'),
(40,2,'Responsibility and Initiative'),
(41,1,'Self-Reflection/Agency'),
(42,1,'Composing/Written Expression'),
(43,1,'Usage/Mechanics');

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise`
(`legend_sub_criteria_district_wise_id`,
`district_id`,
`master_grade_id`,
`legend_sub_criteria_id`)
VALUES
(211,1,6,40),
(212,1,6,8),
(213,1,6,9),
(214,1,6,20),
(215,1,6,24),
(216,1,6,14),
(217,1,6,39),
(218,1,6,5),
(219,1,6,41),
(220,1,6,42),
(221,1,6,43),
(224,1,7,40),
(225,1,7,8),
(226,1,7,9),
(227,1,7,20),
(228,1,7,24),
(229,1,7,14),
(230,1,7,39),
(231,1,7,5),
(232,1,7,41),
(233,1,7,42),
(234,1,7,43),
(236,1,8,40),
(237,1,8,8),
(238,1,8,9),
(239,1,8,20),
(240,1,8,24),
(241,1,8,14),
(242,1,8,39),
(243,1,8,5),
(244,1,8,41),
(245,1,8,42),
(246,1,8,43);

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise`
(`legend_sub_criteria_district_wise_id`,
`district_id`,
`master_grade_id`,
`legend_sub_criteria_id`,
`addition_info`)
VALUES
(247,1,3,14,'I can decipher information and ask meaningful questions.'),
(248,1,3,17,'I can go through the steps to solve a problem.'),
(249,1,3,18,'I can justify my thoughts and opinions.'),
(250,1,3,20,'I can generate new ideas to complete a task or challenge.'),
(251,1,3,22,'I can explore new ideas'),
(252,1,3,24,'I can use my best ideas to create a product or performance.'),
(253,1,3,38,'I can get the response that I want from my intended audience.'),
(254,1,3,5,'I can give a clear oral presentation.'),
(255,1,3,39,'I can use digital media to enhance my communication.'),
(256,1,3,8,'I can cooperate with my group to solve a problem or create a task'),
(257,1,3,40,'I can take responsibility and initiative in my group'),
(258,1,3,9,'I can listen to and use the opinions of others'),
(259,1,4,14,'I can decipher information and ask meaningful questions.'),
(260,1,4,17,'I can go through the steps to solve a problem.'),
(261,1,4,18,'I can justify my thoughts and opinions.'),
(262,1,4,20,'I can generate new ideas to complete a task or challenge.'),
(263,1,4,22,'I can explore new ideas'),
(264,1,4,24,'I can use my best ideas to create a product or performance.'),
(265,1,4,38,'I can get the response that I want from my intended audience.'),
(266,1,4,5,'I can give a clear oral presentation.'),
(267,1,4,39,'I can use digital media to enhance my communication.'),
(268,1,4,8,'I can cooperate with my group to solve a problem or create a task'),
(269,1,4,40,'I can take responsibility and initiative in my group'),
(270,1,4,9,'I can listen to and use the opinions of others'),
(271,1,5,14,'I can decipher information and ask meaningful questions.'),
(272,1,5,17,'I can go through the steps to solve a problem.'),
(273,1,5,18,'I can justify my thoughts and opinions.'),
(274,1,5,20,'I can generate new ideas to complete a task or challenge.'),
(275,1,5,22,'I can explore new ideas'),
(276,1,5,24,'I can use my best ideas to create a product or performance.'),
(277,1,5,38,'I can get the response that I want from my intended audience.'),
(278,1,5,5,'I can give a clear oral presentation.'),
(279,1,5,39,'I can use digital media to enhance my communication.'),
(280,1,5,8,'I can cooperate with my group to solve a problem or create a task'),
(281,1,5,40,'I can take responsibility and initiative in my group'),
(282,1,5,9,'I can listen to and use the opinions of others');

-- Added By Anusuya on 21/7/2017 -------------------------------------------------------------------------------------------------

create table goal_sample_ideas(goal_sample_id  bigint(10),idea_desc varchar(2000));

insert into goal_sample_ideas values(1,'Eat a healthy breakfast'),
(2,'Go to sleep early'), 
(3,'Take a few deep breaths before the test'),
(4,'Get to school early on the day of tests');
(5,'Tell yourself,Beight a bit nervous is normal and may help me do better on the test');

-- Added by Christopher on 14/07/2017 -------------------------------------------------------------------------------------------------
INSERT INTO `edulink1_lpriority`.`stem_paths`(`stem_path_id`,`stem_path_desc`) VALUES (4,'Shared Units');

  CREATE TABLE `edulink1_lpriority`.`syn_handler` (
  `syn_handler_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `syn_content_id` BIGINT(20) NOT NULL,
  `user_reg_id` BIGINT(20) NOT NULL,
  `status` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`syn_handler_id`))
ENGINE = InnoDB;

CREATE TABLE `edulink1_lpriority`.`syn_history_handler` (
  `syn_history_handler_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `syn_handler_id` BIGINT(20) NOT NULL,
  `current_tab` VARCHAR(45) NOT NULL,
  `current_element` VARCHAR(45) NULL,
  `syn_status` VARCHAR(45) NOT NULL,
  `syn_control` VARCHAR(45) NULL,
  `before_update` VARCHAR(1000) NULL,
  `after_update` VARCHAR(1000) NULL,
  `content_status` VARCHAR(45) NULL,
  `time_stamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 
  PRIMARY KEY (`syn_history_handler_id`),
  INDEX `ssyn_handler_id_idx` (`syn_handler_id` ASC),
  CONSTRAINT `ssyn_handler_id`
    FOREIGN KEY (`syn_handler_id`)
    REFERENCES `edulink1_lpriority`.`syn_handler` (`syn_handler_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- Added By Anususya 4/8/2017-------------------------------------------------------------------------------------------------------------------

 drop table stud_caaspp_own_stategies;
 
 
 CREATE TABLE `edulink1_lpriority`.`stud_caaspp_own_stategies` (
  `stud_own_strategies_id` BIGINT NOT NULL AUTO_INCREMENT,
  `caaspp_scores_id` BIGINT NOT NULL,
  `stud_own_strategies_desc` LONGTEXT NULL,
  `goal_count` BIGINT NULL,
  PRIMARY KEY (`stud_own_strategies_id`),
  INDEX `fk_caaspp_scores_idsss_idx` (`caaspp_scores_id` ASC),
  CONSTRAINT `fk_caaspp_scores_idsss`
    FOREIGN KEY (`caaspp_scores_id`)
    REFERENCES `edulink1_lpriority`.`caaspp_scores` (`caaspp_scores_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);  
    
ALTER TABLE `edulink1_lpriority`.`stud_caaspp_own_stategies` 
DROP FOREIGN KEY `fk_caaspp_scores_idsss`;
ALTER TABLE `edulink1_lpriority`.`stud_caaspp_own_stategies` 
CHANGE COLUMN `caaspp_scores_id` `student_id` BIGINT(20) NOT NULL ,
ADD COLUMN `grade_id` BIGINT NOT NULL AFTER `student_id`,
ADD INDEX `fks_grade_id_idsss_idx` (`grade_id` ASC);
ALTER TABLE `edulink1_lpriority`.`stud_caaspp_own_stategies` 
ADD CONSTRAINT `fk_student_id_idsss`
  FOREIGN KEY (`student_id`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fks_grade_id_idsss`
  FOREIGN KEY (`grade_id`)
  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
 
  CREATE TABLE `edulink1_lpriority`.`goal_reports` (
  `goal_report_id` BIGINT NOT NULL AUTO_INCREMENT,
  `report_type` VARCHAR(45) NULL,
  PRIMARY KEY (`goal_report_id`));
  
  
insert into goal_reports values(1,'Boy'),(2,'Trimester1'),(3,'Trimester2');

-- Added By Anusuys on 4/8/2017 ----------------------------------------------------------------------------

drop table student_star_strategies;

CREATE TABLE `edulink1_lpriority`.`student_star_strategies` (
  `stud_star_strategy_id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT NOT NULL,
  `grade_id` BIGINT NOT NULL,
  `goal_strategies_id` BIGINT NOT NULL,
  `goal_count` BIGINT NULL,
  PRIMARY KEY (`stud_star_strategy_id`),
  INDEX `fks_student_idsss_idx` (`student_id` ASC),
  INDEX `fks_grade_idsss_idx` (`grade_id` ASC),
  INDEX `fks_goal_strategies_idsss_idx` (`goal_strategies_id` ASC),
  CONSTRAINT `fks_student_idsss`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fks_grade_idsss`
    FOREIGN KEY (`grade_id`)
    REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fks_goal_strategies_idsss`
    FOREIGN KEY (`goal_strategies_id`)
    REFERENCES `edulink1_lpriority`.`goal_strategies` (`goal_strategies_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
   ALTER TABLE `edulink1_lpriority`.`student_star_strategies` 
ADD COLUMN `trimester_id` BIGINT(20) NOT NULL AFTER `grade_id`,
ADD INDEX `fks_trimester_idsss_idx` (`trimester_id` ASC);
ALTER TABLE `edulink1_lpriority`.`student_star_strategies` 
ADD CONSTRAINT `fks_trimester_idsss`
  FOREIGN KEY (`trimester_id`)
  REFERENCES `edulink1_lpriority`.`trimester` (`trimester_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  ALTER TABLE `edulink1_lpriority`.`student_star_strategies` 
ADD COLUMN `caaspp_types_id` BIGINT NOT NULL AFTER `trimester_id`,
ADD INDEX `fks_caaspp_types_idses_idx` (`caaspp_types_id` ASC);
ALTER TABLE `edulink1_lpriority`.`student_star_strategies` 
ADD CONSTRAINT `fks_caaspp_types_idsss`
  FOREIGN KEY (`caaspp_types_id`)
  REFERENCES `edulink1_lpriority`.`caaspp_types` (`caaspp_types_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  drop table student_star_strategies;
  
CREATE TABLE `student_star_strategies` (
  `stud_star_strategy_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) NOT NULL,
  `grade_id` bigint(20) NOT NULL,
  `trimester_id` bigint(20) NOT NULL,
  `caaspp_types_id` bigint(20) NOT NULL,
  `goal_strategies_id` bigint(20) NOT NULL,
  `goal_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`stud_star_strategy_id`),
  KEY `fks_student_idsss_idx` (`student_id`),
  KEY `fks_grade_idsss_idx` (`grade_id`),
  KEY `fks_goal_strategies_idsss_idx` (`goal_strategies_id`),
  KEY `fks_trimester_idsss_idx` (`trimester_id`),
  KEY `fks_caaspp_types_idsss_idx` (`caaspp_types_id`),
  CONSTRAINT `fks_caaspp_types_idess` FOREIGN KEY (`caaspp_types_id`) REFERENCES `caaspp_types` (`caaspp_types_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fks_goal_strategies_idsss` FOREIGN KEY (`goal_strategies_id`) REFERENCES `goal_strategies` (`goal_strategies_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fks_grade_idsss` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fks_student_idsss` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fks_trimester_idsss` FOREIGN KEY (`trimester_id`) REFERENCES `trimester` (`trimester_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

    
--  Added By Christopher on 11/08/2017 ----------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE `edulink1_lpriority`.`notification_status` (
  `notification_status_id` BIGINT(25) NOT NULL AUTO_INCREMENT,
  `announcement_id` BIGINT(25) NOT NULL,
  `reg_id` BIGINT(25) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `read_time_stamp` VARCHAR(45) NULL,
  PRIMARY KEY (`notification_status_id`),
  INDEX `fk_announcement_ids_idx` (`announcement_id` ASC),
  CONSTRAINT `fk_announcement_ids`
    FOREIGN KEY (`announcement_id`)
    REFERENCES `edulink1_lpriority`.`announcements` (`announcement_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
ALTER TABLE `edulink1_lpriority`.`announcements` 
CHANGE COLUMN `contact_person` `url_links` VARCHAR(500) NOT NULL ;	

ALTER TABLE `edulink1_lpriority`.`announcements` 
CHANGE COLUMN `annoncement_name` `annoncement_name` VARCHAR(300) NOT NULL ,
CHANGE COLUMN `announce_description` `announce_description` VARCHAR(1000) NOT NULL ;
-- --------------------------------------------------------------------------------------------------------------------------------------------------  

-- Added by Lalitha on 8/22/2017

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise`
(`district_id`,
`master_grade_id`,
`legend_sub_criteria_id`)
VALUES
(1,3,26),
(1,3,27),
(1,3,28),
(1,3,29),
(1,3,30),
(1,4,26),
(1,4,27),
(1,4,28),
(1,4,29),
(1,4,30),
(1,5,26),
(1,5,27),
(1,5,28),
(1,5,29),
(1,5,30),
(1,6,26),
(1,6,27),
(1,6,28),
(1,6,29),
(1,6,30),
(1,7,26),
(1,7,27),
(1,7,28),
(1,7,29),
(1,7,30),
(1,8,26),
(1,8,27),
(1,8,28),
(1,8,29),
(1,8,30);

ALTER TABLE `edulink1_lpriority`.`legend` 
ADD COLUMN `from_grade` BIGINT(20) NULL AFTER `district_id`,
ADD COLUMN `to_grade` BIGINT(20) NULL AFTER `from_grade`,
ADD INDEX `fk_from_grade_id_idx` (`from_grade` ASC),
ADD INDEX `fk_to_grade_id_idx` (`to_grade` ASC);
ALTER TABLE `edulink1_lpriority`.`legend` 
ADD CONSTRAINT `fk_from_grade_id`
  FOREIGN KEY (`from_grade`)
  REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_to_grade_id`
  FOREIGN KEY (`to_grade`)
  REFERENCES `edulink1_lpriority`.`master_grades` (`master_grades_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- Added By Anusuya ------------------------------------------------------------------------------------------------

  update goal_strategies set caaspp_types_id=3 where caaspp_types_id=1;
  update goal_strategies set caaspp_types_id=4 where caaspp_types_id=2;
  
-- added by Lalitha on 9/18/2017
-- Changes for Mobile application. added read_status column for 
  
ALTER TABLE `edulink1_lpriority`.`register_for_class` 
ADD COLUMN `read_status` VARCHAR(10) NULL DEFAULT 'no' AFTER `teacher_id`;

ALTER TABLE `edulink1_lpriority`.`attendance` 
ADD COLUMN `read_status` VARCHAR(10) NULL DEFAULT 'no' AFTER `change_date`;

ALTER TABLE `edulink1_lpriority`.`announcements` 
ADD COLUMN `read_status` VARCHAR(10) NULL DEFAULT 'no' AFTER `file_name`;

ALTER TABLE `edulink1_lpriority`.`events` 
ADD COLUMN `read_status` VARCHAR(10) NULL DEFAULT 'no' AFTER `change_date`; 

-- Added by Lalitha on 9/18/2017 -----------------------------------------------------------------------------------

 update goal_strategies set caaspp_types_id=3 where caaspp_types_id=1;
 update goal_strategies set caaspp_types_id=4 where caaspp_types_id=2;
  
 CREATE TABLE edulink1_lpriority.fluency_comments ( comment_id BIGINT(20) NOT NULL AUTO_INCREMENT, comment VARCHAR(200) NOT NULL, PRIMARY KEY (comment_id));
 INSERT INTO edulink1_lpriority.fluency_comments (comment) VALUES ('Student does not pause at commas or stop at periods. Runs on or reads too fast.'), ('Student spends too much time struggling with pronunciation or sounding out unfamiliar words.'), ('Student Skips many words or whole sentences.'), ('Student appears to skip or avoid unfamiliar or difficult words.'), ('Student reads very slow for grade level or pauses excessively.'), ('Student appears to forget where they are at and repeats sentences.'), ('Student struggles with certain consonants or vowels.'), ('Student reads in a \"sing-song\" fashion; highs and lows in tonal inflection.'), ('Student is at grade level for correct words per minute.'), ('Student is an excellent reader for grade level, with very few mistakes.');
 
-- ------------------------------------------------------------------------------------------------------------------

 -- Added by Lalitha on 9/20/2017
 
 CREATE TABLE `edulink1_lpriority`.`math_gear` (
  `math_gear_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `math_gear` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`math_gear_id`));

INSERT INTO `edulink1_lpriority`.`math_gear` (`math_gear_id`, `math_gear`) VALUES ('1', 'Gear1');
INSERT INTO `edulink1_lpriority`.`math_gear` (`math_gear_id`, `math_gear`) VALUES ('2', 'Gear2');
INSERT INTO `edulink1_lpriority`.`math_gear` (`math_gear_id`, `math_gear`) VALUES ('3', 'Gear3');
INSERT INTO `edulink1_lpriority`.`math_gear` (`math_gear_id`, `math_gear`) VALUES ('4', 'Gear4');

ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
ENGINE = InnoDB ,
ADD COLUMN `math_gear_id` BIGINT(20) NOT NULL DEFAULT 1 AFTER `status`;

ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
ADD INDEX `fk_gear_id_idx` (`math_gear_id` ASC);
ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
ADD CONSTRAINT `fk_gear_id`
  FOREIGN KEY (`math_gear_id`)
  REFERENCES `edulink1_lpriority`.`math_gear` (`math_gear_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
 -- Added By Anusuya on 21-9-2017 ---------------------------------------------------------------------------------------------
 
  ALTER TABLE `edulink1_lpriority`.`goal_strategies` 
  ADD COLUMN `order_id` BIGINT NULL AFTER `goal_strategies_desc`;
  
  UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='1' WHERE `goal_strategies_id`='1';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='2' WHERE `goal_strategies_id`='2';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='3' WHERE `goal_strategies_id`='3';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='4' WHERE `goal_strategies_id`='4';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='5' WHERE `goal_strategies_id`='5';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='6' WHERE `goal_strategies_id`='6';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='7' WHERE `goal_strategies_id`='7';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='8' WHERE `goal_strategies_id`='8';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='1' WHERE `goal_strategies_id`='9';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='2' WHERE `goal_strategies_id`='10';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='3' WHERE `goal_strategies_id`='11';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='4' WHERE `goal_strategies_id`='12';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='5' WHERE `goal_strategies_id`='13';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='6' WHERE `goal_strategies_id`='14';
UPDATE `edulink1_lpriority`.`goal_strategies` SET `order_id`='7' WHERE `goal_strategies_id`='15';


-- Added by Lalitha on 9/25/2017
-- Issue #820 Implements Tech Standards for Learning Indicator literacy section

INSERT INTO `edulink1_lpriority`.`legend_criteria` (`legend_criteria_id`, `legend_criteria_name`) VALUES ('7', 'Technology');
delete FROM edulink1_lpriority.learning_indicator_values where sub_criteria_id=35;
delete FROM edulink1_lpriority.le_rubrics where legend_sub_criteria_id=35;
delete FROM edulink1_lpriority.legend where legend_sub_criteria_id=35;
DELETE FROM `edulink1_lpriority`.`legend_sub_criteria` WHERE `legend_sub_criteria_id`='35';

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Empowered Learner');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Digital Citizen');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Knowledge Constructor');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Innovative Designer');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Computational Thinker');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Creative Communicator');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria` ( `legend_criteria_id`, `legend_sub_criteria_name`) VALUES ( '7', 'Global Collaborator');

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '1', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');


INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '2', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');


INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '3', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '4', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '5', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '6', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '7', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');


INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '8', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');


INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '50', 'Students leverage technology to take an active role in choosing, achieving and demonstrating competency in their learning goals, informed by the learning sciences.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '51', 'Students recognize the rights, responsibilities and opportunities of living, learning and working in an interconnected digital world, and they act and model in ways that are safe, legal and ethical.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '52', 'Students critically curate a variety of resources using digital tools to construct knowledge, produce creative artifacts and make meaningful learning experiences for themselves and others. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '53', 'Students use a variety of technologies within a design process to identify and solve problems by creating new, useful or imaginative solutions.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '54', 'Students develop and employ strategies for understanding and solving problems in ways that leverage the power of technological methods to develop and test solutions. ');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '55', 'Students communicate clearly and express themselves creatively for a variety of purposes using the platforms, tools, styles, formats and digital media appropriate to their goals.');
INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise` (`district_id`, `master_grade_id`, `legend_sub_criteria_id`, `additional_info`) VALUES ('1', '13', '56', 'Students use digital tools to broaden their perspectives and enrich their learning by collaborating with others and working effectively in teams locally and globally.');

 -- Added by Lalitha on 29/9/2017
 
ALTER TABLE `edulink1_lpriority`.`notification_status` ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`notification_status` CHANGE COLUMN `notification_status_id` `notification_status_id` BIGINT(20) NOT NULL AUTO_INCREMENT  , CHANGE COLUMN `announcement_id` `announcement_id` BIGINT(20) NOT NULL  , CHANGE COLUMN `reg_id` `reg_id` BIGINT(20) NOT NULL  ;

delete from notification_status;

ALTER TABLE `edulink1_lpriority`.`notification_status` 
  ADD CONSTRAINT `fk_ns_reg_id`
  FOREIGN KEY (`reg_id` )
  REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_ns_announe_id`
  FOREIGN KEY (`announcement_id` )
  REFERENCES `edulink1_lpriority`.`announcements` (`announcement_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_ns_reg_id` (`reg_id` ASC) 
, ADD INDEX `fk_ns_announe_id` (`announcement_id` ASC) ;


CREATE TABLE `event_status` (
  `event_status_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_id` bigint(20) NOT NULL,
  `reg_id` bigint(20) NOT NULL,
  `status` varchar(45) NOT NULL,
  `read_time_stamp` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`event_status_id`),
  KEY `fk_event_ids_idx` (`event_id`),
  KEY `fk_es_reg_id` (`reg_id`),
  KEY `fk_es_announe_id` (`event_id`),
  CONSTRAINT `fk_es_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_es_event_id` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB  ;

ALTER TABLE `edulink1_lpriority`.`event_status` 
ADD UNIQUE INDEX `unique_key` (`event_id` ASC, `reg_id` ASC) ;


-- Added by Lalitha on 10/2/2017

ALTER TABLE `edulink1_lpriority`.`star_scores` 
CHANGE COLUMN `score` `score` FLOAT NULL ;

ALTER TABLE `edulink1_lpriority`.`event_status` 
CHANGE COLUMN `status` `status` VARCHAR(45) NOT NULL DEFAULT 'unread' ;


ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
ADD COLUMN `change_date` DATE NULL AFTER `test_due_date`;

ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
ADD COLUMN `read_status` VARCHAR(10) NULL DEFAULT 'unread'  AFTER `graded_date` , 
CHANGE COLUMN `change_date` `graded_date` DATE NULL DEFAULT NULL  ;

  
-- Added by Lalitha on 10/3/2017 

delete FROM star_scores where score is null;
ALTER TABLE `star_scores` CHANGE COLUMN `score` `score` FLOAT NOT NULL ;

-- Added by Lalitha on 10/6/2017

ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
DROP FOREIGN KEY `caaspp_class_id`;
ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
CHANGE COLUMN `class_id` `class_id` BIGINT(20) NULL ;
ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
ADD CONSTRAINT `caaspp_class_id`
  FOREIGN KEY (`class_id`)
  REFERENCES `edulink1_lpriority`.`class` (`class_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
DROP INDEX `unique_key` ;


ALTER TABLE `edulink1_lpriority`.`caaspp_scores` 
ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `grade_id` ASC, `caaspp_type_id` ASC);

update  edulink1_lpriority.caaspp_scores set class_id = null ;

ALTER TABLE `edulink1_lpriority`.`student_assignment_status` 
CHANGE COLUMN `graded_date` `graded_date` TIMESTAMP NULL DEFAULT NULL ;

-- Added by Lalitha on 02/10/2017 ------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`game_level` 
ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`math_gear` 
ENGINE = InnoDB ;

ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
ADD CONSTRAINT `fk_gear_id`
  FOREIGN KEY (`math_gear_id`)
  REFERENCES `edulink1_lpriority`.`math_gear` (`math_gear_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
ADD CONSTRAINT `fk_stu_ass_id`
  FOREIGN KEY (`student_assignment_id`)
  REFERENCES `edulink1_lpriority`.`student_assignment_status` (`student_assignment_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_game_level_idx`
  FOREIGN KEY (`game_level_id`)
  REFERENCES `edulink1_lpriority`.`game_level` (`game_level_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
DROP FOREIGN KEY `fk_gear_id`;
ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
CHANGE COLUMN `math_gear_id` `math_gear_id` BIGINT(20) NOT NULL ;
ALTER TABLE `edulink1_lpriority`.`math_game_scores` 
ADD CONSTRAINT `fk_gear_id`
  FOREIGN KEY (`math_gear_id`)
  REFERENCES `edulink1_lpriority`.`math_gear` (`math_gear_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;  
  
-- Added by Christopher on 06/11/2017-----------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`academic_year` 
ENGINE = InnoDB ;
ALTER TABLE `edulink1_lpriority`.`school` 
ADD COLUMN `academic_year_id` BIGINT(20) NULL AFTER `district_id`,
ADD INDEX `fk_academic_year_id_idx` (`academic_year_id` ASC);
ALTER TABLE `edulink1_lpriority`.`school` 
ADD CONSTRAINT `fk_academic_year_id`
 FOREIGN KEY (`academic_year_id`)
 REFERENCES `edulink1_lpriority`.`academic_year` (`academic_year_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;
 
UPDATE `edulink1_lpriority`.`school` SET `academic_year_id`='3' ;

-- Added By Anusya on 24/11/2017--------------------------------------------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`events` 
CHANGE COLUMN `description` `description` LONGTEXT NOT NULL ;

-- Added by Anusuya on 29/11/2017 ------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`benchmark_results` 
CHANGE COLUMN `teacher_notes` `teacher_notes` VARCHAR(500) NULL DEFAULT NULL ;

ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
CHANGE COLUMN `comment` `comment` VARCHAR(500) NULL DEFAULT NULL ;

ALTER TABLE `edulink1_lpriority`.`assignment_questions` 
CHANGE COLUMN `teacher_comment` `teacher_comment` VARCHAR(500) NULL DEFAULT NULL ;


-- Added By Anusuya on 4/12/2017-------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`assigned_ptasks` 
CHANGE COLUMN `teacher_comments` `teacher_comments` VARCHAR(500) NULL DEFAULT NULL ;

-- Added By Christopher on 04/01/2018 -------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
ADD COLUMN `assignment_id` BIGINT(10) NOT NULL AFTER `k1_auto_assigned_set_id`,
ADD INDEX `fk_assignment_id_idx_idx` (`assignment_id` ASC);
ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
ADD CONSTRAINT `fk_assignment_id_idx`
  FOREIGN KEY (`assignment_id`)
  REFERENCES `edulink1_lpriority`.`assignment` (`assignment_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `edulink1_lpriority`.`k1_auto_assigned_sets` 
	CHANGE COLUMN `assignment_id` `assignment_id` BIGINT(10) NULL ;
	
update edulink1_lpriority.k1_auto_assigned_sets set assignment_id = null;

-- Added by Anusuya on 27/03/2018 ---------------------------------------------------------------------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`questions` 
DROP FOREIGN KEY `lesson_id`;
ALTER TABLE `edulink1_lpriority`.`questions` 
CHANGE COLUMN `lesson_id` `lesson_id` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `edulink1_lpriority`.`questions` 
ADD CONSTRAINT `lesson_id`
  FOREIGN KEY (`lesson_id`)
  REFERENCES `edulink1_lpriority`.`lesson` (`lesson_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  ALTER TABLE `edulink1_lpriority`.`sub_questions` 
CHANGE COLUMN `lesson_id` `lesson_id` BIGINT(20) NULL DEFAULT NULL ;

 -- Added By Anusuya on 4/6/2018------------------------------------------------------------------------------------------------------------------------------------------
 ALTER TABLE `edulink1_lpriority`.`questions` 
ADD COLUMN `grade_id` BIGINT(20) NULL DEFAULT NULL AFTER `jac_template_id`,
ADD INDEX `fk_ques_grade_id_idx` (`grade_id` ASC);
ALTER TABLE `edulink1_lpriority`.`questions` 
ADD CONSTRAINT `fk_ques_grade_id`
  FOREIGN KEY (`grade_id`)
  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
 -- ---------------------------------------------------------------------------------------------------------------------------------
ALTER TABLE `edulink1_lpriority`.`sub_questions` 
ADD COLUMN `grade_id` BIGINT(20) NULL DEFAULT NULL AFTER `title`;

ALTER TABLE `edulink1_lpriority`.`sub_questions` 
ADD INDEX `fk_sub_grade_id_idxx` (`grade_id` ASC);
ALTER TABLE `edulink1_lpriority`.`sub_questions` 
ADD CONSTRAINT `fk_sub_grade_id`
  FOREIGN KEY (`grade_id`)
  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- Added By Anusuya on 27/4/2018 ------------------------------------------------------------------------------------------------------- 

ALTER TABLE `edulink1_lpriority`.`questions` 
ADD COLUMN `ques_status` VARCHAR(45) NULL DEFAULT 'active' AFTER `grade_id`;

ALTER TABLE `edulink1_lpriority`.`sub_questions` 
ADD COLUMN `sub_ques_status` VARCHAR(45) NULL DEFAULT 'active' AFTER `grade_id`;


-- Reading Register SQLs

-- Added by Lalitha on 2/6/2017

-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_quiz` (
  `question_num` BIGINT(20) NOT NULL,
  `quest` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`question_num`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_master`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_master` (
  `title_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `book_title` VARCHAR(90) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  `number_of_pages` INT(11) NOT NULL,
  `reg_id` BIGINT(20) NULL,
  `create_date` DATE NOT NULL,
  PRIMARY KEY (`title_id`),
  INDEX `fk_read_reg_master_reg_id_idx` (`reg_id` ASC),
  UNIQUE INDEX `unique_key` (`book_title` ASC),
  CONSTRAINT `fk_read_reg_master_reg_id`
    FOREIGN KEY (`reg_id`)
    REFERENCES `edulink1_lpriority`.`user_registration` (`reg_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_questions` (
  `student_id` BIGINT(20) NOT NULL,
  `title_id` BIGINT(20) NOT NULL,
  `question_num` BIGINT(20) NOT NULL,
  `question` VARCHAR(100) NOT NULL,
  `option1` VARCHAR(15) NOT NULL,
  `option2` VARCHAR(15) NOT NULL,
  `option3` VARCHAR(15) NOT NULL,
  `option4` VARCHAR(15) NOT NULL,
  `answer` VARCHAR(10) NOT NULL,
  `create_date` DATE NULL,
  INDEX `fk_read_reg_questions_read_reg_master1_idx` (`title_id` ASC),
  INDEX `fk_read_reg_questions_read_reg_quiz1_idx` (`question_num` ASC),
  PRIMARY KEY (`student_id`, `title_id`, `question_num`),
  CONSTRAINT `fk_read_reg_questions_read_reg_master1`
    FOREIGN KEY (`title_id`)
    REFERENCES `edulink1_lpriority`.`read_reg_master` (`title_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_questions_read_reg_quiz1`
    FOREIGN KEY (`question_num`)
    REFERENCES `edulink1_lpriority`.`read_reg_quiz` (`question_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_questions_read_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_review` (
  `review_id` BIGINT(20) NOT NULL,
  `student_id` BIGINT(20) NOT NULL,
  `title_id` BIGINT(20) NOT NULL,
  `rating` INT(11) NOT NULL,
  `review` LONGTEXT NULL,
  `review_date` DATE NULL,
  PRIMARY KEY (`review_id`),
  INDEX `fk_read_reg_review_read_reg_master1_idx` (`title_id` ASC),
  INDEX `fk_read_reg_review_student_id_idx` (`student_id` ASC),
  CONSTRAINT `fk_read_reg_review_read_reg_master1`
    FOREIGN KEY (`title_id`)
    REFERENCES `edulink1_lpriority`.`read_reg_master` (`title_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_review_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_answers` (
  `student_id` BIGINT(20) NOT NULL,
  `title_id` BIGINT(20) NOT NULL,
  `question_num` BIGINT(20) NOT NULL,
  `answer` VARCHAR(10) NULL,
  `mark` INT(10) NULL,
  `test_date` DATE NULL,
  `student_que_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`student_id`, `title_id`, `question_num`),
  INDEX `fk_read_reg_answers_read_reg_master1_idx` (`title_id` ASC),
  INDEX `fk_read_reg_answers_read_reg_quiz1_idx` (`question_num` ASC),
  INDEX `fk_read_reg_answers_student_que_id_idx` (`student_que_id` ASC),
  CONSTRAINT `fk_read_reg_answers_read_reg_master1`
    FOREIGN KEY (`title_id`)
    REFERENCES `edulink1_lpriority`.`read_reg_master` (`title_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_answers_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_answers_read_reg_quiz1`
    FOREIGN KEY (`question_num`)
    REFERENCES `edulink1_lpriority`.`read_reg_quiz` (`question_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_answers_student_que_id`
    FOREIGN KEY (`student_que_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_activity` (
  `activity_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `actitvity` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`activity_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_rubric`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_rubric` (
  `score` BIGINT(20) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`score`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `edulink1_lpriority`.`read_reg_activity_score`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edulink1_lpriority`.`read_reg_activity_score` (
  `student_id` BIGINT(20) NOT NULL,
  `teacher_id` BIGINT(20) NOT NULL,
  `title_id` BIGINT(20) NOT NULL,
  `activity_id` BIGINT(20) NOT NULL,
  `teacher_score` BIGINT(20) NULL,
  `self_score` BIGINT(20) NOT NULL,
  PRIMARY KEY (`student_id`, `title_id`, `activity_id`),
  INDEX `fk_read_reg_activity_score_read_reg_activity1_idx` (`activity_id` ASC),
  INDEX `fk_read_reg_activity_score_read_reg_master1_idx` (`title_id` ASC),
  INDEX `fk_read_reg_activity_score_read_reg_rubric1_idx` (`teacher_score` ASC),
  INDEX `fk_read_reg_activity_score_teacher_id_idx` (`teacher_id` ASC),
  CONSTRAINT `fk_read_reg_activity_score_read_reg_activity1`
    FOREIGN KEY (`activity_id`)
    REFERENCES `edulink1_lpriority`.`read_reg_activity` (`activity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_activity_score_read_reg_master1`
    FOREIGN KEY (`title_id`)
    REFERENCES `edulink1_lpriority`.`read_reg_master` (`title_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_activity_score_read_reg_rubric1`
    FOREIGN KEY (`teacher_score`)
    REFERENCES `edulink1_lpriority`.`read_reg_rubric` (`score`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_activity_score_teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `edulink1_lpriority`.`teacher` (`teacher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_read_reg_activity_score_student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `edulink1_lpriority`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Data for table `edulink1_lpriority`.`read_reg_quiz`
-- -----------------------------------------------------

INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (1, 'que1');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (2, 'que2');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (3, 'que3');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (4, 'que4');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (5, 'que5');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (6 , 'que6');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (7, 'que7');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (8, 'que8');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (9, 'que9');
INSERT INTO `edulink1_lpriority`.`read_reg_quiz` (`question_num`, `quest`) VALUES (10, 'que10');

-- -----------------------------------------------------
-- Data for table `edulink1_lpriority`.`read_reg_activity`
-- -----------------------------------------------------

INSERT INTO `edulink1_lpriority`.`read_reg_activity` (`activity_id`, `actitvity`) VALUES (1, 'review');
INSERT INTO `edulink1_lpriority`.`read_reg_activity` (`activity_id`, `actitvity`) VALUES (2, 'create a quiz');
INSERT INTO `edulink1_lpriority`.`read_reg_activity` (`activity_id`, `actitvity`) VALUES (3, 'retell');
INSERT INTO `edulink1_lpriority`.`read_reg_activity` (`activity_id`, `actitvity`) VALUES (4, 'take a quiz');
INSERT INTO `edulink1_lpriority`.`read_reg_activity` (`activity_id`, `actitvity`) VALUES (5, 'upload a picture');
-- ------------------------------------------------------------------------
ALTER TABLE `edulink1_lpriority`.`read_reg_review` 
CHANGE COLUMN `rating` `rating` INT(11) NULL ,
ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `title_id` ASC);

ALTER TABLE `edulink1_lpriority`.`read_reg_review` 
CHANGE COLUMN `review_id` `review_id` BIGINT(20) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `edulink1_lpriority`.`read_reg_questions` 
ADD COLUMN `read_reg_questions_id` BIGINT(20) NOT NULL AUTO_INCREMENT AFTER `create_date`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`read_reg_questions_id`),
ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `question_num` ASC, `title_id` ASC);

ALTER TABLE `edulink1_lpriority`.`read_reg_questions` 
CHANGE COLUMN `read_reg_questions_id` `read_reg_questions_id` BIGINT(20) NOT NULL AUTO_INCREMENT FIRST;

ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
ADD COLUMN `read_reg_answers_id` BIGINT(20) NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`read_reg_answers_id`),
ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `title_id` ASC, `question_num` ASC);

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD COLUMN `read_reg_activity_score_id` BIGINT(20) NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`read_reg_activity_score_id`),
ADD UNIQUE INDEX `unique` (`student_id` ASC, `title_id` ASC, `activity_id` ASC);


ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
DROP FOREIGN KEY `fk_read_reg_answers_student_id`,
DROP FOREIGN KEY `fk_read_reg_answers_read_reg_quiz1`,
DROP FOREIGN KEY `fk_read_reg_answers_read_reg_master1`;
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
DROP COLUMN `question_num`,
DROP COLUMN `title_id`,
CHANGE COLUMN `student_id` `read_reg_questions_id` BIGINT(20) NOT NULL ,
ADD INDEX `fk_read_reg_que_id_idx` (`read_reg_questions_id` ASC),
DROP INDEX `fk_read_reg_answers_read_reg_quiz1_idx` ,
DROP INDEX `fk_read_reg_answers_read_reg_master1_idx` ,
DROP INDEX `unique_key` ;
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
ADD CONSTRAINT `fk_read_reg_que_id`
  FOREIGN KEY (`read_reg_questions_id`)
  REFERENCES `edulink1_lpriority`.`read_reg_questions` (`read_reg_questions_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
DROP FOREIGN KEY `fk_read_reg_activity_score_student_id`,
DROP FOREIGN KEY `fk_read_reg_activity_score_read_reg_master1`;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
DROP COLUMN `title_id`,
DROP COLUMN `student_id`,
ADD COLUMN `read_reg_questions_id` BIGINT(20) NOT NULL AFTER `read_reg_activity_score_id`,
ADD INDEX `fk_read_reg_que_id_xx_idx` (`read_reg_questions_id` ASC),
DROP INDEX `fk_read_reg_activity_score_read_reg_master1_idx` ,
DROP INDEX `unique` ;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD CONSTRAINT `fk_read_reg_que_id_xx`
  FOREIGN KEY (`read_reg_questions_id`)
  REFERENCES `edulink1_lpriority`.`read_reg_questions` (`read_reg_questions_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
DROP FOREIGN KEY `fk_read_reg_answers_student_que_id`;
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
CHANGE COLUMN `student_que_id` `student_id` BIGINT(20) NOT NULL ;
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
ADD CONSTRAINT `fk_read_reg_answers_student_que_id`
  FOREIGN KEY (`student_id`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD COLUMN `create_date` DATETIME NOT NULL AFTER `self_score`;

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
DROP FOREIGN KEY `fk_read_reg_que_id_xx`;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
CHANGE COLUMN `read_reg_questions_id` `student_id` BIGINT(20) NOT NULL ,
ADD COLUMN `title_id` BIGINT(20) NOT NULL AFTER `student_id`,
ADD INDEX `fk_read_reg_activity_score_student_id_idx` (`student_id` ASC),
ADD INDEX `fk_read_reg_activity_score_title_id_idx` (`title_id` ASC),
DROP INDEX `fk_read_reg_que_id_xx_idx` ;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD CONSTRAINT `fk_read_reg_activity_score_student_id`
  FOREIGN KEY (`student_id`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_read_reg_activity_score_title_id`
  FOREIGN KEY (`title_id`)
  REFERENCES `edulink1_lpriority`.`read_reg_master` (`title_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
CHANGE COLUMN `answer` `answer` VARCHAR(15) NULL DEFAULT NULL ;


ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
DROP FOREIGN KEY `fk_read_reg_answers_student_que_id`;
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
CHANGE COLUMN `student_id` `current_student_id` BIGINT(20) NOT NULL ;
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
ADD CONSTRAINT `fk_read_reg_answers_student_que_id`
  FOREIGN KEY (`current_student_id`)
  REFERENCES `edulink1_lpriority`.`student` (`student_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  -- Added by Lalitha on 3/2/2018
  
  CREATE TABLE `edulink1_lpriority`.`read_reg_page_range` (
  `read_reg_page_range_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `pages_from` INT(11) NOT NULL,
  `pages_to` INT(11) NULL,
  `range` INT(11) NOT NULL,
  PRIMARY KEY (`read_reg_page_range_id`));
  
  ALTER TABLE `edulink1_lpriority`.`read_reg_activity` 
ADD COLUMN `activity_value` INT(11) NOT NULL AFTER `actitvity`;


UPDATE `edulink1_lpriority`.`read_reg_activity` SET `activity_value`='5' WHERE `activity_id`='1';
UPDATE `edulink1_lpriority`.`read_reg_activity` SET `activity_value`='4' WHERE `activity_id`='2';
UPDATE `edulink1_lpriority`.`read_reg_activity` SET `activity_value`='2' WHERE `activity_id`='3';
UPDATE `edulink1_lpriority`.`read_reg_activity` SET `activity_value`='3' WHERE `activity_id`='4';
UPDATE `edulink1_lpriority`.`read_reg_activity` SET `activity_value`='3' WHERE `activity_id`='5';

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
DROP FOREIGN KEY `fk_read_reg_activity_score_read_reg_rubric1`;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
DROP INDEX `fk_read_reg_activity_score_read_reg_rubric1_idx` ;

ALTER TABLE `edulink1_lpriority`.`read_reg_rubric` 
CHANGE COLUMN `description` `description` VARCHAR(100) NOT NULL FIRST,
CHANGE COLUMN `score` `score` INT(11) NOT NULL ,
DROP PRIMARY KEY;


ALTER TABLE `edulink1_lpriority`.`read_reg_rubric` 
ADD COLUMN `read_reg_rubric_id` BIGINT(20) NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (`read_reg_rubric_id`);

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD INDEX `fk_read_reg_activity_score_teacher_score_idx` (`teacher_score` ASC),
ADD INDEX `fk_read_reg_activity_score_self_score_idx` (`self_score` ASC);
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD CONSTRAINT `fk_read_reg_activity_score_teacher_score`
  FOREIGN KEY (`teacher_score`)
  REFERENCES `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_read_reg_activity_score_self_score`
  FOREIGN KEY (`self_score`)
  REFERENCES `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD COLUMN `points_earned` INT(11) NULL AFTER `create_date`;

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
ADD COLUMN `page_range` BIGINT(20) NULL AFTER `create_date`,
ADD INDEX `fk_read_reg_master_page_range_idx` (`page_range` ASC);
ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
ADD CONSTRAINT `fk_read_reg_master_page_range`
  FOREIGN KEY (`page_range`)
  REFERENCES `edulink1_lpriority`.`read_reg_page_range` (`read_reg_page_range_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `edulink1_lpriority`.`read_reg_page_range` (`read_reg_page_range_id`, `pages_from`, `pages_to`, `range`) VALUES ('1', '1', '30', '1');
INSERT INTO `edulink1_lpriority`.`read_reg_page_range` (`read_reg_page_range_id`, `pages_from`, `pages_to`, `range`) VALUES ('2', '31', '100', '2');
INSERT INTO `edulink1_lpriority`.`read_reg_page_range` (`read_reg_page_range_id`, `pages_from`, `range`) VALUES ('3', '101', '3');

Delete from read_reg_rubric;

INSERT INTO `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`, `description`, `score`) VALUES ('1', 'Completes the activity with exceptional depth and meaning making', '4');
INSERT INTO `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`, `description`, `score`) VALUES ('2', 'Completes the activity with excellent depth and meaning making', '3');
INSERT INTO `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`, `description`, `score`) VALUES ('3', 'Completes the activity with satisfactory depth and meaning making', '2');
INSERT INTO `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`, `description`, `score`) VALUES ('4', 'Completes the activity with minimal depth and meaning making', '1');
INSERT INTO `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`, `description`, `score`) VALUES ('5', 'Does not complete activity.', '0');
  
UPDATE `edulink1_lpriority`.`read_reg_page_range` SET `pages_to`='999999' WHERE `read_reg_page_range_id`='3';


-- Added by Lalitha on 3/16/2018

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD UNIQUE INDEX `unique_key` (`student_id` ASC, `title_id` ASC, `activity_id` ASC);

-- Added by Lalitha on 3/23/2018

UPDATE `edulink1_lpriority`.`read_reg_rubric` SET `description`='Does not complete activity.', `score`='0' WHERE `read_reg_rubric_id`='1';
UPDATE `edulink1_lpriority`.`read_reg_rubric` SET `description`='Completes the activity with minimal depth and meaning making', `score`='1' WHERE `read_reg_rubric_id`='2';
UPDATE `edulink1_lpriority`.`read_reg_rubric` SET `description`='Completes the activity with excellent depth and meaning making', `score`='3' WHERE `read_reg_rubric_id`='4';
UPDATE `edulink1_lpriority`.`read_reg_rubric` SET `description`='Completes the activity with exceptional depth and meaning making', `score`='4' WHERE `read_reg_rubric_id`='5';

-- ----------------------------------------------------------------------
-- Added by Lalitha on 3/24/2018

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
DROP FOREIGN KEY `fk_read_reg_activity_score_self_score`;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
CHANGE COLUMN `self_score` `self_score` BIGINT(20) NULL ;
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD CONSTRAINT `fk_read_reg_activity_score_self_score`
  FOREIGN KEY (`self_score`)
  REFERENCES `edulink1_lpriority`.`read_reg_rubric` (`read_reg_rubric_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- ----------------------------------------------------------------------

  -- Added by Lalitha on 4/19/2018
  
  

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
	ADD COLUMN `grade_id` BIGINT(20) NULL AFTER `page_range`,
	ADD INDEX `fk_read_reg_grade_id_idx` (`grade_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
	ADD CONSTRAINT `fk_read_reg_grade_id`
	  FOREIGN KEY (`grade_id`)
	  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`read_reg_review` 
	ADD COLUMN `grade_id` BIGINT(20) NULL AFTER `review_date`,
	ADD INDEX `fk_read_reg_review_grade_id_idx` (`grade_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`read_reg_review` 
	ADD CONSTRAINT `fk_read_reg_review_grade_id`
	  FOREIGN KEY (`grade_id`)
	  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`read_reg_questions` 
	ADD COLUMN `grade_id` BIGINT(20) NULL AFTER `create_date`,
	ADD INDEX `fk_read_reg_questions_read_grade_id_idx` (`grade_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`read_reg_questions` 
	ADD CONSTRAINT `fk_read_reg_questions_read_grade_id`
	  FOREIGN KEY (`grade_id`)
	  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;
  
ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
	ADD COLUMN `grade_id` BIGINT(20) NULL AFTER `current_student_id`,
	ADD INDEX `fk_read_reg_grade_id_idx` (`grade_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`read_reg_answers` 
	ADD CONSTRAINT `fk_read_reg_grade_id1`
	  FOREIGN KEY (`grade_id`)
	  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
	ADD COLUMN `grade_id` BIGINT(20) NULL AFTER `points_earned`,
	ADD INDEX `fk_read_reg_activity_score_grade_id_idx` (`grade_id` ASC);
	ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
	ADD CONSTRAINT `fk_read_reg_activity_score_grade_id`
	  FOREIGN KEY (`grade_id`)
	  REFERENCES `edulink1_lpriority`.`grade` (`grade_id`)
	  ON DELETE NO ACTION
	  ON UPDATE NO ACTION;

-- Added by Lalitha on 5/10/2018 for Learning Indicator

INSERT INTO `edulink1_lpriority`.`legend_sub_criteria_district_wise`
(`district_id`,
`master_grade_id`,
`legend_sub_criteria_id`)
VALUES
(1,1,37),
(1,1,33),
(1,1,32),
(1,1,31),
(1,3,37),
(1,3,33),
(1,3,32),
(1,3,31),
(1,2,37),
(1,2,33),
(1,2,32),
(1,2,31),
(1,13,37),
(1,13,33),
(1,13,32),
(1,13,31),
(1,5,37),
(1,5,33),
(1,5,32),
(1,5,31),
(1,4,37),
(1,4,33),
(1,4,32),
(1,4,31),
(1,8,37),
(1,8,33),
(1,8,32),
(1,8,31),
(1,7,37),
(1,7,33),
(1,7,32),
(1,7,31),
(1,6,37),
(1,6,33),
(1,6,32),
(1,6,31),
(2,13,37),
(2,13,33),
(2,13,32),
(2,13,31),
(2,1,37),
(2,1,33),
(2,1,32),
(2,1,31),
(2,2,37),
(2,2,33),
(2,2,32),
(2,2,31),
(2,3,37),
(2,3,33),
(2,3,32),
(2,3,31),
(2,4,37),
(2,4,33),
(2,4,32),
(2,4,31),
(2,5,37),
(2,5,33),
(2,5,32),
(2,5,31),
(2,6,37),
(2,6,33),
(2,6,32),
(2,6,31);

DELETE FROM  learning_indicator_values  where 
sub_criteria_id in (37,
33,
32,
31) and learning_indicator_id in (Select learning_indicator_id from edulink1_lpriority.learning_indicator where legend_criteria_id!=6 );

-- Added by Martha 14/05/2018

ALTER TABLE `edulink1_lpriority`.`read_reg_questions` 
CHANGE COLUMN `question` `question` VARCHAR(255) NOT NULL ,
CHANGE COLUMN `option1` `option1` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `option2` `option2` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `option3` `option3` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `option4` `option4` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `answer` `answer` VARCHAR(100) NOT NULL ;


-- Added by Christopher 14/05/2018

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
ADD COLUMN `approved` VARCHAR(10) DEFAULT 'waiting' AFTER `grade_id`;

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
CHANGE COLUMN `page_range` `page_range` BIGINT(20) NULL DEFAULT NULL AFTER `number_of_pages`,
CHANGE COLUMN `create_date` `create_date` DATE NOT NULL AFTER `approved`,
ADD COLUMN `teacher_id` BIGINT(20) NULL AFTER `grade_id`;

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
ADD INDEX `fk_red_reg_teacher_id_x_idx` (`teacher_id` ASC);
ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
ADD CONSTRAINT `fk_red_reg_teacher_id_x`
  FOREIGN KEY (`teacher_id`)
  REFERENCES `edulink1_lpriority`.`teacher` (`teacher_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
 -- Added by Christopher 04/05/2018
  
ALTER TABLE `edulink1_lpriority`.`user_registration` 
ADD COLUMN `sso_username` VARCHAR(80) NULL DEFAULT NULL AFTER `email_id`; 

-- -------------------------------------------------------------------------------------------

-- Added by Christopher 14/05/2017
ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD COLUMN `review_id` BIGINT(20) NULL AFTER `read_reg_activity_score_id`;

ALTER TABLE `edulink1_lpriority`.`assignment` 
DROP INDEX `unique_key` ;

-- Added by Lalitha on 8/6/2018
UPDATE `edulink1_lpriority`.`legend_sub_criteria` SET `legend_sub_criteria_name`='Science' WHERE `legend_sub_criteria_id`='37';

-- Added by Anusuya on 22/8/2018

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
ADD COLUMN `teacher_comment` VARCHAR(200) NULL DEFAULT NULL AFTER `create_date`;

-- Added by Martha on 9/25/2018

ALTER TABLE `edulink1_lpriority`.`questions` 
ADD COLUMN `create_date` DATE NULL DEFAULT NULL AFTER `ques_status`,
ADD COLUMN `change_date` DATETIME NULL DEFAULT NULL AFTER `create_date`;

ALTER TABLE `edulink1_lpriority`.`unit` 
ADD COLUMN `create_date` DATE NULL DEFAULT NULL,
ADD COLUMN `change_date` DATETIME NULL DEFAULT NULL;

-- Added By Anusuya on 27/9/2018 ----------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
CHANGE COLUMN `teacher_comment` `teacher_comment` LONGTEXT NULL DEFAULT NULL ;

-- Added By Anusuya on 23/10/2018 ----------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD COLUMN `approved` VARCHAR(25) NULL DEFAULT 'waiting' AFTER `grade_id`;

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
CHANGE COLUMN `approved` `approve_status` VARCHAR(25) NULL DEFAULT 'waiting' ;

ALTER TABLE `edulink1_lpriority`.`read_reg_activity_score` 
ADD COLUMN `teacher_comment` VARCHAR(250) NULL DEFAULT NULL AFTER `approve_status`

-- Added By Anusuya on 8/2/2019 -------------------------------------------------------------------------

ALTER TABLE `edulink1_lpriority`.`read_reg_master` 
DROP INDEX `book_title_UNIQUE` ;

-- Added by Lalitha on 5/30/2019

UPDATE `edulink1_lpriority`.`academic_year` SET `end_date`='2019-10-30' WHERE `academic_year_id`='4';
UPDATE `edulink1_lpriority`.`academic_year` SET start_date='2016-09-08', `end_date`='2017-09-01' WHERE `academic_year_id`='1';

UPDATE `edulink1_lpriority`.`academic_year` SET start_date='2015-10-01', `end_date`='2016-08-30' WHERE `academic_year_id`='2';

UPDATE `edulink1_lpriority`.`academic_year` SET start_date='2017-08-25', `end_date`='2018-08-25' WHERE `academic_year_id`='3';

---- Added by Madhulika on 7/17/2019	
	ALTER TABLE `edulink1_lpriority`.`fluency_marks` 
ADD COLUMN `wcpm` INT(20) NULL AFTER `comment`;

-- Added By Anusuya on 10/5/2019 -----------------------------------------------------------------------

ALTER TABLE read_reg_activity_score MODIFY  create_date date;

-- Added by Lalitha on 10/23/2019

ALTER TABLE `edulink1_lpriority`.`k1_sets` 
CHANGE COLUMN `sets` `sets` LONGTEXT NOT NULL ;


-- Added by Lalitha on 10/29/2019

ALTER TABLE `edulink1_lpriority`.`k1_sets` 
DROP INDEX `unique_key` ,
ADD UNIQUE INDEX `unique_key` (`set_name` ASC, `cs_id` ASC);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;







