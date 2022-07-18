package com.lp.common.service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.ReadingSkillsDAO;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkCategories;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkResults;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.TaskForceResult;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

public class ReadingSkillsServiceImpl implements ReadingSkillsService {
	
	@Autowired
	private ReadingSkillsDAO readingSkillsDAO;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDAO;
	
	static final Logger logger = Logger.getLogger(ReadingSkillsServiceImpl.class);
	
	@Override
	public void renameFiles(){	
		String rrddPath = FileUploadUtil.getReadingSkillsPath();
		File rrddFolder = new File(rrddPath); 		
		if(rrddFolder.exists()){
			File[] files = rrddFolder.listFiles();
			for(File file : files){
				String filePath = file.getParentFile().getAbsolutePath();
				String fileName = file.getName();
				fileName = fileName.replace(".html", "_2016-17.html"); 
				File newFile = new File(filePath+File.separator+fileName);
				file.renameTo(newFile);
			}	
		}
	}
	
	@Override
	public void expireCurrentFiles(){	
		String rrddPath = FileUploadUtil.getReadingSkillsPath();
		File rrddFolder = new File(rrddPath); 		
		if(rrddFolder.exists()){
			File[] files = rrddFolder.listFiles();
			for(File file : files){
				String filePath = file.getParentFile().getAbsolutePath();
				String fileName = file.getName();
				if(fileName.contains("2016-17") && !fileName.contains("Expir_")  ){
					fileName = "Expir_"+ fileName;
					File newFile = new File(filePath+File.separator+fileName);
					file.renameTo(newFile);		
				}
				
			}	
		}
	}
	
	@Override
	public File[] getStudentRRDashboardFiles(Student student){
		String rrddPath = FileUploadUtil.getReadingSkillsPath();
		File rrddFolder = new File(rrddPath); 			
		final String filesStartWith = student.getStudentScId() +"_"+ student.getUserRegistration().getSchool().getSchoolAbbr();
		 FilenameFilter beginswith = new FilenameFilter()
	        {
	         public boolean accept(File directory, String filename) {
	        	 return filename.startsWith(filesStartWith);
	          }
	        };
	        File[] files = rrddFolder.listFiles(beginswith);
	        return files;
	}	
	@Override
	public File[] getSpecialDashboardFiles(){
		String rrddPath = FileUploadUtil.getLpCommonFilesPath() + File.separator + "SpecialDashboardFiles";
		File rrddFolder = new File(rrddPath); 
        File[] files = rrddFolder.listFiles();
        return files;
	}

