package com.lp.teacher.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.AssessmentService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.FileService;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.CreateUnits;
import com.lp.model.Folders;
import com.lp.model.Grade;
import com.lp.model.Lesson;
import com.lp.model.LessonPaths;
import com.lp.model.School;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class TeacherFilesController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private FileService fileservice;
	@Autowired
	ServletContext context; 
	
	
    static final Logger logger = Logger.getLogger(TeacherFilesController.class);
	@RequestMapping(value = "/displayTeacherClassFiles", method = RequestMethod.GET)
	public ModelAndView displayTeacherClassFiles(HttpSession session, HttpServletRequest request, Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView modelAndView = new ModelAndView("Teacher/teacher_transfer", "createunits", new CreateUnits());
		try{
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			String gradeId = "";
			String classId = "";
			String fileType = "";
			String unitId = "";
			if(request.getParameter("gradeId") != null && request.getParameter("classId") != null && request.getParameter("fileType") != null){
				gradeId = request.getParameter("gradeId");
				classId = request.getParameter("classId");
				fileType = request.getParameter("fileType");
				modelAndView.addObject("gradeId", gradeId);
				modelAndView.addObject("classId", classId);
				modelAndView.addObject("fileType", fileType);
				
				if(request.getParameter("unitId") != null){
					unitId = request.getParameter("unitId");
					modelAndView.addObject("unitId", unitId);
				}
			}
			modelAndView.addObject("tab", WebKeys.LP_TAB_UPLOAD_FILE);
			try{
				if(teacherObj == null){
					//admin
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
					modelAndView.addObject("grList", schoolgrades);
				}else{
					//teacher
					teacherGrades = curriculumService.getTeacherGrades(teacherObj);
					modelAndView.addObject("grList", teacherGrades);
					model.addAttribute("teacherId",  teacherObj.getTeacherId());
					model.addAttribute("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
				}
			}catch(DataException e){
				
				e.printStackTrace();
				modelAndView.addObject("helloAjax",WebKeys.LP_CURRICULUM_PLANNER_ERROR);
			}
			String title = null;
			String page = null;
			fileType = request.getParameter("fileType");
			
			if(fileType != null && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES) && request.getParameter("page") != null){
				 page = request.getParameter("page");
				if(page.equalsIgnoreCase("uploadFile")){
					model.addAttribute("page", page);
				}else if(page.equalsIgnoreCase("public")){
					
					model.addAttribute("page", page);
					model.addAttribute("title", "Class Files");
				}else if(page.equalsIgnoreCase("teacherLessonContent")){
					model.addAttribute("page", page);
					model.addAttribute("title", "Teacher Lesson Content");
				}
				
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				model.addAttribute("title", title);
			}
			model.addAttribute("fileType", fileType);
		}catch(DataException e){
			logger.error("Error in displayTeacherClassFiles() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return  modelAndView;		
	}
	
	//static final Logger logger = Logger.getLogger(TeacherFilesController.class);
	@RequestMapping(value = "/displayTeacherFiles", method = RequestMethod.GET)
	public ModelAndView displayTeacherFiles(HttpSession session, HttpServletRequest request, Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView modelAndView = new ModelAndView("Ajax2/Teacher/teacher_files", "createunits", new CreateUnits());
		try{
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			String gradeId = "";
			String classId = "";
			String fileType = "";
			String unitId = "";
			if(request.getParameter("gradeId") != null && request.getParameter("classId") != null && request.getParameter("fileType") != null){
				gradeId = request.getParameter("gradeId");
				classId = request.getParameter("classId");
				fileType = request.getParameter("fileType");
				modelAndView.addObject("gradeId", gradeId);
				modelAndView.addObject("classId", classId);
				modelAndView.addObject("fileType", fileType);
				
				if(request.getParameter("unitId") != null){
					unitId = request.getParameter("unitId");
					modelAndView.addObject("unitId", unitId);
				}
			}
			modelAndView.addObject("tab", WebKeys.LP_TAB_UPLOAD_FILE);
			try{
				if(teacherObj == null){
					//admin
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
					modelAndView.addObject("grList", schoolgrades);
				}else{
					//teacher
					teacherGrades = curriculumService.getTeacherGrades(teacherObj);
					modelAndView.addObject("grList", teacherGrades);
					model.addAttribute("teacherId",  teacherObj.getTeacherId());
					model.addAttribute("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
				}
			}catch(DataException e){
				
				e.printStackTrace();
				modelAndView.addObject("helloAjax",WebKeys.LP_CURRICULUM_PLANNER_ERROR);
			}
			String title = null;
			String page = null;
			fileType = request.getParameter("fileType");
			
			if(fileType != null && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES) && request.getParameter("page") != null){
				 page = request.getParameter("page");
				if(page.equalsIgnoreCase("uploadFile")){
					model.addAttribute("page", page);
				}else if(page.equalsIgnoreCase("public")){
					
					model.addAttribute("page", page);
					model.addAttribute("title", "Class Files");
				}else if(page.equalsIgnoreCase("teacherLessonContent")){
					model.addAttribute("page", page);
					model.addAttribute("title", "Teacher Lesson Content");
				}
				
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				model.addAttribute("title", title);
			}
			model.addAttribute("fileType", fileType);
		}catch(DataException e){
			logger.error("Error in displayTeacherClassFiles() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return  modelAndView;		
	}
	
	@RequestMapping(value = "/loadLessons")
	public ModelAndView loadLessons(HttpSession session, HttpServletRequest request, Model model) {
		try{
			long unitId =  Long.parseLong(request.getParameter("unitId"));
			long gradeId =  Long.parseLong(request.getParameter("gradeId"));
			long classId =  Long.parseLong(request.getParameter("classId"));
			String fileType = request.getParameter("fileType");
			String page = request.getParameter("page");
			Teacher teacher = (Teacher)session.getAttribute(WebKeys.TEACHER_OBJECT);
			if(teacher != null){
				model.addAttribute("teacherId", teacher.getTeacherId());		
			}			
			UserRegistration userReg = new UserRegistration();
			Unit unit = new Unit();
			unit = curriculumService.getUnitbyUnitId(unitId);			
			userReg = unit.getUserRegistration();		
			String serverPath = FileUploadUtil.getUploadFilesPath(userReg, request);
			
			List<Lesson> lessonsList =  new ArrayList<Lesson>();
			lessonsList = assessmentService.getLessonsByUnitId(unitId);
			Map<Long, File[]> foldersListMap = new HashMap<Long, File[]>();
			String filepath = serverPath+File.separator+WebKeys.TEACHER_FILES+File.separator+unit.getUnitId();
			for (Lesson lesson : lessonsList) {
				String fullPathPublic =  filepath+File.separator+lesson.getLessonId()+File.separator;
				foldersListMap.put(lesson.getLessonId(), fileservice.getFolderFiles(fullPathPublic));
			}
			model.addAttribute("lessonsList", lessonsList);	
			model.addAttribute("foldersListMap", foldersListMap);
			model.addAttribute("unitId", unitId);	
			model.addAttribute("fileType", fileType);
			model.addAttribute("serverPath", serverPath);
			model.addAttribute("gradeId", gradeId);	
			model.addAttribute("classId", classId);
			model.addAttribute("page", page);
		}catch(DataException e){
			logger.error("Error in loadLessons() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("Ajax2/Admin/display_folders");
	}
	
	@RequestMapping(value = "/uploadTeacherLessonFiles", headers="content-type=multipart/form-data", method=RequestMethod.POST )
	public ModelAndView uploadTeacherLessonFiles(HttpSession session,HttpServletRequest request, Model model) throws IOException{
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);			
			String unitId = request.getParameter("unitId");
			String lessonId = request.getParameter("lessonId");
			String page = request.getParameter("page");
			String gradeId = request.getParameter("gradeId");
			String classId = request.getParameter("classId");
			Lesson lesson = assessmentService.getLessonById(Long.parseLong(lessonId));
			Unit unit = curriculumService.getUnitbyUnitId(Long.parseLong(unitId));
			UserRegistration userReg =  unit.getUserRegistration();
			String serverPath = FileUploadUtil.getUploadFilesPath(userReg, request);
			String fullPath = serverPath +File.separator+WebKeys.TEACHER_FILES+File.separator+unitId+File.separator+lessonId;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();				
			if(map != null) {
				@SuppressWarnings("rawtypes")
				Iterator iterator = map.keySet().iterator();
				while(iterator.hasNext()) {
					String str = (String) iterator.next();
					List<MultipartFile> fileList =  map.get(str);
					File directory = new File (fullPath);
					if(!directory.exists())
						directory.mkdirs();		
					for (MultipartFile multipartFile : fileList) {
						File file = new File(fullPath+File.separator + StringUtils.trimAllWhitespace(multipartFile.getOriginalFilename()));						
						OutputStream out;
						out = new FileOutputStream(file);							
						out.write(multipartFile.getBytes());
						out.close();
						LessonPaths lessonPath = new LessonPaths();
						lessonPath.setUploadedBy(userRegistration.getRegId());						
						lessonPath.setLesson(lesson);
						lessonPath.setLessonPath(WebKeys.TEACHER_FILES+File.separator+unitId+File.separator+lessonId+File.separator +StringUtils.trimAllWhitespace(multipartFile.getOriginalFilename()));
						teacherService.setLessonPath(lessonPath);
					}					
				}												
			}
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classId", classId);
			model.addAttribute("fileType","public");
			model.addAttribute("unitId", unitId);
			model.addAttribute("page", page);
		}catch(DataException e){
			logger.error("Error in uploadTeacherLessonFiles() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/displayTeacherClassFiles.htm");
	}

	@RequestMapping(value = "/loadUnitsLessons")
	public ModelAndView loadUnitsLessons(HttpSession session, HttpServletRequest request, Model model) {
		try{
			UserRegistration userReg = null;
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			String title = null;
			String fullPath = null;
			String unitId = request.getParameter("unitId");
			Unit unit = new Unit();
			unit = curriculumService.getUnitbyUnitId( Long.parseLong(unitId));
			if(userRegistration.getUser().getUserTypeid() != 3)
				userReg = unit.getUserRegistration();
			else
				userReg = userRegistration;
			String fileType = request.getParameter("fileType");		
			List<Folders>	foldersList = new ArrayList<Folders>();
			String serverPath = FileUploadUtil.getUploadFilesPath(userReg, request);
			if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES) || fileType.equalsIgnoreCase(WebKeys.TEACHER_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
				String fullPathPublic = serverPath+File.separator+WebKeys.PUBLIC_FILES+File.separator+unit.getUnitId()+File.separator;
				fullPath=fullPathPublic;
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				String fullPathPrivate = serverPath+File.separator+fileType+File.separator+unit.getUnitId()+File.separator;;
				fullPath=fullPathPrivate;
			}
			foldersList= fileservice.getDirectories(fullPath);
			model.addAttribute("fileType", fileType);
			model.addAttribute("foldersList", foldersList);
			model.addAttribute("title", title);
			model.addAttribute("user", userReg.getUser().getUserType());
			model.addAttribute("unitId", unitId);
		}catch(DataException e){
			logger.error("Error in loadUnitsLessons() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("Admin/display_folders");
	}
	
	@RequestMapping(value = "/newFolder", method = RequestMethod.POST)
	public ModelAndView newFolder(HttpSession session, HttpServletRequest request, Model model) {
		try{
			String title = null;
			String fileType = request.getParameter("fileType");
			if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
			}
			model.addAttribute("gradeId", request.getParameter("gradeId"));
			model.addAttribute("classId", request.getParameter("classId"));
			model.addAttribute("fileType", fileType);
			model.addAttribute("title", title);
		}catch(DataException e){
			logger.error("Error in newFolder() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
			return new ModelAndView("Ajax/Teacher/create_folder");
	}
	@RequestMapping(value = "/displayTeacherCreateFolder", method = RequestMethod.GET)
	public ModelAndView displayTeacherCreateFolder(
			HttpSession session,
			@RequestParam("fileType") String fileType,
			@RequestParam("gradeId") long gradeId, 
			@RequestParam("classId") long classId, 
			@RequestParam("teacherId") long teacherId, 
			HttpServletRequest request, 
			Model model) {
		String title = null;
		String usersFolderPath = null;
		try{
			if(fileType != null){				
				UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
				String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
				if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
					title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
					usersFolderPath = serverPath+File.separator+fileType+File.separator+teacherId+File.separator;
				}else if(fileType.equalsIgnoreCase(WebKeys.PRIVATE_FILES)){		
					usersFolderPath = serverPath+File.separator+fileType+File.separator+gradeId+File.separator+classId+File.separator;
					title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				}
				model.addAttribute("usersFolderPath", usersFolderPath);
				model.addAttribute("teacherId", teacherId);
				model.addAttribute("gradeId", gradeId);
				model.addAttribute("classId", classId);
				model.addAttribute("fileType", fileType);
				model.addAttribute("title", title);
			}
		}catch(DataException e){
			logger.error("Error in displayTeacherCreateFolder() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("Ajax/Teacher/create_folder");
	}
	
	@RequestMapping(value = "/loadTeacherAdminFiles")
	public ModelAndView loadTeacherAdminFiles(@RequestParam("fileType") String fileType, HttpSession session, HttpServletRequest request, Model model) {
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			Teacher teacher = teacherService.getTeacher(userRegistration.getRegId());
			School school = userRegistration.getSchool();
			UserRegistration userregis1 = teacherService.getAdminUserRegistration(school);
			String title = WebKeys.ADMIN_FILES_PAGE_TITLE;
			String fullPath = null;
			model.addAttribute("fileType", "admin");
			List<Folders>	foldersList = new ArrayList<Folders>();
			String serverPath = FileUploadUtil.getUploadFilesPath(userregis1, request);
			String fullPathPublic = serverPath+File.separator+fileType+File.separator+teacher.getTeacherId()+File.separator;
			fullPath=fullPathPublic;
			foldersList= fileservice.getDirectories(fullPath);		
			model.addAttribute("foldersList", foldersList);
			model.addAttribute("title", title);
			model.addAttribute("user", "teacher");	
		}catch(DataException e){
			logger.error("Error in loadTeacherAdminFiles() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("Ajax2/Admin/display_folders");
	}
	
	@RequestMapping(value = "/displayTeacherStudentFiles", method = RequestMethod.GET)
	public ModelAndView displayTeacherStudentFiles(HttpSession session, HttpServletRequest request, Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView modelAndView;		
		modelAndView = new ModelAndView("Ajax2/Teacher/teacher_files", "createunits", new CreateUnits());
		try{
			modelAndView.addObject("tab", WebKeys.LP_TAB_CREATE);
			modelAndView.addObject("subTab", WebKeys.LP_SUB_TAB_CREATE_UNITS);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			try{
				if(teacherObj == null){
					//admin
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
					modelAndView.addObject("grList", schoolgrades);
				}else{
					//teacher
					teacherGrades = curriculumService.getTeacherGrades(teacherObj);
					modelAndView.addObject("grList", teacherGrades);
					model.addAttribute("teacherId",  teacherObj.getTeacherId());
				}
			}catch(DataException e){			
				e.printStackTrace();
				modelAndView.addObject("helloAjax",WebKeys.LP_CURRICULUM_PLANNER_ERROR);
			}
			model.addAttribute("fileType",  request.getParameter("fileType"));
			model.addAttribute("title", WebKeys.STUDENT_FILES_PAGE_TITLE);
		}catch(DataException e){
			logger.error("Error in displayTeacherStudentFiles() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
			return  modelAndView;		
	}
	@RequestMapping(value = "/loadTeacherFolders")
	public ModelAndView loadTeacherFolders(
			HttpSession session, 
			HttpServletRequest request, 
			Model model) {
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			String title = null;
			String fullPath = null;
			
	        long teacherId =  Long.parseLong(request.getParameter("teacherId"));
			String fileType = request.getParameter("fileType");	
			long gradeId = Long.parseLong(request.getParameter("gradeId"));
			long classId = Long.parseLong(request.getParameter("classId"));
			
			List<Folders>	foldersList = new ArrayList<Folders>();
			String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
			if(teacherId > 0 && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
				String fullPathPublic = serverPath+File.separator+fileType+File.separator+teacherId+File.separator;
				fullPath=fullPathPublic;
				List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
				model.addAttribute("teachersList", teachersList);			
				
			}else if(fileType.equalsIgnoreCase(WebKeys.PRIVATE_FILES) && gradeId > 0 && classId > 0){
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				String fullPathPrivate = serverPath+File.separator+fileType+File.separator+gradeId+File.separator+classId+File.separator;
				fullPath=fullPathPrivate;
				model.addAttribute("gradeId",gradeId);	
				model.addAttribute("classId", classId);
			}
			if(fullPath != null){
				foldersList= fileservice.getDirectories(fullPath);
			}
			model.addAttribute("teacherId",teacherId);	
			model.addAttribute("fileType", fileType);
			model.addAttribute("foldersList", foldersList);
			model.addAttribute("title", title);
		}catch(DataException e){
			logger.error("Error in loadTeacherFolders() of TeacherFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("Ajax/Admin/display_folders");
	}
	
	@RequestMapping(value = "/createTeacherFolder")
	public @ResponseBody void createTeacherFolder(
			@RequestParam("fileType") String fileType,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("teacherId") long teacherId,
			@RequestParam("folderName") String folderName,
			HttpSession session,HttpServletRequest request,
			HttpServletResponse response, Model model){
	  try{
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Folders folder = new Folders();	
		String fullPath = null;
		String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
		if(teacherId != 0 && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
			String fullPathPublic = serverPath+File.separator+fileType+File.separator+teacherId+File.separator;
			fullPath=fullPathPublic;
			List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
			model.addAttribute("teachersList", teachersList);
			model.addAttribute("teacherId",teacherId);
		}else if(fileType.equalsIgnoreCase(WebKeys.PRIVATE_FILES)){		
			String fullPathPrivate = serverPath+File.separator+fileType+File.separator+gradeId+File.separator+classId+File.separator;
			fullPath=fullPathPrivate;
		}
		File directoryNew = new File (fullPath+folderName);		
		if(!directoryNew.exists()){
			directoryNew.mkdirs();
			folder.setFoldername(folderName);
			folder.setFoldertype(fileType);
			folder.setCreateddate(new java.sql.Date(new java.util.Date().getTime()));
			try {
				fileservice.saveFolder(folder);
			} catch (SQLDataException e) {				
				e.printStackTrace();
			}
		}
	     response.getWriter().write("Created Successfully");  
		 response.setCharacterEncoding("UTF-8");  
	     response.setContentType("text/html");  
	  }catch(Exception e){
			logger.error("Error in createTeacherFolder() of TeacherFilesController"+ e);
			e.printStackTrace();
	  }
	}
	@RequestMapping(value = "/uploadTeacherFiles", headers="content-type=multipart/form-data",  method = RequestMethod.POST)
	public ModelAndView uploadTeacherFiles(HttpSession session,HttpServletRequest request,HttpServletResponse response, Model model) throws IOException{
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String title = null;
			String id = request.getParameter("id");
			String fileType = request.getParameter("fileType");
			String folderPath = request.getParameter("folderPath"+id);
			String gradeId = request.getParameter("gradeId");
			String classId = request.getParameter("classId");		
			List<Folders> foldersList = new ArrayList<Folders>();		
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();		
			if(map != null) {
				@SuppressWarnings("rawtypes")
				Iterator iterator = map.keySet().iterator();
				while(iterator.hasNext()) {
					String str = (String) iterator.next();
					List<MultipartFile> fileList =  map.get(str);
					for(MultipartFile mpf : fileList) {
						File localFile = new File(folderPath+File.separator + StringUtils.trimAllWhitespace(mpf.getOriginalFilename()));
						OutputStream out;
						out = new FileOutputStream(localFile);							
						out.write(mpf.getBytes());
						out.close();							
						}
					}
				}
			String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
			String fullPathPrivate = serverPath+File.separator+fileType+File.separator+gradeId+File.separator+classId+File.separator;
			if(folderPath != null){
				foldersList= fileservice.getDirectories(fullPathPrivate);
			}
			title = WebKeys.UPLOAD_PRIVATE_FILES_PAGE_TITLE;
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classId", classId);
			model.addAttribute("title", title);	
			model.addAttribute("fileType",fileType );		
			model.addAttribute("foldersList", foldersList);	
		}catch(Exception e){
			logger.error("Error in uploadTeacherFiles() of TeacherFilesController"+ e);
			e.printStackTrace();
	   }
		return new ModelAndView("redirect:/displayTeacherClassFiles.htm");
	}
}

