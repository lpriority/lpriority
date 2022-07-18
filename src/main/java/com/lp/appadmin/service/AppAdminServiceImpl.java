package com.lp.appadmin.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lp.admin.dao.AdminDAO;
import com.lp.admin.dao.GradesDAO;
import com.lp.admin.dao.StudentClassesDAO;
import com.lp.appadmin.dao.AssignmentTypeDAO;
import com.lp.appadmin.dao.DaysDAO;
import com.lp.appadmin.dao.GradeLevelDAO;
import com.lp.appadmin.dao.MasterGradesDAO;
import com.lp.appadmin.dao.MinutesDAO;
import com.lp.appadmin.dao.SchoolDAO;
import com.lp.appadmin.dao.SchoolLevelDAO;
import com.lp.appadmin.dao.SchoolTypeDAO;
import com.lp.appadmin.dao.SecurityQuestionDAO;
import com.lp.appadmin.dao.StatesDAO;
import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.model.Address;
import com.lp.model.AssignmentType;
import com.lp.model.ClassStatus;
import com.lp.model.Days;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.GradeLevel;
import com.lp.model.MasterGrades;
import com.lp.model.Minutes;
import com.lp.model.RegisterForClass;
import com.lp.model.RegisterForClassId;
import com.lp.model.School;
import com.lp.model.SchoolLevel;
import com.lp.model.SchoolType;
import com.lp.model.Security;
import com.lp.model.SecurityQuestion;
import com.lp.model.States;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.student.dao.RegisterForClassDAO;
import com.lp.student.dao.StudentDAO;
import com.lp.teacher.dao.CAASPPDAO;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.teacher.dao.TeacherSubjectsDAO;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.WebKeys;

public class AppAdminServiceImpl implements AppAdminService {
	
	static final Logger logger = Logger.getLogger(AppAdminServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AssignmentTypeDAO assignmentTypeDAO;

	@Autowired
	private SecurityQuestionDAO securityQuestionDAO;

	@Autowired
	private MinutesDAO minutesDAO;

	@Autowired
	private DaysDAO daysDAO;

	@Autowired
	private SchoolDAO schoolDAO;

	@Autowired
	private MasterGradesDAO masterGradesDAO;

	@Autowired
	private GradesDAO gradesDAO;

	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private UserRegistrationDAO userRegistrationDAO;

	@Autowired
	private TeacherDAO teacherDAO;

	@Autowired
	private TeacherSubjectsDAO teacherSubjectsDAO;

	@Autowired
	private RegisterForClassDAO registerForClassDAO;

	@Autowired
	private SchoolTypeDAO schoolTypeDAO;

	@Autowired
	private SchoolLevelDAO schoolLevelDAO;

	@Autowired
	private StatesDAO statesDAO;

	@Autowired
	private StudentClassesDAO studentClassesDAO;

	@Autowired
	private GradeLevelDAO gradeLevelDAO;

	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private CAASPPDAO caasppdao;

	/* ########### User Type methods go here ########## */
	public void setUserDao(UserDAO userDao) {
		this.userDAO = userDao;
	}

	@Override
	public List<User> getUserTypes() {
		return userDAO.getUserTypeList();
	}

	@Override
	public User getUserType(long userTypeId) {
		return userDAO.getUserType(userTypeId);
	}

	@Override
	public void deleteUserType(long userTypeId) {
		userDAO.deleteUserType(userTypeId);
	}

	@Override
	public void saveUserType(User user) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		user.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		user.setChangeDate(changeDate);
		userDAO.saveUserType(user);
	}

	/* ########### Assignment Type methods go here ########## */

	public void setAssignmentTypeDao(AssignmentTypeDAO assignmentTypeDao) {
		this.assignmentTypeDAO = assignmentTypeDao;
	}

	@Override
	public List<AssignmentType> getAssignmentTypes() {
		return assignmentTypeDAO.getAssignmentTypeList();
	}

	@Override
	public AssignmentType getAssignmentType(long assignmentTypeId) {
		return assignmentTypeDAO.getAssignmentType(assignmentTypeId);
	}

	@Override
	public void deleteAssignmentType(long assignmentTypeId) {
		assignmentTypeDAO.deleteAssignmentType(assignmentTypeId);
	}

