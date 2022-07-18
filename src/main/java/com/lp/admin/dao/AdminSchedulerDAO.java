package com.lp.admin.dao;

import java.util.List;

import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.HomeroomClasses;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.SchoolSchedule;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;

public interface AdminSchedulerDAO {

	public boolean checkPeriodsBySchoolId(School school);
	
	public boolean deletePeriods(School school);
	
	public boolean makeClassesExpired(School school);
	
	public boolean addSchoolSchedule(SchoolSchedule schoolschedule);
	
	public boolean updateSchoolSchedule(SchoolSchedule schoolschedule);
	
	public SchoolSchedule getSchoolSchedule(School school);
	
	public boolean checkSchoolSchedule(School school);
	
	public List<TeacherSubjects> getTeacherSubjects(School school);
	
	public List<SchoolDays> getSchoolDays(School school);
	
	public long getClassIdbyClassName(School school,String className);
	
	public boolean checkHomeSectionAvailabilityforTeacher(long sectionId);
	
	public boolean checkTeacherAvailabilityforHomeRoom(long teacherId);
	
	public Periods getHomeRoomPeriod(School school,long gradeId);
	
	public boolean SetHomeroomClassForTeacher(HomeroomClasses homeroomclass);
	
	public boolean SetHomeroomClassForTeacher(HomeroomClasses homeroomclass, List<Student> studentList);
	
	public Teacher getTeacher(long teacherId);
	
	public List<Student> getStudentsBySchoolId(long gradeId);
	
	@SuppressWarnings("rawtypes")
	public java.util.ArrayList getStudentCountfromChild(long sectionId);
	
	public String getStudentGenderByRegId(long reg_id);
	
	public void UpdatechildHomeRoom(long gradeId,long regId,long sectionId);
	
	public void saveSchoolSchedule(SchoolSchedule schoolSchedule);
	
	public List<Periods> getSchoolPeriods(long gradeId);
	
	public boolean checkSectionAvailabilityforTeacher(long sectionId,long teacherId);
	
	public long getNoofDaysBySectionId(long sectionId);
	
	public long getNoofSectionsByDay(long dayId,long sectionId);
	
	public long getNoofSectionsByTeacherRegId(long dayId,long teacherId);
	
	public boolean checkTeacherAvailability(long teacherId,long dayId,long periodId);
	
	public long getGradeLevelId(long sectionId);
	
	public void UpdateClassStatus(java.util.Date date,java.util.Date date2,School school);
	
	public long getcsId(long gradeclassId);
	
	public ClassStatus getClassStatus(long teacherRegId,long sectionId);
	
	public List<RegisterForClass> getStudentsbyGradeLevelId(long gradeclassId, long gradeLevelId);
	
	public List<RegisterForClass> getStudentsbygradeclassId(long gradeclassId);
	
	public List<RegisterForClass> getStudentsByTeacherGradelevel(long gradeclassId,long gradeLevelId,long teacherId);
	
	public boolean checkStudentAvailabilityByTime(long regId, long periodId, long dayId);
	
	public boolean SaveClassStatus(ClassStatus cs);
	
    public long getCsIdBySectionId(long sectionId);
    
    public List<RegisterForClass> getStudentsByTeacher(long gradeclassId, long teacherRegId);
    
    public void UpdateRegisterForClass(long sectionId,long cs_id,long gradeclassId,long regId);
    
    public List<RegisterForClass> getStudentsByCsId(long cs_id);
    
	public ClassStatus getclassStatus(long csId);
	
	public boolean SaveClassActualSchedule(ClassActualSchedule clasactschedule);
	
	public HomeroomClasses getHomeRoomByTeacher(long teacherId,long periodId);
	
	public String getSectionName(long sectionId);
	
	 public List<ClassActualSchedule> getClassSchedule(long regId,long periodId);
	
	public String getGradethvalue(long gradeId);
	
	public List<Long> getDayIdsByCsId(long csId, long periodId);
	
	public long getGradeLevelIdByCsId(long csId);
}
