package com.lp.common.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.AppAdminService3;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.FileService;
import com.lp.common.service.MyProfileService;
import com.lp.login.service.UserLoginService;
import com.lp.model.AcademicPerformance;
import com.lp.model.Address;
import com.lp.model.Country;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.GradeLevel;
import com.lp.model.Interest;
import com.lp.model.RegisterForClass;
import com.lp.model.States;
import com.lp.model.Student;
import com.lp.model.SubInterest;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.student.service.StudentTestResultsService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.MimeTypeConstants;
import com.lp.utils.WebKeys;


@Controller
public class CommonMainController {
	 @Autowired
	 HttpServletRequest request;
	 @Autowired
	 private StudentTestResultsService studentTestResultsService;
	 @Autowired
	 private Address address;
	 @Autowired
	 private AppAdminService2 appAdminService2;
	 @Autowired
	 private AppAdminService3 appAdminService3;
	 @Autowired
	 private SchoolAdminService schoolAdminService;
	 @Autowired
	 private UserLoginService userLoginService;
	 @Autowired
	 private MyProfileService myProfileService;
	 @Autowired
	 private AdminService adminservice;
	 @Autowired
	 private CommonService commonService;
	 @Autowired
	 private SchoolAdminService schooladminservice;
	 @Autowired
	 private StudentService studentService;
	 @Autowired
	 private FileService fileservice;
	 
	 static final Logger logger = Logger.getLogger(CommonMainController.class);
	 
	@RequestMapping(value = "/MainDesktop", method = RequestMethod.GET)
	public ModelAndView CommonHeader(Map<String, Object> map) {
		ModelAndView model = new ModelAndView("CommonJsp/MainDesktop");
		return model;
	}
	
