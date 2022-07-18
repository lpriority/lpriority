package com.lp.common.service;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.BenchmarkCategories;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkDirections;

public interface BenchmarkCutOffMarksService {
  public List<BenchmarkCategories> getBenchmarkCategories();
  public List<BenchmarkCutOffMarks> getBenchmarkCutOffMarks(long gradeId)throws DataException;
  public BenchmarkCategories getbenchmarkCategories(long benchmarkCategoryId);
  public boolean setBenchmarkCutOff(List<BenchmarkCutOffMarks> benchmarkCutOffs);
  public boolean checkBenchmarkCutOffExists(long gradeId);
  public List<BenchmarkCategories> getMainBenchmarkTestTypes();
  public List<BenchmarkDirections> getBenchmarkDirections();
}
