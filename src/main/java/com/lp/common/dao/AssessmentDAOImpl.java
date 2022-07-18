package com.lp.common.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.Lesson;
import com.lp.model.PtaskFiles;
import com.lp.model.Questions;
import com.lp.model.QuestionsList;
import com.lp.model.Rubric;
import com.lp.model.RubricScalings;
import com.lp.model.RubricTypes;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.AssignAssessmentsDAO;
import com.lp.teacher.dao.CommonDAO;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Repository("Assessment")
public class AssessmentDAOImpl extends CustomHibernateDaoSupport implements AssessmentDAO {
	static final Logger logger = Logger.getLogger(AssessmentDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	private AssignAssessmentsDAO assessmentsDAO;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private CommonDAO commonDAO;
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentType> getAssignmentsByassignFor(String usedFor)
			throws DataException {
		List<AssignmentType> assignments = new ArrayList<AssignmentType>();
		try {
			if(!usedFor.equalsIgnoreCase("assessments")){
				//load assignments for RTI and Homeworks
				assignments = (List<AssignmentType>) getHibernateTemplate().find(
						"from AssignmentType where usedFor= 'ALL' or usedFor='"+usedFor+"'");
			}else{
				// load assignments for Assessments
				assignments = (List<AssignmentType>) getHibernateTemplate().find(
						"from AssignmentType where usedFor= 'ALL' or usedFor='homeworks' or usedFor='"+usedFor+"'");
			}
		} catch (DataAccessException e) {
			logger.error("Error in getAssignmentsByassignFor() of AssessmentDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException("Error in getAssignmentsByassignFor() of AssessmentDAOImpl",e);
		}
		
		return assignments;
	}

	@Override
	public boolean createAssessments(List<Questions> questions)
			throws DataException {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(Questions question: questions){
				session.saveOrUpdate(question);
			}
			
			if(questions.size() > 0){
				if(questions.get(0).getAssignmentType().getAssignmentTypeId() == 13){
					createRubrics(questions, session);
					savePerformanceFile(questions, session);
				}
			}
			tx.commit();
			session.close();
		} catch (HibernateException | SQLDataException e) {
			logger.error("Error in createAssessments() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in createAssessments() of AssessmentDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Lesson getLessonById(long lessonId) throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try{
			lessons = (List<Lesson>) getHibernateTemplate().find("from Lesson where lessonId="+lessonId+"");
		}catch (HibernateException e) {
			logger.error("Error in getLessonById() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getLessonById() of AssessmentDAOImpl",e);
		}
		
		if (lessons.size() > 0)
			return lessons.get(0);
		else
			return new Lesson();
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssignmentType getassignmentTypeById(long assessmentTypeId)
			throws DataException {
		List<AssignmentType> assessmentTypeLt = Collections.emptyList();
		AssignmentType assignmentType = new AssignmentType();
		try{
			assessmentTypeLt = (List<AssignmentType>) getHibernateTemplate().find("from AssignmentType where assignmentTypeId="+assessmentTypeId+"");
		}catch (HibernateException e) {
			logger.error("Error in getassignmentTypeById() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getassignmentTypeById() of AssessmentDAOImpl",e);
		}
		if (assessmentTypeLt.size() > 0)
			assignmentType = assessmentTypeLt.get(0);

		return assignmentType;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AssignmentType getAssignmentTypeByAssignmentType(String assignmentType)
			throws DataException {
		List<AssignmentType> assessmentType = Collections.emptyList();
		try{
			assessmentType = (List<AssignmentType>) getHibernateTemplate().find("from AssignmentType where assignmentType= '"+assignmentType+"'");
		}catch (HibernateException e) {
			logger.error("Error in getassignmentTypeById() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getassignmentTypeById() of AssessmentDAOImpl",e);
		}
		if (assessmentType.size() > 0)
			return assessmentType.get(0);
		else
			return new AssignmentType();
	}
	
	@Override
	public JacQuestionFile saveJacQuestioFile(JacQuestionFile jacQuesFile) throws SQLDataException{
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(jacQuesFile);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveJacQuestioFile() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in saveJacQuestioFile() of AssessmentDAOImpl",e);
		}
		
		return new JacQuestionFile();
	}

	
	@Override
	public void saveBulkJacTemplates(List<JacTemplate> jacTemplates) throws SQLDataException{
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			long i = 0;
			for ( JacTemplate jt : jacTemplates ) {
				session.saveOrUpdate(jt);
				i++;
				if ( i % 20 == 0 ) { //20, same as the JDBC batch size
					//flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveBulkJacTemplates() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in saveBulkJacTemplates() of AssessmentDAOImpl",e);
		}
	}	
	
	@Override
	public boolean saveJacTemplate(JacTemplate jacTemplate) throws SQLDataException{
		boolean status = true;
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(jacTemplate);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			 status = false;
			logger.error("Error in saveJacTemplate() of AssessmentDAOImpl" + e);
			e.printStackTrace();
		}
		return status;
	}	

	@Override
	public SubQuestions saveSubQuestion(SubQuestions subQuestion) throws SQLDataException{		
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int count = 0;
			if(subQuestion.getQuestionses() != null){
				for (int i = 0; i < subQuestion.getQuestionses().size(); i++) {
					long assessmentTypeId = subQuestion.getAssignmentType().getAssignmentTypeId();
				    if(assessmentTypeId == 7){
						   if(subQuestion.getQuestionses().get(i).getAnswer() != null || subQuestion.getQuestionses().get(i).getOption1() != null  || subQuestion.getQuestionses().get(i).getOption2() != null || subQuestion.getQuestionses().get(i).getOption3() != null)
							if(!subQuestion.getQuestionses().get(i).getAnswer().equalsIgnoreCase("") || !subQuestion.getQuestionses().get(i).getOption1().equalsIgnoreCase("")  || !subQuestion.getQuestionses().get(i).getOption2().equalsIgnoreCase("") || !subQuestion.getQuestionses().get(i).getOption3().equalsIgnoreCase(""))
								 count =  count+1;
					}else if(assessmentTypeId != 19 || (assessmentTypeId == 19 && subQuestion.getQuestionses().get(i).getQuestion() != null)){
						if (assessmentTypeId != 19 || (assessmentTypeId == 19 && !subQuestion.getQuestionses().get(i).getQuestion().equalsIgnoreCase("")))
							 count =  count+1;
					}
				}
			}
			subQuestion.setNumOfOptions(count);
			session.saveOrUpdate(subQuestion);
			for(Questions question: subQuestion.getQuestionses()){
				long assessmentTypeId = subQuestion.getAssignmentType().getAssignmentTypeId();
				if(assessmentTypeId == 7){
				  if(question.getAnswer() != null  || question.getOption1()!= null  || question.getOption2() != null || question.getOption3() != null)
					if(!question.getAnswer().equalsIgnoreCase("") || !question.getOption1().equalsIgnoreCase("")  || !question.getOption2().equalsIgnoreCase("") || !question.getOption3().equalsIgnoreCase(""))
						 session.saveOrUpdate(question);	
				}else if(assessmentTypeId != 19 || (assessmentTypeId == 19 && question.getQuestion() != null))	 {
					if (assessmentTypeId != 19 || (assessmentTypeId == 19 && !question.getQuestion().equalsIgnoreCase("")))
						 session.saveOrUpdate(question);
				}
			}
			
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveSubQuestion() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in saveSubQuestion() of AssessmentDAOImpl",e);
		}
		return subQuestion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RubricTypes> getRubricTypes() throws SQLDataException {
		List<RubricTypes> rubricTypes = Collections.emptyList();
		try{
			rubricTypes = (List<RubricTypes>) getHibernateTemplate().find("from RubricTypes ORDER BY rubricTypeId ASC");
		}catch (HibernateException e) {
			logger.error("Error in getRubricTypes() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getRubricTypes() of AssessmentDAOImpl",e);
		}
		return rubricTypes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RubricScalings> getRubricScalings() throws SQLDataException {
		List<RubricScalings> rubricScalings = Collections.emptyList();
		try{
			rubricScalings = (List<RubricScalings>) getHibernateTemplate().find("from RubricScalings");
		}catch (HibernateException e) {
			logger.error("Error in getRubricScalings() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getRubricScalings() of AssessmentDAOImpl",e);
		}
		return rubricScalings;
	}
	
	private void createRubrics(List<Questions> questions, Session session) throws SQLDataException{
		try{
			for ( Questions ques : questions ) {
				List<Rubric> rubrics = ques.getRubrics(); 
				for ( Rubric rb : rubrics ) {
					rb.setCreatedBy(ques.getCreatedBy().longValue());
					rb.setQuestions(ques);
					rb.setRubricScalings(ques.getRubrics().get(0).getRubricScalings());
					rb.setRubricTypes(ques.getRubrics().get(0).getRubricTypes());
					session.saveOrUpdate(rb);
				}
			}
		}catch(HibernateException e){
			logger.error("Error in createRubrics() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in createRubrics() of AssessmentDAOImpl",e);
		}
	 }
	
	//Save Performance files in to DB
	private void savePerformanceFile(List<Questions> questions, Session session) throws SQLDataException{
		try{
			for ( Questions ques : questions ) {
				List<PtaskFiles> pfiles = ques.getPtaskFiles(); 
				if(pfiles!=null){
					for ( PtaskFiles pf : pfiles ) {
						pf.setQuestions(ques);
						session.saveOrUpdate(pf);
						//Now save files in file system
						savePTaskFilesInFileSystem(pf, ques.getQuestionId());
					}
				}
			}
		}catch(HibernateException e){
			logger.error("Error in savePerformanceFile() of AssessmentDAOImpl" + e);
			throw new DataException("Error in savePerformanceFile() of AssessmentDAOImpl",e);
		}
	 }
	
	private void savePTaskFilesInFileSystem(PtaskFiles pfiles,long ptaskQuestionId) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String pTaskFileFullPath = uploadFilePath +File.separator+ WebKeys.PERFORMANCE_TESTS+File.separator+ptaskQuestionId;
		//Create dir if doesn't exist
		File f = new File(pTaskFileFullPath);
		if (!f.isDirectory()) {
			 f.setExecutable(true, false);
             f.mkdirs();
        } 
		pTaskFileFullPath = pTaskFileFullPath+File.separator+pfiles.getFilename();
  		try {
  			 byte[] bis =  pfiles.getPfile().getBytes();
  			 FileOutputStream fs = new FileOutputStream(pTaskFileFullPath);
             BufferedOutputStream bs = new BufferedOutputStream(fs);
             bs.write(bis);
             bs.close();
             fs.close();
        } catch (Exception e) {
             e.printStackTrace();
         }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getQuestionsByAssignmentType(long assignmentTypeId,long lessonId, String usedFor) throws SQLDataException {
		List<Questions> questions = new ArrayList<Questions>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			questions = (List<Questions>) getHibernateTemplate().find("from Questions where assignmentType.assignmentTypeId="+assignmentTypeId
					+ " and lesson.lessonId="+lessonId+" and usedFor='"+usedFor+"' and createdBy="+userReg.getRegId() +" and queStatus='active' ");
		}catch (HibernateException e) {
			logger.error("Error in getQuestionsByAssignmentType() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getQuestionsByAssignmentType() of AssessmentDAOImpl",e);
		}
		return questions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getQuestionsByAssignmentType(long assignmentTypeId, String usedFor,long gradeId) throws SQLDataException {
		List<Questions> questions = new ArrayList<Questions>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			questions = (List<Questions>) getHibernateTemplate().find("from Questions where assignmentType.assignmentTypeId="+assignmentTypeId
					+ " and usedFor='"+usedFor+"' and createdBy="+userReg.getRegId()+" and grade.gradeId="+gradeId+" and queStatus='active'");
		}catch (HibernateException e) {
			logger.error("Error in getQuestionsByAssignmentType() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getQuestionsByAssignmentType() of AssessmentDAOImpl",e);
		}
		return questions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QuestionsList getQuestionByQuestionId(long questionId) throws SQLDataException {
		QuestionsList questionsLt = new QuestionsList();
		List<Questions> questionLt = new ArrayList<Questions>();
		try{
			 questionLt = (List<Questions>) getHibernateTemplate().find("from Questions where questionId="+questionId);
			 questionsLt.setQuestions(questionLt);
		}catch (HibernateException e) {
			logger.error("Error in getQuestionByQuestionId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getQuestionByQuestionId() of AssessmentDAOImpl",e);
		}
		return questionsLt;
	}

	@Override
	public boolean updateAssessments(Questions question) throws SQLDataException {
		boolean status = true;
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(question);				
				if(question.getAssignmentType().getAssignmentTypeId()==13){
					List<Rubric> rubrics = new ArrayList<Rubric>(); 
					rubrics = performanceTaskService.getRubrics(question.getQuestionId());
					if(rubrics.size() > 0){
						for(Rubric r: rubrics){
							session.delete(r);
						}	
					}
					for(Rubric r:question.getRubrics()){
						r.setQuestions(question);
						session.merge(r);
					}					
				}
				List<Questions> questions = new ArrayList<Questions>();
				questions.add(question);
				if(question.getAssignmentType().getAssignmentTypeId() == 13){
					savePerformanceFile(questions, session);
				}
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			status = false;
			logger.error("Error in updateAssessments() of AssessmentDAOImpl" + e);
			throw new DataException("Error in updateAssessments() of AssessmentDAOImpl",e);
		}
		return status;		
	}

	@Override
	public boolean removeAssessments(Questions question) throws SQLDataException {
		boolean status = true;
		try {
			Session session1 = sessionFactory.openSession();
			Transaction tx = (Transaction) session1.beginTransaction();
				/*if(question.getAssignmentType().getAssignmentTypeId()== 8){
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
					String benchMarkRecordsFilePath = "";
					if(question.getQuestionId() > 0){
						benchMarkRecordsFilePath = uploadFilePath+File.separator+WebKeys.LP_FLUENCY_READING_TEST+File.separator+question.getQuestionId();
						File file = new File(benchMarkRecordsFilePath);
						if(file.exists()) 
							FileUtils.deleteDirectory(file);
							
					}
				}*/
				if(question.getAssignmentType().getAssignmentTypeId()==13){
					for(Rubric r:question.getRubrics()){
						session1.delete(r);
					}	
					if(question.getPtaskFiles().size() > 0){
						for(PtaskFiles pfile:question.getPtaskFiles()){
							deletePerformancefile(pfile.getFileId());
						}
					}
					tx.commit();
					session1.close();
				/*
				 * jdbcTemplate.update("delete from questions where question_id="+question.
				 * getQuestionId());
				 */
				
				  jdbcTemplate.update("update questions set ques_status='inactive' where question_id="+question.getQuestionId());
				 
				}else{
					if(question.getAssignmentType().getAssignmentTypeId()==8 || question.getAssignmentType().getAssignmentTypeId()==20)
					    jdbcTemplate.update("update questions set ques_status='inactive' where question_id="+question.getQuestionId());
					else
					/* session1.delete(question); */
					jdbcTemplate.update("update questions set ques_status='inactive' where question_id="+question.getQuestionId());
					tx.commit();
					session1.close();
				}
			
		} catch (HibernateException e) {
			status = false;
			logger.error("Error in removeAssessments() of AssessmentDAOImpl" + e);
		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
		return status;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubQuestions> getSubQuestionByAssignmentType(long assignmentTypeId,long lessonId, String usedFor,long gradeId) throws SQLDataException {
		List<SubQuestions> questions = new ArrayList<SubQuestions>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		UserRegistration adminReg = commonDAO.getAdminByTeacher(userReg);
		try{
			if(assignmentTypeId!=19){
			questions = (List<SubQuestions>) getHibernateTemplate().find("from SubQuestions where "
					+ " assignmentType.assignmentTypeId="+assignmentTypeId
					+ " and lesson.lessonId="+lessonId+" and usedFor='"+usedFor+"' and createdBy in ("+userReg.getRegId() +","+ adminReg.getRegId() +")");
			}else{
				questions = (List<SubQuestions>) getHibernateTemplate().find("from SubQuestions where "
						+ " assignmentType.assignmentTypeId="+assignmentTypeId
						+ " and grade.gradeId="+gradeId+" and usedFor='"+usedFor+"' and subQuesStatus='active' and createdBy in ("+userReg.getRegId() +","+ adminReg.getRegId() +")");
			}
		}catch (HibernateException e) {
			logger.error("Error in getSubQuestionByAssignmentType() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getSubQuestionByAssignmentType() of AssessmentDAOImpl",e);
		}
		return questions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SubQuestions getSubQuestionBySubQuestionId(long subQuestionId) throws SQLDataException {
		List<SubQuestions> subQuestionsLt = new ArrayList<SubQuestions>();
		try{
			subQuestionsLt = (List<SubQuestions>) getHibernateTemplate().find("from SubQuestions where subQuestionId="+subQuestionId);
		}catch (HibernateException e) {
			logger.error("Error in getSubQuestionBySubQuestionId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getSubQuestionBySubQuestionId() of AssessmentDAOImpl",e);
		}
		return subQuestionsLt.get(0);
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean deletePerformancefile(long fileId) throws SQLDataException {
		boolean status = false;
		try{
			List<PtaskFiles> pf = Collections.emptyList();
			pf = (List<PtaskFiles>) getHibernateTemplate().find("from PtaskFiles where fileId="+fileId);
			jdbcTemplate.update("delete from ptask_files where file_id="+fileId);
			if(pf.size()>0){
				status = deleteFromFileSystem(pf.get(0).getQuestions().getQuestionId(),pf.get(0).getFilename());
			}
		}catch (HibernateException e) {
			logger.error("Error in deletePerformancefile() of AssessmentDAOImpl" + e);
			throw new DataException("Error in deletePerformancefile() of AssessmentDAOImpl",e);
		}
		return status;
	}	

	@Override
	public boolean deleteJacTemplete(JacTemplate jacTemplate) throws SQLDataException {
		boolean status = false;
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			List<Questions> questionsLt = jacTemplate.getQuestionsList();
			for (Questions questions : questionsLt) {
				/* session.delete(questions); */
				//session.update("update Questions set quesStatus='inactive' where questionId="+questions.getQuestionId());
				questions.setQueStatus(WebKeys.ASSIGN_STATUS_INACTIVE);
				session.saveOrUpdate(questions);
			}
			/* session.delete(jacTemplate); */
			tx.commit();
			session.close();
			status = true;
		}catch (HibernateException e) {
			logger.error("Error in deleteJacTemplete() of AssessmentDAOImpl" + e);
			throw new DataException("Error in deleteJacTemplete() of AssessmentDAOImpl",e);
		}
		return status;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean deleteJacTempleteQuestion(long jacQuestionFileId) throws SQLDataException {
		boolean status = false;
		Session session1 = sessionFactory.openSession();
		Transaction tx1 = (Transaction) session1.beginTransaction();
		List<JacQuestionFile> jacFileQuestions = new ArrayList<JacQuestionFile>();
		try{
			jacFileQuestions = (List<JacQuestionFile>) getHibernateTemplate().find("from JacQuestionFile where jacQuestionFileId="+jacQuestionFileId);
			if(jacFileQuestions.size() > 0){
				List<JacTemplate> jacTemplateLt = jacFileQuestions.get(0).getJacTempateList();
				for (JacTemplate jacTemplate : jacTemplateLt) {
					List<Questions> questionsLt =  (List<Questions>) getHibernateTemplate().find("from Questions where jacTemplate.jacTemplateId="+jacTemplate.getJacTemplateId());
					jacTemplate.setQuestionsList(questionsLt);
					status = deleteJacTemplete(jacTemplate);
				}
			}
			tx1.commit();
			session1.close();
		}catch (HibernateException e) {
			status = false;
			logger.error("Error in deleteJacTempleteQuestion() of AssessmentDAOImpl" + e);
		}
		return status;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean deleteSentenceStructure(long subQuestionId) throws SQLDataException {
		boolean status = false;
		Session session1 = sessionFactory.openSession();
		Transaction tx1 = (Transaction) session1.beginTransaction();
		try{
			SubQuestions subQuestion = getSubQuestionBySubQuestionId(subQuestionId);
			if(subQuestion.getAssignmentType().getAssignmentTypeId()==7){
			if(subQuestion.getQuestionses().size() > 0){
				for (Questions question : subQuestion.getQuestionses()) {
					session1.delete(question);
				}
			}
			tx1.commit();
			session1.close();
		   	Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "DELETE FROM SubQuestions WHERE subQuestionId = :subQuestionId";
			Query query = session.createQuery(hql);	
			query.setParameter("subQuestionId", subQuestionId);
			query.executeUpdate();
			tx.commit();
			session.close();
		}else{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "update SubQuestions set subQuesStatus=:subQueStat WHERE subQuestionId = :subQuestionId";
			Query query = session.createQuery(hql);	
			query.setParameter("subQueStat", "inactive");
			query.setParameter("subQuestionId", subQuestionId);
			query.executeUpdate();
			tx.commit();
			session.close();
		  }
			status = true;
		}catch (HibernateException e) {
			status = false;
			logger.error("Error in deleteSentenceStructure() of AssessmentDAOImpl" + e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteQuestion(Questions question) throws SQLDataException {
		boolean status = false;
		try{
			List<AssignmentQuestions> as = Collections.emptyList();
			as = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.questionId ="+question.getQuestionId());
			if(as.size()>0){
				return false;
			}
			
			Session session1 = sessionFactory.openSession();
			Transaction tx1 = (Transaction) session1.beginTransaction();
			SubQuestions  subQuestion = question.getSubQuestions();
			if(subQuestion !=  null){
				int numOfOptions = question.getSubQuestions().getNumOfOptions();
				if(numOfOptions > 0)
					subQuestion.setNumOfOptions(numOfOptions-1);
				session1.saveOrUpdate(subQuestion);
			}
			JacTemplate jacTemplate = question.getJacTemplate();
			if(jacTemplate != null){
				long numOfQuestions = jacTemplate.getNoOfQuestions();
				if(numOfQuestions > 0)
					jacTemplate.setNoOfQuestions(numOfQuestions-1);
				session1.saveOrUpdate(jacTemplate);
			}
			session1.delete(question);
			tx1.commit();
			session1.close();
			status = true;
		}catch (HibernateException e) {
			logger.error("Error in deleteQuestion() of AssessmentDAOImpl" + e);
			throw new DataException("Error in deleteQuestion() of AssessmentDAOImpl",e);
		}
		return status;
	}
	
	@Override
	public Questions saveQuestion(Questions question) throws SQLDataException{
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(question);
			if(question.getJacTemplate()!=null)
				session.saveOrUpdate(question.getJacTemplate());
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveQuestion() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in saveQuestion() of AssessmentDAOImpl",e);
		}
		
		return question;
	}
	
	private boolean deleteFromFileSystem(long questionId, String fileName) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String pTaskFileFullPath = uploadFilePath +File.separator+ WebKeys.JAC_TEMPLATE+File.separator+questionId;
		boolean status = true;
		try{
			File f = new File(pTaskFileFullPath);
			if(f.exists()){
				File[] files = f.listFiles();
				if(files.length > 0){
					for(File subFile:files){
						if(subFile.getName().equalsIgnoreCase(fileName)){
						 status = subFile.delete();
						}
					}
				}
			}
		}catch (HibernateException e) {
			status = false;
			logger.error("Error in deleteFromFileSystem() of AssessmentDAOImpl" + e);
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PtaskFiles> getPtaskFilesByUser(long questionId)
			throws SQLDataException {
		List<PtaskFiles> pf = Collections.emptyList();		
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			pf = (List<PtaskFiles>) getHibernateTemplate().find("from PtaskFiles where questions.questionId="+questionId
					+" and createdBy="+userReg.getRegId());
		}catch (HibernateException e) {
			logger.error("Error in getPtaskFilesByUser() of AssessmentDAOImpl" + e);
			throw new DataException("Error in getPtaskFilesByUser() of AssessmentDAOImpl",e);
		}
		return pf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JacTemplate getJacTemplate(long jacTemplateId) throws SQLDataException{
		List<JacTemplate> jacTemplateLt = new ArrayList<JacTemplate>();
		try{
			jacTemplateLt = (List<JacTemplate>) getHibernateTemplate().find("from JacTemplate where jacTemplateId="+jacTemplateId);
			for (JacTemplate jacTemplate : jacTemplateLt) {
				List<Questions> ques = Collections.emptyList();
				ques = (List<Questions>) getHibernateTemplate().find("from Questions where jacTemplate.jacTemplateId="+jacTemplate.getJacTemplateId());
				jacTemplate.setQuestionsList(ques);
				jacTemplate.setNoOfQuestions(ques.size());
			}
		}catch (HibernateException e) {
			logger.error("Error in getJacFileQuestion() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getJacFileQuestion() of AssessmentDAOImpl",e);
		}
		return jacTemplateLt.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JacQuestionFile> getJacFileQuestionsByUsedFor(long lessonId,
			String usedFor) throws SQLDataException {
		List<JacQuestionFile> jacFileQuestions = new ArrayList<JacQuestionFile>();
		Query query = null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession();
/*			jacFileQuestions = (List<JacQuestionFile>) getHibernateTemplate().find("from JacQuestionFile where lesson.lessonId="+lessonId
					+" and usedFor='"+usedFor+"' and userRegistration.regId="+userReg.getRegId());*/
			query  = session.createQuery("Select qs.jacTemplate.jacQuestionFile FROM Questions qs where "
					+ " qs.lesson.lessonId= "+ lessonId +" and qs.usedFor='"+usedFor +"' and qs.createdBy="+userReg.getRegId() +" and qs.queStatus = 'active'");
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			jacFileQuestions = query.list();
		}catch (HibernateException e) {
			logger.error("Error in getJacFileQuestionsByLessonId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getJacFileQuestionsByLessonId() of AssessmentDAOImpl",e);
		}
		return jacFileQuestions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JacTemplate> getJacTemplateByFileId(long jacQuestionFileId)
			throws SQLDataException {
		List<JacTemplate> jacTemp = new ArrayList<JacTemplate>();
		try{
			jacTemp = (List<JacTemplate>) getHibernateTemplate().find("from JacTemplate where jacQuestionFile.jacQuestionFileId="+jacQuestionFileId);
			for(JacTemplate jt : jacTemp){
				List<Questions> ques = Collections.emptyList();
				ques = (List<Questions>) getHibernateTemplate().find("from Questions where jacTemplate.jacTemplateId="+jt.getJacTemplateId());
				jt.setQuestionsList(ques);
			}
		}catch (HibernateException e) {
			logger.error("Error in getJacTemplateByFileId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getJacTemplateByFileId() of AssessmentDAOImpl",e);
		}
		return jacTemp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JacQuestionFile getJacQuestionFileByFileId(long jacQuestionFileId) throws SQLDataException {
		List<JacQuestionFile> jacQuestionFileIdLt = new ArrayList<JacQuestionFile>();
		try{
			jacQuestionFileIdLt = (List<JacQuestionFile>) getHibernateTemplate().find("from JacQuestionFile where jacQuestionFileId="+jacQuestionFileId);
		}catch (HibernateException e) {
			logger.error("Error in getJacQuestionFileByFileId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getJacQuestionFileByFileId() of AssessmentDAOImpl",e);
		}
		return jacQuestionFileIdLt.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getTestDatesforRTIResults(long createdBy,long csId,String usedFor) throws SQLDataException{
		List<Assignment> testDates = new ArrayList<Assignment>();
		try{
			testDates = (List<Assignment>) getHibernateTemplate().find("from Assignment where classStatus.csId="+csId+" and "
				+ "usedFor='"+usedFor+"' and createdBy="+createdBy+" and assignStatus='"+WebKeys.LP_STATUS_ACTIVE+"' and "
				+ "assignmentType.assignmentType not in ('"+WebKeys.ASSIGNMENT_TYPE_EARLY_LITERACY_WORD+"','"
				+ WebKeys.ASSIGNMENT_TYPE_EARLY_LITERACY_LETTER+"','"+WebKeys.LP_FLUENCY_READING_TEST+"') group by dateAssigned ");			
		}catch (HibernateException e) {
			logger.error("Error in getTestDatesforRTIResults() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getTestDatesforRTIResults() of AssessmentDAOImpl",e);
		}
		return testDates;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getRTIResultsByStudent(long assignmentId) throws SQLDataException{
		List<StudentAssignmentStatus> rtiResults = new ArrayList<StudentAssignmentStatus>();
		UserRegistration userRegistration;
		Student student;
		StudentAssignmentStatus studentAssignmentStatus;
		String assignmentType = null;		
		List<Object[]> list = Collections.emptyList();
		try{
			list = (List<Object[]>) getHibernateTemplate().find("Select studentAssignmentStatus.studentAssignmentId, studentAssignmentStatus.student.studentId,  "
				+ "studentAssignmentStatus.student.userRegistration.firstName, studentAssignmentStatus.student.userRegistration.lastName, sum(secMarks), "
				+ "sum(maxMarks), studentAssignmentStatus.percentage, studentAssignmentStatus.assignment.assignmentType.assignmentType from "
			 	+ "AssignmentQuestions where studentAssignmentStatus.assignment.assignmentId="+assignmentId+" and studentAssignmentStatus.status='"+WebKeys.TEST_STATUS_SUBMITTED+"' "
				+ "and studentAssignmentStatus.gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"' group by studentAssignmentStatus.studentAssignmentId");			
			for(Object[] o : list){
				assignmentType = (String) o[7];
				userRegistration = new UserRegistration();
				userRegistration.setFirstName((String) o[2]);
				userRegistration.setLastName((String) o[3]);
				student = new Student();
				student.setUserRegistration(userRegistration);
				student.setStudentId((long) o[1]);
				studentAssignmentStatus = new StudentAssignmentStatus();
				studentAssignmentStatus.setStudent(student);
				if(assignmentType.equals(WebKeys.ASSIGNMENT_TYPE_ESSAYS) || assignmentType.equals(WebKeys.ASSIGNMENT_TYPE_SHORT_ANSWERS))
				{
					studentAssignmentStatus.setAnswersByRightResponse((long) o[4]/(long) o[5] * 100);
					studentAssignmentStatus.setAnswersBYWrongResponse(((long) o[5]-(long) o[4])/(long) o[5] * 100);					
				}else{	
					studentAssignmentStatus.setAnswersByRightResponse((long) o[4]);
					studentAssignmentStatus.setAnswersBYWrongResponse((long) o[5]-(long) o[4]);
				}
				studentAssignmentStatus.setPercentage((float) o[6]);
				rtiResults.add(studentAssignmentStatus);
			}
			
		}catch (HibernateException e) {
			logger.error("Error in getRTIResultsByStudent() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getRTIResultsByStudent() of AssessmentDAOImpl",e);
		}
		return rtiResults;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions> getRTIResultsByQuestion(long assignmentId) throws SQLDataException{
		List<AssignmentQuestions> rtiTestResults = new ArrayList<AssignmentQuestions>();
		Questions questions;
		AssignmentQuestions assignmentQuestions;
		String assignmentType = null;
		List<Object[]> list = Collections.emptyList();
		Assignment assignment;
		try{
			assignment = assessmentsDAO.getAssignment(assignmentId);
			if(assignment.getAssignmentType().getAssignmentType().equals(WebKeys.ASSIGNMENT_TYPE_SENTENCE_STRUCTURE))
			{
				list = (List<Object[]>) getHibernateTemplate().find("Select questions.subQuestions.subQuestion, sum(secMarks), sum(maxMarks), "
					+ "studentAssignmentStatus.assignment.assignmentType.assignmentType  from AssignmentQuestions "
					+ "where studentAssignmentStatus.assignment.assignmentId="+assignmentId+" and studentAssignmentStatus.status='"+WebKeys.TEST_STATUS_SUBMITTED+"' "
					+ "and studentAssignmentStatus.gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"' group by questions.subQuestions.subQuestionId");					
			}
			else{
				list = (List<Object[]>) getHibernateTemplate().find("Select questions.question, sum(secMarks), sum(maxMarks), "
					+ "studentAssignmentStatus.assignment.assignmentType.assignmentType  from AssignmentQuestions "
					+ "where studentAssignmentStatus.assignment.assignmentId="+assignmentId+" and studentAssignmentStatus.status='"+WebKeys.TEST_STATUS_SUBMITTED+"' "
					+ "and studentAssignmentStatus.gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"' group by questions.questionId");
			}
			for(Object[] o : list){
				questions = new Questions();
				questions.setQuestion((String) o[0]);
				assignmentQuestions = new AssignmentQuestions();
				assignmentQuestions.setQuestions(questions);
				assignmentType = (String) o[3];
				if(assignmentType.equals(WebKeys.ASSIGNMENT_TYPE_ESSAYS) || assignmentType.equals(WebKeys.ASSIGNMENT_TYPE_SHORT_ANSWERS))
				{
					assignmentQuestions.setAnswersByRightResponse((long) o[1]);
					assignmentQuestions.setAnswersByRightResponsePercentage(((long) o[1] * 100)/(long) o[2]);
					assignmentQuestions.setAnswersBYWrongResponse((long) o[2]-(long) o[1]);	
					assignmentQuestions.setAnswersBYWrongResponsePercentage((((long) o[2]-(long) o[1])* 100)/(long) o[2]);
				}else{	
					assignmentQuestions.setAnswersByRightResponse((long) o[1]);
					assignmentQuestions.setAnswersByRightResponsePercentage(((long) o[1] * 100)/(long) o[2]);
					assignmentQuestions.setAnswersBYWrongResponse((long) o[2]-(long) o[1]);
					assignmentQuestions.setAnswersBYWrongResponsePercentage((((long) o[2]-(long) o[1])* 100)/(long) o[2]);
				}
				rtiTestResults.add(assignmentQuestions);
			}			
		}catch (HibernateException e) {
			logger.error("Error in getRTIResultsByQuestion() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getRTIResultsByQuestion() of AssessmentDAOImpl",e);
		}
		return rtiTestResults;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getQuestionsByLessonId(long lessonId) throws SQLDataException {
		List<Questions> questionLt = new ArrayList<Questions>();
		try{
			 questionLt = (List<Questions>) getHibernateTemplate().find("from Questions where lesson.lessonId="+lessonId);
		}catch (HibernateException e) {
			logger.error("Error in getQuestionsByLessonId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getQuestionsByLessonId() of AssessmentDAOImpl",e);
		}
		return questionLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JacQuestionFile> getJacFileQuestionsByLessonId(long lessonId) throws SQLDataException {
		List<JacQuestionFile> jacFileQuestions = new ArrayList<JacQuestionFile>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			jacFileQuestions = (List<JacQuestionFile>) getHibernateTemplate().find("from JacQuestionFile where lesson.lessonId="+lessonId
					+" and userRegistration.regId="+userReg.getRegId());
		}catch (HibernateException e) {
			logger.error("Error in getJacFileQuestionsByLessonId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getJacFileQuestionsByLessonId() of AssessmentDAOImpl",e);
		}
		return jacFileQuestions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkQuestionExists(long questionId){
		boolean status = false;
		try{
			List<AssignmentQuestions> as = Collections.emptyList();
			as = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.questionId ="+questionId);
			if(as.size()>0){
				status = true;
			}
		}catch (HibernateException e) {
			logger.error("Error in checkQuestionExists() of AssessmentDAOImpl" + e);
			throw new DataException("Error in checkQuestionExists() of AssessmentDAOImpl",e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkJacTemplateExists(long jacQuestionFileId){
		boolean status = false;
		List<JacQuestionFile> jacFileQuestions = new ArrayList<JacQuestionFile>();
		try{
			jacFileQuestions = (List<JacQuestionFile>) getHibernateTemplate().find("from JacQuestionFile where jacQuestionFileId="+jacQuestionFileId);
			if(jacFileQuestions.size() > 0){
				List<JacTemplate> jacTemplateLt = jacFileQuestions.get(0).getJacTempateList();
				for (JacTemplate jacTemplate : jacTemplateLt) {
					List<Questions> questionsLt =  (List<Questions>) getHibernateTemplate().find("from Questions where jacTemplate.jacTemplateId="+jacTemplate.getJacTemplateId());
					if(!questionsLt.isEmpty()){
						status=checkQuestionExists(questionsLt.get(0).getQuestionId());
						if(status==true)
							break;
						}	
					}
				}
				
			
		}catch (HibernateException e) {
			status = false;
			logger.error("Error in deleteJacTempleteQuestion() of AssessmentDAOImpl" + e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public long checkQuestionAssigned(long questionId){
		long assignmentId = 0;
		try{
			List<AssignmentQuestions> as = Collections.emptyList();
			as = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.questionId ="+questionId);
			if(as.size()>0){
				assignmentId = as.get(0).getStudentAssignmentStatus().getAssignment().getAssignmentId();
			}
		}catch (HibernateException e) {
			logger.error("Error in checkQuestionAssigned() of AssessmentDAOImpl" + e);
			throw new DataException("Error in checkQuestionAssigned() of AssessmentDAOImpl",e);
		}
		return assignmentId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSubQuestion(Assignment assignment, List<Student> studentList,List<Questions> questionsLt) {
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long assessmentTypeId = assignment.getAssignmentType().getAssignmentTypeId();
		for (Student student : studentList) {
			 long  studentAssignmentId = getStudentAssignmentStatusId(assignment.getAssignmentId(),student.getStudentId());
		     for (Questions question : questionsLt) {
		    	    StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();	
		    	    studentAssignmentStatus.setStudentAssignmentId(studentAssignmentId);
		    	    studentAssignmentStatus.setAssignment(assignment);
				    studentAssignmentStatus.setStudent(student);
					studentAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
					studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					List<AssignmentQuestions> as = Collections.emptyList();
					as = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.questionId ="+question.getQuestionId()+" and studentAssignmentStatus.studentAssignmentId="+studentAssignmentId);
					if(as.size() == 0){
					AssignmentQuestions assignmentQuestion = new AssignmentQuestions();
						if(assessmentTypeId == 7){
						   if(question.getAnswer() != null ||question.getOption1() != null  || question.getOption2() != null || question.getOption3() != null){
								if(!question.getAnswer().equalsIgnoreCase("") || !question.getOption1().equalsIgnoreCase("")  || !question.getOption2().equalsIgnoreCase("") || !question.getOption3().equalsIgnoreCase("")){
									assignmentQuestion.setStudentAssignmentStatus(studentAssignmentStatus);
									assignmentQuestion.setQuestions(question);
									session.saveOrUpdate(assignmentQuestion);
								 }
						   }
						}else if(assessmentTypeId != 19 || (assessmentTypeId == 19 && question.getQuestion() != null)){
							if (assessmentTypeId != 19 || (assessmentTypeId == 19 && !question.getQuestion().equalsIgnoreCase(""))){
								assignmentQuestion.setStudentAssignmentStatus(studentAssignmentStatus);
								assignmentQuestion.setQuestions(question);
								session.saveOrUpdate(assignmentQuestion);
							}
					    }
					}
		     }
		}
		tx.commit();
		session.close();
		return true;
	}

	@SuppressWarnings("unchecked")
	public long getStudentAssignmentStatusId(long assignmentId, long studentId) {
		long studentAssignmentId = 0;
		try {
			List<StudentAssignmentStatus> list = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where assignment.assignmentId="
							+ assignmentId
							+ " and student.studentId= "
							+ studentId);
			if (!list.isEmpty()) {
				studentAssignmentId = list.get(0).getStudentAssignmentId();
			}
		} catch (HibernateException e) {
			logger.error("Error in getAssignmentsByassignFor() of AssessmentDAOImpl"
					+ e);
		}
		return studentAssignmentId;
	}
	@SuppressWarnings("unchecked")
   public boolean checkSubQuestionExists(long subQuestionId) throws SQLDataException{
	boolean status = false;
	long count=0;
	try{
		SubQuestions subQuestion = getSubQuestionBySubQuestionId(subQuestionId);
		if(subQuestion.getQuestionses().size() > 0){
			for (Questions question : subQuestion.getQuestionses()) {
				List<AssignmentQuestions> as = Collections.emptyList();
				as = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.questionId ="+question.getQuestionId());
				if(as.size()>0){
					count++;
				}
			}
			if(count>0)
				status=true;
	}
	}catch (HibernateException e) {
		logger.error("Error in checkQuestionExists() of AssessmentDAOImpl" + e);
		throw new DataException("Error in checkQuestionExists() of AssessmentDAOImpl",e);
	}
	return status;
}
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions>  getAssignmentByquestionId(long questionId) throws DataException {
		List<AssignmentQuestions> AssignmentQuestions = new ArrayList<AssignmentQuestions>();
		try{
				AssignmentQuestions = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.questionId = "+questionId+""
						+ " and studentAssignmentStatus.gradedStatus = '"+WebKeys.GRADED_STATUS_GRADED+"' order by studentAssignmentStatus.studentAssignmentId");				
		}catch (HibernateException e) {
			logger.error("Error in getAssignmentByquestionId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getAssignmentByquestionId() of AssessmentDAOImpl",e);
		}
		return AssignmentQuestions;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions>  getAssignmentBySubQuestionId(long subQuestionId) throws DataException {
		List<AssignmentQuestions> AssignmentQuestions = new ArrayList<AssignmentQuestions>();
		try{
				AssignmentQuestions = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where questions.subQuestions.subQuestionId = "+subQuestionId+""
						+ " and studentAssignmentStatus.gradedStatus = '"+WebKeys.GRADED_STATUS_GRADED+"' order by studentAssignmentStatus.studentAssignmentId");				
		}catch (HibernateException e) {
			logger.error("Error in getAssignmentByquestionId() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getAssignmentByquestionId() of AssessmentDAOImpl",e);
		}
		return AssignmentQuestions;
	}	
	
	@Override
	public boolean updateStudentAssignmentQusetions(Questions question,List<AssignmentQuestions> studentAssignmentQuestions,List<StudentAssignmentStatus> testObjects) throws SQLDataException {
		boolean status = true;
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();

		try {
			
				session.saveOrUpdate(question);	
				for (AssignmentQuestions stuAssQue : studentAssignmentQuestions) {
				   session.saveOrUpdate(stuAssQue);
				}
				for (StudentAssignmentStatus stuAssSta : testObjects) {
					session.saveOrUpdate(stuAssSta);
				}			
			tx.commit();
			
		} catch (HibernateException e) {
			
			tx.rollback();
			status = false;
			logger.error("Error in updateAssessments() of AssessmentDAOImpl" + e);
			throw new DataException("Error in updateAssessments() of AssessmentDAOImpl",e);
		}
		finally {
			session.close();
		}
		return status;		
	}
	
	@Override
	public SubQuestions saveAssignemntQuestions(SubQuestions subQuestion) throws SQLDataException{		
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int count = 0;
			if(subQuestion.getQuestionses() != null){
				for (int i = 0; i < subQuestion.getQuestionses().size(); i++) {
					long assessmentTypeId = subQuestion.getAssignmentType().getAssignmentTypeId();
				    if(assessmentTypeId == 7){
						   if(subQuestion.getQuestionses().get(i).getAnswer() != null || subQuestion.getQuestionses().get(i).getOption1() != null  || subQuestion.getQuestionses().get(i).getOption2() != null || subQuestion.getQuestionses().get(i).getOption3() != null)
							if(!subQuestion.getQuestionses().get(i).getAnswer().equalsIgnoreCase("") || !subQuestion.getQuestionses().get(i).getOption1().equalsIgnoreCase("")  || !subQuestion.getQuestionses().get(i).getOption2().equalsIgnoreCase("") || !subQuestion.getQuestionses().get(i).getOption3().equalsIgnoreCase(""))
								 count =  count+1;
					}else if(assessmentTypeId != 19 || (assessmentTypeId == 19 && subQuestion.getQuestionses().get(i).getQuestion() != null)){
						if (assessmentTypeId != 19 || (assessmentTypeId == 19 && !subQuestion.getQuestionses().get(i).getQuestion().equalsIgnoreCase("")))
							 count =  count+1;
					}
				}
			}
			subQuestion.setNumOfOptions(count);
			session.saveOrUpdate(subQuestion);
			for(Questions question: subQuestion.getQuestionses()){
				long assessmentTypeId = subQuestion.getAssignmentType().getAssignmentTypeId();
				if(assessmentTypeId == 7){
				  if(question.getAnswer() != null  || question.getOption1()!= null  || question.getOption2() != null || question.getOption3() != null)
					if(!question.getAnswer().equalsIgnoreCase("") || !question.getOption1().equalsIgnoreCase("")  || !question.getOption2().equalsIgnoreCase("") || !question.getOption3().equalsIgnoreCase(""))
						 session.saveOrUpdate(question);	
				}else if(assessmentTypeId != 19 || (assessmentTypeId == 19 && question.getQuestion() != null))	 {
					if (assessmentTypeId != 19 || (assessmentTypeId == 19 && !question.getQuestion().equalsIgnoreCase("")))
						 session.saveOrUpdate(question);
					      
				}  
			}
			for(AssignmentQuestions subQus :subQuestion.getAssignmentQuestions()) { 
			   session.saveOrUpdate(subQus);
		    }
		    for(StudentAssignmentStatus stuAssStatus :subQuestion.getStudentassignmentStatus()) {
				session.saveOrUpdate(stuAssStatus);
	        }  
			
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveSubQuestion() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in saveSubQuestion() of AssessmentDAOImpl",e);
		}
		return subQuestion;
	}
	
}