package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bm_categories")
public class BenchmarkCategories  implements java.io.Serializable{

	
	private static final long serialVersionUID = 1L;
	private long benchmarkCategoryId;
	private String benchmarkCategory;
	private String benchmarkName;
	private Integer isFluencyTest; // For checking if the test is a fluency test and not practice or progressmonitoring. FOr practice or progressmonitoring the value would be zero and for fluency tests the value would be one.
	
	public BenchmarkCategories() {
	}

	public BenchmarkCategories(long benchmarkCategoryId,
			String benchmarkCategory, String benchmarkName, Integer isFluencyTest)
	 {
		this.benchmarkCategoryId = benchmarkCategoryId;
		this.benchmarkCategory = benchmarkCategory;
		this.benchmarkName = benchmarkName;
		this.isFluencyTest = isFluencyTest; 
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bm_category_id", unique = true, nullable = false)
	public long getBenchmarkCategoryId() {
		return this.benchmarkCategoryId;
	}

	public void setBenchmarkCategoryId(long benchmarkCategoryId) {
		this.benchmarkCategoryId = benchmarkCategoryId;
	}

	@Column(name = "bm_category", nullable = false, length = 30)
	public String getBenchmarkCategory() {
		return this.benchmarkCategory;
	}

	public void setBenchmarkCategory(String benchmarkCategory) {
		this.benchmarkCategory = benchmarkCategory;
	}

	@Column(name = "bm_name", nullable = false, length = 30)
	public String getBenchmarkName() {
		return this.benchmarkName;
	}

	public void setBenchmarkName(String benchmarkName) {
		this.benchmarkName = benchmarkName;
	}
	
	@Column(name = "is_fluency_test", nullable = false, length = 11)
	public Integer getIsFluencyTest() {
		return isFluencyTest;
	}

	public void setIsFluencyTest(Integer isFluencyTest) {
		this.isFluencyTest = isFluencyTest;
	}

	
}
