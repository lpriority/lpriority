package com.lp.utils;

public interface WebKeys {

	String LP_STATUS_NEW = "new";
	String LP_STATUS_ACTIVE = "active";
	String LP_USER_TYPE_TEACHER = "teacher";
	String LP_USER_TYPE_STUDENT_ABOVE_13 = "student above 13";
	String LP_USER_TYPE_STUDENT_BELOW_13 = "student below 13";
	String LP_SUBJECT_ALREADY_PLANED = "This Subject is already planned, If you want to add more units press ok and Enter no of lessons";
	String LP_CREATE_UNITS_SUCCESS = "Units created successfully";
	String LP_CREATE_UNITS_FAILED = "Units creation failed";
	String LP_CREATE_UNITS_ERROR = "System error occured..while creating units";
	String LP_CREATE_STEM_UNITS_ERROR = "System error occured..while creating stem units";
	String LP_LOAD_UNITS_ERROR = "System error occured..while loading units";
	String LP_CREATE_LESSONS_SUCCESS = "Lessons created successfully";
	String LP_CREATE_LESSONS_FAILED = "Lessons creation failed";
	String LP_CREATE_LESSONS_ERROR = "System error occured..while creating Lessons";
	String LP_CURRICULUM_PLANNER_ERROR = "System error occured..while Loding curriculem planner";
	String LP_USED_FOR_ASSESSMENT = "assessments";
	String LP_USED_FOR_RTI = "rti";
	String LP_USED_FOR_HOMEWORKS = "homeworks";
	String LP_SYSTEM_ERROR = "System error occured..";
	String LP_CREATE_ASSESSMENTS_SUCCESS = "Assessments created successfully";
	String LP_CREATE_ASSESSMENTS_FAILED = "Assessments creation failed";
	String LP_ASSIGN_LESSONS_SUCCESS = "Lessons and activities assigned successfully";
	String LP_ASSIGN_LESSONS_FAILED = "Assign Lessons failed";
	String LP_ASSIGN_LESSONS_ERROR = "System error occured..while assigning lessons";
	String LP_ASSIGN_ASSESSMENT_ERROR = "Assignment Not Assigned Successfully";
	String LP_ASSIGN_ASSESSMENT_SUCESS = "Assignment Assigned Successfully";
	String LP_TOMCAT_SERVER_PATH = "catalina.base";
	String LP_USER_REGISTRATION = "userReg";
	String LP_TOMCAT_WEBAPPS_PATH = "webapps";
	String LP_AUDIO_FILE_DIR = "Directions";
	String AUDIO_FILE_NAME = "audio_record.wav";
	String AUDIO = "audio";
	String TEMP = "temp";
	String ADDITIONAL_MEDIA = "Additional_Media";
	String JAC_TEMPLATE = "JAC_Template";
	String BENCH_MARK_TESTS = "Benchmark_Tests";
	String PERFORMANCE_TESTS = "Performance_Tests";
	String ASSIGNMENTS = "assignments";
	String LP_CREATE_PERFORMANCE_GROUP_SUCCESS = "Performance Group created successfully";
	String LP_CREATE_PERFORMANCE_GROUP_FAILED = "Performance Group creation failed";
	String LP_CREATE_PERFORMANCE_GROUP_ERROR = "System error occured..while creating Performance Group";
	String LP_ADD_STUDENT_SUCCESS = "Added/Removed student Successfully";
	String LP_ADD_STUDENT_FAILED = "Add/Remove student failed!!!!!";
	String LP_ADD_STUDENT_ERROR = "System error occured..while adding students";
	String LP_REMOVE_STUDENT_SUCCESS = "Remove student Successfully";
	String LP_REMOVE_STUDENT_FAILED = "Remove student failed!!!!!";
	String LP_REMOVE_STUDENT_ERROR = "System error occured..while removing students";

	// file transfer
	String LP_USERS_FILES = "LPUsersFIles";
	String PUBLIC_FILES = "public";
	String PRIVATE_FILES = "private";
	String STUDENT_PRIVATE_FILES = "studentPrivate";
	String PUBLIC_FILES_PAGE_TITLE = "Class Files";
	String PRIVATE_FILES_PAGE_TITLE = "Private Files";
	String UPLOAD_PRIVATE_FILES_PAGE_TITLE = "Upload Private Files";
	String TRANSFER_FILES_PAGE_TITLE = "Transfer Files";
	String ADMIN_FILES_PAGE_TITLE = "Admin Files";
	String TEACHER_FILES = "teacher";
	String TRANSFER_FILES = "transfer";
	String STUDENT_FILES_PAGE_TITLE = "Student Files";
	String UPLOAD_LESSONS_SUCCESS = "Successfully Uploaded Lessons";
	String LP_UPLOAD_SUCCESS = "success";
	String LP_UPLOAD_LESSONS = "yes";
	String TRANSFER_FILES_FOLDER = "Student Transfer Files";
	String USER_TYPE_ADMIN = "Admin";
	String USER_TYPE_TEACHER = "Teacher";
	String USER_TYPE_PARENT = "Parent";
	String USER_TYPE_STUDENT_BELOW = "Student below 13";
	String USER_TYPE_STUDENT_ABOVE = "Student above 13";

