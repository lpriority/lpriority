package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.lp.model.BpstTypes;
import com.lp.model.StudentPhonicTestMarks;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("phonicTestReportsDAO")
public class PhonicTestReportsDAOImpl extends CustomHibernateDaoSupport implements PhonicTestReportsDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public  List<StudentPhonicTestMarks>  getStudentPhonicTestMarks(long studentId,long csId, long langId, long bpstTypeId) {
		List<StudentPhonicTestMarks> studentPhonicTestMarksLt = new ArrayList<StudentPhonicTestMarks>();	
		List<StudentPhonicTestMarks> sptmLt = new ArrayList<StudentPhonicTestMarks>();
		try{
			sptmLt =(List<StudentPhonicTestMarks>) getHibernateTemplate().find("from StudentPhonicTestMarks where studentAssignmentStatus.student.studentId="+studentId+" and  studentAssignmentStatus.assignment.classStatus.csId="+csId+" and phonicGroups.phonicSections.language.languageId="+langId+" and  (studentAssignmentStatus.gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"' or studentAssignmentStatus.gradedStatus='"+WebKeys.GRADED_STATUS_LIVE_GRADED+"') group by studentAssignmentStatus.assignment.assignmentId");
			if(bpstTypeId > 0){
				for (int i = 0; i < sptmLt.size(); i++) {
			    	if(sptmLt.get(i).getPhonicGroups().getBpstGroups().get(0).getBpstTypes().getBpstTypeId() == bpstTypeId){
			    		studentPhonicTestMarksLt.add(sptmLt.get(i));
			    	}
				}
			}else{
				studentPhonicTestMarksLt.addAll(sptmLt);
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return studentPhonicTestMarksLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentPhonicTestMarks> getStudentPhonicTestMarksByStudentAssignmentId(long studentAssignmentId) {
		List<StudentPhonicTestMarks> studentPhonicTestMarksLt = new ArrayList<StudentPhonicTestMarks>();
		try{
			studentPhonicTestMarksLt =(List<StudentPhonicTestMarks>) getHibernateTemplate().find("from StudentPhonicTestMarks where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+" and studentAssignmentStatus.status='"+WebKeys.TEST_STATUS_SUBMITTED+"' order by phonicGroups.phonicSections.phonicSectionId,phonicGroups.groupOrder");
		} catch (Exception e) {
			logger.error("Error in getStudentPhonicTestMarksByGroupId() of GradePhonicSkillTestDAOImpl"+ e);
			e.printStackTrace();
		}
		return studentPhonicTestMarksLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public  List<BpstTypes>  getBpstTypes() {
		  List<BpstTypes> bpstTypesLt = new ArrayList<BpstTypes>();	
			try{
				bpstTypesLt = (List<BpstTypes>) getHibernateTemplate().find("from BpstTypes");
				} catch (HibernateException ex) {
				ex.printStackTrace();
			}
			return bpstTypesLt;
		}
}
