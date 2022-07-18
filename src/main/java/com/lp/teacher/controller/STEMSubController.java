package com.lp.teacher.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.service.FileService;
import com.lp.model.FormativeAssessmentCategory;
import com.lp.model.FormativeAssessmentKeywords;
import com.lp.model.FormativeAssessmentRubric;
import com.lp.model.FormativeAssessments;
import com.lp.model.FormativeAssessmentsColumnHeaders;
import com.lp.model.FormativeAssessmentsGradeWise;
import com.lp.model.FormativeAssessmentsUnitWise;
import com.lp.model.Grade;
import com.lp.model.IpalResources;
import com.lp.model.LegendCriteria;
import com.lp.model.LegendSubCriteria;
import com.lp.model.StemList;
import com.lp.model.StemPerformanceIndicator;
import com.lp.model.StemUnit;
import com.lp.model.StemUnitPerformanceIndicator;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.teacher.service.StemCurriculumService;
import com.lp.teacher.service.StemCurriculumSubService;
import com.lp.utils.WebKeys;

@Controller
public class STEMSubController {

	@Autowired
	private StemCurriculumService stemCurriculumService;
	@Autowired
	private StemCurriculumSubService stemCurriculumSubService;
	@Autowired
	private IOLReportCardService iOLReportCardService;
	@Autowired
	private FileService fileService;
	static final Logger logger = Logger.getLogger(STEMSubController.class);
	
