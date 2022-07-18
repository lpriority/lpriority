package com.lp.student.dao;


import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.RegisterForClass;
import com.lp.model.UserRegistration;

public interface RegisterForClassDAO {	
	public List<RegisterForClass> getAllRegisterForClass();
	public boolean saveRegisterForClass(List<RegisterForClass> registerForClasses);
	public boolean expireAllClasses(long studentId) throws DataException;
	public boolean expireAllClasses(UserRegistration userRegistration) throws DataException;
	
	public boolean uploadClassRosters(List<RegisterForClass> registerForClasses);
	
	public List<ClassStatus> getScheduledClasses();
	public List<RegisterForClass> getNewStudents();
	public boolean saveRegisterForClasses(List<RegisterForClass> registerForClasses);

	
}
