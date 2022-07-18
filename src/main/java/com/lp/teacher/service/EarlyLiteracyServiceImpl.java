package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lp.admin.dao.GradesDAO;
import com.lp.model.Assignment;
import com.lp.model.K1Sets;
import com.lp.model.Teacher;
import com.lp.teacher.dao.EarlyLiteracyDAO;


@RemoteProxy(name = "earlyLiteracyService")
public class EarlyLiteracyServiceImpl implements EarlyLiteracyService {
	
	@Autowired
	private EarlyLiteracyDAO earlyLiteracyDAO;
	@Autowired
	private GradesDAO gradesdao;
	
	@Override
	public String createK1Tests(K1Sets k1Sets){
		return earlyLiteracyDAO.createK1Tests(k1Sets);
	}
	
	@Override
	@RemoteMethod
	public List<K1Sets> getK1SetsBycsId(long csId){
		return earlyLiteracyDAO.getK1SetsBycsId(csId);
	}
	
	@Override
	@RemoteMethod
	public List<K1Sets> getTestSets(long gradId,String setType,long csId,boolean isAutomatic){
		long masterGradeId= gradesdao.getMasterGradeIdbyGradeId(gradId);
		return earlyLiteracyDAO.getTestSets(masterGradeId,csId,setType,isAutomatic);
	}
	
	@Override
	@Transactional(readOnly = false)
	public String assginTest(Assignment assignment,ArrayList<Long> setIdsArray,ArrayList<Long> students){
		return earlyLiteracyDAO.assginTest(assignment, setIdsArray,students);
	}
	
	@Override
	public List<Assignment> getAssignedDates(Teacher teacher,long csId,String usedFor,String assignmentType){
		return earlyLiteracyDAO.getAssignedDates(teacher, csId, usedFor, assignmentType);
	}
	
	@Override
	public List<Assignment> getAssignmentTitles(long csId,String usedFor,String assignedDate,String assignmentType){
		return earlyLiteracyDAO.getAssignmentTitles(csId, usedFor, assignedDate, assignmentType);
	}
	
	@Override
	public long getSetIdBySetName(String setName,String setType){
		return earlyLiteracyDAO.getSetIdBySetName(setName, setType);
	}
	
	@Override
	public boolean setGradedMarks(long setId,long studentAssignmentId,String gradedMarks){
		return earlyLiteracyDAO.setGradedMarks(setId, studentAssignmentId, gradedMarks);
	}
	
	@Override
	public boolean updateGradeStatus(long studentId, long studentAssignmentId, float percentage, String gradeStatus, long academicId){
		return earlyLiteracyDAO.updateGradeStatus(studentId, studentAssignmentId, percentage, gradeStatus, academicId);
	}
	
	@Override
	@RemoteMethod
	public ArrayList<String> getGradedMarksAsList(long studentAssignmentId,ArrayList<Long> setIdArray){
		ArrayList<String> gradedMarksList = new ArrayList<String>();
		for (Long setId : setIdArray){
			String gradedMarks = earlyLiteracyDAO.getGradedMarksAsList(studentAssignmentId, setId);
			if(gradedMarks != null && !gradedMarks.equals("")){
				String[] marks =  gradedMarks.split(" ");
				List<String> setMarksList = Arrays.asList(marks);
				gradedMarksList.addAll(setMarksList);
			}
		}
		return gradedMarksList;
	}
	
	@Override
	@RemoteMethod
	public Map<String,Integer> getNoOfAttemptsOnList(long studentId, List<String> setNameArray){
		return earlyLiteracyDAO.getNoOfAttemptsOnList(studentId, setNameArray);
	}
	
	@Override
	public String assignAutomaticTest(Assignment assignment,ArrayList<Long> students, ArrayList<Long> setIdsArray,ArrayList<Integer> recordTimeArray) {
		return earlyLiteracyDAO.assignAutomaticTest(assignment,students,setIdsArray, recordTimeArray);
	}
	@Override
	public K1Sets getK1SetById(long setId){
		return earlyLiteracyDAO.getK1SetById(setId);
	}
	
	@Override
	@RemoteMethod
	public List<Assignment> getAutoAssignmentTitles(long csId,String usedFor,String assignmentType){
		return earlyLiteracyDAO.getAutoAssignmentTitles(csId, usedFor, assignmentType);
	}
	
	public String removeSetBysetId(long setId){
		return earlyLiteracyDAO.removeSetBysetId(setId);
	}
}