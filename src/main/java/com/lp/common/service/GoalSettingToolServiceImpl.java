package com.lp.common.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.GoalSettingToolDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.CAASPPScores;
import com.lp.model.GoalSampleIdeas;
import com.lp.model.GoalStrategies;
import com.lp.model.StarScores;
import com.lp.model.Student;
import com.lp.model.StudentCAASPPOwnStrategies;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Teacher;
import com.lp.model.Trimester;
import com.lp.teacher.dao.IOLReportCardDAO;

@RemoteProxy(name = "goalSettingToolService")
public class GoalSettingToolServiceImpl implements GoalSettingToolService {

	static final Logger logger = Logger.getLogger(GoalSettingToolServiceImpl.class);
	
	@Autowired
	private GoalSettingToolDAO goalSettingToolDAO;
	@Autowired
	private IOLReportCardDAO iolReportCardDAO;
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	
	@Override
	public List<CAASPPScores> getStudentCAASPPScores(long caasppTypesId,long studentId){
		List<CAASPPScores> studCAASPPScores=new ArrayList<CAASPPScores>();
		try{
			studCAASPPScores=goalSettingToolDAO.getStudentCAASPPScores(caasppTypesId,studentId);
		}catch(Exception e){
			logger.error("Error in getStudentGoalScores() of GoalSettingToolServiceImpl", e);
			e.printStackTrace();
		}
		return studCAASPPScores;
		
	}
	@Override
	public List<GoalStrategies> getGoalStrategiesByTypeId(long caasppTypesId){
		List<GoalStrategies> listGoalStrategies=new ArrayList<GoalStrategies>();
		try{
			listGoalStrategies=goalSettingToolDAO.getGoalStrategiesByTypeId(caasppTypesId);
		}catch(Exception e){
			logger.error("Error in getGoalStrategiesByTypeId() of GoalSettingToolServiceImpl", e);
			e.printStackTrace();
		}
		return listGoalStrategies;
	}
	@Override
	public List<StudentStarStrategies> getStudentStarStrategiesByTrimesterId(long studentId,long gradeId,long trimesterId,long caasppTypesId){
		List<StudentStarStrategies> listGoalStrategies=new ArrayList<StudentStarStrategies>();
		try{
			listGoalStrategies=goalSettingToolDAO.getStudentStarStrategiesByTrimesterId(studentId,gradeId,trimesterId,caasppTypesId);
		}catch(Exception e){
			logger.error("Error in getStudentStarStrategiesByTypeId() of GoalSettingToolServiceImpl", e);
			e.printStackTrace();
		}
		return listGoalStrategies;
	}
	@Override
	 public CAASPPScores getCAASPPScoresByCaaSppScoresId(long caasppScoresId){
			CAASPPScores caaSPPScores=new CAASPPScores();  
			  try{
				  caaSPPScores= goalSettingToolDAO.getCAASPPScoresByCaaSppScoresId(caasppScoresId);
				 }catch(DataException e){
					  logger.error("Error in getCAASPPScoresByCaaSppScoresId() of IOLReportCardServiceImpl", e);
				 }
			  return caaSPPScores;
		   
	 }
	@Override
	public void autoSaveStudStarStrategies(long strategyId,Student student,long trimesterId,long goalCount,long caasppTypesId){
		StudentStarStrategies studStarStrategies = new StudentStarStrategies();
		try{
			long check=goalSettingToolDAO.checkExistsGoalStrategies(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,goalCount,caasppTypesId);
			if(check>0){
				studStarStrategies.setStudStarStrategId(check);
			}
			studStarStrategies.setStudent(student);
			studStarStrategies.setGrade(student.getGrade());
			studStarStrategies.setTrimester(iolReportCardDAO.getTrimesterId(trimesterId));
			studStarStrategies.setCaasppTypes(iolReportCardDAO.getCAASPPTypesByCaaSppTypesId(caasppTypesId));
			studStarStrategies.setGoalStrategies(goalSettingToolDAO.getGoalStrategiesById(strategyId));
			studStarStrategies.setGoalCount(goalCount);
			goalSettingToolDAO.autoSaveStudStarStrategies(studStarStrategies);
			
	}catch(Exception e){
		logger.error("Error in autoSaveStudStrategies() of  GradeAssessmentsServiceImpl"+e);
		
	}
	}
	@Override
	public void autoSaveStudOwnStrategies(Student student,String studOwnStrategyDesc,long goalCount){
		StudentCAASPPOwnStrategies studOwnStregies = new StudentCAASPPOwnStrategies();
		try{
			long check=goalSettingToolDAO.checkExistsStudOwnStrategies(student.getStudentId(),student.getGrade().getGradeId(),goalCount);
			if(check>0){
				studOwnStregies.setStudentOwnStrategId(check);
			}
			studOwnStregies.setStudent(student);
			studOwnStregies.setGrade(student.getGrade());
			studOwnStregies.setGoalCount(goalCount);
			studOwnStregies.setStudentOwnStrategDesc(studOwnStrategyDesc);
			goalSettingToolDAO.autoSaveStudOwnStrategies(studOwnStregies);
		}catch(Exception e){
			logger.error("Error in autoSaveStudStrategies() of  GradeAssessmentsServiceImpl"+e);

		}
	}
	@Override
	public List<StarScores> getStudentStarScores(long goalTypeId,long studentId,long gradeId){
		try{
			return goalSettingToolDAO.getStudentStarScores(goalTypeId,studentId,gradeId);
		}catch(Exception e){
			logger.error("Error in getStudentStarScores() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getStudentStarScores() of GradeAssessmentsServiceImpl", e);
		}
	}
	
