package com.lp.model;

// Generated Apr 15, 2015 3:45:41 PM by Hibernate Tools 4.0.0

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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * JacTemplate generated by hbm2java
 */
@Entity
@Table(name = "jac_template")
public class JacTemplate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4943232267399238947L;
	private Long jacTemplateId;
	@JsonManagedReference
	private JacQuestionFile jacQuestionFile;
	private long noOfQuestions;
	private String titleName;
	@Transient
	private List<Questions> questionsList =  new ArrayList<>();
	
	public JacTemplate() {
	}

	public JacTemplate(JacQuestionFile jacQuestionFile, long noOfQuestions,
			String titleName) {
		this.jacQuestionFile = jacQuestionFile;
		this.noOfQuestions = noOfQuestions;
		this.titleName = titleName;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "jac_template_id", unique = true, nullable = false)
	public Long getJacTemplateId() {
		return this.jacTemplateId;
	}

	public void setJacTemplateId(Long jacTemplateId) {
		this.jacTemplateId = jacTemplateId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="jac_question_file_id")
	public JacQuestionFile getJacQuestionFile() {
		return this.jacQuestionFile;
	}

	public void setJacQuestionFile(JacQuestionFile jacQuestionFile) {
		this.jacQuestionFile = jacQuestionFile;
	}
	@Column(name = "no_of_questions")
	public long getNoOfQuestions() {
		return this.noOfQuestions;
	}

	public void setNoOfQuestions(long noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}
	@Column(name = "title_name")
	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}	
	@Transient
	public List<Questions> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<Questions> questionsList) {
		this.questionsList = questionsList;
	}

}