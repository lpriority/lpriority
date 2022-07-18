package com.lp.student.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.service.CommonService;
import com.lp.model.AcademicYear;
import com.lp.model.ReadRegActivity;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegPageRange;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegQuiz;
import com.lp.model.ReadRegReview;
import com.lp.model.ReadRegRubric;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.student.controller.StudentReadRegController;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;
@RemoteProxy(name = "studentReadRegService")
public class StudentReadRegServiceImpl implements StudentReadRegService {
	static final Logger logger = Logger.getLogger(StudentReadRegController.class);
	@Autowired
	HttpSession session;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;	
	@Autowired
	private CommonService commonService;
	
	@Override
	public String saveOrUpdateBook(ReadRegMaster readRegMaster){
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		if(student == null)
			student = commonService.getStudentByRegId(readRegMaster.getUserRegistration().getRegId());
		ReadRegReview readRegReview = getReadRegReviewByStudentId(readRegMaster.getTitleId(), student.getStudentId());
		if(readRegReview == null) {
			readRegReview = new ReadRegReview();
			readRegReview.setGrade(readRegMaster.getGrade());
			readRegReview.setReadRegMaster(readRegMaster);
			readRegReview.setStudent(student);
			readRegReview.setRating(readRegMaster.getRating());
		}else {
			readRegReview.setReadRegMaster(readRegMaster);
			readRegReview.setRating(readRegMaster.getRating());
		}
		return studentReadRegDAO.saveOrUpdateBook(readRegMaster, readRegReview);		
	}
	
	@Override
	public List<ReadRegMaster> getAllAddedBooks(long masterGradesId){
		return studentReadRegDAO.getAllAddedBooks(masterGradesId);
	}
	
	@Override	
	public long getBookCount(long masterGradesId, String bookName) {
		return studentReadRegDAO.getBookCount(masterGradesId, bookName);
	}

	@Override
	public List<ReadRegMaster> getBooks(long masterGradesId, long pageId, long rows, String sortBy, String sortOrder, String bookName){
		List<ReadRegMaster> list = null;
		if(pageId>1){  
	       pageId=(pageId-1)*rows;    
	    }
		else if(pageId == 1){
			pageId = 0;
		}
		System.out.println("masterGradesId="+masterGradesId+"sortBy"+sortBy+"bookName="+bookName+"sortOrder="+sortOrder);
		list = studentReadRegDAO.getBooks(masterGradesId, pageId, rows, sortBy, sortOrder, bookName);
		System.out.println("size="+list.size());
		return list;
	}
	
	@Override
	public ReadRegMaster getBookDetails(long bookId){
		return studentReadRegDAO.getBookDetails(bookId);
	}
	
	@Override
	public List<ReadRegMaster> getStudentBooks(long regId){
		return studentReadRegDAO.getStudentBooks(regId);
	}
	
	@Override
	public ReadRegMaster getReadRegMasterByTitleId(long titleId){
		return studentReadRegDAO.getReadRegMasterByTitleId(titleId);
	}

	@Override
	public String saveOrUpdateReview(ReadRegReview readRegReview, String mode) {
		ReadRegActivityScore rActivityScore  = studentReadRegDAO.getStudentActivity(readRegReview.getStudent().getStudentId(), readRegReview.getReadRegMaster().getTitleId(), WebKeys.READ_REG_REVIEW_ACTIVITY_ID);
		if(rActivityScore == null){
			rActivityScore = new ReadRegActivityScore();
			rActivityScore.setReadRegReview(readRegReview);
			rActivityScore.setReadRegMaster(readRegReview.getReadRegMaster());
			rActivityScore.setStudent(readRegReview.getStudent());
			rActivityScore.setTeacher(studentReadRegDAO.getTeacher(readRegReview.getStudent().getStudentId()));
			rActivityScore.setCreateDate(new Date());
			rActivityScore.setGrade(readRegReview.getGrade());
			ReadRegActivity readRegActivity = new ReadRegActivity();
			readRegActivity.setActivityId(WebKeys.READ_REG_REVIEW_ACTIVITY_ID);
			rActivityScore.setReadRegActivity(readRegActivity);
			rActivityScore.setApproveStatus("waiting");
		}
		if(readRegReview.getRubricScore()>0){
			ReadRegRubric selfScore = new ReadRegRubric();
			selfScore.setReadRegRubricId(readRegReview.getRubricScore());
			rActivityScore.setSelfScore(selfScore);
		}
		
		return studentReadRegDAO.saveOrUpdateReview(readRegReview, rActivityScore);
	}
	
