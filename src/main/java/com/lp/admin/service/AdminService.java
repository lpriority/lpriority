package com.lp.admin.service;
import java.sql.SQLDataException;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.GradeClasses;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.LegendSubCriteria;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.TeacherSubjects;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.model.MasterGrades;
import com.lp.model.Grade;
import com.lp.model.School;
import com.lp.model.StudentClass;
import com.lp.model.SubInterest;
import com.lp.model.GradeLevel;
import com.lp.model.Section;
import com.lp.model.SchoolDays;

public interface AdminService {

	public void addTeacher(UserRegistration user);

	public void addStudent(UserRegistration user);

	public void addParent(UserRegistration user);

	public List<MasterGrades> getMasterGrades();

	public List<Grade> getSchoolGrades(long schoolId);
	
	public void CreateClasses(StudentClass stuclass);

	public void setClassStrength(School school);

	public int CheckExistClass(StudentClass stuclass);

	public void addSubjectInterest(SubInterest subinterest);

	public List<School> getClassStrengthDetails(School school);

	public void AddGrades(Grade grade);

	public void RemoveGrades(Grade grade);

	public List<StudentClass> getStudentClass(School stuclass);

	public void saveGradeClass(GradeClasses gradeClasses);

	public int checkGradeClasses(long gradeId, long classId, long schoolId);

	public int AddClasses(long gradeId, long classId, String status, String operation);

	public List<UserRegistration> getStudentsBySchoolId(long schoolId);

	public List<GradeClasses> getGradeClasses(long schoolId, long gradeId);

	public List<StudentClass> getStudentClasses(long gradeId);
	
	public List<StudentClass> getAddedClasses(long gradeId);
	
	public List<GradeLevel> getStudentGradeLevels(long classId);
	
	public List<GradeClasses> getGradeClasses(long gradeId);
	
	public boolean CreateSections(long gradeclassId,String sectionName,String status,long gradelevelId);

	public int checkExistSection(long gradeclassId, long gradeLevelId, String sectionName);
	
	public List<Section> getAllSections(long gradeId,long classId);
	
	public List<Section> getSectionsByGradeLevel(long gradeId,long classId, long gradeLevelId);
	
	public List<Section> getAllSectionsByHomeRoom(long gradeId,long schoolId);
	
	public List<UserRegistration> getAllParentEmailIds(School school,long userTypeId);
	
	public List<UserRegistration> getAllUserRegistrationsBySchool(School school, long userTypeId);
	
	public long checkSecurityExists(UserRegistration userreg);
	
	public int UpdateSections(long sectionId,String sectionName);
	
	public int DeleteSections(long sectionId,String sectionName);
	
	public int checkSections(long sectionId);
	
	public GradeClasses getGradeClass(long grade_class_id);
	
	public boolean saveRegisterForclass(RegisterForClass regisclass);
	
	public List<UserRegistration> getAllStudentEmailIds(School school,long userTypeId,long userTypeid);

	public List<SchoolDays> getSchoolDays(School school);

	public int AddSchoolDays(SchoolDays schooldays);
	
	public void RemoveDays(SchoolDays schooldays);
	
	public Grade getGrade( long gradeId);

	public String SavePeriod(Periods period);
	
	public List<Periods> getGradePeriods(long gradeId);
	
	public Section getSection(long sectionId);
	
	public String UpdatePeriods(long periodId,String periodname,String starttime,String endtime,long gradeId);
	
	public int checkPeriod(long periodId);
	
	public int deleteClassSchedulePeriod(long periodId);
	
	public int deletePeriod(long periodId);
	
	public long getMasterGradeIdbyGradeId(long gradeId);
	
	public int RemoveClass(long gradeId,long classId);
	
	public List<TeacherSubjects> getSchoolTeachers(School school);
	
	public List<Periods> getSchoolPeriods(School school);
	
	public List<Student> getStudentsBySection(long csId);
	
	public long getSectionIdByGradeClassIdAndGradeLevelId(long gradeClassId, long gradeLevelId);
	
	public List<ClassStatus> getScheduledClassesByGradeNClass(long gradeId, long classId) throws SQLDataException;

	public List<StudentClass> getClassesWithOutHomeRoom(long gradeId)throws DataException;

	public List<GradeClasses> getClassesByGrade(long gradeId)throws DataException;

	public List<Student> getGradeStudentList(long gradeId) throws DataException;

	public boolean promoteStudents(long gradeId, long promoteGradeId, String[] studentIds) throws DataException;
	
	public boolean setParentToStudent(long parentRegId,String semailId);
	
	public List<LegendSubCriteria> getLegendSubCriteriasByCriteriaId(long legendCriteriaId);
	
	public List<LegendCriteria> getAllLegendCriteriaList();
	
	public long createLIRubric(long criteriaId,long subCriteriaId,long rubricScore,String rubricDesc, UserRegistration uRegistration);
	
	public List<Legend> getRubricValuesBySubCriteriaId(long subCriteriaId, long createBy);
	
	public long editLIRubric(long subCriteriaId,long rubricScore,String rubricDesc, long regId);
	
	public List<Grade> getSchoolGradesByAcademicYr(long schoolId);
	
	public List<User> getUserTypesForAnnouncements();
	
	public List<StudentClass> getGradeClassesByGradeId(long gradeId) throws SQLDataException;
}