	// view class web keys
	String ROSTER = "Roster";
	String ATTENDANCE = "Attendance";
	String TAKE_ATTENDANCE = "takeAttendance";
	String UPDATE_ATTENDANCE = "updateAttendance";
	String REGISTRATION = "Registration";
	String TEACHER_OBJECT = "teacherObj";
	String ACCEPTED = "accepted";
	String DECLINED = "declined";
	String WAITING = "waiting";
	String SUCCESS = "success";
	String FAILED = "failed";
	String ALREADY_ASSIGNED ="already assigned";
	String DB_DATE_FORMATE = "yyyy-MM-dd";
	String DATE_FORMATE = "MM/dd/yyyy";
	String TIME_FORMATE = "HH:mm";
	String COMPLETE_TIME_FORMATE = "HH:mm:ss";
	String SYSTEM_TIME_FORMATE = "HH:mm:ss";

	// Teacher Scheduler
	String AVAILABLE = "available";
	String CANCELLED = "cancelled";
	String ALIVE = "alive";
	String EXPIRED = "expired";

	// Student Register for class
	String STUDENT_OBJECT = "studentObj";

	// Early Literacy Letter
	String EARLY_LITERACY_LETTER = "Early Literacy Letter";
	String CREATE_LETTER = "CreateLetter";
	String ASSGIN_LETTER_SET = "assignLetterSet";
	String LETTER = "Letter";
	String WAV_FORMAT = ".wav";
	String JPG_FORMAT = ".jpg";
	String EARLY_LITERACY_TEST = "Early_Literacy_Test";
	String GRADE_LETTER_SET = "gradeLetterSet";

	// Early Literacy Words
	String EARLY_LITERACY_WORD = "Early Literacy Word";
	String CREATE_WORD = "CreateWord";
	String ASSGIN_WORD_SET = "assignWordSet";
	String WORD = "Word";
	String ACTIVE = "active";
	String RTI = "rti";
	String GRADE_WORD_SET = "gradeWordSet";
	String TEACHER_CREATED = "Teacher";
	String SYSTEM_GENERATED = "CreateLetter";

	// Math Assessment
	String MATH_ASSESMENT = "mathAssessment";
	String LP_MATH_ASSESMENT = "Math Assessment";
	String CREATE_MATH_ASSESMENT = "createMathAssessment";
	String ASSIGN_MATH_ASSESMENT = "assignMathAssessment";
	String GRADE_MATH_ASSESMENT = "gradeMathAssessment";
	String ANALYSIS_MATH_ASSESMENT = "analysisMathAssessment";
	String ASSIGN_GEAR_GAME = "assignGearGame";
	String GEAR_GAME = "Gear Game";
	String LOCAL_HOST = "localhost";
		
	// TAB WEB KEYS
	String LP_TAB_CURRICULUM = "curriculum";
	String LP_TAB_CREATE = "create";
	String LP_TAB_EDIT = "edit";
	String LP_TAB_REMOVE = "remove";
	String LP_TAB_CREATE_HOMEWORK = "createHome";
	String LP_TAB_EDIT_HOMEWORK = "editHome";
	String LP_TAB_UPLOAD_FILE = "uploadFile";
	String LP_TAB_ASSIGN_LESSONS = "assignLessons";
	String LP_TAB_ASSIGN_ASSESSMENTS = "assignAssessments";
	String LP_TAB_VIEW_CURRICULUM = "viewCurriculum";
	String LP_TAB_SHOW_ASSIGNED = "showAssigned";
	String LP_TAB_SHOW_ASSIGNED_RTI = "showAssignedRTI";
	String LP_TAB_GRADE_ASSESSMENTS = "gradeAssessments";
	String LP_TAB_GRADE_GROUP = "gradeGroup";
	String LP_TAB_GROUP_PERFORMANCE = "groupPerformance";
	String LP_SUB_TAB_CREATE_UNITS = "createUnits";
	String LP_SUB_TAB_CREATE_ACTIVITY = "createActivity";
	String LP_SUB_TAB_CREATE_ASSESSMENTS = "createAssessments";
	String LP_SUB_TAB_ADD_LESSONS_TO_UNIT = "addLessonsToUnit";
	String LP_SUB_TAB_CREATE_PERFORMANCE_GROUP = "createPerformanceGroup";
	String LP_SUB_TAB_GRADE_PERFORMANCE_GROUP = "gradePerformanceGroup";
	String LP_SUB_TAB_SPLITTING_STUDENTS = "splittingStudents";
	String LP_SUB_TAB_ASSIGNING_PERFORMANCE_GROUP = "assigningPerformanceGroup";

