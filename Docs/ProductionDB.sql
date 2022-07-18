SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP DATABASE edulink1_lpriority;
CREATE DATABASE  IF NOT EXISTS `edulink1_lpriority`;
USE `edulink1_lpriority`;

--
-- Table structure for table `academic_grades`
--

CREATE TABLE `academic_grades` (
  `acedamic_grade_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acedamic_grade_name` varchar(20) NOT NULL,
  `score_from` int(11) NOT NULL,
  `score_to` int(11) NOT NULL,
  `academic_id` bigint(20) NOT NULL,
  PRIMARY KEY (`acedamic_grade_id`),
  UNIQUE KEY `acedamic_grade_name_UNIQUE` (`acedamic_grade_name`),
  KEY `fk_academic_id_idx` (`academic_id`),
  CONSTRAINT `fk_academic_id` FOREIGN KEY (`academic_id`) REFERENCES `academic_performance` (`academic_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `academic_grades`
--

INSERT INTO `academic_grades` (`acedamic_grade_id`, `acedamic_grade_name`, `score_from`, `score_to`, `academic_id`) VALUES (1,'A+',97,100,1),(2,'A',94,96,1),(3,'A-',90,93,1),(4,'B+',87,89,2),(5,'B',84,86,2),(6,'B-',80,83,2),(7,'C+',77,79,3),(8,'C',74,76,3),(9,'C-',70,73,3),(10,'D+',67,69,4),(11,'D',64,66,4),(12,'D-',60,63,4),(13,'F',50,59,5),(14,'I',40,49,5),(15,'N',30,39,5),(16,'P',0,29,5);

--
-- Table structure for table `academic_performance`
--

CREATE TABLE `academic_performance` (
  `academic_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `academic_level` varchar(20) NOT NULL,
  `academic_grade` varchar(1) NOT NULL,
  `academic_description` varchar(260) NOT NULL,
  PRIMARY KEY (`academic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `academic_performance`
--
INSERT INTO `academic_performance` (`academic_id`, `academic_level`, `academic_grade`, `academic_description`) VALUES (1,'Exceeding','A','The student consistently exceeds the performance standards for the grade-level.  The student with relative ease, grasps applies generalizes, and extends key concepts, processes, and skills consistently and independently.'),(2,'Meeting','B','The student consistently meets the performance standards for the grade-level.  The student, with limited errors, grasps key concepts, processes and skills for the grade-level and understands and applies them effectively.'),(3,'Inconsistent Progres','C','The student is progressing toward meeting the performance standard for the grade-level.  The student is beginning to grasp key concepts, processes and skills for the grade-level, but demonstrates inconsistent understanding and application of concepts.'),(4,'Limited Progress','D','The student is making some progress toward meeting the performance standard.  The student is not demonstrating understanding of grade-level key concepts, processes and skills and requires additional time and support.'),(5,'Not Met','F','The student has not yet met the performance standard.');

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `activity_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint(20) NOT NULL,
  `activity_desc` varchar(1000) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `FK_activity_2` (`lesson_id`),
  KEY `fk_user_reg_id2_idx` (`created_by`),
  CONSTRAINT `fk_create_by` FOREIGN KEY (`created_by`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(45) NOT NULL DEFAULT '',
  `state_id` bigint(20) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `zipcode` int(11) NOT NULL DEFAULT '0',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`),
  KEY `fk_state_id_idx` (`state_id`),
  CONSTRAINT `fk_state_id` FOREIGN KEY (`state_id`) REFERENCES `states` (`state_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `admin_teacher_reports`
--

CREATE TABLE `admin_teacher_reports` (
  `admin_teacher_report_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  `user_typeid` bigint(20) NOT NULL,
  PRIMARY KEY (`admin_teacher_report_id`),
  KEY `teacher_id_idx` (`teacher_id`),
  KEY `fk_user_typeid1_idx` (`user_typeid`),
  CONSTRAINT `fk_user_typeid1` FOREIGN KEY (`user_typeid`) REFERENCES `user` (`user_typeid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `announcements`
--

CREATE TABLE `announcements` (
  `announcement_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `annoncement_name` varchar(40) NOT NULL,
  `announce_description` varchar(70) NOT NULL,
  `announce_date` date NOT NULL,
  `school_id` bigint(20) NOT NULL,
  `contact_person` varchar(30) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`announcement_id`),
  UNIQUE KEY `announceId` (`announcement_id`),
  KEY `fk_school_id_idx` (`school_id`),
  CONSTRAINT `fk_announce_school_id` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assign_activity`
--

CREATE TABLE `assign_activity` (
  `assign_activity_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `assigned_date` date NOT NULL,
  `cs_id` bigint(20) NOT NULL,
  PRIMARY KEY (`assign_activity_id`),
  KEY `FK_assign_activity_2` (`activity_id`),
  KEY `fk_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assign_k1_tests`
--

CREATE TABLE `assign_k1_tests` (
  `assign_k1_test_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_id` bigint(20) NOT NULL DEFAULT '0',
  `marks_graded` varchar(300) DEFAULT NULL,
  `student_assignment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`assign_k1_test_id`),
  KEY `fk_set_id` (`set_id`),
  KEY `fk_student_assignment_id_idx` (`student_assignment_id`),
  CONSTRAINT `fk_k1_set_id` FOREIGN KEY (`set_id`) REFERENCES `k1_sets` (`k1_set_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_k1_student_test_id` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assign_lessons`
--

CREATE TABLE `assign_lessons` (
  `assign_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL,
  `lesson_id` bigint(20) NOT NULL,
  `assigned_date` date NOT NULL,
  `due_date` date NOT NULL,
  PRIMARY KEY (`assign_id`),
  UNIQUE KEY `assign_id` (`assign_id`),
  UNIQUE KEY `uk_cs_lesson_id` (`cs_id`,`lesson_id`),
  KEY `fk_lesson_id_idx` (`lesson_id`),
  KEY `fk_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_assign_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assign_lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assigned_ptasks`
--

CREATE TABLE `assigned_ptasks` (
  `assigned_task_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` bigint(20) NOT NULL,
  `performance_task_id` bigint(20) NOT NULL,
  `filepath` varchar(100) DEFAULT NULL,
  `writing` longtext,
  `calculations` longtext,
  `uploadarea` longtext,
  `audiofile` varchar(100) DEFAULT NULL,
  `chatcontents` longtext,
  `self_assessment_score` int(11) DEFAULT NULL,
  `teacher_comments` varchar(400) DEFAULT NULL,
  `teacher_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`assigned_task_id`),
  KEY `fk_student_assignment_id_idx` (`student_assignment_id`),
  KEY `fk_qusetion_id` (`performance_task_id`),
  CONSTRAINT `fk_qusetion_id` FOREIGN KEY (`performance_task_id`) REFERENCES `questions` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assignment`
--

CREATE TABLE `assignment` (
  `assignment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL,
  `lesson_id` bigint(20) DEFAULT NULL,
  `assignment_type_id` bigint(20) NOT NULL,
  `title` varchar(45) NOT NULL DEFAULT '',
  `assign_status` varchar(10) NOT NULL,
  `instructions` varchar(100) DEFAULT '',
  `date_assigned` date NOT NULL,
  `date_due` date NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `used_for` varchar(30) DEFAULT NULL,
  `benchmark_id` bigint(20) DEFAULT NULL,
  `performance_group_id` bigint(20) DEFAULT NULL,
  `test_type` varchar(20) DEFAULT NULL,
  `record_time` INT NULL DEFAULT 0,
  PRIMARY KEY (`assignment_id`),
  UNIQUE KEY `unique_key` (`title`,`cs_id`,`used_for`),
  KEY `fk_lesson_id_idx` (`lesson_id`),
  KEY `fk_assignment_type_id_idx` (`assignment_type_id`),
  KEY `fk_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_asngt_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_asngt_lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assignment_type_id` FOREIGN KEY (`assignment_type_id`) REFERENCES `assignment_type` (`assignment_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assignment_questions`
--

CREATE TABLE `assignment_questions` (
  `assignment_questions_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` bigint(20) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `answer` varchar(1500) DEFAULT NULL,
  `max_marks` int(11) DEFAULT NULL,
  `sec_marks` int(11) DEFAULT NULL,
  `teacher_comment` varchar(200) DEFAULT NULL,
  `b_audio_path` varchar(30) DEFAULT NULL,
  `b_fluencymarks` int(11) DEFAULT NULL,
  `b_quality_of_response_id` bigint(20) DEFAULT NULL,
  `b_retell_path` varchar(45) DEFAULT NULL,
  `b_count_of_errors` int(11) DEFAULT NULL,
  `errors_address` varchar(100) DEFAULT NULL,
  `words_read` int(11) DEFAULT NULL,
  `b_retellmarks` int(11) DEFAULT NULL,
  PRIMARY KEY (`assignment_questions_id`),
  KEY `fk_student_assignment_id_idx` (`student_assignment_id`),
  KEY `fk_question_id_idx` (`question_id`),
  KEY `fk_quality_of_response_id_idx` (`b_quality_of_response_id`),
  CONSTRAINT `fk_aq_quality_of_response_id` FOREIGN KEY (`b_quality_of_response_id`) REFERENCES `quality_of_response` (`qor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_aq_question_id` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_aq_student_assignment_id` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `assignment_type`
--

CREATE TABLE `assignment_type` (
  `assignment_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignment_type` varchar(50) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `used_for` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`assignment_type_id`),
  UNIQUE KEY `assignment_type_UNIQUE` (`assignment_type`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `assignment_type`
--

INSERT INTO `assignment_type` (`assignment_type_id`, `assignment_type`, `create_date`, `change_date`, `used_for`) VALUES (1,'Essay','2009-05-28','2015-07-24 10:43:13','all'),(2,'Short Que','2009-05-28','2015-07-24 10:43:13','all'),(3,'Multiple Choices','2009-05-28','2015-07-24 10:43:13','all'),(4,'Fill in the Blanks','2009-05-28','2015-07-24 10:43:13','all'),(5,'True or False','2009-05-28','2015-07-24 10:43:13','all'),(6,'others','2009-05-28','2015-07-24 10:43:13','all'),(7,'Sentence Structure','2009-05-28','2015-07-24 10:43:13','rti'),(8,'Fluency Reading','2009-05-28','2015-07-24 10:43:13','rti'),(9,'project work','2009-05-28','2013-07-04 03:20:51','classworks'),(10,'Lesson work','2009-05-28','2013-07-09 02:27:41','classworks'),(11,'Activity work','2009-05-28','2013-07-09 02:27:41','classworks'),(12,'Participat\r\nion','2009-05-28','2013-07-09 07:15:03','classworks'),(13,'Performance Task','2013-12-20','2015-07-24 10:43:13','assessments'),(14,'JAC Template','2014-05-15','2015-07-24 10:43:13','homeworks'),(15,'Early Literacy Letter','2014-05-15','2015-05-27 13:23:09','rti_early_literacy'),(16,'Early Literacy Word','2014-05-15','2015-05-27 13:23:09','rti_early_literacy'),(17,'Phonic Skill Test','2015-07-06','2015-07-06 13:23:09','Phonic_Skill_Test'),(18, 'Reading Fluency Learning Practice', '2016-01-25', '2016-02-08 16:29:23', 'RFLP');

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `attendance_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  `status` varchar(45) NOT NULL DEFAULT '',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`attendance_id`),
  KEY `FK_attendance_1` (`cs_id`),
  KEY `fk_student_id_idx` (`student_id`),
  CONSTRAINT `fk_atd_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_atd_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `benchmark_results`
--

CREATE TABLE `benchmark_results` (
  `benchmark_results_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` bigint(20) NOT NULL DEFAULT '0',
  `median_fluency_score` int(11) DEFAULT '0',
  `median_retell_score` int(11) DEFAULT '0',
  `sentence_structure_score` float(10,2) DEFAULT NULL,
  `quality_of_response_id` bigint(20) DEFAULT '0',
  `accuracy` float DEFAULT NULL,
  `teacher_notes` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`benchmark_results_id`),
  KEY `fk_quality_of_response_id_idx` (`quality_of_response_id`),
  KEY `fk_student_assignment_id_idx` (`student_assignment_id`),
  CONSTRAINT `fk_br_quality_of_response_id` FOREIGN KEY (`quality_of_response_id`) REFERENCES `quality_of_response` (`qor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_br_student_assignment_id` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `bm_categories`
--

CREATE TABLE `bm_categories` (
  `bm_category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bm_category` varchar(30) NOT NULL DEFAULT '',
  `bm_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`bm_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `bm_categories`
--

INSERT INTO `bm_categories` (`bm_category_id`, `bm_category`, `bm_name`) VALUES (1,'beginning','Fluency Reading1'),(2,'middle','Fluency Reading2'),(3,'end','Fluency Reading3');


--
-- Table structure for table `bm_ss_cut_off_marks`
--

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


--
-- Table structure for table `citizenship`
--

CREATE TABLE `citizenship` (
  `Citizenship_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Citizenship_name` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`Citizenship_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `citizenship`
--

INSERT INTO `citizenship` (`Citizenship_id`, `Citizenship_name`) VALUES (1,'excellent'),(2,'satisfactory'),(3,'not Satisfactory'),(4,'unsatisfactory');


--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `class_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(45) NOT NULL DEFAULT '',
  `school_id` bigint(20) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`class_id`),
  UNIQUE KEY `class_name_UNIQUE` (`class_name`,`school_id`),
  KEY `fk_school_id_idx` (`school_id`),
  CONSTRAINT `fk_school_id` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `class_actual_schedule`
--

CREATE TABLE `class_actual_schedule` (
  `cas_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL DEFAULT '0',
  `day_id` bigint(20) NOT NULL DEFAULT '0',
  `period_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`cas_id`),
  KEY `cs_id` (`cs_id`,`day_id`),
  KEY `cs_id_2` (`cs_id`,`day_id`),
  KEY `cs_id_3` (`cs_id`),
  KEY `day_id` (`day_id`),
  KEY `fk_period_id_idx` (`period_id`),
  CONSTRAINT `fk_day_id1` FOREIGN KEY (`day_id`) REFERENCES `days` (`day_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cas_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cas_period_id` FOREIGN KEY (`period_id`) REFERENCES `periods` (`period_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `class_status`
--

CREATE TABLE `class_status` (
  `cs_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `section_id` bigint(20) NOT NULL DEFAULT '0',
  `teacher_id` bigint(20) NOT NULL DEFAULT '0',
  `avail_status` varchar(45) DEFAULT NULL,
  `description` varchar(45) NOT NULL DEFAULT '',
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `editing_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cs_id`),
  KEY `FK_class_status_3` (`teacher_id`),
  KEY `fk_section_id_idx` (`section_id`),
  CONSTRAINT `fk_section_id` FOREIGN KEY (`section_id`) REFERENCES `section` (`section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_UNIQUE` (`comment`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`comment_id`, `comment`) VALUES (1,'Attendance affecting grade'),(26,'Behavior is improving'),(6,'Classwork incomplete'),(16,'Disruptive in class'),(8,'Does not follow directions'),(13,'Failed to meet requirements'),(30,'Follows requirements'),(31,'Good class input'),(25,'Helpful in class'),(7,'Homework incomplete'),(20,'Inattentive/uninvolved'),(15,'Inconsistent work habits'),(4,'Needs make-up test/lab/essay'),(23,'Needs required P.E. Uniform'),(22,'Needs weekly monitoring'),(3,'New to class'),(12,'Not working to ability'),(2,'Often absent on test/lab days'),(19,'Often falls asleep in class'),(32,'Outstanding study skills'),(24,'Please contact teacher'),(18,'Poor attitude'),(10,'Poor participation'),(5,'Poor test/quiz grades'),(14,'Poor work quality'),(29,'Positive attitude'),(28,'Puts forth extra effort'),(35,'Recommend CP per the terms of the signed syllabus/petition'),(11,'required materials not present'),(17,'Rude/irresponsible'),(33,'See Learning Priority assignments for grade details'),(27,'Stays focused and on-task'),(34,'Student is in danger of failing'),(21,'Suggest possible level change'),(9,'Weak study skills');

--
-- Table structure for table `compositechart`
--

CREATE TABLE `compositechart` (
  `composite_chart_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL,
  `grade_event_id` bigint(20) NOT NULL,
  `assignment_type_id` bigint(20) NOT NULL,
  `overallgrade` float NOT NULL DEFAULT '0',
  `nooftests` int(11) NOT NULL,
  `pointspertest` int(11) NOT NULL,
  PRIMARY KEY (`composite_chart_id`),
  KEY `fk_grade_event_id_idx` (`grade_event_id`),
  KEY `fk_assignment_type_id_idx` (`assignment_type_id`),
  KEY `fk_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_chrt_assignment_type_id` FOREIGN KEY (`assignment_type_id`) REFERENCES `assignment_type` (`assignment_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chrt_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chrt_grade_event_id` FOREIGN KEY (`grade_event_id`) REFERENCES `gradeevents` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `country`
--

CREATE TABLE `country` (
  `country_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(45) NOT NULL,
  `continent` varchar(45) NOT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `country_UNIQUE` (`country`)
) ENGINE=InnoDB AUTO_INCREMENT=895 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `country`
--

INSERT INTO `country` (`country_id`, `country`, `continent`) VALUES (4,'Afghanistan','AS'),(8,'Albania','EU'),(10,'Antarctica','AN'),(12,'Algeria','AF'),(16,'American Samoa','OC'),(20,'Andorra','EU'),(24,'Angola','AF'),(28,'Antigua and Barbuda','NA'),(31,'Azerbaijan','AS'),(32,'Argentina','SA'),(36,'Australia','OC'),(40,'Austria','EU'),(44,'Bahamas','NA'),(48,'Bahrain','AS'),(50,'Bangladesh','AS'),(51,'Armenia','AS'),(52,'Barbados','NA'),(56,'Belgium','EU'),(60,'Bermuda','NA'),(64,'Bhutan','AS'),(68,'Bolivia','SA'),(70,'Bosnia and Herzegovina','EU'),(72,'Botswana','AF'),(74,'Bouvet Island','AN'),(76,'Brazil','SA'),(84,'Belize','NA'),(86,'British Indian Ocean Territory','AS'),(90,'Solomon Islands','OC'),(92,'British Virgin Islands','NA'),(96,'Brunei','AS'),(100,'Bulgaria','EU'),(104,'Myanmar','AS'),(108,'Burundi','AF'),(112,'Belarus','EU'),(116,'Cambodia','AS'),(120,'Cameroon','AF'),(124,'Canada','NA'),(132,'Cape Verde','AF'),(136,'Cayman Islands','NA'),(140,'Central African Republic','AF'),(144,'Sri Lanka','AS'),(148,'Chad','AF'),(152,'Chile','SA'),(156,'China','AS'),(158,'Taiwan','AS'),(162,'Christmas Island','AS'),(166,'Cocos Islands','AS'),(170,'Colombia','SA'),(174,'Comoros','AF'),(175,'Mayotte','AF'),(178,'Republic of the Congo','AF'),(180,'Democratic Republic of the Congo','AF'),(184,'Cook Islands','OC'),(188,'Costa Rica','NA'),(191,'Croatia','EU'),(192,'Cuba','NA'),(196,'Cyprus','EU'),(203,'Czech Republic','EU'),(204,'Benin','AF'),(208,'Denmark','EU'),(212,'Dominica','NA'),(214,'Dominican Republic','NA'),(218,'Ecuador','SA'),(222,'El Salvador','NA'),(226,'Equatorial Guinea','AF'),(231,'Ethiopia','AF'),(232,'Eritrea','AF'),(233,'Estonia','EU'),(234,'Faroe Islands','EU'),(238,'Falkland Islands','SA'),(239,'South Georgia and the South Sandwich Islands','AN'),(242,'Fiji','OC'),(246,'Finland','EU'),(248,'Aland Islands','EU'),(250,'France','EU'),(254,'French Guiana','SA'),(258,'French Polynesia','OC'),(260,'French Southern Territories','AN'),(262,'Djibouti','AF'),(266,'Gabon','AF'),(268,'Georgia','AS'),(270,'Gambia','AF'),(275,'Palestinian Territory','AS'),(276,'Germany','EU'),(288,'Ghana','AF'),(292,'Gibraltar','EU'),(296,'Kiribati','OC'),(300,'Greece','EU'),(304,'Greenland','NA'),(308,'Grenada','NA'),(312,'Guadeloupe','NA'),(316,'Guam','OC'),(320,'Guatemala','NA'),(324,'Guinea','AF'),(328,'Guyana','SA'),(332,'Haiti','NA'),(334,'Heard Island and McDonald Islands','AN'),(336,'Vatican','EU'),(340,'Honduras','NA'),(344,'Hong Kong','AS'),(348,'Hungary','EU'),(352,'Iceland','EU'),(356,'India','AS'),(360,'Indonesia','AS'),(364,'Iran','AS'),(368,'Iraq','AS'),(372,'Ireland','EU'),(376,'Israel','AS'),(380,'Italy','EU'),(384,'Ivory Coast','AF'),(388,'Jamaica','NA'),(392,'Japan','AS'),(398,'Kazakhstan','AS'),(400,'Jordan','AS'),(404,'Kenya','AF'),(408,'North Korea','AS'),(410,'South Korea','AS'),(414,'Kuwait','AS'),(417,'Kyrgyzstan','AS'),(418,'Laos','AS'),(422,'Lebanon','AS'),(426,'Lesotho','AF'),(428,'Latvia','EU'),(430,'Liberia','AF'),(434,'Libya','AF'),(438,'Liechtenstein','EU'),(440,'Lithuania','EU'),(442,'Luxembourg','EU'),(446,'Macao','AS'),(450,'Madagascar','AF'),(454,'Malawi','AF'),(458,'Malaysia','AS'),(462,'Maldives','AS'),(466,'Mali','AF'),(470,'Malta','EU'),(474,'Martinique','NA'),(478,'Mauritania','AF'),(480,'Mauritius','AF'),(484,'Mexico','NA'),(492,'Monaco','EU'),(496,'Mongolia','AS'),(498,'Moldova','EU'),(499,'Montenegro','EU'),(500,'Montserrat','NA'),(504,'Morocco','AF'),(508,'Mozambique','AF'),(512,'Oman','AS'),(516,'Namibia','AF'),(520,'Nauru','OC'),(524,'Nepal','AS'),(528,'Netherlands','EU'),(530,'Netherlands Antilles','NA'),(533,'Aruba','NA'),(540,'New Caledonia','OC'),(548,'Vanuatu','OC'),(554,'New Zealand','OC'),(558,'Nicaragua','NA'),(562,'Niger','AF'),(566,'Nigeria','AF'),(570,'Niue','OC'),(574,'Norfolk Island','OC'),(578,'Norway','EU'),(580,'Northern Mariana Islands','OC'),(581,'United States Minor Outlying Islands','OC'),(583,'Micronesia','OC'),(584,'Marshall Islands','OC'),(585,'Palau','OC'),(586,'Pakistan','AS'),(591,'Panama','NA'),(598,'Papua New Guinea','OC'),(600,'Paraguay','SA'),(604,'Peru','SA'),(608,'Philippines','AS'),(612,'Pitcairn','OC'),(616,'Poland','EU'),(620,'Portugal','EU'),(624,'Guinea-Bissau','AF'),(626,'East Timor','OC'),(630,'Puerto Rico','NA'),(634,'Qatar','AS'),(638,'Reunion','AF'),(642,'Romania','EU'),(643,'Russia','EU'),(646,'Rwanda','AF'),(652,'Saint Barthélemy','NA'),(654,'Saint Helena','AF'),(659,'Saint Kitts and Nevis','NA'),(660,'Anguilla','NA'),(662,'Saint Lucia','NA'),(663,'Saint Martin','NA'),(666,'Saint Pierre and Miquelon','NA'),(670,'Saint Vincent and the Grenadines','NA'),(674,'San Marino','EU'),(678,'Sao Tome and Principe','AF'),(682,'Saudi Arabia','AS'),(686,'Senegal','AF'),(688,'Serbia','EU'),(690,'Seychelles','AF'),(694,'Sierra Leone','AF'),(702,'Singapore','AS'),(703,'Slovakia','EU'),(704,'Vietnam','AS'),(705,'Slovenia','EU'),(706,'Somalia','AF'),(710,'South Africa','AF'),(716,'Zimbabwe','AF'),(724,'Spain','EU'),(732,'Western Sahara','AF'),(736,'Sudan','AF'),(740,'Suriname','SA'),(744,'Svalbard and Jan Mayen','EU'),(748,'Swaziland','AF'),(752,'Sweden','EU'),(756,'Switzerland','EU'),(760,'Syria','AS'),(762,'Tajikistan','AS'),(764,'Thailand','AS'),(768,'Togo','AF'),(772,'Tokelau','OC'),(776,'Tonga','OC'),(780,'Trinidad and Tobago','NA'),(784,'United Arab Emirates','AS'),(788,'Tunisia','AF'),(792,'Turkey','AS'),(795,'Turkmenistan','AS'),(796,'Turks and Caicos Islands','NA'),(798,'Tuvalu','OC'),(800,'Uganda','AF'),(804,'Ukraine','EU'),(807,'Macedonia','EU'),(818,'Egypt','AF'),(826,'United Kingdom','EU'),(831,'Guernsey','EU'),(832,'Jersey','EU'),(833,'Isle of Man','EU'),(834,'Tanzania','AF'),(840,'United States','NA'),(850,'U.S. Virgin Islands','NA'),(854,'Burkina Faso','AF'),(855,'Kosovo','EU'),(858,'Uruguay','SA'),(860,'Uzbekistan','AS'),(862,'Venezuela','SA'),(876,'Wallis and Futuna','OC'),(882,'Samoa','OC'),(887,'Yemen','AS'),(891,'Serbia and Montenegro','EU'),(894,'Zambia','AF');

--
-- Table structure for table `days`
--

CREATE TABLE `days` (
  `day_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(10) NOT NULL DEFAULT '',
  `create_date` date NOT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`day_id`),
  UNIQUE KEY `UK_days_1` (`day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `days`
--

INSERT INTO `days` (`day_id`, `day`, `create_date`, `change_date`) VALUES (1,'mon','2009-05-29','2009-05-29 05:19:06'),(2,'tue','2009-05-29','2009-05-29 05:19:59'),(3,'wen','2009-05-29','2009-07-24 05:12:50'),(4,'thu','2009-05-29','2009-05-29 05:20:13'),(5,'fri','2009-05-29','2009-05-29 05:20:19'),(6,'sat','2009-05-29','2009-05-29 05:20:23'),(7,'sun','2009-05-29','2009-05-29 05:20:27');

--
-- Table structure for table `district`
--

CREATE TABLE `district` (
  `district_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(30) NOT NULL DEFAULT '',
  `no_schools` bigint(20) DEFAULT '0',
  `logo_link` varchar(50) DEFAULT '',
  `address` varchar(30) NOT NULL DEFAULT '',
  `state_id` bigint(20) DEFAULT NULL,
  `city` varchar(30) NOT NULL DEFAULT '',
  `phone_number` varchar(30) DEFAULT '',
  `fax_number` varchar(30) DEFAULT '',
  PRIMARY KEY (`district_id`),
  KEY `fk_state_ids` (`state_id`),
  CONSTRAINT `fk_state_ids` FOREIGN KEY (`state_id`) REFERENCES `states` (`state_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(45) NOT NULL,
  `description` varchar(70) NOT NULL,
  `announcement_date` date NOT NULL,
  `schedule_date` date NOT NULL,
  `last_date` date NOT NULL,
  `school_id` bigint(20) NOT NULL,
  `venue` varchar(30) NOT NULL,
  `event_time` varchar(30) NOT NULL,
  `contact_person` varchar(30) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `eventId` (`event_id`),
  KEY `fk_events_school_id_idx` (`school_id`),
  CONSTRAINT `fk_events_school_id` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `folders`
--

CREATE TABLE `folders` (
  `folder_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `foldername` varchar(40) NOT NULL,
  `foldertype` varchar(40) NOT NULL,
  `createddate` date DEFAULT NULL,
  PRIMARY KEY (`folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `grade`
--

CREATE TABLE `grade` (
  `grade_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `create_date` date NOT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `master_grades_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  UNIQUE KEY `fk_unique_grades` (`school_id`,`master_grades_id`),
  KEY `fk_master_grades_id_idx` (`master_grades_id`),
  CONSTRAINT `fk_master_grades_id` FOREIGN KEY (`master_grades_id`) REFERENCES `master_grades` (`master_grades_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `grade_classes`
--

CREATE TABLE `grade_classes` (
  `grade_class_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_id` bigint(20) NOT NULL DEFAULT '0',
  `class_id` bigint(20) NOT NULL DEFAULT '0',
  `status` varchar(20) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`grade_class_id`),
  UNIQUE KEY `unique_key` (`grade_id`,`class_id`,`status`),
  KEY `FK_grade_classes_2` (`class_id`),
  KEY `fk_grade_id_idx` (`grade_id`),
  CONSTRAINT `fk_class_id` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `grade_level`
--

CREATE TABLE `grade_level` (
  `grade_level_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_level_name` varchar(45) NOT NULL,
  PRIMARY KEY (`grade_level_id`),
  UNIQUE KEY `grade_level_name_UNIQUE` (`grade_level_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `grade_level`
--

INSERT INTO `grade_level` (`grade_level_id`, `grade_level_name`) VALUES (1,'above grade level'),(2,'at grade level'),(3,'below grade level');


--
-- Table structure for table `gradeevents`
--

CREATE TABLE `gradeevents` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_name_UNIQUE` (`event_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `gradeevents`
--

INSERT INTO `gradeevents` (`event_id`, `event_name`) VALUES (2,'assessments'),(3,'classworks'),(1,'homeworks');

--
-- Table structure for table `group_performance_temp`
--

CREATE TABLE `group_performance_temp` (
  `performance_group_students_id` bigint(20) NOT NULL,
  `assigned_task_id` bigint(20) NOT NULL,
  `writing_area_status` varchar(45) DEFAULT 'unlocked',
  `image_area_status` varchar(45) DEFAULT 'unlocked',
  `calculation_area_status` varchar(45) DEFAULT 'unlocked',
  `dim1_value` bigint(20) DEFAULT NULL,
  `dim2_value` bigint(20) DEFAULT NULL,
  `dim3_value` bigint(20) DEFAULT NULL,
  `dim4_value` bigint(20) DEFAULT NULL,
  `total` bigint(20) DEFAULT NULL,
  `chat_login_status` varchar(45) DEFAULT NULL,
  `permission_status` varchar(45) DEFAULT NULL,
  KEY `PRIMARY_KEY` (`performance_group_students_id`,`assigned_task_id`),
  KEY `fk__idx` (`assigned_task_id`),
  CONSTRAINT `fk_assigned_ptask_id` FOREIGN KEY (`assigned_task_id`) REFERENCES `assigned_ptasks` (`assigned_task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_per_group_student_id` FOREIGN KEY (`performance_group_students_id`) REFERENCES `performance_group_students` (`performance_group_students_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `homeroom_classes`
--

CREATE TABLE `homeroom_classes` (
  `homeroom_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `section_id` bigint(20) DEFAULT NULL,
  `teacher_id` bigint(20) NOT NULL DEFAULT '0',
  `period_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`homeroom_id`),
  KEY `fk_section_id_idx` (`section_id`),
  KEY `fk_teacher_id_idx` (`teacher_id`),
  KEY `fk_period_id_idx` (`period_id`),
  CONSTRAINT `fk_hrc_period_id` FOREIGN KEY (`period_id`) REFERENCES `periods` (`period_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_hrc_section_id` FOREIGN KEY (`section_id`) REFERENCES `section` (`section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_hrc_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `interest`
--

CREATE TABLE `interest` (
  `interest_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `interest` varchar(45) NOT NULL DEFAULT '',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`interest_id`),
  UNIQUE KEY `UK_interest_1` (`interest`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `interest`
--

INSERT INTO `interest` (`interest_id`, `interest`, `create_date`, `change_date`) VALUES (1,'areas of interest','2009-05-28','2009-05-28 06:24:14'),(2,'sports offered','2009-05-29','2009-05-29 04:41:02'),(3,'extra-curricular activities offered','2009-05-29','2009-05-29 04:42:17'),(4,'scholastic areas of interest','2009-05-29','2009-05-29 04:46:35'),(5,'personal areas of interest','2009-05-29','2009-05-29 04:47:00'),(6,'current subject areas','2009-05-29','2009-05-29 04:48:08'),(7,'edu-link subject areas','2009-05-29','2009-05-29 04:48:42'),(8,'sport activities','2009-05-29','2009-05-29 04:49:06');


--
-- Table structure for table `invitations`
--

CREATE TABLE `invitations` (
  `invitation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_id` bigint(20) NOT NULL DEFAULT '0',
  `invitee_email` varchar(45) NOT NULL DEFAULT '',
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `user_typeid` bigint(20) NOT NULL,
  `message` varchar(150) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`invitation_id`),
  KEY `FK_invitations_1` (`reg_id`),
  KEY `fk_user_typeid_idx` (`user_typeid`),
  CONSTRAINT `fk_inv_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_typeid` FOREIGN KEY (`user_typeid`) REFERENCES `user` (`user_typeid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `jac_question_file`
--

CREATE TABLE `jac_question_file` (
  `jac_question_file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint(20) NOT NULL,
  `reg_id` bigint(20) NOT NULL,
  `filename` varchar(200) NOT NULL,
  `used_for` varchar(45) NOT NULL,
  PRIMARY KEY (`jac_question_file_id`),
  KEY `lesson_id_idx` (`lesson_id`),
  KEY `fk_reg_id_idx` (`reg_id`),
  KEY `fk_used_for_idx` (`used_for`),
  CONSTRAINT `jac_lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `jac_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `jac_template`
--

CREATE TABLE `jac_template` (
  `jac_template_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `no_of_questions` bigint(20) NOT NULL,
  `title_name` varchar(45) NOT NULL,
  `jac_question_file_id` bigint(20) NOT NULL,
  PRIMARY KEY (`jac_template_id`),
  KEY `fk_jac_question_file_id_idx` (`jac_question_file_id`),
  CONSTRAINT `fk_jac_question_file_id` FOREIGN KEY (`jac_question_file_id`) REFERENCES `jac_question_file` (`jac_question_file_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `joinsentencenbenchmark`
--

CREATE TABLE `joinsentencenbenchmark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL,
  `benchmark_id` bigint(20) NOT NULL,
  `benchmark_assignment_id` bigint(20) DEFAULT NULL,
  `sentencestructure_assignment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sentencestructure_assignment_id_idx` (`sentencestructure_assignment_id`),
  KEY `fk_benchmark_assignment_id_idx` (`benchmark_assignment_id`),
  KEY `fk_jsb_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_sentencestructure_assignment_id` FOREIGN KEY (`sentencestructure_assignment_id`) REFERENCES `assignment` (`assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_benchmark_assignment_id` FOREIGN KEY (`benchmark_assignment_id`) REFERENCES `assignment` (`assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_jsb_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `k1_sets`
--

CREATE TABLE `k1_sets` (
  `k1_set_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set` varchar(200) NOT NULL,
  `set_type` varchar(20) NOT NULL,
  `master_grade_id` bigint(20) NOT NULL,
  `part_type` varchar(20) DEFAULT 'Null',
  `set_name` varchar(10) DEFAULT NULL,
  `created_date` date NOT NULL,
  PRIMARY KEY (`k1_set_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;


--
-- Dumping data for table `k1_sets`
--

INSERT INTO `k1_sets` (`k1_set_id`, `set`, `set_type`, `master_grade_id`, `part_type`, `set_name`, `created_date`) VALUES (1,'M S F L R N H V W Z B C D G P T J K Y X Q I O A U E','Letter',13,'PART I','Upper Case','2015-06-01'),(2,'m s f l r n h v w z b c d g p t j k y x q i o a u e','Letter',13,'PART II','Lower Case','2015-06-01'),(3,'I see my like a to and go is here for have said the play she are he this from you that was with they of his as first me one look we find many','Word',13,'K1Words','Words','2015-06-01'),(4,'the of a to you was are they from have','Word',1,'PART I','A','2015-06-01'),(5,'one what were there your their sold do many some','Word',1,'PART I','B','2015-06-01'),(6,'would other into two could been who people only find','Word',1,'PART I','C','2015-06-01'),(7,'water very word where most through another come work does','Word',1,'PART I','D','2015-06-01'),(8,'put again old great should give something thought both oftern','Word',1,'PART I','E','2015-06-01'),(9,'world want different together school once enough sometimes four head','Word',1,'PART I','F','2015-06-01'),(10,'above kind almost earth mother country father eyes today sure','Word',1,'PART II','G','2015-06-01'),(11,'told young heard answer against learn toward money move done','Word',1,'PART II','H','2015-06-01'),(12,'group true half cold course front early brought though become','Word',1,'PART II','I','2015-06-01'),(13,'behind ready built hold piece talk blue instead either friend','Word',1,'PART II','J','2015-06-01'),(14,'already warm mind says heavy beautiful everyone watch hour carry','Word',1,'PART II','K','2015-06-01'),(15,'although heart wild weather someone won field gold build walk','Word',1,'PART II','L','2015-06-01');

--
-- Table structure for table `k1_test_marks`
--

CREATE TABLE `k1_test_marks` (
  `k1_test_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_id` bigint(20) NOT NULL DEFAULT '0',
  `student_id` bigint(20) NOT NULL DEFAULT '0',
  `test_status` varchar(20) NOT NULL,
  `graded_status` varchar(20) NOT NULL,
  `marks_graded` varchar(60) DEFAULT NULL,
  `score` bigint(20) DEFAULT NULL,
  `submission_date` date DEFAULT NULL,
  PRIMARY KEY (`k1_test_id`),
  KEY `fk_set_id` (`set_id`),
  KEY `fk_student_id` (`student_id`),
  CONSTRAINT `fk_set_id` FOREIGN KEY (`set_id`) REFERENCES `k1_tests` (`set_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_k1_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8;


--
-- Table structure for table `k1_tests`
--

CREATE TABLE `k1_tests` (
  `set_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL DEFAULT '0',
  `sets` varchar(200) DEFAULT NULL,
  `test_date` date DEFAULT NULL,
  `test_type` varchar(10) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `set_type` varchar(10) NOT NULL,
  PRIMARY KEY (`set_id`),
  KEY `fk_k1_section_id` (`cs_id`),
  CONSTRAINT `fk_k1_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;


--
-- Table structure for table `lesson`
--

CREATE TABLE `lesson` (
  `lesson_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_id` bigint(20) NOT NULL,
  `lesson_no` int(11) DEFAULT NULL,
  `lesson_name` varchar(45) NOT NULL DEFAULT '',
  `lesson_desc` varchar(300) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`lesson_id`),
  UNIQUE KEY `unit_id` (`unit_id`,`lesson_name`),
  KEY `fk_unit_id_idx` (`unit_id`),
  KEY `fk_created_by3_idx` (`created_by`),
  CONSTRAINT `fk_created_by3` FOREIGN KEY (`created_by`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_id` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`unit_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `lesson_paths`
--

CREATE TABLE `lesson_paths` (
  `lesson_path_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint(20) NOT NULL,
  `lesson_path` varchar(300) NOT NULL,
  `uploaded_by` bigint(20) NOT NULL,
  PRIMARY KEY (`lesson_path_id`),
  KEY `fk_lesson_id_idx` (`lesson_id`),
  CONSTRAINT `fk_paths_lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `master_grades`
--

CREATE TABLE `master_grades` (
  `master_grades_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_name` varchar(45) NOT NULL DEFAULT '',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`master_grades_id`),
  UNIQUE KEY `UK_grade_1` (`grade_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `master_grades`
--

INSERT INTO `master_grades` (`master_grades_id`, `grade_name`, `create_date`, `change_date`) VALUES (1,'First','2009-07-16','2009-07-15 23:14:53'),(2,'Second','2009-07-16','2009-07-15 23:15:04'),(3,'Third','2009-07-16','2009-07-15 23:15:13'),(4,'Fourth','2009-07-16','2009-07-15 23:15:22'),(5,'Fifth','2009-07-16','2009-07-15 23:15:35'),(6,'Sixth','2009-07-16','2009-07-15 23:15:44'),(7,'Seventh','2009-07-16','2009-07-15 23:15:54'),(8,'Eighth','2009-07-16','2009-09-08 05:01:55'),(9,'Ninth','2009-07-16','2009-07-15 23:16:13'),(10,'Tenth','2009-07-16','2009-07-15 23:16:22'),(11,'Eleventh','2009-07-16','2009-07-15 23:16:31'),(12,'Twelfth','2009-07-16','2009-08-10 05:36:12'),(13,'Kindergarten','2009-07-16','2009-07-15 23:14:41'),(14,'Others','2009-07-16','2009-07-15 23:16:55');


--
-- Table structure for table `minutes`
--

CREATE TABLE `minutes` (
  `min_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `minute` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`min_id`),
  UNIQUE KEY `minute_UNIQUE` (`minute`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `minutes`
--

INSERT INTO `minutes` (`min_id`, `minute`) VALUES (1,'00'),(2,'01'),(3,'02'),(4,'03'),(5,'04'),(6,'05'),(7,'06'),(8,'07'),(9,'08'),(10,'09'),(11,'10'),(12,'11'),(13,'12'),(14,'13'),(15,'14'),(16,'15'),(17,'16'),(18,'17'),(19,'18'),(20,'19'),(21,'20'),(22,'21'),(23,'22'),(24,'23'),(25,'24'),(26,'25'),(27,'26'),(28,'27'),(29,'28'),(30,'29'),(31,'30'),(32,'31'),(33,'32'),(34,'33'),(35,'34'),(36,'35'),(37,'36'),(38,'37'),(39,'38'),(40,'39'),(41,'40'),(42,'41'),(43,'42'),(44,'43'),(45,'44'),(46,'45'),(47,'46'),(48,'47'),(49,'48'),(50,'49'),(51,'50'),(52,'51'),(53,'52'),(54,'53'),(55,'54'),(56,'55'),(57,'56'),(58,'57'),(59,'58'),(60,'59');


--
-- Table structure for table `parent_lastseen`
--

CREATE TABLE `parent_lastseen` (
  `parent_lastseen_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `last_logged_in` datetime DEFAULT NULL,
  `last_logged_out` datetime DEFAULT NULL,
  `last_seen_feature` varchar(100) DEFAULT '---',
  PRIMARY KEY (`parent_lastseen_id`),
  KEY `fk_parent_last_id` (`parent_id`),
  CONSTRAINT `fk_parent_last_id` FOREIGN KEY (`parent_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `performance_group_students`
--

CREATE TABLE `performance_group_students` (
  `performance_group_students_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `performance_group_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`performance_group_students_id`),
  KEY `fk_performance_group` (`performance_group_id`),
  KEY `fk_per_student_id` (`student_id`),
  CONSTRAINT `fk_performance_group` FOREIGN KEY (`performance_group_id`) REFERENCES `performancetask_groups` (`performance_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_per_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `performancetask_groups`
--

CREATE TABLE `performancetask_groups` (
  `performance_group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) DEFAULT NULL,
  `group_name` varchar(50) DEFAULT NULL,
  `MaxNo_of_students` int(11) DEFAULT NULL,
  `MinNo_of_students` int(11) DEFAULT NULL,
  PRIMARY KEY (`performance_group_id`),
  UNIQUE KEY `fk_unique_group` (`cs_id`,`group_name`),
  KEY `fk_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_ptg_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `periods`
--

CREATE TABLE `periods` (
  `period_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `period_name` varchar(20) NOT NULL DEFAULT '',
  `start_time` time NOT NULL DEFAULT '00:00:00',
  `end_time` time NOT NULL DEFAULT '00:00:00',
  `grade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`period_id`),
  KEY `fk_grade_id_idx` (`grade_id`),
  CONSTRAINT `fk_periods_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `phonic_groups`
--

CREATE TABLE `phonic_groups` (
  `group_id` bigint(10) NOT NULL,
  `phonic_section_id` bigint(10) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  KEY `fk_phonic_section_id_idx` (`phonic_section_id`),
  CONSTRAINT `fk_phonic_section_id` FOREIGN KEY (`phonic_section_id`) REFERENCES `phonic_sections` (`phonic_section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Dumping data for table `phonic_groups`
--

INSERT INTO `phonic_groups` (`group_id`, `phonic_section_id`, `title`, `question`) VALUES 
(1,1,'Continuous Sounds','m s f l r n h v w z'), 
(2,1,'Stop Sounds','b d g p t j k y x q'), 
(3,2,'Short Vowels','i o a u e'), 
(4,2,'Long Vowels','i o a u e'), 
(5,3,'Short','van mop fell sun fix'),
(6,3,'cd','chin bath when shut song'),
(7,3,'c blend','left must frog flip snack'),
(8,3,'final e','fine hope cute kite rake'),
(9,3,'lvd','soap leak pain feed ray'),
(10,3,'r-c','burn fork dirt part serve'),
(11,3,'ovd','coin soon round lawn foot'),
(12,3,'inflec','rested stayed passes making ripped'),
(13,3,'affixes','distrust useful unfair hardship nonsense'),
(14,3,'2-syl','silent ladder napkin polite cactus'),
(15,3,'3+syl','volcano potato electric frequently combination'),
(16,3,'4+syl','unflavored intelligent organization convertible representative');

--
-- Table structure for table `phonic_sections`
--

CREATE TABLE `phonic_sections` (
  `phonic_section_id` bigint(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`phonic_section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Dumping data for table `phonic_sections`
--

INSERT INTO `phonic_sections` (`phonic_section_id`, `name`) VALUES (1,'Consonant sounds and names'),(2,'Short vowel sounds'),(3,'Reading words with phonics patterns');


--
-- Table structure for table `ptask_files`
--

CREATE TABLE `ptask_files` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `performance_task_id` bigint(20) NOT NULL,
  `filename` varchar(1000) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  PRIMARY KEY (`file_id`),
  KEY `fk_performance_taks_id_idx` (`performance_task_id`),
  CONSTRAINT `fk_performance_taks_id` FOREIGN KEY (`performance_task_id`) REFERENCES `questions` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `quality_of_response`
--

CREATE TABLE `quality_of_response` (
  `qor_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `response` varchar(100) NOT NULL,
  PRIMARY KEY (`qor_id`),
  UNIQUE KEY `response_UNIQUE` (`response`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `quality_of_response`
--

INSERT INTO `quality_of_response` (`qor_id`, `response`) VALUES (1,'provides 2 or fewer details'),(2,'provides 3 or more details'),(3,'provides 3 or more details in a meaningful sequence'),(4,'provides 3 or more details in a meaningful sequence that captures a main idea');


--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint(20) NOT NULL,
  `assignment_type_id` bigint(20) NOT NULL,
  `question` LONGTEXT DEFAULT NULL,
  `answer` varchar(50) DEFAULT NULL,
  `option1` varchar(100) DEFAULT NULL,
  `option2` varchar(100) DEFAULT NULL,
  `option3` varchar(100) DEFAULT NULL,
  `option4` varchar(100) DEFAULT NULL,
  `option5` varchar(100) DEFAULT NULL,
  `used_for` varchar(20) NOT NULL,
  `created_by` int(11) NOT NULL,
  `sub_question_id` bigint(20) DEFAULT NULL,
  `b_textdirections` LONGTEXT DEFAULT NULL,
  `b_voicedirectionspath` varchar(100) DEFAULT NULL,
  `b_retelldirectionspath` varchar(30) DEFAULT NULL,
  `b_title` varchar(100) DEFAULT NULL,
  `b_grade_level` varchar(20) DEFAULT NULL,
  `pt_name` varchar(100) DEFAULT NULL,
  `pt_subject_area` LONGTEXT DEFAULT NULL,
  `pt_directions` LONGTEXT DEFAULT NULL,
  `jac_template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `lesson_id_idx` (`lesson_id`),
  KEY `sub_question_id_idx` (`sub_question_id`),
  KEY `assignment_type_id` (`assignment_type_id`),
  KEY `fk_jac_template_id_idx` (`jac_template_id`),
  CONSTRAINT `assignment_type_id` FOREIGN KEY (`assignment_type_id`) REFERENCES `assignment_type` (`assignment_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_jac_template_id` FOREIGN KEY (`jac_template_id`) REFERENCES `jac_template` (`jac_template_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sub_question_id` FOREIGN KEY (`sub_question_id`) REFERENCES `sub_questions` (`sub_question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `register_for_class`
--

CREATE TABLE `register_for_class` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) DEFAULT NULL,
  `section_id` bigint(20) DEFAULT NULL,
  `rti_group_id` bigint(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT '',
  `desktop_status` varchar(20) DEFAULT NULL,
  `class_status` varchar(30) NOT NULL DEFAULT 'alive',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `performance_group_id` bigint(20) DEFAULT NULL,
  `grade_class_id` bigint(20) NOT NULL DEFAULT '0',
  `grade_level_id` bigint(20) DEFAULT NULL,
  `teacher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`student_id`,`grade_class_id`,`status`,`class_status`),
  KEY `fk_cs_id_idx` (`cs_id`),
  KEY `fk_student_id_idx` (`student_id`),
  KEY `fk_rti_group_id_idx` (`rti_group_id`),
  KEY `fk_section_id_idx` (`section_id`),
  KEY `fk_performance_group_id_idx` (`performance_group_id`),
  KEY `FK_grade_class_id_2` (`grade_class_id`),
  KEY `fk_rfc_teacher_id _idx` (`teacher_id`),
  KEY `fk_rfc_grade_level_id` (`grade_level_id`),
  CONSTRAINT `FK_grade_class_id_2` FOREIGN KEY (`grade_class_id`) REFERENCES `grade_classes` (`grade_class_id`),
  CONSTRAINT `FK_grade_level_id_2` FOREIGN KEY (`grade_level_id`) REFERENCES `grade_level` (`grade_level_id`),
  CONSTRAINT `fk_rfc_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_performance_group_id` FOREIGN KEY (`performance_group_id`) REFERENCES `performancetask_groups` (`performance_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_rti_group_id` FOREIGN KEY (`rti_group_id`) REFERENCES `rti_groups` (`rti_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_section_id` FOREIGN KEY (`section_id`) REFERENCES `section` (`section_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rfc_teacher_id ` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  `homework_perc` float NOT NULL,
  `assignment_perc` float NOT NULL,
  `performance_perc` float NOT NULL,
  `acedamic_grade_id` bigint(20) NOT NULL,
  `Citizenship_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  `presentdays` int(11) NOT NULL,
  `absentdays` int(11) NOT NULL,
  `excusedabsentdays` int(11) NOT NULL,
  `tardydays` int(11) NOT NULL,
  `excusedtardydays` int(11) NOT NULL,
  `release_date` date DEFAULT NULL,
  `parent_comments` varchar(200) DEFAULT NULL,
  `parent_comments_date` date DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  KEY `fk_comment_id_idx` (`comment_id`),
  KEY `fk_citizenship_id_idx` (`Citizenship_id`),
  KEY `fk_academic_grade_id_idx` (`acedamic_grade_id`),
  KEY `fk_student_id_idx` (`student_id`),
  KEY `fk_cs_id_idx` (`cs_id`),
  CONSTRAINT `fk_rpt_academic_grade_id` FOREIGN KEY (`acedamic_grade_id`) REFERENCES `academic_grades` (`acedamic_grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_citizenship_id` FOREIGN KEY (`Citizenship_id`) REFERENCES `citizenship` (`Citizenship_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rpt_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `rti_groups`
--

CREATE TABLE `rti_groups` (
  `rti_group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rti_group_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`rti_group_id`),
  UNIQUE KEY `rti_group_name_UNIQUE` (`rti_group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `rti_groups`
--

INSERT INTO `rti_groups` (`rti_group_id`, `rti_group_name`) VALUES (2,'Comprehension sub group'),(1,'Fluency sub group'),(3,'Grade level sub group');

--
-- Table structure for table `rubric`
--

CREATE TABLE `rubric` (
  `rubric_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dimension1` varchar(1000) DEFAULT NULL,
  `dimension2` varchar(1000) DEFAULT NULL,
  `dimension3` varchar(1000) DEFAULT NULL,
  `dimension4` varchar(1000) DEFAULT NULL,
  `score` varchar(45) DEFAULT NULL,
  `dimension1_total` int(11) DEFAULT NULL,
  `dimension2_total` int(11) DEFAULT NULL,
  `dimension3_total` int(11) DEFAULT NULL,
  `dimension4_total` int(11) DEFAULT NULL,
  `rubric_scaling_id` bigint(20) DEFAULT NULL,
  `rubric_type_id` bigint(20) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `perf_task_quest_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rubric_id`),
  KEY `fk_rubric_scaling_id_idx` (`rubric_scaling_id`),
  KEY `fk_rubric_type_id_idx` (`rubric_type_id`),
  KEY `fk_rubric_perf_task_ques_id_idx` (`perf_task_quest_id`),
  CONSTRAINT `fk_rubric_perf_task_ques_id` FOREIGN KEY (`perf_task_quest_id`) REFERENCES `questions` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_scaling_id` FOREIGN KEY (`rubric_scaling_id`) REFERENCES `rubric_scalings` (`rubric_scaling_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rubric_type_id` FOREIGN KEY (`rubric_type_id`) REFERENCES `rubric_types` (`rubric_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `rubric_scalings`
--

CREATE TABLE `rubric_scalings` (
  `rubric_scaling_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rubric_scaling` int(5) NOT NULL,
  PRIMARY KEY (`rubric_scaling_id`),
  UNIQUE KEY `rubric_scaling_UNIQUE` (`rubric_scaling`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `rubric_scalings`
--

INSERT INTO `rubric_scalings` (`rubric_scaling_id`, `rubric_scaling`) VALUES (1,3),(2,4),(3,5),(4,6);


--
-- Table structure for table `rubric_score`
--

CREATE TABLE `rubric_score` (
  `rubric_score_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rubric_score` int(11) NOT NULL,
  PRIMARY KEY (`rubric_score_id`),
  UNIQUE KEY `rubric_score_UNIQUE` (`rubric_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `rubric_types`
--

CREATE TABLE `rubric_types` (
  `rubric_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rubric_type` varchar(50) NOT NULL,
  PRIMARY KEY (`rubric_type_id`),
  UNIQUE KEY `rubric_type_UNIQUE` (`rubric_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `rubric_types`
--

INSERT INTO `rubric_types` (`rubric_type_id`, `rubric_type`) VALUES (3,'General Analytic'),(1,'General Holistic'),(4,'Specific Analytic'),(2,'Specific Holistic');


--
-- Table structure for table `rubric_values`
--

CREATE TABLE `rubric_values` (
  `rubric_values_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_type_id` bigint(20) NOT NULL,
  `assigned_task_id` bigint(20) NOT NULL,
  `dimension1_value` bigint(20) NOT NULL,
  `dimension2_value` bigint(20) NOT NULL,
  `dimension3_value` bigint(20) NOT NULL,
  `dimension4_value` bigint(20) NOT NULL,
  `total_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`rubric_values_id`),
  KEY `fk_rubric_score_id_idx` (`user_type_id`),
  KEY `fk_rubric_id_idx` (`assigned_task_id`),
  CONSTRAINT `fk_assigned_task_id` FOREIGN KEY (`assigned_task_id`) REFERENCES `assigned_ptasks` (`assigned_task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_type_id` FOREIGN KEY (`user_type_id`) REFERENCES `user` (`user_typeid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `school`
--

CREATE TABLE `school` (
  `school_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(45) NOT NULL DEFAULT '',
  `school_type_id` bigint(20) NOT NULL,
  `school_level_id` bigint(20) NOT NULL,
  `no_of_students` int(11) NOT NULL DEFAULT '0',
  `city` varchar(45) NOT NULL DEFAULT '',
  `state_id` bigint(20) NOT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `fax_number` varchar(45) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `registration_date` date NOT NULL,
  `logo_link` varchar(60) DEFAULT NULL,
  `class_strength` int(11) DEFAULT NULL,
  `leveling` varchar(10) DEFAULT NULL,
  `gender_equity` varchar(10) DEFAULT NULL,
  `promot_startdate` date DEFAULT NULL,
  `promot_enddate` date DEFAULT NULL,
  `school_abbr` varchar(50) DEFAULT NULL,
  `district_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`school_id`),
  UNIQUE KEY `UK_school_1` (`school_name`,`school_type_id`,`school_level_id`,`city`,`state_id`),
  KEY `fk_school_state_id_idx` (`state_id`),
  KEY `fk_school_type_id_idx` (`school_type_id`),
  KEY `fk_school_level_id_idx` (`school_level_id`),
  KEY `fk_school_dist_id` (`district_id`),
  CONSTRAINT `fk_school_dist_id` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`),
  CONSTRAINT `fk_school_level_id` FOREIGN KEY (`school_level_id`) REFERENCES `school_level` (`school_level_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_state_id` FOREIGN KEY (`state_id`) REFERENCES `states` (`state_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_type_id` FOREIGN KEY (`school_type_id`) REFERENCES `school_type` (`school_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


--
-- Table structure for table `school_days`
--

CREATE TABLE `school_days` (
  `school_days_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day_id` bigint(20) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `school_id` bigint(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`school_days_id`),
  KEY `fk_day_id_idx` (`day_id`),
  KEY `FK_school_id_2` (`school_id`),
  CONSTRAINT `fk_day_id` FOREIGN KEY (`day_id`) REFERENCES `days` (`day_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_school_id_2` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `school_level`
--

CREATE TABLE `school_level` (
  `school_level_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_level_name` varchar(45) NOT NULL,
  PRIMARY KEY (`school_level_id`),
  UNIQUE KEY `school_level_name_UNIQUE` (`school_level_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;


--
-- Dumping data for table `school_level`
--

INSERT INTO `school_level` (`school_level_id`, `school_level_name`) VALUES (1,'Elementary'),(4,'High School'),(5,'Home School'),(3,'Junior High School'),(2,'Middle School'),(6,'Other');

--
-- Table structure for table `school_schedule`
--

CREATE TABLE `school_schedule` (
  `school_schedule_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) NOT NULL,
  `no_of_days` int(11) NOT NULL DEFAULT '5',
  `no_of_class_periods` int(11) NOT NULL,
  `period_range` int(11) NOT NULL,
  `passing_time` int(11) NOT NULL,
  `home_room_time` int(11) NOT NULL,
  `school_start_time` time NOT NULL,
  `school_end_time` time NOT NULL,
  `no_of_teachers` int(11) NOT NULL,
  `no_of_sections` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`school_schedule_id`),
  UNIQUE KEY `unique_school_id` (`school_id`),
  KEY `fk_school_id_idx` (`school_id`),
  CONSTRAINT `fk_sched_school_id` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `school_type`
--

CREATE TABLE `school_type` (
  `school_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `school_type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`school_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


--
-- Dumping data for table `school_type`
--

INSERT INTO `school_type` (`school_type_id`, `school_type_name`) VALUES (1,'Public'),(2,'Private'),(3,'Chartered'),(4,'Home'),(5,'Other');


--
-- Table structure for table `section`
--

CREATE TABLE `section` (
  `section_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_class_id` bigint(20) NOT NULL,
  `grade_level_id` bigint(20) NOT NULL,
  `section` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`section_id`),
  KEY `fk_grade_class_id_idx` (`grade_class_id`),
  KEY `fk_grade_level_id_idx` (`grade_level_id`),
  CONSTRAINT `fk_grade_class_id` FOREIGN KEY (`grade_class_id`) REFERENCES `grade_classes` (`grade_class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_level_id` FOREIGN KEY (`grade_level_id`) REFERENCES `grade_level` (`grade_level_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `security`
--

CREATE TABLE `security` (
  `security_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_id` bigint(20) NOT NULL DEFAULT '0',
  `sec_question_id` bigint(20) DEFAULT NULL,
  `answer` varchar(45) DEFAULT NULL,
  `verification_code` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`security_id`),
  UNIQUE KEY `UK_security_1` (`reg_id`) USING BTREE,
  KEY `FK_security_2` (`sec_question_id`),
  CONSTRAINT `fk_sec_ques_id` FOREIGN KEY (`sec_question_id`) REFERENCES `security_question` (`security_question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sec_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


--
-- Table structure for table `security_question`
--

CREATE TABLE `security_question` (
  `security_question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(45) NOT NULL DEFAULT '',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`security_question_id`) USING BTREE,
  UNIQUE KEY `UK_sec_question_1` (`question`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `security_question`
--

INSERT INTO `security_question` (`security_question_id`, `question`, `create_date`, `change_date`) VALUES (1,'what is your pet name?','2009-06-02','2009-06-01 23:42:26'),(2,'what is your favourite dish?','2009-06-02','2009-06-01 23:42:46'),(3,'what is your first school name?','2009-06-02','2009-06-01 23:43:17'),(4,'what is your mothers maiden name?','2009-06-02','2009-06-01 23:43:44');


--
-- Table structure for table `states`
--

CREATE TABLE `states` (
  `state_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_id` bigint(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`state_id`),
  KEY `subregion_region_id` (`country_id`),
  CONSTRAINT `states_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3880 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `states`
--

INSERT INTO `states` (`state_id`, `country_id`, `name`) VALUES (1,4,'Badakhshan'),(2,4,'Badghis Province'),(3,4,'Baghlān'),(4,4,'Bāmīān'),(5,4,'Farah'),(6,4,'Faryab Province'),(7,4,'Ghaznī'),(8,4,'Ghowr'),(9,4,'Helmand Province'),(10,4,'Herat Province'),(11,4,'Kabul'),(12,4,'Kāpīsā'),(13,4,'Lowgar'),(14,4,'Nangarhār'),(15,4,'Nīmrūz'),(16,4,'Orūzgān'),(17,4,'Kandahār'),(18,4,'Kunduz Province'),(19,4,'Takhār'),(20,4,'Vardak'),(21,4,'Zabul Province'),(22,4,'Paktīkā'),(23,4,'Balkh'),(24,4,'Jowzjān'),(25,4,'Samangān'),(26,4,'Sar-e Pol'),(27,4,'Konar'),(28,4,'Laghmān'),(29,4,'Paktia Province'),(30,4,'Khowst'),(31,4,'Nūrestān'),(32,4,'Parvān'),(33,4,'Dāykondī'),(34,4,'Panjshīr'),(35,8,'Berat'),(36,8,'Dibër'),(37,8,'Durrës'),(38,8,'Elbasan'),(39,8,'Fier'),(40,8,'Gjirokastër'),(41,8,'Korçë'),(42,8,'Kukës'),(43,8,'Lezhë'),(44,8,'Shkodër'),(45,8,'Tiranë'),(46,8,'Vlorë'),(47,12,'Alger'),(48,12,'Batna'),(49,12,'Constantine'),(50,12,'Médéa'),(51,12,'Mostaganem'),(52,12,'Oran'),(53,12,'Saïda'),(54,12,'Sétif'),(55,12,'Tiaret'),(56,12,'Tizi Ouzou'),(57,12,'Tlemcen'),(58,12,'Bejaïa'),(59,12,'Biskra'),(60,12,'Blida'),(61,12,'Bouira'),(62,12,'Djelfa'),(63,12,'Guelma'),(64,12,'Jijel'),(65,12,'Laghouat'),(66,12,'Mascara'),(67,12,'Mʼsila'),(68,12,'Oum el Bouaghi'),(69,12,'Sidi Bel Abbès'),(70,12,'Skikda'),(71,12,'Tébessa'),(72,12,'Adrar'),(73,12,'Aïn Defla'),(74,12,'Aïn Temouchent'),(75,12,'Annaba'),(76,12,'Béchar'),(77,12,'Bordj Bou Arréridj'),(78,12,'Boumerdes'),(79,12,'Chlef'),(80,12,'El Bayadh'),(81,12,'El Oued'),(82,12,'El Tarf'),(83,12,'Ghardaïa'),(84,12,'Illizi'),(85,12,'Khenchela'),(86,12,'Mila'),(87,12,'Naama النعامة'),(88,12,'Ouargla'),(89,12,'Relizane'),(90,12,'Souk Ahras'),(91,12,'Tamanghasset'),(92,12,'Tindouf'),(93,12,'Tipaza'),(94,12,'Tissemsilt'),(95,16,'American Samoa'),(96,20,'Parròquia de Canillo'),(97,20,'Parròquia dʼEncamp'),(98,20,'Parròquia de la Massana'),(99,20,'Parròquia dʼOrdino'),(100,20,'Parròquia de Sant Julià de Lòria'),(101,20,'Parròquia dʼAndorra la Vella'),(102,20,'Parròquia dʼEscaldes-Engordany'),(103,24,'Benguela'),(104,24,'Bié'),(105,24,'Cabinda'),(106,24,'Cuando Cubango'),(107,24,'Cuanza Norte'),(108,24,'Cuanza Sul'),(109,24,'Cunene'),(110,24,'Huambo'),(111,24,'Huíla'),(112,24,'Luanda'),(113,24,'Malanje'),(114,24,'Namibe'),(115,24,'Moxico'),(116,24,'Uíge'),(117,24,'Zaire'),(118,24,'Lunda Norte'),(119,24,'Lunda Sul'),(120,24,'Bengo'),(121,28,'Redonda'),(122,28,'Barbuda'),(123,28,'Saint George'),(124,28,'Saint John'),(125,28,'Saint Mary'),(126,28,'Saint Paul'),(127,28,'Saint Peter'),(128,28,'Saint Philip'),(129,31,'Abşeron'),(130,31,'Ağcabǝdi'),(131,31,'Ağdam'),(132,31,'Ağdaş'),(133,31,'Ağstafa'),(134,31,'Ağsu'),(135,31,'Əli Bayramli'),(136,31,'Astara'),(137,31,'Baki'),(138,31,'Balakǝn'),(139,31,'Bǝrdǝ'),(140,31,'Beylǝqan'),(141,31,'Bilǝsuvar'),(142,31,'Cǝbrayıl'),(143,31,'Cǝlilabad'),(144,31,'Daşkǝsǝn'),(145,31,'Dǝvǝçi'),(146,31,'Füzuli'),(147,31,'Gǝdǝbǝy'),(148,31,'Gǝncǝ'),(149,31,'Goranboy'),(150,31,'Göyçay'),(151,31,'Hacıqabul'),(152,31,'İmişli'),(153,31,'İsmayıllı'),(154,31,'Kǝlbǝcǝr'),(155,31,'Kürdǝmir'),(156,31,'Laçın'),(157,31,'Lǝnkǝran'),(158,31,'Lǝnkǝran Şǝhǝri'),(159,31,'Lerik'),(160,31,'Masallı'),(161,31,'Mingǝcevir'),(162,31,'Naftalan'),(163,31,'Nakhichevan'),(164,31,'Neftçala'),(165,31,'Oğuz'),(166,31,'Qǝbǝlǝ'),(167,31,'Qǝx'),(168,31,'Qazax'),(169,31,'Qobustan'),(170,31,'Quba'),(171,31,'Qubadlı'),(172,31,'Qusar'),(173,31,'Saatlı'),(174,31,'Sabirabad'),(175,31,'Şǝki'),(176,31,'Şǝki'),(177,31,'Salyan'),(178,31,'Şamaxı'),(179,31,'Şǝmkir'),(180,31,'Samux'),(181,31,'Siyǝzǝn'),(182,31,'Sumqayit'),(183,31,'Şuşa'),(184,31,'Şuşa Şəhəri'),(185,31,'Tǝrtǝr'),(186,31,'Tovuz'),(187,31,'Ucar'),(188,31,'Xaçmaz'),(189,31,'Xankǝndi'),(190,31,'Xanlar'),(191,31,'Xızı'),(192,31,'Xocalı'),(193,31,'Xocavǝnd'),(194,31,'Yardımlı'),(195,31,'Yevlax'),(196,31,'Yevlax'),(197,31,'Zǝngilan'),(198,31,'Zaqatala'),(199,31,'Zǝrdab'),(200,32,'Buenos Aires'),(201,32,'Catamarca'),(202,32,'Chaco'),(203,32,'Chubut'),(204,32,'Córdoba'),(205,32,'Corrientes'),(206,32,'Distrito Federal'),(207,32,'Entre Ríos'),(208,32,'Formosa'),(209,32,'Jujuy'),(210,32,'La Pampa'),(211,32,'La Rioja'),(212,32,'Mendoza'),(213,32,'Misiones'),(214,32,'Neuquén'),(215,32,'Río Negro'),(216,32,'Salta'),(217,32,'San Juan'),(218,32,'San Luis'),(219,32,'Santa Cruz'),(220,32,'Santa Fe'),(221,32,'Santiago del Estero'),(222,32,'Tierra del Fuego, Antártida e Islas del Atlán'),(223,32,'Tucumán'),(224,36,'Australian Capital Territory'),(225,36,'New South Wales'),(226,36,'Northern Territory'),(227,36,'Queensland'),(228,36,'South Australia'),(229,36,'Tasmania'),(230,36,'Victoria'),(231,36,'Western Australia'),(232,40,'Burgenland'),(233,40,'Carinthia'),(234,40,'Lower Austria'),(235,40,'Upper Austria'),(236,40,'Salzburg'),(237,40,'Styria'),(238,40,'Tyrol'),(239,40,'Vorarlberg'),(240,40,'Vienna'),(241,44,'Bimini'),(242,44,'Cat Island'),(243,44,'Inagua'),(244,44,'Long Island'),(245,44,'Mayaguana'),(246,44,'Ragged Island'),(247,44,'Harbour Island, Eleuthera'),(248,44,'North Abaco'),(249,44,'Acklins'),(250,44,'City of Freeport, Grand Bahama'),(251,44,'South Andros'),(252,44,'Hope Town, Abaco'),(253,44,'Mangrove Cay, Andros'),(254,44,'Mooreʼs Island, Abaco'),(255,44,'Rum Cay'),(256,44,'North Andros'),(257,44,'North Eleuthera'),(258,44,'South Eleuthera'),(259,44,'South Abaco'),(260,44,'San Salvador'),(261,44,'Berry Islands'),(262,44,'Black Point, Exuma'),(263,44,'Central Abaco'),(264,44,'Central Andros'),(265,44,'Central Eleuthera'),(266,44,'Crooked Island'),(267,44,'East Grand Bahama'),(268,44,'Exuma'),(269,44,'Grand Cay, Abaco'),(270,44,'Spanish Wells, Eleuthera'),(271,44,'West Grand Bahama'),(272,48,'Southern Governate'),(273,48,'Northern Governate'),(274,48,'Capital Governate'),(275,48,'Central Governate'),(276,48,'Muharraq Governate'),(277,50,'BG80'),(278,50,'Dhaka'),(279,50,'Khulna'),(280,50,'Rājshāhi'),(281,50,'Chittagong'),(282,50,'Barisāl'),(283,50,'Sylhet'),(284,51,'Aragatsotn'),(285,51,'Ararat'),(286,51,'Armavir'),(287,51,'Gegharkʼunikʼ'),(288,51,'Kotaykʼ'),(289,51,'Lorri'),(290,51,'Shirak'),(291,51,'Syunikʼ'),(292,51,'Tavush'),(293,51,'Vayotsʼ Dzor'),(294,51,'Yerevan'),(295,52,'Christ Church'),(296,52,'Saint Andrew'),(297,52,'Saint George'),(298,52,'Saint James'),(299,52,'Saint John'),(300,52,'Saint Joseph'),(301,52,'Saint Lucy'),(302,52,'Saint Michael'),(303,52,'Saint Peter'),(304,52,'Saint Philip'),(305,52,'Saint Thomas'),(306,56,'Bruxelles-Capitale'),(307,56,'Flanders'),(308,56,'Wallonia'),(309,60,'Devonshire'),(310,60,'Hamilton (parish)'),(311,60,'Hamilton (city)'),(312,60,'Paget'),(313,60,'Pembroke'),(314,60,'Saint Georgeʼs (parish)'),(315,60,'Saint Georgeʼs (city)'),(316,60,'Sandys'),(317,60,'Smithʼs'),(318,60,'Southampton'),(319,60,'Warwick'),(320,64,'Bumthang'),(321,64,'Chhukha'),(322,64,'Chirang'),(323,64,'Daga'),(324,64,'Geylegphug'),(325,64,'Ha'),(326,64,'Lhuntshi'),(327,64,'Mongar'),(328,64,'Paro District'),(329,64,'Pemagatsel'),(330,64,'Samchi'),(331,64,'Samdrup Jongkhar District'),(332,64,'Shemgang'),(333,64,'Tashigang'),(334,64,'Thimphu'),(335,64,'Tongsa'),(336,64,'Wangdi Phodrang'),(337,68,'Chuquisaca'),(338,68,'Cochabamba'),(339,68,'El Beni'),(340,68,'La Paz'),(341,68,'Oruro'),(342,68,'Pando'),(343,68,'Potosí'),(344,68,'Santa Cruz'),(345,68,'Tarija'),(346,70,'Federation of Bosnia and Herzegovina'),(347,70,'Republika Srpska'),(348,70,'Brčko'),(349,72,'Central'),(350,72,'Chobe'),(351,72,'Ghanzi'),(352,72,'Kgalagadi'),(353,72,'Kgatleng'),(354,72,'Kweneng'),(355,72,'Ngamiland'),(356,72,'North East'),(357,72,'South East'),(358,72,'Southern'),(359,72,'North West'),(360,76,'Acre'),(361,76,'Alagoas'),(362,76,'Amapá'),(363,76,'Estado do Amazonas'),(364,76,'Bahia'),(365,76,'Ceará'),(366,76,'Distrito Federal'),(367,76,'Espírito Santo'),(368,76,'Fernando de Noronha'),(369,76,'Goias'),(370,76,'Mato Grosso do Sul'),(371,76,'Maranhão'),(372,76,'Mato Grosso'),(373,76,'Minas Gerais'),(374,76,'Pará'),(375,76,'Paraíba'),(376,76,'Paraná'),(377,76,'Pernambuco'),(378,76,'Piauí'),(379,76,'State of Rio de Janeiro'),(380,76,'Rio Grande do Norte'),(381,76,'Rio Grande do Sul'),(382,76,'Rondônia'),(383,76,'Roraima'),(384,76,'Santa Catarina'),(385,76,'São Paulo'),(386,76,'Sergipe'),(387,76,'Estado de Goiás'),(388,76,'Pernambuco'),(389,76,'Tocantins'),(390,84,'Belize'),(391,84,'Cayo'),(392,84,'Corozal'),(393,84,'Orange Walk'),(394,84,'Stann Creek'),(395,84,'Toledo'),(396,86,'British Indian Ocean Territory'),(397,90,'Malaita'),(398,90,'Western'),(399,90,'Central'),(400,90,'Guadalcanal'),(401,90,'Isabel'),(402,90,'Makira'),(403,90,'Temotu'),(404,90,'Central Province'),(405,90,'Choiseul'),(406,90,'Rennell and Bellona'),(407,90,'Rennell and Bellona'),(408,92,'British Virgin Islands'),(409,96,'Belait'),(410,96,'Brunei and Muara'),(411,96,'Temburong'),(412,96,'Tutong'),(413,100,'Burgas'),(414,100,'Grad'),(415,100,'Khaskovo'),(416,100,'Lovech'),(417,100,'Mikhaylovgrad'),(418,100,'Plovdiv'),(419,100,'Razgrad'),(420,100,'Sofiya'),(421,100,'Varna'),(422,100,'Blagoevgrad'),(423,100,'Burgas'),(424,100,'Dobrich'),(425,100,'Gabrovo'),(426,100,'Oblast Sofiya-Grad'),(427,100,'Khaskovo'),(428,100,'Kŭrdzhali'),(429,100,'Kyustendil'),(430,100,'Lovech'),(431,100,'Montana'),(432,100,'Pazardzhit'),(433,100,'Pernik'),(434,100,'Pleven'),(435,100,'Plovdiv'),(436,100,'Razgrad'),(437,100,'Ruse'),(438,100,'Shumen'),(439,100,'Silistra'),(440,100,'Sliven'),(441,100,'Smolyan'),(442,100,'Sofiya'),(443,100,'Stara Zagora'),(444,100,'Tŭrgovishte'),(445,100,'Varna'),(446,100,'Veliko Tŭrnovo'),(447,100,'Vidin'),(448,100,'Vratsa'),(449,100,'Yambol'),(450,104,'Rakhine State'),(451,104,'Chin State'),(452,104,'Ayeyarwady'),(453,104,'Kachin State'),(454,104,'Kayin State'),(455,104,'Kayah State'),(456,104,'Magwe'),(457,104,'Mandalay'),(458,104,'Pegu'),(459,104,'Sagain'),(460,104,'Shan State'),(461,104,'Tanintharyi'),(462,104,'Mon State'),(463,104,'Rangoon'),(464,104,'Magway'),(465,104,'Bago'),(466,104,'Yangon'),(467,108,'Bujumbura'),(468,108,'Bubanza'),(469,108,'Bururi'),(470,108,'Cankuzo'),(471,108,'Cibitoke'),(472,108,'Gitega'),(473,108,'Karuzi'),(474,108,'Kayanza'),(475,108,'Kirundo'),(476,108,'Makamba'),(477,108,'Muyinga'),(478,108,'Ngozi'),(479,108,'Rutana'),(480,108,'Ruyigi'),(481,108,'Muramvya'),(482,108,'Mwaro'),(483,112,'Brestskaya Voblastsʼ'),(484,112,'Homyelʼskaya Voblastsʼ'),(485,112,'Hrodzyenskaya Voblastsʼ'),(486,112,'Mahilyowskaya Voblastsʼ'),(487,112,'Horad Minsk'),(488,112,'Minskaya Voblastsʼ'),(489,112,'Vitsyebskaya Voblastsʼ'),(490,116,'Krŏng Preăh Seihânŭ'),(491,116,'Kâmpóng Cham'),(492,116,'Kâmpóng Chhnăng'),(493,116,'Khétt Kâmpóng Spœ'),(494,116,'Kâmpóng Thum'),(495,116,'Kândal'),(496,116,'Kaôh Kŏng'),(497,116,'Krâchéh'),(498,116,'Môndól Kiri'),(499,116,'Phnum Penh'),(500,116,'Poŭthĭsăt'),(501,116,'Preăh Vihéar'),(502,116,'Prey Vêng'),(503,116,'Stœ̆ng Trêng'),(504,116,'Svay Riĕng'),(505,116,'Takêv'),(506,116,'Kâmpôt'),(507,116,'Phnum Pénh'),(508,116,'Rôtânăh Kiri'),(509,116,'Siĕm Réab'),(510,116,'Bantéay Méan Cheăy'),(511,116,'Kêb'),(512,116,'Ŏtdâr Méan Cheăy'),(513,116,'Preăh Seihânŭ'),(514,116,'Bătdâmbâng'),(515,116,'Palĭn'),(516,120,'Est'),(517,120,'Littoral'),(518,120,'North-West Province'),(519,120,'Ouest'),(520,120,'South-West Province'),(521,120,'Adamaoua'),(522,120,'Centre'),(523,120,'Extreme-Nord'),(524,120,'North Province'),(525,120,'South Province'),(526,124,'Alberta'),(527,124,'British Columbia'),(528,124,'Manitoba'),(529,124,'New Brunswick'),(530,124,'Newfoundland and Labrador'),(531,124,'Nova Scotia'),(532,124,'Ontario'),(533,124,'Prince Edward Island'),(534,124,'Quebec'),(535,124,'Saskatchewan'),(536,124,'Yukon'),(537,124,'Northwest Territories'),(538,124,'Nunavut'),(539,132,'Boa Vista'),(540,132,'Brava'),(541,132,'Maio'),(542,132,'Paul'),(543,132,'Praia'),(544,132,'Ribeira Grande'),(545,132,'Sal'),(546,132,'Santa Catarina   '),(547,132,'São Nicolau'),(548,132,'São Vicente'),(549,132,'Tarrafal '),(550,132,'Mosteiros'),(551,132,'Praia'),(552,132,'Santa Catarina'),(553,132,'Santa Cruz'),(554,132,'São Domingos'),(555,132,'São Filipe'),(556,132,'São Miguel'),(557,132,'Tarrafal'),(558,136,'Creek'),(559,136,'Eastern'),(560,136,'Midland'),(561,136,'South Town'),(562,136,'Spot Bay'),(563,136,'Stake Bay'),(564,136,'West End'),(565,136,'Western'),(566,140,'Bamingui-Bangoran'),(567,140,'Basse-Kotto'),(568,140,'Haute-Kotto'),(569,140,'Mambéré-Kadéï'),(570,140,'Haut-Mbomou'),(571,140,'Kémo'),(572,140,'Lobaye'),(573,140,'Mbomou'),(574,140,'Nana-Mambéré'),(575,140,'Ouaka'),(576,140,'Ouham'),(577,140,'Ouham-Pendé'),(578,140,'Vakaga'),(579,140,'Nana-Grébizi'),(580,140,'Sangha-Mbaéré'),(581,140,'Ombella-Mpoko'),(582,140,'Bangui'),(583,144,'Central'),(584,144,'North Central'),(585,144,'North Eastern'),(586,144,'North Western'),(587,144,'Sabaragamuwa'),(588,144,'Southern'),(589,144,'Uva'),(590,144,'Western'),(591,148,'Batha'),(592,148,'Biltine'),(593,148,'Borkou-Ennedi-Tibesti'),(594,148,'Chari-Baguirmi'),(595,148,'Guéra'),(596,148,'Kanem'),(597,148,'Lac'),(598,148,'Logone Occidental'),(599,148,'Logone Oriental'),(600,148,'Mayo-Kébbi'),(601,148,'Moyen-Chari'),(602,148,'Ouaddaï'),(603,148,'Salamat'),(604,148,'Tandjilé'),(605,152,'Vaaraíso'),(606,152,'Aisén del General Carlos Ibáñez del Campo'),(607,152,'Antofagasta'),(608,152,'Araucanía'),(609,152,'Atacama'),(610,152,'Bío-Bío'),(611,152,'Coquimbo'),(612,152,'Libertador General Bernardo OʼHiggins'),(613,152,'Los Lagos'),(614,152,'Magallanes y Antártica Chilena'),(615,152,'Maule'),(616,152,'Región Metropolitana'),(617,152,'Tarapaca'),(618,152,'Los Lagos'),(619,152,'Tarapacá'),(620,152,'Región de Arica y Parinacota'),(621,152,'Región de Los Ríos'),(622,156,'Anhui'),(623,156,'Zhejiang'),(624,156,'Jiangxi'),(625,156,'Jiangsu'),(626,156,'Jilin'),(627,156,'Qinghai'),(628,156,'Fujian'),(629,156,'Heilongjiang'),(630,156,'Henan'),(631,156,'disputed'),(632,156,'Hebei'),(633,156,'Hunan Province'),(634,156,'Hubei'),(635,156,'Xinjiang'),(636,156,'Xizang'),(637,156,'Gansu'),(638,156,'Guangxi'),(639,156,'Guizhou'),(640,156,'Liaoning Province'),(641,156,'Inner Mongolia'),(642,156,'Ningxia'),(643,156,'Beijing'),(644,156,'Shanghai'),(645,156,'Shanxi'),(646,156,'Shandong'),(647,156,'Shaanxi'),(648,156,'Tianjin'),(649,156,'Yunnan Province'),(650,156,'Guangdong'),(651,156,'Hainan Province'),(652,156,'Sichuan'),(653,156,'Chongqing'),(654,156,'PF99'),(655,158,'Fukien'),(656,158,'Kaohsiung'),(657,158,'Taipei'),(658,158,'Taiwan'),(659,162,'Christmas Island'),(660,166,'Cocos (Keeling) Islands'),(661,170,'Amazonas'),(662,170,'Antioquia'),(663,170,'Arauca'),(664,170,'Atlántico'),(665,170,'Bolívar'),(666,170,'Boyacá'),(667,170,'Caldas'),(668,170,'Caquetá'),(669,170,'Cauca'),(670,170,'Cesar'),(671,170,'Chocó'),(672,170,'Córdoba'),(673,170,'Guaviare'),(674,170,'Guainía'),(675,170,'Huila'),(676,170,'La Guajira'),(677,170,'Magdalena'),(678,170,'Meta'),(679,170,'Nariño'),(680,170,'Norte de Santander'),(681,170,'Putumayo'),(682,170,'Quindío'),(683,170,'Risaralda'),(684,170,'Archipiélago de San Andrés, Providencia y San'),(685,170,'Santander'),(686,170,'Sucre'),(687,170,'Tolima'),(688,170,'Valle del Cauca'),(689,170,'Vaupés'),(690,170,'Vichada'),(691,170,'Casanare'),(692,170,'Cundinamarca'),(693,170,'Bogota D.C.'),(694,170,'Bolívar'),(695,170,'Boyacá'),(696,170,'Caldas'),(697,170,'Magdalena'),(698,174,'Anjouan'),(699,174,'Grande Comore'),(700,174,'Mohéli'),(701,175,'Mayotte'),(702,178,'Bouenza'),(703,178,'CF03'),(704,178,'Kouilou'),(705,178,'Lékoumou'),(706,178,'Likouala'),(707,178,'Niari'),(708,178,'Plateaux'),(709,178,'Sangha'),(710,178,'Pool'),(711,178,'Brazzaville'),(712,178,'Cuvette'),(713,178,'Cuvette-Ouest'),(714,178,'Pointe-Noire'),(715,180,'Bandundu'),(716,180,'Équateur'),(717,180,'Kasaï-Occidental'),(718,180,'Kasaï-Oriental'),(719,180,'Katanga'),(720,180,'Kinshasa'),(721,180,'Bas-Congo'),(722,180,'Orientale'),(723,180,'Maniema'),(724,180,'Nord-Kivu'),(725,180,'Sud-Kivu'),(726,184,'Cook Islands'),(727,188,'Alajuela'),(728,188,'Cartago'),(729,188,'Guanacaste'),(730,188,'Heredia'),(731,188,'Limón'),(732,188,'Puntarenas'),(733,188,'San José'),(734,191,'Bjelovarsko-Bilogorska'),(735,191,'Brodsko-Posavska'),(736,191,'Dubrovačko-Neretvanska'),(737,191,'Istarska'),(738,191,'Karlovačka'),(739,191,'Koprivničko-Križevačka'),(740,191,'Krapinsko-Zagorska'),(741,191,'Ličko-Senjska'),(742,191,'Međimurska'),(743,191,'Osječko-Baranjska'),(744,191,'Požeško-Slavonska'),(745,191,'Primorsko-Goranska'),(746,191,'Šibensko-Kniniska'),(747,191,'Sisačko-Moslavačka'),(748,191,'Splitsko-Dalmatinska'),(749,191,'Varaždinska'),(750,191,'Virovitičk-Podravska'),(751,191,'Vukovarsko-Srijemska'),(752,191,'Zadarska'),(753,191,'Zagrebačka'),(754,191,'Grad Zagreb'),(755,192,'Pinar del Río'),(756,192,'Ciudad de La Habana'),(757,192,'Matanzas'),(758,192,'Isla de la Juventud'),(759,192,'Camagüey'),(760,192,'Ciego de Ávila'),(761,192,'Cienfuegos'),(762,192,'Granma'),(763,192,'Guantánamo'),(764,192,'La Habana'),(765,192,'Holguín'),(766,192,'Las Tunas'),(767,192,'Sancti Spíritus'),(768,192,'Santiago de Cuba'),(769,192,'Villa Clara'),(770,196,'Famagusta'),(771,196,'Kyrenia'),(772,196,'Larnaca'),(773,196,'Nicosia'),(774,196,'Limassol'),(775,196,'Paphos'),(776,203,'Hradec Kralove'),(777,203,'Jablonec nad Nisou'),(778,203,'Jiein'),(779,203,'Jihlava'),(780,203,'Kolin'),(781,203,'Liberec'),(782,203,'Melnik'),(783,203,'Mlada Boleslav'),(784,203,'Nachod'),(785,203,'Nymburk'),(786,203,'Pardubice'),(787,203,'Hlavní Mesto Praha'),(788,203,'Semily'),(789,203,'Trutnov'),(790,203,'South Moravian Region'),(791,203,'Jihočeský Kraj'),(792,203,'Vysočina'),(793,203,'Karlovarský Kraj'),(794,203,'Královéhradecký Kraj'),(795,203,'Liberecký Kraj'),(796,203,'Olomoucký Kraj'),(797,203,'Moravskoslezský Kraj'),(798,203,'Pardubický Kraj'),(799,203,'Plzeňský Kraj'),(800,203,'Středočeský Kraj'),(801,203,'Ústecký Kraj'),(802,203,'Zlínský Kraj'),(803,204,'Atakora'),(804,204,'Atlantique'),(805,204,'Alibori'),(806,204,'Borgou'),(807,204,'Collines'),(808,204,'Kouffo'),(809,204,'Donga'),(810,204,'Littoral'),(811,204,'Mono'),(812,204,'Ouémé'),(813,204,'Plateau'),(814,204,'Zou'),(815,208,'Århus'),(816,208,'Bornholm'),(817,208,'Frederiksborg'),(818,208,'Fyn'),(819,208,'Copenhagen city'),(820,208,'København'),(821,208,'Nordjylland'),(822,208,'Ribe'),(823,208,'Ringkøbing'),(824,208,'Roskilde'),(825,208,'Sønderjylland'),(826,208,'Storstrøm'),(827,208,'Vejle'),(828,208,'Vestsjælland'),(829,208,'Viborg'),(830,208,'Fredriksberg'),(831,208,'Capital Region'),(832,208,'Central Jutland'),(833,208,'North Jutland'),(834,208,'Region Zealand'),(835,208,'Region South Denmark'),(836,212,'Saint Andrew'),(837,212,'Saint David'),(838,212,'Saint George'),(839,212,'Saint John'),(840,212,'Saint Joseph'),(841,212,'Saint Luke'),(842,212,'Saint Mark'),(843,212,'Saint Patrick'),(844,212,'Saint Paul'),(845,212,'Saint Peter'),(846,214,'Azua'),(847,214,'Baoruco'),(848,214,'Barahona'),(849,214,'Dajabón'),(850,214,'Duarte'),(851,214,'Espaillat'),(852,214,'Independencia'),(853,214,'La Altagracia'),(854,214,'Elías Piña'),(855,214,'La Romana'),(856,214,'María Trinidad Sánchez'),(857,214,'Monte Cristi'),(858,214,'Pedernales'),(859,214,'Puerto Plata'),(860,214,'Salcedo'),(861,214,'Samaná'),(862,214,'Sánchez Ramírez'),(863,214,'San Juan'),(864,214,'San Pedro de Macorís'),(865,214,'Santiago'),(866,214,'Santiago Rodríguez'),(867,214,'Valverde'),(868,214,'El Seíbo'),(869,214,'Hato Mayor'),(870,214,'La Vega'),(871,214,'Monseñor Nouel'),(872,214,'Monte Plata'),(873,214,'San Cristóbal'),(874,214,'Distrito Nacional'),(875,214,'Peravia'),(876,214,'San José de Ocoa'),(877,214,'Santo Domingo'),(878,218,'Galápagos'),(879,218,'Azuay'),(880,218,'Bolívar'),(881,218,'Cañar'),(882,218,'Carchi'),(883,218,'Chimborazo'),(884,218,'Cotopaxi'),(885,218,'El Oro'),(886,218,'Esmeraldas'),(887,218,'Guayas'),(888,218,'Imbabura'),(889,218,'Loja'),(890,218,'Los Ríos'),(891,218,'Manabí'),(892,218,'Morona-Santiago'),(893,218,'Napo'),(894,218,'Pastaza'),(895,218,'Pichincha'),(896,218,'Tungurahua'),(897,218,'Zamora-Chinchipe'),(898,218,'Sucumbios'),(899,218,'Napo'),(900,218,'Orellana'),(901,218,'Provincia de Santa Elena'),(902,218,'Provincia de Santo Domingo de los Tsáchilas'),(903,222,'Ahuachapán'),(904,222,'Cabañas'),(905,222,'Chalatenango'),(906,222,'Cuscatlán'),(907,222,'La Libertad'),(908,222,'La Paz'),(909,222,'La Unión'),(910,222,'Morazán'),(911,222,'San Miguel'),(912,222,'San Salvador'),(913,222,'Santa Ana'),(914,222,'San Vicente'),(915,222,'Sonsonate'),(916,222,'Usulután'),(917,226,'Annobón'),(918,226,'Bioko Norte'),(919,226,'Bioko Sur'),(920,226,'Centro Sur'),(921,226,'Kié-Ntem'),(922,226,'Litoral'),(923,226,'Wele-Nzas'),(924,231,'Arsi'),(925,231,'Gonder'),(926,231,'Bale'),(927,231,'Eritrea'),(928,231,'Gamo Gofa'),(929,231,'Gojam'),(930,231,'Harerge'),(931,231,'Ilubabor'),(932,231,'Kefa'),(933,231,'Addis Abeba'),(934,231,'Sidamo'),(935,231,'Tigray'),(936,231,'Welega Kifle Hager'),(937,231,'Welo'),(938,231,'Adis Abeba'),(939,231,'Asosa'),(940,231,'Borena '),(941,231,'Debub Gonder'),(942,231,'Debub Shewa'),(943,231,'Debub Welo'),(944,231,'Dire Dawa'),(945,231,'Gambela'),(946,231,'Metekel'),(947,231,'Mirab Gojam'),(948,231,'Mirab Harerge'),(949,231,'Mirab Shewa'),(950,231,'Misrak Gojam'),(951,231,'Misrak Harerge'),(952,231,'Nazret'),(953,231,'Ogaden'),(954,231,'Omo'),(955,231,'Semen Gonder'),(956,231,'Semen Shewa'),(957,231,'Semen Welo'),(958,231,'Tigray'),(959,231,'Bale'),(960,231,'Gamo Gofa'),(961,231,'Ilubabor'),(962,231,'Kefa'),(963,231,'Sidamo'),(964,231,'Welega'),(965,231,'Ādīs Ābeba'),(966,231,'Āfar'),(967,231,'Āmara'),(968,231,'Bīnshangul Gumuz'),(969,231,'Dirē Dawa'),(970,231,'Gambēla Hizboch'),(971,231,'Hārerī Hizb'),(972,231,'Oromīya'),(973,231,'Sumalē'),(974,231,'Tigray'),(975,231,'YeDebub Bihēroch Bihēreseboch na Hizboch'),(976,232,'Ānseba'),(977,232,'Debub'),(978,232,'Debubawī Kʼeyih Bahrī'),(979,232,'Gash Barka'),(980,232,'Maʼākel'),(981,232,'Semēnawī Kʼeyih Bahrī'),(982,233,'Harjumaa'),(983,233,'Hiiumaa'),(984,233,'Ida-Virumaa'),(985,233,'Järvamaa'),(986,233,'Jõgevamaa'),(987,233,'Läänemaa'),(988,233,'Lääne-Virumaa'),(989,233,'Pärnumaa'),(990,233,'Põlvamaa'),(991,233,'Raplamaa'),(992,233,'Saaremaa'),(993,233,'Tartumaa'),(994,233,'Valgamaa'),(995,233,'Viljandimaa'),(996,233,'Võrumaa'),(997,234,'Norðoyar region'),(998,234,'Eysturoy region'),(999,234,'Sandoy region'),(1000,234,'Streymoy region'),(1001,234,'Suðuroy region'),(1002,234,'Vágar region'),(1003,238,'Falkland Islands (Islas Malvinas)'),(1004,239,'South Georgia and The South Sandwich Islands '),(1005,242,'Central'),(1006,242,'Eastern'),(1007,242,'Northern'),(1008,242,'Rotuma'),(1009,242,'Western'),(1010,246,'Åland'),(1011,246,'Hame'),(1012,246,'Keski-Suomi'),(1013,246,'Kuopio'),(1014,246,'Kymi'),(1015,246,'Lapponia'),(1016,246,'Mikkeli'),(1017,246,'Oulu'),(1018,246,'Pohjois-Karjala'),(1019,246,'Turku ja Pori'),(1020,246,'Uusimaa'),(1021,246,'Vaasa'),(1022,246,'Southern Finland'),(1023,246,'Eastern Finland'),(1024,246,'Western Finland'),(1025,250,'Aquitaine'),(1026,250,'Auvergne'),(1027,250,'Basse-Normandie'),(1028,250,'Bourgogne'),(1029,250,'Brittany'),(1030,250,'Centre'),(1031,250,'Champagne-Ardenne'),(1032,250,'Corsica'),(1033,250,'Franche-Comté'),(1034,250,'Haute-Normandie'),(1035,250,'Île-de-France'),(1036,250,'Languedoc-Roussillon'),(1037,250,'Limousin'),(1038,250,'Lorraine'),(1039,250,'Midi-Pyrénées'),(1040,250,'Nord-Pas-de-Calais'),(1041,250,'Pays de la Loire'),(1042,250,'Picardie'),(1043,250,'Poitou-Charentes'),(1044,250,'Provence-Aes-Côte dʼAzur'),(1045,250,'Rhône-Aes'),(1046,250,'Alsace'),(1047,254,'Guyane'),(1048,260,'Saint-Paul-et-Amsterdam'),(1049,260,'Crozet'),(1050,260,'Kerguelen'),(1051,260,'Terre Adélie'),(1052,260,'Îles Éparses'),(1053,262,'Ali Sabieh'),(1054,262,'Dikhil   '),(1055,262,'Djibouti  '),(1056,262,'Obock'),(1057,262,'Tadjourah'),(1058,262,'Dikhil'),(1059,262,'Djibouti'),(1060,262,'Arta'),(1061,266,'Estuaire'),(1062,266,'Haut-Ogooué'),(1063,266,'Moyen-Ogooué'),(1064,266,'Ngounié'),(1065,266,'Nyanga'),(1066,266,'Ogooué-Ivindo'),(1067,266,'Ogooué-Lolo'),(1068,266,'Ogooué-Maritime'),(1069,266,'Woleu-Ntem'),(1070,268,'Ajaria'),(1071,268,'Tʼbilisi'),(1072,268,'Abkhazia'),(1073,268,'Kvemo Kartli'),(1074,268,'Kakheti'),(1075,268,'Guria'),(1076,268,'Imereti'),(1077,268,'Shida Kartli'),(1078,268,'Mtskheta-Mtianeti'),(1079,268,'Racha-Lechkhumi and Kvemo Svaneti'),(1080,268,'Samegrelo and Zemo Svaneti'),(1081,268,'Samtskhe-Javakheti'),(1082,270,'Banjul'),(1083,270,'Lower River'),(1084,270,'Central River'),(1085,270,'Upper River'),(1086,270,'Western'),(1087,270,'North Bank'),(1088,275,'Gaza Strip'),(1089,275,'West Bank'),(1090,276,'Baden-Württemberg'),(1091,276,'Bavaria'),(1092,276,'Bremen'),(1093,276,'Hamburg'),(1094,276,'Hesse'),(1095,276,'Lower Saxony'),(1096,276,'North Rhine-Westphalia'),(1097,276,'Rhineland-Palatinate'),(1098,276,'Saarland'),(1099,276,'Schleswig-Holstein'),(1100,276,'Brandenburg'),(1101,276,'Mecklenburg-Vorpommern'),(1102,276,'Saxony'),(1103,276,'Saxony-Anhalt'),(1104,276,'Thuringia'),(1105,276,'Berlin'),(1106,288,'Greater Accra'),(1107,288,'Ashanti'),(1108,288,'Brong-Ahafo Region'),(1109,288,'Central'),(1110,288,'Eastern'),(1111,288,'Northern'),(1112,288,'Volta'),(1113,288,'Western'),(1114,288,'Upper East'),(1115,288,'Upper West'),(1116,292,'Gibraltar'),(1117,296,'Line Islands'),(1118,296,'Gilbert Islands'),(1119,296,'Phoenix Islands'),(1120,300,'Mount Athos'),(1121,300,'East Macedonia and Thrace'),(1122,300,'Central Macedonia'),(1123,300,'West Macedonia'),(1124,300,'Thessaly'),(1125,300,'Epirus'),(1126,300,'Ionian Islands'),(1127,300,'West Greece'),(1128,300,'Central Greece'),(1129,300,'Peloponnese'),(1130,300,'Attica'),(1131,300,'North Aegean'),(1132,300,'South Aegean'),(1133,300,'Crete'),(1134,304,'Nordgrønland'),(1135,304,'Østgrønland'),(1136,304,'Vestgrønland'),(1137,308,'Saint Andrew'),(1138,308,'Saint David'),(1139,308,'Saint George'),(1140,308,'Saint John'),(1141,308,'Saint Mark'),(1142,308,'Saint Patrick'),(1143,312,'Guadeloupe'),(1144,316,'Guam'),(1145,320,'Alta Verapaz'),(1146,320,'Baja Verapaz'),(1147,320,'Chimaltenango'),(1148,320,'Chiquimula'),(1149,320,'El Progreso'),(1150,320,'Escuintla'),(1151,320,'Guatemala'),(1152,320,'Huehuetenango'),(1153,320,'Izabal'),(1154,320,'Jalapa'),(1155,320,'Jutiapa'),(1156,320,'Petén'),(1157,320,'Quetzaltenango'),(1158,320,'Quiché'),(1159,320,'Retalhuleu'),(1160,320,'Sacatepéquez'),(1161,320,'San Marcos'),(1162,320,'Santa Rosa'),(1163,320,'Sololá'),(1164,320,'Suchitepéquez'),(1165,320,'Totonicapán'),(1166,320,'Zacapa'),(1167,324,'Beyla'),(1168,324,'Boffa'),(1169,324,'Boké'),(1170,324,'Conakry'),(1171,324,'Dabola'),(1172,324,'Dalaba'),(1173,324,'Dinguiraye'),(1174,324,'Faranah'),(1175,324,'Forécariah'),(1176,324,'Fria'),(1177,324,'Gaoual'),(1178,324,'Guéckédou'),(1179,324,'Kankan'),(1180,324,'Kérouané'),(1181,324,'Kindia'),(1182,324,'Kissidougou'),(1183,324,'Koundara'),(1184,324,'Kouroussa'),(1185,324,'Macenta'),(1186,324,'Mali'),(1187,324,'Mamou'),(1188,324,'Pita'),(1189,324,'Siguiri'),(1190,324,'Télimélé'),(1191,324,'Tougué'),(1192,324,'Yomou'),(1193,324,'Coyah'),(1194,324,'Dubréka'),(1195,324,'Kankan'),(1196,324,'Koubia'),(1197,324,'Labé'),(1198,324,'Lélouma'),(1199,324,'Lola'),(1200,324,'Mandiana'),(1201,324,'Nzérékoré'),(1202,324,'Siguiri'),(1203,328,'Barima-Waini'),(1204,328,'Cuyuni-Mazaruni'),(1205,328,'Demerara-Mahaica'),(1206,328,'East Berbice-Corentyne'),(1207,328,'Essequibo Islands-West Demerara'),(1208,328,'Mahaica-Berbice'),(1209,328,'Pomeroon-Supenaam'),(1210,328,'Potaro-Siparuni'),(1211,328,'Upper Demerara-Berbice'),(1212,328,'Upper Takutu-Upper Essequibo'),(1213,332,'Nord-Ouest'),(1214,332,'Artibonite'),(1215,332,'Centre'),(1216,332,'Nord'),(1217,332,'Nord-Est'),(1218,332,'Ouest'),(1219,332,'Sud'),(1220,332,'Sud-Est'),(1221,332,'GrandʼAnse'),(1222,332,'Nippes'),(1223,336,'Vatican City'),(1224,340,'Atlántida'),(1225,340,'Choluteca'),(1226,340,'Colón'),(1227,340,'Comayagua'),(1228,340,'Copán'),(1229,340,'Cortés'),(1230,340,'El Paraíso'),(1231,340,'Francisco Morazán'),(1232,340,'Gracias a Dios'),(1233,340,'Intibucá'),(1234,340,'Islas de la Bahía'),(1235,340,'La Paz'),(1236,340,'Lempira'),(1237,340,'Ocotepeque'),(1238,340,'Olancho'),(1239,340,'Santa Bárbara'),(1240,340,'Valle'),(1241,340,'Yoro'),(1242,344,'Hong Kong'),(1243,348,'Bács-Kiskun'),(1244,348,'Baranya'),(1245,348,'Békés'),(1246,348,'Borsod-Abaúj-Zemplén'),(1247,348,'Budapest'),(1248,348,'Csongrád'),(1249,348,'Fejér'),(1250,348,'Győr-Moson-Sopron'),(1251,348,'Hajdú-Bihar'),(1252,348,'Heves'),(1253,348,'Komárom-Esztergom'),(1254,348,'Nógrád'),(1255,348,'Pest'),(1256,348,'Somogy'),(1257,348,'Szabolcs-Szatmár-Bereg'),(1258,348,'Jász-Nagykun-Szolnok'),(1259,348,'Tolna'),(1260,348,'Vas'),(1261,348,'Veszprém'),(1262,348,'Zala'),(1263,352,'Borgarfjardarsysla'),(1264,352,'Dalasysla'),(1265,352,'Eyjafjardarsysla'),(1266,352,'Gullbringusysla'),(1267,352,'Hafnarfjördur'),(1268,352,'Husavik'),(1269,352,'Isafjördur'),(1270,352,'Keflavik'),(1271,352,'Kjosarsysla'),(1272,352,'Kopavogur'),(1273,352,'Myrasysla'),(1274,352,'Neskaupstadur'),(1275,352,'Nordur-Isafjardarsysla'),(1276,352,'Nordur-Mulasysla'),(1277,352,'Nordur-Tingeyjarsysla'),(1278,352,'Olafsfjördur'),(1279,352,'Rangarvallasysla'),(1280,352,'Reykjavík'),(1281,352,'Saudarkrokur'),(1282,352,'Seydisfjordur'),(1283,352,'Siglufjordur'),(1284,352,'Skagafjardarsysla'),(1285,352,'Snafellsnes- og Hnappadalssysla'),(1286,352,'Strandasysla'),(1287,352,'Sudur-Mulasysla'),(1288,352,'Sudur-Tingeyjarsysla'),(1289,352,'Vestmannaeyjar'),(1290,352,'Vestur-Bardastrandarsysla'),(1291,352,'Vestur-Hunavatnssysla'),(1292,352,'Vestur-Isafjardarsysla'),(1293,352,'Vestur-Skaftafellssysla'),(1294,352,'East'),(1295,352,'Capital Region'),(1296,352,'Northeast'),(1297,352,'Northwest'),(1298,352,'South'),(1299,352,'Southern Peninsula'),(1300,352,'Westfjords'),(1301,352,'West'),(1302,356,'Andaman and Nicobar Islands'),(1303,356,'Andhra Pradesh'),(1304,356,'Assam'),(1305,356,'Bihar'),(1306,356,'Chandīgarh'),(1307,356,'Dādra and Nagar Haveli'),(1308,356,'NCT'),(1309,356,'Gujarāt'),(1310,356,'Haryana'),(1311,356,'Himāchal Pradesh'),(1312,356,'Kashmir'),(1313,356,'Kerala'),(1314,356,'Laccadives'),(1315,356,'Madhya Pradesh '),(1316,356,'Mahārāshtra'),(1317,356,'Manipur'),(1318,356,'Meghālaya'),(1319,356,'Karnātaka'),(1320,356,'Nāgāland'),(1321,356,'Orissa'),(1322,356,'Pondicherry'),(1323,356,'Punjab'),(1324,356,'State of Rājasthān'),(1325,356,'Tamil Nādu'),(1326,356,'Tripura'),(1327,356,'Uttar Pradesh'),(1328,356,'Bengal'),(1329,356,'Sikkim'),(1330,356,'Arunāchal Pradesh'),(1331,356,'Mizoram'),(1332,356,'Daman and Diu'),(1333,356,'Goa'),(1334,356,'Bihār'),(1335,356,'Madhya Pradesh'),(1336,356,'Uttar Pradesh'),(1337,356,'Chhattisgarh'),(1338,356,'Jharkhand'),(1339,356,'Uttarakhand'),(1340,360,'Aceh'),(1341,360,'Bali'),(1342,360,'Bengkulu'),(1343,360,'Jakarta Raya'),(1344,360,'Jambi'),(1345,360,'Jawa Barat'),(1346,360,'Central Java'),(1347,360,'East Java'),(1348,360,'Yogyakarta '),(1349,360,'West Kalimantan'),(1350,360,'South Kalimantan'),(1351,360,'Kalimantan Tengah'),(1352,360,'East Kalimantan'),(1353,360,'Lampung'),(1354,360,'Nusa Tenggara Barat'),(1355,360,'East Nusa Tenggara'),(1356,360,'Central Sulawesi'),(1357,360,'Sulawesi Tenggara'),(1358,360,'Sulawesi Utara'),(1359,360,'West Sumatra'),(1360,360,'Sumatera Selatan'),(1361,360,'North Sumatra'),(1362,360,'Timor Timur'),(1363,360,'Maluku '),(1364,360,'Maluku Utara'),(1365,360,'West Java'),(1366,360,'North Sulawesi'),(1367,360,'South Sumatra'),(1368,360,'Banten'),(1369,360,'Gorontalo'),(1370,360,'Bangka-Belitung'),(1371,360,'Papua'),(1372,360,'Riau'),(1373,360,'South Sulawesi'),(1374,360,'Irian Jaya Barat'),(1375,360,'Riau Islands'),(1376,360,'Sulawesi Barat'),(1377,364,'Āz̄ārbāyjān-e Gharbī'),(1378,364,'Chahār Maḩāll va Bakhtīārī'),(1379,364,'Sīstān va Balūchestān'),(1380,364,'Kohgīlūyeh va Būyer Aḩmad'),(1381,364,'Fārs Province'),(1382,364,'Gīlān'),(1383,364,'Hamadān'),(1384,364,'Īlām'),(1385,364,'Hormozgān Province'),(1386,364,'Kerman'),(1387,364,'Kermānshāh'),(1388,364,'Khūzestān'),(1389,364,'Kordestān'),(1390,364,'Mazandaran'),(1391,364,'Markazi'),(1392,364,'Zanjan'),(1393,364,'Bushehr Province'),(1394,364,'Lorestān'),(1395,364,'Markazi'),(1396,364,'Semnān Province'),(1397,364,'Tehrān'),(1398,364,'Zanjan'),(1399,364,'Eşfahān'),(1400,364,'Kermān'),(1401,364,'Ostan-e Khorasan-e Razavi'),(1402,364,'Yazd'),(1403,364,'Ardabīl'),(1404,364,'Āz̄ārbāyjān-e Sharqī'),(1405,364,'Markazi Province'),(1406,364,'Māzandarān Province'),(1407,364,'Zanjan Province'),(1408,364,'Golestān'),(1409,364,'Qazvīn'),(1410,364,'Qom'),(1411,364,'Yazd'),(1412,364,'Khorāsān-e Jonūbī'),(1413,364,'Razavi Khorasan Province'),(1414,364,'Khorāsān-e Shomālī'),(1415,368,'Anbar'),(1416,368,'Al Başrah'),(1417,368,'Al Muthanná'),(1418,368,'Al Qādisīyah'),(1419,368,'As Sulaymānīyah'),(1420,368,'Bābil'),(1421,368,'Baghdād'),(1422,368,'Dahūk'),(1423,368,'Dhi Qar'),(1424,368,'Diyala'),(1425,368,'Arbīl'),(1426,368,'Karbalāʼ'),(1427,368,'At Taʼmīm'),(1428,368,'Maysan'),(1429,368,'Nīnawá'),(1430,368,'Wāsiţ'),(1431,368,'An Najaf'),(1432,368,'Şalāḩ ad Dīn'),(1433,372,'Carlow'),(1434,372,'Cavan'),(1435,372,'County Clare'),(1436,372,'Cork'),(1437,372,'Donegal'),(1438,372,'Galway'),(1439,372,'County Kerry'),(1440,372,'County Kildare'),(1441,372,'County Kilkenny'),(1442,372,'Leitrim'),(1443,372,'Laois'),(1444,372,'Limerick'),(1445,372,'County Longford'),(1446,372,'County Louth'),(1447,372,'County Mayo'),(1448,372,'County Meath'),(1449,372,'Monaghan'),(1450,372,'County Offaly'),(1451,372,'County Roscommon'),(1452,372,'County Sligo'),(1453,372,'County Waterford'),(1454,372,'County Westmeath'),(1455,372,'County Wexford'),(1456,372,'County Wicklow'),(1457,372,'Dún Laoghaire-Rathdown'),(1458,372,'Fingal County'),(1459,372,'Tipperary North Riding'),(1460,372,'South Dublin'),(1461,372,'Tipperary South Riding'),(1462,376,'HaDarom'),(1463,376,'HaMerkaz'),(1464,376,'Northern District'),(1465,376,'H̱efa'),(1466,376,'Tel Aviv'),(1467,376,'Yerushalayim'),(1468,380,'Abruzzo'),(1469,380,'Basilicate'),(1470,380,'Calabria'),(1471,380,'Campania'),(1472,380,'Emilia-Romagna'),(1473,380,'Friuli'),(1474,380,'Lazio'),(1475,380,'Liguria'),(1476,380,'Lombardy'),(1477,380,'The Marches'),(1478,380,'Molise'),(1479,380,'Piedmont'),(1480,380,'Apulia'),(1481,380,'Sardinia'),(1482,380,'Sicily'),(1483,380,'Tuscany'),(1484,380,'Trentino-Alto Adige'),(1485,380,'Umbria'),(1486,380,'Aosta Valley'),(1487,380,'Veneto'),(1488,384,'Vaaraíso Region'),(1489,384,'Antofagasta Region'),(1490,384,'Araucanía Region'),(1491,384,'Atacama Region'),(1492,384,'Biobío Region'),(1493,384,'Coquimbo Region'),(1494,384,'Maule Region'),(1495,384,'Santiago Metropolitan Region'),(1496,384,'Danane'),(1497,384,'Divo'),(1498,384,'Ferkessedougou'),(1499,384,'Gagnoa'),(1500,384,'Katiola'),(1501,384,'Korhogo'),(1502,384,'Odienne'),(1503,384,'Seguela'),(1504,384,'Touba'),(1505,384,'Bongouanou'),(1506,384,'Issia'),(1507,384,'Lakota'),(1508,384,'Mankono'),(1509,384,'Oume'),(1510,384,'Soubre'),(1511,384,'Tingrela'),(1512,384,'Zuenoula'),(1513,384,'Bangolo'),(1514,384,'Beoumi'),(1515,384,'Bondoukou'),(1516,384,'Bouafle'),(1517,384,'Bouake'),(1518,384,'Daloa'),(1519,384,'Daoukro'),(1520,384,'Duekoue'),(1521,384,'Grand-Lahou'),(1522,384,'Man'),(1523,384,'Mbahiakro'),(1524,384,'Sakassou'),(1525,384,'San Pedro'),(1526,384,'Sassandra'),(1527,384,'Sinfra'),(1528,384,'Tabou'),(1529,384,'Tanda'),(1530,384,'Tiassale'),(1531,384,'Toumodi'),(1532,384,'Vavoua'),(1533,384,'Abidjan'),(1534,384,'Aboisso'),(1535,384,'Adiake'),(1536,384,'Alepe'),(1537,384,'Bocanda'),(1538,384,'Dabou'),(1539,384,'Dimbokro'),(1540,384,'Grand-Bassam'),(1541,384,'Guiglo'),(1542,384,'Jacqueville'),(1543,384,'Tiebissou'),(1544,384,'Toulepleu'),(1545,384,'Yamoussoukro'),(1546,384,'Agnéby'),(1547,384,'Bafing'),(1548,384,'Bas-Sassandra'),(1549,384,'Denguélé'),(1550,384,'Dix-Huit Montagnes'),(1551,384,'Fromager'),(1552,384,'Haut-Sassandra'),(1553,384,'Lacs'),(1554,384,'Lagunes'),(1555,384,'Marahoué'),(1556,384,'Moyen-Cavally'),(1557,384,'Moyen-Comoé'),(1558,384,'Nʼzi-Comoé'),(1559,384,'Savanes'),(1560,384,'Sud-Bandama'),(1561,384,'Sud-Comoé'),(1562,384,'Vallée du Bandama'),(1563,384,'Worodougou'),(1564,384,'Zanzan'),(1565,388,'Clarendon'),(1566,388,'Hanover Parish'),(1567,388,'Manchester'),(1568,388,'Portland'),(1569,388,'Saint Andrew'),(1570,388,'Saint Ann'),(1571,388,'Saint Catherine'),(1572,388,'St. Elizabeth'),(1573,388,'Saint James'),(1574,388,'Saint Mary'),(1575,388,'Saint Thomas'),(1576,388,'Trelawny'),(1577,388,'Westmoreland'),(1578,388,'Kingston'),(1579,392,'Aichi'),(1580,392,'Akita'),(1581,392,'Aomori'),(1582,392,'Chiba'),(1583,392,'Ehime'),(1584,392,'Fukui'),(1585,392,'Fukuoka'),(1586,392,'Fukushima'),(1587,392,'Gifu'),(1588,392,'Gumma'),(1589,392,'Hiroshima'),(1590,392,'Hokkaidō'),(1591,392,'Hyōgo'),(1592,392,'Ibaraki'),(1593,392,'Ishikawa'),(1594,392,'Iwate'),(1595,392,'Kagawa'),(1596,392,'Kagoshima'),(1597,392,'Kanagawa'),(1598,392,'Kōchi'),(1599,392,'Kumamoto'),(1600,392,'Kyōto'),(1601,392,'Mie'),(1602,392,'Miyagi'),(1603,392,'Miyazaki'),(1604,392,'Nagano'),(1605,392,'Nagasaki'),(1606,392,'Nara'),(1607,392,'Niigata'),(1608,392,'Ōita'),(1609,392,'Okayama'),(1610,392,'Ōsaka'),(1611,392,'Saga'),(1612,392,'Saitama'),(1613,392,'Shiga'),(1614,392,'Shimane'),(1615,392,'Shizuoka'),(1616,392,'Tochigi'),(1617,392,'Tokushima'),(1618,392,'Tōkyō'),(1619,392,'Tottori'),(1620,392,'Toyama'),(1621,392,'Wakayama'),(1622,392,'Yamagata'),(1623,392,'Yamaguchi'),(1624,392,'Yamanashi'),(1625,392,'Okinawa'),(1626,398,'Almaty'),(1627,398,'Almaty Qalasy'),(1628,398,'Aqmola'),(1629,398,'Aqtöbe'),(1630,398,'Astana Qalasy'),(1631,398,'Atyraū'),(1632,398,'Batys Qazaqstan'),(1633,398,'Bayqongyr Qalasy'),(1634,398,'Mangghystaū'),(1635,398,'Ongtüstik Qazaqstan'),(1636,398,'Pavlodar'),(1637,398,'Qaraghandy'),(1638,398,'Qostanay'),(1639,398,'Qyzylorda'),(1640,398,'East Kazakhstan'),(1641,398,'Soltüstik Qazaqstan'),(1642,398,'Zhambyl'),(1643,400,'Balqa'),(1644,400,'Ma’an'),(1645,400,'Karak'),(1646,400,'Al Mafraq'),(1647,400,'Tafielah'),(1648,400,'Az Zarqa'),(1649,400,'Irbid'),(1650,400,'Mafraq'),(1651,400,'Amman'),(1652,400,'Zarqa'),(1653,400,'Irbid'),(1654,400,'Ma’an'),(1655,400,'Ajlun'),(1656,400,'Aqaba'),(1657,400,'Jerash'),(1658,400,'Madaba'),(1659,404,'Central'),(1660,404,'Coast'),(1661,404,'Eastern'),(1662,404,'Nairobi Area'),(1663,404,'North-Eastern'),(1664,404,'Nyanza'),(1665,404,'Rift Valley'),(1666,404,'Western'),(1667,408,'Chagang-do'),(1668,408,'Hamgyŏng-namdo'),(1669,408,'Hwanghae-namdo'),(1670,408,'Hwanghae-bukto'),(1671,408,'Kaesŏng-si'),(1672,408,'Gangwon'),(1673,408,'Pʼyŏngan-bukto'),(1674,408,'Pʼyŏngyang-si'),(1675,408,'Yanggang-do'),(1676,408,'Nampʼo-si'),(1677,408,'Pʼyŏngan-namdo'),(1678,408,'Hamgyŏng-bukto'),(1679,408,'Najin Sŏnbong-si'),(1680,410,'Jeju'),(1681,410,'North Jeolla'),(1682,410,'North Chungcheong'),(1683,410,'Gangwon'),(1684,410,'Busan'),(1685,410,'Seoul'),(1686,410,'Incheon'),(1687,410,'Gyeonggi'),(1688,410,'North Gyeongsang'),(1689,410,'Daegu'),(1690,410,'South Jeolla'),(1691,410,'South Chungcheong'),(1692,410,'Gwangju'),(1693,410,'Daejeon'),(1694,410,'South Gyeongsang'),(1695,410,'Ulsan'),(1696,414,'Muḩāfaz̧atalWafrah'),(1697,414,'Al ‘Āşimah'),(1698,414,'Al Aḩmadī'),(1699,414,'Al Jahrāʼ'),(1700,414,'Al Farwaniyah'),(1701,414,'Ḩawallī'),(1702,417,'Bishkek'),(1703,417,'Chüy'),(1704,417,'Jalal-Abad'),(1705,417,'Naryn'),(1706,417,'Talas'),(1707,417,'Ysyk-Köl'),(1708,417,'Osh'),(1709,417,'Batken'),(1710,418,'Attapu'),(1711,418,'Champasak'),(1712,418,'Houaphan'),(1713,418,'Khammouan'),(1714,418,'Louang Namtha'),(1715,418,'Louangphrabang'),(1716,418,'Oudômxai'),(1717,418,'Phongsali'),(1718,418,'Saravan'),(1719,418,'Savannakhet'),(1720,418,'Vientiane'),(1721,418,'Xiagnabouli'),(1722,418,'Xiangkhoang'),(1723,418,'Khammouan'),(1724,418,'Loungnamtha'),(1725,418,'Louangphabang'),(1726,418,'Phôngsali'),(1727,418,'Salavan'),(1728,418,'Savannahkhét'),(1729,418,'Bokèo'),(1730,418,'Bolikhamxai'),(1731,418,'Viangchan'),(1732,418,'Xékong'),(1733,418,'Viangchan'),(1734,422,'Béqaa'),(1735,422,'Liban-Nord'),(1736,422,'Beyrouth'),(1737,422,'Mont-Liban'),(1738,422,'Liban-Sud'),(1739,422,'Nabatîyé'),(1740,422,'Béqaa'),(1741,422,'Liban-Nord'),(1742,422,'Aakkâr'),(1743,422,'Baalbek-Hermel'),(1744,426,'Balzers Commune'),(1745,426,'Eschen Commune'),(1746,426,'Gamprin Commune'),(1747,426,'Mauren Commune'),(1748,426,'Planken Commune'),(1749,426,'Ruggell Commune'),(1750,426,'Berea District'),(1751,426,'Butha-Buthe District'),(1752,426,'Leribe District'),(1753,426,'Mafeteng'),(1754,426,'Maseru'),(1755,426,'Mohaleʼs Hoek'),(1756,426,'Mokhotlong'),(1757,426,'Qachaʼs Nek'),(1758,426,'Quthing District'),(1759,426,'Thaba-Tseka District'),(1760,428,'Dobeles Rajons'),(1761,428,'Aizkraukles Rajons'),(1762,428,'Alūksnes Rajons'),(1763,428,'Balvu Rajons'),(1764,428,'Bauskas Rajons'),(1765,428,'Cēsu Rajons'),(1766,428,'Daugavpils'),(1767,428,'Daugavpils Rajons'),(1768,428,'Dobeles Rajons'),(1769,428,'Gulbenes Rajons'),(1770,428,'Jēkabpils Rajons'),(1771,428,'Jelgava'),(1772,428,'Jelgavas Rajons'),(1773,428,'Jūrmala'),(1774,428,'Krāslavas Rajons'),(1775,428,'Kuldīgas Rajons'),(1776,428,'Liepāja'),(1777,428,'Liepājas Rajons'),(1778,428,'Limbažu Rajons'),(1779,428,'Ludzas Rajons'),(1780,428,'Madonas Rajons'),(1781,428,'Ogres Rajons'),(1782,428,'Preiļu Rajons'),(1783,428,'Rēzekne'),(1784,428,'Rēzeknes Rajons'),(1785,428,'Rīga'),(1786,428,'Rīgas Rajons'),(1787,428,'Saldus Rajons'),(1788,428,'Talsu Rajons'),(1789,428,'Tukuma Rajons'),(1790,428,'Valkas Rajons'),(1791,428,'Valmieras Rajons'),(1792,428,'Ventspils'),(1793,428,'Ventspils Rajons'),(1794,430,'Bong'),(1795,430,'Grand Jide'),(1796,430,'Grand Cape Mount'),(1797,430,'Lofa'),(1798,430,'Nimba'),(1799,430,'Sinoe'),(1800,430,'Grand Bassa County'),(1801,430,'Grand Cape Mount'),(1802,430,'Maryland'),(1803,430,'Montserrado'),(1804,430,'Bomi'),(1805,430,'Grand Kru'),(1806,430,'Margibi'),(1807,430,'River Cess'),(1808,430,'Grand Gedeh'),(1809,430,'Lofa'),(1810,430,'Gbarpolu'),(1811,430,'River Gee'),(1812,434,'Al Abyār'),(1813,434,'Al ‘Azīzīyah'),(1814,434,'Al Bayḑā’'),(1815,434,'Al Jufrah'),(1816,434,'Al Jumayl'),(1817,434,'Al Kufrah'),(1818,434,'Al Marj'),(1819,434,'Al Qarābūll'),(1820,434,'Al Qubbah'),(1821,434,'Al Ajaylāt'),(1822,434,'Ash Shāţiʼ'),(1823,434,'Az Zahra’'),(1824,434,'Banī Walīd'),(1825,434,'Bin Jawwād'),(1826,434,'Ghāt'),(1827,434,'Jādū'),(1828,434,'Jālū'),(1829,434,'Janzūr'),(1830,434,'Masallatah'),(1831,434,'Mizdah'),(1832,434,'Murzuq'),(1833,434,'Nālūt'),(1834,434,'Qamīnis'),(1835,434,'Qaşr Bin Ghashīr'),(1836,434,'Sabhā'),(1837,434,'Şabrātah'),(1838,434,'Shaḩḩāt'),(1839,434,'Şurmān'),(1840,434,'Tajura’ '),(1841,434,'Tarhūnah'),(1842,434,'Ţubruq'),(1843,434,'Tūkrah'),(1844,434,'Zlīţan'),(1845,434,'Zuwārah'),(1846,434,'Ajdābiyā'),(1847,434,'Al Fātiḩ'),(1848,434,'Al Jabal al Akhḑar'),(1849,434,'Al Khums'),(1850,434,'An Nuqāţ al Khams'),(1851,434,'Awbārī'),(1852,434,'Az Zāwiyah'),(1853,434,'Banghāzī'),(1854,434,'Darnah'),(1855,434,'Ghadāmis'),(1856,434,'Gharyān'),(1857,434,'Mişrātah'),(1858,434,'Sawfajjīn'),(1859,434,'Surt'),(1860,434,'Ţarābulus'),(1861,434,'Yafran'),(1862,438,'Balzers'),(1863,438,'Eschen'),(1864,438,'Gamprin'),(1865,438,'Mauren'),(1866,438,'Planken'),(1867,438,'Ruggell'),(1868,438,'Schaan'),(1869,438,'Schellenberg'),(1870,438,'Triesen'),(1871,438,'Triesenberg'),(1872,438,'Vaduz'),(1873,440,'Alytaus Apskritis'),(1874,440,'Kauno Apskritis'),(1875,440,'Klaipėdos Apskritis'),(1876,440,'Marijampolės Apskritis'),(1877,440,'Panevėžio Apskritis'),(1878,440,'Šiaulių Apskritis'),(1879,440,'Tauragės Apskritis'),(1880,440,'Telšių Apskritis'),(1881,440,'Utenos Apskritis'),(1882,440,'Vilniaus Apskritis'),(1883,442,'Diekirch'),(1884,442,'Grevenmacher'),(1885,442,'Luxembourg'),(1886,446,'Ilhas'),(1887,446,'Macau'),(1888,450,'Antsiranana'),(1889,450,'Fianarantsoa'),(1890,450,'Mahajanga'),(1891,450,'Toamasina'),(1892,450,'Antananarivo'),(1893,450,'Toliara'),(1894,454,'Chikwawa'),(1895,454,'Chiradzulu'),(1896,454,'Chitipa'),(1897,454,'Thyolo'),(1898,454,'Dedza'),(1899,454,'Dowa'),(1900,454,'Karonga'),(1901,454,'Kasungu'),(1902,454,'Lilongwe'),(1903,454,'Mangochi'),(1904,454,'Mchinji'),(1905,454,'Mzimba'),(1906,454,'Ntcheu'),(1907,454,'Nkhata Bay'),(1908,454,'Nkhotakota'),(1909,454,'Nsanje'),(1910,454,'Ntchisi'),(1911,454,'Rumphi'),(1912,454,'Salima'),(1913,454,'Zomba'),(1914,454,'Blantyre'),(1915,454,'Mwanza'),(1916,454,'Balaka'),(1917,454,'Likoma'),(1918,454,'Machinga'),(1919,454,'Mulanje'),(1920,454,'Phalombe'),(1921,458,'Johor'),(1922,458,'Kedah'),(1923,458,'Kelantan'),(1924,458,'Melaka'),(1925,458,'Negeri Sembilan'),(1926,458,'Pahang'),(1927,458,'Perak'),(1928,458,'Perlis'),(1929,458,'Pulau Pinang'),(1930,458,'Sarawak'),(1931,458,'Selangor'),(1932,458,'Terengganu'),(1933,458,'Kuala Lumpur'),(1934,458,'Federal Territory of Labuan'),(1935,458,'Sabah'),(1936,458,'Putrajaya'),(1937,462,'Maale'),(1938,462,'Seenu'),(1939,462,'Alifu Atholhu'),(1940,462,'Lhaviyani Atholhu'),(1941,462,'Vaavu Atholhu'),(1942,462,'Laamu'),(1943,462,'Haa Alifu Atholhu'),(1944,462,'Thaa Atholhu'),(1945,462,'Meemu Atholhu'),(1946,462,'Raa Atholhu'),(1947,462,'Faafu Atholhu'),(1948,462,'Dhaalu Atholhu'),(1949,462,'Baa Atholhu'),(1950,462,'Haa Dhaalu Atholhu'),(1951,462,'Shaviyani Atholhu'),(1952,462,'Noonu Atholhu'),(1953,462,'Kaafu Atholhu'),(1954,462,'Gaafu Alifu Atholhu'),(1955,462,'Gaafu Dhaalu Atholhu'),(1956,462,'Gnyaviyani Atoll'),(1957,462,'Alifu'),(1958,462,'Baa'),(1959,462,'Dhaalu'),(1960,462,'Faafu'),(1961,462,'Gaafu Alifu'),(1962,462,'Gaafu Dhaalu'),(1963,462,'Haa Alifu'),(1964,462,'Haa Dhaalu'),(1965,462,'Kaafu'),(1966,462,'Lhaviyani'),(1967,462,'Maale'),(1968,462,'Meemu'),(1969,462,'Gnaviyani'),(1970,462,'Noonu'),(1971,462,'Raa'),(1972,462,'Shaviyani'),(1973,462,'Thaa'),(1974,462,'Vaavu'),(1975,466,'Bamako'),(1976,466,'Gao'),(1977,466,'Kayes'),(1978,466,'Mopti'),(1979,466,'Ségou'),(1980,466,'Sikasso'),(1981,466,'Koulikoro'),(1982,466,'Tombouctou'),(1983,466,'Gao'),(1984,466,'Kidal'),(1985,470,'Malta'),(1986,474,'Martinique'),(1987,478,'Nouakchott'),(1988,478,'Hodh Ech Chargui'),(1989,478,'Hodh El Gharbi'),(1990,478,'Assaba'),(1991,478,'Gorgol'),(1992,478,'Brakna'),(1993,478,'Trarza'),(1994,478,'Adrar'),(1995,478,'Dakhlet Nouadhibou'),(1996,478,'Tagant'),(1997,478,'Guidimaka'),(1998,478,'Tiris Zemmour'),(1999,478,'Inchiri'),(2000,480,'Black River'),(2001,480,'Flacq'),(2002,480,'Grand Port'),(2003,480,'Moka'),(2004,480,'Pamplemousses'),(2005,480,'Plaines Wilhems'),(2006,480,'Port Louis'),(2007,480,'Rivière du Rempart'),(2008,480,'Savanne'),(2009,480,'Agalega Islands'),(2010,480,'Cargados Carajos'),(2011,480,'Rodrigues'),(2012,484,'Aguascalientes'),(2013,484,'Baja California'),(2014,484,'Baja California Sur'),(2015,484,'Campeche'),(2016,484,'Chiapas'),(2017,484,'Chihuahua'),(2018,484,'Coahuila'),(2019,484,'Colima'),(2020,484,'The Federal District'),(2021,484,'Durango'),(2022,484,'Guanajuato'),(2023,484,'Guerrero'),(2024,484,'Hidalgo'),(2025,484,'Jalisco'),(2026,484,'México'),(2027,484,'Michoacán'),(2028,484,'Morelos'),(2029,484,'Nayarit'),(2030,484,'Nuevo León'),(2031,484,'Oaxaca'),(2032,484,'Puebla'),(2033,484,'Querétaro'),(2034,484,'Quintana Roo'),(2035,484,'San Luis Potosí'),(2036,484,'Sinaloa'),(2037,484,'Sonora'),(2038,484,'Tabasco'),(2039,484,'Tamaulipas'),(2040,484,'Tlaxcala'),(2041,484,'Veracruz-Llave'),(2042,484,'Yucatán'),(2043,484,'Zacatecas'),(2044,492,'Monaco'),(2045,496,'Arhangay'),(2046,496,'Bayanhongor'),(2047,496,'Bayan-Ölgiy'),(2048,496,'East Aimak'),(2049,496,'East Gobi Aymag'),(2050,496,'Middle Govĭ'),(2051,496,'Dzavhan'),(2052,496,'Govĭ-Altay'),(2053,496,'Hentiy'),(2054,496,'Hovd'),(2055,496,'Hövsgöl'),(2056,496,'South Gobi Aimak'),(2057,496,'South Hangay'),(2058,496,'Selenge'),(2059,496,'Sühbaatar'),(2060,496,'Central Aimak'),(2061,496,'Uvs'),(2062,496,'Ulaanbaatar'),(2063,496,'Bulgan'),(2064,496,'Darhan Uul'),(2065,496,'Govĭ-Sumber'),(2066,496,'Orhon'),(2067,498,'Ungheni Judetul'),(2068,498,'Balti'),(2069,498,'Cahul'),(2070,498,'Stinga Nistrului'),(2071,498,'Edinet'),(2072,498,'Găgăuzia'),(2073,498,'Lapusna'),(2074,498,'Orhei'),(2075,498,'Soroca'),(2076,498,'Tighina'),(2077,498,'Ungheni'),(2078,498,'Chişinău'),(2079,498,'Stînga Nistrului'),(2080,498,'Raionul Anenii Noi'),(2081,498,'Bălţi'),(2082,498,'Raionul Basarabeasca'),(2083,498,'Bender'),(2084,498,'Raionul Briceni'),(2085,498,'Raionul Cahul'),(2086,498,'Raionul Cantemir'),(2087,498,'Călăraşi'),(2088,498,'Căuşeni'),(2089,498,'Raionul Cimişlia'),(2090,498,'Raionul Criuleni'),(2091,498,'Raionul Donduşeni'),(2092,498,'Raionul Drochia'),(2093,498,'Dubăsari'),(2094,498,'Raionul Edineţ'),(2095,498,'Raionul Făleşti'),(2096,498,'Raionul Floreşti'),(2097,498,'Raionul Glodeni'),(2098,498,'Raionul Hînceşti'),(2099,498,'Raionul Ialoveni'),(2100,498,'Raionul Leova'),(2101,498,'Raionul Nisporeni'),(2102,498,'Raionul Ocniţa'),(2103,498,'Raionul Orhei'),(2104,498,'Raionul Rezina'),(2105,498,'Raionul Rîşcani'),(2106,498,'Raionul Sîngerei'),(2107,498,'Raionul Şoldăneşti'),(2108,498,'Raionul Soroca'),(2109,498,'Ştefan-Vodă'),(2110,498,'Raionul Străşeni'),(2111,498,'Raionul Taraclia'),(2112,498,'Raionul Teleneşti'),(2113,498,'Raionul Ungheni'),(2114,499,'Opština Andrijevica'),(2115,499,'Opština Bar'),(2116,499,'Opština Berane'),(2117,499,'Opština Bijelo Polje'),(2118,499,'Opština Budva'),(2119,499,'Opština Cetinje'),(2120,499,'Opština Danilovgrad'),(2121,499,'Opština Herceg Novi'),(2122,499,'Opština Kolašin'),(2123,499,'Opština Kotor'),(2124,499,'Opština Mojkovac'),(2125,499,'Opština Nikšić'),(2126,499,'Opština Plav'),(2127,499,'Opština Pljevlja'),(2128,499,'Opština Plužine'),(2129,499,'Opština Podgorica'),(2130,499,'Opština Rožaje'),(2131,499,'Opština Šavnik'),(2132,499,'Opština Tivat'),(2133,499,'Opština Ulcinj'),(2134,499,'Opština Žabljak'),(2135,500,'Saint Anthony'),(2136,500,'Saint Georges'),(2137,500,'Saint Peter'),(2138,504,'Agadir'),(2139,504,'Al Hoceïma'),(2140,504,'Azizal'),(2141,504,'Ben Slimane'),(2142,504,'Beni Mellal'),(2143,504,'Boulemane'),(2144,504,'Casablanca'),(2145,504,'Chaouen'),(2146,504,'El Jadida'),(2147,504,'El Kelaa des Srarhna'),(2148,504,'Er Rachidia'),(2149,504,'Essaouira'),(2150,504,'Fes'),(2151,504,'Figuig'),(2152,504,'Kenitra'),(2153,504,'Khemisset'),(2154,504,'Khenifra'),(2155,504,'Khouribga'),(2156,504,'Marrakech'),(2157,504,'Meknes'),(2158,504,'Nador'),(2159,504,'Ouarzazate'),(2160,504,'Oujda'),(2161,504,'Rabat-Sale'),(2162,504,'Safi'),(2163,504,'Settat'),(2164,504,'Tanger'),(2165,504,'Tata'),(2166,504,'Taza'),(2167,504,'Tiznit'),(2168,504,'Guelmim'),(2169,504,'Ifrane'),(2170,504,'Laayoune'),(2171,504,'Tan-Tan'),(2172,504,'Taounate'),(2173,504,'Sidi Kacem'),(2174,504,'Taroudannt'),(2175,504,'Tetouan'),(2176,504,'Larache'),(2177,504,'Grand Casablanca'),(2178,504,'Fès-Boulemane'),(2179,504,'Marrakech-Tensift-Al Haouz'),(2180,504,'Meknès-Tafilalet'),(2181,504,'Rabat-Salé-Zemmour-Zaër'),(2182,504,'Chaouia-Ouardigha'),(2183,504,'Doukkala-Abda'),(2184,504,'Gharb-Chrarda-Beni Hssen'),(2185,504,'Guelmim-Es Smara'),(2186,504,'Oriental'),(2187,504,'Souss-Massa-Drâa'),(2188,504,'Tadla-Azilal'),(2189,504,'Tanger-Tétouan'),(2190,504,'Taza-Al Hoceima-Taounate'),(2191,504,'Laâyoune-Boujdour-Sakia El Hamra'),(2192,508,'Cabo Delgado'),(2193,508,'Gaza'),(2194,508,'Inhambane'),(2195,508,'Maputo Province'),(2196,508,'Sofala'),(2197,508,'Nampula'),(2198,508,'Niassa'),(2199,508,'Tete'),(2200,508,'Zambézia'),(2201,508,'Manica'),(2202,508,'Maputo'),(2203,512,'Ad Dākhilīyah'),(2204,512,'Al Bāţinah'),(2205,512,'Al Wusţá'),(2206,512,'Ash Sharqīyah'),(2207,512,'Masqaţ'),(2208,512,'Musandam'),(2209,512,'Z̧ufār'),(2210,512,'Az̧ Z̧āhirah'),(2211,512,'Muḩāfaz̧at al Buraymī'),(2212,516,'Bethanien'),(2213,516,'Caprivi Oos'),(2214,516,'Kaokoland'),(2215,516,'Otjiwarongo'),(2216,516,'Outjo'),(2217,516,'Owambo'),(2218,516,'Khomas'),(2219,516,'Kavango'),(2220,516,'Caprivi'),(2221,516,'Erongo'),(2222,516,'Hardap'),(2223,516,'Karas'),(2224,516,'Kunene'),(2225,516,'Ohangwena'),(2226,516,'Okavango'),(2227,516,'Omaheke'),(2228,516,'Omusati'),(2229,516,'Oshana'),(2230,516,'Oshikoto'),(2231,516,'Otjozondjupa'),(2232,520,'Aiwo'),(2233,520,'Anabar'),(2234,520,'Anetan'),(2235,520,'Anibare'),(2236,520,'Baiti'),(2237,520,'Boe'),(2238,520,'Buada'),(2239,520,'Denigomodu'),(2240,520,'Ewa'),(2241,520,'Ijuw'),(2242,520,'Meneng'),(2243,520,'Nibok'),(2244,520,'Uaboe'),(2245,520,'Yaren'),(2246,524,'Bāgmatī'),(2247,524,'Bherī'),(2248,524,'Dhawalāgiri'),(2249,524,'Gandakī'),(2250,524,'Janakpur'),(2251,524,'Karnālī'),(2252,524,'Kosī'),(2253,524,'Lumbinī'),(2254,524,'Mahākālī'),(2255,524,'Mechī'),(2256,524,'Nārāyanī'),(2257,524,'Rāptī'),(2258,524,'Sagarmāthā'),(2259,524,'Setī'),(2260,528,'Provincie Drenthe'),(2261,528,'Provincie Friesland'),(2262,528,'Gelderland'),(2263,528,'Groningen'),(2264,528,'Limburg'),(2265,528,'North Brabant'),(2266,528,'North Holland'),(2267,528,'Utrecht'),(2268,528,'Zeeland'),(2269,528,'South Holland'),(2270,528,'Overijssel'),(2271,528,'Flevoland'),(2272,530,'Netherlands Antilles'),(2273,533,'Aruba'),(2274,548,'Ambrym'),(2275,548,'Aoba/Maéwo'),(2276,548,'Torba'),(2277,548,'Éfaté'),(2278,548,'Épi'),(2279,548,'Malakula'),(2280,548,'Paama'),(2281,548,'Pentecôte'),(2282,548,'Sanma'),(2283,548,'Shepherd'),(2284,548,'Tafea'),(2285,548,'Malampa'),(2286,548,'Penama'),(2287,548,'Shefa'),(2288,554,'Tasman'),(2289,554,'Auckland'),(2290,554,'Bay of Plenty'),(2291,554,'Canterbury'),(2292,554,'Gisborne'),(2293,554,'Hawkeʼs Bay'),(2294,554,'Manawatu-Wanganui'),(2295,554,'Marlborough'),(2296,554,'Nelson'),(2297,554,'Northland'),(2298,554,'Otago'),(2299,554,'Southland'),(2300,554,'Taranaki'),(2301,554,'Waikato'),(2302,554,'Wellington'),(2303,554,'West Coast'),(2304,558,'Boaco'),(2305,558,'Carazo'),(2306,558,'Chinandega'),(2307,558,'Chontales'),(2308,558,'Estelí'),(2309,558,'Granada'),(2310,558,'Jinotega'),(2311,558,'León'),(2312,558,'Madriz'),(2313,558,'Managua'),(2314,558,'Masaya'),(2315,558,'Matagaa'),(2316,558,'Nueva Segovia'),(2317,558,'Río San Juan'),(2318,558,'Rivas'),(2319,558,'Ogun State'),(2320,558,'Atlántico Norte'),(2321,558,'Atlántico Sur'),(2322,562,'Agadez'),(2323,562,'Diffa'),(2324,562,'Dosso'),(2325,562,'Maradi'),(2326,562,'Tahoua'),(2327,562,'Zinder'),(2328,562,'Niamey'),(2329,562,'Tillabéri'),(2330,566,'Lagos'),(2331,566,'Abuja Federal Capital Territory'),(2332,566,'Ogun'),(2333,566,'Akwa Ibom'),(2334,566,'Cross River'),(2335,566,'Kaduna'),(2336,566,'Katsina'),(2337,566,'Anambra'),(2338,566,'Benue'),(2339,566,'Borno'),(2340,566,'Imo'),(2341,566,'Kano'),(2342,566,'Kwara'),(2343,566,'Niger'),(2344,566,'Oyo'),(2345,566,'Adamawa'),(2346,566,'Delta'),(2347,566,'Edo'),(2348,566,'Jigawa'),(2349,566,'Kebbi'),(2350,566,'Kogi'),(2351,566,'Osun'),(2352,566,'Taraba'),(2353,566,'Yobe'),(2354,566,'Abia'),(2355,566,'Bauchi'),(2356,566,'Enugu'),(2357,566,'Ondo'),(2358,566,'Plateau'),(2359,566,'Rivers'),(2360,566,'Sokoto'),(2361,566,'Bayelsa'),(2362,566,'Ebonyi'),(2363,566,'Ekiti'),(2364,566,'Gombe'),(2365,566,'Nassarawa'),(2366,566,'Zamfara'),(2367,570,'Niue'),(2368,574,'Norfolk Island'),(2369,578,'Svalbard'),(2370,578,'Akershus'),(2371,578,'Aust-Agder'),(2372,578,'Buskerud'),(2373,578,'Finnmark'),(2374,578,'Hedmark'),(2375,578,'Hordaland'),(2376,578,'Møre og Romsdal'),(2377,578,'Nordland'),(2378,578,'Nord-Trøndelag'),(2379,578,'Oppland'),(2380,578,'Oslo county'),(2381,578,'Østfold'),(2382,578,'Rogaland'),(2383,578,'Sogn og Fjordane'),(2384,578,'Sør-Trøndelag'),(2385,578,'Telemark'),(2386,578,'Troms'),(2387,578,'Vest-Agder'),(2388,578,'Vestfold'),(2389,583,'Kosrae'),(2390,583,'Pohnpei'),(2391,583,'Chuuk'),(2392,583,'Yap'),(2393,584,'Marshall Islands'),(2394,585,'State of Ngeremlengui'),(2395,586,'Federally Administered Tribal Areas'),(2396,586,'Balochistān'),(2397,586,'North West Frontier Province'),(2398,586,'Punjab'),(2399,586,'Sindh'),(2400,586,'Azad Kashmir'),(2401,586,'Gilgit-Baltistan'),(2402,586,'Islāmābād'),(2403,591,'Bocas del Toro'),(2404,591,'Chiriquí'),(2405,591,'Coclé'),(2406,591,'Colón'),(2407,591,'Darién'),(2408,591,'Herrera'),(2409,591,'Los Santos'),(2410,591,'Panamá'),(2411,591,'San Blas'),(2412,591,'Veraguas'),(2413,598,'Central'),(2414,598,'Gulf'),(2415,598,'Milne Bay'),(2416,598,'Northern'),(2417,598,'Southern Highlands'),(2418,598,'Western'),(2419,598,'Bougainville'),(2420,598,'Chimbu'),(2421,598,'Eastern Highlands'),(2422,598,'East New Britain'),(2423,598,'East Sepik'),(2424,598,'Madang'),(2425,598,'Manus'),(2426,598,'Morobe'),(2427,598,'New Ireland'),(2428,598,'Western Highlands'),(2429,598,'West New Britain'),(2430,598,'Sandaun'),(2431,598,'Enga'),(2432,598,'National Capital'),(2433,600,'Alto Paraná'),(2434,600,'Amambay'),(2435,600,'Caaguazú'),(2436,600,'Caazapá'),(2437,600,'Central'),(2438,600,'Concepción'),(2439,600,'Cordillera'),(2440,600,'Guairá'),(2441,600,'Itapúa'),(2442,600,'Misiones'),(2443,600,'Ñeembucú'),(2444,600,'Paraguarí'),(2445,600,'Presidente Hayes'),(2446,600,'San Pedro'),(2447,600,'Canindeyú'),(2448,600,'Asunción'),(2449,600,'Departamento de Alto Paraguay'),(2450,600,'Boquerón'),(2451,604,'Amazonas'),(2452,604,'Ancash'),(2453,604,'Apurímac'),(2454,604,'Arequipa'),(2455,604,'Ayacucho'),(2456,604,'Cajamarca'),(2457,604,'Callao'),(2458,604,'Cusco'),(2459,604,'Huancavelica'),(2460,604,'Huanuco'),(2461,604,'Ica'),(2462,604,'Junín'),(2463,604,'La Libertad'),(2464,604,'Lambayeque'),(2465,604,'Lima'),(2466,604,'Provincia de Lima'),(2467,604,'Loreto'),(2468,604,'Madre de Dios'),(2469,604,'Moquegua'),(2470,604,'Pasco'),(2471,604,'Piura'),(2472,604,'Puno'),(2473,604,'San Martín'),(2474,604,'Tacna'),(2475,604,'Tumbes'),(2476,604,'Ucayali'),(2477,608,'Abra'),(2478,608,'Agusan del Norte'),(2479,608,'Agusan del Sur'),(2480,608,'Aklan'),(2481,608,'Albay'),(2482,608,'Antique'),(2483,608,'Bataan'),(2484,608,'Batanes'),(2485,608,'Batangas'),(2486,608,'Benguet'),(2487,608,'Bohol'),(2488,608,'Bukidnon'),(2489,608,'Bulacan'),(2490,608,'Cagayan'),(2491,608,'Camarines Norte'),(2492,608,'Camarines Sur'),(2493,608,'Camiguin'),(2494,608,'Capiz'),(2495,608,'Catanduanes'),(2496,608,'Cebu'),(2497,608,'Basilan'),(2498,608,'Eastern Samar'),(2499,608,'Davao del Sur'),(2500,608,'Davao Oriental'),(2501,608,'Ifugao'),(2502,608,'Ilocos Norte'),(2503,608,'Ilocos Sur'),(2504,608,'Iloilo'),(2505,608,'Isabela'),(2506,608,'Laguna'),(2507,608,'Lanao del Sur'),(2508,608,'La Union'),(2509,608,'Leyte'),(2510,608,'Marinduque'),(2511,608,'Masbate'),(2512,608,'Occidental Mindoro'),(2513,608,'Oriental Mindoro'),(2514,608,'Misamis Oriental'),(2515,608,'Mountain Province'),(2516,608,'Negros Oriental'),(2517,608,'Nueva Ecija'),(2518,608,'Nueva Vizcaya'),(2519,608,'Palawan'),(2520,608,'Pampanga'),(2521,608,'Pangasinan'),(2522,608,'Rizal'),(2523,608,'Romblon'),(2524,608,'Samar'),(2525,608,'Maguindanao'),(2526,608,'Cotabato City'),(2527,608,'Sorsogon'),(2528,608,'Southern Leyte'),(2529,608,'Sulu'),(2530,608,'Surigao del Norte'),(2531,608,'Surigao del Sur'),(2532,608,'Tarlac'),(2533,608,'Zambales'),(2534,608,'Zamboanga del Norte'),(2535,608,'Zamboanga del Sur'),(2536,608,'Northern Samar'),(2537,608,'Quirino'),(2538,608,'Siquijor'),(2539,608,'South Cotabato'),(2540,608,'Sultan Kudarat'),(2541,608,'Kalinga'),(2542,608,'Apayao'),(2543,608,'Tawi-Tawi'),(2544,608,'Angeles'),(2545,608,'Bacolod City'),(2546,608,'Compostela Valley'),(2547,608,'Baguio'),(2548,608,'Davao del Norte'),(2549,608,'Butuan'),(2550,608,'Guimaras'),(2551,608,'Lanao del Norte'),(2552,608,'Misamis Occidental'),(2553,608,'Caloocan'),(2554,608,'Cavite'),(2555,608,'Cebu City'),(2556,608,'Cotabato'),(2557,608,'Dagupan'),(2558,608,'Cagayan de Oro'),(2559,608,'Iligan'),(2560,608,'Davao'),(2561,608,'Las Piñas'),(2562,608,'Malabon'),(2563,608,'General Santos'),(2564,608,'Muntinlupa'),(2565,608,'Iloilo City'),(2566,608,'Navotas'),(2567,608,'Parañaque'),(2568,608,'Quezon City'),(2569,608,'Lapu-Lapu'),(2570,608,'Taguig'),(2571,608,'Valenzuela'),(2572,608,'Lucena'),(2573,608,'Mandaue'),(2574,608,'Manila'),(2575,608,'Zamboanga Sibugay'),(2576,608,'Naga'),(2577,608,'Olongapo'),(2578,608,'Ormoc'),(2579,608,'Santiago'),(2580,608,'Pateros'),(2581,608,'Pasay'),(2582,608,'Puerto Princesa'),(2583,608,'Quezon'),(2584,608,'Tacloban'),(2585,608,'Zamboanga City'),(2586,608,'Aurora'),(2587,608,'Negros Occidental'),(2588,608,'Biliran'),(2589,608,'Makati City'),(2590,608,'Sarangani'),(2591,608,'Mandaluyong City'),(2592,608,'Marikina'),(2593,608,'Pasig'),(2594,608,'San Juan'),(2595,612,'Pitcairn Islands'),(2596,616,'Biala Podlaska'),(2597,616,'Bialystok'),(2598,616,'Bielsko'),(2599,616,'Bydgoszcz'),(2600,616,'Chelm'),(2601,616,'Ciechanow'),(2602,616,'Czestochowa'),(2603,616,'Elblag'),(2604,616,'Gdansk'),(2605,616,'Gorzow'),(2606,616,'Jelenia Gora'),(2607,616,'Kalisz'),(2608,616,'Katowice'),(2609,616,'Kielce'),(2610,616,'Konin'),(2611,616,'Koszalin'),(2612,616,'Krakow'),(2613,616,'Krosno'),(2614,616,'Legnica'),(2615,616,'Leszno'),(2616,616,'Lodz'),(2617,616,'Lomza'),(2618,616,'Lublin'),(2619,616,'Nowy Sacz'),(2620,616,'Olsztyn'),(2621,616,'Opole'),(2622,616,'Ostroleka'),(2623,616,'Pita'),(2624,616,'Piotrkow'),(2625,616,'Plock'),(2626,616,'Poznan'),(2627,616,'Przemysl'),(2628,616,'Radom'),(2629,616,'Rzeszow'),(2630,616,'Siedlce'),(2631,616,'Sieradz'),(2632,616,'Skierniewice'),(2633,616,'Slupsk'),(2634,616,'Suwalki'),(2635,616,'Szczecin'),(2636,616,'Tarnobrzeg'),(2637,616,'Tarnow'),(2638,616,'Torufi'),(2639,616,'Walbrzych'),(2640,616,'Warszawa'),(2641,616,'Wloclawek'),(2642,616,'Wroclaw'),(2643,616,'Zamosc'),(2644,616,'Zielona Gora'),(2645,616,'Lower Silesian Voivodeship'),(2646,616,'Kujawsko-Pomorskie Voivodship'),(2647,616,'Łódź Voivodeship'),(2648,616,'Lublin Voivodeship'),(2649,616,'Lubusz Voivodship'),(2650,616,'Lesser Poland Voivodeship'),(2651,616,'Masovian Voivodeship'),(2652,616,'Opole Voivodeship'),(2653,616,'Subcarpathian Voivodeship'),(2654,616,'Podlasie Voivodship'),(2655,616,'Pomeranian Voivodeship'),(2656,616,'Silesian Voivodeship'),(2657,616,'Świętokrzyskie Voivodship'),(2658,616,'Warmian-Masurian Voivodeship'),(2659,616,'Greater Poland Voivodeship'),(2660,616,'West Pomeranian Voivodeship'),(2661,620,'Aveiro'),(2662,620,'Beja'),(2663,620,'Braga'),(2664,620,'Bragança'),(2665,620,'Castelo Branco'),(2666,620,'Coimbra'),(2667,620,'Évora'),(2668,620,'Faro'),(2669,620,'Madeira'),(2670,620,'Guarda'),(2671,620,'Leiria'),(2672,620,'Lisbon'),(2673,620,'Portalegre'),(2674,620,'Porto'),(2675,620,'Santarém'),(2676,620,'Setúbal'),(2677,620,'Viana do Castelo'),(2678,620,'Vila Real'),(2679,620,'Viseu'),(2680,620,'Azores'),(2681,624,'Bafatá'),(2682,624,'Quinara'),(2683,624,'Oio'),(2684,624,'Bolama'),(2685,624,'Cacheu'),(2686,624,'Tombali'),(2687,624,'Gabú'),(2688,624,'Bissau'),(2689,624,'Biombo'),(2690,626,'Bobonaro'),(2691,630,'Puerto Rico'),(2692,634,'Ad Dawḩah'),(2693,634,'Al Ghuwayrīyah'),(2694,634,'Al Jumaylīyah'),(2695,634,'Al Khawr'),(2696,634,'Al Wakrah Municipality'),(2697,634,'Ar Rayyān'),(2698,634,'Jarayan al Batinah'),(2699,634,'Madīnat ash Shamāl'),(2700,634,'Umm Şalāl'),(2701,634,'Al Wakrah'),(2702,634,'Jarayān al Bāţinah'),(2703,634,'Umm Sa‘īd'),(2704,638,'Réunion'),(2705,642,'Alba'),(2706,642,'Arad'),(2707,642,'Argeş'),(2708,642,'Bacău'),(2709,642,'Bihor'),(2710,642,'Bistriţa-Năsăud'),(2711,642,'Botoşani'),(2712,642,'Brăila'),(2713,642,'Braşov'),(2714,642,'Bucureşti'),(2715,642,'Buzău'),(2716,642,'Caraş-Severin'),(2717,642,'Cluj'),(2718,642,'Constanţa'),(2719,642,'Covasna'),(2720,642,'Dâmboviţa'),(2721,642,'Dolj'),(2722,642,'Galaţi'),(2723,642,'Gorj'),(2724,642,'Harghita'),(2725,642,'Hunedoara'),(2726,642,'Ialomiţa'),(2727,642,'Iaşi'),(2728,642,'Maramureş'),(2729,642,'Mehedinţi'),(2730,642,'Mureş'),(2731,642,'Neamţ'),(2732,642,'Olt'),(2733,642,'Prahova'),(2734,642,'Sălaj'),(2735,642,'Satu Mare'),(2736,642,'Sibiu'),(2737,642,'Suceava'),(2738,642,'Teleorman'),(2739,642,'Timiş'),(2740,642,'Tulcea'),(2741,642,'Vaslui'),(2742,642,'Vâlcea'),(2743,642,'Judeţul Vrancea'),(2744,642,'Călăraşi'),(2745,642,'Giurgiu'),(2746,642,'Ilfov'),(2747,643,'Adygeya'),(2748,643,'Altay'),(2749,643,'Altayskiy Kray'),(2750,643,'Amur'),(2751,643,'Arkhangelskaya oblast'),(2752,643,'Astrakhan'),(2753,643,'Bashkortostan'),(2754,643,'Belgorod'),(2755,643,'Brjansk'),(2756,643,'Buryatiya'),(2757,643,'Chechnya'),(2758,643,'Tsjeljabinsk'),(2759,643,'Zabaïkalski Kray'),(2760,643,'Chukotskiy Avtonomnyy Okrug'),(2761,643,'Chuvashia'),(2762,643,'Dagestan'),(2763,643,'Ingushetiya'),(2764,643,'Irkutsk'),(2765,643,'Ivanovo'),(2766,643,'Kabardino-Balkariya'),(2767,643,'Kaliningrad'),(2768,643,'Kalmykiya'),(2769,643,'Kaluga'),(2770,643,'Karachayevo-Cherkesiya'),(2771,643,'Kareliya'),(2772,643,'Kemerovo'),(2773,643,'Khabarovsk Krai'),(2774,643,'Khakasiya'),(2775,643,'Khanty-Mansiyskiy Avtonomnyy Okrug'),(2776,643,'Kirov'),(2777,643,'Komi'),(2778,643,'Kostroma'),(2779,643,'Krasnodarskiy Kray'),(2780,643,'Kurgan'),(2781,643,'Kursk'),(2782,643,'Leningradskaya Oblastʼ'),(2783,643,'Lipetsk'),(2784,643,'Magadan'),(2785,643,'Mariy-El'),(2786,643,'Mordoviya'),(2787,643,'Moskovskaya Oblastʼ'),(2788,643,'Moscow'),(2789,643,'Murmansk Oblast'),(2790,643,'Nenetskiy Avtonomnyy Okrug'),(2791,643,'Nizjnij Novgorod'),(2792,643,'Novgorod'),(2793,643,'Novosibirsk'),(2794,643,'Omsk'),(2795,643,'Orenburg'),(2796,643,'Orjol'),(2797,643,'Penza'),(2798,643,'Primorskiy Kray'),(2799,643,'Pskov'),(2800,643,'Rostov'),(2801,643,'Rjazan'),(2802,643,'Sakha'),(2803,643,'Sakhalin'),(2804,643,'Samara'),(2805,643,'Sankt-Peterburg'),(2806,643,'Saratov'),(2807,643,'Severnaya Osetiya-Alaniya'),(2808,643,'Smolensk'),(2809,643,'Stavropolʼskiy Kray'),(2810,643,'Sverdlovsk'),(2811,643,'Tambov'),(2812,643,'Tatarstan'),(2813,643,'Tomsk'),(2814,643,'Tula'),(2815,643,'Tverskaya Oblast’'),(2816,643,'Tjumen'),(2817,643,'Tyva'),(2818,643,'Udmurtiya'),(2819,643,'Uljanovsk'),(2820,643,'Vladimir'),(2821,643,'Volgograd'),(2822,643,'Vologda'),(2823,643,'Voronezj'),(2824,643,'Yamalo-Nenetskiy Avtonomnyy Okrug'),(2825,643,'Jaroslavl'),(2826,643,'Jewish Autonomous Oblast'),(2827,643,'Perm'),(2828,643,'Krasnoyarskiy Kray'),(2829,643,'Kamtsjatka'),(2830,643,'RSJA'),(2831,646,'Eastern Province'),(2832,646,'Kigali City'),(2833,646,'Northern Province'),(2834,646,'Western Province'),(2835,646,'Southern Province'),(2836,654,'Ascension'),(2837,654,'Saint Helena'),(2838,654,'Tristan da Cunha'),(2839,659,'Christ Church Nichola Town'),(2840,659,'Saint Anne Sandy Point'),(2841,659,'Saint George Basseterre'),(2842,659,'Saint George Gingerland'),(2843,659,'Saint James Windwa'),(2844,659,'Saint John Capesterre'),(2845,659,'Saint John Figtree'),(2846,659,'Saint Mary Cayon'),(2847,659,'Saint Paul Capesterre'),(2848,659,'Saint Paul Charlestown'),(2849,659,'Saint Peter Basseterre'),(2850,659,'Saint Thomas Lowland'),(2851,659,'Saint Thomas Middle Island'),(2852,659,'Trinity Palmetto Point'),(2853,660,'Anguilla'),(2854,662,'Anse-la-Raye'),(2855,662,'Dauphin'),(2856,662,'Castries'),(2857,662,'Choiseul'),(2858,662,'Dennery'),(2859,662,'Gros-Islet'),(2860,662,'Laborie'),(2861,662,'Micoud'),(2862,662,'Soufrière'),(2863,662,'Vieux-Fort'),(2864,662,'Praslin'),(2865,666,'Saint-Pierre-et-Miquelon'),(2866,670,'Charlotte'),(2867,670,'Saint Andrew'),(2868,670,'Saint David'),(2869,670,'Saint George'),(2870,670,'Saint Patrick'),(2871,670,'Grenadines'),(2872,674,'Acquaviva'),(2873,674,'Chiesanuova'),(2874,674,'Domagnano'),(2875,674,'Faetano'),(2876,674,'Fiorentino'),(2877,674,'Borgo Maggiore'),(2878,674,'San Marino'),(2879,674,'Montegiardino'),(2880,674,'Serravalle'),(2881,678,'Príncipe'),(2882,678,'Príncipe'),(2883,678,'São Tomé'),(2884,682,'Al Bāḩah'),(2885,682,'Al Madīnah'),(2886,682,'Ash Sharqīyah'),(2887,682,'Al Qaşīm'),(2888,682,'Ar Riyāḑ'),(2889,682,'‘Asīr'),(2890,682,'Ḩāʼil'),(2891,682,'Makkah'),(2892,682,'Northern Borders Region'),(2893,682,'Najrān'),(2894,682,'Jīzān'),(2895,682,'Tabūk'),(2896,682,'Al Jawf'),(2897,686,'Dakar'),(2898,686,'Diourbel'),(2899,686,'Saint-Louis'),(2900,686,'Tambacounda'),(2901,686,'Thiès'),(2902,686,'Louga'),(2903,686,'Fatick'),(2904,686,'Kaolack'),(2905,686,'Kolda Region'),(2906,686,'Ziguinchor'),(2907,686,'Louga'),(2908,686,'Saint-Louis'),(2909,686,'Matam'),(2910,688,'Autonomna Pokrajina Vojvodina'),(2911,690,'Anse aux Pins'),(2912,690,'Anse Boileau'),(2913,690,'Anse Etoile'),(2914,690,'Anse Louis'),(2915,690,'Anse Royale'),(2916,690,'Baie Lazare'),(2917,690,'Baie Sainte Anne'),(2918,690,'Beau Vallon'),(2919,690,'Bel Air'),(2920,690,'Bel Ombre'),(2921,690,'Cascade'),(2922,690,'Glacis'),(2923,690,'Saint Thomas Middle Island Parish'),(2924,690,'Grand Anse Praslin'),(2925,690,'Trinity Palmetto Point Parish'),(2926,690,'La Riviere Anglaise'),(2927,690,'Mont Buxton'),(2928,690,'Mont Fleuri'),(2929,690,'Plaisance'),(2930,690,'Pointe Larue'),(2931,690,'Port Glaud'),(2932,690,'Saint Louis'),(2933,690,'Takamaka'),(2934,690,'Anse aux Pins'),(2935,690,'Inner Islands'),(2936,690,'English River'),(2937,690,'Port Glaud'),(2938,690,'Baie Lazare'),(2939,690,'Beau Vallon'),(2940,690,'Bel Ombre'),(2941,690,'Glacis'),(2942,690,'Grand Anse Mahe'),(2943,690,'Grand Anse Praslin'),(2944,690,'Inner Islands'),(2945,690,'English River'),(2946,690,'Mont Fleuri'),(2947,690,'Plaisance'),(2948,690,'Pointe Larue'),(2949,690,'Port Glaud'),(2950,690,'Takamaka'),(2951,690,'Au Cap'),(2952,690,'Les Mamelles'),(2953,690,'Roche Caiman'),(2954,694,'Eastern Province'),(2955,694,'Northern Province'),(2956,694,'Southern Province'),(2957,694,'Western Area'),(2958,702,'Singapore'),(2959,703,'Banskobystrický'),(2960,703,'Bratislavský'),(2961,703,'Košický'),(2962,703,'Nitriansky'),(2963,703,'Prešovský'),(2964,703,'Trenčiansky'),(2965,703,'Trnavský'),(2966,703,'Žilinský'),(2967,704,'An Giang'),(2968,704,'Bắc Thái Tỉnh'),(2969,704,'Bến Tre'),(2970,704,'Cao Bang'),(2971,704,'Cao Bằng'),(2972,704,'Ten Bai'),(2973,704,'Ðồng Tháp'),(2974,704,'Hà Bắc Tỉnh'),(2975,704,'Hải Hưng Tỉnh'),(2976,704,'Hải Phòng'),(2977,704,'Hoa Binh'),(2978,704,'Ha Tay'),(2979,704,'Hồ Chí Minh'),(2980,704,'Kiến Giang'),(2981,704,'Lâm Ðồng'),(2982,704,'Long An'),(2983,704,'Minh Hải Tỉnh'),(2984,704,'Thua Thien-Hue'),(2985,704,'Quang Nam'),(2986,704,'Kon Tum'),(2987,704,'Quảng Nam-Ðà Nẵng Tỉnh'),(2988,704,'Quảng Ninh'),(2989,704,'Sông Bé Tỉnh'),(2990,704,'Sơn La'),(2991,704,'Tây Ninh'),(2992,704,'Thanh Hóa'),(2993,704,'Thái Bình'),(2994,704,'Nin Thuan'),(2995,704,'Tiền Giang'),(2996,704,'Vinh Phú Tỉnh'),(2997,704,'Lạng Sơn'),(2998,704,'Binh Thuan'),(2999,704,'Long An'),(3000,704,'Ðồng Nai'),(3001,704,'Ha Nội'),(3002,704,'Bà Rịa-Vũng Tàu'),(3003,704,'Bình Ðịnh'),(3004,704,'Bình Thuận'),(3005,704,'Gia Lai'),(3006,704,'Hà Giang'),(3007,704,'Hà Tây'),(3008,704,'Hà Tĩnh'),(3009,704,'Hòa Bình'),(3010,704,'Khánh Hòa'),(3011,704,'Kon Tum'),(3012,704,'Nam Hà Tỉnh'),(3013,704,'Nghệ An'),(3014,704,'Ninh Bình'),(3015,704,'Ninh Thuận'),(3016,704,'Phú Yên'),(3017,704,'Quảng Bình'),(3018,704,'Quảng Ngãi'),(3019,704,'Quảng Trị'),(3020,704,'Sóc Trăng'),(3021,704,'Thừa Thiên-Huế'),(3022,704,'Trà Vinh'),(3023,704,'Tuyên Quang'),(3024,704,'Vĩnh Long'),(3025,704,'Yên Bái'),(3026,704,'Bắc Giang'),(3027,704,'Bắc Kạn'),(3028,704,'Bạc Liêu'),(3029,704,'Bắc Ninh'),(3030,704,'Bình Dương'),(3031,704,'Bình Phước'),(3032,704,'Cà Mau'),(3033,704,'Ðà Nẵng'),(3034,704,'Hải Dương'),(3035,704,'Hà Nam'),(3036,704,'Hưng Yên'),(3037,704,'Nam Ðịnh'),(3038,704,'Phú Thọ'),(3039,704,'Quảng Nam'),(3040,704,'Thái Nguyên'),(3041,704,'Vĩnh Phúc'),(3042,704,'Cần Thơ'),(3043,704,'Ðắc Lắk'),(3044,704,'Lai Châu'),(3045,704,'Lào Cai'),(3046,705,'Notranjska'),(3047,705,'Koroška'),(3048,705,'Štajerska'),(3049,705,'Prekmurje'),(3050,705,'Primorska'),(3051,705,'Gorenjska'),(3052,705,'Dolenjska'),(3053,706,'Bakool'),(3054,706,'Banaadir'),(3055,706,'Bari'),(3056,706,'Bay'),(3057,706,'Galguduud'),(3058,706,'Gedo'),(3059,706,'Hiiraan'),(3060,706,'Middle Juba'),(3061,706,'Lower Juba'),(3062,706,'Mudug'),(3063,706,'Sanaag'),(3064,706,'Middle Shabele'),(3065,706,'Shabeellaha Hoose'),(3066,706,'Nugaal'),(3067,706,'Togdheer'),(3068,706,'Woqooyi Galbeed'),(3069,706,'Awdal'),(3070,706,'Sool'),(3071,710,'KwaZulu-Natal'),(3072,710,'Free State'),(3073,710,'Eastern Cape'),(3074,710,'Gauteng'),(3075,710,'Mpumalanga'),(3076,710,'Northern Cape'),(3077,710,'Limpopo'),(3078,710,'North-West'),(3079,710,'Western Cape'),(3080,716,'Manicaland'),(3081,716,'Midlands'),(3082,716,'Mashonaland Central'),(3083,716,'Mashonaland East'),(3084,716,'Mashonaland West'),(3085,716,'Matabeleland North'),(3086,716,'Matabeleland South'),(3087,716,'Masvingo'),(3088,716,'Bulawayo'),(3089,716,'Harare'),(3090,724,'Ceuta'),(3091,724,'Balearic Islands'),(3092,724,'La Rioja'),(3093,724,'Autonomous Region of Madrid'),(3094,724,'Murcia'),(3095,724,'Navarre'),(3096,724,'Asturias'),(3097,724,'Cantabria'),(3098,724,'Andalusia'),(3099,724,'Aragon'),(3100,724,'Canary Islands'),(3101,724,'Castille-La Mancha'),(3102,724,'Castille and León'),(3103,724,'Catalonia'),(3104,724,'Extremadura'),(3105,724,'Galicia'),(3106,724,'Basque Country'),(3107,724,'Valencia'),(3108,732,'Western Sahara'),(3109,736,'Al Wilāyah al Wusţá'),(3110,736,'Al Wilāyah al Istiwāʼīyah'),(3111,736,'Khartoum'),(3112,736,'Ash Shamaliyah'),(3113,736,'Al Wilāyah ash Sharqīyah'),(3114,736,'Ba?r al Ghazal Wilayat'),(3115,736,'Darfur Wilayat'),(3116,736,'Kurdufan Wilayat'),(3117,736,'Upper Nile'),(3118,736,'Red Sea'),(3119,736,'Lakes'),(3120,736,'Al Jazirah'),(3121,736,'Al Qadarif'),(3122,736,'Unity'),(3123,736,'White Nile'),(3124,736,'Blue Nile'),(3125,736,'Northern'),(3126,736,'Central Equatoria'),(3127,736,'Gharb al Istiwāʼīyah'),(3128,736,'Western Bahr al Ghazal'),(3129,736,'Gharb Dārfūr'),(3130,736,'Gharb Kurdufān'),(3131,736,'Janūb Dārfūr'),(3132,736,'Janūb Kurdufān'),(3133,736,'Junqalī'),(3134,736,'Kassalā'),(3135,736,'Nahr an Nīl'),(3136,736,'Shamāl Baḩr al Ghazāl'),(3137,736,'Shamāl Dārfūr'),(3138,736,'Shamāl Kurdufān'),(3139,736,'Eastern Equatoria'),(3140,736,'Sinnār'),(3141,736,'Warab'),(3142,740,'Brokopondo'),(3143,740,'Commewijne'),(3144,740,'Coronie'),(3145,740,'Marowijne'),(3146,740,'Nickerie'),(3147,740,'Para'),(3148,740,'Paramaribo'),(3149,740,'Saramacca'),(3150,740,'Sipaliwini'),(3151,740,'Wanica'),(3152,748,'Hhohho'),(3153,748,'Lubombo'),(3154,748,'Manzini'),(3155,748,'Shiselweni'),(3156,752,'Blekinge'),(3157,752,'Gävleborg'),(3158,752,'Gotland'),(3159,752,'Halland'),(3160,752,'Jämtland'),(3161,752,'Jönköping'),(3162,752,'Kalmar'),(3163,752,'Dalarna'),(3164,752,'Kronoberg'),(3165,752,'Norrbotten'),(3166,752,'Örebro'),(3167,752,'Östergötland'),(3168,752,'Södermanland'),(3169,752,'Uppsala'),(3170,752,'Värmland'),(3171,752,'Västerbotten'),(3172,752,'Västernorrland'),(3173,752,'Västmanland'),(3174,752,'Stockholm'),(3175,752,'Skåne'),(3176,752,'Västra Götaland'),(3177,756,'Aargau'),(3178,756,'Appenzell Innerrhoden'),(3179,756,'Appenzell Ausserrhoden'),(3180,756,'Bern'),(3181,756,'Basel-Landschaft'),(3182,756,'Kanton Basel-Stadt'),(3183,756,'Fribourg'),(3184,756,'Genève'),(3185,756,'Glarus'),(3186,756,'Graubünden'),(3187,756,'Jura'),(3188,756,'Luzern'),(3189,756,'Neuchâtel'),(3190,756,'Nidwalden'),(3191,756,'Obwalden'),(3192,756,'Kanton St. Gallen'),(3193,756,'Schaffhausen'),(3194,756,'Solothurn'),(3195,756,'Schwyz'),(3196,756,'Thurgau'),(3197,756,'Ticino'),(3198,756,'Uri'),(3199,756,'Vaud'),(3200,756,'Valais'),(3201,756,'Zug'),(3202,756,'Zürich'),(3203,760,'Al-Hasakah'),(3204,760,'Latakia'),(3205,760,'Quneitra'),(3206,760,'Ar-Raqqah'),(3207,760,'As-Suwayda'),(3208,760,'Daraa'),(3209,760,'Deir ez-Zor'),(3210,760,'Rif-dimashq'),(3211,760,'Aleppo'),(3212,760,'Hama Governorate'),(3213,760,'Homs'),(3214,760,'Idlib'),(3215,760,'Damascus City'),(3216,760,'Tartus'),(3217,762,'Gorno-Badakhshan'),(3218,762,'Khatlon'),(3219,762,'Sughd'),(3220,762,'Dushanbe'),(3221,762,'Region of Republican Subordination'),(3222,764,'Mae Hong Son'),(3223,764,'Chiang Mai'),(3224,764,'Chiang Rai'),(3225,764,'Nan'),(3226,764,'Lamphun'),(3227,764,'Lampang'),(3228,764,'Phrae'),(3229,764,'Tak'),(3230,764,'Sukhothai'),(3231,764,'Uttaradit'),(3232,764,'Kamphaeng Phet'),(3233,764,'Phitsanulok'),(3234,764,'Phichit'),(3235,764,'Phetchabun'),(3236,764,'Uthai Thani'),(3237,764,'Nakhon Sawan'),(3238,764,'Nong Khai'),(3239,764,'Loei'),(3240,764,'Sakon Nakhon'),(3241,764,'Nakhon Phanom'),(3242,764,'Khon Kaen'),(3243,764,'Kalasin'),(3244,764,'Maha Sarakham'),(3245,764,'Roi Et'),(3246,764,'Chaiyaphum'),(3247,764,'Nakhon Ratchasima'),(3248,764,'Buriram'),(3249,764,'Surin'),(3250,764,'Sisaket'),(3251,764,'Narathiwat'),(3252,764,'Chai Nat'),(3253,764,'Sing Buri'),(3254,764,'Lop Buri'),(3255,764,'Ang Thong'),(3256,764,'Phra Nakhon Si Ayutthaya'),(3257,764,'Sara Buri'),(3258,764,'Nonthaburi'),(3259,764,'Pathum Thani'),(3260,764,'Bangkok'),(3261,764,'Phayao'),(3262,764,'Samut Prakan'),(3263,764,'Nakhon Nayok'),(3264,764,'Chachoengsao'),(3265,764,'Chon Buri'),(3266,764,'Rayong'),(3267,764,'Chanthaburi'),(3268,764,'Trat'),(3269,764,'Kanchanaburi'),(3270,764,'Suphan Buri'),(3271,764,'Ratchaburi'),(3272,764,'Nakhon Pathom'),(3273,764,'Samut Songkhram'),(3274,764,'Samut Sakhon'),(3275,764,'Phetchaburi'),(3276,764,'Prachuap Khiri Khan'),(3277,764,'Chumphon'),(3278,764,'Ranong'),(3279,764,'Surat Thani'),(3280,764,'Phangnga'),(3281,764,'Phuket'),(3282,764,'Krabi'),(3283,764,'Nakhon Si Thammarat'),(3284,764,'Trang'),(3285,764,'Phatthalung'),(3286,764,'Satun'),(3287,764,'Songkhla'),(3288,764,'Pattani'),(3289,764,'Yala'),(3290,764,'Yasothon'),(3291,764,'Nakhon Phanom'),(3292,764,'Prachin Buri'),(3293,764,'Ubon Ratchathani'),(3294,764,'Udon Thani'),(3295,764,'Amnat Charoen'),(3296,764,'Mukdahan'),(3297,764,'Nong Bua Lamphu'),(3298,764,'Sa Kaeo'),(3299,768,'Amlame'),(3300,768,'Aneho'),(3301,768,'Atakpame'),(3302,768,'Bafilo'),(3303,768,'Bassar'),(3304,768,'Dapaong'),(3305,768,'Kante'),(3306,768,'Klouto'),(3307,768,'Lama-Kara'),(3308,768,'Lome'),(3309,768,'Mango'),(3310,768,'Niamtougou'),(3311,768,'Notse'),(3312,768,'Kpagouda'),(3313,768,'Badou'),(3314,768,'Sotouboua'),(3315,768,'Tabligbo'),(3316,768,'Tsevie'),(3317,768,'Tchamba'),(3318,768,'Tchaoudjo'),(3319,768,'Vogan'),(3320,768,'Centrale'),(3321,768,'Kara'),(3322,768,'Maritime'),(3323,768,'Plateaux'),(3324,768,'Savanes'),(3325,772,'Tokelau'),(3326,776,'Ha‘apai'),(3327,776,'Tongatapu'),(3328,776,'Vava‘u'),(3329,780,'Port of Spain'),(3330,780,'San Fernando'),(3331,780,'Chaguanas'),(3332,780,'Arima'),(3333,780,'Point Fortin Borough'),(3334,780,'Couva-Tabaquite-Taaro'),(3335,780,'Diego Martin'),(3336,780,'Penal-Debe'),(3337,780,'Princes Town'),(3338,780,'Rio Claro-Mayaro'),(3339,780,'San Juan-Laventille'),(3340,780,'Sangre Grande'),(3341,780,'Siparia'),(3342,780,'Tunapuna-Piarco'),(3343,784,'Abū Z̧aby'),(3344,784,'Ajman'),(3345,784,'Dubayy'),(3346,784,'Al Fujayrah'),(3347,784,'Raʼs al Khaymah'),(3348,784,'Ash Shāriqah'),(3349,784,'Umm al Qaywayn'),(3350,788,'Tunis al Janubiyah Wilayat'),(3351,788,'Al Qaşrayn'),(3352,788,'Al Qayrawān'),(3353,788,'Jundūbah'),(3354,788,'Kef'),(3355,788,'Al Mahdīyah'),(3356,788,'Al Munastīr'),(3357,788,'Bājah'),(3358,788,'Banzart'),(3359,788,'Nābul'),(3360,788,'Silyānah'),(3361,788,'Sūsah'),(3362,788,'Bin ‘Arūs'),(3363,788,'Madanīn'),(3364,788,'Qābis'),(3365,788,'Qafşah'),(3366,788,'Qibilī'),(3367,788,'Şafāqis'),(3368,788,'Sīdī Bū Zayd'),(3369,788,'Taţāwīn'),(3370,788,'Tawzar'),(3371,788,'Tūnis'),(3372,788,'Zaghwān'),(3373,788,'Ariana'),(3374,788,'Manouba'),(3375,792,'Adıyaman'),(3376,792,'Afyonkarahisar'),(3377,792,'Ağrı Province'),(3378,792,'Amasya Province'),(3379,792,'Antalya Province'),(3380,792,'Artvin Province'),(3381,792,'Aydın Province'),(3382,792,'Balıkesir Province'),(3383,792,'Bilecik Province'),(3384,792,'Bingöl Province'),(3385,792,'Bitlis Province'),(3386,792,'Bolu Province'),(3387,792,'Burdur Province'),(3388,792,'Bursa'),(3389,792,'Çanakkale Province'),(3390,792,'Çorum Province'),(3391,792,'Denizli Province'),(3392,792,'Diyarbakır'),(3393,792,'Edirne Province'),(3394,792,'Elazığ'),(3395,792,'Erzincan Province'),(3396,792,'Erzurum Province'),(3397,792,'Eskişehir Province'),(3398,792,'Giresun Province'),(3399,792,'Hatay Province'),(3400,792,'Mersin Province'),(3401,792,'Isparta Province'),(3402,792,'Istanbul'),(3403,792,'İzmir'),(3404,792,'Kastamonu Province'),(3405,792,'Kayseri Province'),(3406,792,'Kırklareli Province'),(3407,792,'Kırşehir Province'),(3408,792,'Kocaeli Province'),(3409,792,'Kütahya Province'),(3410,792,'Malatya Province'),(3411,792,'Manisa Province'),(3412,792,'Kahramanmaraş Province'),(3413,792,'Muğla Province'),(3414,792,'Muş Province'),(3415,792,'Nevşehir'),(3416,792,'Ordu'),(3417,792,'Rize'),(3418,792,'Sakarya Province'),(3419,792,'Samsun Province'),(3420,792,'Sinop Province'),(3421,792,'Sivas Province'),(3422,792,'Tekirdağ Province'),(3423,792,'Tokat'),(3424,792,'Trabzon Province'),(3425,792,'Tunceli Province'),(3426,792,'Şanlıurfa Province'),(3427,792,'Uşak Province'),(3428,792,'Van Province'),(3429,792,'Yozgat Province'),(3430,792,'Ankara Province'),(3431,792,'Gümüşhane'),(3432,792,'Hakkâri Province'),(3433,792,'Konya Province'),(3434,792,'Mardin Province'),(3435,792,'Niğde'),(3436,792,'Siirt Province'),(3437,792,'Aksaray Province'),(3438,792,'Batman Province'),(3439,792,'Bayburt'),(3440,792,'Karaman Province'),(3441,792,'Kırıkkale Province'),(3442,792,'Şırnak Province'),(3443,792,'Adana Province'),(3444,792,'Çankırı Province'),(3445,792,'Gaziantep Province'),(3446,792,'Kars'),(3447,792,'Zonguldak'),(3448,792,'Ardahan Province'),(3449,792,'Bartın Province'),(3450,792,'Iğdır Province'),(3451,792,'Karabük'),(3452,792,'Kilis Province'),(3453,792,'Osmaniye Province'),(3454,792,'Yalova Province'),(3455,792,'Düzce'),(3456,795,'Ahal'),(3457,795,'Balkan'),(3458,795,'Daşoguz'),(3459,795,'Lebap'),(3460,795,'Mary'),(3461,796,'Turks and Caicos Islands'),(3462,798,'Tuvalu'),(3463,800,'Eastern Region'),(3464,800,'Northern Region'),(3465,800,'Central Region'),(3466,800,'Western Region'),(3467,804,'Cherkasʼka Oblastʼ'),(3468,804,'Chernihivsʼka Oblastʼ'),(3469,804,'Chernivetsʼka Oblastʼ'),(3470,804,'Dnipropetrovsʼka Oblastʼ'),(3471,804,'Donetsʼka Oblastʼ'),(3472,804,'Ivano-Frankivsʼka Oblastʼ'),(3473,804,'Kharkivsʼka Oblastʼ'),(3474,804,'Kherson Oblast'),(3475,804,'Khmelʼnytsʼka Oblastʼ'),(3476,804,'Kirovohradsʼka Oblastʼ'),(3477,804,'Avtonomna Respublika Krym'),(3478,804,'Misto Kyyiv'),(3479,804,'Kiev Oblast'),(3480,804,'Luhansʼka Oblastʼ'),(3481,804,'Lʼvivsʼka Oblastʼ'),(3482,804,'Mykolayivsʼka Oblastʼ'),(3483,804,'Odessa Oblast'),(3484,804,'Poltava Oblast'),(3485,804,'Rivnensʼka Oblastʼ'),(3486,804,'Misto Sevastopol'),(3487,804,'Sumy Oblast'),(3488,804,'Ternopilʼsʼka Oblastʼ'),(3489,804,'Vinnytsʼka Oblastʼ'),(3490,804,'Volynsʼka Oblastʼ'),(3491,804,'Zakarpatsʼka Oblastʼ'),(3492,804,'Zaporizʼka Oblastʼ'),(3493,804,'Zhytomyrsʼka Oblastʼ'),(3494,807,'Macedonia, The Former Yugoslav Republic of'),(3495,807,'Aračinovo'),(3496,807,'Bač'),(3497,807,'Belčišta'),(3498,807,'Berovo'),(3499,807,'Bistrica'),(3500,807,'Bitola'),(3501,807,'Blatec'),(3502,807,'Bogdanci'),(3503,807,'Opstina Bogomila'),(3504,807,'Bogovinje'),(3505,807,'Bosilovo'),(3506,807,'Brvenica'),(3507,807,'Čair'),(3508,807,'Capari'),(3509,807,'Čaška'),(3510,807,'Čegrana'),(3511,807,'Centar'),(3512,807,'Centar Župa'),(3513,807,'Češinovo'),(3514,807,'Čučer-Sandevo'),(3515,807,'Debar'),(3516,807,'Delčevo'),(3517,807,'Delogoždi'),(3518,807,'Demir Hisar'),(3519,807,'Demir Kapija'),(3520,807,'Dobruševo'),(3521,807,'Dolna Banjica'),(3522,807,'Dolneni'),(3523,807,'Opstina Gjorce Petrov'),(3524,807,'Drugovo'),(3525,807,'Džepčište'),(3526,807,'Gazi Baba'),(3527,807,'Gevgelija'),(3528,807,'Gostivar'),(3529,807,'Gradsko'),(3530,807,'Ilinden'),(3531,807,'Izvor'),(3532,807,'Jegunovce'),(3533,807,'Kamenjane'),(3534,807,'Karbinci'),(3535,807,'Karpoš'),(3536,807,'Kavadarci'),(3537,807,'Kičevo'),(3538,807,'Kisela Voda'),(3539,807,'Klečevce'),(3540,807,'Kočani'),(3541,807,'Konče'),(3542,807,'Kondovo'),(3543,807,'Konopište'),(3544,807,'Kosel'),(3545,807,'Kratovo'),(3546,807,'Kriva Palanka'),(3547,807,'Krivogaštani'),(3548,807,'Kruševo'),(3549,807,'Kukliš'),(3550,807,'Kukurečani'),(3551,807,'Kumanovo'),(3552,807,'Labuništa'),(3553,807,'Opstina Lipkovo'),(3554,807,'Lozovo'),(3555,807,'Lukovo'),(3556,807,'Makedonska Kamenica'),(3557,807,'Makedonski Brod'),(3558,807,'Mavrovi Anovi'),(3559,807,'Mešeišta'),(3560,807,'Miravci'),(3561,807,'Mogila'),(3562,807,'Murtino'),(3563,807,'Negotino'),(3564,807,'Negotino-Pološko'),(3565,807,'Novaci'),(3566,807,'Novo Selo'),(3567,807,'Obleševo'),(3568,807,'Ohrid'),(3569,807,'Orašac'),(3570,807,'Orizari'),(3571,807,'Oslomej'),(3572,807,'Pehčevo'),(3573,807,'Petrovec'),(3574,807,'Plasnica'),(3575,807,'Podareš'),(3576,807,'Prilep'),(3577,807,'Probištip'),(3578,807,'Radoviš'),(3579,807,'Opstina Rankovce'),(3580,807,'Resen'),(3581,807,'Rosoman'),(3582,807,'Opština Rostuša'),(3583,807,'Samokov'),(3584,807,'Saraj'),(3585,807,'Šipkovica'),(3586,807,'Sopište'),(3587,807,'Sopotnica'),(3588,807,'Srbinovo'),(3589,807,'Staravina'),(3590,807,'Star Dojran'),(3591,807,'Staro Nagoričane'),(3592,807,'Štip'),(3593,807,'Struga'),(3594,807,'Strumica'),(3595,807,'Studeničani'),(3596,807,'Šuto Orizari'),(3597,807,'Sveti Nikole'),(3598,807,'Tearce'),(3599,807,'Tetovo'),(3600,807,'Topolčani'),(3601,807,'Valandovo'),(3602,807,'Vasilevo'),(3603,807,'Veles'),(3604,807,'Velešta'),(3605,807,'Vevčani'),(3606,807,'Vinica'),(3607,807,'Vitolište'),(3608,807,'Vraneštica'),(3609,807,'Vrapčište'),(3610,807,'Vratnica'),(3611,807,'Vrutok'),(3612,807,'Zajas'),(3613,807,'Zelenikovo'),(3614,807,'Želino'),(3615,807,'Žitoše'),(3616,807,'Zletovo'),(3617,807,'Zrnovci'),(3618,818,'Ad Daqahlīyah'),(3619,818,'Al Baḩr al Aḩmar'),(3620,818,'Al Buḩayrah'),(3621,818,'Al Fayyūm'),(3622,818,'Al Gharbīyah'),(3623,818,'Alexandria'),(3624,818,'Al Ismā‘īlīyah'),(3625,818,'Al Jīzah'),(3626,818,'Al Minūfīyah'),(3627,818,'Al Minyā'),(3628,818,'Al Qāhirah'),(3629,818,'Al Qalyūbīyah'),(3630,818,'Al Wādī al Jadīd'),(3631,818,'Eastern Province'),(3632,818,'As Suways'),(3633,818,'Aswān'),(3634,818,'Asyūţ'),(3635,818,'Banī Suwayf'),(3636,818,'Būr Sa‘īd'),(3637,818,'Dumyāţ'),(3638,818,'Kafr ash Shaykh'),(3639,818,'Maţrūḩ'),(3640,818,'Qinā'),(3641,818,'Sūhāj'),(3642,818,'Janūb Sīnāʼ'),(3643,818,'Shamāl Sīnāʼ'),(3644,818,'Luxor'),(3645,818,'Helwan'),(3646,818,'6th of October'),(3647,826,'England'),(3648,826,'Northern Ireland'),(3649,826,'Scotland'),(3650,826,'Wales'),(3651,831,'Guernsey'),(3652,833,'Isle of Man'),(3653,834,'Arusha'),(3654,834,'Pwani'),(3655,834,'Dodoma'),(3656,834,'Iringa'),(3657,834,'Kigoma'),(3658,834,'Kilimanjaro'),(3659,834,'Lindi'),(3660,834,'Mara'),(3661,834,'Mbeya'),(3662,834,'Morogoro'),(3663,834,'Mtwara'),(3664,834,'Mwanza'),(3665,834,'Pemba North'),(3666,834,'Ruvuma'),(3667,834,'Shinyanga'),(3668,834,'Singida'),(3669,834,'Tabora'),(3670,834,'Tanga'),(3671,834,'Kagera'),(3672,834,'Pemba South'),(3673,834,'Zanzibar Central/South'),(3674,834,'Zanzibar North'),(3675,834,'Dar es Salaam'),(3676,834,'Rukwa'),(3677,834,'Zanzibar Urban/West'),(3678,834,'Arusha'),(3679,834,'Manyara'),(3680,840,'Alaska'),(3681,840,'Alabama'),(3682,840,'Arkansas'),(3683,840,'Arizona'),(3684,840,'California'),(3685,840,'Colorado'),(3686,840,'Connecticut'),(3687,840,'District of Columbia'),(3688,840,'Delaware'),(3689,840,'Florida'),(3690,840,'Georgia'),(3691,840,'Hawaii'),(3692,840,'Iowa'),(3693,840,'Idaho'),(3694,840,'Illinois'),(3695,840,'Indiana'),(3696,840,'Kansas'),(3697,840,'Kentucky'),(3698,840,'Louisiana'),(3699,840,'Massachusetts'),(3700,840,'Maryland'),(3701,840,'Maine'),(3702,840,'Michigan'),(3703,840,'Minnesota'),(3704,840,'Missouri'),(3705,840,'Mississippi'),(3706,840,'Montana'),(3707,840,'North Carolina'),(3708,840,'North Dakota'),(3709,840,'Nebraska'),(3710,840,'New Hampshire'),(3711,840,'New Jersey'),(3712,840,'New Mexico'),(3713,840,'Nevada'),(3714,840,'New York'),(3715,840,'Ohio'),(3716,840,'Oklahoma'),(3717,840,'Oregon'),(3718,840,'Pennsylvania'),(3719,840,'Rhode Island'),(3720,840,'South Carolina'),(3721,840,'South Dakota'),(3722,840,'Tennessee'),(3723,840,'Texas'),(3724,840,'Utah'),(3725,840,'Virginia'),(3726,840,'Vermont'),(3727,840,'Washington'),(3728,840,'Wisconsin'),(3729,840,'West Virginia'),(3730,840,'Wyoming'),(3731,850,'Virgin Islands'),(3732,854,'Boucle du Mouhoun'),(3733,854,'Cascades'),(3734,854,'Centre'),(3735,854,'Centre-Est'),(3736,854,'Centre-Nord'),(3737,854,'Centre-Ouest'),(3738,854,'Centre-Sud'),(3739,854,'Est'),(3740,854,'Hauts-Bassins'),(3741,854,'Nord'),(3742,854,'Plateau-Central'),(3743,854,'Sahel'),(3744,854,'Sud-Ouest'),(3745,855,'Komuna e Deçanit'),(3746,855,'Komuna e Dragashit'),(3747,855,'Komuna e Ferizajt'),(3748,855,'Komuna e Fushë Kosovës'),(3749,855,'Komuna e Gjakovës'),(3750,855,'Komuna e Gjilanit'),(3751,855,'Komuna e Drenasit'),(3752,855,'Komuna e Istogut'),(3753,855,'Komuna e Kaçanikut'),(3754,855,'Komuna e Kamenicës'),(3755,855,'Komuna e Klinës'),(3756,855,'Komuna e Leposaviqit'),(3757,855,'Komuna e Lipjanit'),(3758,855,'Komuna e Malisheves'),(3759,855,'Komuna e Mitrovicës'),(3760,855,'Komuna e Novobërdës'),(3761,855,'Komuna e Obiliqit'),(3762,855,'Komuna e Pejës'),(3763,855,'Komuna e Podujevës'),(3764,855,'Komuna e Prishtinës'),(3765,855,'Komuna e Prizrenit'),(3766,855,'Komuna e Rahovecit'),(3767,855,'Komuna e Shtërpcës'),(3768,855,'Komuna e Shtimes'),(3769,855,'Komuna e Skenderajt'),(3770,855,'Komuna e Thërandës'),(3771,855,'Komuna e Vitisë'),(3772,855,'Komuna e Vushtrrisë'),(3773,855,'Komuna e Zubin Potokut'),(3774,855,'Komuna e Zveçanit'),(3775,858,'Artigas Department'),(3776,858,'Canelones Department'),(3777,858,'Cerro Largo Department'),(3778,858,'Colonia Department'),(3779,858,'Durazno'),(3780,858,'Flores'),(3781,858,'Florida Department'),(3782,858,'Lavalleja Department'),(3783,858,'Maldonado Department'),(3784,858,'Montevideo'),(3785,858,'Paysandú'),(3786,858,'Río Negro'),(3787,858,'Rivera'),(3788,858,'Rocha'),(3789,858,'Salto'),(3790,858,'San José'),(3791,858,'Soriano Department'),(3792,858,'Tacuarembó'),(3793,858,'Treinta y Tres'),(3794,860,'Andijon'),(3795,860,'Buxoro'),(3796,860,'Farg ona'),(3797,860,'Xorazm'),(3798,860,'Namangan'),(3799,860,'Navoiy'),(3800,860,'Qashqadaryo'),(3801,860,'Karakaakstan'),(3802,860,'Samarqand'),(3803,860,'Surxondaryo'),(3804,860,'Toshkent Shahri'),(3805,860,'Toshkent'),(3806,860,'Jizzax'),(3807,860,'Sirdaryo'),(3808,862,'Amazonas'),(3809,862,'Anzoátegui'),(3810,862,'Apure'),(3811,862,'Aragua'),(3812,862,'Barinas'),(3813,862,'Bolívar'),(3814,862,'Carabobo'),(3815,862,'Cojedes'),(3816,862,'Delta Amacuro'),(3817,862,'Distrito Federal'),(3818,862,'Falcón'),(3819,862,'Guárico'),(3820,862,'Lara'),(3821,862,'Mérida'),(3822,862,'Miranda'),(3823,862,'Monagas'),(3824,862,'Isla Margarita'),(3825,862,'Portuguesa'),(3826,862,'Sucre'),(3827,862,'Táchira'),(3828,862,'Trujillo'),(3829,862,'Yaracuy'),(3830,862,'Zulia'),(3831,862,'Dependencias Federales'),(3832,862,'Distrito Capital'),(3833,862,'Vargas'),(3834,882,'A‘ana'),(3835,882,'Aiga-i-le-Tai'),(3836,882,'Atua'),(3837,882,'Fa‘asaleleaga'),(3838,882,'Gaga‘emauga'),(3839,882,'Va‘a-o-Fonoti'),(3840,882,'Gagaifomauga'),(3841,882,'Palauli'),(3842,882,'Satupa‘itea'),(3843,882,'Tuamasaga'),(3844,882,'Vaisigano'),(3845,887,'Abyan'),(3846,887,'‘Adan'),(3847,887,'Al Mahrah'),(3848,887,'Ḩaḑramawt'),(3849,887,'Shabwah'),(3850,887,'San’a’'),(3851,887,'Ta’izz'),(3852,887,'Al Ḩudaydah'),(3853,887,'Dhamar'),(3854,887,'Al Maḩwīt'),(3855,887,'Dhamār'),(3856,887,'Maʼrib'),(3857,887,'Şa‘dah'),(3858,887,'Şan‘āʼ'),(3859,887,'Aḑ Ḑāli‘'),(3860,887,'Omran'),(3861,887,'Al Bayḑāʼ'),(3862,887,'Al Jawf'),(3863,887,'Ḩajjah'),(3864,887,'Ibb'),(3865,887,'Laḩij'),(3866,887,'Ta‘izz'),(3867,887,'Amanat Al Asimah'),(3868,887,'Muḩāfaz̧at Raymah'),(3869,891,'Crna Gora (Montenegro)'),(3870,891,'Srbija (Serbia)'),(3871,894,'North-Western'),(3872,894,'Copperbelt'),(3873,894,'Western'),(3874,894,'Southern'),(3875,894,'Central'),(3876,894,'Eastern'),(3877,894,'Northern'),(3878,894,'Luapula'),(3879,894,'Lusaka');


--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_id` bigint(20) NOT NULL DEFAULT '0',
  `grade_id` bigint(20) NOT NULL DEFAULT '0',
  `gender` varchar(45) NOT NULL DEFAULT '',
  `dob` date NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `homeroom_id` bigint(20) DEFAULT '0',
  `grade_status` varchar(45) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`student_id`),
  KEY `fk_stud_reg_id_idx` (`reg_id`),
  KEY `fk_homeroom_id_idx` (`homeroom_id`),
  CONSTRAINT `fk_homeroom_id` FOREIGN KEY (`homeroom_id`) REFERENCES `homeroom_classes` (`homeroom_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stud_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `student_assignment_status`
--

CREATE TABLE `student_assignment_status` (
  `student_assignment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignment_id` bigint(20) NOT NULL,
  `student_id` bigint(20) DEFAULT '0',
  `performance_group_id` bigint(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT '',
  `submitdate` date DEFAULT NULL,
  `percentage` float DEFAULT NULL,
  `graded_status` varchar(30) DEFAULT 'Not Graded',
  `benchmark_assignment_id` int(11) DEFAULT NULL,
  `retest_id` int(11) DEFAULT NULL,
  `academic_grade_id` bigint(20) DEFAULT NULL,
  `last_saved_set` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`student_assignment_id`),
  KEY `fk_assignment_id_idx` (`assignment_id`),
  KEY `fk_student_id_idx` (`student_id`),
  KEY `fk_performance_group` (`performance_group_id`),
  KEY `fk_academic_grade_id` (`academic_grade_id`),
  KEY `fk_phonic_set_id_idx` (`last_saved_set`),
  CONSTRAINT `fk_phonic_set_id` FOREIGN KEY (`last_saved_set`) REFERENCES `phonic_groups` (`group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_academic_grade_id` FOREIGN KEY (`academic_grade_id`) REFERENCES `academic_grades` (`acedamic_grade_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assignment_id` FOREIGN KEY (`assignment_id`) REFERENCES `assignment` (`assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_performance_groups` FOREIGN KEY (`performance_group_id`) REFERENCES `performancetask_groups` (`performance_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
-- Table structure for table `student_composite_activity_score`
--

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

--
-- Table structure for table `student_composite_lesson_score`
--

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


--
-- Table structure for table `student_composite_project_score`
--

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

--
-- Table structure for table `student_phonic_test_marks`
--

CREATE TABLE `student_phonic_test_marks` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `student_assignment_id` bigint(20) NOT NULL,
  `group_id` bigint(10) NOT NULL,
  `max_marks` int(11) DEFAULT '0',
  `sec_marks` int(11) DEFAULT '0',
  `marks_string` varchar(50) DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_test_id2` (`student_assignment_id`),
  KEY `fk_phonic_group_id1` (`group_id`),
  CONSTRAINT `fk_phonic_group_id` FOREIGN KEY (`group_id`) REFERENCES `phonic_groups` (`group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_phonic_student_assignment_id` FOREIGN KEY (`student_assignment_id`) REFERENCES `student_assignment_status` (`student_assignment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `sub_interest`
--

CREATE TABLE `sub_interest` (
  `sub_interest_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sub_interest` varchar(45) NOT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `interest_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sub_interest_id`),
  KEY `fk_interest_id_idx` (`interest_id`),
  CONSTRAINT `fk_interest_id` FOREIGN KEY (`interest_id`) REFERENCES `interest` (`interest_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `sub_interest`
--

INSERT INTO `sub_interest` (`sub_interest_id`, `sub_interest`, `create_date`, `change_date`, `interest_id`) VALUES (101,'psychology','2010-01-06','2010-01-05 23:26:20',1),(102,'sociology','2010-01-06','2010-01-05 23:26:20',1),(103,'child psychology','2010-01-06','2010-01-05 23:26:20',1),(104,'criminal justice','2010-01-06','2010-01-05 23:26:20',1),(105,'management','2010-01-06','2010-01-05 23:26:20',1),(106,'laws','2010-01-06','2010-01-05 23:26:20',1),(107,'current affairs','2010-01-06','2010-01-05 23:26:20',1),(108,'child developement','2010-01-06','2010-01-05 23:26:20',1),(109,'art','2010-01-06','2010-01-05 23:26:20',1),(110,'literature','2010-01-06','2010-01-05 23:26:22',1),(111,'information system','2010-01-06','2010-01-05 23:28:56',1),(112,'quantum mechanics','2010-01-06','2010-01-05 23:28:56',1),(113,'leadership','2010-01-06','2010-01-05 23:28:56',5),(114,'theater','2010-01-06','2010-01-05 23:28:56',5),(115,'current events','2010-01-06','2010-01-05 23:28:56',5),(116,'fashion','2010-01-06','2010-01-05 23:28:56',5),(117,'collecting','2010-01-06','2010-01-05 23:28:56',5),(118,'travel','2010-01-06','2010-01-05 23:28:56',5),(119,'music','2010-01-06','2010-01-05 23:28:56',4),(120,'extend education','2010-01-06','2010-01-05 23:28:58',4),(121,'computer science','2010-01-06','2010-01-05 23:33:06',4),(122,'web design ','2010-01-06','2010-01-05 23:33:06',4),(123,'pta','2010-01-06','2012-03-15 03:28:46',4),(124,'scouting','2010-01-06','2012-03-15 03:29:31',4),(125,'mentoring','2010-01-06','2012-03-15 03:29:52',4),(126,'sports','2010-01-06','2012-03-15 03:30:07',4),(127,'arts','2010-01-06','2012-03-15 03:30:22',4),(128,'teachersaide','2010-01-06','2012-03-15 03:38:18',4),(129,'clubs','2010-01-06','2012-03-15 03:31:43',4),(130,'band','2010-01-06','2012-03-15 03:31:52',4),(131,'office staff','2010-01-06','2012-03-15 03:32:19',4),(132,'youngrepublican','2010-01-06','2012-03-15 04:20:46',3),(133,'youngdemocrat','2010-01-06','2012-03-15 04:21:05',3),(134,'key','2010-01-06','2012-03-15 03:58:38',3),(135,'environmental','2010-01-06','2012-03-15 03:58:48',3),(136,'studentgovt','2010-01-06','2012-03-15 04:23:33',3),(137,'debate','2010-01-06','2012-03-15 03:59:13',3),(138,'academic','2010-01-06','2012-03-15 03:59:22',3),(139,'chess','2010-01-06','2012-03-15 03:59:32',3),(140,'surf','2010-01-06','2012-03-15 04:10:53',3),(141,'skate','2010-01-06','2012-03-15 04:18:43',3),(143,'thesbian','2010-01-06','2012-03-15 04:19:08',3),(144,'nhonorsociety','2010-01-06','2012-03-15 04:20:35',3),(145,'yearbook','2010-01-06','2012-03-15 04:21:41',3),(146,'russian','2010-01-06','2012-03-15 04:22:09',3),(147,'betta','2010-01-06','2012-03-15 04:22:28',3),(148,'basketball','2010-01-06','2012-03-15 03:43:03',2),(149,'boxing','2010-01-06','2012-03-15 03:43:17',8),(150,'baseball','2010-01-06','2012-03-15 03:43:27',2),(151,'cycling','2010-01-06','2012-03-15 03:44:05',2),(152,'football','2010-01-06','2012-03-15 03:45:06',2),(153,'fishing ','2010-01-06','2010-01-05 23:51:09',8),(154,'hunting ','2010-01-06','2010-01-05 23:51:09',8),(155,'hiking ','2010-01-06','2010-01-05 23:51:09',8),(156,'softball','2010-01-06','2012-03-15 03:45:25',2),(157,'skating ','2010-01-06','2010-01-05 23:51:09',8),(158,'wrestling','2010-01-06','2012-03-15 03:45:35',2),(159,'weight lifting','2010-01-06','2012-03-15 03:45:51',2),(160,'racketball','2010-01-06','2012-03-15 03:50:48',2),(161,'martial art','2010-01-06','2012-03-15 03:50:25',2),(162,'swimming','2010-01-06','2012-03-15 03:48:27',2),(163,'cross country','2010-01-06','2012-03-15 03:50:08',2),(164,'soccer','2010-01-06','2012-03-15 03:51:14',2),(165,'track field','2010-01-06','2012-03-15 03:51:22',2),(166,'tennis','2010-01-06','2012-03-15 03:51:34',2),(167,'hockey','2010-01-06','2012-03-15 03:51:43',2),(168,'lacrose','2010-01-06','2012-03-15 03:52:00',2),(169,'crew','2010-01-06','2012-03-15 03:52:11',2),(170,'golf','2010-01-06','2012-03-15 03:52:20',2),(171,'field hockey','2010-01-06','2012-03-15 03:52:37',2),(172,'skiing','2010-01-06','2012-03-15 03:52:45',2),(174,'english ','2010-01-06','2010-01-05 23:51:09',7),(175,'history ','2010-01-06','2010-01-05 23:51:09',7),(176,'pe ','2010-01-06','2010-01-05 23:51:09',7),(177,'computers','2010-01-06','2012-03-15 03:35:21',5),(178,'chemistry','2010-01-06','2012-03-15 04:23:07',3),(179,'biology ','2010-01-06','2010-01-05 23:51:09',4),(180,'physics ','2010-01-06','2010-01-05 23:51:09',4),(181,'ushistory ','2010-01-06','2010-01-05 23:51:09',7),(182,'government ','2010-01-06','2010-01-05 23:51:09',7),(183,'reading','2010-01-06','2012-03-15 03:34:37',5),(184,'spanish','2010-01-06','2012-03-15 03:34:55',3),(185,'german','2010-01-06','2012-03-15 04:22:55',3),(188,'economics ','2010-01-06','2010-01-05 23:51:09',7),(190,'shop','2010-01-06','2010-01-05 23:55:31',7),(191,'cooking','2010-01-06','2012-03-15 03:36:05',5),(192,'politics ','2010-01-06','2010-01-05 23:55:52',7),(193,'speech ','2010-01-06','2010-01-05 23:51:10',7),(194,'coin collecting ','2010-01-06','2010-01-05 23:51:10',7),(195,'bird watching ','2010-01-06','2010-01-05 23:51:10',7),(196,'stamp collecting ','2010-01-06','2010-01-05 23:51:10',7),(197,'religion ','2010-01-06','2010-01-05 23:51:10',7),(199,'home economics ','2010-01-06','2010-01-05 23:51:10',7),(200,'calculus ','2010-01-06','2010-01-05 23:51:10',7),(201,'algebra ii ','2010-01-06','2010-01-05 23:51:10',7);


--
-- Table structure for table `sub_questions`
--

CREATE TABLE `sub_questions` (
  `sub_question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sub_question` varchar(200) NOT NULL,
  `no_of_options` int(11) NOT NULL,
  `assignment_type_id` bigint(20) NOT NULL,
  `lesson_id` bigint(20) NOT NULL,
  `used_for` varchar(45) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  PRIMARY KEY (`sub_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `teacher_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_id` bigint(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`teacher_id`),
  UNIQUE KEY `reg_id_UNIQUE` (`reg_id`),
  KEY `fk_reg_id_idx` (`reg_id`),
  CONSTRAINT `fk_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `teacher_performances`
--

CREATE TABLE `teacher_performances` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `performance` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `performance_UNIQUE` (`performance`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `teacher_performances`
--

INSERT INTO `teacher_performances` (`id`, `performance`) VALUES (5,'Assign and Manage Homework '),(4,'Assign Grades '),(2,'Assign Units, Lessons, Assessments '),(3,'Attendance '),(6,'Attendance Reports completed '),(9,'Keeps Parents and Students Informed '),(1,'Plan and Create Curriculum'),(7,'Test Preparation and Grades ');


--
-- Table structure for table `teacher_reports`
--

CREATE TABLE `teacher_reports` (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_teacher_report_id` bigint(20) NOT NULL,
  `performance_id` bigint(20) NOT NULL,
  `choosen_option` varchar(20) NOT NULL,
  `comments` varchar(200) NOT NULL,
  PRIMARY KEY (`report_id`),
  KEY `fk_admin_teacher_report_id_idx` (`admin_teacher_report_id`),
  KEY `fk_performance_id_idx` (`performance_id`),
  CONSTRAINT `fk_admin_teacher_report_id` FOREIGN KEY (`admin_teacher_report_id`) REFERENCES `admin_teacher_reports` (`admin_teacher_report_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_performance_id` FOREIGN KEY (`performance_id`) REFERENCES `teacher_performances` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `teacher_subjects`
--

CREATE TABLE `teacher_subjects` (
  `teacher_subject_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) NOT NULL DEFAULT '0',
  `grade_id` bigint(20) NOT NULL DEFAULT '0',
  `class_id` bigint(20) NOT NULL DEFAULT '0',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `grade_level_id` bigint(20) NOT NULL,
  `no_sections_per_day` int(11) NOT NULL,
  `no_sections_per_week` int(11) NOT NULL,
  `other_subjects` varchar(25) DEFAULT NULL,
  `desktop_status` varchar(20) DEFAULT NULL,
  `requested_class_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`teacher_subject_id`),
  UNIQUE KEY `UK_teacher_subjects_1` (`teacher_id`,`grade_id`,`class_id`) USING BTREE,
  KEY `FK_teacher_subjects_2` (`grade_id`),
  KEY `FK_teacher_subjects_3` (`class_id`),
  KEY `fk_teacSub_grade_level_id` (`grade_level_id`),
  CONSTRAINT `fk_subj_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacSub_class_id` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `fk_teacSub_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`grade_id`),
  CONSTRAINT `fk_teacSub_grade_level_id` FOREIGN KEY (`grade_level_id`) REFERENCES `grade_level` (`grade_level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `unit`
--

CREATE TABLE `unit` (
  `unit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_class_id` bigint(20) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `unit_no` bigint(20) DEFAULT NULL,
  `unit_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`unit_id`),
  UNIQUE KEY `grade_class_id` (`grade_class_id`,`unit_name`),
  KEY `fk_grade_class_id1_idx` (`grade_class_id`),
  KEY `fk_created_by_reg_id_idx` (`created_by`),
  CONSTRAINT `fk_grade_class_id1` FOREIGN KEY (`grade_class_id`) REFERENCES `grade_classes` (`grade_class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_created_by_reg_id` FOREIGN KEY (`created_by`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_typeid` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_type` varchar(45) NOT NULL DEFAULT '',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_typeid`),
  UNIQUE KEY `UK_user_1` (`user_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_typeid`, `user_type`, `create_date`, `change_date`) VALUES (1,'appmanager','2009-05-28','2009-05-28 05:32:33'),(2,'admin','2009-05-28','2009-05-28 05:32:33'),(3,'teacher','2009-05-28','2009-05-28 05:32:47'),(4,'parent','2009-05-28','2009-05-28 05:32:58'),(5,'student above 13','2009-05-28','2009-06-11 00:00:23'),(6,'student below 13','2009-06-11','2009-06-11 00:01:31');

--
-- Table structure for table `user_interests`
--

CREATE TABLE `user_interests` (
  `user_interests_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reg_id` bigint(20) NOT NULL DEFAULT '0',
  `interest_id` bigint(20) DEFAULT '0',
  `sub_interest_id` bigint(20) DEFAULT '0',
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `other_user_interest` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_interests_id`),
  UNIQUE KEY `UK_user_interests_1` (`reg_id`,`sub_interest_id`) USING BTREE,
  KEY `FK_user_interests_2` (`sub_interest_id`),
  KEY `fk_user_interest_id` (`interest_id`),
  CONSTRAINT `fk_user_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `user_registration` (`reg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_interest_id` FOREIGN KEY (`interest_id`) REFERENCES `interest` (`interest_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_subinterest_id` FOREIGN KEY (`sub_interest_id`) REFERENCES `sub_interest` (`sub_interest_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `user_registration`
--

CREATE TABLE `user_registration` (
  `reg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(5) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `user_typeid` bigint(20) NOT NULL DEFAULT '0',
  `password` varchar(45) DEFAULT NULL,
  `qualification` varchar(200) DEFAULT NULL,
  `phonenumber` varchar(45) DEFAULT NULL,
  `email_id` varchar(45) NOT NULL,
  `parent_reg_id` bigint(20) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `school_id` bigint(20) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `experience` varchar(250) DEFAULT NULL,
  `subjects` varchar(250) DEFAULT NULL,
  `interestareas` varchar(250) DEFAULT NULL,
  `projects` varchar(250) DEFAULT NULL,
  `education` varchar(250) DEFAULT NULL,
  `contactinfo` varchar(250) DEFAULT NULL,
  `district_id` bigint(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`reg_id`),
  UNIQUE KEY `UK_registration_1` (`reg_id`),
  UNIQUE KEY `unique_email_id` (`email_id`, `school_id`)),
  KEY `FK_registration_2` (`user_typeid`) USING BTREE,
  KEY `FK_registration_1` (`reg_id`) USING BTREE,
  KEY `fk_address_idx` (`address_id`),
  KEY `school_id` (`school_id`),
  KEY `fk_userregis_dist_id` (`district_id`),
  CONSTRAINT `fk_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userregis_dist_id` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`),
  CONSTRAINT `fk_usertype` FOREIGN KEY (`user_typeid`) REFERENCES `user` (`user_typeid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_registration_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`),
  CONSTRAINT `user_registration_ibfk_2` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;



--
-- Dumping data for table `user_registration`
--

INSERT INTO `user_registration` (`reg_id`, `title`, `first_name`, `last_name`, `user_typeid`, `password`, `qualification`, `phonenumber`, `email_id`, `parent_reg_id`, 
`address_id`, `school_id`, `create_date`, `change_date`, `experience`, `subjects`, `interestareas`, `projects`, `education`, `contactinfo`, `district_id`, `status`) VALUES 
(7,'Mr','Ian ','Rescigno',1,'f6fdffe48c908deb0f4c3bd36c032e72',NULL,'1234567890','ian@gmail.com',NULL,NULL,NULL,'2015-07-24','2015-07-24 11:20:24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active');


-- Learning Indicator tables

CREATE TABLE `legend` (
  `legend_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `legend_name` varchar(40) NOT NULL,
  `legend_value` bigint(20) NOT NULL,
  PRIMARY KEY (`legend_id`),
  UNIQUE KEY `unique_legend_name` (`legend_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table `legend`
INSERT INTO `legend` (`legend_id`, `legend_name`, `legend_value`) VALUES
(1, 'Not Proficient', 1),
(2, 'Approaching Proficient', 2),
(3, 'Proficient', 3),
(4, 'Outstanding', 4);

CREATE TABLE `legend_criteria` (
  `legend_criteria_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `legend_criteria_name` varchar(20) NOT NULL,
  PRIMARY KEY (`legend_criteria_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table `legend_criteria`

INSERT INTO `legend_criteria` (`legend_criteria_id`, `legend_criteria_name`) VALUES
(1, 'Communication'),
(2, 'Collaboration'),
(3, 'Critical Thinking'),
(4, 'Creativity'),
(5, 'Caring'),
(6, 'Literacies');

CREATE TABLE `legend_sub_criteria` (
  `legend_sub_criteria_id` bigint(20) NOT NULL,
  `legend_criteria_id` bigint(20) NOT NULL,
  `legend_sub_criteria_name` varchar(100) NOT NULL,
  PRIMARY KEY (`legend_sub_criteria_id`),
  UNIQUE KEY `legend_sub_criteria_name_UNIQUE` (`legend_sub_criteria_name`,`legend_criteria_id`),
  KEY `fk_rptcategory_id_idx` (`legend_criteria_id`),
  CONSTRAINT `fk_legend_criteria` FOREIGN KEY (`legend_criteria_id`) REFERENCES `legend_criteria` (`legend_criteria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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

CREATE TABLE `learning_indicator` (
  `learning_indicator_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cs_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `report_date` date NOT NULL,
  `create_date` date NOT NULL,
  `status` varchar(10) NULL,
  PRIMARY KEY (`learning_indicator_id`),
  KEY `fk_rpt_cs_id_idx` (`cs_id`),
  KEY `fk_rpt_student_id_idx` (`student_id`),
  CONSTRAINT `fk_l_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_l_cs_id` FOREIGN KEY (`cs_id`) REFERENCES `class_status` (`cs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `learning_indicator_values` (
  `learning_values_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `learning_indicator_id` bigint(20) NOT NULL,
  `sub_criteria_id` bigint(20) NOT NULL,
  `legend_id` bigint(20) NULL,
  `notes` varchar(1000) NULL,
  `teacher_score` bigint(20) NULL,
  `teacher_comment` varchar(500) NULL,  
  PRIMARY KEY (`learning_values_id`),
  KEY `fk_rpt_legend_id_idx` (`legend_id`),
  KEY `fk_rpt_sub_criteria_id_idx` (`sub_criteria_id`),
  KEY `fk_li_learning_indicator_id_idx` (`learning_indicator_id`),
  KEY `fk_teacher_score_idx` (`teacher_score` ASC),
  CONSTRAINT `fk_learning_indicator_id` FOREIGN KEY (`learning_indicator_id`) REFERENCES `learning_indicator` (`learning_indicator_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_legend_id` FOREIGN KEY (`legend_id`) REFERENCES `legend` (`legend_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_score` FOREIGN KEY (`teacher_score`) REFERENCES `legend` (`legend_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sub_criteria_id` FOREIGN KEY (`sub_criteria_id`) REFERENCES `legend_sub_criteria` (`legend_sub_criteria_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=latin1;

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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;