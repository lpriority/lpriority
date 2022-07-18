package com.lp.admin.service;

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
import com.lp.admin.dao.GradesDAO;
import com.lp.admin.dao.StudentClassesDAO;
import com.lp.appadmin.dao.MasterGradesDAO;
import com.lp.appadmin.dao.SubInterestDAO;
import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.GradeLevel;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.LegendSubCriteria;
import com.lp.model.MasterGrades;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.Section;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.SubInterest;
import com.lp.model.TeacherSubjects;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.IOLReportCardDAO;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "adminService1")
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private TeacherDAO teacherDao;
	@Autowired
	private MasterGradesDAO mastergradesdao;
	@Autowired
	private AdminDAO AdminDAO;
	@Autowired
	private GradesDAO gradesdao;
	@Autowired
	private SubInterestDAO subinterestdao;
	@Autowired
	private StudentClassesDAO classDao;
	@Autowired
	private IOLReportCardDAO iolReportCardDao;
	
	static final Logger logger = Logger.getLogger(AdminServiceImpl.class);
		
	@Autowired
	private HttpSession session;

	@Override
	public void addTeacher(UserRegistration user) {
		user.setUser(userDao.getUserType("teacher"));
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		user.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		user.setChangeDate(changeDate);
		user.setStatus(WebKeys.LP_STATUS_NEW);
		userRegistrationDAO.saveUserRegistration(user);
	}

	@Override
	public void addStudent(UserRegistration user) {

		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		user.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		user.setChangeDate(changeDate);
		user.setStatus(WebKeys.LP_STATUS_NEW);
		userRegistrationDAO.saveUserRegistration(user);
	}

	@Override
	public void addParent(UserRegistration user) {
		user.setUser(userDao.getUserType("parent"));
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		user.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		user.setChangeDate(changeDate);
		user.setStatus(WebKeys.LP_STATUS_NEW);
		userRegistrationDAO.saveUserRegistration(user);
	}

	@Override
	public List<MasterGrades> getMasterGrades() {

		return mastergradesdao.getMasterGradesList();
	}

	@Override
	@RemoteMethod
	public List<Grade> getSchoolGrades(long schoolId) {
		session.removeAttribute("academicYrFlag");
		return gradesdao.getGradesList(schoolId);

	}

	@Override
	public List<StudentClass> getStudentClass(School school) {
		return classDao.getClasses(school);
	}

	@Override
	public List<GradeClasses> getGradeClasses(long schoolId, long gradeId) {
		return classDao.getGradeClasses(schoolId, gradeId);
	}

	@Override
	public void CreateClasses(StudentClass stuclass) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		stuclass.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		stuclass.setChangeDate(changeDate);
		AdminDAO.CreateClasses(stuclass);

	}

	@Override
	public void setClassStrength(School school) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		school.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		school.setChangeDate(changeDate);
		AdminDAO.setClassStrength(school);

	}

	@Override
	public int CheckExistClass(StudentClass stuclass) {
		return AdminDAO.checkClassExists(stuclass);
	}

	@Override
	public void addSubjectInterest(SubInterest subinterest) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		subinterest.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		subinterest.setChangeDate(changeDate);
		subinterestdao.saveSubInterest(subinterest);
	}

	@Override
	public List<School> getClassStrengthDetails(School school) {
		return AdminDAO.getClassStrengthDetails(school);

	}

	@Override
	public void AddGrades(Grade grade) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		grade.setChangeDate(changeDate);
		int n = gradesdao.checkgradeExists(grade);
		if (n > 0) {
			grade.setStatus(WebKeys.ACTIVE);
			gradesdao.UpdateGrades(grade);
		} else {
			grade.setCreateDate(new java.sql.Date(new java.util.Date()
					.getTime()));
			gradesdao.saveGrades(grade);
		}
	}

	@Override
	public void RemoveGrades(Grade grade) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		grade.setChangeDate(changeDate);
		gradesdao.UpdateGrades(grade);
	}

	@Override
	public void saveGradeClass(GradeClasses gradeClasses) {
		// gradeClassDao.saveGradeClasses(gradeClasses);
		// AdminDAO.AddClasses()
	}

	@Override
	public int checkGradeClasses(long gradeId, long classId, long schoolId) {
		int n = AdminDAO.checkGradeClasses(gradeId, classId, schoolId);
		return n;
	}

	@Override
	public int AddClasses(long gradeId, long classId, String status, String opperation) {
		int n = AdminDAO.AddClasses(gradeId, classId, status, opperation);

		return n;
	}

	@Override
	@RemoteMethod
	public List<StudentClass> getStudentClasses(long gradeId) {

		return AdminDAO.getStudentClasses(gradeId);
	}

	@Override
	@RemoteMethod
	public List<StudentClass> getAddedClasses(long gradeId) {

		return AdminDAO.getAddedClasses(gradeId);
	}
	@Override
	@RemoteMethod
	public List<GradeLevel> getStudentGradeLevels(long classId) {
		return AdminDAO.getStudentGradeLevels(classId);
	}

	@Override
	@RemoteMethod
	public List<GradeClasses> getGradeClasses(long gradeId) {
		return AdminDAO.getGradeClasses(gradeId);
	}

	@Override
	public List<UserRegistration> getStudentsBySchoolId(long schoolId) {
		return userRegistrationDAO.getStudentsBySchoolId(schoolId);
	}

	@Override
	public boolean CreateSections(long gradeclassId, String sectionName,
			String status, long gradelevelId) {
		return AdminDAO.CreateSections(gradeclassId, sectionName, status,
				gradelevelId);
	}

	@Override
	public int checkExistSection(long gradeclassId, long gradeLevelId,
			String sectionName) {
		return AdminDAO.checkExistSection(gradeclassId, gradeLevelId,
				sectionName);
	}

	@Override
	@RemoteMethod
	public List<Section> getAllSections(long gradeId, long classId) {
		return AdminDAO.getAllSections(gradeId, classId);
	}
	
	@Override
	@RemoteMethod
	public List<Section> getSectionsByGradeLevel(long gradeId,long classId, long gradeLevelId) {
		return AdminDAO.getAllSections(gradeId, classId);
	}
	
	@Override
	@RemoteMethod
	public List<Section> getAllSectionsByHomeRoom(long gradeId, long schoolId) {
		return AdminDAO.getAllSectionsByHomeRoom(gradeId, schoolId);
	}

	@Override
	public List<UserRegistration> getAllParentEmailIds(School school,
			long userTypeId) {
		return AdminDAO.getAllParentEmailIds(school, userTypeId);

	}
	@Override
	public List<UserRegistration> getAllUserRegistrationsBySchool(School school, long userTypeId){
		return AdminDAO.getAllUserRegistrationsBySchool(school, userTypeId);
	}
	
	@Override
	public List<UserRegistration> getAllStudentEmailIds(School school,
			long userTypeId, long userTypeid) {
		return AdminDAO.getAllStudentEmailIds(school, userTypeId, userTypeid);

	}

	@Override
	public long checkSecurityExists(UserRegistration userreg) {
		return AdminDAO.checkSecurityExists(userreg);
	}

	@Override
	public int UpdateSections(long sectionId, String sectionName) {
		return AdminDAO.UpdateSections(sectionId, sectionName);
	}

	@Override
	public int DeleteSections(long sectionId, String sectionName) {
		return AdminDAO.DeleteSections(sectionId, sectionName);
	}
	@Override
	public int checkSections(long sectionId){
		return AdminDAO.checkSections(sectionId);
	}

	@Override
	public GradeClasses getGradeClass(long grade_class_id) {
		return AdminDAO.getGradeClass(grade_class_id);
	}

	@Override
	public boolean saveRegisterForclass(RegisterForClass regisclass) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		regisclass.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		regisclass.setChangeDate(changeDate);
		return AdminDAO.saveRegisterForclass(regisclass);
	}

	@Override
	public List<SchoolDays> getSchoolDays(School school) {
		return AdminDAO.getSchoolDays(school);
	}

	@Override
	public int AddSchoolDays(SchoolDays schooldays) {
		int s = 0;
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		schooldays.setChangeDate(changeDate);
		int n = AdminDAO.checkDayExists(schooldays);
		if (n > 0) {
			try {
				s = 1;
				AdminDAO.UpdateDays(schooldays);
			} catch (Exception e) {
				s = 0;
			}
		} else {
			schooldays.setCreateDate(new java.sql.Date(new java.util.Date()
					.getTime()));
			try {
				s = 1;
				AdminDAO.saveSchoolDays(schooldays);
			} catch (Exception e) {
				s = 0;
			}
		}
		return s;
	}

	@Override
	public void RemoveDays(SchoolDays schooldays) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		schooldays.setChangeDate(changeDate);
		AdminDAO.UpdateDays(schooldays);
	}

	@Override
	public Grade getGrade(long gradeId) {
		return gradesdao.getGrade(gradeId);
	}

	@Override
	public String SavePeriod(Periods period) {
	    return AdminDAO.SavePeriod(period);
	}

	@Override
	@RemoteMethod
	public List<Periods> getGradePeriods(long gradeId) {
		return AdminDAO.getGradePeriods(gradeId);
	}

	@Override
	public String UpdatePeriods(long periodId, String periodname,
			String starttime, String endtime, long gradeId) {
		return AdminDAO.UpdatePeriods(periodId, periodname, starttime, endtime,
				gradeId);
	}

	@Override
	public int checkPeriod(long periodId) {
		return AdminDAO.checkPeriod(periodId);
	}

	@Override
	public int deletePeriod(long periodId) {
		return AdminDAO.deletePeriod(periodId);
	}

	@Override
	public int deleteClassSchedulePeriod(long periodId) {
		return AdminDAO.deleteClassSchedulePeriod(periodId);
	}

	@Override
	public long getMasterGradeIdbyGradeId(long gradeId) {
		return gradesdao.getMasterGradeIdbyGradeId(gradeId);
	}

	@Override
	public int RemoveClass(long gradeId, long classId) {
		return AdminDAO.RemoveClass(gradeId, classId);
	}

	@Override
	public List<TeacherSubjects> getSchoolTeachers(School school){
		return AdminDAO.getSchoolTeachers(school);
	}
	@Override
	public List<Periods> getSchoolPeriods(School school){
		return AdminDAO.getSchoolPeriods(school);
	}
	
	
	public Section getSection(long sectionId){
		return AdminDAO.getSection(sectionId);
	}
	
	@Override
	@RemoteMethod
	public List<Student> getStudentsBySection(long csId) {
		return teacherDao.getStudentsBySection(csId);
	}

	public long getSectionIdByGradeClassIdAndGradeLevelId(long gradeClassId, long gradeLevelId){
		return AdminDAO.getSectionIdByGradeClassIdAndGradeLevelId(gradeClassId, gradeLevelId);
	}
		
	@Override
	public List<ClassStatus> getScheduledClassesByGradeNClass(long gradeId, long classId) throws SQLDataException{
		List<ClassStatus> cList = Collections.emptyList();
		try{
			cList = AdminDAO.getScheduledClassesByGradeNClass(gradeId, classId);
		}
		catch(Exception e){
			logger.error("error while getting the classes");
		}
		return cList;
	}

	@Override
	public List<StudentClass> getClassesWithOutHomeRoom(long gradeId) throws DataException {
		List<StudentClass> classes = Collections.emptyList();
		try{
			classes = AdminDAO.getClassesWithOutHomeRoom(gradeId);
		}catch(DataException e){
			logger.error("error while getting the classes");
		}
		return classes;
	}

	@Override
	public List<GradeClasses> getClassesByGrade(long gradeId) throws DataException {
		return AdminDAO.getGradeClassesByGrade(gradeId);
	}

	@Override
	public List<Student> getGradeStudentList(long gradeId) throws DataException {
		List<Student> cList = new ArrayList<Student>();
		try{
			cList = AdminDAO.getGradeStudentList(gradeId);
		}
		catch(Exception e){
			logger.error("error while getting the Students");
		}
		return cList;
	}

	@Override
	public boolean promoteStudents(long gradeId, long promoteGradeId, String[] studentIds) throws DataException {
		boolean status = false;
		try{			
			status = AdminDAO.promoteStudents(gradeId,promoteGradeId,studentIds);
		}
		catch(Exception e){
			logger.error("error while promote the Students"+e.getMessage());
			e.printStackTrace();
		}
		return status;
	}
	public boolean setParentToStudent(long parentRegId,String semailId){
		return AdminDAO.setParentToStudent(parentRegId, semailId);
	}
	@Override
	public List<LegendCriteria> getAllLegendCriteriaList(){
		List<LegendCriteria> cList = new ArrayList<LegendCriteria>();
		try{
			cList = AdminDAO.getAllLegendCriteriaList();
		}
		catch(Exception e){
			logger.error("error while getting AllLegendCriteria");
		}
		return cList;
	}
	@Override
	public List<LegendSubCriteria> getLegendSubCriteriasByCriteriaId(long legendCriteriaId){
		List<LegendSubCriteria> csubList = new ArrayList<LegendSubCriteria>();
		try{
			csubList = iolReportCardDao.getLegendSubCriteriasByCriteriaId(legendCriteriaId,6);
		}
		catch(Exception e){
			logger.error("error while getting AllLegendCriteria");
		}
		return csubList;
	}
	@Override
	public long createLIRubric(long criteriaId,long subCriteriaId,long rubricScore,String rubricDesc, UserRegistration uRegistration){
		String stat="no";
		long check=0;

		try{
			Legend legend=new Legend();
			legend.setLegendName(rubricDesc);
			legend.setLegendSubCriteria(iolReportCardDao.getLegendSubCriteria(subCriteriaId));
			legend.setLegendValue(rubricScore);
			legend.setIsDefault(stat);
			legend.setCreatedBy(uRegistration);
			legend.setStatus(WebKeys.ACTIVE);
			AdminDAO.createLIRubric(legend);
			check=1;
		}
		catch(Exception e){
			logger.error("error while getting AllLegendCriteria");
			check=0;
		}
		return check;
	}
	@Override
	public List<Legend> getRubricValuesBySubCriteriaId(long subCriteriaId, long createBy){
		List<Legend> subList = new ArrayList<Legend>();
		try{
			subList = iolReportCardDao.getRubricValuesBySubCriteriaId(subCriteriaId, createBy);
		}
		catch(Exception e){
			logger.error("error while getting AllLegendCriteria");
		}
		return subList;
	}
	public long editLIRubric(long subCriteriaId,long rubricScore,String rubricDesc, long regId){
		long check=0;
		try{
			check=iolReportCardDao.editLIRubric(subCriteriaId,rubricScore,rubricDesc, regId);
		}
		catch(Exception e){
			logger.error("error while getting AllLegendCriteria");
			check=0;
		}
		return check;
	}
	
	@Override
	@RemoteMethod
	public List<Grade> getSchoolGradesByAcademicYr(long schoolId) {
		return gradesdao.getGradesList(schoolId);

	}
	
	@Override
	public List<User> getUserTypesForAnnouncements() {
		List<User> userTypeList = null;
		userTypeList = userDao.getUserTypesForAnnouncements();
		return userTypeList;
	}
	
	@Override
	public List<StudentClass> getGradeClassesByGradeId(long gradeId) throws SQLDataException{
		return AdminDAO.getGradeClassesByGradeId(gradeId);
	}
}
