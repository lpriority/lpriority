package com.lp.student.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.AcademicGrades;
import com.lp.model.AssignmentQuestions;
import com.lp.model.MathConversionTypes;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;


public class MathAssessmentTestDAOImpl extends CustomHibernateDaoSupport implements MathAssessmentTestDAO {
	static final Logger logger = Logger.getLogger(MathAssessmentTestDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentMathAssessMarks> getMathAssessmentQues(long studentAssignmentId){
		List<StudentMathAssessMarks> mathQuizQuestions = null;
		try{
			mathQuizQuestions = (List<StudentMathAssessMarks>) getHibernateTemplate().find("FROM StudentMathAssessMarks WHERE "
					+ "studentAssignmentStatus.studentAssignmentId="+studentAssignmentId +" order by mathQuizQuestions.quizQuestionsId " );
		}
		catch(Exception e){
			logger.error("Error in getStudentAssessmentTests() of  MathAssessmentTestDAOImpl" +e.getStackTrace());
		}
		return mathQuizQuestions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MathConversionTypes> getMathConversionTypes(){
		List<MathConversionTypes> conversionTypes = null;
		try{
			conversionTypes = (List<MathConversionTypes>) getHibernateTemplate().find("FROM MathConversionTypes WHERE status='"+WebKeys.ACTIVE+"' order by conversionTypeId");			
		}
		catch(Exception e){
			logger.error("Error in getMathConversionTypes() of  MathAssessmentTestDAOImpl" +e.getStackTrace());
		}
		return conversionTypes;		
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean submitMathTest(StudentAssignmentStatus stAssignmentStatus){		
		boolean flag = false;
		int maxMarks = 0, secMarks = 0, mark = 0;
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			 tx = (Transaction) session.beginTransaction();
			for(StudentMathAssessMarks assessMarks : stAssignmentStatus.getMathAssessMarks()){
				mark = 0;
				maxMarks++;
				if(assessMarks.getAnswer().isEmpty()){
					mark = 0;
				}
				else if(assessMarks.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()==3)
				{
					try{
						if(Double.parseDouble(assessMarks.getActualAnswer())==Double.parseDouble((assessMarks.getAnswer()))){ 
							mark = 1;
							secMarks++;
						}	
					}
					catch(NumberFormatException e){
					}
					
				}
				else if(assessMarks.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()==5 ||assessMarks.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()==4){
					try{
						if(Double.parseDouble(assessMarks.getActualAnswer()) == Double.parseDouble(assessMarks.getAnswer().replace("%", ""))){
							mark = 1;
							secMarks++;
						}
					}
					catch(NumberFormatException e){
						e.printStackTrace();
					}
				}
				else if(assessMarks.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()>5 || assessMarks.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId() == 1 ){
					
					if(assessMarks.getAnswer().contains("/") && convertToDecimal(assessMarks.getActualAnswer()) == convertToDecimal(assessMarks.getAnswer())){
						mark = 1;
						secMarks++;
					}
				}
				else if(assessMarks.getActualAnswer().equals(assessMarks.getAnswer())){
					mark = 1;
					secMarks++;
				}
				else{
					mark = 0;
				}
				
				String hql1 = "UPDATE StudentMathAssessMarks set answer = :answer, mark = :mark"
						+ " WHERE studentMathAssessMarksId=:studentMathAssessMarksId";
				Query query1 = session.createQuery(hql1);	
				query1.setParameter("answer", assessMarks.getAnswer());
				query1.setParameter("mark", mark);
				query1.setParameter("studentMathAssessMarksId", assessMarks.getStudentMathAssessMarksId());
				query1.executeUpdate();
			}
			float percentage = Float.valueOf(secMarks*100/maxMarks);
			stAssignmentStatus.setPercentage(percentage);
			AcademicGrades academicGrades = gradeAssessmentsDAO.getAcademicGradeByPercentage(percentage);
			stAssignmentStatus.setAcademicGrade(academicGrades);
			stAssignmentStatus.setStatus(WebKeys.TEST_STATUS_SUBMITTED);
			stAssignmentStatus.setSubmitdate(new Date());
			stAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_GRADED);
			stAssignmentStatus.setGradedDate(new Date());
			stAssignmentStatus.setReadStatus(WebKeys.UN_READ);
			session.saveOrUpdate(stAssignmentStatus);
			tx.commit();
			flag = true;
		}
		 catch (HibernateException e) {
			e.printStackTrace();
				tx.rollback();			
			logger.error("Error in submitMathTest() of  MathAssessmentTestDAOImpl" +e.getStackTrace());
		}
		finally{
			session.close();
		}
		return flag;
	}
	private float convertToDecimal(String ratio) {
		String[] rat = null;
		float value = 0;
		try{
			rat = ratio.split("/");
			value =  Float.parseFloat(rat[0]) / Float.parseFloat(rat[1]);
		}
		catch(NullPointerException | NumberFormatException e){
			return 0;
		}
        return value;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StudentMathAssessMarks getStudentMathAssessment(long mathAssessMarksId){
		List<StudentMathAssessMarks> list=new ArrayList<StudentMathAssessMarks>();
		try{
        list = (List<StudentMathAssessMarks>) getHibernateTemplate()
    				.find("from StudentMathAssessMarks where studentMathAssessMarksId= " + mathAssessMarksId);
		}catch(Exception e){
			logger.error("Error in getStudentMathAssessment() of MathAssessmentTestDAOImpl", e);
			e.printStackTrace();
		}
    	return list.get(0);
	}
	@SuppressWarnings("rawtypes")
	public boolean autoSaveMathAnswer(StudentMathAssessMarks studMathAns){
		boolean flag = false;
		int mark = 0;
		Session session = null;
		Transaction tx = null;
		mark = 0;
		try{
		session = sessionFactory.openSession();
		 tx = (Transaction) session.beginTransaction();
		 System.out.println("aswer="+studMathAns.getAnswer());
		 System.out.println("original answer="+studMathAns.getMathQuizQuestions().getActualAnswer());
		if(studMathAns.getAnswer().isEmpty()){
			mark = 0;
		}
		else if(studMathAns.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()==3)
		{
			try{
				if(Double.parseDouble(studMathAns.getMathQuizQuestions().getActualAnswer())==Double.parseDouble((studMathAns.getAnswer()))){ 
					mark = 1;
					
				}	
			}
			catch(NumberFormatException e){
				e.printStackTrace();
			}
			
		}
		else if(studMathAns.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()==5 || studMathAns.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()==4){
			try{
				if(Double.parseDouble(studMathAns.getMathQuizQuestions().getActualAnswer()) == Double.parseDouble(studMathAns.getAnswer().replace("%", ""))){
					mark = 1;
			}
			}
			catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		else if(studMathAns.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId()>5 || studMathAns.getMathQuizQuestions().getMathConversionTypes().getConversionTypeId() == 1 ){
			
			if(studMathAns.getAnswer().contains("/") && convertToDecimal(studMathAns.getMathQuizQuestions().getActualAnswer()) == convertToDecimal(studMathAns.getAnswer())){
				mark = 1;
				
			}
		}
		else if(studMathAns.getMathQuizQuestions().getActualAnswer().equals(studMathAns.getAnswer())){
			mark = 1;
			
		}
		else{
			mark = 0;
		}
		
		String hql1 = "UPDATE StudentMathAssessMarks set answer = :answer, mark = :mark"
				+ " WHERE studentMathAssessMarksId=:studentMathAssessMarksId";
		Query query1 = session.createQuery(hql1);	
		query1.setParameter("answer", studMathAns.getAnswer());
		query1.setParameter("mark", mark);
		query1.setParameter("studentMathAssessMarksId", studMathAns.getStudentMathAssessMarksId());
		query1.executeUpdate();
		tx.commit();
		flag = true;
	}catch(HibernateException e) {
		    flag=false;
			e.printStackTrace();
			logger.error("Error in autoSaveMathAnswer() of  MathAssessmentTestDAOImpl" +e.getStackTrace());
	}finally{
			session.close();
	}
	return flag;
	}
}

