package com.lp.student.service;

import java.util.List;

import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.K1Sets;

public interface EarlyLiteracyTestsService {
	public List<K1Sets> getEarlyLiteracyTests(long studentAssignmentId);
	public void saveAudiorRecord(String setName,String audioData,String audioFileName,String setType,long assignmentId);
	public boolean updateTestStatus(EarlyLiteracyTestsForm earlyLiteracyTestsForm);
	public boolean submitAndAssignNextELTest(EarlyLiteracyTestsForm earlyLiteracyTestsForm);
}