	String LP_TAB_HOMEWORK_REPORTS = "homeReports";
	String LP_TAB_GRADE_HOMEWORK = "gradeHome";
	String LP_TAB_HOMEWORK_MANAGER = "homeManager";
	String LP_TAB_CURRENT_HOMEWORK = "currentHome";
	String LP_TAB_ASSIGN_HOMEWORK = "assignHome";
	String LP_TAB_PREPARE_RTI = "prepareRti";
	String LP_TAB_ASSIGN_RTI = "assignRti";
	String LP_TAB_ASSIGN_RTF = "assignRtf";
	String LP_TAB_RTI_RESULTS = "rtiResults";
	String LP_TAB_GRADE_RTI = "gradeRti";
	String LP_TAB_PROGRESS_MONITORING = "progressMonitoring";
	String LP_TAB_BENCHMARK_RESULTS = "FluencyReading Results";
	String LP_TAB_EARLY_IDENTIFICATION = "earlyIdentification";
	String LP_TAB_EARLY_SIGHT = "earlySight";
	String LP_TAB_PHONIC_SKILL_TEST = "phonicSkillTest";
	String LP_TAB_ASSIGN_PHONIC = "assignPhonic";
	String LP_TAB_GRADE_PHONIC = "gradePhonic";
	String LP_TAB_PHONIC_REPORTS = "phonicReports";
	String LP_TAB_PHONIC_SINGLE_REPORTS = "phonicSingleReports";
	String LP_TAB_PHONIC_MULTIPLE_REPORTS = "phonicMultipleReports";
	String LP_TAB_ERROR_WORD_ITEM = "errorWordItem";
	String LP_TAB_TASKFORCE_RESULTS = "taskForceResults";

	String LP_TAB_VIEW_ASSESSMENTS = "view assessment tests";
	String LP_TAB_VIEW_RTI = "view rti tests";
	String LP_TAB_VIEW_CURRENT_HOMEWORK = "current homeworks";
	String LP_TAB_VIEW_DUE_HOMEWORK = "due homeworks";
	String LP_TAB_VIEW_GROUP_PERFORMANCE = "viewGroupPerformance";

	String TEST_STATUS_PENDING = "pending";
	String TEST_STATUS_SAVED = "saved";
	String TEST_STATUS_SUBMITTED = "submitted";
	String GRADED_STATUS_NOTGRADED = "not graded";
	String GRADED_STATUS_GRADED = "graded";
	String GRADED_STATUS_LIVE_GRADED = "live graded";
	String ASSIGN_STATUS_ACTIVE = "active";
	String ASSIGN_STATUS_INACTIVE = "inactive";
	String TEST_SAVE = "save";
	String TEST_SUBMIT = "submit";

	String TEST_SUBMITTED_SUCCESS = "The test submitted successfully.";
	String TEST_SUBMITTED_FAILED = "The test not submitted !!";
	String TEST_SUBMITTED_FAILURE = "The test is not saved/submitted successfully";
	String TEST_SUBMITTED_ERROR = "System error occured..while saving/submitting the test";

	String TEST_SAVED_SUCCESS = "The test saved successfully.";
	String TEST_SAVED_FAILURE = "The test is not saved successfully";
	String TEST_SAVED_ERROR = "System error occured..while saving the test";

	String ACCURACY_FILE_NAME = "accuracy_audio.wav";
	String FLUENCY_FILE_NAME = "fluency_audio.wav";
	String RETELL_FILE_NAME = "retell_audio.wav";
	String STUDENT_BENCH_MARK_TESTS = "Student_Benchmark_Tests";

	String JAC_SUBMITTED_SUCCESS = "The JacTemplate test submitted successfully.";
	String JAC_SUBMITTED_FAILURE = "The JacTemplate is not submitted successfully";

	String DEFAULT_VERIFICATION_CODE = "adminadmin";
	String DEFAULT_SECURITY_ANSWER = "pizza";
	String SECURITY_STATUS_INACTIVE = "in active";

	String REGISTER_FOR_CLASS_STATUS = "new";
	String LP_USER_TYPE_PARENT = "parent";

	String LP_WRITING = "writing";
	String LP_CALCULATIONS = "calculations";
	String LP_DIMENTION_ONE = "dimension1";
	String LP_DIMENTION_TWO = "dimension2";
	String LP_DIMENTION_THREE = "dimension3";

	String JAC_GRADED_SUCCESS = "The JacTemplate test graded successfully.";
	String JAC_GRADED_FAILURE = "The JacTemplate is not graded successfully";

	String TEST_GRADED_SUCCESS = "The Test graded successfully.";
	String TEST_GRADED_FAILURE = "The Test is not graded successfully";

