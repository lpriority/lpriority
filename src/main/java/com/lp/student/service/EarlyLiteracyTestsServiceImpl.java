package com.lp.student.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.model.AssignK1Tests;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.K1AutoAssignedSets;
import com.lp.model.K1Sets;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.student.dao.EarlyLiteracyTestsDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "earlyLiteracyTestsService")
public class EarlyLiteracyTestsServiceImpl implements EarlyLiteracyTestsService {
	
	@Autowired
	EarlyLiteracyTestsDAO earlyLiteracyTestsDAO;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;
	
	@RemoteMethod
	public List<K1Sets> getEarlyLiteracyTests(long studentAssignmentId){
		return earlyLiteracyTestsDAO.getEarlyLiteracyTests(studentAssignmentId);
	}

	@Override
	public void saveAudiorRecord(String setName,String audioData, String audioFileName,String setType, long assignmentId) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String earlyLiteracyFilePath = "";
		if(setType.equalsIgnoreCase(WebKeys.WORD)){
			earlyLiteracyFilePath = uploadFilePath +File.separator+ WebKeys.EARLY_LITERACY_TEST+File.separator+WebKeys.WORD+File.separator+assignmentId+File.separator+setName+File.separator+audioFileName;
		}else{
			earlyLiteracyFilePath = uploadFilePath +File.separator+ WebKeys.EARLY_LITERACY_TEST+File.separator+WebKeys.LETTER+File.separator+assignmentId+File.separator+setName+File.separator+audioFileName;
		}
		try{
			File dir = new File(earlyLiteracyFilePath);
			if (!dir.isDirectory()) {
				dir.setExecutable(true, false);
				dir.mkdirs();
		    } 		   
			earlyLiteracyFilePath =earlyLiteracyFilePath+File.separator+audioFileName+WebKeys.WAV_FORMAT;
			File file = new File(earlyLiteracyFilePath);
			if(file.exists()) 
				file.delete();
			FileOutputStream fos = null;
			byte[] bis = Base64.decodeBase64(audioData.toString());
			try {
				//File f = new File(earlyLiteracyFilePath);
				synchronized(bis) {
					fos = new FileOutputStream(file, true); 
		  			fos.write(bis);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
	      	  	fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	@Override
	public boolean updateTestStatus(EarlyLiteracyTestsForm earlyLiteracyTestsForm){
		return earlyLiteracyTestsDAO.updateTestStatus(earlyLiteracyTestsForm);		
	}	
	@Override
	public boolean submitAndAssignNextELTest(EarlyLiteracyTestsForm earlyLiteracyTestsForm){
		boolean flag = false; 
		try{
			StudentAssignmentStatus sas = performanceTaskDAO.getStudentAssignmentStatusById(earlyLiteracyTestsForm.getStudentAssignmentId());
			K1Sets assignedSets = sas.getAssignment().getAutoAssignedSets().getNextAutoSetId();	
			if(assignedSets != null){		
				String title = sas.getAssignment().getTitle();
				int i = title.lastIndexOf("_");
				String[] titleArr =  {title.substring(0, i), title.substring(i)};
				if(titleArr[0] != null)
					title = titleArr[0]+"_"+assignedSets.getSetName();
				K1AutoAssignedSets kAutoAssignedSets = null;
				kAutoAssignedSets = earlyLiteracyTestsDAO.getAutoASets(assignedSets.getSetId(),sas.getAssignment().getAutoAssignedSets().getClassStatus().getCsId(),title);
				StudentAssignmentStatus nextsas = new StudentAssignmentStatus();
				nextsas.setAssignment(kAutoAssignedSets.getAssignment());
				nextsas.setTestDueDate(kAutoAssignedSets.getAssignment().getDateDue());
				nextsas.setTestAssignDate(kAutoAssignedSets.getAssignment().getDateAssigned());
				nextsas.setStatus(WebKeys.TEST_STATUS_PENDING);
				nextsas.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
				nextsas.setStudent(sas.getStudent());	 
				
				AssignK1Tests assignK1Tests = new AssignK1Tests();				
				assignK1Tests.setK1Set(assignedSets);
				assignK1Tests.setStudentAssignmentStatus(nextsas);		
				Date startDate = new Date();				
				sas.setSubmitdate(startDate);
				sas.setStatus(earlyLiteracyTestsForm.getStatus());
				flag = earlyLiteracyTestsDAO.submitAndAssignNextELTest(sas,nextsas,assignK1Tests);				
			} else{
				flag = earlyLiteracyTestsDAO.updateTestStatus(earlyLiteracyTestsForm);
			}			
			return flag;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
}