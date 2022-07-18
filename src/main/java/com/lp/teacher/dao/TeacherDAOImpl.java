package com.lp.teacher.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.AdminTeacherReports;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyErrorTypes;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.Grade;
import com.lp.model.HomeroomClasses;
import com.lp.model.LessonPaths;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.Section;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherPerformances;
import com.lp.model.TeacherReports;
import com.lp.model.TeacherSubjects;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("teacherDao")
public class TeacherDAOImpl extends CustomHibernateDaoSupport implements
		TeacherDAO {
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private SessionFactory sessionFactory;	
	@Autowired
	private HttpSession httpSession;
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	@Override
	public boolean saveTeacher(Teacher teacher) {
		super.saveOrUpdate(teacher);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getTeachers(long gradeId, long classId) {
		List<Teacher> teacherLt = new ArrayList<Teacher>();
		List<TeacherSubjects> teacherSubjectsLt = (List<TeacherSubjects>) getHibernateTemplate().find("from TeacherSubjects where grade.gradeId=" + gradeId + " and studentClass.classId="+classId+" and teacher.status='active'");
		for(TeacherSubjects teacherSubjects: teacherSubjectsLt){
			teacherLt.add(teacherSubjects.getTeacher());
		}
		return teacherLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getTeachersByReg(long schoolId,long userTypeId) {
		List<UserRegistration> teachersLt = null;
		teachersLt = (List<UserRegistration>) getHibernateTemplate().find("from UserRegistration where user_typeid= "+ userTypeId+" and school_id="+schoolId+" and status='"+WebKeys.ACTIVE+"'");
		return teachersLt;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TeacherPerformances> getTeacherPerformances(){
		List<TeacherPerformances> teacherperformancesLt = new ArrayList<TeacherPerformances>();
		try{
		teacherperformancesLt = (List<TeacherPerformances>) getHibernateTemplate().find("from TeacherPerformances order by id");
	} catch (DataAccessException e) {
		logger.error("Error in getGradePeriods() of AdminDAOImpl"
				+ e);
		e.printStackTrace();
	}
		return teacherperformancesLt;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public TeacherPerformances getTeacherPerformance(long performanceId) {
		List<TeacherPerformances> teacherPerformance = (List<TeacherPerformances>) getHibernateTemplate()
				.find("from TeacherPerformances where id=" + performanceId + "");
		if (teacherPerformance.size() > 0)
			return teacherPerformance.get(0);
		else
			return new TeacherPerformances();
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public Teacher getTeacher(long teacherregId) {
		List<Teacher> teacher = (List<Teacher>) getHibernateTemplate()
				.find("from Teacher where userRegistration.regId=" + teacherregId + " and userRegistration.status='"+WebKeys.ACTIVE+"'");
		if (teacher.size() > 0)
			return teacher.get(0);
		else
			return new Teacher();
	}
	@Override
	 public boolean saveTeacherPerformances(TeacherReports teacherreports){
		try{
		super.saveOrUpdate(teacherreports);
		return true;
		}catch(Exception e)
		{
			logger.error("Error in saveTeacherPerformances() of AdminDAOImpl"
					+ e);
			return false;
		}
	 }

	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getAdminUserRegistration(School school) {
		List<UserRegistration> userreg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where lcase(user.userType)='admin' and school.schoolId="+school.getSchoolId()+"");
		if (userreg.size() > 0)
			return userreg.get(0);
		else
			return new UserRegistration();
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TeacherReports> getTeacherReports(User user,Teacher teacher){
		List<TeacherReports> teacherReports = new ArrayList<TeacherReports>();
	try{
		teacherReports = (List<TeacherReports>) getHibernateTemplate()
				.find("from TeacherReports where adminTeacherReports.user.userTypeid="+user.getUserTypeid()+" and adminTeacherReports.teacher.teacherId="+teacher.getTeacherId()+" and adminTeacherReports.date=current_date");
		}catch(DataAccessException e) {
			logger.error("Error in getGradePeriods() of AdminDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return teacherReports;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ClassStatus> getTeachersBySchoolId(School school){
		List<ClassStatus> teacherList = new ArrayList<ClassStatus>();
		try{
			
			teacherList = (List<ClassStatus>) getHibernateTemplate()
					.find("from ClassStatus where teacher.userRegistration.school.schoolId="+school.getSchoolId()+" and availStatus='"+WebKeys.AVAILABLE+"'"
							+ " order by teacher.userRegistration.emailId");
			}catch(DataAccessException e) {
				logger.error("Error in getGradePeriods() of AdminDAOImpl"
						+ e);
				e.printStackTrace();
			}
			return teacherList;
		}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<AdminTeacherReports> getTeacherSelfReportDates(long teacherId){
		List<AdminTeacherReports> list1 = new ArrayList<AdminTeacherReports>();	
       
		try {
	     String userType="teacher";
	     list1 = (List<AdminTeacherReports>) getHibernateTemplate().find("from AdminTeacherReports where teacher.teacherId="+teacherId+" and "
         		+ "lcase(user.userType) like '" + userType+"'");
			
		} catch (Exception e) {
			logger.error("Error in getTeacherSelfReportDates() of TeacherDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getAllTeachers() {
		return super.findAll(Teacher.class);
	}

	@Override
	public boolean saveAdminTeacherReports(AdminTeacherReports adminteacherreports){
		try{
		super.saveOrUpdate(adminteacherreports);
		return true;
		}catch(Exception e)
		{
			logger.error("Error in saveAdminTeacherReports() of TeacherDAOImpl"
					+ e);
			return false;
		}
	 }
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TeacherReports> getTeacherReportsByDate(Teacher teacher,String reportDate){
			
		List<TeacherReports> teacherReports = new ArrayList<TeacherReports>();
	try{
		teacherReports = (List<TeacherReports>) getHibernateTemplate().find("from TeacherReports where adminTeacherReports.user.userTypeid="
				+teacher.getUserRegistration().getUser().getUserTypeid()+" and adminTeacherReports.teacher.teacherId="+teacher.getTeacherId()+" and adminTeacherReports.date='"+reportDate+"'");
		}catch(Exception e) {
			logger.error("Error in getTeacherReportsByDate() of TeacherDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return teacherReports;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public Teacher getTeacherbyTeacherId(long teacherId) {
		List<Teacher> teacher = (List<Teacher>) getHibernateTemplate()
				.find("from Teacher where teacherId=" + teacherId + "");
		if (teacher.size() > 0)
			return teacher.get(0);
		else
			return new Teacher();
	}

	@Override
	public List<Teacher> saveTeachers(List<Teacher> teachers) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long i = 0;
		for ( Teacher ur : teachers ) {
		    session.save(ur);
		    i++;
		    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		        session.flush();
		        session.clear();
		    }
		}
		   
		tx.commit();
		session.close();
		return teachers;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Section> getSections(long gradeId, long classId) {
		List<Section> sectionLt = new ArrayList<Section>();
		sectionLt = (List<Section>) getHibernateTemplate().find(
				"from Section where gradeClasses.grade.gradeId="+gradeId+" and gradeClasses.studentClass.classId="+classId+" and gradeClasses.status='"+WebKeys.ACTIVE+"'");
		return sectionLt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getCSIds(long gradeId, long classId, long teacherId, long schoolId) {
		List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
		casLt=(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where classStatus.section.gradeClasses.grade.gradeId="+gradeId+" and classStatus.section.gradeClasses.studentClass.classId="+classId+" and classStatus.teacher.teacherId="+teacherId+"and classStatus.availStatus='"+WebKeys.AVAILABLE+"' and classStatus.teacher.userRegistration.school.schoolId="+schoolId);
		return casLt;
	}
	@Override
	public long setClassStatus(long sectionId, long teacherId, String status,
			Date startDate, Date endDate,long casId) {
		String query_for_csId = null;
		long cs_Id=0;
		if(sectionId == 0){
			if(casId != 0){
				query_for_csId = "select cs_id from class_actual_schedule where cas_id="+casId;
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query_for_csId);
				while (rs.next()) {
					cs_Id = rs.getInt(1);
			     }
				if(cs_Id != 0){
					 jdbcTemplate.update("delete from class_actual_schedule where cs_Id="+cs_Id+" and cas_id="+casId);
				}
				 return 0;
			}else{
				 return 0;
			}
		}else{
			query_for_csId = "select cs_id,avail_status from class_status where section_id="+sectionId+" and teacher_id="+teacherId;
			long csId=0;
			String availStatus ="";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query_for_csId);
			if(rs.next()) {
				csId = rs.getInt(1);
				availStatus = rs.getString(2);
				if(availStatus.equalsIgnoreCase(WebKeys.EXPIRED)){
					String updateQuery = "update class_status set avail_status='"+WebKeys.AVAILABLE+"' where section_id="+sectionId+" and teacher_id="+teacherId;
					jdbcTemplate.update(updateQuery);
				}
		     }
			if(csId == 0){
				String query = "insert into class_status(section_id,teacher_id,avail_status,start_date,end_date) values(?,?,?,?,?)";
				jdbcTemplate.update(query, sectionId, teacherId, status, startDate,endDate);
				query_for_csId = "select cs_id from class_status where section_id="+sectionId+" and teacher_id="+teacherId+" and avail_status='"+WebKeys.AVAILABLE+"'";
				SqlRowSet rs1 = jdbcTemplate.queryForRowSet(query_for_csId);
				while (rs1.next()) {
					csId = rs1.getInt(1);
			     }
			}else{
				DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
				String startDateStr = formatter.format(startDate);
				String endDateStr = formatter.format(endDate);
				String updateQuery = "update class_status set avail_status='"+status+"', start_date='"+startDateStr+"', end_date='"+endDateStr+"'  where section_id="+sectionId+" and teacher_id="+teacherId;
				jdbcTemplate.update(updateQuery);
			}
			return csId;
		}
	}

	@Override
	public boolean setClassActualStatus(long casId, long csId, long dayId, long periodId,long teacherId,long sectionId) {
		String query = "";
		if(casId == 0){
			query = "insert into class_actual_schedule(cs_id,day_id,period_id) values(?,?,?)";
			jdbcTemplate.update(query, csId, dayId, periodId);
		}else{
			long cs_Id=0;
			String query_for_csId = null;
			query_for_csId = "select cs_id from class_status where section_id="+sectionId+" and teacher_id="+teacherId+" and avail_status='"+WebKeys.AVAILABLE+"'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query_for_csId);
			while (rs.next()) {
				cs_Id = rs.getInt(1);
		     }
			query = "update class_actual_schedule set cs_id="+cs_Id+" where cas_id="+casId;
			jdbcTemplate.update(query);
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsBySection(long csId) {
		List<Student> studentLt = new ArrayList<Student>();
		try{
		List<RegisterForClass> registerForClassLt = (List<RegisterForClass>) getHibernateTemplate().find(
				"from RegisterForClass where classStatus.csId="+csId+"and classStatus.availStatus='"+WebKeys.AVAILABLE+"' and status='"+WebKeys.ACCEPTED+"' and classStatus_1='"+WebKeys.ALIVE+"'"
						+ " order by student.userRegistration.lastName");
		for(RegisterForClass rfc: registerForClassLt){
			studentLt.add(rfc.getStudent());
		}
		}catch(Exception e){
			 logger.error("Error in getStudentsBySection() of TeacherDAOImpl"
	                    + e);
	            e.printStackTrace();
		}
		return studentLt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkTeacherAvailability(long gradeId, long classId, long teacherId, long dayId,Periods period) {
		  try {
			boolean isOverlapped = false;
	        List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
			casLt=(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where"
					+" classStatus.teacher.teacherId="+teacherId
					+" and days.dayId="+dayId
					+" and classStatus.availStatus='"+WebKeys.AVAILABLE+"'");
				 if(casLt.size() > 0){
			    	 for(ClassActualSchedule cas : casLt){
						 if(cas.getClassStatus().getSection().getGradeClasses().getGrade().getGradeId() == gradeId){
							 if(cas.getPeriods().getPeriodId() == period.getPeriodId() && cas.getClassStatus().getSection().getGradeClasses().getStudentClass().getClassId() != classId){
								 return false;
							 }
						 }else{
							isOverlapped = commonService.isTimeBetweenTwoTime(cas.getPeriods().getStartTime(),cas.getPeriods().getEndTime(),period.getStartTime(),period.getEndTime());
							if(isOverlapped)
							   return false;
						 }
					 }
		    	 }else{
		        	 return true;
		    	 }
	        } catch (Exception e) {
	            logger.error("Error in checkTeacherAvailability() of TeacherDAOImpl"
	                    + e);
	            e.printStackTrace();
	        }
			return true;
		}

	@Override
	public boolean checkSectionAvailabilityforTeacher(long sectionId,long teacherId) {
		boolean ss=false;
		try{
		String query = "select cs_id from class_status where section_id="+sectionId+" and teacher_id != "+teacherId+ " and avail_status='"+WebKeys.AVAILABLE+"'";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			ss=false;
		}else{
			ss=true;
		}
		}catch(Exception e){
			logger.error("Error in checkSectionAvailabilityforTeacher() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
		}
		return ss;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getStudentsByCsId(long csId) {
		List<RegisterForClass> registerForClassLt =Collections.emptyList();
		AcademicYear academicYear = null;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				registerForClassLt = (List<RegisterForClass>) getHibernateTemplate().find(
						"from RegisterForClass where classStatus.csId="+csId+" and classStatus_1='"+WebKeys.EXPIRED+"' and status='"
								+WebKeys.ACCEPTED+"' order by student.userRegistration.lastName");
			}else{
				registerForClassLt = (List<RegisterForClass>) getHibernateTemplate().find(
						"from RegisterForClass where classStatus.csId="+csId+" and classStatus_1='"+WebKeys.ALIVE+"' and status='"
								+WebKeys.ACCEPTED+"' order by student.userRegistration.lastName");
			}
		}catch(Exception e){
			logger.error("Error in getStudentsByCsIdAndRtiGroup() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
		}
		return registerForClassLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getStudentsByCsIdAndRtiGroup(long csId, long rtiGroupId) {
		List<RegisterForClass> registerForClassLt =Collections.emptyList();
		try{
		 registerForClassLt = (List<RegisterForClass>) getHibernateTemplate().find(
				"from RegisterForClass where classStatus.csId="+csId+" and classStatus_1='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"' and rtiGroups.rtiGroupId= "+rtiGroupId);
		}catch(Exception e){
			logger.error("Error in getStudentsByCsIdAndRtiGroup() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
		}
		return registerForClassLt;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TeacherSubjects> getTeachersRequests(long schoolId) {
		List<TeacherSubjects> teacherRequestLt=Collections.emptyList();
		try{
		 teacherRequestLt = (List<TeacherSubjects>) getHibernateTemplate().find(
				"from TeacherSubjects where requestedClassStatus='"+WebKeys.WAITING+"' and teacher.userRegistration.school.schoolId="+schoolId+"");
		}catch(Exception e){
			logger.error("Error in getTeachersRequests() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
		}
		return teacherRequestLt;
	}
	
	@Override
	public boolean setTeacherReplyAction(long gradeId, long classId, long teacherId, String status) {
		boolean success = false;
		try{
			long teacherSubjectId = 0;
			String query = "select teacher_subject_id from teacher_subjects where grade_id="+gradeId+" and class_id = "+classId+" and teacher_id = "+teacherId;
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				teacherSubjectId = rs.getLong(1);
			}
			if(teacherSubjectId > 0){
				String update_teacher_status_query = "update teacher_subjects set requested_class_status='"+status+"', desktop_status='"+WebKeys.ACTIVE+"' where grade_id="+gradeId+" and class_id="+classId+" and teacher_id="+teacherId;
				jdbcTemplate.update(update_teacher_status_query);
			  	success = true;
			}else{
				success = false;
			}
		}catch(Exception e){
			success = false;
		}
		return success;
	}
	@Override
	@SuppressWarnings("unchecked")
	 public List<Assignment> getBenchmarkDates(Teacher teacher, long csId, String usedFor){
		List<BenchmarkResults> assignmentDates = new ArrayList<BenchmarkResults>();
		List<Assignment> assignments = new ArrayList<>();
		try{
			 assignmentDates =(List<BenchmarkResults>) getHibernateTemplate().find("from BenchmarkResults where studentAssignmentStatus.assignment.classStatus.csId="+csId+" and "
				 		+ "studentAssignmentStatus.assignment.usedFor='"+usedFor+"' group by studentAssignmentStatus.assignment.dateAssigned");
		}catch (DataAccessException e) {
            logger.error("Error in getBenchmarkDates() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
        }
		for(BenchmarkResults br:assignmentDates){
			Assignment as =new Assignment();
			as = br.getStudentAssignmentStatus().getAssignment();
			assignments.add(as);
		}
		return assignments;
	 }
	@Override
	@SuppressWarnings("unchecked")
	public List<BenchmarkResults> getBenchmarkResults(Teacher teacher, long csId, String usedFor,String dateAssigned,long assignmentId){
		
		List<BenchmarkResults> benchmarkResults = new ArrayList<BenchmarkResults>();
		try{
			benchmarkResults =(List<BenchmarkResults>) getHibernateTemplate().find("from BenchmarkResults where studentAssignmentStatus.assignment.classStatus.csId="+csId+" and "
				 		+ "studentAssignmentStatus.assignment.usedFor='"+usedFor+"' and studentAssignmentStatus.assignment.dateAssigned='"+dateAssigned+"' and studentAssignmentStatus.assignment.assignmentId='"+assignmentId+"' and studentAssignmentStatus.gradedStatus='graded' order by medianFluencyScore desc");
		}catch (DataAccessException e) {
            logger.error("Error in getBenchmarkResults() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
        }
				
		return benchmarkResults;
	 }
	@Override
	@SuppressWarnings("unchecked")
	 public String getRTIGroupName(long studentId,long csId){
		 List<RegisterForClass> regis = new ArrayList<RegisterForClass>();
			try{
				regis =(List<RegisterForClass>) getHibernateTemplate().find("from RegisterForClass where classStatus.csId="+csId+" and "
					 		+ "student.studentId="+studentId+"");
			}catch (DataAccessException e) {
	            logger.error("Error in getRTIGroupName() of TeacherDAOImpl"
	                    + e);
	            e.printStackTrace();
	        }
			if(regis.get(0).getRtiGroups()!=null)
			return regis.get(0).getRtiGroups().getRtiGroupName();
			else
			return "Sub Group";
	 }
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<StudentClass> getStudentClasses(long gradeId) {

		List<StudentClass> list1 = new ArrayList<StudentClass>();
		try {

			list1 = (List<StudentClass>) getHibernateTemplate()
					.find("from StudentClass where classId in("
							+ "select sc.classId from GradeClasses as gc inner join gc.studentClass as sc where  gc.grade.gradeId="
							+ gradeId + " and gc.status='"+WebKeys.ACTIVE+"')");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getClassStatus(long gradeId, long classId, long teacherId) {
		List<ClassStatus> csLt = new ArrayList<ClassStatus>();
		csLt=(List<ClassStatus>) getHibernateTemplate().find("from ClassStatus where section.gradeClasses.grade.gradeId="+gradeId+" and section.gradeClasses.studentClass.classId="+classId+" and teacher.teacherId="+teacherId+"and availStatus='"+WebKeys.AVAILABLE+"'");
		return csLt;
	}
	
	@Override
	public void setClassStatusAsExpired(ClassStatus classStatus){
		String updateQuery = "update class_status set avail_status='"+WebKeys.EXPIRED+"' where cs_id ="+classStatus.getCsId()+" and avail_status='"+WebKeys.AVAILABLE+"'";
		jdbcTemplate.update(updateQuery);
	}
	@Override
	public void setLessonPath(LessonPaths lessonPaths) {
		try{
			String query = "insert into lesson_paths(lesson_id,lesson_path,uploaded_by) values(?,?,?)";
			jdbcTemplate.update(query, lessonPaths.getLesson().getLessonId(),lessonPaths.getLessonPath(),lessonPaths.getUploadedBy());
		}catch(Exception e){
			
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean getSelfEvaluation(long teacherId, long userTypeid, String date) throws DataException {
		List<AdminTeacherReports> adminTeacherReports = new ArrayList<AdminTeacherReports>();	
	       
		try {
			adminTeacherReports = (List<AdminTeacherReports>) getHibernateTemplate().find("from AdminTeacherReports where teacher.teacherId="+teacherId+" and "
         		+ "user.userTypeid=" + userTypeid+" and date='"+date+"'");
			
		} catch (Exception e) {
			logger.error("Error in getSelfEvaluation() of TeacherDAOImpl"+ e);
		}
		if(adminTeacherReports.size()>0){
			return true;
		}else{
			return false;
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String checkHomeRoomTeacher(HomeroomClasses hrc) throws DataException {
		List<HomeroomClasses> homeroom = new ArrayList<HomeroomClasses>();	
		String status = "";   
		try {
			if(hrc.getTeacher().getTeacherId() == 0){
				jdbcTemplate.update("delete from homeroom_classes where period_id = "+hrc.getPeriods().getPeriodId()+" and section_id = "+hrc.getSection().getSectionId());
				status = "Updated Successfully !!";
			}else{
				homeroom = (List<HomeroomClasses>) getHibernateTemplate().find("from HomeroomClasses where teacher.teacherId="+hrc.getTeacher().getTeacherId());			
				if(homeroom.size()>0){
					status = "Teacher already assigned !!";
				}else{
					long homeroomId = 0;
					String query = "select homeroom_id from homeroom_classes where period_id = "+hrc.getPeriods().getPeriodId()+" and section_id = "+hrc.getSection().getSectionId();
					SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
					if (rs.next())
						homeroomId = rs.getLong(1);
					

					if(homeroomId > 0){			
						String updateQuery = "update homeroom_classes set teacher_id="+hrc.getTeacher().getTeacherId()+" where period_id = "+hrc.getPeriods().getPeriodId()+" and section_id = "+hrc.getSection().getSectionId();
						jdbcTemplate.update(updateQuery);
						status = "Updated Successfully !!";
					}else{
						Session session = sessionFactory.openSession();
						Transaction tx = session.beginTransaction();
						session.save(hrc);	
						tx.commit();
						session.close();
						status = "Assigned Successfully !!";
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in checkHomeRoomTeacher() of TeacherDAOImpl"+ e);
		}
		return status;		
	}
	@SuppressWarnings("unchecked")
	public List<LessonPaths> getLessonPathByLessonId(long lessonId){
		List<LessonPaths> lessonPathsLt = new ArrayList<LessonPaths>();	
		try {
			lessonPathsLt = (List<LessonPaths>) getHibernateTemplate().find("from LessonPaths where lesson.lessonId="+lessonId);			
		} catch (Exception e) {
			logger.error("Error in getLessonPathByLessonId() of TeacherDAOImpl"+ e);
		}
		return lessonPathsLt;
	}
	public boolean deleteLessonPath(long lessonId,String lessonPath){
		long lesson_path_id = 0;
		boolean status = false;
		try {
			String query_for_lesson_path_id = "select lesson_path_id from lesson_paths where lesson_id="+lessonId+" and lesson_path='"+lessonPath+"'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query_for_lesson_path_id);
			while (rs.next()) {
				lesson_path_id = rs.getInt(1);
		     }
			if(lesson_path_id != 0){
				 jdbcTemplate.update("delete from lesson_paths where lesson_path_id="+lesson_path_id);
				 status = true;
			}
		} catch (Exception e) {
			 status = false;
			logger.error("Error in deleteLessonPath() of TeacherDAOImpl"+ e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public AssignmentQuestions getAssignmentQuestions(long studentAssignmentId){
		
		
			List<AssignmentQuestions> list=new ArrayList<AssignmentQuestions>();
			try{
	        list = (List<AssignmentQuestions>) getHibernateTemplate()
	    				.find("from AssignmentQuestions where studentAssignmentStatus.studentAssignmentId= " + studentAssignmentId);
			}catch(Exception e){
				logger.error("Error in getAssignmentQuestions() of GradeAssessmentsDAOImpl", e);
				e.printStackTrace();
			}
	    	return list.get(0);
	       
	   
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getErrorAddressList(long studentAssignmentId){
		List<FluencyMarksDetails> list = new ArrayList<FluencyMarksDetails>();	
		List<String> errorAddressList = new ArrayList<String>();
		AssignmentQuestions assignQues=new AssignmentQuestions();
		assignQues=getAssignmentQuestions(studentAssignmentId);
		try {			
			list = (List<FluencyMarksDetails>) getHibernateTemplate().find("from FluencyMarksDetails"
					+ " where fluencyMarks.gradingTypes.gradingTypesId=1 and fluencyMarks.assignmentQuestions.assignmentQuestionsId="+assignQues.getAssignmentQuestionsId()+
					" GROUP BY errorWord ORDER BY errorWord");
			for(FluencyMarksDetails o : list){
				errorAddressList.add(o.getErrorWord());
			}
	 		
		} catch (Exception e) {
			logger.error("Error in getErrorAddressList() of TeacherDAOImpl"+ e);
		}
		return errorAddressList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSkippedWordList(long studentAssignmentId){
		List<FluencyMarksDetails> list = new ArrayList<FluencyMarksDetails>();	
		List<String> skippedAddressList = new ArrayList<String>();
		AssignmentQuestions assignQues=new AssignmentQuestions();
		assignQues=getAssignmentQuestions(studentAssignmentId);
		try {			
			list = (List<FluencyMarksDetails>) getHibernateTemplate().find("from FluencyMarksDetails"
					+ " where fluencyMarks.gradingTypes.gradingTypesId=1 and fluencyMarks.assignmentQuestions.assignmentQuestionsId="+assignQues.getAssignmentQuestionsId()+ " and skippedWord='true'"+
					" GROUP BY errorWord ORDER BY errorWord");
			for(FluencyMarksDetails o : list){
				skippedAddressList.add(o.getErrorWord());
			}
	 		
		} catch (Exception e) {
			logger.error("Error in getSkippedWordList() of TeacherDAOImpl"+ e);
		}
		return skippedAddressList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FluencyMarksDetails> getErrorWordCount(long assignmentId){
		List<FluencyMarksDetails> list = new ArrayList<FluencyMarksDetails>();
		try {
			
			list = (List<FluencyMarksDetails>) getHibernateTemplate().find("from FluencyMarksDetails"
					+ " where fluencyMarks.gradingTypes.gradingTypesId=1 and fluencyMarks.assignmentQuestions.studentAssignmentStatus.assignment."
					+ "assignmentId="+assignmentId+" GROUP BY errorWord ORDER BY errorWord");
				
			  
		} catch (Exception e) {
			logger.error("Error in getErrorWordCount() of TeacherDAOImpl"+ e);
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentAssignmentStatusList(long assignmentId){
		List<StudentAssignmentStatus> sAssignmentStatus = new ArrayList<StudentAssignmentStatus>();	
		try {
			sAssignmentStatus = (List<StudentAssignmentStatus>) getHibernateTemplate().find("FROM StudentAssignmentStatus"
					+ " WHERE assignment.assignmentId="+assignmentId +" and gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"'");
		} catch (Exception e) {
			logger.error("Error in getStudentAssignmentStatusList() of TeacherDAOImpl"+ e);
		}
		return sAssignmentStatus;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FluencyAddedWords> getAddedWordCount(long assignmentId,long wordType){
		List<FluencyAddedWords> list = new ArrayList<FluencyAddedWords>();
		try {
			
			list = (List<FluencyAddedWords>) getHibernateTemplate().find("from FluencyAddedWords"
					+ " where fluencyMarks.gradingTypes.gradingTypesId=1 and fluencyMarks.assignmentQuestions.studentAssignmentStatus.assignment."
					+ "assignmentId="+assignmentId+" and wordType="+wordType+" GROUP BY addedWord ORDER BY addedWord");
				
			  
		} catch (Exception e) {
			logger.error("Error in getAddedWordCount() of TeacherDAOImpl"+ e);
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAddedWordList(long studentAssignmentId,long wordType){
		List<FluencyAddedWords> list = new ArrayList<FluencyAddedWords>();	
		List<String> errorAddressList = new ArrayList<String>();
		AssignmentQuestions assignQues=new AssignmentQuestions();
		assignQues=getAssignmentQuestions(studentAssignmentId);
		try {			
			list = (List<FluencyAddedWords>) getHibernateTemplate().find("from FluencyAddedWords"
					+ " where fluencyMarks.gradingTypes.gradingTypesId=1 and fluencyMarks.assignmentQuestions.assignmentQuestionsId="+assignQues.getAssignmentQuestionsId()+
					" and wordType="+wordType+" GROUP BY addedWord ORDER BY addedWord");
			for(FluencyAddedWords o : list){
				errorAddressList.add(o.getAddedWord());
			}
	 		
		} catch (Exception e) {
			logger.error("Error in getAddedWordList() of TeacherDAOImpl"+ e);
		}
		return errorAddressList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FluencyErrorTypes> getFluencyErrorTypes(){
		List<FluencyErrorTypes> list = new ArrayList<FluencyErrorTypes>();
		try {
			list = (List<FluencyErrorTypes>) getHibernateTemplate().find("from FluencyErrorTypes");
		} catch (Exception e) {
			logger.error("Error in getFluencyErrorTypes() of TeacherDAOImpl"+ e);
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUnknownWordList(long studentAssignmentId){
		List<FluencyMarksDetails> list = new ArrayList<FluencyMarksDetails>();	
		List<String> skippedAddressList = new ArrayList<String>();
		AssignmentQuestions assignQues=new AssignmentQuestions();
		assignQues=getAssignmentQuestions(studentAssignmentId);
		try {		
			list = (List<FluencyMarksDetails>) getHibernateTemplate().find("from FluencyMarksDetails"
					+ " where fluencyMarks.gradingTypes.gradingTypesId=2 and fluencyMarks.assignmentQuestions.assignmentQuestionsId="+assignQues.getAssignmentQuestionsId()+ " and unknownWord='true'"+
					" GROUP BY errorWord ORDER BY errorWord");
			for(FluencyMarksDetails o : list){
				skippedAddressList.add(o.getErrorWord());
			}
//			list = (List<FluencyMarksDetails>) getHibernateTemplate().find("from FluencyMarksDetails"
//					+ " where fluencyMarks.gradingTypes.gradingTypesId=2 and fluencyMarks.assignmentQuestions.assignmentQuestionsId="+assignQues.getAssignmentQuestionsId()+ " and unknownWord='true'"+
//					"");
//			for(FluencyMarksDetails o : list){
//				skippedAddressList.add(o.getErrorWord());
//			}
	 		
		} catch (Exception e) {
			logger.error("Error in getSkippedWordList() of TeacherDAOImpl"+ e);
		}
		return skippedAddressList;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getAppAdminUserRegistration() {
		List<UserRegistration> userreg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where lcase(user.userType)='appmanager'");
		if (userreg.size() > 0)
			return userreg.get(0);
		else
			return new UserRegistration();
	}
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public List<Student> getAllStudentsByAssignmentId(long assignemntId){
		List<Student> students = new ArrayList<>();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT sas.student FROM StudentAssignmentStatus sas WHERE"
					+ " sas.assignment.assignmentId=:assignemntId order by sas.student.userRegistration.firstName asc");
					/*+ "and sas.selfGradedStatus=:selfStatus and sas.peerGradedStatus=:peerStatus order by sas.student.userRegistration.firstName asc");*/
			query.setParameter("assignemntId", assignemntId);
			/*query.setParameter("selfStatus", "graded");
			query.setParameter("peerStatus", "graded");*/
			students = query.list();
		}
		catch(Exception e){
			logger.error("Error in getAllStudentsByAssignmentId() of TeacherDAOImpl" + e);
		}
		return students;
	}
	@Override
	@SuppressWarnings("unchecked")
	 public List<Assignment> getAccuracyDates(long csId, String usedFor){
		List<StudentAssignmentStatus> assignmentDates = new ArrayList<StudentAssignmentStatus>();
		List<Assignment> assignments = new ArrayList<>();
		try{
			 assignmentDates =(List<StudentAssignmentStatus>) getHibernateTemplate().find("from StudentAssignmentStatus st where st.assignment.classStatus.csId="+csId+" and "
				 		+ "st.assignment.usedFor='"+usedFor+"' and st.assignment.assignmentType.assignmentTypeId=20 and (st.selfGradedStatus='graded' or st.peerGradedStatus='graded'"
				 				+ " or st.gradedStatus='graded') and st.assignment.assignStatus='active' group by st.assignment.dateAssigned");
		}catch (DataAccessException e) {
            logger.error("Error in getAccuracyDates() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
        }
		for(StudentAssignmentStatus sts:assignmentDates){
			Assignment as =new Assignment();
			as = sts.getAssignment();
			assignments.add(as);
		}
		return assignments;
	 }
	@Override
	public List<Teacher> getTeachersByYear(School school, AcademicYear academicYear) {
		// TODO Auto-generated method stub
		Query query = null;
		List<Teacher> teachers = new ArrayList<Teacher>();	
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		if(academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)) {
			query = session.createQuery("SELECT cs.teacher from ClassStatus cs where cs.teacher.userRegistration.school.schoolId="+school.getSchoolId()+" and "
					+ " cs.availStatus='" + WebKeys.AVAILABLE +"'");
		}  else {
			query = session.createQuery("SELECT cs.teacher from ClassStatus cs where cs.teacher.userRegistration.school.schoolId="+school.getSchoolId()+" and "
					+ " cs.startDate >= '"+ academicYear.getStartDate() + "' and cs.endDate <='" + academicYear.getEndDate() +"'"
					+ " and cs.availStatus='" + WebKeys.EXPIRED +"'");
		}
		
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		teachers =  query.list();
		return teachers;
	}
}