	@Override
	public List<StudentCAASPPOwnStrategies> getStudentOwnStrategiesByTypeId(long studentId,long gradeId){
		List<StudentCAASPPOwnStrategies> listStudOwnStrategies=new ArrayList<StudentCAASPPOwnStrategies>();
		try{
			listStudOwnStrategies=goalSettingToolDAO.getStudentOwnStrategiesByTypeId(studentId,gradeId);
		}catch(Exception e){
			logger.error("Error in getStudentStarStrategiesByTypeId() of GoalSettingToolServiceImpl", e);
			e.printStackTrace();
		}
		return listStudOwnStrategies;
	}
	@Override
	public StarScores getStudentStarScoresByTypeId(long goalTypeId,long studentId,long gradeId,long trimesterId){
		try{
			return goalSettingToolDAO.getStudentStarScoresByTypeId(goalTypeId,studentId,gradeId,trimesterId);
		}catch(Exception e){
			logger.error("Error in getStudentStarReadingBoyScore() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getStudentStarReadingBoyScore() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public List<StarScores> getStudentStarTrimesterScores(long goalTypeId,long studentId,long gradeId){
		try{
			return goalSettingToolDAO.getStudentStarTrimesterScores(goalTypeId,studentId,gradeId);
		}catch(Exception e){
			logger.error("Error in getStudentStarScores() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getStudentStarScores() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public List<GoalSampleIdeas> getGoalSampleIdeas(){
		List<GoalSampleIdeas> listGoalSampleIdeas=new ArrayList<GoalSampleIdeas>();
		try{
			listGoalSampleIdeas=goalSettingToolDAO.getGoalSampleIdeas();
		}catch(Exception e){
			logger.error("Error in getGoalSampleIdeas() of GoalSettingToolServiceImpl", e);
			e.printStackTrace();
		}
		return listGoalSampleIdeas;
	}
	@Override
	public List<Trimester> getTrimesterList(){
		List<Trimester> listTrimester=new ArrayList<Trimester>();
		try{
			listTrimester=goalSettingToolDAO.getTrimesterList();
		}catch(Exception e){
			logger.error("Error in getTrimesterList() of GoalSettingToolServiceImpl", e);
			e.printStackTrace();
		}
		return listTrimester;
	}
		
	@Override
	public AcademicYear getCurrentAcademicYr(){
		return goalSettingToolDAO.getCurrentAcademicYr();
	}
	

	@Override
	public List<StudentStarStrategies> getStudentStarStrategies(String caasppTypes){
		return goalSettingToolDAO.getStudentStarStrategies(caasppTypes);
	}
	
	@Override
	public List<StarScores> getAllTeachersFromStarScores(long gradeId){
		return goalSettingToolDAO.getAllTeachersFromStarScores(gradeId);
	}
	
	@Override
	 public List<StarScores> getAllStudentsFromStarScores(long gradeId){
		 return goalSettingToolDAO.getAllStudentsFromStarScores(gradeId);
	 }
	
	@Override
	public List<StarScores> getStudentStarScoresByStudentId(long studentId,long gradeId){
		 return goalSettingToolDAO.getStudentStarScoresByStudentId(studentId, gradeId);
	 }
	
	@Override
	public List<StudentStarStrategies> getStudentStarStrategies(long studentId,long gradeId){
		return goalSettingToolDAO.getStudentStarStrategies(studentId, gradeId);
	}
	
	@Override
	public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId, long gradeId){
		return goalSettingToolDAO.getCAASPPScoresByStudentId(studentId, gradeId);
	 }
	
	@Override
	public List<StarScores> getAllStudentsFromStarScores(long teacherId, long gradeId){
		return goalSettingToolDAO.getAllStudentsFromStarScores(teacherId, gradeId);
	}
	
	@Override
	public long getMAXStarScore(long studentId,long gradeId,long caasppTypesId){
		long starScore=0;
		try{
			starScore=goalSettingToolDAO.getMAXStarScore(studentId,gradeId,caasppTypesId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return starScore;
		
	}
	@Override
	public List<StarScores> getAllStudents(long teacherId, long gradeId){
	List<StarScores> studLst = new ArrayList<StarScores>();
	Map <Long, String> gradeMap =   new LinkedHashMap <Long,String>();
	List<StarScores> starScoresLst=new ArrayList<StarScores>();	
		try {
			starScoresLst=goalSettingToolDAO.getAllStudentsFromStarScores(teacherId, gradeId);
			if (starScoresLst!=null && starScoresLst.size() > 0) {
				for (StarScores s : starScoresLst) {
					if(!gradeMap.containsKey(s.getStudent().getStudentId())){
						gradeMap.put(s.getStudent().getStudentId(),s.getStudent().getUserRegistration().getFirstName());
						studLst.add(s);
					}
				}
			}
	  }catch(Exception e){
		  logger.error("Error inside getAllStudents in GoalSettingToolServiceImpl"+e);
		e.printStackTrace();
	   }
	 return studLst;
	}
	@Override
	public List<Teacher> getAllTeachers(long gradeId){
		List<Teacher> teacherLst = new ArrayList<Teacher>();
		Map <Long, String> gradeMap =   new LinkedHashMap <Long,String>();
		List<StarScores> starScoresLst=new ArrayList<StarScores>();
		
		try {
			starScoresLst=goalSettingToolDAO.getAllTeachersFromStarScores(gradeId);
			if (starScoresLst!=null && starScoresLst.size() > 0) {
				for (StarScores s : starScoresLst) {
					if(s.getTeacher()!=null && !gradeMap.containsKey(s.getTeacher().getTeacherId())){
						gradeMap.put(s.getStudent().getStudentId(),s.getTeacher().getUserRegistration().getFirstName());
						teacherLst.add(s.getTeacher());
					}
				}
			}
	  }catch(Exception e){
		  logger.error("Error inside getAllTeachers in GoalSettingToolServiceImpl"+e);
		e.printStackTrace();
	   }
		  
		return teacherLst;
	}
	@Override
	public List<Long> getStudentsByCsId(long csId){
		List<Long> studentLst = new ArrayList<Long>();
		try{
		studentLst=goalSettingToolDAO.getStudentsBySection(csId);
		}catch(Exception e){
			 logger.error("Error inside getStudentsByCsId in GoalSettingToolServiceImpl"+e);
			e.printStackTrace();
	   }
		return studentLst;		  
	}
	@Override
	public List<Student> getStudentsListByGradeIdandReportId(long gradeId,long trimesterId,List<Long> studentLst){
		List<StudentStarStrategies> listStarStrategies =new ArrayList<StudentStarStrategies>();
		List<StudentCAASPPOwnStrategies> listOwnStrategies=new ArrayList<StudentCAASPPOwnStrategies>();
		List<Student> listStudent = new ArrayList<Student>();
		Map <Long, String> gradeMap =   new LinkedHashMap <Long,String>();
		try {
		if(trimesterId==0){
			listOwnStrategies=goalSettingToolDAO.getStudentCAASPPOwnStrategies(gradeId,trimesterId,studentLst);
			
				if (listOwnStrategies.size() > 0) {
					for (StudentCAASPPOwnStrategies ows : listOwnStrategies) {
						if(!gradeMap.containsKey(ows.getStudent().getStudentId())){
							gradeMap.put(ows.getStudent().getStudentId(), ows.getStudent().getUserRegistration().getFirstName());
							listStudent.add(ows.getStudent());
						}
					}
				} 
			
		}else{
			listStarStrategies=goalSettingToolDAO.getStudentStarStrategiesByTrimesterId(gradeId,trimesterId,studentLst);
			if (listStarStrategies.size() > 0) {
				for (StudentStarStrategies st : listStarStrategies) {
					if(!gradeMap.containsKey(st.getStudent().getStudentId())){
						gradeMap.put(st.getStudent().getStudentId(), st.getStudent().getUserRegistration().getFirstName());
						listStudent.add(st.getStudent());
					}
				}
			 } 
		
		   }
		} catch (Exception e) {
			logger.error("Error in getStudentsListByGradeIdandReportId() of  GoalSettingToolServiceImpl");
			e.printStackTrace();
		}	
    	return listStudent;
			
	}
}