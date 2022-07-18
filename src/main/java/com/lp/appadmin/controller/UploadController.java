package com.lp.appadmin.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lp.admin.dao.GradesDAO;
import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.AppAdminService3;
import com.lp.appadmin.service.UploadService;
import com.lp.common.dao.AssessmentDAO;
import com.lp.model.AssignmentType;
import com.lp.model.CAASPPTypes;
import com.lp.model.ColumnHeaders;
import com.lp.model.District;
import com.lp.model.FormativeAssessmentCategory;
import com.lp.model.FormativeAssessmentKeywords;
import com.lp.model.FormativeAssessmentRubric;
import com.lp.model.FormativeAssessments;
import com.lp.model.FormativeAssessmentsColumnHeaders;
import com.lp.model.FormativeAssessmentsGradeWise;
import com.lp.model.LegendCriteria;
import com.lp.model.MasterGrades;
import com.lp.model.School;
import com.lp.model.StemAreas;
import com.lp.model.Trimester;
import com.lp.teacher.service.StemCurriculumSubService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class UploadController {
	

	@Autowired
	private StemCurriculumSubService stemCurriculumSubService;
	@Autowired
	private UploadService uploadservice;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private AdminService adminService;
	@Autowired
	private GradesDAO gradeDAO;
	@Autowired
	private AppAdminService3 appadminservice3;
	
	static final Logger logger = Logger.getLogger(UploadController.class);
		
	@RequestMapping(value = "/legendUpload", method = RequestMethod.GET)
	public ModelAndView legendUpload(HttpSession session) {					
		ModelAndView model = new ModelAndView("AppManager/legend_upload");
		List<LegendCriteria> legendCriterias = new ArrayList<LegendCriteria>();
		List<MasterGrades> masterGrades = new ArrayList<MasterGrades>();
		List<District> districts = new ArrayList<District>();
		try{
			legendCriterias = stemCurriculumSubService.getStemLegendCriteria();			
			masterGrades = adminService.getMasterGrades();
			districts = uploadservice.getDistricts();
		}catch(Exception e){
    		logger.error("Error in legendUpload() of UploadController"+e);
		}		
		model.addObject("legendCriterias", legendCriterias);
		model.addObject("masterGrades", masterGrades);
		model.addObject("districts", districts);
		model.addObject("check", WebKeys.LP_STEM);
		return model; 
	}
	
	@RequestMapping(value = "/legendLIUpload", method = RequestMethod.GET)
	public ModelAndView legendLIUpload(HttpSession session) {					
		ModelAndView model = new ModelAndView("AppManager/legend_upload");
		List<LegendCriteria> legendCriterias = new ArrayList<LegendCriteria>();
		List<MasterGrades> masterGrades = new ArrayList<MasterGrades>();
		List<District> districts = new ArrayList<District>();
		try{
			legendCriterias = stemCurriculumSubService.getLegendCriteria();			
			masterGrades = adminService.getMasterGrades();
			districts = uploadservice.getDistricts();
		}catch(Exception e){
    		logger.error("Error in legendUpload() of UploadController"+e);
		}		
		model.addObject("legendCriterias", legendCriterias);
		model.addObject("masterGrades", masterGrades);
		model.addObject("districts", districts);
		model.addObject("check", WebKeys.LP_TAB_UPLOAD_IOLRUBRIC);
		return model; 
	}
		
	@RequestMapping(value="/legendUploadFile", method=RequestMethod.POST)
	public View legendUploadFile(@RequestParam("file") MultipartFile file,Model model,HttpServletRequest request){
	   	boolean status = false;
	    if (!file.isEmpty()) {
	    	try {
	    		long lCriteriaId = Long.parseLong(request.getParameter("lCriteriaId"));
	    		String mGrades[] = request.getParameterValues("masterId");
	    		long districtId = Long.parseLong(request.getParameter("districtId"));
	    		String check = request.getParameter("check");
	    		List<MasterGrades> masterGrades = new ArrayList<MasterGrades>();
	    		for(int i=0;i<mGrades.length;i++){
	    			MasterGrades mg = new MasterGrades();
	    			mg.setMasterGradesId(Long.parseLong(mGrades[i]));
	    			masterGrades.add(mg);
	    		}
	    		status = uploadservice.saveLegendFromFile(file,lCriteriaId, masterGrades, check, districtId);
	            if(status){
	            	model.addAttribute("status", WebKeys.UPLOAD_LEGENDS_SUCCESS);
	            } else {
	            	model.addAttribute("status", WebKeys.UPLOAD_LEGENDS_FAILURE);
	            }
	    	} catch (Exception e) {
	    		model.addAttribute("status", WebKeys.UPLOAD_LEGENDS_FAILURE);
	    		logger.error("Error in legendUploadFile() of  UploadController"+e);
	        }
	    } else {
	    	model.addAttribute("status", WebKeys.UPLOAD_LEGENDS_FAILURE_EMPTY);
	    }
	    return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/uploadFormativeAssessments", method = RequestMethod.GET)
	public ModelAndView uploadStrands(HttpSession session) {
		ModelAndView model = new ModelAndView("AppManager/upload_xls");
		return model; 
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	@RequestMapping(value = "/uploadFormativeContent", method = RequestMethod.POST)
	public View uploadFormativeContent(
			@RequestBody final String jsonString, 
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws JsonParseException, JsonMappingException, IOException {
		 String commonFilesPath = FileUploadUtil.getLpCommonFilesPath();
		 String fullPath = commonFilesPath+File.separator+WebKeys.EXCEL_UPLOAD;
		 File file = new File(fullPath);
		 if(!file.exists())
			 file.mkdir();
		 fullPath = fullPath+File.separator+WebKeys.FORMATIVE_ASSESSMENT_LOG;
		 File f = new File(fullPath);
		 FileUtils.write(f, "");
		 FileOutputStream out = new FileOutputStream(fullPath,true);
		 ObjectMapper mapper = new ObjectMapper();
	     Map<String, ArrayList<Map<String, String>>> xlsxMap;
	     boolean isEmpty = true;
	     long formativeAssessmentsId = 0;
         try {
         xlsxMap = mapper.readValue(jsonString, HashMap.class);
     	 ArrayList<Map<String, String>>  assessmentMap =  (ArrayList<Map<String, String>>) xlsxMap.get("formative_assessment");
     	 if(assessmentMap != null){
        	  if(assessmentMap.size() > 0){
	        	  List<String> perfDescLt = new ArrayList<String>();
	        	  List<String> rubricColumnsLt = new ArrayList<String>();
	        	  String keyWords = ""; 
	        	  String gradeLevel = "";
	        	  for (Map<String, String> map : assessmentMap){
	        		  
	        		  if (map.containsKey("Keywords")) 
	        		     keyWords =  map.get("Keywords").trim();
	        		  
	        		  if (map.containsKey("Grade Level")) 
	        			  gradeLevel =  map.get("Grade Level").trim();
	        		  
	        		  if (map.containsKey("Rubric Columns")){ 
		        		 String rubColumn =  map.get("Rubric Columns").trim();
		        		 rubricColumnsLt.add(rubColumn);
	        		  }
	        		  if (map.containsKey("Performance Description")){ 
		        		 String perfDesc =  map.get("Performance Description").trim();
		        		 perfDescLt.add(perfDesc);
	        		  }
	       	      }
	        	    Map<String, String> map = assessmentMap.get(0);
	        		FormativeAssessments fa = new FormativeAssessments();
		            for(Entry<String, String> e : map.entrySet()) {
			           		String field = e.getKey().trim();
		                    String value =  e.getValue().trim();
			           		if(field.equalsIgnoreCase("Title")){
			           			fa.setTitle(value);	
			           			continue;
			           		}
			           		if(field.equalsIgnoreCase("Description")){
			           			fa.setDescription(value);	
			           			continue;
			           		}
			           		if(field.equalsIgnoreCase("Instructions")){
			           			fa.setInstructions(value);	
			           			continue;
			           		}
			           		if(field.equalsIgnoreCase("Type of Assessment")){
			           			AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(value);
			           			if(assignmentType.getAssignmentTypeId() > 0){
				           			fa.setAssignmentType(assignmentType);
				           			continue;
			           			}else{
			           				String logError = "Type of Assessment: '"+value+"' not found \r\n\n";
									out.write(logError.getBytes());
									break;
			           			}
			           		}
        	       }
		          formativeAssessmentsId = uploadservice.saveFormativeAssessment(fa);
		          if(formativeAssessmentsId > 0){
		        	  if(keyWords.contains(",")){
		        		String keyWordsArr[] =  keyWords.split(",");
		        		for (String keyword : keyWordsArr) {
			        		 FormativeAssessmentKeywords fak = new FormativeAssessmentKeywords();
			        		 fak.setFormativeAssessments(fa);
			        		 fak.setKeyword(keyword.trim());
			        		 uploadservice.saveFormativeAssessmentKeywords(fak);
						}
		        	  }else{
		        		 FormativeAssessmentKeywords fak = new FormativeAssessmentKeywords();
		        		 fak.setFormativeAssessments(fa);
		        		 fak.setKeyword(keyWords.trim()); 
		        		 uploadservice.saveFormativeAssessmentKeywords(fak);
		        	  }
		        	  if(gradeLevel.contains(",")){
		        		   String gradeLevelsFirstArr[] =  gradeLevel.split(",");
			        		for(int i=0;i<gradeLevelsFirstArr.length;i++){
			        			splitGradeLevel(gradeLevelsFirstArr[i],out,fa);
			        		}
		            	  }
		        	  else if(gradeLevel.contains("-")){
		        		  splitGradeLevel(gradeLevel,out,fa);
		        	  }else{
		        		  if(gradeLevel.length() > 0){
			        		   long grade = Long.parseLong(String.valueOf(gradeLevel));
				        	   long masterGradeId= gradeDAO.getMasterGradeIdbyGradeId(grade);
				        	   if(masterGradeId > 0){
			        			MasterGrades masterGrade = appadminservice3.getMasterGrade(masterGradeId);
			        			FormativeAssessmentsGradeWise fag = new FormativeAssessmentsGradeWise();
			        			fag.setMasterGrades(masterGrade);
			        			fag.setFormativeAssessments(fa);
			        			fag.setStatus(WebKeys.LP_STATUS_ACTIVE);
			        			uploadservice.saveFormativeAssessmentsGradeWise(fag);
				        	   }else{
									String logError = "Grade Id: "+grade+" not found \r\n\n";
									out.write(logError.getBytes());
							   }
		        		  }
		        	  }
		        	  for (String perfDesc : perfDescLt) {
			             FormativeAssessmentCategory fac = new FormativeAssessmentCategory();
			             fac.setCategory(perfDesc);
			             fac.setFormativeAssessments(fa);
			             uploadservice.saveFormativeAssessmentCategory(fac);
					  }
		        	  for (String columnName : rubricColumnsLt) {
		        		  columnName = columnName.replaceAll("\\r\\n|\\r|\\n", " ");
		        		  ColumnHeaders columnHeader =  uploadservice.getColumnHeaderByColumn(columnName);
		        		  if(columnHeader.getColumnHeaderId() > 0){
			        		  FormativeAssessmentsColumnHeaders fach= new FormativeAssessmentsColumnHeaders();
			        		  fach.setFormativeAssessments(fa);
			        		  fach.setColumnHeaders(columnHeader);
				              uploadservice.saveFormativeAssessmentsColumnHeaders(fach);
		        		  }
					  }
		        	  
		        	  ArrayList<Map<String, String>>  rubricMap = (ArrayList<Map<String, String>>) xlsxMap.get("formative_rubric");
		        	  if(rubricMap != null){
			        	  if(rubricMap.size() > 0){
			        		  Collections.reverse(rubricMap);
			        		  int score = 1;
			        		  for (Map<String, String> rubric : rubricMap) {
				   	           	   for(Entry<String, String> e : rubric.entrySet()) {
			   	                       String category = e.getKey();
			   	                       String desc =  e.getValue();
			   	                       FormativeAssessmentCategory fac = uploadservice.getFormativeAssessmentCategoryByCategory(formativeAssessmentsId, category);
			   	                       if(fac.getFormativeAssessmentSubCateId() > 0){
				   	                       FormativeAssessmentRubric far = new FormativeAssessmentRubric();
				   	                       far.setFormativeAssessmentCategory(fac);
				   	                       far.setDescription(desc);
				   	                       far.setScore(score);
				   	                       uploadservice.saveFormativeAssessmentRubric(far);
			   	                       }
				   	                  }
				   	           	 score = score+1;
			   	       	      } 
			        	  }
		        	  }
		          }
		      }else{
		    	    String logError = WebKeys.INVALID_EXCEL_DATA_UPLOAD;
					out.write(logError.getBytes());
		      }
     	 }else{
	    	    String logError = WebKeys.INVALID_EXCEL_FORMAT;
				out.write(logError.getBytes());
	     }
    	 BufferedReader br = new BufferedReader(new FileReader(fullPath));     
    	  if (br.readLine() != null) {
    		  isEmpty = false;
    		  String headerStr = WebKeys.UPLOAD_EXCEL_HEADER; 
    		  StringBuilder result = new StringBuilder();
    		  result.append(headerStr);
    		  LineIterator it = FileUtils.lineIterator(f);
    		  try {
    		    while (it.hasNext()) {
      		      result.append(it.nextLine()+"\n");
    		    }
    		  } finally {
    		    it.close();
    		  }
    		 FileUtils.writeStringToFile(f, result.toString());
    		 model.addAttribute("status",WebKeys.LOG_CHECK); 
    	  }else{
    		  model.addAttribute("status", WebKeys.UPLOAD_FORMATIVE_SUCCESS); 
    	  }
    	  model.addAttribute("isEmpty", isEmpty);
    	  model.addAttribute("path", fullPath);
         } catch (Exception e) {
        	 e.printStackTrace();
        	 model.addAttribute("status", WebKeys.UPLOAD_FORMATIVE_FAILURE); 
         } finally {
        	 out.close(); 
         }
        return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/uploadStemStrands", method = RequestMethod.GET)
	public ModelAndView uploadStemStrands(HttpSession session) {					
		ModelAndView model = new ModelAndView("AppManager/stem_strands_upload");
		List<StemAreas> stemAreas = new ArrayList<StemAreas>();
		List<MasterGrades> masterGrades = new ArrayList<MasterGrades>();
		List<School> schoolStates = new ArrayList<School>();
		try {			
			stemAreas = uploadservice.getAllStemAreas();			
			masterGrades = adminService.getMasterGrades();		
			schoolStates = uploadservice.getAllSchoolStates();
		}catch(Exception e){
			logger.error("Error in uploadStemStrands() of  UploadController");
		}
		model.addObject("stemAreas", stemAreas);
		model.addObject("masterGrades", masterGrades);
		model.addObject("schoolStates", schoolStates);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
	    return model; 
	}
	
	@RequestMapping(value="/uploadStrandsFile", method=RequestMethod.POST)
	public View uploadStrandsFile(@RequestParam("file") MultipartFile file,Model model,HttpServletRequest request){
	   	boolean status = false;
	    if (!file.isEmpty()) {
	    	try {
	    		long areaId = Long.parseLong(request.getParameter("areaId"));
	    		long mGradeId = Long.parseLong(request.getParameter("masterId"));	   
	    		long stateId = Long.parseLong(request.getParameter("stateId"));	   
	    		status = uploadservice.uploadStrandsFile(file,areaId,mGradeId,stateId);
	            if(status){
	            	model.addAttribute("status", WebKeys.UPLOAD_STEM_STRANDS_SUCCESS);
	            } else {
	            	model.addAttribute("status", WebKeys.UPLOAD_STEM_STRANDS_FAILURE);
	            }
	    	} catch (Exception e) {
	    		model.addAttribute("status", WebKeys.UPLOAD_STEM_STRANDS_FAILURE);
	    		logger.error("Error in uploadStrandsFile() of UploadController"+e);
	        }
	    } else {
	    	model.addAttribute("status", WebKeys.UPLOAD_STEM_STRANDS_FAILURE_EMPTY);
	    }
	    return new MappingJackson2JsonView();
	}
	public void splitGradeLevel(String gradeLevel,FileOutputStream out,FormativeAssessments fa) throws Exception{
		if(gradeLevel.contains("-")){
    		String gradeLevelsArr[] =  gradeLevel.split("-");
    		int start = Integer.parseInt(gradeLevelsArr[0].trim());
    		int end = Integer.parseInt(gradeLevelsArr[1].trim());
    		if(start < end){
    			for (int i = start; i <= end; i++) {
					long masterGradeId= gradeDAO.getMasterGradeIdbyGradeId(Long.parseLong(String.valueOf(start)));
					if(masterGradeId > 0){
	        			MasterGrades masterGrade = appadminservice3.getMasterGrade(masterGradeId);
	        			FormativeAssessmentsGradeWise fag = new FormativeAssessmentsGradeWise();
	        			fag.setMasterGrades(masterGrade);
	        			fag.setFormativeAssessments(fa);
	        			fag.setStatus(WebKeys.LP_STATUS_ACTIVE);
	        			uploadservice.saveFormativeAssessmentsGradeWise(fag);
					}else{
						String logError = "Grade Id: "+start+" not found \r\n\n";
						out.write(logError.getBytes());
					}
					start = start+1;
				}
	          }else{
					String logError = "Grade Id: "+start+" not found \r\n\n";
					out.write(logError.getBytes());
	          }
	  }
	}
	
	@RequestMapping(value = "/uploadIOLRubrics", method = RequestMethod.GET)
	public ModelAndView uploadIOLRubrics(HttpSession session) {					
		ModelAndView model = new ModelAndView("CommonJsp/upload_iol_rubrics");
		model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD);
		model.addObject("page", WebKeys.LP_TAB_UPLOAD_IOLRUBRIC);
		/*Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");*/
		List<LegendCriteria> legendCriterias = new ArrayList<LegendCriteria>();
		try{	
			legendCriterias = stemCurriculumSubService.getStemLegendCriteria();		
		}catch(Exception e){
    		logger.error("Error in uploadIOLRubrics() of UploadController"+e);
		}		
		model.addObject("legendCriterias", legendCriterias);
		/*model.addObject("teacherObj", teacherObj);*/
	    return model; 
	}
	
	@RequestMapping(value="/uploadIOLRubricFile", method=RequestMethod.POST)
	public View uploadIOLRubricFile(@RequestParam("file") MultipartFile file,Model model,HttpServletRequest request){
	   	boolean status = false;
	    if (!file.isEmpty()) {
	    	try {
	    		long lCriteriaId = Long.parseLong(request.getParameter("lCriteriaId"));
	    		List<MasterGrades> masterGrades = new ArrayList<MasterGrades>();	    		
	    		status = uploadservice.saveLegendFromFile(file,lCriteriaId,masterGrades,WebKeys.LP_TAB_UPLOAD_IOLRUBRIC,0);//no district id for IOL
	            if(status){
	            	model.addAttribute("status", WebKeys.UPLOAD_IOL_SUCCESS);
	            } else {
	            	model.addAttribute("status", WebKeys.UPLOAD_IOL_FAILURE);
	            }
	    	} catch (Exception e) {
	    		model.addAttribute("status", WebKeys.UPLOAD_IOL_FAILURE);
	    		logger.error("Error in uploadIOLRubricFile() of UploadController"+e);	    		
	        }
	    } else {
	    	model.addAttribute("status", WebKeys.UPLOAD_IOL_FAILURE_EMPTY);
	    }
	    return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/uploadStarScores", method = RequestMethod.GET)
	public ModelAndView uploadStarScores(HttpSession session) {					
		ModelAndView model = new ModelAndView("AppManager/star_scores_upload");
		List<Trimester> trimesters = new ArrayList<Trimester>();
		List<CAASPPTypes> cAASPPTypes = new ArrayList<CAASPPTypes>();
		try{
			trimesters = uploadservice.getTrimesters();			
			cAASPPTypes = uploadservice.getCAASPPTypes();
		}catch(Exception e){
    		logger.error("Error in legendUpload() of UploadController"+e);
		}		
		model.addObject("trimesters", trimesters);
		model.addObject("cAASPPTypes", cAASPPTypes);
		return model; 
	}
	
	@RequestMapping(value="/starUploadFile", method=RequestMethod.POST)
	public View starUploadFile(@RequestParam("file") MultipartFile file,Model model,HttpServletRequest request){
		//Header Format for Upload file
		//Student SC Id	| Grade | School_ABR_Name | Score | Teacher SC Id | Test Date(guided by Lalitha)
	   	boolean status = false;
	    if (!file.isEmpty()) {
	    	try {
	    		long trimesterId = Long.parseLong(request.getParameter("trimesterId"));
	    		long caasppId = Long.parseLong(request.getParameter("caasppId"));	    		
	    		status = uploadservice.starUploadFile(file,trimesterId,caasppId);
	            if(status){
	            	model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_SUCCESS);
	            } else {
	            	model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_FAILURE);
	            }
	    	} catch (Exception e) {
	    		model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_FAILURE);
	    		logger.error("Error in starUploadFile() of UploadController"+e);	    		
	        }
	    } else {
	    	model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_FAILURE_EMPTY);
	    }
	    return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/caasppScoresUpload", method = RequestMethod.GET)
	public ModelAndView caasppScoresUpload(HttpSession session) {					
		ModelAndView model = new ModelAndView("AppManager/caaspp_scores_upload");
		return model; 
	}
	
	@RequestMapping(value="/caasppFileUpload", method=RequestMethod.POST)
	public View caasppFileUpload(@RequestParam("file") MultipartFile file,Model model,HttpServletRequest request){
		//Header Format for Upload file
		//Student SC Id	| Grade | School_ABR_Name | ELA.Level | Math.Level | Teacher SC Id  --(guided by Lalitha)
	   	boolean status = false;
	    if (!file.isEmpty()) {
	    	try {	    		
	    		status = uploadservice.caasppFileUpload(file);
	            if(status){
	            	model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_SUCCESS);
	            } else {
	            	model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_FAILURE);
	            }
	    	} catch (Exception e) {
	    		model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_FAILURE);
	    		logger.error("Error in caasppFileUpload() of UploadController"+e);	    		
	        }
	    } else {
	    	model.addAttribute("status", WebKeys.UPLOAD_STAR_SCORE_FAILURE_EMPTY);
	    }
	    return new MappingJackson2JsonView();
	}
}
