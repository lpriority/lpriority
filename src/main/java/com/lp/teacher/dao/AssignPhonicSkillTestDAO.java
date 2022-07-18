package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.List;

import com.lp.model.Assignment;
import com.lp.model.BpstGroups;
import com.lp.model.BpstTypes;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.Language;
import com.lp.model.PhonicGroups;
import com.lp.model.PhonicSections;

public interface AssignPhonicSkillTestDAO {
	
	public List<PhonicGroups> getAllPhonicGroups(long langId);
	public List<PhonicGroups> getAllBpstTypePhonicGroups(long bpstTypeId);
	public List<PhonicSections> getAllPhonicSections();
	public List<PhonicGroups> getAssignedStudentPhonicGroups(long studentAssignmentId);
	public boolean assignPhonicSkillsTest(Assignment assignment,ArrayList<Long> groupIdArray,ArrayList<Long> students,long langId);
	public boolean checkTestAlreadyAssgined(Assignment assignment);
	public void recordPhonicSkillTest(EarlyLiteracyTestsForm earlyLiteracyTestsForm);
	public List<Language> getLanguages();
	public List<PhonicGroups> getPhonicGroupsByLanguage(long langId);
	public List<PhonicSections> getPhonicSectionsByLanguage(long langId);
    public List<BpstTypes> getBpstTypes();
    public List<BpstGroups> getBpstGroupsByBpstTypeId(long bpstTypeId);
}