	@Override
	public ReadRegReview getReadRegReviewByReviewId(long reviewId){
		return studentReadRegDAO.getReadRegReviewByReviewId(reviewId);
	}
		
	@Override
	public ReadRegReview getReadRegReviewByStudentId(long titleId, long studentId){
		return studentReadRegDAO.getReadRegReviewByStudentId(titleId, studentId);
	}
	
	@Override
	public List<ReadRegQuiz> getAllQuizQuestionList(){
		return studentReadRegDAO.getAllQuizQuestionList();
	}
	
	@Override
	public String saveCreateQuestions(ReadRegQuestions regQuestions,int rating){
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		ReadRegActivityScore rActivityScore = null;
		if(regQuestions.getReadRegQuiz().getQuestionNum() == WebKeys.READ_REG_QUIZ_QUESTIONS_COUNT){
			rActivityScore  = studentReadRegDAO.getStudentActivity(student.getStudentId(), 
					regQuestions.getReadRegMaster().getTitleId(), WebKeys.READ_REG_CREATE_QUIZ_ACTIVITY_ID);
			if(rActivityScore == null){
				rActivityScore = new ReadRegActivityScore();
				ReadRegReview readRegReview = studentReadRegDAO.getReadRegReviewByStudentId(regQuestions.getReadRegMaster().getTitleId(), student.getStudentId());
				if(readRegReview == null){
					readRegReview = new ReadRegReview();
					readRegReview.setStudent(student);
					readRegReview.setReadRegMaster(regQuestions.getReadRegMaster());
					readRegReview.setRating(rating);
				}else{
					readRegReview.setRating(rating);
				}
				rActivityScore.setReadRegReview(readRegReview);
				rActivityScore.setReadRegMaster(regQuestions.getReadRegMaster());
				rActivityScore.setStudent(student);
				rActivityScore.setGrade(student.getGrade());
				rActivityScore.setTeacher(studentReadRegDAO.getTeacher(student.getStudentId()));
				rActivityScore.setCreateDate(new Date());
				ReadRegActivity readRegActivity = new ReadRegActivity();
				readRegActivity.setActivityId(WebKeys.READ_REG_CREATE_QUIZ_ACTIVITY_ID);
				rActivityScore.setReadRegActivity(readRegActivity);
				rActivityScore.setApproveStatus("waiting");
			}	
			if(regQuestions.getRubricScore()>0){
				ReadRegRubric selfScore = new ReadRegRubric();
				selfScore.setReadRegRubricId(regQuestions.getRubricScore());
				rActivityScore.setSelfScore(selfScore);
			}
		}
		return studentReadRegDAO.saveCreateQuestions(regQuestions, rActivityScore);
	}
	
	@Override
	public ReadRegQuestions getStudentQuestion(long titleId, long studentId, long quetionNum){
		return studentReadRegDAO.getStudentQuestion(titleId, studentId, quetionNum);
	}

	@Override
	public String updateReadRegQuestion(ReadRegQuestions readRegQuestions) {
		return studentReadRegDAO.updateReadRegQuestion(readRegQuestions);
	}
	
	@Override
	public List<Student> getAllQuizQuestionsGroupByTitleId(long titleId, long studentId){
		return studentReadRegDAO.getAllQuizQuestionsGroupByTitleId(titleId, studentId);
	}
	
	public List<ReadRegQuestions> getStudentAllQuizQuestionByTitleId(long titleId, long studentId){
		return studentReadRegDAO.getStudentAllQuizQuestionByTitleId(titleId, studentId);
	}
	
	@Override
	public List<ReadRegAnswers> getStudentAnswerByTitleId(long titleId, long currentStudentId, long createdStudentId){
		return studentReadRegDAO.getStudentAnswerByTitleId(titleId, currentStudentId, createdStudentId);
	}
	
