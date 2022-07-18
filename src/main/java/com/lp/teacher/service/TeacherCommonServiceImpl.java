package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lp.common.dao.CurriculumDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.CreateUnits;
import com.lp.model.Grade;
import com.lp.model.Lesson;
import com.lp.model.RegisterForClass;
import com.lp.model.SchoolSchedule;
import com.lp.model.StudentClass;
import com.lp.model.StudentCompositeActivityScore;
import com.lp.model.StudentCompositeActivityScoreId;
import com.lp.model.StudentCompositeLessonScore;
import com.lp.model.StudentCompositeLessonScoreId;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.CommonDAO;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.utils.WebKeys;

@Component(value = "assignLessonsService")

public class TeacherCommonServiceImpl implements TeacherCommonService {
	static final Logger logger = Logger.getLogger(TeacherCommonServiceImpl.class);

	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private CurriculumDAO curriculumDao;
	@Autowired
	HttpSession session;
	@Autowired
	private TeacherDAO teacherDAO;

	@Override
	public List<Grade> getTeacherAssignedGrades(Teacher teacher) {
		return commonDAO.getTeacherAssignedGrades(teacher);
	}

	@Override
	public Teacher getTeacher(UserRegistration userReg) {
		return commonDAO.getTeacher(userReg);
	}

	@Override
	@RemoteMethod
	public List<StudentClass> getTeacherClasses(Teacher teacher, long gradeId) {
		return commonDAO.getTeacherClasses(teacher, gradeId);
	}

	@Override
	public List<Unit> getUnits(long gradeId, long classId, Teacher teacher) {
		UserRegistration teacherReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		UserRegistration adminReg = commonDAO.getAdminByTeacher(teacherReg);
		return commonDAO.getUnits(gradeId, classId, teacher, adminReg);
	}

	@Override
	public List<Lesson> getLessonsByUnit(long unitId) {
		return curriculumDao.getLessonssbyUnitId(unitId);
	}

	@Override
	public List<Activity> getActivitiesByLesson(long lessonId) {
		return commonDAO.getActivitiesByLesson(lessonId);
	}

	@Override
	public boolean assignCurriculum(long csId, CreateUnits assignCurriculum,
			Date dueDate) throws DataException {
		boolean status = false;
		List<AssignLessons> assignLessons = new ArrayList<>();
		List<AssignActivity> assignActivities = new ArrayList<>();
		List<Unit> units = assignCurriculum.getUnits();
		List<RegisterForClass> registerForClasses = teacherDAO.getStudentsByCsId(csId);
		for (int unitCount = 0; unitCount < units.size(); unitCount++) {
			List<Lesson> lessons = units.get(unitCount).getLessonsList();
			for (Lesson lesson : lessons) {
				List<StudentCompositeLessonScore> studentCompositeLessonScores = new ArrayList<>() ; 
				if (lesson.isLessonSelected()) {
					AssignLessons assignLesson = new AssignLessons();
					assignLesson.setLesson(lesson);
					assignLesson.setClassStatus(commonDAO.getClassStatus(csId));
					assignLesson.setDueDate(dueDate);
					assignLesson.setAssignedDate(new java.sql.Date(new java.util.Date().getTime()));
					for(RegisterForClass reClass : registerForClasses ){
						StudentCompositeLessonScore studentCompositeLessonScore = new StudentCompositeLessonScore();
						StudentCompositeLessonScoreId studentCompositeLessonScoreId = new StudentCompositeLessonScoreId();
						studentCompositeLessonScoreId.setStudentId(reClass.getStudent().getStudentId());
						studentCompositeLessonScore.setId(studentCompositeLessonScoreId);
						studentCompositeLessonScore.setAssignLessons(assignLesson);
						studentCompositeLessonScore.setStudent(reClass.getStudent());
						studentCompositeLessonScores.add(studentCompositeLessonScore);
					}
					assignLesson.setStudentCompositeLessonScores(studentCompositeLessonScores);
					assignLessons.add(assignLesson);
				}
				List<Activity> activities = lesson.getActivityList();
				for (Activity activity : activities) {
					List<StudentCompositeActivityScore> studentCompositeActivityScores = new ArrayList<>() ;
					if (activity.isActivitySelected()) {
						AssignActivity assignActivity = new AssignActivity();
						assignActivity.setClassStatus(commonDAO.getClassStatus(csId));
						assignActivity.setActivity(activity);
						assignActivity.setAssignedDate(new java.sql.Date(new java.util.Date().getTime()));
						for(RegisterForClass reClass : registerForClasses ){
							StudentCompositeActivityScore studentCompositeActivityScore = new StudentCompositeActivityScore();
							StudentCompositeActivityScoreId studentCompositeActivityScoreId = new StudentCompositeActivityScoreId();
							studentCompositeActivityScoreId.setStudentId(reClass.getStudent().getStudentId());
							studentCompositeActivityScore.setId(studentCompositeActivityScoreId);
							studentCompositeActivityScore.setAssignActivity(assignActivity);
							studentCompositeActivityScore.setStudent(reClass.getStudent());
							studentCompositeActivityScores.add(studentCompositeActivityScore);
						}
						assignActivity.setStudentCompositeActivityScores(studentCompositeActivityScores);
						assignActivities.add(assignActivity);
					}
				}				
			}
		}
		try {
			status = commonDAO.assignCurriculum(assignLessons, assignActivities);
		} catch (DataException e) {
			logger.error("Error in createUnits() of  CommmonServiceImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in createUnits() of CommmonServiceImpl", e);
		}
		return status;
	}

	@Override
	public CreateUnits getTeacherCurriculum(long gradeId, long classId,
			Teacher teacher) {
		UserRegistration adminReg = null;
		if(teacher != null){
			adminReg = commonDAO.getAdminByTeacher(teacher.getUserRegistration());
		}
		else{
			adminReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		}
		long schoolId = adminReg.getSchool().getSchoolId();
		SchoolSchedule schedule = curriculumDao.getSchoolSchedule(schoolId);
		return commonDAO.getTeacherCurriculum(gradeId, classId, teacher, adminReg, schedule);
	}
	
	@Override
	public List<Grade> getTeacherGradesByAcademicYr(Teacher teacher) {
		return commonDAO.getTeacherAssignedGrades(teacher);
	}
}
