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

import com.lp.model.AcademicYear;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("homeworkmanagerdao")
public class HomeworkManagerDAOImpl extends CustomHibernateDaoSupport
implements HomeworkManagerDAO {
	
		private JdbcTemplate jdbcTemplate;
		@Autowired
		private	HttpSession session;
		@Autowired
		public void setdataSource(DataSource datasource) {
			this.jdbcTemplate = new JdbcTemplate(datasource);

		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Assignment> getAssignedHomeworks(Teacher teacher,long csId, String usedFor,long lessonId){
			List<Assignment> assignmentList = null;
			try {
				assignmentList = (List<Assignment>) getHibernateTemplate().find(
						"from Assignment where classStatus.csId=" + csId
								+ " and classStatus.teacher.teacherId="
								+ teacher.getTeacherId() + " and usedFor='"+usedFor+"' and assign_status='"+WebKeys.ACTIVE+"' "
										+ "and lesson.lessonId="+lessonId+" and dateDue>=current_date");
			} catch (HibernateException ex) {
				ex.printStackTrace();
			}
			return assignmentList;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<AssignmentQuestions> getQuestionsByAssignmentId(long assignmentId){
			List<AssignmentQuestions> questionList = null;
			try {
				questionList = (List<AssignmentQuestions>) getHibernateTemplate().find(
						"from AssignmentQuestions where studentAssignmentStatus.assignment.assignmentId="+assignmentId+"");
			} catch (HibernateException ex) {
				ex.printStackTrace();
			}
			
			return questionList;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Assignment> getAllAssignedHomeworks(Teacher teacher,long csId, String usedFor,long lessonId){
			List<Assignment> assignmentList = null;
			try {
				assignmentList = (List<Assignment>) getHibernateTemplate().find(
						"from Assignment where classStatus.csId=" + csId
								+ " and classStatus.teacher.teacherId="
								+ teacher.getTeacherId() + " and usedFor='"+usedFor+"' and assign_status='"+WebKeys.ACTIVE+"' "
										+ "and lesson.lessonId="+lessonId+"");
			} catch (HibernateException ex) {
				ex.printStackTrace();
			}
			return assignmentList;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<StudentAssignmentStatus> getHomeworkReports(Teacher teacher,long csId,String usedFor,String assignedDate,long title){
			List<StudentAssignmentStatus> assignmentList = null;
			try {
				
				assignmentList = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
						"from StudentAssignmentStatus where assignment.classStatus.csId=" + csId
								+ " and assignment.classStatus.teacher.teacherId="
								+ teacher.getTeacherId() + " and assignment.usedFor='"+usedFor+"' and assignment.assignStatus='"+WebKeys.ACTIVE+"'"
								+" and assignment.dateAssigned='"+assignedDate+"' and gradedStatus='graded' and assignment.assignmentId="+title+"");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return assignmentList;
		}
		@Override
		public List<Assignment> getAssignmentTitles(long csId,String usedFor,String assignedDate){
			List<Assignment> assignmentList = new ArrayList<>();
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try {
				
				String query = "select distinct a.assignment_id, a.title from student_assignment_status sas, assignment a where "
		        		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.student_id is Not Null and a.assign_status='"+WebKeys.ACTIVE+"'"
		        		+ " and date_assigned=? and a.used_for=?";
				SqlRowSet rs = null;
				
				if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER) || 
						userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)){
					query = query+" and assignment_type_id not in( select assignment_type_id from assignment_type"
							+ " where assignment_type in('"+WebKeys.EARLY_LITERACY_LETTER+"','"
							+WebKeys.EARLY_LITERACY_WORD+"','"+ WebKeys.PHONIC_SKILL_TEST+"','"+ WebKeys.LP_MATH_ASSESMENT+"','"+WebKeys.GEAR_GAME+"'))";
					
					rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate,usedFor);
				}else{
					rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate,usedFor);
				}
				
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
		@Override
		public List<Assignment> getFluencyTitles(long csId,String assignedDate){
			List<Assignment> assignmentList = new ArrayList<Assignment>();
			AcademicYear academicYear = null;
			String assignStatus = WebKeys.ACTIVE;
			if(session.getAttribute("academicYear") != null)
				academicYear = (AcademicYear) session.getAttribute("academicYear");
			try{
				if(session.getAttribute("academicYrFlag")!=null && session.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
						&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
					assignStatus =  WebKeys.EXPIRED;
				}
				SqlRowSet rs = null;
				assignStatus="active";
				String query = "select distinct a.assignment_id, a.title from student_assignment_status sas, assignment a where "
		        		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.student_id is Not Null and a.assign_status='"+assignStatus+"'"
		        		+ " and date_assigned=? and assignment_type_id=8 and sas.graded_status='graded'";
				
					rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate);
				
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
		@SuppressWarnings("unchecked")
		@Override
		public List<Assignment> getTestTitles(long csId,String usedFor){
			List<Assignment> assignmentList = null;
			try {
				assignmentList = (List<Assignment>) getHibernateTemplate().find(
						"from Assignment where classStatus.csId=" + csId
								+ " and usedFor='"+usedFor+"' and assign_status='"+WebKeys.ACTIVE+"' and "
										+ "((assignmentType.assignmentType='"+WebKeys.ASSIGNMENT_TYPE_MULTIPLE_CHOICES+"') or "
												+ "(assignmentType.assignmentType='"+WebKeys.ASSIGNMENT_TYPE_COMPREHENSION+"'))");
			} catch (HibernateException ex) {
				logger.equals("error while getting test titles in getTestTitles method in HomeworkManagerDAOImpl");
			}
			
			return assignmentList;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Assignment> getHomeworkTitles(long csId, String usedFor,
				String assignedDate) {
			List<Assignment> assignmentList = null;
			try {
				String query = "from Assignment where classStatus.csId=" +csId+ " and usedFor='"+usedFor+"' and assign_status='"+WebKeys.ACTIVE+"' "
						+ "and dateAssigned='"+assignedDate+"'";
							
				assignmentList = (List<Assignment>) getHibernateTemplate().find(query);
			} catch (HibernateException ex) {
				ex.printStackTrace();
			}
			return assignmentList;
		}
		@Override
		public List<Assignment> getRTIResultsTitles(long csId,String usedFor,String assignedDate){
			List<Assignment> assignmentList = new ArrayList<>();
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try {
				
				String query = "select distinct a.assignment_id, a.title from student_assignment_status sas, assignment a where "
		        		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.student_id is Not Null and a.assign_status='"+WebKeys.ACTIVE+"'"
		        		+ " and date_assigned=? and a.used_for=?";
				SqlRowSet rs = null;
				
				if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER) || 
						userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)){
					query = query+" and a.created_by =? and assignment_type_id not in( select assignment_type_id from assignment_type"
							+ " where assignment_type in('"+WebKeys.EARLY_LITERACY_LETTER+"','"
							+WebKeys.EARLY_LITERACY_WORD+"','"+ WebKeys.PHONIC_SKILL_TEST+"','"+WebKeys.LP_FLUENCY_READING_TEST+"'))";
					
					rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate,usedFor,userReg.getRegId());
				}else{
					rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate,usedFor);
				}
				
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
		@Override
		public List<Assignment> getAccuracyTitles(long csId,String assignedDate){
			List<Assignment> assignmentList = new ArrayList<Assignment>();
			AcademicYear academicYear = null;
			String assignStatus = WebKeys.ACTIVE;
			if(session.getAttribute("academicYear") != null)
				academicYear = (AcademicYear) session.getAttribute("academicYear");
			try{
				if(session.getAttribute("academicYrFlag")!=null && session.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
						&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
					assignStatus =  WebKeys.EXPIRED;
				}
				SqlRowSet rs = null;
				assignStatus="active";
				String query = "select distinct a.assignment_id, a.title from student_assignment_status sas, assignment a where "
		        		+ "a.assignment_id = sas.assignment_id and a.cs_id =? and sas.student_id is Not Null and a.assign_status='"+assignStatus+"' and a.used_for='rti' "
		        		+ " and date_assigned=? and assignment_type_id=20 and (sas.self_graded_status='graded' or sas.peer_graded_status='graded' or sas.graded_status='graded')";
				
					rs = jdbcTemplate.queryForRowSet(query,csId,assignedDate);
				
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

	
