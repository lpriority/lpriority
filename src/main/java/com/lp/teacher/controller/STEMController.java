package com.lp.teacher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminServiceImpl;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignStemUnits;
import com.lp.model.Grade;
import com.lp.model.StemAreas;
import com.lp.model.StemCurriculum;
import com.lp.model.StemList;
import com.lp.model.StemQuestions;
import com.lp.model.StemQuestionsType;
import com.lp.model.StemStrategies;
import com.lp.model.StemUnit;
import com.lp.model.StemUnitActList;
import com.lp.model.StemUnitActivity;
import com.lp.model.Teacher;
import com.lp.model.UnitStemAreas;
import com.lp.model.UnitStemContentQuestions;
import com.lp.model.UnitStemStrands;
import com.lp.model.UnitStemStrategies;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.teacher.service.StemCurriculumService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class STEMController {

	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private StemCurriculumService stemCurriculumService;
	@Autowired
	private IOLReportCardService iolReportCardService;
	@Autowired
	private AdminServiceImpl adminServiceImpl;
	static final Logger logger = Logger.getLogger(STEMController.class);
	
	@RequestMapping(value = "/stem", method = RequestMethod.GET)
	public ModelAndView goToStemcurriculum(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView(
				"Teacher/stem_curriculum_main");
		model.addObject("tab", WebKeys.LP_TAB_STEM);
		model.addObject("LP_STEM_TAB", WebKeys.LP_TAB_STEM);
		Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
		try {
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
			model.addObject("teacherObj", teacherObj);
			model.addObject("page","stem");
			model.addObject("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		} catch (DataException e) {
			logger.error("Error in goToStemcurriculum() of STEMController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/displayStemPaths")
	protected ModelAndView appendUnitField(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_paths");
		model.addObject("gradeId", gradeId);
		model.addObject("classId", classId);
		return model;
	}
	
	@RequestMapping(value = "/getStemPaths", method = RequestMethod.GET)
	public View getStemPaths(HttpSession session, Model model) {
		model.addAttribute("stemPaths",stemCurriculumService.getStemPaths());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/openStemUnit")
	protected ModelAndView openStemUnit(
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session,
			@RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("pathId") long pathId
			) {
		   ModelAndView model = new ModelAndView("Ajax/Teacher/create_stem_unit");
		   StemUnit stemUnit=new StemUnit();
		   String mode="create";
		   try {
				if(stemUnitId>0){
				stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
				mode="edit";
				}
				if(stemUnit.getSrcStemUnitId() != null){
					if(stemUnit.getSrcStemUnitId() > 0){
						 StemUnit srcStemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnit.getSrcStemUnitId());
						 model.addObject("srcStemUnit", srcStemUnit);
					}else{
						model.addObject("srcStemUnit", "");
					}
				}else{
					model.addObject("srcStemUnit", "");
				}
				model.addObject("stemUnit", stemUnit);
				model.addObject("stemUnitId", stemUnitId);
				model.addObject("pathId", pathId);
				model.addObject("mode",mode);
				model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
			} catch (Exception e) {
				logger.error("Error in openStemUnit() of STEMController"
						+ e);
				e.printStackTrace();
				
			}
		   
			return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getUnitContent")
	protected ModelAndView getUnitContent(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("stemUnitId") long stemUnitId, @RequestParam("pathId") long pathId) {
		   ModelAndView model = new ModelAndView("Ajax/Teacher/stem_add_unit");
		   StemUnit stemUnit=new StemUnit();
		   String mode="create";
		   try {
			   model.addObject("pathId",pathId);
				if(stemUnitId>0){
					stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
					mode="edit";
				}
				
				if(stemUnit.getSrcStemUnitId() != null){
					if(stemUnit.getSrcStemUnitId() > 0){
						 StemUnit srcStemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnit.getSrcStemUnitId());
						 model.addObject("srcStemUnit", srcStemUnit);
					}else{
						model.addObject("srcStemUnit", "");
					}
				}else{
					model.addObject("srcStemUnit", "");
				}
				model.addObject("stemUnit", stemUnit);
				model.addObject("stemUnitId", stemUnitId);				
				model.addObject("mode", mode);	
			} catch (Exception e) {
				logger.error("Error in getUnitContent() of STEMController"+ e);
				e.printStackTrace();
			}
		return model;
	}
	
	@RequestMapping(value = "/saveStemUnits")
	public View saveStemUnits(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("stemPathId") long stemPathId,
			@RequestParam("stemUnitName") String stemUnitName,
			@RequestParam("stemUnitDesc") String stemUnitDesc,
			@RequestParam("isShared") String isShared,
			@RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("urlLinks") String urlLinks,
			@RequestParam("mode") String mode,Model model) throws DataException {
		boolean isError = false;		
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		StemUnit stemUnits = new StemUnit();
		
		try {
			if (userReg != null) {
				try {
					if(stemUnitId>0){
						stemUnits=stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
					}
					stemUnits.setStemUnitName(stemUnitName);
					stemUnits.setStemUnitDesc(stemUnitDesc);
					stemUnits.setIsShared(isShared);
					stemUnits.setUrlLinks(urlLinks);
					String status = stemCurriculumService.saveStemUnits(gradeId,classId,stemPathId,stemUnits, userReg,mode);
					if (status.equalsIgnoreCase(WebKeys.SUCCESS)){
						if(stemUnitId>0){
							model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
							if(stemUnits.getSrcStemUnitId() != null)
								model.addAttribute("srcStemUnitId", stemUnits.getSrcStemUnitId());
						}else{
							model.addAttribute("status", WebKeys.LP_CREATED_SUCCESS);
						}
					}else if(status.equalsIgnoreCase(WebKeys.LP_UNIT_ALREADY_EXISTS)) {
						 model.addAttribute("status", WebKeys.LP_UNIT_ALREADY_EXISTS);
					}else if(status.equalsIgnoreCase(WebKeys.LP_FAILED)){
						model.addAttribute("status", WebKeys.LP_FAILED);
					}
					model.addAttribute("mode", mode);
					model.addAttribute("stemUnitId", stemUnits.getStemUnitId());
				} catch (DataException e) {
					isError = true;
					logger.error("Error in saveStemUnits() of of STEMController"
							+ e);
					model.addAttribute("status", WebKeys.LP_CREATE_STEM_UNITS_ERROR);
					session.setAttribute("isError", isError);
				}
			}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		} catch (Exception e) {
			logger.error("Error in saveStemUnits() of STEMController" + e);
			model.addAttribute("status", WebKeys.LP_CREATE_STEM_UNITS_ERROR);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/autoSaveStemUnits")
	public View autoSaveStemUnits(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("updatedVal") String updatedVal,
			@RequestParam("elementId") String elementId,
			Model model) throws DataException {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		StemUnit stemUnit = new StemUnit();
		try {
			if (userReg != null) {
	 			  if(stemUnitId>0){
	 				 stemUnit=stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
	 				 model.addAttribute("stemUnitId", stemUnitId);
				  }
	 			  if(elementId.equalsIgnoreCase("stemUnitName")){
	 				 stemUnit.setStemUnitName(updatedVal);
	 			  }else if(elementId.equalsIgnoreCase("stemUnitDesc")){
	 				 stemUnit.setStemUnitDesc(updatedVal);
	 			  }else if(elementId.equalsIgnoreCase("shareId")){
	 				 stemUnit.setIsShared(updatedVal);
	 			  }else if(elementId.equalsIgnoreCase("urlLinks")){ 
	 				 stemUnit.setUrlLinks(updatedVal);
	 			  }
	 			 String status = stemCurriculumService.autoSaveStemUnits(stemUnit);
	 			if (status.equalsIgnoreCase(WebKeys.SUCCESS)){
					if(stemUnitId>0){
						model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
						if(stemUnit.getSrcStemUnitId() != null)
							model.addAttribute("srcStemUnitId", stemUnit.getSrcStemUnitId());
					}else{
						model.addAttribute("status", WebKeys.LP_CREATED_SUCCESS);
					}
				}else if(status.equalsIgnoreCase(WebKeys.LP_UNIT_ALREADY_EXISTS)) {
					 model.addAttribute("status", WebKeys.LP_UNIT_ALREADY_EXISTS);
				}else if(status.equalsIgnoreCase(WebKeys.LP_FAILED)){
					model.addAttribute("status", WebKeys.LP_FAILED);
				}
			 }
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html");  
		} catch (Exception e) {
			logger.error("Error in autoSaveStemUnits() of STEMController" + e);
			model.addAttribute("status", WebKeys.LP_CREATE_STEM_UNITS_ERROR);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/openEssentialUnit")
	protected ModelAndView openEssentialUnit(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("stemUnitId") long stemUnitId) {
		StemList stemList = new StemList();
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_add_essential_question");
		List<StemQuestions> stemEssUnitQues = new ArrayList<StemQuestions>();
		String mode="create";
		try {
			if(stemUnitId>0){
				StemUnit stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
				stemEssUnitQues = stemUnit.getStemQuestionsLt();
				if(stemEssUnitQues.size() > 0){
					mode="edit";
					model.addObject("listSize",stemEssUnitQues.size());
					stemList.setStemQuestionList(stemEssUnitQues);
				}					
			}
			model.addObject("stemEssUnitQues", stemEssUnitQues);
			model.addObject("mode",mode);		
			model.addObject("stemUnitId", stemUnitId);
		} catch (Exception e) {
			logger.error("Error in editStemUnit() of STEMController"
					+ e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/saveEssentialQues", method = RequestMethod.POST)
	public void saveEssentialQues(
			@RequestParam("essQuestion") String essQuestion,
			@RequestParam("unitQuesArr") ArrayList<String> unitQuesArr,
			@RequestParam("stemUnitId") long stemUnitId,
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session, Model model) {
		boolean isError = false;
		StemQuestionsType stemQuesType = null;
		StemQuestions stemQuestion = null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			if (userReg != null) {
				try {
					StemUnit stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
					List<StemQuestions> stemQuestionLt = new ArrayList<StemQuestions>();
					
					stemQuesType = new StemQuestionsType();
					stemQuesType.setStemQuesTypeId(WebKeys.LP_STEM_ESSENTIAL_QUES_TYPE_ID.longValue());
					stemQuestion = new StemQuestions();
					stemQuestion.setStemQuestion(essQuestion);
					stemQuestion.setStemUnit(stemUnit);
					stemQuestion.setStemQuestionsType(stemQuesType);
					stemQuestionLt.add(stemQuestion);

					if(unitQuesArr.size() > 0){
						stemQuesType = new StemQuestionsType();
						stemQuesType.setStemQuesTypeId(WebKeys.LP_STEM_UNIT_QUES_TYPE_ID.longValue());
						for(int i= 0; i<unitQuesArr.size();i++){
							stemQuestion = new StemQuestions();
							stemQuestion.setStemQuestion(unitQuesArr.get(i));
							stemQuestion.setStemUnit(stemUnit);
							stemQuestion.setStemQuestionsType(stemQuesType);
							stemQuestionLt.add(stemQuestion);
						}
					}
					boolean status =stemCurriculumService.saveEssentialQues(stemQuestionLt);
					if (status) {
						 response.getWriter().write(WebKeys.STEM_QUESTIONS_SAVED_SUCCESS);
						 model.addAttribute("stemUnitId", stemUnit.getStemUnitId());
					} else {
						 //response.getWriter().write(WebKeys.LP_UNIT_ALREADY_EXISTS);
					}
				} catch (DataException e) {
					isError = true;
					logger.error("Error in saveEssentialQues() of of STEMController"
							+ e);
					 session.setAttribute("isError", isError);
					 response.getWriter().write(WebKeys.LP_CREATE_STEM_ESSential_UNIT_QUES_ERROR);
				}
			}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		} catch (IOException e) {
			logger.error("Error in saveEssentialQues() of STEMController" + e);
			e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/openStrandsWindow")
	protected ModelAndView openStrandsWindow(
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session,Model model,
			@RequestParam("unitStemAreaId") long unitStemAreaId,
			@RequestParam("stemAreaId") long stemAreaId,
			@RequestParam("stemArea") String stemArea,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("stemGradeStrandId") long stemGradeStrandId) {
		long masterGradeId=0;
		if(stemGradeStrandId == -1 ){
			masterGradeId=stemCurriculumService.getGrade(gradeId).getMasterGrades().getMasterGradesId();
		} else{
			masterGradeId = stemCurriculumService.getGradeStrandBystemGradeStrands(stemGradeStrandId).getMasterGrades().getMasterGradesId();
		}
		model.addAttribute("grades", adminServiceImpl.getMasterGrades());
		model.addAttribute("unitStemAreaId",unitStemAreaId);
		model.addAttribute("stemArea",stemArea);
		model.addAttribute("gradeId",gradeId);
		model.addAttribute("stemAreaId",stemAreaId);
		model.addAttribute("stemGradeStrandId",stemGradeStrandId);
		model.addAttribute("masterGradeId", masterGradeId);
		model.addAttribute("stemGradeStrands" ,stemCurriculumService.getGradeStrands(masterGradeId, stemArea));
		return new ModelAndView("Ajax/Teacher/stem_open_strands_window");
	}
	
	@RequestMapping(value = "/getStemStrands", method = RequestMethod.GET)
	public View getGradeStemStrands(
			@RequestParam("gradeId") long gradeId, @RequestParam("stemArea") String stemArea,
			HttpSession session,
			Model model) {
		model.addAttribute("stemGradeStrands" ,stemCurriculumService.getGradeStrands(gradeId, stemArea));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getStemAreas", method = RequestMethod.GET)
	public View getStemAreas(
			@RequestParam("type") String type,
			HttpSession session,
			Model model) {
		 List<StemAreas> stemAreasLt = stemCurriculumService.getStemAreas(type);
		model.addAttribute("stemAreasLt",stemAreasLt);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/saveStrandConcept", method = RequestMethod.GET)
	public View saveStrandConcept(
			@RequestParam("narrative") String narrative,
			@RequestParam("stemGradeStrandId") long stemGradeStrandId,
			@RequestParam("mode") String mode,
			@RequestParam("strandConceptIdArr") ArrayList<Long> strandConceptIdArr,
			@RequestParam("unitStemAreaId") long unitStemAreaId,
			HttpSession session,
			HttpServletResponse response,Model model) {
		 try {
			 Long unitStemStrandsId = stemCurriculumService.saveStrandConcept(stemGradeStrandId,narrative, strandConceptIdArr,  mode, unitStemAreaId);
			 if(unitStemStrandsId > 0){
				 if(mode.equalsIgnoreCase("create")){	
					 model.addAttribute("status",WebKeys.LP_CREATED_SUCCESS);
				 }else{
					 model.addAttribute("status",WebKeys.LP_UPDATED_SUCCESS);
				 }
				 model.addAttribute("unitStemStrandsId",unitStemStrandsId);
			 }else{
				 model.addAttribute("status",WebKeys.LP_SYSTEM_ERROR);
			 }
		 } catch (Exception e) {			 
			 e.printStackTrace();
		 }
		 return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/viewStemUnits", method = RequestMethod.GET)
	public ModelAndView viewStemUnits(HttpSession session,@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,@RequestParam("pathId") long pathId) {
		List<StemUnit> stemUnits = new ArrayList<StemUnit>();
		ModelAndView model = new ModelAndView(
				"Ajax/Teacher/view_stem_units");
		Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			stemUnits = stemCurriculumService.getStemUnits(gradeId,classId,pathId,userReg);
			model.addObject("stemUnitList", stemUnits);
			model.addObject("teacherObj", teacherObj);
		} catch (DataException e) {
			logger.error("Error in viewStemUnits() of STEMController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}
	
	@RequestMapping(value = "/getStemUnitActivities", method = RequestMethod.POST)
	public ModelAndView getStemUnitActivities(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("unitStemAreaId") long unitStemAreaId) {
		boolean isError = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		StemUnitActList stemUnitActList = new StemUnitActList();
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_display_activities", 
				"stemUnitActList", stemUnitActList);
		try {
			if (userReg != null) {
				try {
					List<StemUnitActivity> stemUnitActivities = new ArrayList<StemUnitActivity>();	
					stemUnitActivities = stemCurriculumService.getStemUnitActivities(unitStemAreaId);
					stemUnitActList.setStemUnitAct(stemUnitActivities);
					model.addObject("stemUnitActList", stemUnitActList);
					model.addObject("noOfActQues", stemUnitActivities.size());
				} catch (DataException e) {
					isError = true;
					logger.error("Error in getStemUnitActivities() of of STEMController"+ e);
					 session.setAttribute("isError", isError);
				}
			}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		} catch (Exception e) {
			logger.error("Error in getStemUnitActivities() of STEMController" + e);
		}
		return model;
	}
	
	@RequestMapping(value = "/saveStemActivities", method = RequestMethod.POST)
	public View saveStemActivities(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,
			@ModelAttribute("stemUnitActList") StemUnitActList stemUnitActList,
			@RequestParam("files") List<MultipartFile> files,Model model) {
		boolean isError = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			if (userReg != null) {
				try {			
					boolean status = false;
					List<StemUnitActivity> stemUnitActivities = new ArrayList<StemUnitActivity>();
					UnitStemAreas unitStemAreas = new UnitStemAreas();
					unitStemAreas.setUnitStemAreasId(Long.parseLong(request.getParameter("unitStemAreasId")));
					stemUnitActivities = stemUnitActList.getStemUnitAct();
					for(StemUnitActivity sa: stemUnitActivities){
						sa.setUnitStemAreas(unitStemAreas);
					}
					status = stemCurriculumService.saveStemUnitActivities(stemUnitActivities,files);
					if (status) {
						model.addAttribute("status", WebKeys.STEM_ACTIVITIES_SAVED_SUCCESS);						
						model.addAttribute("activities",stemUnitActivities);
					} else {
						model.addAttribute("status", WebKeys.STEM_ACTIVITIES_SAVED_FAIL); 
					}
				} catch (DataException e) {
					isError = true;
					logger.error("Error in saveStemActivities() of of STEMController"+ e);
					session.setAttribute("isError", isError);
					model.addAttribute("status", WebKeys.STEM_ACTIVITIES_SAVED_FAIL); 
				}
			}
		} catch (Exception e) {
			logger.error("Error in saveStemActivities() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/saveStemContentQuestions", method = RequestMethod.POST, consumes="application/json")
	public View saveStemContentQuestions(
			@RequestBody Map<Long, List<String>> contentQueMap,
			HttpSession session,
			Model model) {
		try {
			Map<Long, List<Long>> contentQuestionsIdMap =new  HashMap<Long, List<Long>>();
			for (Map.Entry<Long, List<String>> pair : contentQueMap.entrySet()) {
				 long unitStemStrandsId = pair.getKey();
				 List<String> contentQuestionsLt = pair.getValue();
				 List<Long> contentQuestionsIdLt = stemCurriculumService.saveStemContentQuestions(unitStemStrandsId, contentQuestionsLt);
				 contentQuestionsIdMap.put(unitStemStrandsId, contentQuestionsIdLt);
			}
			model.addAttribute("contentQuestionsIdMap", contentQuestionsIdMap);
			if (contentQuestionsIdMap.size() > 0) {
				model.addAttribute("status", WebKeys.LP_CREATED_SUCCESS);
			} else {
				model.addAttribute("status", WebKeys.LP_FAILED);
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in saveStemContentQuestions() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/removeEssUnitQues", method = RequestMethod.POST)
	public View removeEssUnitQues(@RequestParam("unitQuesId") long unitQuesId,HttpSession session,Model model) {
		try {
			boolean status = false;
			status = stemCurriculumService.removeEssUnitQues(unitQuesId);
			if (status) {
				model.addAttribute("status", WebKeys.LP_REMOVED_SUCCESS);
			} else {
				model.addAttribute("status", WebKeys.LP_REMOVED_FAILED);
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in removeEssUnitQues() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/updateContentQuestions", method = RequestMethod.POST)
	public View updateContentQuestions(
			@RequestParam("contentQuesId") long contentQuesId,
			@RequestParam("unitStemAreasId") long unitStemAreasId,
			@RequestParam("contentQue") String contentQue,
			HttpSession session,
			Model model) {
		try {
			boolean status = false;
			UnitStemAreas unitStemAreas = new UnitStemAreas();
			unitStemAreas.setUnitStemAreasId(unitStemAreasId);
			UnitStemContentQuestions unitStemContentQuestion = new UnitStemContentQuestions();
			unitStemContentQuestion.setUnitStemAreas(unitStemAreas);
			unitStemContentQuestion.setUnitStemContentQuesId(contentQuesId);
			unitStemContentQuestion.setContentQuestion(contentQue);
			status = stemCurriculumService.updateContentQuestions(unitStemContentQuestion);
			if(status){
				model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
				model.addAttribute("contentQuesId",unitStemContentQuestion.getUnitStemContentQuesId());
			} else {
				model.addAttribute("status", WebKeys.LP_FAILED);
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in updateContentQuestions() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/deleteContentQuestions", method = RequestMethod.POST)
	public View deleteContentQuestions(@RequestParam("contentQuesId") long contentQuesId,HttpSession session,Model model) {
		try {
			boolean status = false;
			status = stemCurriculumService.deleteContentQuestions(contentQuesId);
			if (status) {
				model.addAttribute("status", WebKeys.LP_REMOVED_SUCCESS);
			} else {
				model.addAttribute("status", WebKeys.LP_REMOVED_FAILED);
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in deleteContentQuestions() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getStemSharedActivities", method = RequestMethod.POST)
	public ModelAndView getStemSharedActivities(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("gradeId") long gradeId,@RequestParam("stemAreaId") long stemAreaId,
			@RequestParam("stemUnitId") long stemUnitId) {
		boolean isError = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_shared_activities", 
				"stemUnitActList", new StemUnitActList());
		try {
			if (userReg != null) {
				try {
					List<StemUnitActivity> stemSharedAct = new ArrayList<StemUnitActivity>();
					stemSharedAct = stemCurriculumService.getStemSharedActivities(gradeId, stemAreaId, stemUnitId);
					model.addObject("stemSharedAct", stemSharedAct);
					model.addObject("noOfSharedAct", stemSharedAct.size());
				} catch (DataException e) {
					isError = true;
					logger.error("Error in getStemSharedActivities() of of STEMController"+ e);
					 session.setAttribute("isError", isError);
				}
			}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		} catch (Exception e) {
			logger.error("Error in getStemSharedActivities() of STEMController" + e);
		}
		return model;
	}
	@RequestMapping(value = "/getUnitStemStrategies", method = RequestMethod.POST)
	public ModelAndView getUnitStemStrategies(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("stemUnitId") long stemUnitId) {
		boolean isError = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_add_strategies");
		try {
			if (userReg != null) {
				try {
					List<StemStrategies> stemStrategiesLt = new ArrayList<StemStrategies>();
					List<UnitStemStrategies> unitStemStratgLt = new ArrayList<UnitStemStrategies>();
					stemStrategiesLt = stemCurriculumService.getStemStrategies();
					StemUnit stemUnit =stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
					unitStemStratgLt = stemUnit.getUnitStemStrategiesLt();
					model.addObject("stemStrategiesLt", stemStrategiesLt);
					model.addObject("unitStemStratgLt", unitStemStratgLt);
					model.addObject("stemUnitId",stemUnitId);
				} catch (DataException e) {
					isError = true;
					logger.error("Error in getUnitStemStrategies() of of STEMController"+ e);
					 session.setAttribute("isError", isError);
				}
			}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		} catch (Exception e) {
			logger.error("Error in getUnitStemStrategies() of STEMController" + e);
		}
		return model;
	}
	@RequestMapping(value = "/saveStemStrategies", method = RequestMethod.POST)
	public View saveStemStrategies(HttpSession session,Model model,@RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("stemStrategiesId") long strategiesId,@RequestParam("mode") String mode) {
		List<UnitStemStrategies> unitStemStrategList=new ArrayList<UnitStemStrategies>();
		boolean status=false;
		try {
		
				boolean chkStatus=false;
				chkStatus=stemCurriculumService.checkExistsStemStrategies(stemUnitId,strategiesId);
				if(!chkStatus && mode.equalsIgnoreCase("create")){
					UnitStemStrategies unitStemStg=new UnitStemStrategies();
					unitStemStg.setStemUnit(stemCurriculumService.getStemUnitByStemUnitId(stemUnitId));
					unitStemStg.setStemStrategies(stemCurriculumService.getStemStrategies(strategiesId));
					unitStemStrategList.add(unitStemStg);
					status=stemCurriculumService.saveUnitStemStrategies(stemUnitId, unitStemStrategList);
					if (status) {
						model.addAttribute("status", WebKeys.ASSIGNED_SUCCESSFULLY);
					} else {
						model.addAttribute("status", WebKeys.LP_FAILED);
					}
				}else{
					status=stemCurriculumService.removeUnitStemStrategies(stemUnitId,strategiesId);
					if (status) {
						model.addAttribute("status", WebKeys.LP_REMOVED_SUCCESS);
					} else {
						model.addAttribute("status", WebKeys.LP_FAILED);
					}
				}
			
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in saveStemContentQuestions() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/loadPremadeStemUnits")
	protected ModelAndView loadPremadeStemUnits(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("pathId") long pathId,
			HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_pre_made_units");
		List<StemUnit> stemUnitLt = stemCurriculumService.getAllStemUnits(gradeId, userReg);
		model.addObject("stemUnitLt", stemUnitLt);
		model.addObject("gradeId", gradeId);
		model.addObject("classId", classId);
		model.addObject("pathId", pathId);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		return model;
	}
	
	@RequestMapping(value = "/getCopyOfStemUnits", method = RequestMethod.POST)
	public View getCopyOfStemUnits(
			@RequestParam("stemUnitIdArray") ArrayList<Long> stemUnitIdArray,
			@RequestParam("gradeId") long gradeId, 
			@RequestParam("classId") long classId, 
			HttpSession session,
			Model model) {
		try {
			List<StemUnit> stemUnitLt = new ArrayList<StemUnit>();
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			
			for (Long stemUnitId : stemUnitIdArray) {
				StemUnit stemUnit = new StemUnit();
				stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
				stemUnitLt.add(stemUnit);
			}
			stemUnitLt = stemCurriculumService.getCopyStemUnits(stemUnitLt, userReg, gradeId, classId);
			model.addAttribute("stemUnitLt", stemUnitLt);
		} catch (Exception e) {
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/goToAssignStemUnitsPage", method = RequestMethod.GET)
	public ModelAndView goToAssignLessonsPage(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		ModelAndView model = new ModelAndView("Teacher/assign_stem_units","stemCurriculum", new StemCurriculum());
		model.addObject("tab", WebKeys.LP_TAB_STEM);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		model.addObject("page","assignStemUnits");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("hellowAjax",e.getMessage());
		}
		
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		model.addObject("page","assignStemUnits");
		return model;
	}
	@RequestMapping(value = "/getStemUnitsByTeacherNAdmin", method = RequestMethod.GET)
	public ModelAndView getStemUnitsByTeacherNAdmin(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,@RequestParam("pathId") long pathId, HttpSession session)
			throws Exception {		
		 ModelAndView model = new ModelAndView("Ajax/Teacher/include_stem_curriculum");
			try{
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			StemCurriculum curriculum = stemCurriculumService.getTeacherStemCurriculum(gradeId, classId, pathId, teacherObj);
		   	model.addObject("stemcurriculum", curriculum);
			model.addObject("stemUnitCount", curriculum.getStemUnits().size()-1);
			}catch(Exception e){
				logger.error("Error in assignCurriculum() of of CommonController"+ e);
				e.printStackTrace();
			}
			return model;
	}
	@RequestMapping(value = "/assignStemCurriculum", method = RequestMethod.POST)
	public @ResponseBody void  assignStemCurriculum(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,@RequestParam("stemUnitIds") long[] stemUnitIds,
			@RequestParam("sectionId") long csId,@RequestParam("dueDate") String dueDate) throws Exception {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<AssignStemUnits> assignStemUnitLst=new ArrayList<AssignStemUnits>();
		if (userReg != null) {
			try {
				boolean chkStatus=false;
				Date dueDates = null;
				Date assignDate = new Date();
				dueDates = new SimpleDateFormat("MM/dd/yyyy").parse(dueDate);
				for(int i=0;i<stemUnitIds.length;i++){
					chkStatus=stemCurriculumService.chkAlreadyAssignStemUnit(stemUnitIds[i]);
					if(!chkStatus){
					AssignStemUnits assignStemUnit=new AssignStemUnits();
					assignStemUnit.setClassStatus(iolReportCardService.getclassStatus(csId));
					assignStemUnit.setStemUnit(stemCurriculumService.getStemUnitByStemUnitId(stemUnitIds[i]));
					assignStemUnit.setDueDate(dueDates);
					assignStemUnit.setAssignedDate(assignDate);
					assignStemUnitLst.add(assignStemUnit);
					}
				}
				boolean status = stemCurriculumService.assignStemCurriculum(assignStemUnitLst);
				if (status) {
					response.getWriter().write( WebKeys.STEM_ASSIGN_STEM_UNIT_SUCCESS);  
				} else {
					response.getWriter().write( WebKeys.STEM_ASSIGN_STEM_UNIT_FAILED);  
				}
			} catch (DataException e) {
				logger.error("Error in assignCurriculum() of of CommonController"+ e);
				e.printStackTrace();
				response.getWriter().write( WebKeys.LP_ASSIGN_LESSONS_FAILED);  
				}
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/viewStemUnitWindow")
	protected ModelAndView viewStemUnitWindow(
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session,
			@RequestParam("stemUnitId") long stemUnitId) {
		   ModelAndView model = new ModelAndView("Ajax/Student/view_stem_unit");
		   StemUnit stemUnit=new StemUnit();
			   try {
				if(stemUnitId>0){
					stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
					model.addObject("stemUnit", stemUnit);
					
					List<UnitStemStrands> unitStemStrandsLt =  new ArrayList<UnitStemStrands>();
					List<UnitStemAreas> unitStemAreas = stemUnit.getUniStemAreasLt();
					List<StemUnitActivity> stemUnitActivities = new ArrayList<StemUnitActivity>();
					
					for(UnitStemAreas unitStemArea : unitStemAreas){
						unitStemStrandsLt.addAll(unitStemArea.getUnitStemStrandsLt());
						stemUnitActivities.addAll(unitStemArea.getStemUnitActivitiesLt());
					}
					model.addObject("unitStemAreas", unitStemAreas);
					model.addObject("unitStemStrandsLt", unitStemStrandsLt);
					model.addObject("stemActivityLt", stemUnitActivities);
					model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
					
					List<StemQuestions> stemQuestionsLt = stemUnit.getStemQuestionsLt();
					model.addObject("stemQuestionsLt", stemQuestionsLt);
					
					
					String uploadFilePath = FileUploadUtil.getUploadFilesPath(stemUnit.getUserRegistration(), request);
					model.addObject("uploadFilePath", uploadFilePath);
					
					
				}
				model.addObject("stemUnitId", stemUnitId);
			} catch (Exception e) {
				logger.error("Error in viewStemUnitWindow() of STEMController"+ e);
				e.printStackTrace();
			}
		   
			return model;
	}
	
	@RequestMapping(value = "/autoSaveEssenUnitQues", method = RequestMethod.POST)
	public View autoSaveEssenUnitQues(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model,@RequestParam("stemUnitId") long stemUnitId,@RequestParam("question") String question,
			@RequestParam("quesTypeId") long quesTypeId,@RequestParam("questionId") long questionId) {
		boolean isError = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			if (userReg != null) {
				try {
					StemQuestions stemQuestion = new StemQuestions();
					if(questionId>0)
					stemQuestion.setStemQuestionId(questionId);
					StemUnit stemUnit = new StemUnit();
					stemUnit.setStemUnitId(stemUnitId); 
						StemQuestionsType stemQuesType = new StemQuestionsType();
						stemQuesType.setStemQuesTypeId(quesTypeId);
						stemQuestion.setStemQuestion(question);
						stemQuestion.setStemUnit(stemUnit);
						stemQuestion.setStemQuestionsType(stemQuesType);
					boolean status =stemCurriculumService.autoSaveEssenUnitQues(stemQuestion);
					if (status) {
						model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
						model.addAttribute("questionId",stemQuestion.getStemQuestionId());
					} else {
						model.addAttribute("status", WebKeys.LP_FAILED);
					}
				} catch (DataException e) {
					isError = true;
					logger.error("Error in autoSaveEssenUnitQues() of of STEMController"
							+ e);
					 session.setAttribute("isError", isError);
					 response.getWriter().write(WebKeys.LP_CREATE_STEM_ESSential_UNIT_QUES_ERROR);
				}
			 }
			 response.setCharacterEncoding("UTF-8");  
			 response.setContentType("text/html");  
		} catch (IOException e) {
			logger.error("Error in autoSaveEssenUnitQues() of STEMController" + e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/deleteStemUnit", method = RequestMethod.POST)
	public View deleteStemUnit(
			HttpSession session,
			Model model,
			@RequestParam("stemUnitId") long stemUnitId) {
		String status= "";
		try {
			if(stemUnitId>0){
				StemUnit stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
				status = stemCurriculumService.deleteStemUnit(stemUnit);
				if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
					model.addAttribute("status", WebKeys.DELETED_SUCCESSFULLY);
				}else if(status.equalsIgnoreCase(WebKeys.FAILED)){
					model.addAttribute("status", WebKeys.FAILED_TO_DELETE);
				}else if(status.equalsIgnoreCase(WebKeys.ALREADY_ASSIGNED)){
					model.addAttribute("status", WebKeys.UNABLE_TO_DELETE);
				}
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.ERROR_OCCURED);
			logger.error("Error in saveStemContentQuestions() of STEMController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getSTEMContentQuestions", method = RequestMethod.GET)
	public ModelAndView getSTEMContentQuestions(
			HttpSession session,
			@RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("type") String type) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_content_questions");
		try {
			if(type.equalsIgnoreCase("main")){
				type = WebKeys.STEM_NO;
			}else if(type.equalsIgnoreCase("additional")){
				type = WebKeys.STEM_YES;
			}
			 List<StemAreas> stemAreasLt = stemCurriculumService.getStemAreas(type);
			 model.addObject("stemAreasLt",stemAreasLt);
			 model.addObject("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
			 StemUnit stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
			 List<UnitStemAreas> sortedUnitStemAreasList =  new ArrayList<UnitStemAreas>();
			 for(UnitStemAreas unitStemAreas : stemUnit.getUniStemAreasLt()){
				 if(unitStemAreas.getStemAreas().getIsOtherStem().equals(type)){
					 sortedUnitStemAreasList.add(unitStemAreas);
				 }
			 }
			 stemUnit.setUniStemAreasLt(sortedUnitStemAreasList);			 
			 model.addObject("stemUnit",stemUnit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getSTEMContentQuestions() of STEMController" + e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getUnitStemAreas", method = RequestMethod.GET)
	public View getUnitStemAreas(
			HttpSession session,
			@RequestParam("stemUnitId") long stemUnitId,
			Model model) {
		try {
			 StemUnit stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
			 List<UnitStemAreas> unitStemAreasLt =  new ArrayList<UnitStemAreas>();
			 for(UnitStemAreas unitStemAreas : stemUnit.getUniStemAreasLt()){
				 unitStemAreasLt.add(unitStemAreas);
			 }
			 model.addAttribute("unitStemAreasLt", unitStemAreasLt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getUnitStemAreas() of STEMController" + e);
		}		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/stemUnitByStemUnitId", method = RequestMethod.GET)
	public @ResponseBody View stemUnitByStemUnitId(
			HttpSession session,
			@RequestParam("stemUnitId") long stemUnitId,
			Model model) {
		try {
			 StemUnit stemUnit = stemCurriculumService.getStemUnitByStemUnitId(stemUnitId);
			 model.addAttribute("stemUnit", stemUnit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in stemUnitByStemUnitId() of STEMController" + e);
		}		
		return new MappingJackson2JsonView();
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/loadSharedStemUnits")
	protected ModelAndView loadSharedStemUnits(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("pathId") long pathId,
			HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_shared_units");
		List<StemUnit> stemUnitLt = stemCurriculumService.getAllSharedStemUnits(gradeId, userReg);
		model.addObject("stemUnitLt", stemUnitLt);
		model.addObject("gradeId", gradeId);
		model.addObject("classId", classId);
		model.addObject("pathId", pathId);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		return model;
	}
}