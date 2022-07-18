package com.lp.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.custom.exception.DataException;
import com.lp.model.CAASPPScores;
import com.lp.model.Grade;
import com.lp.model.LearningIndicator;
import com.lp.model.LegendCriteria;
import com.lp.model.MulYrLegend;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.utils.WebKeys;

@Controller
public class MultiYearLearningIndicatorController {
	
	@Autowired
	private IOLReportCardService iolReportCardService;

	static final Logger logger = Logger.getLogger(MultiYearLearningIndicatorController.class);

	@RequestMapping(value = "/getMultiIOLReportsByStudent", method = RequestMethod.GET)
	public ModelAndView getMultiIOLReportsByStudent(HttpSession session,@RequestParam("studentId") long studentId,@RequestParam("teacherId") long teacherId,@RequestParam("className") String className) {
		String studentName="";
		List<Grade> schoolGrades = new ArrayList<Grade>();
		List<MulYrLegend> multiYearLegends=new ArrayList<MulYrLegend>();
		List<LegendCriteria> criteriaList=new  ArrayList<LegendCriteria>();
		List<LearningIndicator> learningIndList=new  ArrayList<LearningIndicator>();
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/student_multiyear_iol_report");
		model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD);
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<CAASPPScores> caaSppELAScores= new ArrayList<CAASPPScores>();
		List<CAASPPScores> caaSppMathScores=new ArrayList<CAASPPScores>();
		try{
			//schoolGrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
			schoolGrades = iolReportCardService.getSchoolGradesForIOL(userReg.getSchool().getSchoolId());
			model.addObject("grList", schoolGrades);
			Student student=iolReportCardService.getStudent(studentId);
			studentName = student.getUserRegistration().getFirstName() + " "+ student.getUserRegistration().getLastName();
			model.addObject("studentName",studentName);
			multiYearLegends=iolReportCardService.getMultiYearLegends();
			model.addObject("multiYearLegends",multiYearLegends);
			criteriaList=iolReportCardService.getLegendCriteria();
			Map<String,List<LearningIndicator>> criteriaScores = new HashMap<String,List<LearningIndicator>>();
			for (LegendCriteria legCriteria : criteriaList) {
				learningIndList=iolReportCardService.getStudentIOLScoresByCriteriaId(studentId,legCriteria.getLegendCriteriaId());
				criteriaScores.put(legCriteria.getLegendCriteriaName(),learningIndList);
			}
			className = className.replaceAll("\\d+.*", "%");
			className = className.replaceAll("-", "");
			className = className.replaceAll("&", "");
			caaSppELAScores=iolReportCardService.getCAASPPScoresByStudentId(studentId,1,className);
			caaSppMathScores=iolReportCardService.getCAASPPScoresByStudentId(studentId, 2,className);
			model.addObject("criteriaScores",criteriaScores);
			model.addObject("caaSppELAScores",caaSppELAScores);
			model.addObject("caaSppMathScores",caaSppMathScores);
				
		}catch(DataException e){
			logger.error("Error in getMultiIOLReportsByStudent() of MultiYearLearningIndicatorController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
}
