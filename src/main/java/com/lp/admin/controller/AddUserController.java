package com.lp.admin.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.model.School;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class AddUserController {
	@Autowired
	private CommonService reportService;
	@Autowired
	private AdminService adminservice;
	@Autowired
	private  AppAdminService  appAdminService;
	@Autowired
	private  UserDAO userdao;
	@Autowired
	private  SchoolAdminService schoolAdminService;
	
	static final Logger logger = Logger.getLogger(AddUserController.class);
	
	@RequestMapping(value = "/AddUsers", method = RequestMethod.GET)
	public ModelAndView AddUsers(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/AdminAddUsers","user",new UserRegistration());
		return model; 
	}
		
	@RequestMapping(value = "/AdminAddStudent", method = RequestMethod.GET)
	public @ResponseBody  
    void AdminAddStudent(HttpServletResponse response,HttpSession session, @ModelAttribute("user") UserRegistration user,
			BindingResult result,@RequestParam("semailId") String semailId, @RequestParam("pemailId") String pemailId, 
			@RequestParam("studentage") String usertype) throws Exception {
		String helloAjax = "";
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId = userReg.getRegId();
			long studentTypeId = 0, parentRegId = 0;
			School school = reportService.getSchoolIdByRegId(adminRegId);
			user.setSchool(school);
			
			if(usertype.equals("below")) {
				user.setUser(userdao.getUserType("Student Below 13"));
	            studentTypeId = user.getUser().getUserTypeid();
	            user.setUser(appAdminService.getUserType(studentTypeId));
			}else if (usertype.equals("above")) {
				user.setUser(userdao.getUserType("Student Above 13"));
	            studentTypeId = user.getUser().getUserTypeid();
	            User users = appAdminService.getUserType(studentTypeId);
	            user.setUser(users);
	        }
			parentRegId = reportService.getregId(pemailId);
			long usertypeid = reportService.getUserType(pemailId);
			long Pusertypeid = reportService.getUserTypeId("Parent");
			UserRegistration userRegistration = reportService.getUserRegistrationBySchool(semailId, school.getSchoolId());
			if (parentRegId != 0) {
				if(usertypeid == Pusertypeid){
					user.setEmailId(semailId);
					user.setParentRegId(parentRegId);
					long sturegId = reportService.getNewORActiveUserRegistration(semailId).getRegId();
					if(sturegId == 0 && userRegistration.getRegId() ==0){
						adminservice.addStudent(user);
						helloAjax = "Added Student Successfully";
					}
					else{
						helloAjax = "User already Exists with the email";
					}
				}else{
					helloAjax = "You Enter Parent EmailId Already Taken by Another User";
				}							
	        }else{
	        	UserRegistration parentUser =new UserRegistration();          	 
	        	parentUser.setEmailId(pemailId);
	        	parentUser.setSchool(user.getSchool());
	        	adminservice.addParent(parentUser);
	        	user.setEmailId(semailId);
	            parentRegId = parentUser.getRegId();
	            user.setParentRegId(parentRegId);
	            user.setUser(appAdminService.getUserType(studentTypeId)); 
	            long sturegId = reportService.getNewORActiveUserRegistration(semailId).getRegId();               
	            if(sturegId == 0 && userRegistration.getRegId() == 0){
	            	adminservice.addStudent(user);
	            	helloAjax = "Added Student Successfully";
				}else{
					helloAjax = "User already Exists with the email";
				}
	        }
		}
		catch(Exception e){
			logger.error("Error in AdminAddStudent() of AddUserController" +e);
		}
		response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html");  
        response.getWriter().write(helloAjax);  
	}
	
	@RequestMapping(value = "/AdminAddTeacher", method = RequestMethod.GET)
	public @ResponseBody  
    void AdminAddTeacher(HttpServletResponse response,HttpSession session,@ModelAttribute("user") UserRegistration user,
			BindingResult result,@RequestParam("emailId") String emailId) throws Exception { 
		String helloAjax = "";
		try{
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId = userReg.getRegId();
			School school = reportService.getSchoolIdByRegId(adminRegId);
			user.setSchool(school);
			long teacherId = reportService.getNewORActiveUserRegistration(emailId).getRegId();
			UserRegistration userRegistration = reportService.getUserRegistrationBySchool(emailId, school.getSchoolId());			
			if(teacherId == 0  && userRegistration.getRegId() == 0){
				adminservice.addTeacher(user);
				helloAjax = "Added Teacher Successfully";
			}
			else{
			   helloAjax = "EmailId already Exists";
			}  
		}
		catch(Exception e){
			logger.error("Error in AdminAddTeacher() of AddUserController" +e);
		}
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html");  
	    response.getWriter().write(helloAjax);
	}
	@RequestMapping(value = "/AdminDeleteUser", method = RequestMethod.GET)
	public @ResponseBody  
    void AdminDeleteUser(HttpServletResponse response,HttpSession session,@ModelAttribute("user") UserRegistration user,
			BindingResult result,@RequestParam("emailId") String emailId) throws Exception { 
		String helloAjax = "";
		try{
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			UserRegistration delUser=reportService.getNewORActiveUserRegistration(emailId);
			long userRegistrationId = delUser.getRegId();
			if(userRegistrationId == 0){
				helloAjax = "User Does Not Exists";
			}else{
				if(userReg.getSchool().getSchoolId()== delUser.getSchool().getSchoolId()){
					long status = schoolAdminService.deleteUserRegistration(userRegistrationId);
					if(status > 0){
						helloAjax = "User Deleted Successfully";
					}
					else{
						helloAjax = "User Not Deleted Successfully";
					}
				} else{
					helloAjax="Not a valid user";
				}
			}
		}
		catch(Exception e){
			logger.error("Error in AdminDeleteUser() of AddUserController" +e);
		}
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html");  
	    response.getWriter().write(helloAjax);  
	}
	@RequestMapping(value = "/AdminAddParent", method = RequestMethod.GET)
	public @ResponseBody  
    void AdminAddParent(HttpServletResponse response,HttpSession session,@ModelAttribute("user") UserRegistration user,
			BindingResult result,@RequestParam("semailId") String semailId,@RequestParam("pemailId") String pemailId) throws Exception {
		String helloAjax = "";
		try{
			UserRegistration userReg=(UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId = userReg.getRegId();
			long parentRegId = 0;
			School school = reportService.getSchoolIdByRegId(adminRegId);
			user.setSchool(school);
			UserRegistration parentUser = new UserRegistration();          	 
	        parentUser.setEmailId(pemailId);
	        parentUser.setSchool(user.getSchool());
	        long stuId = reportService.getNewORActiveUserRegistration(semailId).getRegId();
	        long parentId = reportService.getNewORActiveUserRegistration(pemailId).getRegId();
			UserRegistration userRegis = reportService.getUserRegistrationBySchool(pemailId, school.getSchoolId());	
			if(stuId != 0){
				if(parentId == 0  && userRegis.getRegId() == 0){
					adminservice.addParent(parentUser);
					parentRegId = parentUser.getRegId();
					boolean stat = adminservice.setParentToStudent(parentRegId,semailId);
					if(stat)
						helloAjax = "Added Parent Successfully";
				}else{
					helloAjax = "User already Exists with the email";
				}
			}else{
				helloAjax = "This Student Not Available";
			}
		}
		catch(Exception e){
			logger.error("Error in AdminAddParent() of AddUserController" +e);
		}
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html");  
        response.getWriter().write(helloAjax);  		
	}
}