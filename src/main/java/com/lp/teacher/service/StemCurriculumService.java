package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignStemUnits;
import com.lp.model.AssignmentType;
import com.lp.model.ColumnHeaders;
import com.lp.model.Grade;
import com.lp.model.MasterGrades;
import com.lp.model.StemAreas;
import com.lp.model.StemCurriculum;
import com.lp.model.StemGradeStrands;
import com.lp.model.StemPaths;
import com.lp.model.StemQuestions;
import com.lp.model.StemStrandConcepts;
import com.lp.model.StemStrategies;
import com.lp.model.StemUnit;
import com.lp.model.StemUnitActivity;
import com.lp.model.Teacher;
import com.lp.model.UnitStemAreas;
import com.lp.model.UnitStemContentQuestions;
import com.lp.model.UnitStemStrands;
import com.lp.model.UnitStemStrategies;
import com.lp.model.UserRegistration;

public interface StemCurriculumService {
	
	public List<StemPaths> getStemPaths();
	
	public String  saveStemUnits(long gradeId,long classId,long pathId,StemUnit units,UserRegistration userReg,String mode) throws DataException;
	
	public StemUnit getStemUnitByStemUnitId(long stemUnitId);

	public boolean saveEssentialQues(List<StemQuestions> stemQuestions);
	
	public List<StemGradeStrands> getGradeStrands(long gradeId,String stemArea);
	
	public StemGradeStrands getGradeStrandBystemGradeStrands(long stemGradeStrandId);
	
	public List<StemStrandConcepts> getStrandConcepts(long stemGradeStrandId);
	
	public StemStrandConcepts getStrandConceptsBystemStrandConceptId(long stemStrandConceptId);
	
	public List<StemAreas> getStemAreas(String type);
	
	public StemAreas getStemAreasByStemAreaId(long stemAreaId);
	
	public long saveStrandConcept(long stemGradeStrandId,String narrative, ArrayList<Long> strandConceptIdArr,String mode, long unitStemAreaId);
	
	public UnitStemStrands getUnitStemStrandsByUnitStemStrandsId(long unitStemStrandsId, long unitStemAreaId);
		
	public List<UnitStemAreas> getStemUnitAreas(long stemUnitId);
	
	public List<StemUnit> getStemUnits(long gradeId,long classId,long pathId,UserRegistration userReg) throws DataException;

	public boolean saveStemUnitActivities(List<StemUnitActivity> stemUnitActivities, List<MultipartFile> files);
	
	public List<Long> saveStemContentQuestions(long unitStemStrandsId, List<String> contentQuestionsLt);
	
	public List<UnitStemStrands> getUnitStemStrandsByStemUnitId(long stemUnitId, String type);

	public List<StemQuestions> getStemEssenUnitQues(long stemUnitId);

	public boolean removeEssUnitQues(long unitQuesId);
	
	public boolean updateContentQuestions(UnitStemContentQuestions unitStemContentQuestion);
	
	public boolean deleteContentQuestions(long contentQuesId);
	
	public List<StemUnitActivity> getStemSharedActivities(long gradeId, long stemAreaId, long stemUnitId);
  
	public boolean deleteUnitStemArea(long unitStemAreasId);
	
	public List<StemStrategies> getStemStrategies();
		
	public List<UnitStemStrategies> getUnitStemStrategies(long stemUnitId);
	
	public boolean saveUnitStemStrategies(long stemUnitId,List<UnitStemStrategies> strategiesIds);
	
	public StemStrategies getStemStrategies(long stemStrategiesId);
	
	public boolean checkExistsStemStrategies(long stemUnitId,long stemStrategiesId);
	
	public boolean removeUnitStemStrategies(long stemUnitId,long stemStrategiesId);
	
	public List<StemUnit> getAllStemUnits(long gradeId, UserRegistration userReg) throws DataException;
	
	public List<StemUnit> getCopyStemUnits(List<StemUnit> stemUnitLt,UserRegistration userReg, long gradeId, long classId) throws DataException;

    public StemCurriculum getTeacherStemCurriculum(long gradeId, long classId,long pathId,Teacher teacher);
	
	public boolean assignStemCurriculum(List<AssignStemUnits> assignStemUnitLst);
	
	public boolean chkAlreadyAssignStemUnit(long stemUnitId);
	
	public List<AssignStemUnits> getAssignedStemUnits(long csId);
	
	public boolean autoSaveEssenUnitQues(StemQuestions stemQuest);
	
	public AssignmentType getAssignmentType(String assignmentType);
	
	public ColumnHeaders getColumnHeaders(String columnName);
	
	public String deleteStemUnit(StemUnit stemUnit);

	public List<StemUnitActivity> getStemUnitActivities(long unitStemAreaId) throws Exception;
	
	public long saveUnitStemAreas(long stemUnitId, long stemAreaId);
	
	public List<StemUnit> getAllSharedStemUnits(long gradeId, UserRegistration userReg) throws DataException;
	
	public String autoSaveStemUnits(StemUnit stemUnit) throws DataException;
	
	public Grade getGrade(long gradeId) throws DataException;
	
}
