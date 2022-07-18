package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("showassignedassessmentdao")
public class ShowAssignedAssessmentDAOImpl extends CustomHibernateDaoSupport
		implements ShowAssignedAssessmentDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private	HttpSession session;
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	@Override
	public List<Assignment> getTeacherAssignedDates(Teacher teacher,long csId,String usedFor){
		List<Assignment> assignmentDates = new ArrayList<>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			
			String query = "select distinct date_assigned from student_assignment_status sas, assignment a where "
	        		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.student_id is Not Null and a.used_for=?"
	        		+ " and assign_status='"+WebKeys.ACTIVE+"'";
			SqlRowSet rs = null;
			
			if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER) || 
					userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)){
				query = query+" and assignment_type_id not in( select assignment_type_id from assignment_type"
						+ " where assignment_type in('"+WebKeys.EARLY_LITERACY_LETTER+"','"
						+WebKeys.EARLY_LITERACY_WORD+"','"+ WebKeys.PHONIC_SKILL_TEST+ "','"
								+ WebKeys.LP_MATH_ASSESMENT+"','"+WebKeys.GEAR_GAME+"'))";
				
				rs = jdbcTemplate.queryForRowSet(query,csId,usedFor);
			}else{
				rs = jdbcTemplate.queryForRowSet(query,csId,usedFor);
			}
			
			while(rs.next()){
		        Assignment assignment = new Assignment(); 
		        assignment.setDateAssigned(rs.getDate(1));
		        assignmentDates.add(assignment);
	        }
					
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentDates;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAssignedAssessmentByDate(Teacher teacher,long csId, String usedFor){
		List<Assignment> assignmentList = null;
		try {
			assignmentList = (List<Assignment>) getHibernateTemplate().find(
					"from Assignment where classStatus.csId=" + csId
							+ " and " + "classStatus.teacher.teacherId="
							+ teacher.getTeacherId() + " and usedFor='"+usedFor+"' and assign_status='"+WebKeys.ACTIVE+"'");
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentList;
	}
	@Override
	public int updateAssignments(long assignmentId, String duedate){
		int out=0; 
		try{
		String query = "update assignment set date_due=? where assignment_id=?";
        out = jdbcTemplate.update(query,duedate,assignmentId);
          	
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return out;
	    }
	@Override
	public int deleteAssignments(long assignmentId){
		int out=0; 
		try{
         String query = "update assignment set assign_status=? where assignment_id=?";
        
         out = jdbcTemplate.update(query,WebKeys.LP_STATUS_INACTIVE,assignmentId);
		}catch(Exception e){
			e.printStackTrace();
        	return 0;
	    }
		return out;
	}
	@Override
	public List<Assignment> getGroupAssignedDates(Teacher teacher, long csId,
			String usedFor) throws DataException {
		List<Assignment> assignmentDates = new ArrayList<>();	
		try{
	        String query = "select distinct date_assigned from student_assignment_status sas, assignment a where "
	         		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.performance_group_id is Not Null"
	         		+ " and a.assign_status='"+WebKeys.ACTIVE+"'";
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query,csId);
	        while(rs.next()){
		        Assignment assignment = new Assignment(); 
		        assignment.setDateAssigned(rs.getDate(1));
		        assignmentDates.add(assignment);
	        }
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentDates;
	}
	@Override
	public List<Assignment> getGroupAssignmentTitles(long csId, String usedFor,
			String assignedDate) throws DataException {
		List<Assignment> assignmentList = new ArrayList<>();	
		try {
			
			String query = "select distinct a.assignment_id, a.title from student_assignment_status sas, assignment a where "
		        		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.performance_group_id is Not Null and a.assign_status='"+WebKeys.ACTIVE+"'"
		        		+ " and date_assigned=?";
		     
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate);
			
			while(rs.next()){
		        Assignment assignment = new Assignment(); 
		        assignment.setAssignmentId(rs.getLong(1));
		        assignment.setTitle(rs.getString(2));
		        assignmentList.add(assignment);
	        }
					
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentList;
	}
}
	

	
