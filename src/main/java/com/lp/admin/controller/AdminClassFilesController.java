package com.lp.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.FileService;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.Folders;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class AdminClassFilesController extends WebApplicationObjectSupport{

	@Autowired
	private AdminService adminService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private FileService fileservice;
	@Autowired
	ServletContext context; 
	
	static final Logger logger = Logger.getLogger(AdminClassFilesController.class);

	@RequestMapping(value = "/displayClassFiles", method = RequestMethod.GET)
	public ModelAndView displayClassFiles(HttpSession session, Model model,HttpServletRequest request) {
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
			String title = null;
			String fileType = request.getParameter("fileType");
			if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
			}
			model.addAttribute("teachersList", teachersList);	
			model.addAttribute("fileType", fileType);
			model.addAttribute("title", title);
		}catch(DataException e){
			logger.error("Error in displayClassFiles() of AdminClassFilesController"+ e);
		}
		return new ModelAndView("Admin/my_files");
	}
	
	@RequestMapping(value = "/displayCreateFolder", method = RequestMethod.GET)
	public ModelAndView displayCreatFolder(HttpSession session,@RequestParam("fileType") String fileType, @RequestParam("teacherId") long teacherId, HttpServletRequest request, Model model) {
		try{
			String title = null;
			String usersFolderPath = null;	
			if(fileType != null){
				UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
				if(teacherId > 0 && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
					   title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
					   usersFolderPath = serverPath+File.separator+fileType+File.separator+teacherId+File.separator;
				   }else if(fileType.equalsIgnoreCase(WebKeys.PRIVATE_FILES)){
					   title = WebKeys.PRIVATE_FILES_PAGE_TITLE;			
					   usersFolderPath = serverPath+File.separator+fileType+File.separator;
				   }
				model.addAttribute("usersFolderPath", usersFolderPath);
				model.addAttribute("teacherId", teacherId);
				model.addAttribute("gradeId", request.getParameter("gradeId"));
				model.addAttribute("classId", request.getParameter("classId"));
				model.addAttribute("fileType", fileType);
				model.addAttribute("title", title);
			}
		}catch(DataException e){
			logger.error("Error in displayCreatFolder() of AdminClassFilesController"+ e);
		}
		return new ModelAndView("Ajax/Admin/create_folder");
	}
	
	@RequestMapping(value = "/loadFolders")
	public ModelAndView loadFolders(@RequestParam("teacherId") long teacherId, HttpSession session, HttpServletRequest request, Model model) {
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			String title = null;
			String fullPath = null;
			String fileType = request.getParameter("fileType");	
			List<Folders>	foldersList = new ArrayList<Folders>();
			String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
			if(teacherId != 0 && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
				String fullPathPublic = serverPath+File.separator+fileType+File.separator+teacherId+File.separator;
				fullPath=fullPathPublic;
				List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
				model.addAttribute("teachersList", teachersList);			
				model.addAttribute("teacherId",teacherId);	
			}else if(fileType.equalsIgnoreCase(WebKeys.PRIVATE_FILES)){
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				String fullPathPrivate = serverPath+File.separator+fileType+File.separator;
				fullPath=fullPathPrivate;
			}
			if(fullPath != null){
				foldersList= fileservice.getDirectories(fullPath);
			}		
			model.addAttribute("fileType", fileType);
			model.addAttribute("foldersList", foldersList);
			model.addAttribute("title", title);
		}catch(DataException e){
			logger.error("Error in loadFolders() of AdminClassFilesController"+ e);
		}
		return new ModelAndView("Ajax/Admin/display_folders");
	}

	@RequestMapping(value = "/createFolder")
	public @ResponseBody void createFolder(@RequestParam("fileType") String fileType, @RequestParam("teacherId") long teacherId,@RequestParam("folderName") String folderName,HttpSession session,HttpServletRequest request,HttpServletResponse response, Model model){
	   try{
		   UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		   Folders folder = new Folders();	
		   List<Folders> foldersList = new ArrayList<Folders>();
		   String title = null;
		   String usersFolderPath = null;	
		   String serverPath = FileUploadUtil.getUploadFilesPath(userRegistration, request);
		   if(teacherId > 0 && fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
			   title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			   String fullPathPublic = serverPath+File.separator+fileType+File.separator+teacherId+File.separator;
			   usersFolderPath = fullPathPublic;
			   List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
			   model.addAttribute("teachersList", teachersList);
			   model.addAttribute("teacherId",teacherId);
		   }else if(fileType.equalsIgnoreCase(WebKeys.PRIVATE_FILES)){
			   title = WebKeys.PRIVATE_FILES_PAGE_TITLE;			
			   String fullPathPrivate = serverPath+File.separator+fileType+File.separator;
			   usersFolderPath = fullPathPrivate;
		   }
		   File directoryNew = new File (usersFolderPath+folderName);		
		   if(!directoryNew.exists()){
			   directoryNew.mkdirs();
			   folder.setFoldername(folderName);
			   folder.setFoldertype(fileType);
			   folder.setCreateddate(new java.sql.Date(new java.util.Date().getTime()));
			   try {
				   fileservice.saveFolder(folder);
			   } catch (SQLDataException e) {			
				   logger.error("Error in createFolder() of AdminClassFilesController"+ e);
			   }
		   }
		   if(usersFolderPath != null && !usersFolderPath.isEmpty()){
			   foldersList= fileservice.getDirectories(usersFolderPath);	
		   }
		   model.addAttribute("title", title);		
		   model.addAttribute("fileType",fileType );
		   model.addAttribute("folder",folder );
		   model.addAttribute("foldersList", foldersList);
		   response.getWriter().write("Created Successfully");  
		   response.setCharacterEncoding("UTF-8");  
		   response.setContentType("text/html");  
	   	}catch(Exception e){
	   		logger.error("Error in createFolder() of AdminClassFilesController"+ e);
	   	}
	}
	
	@RequestMapping(value = "/uploadFile", headers="content-type=multipart/form-data", method=RequestMethod.POST )
	public ModelAndView uploadFile(HttpSession session,HttpServletRequest request, Model model) throws IOException{	
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String title = null;
			String id = request.getParameter("id");
			String fileType = request.getParameter("fileType");
			String folderPath = request.getParameter("folderPath"+id);
			String teacherId = request.getParameter("teacherId");	
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
			foldersList=fileservice.getDirectories(folderPath);		
			if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
				List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());	
				model.addAttribute("teachersList", teachersList);
				model.addAttribute("teacherId",teacherId);
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
			}
			model.addAttribute("title", title);	
			model.addAttribute("fileType",fileType);		
			model.addAttribute("foldersList", foldersList);
		}catch(Exception e){
			logger.error("Error in uploadFile() of AdminClassFilesController"+ e);
	   }
		return new ModelAndView("redirect:/displayClassFiles.htm");
	}
	
	@RequestMapping(value = "/deleteFile",  method=RequestMethod.POST )
	public View deleteFile(HttpSession session,HttpServletRequest request, Model model) throws IOException{
		String teacherId = request.getParameter("teacherId");
		boolean status = true;
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String title = null;
			String folderPath = request.getParameter("folderPath");
			String fileType = request.getParameter("fileType");
			String fileName = request.getParameter("fileName");
			List<Folders> foldersList = new ArrayList<Folders>();
			File[] files = null;
			String filePath = folderPath+File.separator+fileName;
			File localFile = new File(filePath);
			if(localFile.exists()){
				FileUtils.forceDelete(localFile);
				if(request.getParameter("unitId") != null && request.getParameter("lessonId") != null){
					String unitId = request.getParameter("unitId"); 
					String lessonId = request.getParameter("lessonId");
					String lessonPath = WebKeys.TEACHER_FILES+File.separator +File.separator +unitId+File.separator +File.separator +lessonId+File.separator+File.separator +fileName ;
					status = teacherService.deleteLessonPath(Long.parseLong(lessonId), lessonPath);
				}	
				foldersList= fileservice.getDirectories(folderPath);
				files= fileservice.getFolderFiles(folderPath);
				if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
					title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
					List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());	
					model.addAttribute("teachersList", teachersList);
					model.addAttribute("teacherId",teacherId);
				}else{
					title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
				}
			}else{
				status = false;
			}
			model.addAttribute("title", title);	
			model.addAttribute("fileType",fileType );		
			model.addAttribute("foldersList", foldersList);
			model.addAttribute("filesLength", files.length);	
		}catch(Exception e){
			status = false;
			logger.error("Error in deleteFile() of AdminClassFilesController"+ e);
		}finally{
			model.addAttribute("status", status);	
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/downloadFile",method = RequestMethod.GET)
    public  void doDownload(HttpServletRequest request,
            HttpServletResponse response, HttpSession session,@RequestParam("filePath") String filePath ) throws IOException { 
		System.out.println(filePath);
		FileInputStream fileIn = null ;
		ServletOutputStream out = null;
		try {		       
			File file = new File(filePath);
			System.out.println(file.getName());
		    fileIn = new FileInputStream(file);
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment;filename="+file.getName());		        
		    out = response.getOutputStream();
		    byte[] outputByte = new byte[(int)file.length()];
		    while(fileIn.read(outputByte, 0, (int)file.length()) != -1){
		    	out.write(outputByte, 0, (int)file.length());
		    }
		}catch (Exception e){
			logger.error("Error in downloadFile() of AdminClassFilesController" + e);
			e.printStackTrace();
		}finally{
			try {
				if (null != fileIn)
					fileIn.close();
				if (null != fileIn)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		 
		}
    }
	@RequestMapping(value = "/gotoUploadSchoolLogo", method=RequestMethod.GET )
	public ModelAndView gotoUploadSchoolLogo(HttpSession session,HttpServletRequest request, Model model) throws IOException{
		try{
			model.addAttribute("tab", WebKeys.LP_TAB_UPLOAD_SCHOOL_LOGO);
		}catch(Exception e){
			logger.error("Error in gotoUploadSchoolLogo of AdminClassFilesController" + e);
		}
		return new ModelAndView("Ajax/Admin/upload_school_logo");
	}
	
	@RequestMapping(value = "/uploadSchoolLogo", headers="content-type=multipart/form-data", method=RequestMethod.POST )
	public @ResponseBody void uploadSchoolLogo(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			boolean flag = fileservice.uploadSchoolLogo(map);
			if(flag){
				response.getWriter().write( WebKeys.UPLOAD_LOGO_SUCCESS);  
			}	
			else{
				response.getWriter().write( WebKeys.UPLOAD_LOGO_FAILURE);  
			}
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in uploadSchoolLogo of AdminClassFilesController" + e);
		}
	}
	
	@RequestMapping(value = "/getScheduledClassesByGradeNClass", method = RequestMethod.GET)
	public View getScheduledClassesByGradeNClass(HttpSession session,
			@RequestParam("gradeId") long gradeId, @RequestParam("classId") long classId, Model model) {
		try {
			model.addAttribute("scheduledClasses", adminService.getScheduledClassesByGradeNClass(gradeId, classId));
		}catch (SQLDataException e){
			e.printStackTrace();
			logger.error("error in getScheduledClassesByGradeNClass of AdminClassFilesController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/displayAdminClassFiles", method = RequestMethod.GET)
	public ModelAndView displayAdminClassFiles(HttpSession session,HttpServletRequest request) {
		ModelAndView model = new ModelAndView("Ajax2/Admin/my_files_sub");
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<ClassStatus> teachersList = teacherService.getTeachersBySchoolId(userRegistration.getSchool());
			String title = null;
			String fileType = request.getParameter("fileType");
			if(fileType.equalsIgnoreCase(WebKeys.PUBLIC_FILES)){
				title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			}else{
				title = WebKeys.PRIVATE_FILES_PAGE_TITLE;
			}
			model.addObject("teachersList", teachersList);	
			model.addObject("fileType", fileType);
			model.addObject("title", title);
		}catch(DataException e){
			logger.error("Error in displayClassFiles() of AdminClassFilesController"+ e);
		}
		return model;
	}
}
