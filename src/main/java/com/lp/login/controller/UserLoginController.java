package com.lp.login.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.admin.service.AnnouncementsNEventsService;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.appadmin.service.NewsFeedService;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.DashboardService;
import com.lp.custom.exception.DataException;
import com.lp.custom.springsecurity.CustomUserDetailsService;
import com.lp.custom.springsecurity.SessionContext;
import com.lp.model.AcademicYear;
import com.lp.model.Announcements;
import com.lp.model.Attendance;
import com.lp.model.NotificationStatus;
import com.lp.model.ParentLastseen;
import com.lp.model.ReadRegMaster;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentReadRegService;
import com.lp.teacher.service.TeacherCommonService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

//@Author : PRASADBHVN DT: 19/03/2015 

@Controller
public class UserLoginController extends WebApplicationObjectSupport{

	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private TeacherCommonService teacherCommonService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AnnouncementsNEventsService announcementsNEventsService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private ParentService parentService;
	@Autowired
	private SchoolAdminService schooladminservice;
	@Autowired
	private NewsFeedService newsFeedService;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private StudentReadRegService studentReadRegService;
	@Autowired
	private UserRegistrationDAO userRegDAO;

	static final Logger logger = Logger.getLogger(UserLoginController.class);

	@RequestMapping(value = "/loginUser", method = RequestMethod.GET)
	public ModelAndView loginUser(HttpSession session) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		UserRegistration userReg = schoolAdminService.getUserRegistration(userName);
		if (userReg != null) {
			session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
			session.setAttribute(WebKeys.PREVIOUS_AUTH_USER, null); 	
		}		
		return new ModelAndView("redirect:/gotoDashboard.htm");
	}

	@RequestMapping(value = "/loginFail", method = RequestMethod.GET)
	public ModelAndView loginFail(HttpSession session) {
		session.invalidate();
		return new ModelAndView("login/LoginFail");
	}
	
	@RequestMapping(value = "/gmailNotRegistredWithLP", method = RequestMethod.GET)
	public ModelAndView gmailNotRegistredWithLP(HttpSession session) {
		session.invalidate();
		return new ModelAndView("login/gmail_not_registred_with_LP");
	}

	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if(session != null && session.getAttribute(WebKeys.USER_REGISTRATION_OBJ) != null){
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String userType = userReg.getUser().getUserType().toLowerCase();
			if (userType.equals(WebKeys.LP_USER_TYPE_PARENT)) {
				ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
				parentLastseen.setLastLoggedOut(new Date());
				parentService.saveParentLastSeen(parentLastseen);
			}
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null){    
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			//session.invalidate();
		}
		return new ModelAndView("redirect:/index.htm");

	}

	@RequestMapping(value = "/auth2callback",  method = RequestMethod.GET)
	 public ModelAndView auth2callback(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws AuthenticationException, DataException {
		 String code =  request.getParameter("code");
	     String path =  "code="+code +
		                "&client_id=" +WebKeys.G_CLIENT_ID+
		                "&client_secret=" +WebKeys.G_CLIENT_SECRET+
		                "&redirect_uri="+WebKeys.G_REDIRECT_URI+
		                "&grant_type="+WebKeys.G_GRANT_TYPE;
		    HttpClient client = new HttpClient();
		    PostMethod post = new PostMethod(WebKeys.G_URL);
		    post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    try {
		        post.setRequestEntity(new StringRequestEntity(path, null, null));
		    } catch (UnsupportedEncodingException e) {
		        throw new RuntimeException(e);
		    }
		    String accessToken = null;
		    try {
		        client.executeMethod(post);
		        String resp = post.getResponseBodyAsString();
		        JSONParser jsonParser = new JSONParser();
		        Object obj = jsonParser.parse(resp);
		        JSONObject parsed = (JSONObject)obj;            
		        accessToken = (String) parsed.get(WebKeys.G_ACCESS_TOKEN);
		    } catch (HttpException e) {
		        throw new RuntimeException(e);
		    } catch (IOException e) {
		        throw new RuntimeException(e);
		    } catch (ParseException e) {
		        throw new RuntimeException(e);
		    }		    
		    GetMethod getUserInfo = new GetMethod(WebKeys.G_PATH_GET_METHOD+"?access_token="+accessToken);
		    String email = null;
		    String picture = null;
		    try {
		        client.executeMethod(getUserInfo);
		        String resp = getUserInfo.getResponseBodyAsString();
		        JSONParser jsonParser = new JSONParser();
		        Object obj = jsonParser.parse(resp);
		        JSONObject parsed = (JSONObject)obj;
		        email = (String) parsed.get("email");
		        picture = (String) parsed.get("picture");
		        
			    final UserDetails userDetails = customUserDetailsService.checkSSOUserAuthentication(email);
			    if(userDetails != null){
			        UserRegistration userReg = userRegDAO.getLoginUserRegistration(userDetails.getUsername());
			        if (userReg != null) {
						session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
			        	if(picture.length() > 0){
					        try(InputStream in = new URL(picture).openStream()){
					        	String folderPath = FileUploadUtil.getProfilePicturePath(userReg);
					        	File newDir = new File(folderPath);
								if(!newDir.isDirectory()){
									newDir.mkdirs();
								}
								String fullPath = folderPath+File.separator+WebKeys.PROFILE_PIC_FILE_NAME;
								File file = new File(fullPath);
								if(!file.exists()) {
									Files.copy(in, Paths.get(fullPath));
								}
					        }catch(Exception e) {
					        	e.printStackTrace();
					        }
					     }
			        	return new ModelAndView("redirect:/gotoDashboard.htm");
					}else{
						return new ModelAndView("redirect:/gmailNotRegistredWithLP.htm");	
					} 
			    }else{
			    	return new ModelAndView("redirect:/gmailNotRegistredWithLP.htm");	
			    }
		    } catch (IOException e) {
		        throw new RuntimeException(e);
		    } catch (ParseException e) {
		        throw new RuntimeException(e);
		    }
	    }	
	
	@RequestMapping(value = "/gotoDashboard", method = RequestMethod.GET)
	public ModelAndView gotoDashboard(HttpSession session) {
		SessionContext sc = SessionContext.getSessionContext();
		UserRegistration userReg = null;
		String userType = null;
		if (session.getAttribute(WebKeys.USER_REGISTRATION_OBJ) != null) {
			userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			userType = userReg.getUser().getUserType().toLowerCase();
		}
		ModelAndView model = null;
		if (userType != null) {	
			List<AcademicYear> academicYears = commonService.getSchoolAcademicYears();
			session.setAttribute("academicYears", academicYears);
			userReg = schooladminservice.getUserRegistration(userReg.getRegId());
			session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
			long notificationLength = (long) 0;
			if (userType.equals(WebKeys.LP_USER_TYPE_APP_MANAGER)) {
				model = new ModelAndView("AppManager/AppManager");
			} else if (userType.equals(WebKeys.LP_USER_TYPE_ADMIN)) {
				model = new ModelAndView("Admin/admin_desktop");
				model.addObject("schoolGrades", adminService.getSchoolGrades(userReg.getSchool().getSchoolId()));
				model.addObject("teacherEmails", dashboardService.getTeachersBySchoolId(userReg.getSchool()));
				model.addObject("promoStartDate", userReg.getSchool().getPromotStartDate());
				model.addObject("promoEndDate", userReg.getSchool().getPromotEndDate());
				model.addObject("page","adminDesktop");
				model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
				List<TeacherSubjects> teacherRequests = dashboardService.getTeacherRequestsBySchool(userReg.getSchool());
				session.setAttribute("teacherRequests", teacherRequests);
				notificationLength+=teacherRequests.size();
				session.setAttribute("promoStartDate", userReg.getSchool().getPromotStartDate());
				session.setAttribute("promoEndDate", userReg.getSchool().getPromotEndDate());
				Date date = new Date();
				if(userReg.getSchool().getPromotStartDate() != null && userReg.getSchool().getPromotEndDate() != null && date.after(userReg.getSchool().getPromotStartDate()) && date.before(userReg.getSchool().getPromotEndDate())) {					
					notificationLength+=1;
				}
			} else if (userType.equals(WebKeys.LP_USER_TYPE_TEACHER)) {
				notificationLength = (long) 0;
				long teaLitracyNotifyLength= (long) 0;
				Teacher teacherObj = teacherCommonService.getTeacher(userReg);
				session.setAttribute(WebKeys.TEACHER_OBJECT, teacherObj);
				model = new ModelAndView("Teacher/teacher_desktop");
				model.addObject("classes", dashboardService.getTeacherClasses(teacherObj));
				model.addObject("tests", dashboardService.getAssignedTests(teacherObj));
				model.addObject("page","teacherDesktop");
				model.addObject("LP_TAB_STEM100",WebKeys.LP_TAB_STEM100);
				List<RegisterForClass> studentRequests = dashboardService.getStudentRequestsforClass(teacherObj);
				session.setAttribute("studentRequests", studentRequests);
				notificationLength+=studentRequests.size();
				List<Report> parentComments = dashboardService.getParentComments(teacherObj);
				session.setAttribute("parentComments", parentComments);
				notificationLength+=parentComments.size();
				List<StudentAssignmentStatus> testsTobeGraded = dashboardService.getTestsToBeGraded(teacherObj);
				session.setAttribute("testToBeGraded", testsTobeGraded);
				if(testsTobeGraded.size() > 0 ){
				notificationLength+=1;
				teaLitracyNotifyLength+=1;
				}
				session.setAttribute("litracyNotifyLength", teaLitracyNotifyLength);
			} else if (userType.equals(WebKeys.LP_USER_TYPE_PARENT)) {
				notificationLength = (long) 0;
				ParentLastseen parentLastseen = parentService.getParentLastseen(userReg);
				parentLastseen.setParentId(userReg);
				parentLastseen.setLastLoggedIn(new Date());
				parentLastseen.setLastSeenFeature("---");
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, new HashSet<String>());
				model = new ModelAndView("Parent/parent_desktop");
				List<RegisterForClass> teacherResponsesforClassRequests = dashboardService.getTeacherResponseforChildRequests(userReg);
				model.addObject("teacherResponsesforClassRequests", teacherResponsesforClassRequests );
				notificationLength+=teacherResponsesforClassRequests.size();
				List<Attendance> attendanceList = dashboardService.getStudentAttendance(userReg);
				model.addObject("attendanceList", attendanceList);	
				notificationLength+=attendanceList.size();
				model.addObject("promoEndDate", userReg.getSchool().getPromotEndDate());
				session.setAttribute("children", parentService.getStudentByParent(userReg.getRegId()));
				model.addObject("page","parentDesktop");
			} else if (userType.equals(WebKeys.LP_USER_TYPE_STUDENT_ABOVE_13)
					|| userType.equals(WebKeys.LP_USER_TYPE_STUDENT_BELOW_13)) {
				notificationLength = (long) 0;
				long readRegNotifyLength = (long) 0;
				long litracyNotifyLength= (long) 0;
				
				Student studentObj = commonService.getStudent(userReg);
				session.setAttribute(WebKeys.STUDENT_OBJECT, studentObj);
				model = new ModelAndView("Student/student_desktop");
				model.addObject("classes", dashboardService.getStudentClasses(studentObj));
				model.addObject("tests", dashboardService.getStudentAssignedTests(studentObj));
				model.addObject("promoEndDate", userReg.getSchool().getPromotEndDate());
				model.addObject("LP_TAB_STEM100",WebKeys.LP_TAB_STEM100);
				List<RegisterForClass> teacherResponses = dashboardService.getTeacherResponseforStudentRequests(studentObj);
				session.setAttribute("teacherResponses", teacherResponses);
				notificationLength+=teacherResponses.size();
				boolean testsFlag = dashboardService.getStudentRTITests(studentObj);
				session.setAttribute("rtiTests", testsFlag);
				model.addObject("rrTtlPtsEarned", studentReadRegService.getStudentTotalPointsEarned(studentObj.getStudentId(), studentObj.getGrade().getGradeId()));
				if(testsFlag){
					notificationLength+=1;
					litracyNotifyLength+=1;
				}
				List<ReadRegMaster> lstRejectedBook=studentReadRegService.getRejectedBooksByRegId(studentObj.getUserRegistration().getRegId(), studentObj.getUserRegistration().getSchool().getAcademicYear());
				if(lstRejectedBook.size()>0){
					notificationLength+=1;
					readRegNotifyLength+=1;
				}
				model.addObject("rejectedBooks",lstRejectedBook.size());
				model.addObject("page","studentDesktop");
				session.setAttribute("readRegNotifyLength", readRegNotifyLength);
				session.setAttribute("litracyNotifyLength", litracyNotifyLength);
			}else{
				return new ModelAndView("login/LoginFail");
			}
			
			model.addObject("lpNews", newsFeedService.getLPNews());
			model.addObject("userReg", userReg);
			try{
				if(userReg.getSchool() != null){
					if (!(userType.equals(WebKeys.LP_USER_TYPE_ADMIN) || userType.equals(WebKeys.LP_USER_TYPE_APP_MANAGER))) {
						List<NotificationStatus> notificationStatusLt = announcementsNEventsService.getCurrentAnnouncementsBySchool(userReg.getSchool());
						session.setAttribute("notificationStatusLt", notificationStatusLt);
						notificationLength+=notificationStatusLt.size();
					}else{
						List<Announcements> announcements = announcementsNEventsService.getAnnouncementsBySchoolAdmin(userReg.getSchool());
						model.addObject("announcements", announcements);
					}
					
					
					model.addObject("events", announcementsNEventsService.getCurrentEventsBySchool(userReg.getSchool()));
				}
				session.setAttribute("notificationLength", notificationLength);
				
				
				model.addObject("userRegistration", userReg);			
			}catch(DataException e){
				logger.error("Error in gotoDashboard() of UserLoginController"+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			if(sc.getSignIn() != null && sc.getSignIn().equalsIgnoreCase("yes"))
				model.addObject("SC", sc);		

			return model;	
		}
		return new ModelAndView("redirect:/index.htm");		
	}
	@RequestMapping(value = "/getStudentsByGrade", method = RequestMethod.GET)
	public View getTeacherClasses(@RequestParam("gradeId") long gradeId,
		Model model, HttpSession session) throws Exception {
		long startTime = System.currentTimeMillis();
		model.addAttribute("students",dashboardService.getStudentsByGrade(gradeId));
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		logger.info("getStudentsByGrade elapsedTime = " +elapsedTime);
		return new MappingJackson2JsonView();
		
	}
	@RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
	public ModelAndView favicon(HttpSession session) {		
		return new ModelAndView("redirect:/gotoDashboard.htm");

	}
	
	 @RequestMapping(value = "/authSwitchUser",  method = RequestMethod.POST)
	 public ModelAndView authSwitchUser(HttpSession session, HttpServletRequest request){
		 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 String previousAuthUser = userReg.getEmailId();		 
		 String currentAuthUser =  request.getParameter("j_username");
		 if(currentAuthUser == null){
			 if(session.getAttribute(WebKeys.PREVIOUS_AUTH_USER).toString() != null){
				 currentAuthUser = session.getAttribute(WebKeys.PREVIOUS_AUTH_USER).toString();			
				 customUserDetailsService.switchUserAuthentication(currentAuthUser);
				 session.setAttribute(WebKeys.PREVIOUS_AUTH_USER, null); 	
			 }else{
				 customUserDetailsService.switchUserAuthentication(previousAuthUser);
			 }
		 }else if(previousAuthUser != null){
			 UserDetails userDetails = customUserDetailsService.switchUserAuthentication(currentAuthUser);
			 if(userDetails != null){
				 session.setAttribute(WebKeys.PREVIOUS_AUTH_USER, previousAuthUser); 			 
			 }
		 }		
		 return new ModelAndView("redirect:/switchUser.htm");
	 }	
 
}