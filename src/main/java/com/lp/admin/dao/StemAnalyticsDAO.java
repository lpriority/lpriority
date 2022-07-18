package com.lp.admin.dao;

import java.util.List;

import com.lp.model.StemAnalytics;
import com.lp.model.UnitStemAreas;
import com.lp.model.UnitStemStrands;

public interface StemAnalyticsDAO {

	List<UnitStemStrands> getUnitStemStrandsGroupByUnit();
	List<StemAnalytics> getStemReport(String areaValue);
	List<UnitStemAreas> getUnitStemAreassGroupByUnit();
}
