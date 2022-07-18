package com.lp.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignLessons;
import com.lp.model.CreateUnits;
import com.lp.model.Grade;
import com.lp.model.Lesson;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 * 
 */
@Controller
public class CurriculumController extends WebApplicationObjectSupport {

	@Autowired
	private AdminService adminservice;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private AssessmentDAO assessmentDAO;

	static final Logger logger = Logger.getLogger(CurriculumController.class);

	// CurriculumPlan
	@RequestMapping(value = "/curriculumPlan", method = RequestMethod.GET)
	public ModelAndView curriculumPlan(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("curriculum/curriculum_plan",
				"createunits", new CreateUnits());
		model.addObject("tab", WebKeys.LP_TAB_CURRICULUM);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_CREATE_UNITS);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addObject("regId",userReg.getRegId());
		try {
			if (teacherObj == null) {
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);				
			}
			if(session.getAttribute("isError") != null){
				model.addObject("isError", session.getAttribute("isError").toString());
				session.removeAttribute("isError");
			}
			if(session.getAttribute("helloAjax") != null){
				model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
				session.removeAttribute("helloAjax");
			}
		} catch (DataException e) {
			logger.error("Error in curriculumPlan() of CurriculumController"+ e);
			model.addObject("helloAjax", e.getMessage());
		}

		return model;
	}
	
	@RequestMapping(value = "/showUnits", method = RequestMethod.GET)
	public ModelAndView showUnits(@RequestParam("subjectId") long classId,
			@ModelAttribute("gradeId") long gradeId)
			throws DataException {
		List<Unit> units = new ArrayList<Unit>();
		CreateUnits cu = new CreateUnits();		
		boolean isError = false;
		ModelAndView model = new ModelAndView("curriculum/edit_units","editUnits", new CreateUnits());
		try {			
			units = curriculumService.getUnitsByGradeClassUser(gradeId, classId);
			cu.setUnits(units);
			if (units.size() == 0) {
				isError = true;				
				model.addObject("isError", isError);
				model.addObject("helloAjax", WebKeys.LP_NO_UNITS);
			}else{
				model = new ModelAndView("curriculum/edit_units","editUnits", cu);
				model.addObject("isError", isError);
				model.addObject("noOfUnits", units.size()-1);
			}
		} catch (DataException e) {
			isError = true;
			logger.error("Error in showUnits() of of CurriculumController"+ e);
			model.addObject("helloAjax", WebKeys.LP_LOAD_UNITS_ERROR);			
		}
		return model;
	}
	
	@RequestMapping(value = "/createunit")
	public @ResponseBody void createUnits(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("unitName") String unitName) throws DataException {
		boolean isError = false;		
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			if (userReg != null) {
				try {
					// Create units (Admin && Teacher)
					Unit units = new Unit();
					units.setUnitName(unitName);
					boolean status = curriculumService.createUnit(gradeId, classId, units, userReg);
					if (status) {
						 response.getWriter().write(WebKeys.LP_CREATED_SUCCESS);
					} else {
						 response.getWriter().write(WebKeys.LP_UNIT_ALREADY_EXISTS);
					}
				} catch (DataException e) {
					isError = true;
					logger.error("Error in createUnits() of of CurriculumController"
							+ e);
					e.printStackTrace();
					 session.setAttribute("isError", isError);
					 response.getWriter().write(WebKeys.LP_CREATE_UNITS_ERROR);
				}
			}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		} catch (IOException e) {
			logger.error("Error in createUnits() of CurriculumController" + e);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/updateUnit")
	public @ResponseBody void updateUnit(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("unitId") long unitId,
			@RequestParam("unitName") String unitName,
			@RequestParam("unitNo") long unitNo) throws DataException {
		boolean isError = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			if (userReg != null) {
				try {
					// Edit units (Admin && Teacher)
					Unit unit = new Unit();
					unit.setUnitId(unitId);
					unit.setUnitNo(unitNo);
					unit.setUnitName(unitName);
					boolean status = curriculumService.updateUnit(unit, gradeId, classId);
					if (status) {
						 response.getWriter().write(WebKeys.LP_UPDATED_SUCCESS);
					} else {
						// Edit units failed
						 response.getWriter().write("Unit name already used. "+WebKeys.LP_EDIT_UNIT_FAILED);
					}
				} catch (DataException e) {
					isError = true;
					session.setAttribute("isError", isError);
					logger.error("Error in updateUnit() of of CurriculumController"+ e);
					response.getWriter().write(WebKeys.LP_EDIT_UNIT_ERROR);
				}
			}

		} catch (IOException e) {
			logger.error("Error in updateUnit() of CurriculumController" + e);
		}
	}

	// Remove Unit Lessons
	@RequestMapping(value = "/removeUnit", method = RequestMethod.GET)
	public View removeUnit(@RequestParam("unitId") long unitId,Model model) {
		boolean status = false,delStatus=false;
		try {
			delStatus=curriculumService.checkUnitExists(unitId);
			if(delStatus){
				model.addAttribute("status",WebKeys.LP_DELETE);	}
			else{
			  status = curriculumService.removeUnit(unitId);
			  if(status)
			    model.addAttribute("status",WebKeys.LP_REMOVED_SUCCESS);
			  else
				  model.addAttribute("status",WebKeys.LP_REMOVED_FAILED); }
			   
		} catch (DataException e) {
			logger.error("Error in removeUnit() of of CurriculumController"	+ e);
			return new MappingJackson2JsonView();
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addUnitsView")
	protected ModelAndView appendUnitField(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId) {
		ModelAndView model = new ModelAndView("Ajax/curriculum/add_units");
		model.addObject("gradeId", gradeId);
		model.addObject("classId", classId);
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addLessonsView")
	protected ModelAndView appendLessonField(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("unitId") long unitId,
			@RequestParam("lessonId") long lessonId) {
		Lesson lesson = null;
		
		if(lessonId > 0)
			 lesson =	curriculumService.getLessonObj(lessonId);
		else
			 lesson = new Lesson();
		ModelAndView model = new ModelAndView("Ajax/curriculum/add_lessons","lesson",lesson);
		if(lesson.getActivityList() != null){
			model.addObject("activitiesSize", lesson.getActivityList().size());
			model.addObject("lessonId", lessonId);
		}
		if(lessonId > 0)
			 model.addObject("mode", "edit");
		else
			model.addObject("mode", "create");

		model.addObject("gradeId", gradeId);
		model.addObject("classId", classId);
		model.addObject("unitId", unitId);
		return model;
	}

	// Add Lessons To Unit
	@RequestMapping(value = "/addLessonstoUnit", method = RequestMethod.GET)
	public ModelAndView addLessonstoUnit(HttpSession session,HttpServletRequest request) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("curriculum/add_lessons_to_unit");
		model.addObject("tab", WebKeys.LP_TAB_CURRICULUM);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_ADD_LESSONS_TO_UNIT);
		model.addObject("redId", userReg.getRegId());		
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		try {
			if (teacherObj == null) {
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());

				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);				
			}
		} catch (DataException e) {
			logger.error("Error in addLessonstoUnit() of CurriculumController"+ e);
			model.addObject("helloAjax", e.getMessage());
		}
		if(session.getAttribute("helloAjax") != null && session.getAttribute("tab") != null && session.getAttribute("subTab") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			model.addObject("tab", session.getAttribute("tab").toString());
			model.addObject("subTab", session.getAttribute("subTab"));
			session.removeAttribute("tab");
			session.removeAttribute("subTab");			
			session.removeAttribute("helloAjax");
		}
		return model;

	}

	@RequestMapping(value = "/checkUnitExists", method = RequestMethod.GET)
	public View checkUnit(@RequestParam("subjectId") long classId,
			@ModelAttribute("gradeId") long gradeId, Model model)
			throws DataException {
		List<Unit> units = null;
		boolean isError = false;
		try {
			units = curriculumService
					.getUnitsByGradeIdClassId(gradeId, classId);
			if (units.size() > 0) {
				isError = true;
				model.addAttribute("isError", isError);
				model.addAttribute("helloAjax",
						WebKeys.LP_SUBJECT_ALREADY_PLANED);
			}
		} catch (DataException e) {
			isError = true;
			logger.error("Error in checkUnit() of of CurriculumController"
					+ e);
			e.printStackTrace();
			model.addAttribute("helloAjax", WebKeys.LP_LOAD_UNITS_ERROR);
			return new MappingJackson2JsonView();
		}

		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/createLesson", method = RequestMethod.POST)
	public @ResponseBody void createLesson(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@ModelAttribute("lesson") Lesson lesson,
			BindingResult result) throws DataException {
		try {
			boolean status;
			long unitId = lesson.getUnit().getUnitId();
			status = curriculumService.createLesson(unitId, lesson);
			if (status) {
				 response.getWriter().write(WebKeys.LP_CREATED_SUCCESS);
			} else {
				 response.getWriter().write(WebKeys.LP_LESSON_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			logger.error("Error in createLesson() of CurriculumController"
					+ e);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/addActivity", method = RequestMethod.GET)
	public ModelAndView addActivity(@ModelAttribute("count") long count,@ModelAttribute("inc") long inc,@ModelAttribute("mode") String mode,HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/curriculum/add_activity");
		model.addObject("count", count);
		model.addObject("inc", inc);
		model.addObject("mode", mode);
		return model;
	}

	//create new activity
	@RequestMapping(value = "/createActivity")
	public @ResponseBody void createActivity(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			@RequestParam("lessonId") long lessonId,@RequestParam("activityDesc") String activityDesc) {
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Lesson lesson = curriculumService.getLessonObj(lessonId);
			Activity activity = new Activity();
			activity.setLesson(lesson);
			activity.setActivityDesc(activityDesc);
			activity.setUserRegistration(userReg);
			boolean status = curriculumService.saveActivity(activity);
			if (status) {
				 response.getWriter().write(WebKeys.LP_CREATED_SUCCESS);
			} else {
				 response.getWriter().write(WebKeys.LP_FAILED);
			}
		} catch (Exception e) {
			logger.error("Error in createActivity() of CurriculumController"+ e);			

		}
	}
	
	// Remove Unit Lessons
		@RequestMapping(value = "/removeActivity", method = RequestMethod.GET)
		public @ResponseBody void removeActivity(@RequestParam("activityId") long activityId,HttpServletResponse response) {
			boolean status = false;
			try {
				status = curriculumService.removeActivity(activityId);
				if (status)
					 response.getWriter().write(WebKeys.LP_REMOVED_SUCCESS);
				else
					 response.getWriter().write(WebKeys.LP_FAILED);
			} catch (Exception e) {
				logger.error("Error in removeUnit() of of CurriculumController"	+ e);
			}
		}

	@RequestMapping(value = "/viewAssignedCurriculum", method = RequestMethod.GET)
	public ModelAndView viewAssignedCurriculum(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> teacherGrades = new ArrayList<Grade>();
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg,
				request);
		ModelAndView model = new ModelAndView("Teacher/view_assigned_curriculum");
		model.addObject("tab", WebKeys.LP_TAB_VIEW_CURRICULUM);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		model.addObject("filePath", uploadFilePath + File.separator);
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("teacherGrades", teacherGrades);
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/getAssignedCurriculum", method = RequestMethod.GET)
	public ModelAndView getAssignedCurriculum(@RequestParam("csId") long csId,
			HttpSession session) throws Exception {
		UserRegistration userReg = null;
		UserRegistration teacherReg = null;
		ModelAndView model = new ModelAndView(
				"Ajax/Teacher/include_assigned_curriculum");
		userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
        if(userReg.getUser().getUserTypeid() != 3){
        	Teacher teacher = studentService.getTeacherByCsId(csId);
        	teacherReg = teacher.getUserRegistration();
        }else{
        	teacherReg = userReg;
        }
		List<AssignLessons> assignedCurriculum = curriculumService.getAssignedCurriculum(csId, teacherReg);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(teacherReg,
				request);
		model.addObject("filePath", uploadFilePath + File.separator);
    	model.addObject("user", userReg.getUser().getUserType());
		model.addObject("assignedCurriculum", assignedCurriculum);
		return model;
	}

	@RequestMapping(value = "/studentLessonsCurriculum", method = RequestMethod.GET)
	public ModelAndView displayStudentClassFiles(HttpSession session,
			HttpServletRequest request, Model model) {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		model.addAttribute("tab", WebKeys.LP_TAB_LESSON);
		model.addAttribute("student", student);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		model.addAttribute("studentClassList",
				studentService.getStudentClasses(student));
		model.addAttribute("grade",
				studentService.getGrade(student.getGrade().getGradeId()));
		return new ModelAndView("Student/assigned_curriculum");
	}
	
	@RequestMapping(value = "/lessonsCurriculum", method = RequestMethod.GET)
	public ModelAndView lessonsCurriculum(HttpSession session,
			HttpServletRequest request, Model model) {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		model.addAttribute("tab", WebKeys.LP_TAB_LESSON);
		model.addAttribute("student", student);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		model.addAttribute("studentClassList",
				studentService.getStudentClasses(student));
		model.addAttribute("grade",
				studentService.getGrade(student.getGrade().getGradeId()));
		return new ModelAndView("Ajax/Student/lesson_div");
	}

	@RequestMapping(value = "/getStudentAssignedCurriculum", method = RequestMethod.GET)
	public ModelAndView getStudentAssignedCurriculum(
			@RequestParam("csId") long csId, HttpSession session)
			throws Exception {
		Teacher teacher = studentService.getTeacherByCsId(csId);
		List<AssignLessons> assignedCurriculum = curriculumService
				.getAssignedCurriculum(csId, teacher.getUserRegistration());
		ModelAndView model = new ModelAndView(
				"Teacher/include_assigned_curriculum");
		model.addObject("user", WebKeys.STUDENT_OBJECT);
		model.addObject("assignedCurriculum", assignedCurriculum);
		return model;
	}
	
	// Edit Curriculum
	@RequestMapping(value = "/editCurriculum", method = RequestMethod.GET)
	public ModelAndView editCurriculum(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("curriculum/edit_curriculum",	"editUnits", new CreateUnits());
		model.addObject("tab", WebKeys.LP_TAB_EDIT);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_EDIT_UNITS);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in editCurriculum() of CurriculumController"+ e);
			model.addObject("helloAjax", WebKeys.LP_CURRICULUM_PLANNER_ERROR);
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		return model;
	}
	
		
	// Edit Unit Lessons
	@RequestMapping(value = "/editLessons", method = RequestMethod.GET)
	public ModelAndView editLessons(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("curriculum/edit_unit_lessons",
				"editLessons", new CreateUnits());
		model.addObject("tab", WebKeys.LP_TAB_EDIT);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_EDIT_LESSONS);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in editLessons() of CurriculumController"+ e);			
			model.addObject("helloAjax", WebKeys.LP_CURRICULUM_PLANNER_ERROR);
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		return model;
	}
	
	//editLessonsView
	@RequestMapping(value = "/editLessonsView", method = RequestMethod.GET)
	public ModelAndView editLessonsView(@RequestParam("unitId") long unitId,HttpSession session)
			throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		CreateUnits cu = new CreateUnits();		
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("curriculum/edit_lessons","editLessons", new CreateUnits());
		try {			
			lessons = curriculumService.getLessonsByUnitIdUserId(unitId);
			cu.setLessons(lessons);
			model = new ModelAndView("Ajax/curriculum/edit_lessons","editLessons", cu);
			model.addObject("noOfLessons", lessons.size()-1);
			model.addObject("regId",userReg.getRegId());
		} catch (DataException e) {
			logger.error("Error in editLessonsView() of of CurriculumController"+ e);
			model.addObject("helloAjax", WebKeys.LP_LOAD_UNITS_ERROR);			
		}
		return model;
	}
	
	// Remove Lessons
	@RequestMapping(value = "/removeLesson")
	public @ResponseBody void removeLesson(
			@RequestParam("unitId") long unitId,
			@ModelAttribute("lessonId") long lessonId,
			HttpServletResponse response) {
		boolean status = false,delLesson=false;
		try {
			
			delLesson =curriculumService.checkLessonExists(lessonId);
			if(delLesson){
			 response.getWriter().write(WebKeys.LP_DELETE);	
			}
			else{
				status = curriculumService.removeLesson(lessonId);
				if(status)
					 response.getWriter().write(WebKeys.LP_REMOVED_SUCCESS);	
				else
					 response.getWriter().write(WebKeys.LP_UNABLE_REMOVE_LESSON);	
			}
			
		} catch (Exception e) {
			logger.error("Error in removeLesson() of of CurriculumController"	+ e);
		}
	}
	//updateLessons
	@RequestMapping(value = "/updateLesson")
	public @ResponseBody void updateLesson(
			HttpServletRequest request,
			HttpServletResponse response, 
			HttpSession session,
			@RequestParam("unitId") long unitId,
			@RequestParam("lessonName") String lessonName,
			@RequestParam("lessonDesc") String lessonDesc,
			@RequestParam("lessonId") long lessonId) {
		try {
                Lesson lesson =  new Lesson();
                lesson.setUnit(curriculumService.getUnitbyUnitId(unitId));
                lesson.setLessonId(lessonId);
                lesson.setLessonName(lessonName);
                lesson.setLessonDesc(lessonDesc);
				boolean status = curriculumService.updateLesson(lesson);
				if (status) {
					 response.getWriter().write(WebKeys.LP_UPDATED_SUCCESS);
				} else {
					 response.getWriter().write(WebKeys.LP_FAILED);
				}
		}catch (Exception e) {
			logger.error("Error in updateLessons() of of CurriculumController"+ e);
		}
	}
	
	// Remove Unit Lessons
	@RequestMapping(value = "/removeCurriculum", method = RequestMethod.GET)
	public ModelAndView removeCurriculum(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("curriculum/remove_unit_lessons");
		model.addObject("tab", WebKeys.LP_TAB_REMOVE);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in editLessons() of CurriculumController"+ e);			
			model.addObject("helloAjax", WebKeys.LP_CURRICULUM_PLANNER_ERROR);
		}
		return model;
	}

	// Edit activity
	@RequestMapping(value = "/editActivity", method = RequestMethod.GET)
	public ModelAndView editActivity(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("curriculum/edit_activity","editActivity", new CreateUnits());
		model.addObject("tab", WebKeys.LP_TAB_EDIT);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_EDIT_ACTIVITY);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in editActivity() of CurriculumController"+ e);			
			model.addObject("helloAjax", WebKeys.LP_CURRICULUM_PLANNER_ERROR);
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		return model;
	}
	
	//editActivityView
	@RequestMapping(value = "/editActivityView", method = RequestMethod.GET)
	public ModelAndView editActivityView(@RequestParam("lessonId") long lessonId)
			throws DataException {
		List<Activity> activity = new ArrayList<Activity>();
		boolean isError = false;
		ModelAndView model = new ModelAndView("curriculum/edit_activity_sub","editActivity", new CreateUnits());
		try {			
			activity = curriculumService.getActivityByLesson(lessonId);
			CreateUnits editActivity = new CreateUnits();
			if (activity.size() > 0) {		
				editActivity.setActivities(activity);
				model = new ModelAndView("curriculum/edit_activity_sub","editActivity", editActivity);
				model.addObject("noOfActivities", activity.size()-1);
			}else{
				isError = true;				
				model.addObject("isError", isError);
				model.addObject("helloAjax", WebKeys.LP_NO_ACTIVITY);
			}
		} catch (DataException e) {
			isError = true;
			logger.error("Error in editActivityView() of of CurriculumController"+ e);
			model.addObject("helloAjax", WebKeys.LP_CURRICULUM_PLANNER_ERROR);			
		}
		return model;
	}
	
	//updateActivity
	@RequestMapping(value = "/saveActivity")
	public View saveActivity(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			@RequestParam("activityId") long activityId,@RequestParam("activityDesc") String activityDesc,@RequestParam("lessonId") long lessonId,Model model) {
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Lesson lesson = assessmentDAO.getLessonById(lessonId);		
			Activity activity = new Activity();
			if(activityId > 0){
				model.addAttribute("status",WebKeys.LP_UPDATED_SUCCESS);
			 	activity.setActivityId(activityId);
			}else{
				model.addAttribute("status",WebKeys.LP_CREATED_SUCCESS);
			}
			activity.setActivityDesc(activityDesc);
			activity.setLesson(lesson);
			activity.setUserRegistration(userReg);
			activity = curriculumService.updateActivity(activity);
			if(activity.getActivityId() > 0)
				model.addAttribute("activityId",activity.getActivityId());
		} catch (Exception e) {
			model.addAttribute("status",WebKeys.LP_FAILED);
			logger.error("Error in saveActivity() of CurriculumController"+ e);			

		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/StudentSTEMCurriculum", method = RequestMethod.GET)
	public ModelAndView StudentSTEMCurriculum(HttpSession session,
			HttpServletRequest request, Model model) {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		model.addAttribute("tab", WebKeys.LP_TAB_STEM);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		model.addAttribute("mainPage", WebKeys.LP_YES);
		model.addAttribute("student", student);
		model.addAttribute("studentClassList",
				studentService.getStudentClasses(student));
		model.addAttribute("grade",
				studentService.getGrade(student.getGrade().getGradeId()));
		return new ModelAndView("Student/assigned_curriculum");
	}
	
	@RequestMapping(value = "/StudentSTEMCurriculumDiv", method = RequestMethod.GET)
	public ModelAndView StudentSTEMCurriculumDiv(HttpSession session,
			HttpServletRequest request, Model model) {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		model.addAttribute("tab", WebKeys.LP_TAB_STEM);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		model.addAttribute("mainPage", WebKeys.LP_NO);
		model.addAttribute("student", student);
		model.addAttribute("studentClassList",
				studentService.getStudentClasses(student));
		model.addAttribute("grade",
				studentService.getGrade(student.getGrade().getGradeId()));
		return new ModelAndView("Ajax/Student/lesson_div");
	}
	
	
}