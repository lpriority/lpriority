package com.lp.login.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.model.Security;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("securityDao")
public class SecurityDAOImpl extends CustomHibernateDaoSupport implements
		SecurityDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public Security getSecurity(long regId) {
		List<Security> sec = (List<Security>) getHibernateTemplate().find(
				"from Security where userRegistration.regId=" + regId);
		if (sec.size() > 0) {
			return sec.get(0);
		} else {
			return new Security();
		}
	}

	@Override
	public boolean saveSecurity(Security sec) {
		try {
			super.saveOrUpdate(sec);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkSecurity(Security sec) {
		List<Security> secu = (List<Security>) getHibernateTemplate().find(
				"from Security where userRegistration.regId="
						+ sec.getUserRegistration().getRegId()
						+ " and verificationCode='" + sec.getVerificationCode()
						+ "'");
		if (secu.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Security> getUsersList(String userType) {
		List<Security> securityList = null;
		securityList = (List<Security>) getHibernateTemplate()
				.find("from Security where userRegistration.user.userType= '"
						+ userType + "' and userRegistration.status='"+WebKeys.LP_STATUS_NEW+"' ");
		return securityList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Security getSecurity(String emailId) {
		List<Security> sec = (List<Security>) getHibernateTemplate().find(
				"from Security where userRegistration.emailId='" + emailId+"' and userRegistration.status = '"+WebKeys.ACTIVE +"'");
		if (sec.size() > 0) {
			return sec.get(0);
		} else {
			return new Security();
		}
	}
	@Override
	public List<Security> saveBulkSecuriy(List<Security> securityList){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long i = 0;
		for ( Security sec : securityList ) {
		    session.save(sec);
		    i++;
		    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		        session.flush();
		        session.clear();
		    }
		}
		   
		tx.commit();
		session.close();
		return securityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Security getSecurityVerification(String emailId) {
		List<Security> sec = (List<Security>) getHibernateTemplate().find(
				"from Security where userRegistration.emailId='" + emailId+"' and userRegistration.status = '"+WebKeys.LP_STATUS_NEW +"'");
		if (sec.size() > 0) {
			return sec.get(0);
		} else {
			return new Security();
		}
	}
}
