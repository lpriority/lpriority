package com.lp.common.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.GradesDAO;
import com.lp.admin.dao.StudentClassesDAO;
import com.lp.appadmin.dao.AnnouncementsNEventsDAO;
import com.lp.appadmin.dao.SchoolDAO;
import com.lp.common.dao.FileDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.Announcements;
import com.lp.model.FilesLP;
import com.lp.model.Folders;
import com.lp.model.Grade;
import com.lp.model.StudentClass;
import com.lp.model.UserRegistration;
import com.lp.student.dao.StudentDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Component("fileService")
@RemoteProxy(name = "fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDAO fileDAO;
	@Autowired
	private GradesDAO gradesDAO ;
	@Autowired
	private StudentClassesDAO studentClassesDAO;
	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession session;
	@Autowired
	private SchoolDAO schoolDAO;
	
	@Autowired
	private AnnouncementsNEventsDAO announcementsNEventsDAO;
	
	static final Logger logger = Logger.getLogger(FileServiceImpl.class);

	@Override
	public Folders saveFolder(Folders folder) throws SQLDataException {
		return fileDAO.saveFolder(folder);		
	}
	
	public List<Folders> getDirectories(String path){		
		File file = null;	
		File fileFolder = null;
		File[] fileFolderList = null;
		File[] fileList = null;
		List<Folders> foldersList = new ArrayList<Folders>();	
		fileFolder = new java.io.File(path);       
        fileFolderList = fileFolder.listFiles();      
        if (fileFolderList != null) {
            for (int d1 = 0; d1 < fileFolderList.length; d1++) {
            	List<FilesLP> filesList = new ArrayList<FilesLP>();
           	 	if(fileFolderList[d1].isDirectory()) {           	 		
           	 		Folders folder = new Folders();
           	 		file = new java.io.File(path+File.separator+fileFolderList[d1].getName());
           	 		fileList = file.listFiles();
           	 		for (int f1 = 0; f1 < fileList.length; f1++) {
           	 			if(fileList[f1].isFile()){
           	 				FilesLP fileLP = new FilesLP();
           	 				fileLP.setFileName(fileList[f1].getName());
           	 				fileLP.setCreatedDate(new Date (fileList[f1].lastModified()));
           	 				fileLP.setFilePath(path+fileFolderList[d1].getName()+File.separator+fileList[f1].getName());
           	 				filesList.add(fileLP);           	 				
           	 			}          	 			
           	 		}
           	 		folder.setFoldername(fileFolderList[d1].getName()); 
           	 		folder.setCreateddate(new Date (fileFolderList[d1].lastModified()));
           	 		folder.setFilesList(filesList);
           	 		folder.setFolderpath(path+fileFolderList[d1].getName());
           	 		foldersList.add(folder);           	 		
           		}
           	}  
        }
		return foldersList;		
	}
	
	@Override
	public Grade getGrade(long gradeId){
		return gradesDAO.getGrade(gradeId);
	}
	
	@Override
	public StudentClass getClass(long classId){
		return studentClassesDAO.getClass(classId);
	}
	
	@Override
	public long getSectionId(long studentId,long gradeClassId){	
		return studentDAO.getSectionId(studentId, gradeClassId);
	}

	@Override
	public File[] getFolderFiles(String path) throws DataException {
		File fileFolder = null;
		File[] fileFolderList = null;
		fileFolder = new java.io.File(path);       
        fileFolderList = fileFolder.listFiles();	
		return fileFolderList;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean uploadSchoolLogo(MultiValueMap<String, MultipartFile> map) throws DataException{
		String fileName = null;
		try{
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			if(map != null) {
				Iterator iterator = map.keySet().iterator();
				String folderPath = FileUploadUtil.getSchoolCommonFilesPath(userRegistration.getSchool(), request);
				while(iterator.hasNext()) {
					String str = (String) iterator.next();
					List<MultipartFile> fileList =  map.get(str);
					File newDir = new File(folderPath+File.separator + WebKeys.SCHOOL_LOGO_FOLDER);
					if(!newDir.isDirectory()){
						newDir.mkdirs();
					}
					for(MultipartFile mpf : fileList) {
						fileName = mpf.getOriginalFilename();
						File localFile = new File(folderPath+File.separator + WebKeys.SCHOOL_LOGO_FOLDER+File.separator+ StringUtils.trimAllWhitespace(mpf.getOriginalFilename()));
						if(!localFile.exists()){
							localFile.createNewFile();
						}
						OutputStream out;
						out = new FileOutputStream(localFile);							
						out.write(mpf.getBytes());
						out.close();							
					}
				}			
				userRegistration.getSchool().setLogoLink(WebKeys.SCHOOL_LOGO_FOLDER+File.separator+ StringUtils.trimAllWhitespace(fileName));
				schoolDAO.saveSchool(userRegistration.getSchool());
			}
		}
		catch(Exception e){
			logger.error("error while uploading logo file");	
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean uploadProfilePic(MultiValueMap<String, MultipartFile> map) throws DataException{
		try{
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			if(map != null) {
				Iterator iterator = map.keySet().iterator();
				String folderPath = FileUploadUtil.getProfilePicturePath(userRegistration);
				while(iterator.hasNext()) {
					String str = (String) iterator.next();
					List<MultipartFile> fileList =  map.get(str);					
					for(MultipartFile mpf : fileList) {
						File localFile = new File(folderPath+File.separator+WebKeys.PROFILE_PIC_FILE_NAME);
						localFile.deleteOnExit();
						localFile.createNewFile();
						OutputStream out;
						out = new FileOutputStream(localFile);							
						out.write(mpf.getBytes());
						out.close();							
					}
				}			
			}
		}
		catch(Exception e){
			logger.error("error while uploading logo file");	
			return false;
		}
		return true;
	}
	
	@Override
	public boolean deleteFolder(String fullPath) throws DataException{
		return fileDAO.deleteFolder(fullPath);
	}
	
	@Override
	public boolean renameFolder(String fullPath, String renameVal) throws DataException{
		return fileDAO.renameFolder(fullPath,renameVal);
	}
	
	@Override
	public boolean renameFile(String fullPath, String renameVal) throws DataException{
		return fileDAO.renameFile(fullPath,renameVal);
	}
	
	public boolean unzip(String zipFilePath, String destDirectory) throws IOException{
		boolean status = false;
		ZipInputStream zipIn = null;
		try {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				File checkFile = new File(filePath);
				if(checkFile.exists()){
					String renameVal = checkFile.getName()+"_"+WebKeys.EXPIRED;
					renameFile(filePath, renameVal);
				}
				extractFile(zipIn, filePath);	
			} else {
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
			status = true;
		}catch(IOException e){
			logger.error("Error in renameFile() of FileDAOImpl" + e);
			status = false;
		} finally{
			zipIn.close();
		}
		return status;
	}
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
	
	public boolean moveFiles(String zipFilePath, String destDirectory) throws IOException{
		boolean status = false;
		File fileIn = null;
		try {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		fileIn = new File(zipFilePath);
		//ZipEntry entry = fileIn.getNextEntry();
		if(fileIn.isDirectory()){
			File[] files = fileIn.listFiles();			
			for (File file : files) {
				String filePath = destDirectory + File.separator + file.getName();
				if (!file.isDirectory()) {
					if(file.getName().contains("2015-16"))//
					writeFile(file, filePath);	
				}
			}		
			status = true;
		}
		}catch(IOException e){
			logger.error("Error in renameFile() of FileDAOImpl" + e);
			status = false;
		} finally{
		}
		return status;
	}
	private void writeFile(File file, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		FileInputStream fileInputStream = new FileInputStream(file);
		while ((read = fileInputStream.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
		fileInputStream.close();
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public boolean uploadAnnoucementAttachement(MultiValueMap<String, MultipartFile> map, Announcements announcement) throws DataException{
		String fileName = null;
		try{
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			if(map != null) {
				Iterator iterator = map.keySet().iterator();
				String folderPath = FileUploadUtil.getSchoolCommonFilesPath(userRegistration.getSchool(), request);
				while(iterator.hasNext()) {
					String str = (String) iterator.next();
					List<MultipartFile> fileList =  map.get(str);
					fileName = fileList.get(0).getOriginalFilename();
					if(fileName.length() > 0){
						File newDir = new File(folderPath+File.separator + WebKeys.SCHOOL_ANNOUNCEMENTS_FOLDER + File.separator + announcement.getAnnouncementId());
						if(!newDir.isDirectory()){
							newDir.mkdirs();
						}else{
							newDir.delete();
							newDir.mkdirs();
						}
						File localFile = new File(folderPath+File.separator + WebKeys.SCHOOL_ANNOUNCEMENTS_FOLDER  + File.separator	+ announcement.getAnnouncementId() + File.separator + StringUtils.trimAllWhitespace(fileList.get(0).getOriginalFilename()));
						if(!localFile.exists()){
							localFile.createNewFile();
						}
						OutputStream out;
						out = new FileOutputStream(localFile);							
						out.write(fileList.get(0).getBytes());
						out.close();	
						
						announcement.setFileName(announcement.getAnnouncementId() + File.separator + StringUtils.trimAllWhitespace(fileName));
						announcementsNEventsDAO.saveorUpdateAnnouncements(announcement);
					}
				}	
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("error Upload Annoucement Attachement");	
			return false;
		}
		return true;
	}
	
	public boolean moveFolderToDestination(File srcfile, File desfile){
		boolean status = false;
		try {
			if (!desfile.exists()) 
				desfile.mkdir();
			
			if(srcfile.isDirectory()){
				File[] files = srcfile.listFiles();			
				for (File file : files) {
					String filePath = desfile.getPath() + File.separator + file.getName();
					if (file.isDirectory()) {	
						if (!file.exists())
						  file.mkdir();
						FileUtils.copyDirectory(file, new File(filePath));
					}else{						
						FileUtils.copyFile(file, new File(filePath));
					}
				}		
				status = true;
			}
		}catch(IOException e){
			logger.error("Error in moveFolderToDestination() of FileDAOImpl" + e);
			status = false;
		} finally{
		}
		return status;
	}
	
}