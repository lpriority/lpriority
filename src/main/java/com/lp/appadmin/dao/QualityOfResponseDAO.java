package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.QualityOfResponse;

public interface QualityOfResponseDAO {
	
	public QualityOfResponse getQualityOfResponse(long qualityOfResponseId);
	
	public List<QualityOfResponse> getQualityOfResponseList();
	
	public void deleteQualityOfResponse(long qualityOfResponse);
	
	public void saveQualityOfResponse(QualityOfResponse qualityOfResponse);

}
