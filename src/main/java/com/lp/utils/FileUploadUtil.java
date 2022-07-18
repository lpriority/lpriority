package com.lp.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.lp.model.School;
import com.lp.model.UserRegistration;


public class FileUploadUtil {
	
    public static String getServerPath(){
		String path = System.getProperty( WebKeys.LP_TOMCAT_SERVER_PATH );
		return path;
    }
    
    public static String getWepAppsPath() {
		String webAppsPath =  getServerPath()+ File.separator +  WebKeys.LP_TOMCAT_WEBAPPS_PATH;
		File f = new File(webAppsPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
            f.mkdir();             
        } 
		return webAppsPath;
    }
		
    public static String getUploadFilesPath(UserRegistration user,HttpServletRequest request) {
		String fullPath =  getServerPath() +  File.separator+ WebKeys.LP_USERS_FILES +  File.separator+ user.getSchool().getSchoolId() +File.separator+user.getSchool().getAcademicYear().getAcademicYear()+  File.separator+ user.getUser().getUserTypeid() + File.separator+ user.getRegId();
		File f = new File(fullPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
            f.mkdirs();             
        }
		return fullPath;
    }
    
    public static String getUserPath(UserRegistration user,HttpServletRequest request) {
    	String fullPath =  WebKeys.LP_USERS_FILES +  File.separator+ user.getSchool().getSchoolId() +File.separator+user.getSchool().getAcademicYear().getAcademicYear()+  File.separator+ user.getUser().getUserTypeid() + File.separator+ user.getRegId();
		return fullPath;
    }
	
    public static String getSchoolCommonFilesPath(School school, HttpServletRequest request) {
		String fullPath =  getServerPath() +  File.separator+ WebKeys.LP_USERS_FILES +  File.separator+ school.getSchoolId();
		File f = new File(fullPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
            f.mkdirs();             
        }
		return fullPath;
    }
	
	public static String getReadingSkillsPath() {
		String fullPath =  getServerPath() +  File.separator+ WebKeys.LP_USERS_FILES +  File.separator + WebKeys.LP_READING_FILES_FOLDER;
		File f = new File(fullPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
            f.mkdirs();             
        }
		return fullPath;
	}
	
	public static String getLpCommonFilesPath() {
		String fullPath =  getServerPath() +  File.separator+ WebKeys.LP_USERS_FILES;
		File f = new File(fullPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
            f.mkdirs();             
        }
		return fullPath;
	}
	
	public static boolean audioFileUploadToServer(byte[] bis, File file) {
		boolean status = false;
		float sampleRate = 16000;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		try{
			InputStream byteArray = new ByteArrayInputStream(bis); 
			AudioInputStream is =  AudioSystem.getAudioInputStream(byteArray); 
			AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
			AudioInputStream lowResAIS = AudioSystem.getAudioInputStream(format, is);
			AudioSystem.write(lowResAIS, AudioFileFormat.Type.WAVE, file);
			status = true;
		}catch(Exception e){
			status = false;
			e.printStackTrace();
		}
		return status;
	}
	public static String getProfilePicturePath(UserRegistration user) {		
		String fullPath =   getServerPath() +  File.separator+WebKeys.LP_USERS_FILES+  File.separator+ user.getSchool().getSchoolId()+ File.separator +WebKeys.PROFILE_PIC_FOLDER+ File.separator+user.getRegId();
		File f = new File(fullPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
            f.mkdirs();             
        }
		return fullPath;
	}
	
	/*public static String getUpdatedFolderPath(UserRegistration user,HttpServletRequest request) {
		String fullPath =  getServerPath() +  File.separator+ "updated_LPUsersFiles" +  File.separator+ user.getSchool().getSchoolId() +File.separator+user.getSchool().getAcademicYear().getAcademicYear()+  File.separator+ user.getUser().getUserTypeid() + File.separator+ user.getRegId();
		return fullPath;
    }
	
	public static String getUpdatedCommonSchoolFilesPath(School school) {
		String fullPath =  getServerPath() +  File.separator+ "updated_LPUsersFiles" + File.separator+ school.getSchoolId() ;
		return fullPath;
	}
	
	public static String getUpdatedProfilePicturePath(UserRegistration user) {		
		String fullPath =   getServerPath() +  File.separator+"updated_LPUsersFiles"+  File.separator+ user.getSchool().getSchoolId()+ File.separator +WebKeys.PROFILE_PIC_FOLDER+File.separator+user.getUser().getUserTypeid() + File.separator+ user.getRegId();
		return fullPath;
	}*/
	
	public static String getRelativeUserPath(UserRegistration user,HttpServletRequest request) {
	   	String fullPath =  user.getSchool().getSchoolId() +File.separator+user.getSchool().getAcademicYear().getAcademicYear()+  File.separator+ user.getUser().getUserTypeid() + File.separator+ user.getRegId();
		return fullPath;
	}
}
