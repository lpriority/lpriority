package com.lp.common.service;

import java.util.List;

import com.lp.model.BenchmarkResults;
import com.lp.model.Teacher;

public interface BenchmarkRunningRecordService {
  public void create(String name, Integer age);  
  public List<BenchmarkResults> getBenchmarkResults(Teacher teacher, long csId, String usedFor);
}