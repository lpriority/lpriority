package com.lp.admin.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AdminDAO;
import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.dao.GradesDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.GradeLevel;
import com.lp.model.HomeroomClasses;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.SchoolSchedule;
import com.lp.model.Section;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.student.dao.RegisterForClassDAO;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "adminSchedulerService1")
public class AdminSchdulerServiceImpl implements AdminSchedulerService {

	static final Logger logger = Logger.getLogger(AdminSchdulerServiceImpl.class);
	
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private GradesDAO gradesdao;
	@Autowired
	private AdminSchedulerDAO adminSchedulerdao;
	@Autowired
	private HttpSession session;
	@Autowired
	private TeacherDAO teacherDAO;
	@Autowired
	private RegisterForClassDAO registerForClassDAO;	
	@Autowired
	private AddStudentsToClassServiceImpl addStudentsToClassServiceImpl;

	@Override
	public boolean checkPeriodsBySchoolId(School school) {
		boolean flag = false;
		try {
			flag = adminSchedulerdao.checkPeriodsBySchoolId(school);
		} catch (Exception e) {
			logger.error("Error in checkPeriodsBySchoolId() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public boolean deletePeriods(School school) {
		boolean flag = false;
		try {
			return adminSchedulerdao.deletePeriods(school);
		} catch (Exception e) {
			logger.error("Error in deletePeriods() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public boolean makeClassesExpired(School school) {
		boolean flag = false;
		try {
			return adminSchedulerdao.makeClassesExpired(school);
		} catch (Exception e) {
			logger.error("Error in makeClassesExpired() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public boolean addSchoolSchedule(SchoolSchedule schoolschedule) {
		boolean flag = false;
		try {
			return adminSchedulerdao.addSchoolSchedule(schoolschedule);
		} catch (Exception e) {
			logger.error("Error in addSchoolSchedule() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public boolean updateSchoolSchedule(SchoolSchedule schoolschedule) {
		boolean flag = false;
		try {
			return adminSchedulerdao.updateSchoolSchedule(schoolschedule);
		} catch (Exception e) {
			logger.error("Error in updateSchoolSchedule() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public SchoolSchedule getSchoolSchedule(School school) {
		SchoolSchedule schoolSchedule = null;
		try {
			schoolSchedule = adminSchedulerdao.getSchoolSchedule(school);
			if(schoolSchedule.getSchoolStartTime() != null){
				String[] schoolStartTime = schoolSchedule.getSchoolStartTime()
						.split(":");
				String[] schoolEndTime = schoolSchedule.getSchoolEndTime().split(
						":");
	
				if (Long.valueOf(schoolStartTime[0]) > 12) {
					schoolSchedule.setDayStartTime(Long.valueOf(schoolStartTime[0]) - 12);
					schoolSchedule.setDayStartTimeMeridian(WebKeys.TIME_MERIDIAN_PM);
				}
				else if (Long.valueOf(schoolStartTime[0]) == 12) {
					schoolSchedule.setDayStartTime(Long.valueOf(12));
					schoolSchedule.setDayStartTimeMeridian(WebKeys.TIME_MERIDIAN_PM);
				}
				else {
					schoolSchedule
							.setDayStartTime(Long.valueOf(schoolStartTime[0]));
					schoolSchedule
							.setDayStartTimeMeridian(WebKeys.TIME_MERIDIAN_AM);
				}
				schoolSchedule.setDayStartTimeMin(Long.valueOf(schoolStartTime[1]));
	
				if (Long.valueOf(schoolEndTime[0]) > 12) {
					schoolSchedule.setDayEndTime(Long.valueOf(schoolEndTime[0]) - 12);
					schoolSchedule.setDayEndTimeMeridian(WebKeys.TIME_MERIDIAN_PM);
				}else if (Long.valueOf(schoolEndTime[0]) == 12) {
					schoolSchedule.setDayEndTime(Long.valueOf(12));
					schoolSchedule.setDayEndTimeMeridian(WebKeys.TIME_MERIDIAN_PM);
				} 	else {
					schoolSchedule.setDayEndTime(Long.valueOf(schoolEndTime[0]));
					schoolSchedule.setDayEndTimeMeridian(WebKeys.TIME_MERIDIAN_AM);
				}
				schoolSchedule.setDayEndTimeMin(Long.valueOf(schoolEndTime[1]));
			}
		} catch (Exception e) {
			logger.error("Error in getSchoolSchedule() of AdminSchedulerServiceImpl"
					+ e);
		}
		return schoolSchedule;

	}

	@Override
	public boolean checkSchoolSchedule(School school) {
		boolean flag = false;
		try {
			return adminSchedulerdao.checkSchoolSchedule(school);
		} catch (Exception e) {
			logger.error("Error in checkSchoolSchedule() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;

	}

	@Override
	public List<TeacherSubjects> getTeacherSubjects(School school) {
		List<TeacherSubjects> list = new ArrayList<TeacherSubjects>();
		try {
			list = adminSchedulerdao.getTeacherSubjects(school);
		} catch (Exception e) {
			logger.error("Error in getTeacherSubjects() of AdminSchedulerServiceImpl"
					+ e);
		}
		return list;
	}

	@Override
	public List<SchoolDays> getSchoolDays(School school) {
		List<SchoolDays> list = new ArrayList<SchoolDays>();
		try {

			list = adminSchedulerdao.getSchoolDays(school);
		} catch (Exception e) {
			logger.error("Error in getSchoolDays() of AdminSchedulerServiceImpl"
					+ e);
		}
		return list;
	}

	@Override
	public long getClassIdbyClassName(School school, String className) {
		long classId = 0;
		try {
			classId = adminSchedulerdao
					.getClassIdbyClassName(school, className);
		} catch (Exception e) {
			logger.error("Error in getClassIdbyClassName() of AdminSchedulerServiceImpl"
					+ e);
		}
		return classId;
	}

	@Override
	public boolean checkHomeSectionAvailabilityforTeacher(long sectionId) {

		boolean flag = false;
		try {
			flag = adminSchedulerdao
					.checkHomeSectionAvailabilityforTeacher(sectionId);
		} catch (Exception e) {
			logger.error("Error in checkHomeSectionAvailabilityforTeacher() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;

	}

	@Override
	public boolean checkTeacherAvailabilityforHomeRoom(long teacherId) {
		boolean flag = false;
		try {
			flag = adminSchedulerdao
					.checkTeacherAvailabilityforHomeRoom(teacherId);
		} catch (Exception e) {
			logger.error("Error in checkTeacherAvailabilityforHomeRoom() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public long createHomeRooms(Teacher teacher, Grade grade, Section section) {
		long cs_id = -1;
		try {
			School school = userRegistrationDAO.getUserRegistration(teacher.getUserRegistration().getRegId()).getSchool();
			Periods period = adminSchedulerdao.getHomeRoomPeriod(school, grade.getGradeId());
			HomeroomClasses homeroomclass = new HomeroomClasses();
			homeroomclass.setPeriods(period);
			homeroomclass.setSection(section);
			homeroomclass.setTeacher(teacher);
			ArrayList studentCount = new ArrayList();
			int classStrength = 0;
			String genderEquity = "";
			classStrength = school.getClassStrength();
			genderEquity = school.getGenderEquity();
			List<Student> students = adminSchedulerdao.getStudentsBySchoolId(grade.getGradeId());
			List<Student> homeRoomStudents = new ArrayList<Student>();
			if (students.size() == 0) {
				return 0;
			}
			int noOfBoys = 0, noOfGirls = 0;
			studentCount = adminSchedulerdao.getStudentCountfromChild(section.getSectionId());
			if (!studentCount.isEmpty()) {
				noOfBoys = Integer.parseInt(studentCount.get(0).toString());
				noOfGirls = Integer.parseInt(studentCount.get(1).toString());
			}
			for (Student student : students) {
				long regId = student.getUserRegistration().getRegId();				
				if ((noOfBoys + noOfGirls) >= classStrength) { //Checking if the class is already filled
					break;
				}
				if (genderEquity!=null && genderEquity.equalsIgnoreCase("yes") && classStrength>0) { //Checking gender equity
					if (classStrength % 2 == 1) {
						if (classStrength / 2 + 1 == noOfBoys
								&& adminSchedulerdao.getStudentGenderByRegId(
										regId).equalsIgnoreCase(WebKeys.LP_MALE)) {
							continue;
						} else if (classStrength / 2 + 1 == noOfGirls
								&& adminSchedulerdao.getStudentGenderByRegId(
										regId).equalsIgnoreCase(WebKeys.LP_FEMALE)) {
							continue;
						}
					} else {
						if (classStrength / 2 == noOfBoys
								&& adminSchedulerdao.getStudentGenderByRegId(
										regId).equalsIgnoreCase(WebKeys.LP_MALE)) {
							continue;
						}
						if (classStrength / 2 == noOfGirls
								&& adminSchedulerdao.getStudentGenderByRegId(
										regId).equalsIgnoreCase(WebKeys.LP_FEMALE)) {
							continue;
						}
					}
				}
				if(student.getGender().equals(WebKeys.LP_FEMALE)){ //Incrementing the student count by gender for the next iteration
					noOfGirls++;
				}
				else{
					noOfBoys++;
				}
				
				if (homeRoomStudents.size() < classStrength) {
					student.setHomeroomClasses(homeroomclass);
					homeRoomStudents.add(student);
				} else {
					break;
				}
			}
			adminSchedulerdao.SetHomeroomClassForTeacher(homeroomclass,
					homeRoomStudents);
		} catch (Exception e) {
			logger.error("Error in createHomeRooms() of AdminSchedulerServiceImpl"
					+ e);
		}
		return cs_id;

	}

	@Override
	public void saveSchoolSchedule(SchoolSchedule schoolSchedule) {
		try {
			adminSchedulerdao.saveSchoolSchedule(schoolSchedule);
		} catch (Exception e) {
			logger.error("Error in saveSchoolSchedule() of AdminSchedulerServiceImpl"
					+ e);
		}
	}

	@Override
	public List<Periods> getSchoolPeriods(long gradeId) {
		List<Periods> list = new ArrayList<Periods>();
		try {
			list = adminSchedulerdao.getSchoolPeriods(gradeId);
		} catch (Exception e) {
			logger.error("Error in getSchoolPeriods() of AdminSchedulerServiceImpl"
					+ e);
		}
		return list;
	}

	@Override
	public boolean checkSectionAvailabilityforTeacher(long sectionId,
			long teacherId) {
		boolean flag = false;
		try {
			return adminSchedulerdao.checkSectionAvailabilityforTeacher(
					sectionId, teacherId);
		} catch (Exception e) {
			logger.error("Error in checkSectionAvailabilityforTeacher() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public long getNoofDaysBySectionId(long sectionId) {
		long numOfDays = 0;
		try {
			numOfDays = adminSchedulerdao.getNoofDaysBySectionId(sectionId);
		} catch (Exception e) {
			logger.error("Error in getNoofDaysBySectionId() of AdminSchedulerServiceImpl"
					+ e);
		}
		return numOfDays;
	}

	@Override
	public long getNoofSectionsByDay(long dayId, long sectionId) {
		long numOfSection = 0;
		try {
			numOfSection = adminSchedulerdao.getNoofSectionsByDay(dayId,
					sectionId);
		} catch (Exception e) {
			logger.error("Error in getNoofSectionsByDay() of AdminSchedulerServiceImpl"
					+ e);
		}
		return numOfSection;

	}

	@Override
	public long getNoofSectionsByTeacherRegId(long dayId, long teacherId) {
		long numOfSection = 0;
		try {
			numOfSection = adminSchedulerdao.getNoofSectionsByTeacherRegId(
					dayId, teacherId);
		} catch (Exception e) {
			logger.error("Error in getNoofSectionsByDay() of AdminSchedulerServiceImpl"
					+ e);
		}
		return numOfSection;

	}

	@Override
	public boolean checkTeacherAvailability(long gradeId, long classId, long teacherId, long dayId, Periods period) {
		boolean flag = false;
		try {
			flag = adminSchedulerdao.checkTeacherAvailability(teacherId, dayId,
					period.getPeriodId());
		} catch (Exception e) {
			logger.error("Error in getNoofSectionsByDay() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public long prepareSchedule(ClassStatus cs, ClassActualSchedule cas) {
		List<RegisterForClass> studentList = new ArrayList<RegisterForClass>();
		try {
			School school = cs.getTeacher().getUserRegistration().getSchool();
			String leveling = "", genderEquity = "";
			int classStrength = 0;
			List<RegisterForClass> students;

			long gradeLevelId = cs.getSection().getGradeLevel().getGradeLevelId();
			leveling = school.getLeveling();
			classStrength = school.getClassStrength();
			genderEquity = school.getGenderEquity();

			long gradeclassId = cs.getSection().getGradeClasses().getGradeClassId();
			ClassStatus classstat = adminSchedulerdao.getClassStatus(cs.getTeacher().getTeacherId(), cs.getSection().getSectionId());
			@SuppressWarnings("unused")
			int studentsCount = 0, maleCount = 0, femaleCount = 0;
			boolean classExists = false;
			@SuppressWarnings("unused")
			long regId = 0;
			if (classstat != null) {
				classExists = true;
				cs.setCsId(classstat.getCsId());
			}
			cas.setClassStatus(cs);
			if(classExists){
				adminSchedulerdao.SaveClassActualSchedule(cas);
				return 0;
			}
			if (leveling!=null && leveling.equalsIgnoreCase("yes")) {
				students = adminSchedulerdao.getStudentsByTeacherGradelevel(gradeclassId, gradeLevelId, cs.getTeacher().getTeacherId());
				if (students.isEmpty()) {
					return 0; // dont schedule the teacher if he/she does not have students to be scheduled
				}
			} else {
				students = adminSchedulerdao.getStudentsByTeacher(gradeclassId, cs.getTeacher().getTeacherId());
				if (students.isEmpty()) {
					students = adminSchedulerdao.getStudentsbygradeclassId(gradeclassId);
				}
			}
			if (students.isEmpty()) {
				return 0;
			}
			adminSchedulerdao.SaveClassActualSchedule(cas);
			maleCount = 0;
			femaleCount = 0;
			studentsCount = 0;
			
			for (RegisterForClass regForClass : students) {
				regId = regForClass.getStudent().getUserRegistration().getRegId();
				//if (studentList.size() < classStrength) {
					boolean genderEquityCheck =false;
					if(genderEquity != null && genderEquity.equalsIgnoreCase("yes") && classStrength > 0){
						 genderEquityCheck = addStudentsToClassServiceImpl.checkClassStrengthByGenderEquity(classStrength, regForClass.getStudent().getStudentId(), maleCount, femaleCount);
					}
					if(genderEquity.equalsIgnoreCase("yes") && !genderEquityCheck){
						continue;
					}						
					boolean studentAvailability = adminSchedulerdao.checkStudentAvailabilityByTime(regForClass.getStudent().getStudentId(), cas.getPeriods().getPeriodId(), cas.getDays().getDayId());
					if(studentAvailability){
						regForClass.setClassStatus(cs);
						regForClass.setStatus(WebKeys.ACCEPTED);
						regForClass.setSection(cs.getSection());						
						studentList.add(regForClass);
						if (genderEquity != null && genderEquity.equalsIgnoreCase("no")) {
							studentsCount++;
						} else if (regForClass.getStudent().getGender().equalsIgnoreCase(WebKeys.LP_MALE)) {
							maleCount++;
						} else {
							femaleCount++;
						}
					}
				}
			registerForClassDAO.saveRegisterForClass(studentList);				
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error in prepareSchedule() of AdminSchedulerServiceImpl"
					+ ex);
		}
		finally{
			studentList.clear();
		}
		return cs.getCsId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean createTimeTable(int periodRange, int passingTime,
			int homeRoomTime, Long daystarttime, Long daystarttimemin,
			String daystarttimemeridian, Long dayendtime, Long dayendtimemin,
			String dayendtimemeridian, School school) {
		long gradeId = 0;
		long startTotalTime = 0, endTotalTime = 0;
		String status = "";
		String result="";
		boolean check = false;
		try {

			List<Grade> grades = gradesdao.getGradesList(school.getSchoolId());
			for (int pid = 0; pid < grades.size(); pid++) {
				gradeId = grades.get(pid).getGradeId();
				if (daystarttimemeridian.equalsIgnoreCase("PM")) {
					if (daystarttime == 12) {
						startTotalTime = ((daystarttime * 60) + (daystarttimemin));
					} else {
						startTotalTime = ((daystarttime * 60)
								+ (daystarttimemin) + (12 * 60));
					}
				} else if (daystarttimemeridian.equalsIgnoreCase("AM")) {
					startTotalTime = ((daystarttime * 60) + (daystarttimemin));
				}
				if (dayendtimemeridian.equalsIgnoreCase("PM")) {
					if (dayendtime == 12) {
						endTotalTime = ((dayendtime * 60) + (dayendtimemin));
					} else {
						endTotalTime = ((dayendtime * 60) + (dayendtimemin) + (12 * 60));
					}
				} else if (dayendtimemeridian.equalsIgnoreCase("AM")) {
					endTotalTime = ((dayendtime * 60) + (dayendtimemin));
				}
				long availMinutes = endTotalTime - startTotalTime;
				long hour = daystarttime;
				long minute = daystarttimemin;
				// Setting Home Room Timings
				Time startTime = new Time((int) hour, (int) minute, 00);
				// updating minutes by home room period for endtime
				minute = minute + homeRoomTime;
				if (minute >= 60) {
					hour = hour + 1;
					minute = minute - 60;
				}
				Time endTime = new Time((int) hour, (int) minute, 00);
				Periods period = new Periods();
				period.setPeriodName(WebKeys.LP_CLASS_HOMEROME);
				period.setStartTime(String.valueOf(startTime));
				period.setEndTime(String.valueOf(endTime));
				period.setGrade(gradesdao.getGrade(gradeId));
				status = adminDAO.SavePeriod(period);
				availMinutes = availMinutes - homeRoomTime;
				startTime = endTime;
				minute = minute + periodRange;
				if (minute >= 60) {
					hour = hour + 1;
					minute = minute - 60;
				}
				endTime = new Time((int) hour, (int) minute, 00);
				availMinutes = availMinutes - periodRange;
				int i = 0;
				while (availMinutes >= 0) {
					i = i + 1;
					String periodName = "period" + String.valueOf(i);
					Periods per = new Periods();
					per.setPeriodName(periodName);
					per.setStartTime(String.valueOf(startTime));
					per.setEndTime(String.valueOf(endTime));
					per.setGrade(gradesdao.getGrade(gradeId));
					result = adminDAO.SavePeriod(per);

					hour = endTime.getHours();
					minute = minute + passingTime;
					if (minute >= 60) {
						hour = hour + 1;
						minute = minute - 60;
					}
					startTime = new Time((int) hour, (int) minute, 00);
					minute = minute + periodRange;
					if (minute >= 60) {
						minute = minute - 60;
						hour = hour + 1;
					}
					endTime = new Time((int) hour, (int) minute, 00);
					availMinutes = availMinutes - periodRange - passingTime;
				}
			}
			   if (status.equalsIgnoreCase("Success") && result.equalsIgnoreCase("Success") )
				check = true;
		} catch (Exception e) {
			check = false;
			logger.error("Error in createTimeTable() of AdminSchedulerServiceImpl"
					+ e);
		}
		return check;
	}

	@Override
	public Periods getHomeRoomPeriod(School school, long gradeId) {
		Periods homePeriod = null;
		try {
			homePeriod = adminSchedulerdao.getHomeRoomPeriod(school, gradeId);
		} catch (Exception e) {
			logger.error("Error in getHomeRoomPeriod() of AdminSchedulerServiceImpl"
					+ e);
		}
		return homePeriod;
	}

	@Override
	public Teacher getTeacher(long teacherId) {
		Teacher teacher = null;
		try {
			teacher = adminSchedulerdao.getTeacher(teacherId);
		} catch (Exception e) {
			logger.error("Error in getTeacher() of AdminSchedulerServiceImpl"
					+ e);
		}
		return teacher;
	}

	@Override
	public Periods getPeriod(long periodId) {
		Periods periods = null;
		try {
			periods = adminDAO.getPeriod(periodId);
		} catch (Exception e) {
			logger.error("Error in getPeriod() of AdminSchedulerServiceImpl"
					+ e);
		}
		return periods;
	}

	@Override
	public boolean SetHomeroomClassForTeacher(HomeroomClasses homeroomclass) {
		boolean flag = false;
		try {
			flag = adminSchedulerdao.SetHomeroomClassForTeacher(homeroomclass);
		} catch (Exception e) {
			logger.error("Error in SetHomeroomClassForTeacher() of AdminSchedulerServiceImpl"
					+ e);
		}
		return flag;
	}

	@Override
	public boolean scheduleTeachers(SchoolSchedule schoolSchedule) {
		boolean flag = false;
		try {
			long gradeId = 0, classId = 0, classPeriodsperDay = 0, classPeriodsofTeacher = 0, noOfDayss = 0;
			boolean outerBreak1 = false;
			boolean outerBreak2 = false;
			String leveling = "";
			Teacher teacher = null;
			String startTime = "", endTime = "";
			Long dayStartTime = null, dayEndTime = null;
			GradeLevel gradeLevel = null;
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			School school = userReg.getSchool();
			List<Section> sections = Collections.emptyList();
			Boolean checkSectionAvailability = false, teacherAvailability = false;
			List<TeacherSubjects> teachersubjects = getTeacherSubjects(school);
			leveling = school.getLeveling();
			List<SchoolDays> schoolDays = getSchoolDays(school);
			boolean checkPeriods = checkPeriodsBySchoolId(school);
			schoolSchedule.setSchool(school);
			if (schoolSchedule.getDayStartTimeMeridian().equalsIgnoreCase("PM")
					&& schoolSchedule.getDayStartTime() != 12) {
				dayStartTime = schoolSchedule.getDayStartTime() + 12;
			} else {
				dayStartTime = schoolSchedule.getDayStartTime();
			}
			startTime = dayStartTime + ":"
					+ schoolSchedule.getDayStartTimeMin() + ":" + "00";
			if (schoolSchedule.getDayEndTimeMeridian().equalsIgnoreCase("PM")
					&& schoolSchedule.getDayEndTime() != 12) {
				dayEndTime = schoolSchedule.getDayEndTime() + 12;
			} else {
				dayEndTime = schoolSchedule.getDayEndTime();
			}
			endTime = dayEndTime + ":" + schoolSchedule.getDayEndTimeMin()
					+ ":" + "00";
			schoolSchedule.setSchoolEndTime(endTime);
			schoolSchedule.setSchoolStartTime(startTime);
			adminSchedulerdao.saveSchoolSchedule(schoolSchedule);

			if (!checkPeriods) {
				createTimeTable(schoolSchedule.getPeriodRange(),
						schoolSchedule.getPassingTime(),
						schoolSchedule.getHomeRoomTime(),
						schoolSchedule.getDayStartTime(),
						schoolSchedule.getDayStartTimeMin(),
						schoolSchedule.getDayStartTimeMeridian(),
						schoolSchedule.getDayEndTime(),
						schoolSchedule.getDayEndTimeMin(),
						schoolSchedule.getDayEndTimeMeridian(), school);
			}
			StudentClass studentClass = new StudentClass();
			studentClass.setClassName(WebKeys.LP_CLASS_HOMEROME);
			studentClass.setSchool(school);
			classId = adminDAO.checkClassExists(studentClass);
			if(classId == 0){
				adminDAO.CreateClasses(studentClass);
			}
			else{
				studentClass.setClassId(classId);
			}
			sections = adminDAO.getAllSections(gradeId, studentClass.getClassId());
			for (TeacherSubjects teacherSub : teachersubjects) {
				gradeId = teacherSub.getGrade().getGradeId();
				teacher = teacherSub.getTeacher();
				for (Section section : sections) {
					checkSectionAvailability = checkHomeSectionAvailabilityforTeacher(section
							.getSectionId());
					teacherAvailability = checkTeacherAvailabilityforHomeRoom(teacher
							.getTeacherId());
					if (checkSectionAvailability && teacherAvailability) {
						createHomeRooms(teacher, teacherSub.getGrade(), section);
						break;
					}
				}
			}
			sections.clear();
			for (TeacherSubjects teacherSub : teachersubjects) {
				gradeId = teacherSub.getGrade().getGradeId();
				classId = teacherSub.getStudentClass().getClassId();
				teacher = teacherSub.getTeacher();
				gradeLevel = teacherSub.getGradeLevel();
				List<Periods> schoolPeriods = getSchoolPeriods(gradeId);
				if (leveling != null && leveling.equalsIgnoreCase("yes")) {
					sections = adminDAO.getSectionsByGradeLevel(gradeId, classId, gradeLevel.getGradeLevelId());
				} else {
					sections = adminDAO.getAllSections(gradeId, classId);
				}

				for (Section section : sections) {
					checkSectionAvailability = checkSectionAvailabilityforTeacher(section.getSectionId(), teacher.getTeacherId());
					if (!checkSectionAvailability) {
						continue;
					}

					for (SchoolDays schoolDay : schoolDays) {
						for (Periods period : schoolPeriods) {
							noOfDayss = getNoofDaysBySectionId(section.getSectionId());
							classPeriodsperDay = getNoofSectionsByDay(schoolDay.getDays().getDayId(), section.getSectionId());
							classPeriodsofTeacher = getNoofSectionsByTeacherRegId(schoolDay.getDays().getDayId(), teacher.getTeacherId());
							if (noOfDayss >= schoolSchedule.getNoOfDays()) {
								outerBreak1 = true;
								outerBreak2 = true;
								break;
							}
							if (classPeriodsofTeacher >= schoolSchedule.getNoOfClassPeriods() || classPeriodsperDay >= schoolSchedule.getNoOfSections()) {
								break;
							}
							teacherAvailability = teacherDAO.checkTeacherAvailability(gradeId, classId, teacher.getTeacherId(), schoolDay.getDays().getDayId(), period);
							if (teacherAvailability) {
								ClassStatus cs = new ClassStatus();
								cs.setStartDate(schoolSchedule.getStartDate());
								cs.setEndDate(schoolSchedule.getEndDate());
								cs.setTeacher(teacher);
								cs.setSection(section);
								cs.setAvailStatus(WebKeys.AVAILABLE);
								cs.setDescription(WebKeys.CLASS_DESC);
								cs.setEditingStatus(WebKeys.CLASS_EDITING_STATUS);
								ClassActualSchedule classActualSchedule = new ClassActualSchedule();
								classActualSchedule.setClassStatus(cs);
								classActualSchedule.setDays(schoolDay.getDays());
								classActualSchedule.setPeriods(period);
								prepareSchedule(cs, classActualSchedule);
							}
						}
						if (outerBreak2) {
							outerBreak2 = false;
							break;
						}
					}
					if (outerBreak1) {
						outerBreak1 = false;
						break;
					}
				}
			}

			flag = true;
		} catch (Exception e) {
			logger.error("Error in scheduleTeachers() of AdminSchedulerServiceImpl" + e);
		}
		return flag;
	}
}