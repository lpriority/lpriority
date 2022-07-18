package com.lp.admin.dao;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.model.StemAnalytics;
import com.lp.model.UnitStemAreas;
import com.lp.model.UnitStemStrands;
import com.lp.model.UnitStemStrategies;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("stemAnalyticsDAO")
public class StemAnalyticsDAOImpl extends CustomHibernateDaoSupport implements StemAnalyticsDAO {
	
	static final Logger logger = Logger.getLogger(StemAnalyticsDAOImpl.class);
	
	@Autowired
	HttpSession session;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitStemStrands> getUnitStemStrandsGroupByUnit() {
		List<UnitStemStrands> unitStemStrands = new ArrayList<UnitStemStrands>();
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			unitStemStrands = (List<UnitStemStrands>) getHibernateTemplate().find("from UnitStemStrands where stemUnit.stemUnitId in "
					+ "(select stemUnit.stemUnitId from UnitStemStrands group by stemUnit.stemUnitId having count(*)>1)"
					+ " and stemUnit.userRegistration.school.schoolId = "+userReg.getSchool().getSchoolId());

		} catch (Exception e) {
			logger.error("Error in getUnitStemStrandsGroupByUnit() of StemAnalyticsDAOImpl" + e);
		}
		return unitStemStrands;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public
	List<UnitStemAreas> getUnitStemAreassGroupByUnit() {
		List<UnitStemAreas> unitStemAreas = new ArrayList<UnitStemAreas>();
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			
			unitStemAreas = (List<UnitStemAreas>) getHibernateTemplate().find("from UnitStemAreas where stemUnit.stemUnitId in "
					+ "(select stemUnit.stemUnitId from UnitStemAreas where stemAreas.isOtherStem='No' group by stemUnit.stemUnitId having count(*)>1 )  and"
					+ " stemUnit.userRegistration.school.schoolId = "+userReg.getSchool().getSchoolId() +" and stemAreas.isOtherStem='No' ");

		} catch (Exception e) {
			logger.error("Error in getUnitStemAreassGroupByUnit() of StemAnalyticsDAOImpl" + e);
		}
		return unitStemAreas;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StemAnalytics> getStemReport(String areaValue){
		 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 List<StemAnalytics> stemAnalyticsLt = new ArrayList<StemAnalytics>();
		 long count = 1;
		 HashMap<String,Long> usMap = new HashMap<String,Long>();
		 if(areaValue.equalsIgnoreCase(WebKeys.LP_STRANDS)){
			 List<UnitStemStrands> unitStemStrandsLt = new ArrayList<UnitStemStrands>();
			 unitStemStrandsLt = (List<UnitStemStrands>) getHibernateTemplate().find("from UnitStemStrands where unitStemAreas.stemUnit.userRegistration.school.schoolId = "+userReg.getSchool().getSchoolId()+" ORDER BY stemGradeStrands.stemStrandTitle ASC");
			 for (UnitStemStrands unitStemStrands : unitStemStrandsLt) {
				 String stemStrandTitle = unitStemStrands.getStemGradeStrands().getStemStrandTitle();
				 if(usMap.get(stemStrandTitle) == null){
					 usMap.put(stemStrandTitle, count);
					 count = 1;
				 }else if(usMap.containsKey(stemStrandTitle)){
					 usMap.put(stemStrandTitle, ++count); 
				 }
			  }
			 for (Map.Entry<String,Long> entry : usMap.entrySet()){
				 StemAnalytics stemAnalytics = new StemAnalytics();
				 int occurrence =  entry.getValue().intValue();
				 int tot = unitStemStrandsLt.size();
				 int per = (occurrence * 100) / tot;
				 stemAnalytics.setStrand(entry.getKey());
				 stemAnalytics.setPercentage(per);
				 stemAnalyticsLt.add(stemAnalytics);
			 }
		 }else if(areaValue.equalsIgnoreCase(WebKeys.LP_STRATEGIES)){
			 List<UnitStemStrategies> unitStemStrategiesLt = new ArrayList<UnitStemStrategies>();
			 unitStemStrategiesLt = (List<UnitStemStrategies>) getHibernateTemplate().find("from UnitStemStrategies where stemUnit.userRegistration.school.schoolId = "+userReg.getSchool().getSchoolId()+" ORDER BY stemStrategies.strategiesDesc ASC");
				for (UnitStemStrategies unitStemStrategies : unitStemStrategiesLt) {
					 String strategiesDesc = unitStemStrategies.getStemStrategies().getStrategiesDesc();
					 if(usMap.get(strategiesDesc) == null){
						 usMap.put(strategiesDesc, count);
						 count = 1;
					 }else if(usMap.containsKey(strategiesDesc)){
						 usMap.put(strategiesDesc, ++count); 
					 }
				}
				for (Map.Entry<String,Long> entry : usMap.entrySet()){
					 StemAnalytics stemAnalytics = new StemAnalytics();
					 int occurrence =  entry.getValue().intValue();
					 int tot = unitStemStrategiesLt.size();
					 int per = (occurrence * 100) / tot;
					 stemAnalytics.setStrategy(entry.getKey());
					 stemAnalytics.setPercentage(per);
					 stemAnalyticsLt.add(stemAnalytics);
				 }
	   }
		return stemAnalyticsLt;
	}
	
}