	String TEST_BENCUTOFF_SUCCESS = "The Benchmark Cut Off Marks Created/Updated successfully.";
	String TEST_BENCUTOFF_FAILURE = "The Benchmark Cut Off Marks Not Created/Updated successfully";

	String LP_YES = "yes";
	String LP_NO = "no";
	String LP_UNLOCKED = "unlocked";
	String LP_LOCKED = "locked";

	String LP_APPROVED = "approved";
	String LP_REJECTED = "returned";
	String LP_WAITING = "waiting";
	// RTI Test Results
	String LP_TAB_RTI_TEST_RESULTS = "rti test results";

	String LP_CLASS_HOMEROME = "Home Room";
	String CLASS_DESC = "class description";
	String CLASS_EDITING_STATUS = "assigned";

	String TIME_MERIDIAN_AM = "AM";
	String TIME_MERIDIAN_PM = "PM";

	String BENCHMARK_FLUENCY_PASSAGE = "fluency";
	String BENCHMARK_RETELL_PASSAGE = "retell";

	String LP_TAB = "tab";

	String LP_TAB_VIEW_ASSESSMENT_COMPLETED = "viewAssessmentCompleted";

	String LP_TAB_GRADES = "grades";
	String LP_TAB_GRADEBOOK_ATTENDANCE = "attendance";
	String LP_TAB_REPORTS = "reports";
	String LP_TAB_PREVIOUS_REPORTS = "previousReports";
	String LP_TAB_ITEM_ANALYSIS_REPORTS = "itemAnalysisReport";
	String LP_TAB_COMPOSITE = "composite";
	String LP_TAB_IOLREPORTCARD = "iolReportCard";
	String LP_TAB_SHOWREPORTS = "showReports";
	String LP_TAB_UPLOAD_IOLRUBRIC = "uploadIOLRubrics";
	String LP_TAB_EDIT_IOLRUBRIC = "editIOLRubrics";
	String LP_TAB_ASSIGN_IOLRUBRIC = "assignIOLRubrics";

	// my profile
	String PERSONAL_INFO = "Personal Info";
	String CHANGE_USER_NAME = "Change User Name";
	String ADDRESSES_CONCAT_TOKEN = "##@##";
	String CHANGE_PASSWORD = "Change Password";
	String SCHOOL_INFORMATION =  "School Information";
	String HOME_PAGE =  "Home Page";
	String PERSONAL_INTEREST =  "Personal Interest";
	String UPDATED_SUCCESSFULLY = "Updated Successfully";
	String FAILED_TO_UPDATE = "Failed to Update";
	String ADDED_SUCCESSFULLY = "Added Successfully";
	String FAILED_TO_ADD = "Failed to Add";
	String FAILED_TO_DELETE = "Failed to Delete";
	String EDUCATIONAL_INFO = "Educational Info";
	
	String GRADEBOOK = "gradebook";

	String ASSIGNMENT_TYPE_MULTIPLE_CHOICES = "Multiple Choices";
	String ASSIGNMENT_TYPE_ESSAYS = "Essay";
	String ASSIGNMENT_TYPE_SHORT_ANSWERS = "Short Que";
	String ASSIGNMENT_TYPE_EARLY_LITERACY_WORD= "Early Literacy Word";
	String ASSIGNMENT_TYPE_EARLY_LITERACY_LETTER = "Early Literacy Letter";
	String ASSIGNMENT_TYPE_SENTENCE_STRUCTURE = "Sentence Structure";

	String OPTION_A = "option1";
	String OPTION_B = "option2";
	String OPTION_C = "option3";
	String OPTION_D = "option4";
	String OPTION_E = "option5";
	
	String LP_TAB_PROGRESS_REPORTS = "progressReports";
	String LP_TAB_SHOW_REPORTS = "showReports";
	
	String ALL_ASSIGNMENT_TYPE = "all";
	String CLASSWORK_ASSIGNMENT_TYPE = "classworks";
	
	String ASSIGNMENT_TYPE_JAC_TEMPLATE = "Spelling";
	
	String LP_ERROR_MESSAGE_USER_CREATED_SUCCESS = "user added successfully";
	String LP_ERROR_MESSAGE_USER_ALREADY_EXISTS = "user exists";
	
	String COMPOSITE_CHART_CREATED_SUCCESS = "Composite chart values updated successfully";
	String LP_STATUS_INACTIVE = "inactive";
	
	//Assigned Phonic Skills Test
	
    String ASSIGNED_SUCCESSFULLY = "Assigned Successfully !!";
    String FAILED_TO_ASSIGNED = "Failed to Assign !!";
    String TEST_ALREDAY_ASSGINED = "Test already Assigned !!";
    String PHONIC_SKILL_TEST = "Phonic Skill Test";
    String PHONIC_TEST = "Phonic_Test";
    String READING_REGISTER = "Reading_Register";
    String ASSIGN_PHONIC_SKILL_TEST = "Assign Phonic Skill Test";
    String TEST_COMPLETED_SUCCESSFULLY = "Test Completed Successfully !!";
    String ERROR_OCCURED = "Error Occured !!";
    String GRADED_SUCCESSFULLY = "Graded Successfully !!";
    String GRADING_FAILED = "Grading failed !!";
    String GRADE_PHONIC_SKILL_TEST = "Grade Phonic Skill Test";
    
