package com.lp.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "stem_unit")
public class StemUnit implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long stemUnitId;
	private String stemUnitName ;
	private String stemUnitDesc;
	@JsonManagedReference
	private GradeClasses gradeClasses;
	@JsonManagedReference
	private StemPaths stemPaths;
	@JsonManagedReference
	private UserRegistration userRegistration;
	private String stemNotes;
	private String isShared;
	private Long srcStemUnitId;
	private String urlLinks;
	@JsonManagedReference
	List<StemQuestions> stemQuestionsLt = new ArrayList<StemQuestions>();
	@JsonManagedReference
	List<StemUnitPerformanceIndicator> stemUnitPerformanceIndicatorLt = new ArrayList<StemUnitPerformanceIndicator>();
	@JsonManagedReference
	List<UnitStemAreas>  uniStemAreasLt = new ArrayList<UnitStemAreas>();
	@JsonManagedReference
	List<UnitStemStrategies> unitStemStrategiesLt = new ArrayList<UnitStemStrategies>();
	@JsonManagedReference
	private List<FormativeAssessmentsUnitWise> formativeAssessmentsLt = new ArrayList<FormativeAssessmentsUnitWise>(0);


	public StemUnit() {
	}

	public StemUnit(Long stemUnitId,GradeClasses gradeClass,StemPaths stemPaths,UserRegistration userRegistration,String stemUnitName,String stemUnitDesc,Long srcStemUnitId,String urlLinks) {
		this.stemUnitId = stemUnitId;
		this.gradeClasses = gradeClass;
		this.stemPaths=stemPaths;
		this.userRegistration = userRegistration;
		this.stemUnitName = stemUnitName;
		this.stemUnitDesc = stemUnitDesc;
		this.srcStemUnitId = srcStemUnitId;
		this.urlLinks = urlLinks;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_unit_id", unique = true, nullable = false)
	public Long getStemUnitId() {
		return this.stemUnitId;
	}

	public void setStemUnitId(Long stemUnitId) {
		this.stemUnitId = stemUnitId;
	}

	@Column(name = "stem_unit_name", length = 45)
	public String getStemUnitName() {
		return this.stemUnitName;
	}

	public void setStemUnitName(String stemUnitName) {
		this.stemUnitName = stemUnitName;
	}
	
	@Column(name = "stem_unit_desc", length = 500)
	public String getStemUnitDesc() {
		return this.stemUnitDesc;
	}

	public void setStemUnitDesc(String stemUnitDesc) {
		this.stemUnitDesc = stemUnitDesc;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_class_id")
	public GradeClasses getGradeClasses() {
		return gradeClasses;
	}

	public void setGradeClasses(GradeClasses gradeClasses) {
		this.gradeClasses = gradeClasses;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by")
	public UserRegistration getUserRegistration() {
		return userRegistration;
	}

	public void setUserRegistration(UserRegistration userRegistration) {
		this.userRegistration = userRegistration;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_path_id")
	public StemPaths getStemPaths() {
		return stemPaths;
	}

	public void setStemPaths(StemPaths stemPaths) {
		this.stemPaths = stemPaths;
	}
	@Column(name = "stem_notes", length = 500)
	public String getStemNotes() {
		return this.stemNotes;
	}

	public void setStemNotes(String stemNotes) {
		this.stemNotes = stemNotes;
	}
	@Column(name = "is_shared", length = 50)
	public String getIsShared() {
		return isShared;
	}
	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}
	
	@Column(name = "src_stem_unit_id", length = 20)
	public Long getSrcStemUnitId() {
		return srcStemUnitId;
	}

	public void setSrcStemUnitId(Long srcStemUnitId) {
		this.srcStemUnitId = srcStemUnitId;
	}
	
	@Column(name = "url_links", length = 1000)
	public String getUrlLinks() {
		return urlLinks;
	}

	public void setUrlLinks(String urlLinks) {
		this.urlLinks = urlLinks;
	}

	@OneToMany(mappedBy = "stemUnit", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<StemQuestions> getStemQuestionsLt() {
		return stemQuestionsLt;
	}

	public void setStemQuestionsLt(List<StemQuestions> stemQuestionsLt) {
		this.stemQuestionsLt = stemQuestionsLt;
	}
	@OneToMany(mappedBy = "stemUnit", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<StemUnitPerformanceIndicator> getStemUnitPerformanceIndicatorLt() {
		return stemUnitPerformanceIndicatorLt;
	}

	public void setStemUnitPerformanceIndicatorLt(
			List<StemUnitPerformanceIndicator> stemUnitPerformanceIndicatorLt) {
		this.stemUnitPerformanceIndicatorLt = stemUnitPerformanceIndicatorLt;
	}
	
	@OneToMany(mappedBy = "stemUnit", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<UnitStemAreas> getUniStemAreasLt() {
		return uniStemAreasLt;
	}

	public void setUniStemAreasLt(List<UnitStemAreas> uniStemAreasLt) {
		this.uniStemAreasLt = uniStemAreasLt;
	}
	@OneToMany(mappedBy = "stemUnit", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<UnitStemStrategies> getUnitStemStrategiesLt() {
		return unitStemStrategiesLt;
	}

	public void setUnitStemStrategiesLt(
			List<UnitStemStrategies> unitStemStrategiesLt) {
		this.unitStemStrategiesLt = unitStemStrategiesLt;
	}

	@OneToMany(mappedBy = "stemUnit", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<FormativeAssessmentsUnitWise> getFormativeAssessmentsLt() {
		return formativeAssessmentsLt;
	}

	public void setFormativeAssessmentsLt(
			List<FormativeAssessmentsUnitWise> formativeAssessmentsLt) {
		this.formativeAssessmentsLt = formativeAssessmentsLt;
	}
	
}
