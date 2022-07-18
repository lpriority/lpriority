package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lp.model.Assignment;
import com.lp.model.K1Sets;
import com.lp.model.Teacher;

public interface EarlyLiteracyService {
	public String createK1Tests(K1Sets k1Sets);
	public List<K1Sets> getK1SetsBycsId(long csId);
	public List<K1Sets> getTestSets(long gradId,String setType,long csId,boolean isAutomatic);
	public String assginTest(Assignment assignment,ArrayList<Long> setIdsArray,ArrayList<Long> students);
	public List<Assignment> getAssignedDates(Teacher teacher,long csId,String usedFor,String assignmentType);
	public List<Assignment> getAssignmentTitles(long csId,String usedFor,String assignedDate,String assignmentType);
	public long getSetIdBySetName(String setName,String setType);
	public boolean setGradedMarks(long setId,long studentAssignmentId,String gradedMarks);
	public boolean updateGradeStatus(long studentId,long studentAssignmentId,float percentage,String gradeStatus,long academicId);
	public ArrayList<String> getGradedMarksAsList(long studentAssignmentId,ArrayList<Long> setIdArray);
	public Map<String,Integer> getNoOfAttemptsOnList(long studentId, List<String> setNameArray);
	public String assignAutomaticTest(Assignment assignment,ArrayList<Long> students,ArrayList<Long> setIdsArray,ArrayList<Integer> recordTimeArray);
	public K1Sets getK1SetById(long setId);
	public List<Assignment> getAutoAssignmentTitles(long csId,String usedFor,String assignmentType);
	public String removeSetBysetId(long setId);
}
