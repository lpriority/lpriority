package com.lp.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.StemAnalyticsDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.StemAnalytics;
import com.lp.model.UnitStemAreas;
import com.lp.utils.WebKeys;

public class StemAnalyticsServiceImpl implements StemAnalyticsService {
	
	static final Logger logger = Logger.getLogger(StemAnalyticsServiceImpl.class);

	@Autowired
	private StemAnalyticsDAO stemAnalyticsDAO;
	
	@Override
	public List<StemAnalytics> getStemReportByStemPair() {
		List<StemAnalytics> stemAnalytics = new ArrayList<StemAnalytics>();
		List<UnitStemAreas> uss = new ArrayList<UnitStemAreas>();
		int ST=0, SE=0, SM=0, TE=0, TM=0, EM = 0;
		StemAnalytics STO = new StemAnalytics(),SEO = new StemAnalytics(), SMO =new StemAnalytics(), TEO = new StemAnalytics(), TMO = new StemAnalytics(),
				EMO = new StemAnalytics();
		try {			
			uss = stemAnalyticsDAO.getUnitStemAreassGroupByUnit();
			HashMap<Long, String> map1 = new HashMap<Long, String>();
			HashMap<Long, String> map2 = new HashMap<Long, String>();
			for(UnitStemAreas u:uss){
				if(!map1.containsKey(u.getStemUnit().getStemUnitId())){
					map1.put(u.getStemUnit().getStemUnitId(), u.getStemAreas().getStemArea());
				}else{
					map2.put(u.getStemUnit().getStemUnitId(), u.getStemAreas().getStemArea());
				}				
			}
			for (Map.Entry<Long, String> entry : map1.entrySet()) {
				String area1="", area2 = "";
				area1 = entry.getValue();
				area2 = map2.get(entry.getKey());	
				STO.setStemArea1(WebKeys.LP_STEM_SCIENCE);
				STO.setStemArea2(WebKeys.LP_STEM_TECHNOLOGY);
				if((area1.equalsIgnoreCase(WebKeys.LP_STEM_SCIENCE) && area2.equalsIgnoreCase(WebKeys.LP_STEM_TECHNOLOGY))
						|| (area2.equalsIgnoreCase(WebKeys.LP_STEM_SCIENCE) && area1.equalsIgnoreCase(WebKeys.LP_STEM_TECHNOLOGY))){
					ST++;			
				}
				SEO.setStemArea1(WebKeys.LP_STEM_SCIENCE);
				SEO.setStemArea2(WebKeys.LP_STEM_ENGINEERING);
				if((area1.equalsIgnoreCase(WebKeys.LP_STEM_SCIENCE) && area2.equalsIgnoreCase(WebKeys.LP_STEM_ENGINEERING))
						|| (area2.equalsIgnoreCase(WebKeys.LP_STEM_SCIENCE) && area1.equalsIgnoreCase(WebKeys.LP_STEM_ENGINEERING))){
					SE++;
					
				}
				SMO.setStemArea1(WebKeys.LP_STEM_SCIENCE);
				SMO.setStemArea2(WebKeys.LP_STEM_MATH);
				if((area1.equalsIgnoreCase(WebKeys.LP_STEM_SCIENCE) && area2.equalsIgnoreCase(WebKeys.LP_STEM_MATH))
						|| (area2.equalsIgnoreCase(WebKeys.LP_STEM_SCIENCE) && area1.equalsIgnoreCase(WebKeys.LP_STEM_MATH))){
					SM++;
					
				}
				TEO.setStemArea1(WebKeys.LP_STEM_ENGINEERING);
				TEO.setStemArea2(WebKeys.LP_STEM_TECHNOLOGY);
				if((area1.equalsIgnoreCase(WebKeys.LP_STEM_ENGINEERING) && area2.equalsIgnoreCase(WebKeys.LP_STEM_TECHNOLOGY))
						&& (area2.equalsIgnoreCase(WebKeys.LP_STEM_ENGINEERING) && area1.equalsIgnoreCase(WebKeys.LP_STEM_TECHNOLOGY))){
					TE++;					
				}
				TMO.setStemArea1(WebKeys.LP_STEM_TECHNOLOGY);
				TMO.setStemArea2(WebKeys.LP_STEM_MATH);
				if((area1.equalsIgnoreCase(WebKeys.LP_STEM_MATH) && area2.equalsIgnoreCase(WebKeys.LP_STEM_TECHNOLOGY))
						|| (area2.equalsIgnoreCase(WebKeys.LP_STEM_MATH) && area1.equalsIgnoreCase(WebKeys.LP_STEM_TECHNOLOGY))){
					TM++;
				}
				EMO.setStemArea1(WebKeys.LP_STEM_ENGINEERING);
				EMO.setStemArea2(WebKeys.LP_STEM_MATH);
				if((area1.equalsIgnoreCase(WebKeys.LP_STEM_ENGINEERING) && area2.equalsIgnoreCase(WebKeys.LP_STEM_MATH))
						|| (area2.equalsIgnoreCase(WebKeys.LP_STEM_ENGINEERING) && area1.equalsIgnoreCase(WebKeys.LP_STEM_MATH))){
					EM++;
				}
			}
			if(!map1.isEmpty() && map1.size() > 0){
				STO.setPercentage((ST * 100) / map1.size());
				SEO.setPercentage((SE * 100) / map1.size());
				SMO.setPercentage((SM * 100) / map1.size());
				TEO.setPercentage((TE * 100) / map1.size());
				TMO.setPercentage((TM * 100) / map1.size());
				EMO.setPercentage((EM * 100) / map1.size());
			}
			stemAnalytics.add(STO);
			stemAnalytics.add(SEO);
			stemAnalytics.add(SMO);
			stemAnalytics.add(TEO);
			stemAnalytics.add(TMO);
			stemAnalytics.add(EMO);
			
		} catch (DataException e) {
			logger.error("Error in getUnitStemStrandsByUnitId() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in getUnitStemStrandsByUnitId() of StemCurriculumServiceImpl", e);
		}		
		return stemAnalytics;
	}
	@Override
	public List<StemAnalytics> getStemReport(String areaValue) {
		return stemAnalyticsDAO.getStemReport(areaValue);
	}
	
}
