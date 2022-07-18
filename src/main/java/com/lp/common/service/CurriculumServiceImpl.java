package com.lp.common.service;

import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AdminDAO;
import com.lp.common.dao.AssessmentDAO;
import com.lp.common.dao.CurriculumDAO;
import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.JacQuestionFile;
import com.lp.model.Lesson;
import com.lp.model.LessonPaths;
import com.lp.model.PtaskFiles;
import com.lp.model.Questions;
import com.lp.model.Rubric;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.CommonDAO;
import com.lp.teacher.service.TeacherCommonService;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "curriculumService")
public class CurriculumServiceImpl implements CurriculumService {

	@Autowired
	private AdminDAO adminDAO;	
	@Autowired
	private TeacherCommonService teacherCommonService;
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	HttpSession session;
	@Autowired
	private CurriculumDAO curriculumDAO;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;

	static final Logger logger = Logger.getLogger(CurriculumServiceImpl.class);

	@Override
	public boolean createUnit(long gradeId, long classId,
			Unit unit, UserRegistration userReg)
			throws DataException {
		// TODO Auto-generated method stub
		long unitNo = 0;
		boolean status = false;
		try {
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);
			unit.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			unit.setChangeDate(changeDate);
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			GradeClasses gradeClass = adminDAO.getGradeClass(gradeClassId);
			List<Unit> units = curriculumDAO
					.getUnitsbygradeClassId(gradeClassId);
			long noOfUnits = units.size();
			if (noOfUnits == 0) {
				unitNo = 0;
			} else {
				unitNo = units.size();
			}
				if(unit.getUnitName() != null){
					Unit newUnit = new Unit();
					newUnit.setGradeClasses(gradeClass);
					newUnit.setUserRegistration(userReg);
					newUnit.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
					newUnit.setUnitNo(++unitNo);
					newUnit.setUnitName(unit.getUnitName());
					status = curriculumDAO.createUnit(newUnit);
				}
			
		} catch (DataException e) {
			logger.error("Error in createUnits() of  CurriculumServiceImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in createUnits() of CurriculumServiceImpl", e);
		}

