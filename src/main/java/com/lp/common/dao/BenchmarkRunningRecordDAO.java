package com.lp.common.dao;

import java.util.List;

import com.lp.model.BenchmarkResults;
import com.lp.model.Teacher;

public interface BenchmarkRunningRecordDAO {
   /** 
    * This is the method to be used to create
    * a record in the Student table.
    */
  public void create(String name, Integer age);
  public List<BenchmarkResults> getBenchmarkResults(Teacher teacher, long csId, String usedFor);
  
}