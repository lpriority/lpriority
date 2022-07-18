package com.lp.teacher.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.lp.common.service.CommonService;
import com.lp.common.service.SynHandlerService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignStemUnits;
import com.lp.model.AssignmentType;
import com.lp.model.ColumnHeaders;
import com.lp.model.FormativeAssessmentsUnitWise;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.StemAreas;
import com.lp.model.StemCurriculum;
import com.lp.model.StemGradeStrands;
import com.lp.model.StemPaths;
import com.lp.model.StemQuestions;
import com.lp.model.StemStrandConcepts;
import com.lp.model.StemStrategies;
import com.lp.model.StemUnit;
import com.lp.model.StemUnitActivity;
import com.lp.model.StemUnitPerformanceIndicator;
import com.lp.model.SynHandler;
import com.lp.model.SynHistoryHandler;
import com.lp.model.Teacher;
import com.lp.model.UnitStemAreas;
import com.lp.model.UnitStemConcepts;
import com.lp.model.UnitStemContentQuestions;
import com.lp.model.UnitStemStrands;
import com.lp.model.UnitStemStrategies;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Repository("stemCurriculumDAO")
public class StemCurriculumDAOImpl extends CustomHibernateDaoSupport implements StemCurriculumDAO {
		@Autowired
		private SessionFactory sessionFactory;
		private JdbcTemplate jdbcTemplate;
		@Autowired
		HttpSession ses;
		@Autowired
		HttpServletRequest request;
		@Autowired
		private CommonService commonService;
		@Autowired
		private SynHandlerService synHandlerService;

