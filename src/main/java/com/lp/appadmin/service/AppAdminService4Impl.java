package com.lp.appadmin.service;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lp.appadmin.dao.IneterestDAO;
import com.lp.appadmin.dao.QualityOfResponseDAO;
import com.lp.appadmin.dao.RTIGroupsDAO;
import com.lp.appadmin.dao.RubricTypesDAO;
import com.lp.appadmin.dao.SubInterestDAO;
import com.lp.model.Interest;
import com.lp.model.QualityOfResponse;
import com.lp.model.RtiGroups;
import com.lp.model.RubricTypes;
import com.lp.model.SubInterest;

@Service
public class AppAdminService4Impl implements AppAdminService4 {
	static final Logger logger = Logger.getLogger(AppAdminService4Impl.class);

	@Autowired
	private IneterestDAO interestDao;
	@Autowired
	private QualityOfResponseDAO qualityOfResponseDao;

	@Autowired
	private RTIGroupsDAO rtiGroupsDao;

	@Autowired
	private RubricTypesDAO rubricTypesDao;

	@Autowired
	private SubInterestDAO subInterestDao;

	public void setIneterestDAO(IneterestDAO interestDao) {
		this.interestDao = interestDao;
	}

	public void setQualityOfResponseDAO(
			QualityOfResponseDAO qualityOfResponseDao) {
		this.qualityOfResponseDao = qualityOfResponseDao;
	}

	public void setRTIGroupsDAO(RTIGroupsDAO rtiGroupsDao) {
		this.rtiGroupsDao = rtiGroupsDao;
	}

	public void setRubricTypesDAO(RubricTypesDAO rubricTypesDao) {
		this.rubricTypesDao = rubricTypesDao;
	}

	public void setSubInterestDAO(SubInterestDAO subInterestDao) {
		this.subInterestDao = subInterestDao;
	}

	/* ##### Interest methods go here ##### */
	public List<Interest> getInterests() {
		return interestDao.getInterestList();
	}

	public Interest getInterest(long interestId) {
		return interestDao.getInterest(interestId);
	}

	public Interest getInterest(String interest) {
		return interestDao.getInterest(interest);
	}

	public void deleteInterest(long interestId) {
		interestDao.deleteInterest(interestId);
	}

	public void saveInterest(Interest interest) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		interest.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		interest.setChangeDate(changeDate);
		interestDao.saveInterest(interest);
	}

	/* ##### SubInterest methods go here ##### */

	public List<SubInterest> getSubInterests() {
		return subInterestDao.getSubInterestList();
	}

	public List<SubInterest> getSubInterests(long interestId) {
		return subInterestDao.getSubInterests(interestId);
	}

	public SubInterest getSubInterest(long subInterestId) {
		return subInterestDao.getSubInterest(subInterestId);
	}

	public void deleteSubInterest(long subInterestId) {
		subInterestDao.deleteSubInterest(subInterestId);
	}

	public void saveSubInterest(SubInterest subInterest) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		subInterest.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		subInterest.setChangeDate(changeDate);
		if (subInterest.getInterestId() != null) {
			subInterest.setInterest(getInterest(Long.valueOf(subInterest
					.getInterestId())));
		}
		subInterestDao.saveSubInterest(subInterest);
	}

	/* ##### QualityOfResponse methods go here ##### */
	public List<QualityOfResponse> getQualityOfResponse() {
		return qualityOfResponseDao.getQualityOfResponseList();
	}

	public QualityOfResponse getQualityOfResponse(long qualityOfResponseId) {
		return qualityOfResponseDao.getQualityOfResponse(qualityOfResponseId);
	}

	public void deleteQualityOfResponse(long qualityOfResponse) {
		qualityOfResponseDao.deleteQualityOfResponse(qualityOfResponse);
	}

	public void saveQualityOfResponse(QualityOfResponse qualityOfResponse) {
		qualityOfResponseDao.saveQualityOfResponse(qualityOfResponse);
	}

	/* ##### RtiGroups methods go here ##### */
	public List<RtiGroups> getRtiGroups() {
		return rtiGroupsDao.getRTIGroupsList();
	}

	public RtiGroups getRtiGroup(long rtiGroupId) {
		return rtiGroupsDao.getRTIGroup(rtiGroupId);
	}

	public void deleteRtiGroup(long rtiGroupId) {
		rtiGroupsDao.deleteRTIGroup(rtiGroupId);
	}

	public void saveRtiGroup(RtiGroups rtiGroup) {
		rtiGroupsDao.saveRTIGroup(rtiGroup);
	}

	/* ##### RubricTypes methods go here ##### */
	public List<RubricTypes> getRubricTypes() {
		return rubricTypesDao.getRubricTypeList();
	}

	public RubricTypes getRubricType(long rubricTypeId) {
		return rubricTypesDao.getRubric(rubricTypeId);
	}

	public void deleteRubricType(long rubricTypeId) {
		rubricTypesDao.deleteRubricType(rubricTypeId);
	}

	public void saveRubricType(RubricTypes rubricType) {
		rubricTypesDao.saveRubricType(rubricType);
	}

}
