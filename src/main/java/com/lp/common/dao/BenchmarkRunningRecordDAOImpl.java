package com.lp.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lp.login.controller.DBConnection;
import com.lp.model.BenchmarkResults;
import com.lp.model.Teacher;
import com.lp.utils.CustomHibernateDaoSupport;

public class BenchmarkRunningRecordDAOImpl extends CustomHibernateDaoSupport implements BenchmarkRunningRecordDAO  {

	public void create(String name, Integer age) {
	Connection conn = null;
		String SQL = "insert into Student (name, age) values (?, ?)";	
		try {
			conn = DBConnection.getConnection();
			PreparedStatement ps =  conn.prepareStatement(SQL);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BenchmarkResults> getBenchmarkResults(Teacher teacher, long csId, String usedFor){
		
		List<BenchmarkResults> benchmarkResults = new ArrayList<BenchmarkResults>();
		try{
			benchmarkResults =(List<BenchmarkResults>) getHibernateTemplate().find("from BenchmarkResults where studentAssignmentStatus.assignment.classStatus.csId="+csId+" and "
				 		+ "studentAssignmentStatus.assignment.usedFor='"+usedFor+"' and studentAssignmentStatus.gradedStatus='graded' order by (medianFluencyScore+medianRetellScore) ASC");
		}catch (DataAccessException e) {
            logger.error("Error in getBenchmarkResults() of TeacherDAOImpl"
                    + e);
            e.printStackTrace();
        }
		@SuppressWarnings("unused")
		Connection conn = null;
		conn = DBConnection.getConnection();
		@SuppressWarnings("unused")
		String SQL = "SELECT a.median_fulency_core, a.median_retell_score, c.benchmark_id "
				+ "FROM edulink1_sample.rti_results a, student_assignment_status b, assignment c "
				+ "where a.student_assignment_id= b.student_assignment_id and "
				+ "b.assignment_id = c.assignment_id and b.studentreg_id=? and c.benchmark_id=?";				
		return benchmarkResults;
	 }
}