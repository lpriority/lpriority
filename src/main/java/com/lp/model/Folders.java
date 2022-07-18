package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Folders generated by hbm2java
 */
@Entity
@Table(name = "folders")
public class Folders implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long folderId;
	private String foldername;
	private String foldertype;
	private Date createddate;
	@Transient
	private List<Folders> foldersList;
	@Transient
	private List<FilesLP> filesList;
	@Transient
	private String folderpath;

	

	public Folders() {
	}

	public Folders(long folderId) {
		this.folderId = folderId;
	}

	public Folders(long folderId, String foldername, String foldertype,
			Date createddate) {
		this.folderId = folderId;
		this.foldername = foldername;
		this.foldertype = foldertype;
		this.createddate = createddate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "folder_id", unique = true, nullable = false)
	public long getFolderId() {
		return this.folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	@Column(name = "foldername", length = 40)
	public String getFoldername() {
		return this.foldername;
	}

	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	@Column(name = "foldertype", length = 40)
	public String getFoldertype() {
		return this.foldertype;
	}

	public void setFoldertype(String foldertype) {
		this.foldertype = foldertype;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createddate", length = 10)
	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	
	@Transient
	public List<Folders> getFoldersList() {
		return foldersList;
	}

	public void setFoldersList(List<Folders> foldersList) {
		this.foldersList = foldersList;
	}

	@Transient
	public List<FilesLP> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<FilesLP> filesList) {
		this.filesList = filesList;
	}

	@Transient
	public String getFolderpath() {
		return folderpath;
	}

	public void setFolderpath(String folderpath) {
		this.folderpath = folderpath;
	}

}