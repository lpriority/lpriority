package com.lp.appadmin.service;

import java.util.List;

import com.lp.model.Interest;
import com.lp.model.QualityOfResponse;
import com.lp.model.RtiGroups;
import com.lp.model.RubricTypes;
import com.lp.model.SubInterest;

public interface AppAdminService4 {

	/* ##### Interest methods goes here ##### */
	public List<Interest> getInterests();

	public Interest getInterest(long interestId);
	
	public Interest getInterest(String	interest);

	public void deleteInterest(long interestId);

	public void saveInterest(Interest interest);

	/* ##### SubInterest methods goes here ##### */

	public List<SubInterest> getSubInterests();

	public List<SubInterest> getSubInterests(long interestId);

	public SubInterest getSubInterest(long subInterestId);

	public void deleteSubInterest(long subInterestId);

	public void saveSubInterest(SubInterest subInterest);

	/* ##### QualityOfResponse methods goes here ##### */
	public List<QualityOfResponse> getQualityOfResponse();

	public QualityOfResponse getQualityOfResponse(long qualityOfResponseId);

	public void deleteQualityOfResponse(long qualityOfResponseId);

	public void saveQualityOfResponse(QualityOfResponse qualityOfResponse);

	/* ##### RtiGroups methods goes here ##### */
	public List<RtiGroups> getRtiGroups();

	public RtiGroups getRtiGroup(long rtiGroupId);

	public void deleteRtiGroup(long rtiGroupId);

	public void saveRtiGroup(RtiGroups rtiGroup);

	/* ##### RubricTypes methods goes here ##### */
	public List<RubricTypes> getRubricTypes();

	public RubricTypes getRubricType(long rubricTypeId);

	public void deleteRubricType(long rubricTypeId);

	public void saveRubricType(RubricTypes rubricType);

}
