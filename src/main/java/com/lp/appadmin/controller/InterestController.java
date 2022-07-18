package com.lp.appadmin.controller;

import java.util.HashMap;
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

import com.lp.appadmin.service.AppAdminService4;
import com.lp.model.QualityOfResponse;
import com.lp.model.RtiGroups;
import com.lp.model.Interest;
import com.lp.model.RubricTypes;
import com.lp.model.SubInterest;

@Controller
public class InterestController {

	@Autowired
	@Qualifier("subInterestFormValidator")
	private Validator subInterestValidator;

	@InitBinder("subInterest")
	protected void initSchoolBinder(WebDataBinder binder) {
		binder.setValidator(subInterestValidator);
	}

	@Autowired
	private AppAdminService4 appAdminService4;

	static final Logger logger = Logger.getLogger(InterestController.class);

	/* ##### Interest methods go here ###### */
	@RequestMapping("/displayInterests")
	public ModelAndView listInterests(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("iList", appAdminService4.getInterests());
		} catch (Exception e) {
			logger.error("Error in listInterests() of Interests Controller" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayInterest", model);
	}

	@RequestMapping("/deleteInterest")
	public ModelAndView deleteInterest(@RequestParam int id) {
		try {
			appAdminService4.deleteInterest(id);
		} catch (Exception e) {
			logger.error("Error in deleteInterest() of InterestController" + e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayInterests.htm");
	}

	@RequestMapping("/saveInterest")
	public ModelAndView saveInterest(
			@ModelAttribute("interest") @Valid Interest interest,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddInterest");
			} else {
				appAdminService4.saveInterest(interest);
			}
		} catch (Exception e) {
			logger.error("Error in saveInterest() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayInterests.htm");
	}

	@RequestMapping("/editInterest")
	public ModelAndView editInterest(@RequestParam int id,
			@ModelAttribute Interest interest) {
		try {
			interest = appAdminService4.getInterest(id);
		} catch (Exception e) {
			logger.error("Error in editInterest() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddInterest", "interest", interest);
	}

	@RequestMapping("/addInterest")
	public ModelAndView addInterest(@ModelAttribute Interest interest) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddInterest", "interest",
					new Interest());
		} catch (Exception e) {
			logger.error("Error in addInterest() of InterestController" + e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### SubIneterest methods go here ###### */

	@RequestMapping("/displaySubInterests")
	public ModelAndView listSubInterest(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("siList", appAdminService4.getSubInterests());
		} catch (Exception e) {
			logger.error("Error in listSubInterests() of AcademicPerformances Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplaySubInterests", model);
	}

	@RequestMapping("/deleteSubInterest")
	public ModelAndView deleteSubInterest(@RequestParam int id) {
		try {
			appAdminService4.deleteSubInterest(id);
		} catch (Exception e) {
			logger.error("Error in deleteSubIneterest() of InterestController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySubInterests.htm");
	}

	@RequestMapping("/saveSubInterest")
	public ModelAndView saveSubInterest(
			@ModelAttribute("subInterest") @Valid SubInterest subInterest,
			BindingResult result, Map<String, Object> map) {
		try {

			if (result.hasErrors()) {
				map.put("interestList", appAdminService4.getInterests());
				map.put("interestId", subInterest.getInterestId());
				return new ModelAndView("AppManager/AddSubInterest");
			} else {
				appAdminService4.saveSubInterest(subInterest);
			}
		} catch (Exception e) {
			logger.error("Error in saveSubIneterest() of InterestController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySubInterests.htm");
	}

	@RequestMapping("/editSubInterest")
	public ModelAndView editSubInterest(@RequestParam int id,
			@ModelAttribute SubInterest subInterest, Map<String, Object> map) {
		try {
			subInterest = appAdminService4.getSubInterest(id);
			map.put("interestList", appAdminService4.getInterests());
			map.put("interestId", subInterest.getInterest().getInterestId());
		} catch (Exception e) {
			logger.error("Error in editSubInterest() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddSubInterest", "subInterest",
				subInterest);
	}

	@RequestMapping("/addSubInterest")
	public ModelAndView addSubInterest(@ModelAttribute SubInterest subInterest,
			Map<String, Object> map) {
		map.put("interestList", appAdminService4.getInterests());
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddSubInterest", "subInterest",
					new SubInterest());
		} catch (Exception e) {
			logger.error("Error in addSubInterest() of InterestController" + e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### QualityOfResponse methods go here ###### */

	@RequestMapping("/displayQualityOfResponses")
	public ModelAndView listQualityOfResponse(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("qorList", appAdminService4.getQualityOfResponse());
		} catch (Exception e) {
			logger.error("Error in listQualityOfResponse() of QualityOfResponse Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayQualityResponse", model);

	}

	@RequestMapping("/deleteQualityOfResponse")
	public ModelAndView deleteQualityOfResponse(@RequestParam int id) {
		try {
			appAdminService4.deleteQualityOfResponse(id);
		} catch (Exception e) {
			logger.error("Error in deleteQualityOfResponse() of InterestController"
					+ e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayQualityOfResponses.htm");
	}

	@RequestMapping("/saveQualityOfResponse")
	public ModelAndView saveQualityOfResponse(
			@ModelAttribute("qualityOfResponse") @Valid QualityOfResponse qualityOfResponse,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddQualityResponse");
			} else {
				appAdminService4.saveQualityOfResponse(qualityOfResponse);
			}
		} catch (Exception e) {
			logger.error("Error in saveQualityOfResponse() of InterestController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayQualityOfResponses.htm");

	}

	@RequestMapping("/editQualityOfResponse")
	public ModelAndView editQualityOfResponse(@RequestParam int id,
			@ModelAttribute QualityOfResponse qualityOfResponse) {
		try {
			qualityOfResponse = appAdminService4.getQualityOfResponse(id);
		} catch (Exception e) {
			logger.error("Error in editQualityOfResponse() of InterestController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddQualityResponse",
				"qualityOfResponse", qualityOfResponse);
	}

	@RequestMapping("/addQualityOfResponse")
	public ModelAndView addQualityOfResponse(
			@ModelAttribute QualityOfResponse qualityOfResponse) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddQualityResponse",
					"qualityOfResponse", new QualityOfResponse());
		} catch (Exception e) {
			logger.error("Error in addQualityOfResponse() of InterestController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### RtiGroups methods go here ###### */

	@RequestMapping("/displayRtiGroups")
	public ModelAndView listRtiGroups(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("rtiList", appAdminService4.getRtiGroups());
		} catch (Exception e) {
			logger.error("Error in listRtiGroups() of RtiGroups Controller" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayRTIGroups", model);

	}

	@RequestMapping("/deleteRtiGroup")
	public ModelAndView deleteRtiGroup(@RequestParam int id) {
		try {
			appAdminService4.deleteRtiGroup(id);
		} catch (Exception e) {
			logger.error("Error in deleteRtiGroup() of InterestController" + e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayRtiGroups.htm");
	}

	@RequestMapping("/saveRtiGroup")
	public ModelAndView saveRtiGroup(
			@ModelAttribute("rtiGroup") @Valid RtiGroups rtiGroup,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddRTIGroup");
			} else {
				appAdminService4.saveRtiGroup(rtiGroup);
			}
		} catch (Exception e) {
			logger.error("Error in saveRtiGroup() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayRtiGroups.htm");

	}

	@RequestMapping("/editRtiGroup")
	public ModelAndView editRtiGroups(@RequestParam int id,
			@ModelAttribute RtiGroups rtiGroup) {
		try {
			rtiGroup = appAdminService4.getRtiGroup(id);
		} catch (Exception e) {
			logger.error("Error in editRtiGroup() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddRTIGroup", "rtiGroup", rtiGroup);
	}

	@RequestMapping("/addRtiGroup")
	public ModelAndView addRtiGroups(@ModelAttribute RtiGroups rtiGroup) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddRTIGroup", "rtiGroup",
					new RtiGroups());
		} catch (Exception e) {
			logger.error("Error in addRtiGroup() of InterestController" + e);
			e.printStackTrace();
		}
		return mav;

	}

	/* ##### RubricTypes methods go here ###### */

	@RequestMapping("/displayRubricTypes")
	public ModelAndView listRubricTypes(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("rtList", appAdminService4.getRubricTypes());
		} catch (Exception e) {
			logger.error("Error in listRubricTypes() of RubricTypes Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayRubricTypes", model);

	}

	@RequestMapping("/deleteRubricType")
	public ModelAndView deleteRubricType(@RequestParam int id) {
		try {
			appAdminService4.deleteRubricType(id);
		} catch (Exception e) {
			logger.error("Error in deleteRubricType() of InterestController"
					+ e);
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/displayRubricTypes.htm");
	}

	@RequestMapping("/saveRubricType")
	public ModelAndView saveRubricType(
			@ModelAttribute("rubricType") @Valid RubricTypes rubricType,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddRubricType");
			} else {
				appAdminService4.saveRubricType(rubricType);
			}
		} catch (Exception e) {
			logger.error("Error in saveRubricType() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayRubricTypes.htm");

	}

	@RequestMapping("/editRubricType")
	public ModelAndView editRubricTypes(@RequestParam int id,
			@ModelAttribute RubricTypes rubricType) {
		try {
			rubricType = appAdminService4.getRubricType(id);
		} catch (Exception e) {
			logger.error("Error in editRubricType() of InterestController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddRubricType", "rubricType",
				rubricType);
	}

	@RequestMapping("/addRubricType")
	public ModelAndView addRubricTypes(@ModelAttribute RubricTypes RubricType) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddRubricType", "rubricType",
					new RubricTypes());
		} catch (Exception e) {
			logger.error("Error in addRubricType() of InterestController" + e);
			e.printStackTrace();
		}
		return mav;
	}
}