	@RequestMapping(value= "/getLPSystemRubric", method = RequestMethod.GET)
	public ModelAndView getStudentList(Model model) {
		try{
			List<AcademicPerformance>  academicPerformanceLt = studentTestResultsService.getAcademicPerformance();
			Map<Long, String>  numPerGradeMap = studentTestResultsService.getNumericalPercentageScore();
			model.addAttribute("academicPerformanceLt", academicPerformanceLt);
			model.addAttribute("numPerGradeMap", numPerGradeMap);
		}catch(Exception e){
			logger.error("Error in getStudentList() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/LPSystemRubric");
	}
	@RequestMapping(value= "/personalInformation", method = RequestMethod.GET)
	public ModelAndView personalInformation(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.PERSONAL_INFO);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			List<Country> countryLt = appAdminService2.getCountries();
			List<States>  statesLt = schoolAdminService.getStates(userRegistration.getAddress().getStates().getCountry().getCountryId());
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("countryLt", countryLt);
			model.addAttribute("statesLt", statesLt);
			model.addAttribute("userRegistrationForm",userRegistration);
		}catch(Exception e){
			logger.error("Error in personalInformation() of CommonMainController "+ e);
		}
		return new ModelAndView("CommonJsp/my_profile_main_page");		
	}
	@RequestMapping(value = "/updatePersonalInfo", method = RequestMethod.GET)
	public @ResponseBody void updatePersonalInfo(HttpServletResponse response,@ModelAttribute("userRegistrationForm") UserRegistration userRegistration, HttpSession session,HttpServletRequest request) {
		try{
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			userRegistration.setAddress(address);
			userRegistration.setRegId(userReg.getRegId());
			userRegistration.getAddress().setAddressId(userReg.getAddress().getAddressId());
			userRegistration.getAddress().setAddress(userRegistration.getAddress1() + WebKeys.ADDRESSES_CONCAT_TOKEN + userRegistration.getAddress2());
			userRegistration.getAddress().setCity(userRegistration.getCity());
			userRegistration.getAddress().setStates(appAdminService2.getState(Long.valueOf(userRegistration.getStateId())));
			userRegistration.getAddress().setZipcode(Integer.valueOf(userRegistration.getZipcode()));
			userRegistration.getAddress().getStates().setCountry(appAdminService2.getCountry((Long.valueOf(userRegistration.getCountryId())))); 
			userRegistration.setUser(userReg.getUser());
			userRegistration.setStatus(userReg.getStatus());
			boolean status = myProfileService.updatePersonalInfo(userRegistration);
	
			if(status){
				userReg = schoolAdminService.getUserRegistration(userRegistration.getRegId());
				if (userReg != null) {
					session.setAttribute(WebKeys.LP_USER_REGISTRATION, userReg);
				}
				 response.getWriter().write(WebKeys.UPDATED_SUCCESSFULLY);  
			}else{
				 response.getWriter().write(WebKeys.FAILED_TO_UPDATE);  
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in updatePersonalInfo() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value= "/changeUserName", method = RequestMethod.GET)
	public ModelAndView changeUserName(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.CHANGE_USER_NAME);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("userRegistrationForm",new UserRegistration());
		}catch(Exception e){
			logger.error("Error in changeUserName() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/change_user_name");
	}
	
	@RequestMapping(value= "/updateUserName", method = RequestMethod.GET)
	public @ResponseBody void updateUserName(HttpServletResponse response,@ModelAttribute("userRegistrationForm") UserRegistration userRegistration,HttpSession session) {
		boolean status = myProfileService.updateUserName(userRegistration);
		try{
			if(status){
				UserRegistration userReg = schoolAdminService.getUserRegistration(userRegistration.getRegId());
				if (userReg != null) {
					session.setAttribute(WebKeys.LP_USER_REGISTRATION, userReg);
				}
				 response.getWriter().write(WebKeys.UPDATED_SUCCESSFULLY);  
			}else{
				 response.getWriter().write(WebKeys.FAILED_TO_UPDATE);  
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in updateUserName() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value= "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.CHANGE_PASSWORD);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			String password = userRegistration.getPassword();
			model.addAttribute("regId",userRegistration.getRegId());
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("password",password);
			model.addAttribute("userRegistrationForm",new UserRegistration());
		}catch(Exception e){
			logger.error("Error in changePassword() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/change_password");
	}
	
	@RequestMapping(value= "/updatePassword", method = RequestMethod.GET)
	public @ResponseBody void updatePassword(HttpServletResponse response,@ModelAttribute("userRegistrationForm") UserRegistration userRegistration,HttpSession session) {
		String md5Password = userLoginService.getMD5Conversion(userRegistration.getNewPassword());
		userRegistration.setNewPassword(md5Password);
		boolean status = myProfileService.updatePassword(userRegistration);
		try{
			if(status){
				UserRegistration userReg = schoolAdminService.getUserRegistration(userRegistration.getRegId());
				if (userReg != null) {
					session.setAttribute(WebKeys.LP_USER_REGISTRATION, userReg);
				}
				 response.getWriter().write(WebKeys.UPDATED_SUCCESSFULLY);  
			}else{
				 response.getWriter().write(WebKeys.FAILED_TO_UPDATE);  
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in updatePassword() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value= "/schoolInfo", method = RequestMethod.GET)
	public ModelAndView schoolInfo(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.SCHOOL_INFORMATION);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("userRegistrationForm",new UserRegistration());
		}catch(Exception e){
			logger.error("Error in schoolInfo() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/school_info");
	}
	
	@RequestMapping(value= "/homePage", method = RequestMethod.GET)
	public ModelAndView homePage(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.HOME_PAGE);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("userRegistrationForm",new UserRegistration());
		}catch(Exception e){
			logger.error("Error in homePage() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/home_page");
	}
	
	
	@RequestMapping(value= "/updateHomePage", method = RequestMethod.GET)
	public @ResponseBody void updateHomePage(HttpServletResponse response,@ModelAttribute("userRegistrationForm") UserRegistration userRegistration,HttpSession session) {
		boolean status = myProfileService.updateHomePage(userRegistration);
		try{
			if(status){
				UserRegistration userReg = schoolAdminService.getUserRegistration(userRegistration.getRegId());
				if (userReg != null) {
					session.setAttribute(WebKeys.LP_USER_REGISTRATION, userReg);
				}
				 response.getWriter().write(WebKeys.UPDATED_SUCCESSFULLY);  
			}else{
				 response.getWriter().write(WebKeys.FAILED_TO_UPDATE);  
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in updateHomePage() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value= "/personalInterest", method = RequestMethod.GET)
	public ModelAndView personalInterest(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.PERSONAL_INTEREST);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			long userTypeId = userRegistration.getUser().getUserTypeid();
			List<Integer> interestIdsLt = null;
			List<Interest> InterestLt = new ArrayList<Interest>();
			if(userTypeId == 1 || userTypeId == 2){
				interestIdsLt =  new ArrayList<Integer>(Arrays.asList(1));
			}else if(userTypeId == 3 || userTypeId == 4){
				interestIdsLt = new ArrayList<Integer>(Arrays.asList(4, 5));
			}else if(userTypeId == 5 || userTypeId == 6){
				interestIdsLt = new ArrayList<Integer>(Arrays.asList(7, 8));
			}
			for (Integer interestId : interestIdsLt) {
				Interest interest = myProfileService.getInterestByInterestId(interestId);
				if(interest != null){
					InterestLt.add(interest);
				}
			}
			List<SubInterest> subInterestLt = myProfileService.getAllUserPersonalInterests(interestIdsLt);
			List<UserInterests> userInterestsLt = myProfileService.getUserPersonalInterests(userRegistration.getRegId());
			
			model.addAttribute("userTypeId",userTypeId);
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("subInterestLt",subInterestLt);
			model.addAttribute("InterestLt",InterestLt);
			model.addAttribute("userInterestsLt",userInterestsLt);
			model.addAttribute("typeOfInterest","personal");
		}catch(Exception e){
			logger.error("Error in personalInterest() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/personal_interest");
	}
	
	@RequestMapping(value= "/updatePersonalInterest", method = RequestMethod.GET)
	public @ResponseBody void updatePersonalInterest(@RequestParam("userInterestArray") ArrayList<String>  userInterestArray, HttpServletResponse response,HttpSession session) {
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
		boolean status = myProfileService.updatePersonalInterest(userInterestArray, userRegistration.getRegId());
		try{
			if(status){
				UserRegistration userReg = schoolAdminService.getUserRegistration(userRegistration.getRegId());
				if (userReg != null) {
					session.setAttribute(WebKeys.LP_USER_REGISTRATION, userReg);
				}
				 response.getWriter().write(WebKeys.UPDATED_SUCCESSFULLY);  
			}else{
				 response.getWriter().write(WebKeys.FAILED_TO_UPDATE);  
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in updatePersonalInterest() of CommonMainController "+ e);
		}
	}
	@RequestMapping(value= "/educationalInfo", method = RequestMethod.GET)
	public ModelAndView educationalInfo(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.EDUCATIONAL_INFO);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			List<GradeLevel> gradeLevelLt = appAdminService3.getGradeLevel();
			long userTypeId = userRegistration.getUser().getUserTypeid();
			List<Grade> gradesLt = new ArrayList<Grade>();
			List<Long> gradIdsLt = new ArrayList<Long>();
			gradIdsLt.clear();
			Map<Long,List<GradeClasses>> classesMap = new HashMap<Long,List<GradeClasses>>();
			if(userTypeId == 3){
				Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
				List<TeacherSubjects> classLt = new ArrayList<TeacherSubjects>();
				classLt = myProfileService.getTeacherClasses(teacherObj.getTeacherId());
				for (TeacherSubjects teacherSubjects : classLt) {
					if(!gradIdsLt.contains(teacherSubjects.getGrade().getGradeId()) && !teacherSubjects.getStudentClass().getClassName().equalsIgnoreCase(WebKeys.LP_CLASS_HOMEROME)){
						gradIdsLt.add(teacherSubjects.getGrade().getGradeId());
						gradesLt.add(teacherSubjects.getGrade());
						classesMap.put(teacherSubjects.getGrade().getGradeId(), myProfileService.getTeacherGradeClasses(teacherSubjects.getGrade().getGradeId(),teacherObj.getTeacherId()));
					}
				}
				model.addAttribute("userId",teacherObj.getTeacherId());
				model.addAttribute("classLt",classLt);
			}else if(userTypeId == 5 || userTypeId == 6 || userTypeId == 4){
				Student studentObj = new Student();
				if(userTypeId == 4){
					studentObj = (Student) session.getAttribute(WebKeys.PARENT_CHILD_OBJECT);
				}else{
					studentObj = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
				}
				
				List<RegisterForClass>  classLt = new ArrayList<RegisterForClass>();
				classLt = myProfileService.getStudentClasses(studentObj.getStudentId());
				for (RegisterForClass registerForClass : classLt) {
					if(!gradIdsLt.contains(studentObj.getGrade().getGradeId())){
						gradIdsLt.add(studentObj.getGrade().getGradeId());
						gradesLt.add(registerForClass.getGradeClasses().getGrade());
						classesMap.put(studentObj.getGrade().getGradeId(),myProfileService.getStudentGradeClasses(studentObj.getGrade().getGradeId(), studentObj.getStudentId()));
					}
				}
				model.addAttribute("userId",studentObj.getStudentId());
				model.addAttribute("classLt",classLt);
			}
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("gradesLt",gradesLt);
			model.addAttribute("gradeLevelLt",gradeLevelLt);
			model.addAttribute("classesMap",classesMap);
			model.addAttribute("userTypeId",userTypeId);

		}catch(Exception e){
			logger.error("Error in educationalInfo() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/educational_info");
	}
	@RequestMapping(value= "/addEducationalInfo", method = RequestMethod.GET)
	public ModelAndView addEducationalInfo(@RequestParam("userId") long userId, HttpSession session, Model model) {
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			long userTypeId = userRegistration.getUser().getUserTypeid();
			model.addAttribute("userTypeId",userTypeId);
			if(userTypeId == 5 || userTypeId == 6 || userTypeId == 4){
				Student studentObj = new Student();
				if(userTypeId == 4){
					model.addAttribute("userTypeId",5);
					studentObj = (Student) session.getAttribute(WebKeys.PARENT_CHILD_OBJECT);
				}else{
					studentObj = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
				}
				model.addAttribute("hiddenGradeId",studentObj.getGrade().getGradeId());
			}
			List<Grade> gradesLt = adminservice.getSchoolGrades(userRegistration.getSchool().getSchoolId());
			model.addAttribute("gradesLt",gradesLt);
			model.addAttribute("userId",userId);
		}catch(Exception e){
			logger.error("Error in schoolInfo() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/addEducationalInfo");
	}
	
	@RequestMapping(value= "/addTeacherGradeClass", method = RequestMethod.GET)
	public @ResponseBody void addTeacherGradeClass(
			@RequestParam("teacherId") long teacherId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("gradeLevelIdArr") ArrayList<Long>  gradeLevelIdArr,
			@RequestParam("noSectionsPerDayArr") ArrayList<Integer>  noSectionsPerDayArr,
			@RequestParam("noSectionsPerWeekArr") ArrayList<Integer>  noSectionsPerWeekArr,
			@RequestParam("classIdArr") ArrayList<Long> classIdArr,
			HttpSession session, 
			HttpServletResponse response) {
		boolean success = true;
		try{
			for (int i = 0; i < classIdArr.size(); i++) {
				success = myProfileService.addTeacherGradeClass(teacherId, gradeId, classIdArr.get(i), gradeLevelIdArr.get(i), noSectionsPerDayArr.get(i), noSectionsPerWeekArr.get(i));
			    if(!success){
			    	  response.getWriter().write(WebKeys.FAILED_TO_ADD);
			    	  return;
			    }
			}
			if(success){
				response.getWriter().write(WebKeys.ADDED_SUCCESSFULLY);
			}
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in addTeacherGradeClass() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value= "/addStudentGradeClass", method = RequestMethod.GET)
	public @ResponseBody void addStudentGradeClass(
			@RequestParam("studentId") long studentId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("gradeLevelIdArr") ArrayList<Long>  gradeLevelIdArr,
			@RequestParam("classIdArr") ArrayList<Long> classIdArr,
			HttpSession session, 
			HttpServletResponse response) {
		boolean success = true;
		try{
			for (int i = 0; i < classIdArr.size(); i++) {
				long gradeClassId = commonService.getGradeClassId(gradeId, classIdArr.get(i));
				long sectionId = adminservice.getSectionIdByGradeClassIdAndGradeLevelId(gradeClassId, gradeLevelIdArr.get(i));
				success = myProfileService.addStudentGradeClass(studentId, gradeClassId, gradeLevelIdArr.get(i), sectionId);
				if(!success){
			    	 response.getWriter().write(WebKeys.FAILED_TO_ADD);
			    	 return;
			    }
			}
			if(success){
				response.getWriter().write(WebKeys.ADDED_SUCCESSFULLY);
			}
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html");  			
		}catch(Exception e){
			logger.error("Error in addStudentGradeClass() of CommonMainController "+ e);
		}
	}
	@RequestMapping(value= "/sportsAndActivities", method = RequestMethod.GET)
	public ModelAndView sportsAndActivities(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.LP_TAB_UPDATE_SCHOOL_SPORTS);
			model.addAttribute("tab",WebKeys.LP_TAB_UPDATE_SCHOOL_SPORTS);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			List<Integer> interestIdsLt = null;
			List<Interest> InterestLt = new ArrayList<Interest>();
			interestIdsLt =  new ArrayList<Integer>(Arrays.asList(2,3));
			for (Integer interestId : interestIdsLt) {
				Interest interest = myProfileService.getInterestByInterestId(interestId);
				if(interest != null){
					InterestLt.add(interest);
				}
			}
			List<SubInterest> subInterestLt = myProfileService.getAllUserPersonalInterests(interestIdsLt);
			List<UserInterests> userInterestsLt = myProfileService.getUserPersonalInterests(userRegistration.getRegId());
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("subInterestLt",subInterestLt);
			model.addAttribute("InterestLt",InterestLt);
			model.addAttribute("userInterestsLt",userInterestsLt);
			model.addAttribute("typeOfInterest","sports");
		}catch(Exception e){
			logger.error("Error in personalInterest() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/personal_interest");
	}
	
	@RequestMapping(value = "/loadUserFile", method = RequestMethod.GET)
	public void loadUserFile(
			@RequestParam("regId") long regId,
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session){
		try{
			String uploadFilePath = "";
			if(usersFilePath.toString().length() > 0 && regId > 0){
				UserRegistration userReg = schooladminservice.getUserRegistration(regId);
				uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);	
				System.out.println("uploadFilePath="+uploadFilePath);
				String fullUsersFilePath = uploadFilePath+ File.separator + usersFilePath;
				System.out.println("fullUsersFilePath="+fullUsersFilePath);
				try{
					File file = new File(fullUsersFilePath);
					
				
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(usersFilePath);
						if(extension != null){
							if(extension.equalsIgnoreCase("wav")){
								byte[] byteArr = readWAVAudioFileData(fullUsersFilePath);
								if(byteArr == null){
									Path path = Paths.get(fullUsersFilePath);
									byteArr = Files.readAllBytes(path);
								}
								inputStream = new ByteArrayInputStream(byteArr);
								response.setContentLength((int)byteArr.length);
							}else{
								inputStream = new FileInputStream(file);
								response.setContentLength((int)file.length());
							}
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}catch(IOException ie){
					ie.printStackTrace();
				}
			}
		}catch(Exception e){
			logger.error("Error in loadUserFile() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value = "/loadRRFile", method = RequestMethod.GET)
	public void loadRRFile(
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session){
		try{
			if(usersFilePath.toString().length() > 0){
				try{
					File file = new File(usersFilePath);
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(usersFilePath);
						if(extension != null){
							if(extension.equalsIgnoreCase("wav")){
								byte[] byteArr = readWAVAudioFileData(usersFilePath);
								if(byteArr == null){
									Path path = Paths.get(usersFilePath);
									byteArr = Files.readAllBytes(path);
								}
								inputStream = new ByteArrayInputStream(byteArr);
								response.setContentLength((int)byteArr.length);
							}else{
								inputStream = new FileInputStream(file);
								response.setContentLength((int)file.length());
							}
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}catch(IOException ie){
					ie.printStackTrace();
				}
			}
		}catch(Exception e){
			logger.error("Error in loadUserFile() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value = "/loadSchoolCommonFile", method = RequestMethod.GET)
	public void loadSchoolCommonFile(
			@RequestParam("schoolCommonFilePath") String schoolCommonFilePath,
			HttpServletResponse response,
			HttpSession session){
		try{
			String uploadFilePath = "";
			if(schoolCommonFilePath.toString().length() > 0){
				UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
				uploadFilePath = FileUploadUtil.getSchoolCommonFilesPath(userRegistration.getSchool(), request);
				String fullUsersFilePath = uploadFilePath+ File.separator + schoolCommonFilePath;
				try{
					File file = new File(fullUsersFilePath);
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(schoolCommonFilePath);
						if(extension != null){
							if(extension.equalsIgnoreCase("wav")){
								byte[] byteArr = readWAVAudioFileData(fullUsersFilePath);
								inputStream = new ByteArrayInputStream(byteArr);
								response.setContentLength((int)byteArr.length);
							}else{
								inputStream = new FileInputStream(file);
								response.setContentLength((int)file.length());
							}
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}catch(IOException ie){}
			}
		}catch(Exception e){
			/*logger.error("Error in loadSchoolCommonFile() of CommonMainController "+ e);*/
		}
	}
	
	@RequestMapping(value = "/loadCommonFile", method = RequestMethod.GET)
	public void loadCommonFile(
			@RequestParam("commonFilePath") String commonFilePath,
			HttpServletResponse response,
			HttpSession session){
		try{
			String filePath = "";
			if(commonFilePath.toString().length() > 0){
				filePath = FileUploadUtil.getLpCommonFilesPath();
				String fullFilePath = filePath+ File.separator + commonFilePath;
				try{
					File file = new File(fullFilePath);
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(commonFilePath);
						if(extension != null){
							if(extension.equalsIgnoreCase("wav")){
								byte[] byteArr = readWAVAudioFileData(fullFilePath);
								inputStream = new ByteArrayInputStream(byteArr);
								response.setContentLength((int)byteArr.length);
							}else{
								inputStream = new FileInputStream(file);
								response.setContentLength((int)file.length());
							}
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}catch(IOException ie){}
			}
		}catch(Exception e){
		}
	}
	
	@RequestMapping(value = "/loadDirectUserFile", method = RequestMethod.GET)
	public void loadDirectUserFile(@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,HttpSession session){
		try{
			if(usersFilePath.toString().length() > 0){	
				String fullUsersFilePath = usersFilePath;
				try{
					File file = new File(fullUsersFilePath);
					if(file.exists()) {
					    InputStream inputStream;
						inputStream = new FileInputStream(new File(fullUsersFilePath));
						String extension = FilenameUtils.getExtension(usersFilePath);
						if(extension != null){
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.setContentLength((int)file.length());
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
					else if(usersFilePath.contains("?") && FilenameUtils.getExtension(usersFilePath).equals(WebKeys.WAV_FORMAT)){
							String updateFilePath = usersFilePath.replaceAll("?","???");
							File updatedFile = new File(updateFilePath);
							if(updatedFile.exists()){
								InputStream inputStream;
								inputStream = new FileInputStream(new File(fullUsersFilePath));
								String extension = FilenameUtils.getExtension(usersFilePath);
								if(extension != null){
									String contentType =  MimeTypeConstants.getMimeType(extension);
									response.setContentType(contentType);
									response.setContentLength((int)file.length());
									response.addHeader("Accept-Ranges", "bytes");
									IOUtils.copy(inputStream, response.getOutputStream());
									IOUtils.closeQuietly(response.getOutputStream());
									IOUtils.closeQuietly(inputStream);
								}
							}
						}
				}catch(IOException ie){
					ie.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in loadDirectUserFile() of CommonMainController"+ e);
		}
	}
	
	@RequestMapping(value = "/checkFileExists", method = RequestMethod.GET)
	public @ResponseBody void checkFileExists(
			@RequestParam("regId") long regId,
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session) {
		try{
			String uploadFilePath = "";
			if(usersFilePath.toString().length() > 0 && regId > 0){
				UserRegistration userReg = schooladminservice.getUserRegistration(regId);
				uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);	
				String fullUsersFilePath = uploadFilePath+ File.separator + usersFilePath;
				File file = new File(fullUsersFilePath);
				if(file.exists()) {
					response.getWriter().write(WebKeys.EXISTS);
				}else{
					response.getWriter().write(WebKeys.NOT_EXISTED);
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			}
		}catch(Exception e){
			logger.error("Error in checkFileExists() of CommonMainController"+ e);
		}
	}
	
	@RequestMapping(value = "/checkRRFileExists", method = RequestMethod.GET)
	public @ResponseBody void checkRRFileExists(
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session) {
		try{
			if(usersFilePath.toString().length() > 0){
				File file = new File(usersFilePath);
				if(file.exists()) {
					response.getWriter().write(WebKeys.EXISTS);
				}else{
					response.getWriter().write(WebKeys.NOT_EXISTED);
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			}
		}catch(Exception e){
			logger.error("Error in checkFileExists() of CommonMainController"+ e);
		}
	}
	
	@RequestMapping(value = "/checkFolderExists", method = RequestMethod.GET)
	public @ResponseBody void checkFileExists(
			@RequestParam("usersFolderPath") String usersFolderPath,
			HttpServletResponse response,
			HttpSession session) {
		try{
			if(usersFolderPath.toString().length() > 0){
				File file = new File(usersFolderPath);
				if(file.exists()) {
					response.getWriter().write(WebKeys.EXISTS);
				}else{
					response.getWriter().write(WebKeys.NOT_EXISTED);
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			}
		}catch(Exception e){
			logger.error("Error in checkFolderExists() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value= "/studentEducationalInfo", method = RequestMethod.GET)
	public ModelAndView studentEducationalInfo(@RequestParam("stdRegId") long stdRegId, HttpSession session, Model model) {
		try{
			Student	studentObj = studentService.getStudent(stdRegId);			
			session.setAttribute(WebKeys.PARENT_CHILD_OBJECT, studentObj);		
			
			model.addAttribute("page",WebKeys.EDUCATIONAL_INFO);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			List<GradeLevel> gradeLevelLt = appAdminService3.getGradeLevel();
			long userTypeId = userRegistration.getUser().getUserTypeid();
			List<Grade> gradesLt = new ArrayList<Grade>();
			List<Long> gradIdsLt = new ArrayList<Long>();
			gradIdsLt.clear();
			Map<Long,List<GradeClasses>> classesMap = new HashMap<Long,List<GradeClasses>>();
			List<RegisterForClass>  classLt = new ArrayList<RegisterForClass>();
			classLt = myProfileService.getStudentClasses(studentObj.getStudentId());
			for (RegisterForClass registerForClass : classLt) {
				if(!gradIdsLt.contains(studentObj.getGrade().getGradeId())){
					gradIdsLt.add(studentObj.getGrade().getGradeId());
					gradesLt.add(registerForClass.getGradeClasses().getGrade());
					classesMap.put(studentObj.getGrade().getGradeId(),myProfileService.getStudentGradeClasses(studentObj.getGrade().getGradeId(), studentObj.getStudentId()));
				}
			}
			model.addAttribute("stdRegId",stdRegId);
			model.addAttribute("userId",studentObj.getStudentId());
			model.addAttribute("classLt",classLt);
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("gradesLt",gradesLt);
			model.addAttribute("gradeLevelLt",gradeLevelLt);
			model.addAttribute("classesMap",classesMap);
			model.addAttribute("userTypeId",userTypeId);
			
		}catch(Exception e){
			logger.error("Error in studentEducationalInfo() of CommonMainController "+ e);
		}
		return new ModelAndView("CommonJsp/my_child_education_info");
		//return new ModelAndView("redirect:/educationalInfo.htm");
	}
	
	@RequestMapping(value = "/checkAudioExisted", method = RequestMethod.GET)
	public View checkAudioExisted(
			@RequestParam("rflpTestId") long rflpTestId,
			@RequestParam("rflpPracticeId") long rflpPracticeId,
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session,Model model){
		try{
			if(usersFilePath.toString().length() > 0 && rflpTestId > 0 && rflpPracticeId > 0){
				String rflpRecordsFilePath = usersFilePath+File.separator+WebKeys.RFLP_TEST+File.separator+rflpTestId+File.separator+rflpPracticeId+WebKeys.WAV_FORMAT;
				 File file = new File(rflpRecordsFilePath);
				if(file.exists()){
					model.addAttribute("isExisted",WebKeys.LP_TRUE);
				}else{
					model.addAttribute("isExisted",WebKeys.LP_FALSE);
				}
			}
		}catch(Exception e){
			logger.error("Error in checkAudioExisted() of CommonMainController "+ e);
		}
		return new MappingJackson2JsonView();
	}	
	
	 public byte[] readWAVAudioFileData(final String filePath){
	        byte[] data = null;
	        try {
	            final ByteArrayOutputStream baout = new ByteArrayOutputStream();
	            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
	            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, baout);
	            audioInputStream.close();
	            baout.close();
	            data = baout.toByteArray();
	        } catch (Exception e) {}
	        return data;
	 }
	 
	 @RequestMapping(value = "/loadDemoVideoFile", method = RequestMethod.GET)
	 @ResponseBody public void loadDemoVideoFile(@RequestParam("demoVideoFilePath") String demoVideoFilePath,
				HttpServletResponse response, HttpSession session){
		 	String lpTutorialPath = "";
			try{
				if(demoVideoFilePath != null){
					lpTutorialPath = FileUploadUtil.getLpCommonFilesPath();	
					String fullPath = lpTutorialPath + File.separator + demoVideoFilePath;
					File file = new File(fullPath);
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(demoVideoFilePath);
						response.reset();
						if(extension != null){
							if(extension.equalsIgnoreCase(WebKeys.VIDEO_MP4)){
								FileInputStream is = new FileInputStream(fullPath);									
						        byte [] byteArr = IOUtils.toByteArray(is);
						        inputStream = new ByteArrayInputStream(byteArr);
						        response.setContentLength((int)byteArr.length);
							}
           					String contentType =  Files.probeContentType(file.toPath());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}
			}catch(IOException ioe){
			}catch(Exception e){
				logger.error("Error in loadDemoVodeoFile() of CommonMainController "+ e);
			}
	 }
	 @RequestMapping(value = "/viewPDForImage" , method = RequestMethod.GET) 
	 public void doDownloadPDF(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException {

		   // get absolute path of the application
		   ServletContext context = request.getSession().getServletContext();
		   String appPath = context.getRealPath("");
		   // construct the complete absolute path of the file
		   String fullPath = appPath + filePath;       
		   File downloadFile = new File(filePath);
		   FileInputStream inputStream = new FileInputStream(downloadFile);

		   // get MIME type of the file
		   String mimeType = context.getMimeType(fullPath);
		   if (mimeType == null) {
		       // set to binary type if MIME mapping not found
		       mimeType = "application/"+mimeType;
		   }
		   response.setContentType(mimeType);
		   // get output stream of the response
		   OutputStream outStream = response.getOutputStream();
		   byte[] buffer = new byte[1024];
		   int bytesRead = -1;
		   // write bytes read from the input stream into the output stream
		   while ((bytesRead = inputStream.read(buffer)) != -1) {
		       outStream.write(buffer, 0, bytesRead);
		   }
		   inputStream.close();
		   outStream.close();
   }
	 
		
	@RequestMapping(value = "/checkParentSignExists", method = RequestMethod.GET)
	public @ResponseBody void checkParentSignExists(
			@RequestParam("regId") long regId,
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session) {
		try{
			if(usersFilePath.toString().length() > 0 && regId > 0){
				UserRegistration userReg = schooladminservice.getUserRegistration(regId);
				String folderPath = FileUploadUtil.getUploadFilesPath(userReg, request);
				String fullUsersFilePath = folderPath+ File.separator + usersFilePath;
				File file = new File(fullUsersFilePath);
				if(file.exists()) {
					response.getWriter().write(fullUsersFilePath);
				}else{
					response.getWriter().write("");
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			   
			}
		}catch(Exception e){
			logger.error("Error in checkParentSignExists() of GradePhonicSkillTestController "+ e);
		}
	}
		@RequestMapping(value = "/checkProfilePictureExists", method = RequestMethod.GET)
		public @ResponseBody void checkParentSignExists1(
				@RequestParam("regId") long regId,
				@RequestParam("usersFilePath") String usersFilePath,
				HttpServletResponse response,
				HttpSession session) {
			try{
				if(usersFilePath.toString().length() > 0 && regId > 0){
				UserRegistration userReg = schooladminservice.getUserRegistration(regId);
				String folderPath = FileUploadUtil.getProfilePicturePath(userReg);
				String fullUsersFilePath = folderPath+ File.separator + WebKeys.PROFILE_PIC_FILE_NAME;
				File file = new File(fullUsersFilePath);
				if(file.exists()) {
					response.getWriter().write(fullUsersFilePath);
				}else{
					response.getWriter().write("");
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			   
			}
		}catch(Exception e){
			logger.error("Error in checkParentSignExists() of GradePhonicSkillTestController "+ e);
		}
	}
	
	@RequestMapping(value = "/checkProfilePicExists", method = RequestMethod.GET)
	public @ResponseBody void checkProfilePicExists(
			@RequestParam("regId") long regId,
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletResponse response,
			HttpSession session) {
		try{
			if(usersFilePath.toString().length() > 0 && regId > 0){
				UserRegistration userReg = schooladminservice.getUserRegistration(regId);
				String folderPath = FileUploadUtil.getProfilePicturePath(userReg);
				String fullUsersFilePath = folderPath+ File.separator + usersFilePath;
				File file = new File(fullUsersFilePath);
				if(file.exists()) {
					response.getWriter().write(fullUsersFilePath);
				}else{
					response.getWriter().write("");
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			   
			}
		}catch(Exception e){
			logger.error("Error in checkProfilePicExists() of CommonMainController "+ e);
		}
	}
	
	@RequestMapping(value = "/uploadProfilePic", headers="content-type=multipart/form-data",method=RequestMethod.POST )
	public @ResponseBody void uploadProfilePic(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			boolean flag = fileservice.uploadProfilePic(map);
			if(flag){
				response.getWriter().write( WebKeys.PROFILE_PIC_UPLOAD_SUCCESS);  
			}	
			else{
				response.getWriter().write( WebKeys.PROFILE_PIC_UPLOAD_FAILED);  
			}
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html");  
		}
		catch(Exception e){
			logger.error("Error while redirecting to the page");
		}
	}
	
	@RequestMapping(value = "/deleteProfileImage", method=RequestMethod.POST )
	public @ResponseBody void deleteProfileImage(
			@RequestParam("regId") long regId,
			@RequestParam("usersFilePath") String usersFilePath,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try{	
			if(usersFilePath.toString().length() > 0 && regId > 0){
				UserRegistration userReg = schooladminservice.getUserRegistration(regId);
				String folderPath = FileUploadUtil.getProfilePicturePath(userReg);
				String fullUsersFilePath = folderPath+ File.separator + usersFilePath;
				File file = new File(fullUsersFilePath);
				if(file.exists()) {
					file.delete();
					response.getWriter().write( WebKeys.PROFILE_PIC_DELETED_SUCCESS);  
				}else{
					response.getWriter().write( WebKeys.PROFILE_PIC_DELETED_FAILED);  
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			}
		}
		catch(Exception e){
			logger.error("Error while redirecting to the page");
		}
	}
	
	@RequestMapping(value= "/getSignatureContent", method = RequestMethod.GET)
	public ModelAndView getSignatureContent(@RequestParam("page") String page,@RequestParam("studentId") long studentId,
			@RequestParam("gradeId") long gradeId,@RequestParam("trimesterId") long trimesterId,
			HttpSession session, Model model) {
		model.addAttribute("page", page);
		model.addAttribute("studentId", studentId);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("trimesterId", trimesterId);
		return new ModelAndView("Ajax/CommonJsp/signature");
	}
	
	
	@RequestMapping(value = "/saveSignatureContent", method = RequestMethod.POST)
	public @ResponseBody void saveSignatureContent(HttpSession session,Map<String, Object> map,HttpServletResponse response, HttpServletRequest request) {
		 try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			String imageContent = request.getParameter("imageContent");
			String page = request.getParameter("page");
			long studentId = Long.parseLong(request.getParameter("studentId"));
			long gradeId = Long.parseLong(request.getParameter("gradeId"));
			long trimesterId = Long.parseLong(request.getParameter("trimesterId"));
			byte[] bis = Base64.decodeBase64(imageContent.toString());
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			String signaturePath = "";
			FileOutputStream fos = null;
			signaturePath = uploadFilePath+File.separator+WebKeys.SIGN_PATH+File.separator+page+File.separator+gradeId+File.separator+studentId+File.separator+trimesterId;
		  	  try{
		  		  File file = new File(signaturePath);
		  		  if(!file.exists()) 
		  			 file.mkdirs();
		  			File f = new File(signaturePath+File.separator+"sign.png");
		  			if(f.exists()) 
		  				f.delete();
		  			synchronized(bis) {
		  				fos = new FileOutputStream(f, true); 
		  				fos.write(bis);
		  			}
		  			response.getWriter().write(f.getPath());  
			    } catch (Exception e) {
			    	response.getWriter().write("");  
		             e.printStackTrace();
		        } finally{
		        	  fos.close();
		        }
		   response.setCharacterEncoding("UTF-8");  
		   response.setContentType("text/html");  
		   
		 } catch (Exception e) {
             e.printStackTrace();
        }
	}
	
	// Organization of LPUserFile system based on School and Academic Year.
	/*@RequestMapping(value = "/moveFolderToDestination", method = RequestMethod.GET)
	public @ResponseBody void moveFolderToDestination(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		 try{
		   List<School> schoolLt = schoolAdminService.getSchools();
		   for (School school : schoolLt) { 		   
			   List<UserRegistration> adminLt = adminservice.getAllUserRegistrationsBySchool(school, 2);
			   for (UserRegistration userReg : adminLt) {
				   String desFolderPath = FileUploadUtil.getUpdatedFolderPath(userReg, request);
				   String srcFolderPath = FileUploadUtil.getUploadFilesPath(userReg, request);
				   File srcfile = new File(srcFolderPath);
				   File desfile = new File(desFolderPath);
				   if(srcfile.exists()){
					   fileservice.moveFolderToDestination(srcfile, desfile);
				   }
				   
				   String srcProfilePath = srcFolderPath+ File.separator + WebKeys.PROFILE_PIC_FOLDER;
				   String desProfilePath = FileUploadUtil.getUpdatedProfilePicturePath(userReg);
				   File srcProfile = new File(srcProfilePath);
				   File desProfile = new File(desProfilePath);
				   if(srcProfile.exists()){
					   fileservice.moveFolderToDestination(srcProfile, desProfile);
				   }
			   }
			   
			   List<UserRegistration> teachersLt = adminservice.getAllUserRegistrationsBySchool(school, 3);
			   for (UserRegistration userReg : teachersLt) {
				   String desFolderPath = FileUploadUtil.getUpdatedFolderPath(userReg, request);
				   String srcFolderPath = FileUploadUtil.getUploadFilesPath(userReg, request);
				   File srcfile = new File(srcFolderPath);
				   File desfile = new File(desFolderPath);
				   if(srcfile.exists()){
					   fileservice.moveFolderToDestination(srcfile, desfile);
				   }
				   
				   String srcProfilePath = srcFolderPath+ File.separator + WebKeys.PROFILE_PIC_FOLDER;
				   String desProfilePath = FileUploadUtil.getUpdatedProfilePicturePath(userReg);
				   File srcProfile = new File(srcProfilePath);
				   File desProfile = new File(desProfilePath);
				   if(srcProfile.exists()){
					   fileservice.moveFolderToDestination(srcProfile, desProfile);
				   }
			   }
			   List<UserRegistration> parentsLt = adminservice.getAllUserRegistrationsBySchool(school, 4);
			   for (UserRegistration userReg : parentsLt) {
				   String desFolderPath = FileUploadUtil.getUpdatedFolderPath(userReg, request);
				   String srcFolderPath = FileUploadUtil.getUploadFilesPath(userReg, request);
				   File srcfile = new File(srcFolderPath);
				   File desfile = new File(desFolderPath);
				   if(srcfile.exists()){
					   fileservice.moveFolderToDestination(srcfile, desfile);
				   }
				   
				   String srcProfilePath = srcFolderPath+ File.separator + WebKeys.PROFILE_PIC_FOLDER;
				   String desProfilePath = FileUploadUtil.getUpdatedProfilePicturePath(userReg);
				   File srcProfile = new File(srcProfilePath);
				   File desProfile = new File(desProfilePath);
				   if(srcProfile.exists()){
					   fileservice.moveFolderToDestination(srcProfile, desProfile);
				   }
			   }
			   
			   List<UserRegistration> userRegLt = adminservice.getStudentsBySchoolId(school.getSchoolId());
			   for (UserRegistration userReg : userRegLt) {
				   String desFolderPath = FileUploadUtil.getUpdatedFolderPath(userReg, request);
				   String srcFolderPath = FileUploadUtil.getUploadFilesPath(userReg, request);
				   File srcfile = new File(srcFolderPath);
				   File desfile = new File(desFolderPath);
				   if(srcfile.exists()){
					   fileservice.moveFolderToDestination(srcfile, desfile);
				   }
				   
				   String srcProfilePath = srcFolderPath+ File.separator + WebKeys.PROFILE_PIC_FOLDER;
				   String desProfilePath = FileUploadUtil.getUpdatedProfilePicturePath(userReg);
				   File srcProfile = new File(srcProfilePath);
				   File desProfile = new File(desProfilePath);
				   if(srcProfile.exists()){
					   fileservice.moveFolderToDestination(srcProfile, desProfile);
				   }
			   }
			   
			   String desFolderPath = FileUploadUtil.getUpdatedCommonSchoolFilesPath(school);
			   String srcFolderPath = FileUploadUtil.getSchoolCommonFilesPath(school, request);
			   File srcfile = new File(srcFolderPath);
			   File desfile = new File(desFolderPath);
			   if(srcfile.exists()){
				   fileservice.moveFolderToDestination(srcfile, desfile);
			   }
			   
		   }
		   response.setCharacterEncoding("UTF-8");  
		   response.setContentType("text/html");  
		   response.getWriter().write("success");
		 } catch (Exception e) {
             e.printStackTrace();
        }
	}*/
	@RequestMapping(value = "/loadCommonAudioFile", method = RequestMethod.GET)
	public void loadCommonAudioFile(
			@RequestParam("commonFilePath") String commonFilePath,
			HttpServletResponse response,
			HttpSession session){
		try{
			String filePath = "";
			if(commonFilePath.toString().length() > 0){
				filePath = FileUploadUtil.getLpCommonFilesPath();
				String fullFilePath = filePath + File.separator + commonFilePath;
				try{
					File file = new File(fullFilePath);
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(commonFilePath);
						if(extension != null){
							if(extension.equalsIgnoreCase("wav") || extension.equalsIgnoreCase("jpg")){
								byte[] byteArr = readWAVFileData(fullFilePath);
								inputStream = new ByteArrayInputStream(byteArr);
								response.setContentLength((int)byteArr.length);
							}else{
								inputStream = new FileInputStream(file);
								response.setContentLength((int)file.length());
							}
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}catch(IOException ie){}
			}
		}catch(Exception e){
		}
	}
	@RequestMapping(value = "/loadCommonHtmlFile", method = RequestMethod.GET)
	public void loadCommonHtmlFile(
			@RequestParam("commonFilePath") String commonFilePath,
			HttpServletResponse response,
			HttpSession session){
		try{
			String filePath = "";
			if(commonFilePath.toString().length() > 0){
				filePath = FileUploadUtil.getLpCommonFilesPath();
				String fullFilePath = filePath + File.separator + commonFilePath;
				try{
					File file = new File(commonFilePath);
					InputStream inputStream = null;
					if(file.exists()) {
						String extension = FilenameUtils.getExtension(commonFilePath);
						if(extension != null){
							if(extension.equalsIgnoreCase("wav") || extension.equalsIgnoreCase("html")){
								byte[] byteArr = readWAVFileData(commonFilePath);
								inputStream = new ByteArrayInputStream(byteArr);
								response.setContentLength((int)byteArr.length);
								
							}else{
								inputStream = new FileInputStream(file);
								response.setContentLength((int)file.length());
							}
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							response.setContentType(contentType);
							response.addHeader("Accept-Ranges", "bytes");
							IOUtils.copy(inputStream, response.getOutputStream());
							IOUtils.closeQuietly(response.getOutputStream());
							IOUtils.closeQuietly(inputStream);
						}
					}
				}catch(IOException ie){}
			}
		}catch(Exception e){
		}
	}
	
	@SuppressWarnings("resource")
	public byte[] readWAVFileData(final String filePath){
        byte[] audioBytes = null;
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            File file = new File(filePath);            
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0)
            {
                out.write(buff, 0, read);
            }
            out.flush();
            audioBytes = out.toByteArray();
        } catch (Exception e) {}
        return audioBytes;
	}
	
	@RequestMapping(value= "/personalInfo", method = RequestMethod.GET)
	public ModelAndView personalInfo(HttpSession session, Model model) {
		try{
			model.addAttribute("page",WebKeys.PERSONAL_INFO);
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			List<Country> countryLt = appAdminService2.getCountries();
			List<States>  statesLt = schoolAdminService.getStates(userRegistration.getAddress().getStates().getCountry().getCountryId());
			model.addAttribute("userRegistration",userRegistration);
			model.addAttribute("countryLt", countryLt);
			model.addAttribute("statesLt", statesLt);
			model.addAttribute("userRegistrationForm",userRegistration);
		}catch(Exception e){
			logger.error("Error in personalInformation() of CommonMainController "+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/personal_info");
	}
}
