package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.AdminDAO;
import com.lp.admin.dao.GradesDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignStemUnits;
import com.lp.model.AssignmentType;
import com.lp.model.ColumnHeaders;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
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
import com.lp.teacher.dao.CommonDAO;
import com.lp.teacher.dao.StemCurriculumDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "stemCurriculumService")
public class StemCurriculumServiceImpl implements StemCurriculumService {
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private GradesDAO gradeDAO;
	@Autowired
	private CommonDAO commonDAO;
	
	static final Logger logger = Logger.getLogger(StemCurriculumServiceImpl.class);

	@Autowired
	HttpSession session;
	@Autowired
	private StemCurriculumDAO stemCurriculumDAO;

	@Override
	@RemoteMethod
	public List<StemPaths> getStemPaths() {
		try{
			return stemCurriculumDAO.getStemPaths();
		}catch(DataException e){
			logger.error("Error in getStemPaths() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getStemPaths() of StemCurriculumServiceImpl", e);
		}
	}
	@Override
	public String saveStemUnits(long gradeId, long classId,long stemPathId,StemUnit stemUnit, UserRegistration userReg,String mode)
			throws DataException {
		String status = "";
		try {
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			GradeClasses gradeClass = adminDAO.getGradeClass(gradeClassId);
			if(stemUnit.getStemUnitName() != null){
				boolean chkStatus=false;
				chkStatus=stemCurriculumDAO.checkExistsStemUnit(gradeClassId,stemUnit);
				if(!chkStatus){
					stemUnit.setGradeClasses(gradeClass);
					stemUnit.setStemPaths(stemCurriculumDAO.getStemPaths(stemPathId));
					stemUnit.setUserRegistration(userReg);
					status = stemCurriculumDAO.saveStemUnits(stemUnit);
				}else{
					status = WebKeys.LP_UNIT_ALREADY_EXISTS;
				}
			}
			
		} catch (DataException e) {
			logger.error("Error in saveStemUnits() of  StemCurriculumServiceImpl" + e);
			e.printStackTrace();
		}

		return status;
	}
	
	@Override
	@RemoteMethod
	public StemUnit getStemUnitByStemUnitId(long stemUnitId){
	try{
		return stemCurriculumDAO.getStemUnitByStemUnitId(stemUnitId);
	}catch(DataException e){
		logger.error("Error in getStemUnitByStemUnitId() of  StemCurriculumServiceImpl");
		throw new DataException(
				"Error in getStemUnitByStemUnitId() of StemCurriculumServiceImpl", e);
	}
	}
	
	@Override
	public boolean saveEssentialQues(List<StemQuestions> stemQuestions) {
		boolean status = false;
		try {
			status = stemCurriculumDAO.saveEssentialQues(stemQuestions);
			
		} catch (DataException e) {
			logger.error("Error in saveEssentialQues() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in saveEssentialQues() of StemCurriculumServiceImpl", e);
		}
		return status;
	}
	
	@Override
	@RemoteMethod
	public List<StemGradeStrands> getGradeStrands(long gradeId,String stemArea){
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long stateId = userReg.getSchool().getStates().getStateId();
		return stemCurriculumDAO.getGradeStrands(gradeId, stemArea, stateId);
	}
	
	public StemGradeStrands getGradeStrandBystemGradeStrands(long stemGradeStrandId){
		return stemCurriculumDAO.getGradeStrandBystemGradeStrands(stemGradeStrandId);
	}
	
	@Override
	@RemoteMethod
	public List<StemStrandConcepts> getStrandConcepts(long stemGradeStrandId){
		return stemCurriculumDAO.getStrandConcepts(stemGradeStrandId);
	}
	
	@Override
	public StemStrandConcepts getStrandConceptsBystemStrandConceptId(long stemStrandConceptId){
		return stemCurriculumDAO.getStrandConceptsBystemStrandConceptId(stemStrandConceptId);
	}
	
	@Override
	public List<StemAreas> getStemAreas(String type){
		return stemCurriculumDAO.getStemAreas(type);
	}
	
	@Override
	public StemAreas getStemAreasByStemAreaId(long stemAreaId){
		return stemCurriculumDAO.getStemAreasByStemAreaId(stemAreaId);
	}
	
