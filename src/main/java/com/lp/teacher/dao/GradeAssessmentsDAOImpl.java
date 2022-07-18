
package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicGrades;
import com.lp.model.AcademicYear;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkResults;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyComments;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.GradingTypes;
import com.lp.model.QualityOfResponse;
import com.lp.model.RflpRubric;
import com.lp.model.RflpTest;
import com.lp.model.StudentAssignmentStatus;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("gradeassessmentsdao")
public class GradeAssessmentsDAOImpl extends CustomHibernateDaoSupport
implements GradeAssessmentsDAO {
		@Autowired
		private SessionFactory sessionFactory;
		private JdbcTemplate jdbcTemplate;
		@Autowired
		private HttpSession httpSession;

		@Autowired
		public void setdataSource(DataSource datasource) {
			this.jdbcTemplate = new JdbcTemplate(datasource);

		}
		@SuppressWarnings("unchecked")
		@Override
		public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId){
			List<StudentAssignmentStatus> assignmentList = null;
			AcademicYear academicYear = null;
			String assignStatus = WebKeys.ACTIVE;
			if(httpSession.getAttribute("academicYear") != null)
				academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
			try{
				if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
						&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
					assignStatus =  WebKeys.EXPIRED;
				}
				
				assignmentList = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
						"from StudentAssignmentStatus where assignment.assignStatus='"+assignStatus+"' and "
						+ "assignment.assignmentId="+assignmentId+" and performanceGroup.performanceGroupId = Null and "
						+ "student.gradeStatus='"+WebKeys.ACTIVE+"'  order by student.userRegistration.lastName asc");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return assignmentList;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId) {
			List<AssignmentQuestions> questions = null;
			try {
				questions = (List<AssignmentQuestions>) getHibernateTemplate()
						.find("From AssignmentQuestions where studentAssignmentStatus.studentAssignmentId="
								+ studentAssignmentId);
			} catch (Exception e) {
				logger.error("Error in getTestQuestions() of GradeAssessmentsDAOImpl", e);
			}
			return questions;
		}
		@SuppressWarnings("rawtypes")
		@Override
		public void updateJacMarks(long mark,long assignmentQuestionId){
			try{				
				Session session = getSessionFactory().getCurrentSession();
				String hql = "UPDATE AssignmentQuestions set secMarks = :secMark"
						+ " WHERE assignmentQuestionsId = :assignmentQuestionsId";
				Query query = session.createQuery(hql);
				query.setParameter("secMark",  (int) mark);
				query.setParameter("assignmentQuestionsId",assignmentQuestionId);
				query.executeUpdate();
			}catch(Exception e){
				logger.error("Error in updateJacMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
		}
		@SuppressWarnings("rawtypes")
		@Override
		public boolean submitJacTemplateTest(long studentAssignmentId,float percentage,long acedamicGradeId){
			Session session = getSessionFactory().getCurrentSession();
			try {
				String hql = "UPDATE StudentAssignmentStatus set gradedStatus = :gradedStatus, percentage=:percentage,"
						+ "academicGrade.acedamicGradeId=:acedamicGradeId,gradedDate=current_date,readStatus=:readStatus WHERE studentAssignmentId = :studentAssignmentId";
				Query query = session.createQuery(hql);
				query.setParameter("gradedStatus","graded");
				query.setParameter("percentage",percentage);
				query.setParameter("studentAssignmentId",studentAssignmentId);
				query.setParameter("acedamicGradeId",acedamicGradeId);
				query.setParameter("readStatus",WebKeys.UN_READ);
				query.executeUpdate();
				return true;
			}catch(Exception e){
				logger.error("Error in submitJacTemplateTest() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
				return false;
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public AcademicGrades getAcademicGradeByPercentage(float percentage) {
			AcademicGrades academicGrades=null;
	   		int per=(int)percentage;
	   		try{
	   		List<AcademicGrades> academicGradesLt = (List<AcademicGrades>) getHibernateTemplate()
	    				.find("from AcademicGrades where scoreFrom<="+per+ " and scoreTo>= " + per);
	   		if(!academicGradesLt.isEmpty())
	   			academicGrades = academicGradesLt.get(0);	
	   		}catch(Exception e){
				logger.error("Error in getAcademicGradeByPercentage() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
				
			}
	    	return academicGrades;
	          
	    }
		@SuppressWarnings("rawtypes")
		@Override
		public boolean gradeStudentAssessments(List<AssignmentQuestions> assQuesList,StudentAssignmentStatus stuAss){
			Session session = getSessionFactory().getCurrentSession();
			try {
				for(int i=0;i<assQuesList.size();i++){
					String hql = "UPDATE AssignmentQuestions set maxMarks = :maxMarks, secMarks=:secMarks,teacherComment=:teacherComment "
							+ "WHERE assignmentQuestionsId = :assignmentQuestionId";
					Query query = session.createQuery(hql);
					query.setParameter("maxMarks",assQuesList.get(i).getMaxMarks());
					query.setParameter("secMarks",assQuesList.get(i).getSecMarks());
					query.setParameter("teacherComment",assQuesList.get(i).getTeacherComment());
					query.setParameter("assignmentQuestionId",assQuesList.get(i).getAssignmentQuestionsId());
					query.executeUpdate();
				}
				String hql = "UPDATE StudentAssignmentStatus set gradedStatus = :gradedStatus, percentage=:percentage,"
						+ "academicGrade.acedamicGradeId=:acedamicGradeId,gradedDate=current_date,readStatus=:readStatus WHERE studentAssignmentId = :studentAssignmentId";
				Query query = session.createQuery(hql);
				query.setParameter("gradedStatus","graded");
				query.setParameter("percentage",stuAss.getPercentage());
				query.setParameter("studentAssignmentId",stuAss.getStudentAssignmentId());
				query.setParameter("acedamicGradeId",stuAss.getAcademicGrade().getAcedamicGradeId());
				query.setParameter("readStatus",WebKeys.UN_READ);
				query.executeUpdate();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public AssignmentQuestions getAssignmentQuestions(long assignmentQuestionId) {
			List<AssignmentQuestions> list=new ArrayList<AssignmentQuestions>();
			try{
	        list = (List<AssignmentQuestions>) getHibernateTemplate()
	    				.find("from AssignmentQuestions where assignmentQuestionsId= " + assignmentQuestionId);
			}catch(Exception e){
				logger.error("Error in getAssignmentQuestions() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list.get(0);
	       
	    }

		@SuppressWarnings("rawtypes")
		@Override
		public void deleteBenchmarkAllMarks(long studentAssignmentId,long assignmemntQuestionsId){
			Session session = getSessionFactory().getCurrentSession();
			try{
				String hql = "UPDATE  FluencyMarks set marks=0, countOfErrors=0,"
						+ "wordsRead=0,accuracy_score=0 WHERE assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId";
				Query query = session.createQuery(hql);
				query.setParameter("assignmentQuestionsId",assignmemntQuestionsId);
				query.executeUpdate();
				String hql1 = "UPDATE  FluencyMarksDetails set errorsAddress=null WHERE fluencyMarks.assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId";
				Query query1 = session.createQuery(hql1);
				query1.setParameter("assignmentQuestionsId",assignmemntQuestionsId);
				query1.executeUpdate();
			}catch(Exception e){
				logger.error("Error in deleteFluencyMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
				
			}
		}
		@SuppressWarnings("rawtypes")
		@Override
		public void deleteBenchmarkTypeMarks(long studentAssignmentId,long assignmemntQuestionsId,long readingTypesId,long fluencyMarksId,long gradeTypesId){
			Session session = getSessionFactory().getCurrentSession();
			try{
			String hql = "UPDATE  FluencyMarks set marks=null, countOfErrors=null,"
					+ "wordsRead=null,accuracyScore=null,comment=null,wcpm=null WHERE assignmentQuestions.assignmentQuestionsId=:assignmentQuestId and readingTypes.readingTypesId=:readingTypesId and gradingTypes.gradingTypesId=:gradeTypesId";
			Query query = session.createQuery(hql);
			query.setParameter("assignmentQuestId",assignmemntQuestionsId);
			query.setParameter("readingTypesId",readingTypesId);
			query.setParameter("gradeTypesId",gradeTypesId);
			query.executeUpdate();
			
			String hql1 = "delete from FluencyMarksDetails WHERE fluencyMarks.fluencyMarksId=:fluencyMarkId";
			Query query1 = session.createQuery(hql1);
			query1.setParameter("fluencyMarkId",fluencyMarksId);
			query1.executeUpdate();
		}catch(Exception e){
			logger.error("Error in deleteFluencyMarks() of GradeAssessmentsDAOImpl", e);
			e.printStackTrace();
			
		}
		}
	
	
		@SuppressWarnings("rawtypes")
		@Override
		public void deleteRetellMarks(long studentAssignmentId,long assignmemntQuestionsId,long readingTypesId,long fluencyMarksId,long gradeTypesId){
			try{
			Session session = getSessionFactory().getCurrentSession();
			String hql = "UPDATE  FluencyMarks set qualityOfResponse.qorId=null"
					+ " WHERE assignmentQuestions.assignmentQuestionsId=:assignmentQuestId and readingTypes.readingTypesId=:readingTypesId and gradingTypes.gradingTypesId=:gradeTypesId";
			Query query = session.createQuery(hql);
			query.setParameter("assignmentQuestId",assignmemntQuestionsId);
			query.setParameter("readingTypesId",readingTypesId);
			query.setParameter("gradeTypesId",gradeTypesId);
			query.executeUpdate();
			}catch(Exception e){
				logger.error("Error in deleteRetellMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
				
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<QualityOfResponse> getQualityOfResponse(){
			List<QualityOfResponse> qualResList = null;
			try {
				
				qualResList = (List<QualityOfResponse>) getHibernateTemplate().find("from QualityOfResponse order by qorId");
			} catch (Exception ex) {
				 logger.error("Error in getQualityOfResponse() of GradeAssessmentsDAOImpl", ex);
				ex.printStackTrace();
			}
			return qualResList;
		}
		@Override
		public boolean gradeRetellFluencyTest(long assignmentQuestionId,long retellScore,long readingTypesId,long gradeTypesId,String comment){
			try{
				 String sql = "UPDATE fluency_marks set quality_of_response_id=?,comment=? where assignment_questions_id = ? and reading_type_id=? and grading_type_id=?";
			     jdbcTemplate.update(sql, new Object[] { retellScore,comment,assignmentQuestionId,readingTypesId,gradeTypesId});
				return true;
			 }catch(Exception e){
				 logger.error("Error in gradeRetellFluencyTest() of GradeAssessmentsDAOImpl", e);
					e.printStackTrace();
					return false;
				}
		}
		@SuppressWarnings("unchecked")
		@Override
		public BenchmarkResults getBenchmarkResults(long studentAssignmentId){
			
			List<BenchmarkResults> list = (List<BenchmarkResults>) getHibernateTemplate()
    				.find("from BenchmarkResults where studentAssignmentStatus.studentAssignmentId="+ studentAssignmentId);
			if(list.size()>0)
				return list.get(0);
			else
				return new BenchmarkResults();
				
				
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public QualityOfResponse getMedianresponse(int qualOfResponse){
		
			List<QualityOfResponse> list = (List<QualityOfResponse>) getHibernateTemplate().find("from QualityOfResponse where qorId="+qualOfResponse+"");
			if(list.size()>0)
				return list.get(0);
			else
				return new QualityOfResponse();
			
			
			}
		@Override
		public int updateStudentAssessmentStatus(long studentAssignmentId, float percentageAcquired,AcademicGrades academicGrade){
			try{
				 String sql = "UPDATE student_assignment_status set  graded_status= ?,percentage=?,academic_grade_id=?,graded_date=current_date,read_status=? where student_assignment_id= ?";
			     jdbcTemplate.update(sql, new Object[] { "graded",percentageAcquired,academicGrade.getAcedamicGradeId(),WebKeys.UN_READ,studentAssignmentId });
				return 1;
			 }catch(Exception e){
					e.printStackTrace();
					return 0;
				}
		}
		@Override
		public boolean saveBenchmarkResults(BenchmarkResults benchResults){
		try {
			super.saveOrUpdate(benchResults);
			return true;
		} catch (HibernateException e) {
			logger.error("Error in saveBenchmarkResults() of GradeAssessmentDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		 public BenchmarkCutOffMarks getBenchmarkCutOffMarks(long gradeId,long benchmarkId)throws DataException{
			 List<BenchmarkCutOffMarks> benchmarkCutOffMarks = Collections.emptyList();
				try{
					benchmarkCutOffMarks = (List<BenchmarkCutOffMarks>) getHibernateTemplate().find("from BenchmarkCutOffMarks where grade.gradeId="+gradeId+" and benchmarkCategories.benchmarkCategoryId="+benchmarkId+"");
				}catch (HibernateException e) {
					logger.error("Error in getBenchmarkCutOffMarks() of BenchmarkCutOffMarksDAOImpl" + e);
					e.printStackTrace();
					throw new DataException("Error in getBenchmarkCutOffMarks() of BenchmarkCutOffMarksDAOImpl",e);
				}
				if(benchmarkCutOffMarks.size()>0)
				return benchmarkCutOffMarks.get(0);
				else
					return null;
		 }
		
		@Override
		public boolean updateStudentRTISections(StudentAssignmentStatus studentAssignmentStatus, long sectionId, long rtiGroupId){
			try{
				 String sql = "UPDATE register_for_class set rti_group_id=? where section_id=? and student_id=?";
			     jdbcTemplate.update(sql, new Object[] { rtiGroupId,sectionId,studentAssignmentStatus.getStudent().getStudentId() });
				return true;
			 }catch(Exception e){
					e.printStackTrace();
					return false;
			 }
		}
		
		@Override
		public String getBenchmarkTeacherComment(long studentAssignmentId){
			String teacherComment ="";
			try{
			String query = "select teacher_notes from benchmark_results where student_assignment_id="
					+ studentAssignmentId+"";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				teacherComment = rs.getString(1);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return teacherComment;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<RflpRubric> getRflpRubricValues(){
			List<RflpRubric> list = (List<RflpRubric>) getHibernateTemplate().find("from RflpRubric order by rflpRubricValue");
			if(list.size()>0)
				return list;
			else
				return null;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<RflpTest> getRFLPTests(long assignmentId){
			List<RflpTest> assignmentList = null;
			try {
				
				assignmentList = (List<RflpTest>) getHibernateTemplate().find(
						"from RflpTest where studentAssignmentStatus.assignment.assignStatus='"+WebKeys.ACTIVE+"'"
								+" and studentAssignmentStatus.assignment.assignmentId="+assignmentId+" and studentAssignmentStatus.performanceGroup.performanceGroupId = Null order by studentAssignmentStatus.student.userRegistration.lastName asc");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return assignmentList;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<FluencyMarks> getBenchmarkTypes(long assignmentQuestionsId,long gradeTypeId){
			List<FluencyMarks> list=new ArrayList<FluencyMarks>();
			try{
	        list = (List<FluencyMarks>) getHibernateTemplate()
	    				.find("from FluencyMarks where assignmentQuestions.assignmentQuestionsId="+assignmentQuestionsId+" and gradingTypes.gradingTypesId="+gradeTypeId+"");
			}catch(Exception e){
				logger.error("Error in getBenchmarkTypes() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<FluencyMarksDetails> getErrorsList(long fluencyMarksId){
			List<FluencyMarksDetails> list=new ArrayList<FluencyMarksDetails>();
			try{
	        list = (List<FluencyMarksDetails>) getHibernateTemplate()
	    				.find("from FluencyMarksDetails where fluencyMarks.fluencyMarksId= " + fluencyMarksId);
			}catch(Exception e){
				logger.error("Error in getErrorsList() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
	    	
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public FluencyMarks getFluencyMarks(long assignmentQuestionId,long readingTypesId,long gradeTypesId){
			List<FluencyMarks> list=new ArrayList<FluencyMarks>();
			FluencyMarks fluencyMarks = new FluencyMarks();
			try{
	        list = (List<FluencyMarks>) getHibernateTemplate()
	    				.find("from FluencyMarks where assignmentQuestions.assignmentQuestionsId=" + assignmentQuestionId+" and readingTypes.readingTypesId="+readingTypesId+" and gradingTypes.gradingTypesId="+gradeTypesId+"");
	        fluencyMarks = list.get(0);
	        if(fluencyMarks.getFluencyMarksId() > 0){
		        List<FluencyAddedWords> fluencyAddedWordsLt = getFluencyAddedWords(fluencyMarks.getFluencyMarksId());
		        fluencyMarks.setFluencyAddedWordsLt(fluencyAddedWordsLt);
	        }
	   		}catch(Exception e){
				logger.error("Error in getFluencyMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return fluencyMarks;
		}
		
		@Override
		public boolean gradeFluencyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorIdsString[],String errorsString[],long index){
			try{
			 String sql = "UPDATE assignment_questions set b_fluencymarks = ?,words_read=?,b_count_of_errors=?,errors_address=? where assignment_questions_id = ?";
		     jdbcTemplate.update(sql, new Object[] { correctWords,wordsRead,errors,errorIdsString,assignmentQuestionId });
			return true;
		 }catch(Exception e){
			 logger.error("Error in gradeFluencyTest() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
				return false;
			}
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		@Transactional 
		public boolean gradeAccuracyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String[] errorsString,long readingTypesId,List<FluencyMarksDetails> fluencyMarksDet,long gradeTypesId,List<FluencyAddedWords> FluencyAddedWordsLt,String percentage,String comment,Integer wcpm){	
			
			Session session = getSessionFactory().getCurrentSession();
			Session session1 = getSessionFactory().getCurrentSession();
			try{
				String hql = "UPDATE FluencyMarks set marks ="+correctWords+", countOfErrors="+errors+","
					+ "wordsRead="+wordsRead+",comment=:comment, accuracyScore="+percentage+", wcpm="+wcpm+" WHERE readingTypes.readingTypesId="+readingTypesId+" and assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId and gradingTypes.gradingTypesId="+gradeTypesId+"";
			    Query query = session.createQuery(hql);
			    query.setParameter("comment",comment);
			    query.setParameter("assignmentQuestionsId",assignmentQuestionId);
			    query.executeUpdate();
			    for(int i=0;i<fluencyMarksDet.size();i++){
					session.saveOrUpdate(fluencyMarksDet.get(i));
			    }
			     
	    	    for(int i=0;i<FluencyAddedWordsLt.size();i++){
	    	    	session1.saveOrUpdate(FluencyAddedWordsLt.get(i));
	    	    }
	    	    return true;
			 }catch(Exception e){
				 logger.error("Error in gradeAccuracyTest() of GradeAssessmentsDAOImpl", e);
				 e.printStackTrace();
				 return false;
			 }
		}
		
		
		@SuppressWarnings("unchecked")
		@Override
		public List<GradingTypes> getGradeTypes(){
			List<GradingTypes> list=new ArrayList<GradingTypes>();
			try{
	        list = (List<GradingTypes>) getHibernateTemplate()
	    				.find("from GradingTypes");
			}catch(Exception e){
				logger.error("Error in getGradeTypes() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		 public GradingTypes getGradingType(long gradeTypesId){
				List<GradingTypes> list=new ArrayList<GradingTypes>();
				try{
		        list = (List<GradingTypes>) getHibernateTemplate().find("from GradingTypes where gradingTypesId="+gradeTypesId);
		        }catch(Exception e){
					logger.error("Error in getGradeTypes() of GradeAssessmentsDAOImpl", e);
					e.printStackTrace();
				}
				if(list.size()>0)
					return list.get(0);
		        else
		        	return new GradingTypes();
				
		}
		
		@Override
		public boolean gradeSelfAndPeerBenchmark(long studentAssignmentId,long gradeTypesId)
		{
			try{
				 String sql="";
				if(gradeTypesId==2)
				  sql = "UPDATE student_assignment_status set self_graded_status= ? where student_assignment_id= ?";
				else
				  sql = "UPDATE student_assignment_status set peer_graded_status= ?, peer_submitdate=current_date where student_assignment_id= ?";
				jdbcTemplate.update(sql, new Object[] { "graded",studentAssignmentId});
				return true;
			 }catch(Exception e){
					e.printStackTrace();
					return false;
				}
		}
		
		@Override
		public int updateStudentAssessmentStatus(long studentAssignmentId){
			try{
				 String sql = "UPDATE student_assignment_status set graded_status= ?,graded_date=current_date,read_status=?  where student_assignment_id= ?";
			     jdbcTemplate.update(sql, new Object[] { "graded",WebKeys.UN_READ, studentAssignmentId });
				return 1;
			 }catch(Exception e){
					e.printStackTrace();
					return 0;
				}
		}
		
		@SuppressWarnings("rawtypes")
		public boolean gradeFluencyTestWithoutRFLP(long assignmentQuestionId,long wordsRead,long errors,long correctWords,long readingTypesId,long gradeTypesId,String percentage,String comment){
			Session session = getSessionFactory().getCurrentSession();
			try{
				String hql = "UPDATE FluencyMarks set marks ="+correctWords+", countOfErrors="+errors+","
						+ "wordsRead="+wordsRead+",accuracyScore="+percentage+",comment=:comment WHERE readingTypes.readingTypesId="+readingTypesId+" and assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId and gradingTypes.gradingTypesId="+gradeTypesId+"";
				Query query = session.createQuery(hql);
				query.setParameter("comment", comment);
				query.setParameter("assignmentQuestionsId",assignmentQuestionId);
				query.executeUpdate();
				return true;
			}catch(Exception e){
				logger.error("Error in gradeFluencyTestWithoutRFLP() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
				return false;
			}
		}
		
		@SuppressWarnings("unchecked")
		public List<FluencyAddedWords> getFluencyAddedWords(long fluencyMarksId){
			List<FluencyAddedWords> list=new ArrayList<FluencyAddedWords>();
			try{
				list = (List<FluencyAddedWords>) getHibernateTemplate().find("from FluencyAddedWords where fluencyMarks.fluencyMarksId=" + fluencyMarksId);
	   		}catch(Exception e){
				logger.error("Error in getFluencyAddedWords() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
		}
		
		@Override
		public boolean deleteFluencyAddedWords(long fluencyMarksId){
			try{
			String query = "delete from fluency_added_words where fluency_marks_id=?";
			jdbcTemplate.update(query, fluencyMarksId);
			return true;
			}catch(Exception e){
				 logger.error("Error in deleteFluencyAddedWords() of GradeAssessmentsDAOImpl", e);
				 e.printStackTrace();
				 return false;
			}
	    	    
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<FluencyMarks> getSelfAndPeerFluencyMarks(long assignmentQuestionId,long readingTypesId){
			List<FluencyMarks> list=new ArrayList<FluencyMarks>();	
					
			try{
				list = (List<FluencyMarks>) getHibernateTemplate()
						.find("from FluencyMarks where assignmentQuestions.assignmentQuestionsId=" + assignmentQuestionId+" and readingTypes.readingTypesId="+readingTypesId+"");	      	      
	   		}catch(Exception e){
				logger.error("Error in getFluencyMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
		}
		
		@Override
		public void autoSaveAddedWord(long assignmentQuestionId,long readingTypesId,long gradeTypesId,FluencyAddedWords FluencyAddedWordsLt){
			try{
				Session session1 = sessionFactory.openSession();			   
		    	Transaction tx1 = (Transaction) session1.beginTransaction();
				session1.saveOrUpdate(FluencyAddedWordsLt);
				tx1.commit();
			 }catch(Exception e){
				 logger.error("Error in autoSaveAddedWord() of GradeAssessmentsDAOImpl", e);
				 e.printStackTrace();				 
			 }		
		}
		
		@Override
		public boolean removeAddedWord(long wordIndex,long fluencyMarksId,int wordType){
			int out = 0;
				try {
					String query = "delete from fluency_added_words where word_index=? and fluency_marks_id=? and word_type=?";
					out = jdbcTemplate.update(query,wordIndex,fluencyMarksId,wordType);
				} catch (Exception e) {
					logger.error("Error in removeAddedWord() of gradeAssessmentDAOImpl"
							+ e);
					e.printStackTrace();
				}
				if (out != 0) {
					return true;
				} else {
					return false;
				}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public long checkAddedWordExists(long wordIndex,long fluencyMarksId){
			List<FluencyAddedWords> fluencyAddWordDetails = new ArrayList<FluencyAddedWords>();
			try{
				fluencyAddWordDetails = (List<FluencyAddedWords>) getHibernateTemplate().find("from FluencyAddedWords where fluencyMarks.fluencyMarksId="+fluencyMarksId+" and wordIndex="+wordIndex+"");
			}catch (HibernateException e) {
				logger.error("Error in checkAddedWordExists() of gradeAssessmentDAOImpl" + e);
				e.printStackTrace();
				throw new DataException("Error in checkAddedWordExists() of gradeAssessmentDAOImpl",e);
			}
			if(fluencyAddWordDetails.size()>0)
				return fluencyAddWordDetails.get(0).getAddedWordId();
			else
				return 0;
		}
		
		public void autoSaveErrorWord(FluencyMarksDetails fluMarkDet){
			try{
				Session session1 = sessionFactory.openSession();
			   	Transaction tx1 = (Transaction) session1.beginTransaction();
				session1.saveOrUpdate(fluMarkDet);
				tx1.commit();
				session1.close();
			 }catch(Exception e){
				  logger.error("Error in autoSaveErrorWord() of GradeAssessmentsDAOImpl", e);
				  e.printStackTrace();
						
				  }		
		}
		
		
		@SuppressWarnings("rawtypes")
		@Override
		public void autoSaveErrorComment(long fluencyMarksId,String errComment,long errAddress){
			Session session = getSessionFactory().getCurrentSession();
			try{
				String hql = "UPDATE  FluencyMarksDetails set comments=:comment WHERE fluencyMarks.fluencyMarksId=:fluencyMarkId and errorsAddress=:errorAddress";
				Query query = session.createQuery(hql);
				query.setParameter("comment",errComment);
				query.setParameter("fluencyMarkId",fluencyMarksId);
				query.setParameter("errorAddress",errAddress);
				query.executeUpdate();			
			}catch(Exception e){
				logger.error("Error in deleteFluencyMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();				
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public long checkErrorWordExists(long fluencyMarksId,long errorAddress){
			List<FluencyMarksDetails> fluencyMarksDetails = new ArrayList<FluencyMarksDetails>();
			try{
				fluencyMarksDetails = (List<FluencyMarksDetails>) getHibernateTemplate().find("from FluencyMarksDetails where fluencyMarks.fluencyMarksId="+fluencyMarksId+" and errorsAddress="+errorAddress+"");
			}catch (HibernateException e) {
				logger.error("Error in checkErrorWordExists() of gradeAssessmentDAOImpl" + e);
				e.printStackTrace();
				throw new DataException("Error in checkErrorWordExists() of gradeAssessmentDAOImpl",e);
			}
			if(fluencyMarksDetails.size()>0)
				return fluencyMarksDetails.get(0).getFluencyMarksDetailsId();
			else
				return 0;
		}
		
		@Override
		public boolean removeErrorWord(long fluencyMarksId,long errAddress){
			int out = 0;
			try {
				String query = "delete from fluency_marks_details where errors_address=? and fluency_marks_id=?";

				out = jdbcTemplate.update(query,errAddress,fluencyMarksId);
			} catch (Exception e) {
				logger.error("Error in removeErrorWord() of gradeAssessmentDAOImpl"
						+ e);
				e.printStackTrace();
			}
			if (out != 0) {
				return true;
			} else {
				return false;
			}
			
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public void autoGradeAccuracy(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long wordsRead,long errRead,long totalRead,String percentage){
			Session session = getSessionFactory().getCurrentSession();
			
			try{
				String hql = "UPDATE FluencyMarks set marks ="+totalRead+", countOfErrors="+errRead+","
						+ "wordsRead="+wordsRead+", accuracyScore="+percentage+" WHERE readingTypes.readingTypesId="+readingTypesId+" and assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId and gradingTypes.gradingTypesId="+gradeTypesId+"";
				Query query = session.createQuery(hql);
			    query.setParameter("assignmentQuestionsId",assignmentQuestionId);
			    query.executeUpdate();			    
			}catch(Exception e){
				logger.error("Error in gradeAccuracyTest() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();				
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<FluencyComments>  getFluencyComments(){
			List<FluencyComments> fluencyCommentsLt = new ArrayList<FluencyComments>();
			try{
				fluencyCommentsLt = (List<FluencyComments>) getHibernateTemplate().find("from FluencyComments");
			}catch (HibernateException e) {
				logger.error("Error in getFluencyComments() of gradeAssessmentDAOImpl" + e);
				e.printStackTrace();
				throw new DataException("Error in getFluencyComments() of gradeAssessmentDAOImpl",e);
			}
			return fluencyCommentsLt;
		}
		@SuppressWarnings("unchecked")
		@Override
		public void autoSaveComments(FluencyMarks fluMarks){
			try{
				Session session1 = sessionFactory.openSession();			   
		    	Transaction tx1 = (Transaction) session1.beginTransaction();
				session1.saveOrUpdate(fluMarks);
				tx1.commit();
			 }catch(Exception e){
				 logger.error("Error in autoSaveComments() of GradeAssessmentsDAOImpl", e);
				 e.printStackTrace();				 
			 }	
		}
		@SuppressWarnings("rawtypes")
		@Override
		public boolean retestFluencyAndAccuracy(List<FluencyMarks> fluencyMarks, long studentAssignmentId){
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			boolean check=false;
			try{
				String hql = "UPDATE StudentAssignmentStatus set status ='pending', gradedStatus='not graded',selfGradedStatus='not graded',peerGradedStatus='not graded',submitdate=null  WHERE studentAssignmentId=:studentAssignmentId";
				Query query = session.createQuery(hql);
			    query.setParameter("studentAssignmentId",studentAssignmentId);
			    query.executeUpdate();
			    
			    for(FluencyMarks fMarks : fluencyMarks) {
					Query m = session.createQuery("delete from FluencyMarksDetails where fluencyMarks.fluencyMarksId="+fMarks.getFluencyMarksId());
					m.executeUpdate();
					Query m2 = session.createQuery("UPDATE FluencyMarks set wordsRead=null,countOfErrors=null,marks=null,accuracyScore=null,wcpm=null,comment=null, qualityOfResponse = null  where fluencyMarksId="+fMarks.getFluencyMarksId());
					m2.executeUpdate();
					Query m1 = session.createQuery("delete from FluencyAddedWords where fluencyMarks.fluencyMarksId="+fMarks.getFluencyMarksId());
					m1.executeUpdate();
			    }
				transaction.commit();
			    check=true;
			}catch(HibernateException e){
				logger.error("Error in retestFluencyAndAccuracy() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();	
				transaction.rollback();
				return false;
			}
			return check;
		}
		
				
		@SuppressWarnings("unchecked")
		@Override
		public void autoGradeFluency(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long errRead){
			Session session = getSessionFactory().getCurrentSession();
			try{
				String hql = "UPDATE FluencyMarks set countOfErrors="+errRead+""
						+ " WHERE readingTypes.readingTypesId="+readingTypesId+" and assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId and gradingTypes.gradingTypesId="+gradeTypesId+"";
				Query query = session.createQuery(hql);
			    query.setParameter("assignmentQuestionsId",assignmentQuestionId);
			    query.executeUpdate();			    
			}catch(Exception e){
				logger.error("Error in gradeAccuracyTest() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();				
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public void autoSaveWordCount(long fluencyMarksId,long errRead,long wordsRead,long totalRead){
			Session session = getSessionFactory().getCurrentSession();
			try{
				String hql = "UPDATE FluencyMarks set countOfErrors="+errRead+", wordsRead="+wordsRead
						+ " WHERE fluencyMarksId="+fluencyMarksId+"";
				Query query = session.createQuery(hql);
			    query.executeUpdate();			    
			}catch(Exception e){
				logger.error("Error in autoSaveWordCount() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();				
			}
		}
		
		@Override
		public boolean gradeFluencyReadingTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,long readingTypesid,long gradeTypesId,String percentage,String comment){
			Session session = getSessionFactory().getCurrentSession();
			
			try{
				String hql = "UPDATE FluencyMarks set marks ="+correctWords+", countOfErrors="+errors+","
					+ "wordsRead="+wordsRead+",comment=:comment, accuracyScore="+percentage+" WHERE readingTypes.readingTypesId="+readingTypesid+" and assignmentQuestions.assignmentQuestionsId=:assignmentQuestionsId and gradingTypes.gradingTypesId="+gradeTypesId+"";
			    Query query = session.createQuery(hql);
			    query.setParameter("comment",comment);
			    query.setParameter("assignmentQuestionsId",assignmentQuestionId);
			    query.executeUpdate();
			 	return true;
			 }catch(Exception e){
				 logger.error("Error in gradeAccuracyTest() of GradeAssessmentsDAOImpl", e);
				 e.printStackTrace();
				 return false;
			 }
		}
		public List<AssignmentQuestions> getStudentAssignmentQuestions(long studentId,long assignmentId){
			List<AssignmentQuestions> list=new ArrayList<AssignmentQuestions>();
			FluencyMarks fluencyMarks = new FluencyMarks();
			try{
	        list = (List<AssignmentQuestions>) getHibernateTemplate().find("from AssignmentQuestions where studentAssignmentStatus.student.studentId="+studentId+" and studentAssignmentStatus.assignment.assignmentId="+assignmentId+"");
	            
	   		}catch(Exception e){
				logger.error("Error in getStudentAssignmentQuestions() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<FluencyMarks> getStudSelfAndPeerFluencyMarks(long assignmentId){
			List<FluencyMarks> list=new ArrayList<FluencyMarks>();
			FluencyMarks fluencyMarks = new FluencyMarks();
			try{
	        list = (List<FluencyMarks>) getHibernateTemplate()
	    				.find("from FluencyMarks where assignmentQuestions.studentAssignmentStatus.assignment.assignmentId=" + assignmentId+" "
	    						+ "and (assignmentQuestions.studentAssignmentStatus.gradedStatus = 'graded' or assignmentQuestions.studentAssignmentStatus.peerGradedStatus = 'graded'"
	    						+ " or assignmentQuestions.studentAssignmentStatus.selfGradedStatus = 'graded')  and readingTypes.readingTypesId=1");
	        fluencyMarks = list.get(0);
	        for(int i=0;i<list.size();i++){
	        	 fluencyMarks = list.get(i);
	        	 if(fluencyMarks.getFluencyMarksId() > 0){
	        		 List<FluencyMarksDetails> fluencyMarkDets = getErrorsList(fluencyMarks.getFluencyMarksId());
	        		 list.get(i).setFluencyMarksDetails(fluencyMarkDets);
	        	 }
	   		}
			}catch(Exception e){
				logger.error("Error in getFluencyMarks() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
			}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<FluencyMarks> getFluencyMarks(long studentAssignmentId){
			List<FluencyMarks> list=new ArrayList<FluencyMarks>();
			try{
	        list = (List<FluencyMarks>) getHibernateTemplate()
	    				.find("from FluencyMarks where assignmentQuestions.studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+"");
			}catch(Exception e){
				logger.error("Error in getBenchmarkTypes() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list;
		}
		
		@Override
		public boolean saveWCPM(long assignmentQuestionId, Integer wcpm, long gradingTypeId, long readingTypeId) {
			try{
				Session session = getHibernateTemplate().getSessionFactory().openSession();
				Transaction transaction = session.beginTransaction();
				String hql = "UPDATE FluencyMarks set wcpm="+wcpm+" WHERE readingTypes.readingTypesId="+readingTypeId+" and "
					+ "assignmentQuestions.assignmentQuestionsId="+assignmentQuestionId+" and gradingTypes.gradingTypesId="+gradingTypeId+"";
			    Query query = session.createQuery(hql);
			    query.executeUpdate();
			    transaction.commit();
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}	
