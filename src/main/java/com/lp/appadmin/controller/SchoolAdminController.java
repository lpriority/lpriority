package com.lp.appadmin.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.BenchmarkCutOffMarksService;
import com.lp.common.service.CommonService;
import com.lp.common.service.DashboardService;
import com.lp.custom.exception.DataException;
import com.lp.login.service.UserLoginService;
import com.lp.mail.service.MailServiceImpl;
import com.lp.model.AcademicYear;
import com.lp.model.AssignK1Tests;
import com.lp.model.BenchmarkResults;
import com.lp.model.Country;
import com.lp.model.District;
import com.lp.model.FluencyMarks;
import com.lp.model.School;
import com.lp.model.SchoolLevel;
import com.lp.model.SchoolType;
import com.lp.model.Security;
import com.lp.model.States;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class SchoolAdminController {

	@Autowired
	@Qualifier("schoolFormValidator")
	private Validator schoolValidator;
	
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private BenchmarkCutOffMarksService benchmarkCutOffMarksService;

	@Autowired
	@Qualifier("registrationFormValidator")
	private Validator regFormValidator;

	@InitBinder("school")
	protected void initSchoolBinder(WebDataBinder binder) {
		binder.setValidator(schoolValidator);
	}

	@InitBinder("userRegistration")
	protected void initUserRegistrationBinder(WebDataBinder binder) {
		binder.setValidator(regFormValidator);
	}

	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private CommonService commonService;
	@Autowired 
	private HttpSession session;

	static final Logger logger = Logger.getLogger(SchoolAdminController.class);

	/* ##### School methods go here ###### */
	@RequestMapping("/displaySchools")
	public ModelAndView listSchools(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("sList", schoolAdminService.getSchools());
		} catch (Exception e) {
			logger.error("Error in listSchools() of SchoolAdminController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplaySchools", model);
	}

	@RequestMapping("/deleteSchool")
	public ModelAndView deleteSchool(@RequestParam int id) {
		try {
			schoolAdminService.deleteSchool(id);
		} catch (Exception e) {
			logger.error("Error in deleteSchool() of SchoolAdminController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySchools.htm");
	}

	@RequestMapping("/saveSchool")
	public ModelAndView saveSchool(
			@ModelAttribute("school") @Valid School school,
			BindingResult result, Map<String, Object> map) {
		ModelAndView model = new ModelAndView("AppManager/DisplaySchools", map);	
		try {
			if (result.hasErrors()) {
				List<Country> countryList = appAdminService2.getCountries();
				map.put("countryIds", countryList);
				map.put("countryId", school.getCountryId());
				List<States> stateList = Collections.emptyList();
				if(!school.getCountryId().equalsIgnoreCase(WebKeys.LP_SELECT)){
					if(Long.parseLong(school.getCountryId())>0){
						stateList = schoolAdminService.getStates(Long.parseLong(school.getCountryId()));
					}
				}
				map.put("stateIds", stateList);				
				map.put("stateId", school.getStateId());
				map.put("schoolLevelId", school.getSchoolLevelId());
				map.put("schoolTypeId", school.getSchoolTypeId());
				map.put("schoolTypeIds", schoolAdminService.getSchoolTypeList());
				map.put("schoolLevelIds",
						schoolAdminService.getSchoolLevelList());
				return new ModelAndView("AppManager/AddSchool");
			} else {
				schoolAdminService.saveSchool(school);
				model.addObject("status", WebKeys.LP_SCHOOL_SAVED_SUCCESSFULLY);
			}
		} catch (DataException e) {
			logger.error(e.getMessage());
			model.addObject("status", e.getMessage());
		}		
		map.put("sList", schoolAdminService.getSchools());		
		return model;
	}

	@RequestMapping("/editSchool")
	public ModelAndView editschool(@RequestParam int id,
			@ModelAttribute School school, Map<String, Object> map) {
		try {
			school = schoolAdminService.getSchool(id);
			map.put("countryIds", appAdminService2.getCountries());
			map.put("stateIds", appAdminService2.getStates());
			map.put("countryId", appAdminService2.getCountryByState(Long
					.valueOf(school.getStates().getStateId())));
			map.put("stateId", school.getStates().getStateId());
			map.put("schoolLevelId", school.getSchoolLevel().getSchoolLevelId());
			map.put("schoolTypeId", school.getSchoolType().getSchoolTypeId());
			map.put("schoolTypeIds", schoolAdminService.getSchoolTypeList());
			map.put("schoolLevelIds", schoolAdminService.getSchoolLevelList());
		} catch (Exception e) {
			logger.error("Error in editSchool() of SchoolAdminController" + e);
		}
		return new ModelAndView("AppManager/AddSchool", "school", school);
	}

	@RequestMapping("/addSchool")
	public ModelAndView addSchool(@ModelAttribute School school,
			Map<String, Object> map) {
		List<Country> countryList = appAdminService2.getCountries();
		map.put("countryIds", countryList);
		map.put("schoolTypeIds", schoolAdminService.getSchoolTypeList());
		map.put("schoolLevelIds", schoolAdminService.getSchoolLevelList());
		map.put("districtIds", schoolAdminService.getDistricts());
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddSchool", "school",
					new School());
		} catch (Exception e) {
			logger.error("Error in addSchool() of SchoolAdminController" + e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### UserRegistration methods go here ###### */

	@RequestMapping("/displayUserRegistrations")
	public ModelAndView listUserRegistrations(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("urList",
					schoolAdminService.getUserRegistrations(WebKeys.LP_USER_TYPE_ADMIN));
		} catch (Exception e) {
			logger.error("Error in displayUserRegistrations() of AcademicPerformances Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayUserRegistrations", model);
	}

	@RequestMapping("/deleteUserRegistration")
	public @ResponseBody
	void deleteSchoolAdmin(HttpSession session, HttpServletResponse response,
			@RequestParam("regId") long regId) {
		try {

			String helloAjax = "";
			int stat = 0;
			stat = schoolAdminService.deleteUserRegistration(regId);
			if (stat > 0) {
				helloAjax = "School Administrator deleted Successfully";
			} else {
				helloAjax = "School Administrator Not deleted Successfully";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/saveUserRegistration")
	public ModelAndView saveUserRegistration(
			@ModelAttribute("userRegistration") @Valid UserRegistration userRegistration,
			BindingResult result, Map<String, Object> map) {
		boolean flag = false;
		String status = "";
		try {
			if (result.hasErrors()) {
				map.put("schoolList", schoolAdminService.getSchools());
				map.put("schoolId", userRegistration.getSchoolId());
				return new ModelAndView("AppManager/AddUserRegistration");
			} else {
				userRegistration.setUser(appAdminService.getUserType("Admin"));
				if(userRegistration.getRegId()>0){
					flag = schoolAdminService.updateAdminRegistration(userRegistration);
					if(!flag){
						status = WebKeys.LP_ADMIN_SCHOOL_ALREADY_EXISTS;
					}
				}else{
					userRegistration.setStatus(WebKeys.LP_STATUS_NEW);
					flag = schoolAdminService.saveUserRegistration(userRegistration);
					if(!flag){
						status = WebKeys.LP_ADMIN_ALREADY_EXISTS;
					}
					logger.info("status :"+status);
					if(flag){
						Security sec = new Security();
						sec.setUserRegistration(userRegistration);
						if (userLoginService.checkSecurity(sec)) {
							sec.setVerificationCode("adminadmin");
							sec.setStatus(WebKeys.ACTIVE);
							userLoginService.saveSecurity(sec);
							MailServiceImpl.FirstTimeUserInfoMail(userRegistration.getEmailId(), "admin@learningpriority.com", "adminadmin", userRegistration.getUser().getUserTypeid());
						}
					}
				}				
			}
		} catch (Exception e) {
			logger.error("Error in saveUserRegistration() of SchoolAdminController"	+ e);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();	
		ModelAndView mav = new ModelAndView("AppManager/DisplayUserRegistrations", model);
		
		try {
			model.put("urList",	schoolAdminService.getUserRegistrations(WebKeys.LP_USER_TYPE_ADMIN));
			mav = new ModelAndView("AppManager/DisplayUserRegistrations", model);
			if(flag){
	            mav.addObject("returnMessage", WebKeys.LP_ADMIN_ADDED_SUCCESSFULLY);
		    }
		    else{
		        mav.addObject("returnMessage", WebKeys.LP_ADMIN_ALREADY_EXISTS);
		    }
		} catch (Exception e) {
			logger.error("Error in displayUserRegistrations() of AcademicPerformances Controller"+ e);
			mav.addObject("returnMessage", WebKeys.LP_SYSTEM_ERROR);
		}
		
		
        
        return mav;
	}

	@RequestMapping("/editUserRegistration")
	public ModelAndView editUserRegistration(@RequestParam int id,
			@ModelAttribute UserRegistration userRegistration,
			Map<String, Object> map) {
		try {
			userRegistration = schoolAdminService.getUserRegistration(id);
			map.put("schoolList", schoolAdminService.getSchools());
			map.put("schoolId", userRegistration.getSchool().getSchoolId());
		} catch (Exception e) {
			logger.error("Error in editUserRegistration() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddUserRegistration",
				"userRegistration", userRegistration);
	}

	@RequestMapping("/addUserRegistration")
	public ModelAndView addUserRegistration(
			@ModelAttribute UserRegistration userRegistration,
			Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		map.put("schoolList", schoolAdminService.getSchools());
		try {
			mav = new ModelAndView("AppManager/AddUserRegistration",
					"userRegistration", new UserRegistration());
		} catch (Exception e) {
			logger.error("Error in addUserRegistration() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getStatesByCountryId", method = RequestMethod.GET)
	public @ResponseBody
	List<States> showstates(@RequestParam("countryId") long countryId) {
		List<States> statelist = appAdminService2.getStates(countryId);
		return statelist;
	}

	/* ##### SchoolType methods go here ###### */
	@RequestMapping("/displaySchoolTypes")
	public ModelAndView listSchoolTypes(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("stList", schoolAdminService.getSchoolTypeList());
		} catch (Exception e) {
			logger.error("Error in listSchoolTypes() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplaySchoolTypes", model);
	}

	@RequestMapping("/deleteSchoolType")
	public ModelAndView deleteSchoolType(@RequestParam int id) {
		try {
			schoolAdminService.deleteSchoolType(id);
		} catch (Exception e) {
			logger.error("Error in deleteSchoolType() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySchoolTypes.htm");
	}

	@RequestMapping("/saveSchoolType")
	public ModelAndView saveSchoolType(
			@ModelAttribute("schoolType") @Valid SchoolType schoolType,
			BindingResult result, Map<String, Object> map) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddSchoolType");
			} else {
				schoolAdminService.saveSchoolType(schoolType);
			}
		} catch (Exception e) {
			logger.error("Error in saveSchoolType() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySchoolTypes.htm");
	}

	@RequestMapping("/editSchoolType")
	public ModelAndView editschool(@RequestParam int id,
			@ModelAttribute SchoolType schoolType, Map<String, Object> map) {
		try {
			schoolType = schoolAdminService.getSchoolType(id);
		} catch (Exception e) {
			logger.error("Error in editSchool() of SchoolAdminController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddSchoolType", "schoolType",
				schoolType);
	}

	@RequestMapping("/addSchoolType")
	public ModelAndView addSchoolType(@ModelAttribute SchoolType schoolType,
			Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddSchoolType", "schoolType",
					new SchoolType());
		} catch (Exception e) {
			logger.error("Error in addSchoolType() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	/* ##### SchoolLevel methods go here ###### */
	@RequestMapping("/displaySchoolLevels")
	public ModelAndView listSchoolLevels(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("slList", schoolAdminService.getSchoolLevelList());
		} catch (Exception e) {
			logger.error("Error in listSchoolLevels() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplaySchoolLevels", model);
	}

	@RequestMapping("/deleteSchoolLevel")
	public ModelAndView deleteSchoolLevel(@RequestParam int id) {
		try {
			schoolAdminService.deleteSchoolLevel(id);
		} catch (Exception e) {
			logger.error("Error in deleteSchoolLevel() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySchoolLevels.htm");
	}

	@RequestMapping("/saveSchoolLevel")
	public ModelAndView saveSchoolLevel(
			@ModelAttribute("schoolLevel") @Valid SchoolLevel schoolLevel,
			BindingResult result, Map<String, Object> map) {
		try {
			if (result.hasErrors()) {
				return new ModelAndView("AppManager/AddSchoolLevel");
			} else {
				schoolAdminService.saveSchoolLevel(schoolLevel);
			}
		} catch (Exception e) {
			logger.error("Error in saveSchoolLevel() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displaySchoolLevels.htm");
	}

	@RequestMapping("/editSchoolLevel")
	public ModelAndView editSchoolLevel(@RequestParam int id,
			@ModelAttribute SchoolLevel schoolLevel, Map<String, Object> map) {
		try {
			schoolLevel = schoolAdminService.getSchoolLevel(id);
		} catch (Exception e) {
			logger.error("Error in editSchoolLevel() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddSchoolLevel", "schoolLevel",
				schoolLevel);
	}

	@RequestMapping("/addSchoolLevel")
	public ModelAndView addSchoolLevel(@ModelAttribute SchoolLevel schoolLevel,
			Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("AppManager/AddSchoolLevel", "schoolLevel",
					new SchoolLevel());
		} catch (Exception e) {
			logger.error("Error in addSchoolLevel() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping("/addDistrict")
	public ModelAndView addDistrict(@ModelAttribute District district,
			Map<String, Object> map, HttpSession session) {
		List<Country> countryList = appAdminService2.getCountries();
		map.put("countryIds", countryList);
		ModelAndView mav = new ModelAndView();
		String saves = "save";
		try {
			mav = new ModelAndView("AppManager/AddDistrict", "district",
					new District());
			session.setAttribute("saves", saves);
		} catch (Exception e) {
			logger.error("Error in addDistrict() of SchoolAdminController" + e);
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/SaveDistrict", method = RequestMethod.GET)
	public @ResponseBody
	void SaveDistrict(HttpServletResponse response, HttpSession session,
			@ModelAttribute("district") District district,
			BindingResult result,
			@RequestParam("districtName") String districtName,
			@RequestParam("noSchools") long noSchools,
			@RequestParam("countryId") String countryId,
			@RequestParam("stateId") long stateId,
			@RequestParam("city") String city,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address,
			@RequestParam("faxNumber") String faxNumber) throws Exception {
		String helloAjax = "";
		String saves = (String) session.getAttribute("saves");
		
		if (saves.equalsIgnoreCase("edit")) {
			int id = (Integer) session.getAttribute("id");
			District dist = schoolAdminService.getDistrict(id);
			dist.setDistrictName(districtName);
			dist.setNoSchools(noSchools);
			dist.setCountryId(countryId);
			dist.setStates(appAdminService2.getState(stateId));
			dist.setCity(city);
			dist.setAddress(address);
			dist.setPhoneNumber(phoneNumber);
			dist.setFaxNumber(faxNumber);
			schoolAdminService.updateDistrict(dist);
			try {
				schoolAdminService.updateDistrict(dist);
				helloAjax = "District Updated Successfully";
			} catch (Exception e) {
				helloAjax = "District Not Updated Successfully";
			}
		} else {

			district.setDistrictName(districtName);
			district.setNoSchools(noSchools);
			district.setCountryId(countryId);
			district.setStates(appAdminService2.getState(stateId));
			district.setCity(city);
			district.setAddress(address);
			district.setPhoneNumber(phoneNumber);
			district.setFaxNumber(faxNumber);
			try {
				schoolAdminService.saveDistrict(district);
				helloAjax = "District Added Successfully";
			} catch (Exception e) {
				helloAjax = "District Not Added Successfully";
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);

	}

	@RequestMapping("/displayDistricts")
	public ModelAndView listDistricts(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("dList", schoolAdminService.getDistricts());
		} catch (Exception e) {
			logger.error("Error in listDistricts() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayDistricts", model);
	}

	@RequestMapping("/editDistrict")
	public ModelAndView editdistrict(@RequestParam int id,
			@ModelAttribute District district, Map<String, Object> map,
			HttpSession session) {
		String saves = "edit";
		try {
			district = schoolAdminService.getDistrict(id);
			map.put("countryIds", appAdminService2.getCountries());
			map.put("stateIds", appAdminService2.getStates());
			map.put("countryId", appAdminService2.getCountryByState(Long
					.valueOf(district.getStates().getStateId())));
			map.put("stateId", district.getStates().getStateId());
			session.setAttribute("saves", saves);
			session.setAttribute("id", id);
		} catch (Exception e) {
			logger.error("Error in editdistrict() of SchoolAdminController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/AddDistrict", "district", district);
	}
	@RequestMapping(value = "/deleteDistrict", method = RequestMethod.GET)
	public @ResponseBody
	void deleteDistrict(HttpSession session, HttpServletResponse response,
			@RequestParam("districtId") long districtId) {
		try {

			String helloAjax = "";
			int stat = 0;
			stat = schoolAdminService.deleteDistrict(districtId);
			if (stat > 0) {
				helloAjax = "District deleted Successfully";
			} else {
				helloAjax = "District Not deleted Successfully";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	@RequestMapping(value = "/exportFluencyResults", method = RequestMethod.POST)
	public ModelAndView getExcel(HttpServletResponse response, HttpServletRequest request) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String[] teacherIds = request.getParameterValues("teacherId");
		String[] benchmarkIds = request.getParameterValues("benchmarkCategoryId");
		long academicYearId=Long.parseLong(request.getParameter("academicYearId"));
		List<BenchmarkResults> benchmarkResults = commonService.getBenchmarkResults(userReg.getSchool().getSchoolId(), teacherIds, benchmarkIds,academicYearId);
		response.setHeader("Content-Disposition", "attachment; filename=\"FluencyResults.xls\"");
		return new ModelAndView("benchmarkResultsExcelView", "benchmarkResults", benchmarkResults);
	}
	
	@RequestMapping(value = "/getFluencyResults", method = RequestMethod.GET)
	public ModelAndView getFluencyResults(HttpServletResponse response) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("assessments/test_results_tab");
		model.addObject("teacherEmails", dashboardService.getTeachersBySchoolId(userReg.getSchool()));
		model.addObject("benchmarkTypes", benchmarkCutOffMarksService.getMainBenchmarkTestTypes());
		model.addObject("acadeYearsLst",commonService.getSchoolAcademicYears());
		model.addObject("tab", WebKeys.LP_TAB_DOWNLOAD_FLUENCY_RESULTS);
		return model;
	}
	
	@RequestMapping(value = "/getFluency", method = RequestMethod.GET)
	public ModelAndView getFluency(HttpServletResponse response) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Admin/fluency_results");
		model.addObject("teacherEmails", dashboardService.getTeachersBySchoolId(userReg.getSchool()));
		model.addObject("benchmarkTypes", benchmarkCutOffMarksService.getMainBenchmarkTestTypes());
		model.addObject("acadeYearsLst",commonService.getSchoolAcademicYears());
		model.addObject("tab", WebKeys.LP_TAB_DOWNLOAD_FLUENCY_RESULTS);
		return model;
	}
	
	@RequestMapping(value = "/getTeachersByYear", method = RequestMethod.GET)
	public View getTeachersByYear(HttpServletResponse response, @RequestParam("academicYearId") long academicYearId, Model model ) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		AcademicYear academicYear = commonService.getAcademicYearById(academicYearId);
		model.addAttribute("teachers", dashboardService.getTeachersByYear(userReg.getSchool(), academicYear));
		return new  MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/exportEarlyLitracyResults", method = RequestMethod.GET)
	public ModelAndView getEarlyLitracyExcel() {
		List<AssignK1Tests> earlyLitracyResults = commonService.getEarlyLitracyTestResults();
		return new ModelAndView("earlyLitracyResultsExcelView", "earlyLitracyResults", earlyLitracyResults);
	}
	@RequestMapping(value = "/exportFluencyReadingResults", method = RequestMethod.GET)
	public ModelAndView exportFluencyReadingResults() {
		List<FluencyMarks> fluencyMarks = commonService.getFluencyMarks(15);
		return new ModelAndView("fluencyReadingResultsExcelView", "fluencyMarks", fluencyMarks);
	}
	@RequestMapping(value = "/migrateIOLData", method = RequestMethod.GET)
	public ModelAndView migrateIOLData() {
		commonService.migrateIOLData();
		return new ModelAndView("redirect:/index.htm");
	}
	@RequestMapping(value = "/getComprehensionResults", method = RequestMethod.GET)
	public ModelAndView getBenchmarkResults(HttpServletResponse response) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Admin/comprehension_results");
		model.addObject("teacherEmails", dashboardService.getTeachersBySchoolId(userReg.getSchool()));
		model.addObject("acadeYearsLst",commonService.getSchoolAcademicYears());
		model.addObject("tab", WebKeys.LP_TAB_DOWNLOAD_COMPREHENSION_RESULTS);
		return model;
	}
	@RequestMapping(value = "/exportComprehensionResults", method = RequestMethod.POST)
	public ModelAndView exportComprehensionResults(HttpServletResponse response, HttpServletRequest request) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long academicYearId=Long.parseLong(request.getParameter("compAcademicYearId"));
		AcademicYear academicYear = commonService.getAcademicYearById(academicYearId);
		String[] teacherIds = request.getParameterValues("teacherId");
		List<StudentAssignmentStatus> comprehensionResults = commonService.getComprehensionResults(userReg.getSchool().getSchoolId(), teacherIds, academicYear);
		response.setHeader("Content-Disposition", "attachment; filename=\"ComprehensionResults.xls\"");
		return new ModelAndView("comprehensionResultsExcelView", "comprehensionResults", comprehensionResults);
	}
	

}
