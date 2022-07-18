package com.lp.admin.service;

import java.util.List;

import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.HomeroomClasses;
import com.lp.model.Periods;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.SchoolSchedule;
import com.lp.model.Section;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;

public interface AdminSchedulerService {
	
	public boolean checkPeriodsBySchoolId(School school);
	
	public boolean deletePeriods(School school);
	
	public boolean makeClassesExpired(School school);
	
	public boolean createTimeTable(int periodRange, int passingTime,
			int homeRoomTime, Long daystarttime, Long daystarttimemin,
			String daystarttimemeridian, Long dayendtime, Long dayendtimemin,
			String dayendtimemeridian, School school);

    public boolean addSchoolSchedule(SchoolSchedule schoolschedule);
    
    public boolean updateSchoolSchedule(SchoolSchedule schoolschedule);
    
    public boolean checkSchoolSchedule(School school);
    
    public SchoolSchedule getSchoolSchedule(School school);
    
    public List<TeacherSubjects> getTeacherSubjects(School school);
    
    public List<SchoolDays> getSchoolDays(School school);
    
    public long getClassIdbyClassName(School school,String className);
    
    public boolean checkHomeSectionAvailabilityforTeacher(long sectionId);
    
    public boolean checkTeacherAvailabilityforHomeRoom(long teacherId);
    
    public long createHomeRooms(Teacher teacher, Grade grade, Section section);
    
    public void saveSchoolSchedule(SchoolSchedule schoolSchedule);
    
    public List<Periods> getSchoolPeriods(long gradeId);
    
    public boolean checkSectionAvailabilityforTeacher(long sectionId,long teacherId);
    
    public long getNoofDaysBySectionId(long sectionId);

    public long getNoofSectionsByDay(long dayId,long sectionId);
    
    public long getNoofSectionsByTeacherRegId(long dayId,long teacherId);
    
    public boolean checkTeacherAvailability(long gradeId, long classId, long teacherId,long dayId, Periods period);
    
    public long prepareSchedule(ClassStatus cs, ClassActualSchedule cas);
        
    public Periods getHomeRoomPeriod(School school, long gradeId);  
    
    public Periods getPeriod(long periodId);
    
    public Teacher getTeacher(long teacherId);
    
    public boolean SetHomeroomClassForTeacher(HomeroomClasses homeroomclass);
    
    public boolean scheduleTeachers(SchoolSchedule schoolSchedule);
}
