package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.dao.GradesDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignLegendRubrics;
import com.lp.model.CAASPPScores;
import com.lp.model.ClassStatus;
import com.lp.model.FilesLP;
import com.lp.model.Grade;
import com.lp.model.IOLReport;
import com.lp.model.LERubrics;
import com.lp.model.LearningIndicator;
import com.lp.model.LearningIndicatorValues;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.LegendSubCriteria;
import com.lp.model.MulYrLegend;
import com.lp.model.Student;
import com.lp.model.Trimester;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.AssignAssessmentsDAO;
import com.lp.teacher.dao.IOLReportCardDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "iolReportCardService")
public class IOLReportCardServiceImpl implements IOLReportCardService {
	@Autowired
	private IOLReportCardDAO iolReportCardDAO;
	@Autowired
	private AssignAssessmentsDAO assignAssessmentsDAO;
	@Autowired
	private GradesDAO gradesDAO;
	@Autowired
	private AdminSchedulerDAO adminSchedulerDAO;
	@Autowired
	private UserRegistrationDAO userRegisDao;
	
	@Autowired
	private GradesDAO gDao;
	
	static final Logger logger = Logger.getLogger(IOLReportCardServiceImpl.class);
	@Override
	public List<LegendCriteria> getLegendCriteria(){
		
		List<LegendCriteria> legendCriterias= iolReportCardDAO.getLegendCriteria();
		List<LegendSubCriteria> legendSubCriterias = new ArrayList<LegendSubCriteria>() ; 
		try{
		for(LegendCriteria legcr : legendCriterias ){
			    legendSubCriterias=iolReportCardDAO.getLegendSubCriteriasByCriteriaId(legcr.getLegendCriteriaId(),6);
			    legcr.setLegendSubCriteria(legendSubCriterias);
			   
			}
		}catch(Exception e){
			logger.error("Error in getLegendCriteria() of IOLReportCardServiceImpl", e);
		}
		return legendCriterias;
	}
	@Override
	public Student getStudent(long studentId){
		Student stud=new Student();
		try{
			stud= assignAssessmentsDAO.getStudent(studentId);
		}catch(Exception e)
		{
			logger.error("Error in getStudent() of IOLReportCardServiceImpl", e);
			
		}
		return stud;
	}
	@Override
	public Grade getGrade(long gradeId){
		Grade grade=new Grade();
		try{
			grade= gradesDAO.getGrade(gradeId);
		}catch(Exception e)
		{
			logger.error("Error in getGrade() of IOLReportCardServiceImpl", e);
			
		}
		return grade;
	}
	@Override
	public List<Legend> getLegends(){
		List<Legend> legends=new ArrayList<Legend>();
		try{
			legends= iolReportCardDAO.getLegends();
		}catch(Exception e)
		{
			logger.error("Error in CreateIOLReportCard() of IOLReportCardServiceImpl", e);
			
		}
		return legends;
	}
	@Override
	public boolean TeacherCreateIOLReportCard(long iolReportId){
		try{
			iolReportCardDAO.submitIOLCardtoStudent(iolReportId);
		}catch(Exception e)
		{
			logger.error("Error in TeacherCreateIOLReportCard() of IOLReportCardServiceImpl", e);
			e.printStackTrace();
			return false;
		}
		 return true;
	 
	}
	
