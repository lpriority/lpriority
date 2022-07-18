package com.lp.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.BenchmarkCutOffMarksDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.BenchmarkCategories;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkDirections;


public class BenchmarkCutOffMarkServiceImpl implements BenchmarkCutOffMarksService {

	@Autowired
	private BenchmarkCutOffMarksDAO benchmarkCutOffMarksDao;	
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	
	
	static final Logger logger = Logger.getLogger(BenchmarkCutOffMarkServiceImpl.class);
	
	@Override
	public List<BenchmarkCategories> getBenchmarkCategories() throws DataException {
		List<BenchmarkCategories> benchmarkCategories = new ArrayList<BenchmarkCategories>();
		try{
			benchmarkCategories = benchmarkCutOffMarksDao.getBenchmarkCategories();
			
		}catch(DataException e){
			logger.error("Error in getLessonsByUnitId() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getLessonsByUnitId() of AssessmentServiceImpl",e);
		}
		return benchmarkCategories;
	}
	@Override
	public List<BenchmarkCutOffMarks> getBenchmarkCutOffMarks(long gradeId) throws DataException {
		List<BenchmarkCutOffMarks> benchmarkCutOffMarks = new ArrayList<BenchmarkCutOffMarks>();
		try{
			benchmarkCutOffMarks = benchmarkCutOffMarksDao.getBenchmarkCutOffMarks(gradeId);
			
		}catch(DataException e){
			logger.error("Error in getBenchmarkCutOffMarks() of  BenchmarkCutOffMarkServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getBenchmarkCutOffMarks() of BenchmarkCutOffMarkServiceImpl",e);
		}
		return benchmarkCutOffMarks;
	}
	@Override
	 public BenchmarkCategories getbenchmarkCategories(long benchmarkCategoryId){
		 return benchmarkCutOffMarksDao.getbenchmarkCategories(benchmarkCategoryId);
	 }
	@Override
	public boolean setBenchmarkCutOff(List<BenchmarkCutOffMarks> benchmarkCutOffs){
		return benchmarkCutOffMarksDao.setBenchmarkCutOff(benchmarkCutOffs);
	}
	@Override
	public boolean checkBenchmarkCutOffExists(long gradeId){
		return benchmarkCutOffMarksDao.checkBenchmarkCutOffExists(gradeId);
	}
	@Override
	public List<BenchmarkCategories> getMainBenchmarkTestTypes() throws DataException {
		List<BenchmarkCategories> benchmarkCategories = new ArrayList<BenchmarkCategories>();
		try{
			benchmarkCategories = benchmarkCutOffMarksDao.getMainBenchmarkTestTypes();
			
		}catch(DataException e){
			logger.error("Error in getMainBenchmarkTestTypes() of  BenchmarkCutOffMarkServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getMainBenchmarkTestTypes() of BenchmarkCutOffMarkServiceImpl",e);
		}
		return benchmarkCategories;
	}
	@Override
	public List<BenchmarkDirections> getBenchmarkDirections() throws DataException {
		List<BenchmarkDirections> benchmarkDirections = new ArrayList<BenchmarkDirections>();
		try{
			benchmarkDirections = benchmarkCutOffMarksDao.getBenchmarkDirections();
			
		}catch(DataException e){
			logger.error("Error in getBenchmarkDirections() of  BenchmarkCutOffMarkServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getBenchmarkDirections() of BenchmarkCutOffMarkServiceImpl",e);
		}
		return benchmarkDirections;
	}
}