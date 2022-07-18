package com.lp.appadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.AppAdminService3;
import com.lp.model.AcademicGrades;
import com.lp.model.AcademicPerformance;
import com.lp.model.GradeLevel;
import com.lp.model.Gradeevents;
import com.lp.model.MasterGrades;
import com.lp.utils.WebKeys;

@Controller
public class AcademicGradesController {

	@Autowired
	@Qualifier("addAcademicGradesFormValidator")
	private Validator academicGradesValidator;


	@InitBinder("academicGrade")
	protected void initAcademicBinder(WebDataBinder binder) {
		binder.setValidator(academicGradesValidator);
	}
	@Autowired
	private AppAdminService3 appAdminService3;

	static final Logger logger = Logger.getLogger(AcademicGradesController.class);

	/* Redirect to AppManager homepage */
	@RequestMapping("/appManagerHomePage")
	public ModelAndView appManagerHomePage(Model model) {
		model.addAttribute("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		return new ModelAndView("AppManager/AppManager");
		
	}

	/* ##### AcademicGrades methods go here ###### */
	@RequestMapping("/displayAcademicGrades")
	public ModelAndView listAcademicGrades(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("agList", appAdminService3.getAcademicGrades());
		} catch (Exception e) {
			logger.error("Error in listAcademicGrades() of AcademicGrades Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayAcademicGrades", model);
	}

	@RequestMapping("/deleteAcademicGrade")
	public ModelAndView deleteAcademicGrade(@RequestParam int id) {
		try {
			appAdminService3.deleteAcademicGrade(id);
		} catch (Exception e) {
			logger.error("Error in deleteAcademicGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayAcademicGrades.htm");
	}

	@RequestMapping("/saveAcademicGrade")
	public ModelAndView saveAcademicGrade(
			@ModelAttribute("academicGrade") @Valid AcademicGrades academicGrade,
			BindingResult result, Map<String, Object> map) {
		try {
			if (result.hasErrors()) {
				map.put("academicPerformanceList",
						appAdminService3.getAcademicPerformances());
				map.put("id", academicGrade.getAcademicId());
				return new ModelAndView("AppManager/AddAcademicGrades","academicGrade", academicGrade);
			} else {
				appAdminService3.saveAcademicGrade(academicGrade);
			}
		} catch (Exception e) {
			logger.error("Error in saveAcademicGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayAcademicGrades.htm");
	}

	@RequestMapping("/editAcademicGrade")
	public ModelAndView editAcademicGrade(@RequestParam int id,
			@ModelAttribute AcademicGrades academicGrade,
			Map<String, Object> map) {
		List<AcademicPerformance> academicPerformanceList = appAdminService3
				.getAcademicPerformances();
		map.put("academicPerformanceList", academicPerformanceList);

		AcademicGrades academicGrades = new AcademicGrades();

		try {
			academicGrades = appAdminService3.getAcademicGrade(id);
			map.put("id", academicGrades.getAcademicPerformance()
					.getAcademicId());
		} catch (Exception e) {
			logger.error("Error in editAcademicGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddAcademicGrades",
				"academicGrade", academicGrades);
	}

	@RequestMapping("/addAcademicGrade")
	public ModelAndView addAcademicGrade(
			@ModelAttribute AcademicGrades academicGrade,
			Map<String, Object> map) {
		List<AcademicPerformance> academicPerformanceList = appAdminService3
				.getAcademicPerformances();
		map.put("academicPerformanceList", academicPerformanceList);

		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddAcademicGrades",
					"academicGrade", new AcademicGrades());
		} catch (Exception e) {
			logger.error("Error in addAcademicGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### AcademicPerformance methods go here ###### */

	@RequestMapping("/displayAcademicPerformances")
	public ModelAndView listAcademicPerformance(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("apList", appAdminService3.getAcademicPerformances());
		} catch (Exception e) {
			logger.error("Error in listAcademicPerformances() of AcademicPerformances Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayAcademicPerformances", model);
	}

	@RequestMapping("/deleteAcademicPerformance")
	public ModelAndView deleteAcademicPerformance(@RequestParam int id) {
		try {
			appAdminService3.deleteAcademicPerformance(id);
		} catch (Exception e) {
			logger.error("Error in deleteAcademicPerformance() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayAcademicPerformances.htm");
	}

	@RequestMapping("/saveAcademicPerformance")
	public ModelAndView saveAcademicPerformance(
			@ModelAttribute("academicPerformance") @Valid AcademicPerformance academicPerformance,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddAcademicPerformances");
			} else {
				appAdminService3.saveAcademicPerformance(academicPerformance);
			}
		} catch (Exception e) {
			logger.error("Error in saveAcademicPerformance() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayAcademicPerformances.htm");
	}

	@RequestMapping("/editAcademicPerformance")
	public ModelAndView editAcademicGrade(@RequestParam int id,
			@ModelAttribute AcademicPerformance academicPerformance) {
		AcademicPerformance academicPerformances = new AcademicPerformance();
		try {
			academicPerformances = appAdminService3.getAcademicPerformance(id);
		} catch (Exception e) {
			logger.error("Error in editAcademicGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddAcademicPerformances",
				"academicPerformance", academicPerformances);
	}

	@RequestMapping("/addAcademicPerformance")
	public ModelAndView addAcademicPerformance(
			@ModelAttribute AcademicPerformance academicPerformance) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddAcademicPerformances",
					"academicPerformance", new AcademicPerformance());
		} catch (Exception e) {
			logger.error("Error in addAcademicPerformance() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### GradeLevel methods go here ###### */

	@RequestMapping("/displayGradeLevels")
	public ModelAndView listGradeLevel(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("glList", appAdminService3.getGradeLevel());
		} catch (Exception e) {
			logger.error("Error in listGradeLevel() of GradeLevel Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayGradeLevel", model);

	}

	@RequestMapping("/deleteGradeLevel")
	public ModelAndView deleteGradeLevel(@RequestParam int id) {
		try {
			appAdminService3.deleteGradeLevel(id);
		} catch (Exception e) {
			logger.error("Error in deleteGradeLevel() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayGradeLevels.htm");
	}

	@RequestMapping("/saveGradeLevel")
	public ModelAndView saveGradeLevel(
			@ModelAttribute("gradeLevel") @Valid GradeLevel gradeLevel,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddGradeLevel");
			} else {
				appAdminService3.saveGradeLevel(gradeLevel);
			}
		} catch (Exception e) {
			logger.error("Error in saveGradeLevel() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayGradeLevels.htm");

	}

	@RequestMapping("/editGradeLevel")
	public ModelAndView editGradeLevel(@RequestParam int id,
			@ModelAttribute GradeLevel gradeLevel) {
		try {
			gradeLevel = appAdminService3.getGradeLevel(id);
		} catch (Exception e) {
			logger.error("Error in editGradeLevel() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddGradeLevel", "gradeLevel",
				gradeLevel);
	}

	@RequestMapping("/addGradeLevel")
	public ModelAndView addGradeLevel(@ModelAttribute GradeLevel gradeLevel) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddGradeLevel", "gradeLevel",
					new GradeLevel());
		} catch (Exception e) {
			logger.error("Error in addGradeLevel() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### Gradeevents methods go here ###### */

	@RequestMapping("/displayGradeevents")
	public ModelAndView listGradeevents(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("geList", appAdminService3.getGradeEvents());
		} catch (Exception e) {
			logger.error("Error in listGradeevents() of Gradeevents Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayGradeEvents", model);

	}

	@RequestMapping("/deleteGradeEvent")
	public ModelAndView deleteGradeevent(@RequestParam int id) {
		try {
			appAdminService3.deleteGradeEvent(id);
		} catch (Exception e) {
			logger.error("Error in deleteGradeEvent() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayGradeevents.htm");
	}

	@RequestMapping("/saveGradeEvent")
	public ModelAndView saveGradeevent(
			@ModelAttribute("gradeEvent") @Valid Gradeevents gradeEvent,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddGradeEvents");
			} else {
				appAdminService3.saveGradeEvent(gradeEvent);
			}
		} catch (Exception e) {
			logger.error("Error in saveGradeEvent() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayGradeevents.htm");

	}

	@RequestMapping("/editGradeEvent")
	public ModelAndView editGradeevents(@RequestParam int id,
			@ModelAttribute Gradeevents gradeEvent) {
		try {
			gradeEvent = appAdminService3.getGradeEvent(id);
		} catch (Exception e) {
			logger.error("Error in editGradeEvent() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddGradeEvents", "gradeEvent",
				gradeEvent);
	}

	@RequestMapping("/addGradeEvent")
	public ModelAndView addGradeevents(@ModelAttribute Gradeevents gradeEvent) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddGradeEvents", "gradeEvent",
					new Gradeevents());
		} catch (Exception e) {
			logger.error("Error in addGradeEvent() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return mav;

	}

	/* ##### MasterGrades methods go here ###### */

	@RequestMapping("/displayMasterGrades")
	public ModelAndView listMasterGrades(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("mgList", appAdminService3.getMasterGrades());
		} catch (Exception e) {
			logger.error("Error in listMasterGrades() of MasterGrades Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayMasterGrades", model);

	}

	@RequestMapping("/deleteMasterGrade")
	public ModelAndView deletemasterGrade(@RequestParam int id) {
		try {
			appAdminService3.deleteMasterGrade(id);
		} catch (Exception e) {
			logger.error("Error in deleteMasterGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayMasterGrades.htm");
	}

	@RequestMapping("/saveMasterGrade")
	public ModelAndView saveMasterGrade(
			@ModelAttribute("masterGrade") @Valid MasterGrades masterGrade,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddMasterGrades");
			} else {
				appAdminService3.saveMasterGrade(masterGrade);
			}
		} catch (Exception e) {
			logger.error("Error in saveMasterGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayMasterGrades.htm");

	}

	@RequestMapping("/editMasterGrade")
	public ModelAndView editMasterGrades(@RequestParam int id,
			@ModelAttribute MasterGrades masterGrade) {
		try {
			masterGrade = appAdminService3.getMasterGrade(id);
		} catch (Exception e) {
			logger.error("Error in editMasterGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddMasterGrades", "masterGrade",
				masterGrade);
	}

	@RequestMapping("/addMasterGrade")
	public ModelAndView addMasterGrades(@ModelAttribute MasterGrades masterGrade) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddMasterGrades", "masterGrade",
					new MasterGrades());
		} catch (Exception e) {
			logger.error("Error in addMasterGrade() of AcademicGradeController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

}
