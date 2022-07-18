package com.lp.login.dao;


import java.util.List;
import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import com.lp.model.Security;
import com.lp.model.SecurityQuestion;
import com.lp.utils.CustomHibernateDaoSupport;


@Repository("forgotPasswordDao")
public class ForgotPasswordDAOImpl extends CustomHibernateDaoSupport implements ForgotPasswordDAO{
	
	@Autowired  
	private SessionFactory sessionFactory;  
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setdataSource(DataSource datasource){
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	@Override
	public SecurityQuestion getQuestionId(long QuestionId) {
		 Session session = sessionFactory.openSession();  
		 SecurityQuestion questions = (SecurityQuestion) session.load(SecurityQuestion.class, QuestionId);
		 return questions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityQuestion> getQuestionList() {
		List<SecurityQuestion> qList = null;
		qList = (List<SecurityQuestion>) getHibernateTemplate().find("from SecurityQuestion");
		return qList;
	}
	
	@Override
	public boolean checkSecurityforUser(Security sec)
	{
			
		String query = "select * from security where reg_id="+sec.getUserRegistration().getRegId()+" and sec_question_id="+sec.getsecurityQuestionId()+" and answer='"+sec.getAnswer()+"'";
	    
	       SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	      if(rs.next()){
	    	   return true;	    	 
	     }else {
	    	 return false;
	     }
	      
	}
	@Override
	public int UpdatePasswords(long regId, String password){ 
		String query = "update user_registration set password=? where reg_id=?";
        
        int out = jdbcTemplate.update(query,password,regId);
        if(out !=0){
        	return 1;
        }else 
        	return 0;
	    }
	}
	
	
