package com.lp.common.service;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.BenchmarkRunningRecordDAO;
import com.lp.model.BenchmarkResults;
import com.lp.model.Teacher;


public class BenchmarkRunningRecordServiceImpl implements BenchmarkRunningRecordService {
   
	static final Logger logger = Logger.getLogger(BenchmarkRunningRecordServiceImpl.class);

	@Autowired
	private BenchmarkRunningRecordDAO benchmarkRunningRecordDAO;
	
	public void create(String name, Integer age) {
		benchmarkRunningRecordDAO.create(name, age);
	}
	@Override
	public List<BenchmarkResults> getBenchmarkResults(Teacher teacher, long csId, String usedFor){
		List<BenchmarkResults> assignments=Collections.emptyList();
		try{
			assignments= benchmarkRunningRecordDAO.getBenchmarkResults(teacher, csId,usedFor);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return assignments;
	}
}