	@Override
	public String submitQuizAnswers(List<ReadRegAnswers> readRegAnswersLt,int rating,long titleId){
		String sss="";
		try{
			
		Student student = readRegAnswersLt.get(0).getCurrentStudent();
		ReadRegMaster readRegMaster = studentReadRegDAO.getReadRegMasterByTitleId(titleId);
		ReadRegActivityScore rActivityScore  = studentReadRegDAO.getStudentActivity(student.getStudentId(), 
				readRegMaster.getTitleId(), WebKeys.READ_REG_TAKE_QUIZ_ACTIVITY_ID);
		
		if(rActivityScore == null){
			rActivityScore = new ReadRegActivityScore();
			ReadRegReview readRegReview = studentReadRegDAO.getReadRegReviewByStudentId(readRegMaster.getTitleId(), student.getStudentId());
			if(readRegReview == null){
				readRegReview = new ReadRegReview();
				readRegReview.setStudent(student);
				readRegReview.setReadRegMaster(readRegMaster);
				readRegReview.setRating(rating);
			}else{
				readRegReview.setRating(rating);
			}

			rActivityScore.setReadRegReview(readRegReview);
			rActivityScore.setReadRegMaster(readRegMaster);
			rActivityScore.setStudent(student);
			rActivityScore.setGrade(student.getGrade());
			rActivityScore.setTeacher(studentReadRegDAO.getTeacher(student.getStudentId()));
			rActivityScore.setCreateDate(new Date());
			rActivityScore.setApproveStatus(WebKeys.LP_APPROVED);
			ReadRegActivity readRegActivity = new ReadRegActivity();
			readRegActivity.setActivityId(WebKeys.READ_REG_TAKE_QUIZ_ACTIVITY_ID);
			rActivityScore.setReadRegActivity(readRegActivity);
		}	
		long score=0;
		for(ReadRegAnswers aRegAnswers : readRegAnswersLt){
			score = score+aRegAnswers.getMark();
		}
		if(score == 5)
			score = 4;
		ReadRegRubric regRubric = studentReadRegDAO.getReadRegRubric(score);
		rActivityScore.setSelfScore(regRubric);
		rActivityScore.setReadRegRubric(regRubric);
		long activityValue=studentReadRegDAO.getActivityValue(WebKeys.READ_REG_TAKE_QUIZ_ACTIVITY_ID);
		long pointsEarned = readRegMaster.getReadRegPageRange().getRange()*activityValue*score;
		rActivityScore.setPointsEarned(pointsEarned);
		sss=studentReadRegDAO.submitQuizAnswers(readRegAnswersLt, rActivityScore);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sss;
	}
	@Override
	public List<ReadRegRubric> getReadRegRubric(){
		return studentReadRegDAO.getReadRegRubric();
	}
	@Override
	public ReadRegActivityScore getStudentActivity(long studentId, long titleId, long activityId){
		return studentReadRegDAO.getStudentActivity(studentId, titleId, activityId);
	}
	@Override
	public String saveOrUpdateActivityScore(Student student, long titleId, long rubricScore, long activityId, String mode,int rating){
		ReadRegReview readRegReview = null;
		ReadRegMaster readRegMaster = new ReadRegMaster();
		ReadRegActivityScore rActivityScore  = studentReadRegDAO.getStudentActivity(student.getStudentId(), titleId, activityId);
		if(rActivityScore == null) {
			rActivityScore = new ReadRegActivityScore();			
			readRegMaster.setTitleId(titleId);
			rActivityScore.setReadRegMaster(readRegMaster);
			rActivityScore.setStudent(student);
			rActivityScore.setGrade(student.getGrade());
			rActivityScore.setTeacher(studentReadRegDAO.getTeacher(student.getStudentId()));
			rActivityScore.setCreateDate(new Date());
			ReadRegActivity readRegActivity = new ReadRegActivity();
			readRegActivity.setActivityId(activityId);
			rActivityScore.setReadRegActivity(readRegActivity);
			rActivityScore.setApproveStatus("waiting");
			readRegReview = studentReadRegDAO.getReadRegReviewByStudentId(titleId, student.getStudentId());
			if(readRegReview == null){
				readRegReview = new ReadRegReview();
				readRegReview.setStudent(student);
				readRegReview.setRating(rating);
				readRegReview.setReadRegMaster(readRegMaster);
			}else{
				readRegReview.setRating(rating);
			}
			
			rActivityScore.setReadRegReview(readRegReview);
		}

		if(rubricScore > 0){
			ReadRegRubric selfScore = new ReadRegRubric();
			selfScore.setReadRegRubricId(rubricScore);
			rActivityScore.setSelfScore(selfScore);
			//rActivityScore.setApproveStatus("waiting");
		}
		return studentReadRegDAO.saveOrUpdateActivityScore(rActivityScore, readRegReview);
	}
	@Override
	public List<ReadRegQuestions> getStudentAllCreatedQuestionsList(long titleId, long studentId){
		return studentReadRegDAO.getStudentAllCreatedQuestionsList(titleId, studentId);
	}
	
	@Override
	public ReadRegActivityScore getStudentActivity(long readRegActivityScoreId){
		return studentReadRegDAO.getStudentActivity(readRegActivityScoreId);
	}
	
	@Override
	public List<ReadRegActivityScore> getStudentActivities(long studentId, String sortBy, String startDate, String endDate){
		return studentReadRegDAO.getStudentActivities(studentId, sortBy, startDate, endDate);		
	}
	
	@Override
	public List<ReadRegActivityScore> getParentChildActivities(long studentId, String sortBy){
		return studentReadRegDAO.getParentChildActivities(studentId, sortBy);		
	}
	
	@Override
	public long getStudentTotalPointsEarned(long studentId, long gradeId){
		return studentReadRegDAO.getStudentTotalPointsEarned(studentId, gradeId);
	}
	
	@Override
	public boolean deleteBook(ReadRegMaster readRegMaster) {
		return studentReadRegDAO.deleteBook(readRegMaster);
	}
	@Override
		public boolean TeacherSaveBook(ReadRegMaster readRegMaster) {
		  ReadRegPageRange pageRange = studentReadRegDAO.getPageRange(readRegMaster.getNumberOfPages());
		  readRegMaster.setReadRegPageRange(pageRange);
		 return studentReadRegDAO.TeacherSaveBook(readRegMaster);
		}
	@Override
	public boolean checkActivityExists(long titleId,long studentId) {
		  return studentReadRegDAO.checkActivityExists(titleId,studentId);
	}
	@Override
	public boolean checkBookExists(String bookTitle,long masterGradesId){
		 return studentReadRegDAO.checkBookExists(bookTitle,masterGradesId);
	}
	@Override
	public List<ReadRegMaster> getRejectedBooksByRegId(long regId, AcademicYear academicYear){
		return studentReadRegDAO.getRejectedBooksByRegId(regId, academicYear );
	}
	@Override
	public boolean reSubmitBook(long titleId,long studentId){
		return studentReadRegDAO.reSubmitBook(titleId,studentId);
	}
	@Override
	public int getStudentRating(long titleId,long studentId){
		int rating=0;
		try{
			rating= studentReadRegDAO.getStudentRating(titleId,studentId);
		}catch(Exception e){
			logger.error("Error in getStudentRating() of studentReadingRegServiceImpl"+ e);	
		}
		return rating;
	}
	@Override	
	public long getApprovalBookCount(long gradeId, long teacherId,String bookName, AcademicYear academicYear) {
		return studentReadRegDAO.getApprovalBookCount(gradeId, teacherId,bookName, academicYear);
	}
	
	@Override
	public String getRRPath(ReadRegActivityScore rActivityScore,  HttpServletRequest request) {
			//Date utilDate = new Date(rActivityScore.getCreateDate().getTime());
		long activityDate=rActivityScore.getCreateDate().getTime();
		java.sql.Date actDate=new java.sql.Date(activityDate);
		//System.out.println("actdate="+actDate);
			//AcademicYear academicYear = studentReadRegDAO.getRRAcademicYearByActivityDate(actDate.toString());
		    AcademicYear academicYear=commonService.getCurrentAcademicYear();
			System.out.println(academicYear.getAcademicYear());
			UserRegistration user = rActivityScore.getStudent().getUserRegistration();
			String fullPath = null;
			if(rActivityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_RETELL_ACTIVITY_ID)  {
				fullPath =  FileUploadUtil.getServerPath() +  File.separator+ WebKeys.LP_USERS_FILES +  File.separator+ rActivityScore.getGrade().getSchoolId() +File.separator+academicYear.getAcademicYear()+  File.separator+ user.getUser().getUserTypeid() + File.separator+ user.getRegId() + File.separator+  WebKeys.READING_REGISTER+File.separator+rActivityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT;
				
			} else {
				fullPath =  FileUploadUtil.getServerPath() +  File.separator+ WebKeys.LP_USERS_FILES +  File.separator+ rActivityScore.getGrade().getSchoolId() +File.separator+academicYear.getAcademicYear()+  File.separator+ user.getUser().getUserTypeid() + File.separator+ user.getRegId() + File.separator + WebKeys.READING_REGISTER+File.separator+ rActivityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;
				
			}
			return fullPath;
	}
	
}