	public List<IOLReport> getStudentIOLReportDates(long csId, long studentId){
		 List<IOLReport> iolReports=new ArrayList<IOLReport>();
		 try{
			 iolReports= iolReportCardDAO.getStudentIOLReportDates(csId, studentId);
		 }catch(DataException e){
			  logger.error("Error in getStudentIOLReportDates() of IOLReportCardServiceImpl", e);
			}
		 return iolReports;
	}
	public List<IOLReport> getStudentCompletedIOLReportDates(long csId, long studentId){
		 List<IOLReport> iolReports=new ArrayList<IOLReport>();
		 try{
			 iolReports= iolReportCardDAO.getStudentCompletedIOLReportDates(csId, studentId);
		 }catch(DataException e){
			  logger.error("Error in getStudentIOLReportDates() of IOLReportCardServiceImpl", e);
			}
		 return iolReports;
	}
	 public List<LearningIndicatorValues> getStudentIOLReportCard(long learnIndicatorId){
		 List<LearningIndicatorValues> reportCards=new ArrayList<LearningIndicatorValues>();
		 try{
			 reportCards= iolReportCardDAO.getStudentIOLReportCard(learnIndicatorId);
		  }catch(DataException e){
			  logger.error("Error in getStudentIOLReportCard() of IOLReportCardServiceImpl", e);
			}
		 return reportCards;
	 }
	 // used by student show report
	  public List<IOLReport> getStudentAllIOLReportDates(long studentId){
		  List<IOLReport> learnInd=new ArrayList<IOLReport>();
		  try{
		   learnInd= iolReportCardDAO.getStudentAllIOLReportDates(studentId);
		  }catch(Exception e){
			  logger.error("Error in getStudentAllIOLReportDates() of IOLReportCardServiceImpl", e); 
		  }
		  return learnInd;
	  }
	  public List<LearningIndicator> getStudentAllCompletedIOLReportDates(long studentId){
		  List<LearningIndicator> learnInd=new ArrayList<LearningIndicator>();
		  try{
		   learnInd= iolReportCardDAO.getStudentAllCompletedIOLReportDates(studentId);
		  }catch(Exception e){
			  logger.error("Error in getStudentAllIOLReportDates() of IOLReportCardServiceImpl", e); 
		  }
		  return learnInd;
	  }

	  public LearningIndicator getLearningIndicator(long learningIndicatorId){
		  LearningIndicator learnInd=new LearningIndicator();
		  try{
		  learnInd=iolReportCardDAO.getLearningIndicator(learningIndicatorId);
		  }catch(DataException e){
				logger.error("Error in getLearningIndicator() of IOLReportCardServiceImpl", e);
			}
		  return learnInd;
	  }
		@Override
		public ClassStatus getclassStatus(long csId) {
			ClassStatus scsche = new ClassStatus();
			try {
				scsche=adminSchedulerDAO.getclassStatus(csId);
			} catch (Exception e) {
				logger.error("Error in getclassStatus() of AdminSchedulerDAOImpl"
						+ e);
			}
			return scsche;
		}
	  
	  
	  @Override
		public boolean saveTeacherScore(long learnValId,long legend){
			
			try{
				iolReportCardDAO.saveTeacherScore(learnValId, legend);
			}catch(Exception e)
			{
				logger.error("Error in saveTeacherScore() of IOLReportCardServiceImpl", e);
				return false;
			}
			 return true;		 
		}  
	  
	  @Override
		public boolean saveTeacherComment(long learnValId,String teacherComment){				
			try{
				iolReportCardDAO.saveTeacherComment(learnValId, teacherComment);
			}catch(Exception e)
			{
				logger.error("Error in saveTeacherComment() of IOLReportCardServiceImpl", e);
				return false;
			}
			 return true;			 
		}  
	  
