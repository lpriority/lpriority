package com.lp.student.dao;

import java.util.List;

import com.lp.model.AssignK1Tests;
import com.lp.model.Assignment;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.K1AutoAssignedSets;
import com.lp.model.K1Sets;
import com.lp.model.StudentAssignmentStatus;

public interface EarlyLiteracyTestsDAO {

	public List<K1Sets> getEarlyLiteracyTests(long studentAssignmentId);
	public boolean updateTestStatus(EarlyLiteracyTestsForm earlyLiteracyTestsForm);
	public boolean submitAndAssignNextELTest(StudentAssignmentStatus sas, StudentAssignmentStatus nextsas, AssignK1Tests assignK1Tests);
	public List<AssignK1Tests> getAssignedK1Sets(long studentAssignmentId);
	public StudentAssignmentStatus getELTest(String title, long csId, long studentId);
	public Assignment getELTest(String title, long csId);
	public K1AutoAssignedSets getAutoASets(long setId, long csId, String title);
}