		return status;
	}

	@Override
	@RemoteMethod
	public List<Unit> getUnitsByGradeIdClassId(long gradeId, long classId)
			throws DataException {
		List<Unit> units = Collections.emptyList();
		try {
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			if(teacherObj == null){
				units = curriculumDAO.getUnitsbygradeClassId(gradeClassId);
			}else{
				UserRegistration adminReg = commonDAO.getAdminByTeacher(teacherObj.getUserRegistration());
				units = commonDAO.getUnits(gradeId, classId, teacherObj, adminReg);
			}
			

		} catch (DataException e) {
			logger.error("Error in getUnitsByGradeIdClassId() of  CurriculumServiceImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getUnitsByGradeIdClassId() of CurriculumServiceImpl",
					e);
		}
		return units;
	}

	@Override
	public boolean createLesson(long unitId, Lesson lesson)	throws DataException {
		int lessonNo = 0;
		boolean status = false;
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Unit unit = curriculumDAO.getUnitbyUnitId(unitId);
			List<Activity> activityLt = new ArrayList<Activity>();
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);
			if(lesson.getLessonName() != null){
				    Lesson newLesson = new Lesson();
					newLesson.setUnit(unit);
					newLesson.setLessonNo(++lessonNo);
					newLesson.setLessonName(lesson.getLessonName());
					newLesson.setLessonDesc(lesson.getLessonDesc());
					newLesson.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
					newLesson.setChangeDate(changeDate);
					newLesson.setUserRegistration(userReg);
					for (int i = 0; i < lesson.getActivityList().size(); i++) {
						if(lesson.getActivityList().get(i).getActivityDesc() != null && lesson.getActivityList().get(i).getActivityDesc() != ""){
							lesson.getActivityList().get(i).setUserRegistration(userReg);
							activityLt.add(lesson.getActivityList().get(i));
						}
					}
					newLesson.setActivityList(activityLt);
					status = curriculumDAO.createLesson(newLesson);
				}
		} catch (DataException e) {
			logger.error("Error in createLessons() of  CurriculumServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in createLessons() of CurriculumServiceImpl", e);
		}
		return status;
	}

	@Override
	public List<Grade> getTeacherGrades(Teacher teacherObj)
			throws DataException {
		List<Grade> teacherGrades = new ArrayList<Grade>();		
		session.removeAttribute("academicYrFlag");
		try {
			teacherGrades = teacherCommonService.getTeacherAssignedGrades(teacherObj);
		} catch (DataException e) {
			logger.error("Error in getTeacherGrades() of  CurriculumServiceImpl");
			throw new DataException(WebKeys.TEACHER_NO_GRADES_MESSAGE, e);
		}

		return teacherGrades;
	}

	@RemoteMethod
	public List<StudentClass> getAssingedTeacherClasses(long gradeId) throws DataException, SQLDataException {
		List<StudentClass> teacherClasses = new ArrayList<StudentClass>();
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if(teacherObj == null) {
				teacherClasses = adminDAO.getGradeClassesByGradeId(gradeId);				
			}else{
				teacherClasses = commonDAO.getTeacherClasses(teacherObj, gradeId);	
			}
		} catch (DataException e) {
			logger.error("Error in getAssingedTeacherClasses() of  CurriculumServiceImpl");
			throw new DataException(
					"Error in getTeacherGrades() of CurriculumServiceImpl");
		}
		return teacherClasses;
	}

	@Override
	@RemoteMethod
	public List<Lesson> getLessonsByGradeIdClassId(long gradeId, long classId)
			throws DataException {
		List<Lesson> lessons = null;
		try {
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			lessons = curriculumDAO.getLessonsbygradeClassId(gradeClassId);

		} catch (DataException e) {
			logger.error("Error in getLessonsByGradeIdClassId() of  CurriculumServiceImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getLessonsByGradeIdClassId() of CurriculumServiceImpl",
					e);
		}
		return lessons;
	}

	@Override
	@RemoteMethod
	public boolean saveActivity(Activity activity) throws DataException {
		return curriculumDAO.saveActivity(activity);
	}

	@Override
	public Lesson getLessonObj(long lessonId) {
		return curriculumDAO.getLessonObj(lessonId);
	}
	@RemoteMethod
	@Override
	public List<ClassStatus> getTeacherSections(Teacher teacher, long gradeId,
			long classId) {
		return commonDAO.getTeacherSections(teacher, gradeId, classId);
	}
	
	public Unit getUnitbyUnitId(long unitId) throws DataException{
		return curriculumDAO.getUnitbyUnitId(unitId);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AssignLessons> getAssignedCurriculum(long csId, UserRegistration userReg){
		List<AssignLessons> assignedLessons = new ArrayList<AssignLessons>();
		try{		
			assignedLessons = curriculumDAO.getAssignedLesson(csId);
			List<AssignActivity> assignedActivities= curriculumDAO.getAssignedActivities(csId);
			List<Assignment> assignedAssignments = curriculumDAO.getAssignedAssignments(csId);	
			List<LessonPaths> lessonPaths = curriculumDAO.getLessonPaths(userReg.getRegId());
			
			for(AssignLessons assignLesson : assignedLessons){
				List<AssignActivity> assignActivityforEachLesson = new ArrayList();
				for(AssignActivity assActivity : assignedActivities){
					if(assignLesson.getLesson().getLessonId() == assActivity.getActivity().getLesson().getLessonId()){
						assignActivityforEachLesson.add(assActivity);
					}
				}
				assignLesson.setActivityList(assignActivityforEachLesson);
				
				List<LessonPaths> lessonPathsforEachLesson = new ArrayList();
				for(LessonPaths lessonPath : lessonPaths){
					if(lessonPath.getLesson().getLessonId() == assignLesson.getLesson().getLessonId()){
						lessonPathsforEachLesson.add(lessonPath);
					}
				}
				assignLesson.setLessonPathses(lessonPathsforEachLesson);		
				for(Assignment assignment : assignedAssignments){
					if(assignLesson != null && assignment != null){
						if(assignLesson.getLesson() != null && assignment.getLesson() != null){
							if(assignment.getLesson().getLessonId() == assignLesson.getLesson().getLessonId() && WebKeys.LP_USED_FOR_ASSESSMENT.equals(assignment.getUsedFor())){
								assignLesson.setAssessmentStatus(true);	
								if(assignLesson.isHomeworkStatus()){
									break;
								}
							}
							if(assignment.getLesson().getLessonId() == assignLesson.getLesson().getLessonId() && WebKeys.LP_USED_FOR_HOMEWORKS.equals(assignment.getUsedFor())){
								assignLesson.setHomeworkStatus(true);		
								if(assignLesson.isAssessmentStatus()){
									break;
								}
							}
						}
					}
				}
			}
			
			
		}
		catch(Exception e){
			logger.error("Error in getAssignedCurriculum() of  CurriculumServiceImpl"
					+ e);
		}
		return assignedLessons;
	}

	@Override
	public List<ClassStatus> getAdminSections(long gradeId, long classId)
			throws DataException {
		List<ClassStatus> cStatus = Collections.emptyList();;
		try {
			cStatus = commonDAO.getAdminSections(gradeId, classId);

		} catch (DataException e) {
			logger.error("Error in getAdminSections() of  CurriculumServiceImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getAdminSections() of CurriculumServiceImpl",
					e);
		}
		return cStatus;
	}
	
	@Override
	@RemoteMethod
	public List<Unit> getUnitsByGradeClassUser(long gradeId, long classId)
			throws DataException {
		List<Unit> units = Collections.emptyList();
		try {
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(userReg.getUser().getUserType().equals(WebKeys.LP_USER_TYPE_TEACHER)){
				
				units = curriculumDAO.getTeacherUnits(gradeClassId, userReg.getRegId());
			}
			else{
				units = curriculumDAO.getUnitsbygradeClassId(gradeClassId);
			}	
			
		} catch (DataException e) {
			logger.error("Error in getUnitsByGradeClassUser() of  CurriculumServiceImpl"+ e);
			throw new DataException("Error in getUnitsByGradeClassUser() of CurriculumServiceImpl",	e);
		}
		return units;
	}

	@Override
	public boolean updateUnit(Unit unit,long gradeId, long classId) throws DataException {
		// TODO Auto-generated method stub
		boolean status = false;
		try {				
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			GradeClasses gradeClass = adminDAO.getGradeClass(gradeClassId);
			unit.setGradeClasses(gradeClass);
			status = curriculumDAO.updateUnit(unit);
		} catch (DataException e) {
			logger.error("Error in updateEditedUnits() of  CurriculumServiceImpl" + e);
			throw new DataException("Error in updateEditedUnits() of CurriculumServiceImpl", e);
		}
		return status;
	}

	@Override
	@RemoteMethod
	public List<Lesson> getLessonsByUnitIdUserId(long unitId)
			throws DataException {
		List<Lesson> lessons = null;
		try {
			lessons = curriculumDAO.getLessonssbyUnitIdUserId(unitId);

		} catch (DataException e) {
			logger.error("Error in getLessonssbyUnitIdUserId() of  CurriculumServiceImpl"+ e);
			throw new DataException("Error in getLessonssbyUnitIdUserId() of CurriculumServiceImpl",e);
		}
		return lessons;
	}
	
	@Override
	public boolean updateLesson(Lesson lesson) throws DataException{
		boolean status = false;
		try {
			status = curriculumDAO.updateLesson(lesson);
		} catch (DataException e) {
			logger.error("Error in updateEditedUnits() of  CurriculumServiceImpl" + e);
			throw new DataException("Error in updateEditedUnits() of CurriculumServiceImpl", e);
		}
		return status;
	}

	@Override
	public boolean removeUnit(long unitId)	throws DataException {
		boolean status = false;
		try {	
			List<Lesson> lessonsLt = curriculumDAO.getLessonssbyUnitId(unitId);
			for (Lesson lesson : lessonsLt) {
				removeLesson(lesson.getLessonId());
			}
			status = curriculumDAO.removeUnit(unitId);
		} catch (DataException e) {
			logger.error("Error in removeUnit() of  CurriculumServiceImpl" + e);
			//throw new DataException(e.getMessage(), e);
		}
		return status;
	}
	
	@Override
	public boolean removeLesson(long lessonId)	throws DataException {
		boolean status = true;
		try {
			List<JacQuestionFile> jacFileQuestions = Collections.emptyList();
			jacFileQuestions = assessmentDAO.getJacFileQuestionsByLessonId(lessonId);
			if(jacFileQuestions.size() > 0){
				for (JacQuestionFile jacQuestionFile : jacFileQuestions) {
					status = assessmentDAO.deleteJacTempleteQuestion(jacQuestionFile.getJacQuestionFileId());
				}
			}
			List<Questions> questionLt = assessmentDAO.getQuestionsByLessonId(lessonId);
			if(questionLt.size() > 0){
				for (Questions question : questionLt) {
					if(question.getAssignmentType().getAssignmentTypeId() == 13){
						 List<PtaskFiles> pTaskFiles = Collections.emptyList();
						 List<Rubric> rubrics = new ArrayList<Rubric>();
						 pTaskFiles = performanceTaskDAO.getPtaskFiles(question.getQuestionId());
		 		         rubrics = performanceTaskDAO.getRubrics(question.getQuestionId());
						 question.setRubrics(rubrics);
						 question.setPtaskFiles(pTaskFiles);
					}
						status = assessmentDAO.removeAssessments(question);
				}
			}
			
			if(status)
				status = curriculumDAO.removeLesson(lessonId);
		} catch (Exception e) {
			logger.error("Error in removeLesson() of  CurriculumServiceImpl" + e);
			//throw new DataException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public List<Activity> getActivityByLesson(long lessonId) throws DataException {
		List<Activity> activity = new ArrayList<Activity>();
		try {
			activity = curriculumDAO.getActivityByLesson(lessonId);

		} catch (DataException e) {
			logger.error("Error in getActivityByLesson() of  CurriculumServiceImpl"+ e);
			throw new DataException("Error in getActivityByLesson() of CurriculumServiceImpl",e);
		}
		return activity;
	}

	@Override
	public boolean saveActivities(List<Activity> activities) throws DataException {
		boolean status = false;
		try {					
			status = curriculumDAO.saveActivities(activities);
		} catch (DataException e) {
			logger.error("Error in saveActivities() of  CurriculumServiceImpl" + e);
			throw new DataException("Error in saveActivities() of CurriculumServiceImpl", e);
		}
		return status;
	}
	
	@Override
	public boolean removeActivity(long activityId){
		return curriculumDAO.removeActivity(activityId);
	}
	
	@Override
	public Activity updateActivity(Activity activity) throws DataException{
		return curriculumDAO.updateActivity(activity);
	}
	@Override
	public boolean checkLessonExists(long lessonId) throws DataException{
		boolean status = false;
		try {	
			status=curriculumDAO.checkLessonExists(lessonId);
		}catch (DataException e) {
			logger.error("Error in saveActivities() of  CurriculumServiceImpl" + e);
			throw new DataException("Error in saveActivities() of CurriculumServiceImpl", e);
		}
		return status;
		}
	@Override
	public boolean checkUnitExists(long unitId) throws DataException{
		boolean status = false;
		try {	
			status=curriculumDAO.checkUnitExists(unitId);
		}catch (DataException e) {
			logger.error("Error in saveActivities() of  CurriculumServiceImpl" + e);
			throw new DataException("Error in saveActivities() of CurriculumServiceImpl", e);
		}
		return status;
	}
	
	
	@Override
	public List<Grade> getTeacherGradesByAcademicYr(Teacher teacherObj)
			throws DataException {
		List<Grade> teacherGrades = new ArrayList<Grade>();		
		try {
			teacherGrades = teacherCommonService.getTeacherAssignedGrades(teacherObj);
		} catch (DataException e) {
			logger.error("Error in getTeacherGrades() of  CurriculumServiceImpl");
			throw new DataException(WebKeys.TEACHER_NO_GRADES_MESSAGE, e);
		}

		return teacherGrades;
	}
	@Override
	public List<StudentClass> getTeacherSubjects(long gradeId,long teacherId) throws DataException, SQLDataException
	{
		List<StudentClass> teacherClasses=new ArrayList<StudentClass>();
		try {
			teacherClasses = curriculumDAO.getTeacherSubjects(gradeId,teacherId);				
		} catch (DataException e) {
			logger.error("Error in getAssingedTeacherClasses() of  CurriculumServiceImpl");
			throw new DataException(
					"Error in getTeacherGrades() of CurriculumServiceImpl");
		}
		return teacherClasses;
	}
}