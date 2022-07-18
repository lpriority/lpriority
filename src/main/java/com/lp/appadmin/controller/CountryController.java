package com.lp.appadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.AppAdminService2;
import com.lp.model.Citizenship;
import com.lp.model.Comments;
import com.lp.model.Country;
import com.lp.model.States;
import com.lp.model.TeacherPerformances;

@Controller
public class CountryController {
	
	static final Logger logger = Logger.getLogger(CountryController.class);

	@Autowired
	@Qualifier("addStatesFormValidator")
	private Validator statesValidator;


	@InitBinder("states")
	protected void initStatesBinder(WebDataBinder binder) {
		binder.setValidator(statesValidator);
	}
	
	@Autowired
	private AppAdminService2 appAdminService2;

	/* ######### Country Methods starts from here ############# */

	@RequestMapping("/displayCountries")
	public ModelAndView listCountries(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("countryList", appAdminService2.getCountries());
		return new ModelAndView("AppManager/DisplayCountries", model);
	}

	@RequestMapping("/deleteCountry")
	public ModelAndView deleteCountry(@RequestParam int id) {
		appAdminService2.deleteCountry(id);
		return new ModelAndView("redirect:/displayCountries.htm");
	}

	@RequestMapping("/saveCountry")
	public ModelAndView saveCountry(
			@ModelAttribute("country") @Valid Country country,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddCountry");
		} else {
			appAdminService2.saveCountry(country);
		}
		return new ModelAndView("redirect:/displayCountries.htm");

	}

	@RequestMapping("/addCountry")
	public ModelAndView addCountry(@ModelAttribute Country country) {
		return new ModelAndView("AppManager/AddCountry", "country",
				new Country());
	}

	@RequestMapping("/editCountry")
	public ModelAndView editCountry(@RequestParam int id,
			@ModelAttribute Country country) {
		return new ModelAndView("AppManager/AddCountry", "country",
				appAdminService2.getCountry(id));
	}

	/* ######### States Methods starts from here ############# */

	@RequestMapping("/displayStates")
	public ModelAndView listStates(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("statesList", appAdminService2.getStates());
		return new ModelAndView("AppManager/DisplayStates", model);
	}

	@RequestMapping("/deleteState")
	public ModelAndView deleteState(@RequestParam int id) {
		appAdminService2.deleteState(id);
		return new ModelAndView("redirect:/displayStates.htm");
	}

	@RequestMapping("/saveState")
	public ModelAndView saveState(@ModelAttribute("states") @Valid States states,
			BindingResult result, Map<String, Object> map) {
		if (result.hasErrors()) {
			map.put("countryIds", appAdminService2.getCountries());
			map.put("countryId", states.getCountryId());
			return new ModelAndView("AppManager/AddState","states", states);
		} else {
			appAdminService2.saveState(states);
		}
		return new ModelAndView("redirect:/displayStates.htm");
	}

	@RequestMapping("/addState")
	public ModelAndView addState(@ModelAttribute States state,
			Map<String, Object> map) {
		map.put("countryIds", appAdminService2.getCountries());
		return new ModelAndView("AppManager/AddState", "states", state);
	}

	@RequestMapping("/editState")
	public ModelAndView editState(@RequestParam int id,
			@ModelAttribute States states, Map<String, Object> map) {
		List<Country> countryList = appAdminService2.getCountries();
		map.put("countryIds", countryList);
		map.put("countryId", appAdminService2.getState(id).getCountry()
				.getCountryId());
		return new ModelAndView("AppManager/AddState", "states",
				appAdminService2.getState(id));
	}

	/* ######### Citizenship Methods starts from here ############# */

	@RequestMapping("/displayCitizenships")
	public ModelAndView listCitizenships(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("citizenshipList", appAdminService2.getCitizenships());
		return new ModelAndView("AppManager/DisplayCitizenships", model);
	}

	@RequestMapping("/deleteCitizenship")
	public ModelAndView deleteCitizenship(@RequestParam int id) {
		appAdminService2.deleteCitizenship(id);
		return new ModelAndView("redirect:/displayCitizenships.htm");
	}

	@RequestMapping("/saveCitizenship")
	public ModelAndView saveCitizenship(
			@ModelAttribute("citizenship") @Valid Citizenship citizenship,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddCitizenship");
		} else {
			appAdminService2.saveCitizenship(citizenship);
		}
		return new ModelAndView("redirect:/displayCitizenships.htm");

	}

	@RequestMapping("/addCitizenship")
	public ModelAndView addCitizenship(@ModelAttribute Citizenship citizenship) {
		return new ModelAndView("AppManager/AddCitizenship", "citizenship",
				new Citizenship());
	}

	@RequestMapping("/editCitizenship")
	public ModelAndView editCitizenship(@RequestParam int id,
			@ModelAttribute Citizenship citizenship) {
		return new ModelAndView("AppManager/AddCitizenship", "citizenship",
				appAdminService2.getCitizenship(id));
	}

	/* ######### Comments Methods starts from here ############# */

	@RequestMapping("/displayComments")
	public ModelAndView listComments(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("commentsList", appAdminService2.getComments());
		return new ModelAndView("AppManager/DisplayComments", model);
	}

	@RequestMapping("/deleteComment")
	public ModelAndView deleteComments(@RequestParam int id) {
		appAdminService2.deleteComment(id);
		return new ModelAndView("redirect:/displayComments.htm");
	}

	@RequestMapping("/saveComment")
	public ModelAndView saveComments(
			@ModelAttribute("comments") @Valid Comments comments,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddComment");
		} else {
			appAdminService2.saveComment(comments);
		}
		return new ModelAndView("redirect:/displayComments.htm");

	}

	@RequestMapping("/addComment")
	public ModelAndView addComments(@ModelAttribute Comments comments) {
		return new ModelAndView("AppManager/AddComment", "comments",
				new Comments());
	}

	@RequestMapping("/editComment")
	public ModelAndView editComments(@RequestParam int id,
			@ModelAttribute Comments comments) {
		return new ModelAndView("AppManager/AddComment", "comments",
				appAdminService2.getComment(id));
	}

	/* ######### TeacherPerformances Methods starts from here ############# */

	@RequestMapping("/displayTeacherPerformances")
	public ModelAndView listTeacherPerformances(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("teacherPerformancesList",
				appAdminService2.getTeacherPerformances());
		return new ModelAndView("AppManager/DisplayTeacherPerformances", model);
	}

	@RequestMapping("/deleteTeacherPerformance")
	public ModelAndView deleteTeacherPerformance(@RequestParam int id) {
		appAdminService2.deleteTeacherPerformance(id);
		return new ModelAndView("redirect:/displayTeacherPerformances.htm");
	}

	@RequestMapping("/saveTeacherPerformance")
	public ModelAndView saveTeacherPerformance(
			@ModelAttribute("teacherPerformances") @Valid TeacherPerformances teacherPerformances,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddTeacherPerformances");
		} else {
			appAdminService2.saveTeacherPerformance(teacherPerformances);
		}
		return new ModelAndView("redirect:/displayTeacherPerformances.htm");

	}

	@RequestMapping("/addTeacherPerformance")
	public ModelAndView addTeacherPerformances(
			@ModelAttribute TeacherPerformances teacherPerformances) {
		return new ModelAndView("AppManager/AddTeacherPerformances",
				"teacherPerformances", new TeacherPerformances());
	}

	@RequestMapping("/editTeacherPerformance")
	public ModelAndView editTeacherPerformances(@RequestParam int id,
			@ModelAttribute TeacherPerformances teacherPerformances) {
		return new ModelAndView("AppManager/AddTeacherPerformances",
				"teacherPerformances",
				appAdminService2.getTeacherPerformances(id));
	}

}
