package com.lp.teacher.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.model.StudentPhonicTestMarks;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("gradePhonicSkillTestDAO")
public class GradePhonicSkillTestDAOImpl extends CustomHibernateDaoSupport implements GradePhonicSkillTestDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;

	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean submitStudentPhonicTestMarks(long studentAssignmentId,
			long groupId, long lastSavedSetId, int totalMarks, int secmarks, String marksStr,
			String commentStr) {
		boolean status = true;
		boolean isCompleted = true;
		try{
		  String update_student_phonic_test_marks_query = "update student_phonic_test_marks set max_marks="+totalMarks+",sec_marks="+secmarks+",marks_string='"+marksStr+"',comments='"+commentStr+"' where student_assignment_id= "+studentAssignmentId+" AND group_id= "+groupId;
		  jdbcTemplate.update(update_student_phonic_test_marks_query);
		  
		  List<StudentPhonicTestMarks> studentPhonicTestMarksLt = null;	
			try{
				studentPhonicTestMarksLt =(List<StudentPhonicTestMarks>) getHibernateTemplate().find("from StudentPhonicTestMarks where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+" order by phonicGroups.phonicSections.phonicSectionId, phonicGroups.groupOrder");
			    
				if(studentPhonicTestMarksLt != null && studentPhonicTestMarksLt.size() > 0){
					for (StudentPhonicTestMarks studentPhonicTestMarks : studentPhonicTestMarksLt) {
						if(studentPhonicTestMarks.getPhonicGroups().getGroupId() == lastSavedSetId){
							break;
						}
						if(studentPhonicTestMarks.getMarksString() == null || studentPhonicTestMarks.getMarksString().equals("")){
							isCompleted = false;
						}
					}
				}
				if(isCompleted){
					status = updateGradedStatus(studentAssignmentId);
				}
				
			} catch (HibernateException ex) {
				ex.printStackTrace();
			}
		}catch(Exception e){
			status = false;
			logger.error("Error in submitStudentPhonicTestMarks() of GradePhonicSkillTestDAOImpl"+ e);
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean updateGradedStatus(long studentAssignmentId){
		int count = 0;
		boolean success = true;
		String gradedStatus = "";
		String status = "";
		try{
		String query_for_gradedStatus = "select graded_status, status from student_assignment_status where student_assignment_id="+studentAssignmentId;
		SqlRowSet rs_for_gradedStatus = jdbcTemplate.queryForRowSet(query_for_gradedStatus);
		 if (rs_for_gradedStatus.next()){
			 count++;
			 gradedStatus = rs_for_gradedStatus.getString(1);
			 status = rs_for_gradedStatus.getString(2);
		 }else{
			 success = false;
		 }
		 
		 if(count > 0 && gradedStatus.equalsIgnoreCase(WebKeys.GRADED_STATUS_NOTGRADED)){
			    String update_query = "";
			    if(status.equalsIgnoreCase(WebKeys.TEST_STATUS_PENDING)){
			    	DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
					Date date = new Date();
					String currentDate = formatter.format(date);
			    	update_query = "update student_assignment_status set graded_status='"+WebKeys.GRADED_STATUS_LIVE_GRADED+"', status='"+WebKeys.TEST_STATUS_SUBMITTED+"', submitdate='"+currentDate+"' where student_assignment_id="+studentAssignmentId;
			    }else{
			 	    update_query = "update student_assignment_status set graded_status='"+WebKeys.GRADED_STATUS_GRADED+"',graded_date=current_date,read_status='"+WebKeys.UN_READ+"' where student_assignment_id="+studentAssignmentId;
			    }
				jdbcTemplate.update(update_query);
				success = true;
		 }
		}catch(Exception e){
			success = false;
			logger.error("Error in updateGradedStatus() of GradePhonicSkillTestDAOImpl"+ e);
			e.printStackTrace();
		}
		return success;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public StudentPhonicTestMarks getStudentPhonicTestMarksByGroupId(
			long studentAssignmentId, long groupId) {
		List<StudentPhonicTestMarks> studentPhonicTestMarksLt = new ArrayList<StudentPhonicTestMarks>();
		try{
			studentPhonicTestMarksLt =(List<StudentPhonicTestMarks>) getHibernateTemplate().find("from StudentPhonicTestMarks where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+" AND phonicGroups.groupId="+groupId);
			
		} catch (Exception e) {
			logger.error("Error in getStudentPhonicTestMarksByGroupId() of GradePhonicSkillTestDAOImpl"+ e);
			e.printStackTrace();
		}
		if(!studentPhonicTestMarksLt.isEmpty()){
			return studentPhonicTestMarksLt.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public long getLastSavedGroupId(long studentAssignmentId) {
		long lastSavedGroupId = 0;
		try {
			String query = "select last_saved_set from student_assignment_status where student_assignment_id="+ studentAssignmentId + " and status='"+WebKeys.TEST_STATUS_SAVED+"'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				lastSavedGroupId = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastSavedGroupId;
	}
}