	  @Override
	  public List<LearningIndicator> saveStudentIOLReportCard(long csId, long studentId,long trimesterId, Grade grade){
		  trimesterId=trimesterId+1;
		  String isMulYrReport="no";
		  IOLReport iolReport=new IOLReport();
		  @SuppressWarnings("unused")
		  List<LearningIndicatorValues> learningIndicatorValues = new ArrayList<LearningIndicatorValues>();
		  List<LearningIndicator> learningIndicators=new ArrayList<LearningIndicator>();
		  try{		
			  List<LearningIndicatorValues> indicatorValues = new ArrayList<LearningIndicatorValues>(); 
			  List<LegendCriteria> legendCriterias = iolReportCardDAO.getLegendCriteria();
			  ClassStatus classStatus = new ClassStatus();
			  Student student = new Student();
			  classStatus.setCsId(csId);
			  student.setStudentId(studentId);
			  iolReport.setClassStatus(classStatus);
			  iolReport.setStudent(student);
			  iolReport.setReportDate(new java.sql.Date(new java.util.Date().getTime()));
			  iolReport.setTrimester(iolReportCardDAO.getTrimesterId(trimesterId));
			  iolReport.setIsMulYearReport(isMulYrReport);
			  for(LegendCriteria legendCriteria : legendCriterias){
				  LearningIndicator learningIndicator = new LearningIndicator();	
				  learningIndicator.setReportDate(new java.sql.Date(new java.util.Date().getTime()));
				  learningIndicator.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
				  learningIndicator.setLegendCriteria(legendCriteria);
				  learningIndicator.setIolReport(iolReport);
				  learningIndicators.add(learningIndicator);
			  
			 /* List<LegendSubCriteria> legendSubCriterias = iolReportCardDAO.getLegendSubCriteriasByCriteriaId(legendCriteria.getLegendCriteriaId(),grade.getMasterGrades().getMasterGradesId());
			  List<LegendSubCriteria> literacies =  iolReportCardDAO.getLiteracyLegendSubCriterias();
			   for(LegendSubCriteria legendSubCriteria : legendSubCriterias){
				  LearningIndicatorValues indicatorValues2 = new LearningIndicatorValues();
				  indicatorValues2.setLearningIndicator(learningIndicator);
				  indicatorValues2.setLegendSubCriteria(legendSubCriteria);
				  indicatorValues.add(indicatorValues2);
			  }*/
				  List<LegendSubCriteria> legendSubCriterias = iolReportCardDAO.getLegendSubCriteriasByCriteriaId(legendCriteria.getLegendCriteriaId(),grade.getMasterGrades().getMasterGradesId());
				  for(LegendSubCriteria legendSubCriteria : legendSubCriterias){
					  LearningIndicatorValues indicatorValues2 = new LearningIndicatorValues();
					  indicatorValues2.setLearningIndicator(learningIndicator);
					  indicatorValues2.setLegendSubCriteria(legendSubCriteria);
					  indicatorValues.add(indicatorValues2);
				  }
			  }
			 
			  learningIndicatorValues = iolReportCardDAO.CreateIOLReportCard(iolReport,learningIndicators, indicatorValues);
			}catch(Exception e)
			{
				logger.error("Error in saveStudentIOLReportCard() of IOLReportCardServiceImpl", e);
				return learningIndicators;
			}
			 return learningIndicators;		
	  }
	  @Override
	  public boolean submitStudentIOLReportCardToTeacher(long iolReportId,long studentId,long csId,long trimesterId){
		    try{
		    if(trimesterId>0){
		    	for(long i=1;i<trimesterId;i++)
		    		iolReportCardDAO.setTrimesterIdForStudent(studentId, csId, i);
		    }
			   iolReportCardDAO.submitStudentIOLReportCardToTeacher(iolReportId);
			}catch(Exception e)
			{
				logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardServiceImpl", e);
				return false;
			}
			 return true;
	  }
	@Override
	public boolean saveStudentSelfScore(long learnIndiValueId,long legendId) {
		try{
			iolReportCardDAO.saveStudentSelfScore(learnIndiValueId, legendId);
		}catch(Exception e)
		{
			logger.error("Error in saveStudentSelfScore() of IOLReportCardServiceImpl", e);
			return false;
		}
		 return true;		
	}
	@Override
	public boolean saveStudentNotes(long learnIndiValueId, String studentNotes) {
		try{
			iolReportCardDAO.saveStudentNotes(learnIndiValueId, studentNotes);
		}catch(Exception e)
		{
			logger.error("Error in saveStudentSelfScore() of IOLReportCardServiceImpl", e);
			return false;
		}
		 return true;		
	}
	@Override
	public List<IOLReport> getStudentInCompletedIOLReport(long csId, long studentId){
		 List<IOLReport> iolReports=new ArrayList<IOLReport>();
		 try{
			 iolReports= iolReportCardDAO.getStudentInCompletedIOLReport(csId, studentId);
		 }catch(DataException e){
			  logger.error("Error in getStudentInCompletedIOLReport() of IOLReportCardServiceImpl", e);
			}
		 return iolReports;
	}
	
