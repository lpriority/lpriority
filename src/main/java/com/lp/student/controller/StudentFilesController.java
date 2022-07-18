package com.lp.student.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.lp.appadmin.service.AppAdminService3;
import com.lp.common.service.FileService;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.Folders;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.GradeBookService;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class StudentFilesController {

	@Autowired
	ServletContext context;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AppAdminService3 adminService3;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private FileService fileservice;
	@Autowired
	private GradeBookService gradeBookService;

	static final Logger logger = Logger.getLogger(StudentFilesController.class);

	@RequestMapping(value = "/displayStudentClassFiles", method = RequestMethod.GET)
	public ModelAndView displayStudentClassFiles(HttpSession session,
			HttpServletRequest request, Model model) {
		try {
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Student student = studentService.getStudent(userRegistration
					.getRegId());
			long mastergradeId = student.getGrade().getMasterGrades()
					.getMasterGradesId();
			String title = null;
			String fileType = request.getParameter("fileType");
			if (fileType != null
					&& fileType.equalsIgnoreCase(WebKeys.TEACHER_FILES)) {
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			} else if (fileType != null
					&& fileType.equalsIgnoreCase(WebKeys.STUDENT_PRIVATE_FILES)) {
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
			} else if (fileType != null
					&& fileType.equalsIgnoreCase(WebKeys.TRANSFER_FILES)) {
				title = WebKeys.TRANSFER_FILES_PAGE_TITLE;
			}

			if (request.getParameter("classId") != null) {
				String classId = request.getParameter("classId");
				model.addAttribute("classId", classId);
			}
			model.addAttribute("fileType", fileType);
			model.addAttribute("studentId", student.getStudentId());
			model.addAttribute("title", title);
			model.addAttribute("gradeId", student.getGrade().getGradeId());
			model.addAttribute("gradeName", adminService3.getMasterGrade(mastergradeId).getGradeName());
			if(userRegistration.getUser().getUserTypeid()==5 || userRegistration.getUser().getUserTypeid()==6){
				model.addAttribute("studentClassList", studentService.getStudentScheduledClasses(student));
			}else{
			model.addAttribute("studentClassList", adminService.getStudentClasses(student.getGrade().getGradeId()));
			}
		} catch (DataException e) {
			logger.error("Error in displayStudentClassFiles() of StudentFilesController"+ e.getStackTrace());
		}
		return new ModelAndView("Student/StudentTransfer");
	}
	
	@RequestMapping(value = "/displayStudentFiles", method = RequestMethod.GET)
	public ModelAndView displayStudentFiles(HttpSession session,
			HttpServletRequest request, Model model) {
		try {
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Student student = studentService.getStudent(userRegistration
					.getRegId());
			long mastergradeId = student.getGrade().getMasterGrades()
					.getMasterGradesId();
			String title = null;
			String fileType = request.getParameter("fileType");
			if (fileType != null
					&& fileType.equalsIgnoreCase(WebKeys.TEACHER_FILES)) {
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			} else if (fileType != null
					&& fileType.equalsIgnoreCase(WebKeys.STUDENT_PRIVATE_FILES)) {
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
			} else if (fileType != null
					&& fileType.equalsIgnoreCase(WebKeys.TRANSFER_FILES)) {
				title = WebKeys.TRANSFER_FILES_PAGE_TITLE;
			}

			if (request.getParameter("classId") != null) {
				String classId = request.getParameter("classId");
				model.addAttribute("classId", classId);
			}
			model.addAttribute("fileType", fileType);
			model.addAttribute("studentId", student.getStudentId());
			model.addAttribute("title", title);
			model.addAttribute("gradeId", student.getGrade().getGradeId());
			model.addAttribute("gradeName", adminService3.getMasterGrade(mastergradeId).getGradeName());
			if(userRegistration.getUser().getUserTypeid()==5 || userRegistration.getUser().getUserTypeid()==6){
				model.addAttribute("studentClassList", studentService.getStudentScheduledClasses(student));
			}else{
			model.addAttribute("studentClassList", adminService.getStudentClasses(student.getGrade().getGradeId()));
			}
		} catch (DataException e) {
			logger.error("Error in displayStudentClassFiles() of StudentFilesController"+ e.getStackTrace());
		}
		return new ModelAndView("Ajax/Student/student_files");
	}

	@RequestMapping(value = "/displayStudentCreateFolder", method = RequestMethod.GET)
	public ModelAndView displayStudentCreateFolder(HttpSession session,
			@RequestParam("fileType") String fileType,
			@RequestParam("studentId") long studentId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId, 
			HttpServletRequest request,
			Model model) {
		try {
			String title = null;
			String usersFolderPath = null;
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
			usersFolderPath = serverPath + File.separator + fileType + File.separator + gradeId + File.separator + classId + File.separator;
			if (fileType != null) {
				if (fileType.equalsIgnoreCase(WebKeys.STUDENT_PRIVATE_FILES)) {
					title = WebKeys.STUDENT_PRIVATE_FILES;
				} else {
					title = WebKeys.TRANSFER_FILES_PAGE_TITLE;
				}
				model.addAttribute("usersFolderPath", usersFolderPath);
				model.addAttribute("studentId", studentId);
				model.addAttribute("gradeId", gradeId);
				model.addAttribute("classId", classId);
				model.addAttribute("fileType", fileType);
				model.addAttribute("title", title);
			}
		} catch (DataException e) {
			logger.error("Error in displayStudentCreateFolder() of StudentFilesController" + e.getStackTrace());
		}
		return new ModelAndView("Ajax/Student/create_folder");
	}

	@RequestMapping(value = "/createStudentFolder")
	public @ResponseBody
	void createTeacherFolder(@RequestParam("fileType") String fileType,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("studentId") long studentId,
			@RequestParam("folderName") String folderName, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Folders folder = new Folders();
			String usersFolderPath = null;
			String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
			if (studentId > 0 && classId > 0 && gradeId > 0) {
				usersFolderPath = serverPath + File.separator + fileType + File.separator + gradeId + File.separator + classId + File.separator;
				List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
				model.addAttribute("teachersList", teachersList);
				model.addAttribute("studentId", studentId);
			}
			File directoryNew = new File(usersFolderPath + folderName);
			if (!directoryNew.exists()) {
				directoryNew.mkdirs();
				folder.setFoldername(folderName);
				folder.setFoldertype(fileType);
				folder.setCreateddate(new java.sql.Date(new java.util.Date()
						.getTime()));
				try {
					fileservice.saveFolder(folder);
				} catch (SQLDataException e) {
					e.printStackTrace();
				}
			}
			response.getWriter().write("Created Successfully");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
		} catch (Exception e) {
			logger.error("Error in createTeacherFolder() of StudentFilesController" + e.getStackTrace());
		}
	}

	@RequestMapping(value = "/loadStudentFolders")
	public ModelAndView loadStudentFolders(HttpSession session,
			HttpServletRequest request, Model model) {
		try {
			String title = null;
			String fullPath = null;
			UserRegistration userReg = null;
			long studentId = Long.parseLong(request.getParameter("studentId"));
			String fileType = null;
			long gradeId = Long.parseLong(request.getParameter("gradeId"));
			long classId = Long.parseLong(request.getParameter("classId"));

			List<Folders> foldersList = new ArrayList<Folders>();
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if (userRegistration.getUser().getUserTypeid() == 3) {
				Student student = gradeBookService.getStudentById(studentId);
				userReg = student.getUserRegistration();
				fileType = WebKeys.TRANSFER_FILES;
			} else {
				userReg = userRegistration;
				fileType = request.getParameter("fileType");
			}

			String serverPath = FileUploadUtil.getUploadFilesPath(userReg,
					request);
			if (studentId > 0 && classId > 0 && gradeId > 0) {
				fullPath = serverPath + File.separator + fileType
						+ File.separator + gradeId + File.separator + classId
						+ File.separator;
			}
			if (fullPath != null) {
				foldersList = fileservice.getDirectories(fullPath);
			}
			model.addAttribute("studentId", studentId);
			model.addAttribute("fileType", request.getParameter("fileType"));
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classId", classId);
			model.addAttribute("foldersList", foldersList);
			model.addAttribute("title", title);
		} catch (Exception e) {
			logger.error("Error in loadStudentFolders() of StudentFilesController" + e.getStackTrace());
		}
		return new ModelAndView("Ajax/Admin/display_folders");
	}

	@RequestMapping(value = "/uploadStudentFiles", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public ModelAndView uploadStudentFiles(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String title = null;
		try {
			String id = request.getParameter("id");
			String fileType = request.getParameter("fileType");
			String folderPath = request.getParameter("folderPath"+id);
			String gradeId = request.getParameter("gradeId");
			String classId = request.getParameter("classId");
			List<Folders> foldersList = new ArrayList<Folders>();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultiValueMap<String, MultipartFile> map = multipartRequest
					.getMultiFileMap();
			if (map != null) {
				@SuppressWarnings("rawtypes")
				Iterator iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String str = (String) iterator.next();
					List<MultipartFile> fileList = map.get(str);
					for (MultipartFile mpf : fileList) {
						File localFile = new File(folderPath
								+ File.separator
								+ StringUtils.trimAllWhitespace(mpf.getOriginalFilename()));
						OutputStream out;
						out = new FileOutputStream(localFile);
						out.write(mpf.getBytes());
						out.close();
					}
				}
			}
			String serverPath = FileUploadUtil.getUploadFilesPath(
					userRegistration, request);
			String fullPathPrivate = serverPath + File.separator + fileType
					+ File.separator + gradeId + File.separator + classId
					+ File.separator;
			if (folderPath != null) {
				foldersList = fileservice.getDirectories(fullPathPrivate);
			}
			title = WebKeys.UPLOAD_PRIVATE_FILES_PAGE_TITLE;
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classId", classId);
			model.addAttribute("title", title);
			model.addAttribute("fileType", fileType);
			model.addAttribute("foldersList", foldersList);
		} catch (Exception e) {
			logger.error("Error in uploadStudentFiles() of StudentFilesController" + e.getStackTrace());
		}
		return new ModelAndView("redirect:/displayStudentClassFiles.htm");
	}
	
	@RequestMapping(value = "/deleteFolder")
	public @ResponseBody void deleteFolders(
			@RequestParam("folderPath") String folderPath, 
			HttpSession session,
			HttpServletRequest request, 
			HttpServletResponse response,
			Model model) {
		try {
			boolean status = fileservice.deleteFolder(folderPath);
			if(status)
				response.getWriter().write("Deleted Successfully");
			else
				response.getWriter().write("Failed to delete");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
		} catch (Exception e) {
			logger.error("Error in deleteFolders() of StudentFilesController"+ e.getStackTrace());
		}
	}
	
	@RequestMapping(value = "/renameFolder")
	public @ResponseBody void renameFolder(
			@RequestParam("folderPath") String folderPath, 
			@RequestParam("renameVal") String renameVal,
			HttpSession session,
			HttpServletRequest request, 
			HttpServletResponse response,
			Model model) {
		try {
			boolean status = fileservice.renameFolder(folderPath,renameVal);
			if(status)
				response.getWriter().write("Renamed Successfully");
			else
				response.getWriter().write("Failed to rename");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
		} catch (Exception e) {
			logger.error("Error in renameFolder() of StudentFilesController" + e.getStackTrace());
		}
	}
}
