package com.lp.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.ReadRegReviewResultsDAO;
import com.lp.model.AcademicYear;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegQuestions;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;
@RemoteProxy(name = "studentReadRegService")
public class ReadRegReviewResultsServiceImpl implements ReadRegReviewResultsService {
	@Autowired
	private ReadRegReviewResultsDAO resultsDAO;	
	
	@Override
	public List<ReadRegActivityScore> getActivitiesToBeReviewed(long studentId, AcademicYear academicYear, String sortBy){
		return resultsDAO.getActivitiesToBeReviewed(studentId, academicYear,sortBy);		
	}
	
	@Override
	public List<Student> getRRStudents(long gradeId,  long teacherId){
		return resultsDAO.getRRStudents(gradeId, teacherId);
	}
	
	@Override
	public List<Student> getRRStudents(long gradeId){
		return resultsDAO.getRRStudents(gradeId);
	}
	
	@Override
	public List<Student> getRRStudentsByGrade(long gradeId, AcademicYear academicYear, String gradedStatus){
		return resultsDAO.getRRStudentsByGrade(gradeId, academicYear, gradedStatus);
	}
	
	@Override
	public List<Student> getRRStudentsByYear(long gradeId, AcademicYear academicYear, long teacherId, String gradedStatus){
		return resultsDAO.getRRStudentsByYear(gradeId, academicYear, teacherId, gradedStatus);
	}
	
	@Override
	public List<ReadRegActivityScore> getGradedActivities(long gradeId,String sortBy, long teacherId, String startDate, String endDate){
		return resultsDAO.getGradedActivities(gradeId,sortBy,teacherId,startDate, endDate);	
	}
	
	@Override
	public List<ReadRegActivityScore> getUnGradedActivities(long gradeId,String sortBy, AcademicYear academicYear){
		  return resultsDAO.getUnGradedActivities(gradeId,sortBy, academicYear);	
	}

	@Override
	public boolean saveScore(long scoreId, long score, long readRegActivityScoreId, long pageRange, long activityValue,String actApproveStat,String teacherComment,String approveId) {		
		long pointsEarned = pageRange*activityValue*score;
		if(approveId.equalsIgnoreCase("book")){
			return resultsDAO.saveScore(readRegActivityScoreId, scoreId, pointsEarned,"approved",teacherComment);
		}
		else{
			return resultsDAO.saveScore(readRegActivityScoreId, scoreId, pointsEarned,actApproveStat,teacherComment);
		}
	}	
	@Override
	public List<ReadRegQuestions> getReadRegQuestions(long studentId, long titleId){
		return resultsDAO.getReadRegQuestions(studentId, titleId);
		
	}
	@Override
	public List<ReadRegAnswers> getStudentQuiz(long studentId, long titleId){
		return resultsDAO.getStudentQuiz(studentId, titleId);
		
	}
	
	@Override
	public List<ReadRegMaster> getAllAddedBooksByGrade(long gradeId, long teacherId){
		return resultsDAO.getAllAddedBooksByGrade(gradeId, teacherId);
	}
	
	@Override
	public boolean updateAprrovalStatus(ReadRegMaster readRegMaster) {
	try{
		if(readRegMaster.getApproved()==WebKeys.LP_REJECTED)
		resultsDAO.setActivityApprovalStatus(readRegMaster);
		resultsDAO.updateAprrovalStatus(readRegMaster);
	  
	}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	 return true;
	}
	
	@Override
	public List<ReadRegActivityScore> getUnGradedActivities(long gradeId,String sortBy,long teacherId, AcademicYear academicYear){
		
		  return resultsDAO.getUnGradedActivities(gradeId,sortBy,teacherId, academicYear);	
	}
	@Override
	public List<Student> getRRStudents(long gradeId,String approveStatus){
		List<Student> studLst=new ArrayList<Student>();
		try{
			studLst=resultsDAO.getRRStudents(gradeId,approveStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
		return studLst;
	}
	@Override
	public List<Student> getRRStudents(long gradeId,long teacherId,String approveStatus){
		List<Student> studLst=new ArrayList<Student>();
		try{
			studLst=resultsDAO.getRRStudents(gradeId,teacherId,approveStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
		return studLst;
	}
	@Override
	public List<ReadRegMaster> getAllAddedBooksByGrade(long gradeId, AcademicYear academicYear, long teacherId,long pageId,long rows,String sortBy, String sortOrder, String bookName){
		List<ReadRegMaster> list = null;
		if(pageId>1){  
	       pageId=(pageId-1)*rows;    
	    }
		else if(pageId == 1){
			pageId = 0;
		}
		list=resultsDAO.getAllAddedBooksByGrade(gradeId, academicYear, teacherId,pageId,rows,sortBy,sortOrder,bookName);
		return list;
		
	}
	@Override
	public List<ReadRegMaster> getReadRegBooksByGrade(long gradeId, long teacherId,  AcademicYear academicYear,long pageId,long rows){
		List<ReadRegMaster> list = null;
		if(pageId>1){  
	       pageId=(pageId-1)*rows;    
	    }
		else if(pageId == 1){
			pageId = 0;
		}
		list=resultsDAO.getReadRegBooksByGrade(gradeId, teacherId, academicYear, pageId,rows);
		return list;
	}
	public boolean returnGradedActivity(long readRegActivityScoreId){
		boolean status=false;
		try{
			status=resultsDAO.returnGradedActivity(readRegActivityScoreId);
		}catch(Exception e){
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	
	@Override
	public boolean mergeReadRegDulicateBooks(Long[] readRedTitleIdArray){
		long oldestTitleId = 0;
		
		/* Arrays.sort(readRedTitleIdArray, Collections.reverseOrder());
		oldestTitleId = readRedTitleIdArray[readRedTitleIdArray.length-1];
		readRedTitleIdArray = ArrayUtils.remove(readRedTitleIdArray, readRedTitleIdArray.length-1);
		List readRedTitleIdArrayLt = Arrays.asList(readRedTitleIdArray); */
		
		ReadRegMaster approvedBook = resultsDAO.getApprovedBook(readRedTitleIdArray);
		oldestTitleId = approvedBook.getTitleId();
		List readRedTitleIdArrayLt = Arrays.asList(readRedTitleIdArray);
		int index = readRedTitleIdArrayLt.indexOf(oldestTitleId);
		if(index > -1 ) {
			readRedTitleIdArray = ArrayUtils.remove(readRedTitleIdArray, index);
		}
		readRedTitleIdArrayLt = Arrays.asList(readRedTitleIdArray);
		
		return resultsDAO.mergeReadRegDulicateBooks(readRedTitleIdArray, approvedBook);		
	}
}