	 @Override
		public long assignLIRubricToGrade(long subCriteriaId,List<Long> legendIds,long gradeId){
			long stat=0;
			List<LERubrics> leRubrics =new ArrayList<LERubrics>();			
			LegendSubCriteria legendSubCriteria = new LegendSubCriteria();
			for(Long legendId :legendIds){
				Legend legend = new Legend();
				legend.setLegendId(legendId);
				legendSubCriteria.setLegendSubCriteriaId(subCriteriaId);
				LERubrics leRubric=new LERubrics();
				leRubric.setGrade(getGrade(gradeId));	
				leRubric.setLegendSubCriteria(legendSubCriteria);
				leRubric.setLegend(legend);
				leRubric.setStatus(WebKeys.ACTIVE);			
				leRubrics.add(leRubric);
			}
			stat=iolReportCardDAO.assignLIRubricToGrade(leRubrics);
			return stat;
		}
	@Override
	public List<LERubrics> getRubricValuesByGradeId(long gradeId,long subCriteriaId){
		List<LERubrics> leRubrics=new ArrayList<LERubrics>();
		 try{
			 leRubrics= iolReportCardDAO.getRubricValuesByGradeId(gradeId, subCriteriaId);
		 }catch(DataException e){
			  logger.error("Error in getRubricValuesByGradeId() of IOLReportCardServiceImpl", e);
			}
		 return leRubrics;
		
	}
	@Override
	public LegendSubCriteria getLegendSubCriteria(long legendSubCriteriaId){
		LegendSubCriteria legendSubCriteria=new LegendSubCriteria();
		try{
			legendSubCriteria= iolReportCardDAO.getLegendSubCriteria(legendSubCriteriaId);
		 }catch(DataException e){
			  logger.error("Error in getLegendSubCriteria() of IOLReportCardServiceImpl", e);
			}
		 return legendSubCriteria;
	}
	@Override
	public String leFileAutoSave(MultipartFile file,long createdBy,long learningIndicatorId,long subCriteriaId,long learnIndValuesId){
		String path="";
		Student stud=assignAssessmentsDAO.getStudent(createdBy);		
		path=iolReportCardDAO.leFileAutoSave(file, stud,learningIndicatorId,subCriteriaId,learnIndValuesId);
		return path;
	}
	@Override
	public LearningIndicatorValues getLearningIndicatorValues(long learnIndiValueId){
		LearningIndicatorValues learnIndValues=new LearningIndicatorValues();
		try{
			learnIndValues= iolReportCardDAO.getLearningIndicatorValues(learnIndiValueId);
		 }catch(DataException e){
			  logger.error("Error in getLearningIndicatorValues() of IOLReportCardServiceImpl", e);
			}
		 return learnIndValues;
	}
	@Override
	public List<FilesLP> getLEFileList(long studentId,long learningIndicatorId,long learnIndValuesId){
		
		List<FilesLP> fileList=new ArrayList<FilesLP>();
		try{
			Student stud=assignAssessmentsDAO.getStudent(studentId);
			fileList= iolReportCardDAO.getLEFileList(stud,learningIndicatorId,learnIndValuesId);
		 }catch(DataException e){
			  logger.error("Error in getLEFileList() of IOLReportCardServiceImpl", e);
			}
		 return fileList;
	}
	@Override
	public String getRubricDesc(long criteriaId,long subCriteriaId,long rubricScore, long createdBy){
		String rubricDesc="";
		try{
			rubricDesc= iolReportCardDAO.getRubricDesc(criteriaId,subCriteriaId,rubricScore, createdBy);
		 }catch(DataException e){
			  logger.error("Error in getRubricDesc() of IOLReportCardServiceImpl", e);
			}
		 return rubricDesc;
	}
	@Override
	public boolean checkLIRubricExists(long subCriteriaId,long rubricScore, long regId){
		boolean stat=false;
		try{
			stat= iolReportCardDAO.checkLIRubricExists(subCriteriaId,rubricScore, regId);
		 }catch(DataException e){
			  logger.error("Error in checkLIRubricExists() of IOLReportCardServiceImpl", e);
			}
		 return stat;
	}
	@Override
	 public List<IOLReport> getChildAllIOLReportDates(long studentId){
		  List<IOLReport> learnInd=new ArrayList<IOLReport>();
		  try{
		   learnInd= iolReportCardDAO.getChildAllIOLReportDates(studentId);
		  }catch(Exception e){
			  logger.error("Error in getChildAllIOLReportDates() of IOLReportCardServiceImpl", e); 
		  }
		  return learnInd;
	  }
	@Override
	public List<LegendSubCriteria> getLegendSubCriteriasByCriteriaId(long legendCriteriaId, long masterGradeId){
		List<LegendSubCriteria> subCriteriaList=new ArrayList<LegendSubCriteria>();
		 try{
			 subCriteriaList= iolReportCardDAO.getLegendSubCriteriasByCriteriaId(legendCriteriaId, masterGradeId);
			  }catch(Exception e){
				  logger.error("Error in getLegendSubCriteriasByCriteriaId() of IOLReportCardServiceImpl", e); 
			  }
			  return subCriteriaList;
		
	}
	@Override
	public List<LearningIndicatorValues> getStudentInCompletedIOLSection(long learningIndicatorId){
		 List<LearningIndicatorValues> learnInds=new ArrayList<LearningIndicatorValues>();
		 try{
			 learnInds= iolReportCardDAO.getStudentInCompletedIOLSection(learningIndicatorId);
		 }catch(DataException e){
			  logger.error("Error in getStudentInCompletedIOLSection() of IOLReportCardServiceImpl", e);
			}
		 return learnInds;
	}
	@Override
	public LegendCriteria getLegendCriteria(long legendCriteriaId){
		LegendCriteria legendCriteria=new LegendCriteria();
		try{
			legendCriteria= iolReportCardDAO.getLegendCriteria(legendCriteriaId);
		 }catch(DataException e){
			  logger.error("Error in getLegendCriteria() of IOLReportCardServiceImpl", e);
			}
		 return legendCriteria;
	}
	  @Override
	  public boolean submitStudentIOLSectionToTeacher(long learIndicatorId,String leScore){
		  try{
				iolReportCardDAO.submitStudentIOLSectionToTeacher(learIndicatorId,leScore);
			}catch(Exception e)
			{
				logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardServiceImpl", e);
				return false;
			}
			 return true;
	  }
	  @Override
	  public long getSumOfLegends(long learningIndicatorId){
		  long sumOfLegends=0;
		  try{
			  sumOfLegends=iolReportCardDAO.getSumOfLegends(learningIndicatorId);
			}catch(Exception e)
			{
				logger.error("Error in getSumOfLegends() of IOLReportCardServiceImpl", e);
				return 0;
			}
			 return sumOfLegends; 
	  }
	  @Override
	  public long getNoOfLegends(long learningIndicatorId){
		  long noOfLegends=0;
		  try{
			  noOfLegends=iolReportCardDAO.getNoOfLegends(learningIndicatorId);
			}catch(Exception e)
			{
				logger.error("Error in getNoOfLegends() of IOLReportCardServiceImpl", e);
				return 0;
			}
			 return noOfLegends;
	  }
	  @Override
	  public LearningIndicator getIOLSectionStatus(long csId,long studentId,long legendCriteriaId,String createDate){
		  LearningIndicator learnIndicator=new LearningIndicator();
		  try{
			  learnIndicator=iolReportCardDAO.getIOLSectionStatus(csId,studentId,legendCriteriaId,createDate);
			}catch(Exception e)
			{
				e.printStackTrace();
				logger.error("Error in getIOLSectionStatus() of IOLReportCardServiceImpl", e);
				
			}
			 return learnIndicator;
	  }
	  public List<LearningIndicatorValues> getStudentCompletedIOLSection(long learningIndicatorId){
		  List<LearningIndicatorValues> learnInds=new ArrayList<LearningIndicatorValues>();
			 try{
				 learnInds= iolReportCardDAO.getStudentCompletedIOLSection(learningIndicatorId);
			 }catch(DataException e){
				  logger.error("Error in getStudentInCompletedIOLSection() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
	  }
	  @Override
	  public List<LearningIndicator> getStudentCurrentIOLReport(long csId, long studentId,long iolReportId){
			 List<LearningIndicator> learnInds=new ArrayList<LearningIndicator>();
			 try{
				 learnInds= iolReportCardDAO.getStudentCurrentIOLReport(csId, studentId,iolReportId);
			 }catch(DataException e){
				  logger.error("Error in getStudentInCompletedIOLReport() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
		}
	  
	  @Override
	  public List<LearningIndicatorValues> getLitracyLearnIndValues(long iolReportId,long legendCriteriaId){
		  List<LearningIndicatorValues> learnInds=new ArrayList<LearningIndicatorValues>();
			 try{
				 
				 learnInds= iolReportCardDAO.getIncompleteLitracyLearnIndValues(iolReportId,legendCriteriaId);
				 			 
			 }catch(DataException e){
				  logger.error("Error in getStudentInCompletedIOLSection() of IOLReportCardServiceImpl", e);
				  e.printStackTrace();
				}
			 return learnInds;
	  }
	  @Override
	  public List<LearningIndicatorValues> getNewLitracyLearnIndValues(long csId,long studentId,long legendCriteriaId){
		  List<LearningIndicatorValues> learnInds=new ArrayList<LearningIndicatorValues>();
			 try{
				learnInds=iolReportCardDAO.getNewLitracyLearnIndValues(csId,studentId,legendCriteriaId);
			 }catch(DataException e){
				  logger.error("Error in getStudentInCompletedIOLSection() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
	  }
	  
	  @Override
		public LearningIndicatorValues getStudentInCompletedSubCriteria(long learningValuesId){
			LearningIndicatorValues learnInds=new LearningIndicatorValues();
			 try{
				 learnInds= iolReportCardDAO.getStudentInCompletedSubCriteria(learningValuesId);
			 }catch(DataException e){
				  logger.error("Error in getStudentInCompletedSubCriteria() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
		}
	  @Override
	  public LearningIndicatorValues getStudentCompletedSubCriteria(long learningvaluesId) {
			LearningIndicatorValues learnInds=new LearningIndicatorValues();
			 try{
				 learnInds= iolReportCardDAO.getStudentCompletedSubCriteria(learningvaluesId);
			 }catch(DataException e){
				  logger.error("Error in getStudentInCompletedSubCriteria() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
	  }
	 
	  @Override
	  public void submitStudentIOLSubCriteriaToTeacher(long learIndicatorId,long learningValuesId){
		  try{
				iolReportCardDAO.submitStudentIOLSubCriteriaToTeacher(learIndicatorId,learningValuesId);
			}catch(Exception e)
			{
				logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardServiceImpl", e);
				
			}
			 
	  }
	  @Override
	  public IOLReport getIOLReport(long iolReportId){
		  IOLReport iolReport=new IOLReport();
		  try{
			  iolReport=iolReportCardDAO.getIOLReport(iolReportId);
		  }catch(DataException e){
				logger.error("Error in getIOLReport() of IOLReportCardServiceImpl", e);
			}
		  return iolReport;
	  }
	  @Override
	  public long getSumOfTeacherLegends(long learningIndicatorId){
		  long sumOfTeacherLegends=0;
		  try{
			  sumOfTeacherLegends=iolReportCardDAO.getSumOfTeacherLegends(learningIndicatorId);
			}catch(Exception e)
			{
				logger.error("Error in getSumOfTeacherLegends() of IOLReportCardServiceImpl", e);
				return 0;
			}
			 return sumOfTeacherLegends; 
	  }
	  @Override
	  public long getNoOfTeacherLegends(long learningIndicatorId){
		  long noOfTeacherLegends=0;
		  try{
			  noOfTeacherLegends=iolReportCardDAO.getNoOfTeacherLegends(learningIndicatorId);
			}catch(Exception e)
			{
				logger.error("Error in getNoOfTeacherLegends() of IOLReportCardServiceImpl", e);
				return 0;
			}
			 return noOfTeacherLegends;
	  }
	  @Override
	  public boolean gradeStudentIOLSectionToTeacher(long learIndicatorId,String leScore){
		  try{
				iolReportCardDAO.gradeStudentIOLSectionToTeacher(learIndicatorId,leScore);
			}catch(Exception e)
			{
				logger.error("Error in gradeStudentIOLSectionToTeacher() of IOLReportCardServiceImpl", e);
				return false;
			}
			 return true;
	  }
	  @Override
	  public void gradeStudentIOLSubCriteriaToTeacher(long learIndicatorId,long learningValuesId){
		  try{
				iolReportCardDAO.gradeStudentIOLSubCriteriaToTeacher(learIndicatorId,learningValuesId);
			}catch(Exception e)
			{
				logger.error("Error in gradeStudentIOLSubCriteriaToTeacher() of IOLReportCardServiceImpl", e);
				
			}
			 
	  }
	  @Override
	  public boolean checkIOLReportStatus(long iolReportId){
		  boolean status=false;
		  try{
				status=iolReportCardDAO.checkIOLReportStatus(iolReportId);
			}catch(Exception e)
			{
				logger.error("Error in checkIOLReportStatus() of IOLReportCardServiceImpl", e);
				
			}
		  return status;
	  }
	  @Override
	  public boolean checkIOLGradeStatus(long iolReportId){
		  boolean status=false;
		  try{
				status=iolReportCardDAO.checkIOLGradeStatus(iolReportId);
			}catch(Exception e)
			{
				logger.error("Error in checkIOLGradeStatus() of IOLReportCardServiceImpl", e);
				
			}
		  return status;
	  }
	  @Override
	  public List<LearningIndicator> getStudentIOLReportByIOLReportId(long studentId,long iolReportId){
			 List<LearningIndicator> learnInds=new ArrayList<LearningIndicator>();
			 try{
				 learnInds= iolReportCardDAO.getStudentIOLReportByIOLReportId(studentId,iolReportId);
			 }catch(DataException e){
				  logger.error("Error in getStudentIOLReportByIOLReportId() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
		}
	  @Override
	  public List<MulYrLegend> getMultiYearLegends(){
		  List<MulYrLegend> mulYrLegends=new ArrayList<MulYrLegend>();
			 try{
				 mulYrLegends= iolReportCardDAO.getMultiYearLegends();
			 }catch(DataException e){
				  logger.error("Error in getMultiYearLegends() of IOLReportCardServiceImpl", e);
				}
			 return mulYrLegends;
	  }
	  @Override
	  public List<LearningIndicator> getStudentIOLScoresByCriteriaId(long studentId,long legendCriteriaId){
		  List<LearningIndicator> learnInds=new ArrayList<LearningIndicator>();
		  try{
				 learnInds= iolReportCardDAO.getStudentIOLScoresByCriteriaId(studentId,legendCriteriaId);
			 }catch(DataException e){
				  logger.error("Error in getStudentIOLReportByIOLReportId() of IOLReportCardServiceImpl", e);
				}
			 return learnInds;
	  }
	  @Override
	  public long chkIOLReportExistsByCsIdStudentId(long csId,long studentId){
		 long stat=0;
		  try{
				 stat= iolReportCardDAO.chkIOLReportExistsByCsIdStudentId(csId,studentId);
			 }catch(DataException e){
				  logger.error("Error in getStudentIOLReportByIOLReportId() of IOLReportCardServiceImpl", e);
				}
			 return stat;
	  }
	  @Override
	  public IOLReport getTrimesterId(long csId,long studentId){
		  IOLReport iolReport=new IOLReport();  
		  try{
			  iolReport= iolReportCardDAO.getTrimesterId(csId,studentId);
			 }catch(DataException e){
				  logger.error("Error in getTrimesterId() of IOLReportCardServiceImpl", e);
			 }
		  return iolReport;
	    }
	  @Override
	  public boolean addDefaultRubricsToGrades(){
		  List<LERubrics> leRubrics = new ArrayList<LERubrics>() ;
		  List<Grade> grades = null;
		  List<Legend> legendList = null;		  
		  try{
			  grades = gDao.getGradesList();
			  legendList = iolReportCardDAO.getDefaultLegendsByCriteria(5);
			  for(Grade grade : grades){
				  for(Legend legend : legendList){
					  LERubrics lRubric = new LERubrics();
					  lRubric.setGrade(grade);
					  lRubric.setLegendSubCriteria(legend.getLegendSubCriteria());
					  lRubric.setLegend(legend);
					  lRubric.setStatus(WebKeys.ACTIVE);
					  leRubrics.add(lRubric);					  
				  }
			  }			  
			  iolReportCardDAO.addDefaultRubricsToGrades(leRubrics);
		  }catch(DataException e){
				  logger.error("Error in addDefaultRubricsToGrades() of IOLReportCardServiceImpl", e);
		  }
		  return true;
	  }
	  @Override
	  public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId,long caasppTypesId, String className){
		  List<CAASPPScores> caasppScores=new ArrayList<CAASPPScores>();
		  try{
			  caasppScores= iolReportCardDAO.getCAASPPScoresByStudentId(studentId,caasppTypesId,className);
			 }catch(DataException e){
				  logger.error("Error in getCAASPPScoresByStudentId() of IOLReportCardServiceImpl", e);
				}
			 return caasppScores; 
	  }
	  @Override
	  public List<Grade> getSchoolGradesForIOL(long schoolId){
		  List<Grade> gradeList=new ArrayList<Grade>();
		  try{
			  gradeList= iolReportCardDAO.getSchoolGradesForIOL(schoolId);;
			 }catch(DataException e){
				  logger.error("Error in getSchoolGradesForIOL() of IOLReportCardServiceImpl", e);
				}
			 return gradeList; 
	  }
	@Override
	public List<Legend> getSubcriteriaRubrics(long subId) {
		return iolReportCardDAO.getSubcriteriaRubrics(subId);
	}
	@Override
	public boolean updateIOLRubrics(List<Legend> legendList) {
		return iolReportCardDAO.updateIOLRubrics(legendList);
	}
	@Override
	public boolean assignIOLRubricToGrade(long userRegId,long gradeId,long createdBy,long trimesterId){
		try{
			 UserRegistration useR=null;
			 AssignLegendRubrics assRubrics = new AssignLegendRubrics();
			 assRubrics.setGrade(gradesDAO.getGrade(gradeId));
			 if(userRegId==0)
				 assRubrics.setReferRegId(useR);	
			 else
				 assRubrics.setReferRegId(userRegisDao.getUserRegistration(userRegId));	
			 assRubrics.setCreatedBy(userRegisDao.getUserRegistration(createdBy));
			 assRubrics.setTrimester(iolReportCardDAO.getTrimesterId(trimesterId));
			 iolReportCardDAO.assignIOLRubricToGrade(assRubrics);
			 return true;
		}catch(Exception e){
			logger.error("Error in assignIOLRubricToGrade() of IOLReportCardServiceImpl", e);
			return false;
		}
		
	}
	@Override
	public List<Legend> getCriteriaRubrics(long createdBy, long subCriteriaId) {
		return iolReportCardDAO.getCriteriaRubrics(createdBy,subCriteriaId);
	}
	@Override
	public List<Trimester> getAllTrimesters(){
		 List<Trimester> trimesList=new ArrayList<Trimester>();
		  try{
			  trimesList= iolReportCardDAO.getAllTrimesters();;
			 }catch(DataException e){
				  logger.error("Error in getAllTrimesters() of IOLReportCardServiceImpl", e);
				}
			 return trimesList; 
	}
	public boolean checkAlreadyAssignRubric(long gradeId,long trimesterId,long teacherRegId){
		boolean stat=false;
		try{
			stat=iolReportCardDAO.checkAlreadyAssignRubric(gradeId, trimesterId, teacherRegId);
		}catch(Exception e){
			logger.error("Error in checkAlreadyAssignRubric() of IOLReportCardServiceImpl", e);
			stat=false;
		}
		return stat;
	}
	public IOLReport getTrimesterByIolReportId(long iolReportId){
		IOLReport iolReport=new IOLReport();  
		  try{
			  iolReport= iolReportCardDAO.getTrimesterByIolReportId(iolReportId);
			 }catch(DataException e){
				  logger.error("Error in getTrimesterByIolReportId() of IOLReportCardServiceImpl", e);
			 }
		  return iolReport;
	}
	public long getReferRegId(long gradeId,long trimesterId,long createdBy){
		long referId=0;  
		  try{
			  referId= iolReportCardDAO.getReferRegId(gradeId,trimesterId,createdBy);
			 }catch(DataException e){
				  logger.error("Error in getTrimesterByIolReportId() of IOLReportCardServiceImpl", e);
			 }
		  return referId;
	}
	public List<Legend> getRubricValuesByUserId(long referRegId,long subCriteriaId, long gradeId){
		 List<Legend> lstLegend=new ArrayList<Legend>();
		  try{
			  lstLegend= iolReportCardDAO.getRubricValuesByUserId(referRegId,subCriteriaId, gradeId);
			 }catch(DataException e){
				  logger.error("Error in getAllTrimesters() of IOLReportCardServiceImpl", e);
				}
			 return lstLegend; 
	}
	@Override
	public List<LearningIndicatorValues> getWholeClassCriteriaReport(long csId,long trimesterId,long criteriaId){
		List<LearningIndicatorValues> lstWholeClassCriteriaReport=new ArrayList<LearningIndicatorValues>();
		  try{
			  lstWholeClassCriteriaReport= iolReportCardDAO.getWholeClassCriteriaReport(csId,trimesterId,criteriaId);
			 }catch(DataException e){
				  logger.error("Error in getWholeClassCriteriaReport() of IOLReportCardServiceImpl", e);
				}
			 return lstWholeClassCriteriaReport; 
	}
	public List<LearningIndicatorValues> getStudentCriteriaReport(long studentId,long trimesterId,long criteriaId){
		List<LearningIndicatorValues> lstStudentCriteriaReport=new ArrayList<LearningIndicatorValues>();
		  try{
			  lstStudentCriteriaReport= iolReportCardDAO.getStudentCriteriaReport(studentId,trimesterId,criteriaId);
			 }catch(DataException e){
				  logger.error("Error in getWholeClassCriteriaReport() of IOLReportCardServiceImpl", e);
				}
			 return lstStudentCriteriaReport;
	}
}