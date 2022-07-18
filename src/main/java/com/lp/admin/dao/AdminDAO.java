package com.lp.admin.dao;

import java.sql.SQLDataException;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.GradeClasses;
import com.lp.model.GradeLevel;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.Section;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;

public interface AdminDAO {

	public void CreateClasses(StudentClass stuclass);

	public void setClassStrength(School school);

	public int checkClassExists(StudentClass stuclass);

	public List<School> getClassStrengthDetails(School school);

	public int checkGradeClasses(long gradeId, long classId, long schoolId);

	public int AddClasses(long gradeId, long classId, String status, String opperation);

	public List<GradeClasses> getGradeClasses(long gradeId);

	public List<StudentClass> getStudentClasses(long gradeId);
	
	public List<StudentClass> getAddedClasses(long gradeId);

	public List<GradeLevel> getStudentGradeLevels(long classId);

	public long getGradeClassId(long gradeId, long classId);

	public boolean CreateSections(long gradeclassId, String sectionName,
			String status, long gradelevelId);

	public int checkExistSection(long gradeclassId, long gradeLevelId,
			String sectionName);

	public List<Section> getAllSections(long gradeId, long classId);

	public List<Section> getAllSectionsByHomeRoom(long gradeId, long schoolId);

	public Section getSection(long sectionId);

	public List<UserRegistration> getAllParentEmailIds(School school,
			long userTypeId);
	
	public List<UserRegistration> getAllUserRegistrationsBySchool(School school, long userTypeId);

	public long checkSecurityExists(UserRegistration userreg);

	public int UpdateSections(long sectionId, String sectionName);

	public int DeleteSections(long sectionId, String sectionName);
	
	public int checkSections(long sectionId);

	public String getClassName(long classId);

	public GradeClasses getGradeClass(long grade_class_id);

	public boolean saveRegisterForclass(RegisterForClass regisclass);

	public List<UserRegistration> getAllStudentEmailIds(School school,
			long userTypeId, long usertypeid);

	public List<SchoolDays> getSchoolDays(School school);

	public int checkDayExists(SchoolDays schooldays);

	public void UpdateDays(SchoolDays schooldays);

	public void saveSchoolDays(SchoolDays schooldays);

	public String SavePeriod(Periods period);

	public List<Periods> getGradePeriods(long gradeId);

	public String UpdatePeriods(long periodId, String periodname,
			String starttime, String endtime, long gradeId);

	public int checkPeriod(long periodId);

	public int deletePeriod(long periodId);

	public Periods getPeriod(long periodId);

	public int deleteClassSchedulePeriod(long periodId);

	public int RemoveClass(long gradeId, long classId);

	public List<TeacherSubjects> getSchoolTeachers(School school);

	public List<Periods> getSchoolPeriods(School school);

	public List<StudentClass> getAllClasses();

	public List<GradeClasses> getAllGradeClasses();

	public GradeClasses saveGradeClasses(GradeClasses gradeClasses);

	public List<Section> getSectionsByGradeLevel(long gradeId, long classId,
			long gradeLevelId);
	
	public long getSectionIdByGradeClassIdAndGradeLevelId(long gradeClassId, long gradeLevelId);
	
	public List<ClassStatus> getScheduledClassesByGradeNClass(long gradeId, long classId) throws SQLDataException;

	public List<StudentClass> getClassesWithOutHomeRoom(long gradeId) throws DataException;

	public List<GradeClasses> getGradeClassesByGrade(long gradeId) throws DataException;

	public List<Student> getGradeStudentList(long gradeId) throws DataException;

	public boolean promoteStudents(long gradeId, long promoteGradeId, String[] studentIds) throws DataException;
	
	public boolean setParentToStudent(long parentRegId,String semailId);
	
    public List<LegendCriteria> getAllLegendCriteriaList();
    
    public void createLIRubric(Legend legend);
    
    public List<StudentClass> getGradeClassesByGradeId(long gradeId) throws SQLDataException;
}
