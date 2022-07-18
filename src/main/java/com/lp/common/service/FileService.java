package com.lp.common.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.lp.custom.exception.DataException;
import com.lp.model.Announcements;
import com.lp.model.Folders;
import com.lp.model.Grade;
import com.lp.model.StudentClass;



public interface FileService {

	public Folders saveFolder(Folders folder) throws SQLDataException;
	public List<Folders> getDirectories(String path);
	public Grade getGrade(long gradeId);
	public StudentClass getClass(long classId);
	public long getSectionId(long studentId,long gradeClassId);
	public File[] getFolderFiles(String path) throws DataException;
	public boolean uploadSchoolLogo(MultiValueMap<String, MultipartFile> map ) throws DataException;
	public boolean uploadProfilePic(MultiValueMap<String, MultipartFile> map) throws DataException;
	public boolean deleteFolder(String fullPath) throws DataException;
	public boolean renameFolder(String fullPath, String renameVal) throws DataException;
	public boolean renameFile(String fullPath, String renameVal) throws DataException;
	public boolean unzip(String zipFilePath, String destDirectory) throws IOException;
	public boolean moveFiles(String zipFilePath, String destDirectory) throws IOException;
	public boolean uploadAnnoucementAttachement(MultiValueMap<String, MultipartFile> map, Announcements annoucement) throws DataException;
	public boolean moveFolderToDestination(File srcfile, File desfile) throws IOException;
}
