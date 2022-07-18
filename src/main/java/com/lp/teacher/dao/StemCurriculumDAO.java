package com.lp.teacher.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignStemUnits;
import com.lp.model.AssignmentType;
import com.lp.model.ColumnHeaders;
import com.lp.model.Grade;
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


public interface StemCurriculumDAO {
	
	public List<StemPaths> getStemPaths();
	
	public StemPaths getStemPaths(long stemPathId);
	
	public String saveStemUnits(StemUnit newUnit);
	
	public StemUnit getStemUnitByStemUnitId(long stemUnitId);

	public boolean saveEssentialQues(List<StemQuestions> stemQuestions);
	
	public List<StemGradeStrands> getGradeStrands(long masterGradeId, String stemArea, long stateId);
	
	public StemGradeStrands getGradeStrandBystemGradeStrands(long stemGradeStrandId);
	
	public List<StemStrandConcepts> getStrandConcepts(long stemGradeStrandId);
	
	public StemStrandConcepts getStrandConceptsBystemStrandConceptId(long stemStrandConceptId);
	
	public boolean checkExistsStemUnit(long gradeClassId,StemUnit stemUnit);
	
	public List<StemAreas> getStemAreas(String type);
	
	public StemAreas getStemAreasByStemAreaId(long stemAreaId);
	
	public long saveStrandConcept(long stemGradeStrandId,String narrative, ArrayList<Long> strandConceptIdArr, String mode, long unitStemAreaId);
	
	public UnitStemStrands getUnitStemStrandsByUnitStemStrandsId(long stemGradeStrandsId, long unitStemAreaId);
		
	public List<UnitStemStrands> getUnitStemStrandsByUnitId(long stemUnitId);
	
	public List<StemUnit> getStemUnits(long gradeClassId,long pathId,UserRegistration userReg);

	boolean saveStemUnitActivities(List<StemUnitActivity> stemUnitActivity, List<MultipartFile> files);
	
	public List<Long> saveStemContentQuestions(long unitStemStrandsId, List<String> contentQuestionsLt);
	
	public List<UnitStemStrands> getUnitStemStrandsByStemUnitId(long stemUnitId, String type);

	public List<StemQuestions> getStemEssenUnitQues(long stemUnitId);

	public boolean removeEssUnitQues(long unitQuesId);
	
	public boolean updateContentQuestions(UnitStemContentQuestions unitStemContentQuestion);
	
	public boolean deleteContentQuestions(long contentQuesId);
	
	public List<StemUnitActivity> getStemSharedActivities(Grade grade,	long stemAreaId, long stemUnitId);
	
	public boolean deleteUnitStemArea(long unitStemAreasId);
	
    public List<StemStrategies> getStemStrategies();
	
	public List<UnitStemStrategies> getUnitStemStrategies(long stemUnitId);
	
	public StemStrategies getStemStrategies(long stemStrategiesId);
	
    public boolean saveUnitStemStrategies(long stemUnitId,List<UnitStemStrategies> strategiesIds);
	
	public boolean checkExistsStemStrategies(long stemUnitId,long stemStrategiesId);
	
	public boolean removeUnitStemStrategies(long stemUnitId,long stemStrategiesId);

	public List<StemUnit> getAllStemUnits(long masterGradeId, UserRegistration userReg);
	
	public List<StemUnit> getCopyStemUnits(List<StemUnit> stemUnitLt,UserRegistration userReg, long gradeId, long classId) throws DataException;

	public StemCurriculum getTeacherStemCurriculum(long gradeId, long classId,long pathId,Teacher teacher,UserRegistration adminReg);
	
	public boolean assignStemCurriculum(List<AssignStemUnits> assignStemUnitLst);
	
	public boolean chkAlreadyAssignStemUnit(long stemUnitId);
	
	public List<AssignStemUnits> getAssignedStemUnits(long csId);
	
	public boolean autoSaveEssenUnitQues(StemQuestions stemQuest);
	
	public AssignmentType getAssignmentType(String assignmentType);
	
	public ColumnHeaders getColumnHeaders(String columnName);
	
	public String deleteStemUnit(StemUnit stemUnit);

	public List<StemUnitActivity> getStemUnitActivities(long unitStemAreaId);
	
	public long saveUnitStemAreas(UnitStemAreas unitStemAreas);
	
	public UnitStemAreas getUnitStemAreas(long unitStemAreasId);
	
	public List<StemUnit> getAllSharedStemUnits(long masterGradeId, UserRegistration userReg);

}