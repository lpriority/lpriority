package com.lp.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("AddStudentsToClass")
public class AddStudentsToClassDAOImpl extends CustomHibernateDaoSupport implements AddStudentsToClassDAO {

	private JdbcTemplate jdbcTemplate;
	
	static final Logger logger = Logger.getLogger(AddStudentsToClassDAOImpl.class);


	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
	public Grade getSchoolGrade(long gradeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Grade> getGradesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGrades(long gradeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGrades(Grade Grade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Grade> getGradesList(long schoolId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateGrades(Grade grade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int checkgradeExists(Grade grade) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Grade getGrade(long gradeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMasterGradeIdbyGradeId(long gradeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getAllClassStudents(long gradeLevelId,
			long gradeclassId, long csId) throws DataException{
		List<RegisterForClass> list = new ArrayList<RegisterForClass>();		
		try {

			list= (List<RegisterForClass>) getHibernateTemplate().find(
					"from RegisterForClass where gradeClasses.gradeClassId="+gradeclassId
							+ "and classStatus_1='"+WebKeys.ALIVE+"' and gradeLevel.gradeLevelId="+gradeLevelId+" and "
									+ "((classStatus.csId="+csId+" and status='"+WebKeys.ACCEPTED+"') or (status='"+WebKeys.WAITING+"' or status='"+WebKeys.LP_STATUS_NEW+"')) order by student.userRegistration.lastName");
			
		
		} catch (DataAccessException e) {
			logger.error("Error "
					+ e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		return list;
	}
	
	@Override
	public boolean addStudentToClass(long studentId,long gClassId,long csId) throws DataException{
			boolean updateStuas = false;
		try {
			String query = "update register_for_class set cs_id=?, status='"+WebKeys.ACCEPTED+"' where student_id=? and grade_class_id=?";

			int out = jdbcTemplate.update(query, csId, studentId,gClassId);
			if (out != 0) {
				updateStuas = true;
				
			} else {
				updateStuas = false; }

		} catch (DataAccessException e) {
			logger.error("Error in addStudentToClass() of AddStudentsToClassDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		return updateStuas;
	}


	public boolean removeStudentFromClass(long studentId,long gClassId,long csId) throws DataException{
			boolean updateStuas = false;
		try {
			String query = "update register_for_class set cs_id="+csId+", status='"+WebKeys.WAITING+"' where student_id=? and grade_class_id=? and cs_id=?";

			int out = jdbcTemplate.update(query, studentId,gClassId,csId);
			if (out != 0) {
				updateStuas = true;
				
			} else {
				updateStuas = false; }

		} catch (DataAccessException e) {
			logger.error("Error "
					+ e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		return updateStuas;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getDayIdPeriodIds(long csId) throws DataException {
		
			List<ClassActualSchedule> classActualSch  = new ArrayList<ClassActualSchedule>();
			try {
				classActualSch = (List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where classStatus.csId="+csId);
			} catch (DataAccessException e) {
				logger.error("Error in getDayIdPeriodIds() of AddStudentsToClassImpl"
						+ e);
				e.printStackTrace();
				throw new DataException("Unexpected System error....");
			}
			if(classActualSch.size()>= 0 )
				return classActualSch;
			else
				throw new DataException("Class doesn't have any Day periods..");
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getClassStrengthByCsId(long csId) throws DataException {
		// TODO Auto-generated method stub
		List<RegisterForClass> regForclass = null;
		try {
			List<RegisterForClass> find = (List<RegisterForClass>) getHibernateTemplate().find("from RegisterForClass where classStatus.csId="+csId +""
					+ " and status ='"+WebKeys.ACCEPTED+"' and classStatus_1='"+WebKeys.ALIVE+"' order by student.userRegistration.lastName");
			regForclass = find;
		} catch (DataAccessException e) {
			logger.error("Error in getClassStrengthByCsId() of AddStudentsToClassDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		if(regForclass.size()>= 0 )
			return regForclass;
		else
			throw new DataException("Students not found for this class");
	}
	@SuppressWarnings("unchecked")
	@Override
	public Student getStudent(long studentId) throws DataException {
		List<Student> student = null;
		try {
			student = (List<Student>) getHibernateTemplate().find("from Student where studentId="+studentId );
		} catch (DataAccessException e) {
			logger.error("Error in getDayIdPeriodIds() of AddStudentsToClassImpl"
					+ e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		if(student.size() > 0 )
			return student.get(0);
		else
			throw new DataException("Student not found in the database");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getAllClassStudentsWithoutLevel(
			long gradeclassId, long csId) throws DataException {
		List<RegisterForClass> list = new ArrayList<RegisterForClass>();		
		try {

			list= (List<RegisterForClass>) getHibernateTemplate().find(
					"from RegisterForClass where gradeClasses.gradeClassId="+gradeclassId
					+ "and classStatus_1='"+WebKeys.ALIVE+"' and ((classStatus.csId="+csId+" and status='"+WebKeys.ACCEPTED+"') or "
							+ "(status='"+WebKeys.WAITING+"' or status='"+WebKeys.LP_STATUS_NEW+"')) order by student.userRegistration.lastName");
			
		
		} catch (DataAccessException e) {
			logger.error("Error in getAllClassStudentsWithoutLevel() of AddStudentsToClassImpl"+ e);
			throw new DataException("Unexpected System error....");
		}
		return list;
	}
	
	
}
