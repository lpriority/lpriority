package com.lp.admin.service;

import java.util.List;

import com.lp.model.StemAnalytics;

public interface StemAnalyticsService {

	List<StemAnalytics> getStemReportByStemPair();
	List<StemAnalytics> getStemReport(String areaValue);
}