	@Override
	public List<TaskForceResult> getTaskForceResults(long fluencyId, long comId) {
		List<TaskForceResult> taskForceResultList = new ArrayList<TaskForceResult>();
		HashMap<Long, TaskForceResult> taskMap = new HashMap<>();
		try{
			List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
			List<BenchmarkResults> benchmarkResults = new ArrayList<BenchmarkResults>();
			studentAssignments = readingSkillsDAO.getComprehensionResults(comId);
			benchmarkResults = readingSkillsDAO.getFluencyResults(fluencyId);
			long fluencyCutOff = 0;
			long masterGradeId= 0;
			BenchmarkCutOffMarks benCutOff= null;
			if(!benchmarkResults.isEmpty()) {			
				masterGradeId=  benchmarkResults.get(0).getStudentAssignmentStatus().getStudent().getGrade().getMasterGrades().getMasterGradesId();
				long gradeId = benchmarkResults.get(0).getStudentAssignmentStatus().getStudent().getGrade().getGradeId();
				BenchmarkCategories benchMark = benchmarkResults.get(0).getStudentAssignmentStatus().getAssignment().getBenchmarkCategories();
				if(benchMark.getIsFluencyTest() > 0 ) {
					benCutOff= gradeAssessmentsDAO.getBenchmarkCutOffMarks(gradeId, benchMark.getBenchmarkCategoryId());
					if(benCutOff != null)
						fluencyCutOff = benCutOff.getFluencyCutOff();
				}
				if( benchMark.getIsFluencyTest() == 0 || fluencyCutOff ==0 ) {					
					if(masterGradeId >= 5 && masterGradeId != 13){
						fluencyCutOff = 120; 
					}else if(masterGradeId == 4){
						fluencyCutOff = 110; 
					}else if(masterGradeId == 3){
						fluencyCutOff = 100; 
					}else if(masterGradeId == 2){
						fluencyCutOff = 90; 
					}else if(masterGradeId == 1 || masterGradeId == 13){
						fluencyCutOff = 60; 
					}
				} 
			}
			
			for(StudentAssignmentStatus sas:studentAssignments){
				TaskForceResult tfr = new TaskForceResult();
				tfr.setFirstName(sas.getStudent().getUserRegistration().getFirstName());
				tfr.setLastName(sas.getStudent().getUserRegistration().getLastName());
				if(!taskMap.containsKey(sas.getStudent().getStudentId())){
					if(sas.getGradedStatus().equalsIgnoreCase(WebKeys.GRADED_STATUS_GRADED))
						tfr.setComprehension(sas.getPercentage());
				}
				tfr.setCheckComGrade(1);
				if(sas.getGradedStatus().equalsIgnoreCase(WebKeys.GRADED_STATUS_GRADED)){
					tfr.setComGraded(WebKeys.GRADED_STATUS_GRADED);
				}
				taskMap.put(sas.getStudent().getStudentId(), tfr);
				taskForceResultList.add(tfr);
			}
			for(BenchmarkResults br:benchmarkResults){
				TaskForceResult tfr = new TaskForceResult();
				if(taskMap.containsKey(br.getStudentAssignmentStatus().getStudent().getStudentId())){
					tfr = taskMap.get(br.getStudentAssignmentStatus().getStudent().getStudentId());
					
				}else{
					tfr.setFirstName(br.getStudentAssignmentStatus().getStudent().getUserRegistration().getFirstName());
					tfr.setLastName(br.getStudentAssignmentStatus().getStudent().getUserRegistration().getLastName());
					taskForceResultList.add(tfr);
				}
				if(br.getMedianFluencyScore() != null)
					tfr.setWcpm(br.getMedianFluencyScore());
				
				if(br.getQualityOfResponse() != null)
					tfr.setRetellScore(br.getQualityOfResponse().getQorId());
				
				if(br.getAccuracy() != null)
					tfr.setAccuracy(br.getAccuracy());
				
				tfr.setCheckFluencyGrade(fluencyCutOff);
				
				
				if(br.getStudentAssignmentStatus().getGradedStatus().equalsIgnoreCase(WebKeys.GRADED_STATUS_GRADED)){
					tfr.setFluencyGraded(WebKeys.GRADED_STATUS_GRADED);
				}
			}
		}catch(Exception e){
			logger.error("Error in getTaskForceResults() of ReadingSkillsDAOImpl"+e);
		}
		return taskForceResultList;
	}

	@Override
	public HashMap<String, List<Assignment>> getTaskForceTitles(long csId, String usedFor) {
		List<Assignment> assignments = new ArrayList<Assignment>();
		HashMap<String, List<Assignment>> titlesList = new HashMap<>();
		try{
			assignments = readingSkillsDAO.getTaskForceTitles(csId, usedFor);
			List<Assignment> fluency = new ArrayList<Assignment>();
			List<Assignment> comprehension = new ArrayList<Assignment>();
			for(Assignment ass: assignments){
				if(ass.getAssignmentType().getAssignmentType().equalsIgnoreCase(WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING)){
					fluency.add(ass);
				}else{
					comprehension.add(ass);
				}
			}
			titlesList.put(WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING, fluency);
			titlesList.put(WebKeys.ASSIGNMENT_TYPE_COMPREHENSION, comprehension);
		}catch(Exception e){
			logger.error("Error in getTaskForceTitles() of ReadingSkillsDAOImpl");
		}
		return titlesList;
	}	
}