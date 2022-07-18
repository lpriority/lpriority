package com.lp.student.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.model.AssignK1Tests;
import com.lp.model.Assignment;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.K1AutoAssignedSets;
import com.lp.model.K1Sets;
import com.lp.model.StudentAssignmentStatus;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("earlyLiteracyTestsDAO")
public class EarlyLiteracyTestsDAOImpl  extends CustomHibernateDaoSupport implements EarlyLiteracyTestsDAO {

	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
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
	public List<K1Sets> getEarlyLiteracyTests(long studentAssignmentId){
		List<K1Sets> k1SetsLt = new ArrayList<K1Sets>();
		List<AssignK1Tests> assignK1TestsLt =(List<AssignK1Tests>) getHibernateTemplate().find("from AssignK1Tests where studentAssignmentStatus.studentAssignmentId= "+studentAssignmentId+" order by k1Set.setId,studentAssignmentStatus.submitdate");
		for (AssignK1Tests assignK1Test : assignK1TestsLt) {
			k1SetsLt.add(assignK1Test.getk1Set());
		}
		return k1SetsLt;
	}
	@Override
	public boolean updateTestStatus(EarlyLiteracyTestsForm earlyLiteracyTestsForm){
		String groupId = "";
		try{		
		    if(earlyLiteracyTestsForm.getGroupId() == 0){
		    	groupId = null;
		    }else{
		    	groupId = String.valueOf(earlyLiteracyTestsForm.getGroupId());
		    }
		 	String update_query = "update student_assignment_status set status='"+earlyLiteracyTestsForm.getStatus()+"', submitdate=current_date, last_saved_set="+groupId+" where student_assignment_id="+earlyLiteracyTestsForm.getStudentAssignmentId();
			int effectedRows = jdbcTemplate.update(update_query);
			if(effectedRows>0)
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean submitAndAssignNextELTest(StudentAssignmentStatus sas,StudentAssignmentStatus nextsas, AssignK1Tests assignK1Tests){
		boolean success = false;
		Transaction tx = null;
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(sas);
			session.saveOrUpdate(nextsas);
			session.saveOrUpdate(assignK1Tests);
			tx.commit();		
			success = true;
		}catch(Exception e){
			tx.rollback();
			success = false;
			e.printStackTrace();
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignK1Tests> getAssignedK1Sets(long studentAssignmentId){
		List<AssignK1Tests> assignK1Tests = null;
		try{
			assignK1Tests = (List<AssignK1Tests>) getHibernateTemplate().find("from AssignK1Tests where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId);
		}
		catch(Exception e){
		}
		return assignK1Tests;
	}
	public StudentAssignmentStatus getELTest(String title, long csId, long studentId){
		StudentAssignmentStatus sAssignmentStatus = null;
		try{
			@SuppressWarnings("unchecked")
			List<StudentAssignmentStatus> sList = (List<StudentAssignmentStatus>) getHibernateTemplate().find("from StudentAssignmentStatus where assignment.title='"+title+"' and "
					+ "assignment.classStatus.csId="+csId+" and student.studentId="+studentId+" and assignment.assignmentType.assignmentType ='"+WebKeys.ASSIGNMENT_TYPE_EARLY_LITERACY_WORD+"'");
			if(!sList.isEmpty()){
				sAssignmentStatus = sList.get(0);
			}
		}
		catch(Exception e){
			
		}
		return sAssignmentStatus;		
	}
	@Override
	public Assignment getELTest(String title, long csId){
		Assignment assignment  = null;
		try{
			@SuppressWarnings("unchecked")
			List<Assignment> aList = (List<Assignment>) getHibernateTemplate().find("from Assignment where title='"+title+"' and "
					+ "classStatus.csId="+csId+" and assignmentType.assignmentType ='"+WebKeys.ASSIGNMENT_TYPE_EARLY_LITERACY_WORD+"'");
			if(!aList.isEmpty()){
				assignment = aList.get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return assignment;		
	}
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public K1AutoAssignedSets getAutoASets(long setId, long csId, String title){
		K1AutoAssignedSets k1AutoAssignedSets  = null;
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession();

			Query query = session.createQuery("from K1AutoAssignedSets where setId.setId =:setId and "
					+ " classStatus.csId=:csId and assignment.title=:title");
			query.setParameter("setId", setId);
			query.setParameter("csId", csId);
			query.setParameter("title", title);
			List<K1AutoAssignedSets> k1AutoAssignedSets2 = query.list();
			if(!k1AutoAssignedSets2.isEmpty()) {
				k1AutoAssignedSets = k1AutoAssignedSets2.get(0);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return k1AutoAssignedSets;	
	}
}