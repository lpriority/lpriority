package com.lp.teacher.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AdminDAO;
import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.Section;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "teacherSchedulerService")
public class TeacherSchdulerServiceImpl implements TeacherSchedulerService {
	@Autowired
	private TeacherDAO teacherDao;
	@Autowired
	private AdminDAO adminDAO;
	
	@Override
	public List<UserRegistration> getTeachersByReg(long schoolId, long userTypeId) {		
		return teacherDao.getTeachersByReg(schoolId, userTypeId);
	}
	@Override
	@RemoteMethod
	public List<Teacher> getTeachers(long gradeId, long classId) {
		return teacherDao.getTeachers(gradeId, classId);
	}
	@Override
	public List<Section> getSections(long gradeId, long classId){
		return teacherDao.getSections(gradeId, classId);
	}
	@Override
	public List<ClassActualSchedule> getCSIds(long gradeId, long classId,long teacherId, long schoolId){
		return teacherDao.getCSIds(gradeId, classId, teacherId, schoolId);
	}
	@Override
	@RemoteMethod
	public boolean planSchedule(long gradeId, long classId, long teacherId,
			long dayId,long schoolId, String startDate, String endDate,String periodIds,String sectionIds,String casIds) {
		long csId = 0;
        boolean inserted = false;
		Date stDate ; 
		Date enDate ;
		List<Long> csIdLt = new ArrayList<Long>();
		DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
		   try {
			   stDate = formatter.parse(startDate);
			   enDate = formatter.parse(endDate);
			   String[] peroids =   SplitUsingTokenizer(periodIds, "^");   
			   String[] sections =   SplitUsingTokenizer(sectionIds, "^");
			   String[] casId =   SplitUsingTokenizer(casIds, "^"); 
		   
			   for (int i = 0; i < sections.length; i++) {
				   long section_id= Long.parseLong(sections[i]);
				   long cas_Id = 0;
				   if(casId.length > 0){
					    cas_Id= Long.parseLong(casId[i]);
					}
				   csId = teacherDao.setClassStatus(section_id, teacherId, WebKeys.AVAILABLE, stDate, enDate,cas_Id);
				   csIdLt.add(csId);
			    }
			   for (int i = 0; i < peroids.length; i++) {
				   long peroids_id= Long.parseLong(peroids[i]);
				   long section_id= Long.parseLong(sections[i]);
				   long cas_Id = 0;
				   if(casId.length > 0){
				    cas_Id= Long.parseLong(casId[i]);
				   }
				   if(section_id != 0 && csIdLt.get(i) != 0){
					   inserted = teacherDao.setClassActualStatus(cas_Id,csIdLt.get(i), dayId, peroids_id,teacherId,section_id);
				   }else{
					   inserted = true;
				   }
				}
			   if(inserted){
				  List<ClassActualSchedule> casLt =  teacherDao.getCSIds(gradeId, classId, teacherId, schoolId);
				  if(casLt.size() > 0){
					  teacherDao.setTeacherReplyAction(gradeId, classId, teacherId, WebKeys.ACCEPTED);
				  }else{
					  List<ClassStatus> csLt = teacherDao.getClassStatus(gradeId, classId, teacherId); 
					  if(csLt.size() > 0){
						  for (ClassStatus classStatus : csLt) {
							  teacherDao.setClassStatusAsExpired(classStatus);
						  }
					  }
					  teacherDao.setTeacherReplyAction(gradeId, classId, teacherId, "");
				  }
			
			   }

		   } catch (ParseException e) {
				e.printStackTrace();
			}
		return inserted;
	}
	public String[] SplitUsingTokenizer(String subject, String delimiters) {
		   StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
		   ArrayList<String> arrLis = new ArrayList<String>(subject.length());

		   while(strTkn.hasMoreTokens())
		      arrLis.add(strTkn.nextToken().trim());

		   return arrLis.toArray(new String[0]);
	}
	@Override
	@RemoteMethod
	public boolean checkTeacherAvailability(long gradeId, long classId, long teacherId, long dayId,long periodId) {
		 return teacherDao.checkTeacherAvailability(gradeId, classId, teacherId, dayId, adminDAO.getPeriod(periodId));
	 }
	@Override
	@RemoteMethod
	public boolean checkSectionAvailabilityforTeacher(long sectionId,long teacherId) {
		return teacherDao.checkSectionAvailabilityforTeacher(sectionId, teacherId);
	}
	
	public List<TeacherSubjects> getTeachersRequests(long schoolId){
		return teacherDao.getTeachersRequests(schoolId);
	}
	
	public boolean setTeacherReplyAction(long gradeId, long classId, long teacherId, String status){
		return teacherDao.setTeacherReplyAction(gradeId, classId, teacherId, status);
	}
	@Override
	@RemoteMethod
	public List<StudentClass> getStudentClasses(long gradeId) {
		Map <Long, String> stdClsMap =   new LinkedHashMap <Long,String>();
		List<StudentClass> studentClassLt = teacherDao.getStudentClasses(gradeId);
		List<StudentClass> finalStudentClassLt = new ArrayList<StudentClass>();
		for (StudentClass studentClass : studentClassLt) {
			if(!stdClsMap.containsKey(studentClass.getClassId()) 
					&& !studentClass.getClassName().equalsIgnoreCase(WebKeys.LP_CLASS_HOMEROME)){ 
				
				stdClsMap.put(studentClass.getClassId(),studentClass.getClassName());
				finalStudentClassLt.add(studentClass);
			}
		}
		return  finalStudentClassLt;
	}
}