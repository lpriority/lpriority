package com.lp.model;


import java.util.Date;
import java.util.List;


public class FilesLP implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long fileId;
	private String fileName;
	private String fileType;
	private Date createdDate;
	private List<FilesLP> filesList;
	private String filePath;

	

	public FilesLP() {
	}

	public FilesLP(long fileId) {
		this.fileId = fileId;
	}

	public FilesLP(long fileId, String fileName, String fileType,
			Date createddate) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.createdDate = createddate;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<FilesLP> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<FilesLP> filesList) {
		this.filesList = filesList;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
}
