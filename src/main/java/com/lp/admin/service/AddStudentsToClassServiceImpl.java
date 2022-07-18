package com.lp.admin.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AddStudentsToClassDAO;
import com.lp.admin.dao.AdminDAO;
import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassActualSchedule;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "AddStudentsToClassService")
public class AddStudentsToClassServiceImpl implements AddStudentsToClassService {

	@Autowired
	private AdminSchedulerDAO adminSchedulerdao;
	@Autowired
	private AddStudentsToClassDAO addStudentsToClassDAO;
	@Autowired
	private AdminDAO AdminDAO;
	@Autowired
	private HttpSession session;
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;	

	static final Logger logger = Logger.getLogger(AddStudentsToClassServiceImpl.class);

	@Override
	@RemoteMethod
	public List<RegisterForClass> getStudentList(long gradeId,long classId,long csId) throws DataException{
		
		List<RegisterForClass> registerForClasses = null;
		long gradeLevelId = adminSchedulerdao.getGradeLevelIdByCsId(csId);
		if(gradeLevelId == 0)
			 throw new DataException("No grade level id for this section");
		long gradeclassId=AdminDAO.getGradeClassId(gradeId,classId);
		if(gradeclassId == 0)
			 throw new DataException("No grade class id for this class");
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			userReg = userRegistrationDAO.getUserRegistration(userReg.getRegId());
			session.setAttribute(WebKeys.USER_REGISTRATION_OBJ,userReg);
			String leveling = userReg.getSchool().getLeveling();
			if(leveling != null && leveling.equalsIgnoreCase("yes")){
				registerForClasses =  addStudentsToClassDAO.getAllClassStudents(gradeLevelId,gradeclassId,csId);
			}else{
				registerForClasses =  addStudentsToClassDAO.getAllClassStudentsWithoutLevel(gradeclassId,csId);
			}
				
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}		
		return registerForClasses;
	}
	
	@RemoteMethod
	public boolean addStudentToClass(HttpSession session, long studentId,long gClassId,long csId) throws DataException{
		boolean updateStatus =false;
		boolean studentAvailability =false;
	    long classStrength;
		try{
			 if(csId == 0)
				 throw new DataException("No teacher is scheduled for this class");
			 
			 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			 userReg = userRegistrationDAO.getUserRegistration(userReg.getRegId());
			 session.setAttribute(WebKeys.USER_REGISTRATION_OBJ,userReg);
			 
			 if(userReg != null){
				 String genderEquity =  userReg.getSchool().getGenderEquity();
				 int noOfBoys = getStudentGenderCount(csId,WebKeys.LP_MALE);
				 int noOfGirls = getStudentGenderCount(csId,WebKeys.LP_FEMALE);
				 classStrength = userReg.getSchool().getClassStrength();

				 if(genderEquity != null && !genderEquity.isEmpty()){
					 if(genderEquity.equalsIgnoreCase("yes") && classStrength > 0){
						 boolean genderEquityCheck = checkClassStrengthByGenderEquity(classStrength, studentId, noOfBoys, noOfGirls);
						 if(!genderEquityCheck){
							 throw new DataException("This Gender is full in this Class. Please add different gender..");
						 }
					 }
				 }
				 //	checking Student Availability by time
				 studentAvailability = checkStudentAvailabilityByTime(studentId,csId);
				 if(studentAvailability){
					 if(classStrength > 0){
						 //	Checking class strength
						 if(checkClassStrengthByCsId(csId,classStrength)) 
							 updateStatus= addStudentsToClassDAO.addStudentToClass(studentId, gClassId, csId);
					 }
					 else{
						 updateStatus= addStudentsToClassDAO.addStudentToClass(studentId, gClassId, csId);
					 }
				 }
			 } else{
				 throw new DataException("User doesn't exist");
			 }
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}
		return updateStatus;
	}

	@Override
	public boolean removeStudentFromClass(long studentId, long gClassId,long csId) throws DataException{
		boolean updateStatus =false;
		try{
			 updateStatus= addStudentsToClassDAO.removeStudentFromClass(studentId, gClassId,csId);
				
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}
		return updateStatus;
	}
	private boolean checkStudentAvailabilityByTime(long studentId,long csId) throws DataException{
		boolean updateStutus = false; 
		List<ClassActualSchedule> classActualSch = null;
		try{
			classActualSch = addStudentsToClassDAO.getDayIdPeriodIds(csId);
			for (int cas = 0; cas < classActualSch.size(); cas++) {
				 long periodId = classActualSch.get(cas).getPeriods().getPeriodId();
				 long dayId = classActualSch.get(cas).getDays().getDayId();
				 updateStutus = adminSchedulerdao.checkStudentAvailabilityByTime(studentId, periodId, dayId);
				 if(!updateStutus)
					 throw new DataException("This Student is already scheduled for another class for this period. ..");
		     }		
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}
		
		 	 
		return updateStutus;
	}
	private boolean checkClassStrengthByCsId(long csId, long actualClassStrength) throws DataException{
		List<RegisterForClass> studentClassStrength;
		try{
			studentClassStrength =   addStudentsToClassDAO.getClassStrengthByCsId(csId);
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}
		if(studentClassStrength.size() < actualClassStrength)
			return true;
		else
			return false;
	}
	
	private int getStudentGenderCount(long csId,String gender) throws DataException{
		List<RegisterForClass> studentClassStrength;
		int count = 0;
		try{
			studentClassStrength =   addStudentsToClassDAO.getClassStrengthByCsId(csId);
			for(RegisterForClass stuClass : studentClassStrength){
				if(stuClass.getStudent().getGender().equalsIgnoreCase(gender))
					count ++;
			}
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}
		
		return count;
	}	
		
	public boolean checkClassStrengthByGenderEquity(long classStrength,long studentId,int noOfBoys,int noOfGirls) throws DataException{
		Student stu = null;
		try{
			stu = addStudentsToClassDAO.getStudent(studentId);
		}catch(DataException e){
			throw new DataException(e.getMessage());
		}
		if(noOfBoys + noOfGirls == classStrength ){
			throw new DataException("Class Strength is full, You can not add students to this Class..");
		}		
		if(classStrength % 2 !=0 ){
			 if (classStrength / 2 + 1 == noOfBoys && stu.getGender().equalsIgnoreCase(WebKeys.LP_MALE)) 
				 return false;
			 if (classStrength / 2 + 1 == noOfGirls && stu.getGender().equalsIgnoreCase(WebKeys.LP_FEMALE))
				 return false;
		}else{
			if (classStrength / 2  == noOfBoys && stu.getGender().equalsIgnoreCase(WebKeys.LP_MALE)) 
			 return false;
		 if (classStrength / 2  == noOfGirls && stu.getGender().equalsIgnoreCase(WebKeys.LP_FEMALE))
			 return false;
		}
		return true;
	}	
	
	@Override
	public String getTeacherName(long sectionId) {
		long csId = adminSchedulerdao.getCsIdBySectionId(sectionId);
		String teacherFullName = "No teacher is scheduled for this class";
		if(csId > 0){
			long teacherId = adminSchedulerdao.getclassStatus(csId).getTeacher().getTeacherId();
			Teacher teacher = adminSchedulerdao.getTeacher(teacherId);
			String teacherTitle = teacher.getUserRegistration().getTitle();
			String teacherFirstName = teacher.getUserRegistration().getFirstName();
			String teacherLastName = teacher.getUserRegistration().getLastName();
			teacherFullName = teacherTitle+". "+teacherFirstName +" "+teacherLastName;
		}		
		return teacherFullName;
	}
}