    String LP_USER_TYPE_ADMIN = "admin";
	String LP_USER_TYPE_APP_MANAGER = "appmanager";
	String ATTENDANCE_STATUS_PRESENT = "Present";
	String ATTENDANCE_STATUS_ABSENT = "Absent";
	String ATTENDANCE_STATUS_EXCUSED_ABSENT = "ExcusedAbsent";
	String ATTENDANCE_STATUS_TARDY = "Tardy";
	String ATTENDANCE_STATUS_EXCUSED_TARDY = "ExcusedTardy";
	
	String LP_SUB_TAB_EDIT_UNITS = "editUnits";
	String LP_SUB_TAB_EDIT_LESSONS = "editLessons";
	String LP_SUB_TAB_EDIT_ACTIVITY = "editActivity";
	String LP_SUB_TAB_EDIT_ASSESSMENTS = "editAssessments";
	String LP_NO_UNITS = "Units not yet created for this class";
	String LP_EDIT_UNIT_SUCCESS = "Unit updated successfully";
	String LP_EDIT_UNIT_FAILED = "Unit update failed";
	String LP_EDIT_UNIT_ERROR = "System error occured..while Updating units";
	String LP_UNIT_ALREADY_EXISTS = "Unit already existed with same name";
	String LP_NO_LESSONS = "Lessons not yet created for this Unit";
	String LP_EDIT_LESSONS_SUCCESS = "Lesson updated successfully";
	String LP_LESSON_ALREADY_EXISTS = "Lesson already existed with same name";
	String LP_EDIT_LESSONS_FAILED = "Lessons update failed";
	String LP_EDIT_LESSONS_ERROR = "System error occured..while Updating Lessons";
	String LP_REMOVED_UNIT_LESSON = "Removed Successfully";
	String LP_UNABLE_REMOVE_UNIT ="Unable to remove Unit";
	String LP_UNABLE_REMOVE_LESSON ="Unable to remove Lesson";
	
	String LP_NO_ACTIVITY = "Activity not yet created for this Lesson";
	String LP_UPDATE_ASSESSMENTS_SUCCESS = "Assessments updated successfully";
	String LP_UPDATE_ASSESSMENTS_FAILED = "Assessments updated failed";
	String LP_NO_QUESTIONS = "There is no questions to edit";
	String LP_FLUENCY_READING_TEST = "Fluency Reading"; 
	
	String LP_PARENT_LAST_SEEN_OBJ = "parentLastSeen";	
	String LP_PARENT_LAST_SEEN_TAB = "parentLastSeenTab";
	String LP_LESSONS = "Lessons";	
	String LP_PROGRESS_REPORTS = "Progress Report";
	String LP_FILE_TRANSFER = "File Transfer";
	String LP_ASSESSMENTS = "Assessments";
	String LP_HOMEWORKS = "Homeworks";
	
	String USER_REGISTRATION_OBJ = "userReg";
	String LP_SELECT = "select";
	
	//Chat
	String GROUP_CHAT_LOGIN_STATUS = "true";
	
	String LP_SCHOOL_SAVED_SUCCESSFULLY = "School Created/Updated Successfully";
	String LP_ADMIN_ADDED_SUCCESSFULLY = "Administrator added/Updated successfully";
	String LP_ADMIN_ALREADY_EXISTS = "Administrator Already exists for this school";
	String LP_ADMIN_SCHOOL_ALREADY_EXISTS = "Email/School Already in Use";
	String LP_ADMIN_UPDATED_SUCCESSFULLY = "Administrator updated successfully";
	
	String TAB_VIEW_DAILY_ATTENDANCE ="daily attendance";
	String TAB_VIEW_WEEKLY_ATTENDANCE = "weekly attendance";
	String TAB_VIEW_SCHOOL_ATTENDANCE = "school attendance";
	
	String SCHOOL_LOGO_FOLDER ="schoollogo";
	String PROFILE_PIC_FOLDER ="profilePic";
	String PROFILE_PIC_FILE_NAME ="profile_pic.jpg";
	String PROFILE_PIC_UPLOAD_SUCCESS = "Uploaded successfully"; 
	String PROFILE_PIC_UPLOAD_FAILED = "Upload Failed"; 
	String PROFILE_PIC_DELETED_SUCCESS = "Removed"; 
	String PROFILE_PIC_DELETED_FAILED = "Remove Failed"; 
	
	String LP_TAB_UPDATE_SCHOOL_INFO = "update school info";
	String LP_TAB_UPDATE_SCHOOL_SPORTS = "update school sports";
	String LP_TAB_UPLOAD_SCHOOL_LOGO = "upload school logo";
	