	@Override
	public void saveAssignmentType(AssignmentType assignmentType) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		assignmentType.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		assignmentType.setChangeDate(changeDate);
		assignmentTypeDAO.saveAssignmentType(assignmentType);
	}

	/* ########### SecurityQuestion methods go here ########## */

	public void setSecurityQuestionDao(SecurityQuestionDAO securityQuestionDao) {
		this.securityQuestionDAO = securityQuestionDao;
	}

	@Override
	public List<SecurityQuestion> getSecurityQuestions() {
		return securityQuestionDAO.getSecurityQuestionList();
	}

	@Override
	public List<SecurityQuestion> loadSecurityQuestions() {
		return securityQuestionDAO.getSecurityQuestionList();
	}

	@Override
	public SecurityQuestion getSecurityQuestion(long securityQuestionId) {
		return securityQuestionDAO.getSecurityQuestion(securityQuestionId);
	}

	@Override
	public void deleteSecurityQuestion(long securityQuestionId) {
		securityQuestionDAO.deleteSecurityQuestion(securityQuestionId);
	}

	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		securityQuestion.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		securityQuestion.setChangeDate(changeDate);
		securityQuestionDAO.saveSecurityQuestion(securityQuestion);
	}

	/* ########### SecurityQuestion methods go here ########## */

	public void setMinutesDao(MinutesDAO minutesDAO) {
		this.minutesDAO = minutesDAO;
	}

	@Override
	public List<Minutes> getMinutes() {
		return minutesDAO.getMinutesList();
	}

	@Override
	public Minutes getMinute(long minuteId) {
		return minutesDAO.getMinute(minuteId);
	}

	@Override
	public void deleteMinute(long minuteId) {
		minutesDAO.deleteMinute(minuteId);
	}

	@Override
	public void saveMinute(Minutes minute) {
		minutesDAO.saveMinute(minute);
	}

	/* ##### Days methods starts from here ##### */

	public void setMinutesDao(DaysDAO daysDAO) {
		this.daysDAO = daysDAO;
	}

	public List<Days> getDays() {
		return daysDAO.getDaysList();
	}

	public Days getDay(long dayId) {
		return daysDAO.getDay(dayId);
	}

	public void deleteDay(long dayId) {
		daysDAO.deleteDay(dayId);
	}

	public void saveDay(Days day) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		day.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		day.setChangeDate(changeDate);
		daysDAO.saveDay(day);
	}

	@Override
	public List<User> getUserTypeList2() {
		List<User> aList = userDAO.getUserTypeList2();
		return aList;
	}

	@Override
	public User getUserType(String userType) {
		return userDAO.getUserType(userType);
	}

	/* ########### School methods go here ########## */

	public void setSchoolDao(SchoolDAO schoolDao) {
		this.schoolDAO = schoolDao;
	}

	/* ########### MasterGrades methods go here ########## */

	public void setMasterGradesDAO(MasterGradesDAO masterGradesDao) {
		this.masterGradesDAO = masterGradesDao;
	}

	/* ########### Grades methods go here ########## */

	public void setGradesDAO(GradesDAO gradesDao) {
		this.gradesDAO = gradesDao;
	}

	/* ########### Admin methods go here ########## */

	public void setAdminDAO(AdminDAO adminDao) {
		this.adminDAO = adminDao;
	}

	/* ########### UserRegistrationDAO methods go here ########## */

	public void setuserRegistrationDAO(UserRegistrationDAO userRegistrationDAO) {
		this.userRegistrationDAO = userRegistrationDAO;
	}

	/* ########### Teacher methods go here ########## */

	public void setTeacherDAO(TeacherDAO teacherDao) {
		this.teacherDAO = teacherDao;
	}

	/* ########### TeacherSubjectsDAO methods go here ########## */

	public void setTeacherSubjectsDAO(TeacherSubjectsDAO teacherSubjectsDAO) {
		this.teacherSubjectsDAO = teacherSubjectsDAO;
	}

	/* ########### SchoolType methods go here ########## */

	public void setSchoolTypeDAO(SchoolTypeDAO schoolTypeDAO) {
		this.schoolTypeDAO = schoolTypeDAO;
	}

	/* ########### SchoolLevelDAO methods go here ########## */

	public void setSchoolLevelDAO(SchoolLevelDAO schoolLevelDAO) {
		this.schoolLevelDAO = schoolLevelDAO;
	}

	/* ########### StatesDAO methods go here ########## */

	public void setStatesDAO(StatesDAO statesDAO) {
		this.statesDAO = statesDAO;
	}

	/* ########### StudentClassesDAO methods go here ########## */

	public void setStudentClassesDAO(StudentClassesDAO studentClassesDAO) {
		this.studentClassesDAO = studentClassesDAO;
	}

	/* ########### GradeLevelDAO methods go here ########## */

	public void setGradeLevelDAO(GradeLevelDAO gradeLevelDAO) {
		this.gradeLevelDAO = gradeLevelDAO;
	}

	/* ########### StudentDAO methods go here ########## */

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	@Override
	public boolean bulkRegisterUsers(InputStream xmlFile, long userTypeId)
			throws Exception {
		if (userTypeId == 3) {

			List<String> lastNamesFromXML = new ArrayList<String>();
			List<String> firstNamesFromXML = new ArrayList<String>();
			List<Long> gradeIdsFromXML = new ArrayList<Long>();
			List<String> userNamesFromXML = new ArrayList<String>();
			List<String> schoolAbbreviationsFromXML = new ArrayList<String>();
			List<String> classNamesFromXML = new ArrayList<String>();
			List<Long> teacherScIdsFromXML = new ArrayList<Long>();

			DocumentBuilderFactory Factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = Factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			// creating an XPathFactory:
			XPathFactory factory = XPathFactory.newInstance();
			// using this factory to create an XPath object:
			XPath xpath = factory.newXPath();
			// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile("//" + "Table" + "/*");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			for (int i = 0; i < nodes.getLength(); i++) {
				Element el = (Element) nodes.item(i);
				NodeList children = el.getChildNodes();
				int count = 0;
				for (int k = 0; k < children.getLength(); k++) {
					Node child = children.item(k);
					NodeList gchildren = child.getChildNodes();
					for (int l = 0; l < gchildren.getLength(); l++) {
						Node gchild = gchildren.item(l);
						if (gchild.getNodeType() != Node.TEXT_NODE) {
							if (gchild.getFirstChild() != null
									&& gchild.getFirstChild().getNodeType() == Node.TEXT_NODE) {
								count++;
								if (gchild.getFirstChild().getNodeValue().equals("Teacher last")
										|| gchild.getFirstChild().getNodeValue().equals("Teacher first")
										|| gchild.getFirstChild().getNodeValue().equals("Grade")
										|| gchild.getFirstChild().getNodeValue().equals("Username")
										|| gchild.getFirstChild().getNodeValue().equals("School")
										|| gchild.getFirstChild().getNodeValue().equals("Class Name") 
										|| gchild.getFirstChild().getNodeValue().equals("ID")
										|| gchild.getFirstChild().getNodeValue().equals("NA")
										|| gchild.getFirstChild().getNodeValue().equals("")) {
									continue;
								}
								if (count == 1) {
									lastNamesFromXML.add(gchild.getFirstChild()
											.getNodeValue());
								} else if (count == 2) {
									firstNamesFromXML.add(gchild
											.getFirstChild().getNodeValue());
								} else if (count == 3) {
									schoolAbbreviationsFromXML.add(gchild
											.getFirstChild().getNodeValue());
								} else if (count == 4) {
									gradeIdsFromXML.add(Long.parseLong(gchild
											.getFirstChild().getNodeValue()));									
								} else if (count == 5) {
									teacherScIdsFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));									
								} else if (count == 6) {
									classNamesFromXML.add(gchild
											.getFirstChild().getNodeValue());
								}
								else if(count == 7){
									if (gchild.getFirstChild().getNodeValue()
											.contains("@"))
										userNamesFromXML
												.add(gchild.getFirstChild()
														.getNodeValue());
									else
										userNamesFromXML.add(gchild
												.getFirstChild().getNodeValue()
												+ "@rioschools.org");
									count = 0;
								}
							}
						}
					}
				}

			}

			// TODO NEED TO ADD FAULT TOLERANCE I.E WAY TO HANDLE WRONG OR NO
			// DATA

			List<Long> userIdsXMLBased = new ArrayList<Long>();
			List<MasterGrades> masterGradesObjectsFromDB = masterGradesDAO
					.getMasterGradesList();
			Map<String, Long> newUserNameIdMap = new HashMap<String, Long>();
			Map<String, Teacher> newUserNameTeacherObjMap = new HashMap<String, Teacher>();
			List<UserRegistration> userRegistrationListFromDB = userRegistrationDAO
					.getUserRegistrationList(new User(3,
							WebKeys.LP_USER_TYPE_TEACHER, new java.util.Date(),
							new java.util.Date()));
			List<Grade> gradesFromDB = gradesDAO.getGradesList();

			// TODO MANUALLY POPULATING SCHOOL TABLE IF SCHOOL DATA IS NOT
			// PROVIDED
			List<School> schoolObjectsFromDB = schoolDAO.getSchoolList();
			List<School> schoolObjsXMLBasedOrder = new ArrayList<School>();
			Map<String, School> schoolsAbbrvObjMap = new HashMap<String, School>();
			Map<Long, School> schoolsIdObjMap = new HashMap<Long, School>();
			Set<String> schoolAbbrSET = new HashSet<String>();

			for (String schoolAbbr : schoolAbbreviationsFromXML) {
				schoolAbbrSET.add(schoolAbbr);
			}

			SchoolType schoolType = schoolTypeDAO.getSchoolType(1);
			SchoolLevel schoolLevel = schoolLevelDAO.getSchoolLevel(4);
			States state = statesDAO.getState(1);
			for (String schoolAbbr : schoolAbbrSET) {
				boolean schoolExists = false;
				for (School school : schoolObjectsFromDB) {
					if (schoolAbbr.equals(school.getSchoolAbbr())) {
						schoolsAbbrvObjMap.put(schoolAbbr, school);
						schoolsIdObjMap.put(school.getSchoolId(), school);
						schoolExists = true;
						break;
					}
				}
				if (!schoolExists) {
					School newSchool = new School();
					newSchool.setSchoolType(schoolType);
					newSchool.setSchoolLevel(schoolLevel);
					newSchool.setSchoolAbbr(schoolAbbr);
					newSchool.setSchoolName(schoolAbbr);
					newSchool.setNoOfStudents(100);
					newSchool.setCity("Vizag"); // TODO ELIMINATE TEST DATA
					newSchool.setStates(state);
					newSchool.setRegistrationDate(new java.util.Date());
					schoolDAO.saveSchool(newSchool);
					schoolsAbbrvObjMap.put(schoolAbbr, newSchool);
					schoolsIdObjMap.put(newSchool.getSchoolId(), newSchool);
				}
			}

			for (String schoolAbbr : schoolAbbreviationsFromXML) {
				if (schoolsAbbrvObjMap.get(schoolAbbr) != null) {
					schoolObjsXMLBasedOrder.add(schoolsAbbrvObjMap
							.get(schoolAbbr));
				} else {
					for (School school : schoolObjectsFromDB) {
						if (schoolAbbr.equals(school.getSchoolAbbr())) {
							schoolObjsXMLBasedOrder.add(school);
						}
					}
				}
			}

			/*************
			 * POPULATING GRADE TABLE BASED ON SCHOOL_ID AND MASTER_GRADES_ID
			 * BASED ON GRADE_NAME
			 ************/
			List<Long> normalizedGradeIds = new ArrayList<Long>();
			for (Long grade : gradeIdsFromXML) {
				boolean found = false;
				for (MasterGrades masterGrade : masterGradesObjectsFromDB) {
					if (masterGrade.getMasterGradesId() == grade) {
						found = true;
						break;
					}
				}
				if (found)
					normalizedGradeIds.add(grade);
				else if(grade == 0)
					normalizedGradeIds.add(new Long(13));
				else
					normalizedGradeIds.add(new Long(14));
			}

			for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
				boolean found = false;
				for (Grade g : gradesFromDB) {
					if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
							.getSchoolId()
							&& normalizedGradeIds.get(temp_i) == g
									.getMasterGrades().getMasterGradesId()) {
						found = true;
						break;
					}
				}
				if (!found) {
					Grade newGrade = new Grade();
					newGrade.setSchoolId((schoolObjsXMLBasedOrder.get(temp_i)
							.getSchoolId()));
					for (MasterGrades masterGrade : masterGradesObjectsFromDB) {
						if (normalizedGradeIds.get(temp_i).equals(
								masterGrade.getMasterGradesId()))
							newGrade.setMasterGrades(masterGrade);
					}
					newGrade.setCreateDate(new java.util.Date());
					newGrade.setChangeDate(new java.util.Date());
					newGrade.setStatus(WebKeys.LP_STATUS_ACTIVE);
					gradesDAO.saveGrades(newGrade);
					gradesFromDB.add(newGrade);
				}
			}

			/************** POPULATING CLASS TABLE BASED ON SCHOOL_ID AND CLASS_NAME BASED **************/
			List<StudentClass> classesFromDB = adminDAO.getAllClasses();
			Set<String> schoolClassSet = new HashSet<String>();
			for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
				schoolClassSet.add(schoolObjsXMLBasedOrder.get(temp_i)
						.getSchoolId()
						+ "@BAYLOGIC"
						+ classNamesFromXML.get(temp_i));
			}
			for (String s : schoolClassSet) {
				if (s.split("@BAYLOGIC").length == 2) {
					String schoolId = s.split("@BAYLOGIC")[0];
					String className = s.split("@BAYLOGIC")[1];
					boolean found = false;
					for (StudentClass stuClass : classesFromDB) {
						if (stuClass.getClassName().equalsIgnoreCase(className)
								&& stuClass.getSchool().getSchoolId() == Long
										.parseLong(schoolId)) {
							found = true;
							break;
						}
					}
					if (!found) {
						StudentClass newClass = new StudentClass();
						newClass.setClassName(className);
						newClass.setSchool(schoolsIdObjMap.get((Long
								.parseLong(schoolId))));
						newClass.setChangeDate(new java.util.Date());
						newClass.setCreateDate(new java.util.Date());
						studentClassesDAO.saveStudentclasses(newClass);
						classesFromDB.add(newClass);
					}
				}

			}

			/***********
			 * POPULATING GRADE_CLASSES TABLE BASED ON GRADE_ID AND CLASS_ID
			 * BASED
			 ******************/

			List<GradeClasses> gradeClassesFromDB = adminDAO
					.getAllGradeClasses();
			classesFromDB = adminDAO.getAllClasses();
			gradesFromDB = gradesDAO.getGradesList();
			for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
				Long gradeId = null;
				Long classId = null;
				Grade grade = null;
				StudentClass studentClass = null;
				for (Grade g : gradesFromDB) {
					if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
							.getSchoolId()
							&& normalizedGradeIds.get(temp_i) == g
									.getMasterGrades().getMasterGradesId()) {
						gradeId = g.getGradeId();
						grade = g;
						break;
					}
				}
				for (StudentClass stuClass : classesFromDB) {
					if (stuClass.getClassName().equalsIgnoreCase(
							classNamesFromXML.get(temp_i))
							&& stuClass.getSchool().getSchoolId() == schoolObjsXMLBasedOrder
									.get(temp_i).getSchoolId()) {
						classId = stuClass.getClassId();
						studentClass = stuClass;
						break;
					}
				}
				boolean found = false;
				for (GradeClasses gradeClass : gradeClassesFromDB) {
					if (gradeClass.getStudentClass().getClassId() == classId
							&& gradeClass.getGrade().getGradeId() == gradeId) {
						found = true;
						break;
					}
				}
				if (!found) {
					GradeClasses newGradeClass = new GradeClasses();
					newGradeClass.setChangeDate(new java.util.Date());
					newGradeClass.setCreateDate(new java.util.Date());
					newGradeClass.setGrade(grade);
					newGradeClass.setStudentClass(studentClass);
					newGradeClass.setStatus(WebKeys.LP_STATUS_ACTIVE);
					adminDAO.saveGradeClasses(newGradeClass);
					gradeClassesFromDB.add(newGradeClass);
				}
			}

			/*******
			 * POPULATE USER_REGISTRATION TABLE AND TEACHER TABLE BASED ON
			 * REG_ID OF USER_REGISTRATION
			 *****/

			Set<String> userNameSET = new HashSet<String>();
			String pwd = userRegistrationDAO.getMD5Conversion("teacher");
			User uType = userDAO.getUserType(WebKeys.LP_USER_TYPE_TEACHER);
			List<UserRegistration> newURList = new ArrayList<UserRegistration>();
			SecurityQuestion secQuestion = new SecurityQuestion();
			secQuestion.setSecurityQuestionId(2);

			for (String userName : userNamesFromXML) {
				userNameSET.add(userName);
			}

			for (String userName : userNameSET) {
				boolean found = false;				
				for (UserRegistration uReg : userRegistrationListFromDB) {
					if (userName.equalsIgnoreCase(uReg.getEmailId())) {
						found = true;
						int index = userNamesFromXML.indexOf(userName);
						Teacher teacher = teacherService.getTeacher(uReg.getRegId());
						if(teacher.getTeacherScId() == null){
							teacher.setTeacherScId(teacherScIdsFromXML.get(index));
							teacherDAO.saveTeacher(teacher);
						}
						break;
					}					
				}
				if (!found) {
					int index = userNamesFromXML.indexOf(userName);
					UserRegistration newUserRegistration = new UserRegistration();
					newUserRegistration.setTitle("");
					newUserRegistration.setEmailId(userNamesFromXML.get(index));
					newUserRegistration.setPassword(pwd);
					newUserRegistration.setUser(uType);
					newUserRegistration.setStatus(WebKeys.LP_STATUS_ACTIVE);
					newUserRegistration.setSchool((schoolObjsXMLBasedOrder
							.get(index)));
					newUserRegistration.setFirstName(firstNamesFromXML
							.get(index).trim());
					newUserRegistration
							.setLastName(lastNamesFromXML.get(index).trim());
					newUserRegistration.setTeacherScId(teacherScIdsFromXML.get(index));
					Address addressObj = new Address();
					addressObj.setAddress("600 Simon Way #");
					addressObj.setCity("Oxnard");
					addressObj.setZipcode(93036);
					addressObj.setStates(state);
					newUserRegistration.setAddress(addressObj);
					newURList.add(newUserRegistration);
				}
			}

			List<Teacher> newTeachers = new ArrayList<Teacher>();
			for (UserRegistration ur : newURList) {
				newUserNameIdMap.put(ur.getEmailId(), ur.getRegId());
				Teacher newTeacher = new Teacher();
				newTeacher.setUserRegistration(ur);
				newTeacher.setStatus(WebKeys.LP_STATUS_ACTIVE);
				newTeacher.setTeacherScId(ur.getTeacherScId());
				newTeachers.add(newTeacher);
				newUserNameTeacherObjMap.put(ur.getEmailId(), newTeacher);
			}

			List<Security> securityList = new ArrayList<Security>();
			for (UserRegistration ur : newURList) {
				Security security = new Security();
				newUserNameIdMap.put(ur.getEmailId(), ur.getRegId());
				security.setUserRegistration(ur);
				security.setSecurityQuestion(secQuestion);
				security.setVerificationCode(WebKeys.DEFAULT_VERIFICATION_CODE);
				security.setAnswer(WebKeys.DEFAULT_SECURITY_ANSWER);
				security.setStatus(WebKeys.SECURITY_STATUS_INACTIVE);
				securityList.add(security);
			}

			userRegistrationDAO.saveBulkTeacherRegistration(newURList,
					newTeachers, securityList);

			for (String userName : userNamesFromXML) {
				if (newUserNameIdMap.get(userName) != null) {
					userIdsXMLBased.add(newUserNameIdMap.get(userName));
				} else {
					for (UserRegistration uReg : userRegistrationListFromDB) {
						if (userName.equals(uReg.getEmailId())) {
							userIdsXMLBased.add(uReg.getRegId());
						}
					}
				}
			}

			/***********
			 * POPULATING TEACHER_SUBJECTS TABLE BASED ON TEACHER_ID, GRADE_ID
			 * AND CLASS_ID BASED
			 ******************/

			List<TeacherSubjects> teacherSubjectsFromDB = teacherSubjectsDAO
					.getAllTeacherSubjects();
			List<Teacher> teachersFromDB = teacherDAO.getAllTeachers();
			List<TeacherSubjects> teacherSubjects = new ArrayList<TeacherSubjects>();
			GradeLevel atGradeLevel = gradeLevelDAO.getGradeLevel(2);
			for (int temp_i = 0; temp_i < userNamesFromXML.size(); temp_i++) {
				Long gradeId = null;
				Long classId = null;
				Long teacherId = null;
				Grade grade = null;
				StudentClass studentClass = null;
				Teacher teacher = null;
				for (Grade g : gradesFromDB) {
					if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
							.getSchoolId()
							&& normalizedGradeIds.get(temp_i) == g
									.getMasterGrades().getMasterGradesId()) {
						gradeId = g.getGradeId();
						grade = g;
						break;
					}
				}
				for (StudentClass stuClass : classesFromDB) {
					if (stuClass.getClassName().equalsIgnoreCase(
							classNamesFromXML.get(temp_i))
							&& stuClass.getSchool().getSchoolId() == schoolObjsXMLBasedOrder
									.get(temp_i).getSchoolId()) {
						classId = stuClass.getClassId();
						studentClass = stuClass;
						break;
					}
				}
				if (newUserNameTeacherObjMap.get(userNamesFromXML.get(temp_i)) != null) {
					teacherId = newUserNameTeacherObjMap.get(
							userNamesFromXML.get(temp_i)).getTeacherId();
					teacher = newUserNameTeacherObjMap.get(userNamesFromXML
							.get(temp_i));
				} else {
					for (Teacher t : teachersFromDB) {
						if (t.getUserRegistration().getEmailId()
								.equals(userNamesFromXML.get(temp_i))) {
							teacherId = t.getTeacherId();
							teacher = t;
							break;
						}
					}
				}
				boolean found = false;
				for (TeacherSubjects teacherSubject : teacherSubjectsFromDB) {
					if (teacherSubject.getStudentClass().getClassId() == classId
							&& teacherSubject.getGrade().getGradeId() == gradeId
							&& teacherSubject.getTeacher().getTeacherId() == teacherId) {
						found = true;
						break;
					}
				}
				if (!found) {
					TeacherSubjects newTeacherSubjects = new TeacherSubjects();
					newTeacherSubjects.setTeacher(teacher);
					newTeacherSubjects.setStudentClass(studentClass);
					newTeacherSubjects.setGrade(grade);
					newTeacherSubjects.setChangeDate(new java.util.Date());
					newTeacherSubjects.setCreateDate(new java.util.Date());
					newTeacherSubjects.setGradeLevel(atGradeLevel);
					newTeacherSubjects.setNoSectionsPerDay(3);
					newTeacherSubjects.setNoSectionsPerWeek(9);
					teacherSubjects.add(newTeacherSubjects);
				}
			}
			teacherSubjectsDAO.saveTeacherSubject(teacherSubjects);
			return true;
		} 
		else if (userTypeId == 4) {  // student bulk registration start
		boolean outerBreak = false;
		ArrayList<String> lastNamesFromXML = new ArrayList<String>();
		ArrayList<String> firstNamesFromXML = new ArrayList<String>();
		ArrayList<String> birthDatesFromXML = new ArrayList<String>();
		ArrayList<String> gendersFromXML = new ArrayList<String>();
		ArrayList<Long> gradeIdsFromXML = new ArrayList<Long>();
		ArrayList<String> classNamesFromXML = new ArrayList<String>();
		ArrayList<String> userNamesFromXML = new ArrayList<String>();
		ArrayList<String> parentUserNamesFromXML = new ArrayList<String>();
		ArrayList<String> tempParentUserNamesFromXML = new ArrayList<String>();
		ArrayList<String> teachersFromXML = new ArrayList<String>();
		ArrayList<Long> teacherIdsFromXML = new ArrayList<Long>();
		ArrayList<String> schoolAbbreviationsFromXML = new ArrayList<String>();
		ArrayList<String> passwordsFromXML = new ArrayList<String>();
		ArrayList<Long> studentIdFromXML = new ArrayList<Long>();
		
		System.out.println("start student bulk regis");
		
		DocumentBuilderFactory Factory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = Factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		// creating an XPathFactory:
		XPathFactory factory = XPathFactory.newInstance();
		// using this factory to create an XPath object:
		XPath xpath = factory.newXPath();
		// XPath Query for showing all nodes value
		XPathExpression expr = xpath.compile("//" + "Table" + "/*");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		for (int i = 0; i < nodes.getLength(); i++) {
			Element el = (Element) nodes.item(i);
			NodeList children = el.getChildNodes();
			int count = 0;
			for (int k = 0; k < children.getLength(); k++) {
				Node child = children.item(k);
				NodeList gchildren = child.getChildNodes();
				for (int l = 0; l < gchildren.getLength(); l++) {
					Node gchild = gchildren.item(l);
					if (gchild.getNodeType() != Node.TEXT_NODE) {
						if (gchild.getFirstChild() != null
								&& gchild.getFirstChild().getNodeType() == Node.TEXT_NODE) {
							count++;
							System.out.println("excel header"+gchild.getFirstChild().getNodeValue());
							if (gchild.getFirstChild().getNodeValue().equals("Ident")
									|| gchild.getFirstChild().getNodeValue().equals("Student Last")
									|| gchild.getFirstChild().getNodeValue().equals("Student First")
									|| gchild.getFirstChild().getNodeValue().equals("Birth Date")
									|| gchild.getFirstChild().getNodeValue().equals("gender")
									|| gchild.getFirstChild().getNodeValue().equals("Grade")
									|| gchild.getFirstChild().getNodeValue().equals("Teacher last first")
									|| gchild.getFirstChild().getNodeValue().equals("TeacherId") 
									|| gchild.getFirstChild().getNodeValue().equals("Username")
									|| gchild.getFirstChild().getNodeValue().equals("Password")
									|| gchild.getFirstChild().getNodeValue().equals("schoolc")
									|| gchild.getFirstChild().getNodeValue().equals("CT_Email")
									|| gchild.getFirstChild().getNodeValue().equals("Course")) {
								outerBreak = true;
								break;
								
							}
							if (count == 1) {
								studentIdFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));
							} else if (count == 2) {
								lastNamesFromXML.add(gchild.getFirstChild().getNodeValue());
							} else if (count == 3) {
								firstNamesFromXML.add(gchild.getFirstChild().getNodeValue());								
							} else if (count == 4) {
								birthDatesFromXML.add(gchild.getFirstChild().getNodeValue());
							} else if (count == 5) {
								gendersFromXML.add(gchild.getFirstChild().getNodeValue());				
							} else if (count == 6) {
								gradeIdsFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));	
							}
							else if(count == 7){
								teachersFromXML.add(gchild.getFirstChild().getNodeValue());
							}
							else if(count == 8){
								teacherIdsFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));
							}
							else if(count == 9){
								if (gchild.getFirstChild().getNodeValue().contains("@"))
									userNamesFromXML.add(gchild.getFirstChild().getNodeValue());
								else
									userNamesFromXML.add(gchild.getFirstChild().getNodeValue()+ "@rioschools.org");
							}
							else if(count == 10){
								passwordsFromXML.add(gchild.getFirstChild().getNodeValue());				
							}
							else if(count == 11){
								schoolAbbreviationsFromXML.add(gchild.getFirstChild().getNodeValue());
							}
							else if(count == 12){
								parentUserNamesFromXML.add(gchild.getFirstChild().getNodeValue().toLowerCase());
								tempParentUserNamesFromXML.add(gchild.getFirstChild().getNodeValue().toLowerCase());
							}
							else if(count == 13){
								classNamesFromXML.add(gchild.getFirstChild().getNodeValue());
							}
						}
					}
				}
				if(outerBreak){
					outerBreak = false;
					break;
				}
			}

		}
		System.out.println("done adding data");
		// TODO
		/**************** MANUALLY POPULATING SCHOOL TABLE IF SCHOOL DATA IS NOT PROVIDED *********************/

		// TODO MANUALLY POPULATING SCHOOL TABLE IF SCHOOL DATA IS NOT
		// PROVIDED
		List<School> schoolObjectsFromDB = schoolDAO.getSchoolList();
		List<School> schoolObjsXMLBasedOrder = new ArrayList<School>();
		Map<String, School> schoolsAbbrvObjMap = new HashMap<String, School>();
		Map<Long, School> schoolsIdObjMap = new HashMap<Long, School>();
		Set<String> schoolAbbrSET = new HashSet<String>();

		for (String schoolAbbr : schoolAbbreviationsFromXML) {
			schoolAbbrSET.add(schoolAbbr);
		}

		SchoolType schoolType = schoolTypeDAO.getSchoolType(1);
		SchoolLevel schoolLevel = schoolLevelDAO.getSchoolLevel(4);
		States state = statesDAO.getState(1);
		for (String schoolAbbr : schoolAbbrSET) {
			boolean schoolExists = false;
			for (School school : schoolObjectsFromDB) {
				if (schoolAbbr.equals(school.getSchoolAbbr())) {
					schoolsAbbrvObjMap.put(schoolAbbr, school);
					schoolsIdObjMap.put(school.getSchoolId(), school);
					schoolExists = true;
					break;
				}
			}
			if (!schoolExists) {
				School newSchool = new School();
				newSchool.setSchoolType(schoolType);
				newSchool.setSchoolLevel(schoolLevel);
				newSchool.setSchoolAbbr(schoolAbbr);
				newSchool.setSchoolName(schoolAbbr);
				newSchool.setNoOfStudents(100);
				newSchool.setCity("Vizag"); // TODO ELIMINATE TEST DATA
				newSchool.setStates(state);
				newSchool.setRegistrationDate(new java.util.Date());
				schoolDAO.saveSchool(newSchool);
				schoolsAbbrvObjMap.put(schoolAbbr, newSchool);
				schoolsIdObjMap.put(newSchool.getSchoolId(), newSchool);
			}
		}

		for (String schoolAbbr : schoolAbbreviationsFromXML) {
			if (schoolsAbbrvObjMap.get(schoolAbbr) != null) {
				schoolObjsXMLBasedOrder.add(schoolsAbbrvObjMap
						.get(schoolAbbr));
			} else {
				for (School school : schoolObjectsFromDB) {
					if (schoolAbbr.equals(school.getSchoolAbbr())) {
						schoolObjsXMLBasedOrder.add(school);
					}
				}
			}
		}

		/*************
		 * POPULATING GRADE TABLE BASED ON SCHOOL_ID AND MASTER_GRADES_ID
		 * BASED ON GRADE_NAME
		 ************/
		List<MasterGrades> masterGradesObjectsFromDB = masterGradesDAO
				.getMasterGradesList();
		List<Long> normalizedGradeIds = new ArrayList<Long>();
		List<Grade> gradesFromDB = gradesDAO.getGradesList();
		for (Long grade : gradeIdsFromXML) {
			boolean found = false;
			for (MasterGrades masterGrade : masterGradesObjectsFromDB) {
				if (masterGrade.getMasterGradesId() == grade) {
					found = true;
					break;
				}
			}
			if (found)
				normalizedGradeIds.add(grade);
			else if(grade == 0)
				normalizedGradeIds.add(new Long(13));
			else
				normalizedGradeIds.add(new Long(14));
		}

		for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
			boolean found = false;
			for (Grade g : gradesFromDB) {
				if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
						.getSchoolId()
						&& normalizedGradeIds.get(temp_i) == g
								.getMasterGrades().getMasterGradesId()) {
					found = true;
					break;
				}
			}
			if (!found) {
				Grade newGrade = new Grade();
				newGrade.setSchoolId((schoolObjsXMLBasedOrder.get(temp_i)
						.getSchoolId()));
				for (MasterGrades masterGrade : masterGradesObjectsFromDB) {
					if (normalizedGradeIds.get(temp_i).equals(
							masterGrade.getMasterGradesId()))
						newGrade.setMasterGrades(masterGrade);
				}
				newGrade.setCreateDate(new java.util.Date());
				newGrade.setChangeDate(new java.util.Date());
				newGrade.setStatus(WebKeys.LP_STATUS_ACTIVE);
				gradesDAO.saveGrades(newGrade);
				gradesFromDB.add(newGrade);
			}
		}
        
		System.out.println("grade added");
		/************** POPULATING CLASS TABLE BASED ON SCHOOL_ID AND CLASS_NAME BASED **************/
		List<StudentClass> classesFromDB = adminDAO.getAllClasses();
		Set<String> schoolClassSet = new HashSet<String>();
		for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
			schoolClassSet.add(schoolObjsXMLBasedOrder.get(temp_i)
					.getSchoolId()
					+ "@BAYLOGIC"
					+ classNamesFromXML.get(temp_i));
		}
		System.out.println("baylogic");
		for (String s : schoolClassSet) {
			System.out.println("baylogic1");
			if (s.split("@BAYLOGIC").length == 2) {
				String schoolId = s.split("@BAYLOGIC")[0];
				String className = s.split("@BAYLOGIC")[1];
				boolean found = false;
				for (StudentClass stuClass : classesFromDB) {
					if (stuClass.getClassName().equalsIgnoreCase(className)
							&& stuClass.getSchool().getSchoolId() == Long
									.parseLong(schoolId)) {
						found = true;
						break;
					}
				}
				if (!found) {
					StudentClass newClass = new StudentClass();
					newClass.setClassName(className);
					newClass.setSchool(schoolsIdObjMap.get((Long
							.parseLong(schoolId))));
					newClass.setChangeDate(new java.util.Date());
					newClass.setCreateDate(new java.util.Date());
					studentClassesDAO.saveStudentclasses(newClass);
					classesFromDB.add(newClass);
				}
			}

		}

		/***********
		 * POPULATING GRADE_CLASSES TABLE BASED ON GRADE_ID AND CLASS_ID
		 * BASED
		 ******************/
         
		System.out.println("POPULATING GRADE_CLASSES TABLE BASED ON GRADE_ID AND CLASS_ID");
		List<GradeClasses> gradeClassesFromDB = adminDAO
				.getAllGradeClasses();
		classesFromDB = adminDAO.getAllClasses();
		gradesFromDB = gradesDAO.getGradesList();
		for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
			Long gradeId = null;
			Long classId = null;
			Grade grade = null;
			StudentClass studentClass = null;
			for (Grade g : gradesFromDB) {
				if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
						.getSchoolId()
						&& normalizedGradeIds.get(temp_i) == g
								.getMasterGrades().getMasterGradesId()) {
					gradeId = g.getGradeId();
					grade = g;
					break;
				}
			}
			for (StudentClass stuClass : classesFromDB) {
				if (stuClass.getClassName().equalsIgnoreCase(
						classNamesFromXML.get(temp_i))
						&& stuClass.getSchool().getSchoolId() == schoolObjsXMLBasedOrder
								.get(temp_i).getSchoolId()) {
					classId = stuClass.getClassId();
					studentClass = stuClass;
					break;
				}
			}
			boolean found = false;
			for (GradeClasses gradeClass : gradeClassesFromDB) {
				if (gradeClass.getStudentClass().getClassId() == classId
						&& gradeClass.getGrade().getGradeId() == gradeId) {
					found = true;
					break;
				}
			}
			if (!found) {
				GradeClasses newGradeClass = new GradeClasses();
				newGradeClass.setChangeDate(new java.util.Date());
				newGradeClass.setCreateDate(new java.util.Date());
				newGradeClass.setGrade(grade);
				newGradeClass.setStudentClass(studentClass);
				newGradeClass.setStatus(WebKeys.LP_STATUS_ACTIVE);
				adminDAO.saveGradeClasses(newGradeClass);
				gradeClassesFromDB.add(newGradeClass);
			}
		}

		/*******
		 * POPULATE USER_REGISTRATION TABLE AND STUDENT TABLE BASED ON
		 * REG_ID OF USER_REGISTRATION
		 *****/
		System.out.println("POPULATE USER_REGISTRATION TABLE AND STUDENT TABLE");
		String parentPwd = userRegistrationDAO.getMD5Conversion("parent");
		List<UserRegistration> newURList = new ArrayList<UserRegistration>();
		Map<String, UserRegistration> newParentList = new HashMap<String, UserRegistration>();
		List<Student> newStuList = new ArrayList<Student>();
		List<Security> securityList = new ArrayList<Security>();
		List<Security> parentSecurityList = new ArrayList<Security>();
		SecurityQuestion secQuestion = new SecurityQuestion();
		secQuestion.setSecurityQuestionId(2);
		User userTypeStudentAbove13 = userDAO
				.getUserType(WebKeys.LP_USER_TYPE_STUDENT_ABOVE_13);
		User userTypeStudentBelow13 = userDAO
				.getUserType(WebKeys.LP_USER_TYPE_STUDENT_BELOW_13);
		User userTypeParent = userDAO
				.getUserType(WebKeys.LP_USER_TYPE_PARENT);
		@SuppressWarnings("unused")
		Map<String, Long> newUserNameIdMap = new HashMap<String, Long>();
		Map<String, Student> newUserNameStudentObjMap = new HashMap<String, Student>();
		@SuppressWarnings("unused")
		Map<String, Date> newUserNameDOBMap = new HashMap<String, Date>();
		@SuppressWarnings("unused")
		Map<String, String> newUserNameGenderMap = new HashMap<String, String>();
		User userTypeAdmin = userDAO.getUserType(WebKeys.LP_USER_TYPE_ADMIN);
		User userTypeTeacher = userDAO.getUserType(WebKeys.LP_USER_TYPE_TEACHER);
		Map<Long, Student> studentDBMap = new HashMap<Long, Student>();
		
		List<Student> studentsFromDB = studentDAO.getActiveStudents();
	
		
		List<UserRegistration> userRegistrationListFromDB = userRegistrationDAO.getUserRegistrationList(userTypeStudentAbove13);
		userRegistrationListFromDB.addAll(userRegistrationDAO.getUserRegistrationList(userTypeStudentBelow13));
		
		List<UserRegistration> parentUserRegistrationListFromDB = userRegistrationDAO.getUserRegistrationList(userTypeParent);
		parentUserRegistrationListFromDB.addAll(userRegistrationDAO.getUserRegistrationList(userTypeAdmin));
		parentUserRegistrationListFromDB.addAll(userRegistrationDAO.getUserRegistrationList(userTypeTeacher));
		
		System.out.println("add user redgistration table");
		Set<String> userNameSET = new HashSet<String>();
		Set<String> parentUserNameSET = new HashSet<String>();
		for (String userName : userNamesFromXML) {
			userNameSET.add(userName);
		}
		
		for(Student student : studentsFromDB){
			if(!studentDBMap.containsKey(student.getStudentScId()))
				studentDBMap.put(student.getStudentScId(), student);
		}
		
		int parentIndex = -1;
		/* Code for replacing blank emailids and emails which already exists in the db with student username followed by string "@rioschools.org"  */
		for (String userName : tempParentUserNamesFromXML) {
			parentIndex++;
			if(userName.equals("blank")){
				parentUserNamesFromXML.remove(parentIndex);
				parentUserNamesFromXML.add(parentIndex, userNamesFromXML.get(parentIndex).split("@")[0].concat("@rioparent.org"));
			}				
		}
		for (String userName : parentUserNamesFromXML) {
			if(!parentUserNameSET.contains(userName)){
				parentUserNameSET.add(userName);
			}
		}
		Long gradeId = null;
		Grade grade = null;
		// PARENT REGISTRATION 
		
		for (String userName : parentUserNameSET) {
			int index = parentUserNamesFromXML.indexOf(userName);
			Long studentSCID = studentIdFromXML.get(index);
			if(studentDBMap.containsKey((studentSCID)))
				continue;
			boolean found = false;
			for (UserRegistration uReg : parentUserRegistrationListFromDB) {
				/* Skip if the email is already registered as a parent*/
				if (userName.equalsIgnoreCase(uReg.getEmailId()) && uReg.getUser().getUserType().equals(WebKeys.LP_USER_TYPE_PARENT)) {
					found = true;
					break;
				}
				if (userName.equalsIgnoreCase(uReg.getEmailId()) && !uReg.getUser().getUserType().equals(WebKeys.LP_USER_TYPE_PARENT)) {
					//int index = parentUserNamesFromXML.indexOf(userName);
					parentUserNamesFromXML.remove(index);
					parentUserNamesFromXML.add(index, userNamesFromXML.get(index).split("@")[0].concat("@rioparent.org"));					
					userName = parentUserNamesFromXML.get(index);
					if (userRegistrationDAO.getActiveUserRegistration(userName).getRegId()>0) {
						found = true;
					}
					break;
				}
			}

			if (!found) {
				//int index = parentUserNamesFromXML.indexOf(userName);
				UserRegistration newParent = new UserRegistration();
				newParent.setEmailId(parentUserNamesFromXML.get(index));
				newParent.setPassword(parentPwd);
				newParent.setUser(userTypeParent);
				newParent.setStatus(WebKeys.LP_STATUS_ACTIVE);
				newParent.setSchool(schoolObjsXMLBasedOrder
						.get(index));
				newParent.setFirstName(firstNamesFromXML
						.get(index).trim());
				newParent
						.setLastName(lastNamesFromXML.get(index).trim());

				Address addressObj = new Address();
				addressObj.setAddress("600 Simon Way #");
				addressObj.setCity("Oxnard");
				addressObj.setZipcode(93036);
				addressObj.setStates(state);
				newParent.setAddress(addressObj);
				newParentList.put(parentUserNamesFromXML.get(index) , newParent);
				Security security = new Security();
				security.setUserRegistration(newParent);
				security.setSecurityQuestion(secQuestion);
				security.setVerificationCode(WebKeys.DEFAULT_VERIFICATION_CODE);
				security.setAnswer(WebKeys.DEFAULT_SECURITY_ANSWER);
				security.setStatus(WebKeys.SECURITY_STATUS_INACTIVE);
				parentSecurityList.add(security);
			}
		}
		userRegistrationDAO.saveBulkParentRegistration(parentSecurityList);
		System.out.println("parent reg end");
		//PARENT REGISTRATION ENDS//
		
		Long parentRegId = (long) 0;
		@SuppressWarnings("unused")
		UserRegistration parentUserReg = null;
		List<UserRegistration> userRegistrationsToBeUpdated = new ArrayList<UserRegistration>() ;
		for (String userName : userNameSET) {
			int index = userNamesFromXML.indexOf(userName);
			Long studentSCID = studentIdFromXML.get(index);
			boolean found = false;
			if(studentDBMap.containsKey((studentSCID)))
				continue;
			for (UserRegistration uReg : userRegistrationListFromDB) {
				//School Shifting through Bulk Registration					
				if (userName.equalsIgnoreCase(uReg.getEmailId()) && uReg.getSchool().getSchoolId() != schoolObjsXMLBasedOrder.get(index).getSchoolId() 
						&& uReg.getStatus().equals(WebKeys.ACTIVE)) {
					found = true;
					uReg.setSchool(schoolObjsXMLBasedOrder.get(index));
					userRegistrationsToBeUpdated.add(uReg);
					break;
				}
				else if(userName.equalsIgnoreCase(uReg.getEmailId()) && uReg.getStatus().equals(WebKeys.ACTIVE)){
					found = true;
					break;
				}
			}
			

			if (!found) {					
				UserRegistration newUserRegistration = new UserRegistration();
				newUserRegistration.setEmailId(userNamesFromXML.get(index));
				newUserRegistration.setPassword(userRegistrationDAO.getMD5Conversion(passwordsFromXML.get(index))); // newUserRegistration.setPassword(studentPWD);
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
				Date birthday = sdf.parse(birthDatesFromXML.get(index));
				Calendar bdayCal = Calendar.getInstance();
				bdayCal.setTime(birthday);
				Calendar todayCal = Calendar.getInstance();
				todayCal.setTime(new Date());
				if ((bdayCal.get(Calendar.YEAR) - todayCal
						.get(Calendar.YEAR)) >= 13)
					newUserRegistration.setUser(userTypeStudentAbove13);
				else
					newUserRegistration.setUser(userTypeStudentBelow13);
				newUserRegistration.setStatus(WebKeys.LP_STATUS_ACTIVE);
				newUserRegistration.setSchool(schoolObjsXMLBasedOrder
						.get(index));
				newUserRegistration.setFirstName(firstNamesFromXML
						.get(index).trim());
				newUserRegistration
						.setLastName(lastNamesFromXML.get(index).trim());

				Address addressObj = new Address();
				addressObj.setAddress("600 Simon Way #");
				addressObj.setCity("Oxnard");
				addressObj.setZipcode(93036);
				addressObj.setStates(state);
				newUserRegistration.setAddress(addressObj);
				
				if (newParentList.get(parentUserNamesFromXML.get(index)) != null) {
					parentRegId = newParentList.get(
							parentUserNamesFromXML.get(index)).getRegId();
					parentUserReg = newParentList.get(
							parentUserNamesFromXML.get(index));
				} else {
					for (UserRegistration t : parentUserRegistrationListFromDB) {
						if (t.getEmailId()
								.equalsIgnoreCase(parentUserNamesFromXML.get(index))) {
							parentRegId = t.getRegId();
							parentUserReg = t;
							break;
						}
					}
				}
				newUserRegistration.setParentRegId(parentRegId);
				newURList.add(newUserRegistration);
			
				Student newStudent = new Student();
				newStudent.setUserRegistration(newUserRegistration);
				newStudent.setDob(birthday);
				newStudent.setGender(gendersFromXML.get(index).equalsIgnoreCase("M")?WebKeys.LP_MALE:WebKeys.LP_FEMALE);
				newStudent.setChangeDate(new Date());
				newStudent.setCreateDate(new Date());
				newStudent.setGradeStatus(WebKeys.ACTIVE);
				newStudent.setStudentScId(studentIdFromXML.get(index));

				for (Grade g : gradesFromDB) {
					if (schoolObjsXMLBasedOrder.get(index).getSchoolId() == g
							.getSchoolId()
							&& normalizedGradeIds.get(index) == g
									.getMasterGrades().getMasterGradesId()) {
						gradeId = g.getGradeId();
						grade = g;
						break;
					}
				}
				newStudent.setGrade(grade);
				newStuList.add(newStudent);
				newUserNameStudentObjMap.put(newUserRegistration.getEmailId(), newStudent);

				Security security = new Security();
				security.setUserRegistration(newUserRegistration);
				security.setSecurityQuestion(secQuestion);
				security.setVerificationCode(WebKeys.DEFAULT_VERIFICATION_CODE);
				security.setAnswer(WebKeys.DEFAULT_SECURITY_ANSWER);
				security.setStatus(WebKeys.SECURITY_STATUS_INACTIVE);
				securityList.add(security);
			}
		}
		logger.info("number of new students registered :"+newURList.size());
		System.out.println("number of new students registered :"+newURList.size());
		userRegistrationDAO.saveBulkStudentRegistration(newURList,
				newStuList, securityList, userRegistrationsToBeUpdated);
		
		/***********
		 * POPULATING REGISTER_FOR_CLASS TABLE BASED ON STUDENT_ID, GRADE_ID
		 * AND CLASS_ID BASED
		 ******************/
		List<RegisterForClass> registerForClassFromDB = registerForClassDAO
				.getAllRegisterForClass();
		@SuppressWarnings("unused")
		List<Student> studentFromDB = studentDAO.getAllStudents();
		List<Teacher> teacherFromDB = caasppdao.getActiveTeachers();
		List<RegisterForClass> registerForClasses = new ArrayList<RegisterForClass>();
		Map<Long, Teacher> teacherMap = new HashMap<>();
		Map<String, RegisterForClass> registerForClassMap = new HashMap<>();
		Map<String, Teacher> teacherNameMap = new HashMap<>();
		for(Teacher teacher : teacherFromDB){
			if(!teacherMap.containsKey(teacher.getTeacherScId()))
				teacherMap.put(teacher.getTeacherScId(), teacher);
		}
		for(Teacher teacher : teacherFromDB){
			if(!teacherNameMap.containsKey(teacher.getUserRegistration().getFirstName()+"_"+teacher.getUserRegistration().getLastName()+"_"+teacher.getUserRegistration().getSchool().getSchoolAbbr()))
				teacherNameMap.put(teacher.getUserRegistration().getFirstName()+"_"+teacher.getUserRegistration().getLastName()+"_"+teacher.getUserRegistration().getSchool().getSchoolAbbr(), teacher);
		}
		for(RegisterForClass reClass : registerForClassFromDB ){
			if(!registerForClassMap.containsKey(reClass.getStudent().getStudentId()+"_"+ reClass.getGradeClasses().getGradeClassId()))
				registerForClassMap.put(reClass.getStudent().getStudentId()+"_"+ reClass.getGradeClasses().getGradeClassId(), reClass);
		}
		
		GradeLevel atGradeLevel = gradeLevelDAO.getGradeLevel(2);
		Map<String, RegisterForClass> reMap = new HashMap<String, RegisterForClass>();
		for (int temp_i = 0; temp_i < userNamesFromXML.size(); temp_i++) {
			gradeId = null;
			Long classId = null;
			Long studentId = null;
			@SuppressWarnings("unused")
			Long teacherId = null;
			Long gradeClassId = null;
			@SuppressWarnings("unused")
			Grade dbGrade =null;
			String[] teacherName = null;
			grade = null;
			Student student = null;
			Teacher teacher = null;
			@SuppressWarnings("unused")
			StudentClass studentClass = null;
			GradeClasses gradeClasses = null;
			@SuppressWarnings("unused")
			RegisterForClass regForClass = null;
			for (Grade g : gradesFromDB) {
				if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
						.getSchoolId()
						&& normalizedGradeIds.get(temp_i) == g
								.getMasterGrades().getMasterGradesId()) {
					gradeId = g.getGradeId();
					grade = g;
					break;
				}
			}
			for (StudentClass stuClass : classesFromDB) {
				if (stuClass.getClassName().equalsIgnoreCase(
						classNamesFromXML.get(temp_i))
						&& stuClass.getSchool().getSchoolId() == schoolObjsXMLBasedOrder
								.get(temp_i).getSchoolId()) {
					classId = stuClass.getClassId();
					studentClass = stuClass;
					break;
				}
			}

			boolean found = false;
			for (GradeClasses gradeClass : gradeClassesFromDB) {
				if (gradeClass.getStudentClass().getClassId() == classId
						&& gradeClass.getGrade().getGradeId() == gradeId) {
					found = true;
					gradeClassId = gradeClass.getGradeClassId();
					gradeClasses = gradeClass;
					break;
				}
			}
			if (newUserNameStudentObjMap.get(userNamesFromXML.get(temp_i)) != null) {
				studentId = newUserNameStudentObjMap.get(
						userNamesFromXML.get(temp_i)).getStudentId();
				student = newUserNameStudentObjMap.get(userNamesFromXML
						.get(temp_i));
			} else {
				/*for (Student t : studentFromDB) {
					if (t.getUserRegistration().getEmailId()
							.equals(userNamesFromXML.get(temp_i))) {
						studentId = t.getStudentId();
						student = t;
						break;
					}
				}*/
				student = studentDBMap.get(studentIdFromXML.get(temp_i));
				if(student == null) {
					System.out.println("student is not present");
					continue;
				}
				studentId = student.getStudentId();
				
				
			}

			
			/*for (Teacher t : teacherFromDB) {
				if(t.getTeacherScId()!=null){
					if(t.getTeacherScId() == teacherIdsFromXML.get(temp_i) && t.getUserRegistration().getSchool().getSchoolAbbr().equals(schoolAbbreviationsFromXML.get(temp_i))){
						teacherId = t.getTeacherId();
						teacher = t;
						break;
					}
				}
				else{
					teacherName = teachersFromXML.get(temp_i).split(",");
					if (t.getUserRegistration().getLastName()
							.equals(teacherName[0].trim())
							&& t.getUserRegistration().getFirstName().equals(teacherName[1].trim())
							&& t.getUserRegistration().getUser().getUserType().equals(WebKeys.LP_USER_TYPE_TEACHER)
							&& t.getUserRegistration().getSchool().getSchoolAbbr().equals(schoolAbbreviationsFromXML.get(temp_i))) {
						teacherId = t.getTeacherId();
						teacher = t;
						break;
					}
				}
			}*/
			
			teacher = teacherMap.get(teacherIdsFromXML.get(temp_i));
			if(teacher == null){
				teacherName = teachersFromXML.get(temp_i).split(",");
				teacher = teacherNameMap.get(teacherName[0].trim()+"_"+teacherName[1].trim()+"_"+schoolAbbreviationsFromXML.get(temp_i));
				
			}
			if(teacher != null){
				teacherId = teacher.getTeacherId();
			}
			
			found = false;				
			/*for (RegisterForClass regForClassObj : registerForClassFromDB) {
				if (regForClassObj.getGradeClasses().getGradeClassId() == gradeClassId
						&& regForClassObj.getStudent().getStudentId() == studentId){
					found = true;
					break;
				}
			}	*/
			if(registerForClassMap.containsKey(studentId + "_"+ gradeClassId)){
				found = true;
			}
			// Student Grade Promotion
			if(student.getGrade().getGradeId() != grade.getGradeId()) { // Checking student current grade with grade provided in xml
				//registerForClassDAO.expireAllClasses(student.getStudentId());				
				student.setGrade(grade);
				student.setChangeDate(new Date());
				School school = new School();
				school.setSchoolId(grade.getSchoolId());
				student.getUserRegistration().setSchool(school);
				//studentDAO.SaveStudent(student);
				logger.info("promoted a student"+student.getStudentId());
			}
			if (!found ) {//&& student.getGrade().getGradeId() == grade.getGradeId()
				if(!reMap.containsKey(student.getStudentId() +"_"+ gradeClassId)){
					RegisterForClass newRegisterForClass = new RegisterForClass();
					RegisterForClassId newRegisterForClassId = new RegisterForClassId();
					newRegisterForClassId.setGradeClassId(gradeClassId);
					newRegisterForClassId.setStudentId(studentId);
					newRegisterForClass.setStudent(student);
					newRegisterForClass.setGradeClasses(gradeClasses);
					newRegisterForClass.setId(newRegisterForClassId);
					newRegisterForClass.setClassStatus_1(WebKeys.ALIVE);
					newRegisterForClass.setStatus(WebKeys.REGISTER_FOR_CLASS_STATUS);
					newRegisterForClass.setTeacher(teacher);
					newRegisterForClass.setGradeLevel(atGradeLevel);
					newRegisterForClass.setCreateDate(new Date());
					newRegisterForClass.setChangeDate(new Date());
					reMap.put(student.getStudentId() +"_"+ gradeClassId, newRegisterForClass);
					logger.info("student ID "+ student.getUserRegistration().getFirstName() + " "+ student.getUserRegistration().getLastName()+
							" grade Id "+ grade.getMasterGrades().getMasterGradesId()+ " class name = "+gradeClasses.getStudentClass().getClassName());
					registerForClasses.add(newRegisterForClass);
				}
				
			}
		}
			registerForClassDAO.saveRegisterForClass(registerForClasses);
			logger.info("count "+ registerForClasses.size());
			return true;
		}else{ 
			
			//  StudentId	| SchoolName | Course | Teacher | Grade 
			logger.info("Class Rosters upload on " + new Date());
				boolean outerBreak = false;
				ArrayList<Long> studentIdFromXML = new ArrayList<Long>();
				ArrayList<String> schoolAbbreviationsFromXML = new ArrayList<String>();
				ArrayList<String> classNamesFromXML = new ArrayList<String>();
				ArrayList<Long> teacherIdFromXML = new ArrayList<Long>();
				ArrayList<Long> gradeIdsFromXML = new ArrayList<Long>();

				
				
				DocumentBuilderFactory Factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = Factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				// creating an XPathFactory:
				XPathFactory factory = XPathFactory.newInstance();
				// using this factory to create an XPath object:
				XPath xpath = factory.newXPath();
				// XPath Query for showing all nodes value
				XPathExpression expr = xpath.compile("//" + "Table" + "/*");
				Object result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes = (NodeList) result;

				for (int i = 0; i < nodes.getLength(); i++) {
					Element el = (Element) nodes.item(i);
					NodeList children = el.getChildNodes();
					int count = 0;
					for (int k = 0; k < children.getLength(); k++) {
						Node child = children.item(k);
						NodeList gchildren = child.getChildNodes();
						for (int l = 0; l < gchildren.getLength(); l++) {
							Node gchild = gchildren.item(l);
							if (gchild.getNodeType() != Node.TEXT_NODE) {
								if (gchild.getFirstChild() != null
										&& gchild.getFirstChild().getNodeType() == Node.TEXT_NODE) {
									count++;
									if (gchild.getFirstChild().getNodeValue().equals("StudentId")
											|| gchild.getFirstChild().getNodeValue().equals("SchoolName")
											|| gchild.getFirstChild().getNodeValue().equals("Course")
											|| gchild.getFirstChild().getNodeValue().equals("Teacher")
											|| gchild.getFirstChild().getNodeValue().equals("Grade")) {
										outerBreak = true;
										break;
										
									}
									if (count == 1) {
										studentIdFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));
									} else if (count == 2) {
										schoolAbbreviationsFromXML.add(gchild.getFirstChild().getNodeValue());
									} else if (count == 3) {
										classNamesFromXML.add(gchild.getFirstChild().getNodeValue());								
									} else if (count == 4) {
										teacherIdFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));
									} else if (count == 5) {
										gradeIdsFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));	
									}
								}
							}
						}
						if(outerBreak){
							outerBreak = false;
							break;
						}
					}

				}
				// TODO
				/**************** MANUALLY POPULATING SCHOOL TABLE IF SCHOOL DATA IS NOT PROVIDED *********************/

				// TODO MANUALLY POPULATING SCHOOL TABLE IF SCHOOL DATA IS NOT
				// PROVIDED
				List<School> schoolObjectsFromDB = schoolDAO.getSchoolList();
				List<School> schoolObjsXMLBasedOrder = new ArrayList<School>();
				Map<String, School> schoolsAbbrvObjMap = new HashMap<String, School>();
				Map<Long, School> schoolsIdObjMap = new HashMap<Long, School>();
				Set<String> schoolAbbrSET = new HashSet<String>();

				for (String schoolAbbr : schoolAbbreviationsFromXML) {
					schoolAbbrSET.add(schoolAbbr);
				}

				for (String schoolAbbr : schoolAbbrSET) {
					for (School school : schoolObjectsFromDB) {
						if (schoolAbbr.equals(school.getSchoolAbbr())) {
							schoolsAbbrvObjMap.put(schoolAbbr, school);
							schoolsIdObjMap.put(school.getSchoolId(), school);
							break;
						}
					}
				}

				for (String schoolAbbr : schoolAbbreviationsFromXML) {
					if (schoolsAbbrvObjMap.get(schoolAbbr) != null) {
						schoolObjsXMLBasedOrder.add(schoolsAbbrvObjMap
								.get(schoolAbbr));
					} else {
						for (School school : schoolObjectsFromDB) {
							if (schoolAbbr.equals(school.getSchoolAbbr())) {
								schoolObjsXMLBasedOrder.add(school);
							}
						}
					}
				}

				/*************
				 * POPULATING GRADE TABLE BASED ON SCHOOL_ID AND MASTER_GRADES_ID
				 * BASED ON GRADE_NAME
				 ************/
				List<MasterGrades> masterGradesObjectsFromDB = masterGradesDAO
						.getMasterGradesList();
				List<Long> normalizedGradeIds = new ArrayList<Long>();
				List<Grade> gradesFromDB = gradesDAO.getGradesList();
				for (Long grade : gradeIdsFromXML) {
					boolean found = false;
					for (MasterGrades masterGrade : masterGradesObjectsFromDB) {
						if (masterGrade.getMasterGradesId() == grade) {
							found = true;
							break;
						}
					}
					if (found)
						normalizedGradeIds.add(grade);
					else if(grade == 0)
						normalizedGradeIds.add(new Long(13));
					else
						normalizedGradeIds.add(new Long(14));
				}

				for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
					boolean found = false;
					for (Grade g : gradesFromDB) {
						if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
								.getSchoolId()
								&& normalizedGradeIds.get(temp_i) == g
										.getMasterGrades().getMasterGradesId()) {
							found = true;
							break;
						}
					}
					if (!found) {
						Grade newGrade = new Grade();
						newGrade.setSchoolId((schoolObjsXMLBasedOrder.get(temp_i)
								.getSchoolId()));
						for (MasterGrades masterGrade : masterGradesObjectsFromDB) {
							if (normalizedGradeIds.get(temp_i).equals(
									masterGrade.getMasterGradesId()))
								newGrade.setMasterGrades(masterGrade);
						}
						newGrade.setCreateDate(new java.util.Date());
						newGrade.setChangeDate(new java.util.Date());
						newGrade.setStatus(WebKeys.LP_STATUS_ACTIVE);
						gradesDAO.saveGrades(newGrade);
						gradesFromDB.add(newGrade);
					}
				}

				/************** POPULATING CLASS TABLE BASED ON SCHOOL_ID AND CLASS_NAME BASED **************/
				List<StudentClass> classesFromDB = adminDAO.getAllClasses();
				Set<String> schoolClassSet = new HashSet<String>();
				for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
					schoolClassSet.add(schoolObjsXMLBasedOrder.get(temp_i)
							.getSchoolId()
							+ "@BAYLOGIC"
							+ classNamesFromXML.get(temp_i));
				}
				for (String s : schoolClassSet) {
					if (s.split("@BAYLOGIC").length == 2) {
						String schoolId = s.split("@BAYLOGIC")[0];
						String className = s.split("@BAYLOGIC")[1];
						boolean found = false;
						for (StudentClass stuClass : classesFromDB) {
							if (stuClass.getClassName().equalsIgnoreCase(className)
									&& stuClass.getSchool().getSchoolId() == Long
											.parseLong(schoolId)) {
								found = true;
								break;
							}
						}
						if (!found) {
							StudentClass newClass = new StudentClass();
							newClass.setClassName(className);
							newClass.setSchool(schoolsIdObjMap.get((Long
									.parseLong(schoolId))));
							newClass.setChangeDate(new java.util.Date());
							newClass.setCreateDate(new java.util.Date());
							studentClassesDAO.saveStudentclasses(newClass);
							classesFromDB.add(newClass);
						}
					}

				}

				/***********
				 * POPULATING GRADE_CLASSES TABLE BASED ON GRADE_ID AND CLASS_ID
				 * BASED
				 ******************/

				List<GradeClasses> gradeClassesFromDB = adminDAO
						.getAllGradeClasses();
				classesFromDB = adminDAO.getAllClasses();
				gradesFromDB = gradesDAO.getGradesList();
				for (int temp_i = 0; temp_i < schoolObjsXMLBasedOrder.size(); temp_i++) {
					Long gradeId = null;
					Long classId = null;
					Grade grade = null;
					StudentClass studentClass = null;
					for (Grade g : gradesFromDB) {
						if (schoolObjsXMLBasedOrder.get(temp_i).getSchoolId() == g
								.getSchoolId()
								&& normalizedGradeIds.get(temp_i) == g
										.getMasterGrades().getMasterGradesId()) {
							gradeId = g.getGradeId();
							grade = g;
							break;
						}
					}
					for (StudentClass stuClass : classesFromDB) {
						if (stuClass.getClassName().equalsIgnoreCase(
								classNamesFromXML.get(temp_i))
								&& stuClass.getSchool().getSchoolId() == schoolObjsXMLBasedOrder
										.get(temp_i).getSchoolId()) {
							classId = stuClass.getClassId();
							studentClass = stuClass;
							break;
						}
					}
					boolean found = false;
					for (GradeClasses gradeClass : gradeClassesFromDB) {
						if (gradeClass.getStudentClass().getClassId() == classId
								&& gradeClass.getGrade().getGradeId() == gradeId) {
							found = true;
							break;
						}
					}
					if (!found) {
						GradeClasses newGradeClass = new GradeClasses();
						newGradeClass.setChangeDate(new java.util.Date());
						newGradeClass.setCreateDate(new java.util.Date());
						newGradeClass.setGrade(grade);
						newGradeClass.setStudentClass(studentClass);
						newGradeClass.setStatus(WebKeys.LP_STATUS_ACTIVE);
						adminDAO.saveGradeClasses(newGradeClass);
						gradeClassesFromDB.add(newGradeClass);
					}
				}

				
				/***********
				 * POPULATING REGISTER_FOR_CLASS TABLE BASED ON STUDENT_ID, GRADE_ID
				 * AND CLASS_ID BASED
				 ******************/
				
				List<Student> studentFromDB = studentDAO.getAllStudents();
				List<Teacher> teacherFromDB = caasppdao.getActiveTeachers();
				List<RegisterForClass> registerForClasses = new ArrayList<RegisterForClass>();
				GradeLevel atGradeLevel = gradeLevelDAO.getGradeLevel(2);
				
				
				Map<Long, Student> studentMap = new HashMap<Long, Student>();
				Map<Long, Teacher> teacherMap = new HashMap<Long, Teacher>();
				Map<String, School> schoolMap = new HashMap<>();
				Map<String, GradeClasses> gradeClassesMap = new HashMap<>();
				Map<String, Grade> gradesMap = new HashMap<>();
				Map<String, RegisterForClass> regMap = new HashMap<>();

				for(Student student : studentFromDB){
					if(!studentMap.containsKey(student.getStudentScId()))
						studentMap.put(student.getStudentScId(), student);
				}
				
				for(Teacher teacher : teacherFromDB){
					if(!teacherMap.containsKey(teacher.getTeacherScId()))
						teacherMap.put(teacher.getTeacherScId(), teacher);
				}
				
				for(School school : schoolObjectsFromDB){
					if(!schoolMap.containsKey(school.getSchoolAbbr()))
						schoolMap.put(school.getSchoolAbbr(), school);
				}
				
				for(GradeClasses gradeClass : gradeClassesFromDB){
					if(!gradeClassesMap.containsKey(gradeClass.getStudentClass().getSchool().getSchoolId() + "_" +
							gradeClass.getGrade().getMasterGrades().getMasterGradesId() +"_" + gradeClass.getStudentClass().getClassName()))
						gradeClassesMap.put(gradeClass.getStudentClass().getSchool().getSchoolId() + "_" + 
							gradeClass.getGrade().getMasterGrades().getMasterGradesId() +"_" + gradeClass.getStudentClass().getClassName(), gradeClass);
				}
				
				for(Grade grade : gradesFromDB){
					if(!gradesMap.containsKey(grade.getMasterGrades().getMasterGradesId() +"_" + grade.getSchoolId()))
						gradesMap.put(grade.getMasterGrades().getMasterGradesId() +"_" + grade.getSchoolId(), grade);
				}
				

				List<RegisterForClass> registerForClassFromDB = registerForClassDAO
						.getAllRegisterForClass();
							Map<String, RegisterForClass> registerForClassMap = new HashMap<>();
							
							for(RegisterForClass reClass : registerForClassFromDB ){
					if(!registerForClassMap.containsKey(reClass.getStudent().getStudentId()+"_"+ reClass.getGradeClasses().getGradeClassId()))
						registerForClassMap.put(reClass.getStudent().getStudentId()+"_"+ reClass.getGradeClasses().getGradeClassId(), reClass);
				}
				/*gradeClassesFromDB classesFromDB masterGradesObjectsFromDB schoolObjectsFromDB*/

				//studentIdFromXML schoolAbbreviationsFromXML classNamesFromXML teacherIdFromXML gradeIdsFromXML
				for (int temp_i = 0; temp_i < studentIdFromXML.size(); temp_i++) {
					Student student = studentMap.get(studentIdFromXML.get(temp_i));
					Grade grade = null;
					School school = null;
					UserRegistration userReg = null;
					GradeClasses gradeClass = null;
					Teacher teacher = null;
					if(student != null){
						logger.info("student details" + studentIdFromXML.get(temp_i) + " and students LP Id : "+ student.getStudentId());
						school = schoolMap.get(schoolAbbreviationsFromXML.get(temp_i));
						grade = gradesMap.get(gradeIdsFromXML.get(temp_i) + "_" + school.getSchoolId());
						if(grade == null || school == null) {
							logger.info(studentIdFromXML.get(temp_i) +" registration failed due to grade or school");
							continue;
						}
						userReg = student.getUserRegistration();	

						gradeClass = gradeClassesMap.get(school.getSchoolId()+"_"+grade.getMasterGrades().getMasterGradesId() +"_"+classNamesFromXML.get(temp_i));
						
						teacher = teacherMap.get(teacherIdFromXML.get(temp_i));
						if(gradeClass == null || teacher == null) {
							logger.info(studentIdFromXML.get(temp_i) +" registration failed due to gradeClass or teacher");
							continue;
						}
						if(userReg.getSchool().getSchoolId() != school.getSchoolId()){
							userReg.setSchool(school);
							student.setUserRegistration(userReg);
						}
						if(!registerForClassMap.containsKey(student.getStudentId() + "_"+ gradeClass.getGradeClassId())) { 
							logger.info("Current Grade : "+student.getGrade().getMasterGrades().getMasterGradesId()+ " XML Grade : "+grade.getMasterGrades().getMasterGradesId());
							student.setGrade(grade);
							student.setChangeDate(new Date());
							if(!regMap.containsKey(student.getStudentId()+"_"+gradeClass.getGradeClassId())){
								RegisterForClass newRegisterForClass = new RegisterForClass();
								RegisterForClassId newRegisterForClassId = new RegisterForClassId();
								newRegisterForClassId.setGradeClassId(gradeClass.getGradeClassId());
								newRegisterForClassId.setStudentId(student.getStudentId());
								newRegisterForClass.setStudent(student);
								newRegisterForClass.setGradeClasses(gradeClass);
								newRegisterForClass.setId(newRegisterForClassId);
								newRegisterForClass.setClassStatus_1(WebKeys.ALIVE);
								newRegisterForClass.setStatus(WebKeys.REGISTER_FOR_CLASS_STATUS);
								newRegisterForClass.setTeacher(teacher);
								newRegisterForClass.setGradeLevel(atGradeLevel);
								newRegisterForClass.setCreateDate(new Date());
								newRegisterForClass.setChangeDate(new Date());
								regMap.put(student.getStudentId()+"_"+gradeClass.getGradeClassId(), newRegisterForClass);
								logger.info("student ID "+ student.getUserRegistration().getFirstName() + " "+ student.getUserRegistration().getLastName()+
										" grade Id "+ grade.getMasterGrades().getMasterGradesId()+ " class name = "+gradeClass.getStudentClass().getClassName() + "teacher firstname  "
										+teacher.getUserRegistration().getFirstName() +" and last name "+ teacher.getUserRegistration().getLastName());
								registerForClasses.add(newRegisterForClass);
							}
						}
					}
				}
				logger.info("Total students uploaded :"+ studentIdFromXML.size());
				logger.info("students updated rosters :"+ registerForClasses.size());
				registerForClassDAO.uploadClassRosters(registerForClasses);
				return true;
			}
	}	
	public long massScheduleStudents(){
		List<ClassStatus> scheduledClasses = registerForClassDAO.getScheduledClasses();
		List<RegisterForClass> studentsTobeScheduled = registerForClassDAO.getNewStudents();
		Map<String, ClassStatus> scheduledClassesMap = new HashMap<>();
		List<RegisterForClass> ObjectsToBeSaved = new ArrayList<>();
		
		for(ClassStatus classStatus : scheduledClasses){
			if(!scheduledClassesMap.containsKey(classStatus.getTeacher().getTeacherId() +"_"+ classStatus.getSection().getGradeClasses().getGradeClassId()))
				scheduledClassesMap.put(classStatus.getTeacher().getTeacherId() +"_"+ classStatus.getSection().getGradeClasses().getGradeClassId(), classStatus);
		}
		for(RegisterForClass reClass : studentsTobeScheduled){
			ClassStatus classStatus = scheduledClassesMap.get(reClass.getTeacher().getTeacherId() +"_"+ reClass.getGradeClasses().getGradeClassId());
			if(classStatus!=null){
				reClass.setClassStatus(classStatus);
				reClass.setStatus(WebKeys.ACCEPTED);
				reClass.setSection(classStatus.getSection());
				reClass.setChangeDate(new Date());
				ObjectsToBeSaved.add(reClass);
			}
		}
		registerForClassDAO.saveRegisterForClasses(ObjectsToBeSaved);				
		return ObjectsToBeSaved.size();
	}
	
	@Override
	public void updateStudentGradeAndSchool(InputStream xmlFile) throws Exception{ 
		
		//  StudentId | SchoolName | Grade 
		logger.info("Class Rosters upload on " + new Date());
			boolean outerBreak = false;
			ArrayList<Long> studentIdFromXML = new ArrayList<Long>();
			ArrayList<String> schoolAbbreviationsFromXML = new ArrayList<String>();
			ArrayList<Long> gradeIdsFromXML = new ArrayList<Long>();
			
			DocumentBuilderFactory Factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = Factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			// creating an XPathFactory:
			XPathFactory factory = XPathFactory.newInstance();
			// using this factory to create an XPath object:
			XPath xpath = factory.newXPath();
			// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile("//" + "Table" + "/*");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			for (int i = 0; i < nodes.getLength(); i++) {
				Element el = (Element) nodes.item(i);
				NodeList children = el.getChildNodes();
				int count = 0;
				for (int k = 0; k < children.getLength(); k++) {
					Node child = children.item(k);
					NodeList gchildren = child.getChildNodes();
					for (int l = 0; l < gchildren.getLength(); l++) {
						Node gchild = gchildren.item(l);
						if (gchild.getNodeType() != Node.TEXT_NODE) {
							if (gchild.getFirstChild() != null
									&& gchild.getFirstChild().getNodeType() == Node.TEXT_NODE) {
								count++;
								if (gchild.getFirstChild().getNodeValue().equals("StudentId")
										|| gchild.getFirstChild().getNodeValue().equals("SchoolName")
										|| gchild.getFirstChild().getNodeValue().equals("Grade")) {
									outerBreak = true;
									break;
									
								}
								if (count == 1) {
									studentIdFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));
								} else if (count == 2) {
									schoolAbbreviationsFromXML.add(gchild.getFirstChild().getNodeValue());
								} else if (count == 3) {
									gradeIdsFromXML.add(Long.parseLong(gchild.getFirstChild().getNodeValue()));	
								}
							}
						}
					}
					if(outerBreak){
						outerBreak = false;
						break;
					}
				}

			}
			// TODO
			List<School> schoolObjectsFromDB = schoolDAO.getSchoolList();
			List<Grade> gradesFromDB = gradesDAO.getGradesList();
			List<Student> studentFromDB = studentDAO.getAllStudents();
		
			Map<Long, Student> studentMap = new HashMap<Long, Student>();
			Map<String, School> schoolMap = new HashMap<>();
			Map<String, Grade> gradesMap = new HashMap<>();
			
			for(Student student : studentFromDB){
				if(!studentMap.containsKey(student.getStudentScId()))
					studentMap.put(student.getStudentScId(), student);
			}
	
			for(School school : schoolObjectsFromDB){
				if(!schoolMap.containsKey(school.getSchoolAbbr()))
					schoolMap.put(school.getSchoolAbbr(), school);
			}	
			
			for(Grade grade : gradesFromDB){
				if(!gradesMap.containsKey(grade.getMasterGrades().getMasterGradesId() +"_" + grade.getSchoolId()))
					gradesMap.put(grade.getMasterGrades().getMasterGradesId() +"_" + grade.getSchoolId(), grade);
			}
			int changedGradeCount =0, changedSchoolCount=0, unregisteredCount=0;
			for (int temp_i = 0; temp_i < studentIdFromXML.size(); temp_i++) {
				Student student = studentMap.get(studentIdFromXML.get(temp_i));
				Grade grade = null;
				School school = null;
				UserRegistration userReg = null;
				if(student != null){
					school = schoolMap.get(schoolAbbreviationsFromXML.get(temp_i));
					grade = gradesMap.get(gradeIdsFromXML.get(temp_i) + "_" + school.getSchoolId());
					userReg = student.getUserRegistration();
					
					if(userReg.getSchool().getSchoolId() != school.getSchoolId()){
						changedSchoolCount++;
						userReg.setSchool(school);
						student.setUserRegistration(userReg);
					}
					if(student.getGrade().getMasterGrades().getMasterGradesId() != grade.getMasterGrades().getMasterGradesId()){
						changedGradeCount++;
						student.setGrade(grade);
					}
				}
				else{
					unregisteredCount++;
				}
			}
			studentDAO.updateStudentGradeAndSchool(studentFromDB);
			logger.info("changedGradeCount = "+ changedGradeCount);
			logger.info("changedSchoolCount = "+ changedSchoolCount);
			logger.info("unregisteredCount = "+ unregisteredCount);
		}
}
