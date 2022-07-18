package com.lp.common.dao;

import java.io.File;
import java.sql.SQLDataException;

import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.Folders;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("Folders")
public class FileDAOImpl extends CustomHibernateDaoSupport implements FileDAO {

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public Folders saveFolder(Folders folder) throws SQLDataException{
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(folder);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in createAssessments() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in createAssessments() of AssessmentDAOImpl",e);
		}
		return new Folders();
		
	}
	
	@Override
	public boolean deleteFolder(String fullPath) throws DataException{
		boolean status = false;
		try {
		File f = new File(fullPath);
		if (f.isDirectory()) {
		   FileUtils.deleteDirectory(f);
           status = true;
        }
		} catch (Exception e) {
			logger.error("Error in deleteFolder() of FileDAOImpl" + e);
			status = false;
		}
		return status;
	}
	
	public boolean renameFolder(String fullPath, String renameVal) throws DataException {
		boolean status = false;
		File dir = null;
		File newDir = null;
		try {
		String folderPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));
		dir = new File(fullPath);
		newDir = new File(folderPath+File.separator+renameVal);
		if (dir.isDirectory()) {
			dir.renameTo(newDir);
            status = true;
        }
		} catch (Exception e) {
			logger.error("Error in renameFolder() of FileDAOImpl" + e);
			status = false;
		}
		return status;
	}
	
	public boolean renameDir(String fullPath, String oldTitleId, String newTitleId) throws DataException {
		boolean status = false;
		File dir = null;
		File newDir = null;
		try {
		String folderPath = fullPath.replace(oldTitleId, newTitleId);
		dir = new File(fullPath);
		newDir = new File(folderPath);
		if (dir.isDirectory()) {
			dir.renameTo(newDir);
            status = true;
        }
		} catch (Exception e) {
			logger.error("Error in renameFolder() of FileDAOImpl" + e);
			status = false;
		}
		return status;
	}
	
	
	
	public boolean renameFile(String fullPath, String renameVal) throws DataException{
		boolean status = false;
		File existedFile = null;
		File newFile = null;
		try {
		existedFile = new File(fullPath);
		String folderPath = fullPath.substring(0, existedFile.getPath().lastIndexOf(File.separator));
		newFile = new File(folderPath+File.separator+renameVal);
		if(existedFile.renameTo(newFile))
			 status = true;
        else
        	status = false;
		} catch (Exception e) {
			logger.error("Error in renameFile() of FileDAOImpl" + e);
			status = false;
		}
		return status;
	}
	
	
}
