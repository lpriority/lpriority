package com.lp.common.dao;

import java.sql.SQLDataException;

import com.lp.custom.exception.DataException;
import com.lp.model.Folders;


public interface FileDAO {
	
	public Folders saveFolder(Folders folder) throws SQLDataException;
	public boolean deleteFolder(String fullPath) throws DataException;
	public boolean renameFolder(String fullPath, String renameVal) throws DataException;
	public boolean renameFile(String fullPath, String renameVal) throws DataException;
	public boolean renameDir(String fullPath, String oldTitleId, String newTitleId) throws DataException;
}
