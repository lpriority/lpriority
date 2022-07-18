package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.QualityOfResponse;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("qualityOfResponseDao")
public class QualityOfResponseDAOImpl extends CustomHibernateDaoSupport implements QualityOfResponseDAO {
	static final Logger logger = Logger.getLogger(QualityOfResponseDAOImpl.class);

	@Override
	public QualityOfResponse getQualityOfResponse(long qualityOfResponseId) {
		QualityOfResponse qor = (QualityOfResponse) super.find(
				QualityOfResponse.class, qualityOfResponseId);
		return qor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QualityOfResponse> getQualityOfResponseList() {
		List<QualityOfResponse> QualityOfResponseList = null;
		QualityOfResponseList = (List<QualityOfResponse>) super
				.findAll(QualityOfResponse.class);
		return QualityOfResponseList;
	}

	@Override
	public void deleteQualityOfResponse(long qualityOfResponseId) {
		super.delete(getQualityOfResponse(qualityOfResponseId));
	}

	@Override
	public void saveQualityOfResponse(QualityOfResponse qualityOfResponse) {
		super.saveOrUpdate(qualityOfResponse);
	}
}
