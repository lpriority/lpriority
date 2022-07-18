package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.BpstGroups;
import com.lp.model.BpstTypes;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.Language;
import com.lp.model.PhonicGroups;
import com.lp.model.PhonicSections;
import com.lp.teacher.dao.AssignPhonicSkillTestDAO;

@RemoteProxy(name = "assignPhonicSkillTestService")
public class AssignPhonicSkillTestServiceImpl implements AssignPhonicSkillTestService {
    
	static final Logger logger = Logger.getLogger(AssignPhonicSkillTestServiceImpl.class);
	@Autowired
	private AssignPhonicSkillTestDAO assignPhonicSkillTestDAO;
	
	@Override
	@RemoteMethod
	public List<PhonicGroups> getAllPhonicGroups(long langId){
		return assignPhonicSkillTestDAO.getAllPhonicGroups(langId);
	}
	@Override
	@RemoteMethod
	public List<PhonicGroups> getAllBpstTypePhonicGroups(long bpstTypeId){
		return assignPhonicSkillTestDAO.getAllBpstTypePhonicGroups(bpstTypeId);
	}
	@Override
	public List<PhonicSections> getAllPhonicSections() {
		return assignPhonicSkillTestDAO.getAllPhonicSections();
	}
	
	@Override
	@RemoteMethod
	public List<PhonicGroups> getAssignedStudentPhonicGroups(long studentAssignmentId){
		return assignPhonicSkillTestDAO.getAssignedStudentPhonicGroups(studentAssignmentId);
	}

	@Override
	@Transactional
	public boolean assignPhonicSkillsTest(Assignment assignment, ArrayList<Long> groupIdArray,ArrayList<Long> students,long langId) {
		return assignPhonicSkillTestDAO.assignPhonicSkillsTest(assignment, groupIdArray,students,langId);
	}

	@Override
	public boolean checkTestAlreadyAssgined(Assignment assignment) {
		return assignPhonicSkillTestDAO.checkTestAlreadyAssgined(assignment);
	}

	@Override
	public void recordPhonicSkillTest(EarlyLiteracyTestsForm earlyLiteracyTestsForm) {
		assignPhonicSkillTestDAO.recordPhonicSkillTest(earlyLiteracyTestsForm);
	}
	@Override
	public List<Language> getLanguages(){
		return assignPhonicSkillTestDAO.getLanguages();
	}
	@Override
	public List<PhonicGroups> getPhonicGroupsByLanguage(long langId){
		return assignPhonicSkillTestDAO.getPhonicGroupsByLanguage(langId);
	}
	
	@Override
	public List<PhonicSections> getPhonicSectionsByLanguage(long langId) {
		return assignPhonicSkillTestDAO.getPhonicSectionsByLanguage(langId);
	}
   @Override
   public List<BpstTypes> getBpstTypes(){
	   return assignPhonicSkillTestDAO.getBpstTypes();
   }
   @Override
   public List<BpstGroups> getBpstGroupsByBpstTypeId(long bpstTypeId){
	  return assignPhonicSkillTestDAO.getBpstGroupsByBpstTypeId(bpstTypeId);
    }
   @Override
   public List<PhonicSections> getPhonicSections(List<BpstGroups> bpstGroupsLt) throws DataException{
	   List<PhonicSections> phonicSectionsLt = new ArrayList<PhonicSections>();
		Map <Long, String> gradeMap =   new LinkedHashMap <Long,String>();
		
		try {
			if (bpstGroupsLt!=null && bpstGroupsLt.size() > 0) {
				for (BpstGroups bg : bpstGroupsLt) {
					if(!gradeMap.containsKey(bg.getPhonicGroups().getPhonicSections().getPhonicSectionId())){
						gradeMap.put(bg.getPhonicGroups().getPhonicSections().getPhonicSectionId(), bg.getPhonicGroups().getPhonicSections().getName());
						phonicSectionsLt.add(bg.getPhonicGroups().getPhonicSections());
					}
				}
			} else {
				logger.error("Error in getPhonicSections() of  AssignPhonicSkillTestServiceImpl");
				throw new DataException(
						"Error in getPhonicSections() of AssignPhonicSkillTestServiceImpl");
			}
		} catch (DataException e) {
			logger.error("Error in getPhonicSections() of  AssignPhonicSkillTestServiceImpl");
			throw new DataException(
					"Error in getPhonicSections() of AssignPhonicSkillTestServiceImpl", e);
		}

		return phonicSectionsLt;
   }
}