	String UPLOAD_LOGO_SUCCESS = "School Logo uploaded successfully"; 
	String UPLOAD_LOGO_FAILURE = "School Logo not uploaded successfully"; 
	
	String TEACHER_GRADES_ERROR_MESSAGE = "Error while getting teacher grades";
	String TEACHER_TEST_DATES_ERROR_MESSAGE = "Error while getting dates";
	
	String SESSION_EXPIRED_MESSAGE = "Session expired. Please login again";
	
	String TEACHER_NO_GRADES_MESSAGE = "No Greades for this teacher";
	
	String TINYMCE_IMAGES = "tinymce_images";
	
	String ALREADY_REPORT_CREATED = "Report has already been created for the day";
	
	String LP_FEMALE = "female";
	String LP_MALE = "male";
	
	String AUTOMATED_SCHEDULER_SUCCESS = "Scheduled possible classes successfully";	
	String AUTOMATED_SCHEDULER_FAILED = "Automated Scheduler Failed";	
	
	String LP_UPDATED_SUCCESS = "Updated";
	String LP_CREATED_SUCCESS = "Created";
	String LP_FAILED = "Failed";
	String LP_REMOVED_SUCCESS = "Removed";
	String LP_ADDED_SUCCESS = "Added";
	String LP_REMOVED_FAILED = "Unable to Remove";
	String LP_UNABLE_TO_UPADATE = "Unable to Update";
	/*String LP_REMOVE_ACTIVITY_FAILED = "Failed";*/

	
	String PROMOTE_SUCCESS = "Promoted Students successfully";
	String PARENT_CHILD_OBJECT = "parentChildObj";
	
	
	String LP_TAB_BENCHMARK_RUNNING_RECORD = "benchmark running record";
	String RFLP_TEST = "RFLP Test"; 
	String TEST_RFLP = "testRflp"; 
	String GRADE_RFLP = "gradeRflp"; 
	String LP_DELETE="Unable to delete. Its already assigned";
	String ASSIGNMENT_TYPE_FILL_IN_THE_BLANKS = "Fill in the Blanks";
	String ASSIGNMENT_TYPE_TRUE_OR_FALSE ="True or False";
	String ASSIGNMENT_TYPE_RFLP ="Reading Fluency Learning Practice";
	
	String LP_TAB_BENCHMARK_SELF_GRADING="benchmarkSelfReview";
	String LP_TAB_BENCHMARK_PEER_REVIEW="benchmarkPeerReview";
	
	String LP_TAB_RFLP_SELF_GRADING = "rflp self grading";
	String LP_TAB_RFLP_PEER_GRADING = "rflp peer grading";
	String LP_TAB_RFLP_GRADING="rflp grading";
	String[] MCQOPTIONS = {"A", "B", "C", "D", "E"};
	
	String ASSIGNMENT_TYPE_FLUENCY_READING ="Fluency Reading";
	String ASSIGNMENT_TYPE_ACCURACY_READING ="Accuracy Reading";
	String LP_TAB_ACCURACY_RESULTS = "Accuracy Reading Results";
	
	String LP_TRUE = "true";
	String LP_FALSE = "false";
	
	// Reading Skills Development Dashboard
	String LP_READING_FILES_FOLDER = "RSDashboard";
	String VIDEO_MP4 = "mp4";
	String ASSIGNMENT_TYPE_COMPREHENSION = "Comprehension";
	Integer eltAutoTest = 1 ;
	
	String LP_TAB_CREATE_LE_RUBRIC="createLeRubric";
	String LP_TAB_EDIT_LE_RUBRIC="editLeRubric";
	String LP_TAB_ASSIGN_LE_RUBRIC="assignLeRubric";
	
	String LP_FLUENCY_AUDIO_DIRECTION_DIR = "fluency_reading_direction_files";
	String LP_FLUENCY_DIRECTION_AUDIO = "fluency_directions.wav";
	String LP_RETELL_DIRECTION_AUDIO = "retell_directions.wav";	
	
	String LP_TAB_VIEW_WORD_WORK = "ViewWordWorkTests";
	
	String LP_LEARNING_INDICATOR="LearningIndicator ";
	String 	ALREADY_EXISTED = "already existed";
	String LP_USED_FOR_MATH_ASSESS = "mathAssessment";
	
