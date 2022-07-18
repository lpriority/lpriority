package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.model.MasterGrades;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("masterGradesDao")
public class MasterGradesDAOImpl extends CustomHibernateDaoSupport implements
		MasterGradesDAO {
	static final Logger logger = Logger.getLogger(MasterGradesDAOImpl.class);

	@Override
	public MasterGrades getMasterGrade(long masterGradeId) {
		MasterGrades mGrades = new MasterGrades();
		mGrades = (MasterGrades) super.find(MasterGrades.class, masterGradeId);
		return mGrades;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterGrades> getMasterGradesList() {
		List<MasterGrades> agList = new ArrayList<MasterGrades>();
		try {
			agList = (List<MasterGrades>) super.findAll(MasterGrades.class);
		} catch (DataAccessException e) {
			logger.error("Error in getMasterGradesList() of MasterGradesDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return agList;
	}

	@Override
	public void deleteMasterGrades(long masterGradeId) {
		try {
			super.delete(getMasterGrade(masterGradeId));
		} catch (HibernateException e) {
			logger.error("Error in deleteMasterGrades() of MasterGradesDAOImpl"
					+ e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveMasterGrades(MasterGrades masterGrade) {
		try {
			super.saveOrUpdate(masterGrade);
		} catch (HibernateException e) {
			logger.error("Error in saveMasterGrades() of MasterGradesDAOImpl"
					+ e);
			e.printStackTrace();
		}
	}
	
	
//	@Override
//    public Hashtable getschoolGrades(int schoolId) {
//         try {
//        String query = "select reg_id from user_registration where email_id='"+emailId+"'";
//        long parentId = 0;
//        
//        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
//        while(rs.next()){
//        	parentId = rs.getLong(1);
//        }
//         
//        return parentId;
//         }catch (final EmptyResultDataAccessException e) {
// 			return 0;}
//    }

}
