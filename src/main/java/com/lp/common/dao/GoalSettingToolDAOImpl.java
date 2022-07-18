package com.lp.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.CAASPPScores;
import com.lp.model.GoalSampleIdeas;
import com.lp.model.GoalStrategies;
import com.lp.model.RegisterForClass;
import com.lp.model.StarScores;
import com.lp.model.StudentCAASPPOwnStrategies;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Trimester;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("goalSettingTool")
public class GoalSettingToolDAOImpl extends CustomHibernateDaoSupport implements
		GoalSettingToolDAO {

	private JdbcTemplate jdbcTemplate;
	@Autowired
	HttpSession session;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CAASPPScores> getStudentCAASPPScores(long caasppTypesId,long studentId)
			throws DataException {
			
		List<CAASPPScores> studentCAASPPScores = new ArrayList<CAASPPScores>();
		try {
			studentCAASPPScores = (List<CAASPPScores>) getHibernateTemplate()
					.find("from CAASPPScores where caasppType.caasppTypesId="+ caasppTypesId+" and student.studentId="+studentId+" and grade.masterGrades.masterGradesId = student.grade.masterGrades.masterGradesId-1");
		} catch (DataAccessException e) {
			logger.error("Error in getStudentGoalScores() of GoalSettingToolDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return studentCAASPPScores;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<GoalStrategies> getGoalStrategiesByTypeId(long caasppTypesId){
		List<GoalStrategies> listGoalStrategies = new ArrayList<GoalStrategies>();
		try {
			listGoalStrategies = (List<GoalStrategies>) getHibernateTemplate().find("from GoalStrategies where caasppType.caasppTypesId="+ caasppTypesId+"");
		} catch (Exception e) {
			logger.error("Error in getGoalStrategiesByTypeId() of GoalSettingToolDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return listGoalStrategies;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentStarStrategies> getStudentStarStrategiesByTrimesterId(long studentId,long gradeId,long trimesterId,long caasppTypesId){
		List<StudentStarStrategies> listGoalStrategies = new ArrayList<StudentStarStrategies>();
		try {
			if(trimesterId==0){
				long triId=getMAXTrimesterId(studentId,gradeId,caasppTypesId);
				listGoalStrategies = (List<StudentStarStrategies>) getHibernateTemplate()
						.find("from StudentStarStrategies where student.studentId="+studentId+" and grade.gradeId="+gradeId+" and trimester.trimesterId="+triId+" and caasppTypes.caasppTypesId="+caasppTypesId+" order by goalCount");
			}else{
			    listGoalStrategies = (List<StudentStarStrategies>) getHibernateTemplate()
					.find("from StudentStarStrategies where student.studentId="+studentId+" and grade.gradeId="+gradeId+" and trimester.trimesterId="+trimesterId+" and caasppTypes.caasppTypesId="+caasppTypesId+" order by goalCount");
			}
		} catch (Exception e) {
			logger.error("Error in getStudentStarStrategiesByTypeId() of GoalSettingToolDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return listGoalStrategies;
	}
	
	@Override
	public long getMAXTrimesterId(long studentId,long gradeId,long caasppTypesId){
		long trimesterId =0;
		try{
		String query = "select max(trimester_id) from student_star_strategies where student_id="
				+ studentId+" and grade_id="+gradeId+" and caaspp_types_id="+caasppTypesId+" and trimester_id <> 4";
		trimesterId = jdbcTemplate.queryForObject(query, new Object[] {}, Integer.class)!=null?jdbcTemplate.queryForObject(query, new Object[] {}, Integer.class):0; //jdbcTemplate.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
		}
		return trimesterId;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CAASPPScores getCAASPPScoresByCaaSppScoresId(long caasppScoresId){
		List<CAASPPScores> listCAASPPScores = new ArrayList<CAASPPScores>();
		try {
			listCAASPPScores = (List<CAASPPScores>) getHibernateTemplate().find(
					"from CAASPPScores where caaspp_scores_id="+ caasppScoresId + "");
		} catch (Exception e) {
			logger.error("Error in getCAASPPScoresByCaaSppScoresId() of GoalSettingToolDAOImpl"
				+ e);
		}
		if (listCAASPPScores.size() > 0)
			return listCAASPPScores.get(0);
		else
			return new CAASPPScores();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GoalStrategies getGoalStrategiesById(long goalStrategiesId){
		List<GoalStrategies> listGoalStrategies = new ArrayList<GoalStrategies>();
		try {
			listGoalStrategies = (List<GoalStrategies>) getHibernateTemplate().find(
					"from GoalStrategies where goalStrategiesId="+ goalStrategiesId + "");
		} catch (Exception e) {
			logger.error("Error in getGoalStrategiesById() of GoalSettingToolDAOImpl"
					+ e);
		}
		if (listGoalStrategies.size() > 0)
			return listGoalStrategies.get(0);
		else
			return new GoalStrategies();
	}
	@Override
	public boolean autoSaveStudStarStrategies(StudentStarStrategies studStarStrategy){
		try {
			super.saveOrUpdate(studStarStrategy);
			return true;
		} catch (HibernateException e) {
			logger.error("Error in autoSaveStudStarStrategies() of GoalSettingToolDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public long checkExistsGoalStrategies(long studentId,long gradeId,long trimesterId,long goalCount,long caasppTypesId){
		List<StudentStarStrategies> studStarStrategies = new ArrayList<StudentStarStrategies>();
		try {
			studStarStrategies = (List<StudentStarStrategies>) getHibernateTemplate().find("from StudentStarStrategies where student.studentId="+studentId+" and grade.gradeId="+gradeId+" and trimester.trimesterId="+trimesterId+" and goalCount="+goalCount+" and caasppTypes.caasppTypesId="+caasppTypesId+"");
		} catch (Exception e) {
			logger.error("Error in checkExistsGoalStrategies() of GoalSettingToolDAOImpl" + e);
		}
		if(studStarStrategies.size()>0)
			return studStarStrategies.get(0).getStudStarStrategId();
		else
			return 0;
	}
	@Override
	public boolean autoSaveStudOwnStrategies(StudentCAASPPOwnStrategies studOwnStrategy){
		try {
			super.saveOrUpdate(studOwnStrategy);
			return true;
		} catch (HibernateException e) {
			logger.error("Error in autoSaveStudOwnStrategies() of GoalSettingToolDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<StarScores> getStudentStarScores(long goalTypeId,long studentId,long gradeId){
		 List<StarScores> studentStarScores = new ArrayList<StarScores>();
		 try {
			 studentStarScores = (List<StarScores>) getHibernateTemplate()
					 .find("from StarScores where caasppType.caasppTypesId="+ goalTypeId+" and student.studentId="+studentId+" and grade.gradeId="+gradeId+" order by trimester.orderId asc");
		 } catch (DataAccessException e) {
			 logger.error("Error in getStudentStarScores() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return studentStarScores;
	 }
	
	 @SuppressWarnings("unchecked")
	 @Override
	 public StarScores getStarScoresByStarScoresId(long starScoresId){
		 List<StarScores> listStarScores = new ArrayList<StarScores>();
		 try {
			 listStarScores = (List<StarScores>) getHibernateTemplate().find(
					 "from StarScores where starScoresId="+ starScoresId + "");
		 } catch (Exception e) {
			 logger.error("Error in getStarScoresByStarScoresId() of GoalSettingToolDAOImpl"
					 + e);
		 }
		 if (listStarScores.size() > 0)
			 return listStarScores.get(0);
		 else
			 return new StarScores();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<StudentCAASPPOwnStrategies> getStudentOwnStrategiesByTypeId(long studentId,long gradeId){
		 List<StudentCAASPPOwnStrategies> listStudOwnStrategies = new ArrayList<StudentCAASPPOwnStrategies>();
		 try {
			 listStudOwnStrategies = (List<StudentCAASPPOwnStrategies>) getHibernateTemplate().find("from StudentCAASPPOwnStrategies where student.studentId="+ studentId+" and grade.gradeId="+gradeId+"");
		 } catch (Exception e) {
			 logger.error("Error in getStudentOwnStrategiesByTypeId() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return listStudOwnStrategies;
	
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public long checkExistsStudOwnStrategies(long studentId,long gradeId,long goalCount){
		 List<StudentCAASPPOwnStrategies> studOwnStrategies = new ArrayList<StudentCAASPPOwnStrategies>();
		 try {
			 studOwnStrategies = (List<StudentCAASPPOwnStrategies>) getHibernateTemplate().find("from StudentCAASPPOwnStrategies where student.studentId="+studentId+" and grade.gradeId="+gradeId+" and goalCount="+goalCount);
		 } catch (Exception e) {
			 logger.error("Error in checkExistsStudOwnStrategies() of GoalSettingToolDAOImpl" + e);
		 }
		 if(studOwnStrategies.size()>0)
			 return studOwnStrategies.get(0).getStudentOwnStrategId();
		 else
			 return 0;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public StarScores getStudentStarScoresByTypeId(long goalTypeId,long studentId,long gradeId,long trimesterId){
		 List<StarScores> studentStarScores = new ArrayList<StarScores>();
		 try {
			 studentStarScores = (List<StarScores>) getHibernateTemplate()
					 .find("from StarScores where caasppType.caasppTypesId="+ goalTypeId+" and student.studentId="+studentId+" and grade.gradeId="+gradeId+" and trimester.trimesterId="+trimesterId+"");
		 } catch (DataAccessException e) {
			 logger.error("Error in getStudentStarReadingBoyScore() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 if (studentStarScores.size() > 0)
			 return studentStarScores.get(0);
		 else
			 return new StarScores();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<StarScores> getStudentStarTrimesterScores(long caasppTypesId,long studentId,long gradeId){
		 List<StarScores> studentStarScores = new ArrayList<StarScores>();
		 try {
			 studentStarScores = (List<StarScores>) getHibernateTemplate()
					 .find("from StarScores where caasppType.caasppTypesId="+ caasppTypesId+" and student.studentId="+studentId+" and grade.gradeId="+gradeId+" and trimester.trimesterId !=4  order by trimester.orderId asc");
		 } catch (DataAccessException e) {
			 logger.error("Error in getStudentStarScores() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return studentStarScores;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<GoalSampleIdeas> getGoalSampleIdeas(){
		 List<GoalSampleIdeas> listGoalSampleIdeas = new ArrayList<GoalSampleIdeas>();
		 try {
			 listGoalSampleIdeas = (List<GoalSampleIdeas>) getHibernateTemplate()
					 .find("from GoalSampleIdeas");
		 } catch (Exception e) {
			 logger.error("Error in getGoalSampleIdeas() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return listGoalSampleIdeas;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Trimester> getTrimesterList(){
		 List<Trimester> listTrimester = new ArrayList<Trimester>();
		 try {
			 listTrimester = (List<Trimester>) getHibernateTemplate().find("from Trimester order by orderId");
		 } catch (Exception e) {
			 logger.error("Error in getTrimesterList() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return listTrimester;
	 }
		
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<StudentStarStrategies> getStudentStarStrategiesByTrimesterId(long gradeId,long trimesterId,List<Long> studentLst){
		 List<StudentStarStrategies> listGoalStrategies = new ArrayList<StudentStarStrategies>();
		 try {				
			 listGoalStrategies = (List<StudentStarStrategies>) getHibernateTemplate()
					 .findByNamedParam("from StudentStarStrategies where grade.gradeId="+gradeId+" and trimester.trimesterId="+trimesterId+" and student.studentId in (:studentIds) group by student.studentId having count(*) = 6","studentIds", studentLst);
		 } catch (Exception e) {
			 logger.error("Error in getStudentStarStrategiesByTrimesterId() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return listGoalStrategies;
	 }
	
	 @SuppressWarnings("unchecked")
	 @Override
	 public AcademicYear getCurrentAcademicYr(){
		 AcademicYear currentYr = null;
		 List<AcademicYear> academicYears = null;
		 try {
			 academicYears = (List<AcademicYear>) getHibernateTemplate()
					 .find("from AcademicYear where isCurrentYear='"+WebKeys.STEM_YES.toUpperCase()+"'");	
		 } catch (Exception e) {
			 logger.error("Error in getStudentStarStrategiesByTrimesterId() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 if(!academicYears.isEmpty())
			 currentYr = academicYears.get(0);
		 return currentYr;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<StudentCAASPPOwnStrategies> getStudentCAASPPOwnStrategies(long gradeId,long trimesterId,List<Long> studentLst){
		 List<StudentCAASPPOwnStrategies> listGoalStrategies = new ArrayList<StudentCAASPPOwnStrategies>();
		 try {
			 listGoalStrategies = (List<StudentCAASPPOwnStrategies>) getHibernateTemplate()
					 .findByNamedParam("from StudentCAASPPOwnStrategies where grade.gradeId="+gradeId+" and student.studentId in (:studentIds) group by student.studentId having count(*) = 3","studentIds", studentLst);
			
		 } catch (Exception e) {
			 logger.error("Error in getStudentCAASPPOwnStrategies() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return listGoalStrategies;
	 }	
	 
	 @SuppressWarnings("unchecked")
	@Override
	 public List<StudentStarStrategies> getStudentStarStrategies(String caasppTypes){
		 List<StudentStarStrategies> sssLt = new ArrayList<StudentStarStrategies>();
		 try {
			 sssLt = (List<StudentStarStrategies>) getHibernateTemplate().find("from StudentStarStrategies where caasppTypes.caasppType='"+caasppTypes+"' order by goalStrategies.goalStrategiesId");
		 } catch (Exception e) {
			 logger.error("Error in getStudentStarStrategies() of GoalSettingToolDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return sssLt;
	 }
	    
    @SuppressWarnings("unchecked")
	@Override
	public List<StarScores> getAllTeachersFromStarScores(long gradeId){
    	List<StarScores> studentStarScores = new ArrayList<StarScores>();
    	try {
    		studentStarScores = (List<StarScores>) getHibernateTemplate()
    				.find("from StarScores where grade.gradeId="+gradeId+" group by teacher.teacherId");
    	} catch (DataAccessException e) {
    		logger.error("Error in getAllTeachersFromStarScores() of GoalSettingToolDAOImpl"
    				+ e);
    		e.printStackTrace();
    	}
    	return studentStarScores;
	 }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<StarScores> getAllStudentsFromStarScores(long gradeId){
    	List<StarScores> studentStarScores = new ArrayList<StarScores>();
    	try {
    		studentStarScores = (List<StarScores>) getHibernateTemplate()
    				.find("from StarScores where grade.gradeId="+gradeId+" order by teacher.teacherId, caasppType.caasppTypesId, trimester.orderId asc");
    	} catch (DataAccessException e) {
    		logger.error("Error in getAllStudentsFromStarScores() of GoalSettingToolDAOImpl"+ e);
    		e.printStackTrace();
    	}
    	return studentStarScores;
    }
	 
    @SuppressWarnings("unchecked")
	@Override
    public List<StarScores> getAllStudentsFromStarScores(long teacherId, long gradeId){
    	List<StarScores> studentStarScores = new ArrayList<StarScores>();
    	try {
    		studentStarScores = (List<StarScores>) getHibernateTemplate().find("from StarScores where teacher.teacherId="+teacherId+" and grade.gradeId="+gradeId+" order by trimester.orderId asc");
		} catch (DataAccessException e) {
			logger.error("Error in getAllStudentsFromStarScores() of GoalSettingToolDAOImpl"+ e);
			e.printStackTrace();
		}
		return studentStarScores;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<StarScores> getStudentStarScoresByStudentId(long studentId,long gradeId){
    	List<StarScores> studentStarScores = new ArrayList<StarScores>();
    	try {
			studentStarScores = (List<StarScores>) getHibernateTemplate().find("from StarScores where student.studentId="+studentId+" and grade.gradeId="+gradeId+" order by caasppType.caasppTypesId, trimester.orderId asc");
		} catch (DataAccessException e) {
			logger.error("Error in getStudentStarScoresByStudentId() of GoalSettingToolDAOImpl"+ e);
			e.printStackTrace();
		}
		return studentStarScores;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<StudentStarStrategies> getStudentStarStrategies(long studentId,long gradeId){
    	List<StudentStarStrategies> listGoalStrategies = new ArrayList<StudentStarStrategies>();
    	try {
    		listGoalStrategies = (List<StudentStarStrategies>) getHibernateTemplate().find("from StudentStarStrategies where student.studentId="+studentId+" and grade.gradeId="+gradeId+" order by trimester.orderId, caasppTypes.caasppTypesId asc");
    	} catch (Exception e) {
    		logger.error("Error in getStudentStarStrategies() of GoalSettingToolDAOImpl"
    				+ e);
    		e.printStackTrace();
		}
		return listGoalStrategies;
	}
    
    @SuppressWarnings("unchecked")
	@Override
    public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId, long gradeId){
    	List<CAASPPScores> listCAASPPScores = new ArrayList<CAASPPScores>();
    	try {
    		gradeId = gradeId - 1; // get the previous master Grade
			listCAASPPScores = (List<CAASPPScores>) getHibernateTemplate().find(
					"from CAASPPScores where student.studentId="+studentId+ " and grade.masterGrades.masterGradesId="+gradeId+" order by caasppType.caasppTypesId, teacher.teacherId asc");
    	} catch (Exception e) {
    		logger.error("Error in getCAASPPScoresByStudentId() of GoalSettingToolDAOImpl"
    				+ e);
    	}
    	return listCAASPPScores;
    }
	@Override
	public long getMAXStarScore(long studentId,long gradeId,long caasppTypesId){
    	long starScore=0;
    	try{
    		String query = "select max(score) from star_scores where student_id="
				+ studentId+" and grade_id="+gradeId+" and caaspp_types_id="+caasppTypesId+"";
    		starScore =  jdbcTemplate.queryForObject(query, new Object[] {}, Long.class)!=null?jdbcTemplate.queryForObject(query, new Object[] {}, Long.class):0;  //jdbcTemplate.queryForLong(query);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return starScore;
		
	}
    @SuppressWarnings("unchecked")
	@Override
	public long getMaxOrderIdByGradeId(long gradeId,long caasppTypesId){
    	List<StarScores> studentStarScores = new ArrayList<StarScores>();
    	try {
    		studentStarScores = (List<StarScores>) getHibernateTemplate()
    				.find("from StarScores where caasppType.caasppTypesId="+ caasppTypesId+" and grade.gradeId="+gradeId+" order by trimester.orderId desc");
    	} catch (Exception e) {
    		logger.error("Error in getMaxOrderIdByGradeId() of GoalSettingToolDAOImpl"
    				+ e);
    		e.printStackTrace();
    	}
    	if (studentStarScores.size() > 0)
    		return studentStarScores.get(0).getTrimester().getOrderId();
    	else
    		return 0;
    }
    @SuppressWarnings("unchecked")
	@Override
	public List<Long> getStudentsBySection(long csId) {
    	List<Long> studentLt = new ArrayList<Long>();
		try{
			List<RegisterForClass> registerForClassLt = (List<RegisterForClass>) getHibernateTemplate().find(
					"from RegisterForClass where classStatus.csId="+csId+"and classStatus.availStatus='"+WebKeys.AVAILABLE+"' and status='"+WebKeys.ACCEPTED+"' and classStatus_1='"+WebKeys.ALIVE+"'"
							+ " order by student.userRegistration.lastName");
			for(RegisterForClass rfc: registerForClassLt){
				studentLt.add(rfc.getStudent().getStudentId());
			}
		}catch(Exception e){
			logger.error("Error in getStudentsBySection() of TeacherDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return studentLt;
    }
}