	//STEM 100
	String LP_TAB_STEM = "stem";
	String LP_STEM_TAB = "STEAM";
	String LP_TAB_STEM100 = "STEAM100";
	Integer LP_STEM_UNIT_QUES_TYPE_ID = 2;
	Integer LP_STEM_ESSENTIAL_QUES_TYPE_ID = 1;
	String LP_CREATE_STEM_ESSential_UNIT_QUES_ERROR = "System error occured..while creating stem essential, unit questions";
	String STEM_QUESTIONS_SAVED_SUCCESS = "The STEM Questions saved successfully.";
	String STEM_ACTIVITIES_SAVED_SUCCESS = "The STEM Activities saved successfully.";
	String STEM_ACTIVITIES_SAVED_FAIL = "Fail to save STEM Activities.";
	String STEM_FILES = "stem_files";
	String STEM_YES = "Yes";
	String STEM_NO = "No";
	String LITERACIES = "Literacies";
	String TECHNOLOGY = "Technology";
	String STEM_ASSIGN_SUCCESS="Assigned/Removed Successfully";
	String STEM_ASSIGN_STEM_UNIT_SUCCESS="Stem Units assigned successfully";
	String STEM_ASSIGN_STEM_UNIT_FAILED = "Assign Stem Units failed";
	String LP_STEM = "stem";
	String LP_STRANDS = "strands";
	String LP_STRATEGIES = "strategies";
	String LP_STEM_SCIENCE = "Science";
	String LP_STEM_TECHNOLOGY = "Technology";
	String LP_STEM_ENGINEERING = "Engineering";
	String LP_STEM_MATH = "Math";
	String DELETED_SUCCESSFULLY = "Deleted Successfully";
	String UNABLE_TO_DELETE ="Test already assigned..Unable to delete !!";
	String DELETE_FAILED = "Unable to Delete";
	
	//Upload Excel Files
	String UPLOAD_LEGENDS_SUCCESS = "Legends Uploaded successfully"; 
	String UPLOAD_LEGENDS_FAILURE = "Legends not uploaded successfully";
	String UPLOAD_LEGENDS_FAILURE_EMPTY = "Upload Legends Failed, because the file uploaded is empty!!!";
	String STEM_QUESTIONS_UPDATE_SUCCESS = "The STEM Questions Updated successfully.";
	String UPLOAD_STEM_STRANDS_SUCCESS = "Stem Strands Uploaded successfully"; 
	String UPLOAD_STEM_STRANDS_FAILURE = "Stem Strands not uploaded successfully";
	String UPLOAD_STEM_STRANDS_FAILURE_EMPTY = "Upload Stem Strands Failed, because the file uploaded is empty!!!";
	String UPLOAD_IOL_SUCCESS = "IOL Rubrics Uploaded successfully"; 
	String UPLOAD_IOL_FAILURE = "IOL Rubrics not uploaded successfully";
	String UPLOAD_IOL_FAILURE_EMPTY = "Upload IOL Rubrics Failed, because the file uploaded is empty!!!";
	String UPLOAD_STAR_SCORE_SUCCESS= "Star Score Uploaded successfully"; 
	String UPLOAD_STAR_SCORE_FAILURE = "Star Score not uploaded successfully";
	String UPLOAD_STAR_SCORE_FAILURE_EMPTY = "Upload Star Score Failed, because the file uploaded is empty!!!";
	
	String UPLOAD_FORMATIVE_SUCCESS = "Formative uploaded successfully"; 
	String UPLOAD_FORMATIVE_FAILURE = "Formative not uploaded successful";
	String UPLOAD_EXCEL_HEADER = "Found ERRORS in Excel Sheet. Please Check and Upload again.\n##########################################################\n\n";
	String INVALID_EXCEL_DATA_UPLOAD = "Please provide valid data and upload again. \r\n\n";
	String INVALID_EXCEL_FORMAT ="Unable to upload the Excel Sheet.Please verfity the format. \r\n\n";
	String LOG_CHECK = "Found Errors..Please Check Log file.";
	String EXCEL_UPLOAD = "excel_upload";
	String FORMATIVE_ASSESSMENT_LOG = "formative_assessment_log.log";
	String FORMAT_XLS = "xls";
	
	//Math Game
	long GAME_LEVEL_ONE = 1;
	long GAME_LEVEL_TWO = 2;
	long GAME_LEVEL_THREE = 3;
	
	String LP_TAB_LESSON = "lesson";
	String LP_REMOVE_STEM_STRAND_SUCCESS = "Removed STEM Strand successfully";
	String LP_REMOVE_STEM_STRAND_FAILED = "Removed STEM Strand failed";
	String LP_REMOVE_STEM_STRAND_ERROR = "System error occured..while removing STEM Strand";
	String LP_TAB_DOWNLOAD_FLUENCY_RESULTS="fluencyResults";
	String LP_TAB_DOWNLOAD_COMPREHENSION_RESULTS="comprehensionResults";
	
	String LP_SHOW = "show";
	String SCHOOL_ANNOUNCEMENTS_FOLDER ="SchoolAnnoucements";
	String LP_CHANGED= "changed";
	String LP_NOT_CHANGED= "not changed";
	String SIGN_PATH = "signature"; 
	String UN_READ= "unread";
	String READ= "read";
	String student="student";
	