		@Autowired
		public void setdataSource(DataSource datasource) {
			this.jdbcTemplate = new JdbcTemplate(datasource);

		}
		@SuppressWarnings("unchecked")
		@Override
		public List<StemPaths> getStemPaths(){
			List<StemPaths> stemPaths = null;
			try {
				
				stemPaths = (List<StemPaths>) getHibernateTemplate().find(
						"from StemPaths");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return stemPaths;
		}
		@SuppressWarnings("unchecked")
		@Override
		public StemPaths getStemPaths(long stemPathId){
			List<StemPaths> stemPaths = new ArrayList<StemPaths>();
			try {
				stemPaths = (List<StemPaths>) getHibernateTemplate().find("from StemPaths where stemPathId=" + stemPathId + "");

			} catch (Exception e) {
				logger.error("Error in getStemPaths() of StemCurriculumDAOImpl" + e);
			}
			if (stemPaths.size() > 0)
				return stemPaths.get(0);
			else
				return new StemPaths();
		}
		@Override
		public String saveStemUnits(StemUnit stemUnit) {
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(stemUnit);
				tx.commit();
				session.close();
				return WebKeys.SUCCESS;
			} catch (HibernateException e) {
				logger.error("Error in CreateClasses() of AdminDAOImpl" + e);
				e.printStackTrace();
				return WebKeys.LP_FAILED;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public StemUnit getStemUnitByStemUnitId(long stemUnitId){
			List<StemUnit> stemUnitLt = new ArrayList<StemUnit>();
			try {
				stemUnitLt = (List<StemUnit>) getHibernateTemplate().find("from StemUnit where stemUnitId="+stemUnitId);
			} catch (Exception e) {
				logger.error("Error in getStemUnitByStemUnitId() of StemCurriculumDAOImpl" + e);
			}
			if (stemUnitLt.size() > 0)
				return stemUnitLt.get(0);
			else
				return new StemUnit();
			
		}
		
		
		@Override
		public boolean saveEssentialQues(List<StemQuestions> stemQuestions) {
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				for(StemQuestions sq : stemQuestions) {
					session.saveOrUpdate(sq);
				}							
				tx.commit();
				session.close();
				return true;
			} catch (HibernateException e) {
				logger.error("Error in saveEssentialQues() of AdminDAOImpl" + e);
				return false;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemGradeStrands> getGradeStrands(long masterGradeId,String stemArea, long stateId){
			List<StemGradeStrands> stemGradeStrandsLt = new ArrayList<StemGradeStrands>();
			try {
				stemGradeStrandsLt = (List<StemGradeStrands>) getHibernateTemplate().find("from StemGradeStrands where masterGrades.masterGradesId="+ masterGradeId+" and stemAreas.stemArea='"+stemArea+"' and states.stateId="+ stateId);
			} catch (Exception e) {
				logger.error("Error in getGradeStrands() of StemCurriculumDAOImpl" + e);
			}
			return stemGradeStrandsLt;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public StemGradeStrands getGradeStrandBystemGradeStrands(long stemGradeStrandId){
			List<StemGradeStrands> stemGradeStrandsLt = new ArrayList<StemGradeStrands>();
			try {
				stemGradeStrandsLt = (List<StemGradeStrands>) getHibernateTemplate().find("from StemGradeStrands where stemGradeStrandId="+stemGradeStrandId);
			} catch (Exception e) {
				logger.error("Error in getGradeStrandBystemGradeStrands() of StemCurriculumDAOImpl" + e);
			}
			return stemGradeStrandsLt.get(0);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemStrandConcepts> getStrandConcepts(long stemGradeStrandId){
			List<StemStrandConcepts> stemStrandConceptsLt = new ArrayList<StemStrandConcepts>();
			try {
				stemStrandConceptsLt = (List<StemStrandConcepts>) getHibernateTemplate().find("from StemStrandConcepts where stemGradeStrands.stemGradeStrandId="+ stemGradeStrandId);
			} catch (Exception e) {
				logger.error("Error in getStrandConcepts() of StemCurriculumDAOImpl" + e);
			}
			return stemStrandConceptsLt;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public StemStrandConcepts getStrandConceptsBystemStrandConceptId(long stemStrandConceptId){
			List<StemStrandConcepts> stemStrandConceptsLt = new ArrayList<StemStrandConcepts>();
			try {
				stemStrandConceptsLt = (List<StemStrandConcepts>) getHibernateTemplate().find("from StemStrandConcepts where stemStrandConceptId="+ stemStrandConceptId);
			} catch (Exception e) {
				logger.error("Error in getStrandConceptsBystemStrandConceptId() of StemCurriculumDAOImpl" + e);
			}
			return stemStrandConceptsLt.get(0);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean checkExistsStemUnit(long gradeClassId,StemUnit stemUnit){
			List<StemUnit> stemUnits = new ArrayList<StemUnit>();
			try {
				if(stemUnit.getStemUnitId() != null)
					stemUnits = (List<StemUnit>) getHibernateTemplate().find("from StemUnit where stemUnitName='"+stemUnit.getStemUnitName()+"' and gradeClasses.gradeClassId="+gradeClassId+" and stemUnitId not in("+stemUnit.getStemUnitId()+")");
				else
					stemUnits = (List<StemUnit>) getHibernateTemplate().find("from StemUnit where stemUnitName='"+stemUnit.getStemUnitName()+"' and gradeClasses.gradeClassId="+gradeClassId);
			} catch (Exception e) {
				logger.error("Error in checkExistsStemUnit() of StemCurriculumDAOImpl" + e);
			}
			if(stemUnits.size()>0)
				return true;
			else
				return false;
			}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemAreas> getStemAreas(String type){
			List<StemAreas> stemAreasLt = new ArrayList<StemAreas>();
			try {
				stemAreasLt = (List<StemAreas>) getHibernateTemplate().find("from StemAreas where isOtherStem='"+type+"'");
			} catch (Exception e) {
				logger.error("Error in getStemAreas() of StemCurriculumDAOImpl" + e);
			}
			return stemAreasLt;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public StemAreas getStemAreasByStemAreaId(long stemAreaId){
			List<StemAreas> stemAreasLt = new ArrayList<StemAreas>();
			try {
				stemAreasLt = (List<StemAreas>) getHibernateTemplate().find("from StemAreas where stemAreaId="+stemAreaId);
			} catch (Exception e) {
				logger.error("Error in getStemAreasByStemAreaId() of StemCurriculumDAOImpl" + e);
			}
			return stemAreasLt.get(0);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public UnitStemStrands getUnitStemStrandsByUnitStemStrandsId(long stemGradeStrandsId, long unitStemAreaId){
			List<UnitStemStrands> unitStemStrandsLt = new ArrayList<UnitStemStrands>();
			try {
				unitStemStrandsLt = (List<UnitStemStrands>) getHibernateTemplate().find("from UnitStemStrands where stemGradeStrands.stemGradeStrandId="+stemGradeStrandsId+ " and "
						+ "unitStemAreas.unitStemAreasId="+unitStemAreaId);
			} catch (Exception e) {
				logger.error("Error in getUnitStemStrandsByUnitStemStrandsId() of StemCurriculumDAOImpl" + e);
			}
			if(!unitStemStrandsLt.isEmpty())
				return unitStemStrandsLt.get(0);
			else
				return new UnitStemStrands();
		}
		
		@Override
		public long saveStrandConcept(long stemGradeStrandId,String narrative, ArrayList<Long> strandConceptIdArr, String mode, long unitStemAreaId){
			long unitStemStrandsId = 0;
			try {
					String query_for_unit_stem_strands = "select * from unit_stem_strands where stem_grade_strand_id="+stemGradeStrandId+" and unit_stem_areas_id="+unitStemAreaId+"";
					SqlRowSet rs =  jdbcTemplate.queryForRowSet(query_for_unit_stem_strands);
					if(!rs.next()){
						String unit_stem_strands = "insert into unit_stem_strands(stem_grade_strand_id,narrative, unit_stem_areas_id) values(?,?,?)";
						jdbcTemplate.update(unit_stem_strands, stemGradeStrandId, narrative,unitStemAreaId);
						unitStemStrandsId = jdbcTemplate.queryForObject("select last_insert_id()", new Object[] {}, Integer.class); //jdbcTemplate.queryForInt("select last_insert_id()");
					}
					else{
						String updateQuery = "update unit_stem_strands set narrative='"+narrative+"' where stem_grade_strand_id="+stemGradeStrandId+" and unit_stem_areas_id="+unitStemAreaId+"";
						jdbcTemplate.update(updateQuery);
						unitStemStrandsId = rs.getLong(1);
					}
					if(unitStemStrandsId > 0){
						deleteUnitStemConceptsByUnitStemStrandsId(unitStemStrandsId);
						for (Long strandConceptId : strandConceptIdArr) {
							String query_for_unit_stem_concepts = "insert into unit_stem_concepts(unit_stem_strands_id,stem_str_cpt_id) values(?,?)";
							jdbcTemplate.update(query_for_unit_stem_concepts, unitStemStrandsId, strandConceptId);
						}
					}else{
						unitStemStrandsId = 0;
					}
			} catch (Exception e) {
				logger.error("Error in saveStrandConcept() of StemCurriculumDAOImpl" + e);
				unitStemStrandsId = 0;
			}
			return unitStemStrandsId;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitStemStrands> getUnitStemStrandsByStemUnitId(long stemUnitId, String type) {
			List<UnitStemStrands> unitStemStrandsLt = new ArrayList<UnitStemStrands>();
			String otherSTEM = "";
			if(type.equalsIgnoreCase("main")){
				otherSTEM = WebKeys.STEM_NO;
			}else if(type.equalsIgnoreCase("additional")){
				otherSTEM = WebKeys.STEM_YES;
			}
			try {
				unitStemStrandsLt = (List<UnitStemStrands>) getHibernateTemplate().find("from UnitStemStrands where stemUnit.stemUnitId="+stemUnitId+" and stemAreas.isOtherStem='"+otherSTEM+"'");

			} catch (Exception e) {
				logger.error("Error in getStemContentQuesByStemUnitId() of StemCurriculumDAOImpl" + e);
			}
			return unitStemStrandsLt;
		}
		
		@SuppressWarnings({ "unchecked" })
		@Override
		public List<UnitStemStrands> getUnitStemStrandsByUnitId(long stemUnitId) {
			List<UnitStemStrands> unitStemStrands = new ArrayList<UnitStemStrands>();
			try {
				unitStemStrands = (List<UnitStemStrands>) getHibernateTemplate().find("from UnitStemStrands where stemUnit.stemUnitId=" + stemUnitId);

			} catch (Exception e) {
				logger.error("Error in getUnitStemStrandsByUnitId() of StemCurriculumDAOImpl" + e);
			}
			return unitStemStrands;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemUnit> getStemUnits(long gradeClassId,long pathId,UserRegistration userReg){
			List<StemUnit> stemUnitLst = new ArrayList<StemUnit>();
			try {
				stemUnitLst = (List<StemUnit>) getHibernateTemplate().find("from StemUnit where gradeClasses.gradeClassId="+ gradeClassId+" "
						+ "and stemPaths.stemPathId="+pathId+" and userRegistration.regId="+userReg.getRegId()+"");
			} catch (Exception e) {
				logger.error("Error in getStemUnits() of StemCurriculumDAOImpl" + e);
			}
			return stemUnitLst;
		}
		
		@Override
		public boolean saveStemUnitActivities(
				List<StemUnitActivity> stemUnitActivities, List<MultipartFile> files) {
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				for(StemUnitActivity stemAct : stemUnitActivities) {
					if(!stemAct.getActivityDesc().equalsIgnoreCase("") || !stemAct.getActivityLink().equalsIgnoreCase("")){
						session.saveOrUpdate(stemAct);
					}
				}							
				tx.commit();
				session.close();
				saveStemFileInFileSystem(files,stemUnitActivities);
				return true;
			} catch (HibernateException e) {
				logger.error("Error in saveStemContentActivities() of StemCurriculumDAOImpl" + e);
				return false;
			}
		}
		
		private void saveStemFileInFileSystem(List<MultipartFile> files, List<StemUnitActivity> stemUnitActivities) {
			BufferedOutputStream bs = null;
			String stemActFileFullPath ="";
			int index = 0;
		    byte[] bis;
		    UserRegistration userReg = (UserRegistration) ses.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try {
				String stemPath = WebKeys.STEM_FILES;
				String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
				for(MultipartFile file : files){
					if(!file.isEmpty()){
						bis = file.getBytes();
						stemActFileFullPath = uploadFilePath +File.separator+ stemPath+File.separator
								+stemUnitActivities.get(index).getStemActivityId();
						File f = new File(stemActFileFullPath);
						if (!f.isDirectory()) {
							 f.setExecutable(true, false);
				             f.mkdirs();
				           
				        } 
						File[] fileList = f.listFiles();
						if(fileList.length > 0){
							for(File subFile:fileList){
								subFile.delete();
							}
						}
						stemActFileFullPath = stemActFileFullPath +File.separator+file.getOriginalFilename();
						FileOutputStream fs = new FileOutputStream(stemActFileFullPath);
			            bs = new BufferedOutputStream(fs);
			            bs.write(bis);
			            bs.close();						
					}
					++index;
				}
			} catch (IOException e) {
				logger.error("Error in saveStemFileInFileSystem() of AdminDAOImpl" + e);
				e.printStackTrace();
			}
		}
		
		public List<Long> saveStemContentQuestions(long unitStemAreaId, List<String> contentQuestionsLt){
		List<Long> contentQuestionsIdLt = new ArrayList<Long>();
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for (String contentQue : contentQuestionsLt) {
				String query_for_unit_stem_cont_quesid = "select unit_stem_cont_quesid from unit_stem_content_quests where content_question='"+contentQue+"' and unit_stem_area_id="+unitStemAreaId;
				long unit_stem_cont_quesid=0;
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query_for_unit_stem_cont_quesid);
				while (rs.next()) {
					unit_stem_cont_quesid = rs.getInt(1);
				}
               if(unit_stem_cont_quesid == 0){
					UnitStemAreas unitStemAreas = new UnitStemAreas();
					unitStemAreas.setUnitStemAreasId(unitStemAreaId);
					UnitStemContentQuestions unitStemContentQuestions = new UnitStemContentQuestions();
					unitStemContentQuestions.setUnitStemAreas(unitStemAreas);
					unitStemContentQuestions.setContentQuestion(contentQue);
					session.saveOrUpdate(unitStemContentQuestions);
					contentQuestionsIdLt.add(unitStemContentQuestions.getUnitStemContentQuesId());
               }
			}
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveStemContentQuestions() of StemCurriculumDAOImpl" + e);
			contentQuestionsIdLt.clear();
			return contentQuestionsIdLt;
		}
		return contentQuestionsIdLt;
		}
		
		@SuppressWarnings("unchecked")
		public List<UnitStemConcepts> getUnitStemConceptsByUnitStemStrandsId(long unitStemStrandsId){
			List<UnitStemConcepts> unitStemConceptsLt = new ArrayList<UnitStemConcepts>();
			try {
				unitStemConceptsLt = (List<UnitStemConcepts>) getHibernateTemplate().find("from UnitStemConcepts where unitStemStrands.unitStemStrandsId="+unitStemStrandsId);
			} catch (Exception e) {
				logger.error("Error in getUnitStemConceptsByUnitStemStrandsId() of StemCurriculumDAOImpl" + e);
			}
			return unitStemConceptsLt;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<StemQuestions> getStemEssenUnitQues(long stemUnitId) {
			List<StemQuestions> stemEssenUnitQuess = new ArrayList<StemQuestions>();
			try {
				stemEssenUnitQuess = (List<StemQuestions>) getHibernateTemplate().find("from StemQuestions where stemUnit.stemUnitId="+stemUnitId
						+" and (stemQuestionsType.stemQuesType='Essential Questions' or stemQuestionsType.stemQuesType='Unit Questions')"
						+ " order by stemQuestionsType.stemQuesTypeId");
			} catch (Exception e) {
				logger.error("Error in getStemEssenUnitQues() of StemCurriculumDAOImpl" + e);
			}
			return stemEssenUnitQuess;
		}
		@SuppressWarnings("rawtypes")
		@Override
		public boolean removeEssUnitQues(long unitQuesId) {
			try{
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				String hql = "DELETE from StemQuestions WHERE stemQuestionId = :stemQuestionId";
				Query query = session.createQuery(hql);
				query.setParameter("stemQuestionId", unitQuesId);
			    query.executeUpdate();						
				tx.commit();
				session.close();
			}catch (HibernateException e) {
				logger.error("Error in removeEssUnitQues() of StemCurriculumDAOImpl" + e);
				throw new DataException("Error in removeEssUnitQues() of StemCurriculumDAOImpl",e);
			}
			return true;
		}
		
		@Override
		public boolean updateContentQuestions(UnitStemContentQuestions unitStemContentQuestion){
		   try{
			    if(unitStemContentQuestion.getUnitStemContentQuesId() > 0){
			    	String updateQuery = "update unit_stem_content_quests set content_question='"+unitStemContentQuestion.getContentQuestion()+"' where unit_stem_cont_quesid="+unitStemContentQuestion.getUnitStemContentQuesId();
					jdbcTemplate.update(updateQuery);
			    }else{
			    	Session session = sessionFactory.openSession();
					Transaction tx = (Transaction) session.beginTransaction();
					session.saveOrUpdate(unitStemContentQuestion);
					tx.commit();
					session.close();
			    }
			}catch (HibernateException e) {
				logger.error("Error in updateContentQuestions() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		
		@SuppressWarnings("unchecked")
		public UnitStemContentQuestions getStemContentQuesByContentQuesId(long unitStemContentQuesId) {
			List<UnitStemContentQuestions> unitStemContentQuestionsLt = new ArrayList<UnitStemContentQuestions>();
			try {
				unitStemContentQuestionsLt = (List<UnitStemContentQuestions>) getHibernateTemplate().find("from UnitStemContentQuestions where unitStemContentQuesId=" + unitStemContentQuesId); 

			} catch (Exception e) {
				logger.error("Error in getStemContentQuesByContentQuesId() of StemCurriculumDAOImpl" + e);
			}
			return unitStemContentQuestionsLt.get(0);
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public boolean deleteContentQuestions(long unitStemContentQuesId){
			try{
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				String hql = "DELETE from UnitStemContentQuestions WHERE unitStemContentQuesId = :unitStemContentQuesId";
				Query query = session.createQuery(hql);
				query.setParameter("unitStemContentQuesId", unitStemContentQuesId);
			    query.executeUpdate();						
				tx.commit();
				session.close();
			}catch (HibernateException e) {
				logger.error("Error in deleteContentQuestions() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemUnitActivity> getStemSharedActivities(Grade grade,	long stemAreaId, long stemUnitId) {
			List<StemUnitActivity> stemSharedActivities = new ArrayList<StemUnitActivity>();
			try {
				stemSharedActivities = (List<StemUnitActivity>) getHibernateTemplate().find("from StemUnitActivity where "
						+ "unitStemAreas.stemUnit.gradeClasses.grade.masterGrades.masterGradesId=" + grade.getMasterGrades().getMasterGradesId() 
						+" and unitStemAreas.stemAreas.stemAreaId ="+stemAreaId
						+" and unitStemAreas.stemUnit.isShared = '"+WebKeys.STEM_YES+"'"
						+" and referActivityId = 0 and unitStemAreas.stemUnit.stemUnitId !="+stemUnitId);

			} catch (Exception e) {
				logger.error("Error in getStemSharedActivities() of StemCurriculumDAOImpl" + e);
			}
			return stemSharedActivities;
		}
		
		public boolean deleteUnitStemConceptsByUnitStemStrandsId(long unitStemStrandsId){
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			try {
				List<UnitStemConcepts> unitStemConceptsLt = getUnitStemConceptsByUnitStemStrandsId(unitStemStrandsId);
				for (UnitStemConcepts unitStemConcepts : unitStemConceptsLt) {
					session.delete(unitStemConcepts);
				}
				tx.commit();
				session.close();
			} catch (Exception e) {
				session.close();
				logger.error("Error in deleteUnitStemConceptsByUnitStemStrandsId() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public boolean deleteUnitStemArea(long unitStemAreasId){
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();

			try {
				UnitStemAreas unitStemArea =  null;	
				Query query = session.createQuery("FROM UnitStemAreas where unitStemAreasId="+unitStemAreasId);
				List<UnitStemAreas> unitStemStrands = query.list();
				unitStemArea = unitStemStrands.get(0);
				for(UnitStemStrands unitStemStrandsObj : unitStemArea.getUnitStemStrandsLt()){
					List<UnitStemConcepts> unitStemConceptsLt = unitStemStrandsObj.getUnitStemConceptsLt();
					for (UnitStemConcepts unitStemConcepts : unitStemConceptsLt) {
						session.delete(unitStemConcepts);
					}
					session.delete(unitStemStrandsObj);
				}
				List<UnitStemContentQuestions> unitStemContentQuestionsLt = unitStemArea.getUnitStemContentQuestionsLt();
				for (UnitStemContentQuestions unitStemContentQuestion : unitStemContentQuestionsLt) {
					session.delete(unitStemContentQuestion);
				}
				List<StemUnitActivity> unitActivities = unitStemArea.getStemUnitActivitiesLt();
				for(StemUnitActivity stemUnitActivity : unitActivities){
					session.delete(stemUnitActivity);
				}			
				session.delete(unitStemArea);
				tx.commit();
				session.close();
			} catch (Exception e) {
				tx.rollback();
				session.close();
				logger.error("Error in deleteUnitStemArea() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemStrategies> getStemStrategies(){
			List<StemStrategies> stemStrategiesLt = new ArrayList<StemStrategies>();
			try {
				stemStrategiesLt = (List<StemStrategies>) getHibernateTemplate().find("from StemStrategies");

			} catch (Exception e) {
				logger.error("Error in getStemStrategies() of StemCurriculumDAOImpl" + e);
			}
			return stemStrategiesLt;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitStemStrategies> getUnitStemStrategies(long stemUnitId){
			List<UnitStemStrategies> unitStemStrategLt = new ArrayList<UnitStemStrategies>();
			try {
				unitStemStrategLt = (List<UnitStemStrategies>) getHibernateTemplate().find("from UnitStemStrategies where stemUnit.stemUnitId="+stemUnitId+"");

			} catch (Exception e) {
				logger.error("Error in getUnitStemStrategies() of StemCurriculumDAOImpl" + e);
			}
			return unitStemStrategLt;	
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public StemStrategies getStemStrategies(long stemStrategiesId){
			List<StemStrategies> unitStrategiesLt = new ArrayList<StemStrategies>();
			try {
				unitStrategiesLt = (List<StemStrategies>) getHibernateTemplate().find("from StemStrategies where stemStrategiesId="+stemStrategiesId);
			} catch (Exception e) {
				logger.error("Error in getStemStrategies() of StemCurriculumDAOImpl" + e);
			}
			if (unitStrategiesLt.size() > 0)
				return unitStrategiesLt.get(0);
			else
				return new StemStrategies();
		}
		
		@Override
		public boolean saveUnitStemStrategies(long stemUnitId,List<UnitStemStrategies> strategiesList){
			
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				for (UnitStemStrategies unitStrateg: strategiesList) {
					session.saveOrUpdate(unitStrateg);
				}
				tx.commit();
				session.close();
			} catch (HibernateException e) {
				logger.error("Error in saveStemContentQuestions() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		@SuppressWarnings("unchecked")
		@Override
		public boolean checkExistsStemStrategies(long stemUnitId,long stemStrategiesId){
			List<UnitStemStrategies> unitstemStrategList = new ArrayList<UnitStemStrategies>();
			try {
				unitstemStrategList = (List<UnitStemStrategies>) getHibernateTemplate().find("from UnitStemStrategies where stemUnit.stemUnitId="+stemUnitId+" and stemStrategies.stemStrategiesId="+stemStrategiesId+"");

			} catch (Exception e) {
				logger.error("Error in checkExistsStemStrategies() of StemCurriculumDAOImpl" + e);
			}
			if (unitstemStrategList.size() > 0)
				return true;
			else
				return false;
		}
		@SuppressWarnings("rawtypes")
		@Override
		public boolean removeUnitStemStrategies(long stemUnitId,long stemStrategiesId){
			try{
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				String hql = "DELETE from UnitStemStrategies WHERE stemUnit.stemUnitId ="+stemUnitId+" and stemStrategies.stemStrategiesId="+stemStrategiesId+"";
				Query query = session.createQuery(hql);
				query.executeUpdate();						
				tx.commit();
				session.close();
			}catch (HibernateException e) {
				logger.error("Error in removeUnitStemStrategies() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemUnit> getAllStemUnits(long masterGradeId, UserRegistration userReg){
			List<StemUnit> stemUnitLt = new ArrayList<StemUnit>();
			try {
				stemUnitLt = (List<StemUnit>) getHibernateTemplate().find("from StemUnit where "//gradeClasses.grade.masterGrades.masterGradesId="+ masterGradeId +" and"
						+"  userRegistration.regId != "+userReg.getRegId()+" and isShared ='"+WebKeys.STEM_YES+"' and userRegistration.school.states.stateId= "+userReg.getSchool().getStates().getStateId());
			} catch (Exception e) {
				logger.error("Error in getAllStemUnits() of StemCurriculumDAOImpl" + e);
			}
			return stemUnitLt;
		}
		
		@Override
		public List<StemUnit> getCopyStemUnits(List<StemUnit> stemUnitLt,UserRegistration userReg, long gradeId, long classId) throws DataException{

			long gradeClassId = commonService.getGradeClassId(gradeId, classId);
			GradeClasses gradeClasses = new GradeClasses();
			gradeClasses.setGradeClassId(gradeClassId);
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			try {
				StemPaths stemPath = getStemPaths(2);
				for (StemUnit stemUnit : stemUnitLt) {
					long stem_unit_id = 0;
					String query = "select stem_unit_id from stem_unit where stem_unit_name='Copy of "+stemUnit.getStemUnitName()+"' and created_by="+userReg.getRegId();
					SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
					while (rs.next()) {
						stem_unit_id = rs.getLong(1);
				     }
					if(stem_unit_id > 0){
						stemUnit.setStemUnitId((long) 0);
						continue;
					}else{
						stemUnit.setStemUnitName("Copy of "+stemUnit.getStemUnitName());
						stemUnit.setSrcStemUnitId(stemUnit.getStemUnitId());
						stemUnit.setStemUnitId((long) 0);
						stemUnit.setIsShared(WebKeys.STEM_NO);
						stemUnit.setUserRegistration(userReg);
						stemUnit.setStemPaths(stemPath);
						stemUnit.setGradeClasses(gradeClasses);
						session.save(stemUnit);
						
						if(stemUnit.getStemUnitId() > 0){
							List<StemQuestions> stemQuestionsLt = stemUnit.getStemQuestionsLt();
							for (StemQuestions stemQuestions : stemQuestionsLt) {
								stemQuestions.setStemQuestionId((long) 0);
								stemQuestions.setStemUnit(stemUnit);
								session.save(stemQuestions);
							}
							List<UnitStemAreas> unitStemAreas = stemUnit.getUniStemAreasLt();
							for(UnitStemAreas unitStemArea : unitStemAreas){
								unitStemArea.setUnitStemAreasId((long) 0);
								unitStemArea.setStemUnit(stemUnit);
								session.save(unitStemArea);
								List<UnitStemStrands> unitStemStrandsLt =  unitStemArea.getUnitStemStrandsLt();
								for (UnitStemStrands unitStemStrand : unitStemStrandsLt) {
									unitStemStrand.setUnitStemAreas(unitStemArea);
									unitStemStrand.setUnitStemStrandsId((long) 0);
									session.save(unitStemStrand);
									
									if(unitStemStrand.getUnitStemStrandsId() > 0){
										List<UnitStemConcepts> unitStemConceptsLt = unitStemStrand.getUnitStemConceptsLt();
										for (UnitStemConcepts unitStemConcept : unitStemConceptsLt) {
											unitStemConcept.setUnitStemConceptsId((long) 0);
											unitStemConcept.setUnitStemStrands(unitStemStrand);
											session.save(unitStemConcept);
										}
									}	
								}								
								List<UnitStemContentQuestions> unitStemContentQuestionsLt = unitStemArea.getUnitStemContentQuestionsLt();
								for (UnitStemContentQuestions unitStemContentQuestion : unitStemContentQuestionsLt) {
									unitStemContentQuestion.setUnitStemAreas(unitStemArea);
									unitStemContentQuestion.setUnitStemContentQuesId((long) 0);
									session.save(unitStemContentQuestion);
								}
								List<StemUnitActivity> stemUnitActivities = unitStemArea.getStemUnitActivitiesLt();
									for(StemUnitActivity stemUnitActivity : stemUnitActivities){
										if(stemUnitActivity.getStemActivityId() > 0){
											stemUnitActivity.setUnitStemAreas(unitStemArea);
											stemUnitActivity.setStemActivityId((long) 0);
												session.save(stemUnitActivity);
										}
									}
							}
							
							
							List<UnitStemStrategies> unitStemStrategiesLt = stemUnit.getUnitStemStrategiesLt();
							for (UnitStemStrategies unitStemStrategies : unitStemStrategiesLt) {
								unitStemStrategies.setUnitStemStrategiesId((long) 0);
								unitStemStrategies.setStemUnit(stemUnit);
								session.save(unitStemStrategies);
							}
							
							List<StemUnitPerformanceIndicator> stemUnitPerformanceIndicatorLt = stemUnit.getStemUnitPerformanceIndicatorLt();
							for (StemUnitPerformanceIndicator stemUnitPerIndicator : stemUnitPerformanceIndicatorLt) {
								stemUnitPerIndicator.setStemUnitPerformanceIndicatorId((long) 0);
								stemUnitPerIndicator.setStemUnit(stemUnit);
								session.save(stemUnitPerIndicator);
							}
							List<FormativeAssessmentsUnitWise> formativeAssessments = stemUnit.getFormativeAssessmentsLt();
							for (FormativeAssessmentsUnitWise fAssessments : formativeAssessments) {
								fAssessments.setFormativeAssessmentsUnitWiseId((long) 0);
								fAssessments.setStemUnit(stemUnit);
								session.save(fAssessments);
							}
							
						}
					}
				}
				tx.commit();
				session.close();
			} catch (Exception e) {
				logger.error("Error in getCopyStemUnits() of StemCurriculumDAOImpl" + e);
			}
			return stemUnitLt;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public StemCurriculum getTeacherStemCurriculum(long gradeId, long classId,long pathId,Teacher teacher,UserRegistration adminReg)
		{
			StemCurriculum curriculum = new StemCurriculum();
			List<StemUnit> stemUnits = null;
			try {
				stemUnits = (List<StemUnit>) getHibernateTemplate().find(
						"FROM StemUnit where gradeClasses.grade.gradeId=" + gradeId
								+ "" + " and gradeClasses.studentClass.classId="
								+ classId + " and userRegistration.regId in ("
								+ teacher.getUserRegistration().getRegId() + ", "
								+ adminReg.getRegId() + ") order by stemUnitId");
				/*for (int unitNo = 0; unitNo < stemUnits.size(); unitNo++) {
					List<UnitStemContentActivities> activitiesList = getStemUnitActivities(stemUnits.get(unitNo).getStemUnitId());
					stemUnits.get(unitNo).setActivityList(activitiesList);
				}*/
				curriculum.setStemUnits(stemUnits);
			} catch (HibernateException e) {
				e.printStackTrace();
			}
			return curriculum;
		}
		@Override
		public boolean assignStemCurriculum(List<AssignStemUnits> assignStemUnitLst){
			
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				for (AssignStemUnits assignStemUnit: assignStemUnitLst) {
					session.saveOrUpdate(assignStemUnit);
				}
				tx.commit();
				session.close();
			} catch (HibernateException e) {
				logger.error("Error in assignStemCurriculum() of StemCurriculumDAOImpl" + e);
				return false;
			}
			return true;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean chkAlreadyAssignStemUnit(long stemUnitId){
			List<AssignStemUnits> assignStemUnitsList = new ArrayList<AssignStemUnits>();
			try {
				assignStemUnitsList = (List<AssignStemUnits>) getHibernateTemplate().find("from AssignStemUnits where stemUnit.stemUnitId="+stemUnitId+"");

			} catch (Exception e) {
				logger.error("Error in chkAlreadyAssignStemUnit() of StemCurriculumDAOImpl" + e);
			}
			if (assignStemUnitsList.size() > 0)
				return true;
			else
				return false;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<AssignStemUnits> getAssignedStemUnits(long csId) {
			List<AssignStemUnits> assignStemUnitsLt = new ArrayList<AssignStemUnits>();
			try {
				assignStemUnitsLt = (List<AssignStemUnits>) getHibernateTemplate().find("from AssignStemUnits where classStatus.csId="+csId);
			} catch (Exception e) {
				logger.error("Error in getAssignedStemUnits() of StemCurriculumDAOImpl" + e);
			}
			return assignStemUnitsLt;
		}
		
		@Override
		public boolean autoSaveEssenUnitQues(StemQuestions stemQuest) {
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(stemQuest);
				tx.commit();
				session.close();
				return true;
			} catch (HibernateException e) {
				logger.error("Error in autoSaveEssenUnitQues() of AdminDAOImpl" + e);
				return false;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public AssignmentType getAssignmentType(String assignmentType){
			List<AssignmentType> assignType = new ArrayList<AssignmentType>();
			try {
				assignType = (List<AssignmentType>) getHibernateTemplate().find("from AssignmentType where assignmentType='" + assignmentType + "'");

			} catch (Exception e) {
				logger.error("Error in getAssignmentType() of StemCurriculumDAOImpl" + e);
			}
			if (assignType.size() > 0)
				return assignType.get(0);
			else
				return new AssignmentType();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public ColumnHeaders getColumnHeaders(String columnName){
			List<ColumnHeaders> columnHeader = new ArrayList<ColumnHeaders>();
			try {
				columnHeader = (List<ColumnHeaders>) getHibernateTemplate().find("from ColumnHeaders where columnName='" + columnName + "'");

			} catch (Exception e) {
				logger.error("Error in getColumnHeaders() of StemCurriculumDAOImpl" + e);
			}
			if (columnHeader.size() > 0)
				return columnHeader.get(0);
			else
				return new ColumnHeaders();
		}
		
		@SuppressWarnings("unchecked")
		public List<AssignStemUnits> getAssignedStemUnitsBystemUnitId(long stemUnitId) {
			List<AssignStemUnits> assignStemUnitsLt = new ArrayList<AssignStemUnits>();
			try {
				assignStemUnitsLt = (List<AssignStemUnits>) getHibernateTemplate().find("from AssignStemUnits where stemUnit.stemUnitId="+stemUnitId);
			} catch (Exception e) {
				logger.error("Error in getAssignedStemUnitsBystemUnitId() of StemCurriculumDAOImpl" + e);
			}
			return assignStemUnitsLt;
		}
		
		public String deleteStemUnit(StemUnit stemUnit){
			UserRegistration userReg = (UserRegistration) ses.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String status = "";
			try {
				if(stemUnit.getStemUnitId() > 0){
					List<AssignStemUnits> assignStemUnitsLt = getAssignedStemUnitsBystemUnitId(stemUnit.getStemUnitId());
					if(assignStemUnitsLt.size() > 0){
						session.close();
						status = WebKeys.ALREADY_ASSIGNED;
					}else{
						List<StemQuestions> stemQuestionsLt = stemUnit.getStemQuestionsLt();
						for (StemQuestions stemQuestions : stemQuestionsLt) {
							session.delete(stemQuestions);
						}
						List<UnitStemAreas> unitStemAreas = stemUnit.getUniStemAreasLt();
						for(UnitStemAreas unitStemArea : unitStemAreas){
							List<UnitStemStrands> unitStemStrandsLt =  unitStemArea.getUnitStemStrandsLt();
							for (UnitStemStrands unitStemStrand : unitStemStrandsLt) {
								if(unitStemStrand.getUnitStemStrandsId() > 0){
									List<UnitStemConcepts> unitStemConceptsLt = unitStemStrand.getUnitStemConceptsLt();
									for (UnitStemConcepts unitStemConcept : unitStemConceptsLt) {
										session.delete(unitStemConcept);
									}
								 session.delete(unitStemStrand);
								}
							}
							List<UnitStemContentQuestions> unitStemContentQuestionsLt = unitStemArea.getUnitStemContentQuestionsLt();
							for (UnitStemContentQuestions unitStemContentQuestion : unitStemContentQuestionsLt) {								
								session.delete(unitStemContentQuestion);
							}
							List<StemUnitActivity> stemUnitActivities = unitStemArea.getStemUnitActivitiesLt();
							for(StemUnitActivity stemUnitActivity : stemUnitActivities){
								session.delete(stemUnitActivity);
							}
							session.delete(unitStemArea);
						}
						List<UnitStemStrategies> unitStemStrategiesLt = stemUnit.getUnitStemStrategiesLt();
						for (UnitStemStrategies unitStemStrategies : unitStemStrategiesLt) {
							session.delete(unitStemStrategies);
						}
						List<StemUnitPerformanceIndicator> stemUnitPerformanceIndicatorLt = stemUnit.getStemUnitPerformanceIndicatorLt();
						for (StemUnitPerformanceIndicator stemUnitPerIndicator : stemUnitPerformanceIndicatorLt) {
							session.delete(stemUnitPerIndicator);
						}
						List<FormativeAssessmentsUnitWise> formativeAssessments = stemUnit.getFormativeAssessmentsLt();
						for (FormativeAssessmentsUnitWise fAssessments : formativeAssessments) {
							session.delete(fAssessments);
						}
                        if(stemUnit.getIsShared().equalsIgnoreCase(WebKeys.STEM_YES)){
                        	SynHandler synHandler = synHandlerService.getSynHandler(stemUnit.getStemUnitId(), userReg.getRegId());
                        	List<SynHistoryHandler> synHistoryHandlerLt = synHandler.getSynHistoryHandlerLt();
                        	for (SynHistoryHandler synHistoryHandler : synHistoryHandlerLt) {
                        		 jdbcTemplate.update("delete from syn_history_handler where syn_history_handler_id="+synHistoryHandler.getSynHistoryHandlerId());
							}
                        	 jdbcTemplate.update("delete from syn_handler where syn_handler_id="+synHandler.getSynHandlerId());
                        }
						session.delete(stemUnit);
						tx.commit();
						session.close();
						status = WebKeys.SUCCESS;
					}
				}
			} catch (Exception e) {
				session.close();
				logger.error("Error in deleteStemUnit() of AdminDAOImpl" + e);
				status = WebKeys.FAILED;
			}
			return status;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemUnitActivity> getStemUnitActivities(long unitStemAreaId) {
			List<StemUnitActivity> stemUnitActivities = new ArrayList<StemUnitActivity>();
			try {
				stemUnitActivities = (List<StemUnitActivity>) getHibernateTemplate().find("from StemUnitActivity where unitStemAreas.unitStemAreasId ="+unitStemAreaId);

			} catch (Exception e) {
				logger.error("Error in getStemUnitActivities() of StemCurriculumDAOImpl" + e);
			}
			return stemUnitActivities;
		}
		

		@Override
		public long saveUnitStemAreas(UnitStemAreas unitStemAreas){
			try {
				getHibernateTemplate().saveOrUpdate(unitStemAreas);
				return unitStemAreas.getUnitStemAreasId();
			} catch (Exception e) {
				logger.error("Error in getUnitStemStrandsByUnitStemStrandsId() of StemCurriculumDAOImpl" + e);
				return -1;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public UnitStemAreas getUnitStemAreas(long unitStemAreasId){
			List<UnitStemAreas> unitStemAreas = Collections.emptyList();
			try {
				unitStemAreas = (List<UnitStemAreas>) getHibernateTemplate().find("FROM UnitStemAreas where unitStemAreasId="+unitStemAreasId);
			} catch (Exception e) {
				logger.error("Error in getUnitStemStrandsByUnitStemStrandsId() of StemCurriculumDAOImpl" + e);
				return null;
			}
			if(!unitStemAreas.isEmpty())
				return unitStemAreas.get(0);
			else 
				return new UnitStemAreas();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<StemUnit> getAllSharedStemUnits(long masterGradeId, UserRegistration userReg){
			List<StemUnit> stemUnitLt = new ArrayList<StemUnit>();
			try {
				stemUnitLt = (List<StemUnit>) getHibernateTemplate().find("from StemUnit where gradeClasses.grade.masterGrades.masterGradesId="+ masterGradeId
						+" and isShared ='"+WebKeys.STEM_YES+"'");
			} catch (Exception e) {
				logger.error("Error in getAllSharedStemUnits() of StemCurriculumDAOImpl" + e);
			}
			return stemUnitLt;
		}
}