package com.lp.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.service.AddStudentsToClassService;
import com.lp.custom.exception.DataException;
import com.lp.model.RegisterForClass;
import com.lp.model.Teacher;
import com.lp.utils.WebKeys;

@Controller
public class AddStudentsToClassController {
	
	@Autowired
	private AdminSchedulerDAO adminSchedulerdao;
	@Autowired
	private AddStudentsToClassService addStudentsToClassService;
	
	static final Logger logger = Logger.getLogger(AddStudentsToClassController.class);
	
	@RequestMapping(value = "/loadStudentList", method = RequestMethod.GET)
	public ModelAndView getStudentList( HttpSession session,@RequestParam("gradeId") long gradeId,@RequestParam("classId") long classId,
			@RequestParam("csId") int csId, Model model) throws DataException {
		List<RegisterForClass> studentList = null;
		try{
			studentList = addStudentsToClassService.getStudentList(gradeId, classId, csId);
		}catch(DataException e){
			logger.error("Error in getStudentList() of AddStudentsToClassController"
					+ e);
			e.printStackTrace();
		}
		if(csId > 0){
			long teacherId = adminSchedulerdao.getclassStatus(csId).getTeacher().getTeacherId();
			Teacher teacher = adminSchedulerdao.getTeacher(teacherId);
			String teacherTitle = teacher.getUserRegistration().getTitle();
			String teacherFirstName = teacher.getUserRegistration().getFirstName();
			String teacherLastName = teacher.getUserRegistration().getLastName();
			String teacherFullName = teacherTitle+". "+teacherFirstName +" "+teacherLastName;
			model.addAttribute("sectionTeacher", teacherFullName);
		}	
		model.addAttribute("allClassStudents", studentList);

		
		return new ModelAndView("Ajax/Admin/add_students_to_class_list");
	
	}
	
	@RequestMapping(value = "/AddStudent", method = RequestMethod.GET)
	public @ResponseBody
    void addStudents(HttpServletResponse response, HttpSession session,@RequestParam("studentId") long studentId, 
					@RequestParam("gClassId") long gClassId, @RequestParam("gLevelId") long gLevelId, 
					@RequestParam("csId") int csId, Model model) throws DataException , Exception {
		boolean IsStudentAdded = false;
		String helloAjax = "";
		try{		
			 IsStudentAdded = addStudentsToClassService.addStudentToClass(session, studentId, gClassId, csId);
		if(IsStudentAdded){
			helloAjax = WebKeys.LP_ADDED_SUCCESS;
		}else
			helloAjax = WebKeys.LP_FAILED;
		}catch(DataException e){
			helloAjax = e.getMessage();
			logger.error("Error in addStudents() of AddStudentsToClassController"
					+ e);
			e.printStackTrace();
		}		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);	
	}
	
	@RequestMapping(value = "/RemoveStudent", method = RequestMethod.GET)
	public @ResponseBody
	void removeStudents(HttpServletResponse response, HttpSession session, @RequestParam("studentId") long studentId, @RequestParam("gClassId") long gClassId, 
			@RequestParam("csId") long csId, Model model) throws Exception {		
	    String helloAjax = "";
	    try{		
			boolean IsStudentAdded = addStudentsToClassService.removeStudentFromClass(studentId, gClassId,csId);
			if(IsStudentAdded){
				helloAjax =  WebKeys.LP_REMOVED_SUCCESS;
			}else
				helloAjax =  WebKeys.LP_FAILED;
			}catch(Exception e){
				e.getMessage();
			}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);
	
	}

}
