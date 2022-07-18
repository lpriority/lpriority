package com.lp.model;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "bm_ss_cut_off_marks")
public class BenchmarkCutOffMarks implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private long id;
	@JsonManagedReference
	private Grade grade;
	@JsonManagedReference
	private BenchmarkCategories benchmarkCategories;
	private Integer fluencyCutOff;
	private Integer retellCutOff;
	
	public BenchmarkCutOffMarks() {
	}

	public BenchmarkCutOffMarks(long id, Grade grade, BenchmarkCategories benchmarkCategories,
			Integer fluencyCutOff, Integer retellCutOff)
		{
		this.id = id;
		this.grade = grade;
		this.benchmarkCategories = benchmarkCategories;
		this.fluencyCutOff = fluencyCutOff;
		this.retellCutOff = retellCutOff;
		
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id", nullable = false )
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bm_category_id", nullable = false)
	public BenchmarkCategories getBenchmarkCategories() {
		return this.benchmarkCategories;
	}

	public void setBenchmarkCategories(BenchmarkCategories benchmarkCategories) {
		this.benchmarkCategories = benchmarkCategories;
	}

	
	@Column(name = "fluency_cut_off", nullable = false)
	public Integer getFluencyCutOff() {
		return this.fluencyCutOff;
	}

	public void setFluencyCutOff(Integer fluencyCutOff) {
		this.fluencyCutOff = fluencyCutOff;
	}

	@Column(name = "retell_cut_off", nullable = false)
	public Integer getRetellCutOff() {
		return this.retellCutOff;
	}

	public void setRetellCutOff(Integer retellCutOff) {
		this.retellCutOff = retellCutOff;
	}

}