	@Override
	public long saveStrandConcept(long stemGradeStrandId,String narrative, ArrayList<Long> strandConceptIdArr, String mode, long unitStemAreaId){
		return stemCurriculumDAO.saveStrandConcept(stemGradeStrandId,narrative, strandConceptIdArr, mode, unitStemAreaId);
	}
	
	@Override
	@RemoteMethod
	public UnitStemStrands getUnitStemStrandsByUnitStemStrandsId(long stemGradeStrandsId, long unitStemAreaId){
		return stemCurriculumDAO.getUnitStemStrandsByUnitStemStrandsId(stemGradeStrandsId, unitStemAreaId);
	}
		
	@Override
	@RemoteMethod
	public List<UnitStemAreas> getStemUnitAreas(long stemUnitId) {
		List<UnitStemAreas> stemAreas = new ArrayList<UnitStemAreas>();
		try {			
			StemUnit stemUnit = stemCurriculumDAO.getStemUnitByStemUnitId(stemUnitId);
			stemAreas = stemUnit.getUniStemAreasLt();			
			
		} catch (DataException e) {
			logger.error("Error in getStemUnitAreas() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in getStemUnitAreas() of StemCurriculumServiceImpl", e);
		}		
		return stemAreas;
	}
	
	@Override
	public List<StemUnit> getStemUnits(long gradeId,long classId,long pathId,UserRegistration userReg) throws DataException{
		try{
			long gradeClassId = adminDAO.getGradeClassId(gradeId, classId);
			return stemCurriculumDAO.getStemUnits(gradeClassId,pathId,userReg);
		}catch(DataException e){
			logger.error("Error in getStemUnits() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getStemUnits() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	public boolean saveStemUnitActivities(List<StemUnitActivity> stemUnitActivities,List<MultipartFile> files) {
		boolean status = false;
		int loop = 0;
		try {
			for(MultipartFile file : files){
				if(!file.isEmpty()){
					stemUnitActivities.get(loop).setFileName(file.getOriginalFilename());
				}				
				++loop;
			}
			status = stemCurriculumDAO.saveStemUnitActivities(stemUnitActivities,files);
			
		} catch (DataException e) {
			logger.error("Error in saveStemContentActivities() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in saveStemContentActivities() of StemCurriculumServiceImpl", e);
		}
		return status;
	}
	
	@Override
	public List<Long> saveStemContentQuestions(long unitStemStrandsId, List<String> contentQuestionsLt){
		return stemCurriculumDAO.saveStemContentQuestions(unitStemStrandsId, contentQuestionsLt);
	}
	
	@Override
	@RemoteMethod
	public List<UnitStemStrands> getUnitStemStrandsByStemUnitId(long stemUnitId, String type){
		return stemCurriculumDAO.getUnitStemStrandsByStemUnitId(stemUnitId, type);
	}

	@Override
	public List<StemQuestions> getStemEssenUnitQues(long stemUnitId) {
		return stemCurriculumDAO.getStemEssenUnitQues(stemUnitId);
	}

	@Override
	public boolean removeEssUnitQues(long unitQuesId) {
		return stemCurriculumDAO.removeEssUnitQues(unitQuesId);
	}
	
	@Override
	public boolean updateContentQuestions(UnitStemContentQuestions unitStemContentQuestion){
		return stemCurriculumDAO.updateContentQuestions(unitStemContentQuestion);
	}
	
	@Override
	public boolean deleteContentQuestions(long contentQuesId){
		return stemCurriculumDAO.deleteContentQuestions(contentQuesId);
	}
	
	@Override
	public List<StemUnitActivity> getStemSharedActivities(long gradeId, long stemAreaId, long stemUnitId) {
		List<StemUnitActivity> stemSharedActivities = Collections.emptyList();
		try {
			Grade grade = gradeDAO.getGrade(gradeId);
			stemSharedActivities = stemCurriculumDAO.getStemSharedActivities(grade, stemAreaId, stemUnitId);
			
		} catch (DataException e) {
			logger.error("Error in getStemSharedActivities() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in getStemSharedActivities() of StemCurriculumServiceImpl", e);
		}
		return stemSharedActivities;
	}
	
	@Override
	@RemoteMethod
	public boolean deleteUnitStemArea(long unitStemAreasId){
		return stemCurriculumDAO.deleteUnitStemArea(unitStemAreasId);
	}
	
	@Override
	public List<StemStrategies> getStemStrategies(){
		List<StemStrategies> stemStrategiesLt = new ArrayList<StemStrategies>();
		try {
			
			stemStrategiesLt = stemCurriculumDAO.getStemStrategies();
			
		} catch (DataException e) {
			logger.error("Error in getStemStrategies() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in getStemStrategies() of StemCurriculumServiceImpl", e);
		}
		return stemStrategiesLt;
	}
	
	public List<UnitStemStrategies> getUnitStemStrategies(long stemUnitId){
		List<UnitStemStrategies> unitStemStemStrategLt = new ArrayList<UnitStemStrategies>();
		try {
			
			unitStemStemStrategLt = stemCurriculumDAO.getUnitStemStrategies(stemUnitId);
			
		} catch (DataException e) {
			logger.error("Error in getStemSharedActivities() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in getStemSharedActivities() of StemCurriculumServiceImpl", e);
		}
		return unitStemStemStrategLt;
	}
	@Override
	public StemStrategies getStemStrategies(long stemStrategiesId){
	try{
		return stemCurriculumDAO.getStemStrategies(stemStrategiesId);
	}catch(DataException e){
		logger.error("Error in getStemStrategies() of  StemCurriculumServiceImpl");
		throw new DataException(
				"Error in getStemStrategies() of StemCurriculumServiceImpl", e);
	}
	}
	@Override
	public boolean saveUnitStemStrategies(long stemUnitId,List<UnitStemStrategies> strategiesIds){
		try{
			return stemCurriculumDAO.saveUnitStemStrategies(stemUnitId,strategiesIds);
		}catch(DataException e){
			logger.error("Error in saveUnitStemStrategies() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in saveUnitStemStrategies() of StemCurriculumServiceImpl", e);
		}
	}
	@Override
	public boolean checkExistsStemStrategies(long stemUnitId,long stemStrategiesId){
		try{
			return stemCurriculumDAO.checkExistsStemStrategies(stemUnitId,stemStrategiesId);
		}catch(DataException e){
			logger.error("Error in checkExistsStemStrategies() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in checkExistsStemStrategies() of StemCurriculumServiceImpl", e);
		}
	}
	@Override
	public boolean removeUnitStemStrategies(long stemUnitId,long stemStrategiesId){
		try{
			return stemCurriculumDAO.removeUnitStemStrategies(stemUnitId,stemStrategiesId);
		}catch(DataException e){
			logger.error("Error in checkExistsStemStrategies() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in checkExistsStemStrategies() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	public List<StemUnit> getAllStemUnits(long gradeId,UserRegistration userReg) throws DataException{
		try{
			long masterGradeId=gradeDAO.getMasterGradeIdbyGradeId(gradeId);
			return stemCurriculumDAO.getAllStemUnits(masterGradeId,userReg);
		}catch(DataException e){
			logger.error("Error in getStemUnits() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getStemUnits() of StemCurriculumServiceImpl", e);
		}
	}
	
	public List<StemUnit> getCopyStemUnits(List<StemUnit> stemUnitLt,UserRegistration userReg, long gradeId, long classId) throws DataException{
		return stemCurriculumDAO.getCopyStemUnits(stemUnitLt,userReg,gradeId,classId);
	}
	@Override
	public StemCurriculum getTeacherStemCurriculum(long gradeId, long classId,long pathId,Teacher teacher) 
	{
		UserRegistration adminReg = commonDAO.getAdminByTeacher(teacher.getUserRegistration());
		return stemCurriculumDAO.getTeacherStemCurriculum(gradeId, classId,pathId,teacher,adminReg);
	}
	@Override
	public boolean assignStemCurriculum(List<AssignStemUnits> assignStemUnitLst){
		try{
			return stemCurriculumDAO.assignStemCurriculum(assignStemUnitLst);
		}catch(DataException e){
			logger.error("Error in assignStemCurriculum() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in assignStemCurriculum() of StemCurriculumServiceImpl", e);
		}
	}
	@Override
	public boolean chkAlreadyAssignStemUnit(long stemUnitId){
		try{
			return stemCurriculumDAO.chkAlreadyAssignStemUnit(stemUnitId);
		}catch(DataException e){
			logger.error("Error in chkAlreadyAssignStemUnit() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in chkAlreadyAssignStemUnit() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	@RemoteMethod
	public List<AssignStemUnits> getAssignedStemUnits(long csId){
		try{
			return stemCurriculumDAO.getAssignedStemUnits(csId);
		}catch(DataException e){
			logger.error("Error in getAssignedStemUnits() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getAssignedStemUnits() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	public boolean autoSaveEssenUnitQues(StemQuestions stemQuest) {
		boolean status = false;
		try {
			
			status = stemCurriculumDAO.autoSaveEssenUnitQues(stemQuest);
			
		} catch (DataException e) {
			logger.error("Error in autoSaveEssenUnitQues() of  StemCurriculumServiceImpl" + e);
			throw new DataException(
					"Error in autoSaveEssenUnitQues() of StemCurriculumServiceImpl", e);
		}
		return status;
	}
	
	@Override
	public AssignmentType getAssignmentType(String assignmentType){
		try{
			return stemCurriculumDAO.getAssignmentType(assignmentType);
		}catch(DataException e){
			logger.error("Error in getAssignmentType() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getAssignmentType() of StemCurriculumServiceImpl", e);
		}
	}
	@Override
	public ColumnHeaders getColumnHeaders(String columnName){
		try{
			return stemCurriculumDAO.getColumnHeaders(columnName);
		}catch(DataException e){
			logger.error("Error in getColumnHeaders() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getColumnHeaders() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	public String deleteStemUnit(StemUnit stemUnit){
		try{
			return stemCurriculumDAO.deleteStemUnit(stemUnit);
		}catch(DataException e){
			logger.error("Error in deleteStemUnit() of  StemCurriculumServiceImpl");
			throw new DataException("Error in deleteStemUnit() of StemCurriculumServiceImpl", e);
		}
	}

	@Override
	public List<StemUnitActivity> getStemUnitActivities(long unitStemAreaId) throws Exception {
		List<StemUnitActivity> stemUnitActivities = new ArrayList<StemUnitActivity>();
		try {			
			stemUnitActivities = stemCurriculumDAO.getStemUnitActivities(unitStemAreaId);			
		} catch (Exception e) {
			logger.error("Error in getStemUnitActivities() of  StemCurriculumServiceImpl" + e);
			throw new Exception(
					"Error in getStemUnitActivities() of StemCurriculumServiceImpl", e);
		}		
		return stemUnitActivities;
	}
		
	@Override
	@RemoteMethod
	public long saveUnitStemAreas(long stemUnitId, long stemAreaId){
		StemUnit stemUnit = new StemUnit();
		stemUnit.setStemUnitId(stemUnitId);
		StemAreas stemAreas = new StemAreas();
		stemAreas.setStemAreaId(stemAreaId);
		UnitStemAreas unitStemAreas = new UnitStemAreas();
		unitStemAreas.setStemUnit(stemUnit);
		unitStemAreas.setStemAreas(stemAreas);	
		return stemCurriculumDAO.saveUnitStemAreas(unitStemAreas);
	}
	
	public List<StemUnit> getAllSharedStemUnits(long gradeId, UserRegistration userReg) throws DataException{
		try{
			long masterGradeId=gradeDAO.getMasterGradeIdbyGradeId(gradeId);
			return stemCurriculumDAO.getAllSharedStemUnits(masterGradeId,userReg);
		}catch(DataException e){
			logger.error("Error in getAllSharedStemUnits() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getAllSharedStemUnits() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	public String autoSaveStemUnits(StemUnit stemUnit) throws DataException{
		try{
			return stemCurriculumDAO.saveStemUnits(stemUnit);
		}catch(DataException e){
			logger.error("Error in getAllSharedStemUnits() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getAllSharedStemUnits() of StemCurriculumServiceImpl", e);
		}
	}
	
	@Override
	public Grade getGrade(long gradeId) throws DataException{
		try{
			return gradeDAO.getGrade(gradeId);
		}catch(DataException e){
			logger.error("Error in getGrade() of  StemCurriculumServiceImpl");
			throw new DataException(
					"Error in getGrade() of StemCurriculumServiceImpl", e);
		}
	}

}