	String READ_STATUS_YES = "Yes";
	String READ_STATUS_NO = "No";
	
	String LP_TAB_GOALREPORTS = "goalReports";
	
	String CAASPP_TYPE_ELA = "ELA Scaled Level";
	String CAASPP_TYPE_MATH = "Math Scaled Level";
	
	String TEACHER_GRADING = "Teacher Grading";
	String READING_TYPE_FLUENCY = "Fluency";
	String READING_TYPE_RETELL = "Retell";
	String LP_PICTURE = "Picture";
	
	String ACADEMIC_YEAR_OBJ = "AcademicYear";
	String CURRENT_ACADEMIC_YES = "YES";
	String CURRENT_ACADEMIC_NO = "NO";
	String COMMON_FOLDER = "common";
    
	long TEACHER_USER_TYPEID=3;
	String LP_TAB_TEACHER_GOAL_SETTING_EXCEL_DOWNLOAD="teacherGoalSettingExcelDownload";
	String PREVIOUS_AUTH_USER = "previousAuthUser";
	String EXISTS = "Exists";
	String NOT_EXISTED = "Not Existed";
	
	String LP_TAB_ACCURACY_SELF_GRADING="accuracySelfReview";
	String LP_TAB_ACCURACY_PEER_REVIEW="accuracyPeerReview";
	
	String LP_BOOK_ALREADY_EXISTED = "Book already existed with same name";
	
	long READ_REG_REVIEW_ACTIVITY_ID = 1;
	long READ_REG_CREATE_QUIZ_ACTIVITY_ID = 2;
	long READ_REG_RETELL_ACTIVITY_ID = 3;
	long READ_REG_TAKE_QUIZ_ACTIVITY_ID = 4;
	long READ_REG_UPLOAd_PIC_ACTIVITY_ID = 5;
	
	String LP_SAVED_OR_UPDATED_SUCCESS = "Saved or updated successfully.";
	
	long READ_REG_QUIZ_QUESTIONS_COUNT = 10;
	String INDEX = "index";
	String LP_TAB_RR_REVIEW="rr_review";
	String LP_TAB_PRR="prr";
	String LP_TAB_BOOK_APPROVAL="book_approval";
	String LP_TAB_READING_REGISTER = "Reading Register";
	String LP_TAB_PERSONAL_READING_REGISTER = "Personal Reading Register";
	String LP_TAB_RETURN_BOOKS = "Returned Books";
	String LP_TAB_HOME_RETURN_BOOKS = "Home Returned Books";
	String LP_TAB_RR_REPORTS="rr_reports";
	String LP_TAB_RR_BOOKS_MERGED_SUCCESSFULLY = "Books Merged Successfully";
	//Google SSO
	
	  String G_CLIENT_ID = "917074430981-t4893cqjouiil5u1ve27iag5dhs4ato2.apps.googleusercontent.com";
	  String G_CLIENT_SECRET = "CRp5q4AejbpcvTIeR-fS3Ihk"; 
	  String G_REDIRECT_URI = "https://learningpriority.com/auth2callback.htm";
	 
	
	//Google SSO settings for server
	//String G_CLIENT_ID ="502412324291-448ue281efh44o30104ltdukcrn432u0.apps.googleusercontent.com";
	//String G_CLIENT_SECRET = "buinoAH-RGMFDdiOm96bgmhi";
	//String G_REDIRECT_URI = "https://learningpriority.com/auth2callback.htm";
		
	//Use the below for local setup
	//String G_CLIENT_ID ="315975326195-2mkfbc5rap4i3q8qlfdb8tv4g0ap2e3r.apps.googleusercontent.com";
	//String G_CLIENT_SECRET = "rxYJgyZeqBd23kcGeV-Yb6PS";
	//String G_REDIRECT_URI = "http://localhost:8080/auth2callback.htm";
	
	String G_GRANT_TYPE= "authorization_code";
	String G_URL= "https://accounts.google.com/o/oauth2/token";
	String G_ACCESS_TOKEN = "access_token";
	String G_PATH_GET_METHOD = "https://www.googleapis.com/oauth2/v1/userinfo";
	
	long RRROWCOUNT = 25;
	long RRDISPLAYPAGES = 10;
	long RRFIRSTPAGE = 1;
	
	long RRROWCOUNT1 = 25;
	
	String SORTING_ORDER_DESC = "desc";
	String SORTING_ORDER_ASC = "asc";
	String RR_COLUMN_CREATEDATE = "createDate";
	String RR_COLUMN_BOOK_TITLE = "bookTitle";
	String RR_COLUMN_AUTHOR = "author";
	String RR_COLUMN_NUMBER_OF_PAGES = "numberOfPages";
	
	String LP_PAGE_SELF_PEER_RESULTS = "selfAndPeerReviewResults";
	
	String SCHOOL_LEVEL_HIGH_SCHOOL = "High School";
	
	
	
}
