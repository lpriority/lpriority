package com.lp.admin.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("errorWordItemDAO")
public class ErrorWordItemDAOImpl extends CustomHibernateDaoSupport implements ErrorWordItemDAO {
	
	static final Logger logger = Logger.getLogger(ErrorWordItemDAOImpl.class);
	
	@Autowired
	HttpSession session;
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Long> getErrorAnalysis(long typeId, long gradeId, UserRegistration userReg) throws DataException {
		Map<String, Long> result = new TreeMap<String, Long>();
		List<Object[]> resultSet = Collections.emptyList();
		try{
			if(typeId==1){
				resultSet = (List<Object[]>) getHibernateTemplate().find("select errorWord, count(fluencyMarksDetailsId) "
						+ "From FluencyMarksDetails where unknownWord is null and skippedWord is null and errorWord is not null"
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.userRegistration.school.schoolId="+ userReg.getSchool().getSchoolId()
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.grade.gradeId="+gradeId
						+ " GROUP BY errorWord ORDER BY errorWord ASC");
			}else if(typeId==2){
				resultSet = (List<Object[]>) getHibernateTemplate().find("select errorWord, count(fluencyMarksDetailsId) "
						+ "From FluencyMarksDetails where unknownWord is null and skippedWord is not null and errorWord is not null"
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.userRegistration.school.schoolId="+ userReg.getSchool().getSchoolId()
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.grade.gradeId="+gradeId
						+ " GROUP BY errorWord ORDER BY errorWord ASC");
			}else if(typeId==5){
				resultSet = (List<Object[]>) getHibernateTemplate().find("select errorWord, count(fluencyMarksDetailsId) "
						+ "From FluencyMarksDetails where unknownWord is not null and skippedWord is null and errorWord is not null"
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.userRegistration.school.schoolId="+ userReg.getSchool().getSchoolId()
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.grade.gradeId="+gradeId
						+ " GROUP BY errorWord ORDER BY errorWord ASC");
			}else if(typeId==4){
				resultSet = (List<Object[]>) getHibernateTemplate().find("select addedWord, count(addedWordId) "
						+ "From FluencyAddedWords where wordType = "+1+" and addedWord is not null and addedWord != ''"
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.userRegistration.school.schoolId="+ userReg.getSchool().getSchoolId()
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.grade.gradeId="+gradeId
						+ " GROUP BY addedWord ORDER BY addedWord ASC");
			}else if(typeId==3){
				resultSet = (List<Object[]>) getHibernateTemplate().find("select addedWord, count(addedWordId) "
						+ "From FluencyAddedWords where wordType = "+2+" and addedWord is not null and addedWord != ''"
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.userRegistration.school.schoolId="+ userReg.getSchool().getSchoolId()
						+ " and fluencyMarks.assignmentQuestions.studentAssignmentStatus.student.grade.gradeId="+gradeId
						+ " GROUP BY addedWord ORDER BY addedWord ASC");
			}
			
			for(Object[] obj:resultSet){
				try{
					String errorWord = ((String)obj[0]).substring(0, 1).toUpperCase() + ((String)obj[0]).substring(1);
					result.put(errorWord, (Long)obj[1]);
				}catch(DataException e){
					throw new DataException("Unable to parse the value"); 
				}				
			}
		}catch(Exception e){
			
		}
		return result;
	}
	
}
