package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.model.AcademicYear;
import com.lp.model.AssignK1Tests;
import com.lp.model.Assignment;
import com.lp.model.K1AutoAssignedSets;
import com.lp.model.K1Sets;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.teacher.service.EarlyLiteracyService;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("earlyLiteracyDAO")
public class EarlyLiteracyDAOImpl extends CustomHibernateDaoSupport  implements EarlyLiteracyDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EarlyLiteracyService earlyLiteracyService;
	@Autowired
	private HttpSession httpSession;
	
	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	@SuppressWarnings("unchecked")
	@Override
	public String createK1Tests(K1Sets k1Sets){
		try{
			List<AssignK1Tests> assignK1Tests = null;
			List<K1AutoAssignedSets> k1AutoAssignedSets = null;
			if(k1Sets.getSetId() > 0){
				assignK1Tests =(List<AssignK1Tests>) getHibernateTemplate().find("from AssignK1Tests where k1Set.setId="+k1Sets.getSetId());
				if(assignK1Tests.size() > 0){
					return WebKeys.TEST_ALREDAY_ASSGINED;
				}else{
					k1AutoAssignedSets =(List<K1AutoAssignedSets>) getHibernateTemplate().find("from K1AutoAssignedSets where setId.setId="+k1Sets.getSetId());
					if(k1AutoAssignedSets.size() > 0)
						return WebKeys.TEST_ALREDAY_ASSGINED;
				}
			}
			Session session = getSessionFactory().getCurrentSession();
			if(k1Sets != null){
				session.saveOrUpdate(k1Sets);
				return WebKeys.SUCCESS;
			}else{
				return WebKeys.LP_FAILED;
			}
		}catch(Exception e){
			e.printStackTrace();
			return WebKeys.LP_FAILED;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<K1Sets> getK1SetsBycsId(long csId){
		List<K1Sets> k1SetsLt = new ArrayList<K1Sets>();	
			try{
				k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where csId="+csId+" and status='"+WebKeys.ACTIVE+"' order by setId");
			} catch (HibernateException ex) {
				ex.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		return k1SetsLt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<K1Sets> getTestSets(long masterGradeId, long csId,String setType, boolean isAutomatic) {
		List<K1Sets> k1SetsLt = new ArrayList<K1Sets>();
		if(setType.equalsIgnoreCase(WebKeys.LETTER)){
			masterGradeId = 13;
			k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where masterGrades.masterGradesId="+masterGradeId+" AND setType='"+setType+"' and status='"+WebKeys.ACTIVE+"' order by setId");
		}else if(setType.equalsIgnoreCase(WebKeys.WORD)){
			if(isAutomatic == true){
				  if(masterGradeId == 1 || masterGradeId == 13){
					 k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where ( csId="+csId+" OR masterGrades.masterGradesId="+masterGradeId+" ) AND setType='"+setType+"' and status='"+WebKeys.ACTIVE+"' order by listType DESC, setId");
				  }else{
					masterGradeId = 1;
					k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where ( csId="+csId+" OR masterGrades.masterGradesId="+masterGradeId+" ) AND setType='"+setType+"' and status='"+WebKeys.ACTIVE+"' order by listType DESC, setId");
				  }
			}else{
	            if(masterGradeId == 1 || masterGradeId == 13){
	            	k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where masterGrades.masterGradesId="+masterGradeId+" AND setType='"+setType+"' and status='"+WebKeys.ACTIVE+"' order by setId");
	            }else{
	            	masterGradeId = 1;
	            	k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where masterGrades.masterGradesId="+masterGradeId+" AND setType='"+setType+"' and status='"+WebKeys.ACTIVE+"' order by setId");
			    }
			}
		}
		return k1SetsLt;
	}

	@Override
	public String assginTest(Assignment assignment,ArrayList<Long> setIdsArray,ArrayList<Long> students){
		String status = "";
		long assignmentId = 0;
		long studentAssignmentId = 0;
		try{			
			long assignment_id = 0;
			String query_for_assignment_id = "select assignment_id from assignment where cs_id= '"+assignment.getClassStatus().getCsId()+"' AND title = '"+assignment.getTitle()+"' AND used_for= '"+assignment.getUsedFor()+"'";
			SqlRowSet rs_for_assignment_id = jdbcTemplate.queryForRowSet(query_for_assignment_id);
			 if (rs_for_assignment_id.next()) {
				 assignment_id = rs_for_assignment_id.getLong(1);
			 }
			
			if(assignment_id > 0){ 
				 status ="already assigned";
		    }else{
		    	String insert_for_assignment = "insert into assignment (cs_id,assignment_type_id,title,assign_status,date_assigned,date_due,used_for,test_type,record_time) values(?,?,?,?,?,?,?,?,?)";
			    jdbcTemplate.update(
			    		insert_for_assignment,
			    		assignment.getClassStatus().getCsId(),
			    		assignment.getAssignmentType().getAssignmentTypeId(),
			    		assignment.getTitle(),
			    		assignment.getAssignStatus(),
			    		assignment.getDateAssigned(),
			    		assignment.getDateDue(),
			    		assignment.getUsedFor(),
			    		assignment.getTestType(),
			    		assignment.getRecordTime()
			    		); 
				assignmentId = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
	
				if(assignmentId > 0){				 
					for (Long studentId : students) {
						String insert_for_student_assignment_status = "insert into student_assignment_status (assignment_id,student_id,status,graded_status) values(?,?,?,?)";
						jdbcTemplate.update(insert_for_student_assignment_status,assignmentId,studentId,WebKeys.TEST_STATUS_PENDING,WebKeys.GRADED_STATUS_NOTGRADED); 
						studentAssignmentId = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
						for (Long setId : setIdsArray) {
							String insert_for_assign_k1_tests = "insert into assign_k1_tests (set_id,student_assignment_id) values(?,?)";
							jdbcTemplate.update(insert_for_assign_k1_tests,setId,studentAssignmentId); 
						}
					}
					status ="success";
				}else{
					status ="failed";
				}
		    }
		}catch(Exception e){
			status ="failed";
			e.printStackTrace();
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAssignedDates(Teacher teacher,long csId,String usedFor,String assignmentType){
		List<Assignment> assignmentDates = null;	
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
			}
		     assignmentDates =(List<Assignment>) getHibernateTemplate().find("from Assignment where classStatus.csId="+csId+" and "
		 		+ "usedFor='"+usedFor+"' and assignmentType.assignmentType='"+assignmentType+"' and assignStatus='"+assignStatus+"' and "
		 		+ "autoAssignedSets.k1AutoAssignedSetId is null group by dateAssigned");
				} catch (HibernateException ex) {
				ex.printStackTrace();
		 }
		return assignmentDates;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAssignmentTitles(long csId,String usedFor,String assignedDate,String assignmentType){
		List<Assignment> assignmentList = null;
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
			}
			assignmentList = (List<Assignment>) getHibernateTemplate().find(
					"from Assignment where classStatus.csId=" + csId
					+ " and usedFor='"+usedFor+"' and assign_status='"+assignStatus+"' "
					+ "and dateAssigned='"+assignedDate+"' and assignmentType.assignmentType='"+assignmentType+"' "
					+ "and autoAssignedSets.k1AutoAssignedSetId is null ");
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		
		return assignmentList;
	}
	public long getSetIdBySetName(String setName,String setType){
		long setId = 0;
		String query_for_setId = "select k1_set_id from k1_sets where set_type= '"+setType+"' AND set_name= '"+setName+"'";
		SqlRowSet rs_for_setId = jdbcTemplate.queryForRowSet(query_for_setId);
		 if (rs_for_setId.next()) {
			 setId = rs_for_setId.getLong(1);
		 }
		 return setId;
	}
	public boolean setGradedMarks(long setId,long studentAssignmentId,String gradedMarks){
		/*
		 * long set_id = 0; String query_for_checkSetId =
		 * "select set_id from assign_k1_tests where set_id= "
		 * +setId+" AND student_assignment_id= "+studentAssignmentId; SqlRowSet
		 * rs_for_checkSetId = jdbcTemplate.queryForRowSet(query_for_checkSetId); if
		 * (rs_for_checkSetId.next()) set_id = rs_for_checkSetId.getLong(1);
		 */
		 
			if(setId > 0){
			  String update_query = "update assign_k1_tests set marks_graded='"+gradedMarks+"' where set_id= "+setId+" AND student_assignment_id= "+studentAssignmentId;
			  jdbcTemplate.update(update_query);
			  return true;
			}else{
			  return false;	
			}
	}
	public boolean updateGradeStatus(long studentId,long studentAssignmentId,float percentage,String gradeStatus,long academicId){
		
		long student_assignment_id = 0;
		String query_for_checkAssignmentId = "select student_assignment_id from student_assignment_status where student_id= "+studentId+" AND student_assignment_id= "+studentAssignmentId;
		SqlRowSet rs_for_checkAssignmentId = jdbcTemplate.queryForRowSet(query_for_checkAssignmentId);
		 if (rs_for_checkAssignmentId.next())
			 student_assignment_id = rs_for_checkAssignmentId.getLong(1);
		 
			if(student_assignment_id > 0){
			  String update_query = "update student_assignment_status set graded_status= '"+gradeStatus+"', percentage= "+percentage+", academic_grade_id= "+academicId+",graded_date=current_date,read_status='"+WebKeys.UN_READ+"' where student_id= "+studentId+" AND student_assignment_id= "+studentAssignmentId;
			  jdbcTemplate.update(update_query);
			  return true;
			}else{
			  return false;	
			}
	}
	
	public String getGradedMarksAsList(long studentAssignmentId,long setId){
		String gradedMarks = "";
		String query_for_getGradedMarks = "select marks_graded from assign_k1_tests where student_assignment_id= "+studentAssignmentId+" AND set_id= "+setId;
		SqlRowSet rs_for_getGradedMarks = jdbcTemplate.queryForRowSet(query_for_getGradedMarks);
		 if (rs_for_getGradedMarks.next())
			 gradedMarks = rs_for_getGradedMarks.getString(1);
		 return gradedMarks;
	}

	@SuppressWarnings("unchecked")
	public Map<String,Integer> getNoOfAttemptsOnList(long studentId,List<String> setNameArray) {
		List<AssignK1Tests> assignK1Tests = null;
		Map<String,Integer> setNameArrayMap = new HashMap<String,Integer>();
		try{
			for (String setName : setNameArray) {
				assignK1Tests =(List<AssignK1Tests>) getHibernateTemplate().find("from AssignK1Tests where k1Set.setName='"+setName+"' and studentAssignmentStatus.student.studentId="+studentId+" and studentAssignmentStatus.status='submitted'");
				if(assignK1Tests.size() > 0)
					setNameArrayMap.put(setName, assignK1Tests.size());
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return setNameArrayMap;
		
	}

	@Override
	public String assignAutomaticTest(Assignment assignment,ArrayList<Long> students,ArrayList<Long> setIdsArray,ArrayList<Integer> recordTimeArray){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		String status = "";
		long assignment_id = 0;
		String query_for_assignment_id = "select assignment_id from assignment where cs_id= '"+assignment.getClassStatus().getCsId()+"' AND title = '"+assignment.getTitle()+"' AND used_for= '"+assignment.getUsedFor()+"'";
		SqlRowSet rs_for_assignment_id = jdbcTemplate.queryForRowSet(query_for_assignment_id);
		 if (rs_for_assignment_id.next()) {
			 assignment_id = rs_for_assignment_id.getLong(1);
		 }
		if(assignment_id > 0){ 
			status = WebKeys.TEST_ALREDAY_ASSGINED;
	    }else{
			try{				
				ArrayList<K1AutoAssignedSets> K1AutoAssignedSetsLt = new ArrayList<K1AutoAssignedSets>();
				int size = setIdsArray.size();
				for (int i = 0; i < size; i++) {
					String title = null;
					title = assignment.getTitle();
					Assignment assign = null;
					assign = new Assignment();
					assign.setAssignmentType(assignment.getAssignmentType());
					assign.setClassStatus(assignment.getClassStatus());
					assign.setAssignStatus(WebKeys.ASSIGN_STATUS_ACTIVE);
					assign.setCreatedBy(assignment.getCreatedBy());
					assign.setUsedFor(WebKeys.RTI);
					assign.setTestType(assignment.getTestType());
					assign.setDateAssigned(assignment.getDateAssigned());
					assign.setDateDue(assignment.getDateDue());
					assign.setRecordTime(recordTimeArray.get(i));
					assign.setAssignmentId(0);
					K1Sets k1SetsNext = null;
					K1Sets k1Sets = earlyLiteracyService.getK1SetById(setIdsArray.get(i));
					if(size > i+1){
						k1SetsNext = new K1Sets();
						k1SetsNext.setSetId(setIdsArray.get(i+1));
					}
					title = title + "_" + k1Sets.getSetName();	
					assign.setTitle(title);
					session.saveOrUpdate(assign);
					K1AutoAssignedSets k1AutoAssignedSets = new K1AutoAssignedSets();
					k1AutoAssignedSets.setClassStatus(assign.getClassStatus());
					k1AutoAssignedSets.setRecordTime(recordTimeArray.get(i));
					k1AutoAssignedSets.setSetId(k1Sets);
					k1AutoAssignedSets.setNextAutoSetId(k1SetsNext);
					k1AutoAssignedSets.setAssignment(assign);
					K1AutoAssignedSetsLt.add(k1AutoAssignedSets);
					session.saveOrUpdate(k1AutoAssignedSets);
					assign.setAutoAssignedSets(k1AutoAssignedSets);
					session.saveOrUpdate(assign);
				 }
				K1AutoAssignedSets k1aas = K1AutoAssignedSetsLt.get(0);
				assignment = k1aas.getAssignment();
				for (Long studentId : students) {
					Student student = new Student();
					student.setStudentId(studentId);
					StudentAssignmentStatus sas = new StudentAssignmentStatus();
					sas.setAssignment(assignment);
					sas.setStudent(student);
					sas.setTestAssignDate(assignment.getDateAssigned());
					sas.setTestDueDate(assignment.getDateDue());
					sas.setStatus(WebKeys.TEST_STATUS_PENDING);
					sas.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					session.saveOrUpdate(sas);
					AssignK1Tests assignK1Tests = new AssignK1Tests();
					assignK1Tests.setK1Set(k1aas.getSetId());
					assignK1Tests.setStudentAssignmentStatus(sas);
					session.saveOrUpdate(assignK1Tests);
			    }		
				tx.commit();	
				status = WebKeys.ASSIGNED_SUCCESSFULLY;
			 } catch (HibernateException e) {
				 status =  WebKeys.FAILED_TO_ASSIGNED;
				 tx.rollback();
				 e.printStackTrace();
			 }
			session.close();
	    }
		return status;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public K1Sets getK1SetById(long setId){
		List<K1Sets> k1SetsLt = new ArrayList<K1Sets>();	
		try{
			k1SetsLt =(List<K1Sets>) getHibernateTemplate().find("from K1Sets where setId="+setId);
			} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return k1SetsLt.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAutoAssignmentTitles(long csId,String usedFor,String assignmentType){
		List<Assignment> assignmentDates = null;	
		try{
		     assignmentDates =(List<Assignment>) getHibernateTemplate().find("from Assignment where classStatus.csId="+csId+" and "
		 		+ "usedFor='"+usedFor+"' and assignmentType.assignmentType='"+assignmentType+"' and assignStatus='"+WebKeys.ACTIVE+"' and "
		 		+ "autoAssignedSets.k1AutoAssignedSetId is not null");
				} catch (HibernateException ex) {
				ex.printStackTrace();
		 }
		return assignmentDates;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String removeSetBysetId(long setId){
		try{
			List<AssignK1Tests> assignK1Tests = null;
			List<K1AutoAssignedSets> k1AutoAssignedSets = null;
			if(setId > 0){
				assignK1Tests =(List<AssignK1Tests>) getHibernateTemplate().find("from AssignK1Tests where k1Set.setId="+setId);
				if(assignK1Tests.size() > 0){
					return WebKeys.TEST_ALREDAY_ASSGINED;
				}else{
					k1AutoAssignedSets =(List<K1AutoAssignedSets>) getHibernateTemplate().find("from K1AutoAssignedSets where setId.setId="+setId);
					if(k1AutoAssignedSets.size() > 0)
						return WebKeys.TEST_ALREDAY_ASSGINED;
				}
			}
			K1Sets k1Sets = getK1SetById(setId);
			if(k1Sets != null){
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.delete(k1Sets);
				tx.commit();
				session.close();
				return WebKeys.SUCCESS;
			}else{
				return WebKeys.LP_FAILED;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return WebKeys.LP_FAILED;
		}
	}
}