	@RequestMapping(value = "/getStemFiveCs", method = RequestMethod.POST)
	public ModelAndView getStemFiveCs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_five_cs");
		try {
			List<LegendCriteria> legendCriteria = new ArrayList<LegendCriteria>();
			legendCriteria = stemCurriculumSubService.getStemLegendCriteria();
			model.addObject("legendCriteria", legendCriteria);	
		} catch (Exception e) {
			logger.error("Error in getStemFiveCs() of STEMSubController"
					+ e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/getSubCriterias", method = RequestMethod.POST)
	public ModelAndView getSubCriterias(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("lCriteriaId") long lCriteriaId, @RequestParam("stemUnitId") long stemUnitId, @RequestParam("gradeId") long gradeId) {
		StemList stemList = new StemList();
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_sub_criterias", "stemList", stemList);
		//String mode="create";
		try {
			Grade grade =  fileService.getGrade(gradeId);
			stemList.setStemUnitPerformanceIndicator(stemCurriculumSubService.getStemUnitPerformanceIndicator(stemUnitId, lCriteriaId));
			List<LegendSubCriteria> legendSubCriteria = new ArrayList<LegendSubCriteria>();
			legendSubCriteria = iOLReportCardService.getLegendSubCriteriasByCriteriaId(lCriteriaId,grade.getMasterGrades().getMasterGradesId());
			model.addObject("legendSubCriteria", legendSubCriteria);
			model.addObject("stemUnitId",stemUnitId);		
		} catch (Exception e) {
			logger.error("Error in getSubCriterias() of STEMSubController"
					+ e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/saveStemIndicator", method = RequestMethod.POST)
	public View saveStemIndicator(HttpServletRequest request, HttpServletResponse response,
			Model model, @RequestParam("subCriteriaId") long subCriteriaId, @RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("indicatorId") long indicatorId, @RequestParam("indicatorStatus") String indicatorStatus) {
		StemUnitPerformanceIndicator stemUnitIndicator = new StemUnitPerformanceIndicator();
		StemUnit stemUnit = new StemUnit();
		LegendSubCriteria legendSub = new LegendSubCriteria();
		try {
			stemUnit.setStemUnitId(stemUnitId);
			legendSub.setLegendSubCriteriaId(subCriteriaId);
			stemUnitIndicator.setStemUnitPerformanceIndicatorId(indicatorId);
			stemUnitIndicator.setStemUnit(stemUnit);
			stemUnitIndicator.setLegendSubCriteria(legendSub);
			stemUnitIndicator.setStatus(indicatorStatus);
			boolean status = stemCurriculumSubService.saveStemIndicator(stemUnitIndicator);
			if(status){
				model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
				model.addAttribute("stemIndicatorId",stemUnitIndicator.getStemUnitPerformanceIndicatorId());
				model.addAttribute("stemStatus",stemUnitIndicator.getStatus());
			} else {
				model.addAttribute("status", WebKeys.LP_FAILED);
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in saveStemIndicator() of STEMSubController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getStemPerIndicators", method = RequestMethod.POST)
	public ModelAndView getStemPerIndicators(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("gradeId") long gradeId, @RequestParam("subCriteriaId") long subCriteriaId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_per_indicators");
		try {
			List<StemPerformanceIndicator> stemPerIndicatorList = new ArrayList<StemPerformanceIndicator>();
			stemPerIndicatorList = stemCurriculumSubService.getStemPerIndicators(gradeId,subCriteriaId);
			model.addObject("stemPerIndicatorList",stemPerIndicatorList);		
		} catch (Exception e) {
			logger.error("Error in getStemPerIndicators() of STEMSubController"+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getFormativeAssessments", method = RequestMethod.POST)
	public ModelAndView getFormativeAssessments(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("stemUnitId") long stemUnitId, @RequestParam("gradeId") long gradeId) {
		StemList stemList = new StemList();
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_assessments", "stemList", stemList);
		try {
			List<FormativeAssessmentsGradeWise> stemFormativeAssGradeList = new ArrayList<FormativeAssessmentsGradeWise>();
			stemFormativeAssGradeList = stemCurriculumSubService.getFormativeAssessmentsGradeWise(gradeId);
			stemList.setFormativeAssessmentsUnitWise(stemCurriculumService.getStemUnitByStemUnitId(stemUnitId).getFormativeAssessmentsLt());
			model.addObject("stemFormativeAssGradeList",stemFormativeAssGradeList);	
		} catch (Exception e) {
			logger.error("Error in getFormativeAssessments() of STEMSubController"+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/saveformativeAssessUnit", method = RequestMethod.POST)
	public View saveformativeAssessUnit(HttpServletRequest request, HttpServletResponse response,
			Model model, @RequestParam("formativeAssessmentId") long formativeAssessmentId, @RequestParam("stemUnitId") long stemUnitId,
			@RequestParam("formativeUnitId") long formativeUnitId, @RequestParam("formativeUnitStatus") String formativeUnitStatus) {
		FormativeAssessmentsUnitWise forAssessmentsUnitWise = new FormativeAssessmentsUnitWise();
		StemUnit stemUnit = new StemUnit();
		FormativeAssessments forAssessments = new FormativeAssessments();
		try {
			stemUnit.setStemUnitId(stemUnitId);
			forAssessments.setFormativeAssessmentsId(formativeAssessmentId);
			forAssessmentsUnitWise.setFormativeAssessmentsUnitWiseId(formativeUnitId);
			forAssessmentsUnitWise.setStemUnit(stemUnit);
			forAssessmentsUnitWise.setFormativeAssessments(forAssessments);
			forAssessmentsUnitWise.setStatus(formativeUnitStatus);
			boolean status = stemCurriculumSubService.saveformativeAssessUnit(forAssessmentsUnitWise);
			if(status){
				model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
				model.addAttribute("formativeUnitId",forAssessmentsUnitWise.getFormativeAssessmentsUnitWiseId());
				model.addAttribute("formativeUnitStatus",forAssessmentsUnitWise.getStatus());
			} else {
				model.addAttribute("status", WebKeys.LP_FAILED);
			}
		} catch (Exception e) {
			model.addAttribute("status", WebKeys.LP_FAILED);
			logger.error("Error in saveformativeAssessUnit() of STEMSubController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getIpalResources", method = RequestMethod.POST)
	public ModelAndView getIpalResources(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("gradeId") long gradeId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/ipal_resources");
		try {
			List<IpalResources> iPalResources = new ArrayList<IpalResources>();
			iPalResources = stemCurriculumSubService.getIpalResources(gradeId);
			model.addObject("iPalResources",iPalResources);	
		} catch (Exception e) {
			logger.error("Error in getIpalResources() of STEMSubController"+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getAssessmentDetail", method = RequestMethod.POST)
	public ModelAndView getAssessmentDetail(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("formativeAssessmentId") long formativeAssessmentId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/stem_formative_detail");
		try {
			List<FormativeAssessmentKeywords> keywords = new ArrayList<FormativeAssessmentKeywords>();
			keywords = stemCurriculumSubService.getStemFormativeKeywords(formativeAssessmentId);
			FormativeAssessments fAssessment = new FormativeAssessments();
			fAssessment = stemCurriculumSubService.getFormativeAssessmentById(formativeAssessmentId);
			model.addObject("keywords",keywords);		
			model.addObject("fAssessment",fAssessment);
			List<FormativeAssessmentsColumnHeaders> fColumnHeaders = stemCurriculumSubService.getFormativeAssessmentColumnHeaders(formativeAssessmentId);
			List<FormativeAssessmentRubric> formativeAssessmentRubrics = stemCurriculumSubService.getFormativeAssessmentRubric(formativeAssessmentId);
			List<FormativeAssessmentCategory> fAssessmentCategories = new ArrayList<FormativeAssessmentCategory>();
			fAssessmentCategories = stemCurriculumSubService.getFormativeAssessmentCategories(formativeAssessmentId);
			model.addObject("fColumnHeaders",fColumnHeaders);		
			model.addObject("formativeAssessmentRubrics",formativeAssessmentRubrics);
			model.addObject("fAssessmentCategories",fAssessmentCategories);
		} catch (Exception e) {
			logger.error("Error in getAssessmentDetail() of STEMSubController"+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/removeUnitStandards", method = RequestMethod.POST)
	public View removeUnitStandards(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model, @RequestParam("strandsId") long strandsId ) {
		boolean status = false;
		try {
			status = stemCurriculumSubService.removeUnitStandards(strandsId);
			if(status){
				model.addAttribute("status", WebKeys.LP_REMOVE_STEM_STRAND_SUCCESS);
			}else{
				model.addAttribute("status", WebKeys.LP_REMOVE_STEM_STRAND_FAILED);
			}
			
		} catch (Exception e) {
			logger.error("Error in removeUnitStandards() of STEMSubController"+ e);
			model.addAttribute("status", WebKeys.LP_REMOVE_STEM_STRAND_ERROR);
		}
		return new MappingJackson2JsonView();
	}
	
}


