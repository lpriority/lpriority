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

@Entity
@Table(name = "formative_assessment_column_headers")
public class FormativeAssessmentsColumnHeaders implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private long formativeAssessmentsColumnHeadersId;
	private FormativeAssessments formativeAssessments;
	private ColumnHeaders columnHeaders;
	
	public FormativeAssessmentsColumnHeaders() {
		super();
	}

	public FormativeAssessmentsColumnHeaders(
			long formativeAssessmentsColumnHeadersId,
			FormativeAssessments formativeAssessments,
			ColumnHeaders columnHeaders) {
		super();
		this.formativeAssessmentsColumnHeadersId = formativeAssessmentsColumnHeadersId;
		this.formativeAssessments = formativeAssessments;
		this.columnHeaders = columnHeaders;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessment_column_headers_id", unique = true, nullable = false)
	public long getFormativeAssessmentsColumnHeadersId() {
		return formativeAssessmentsColumnHeadersId;
	}

	public void setFormativeAssessmentsColumnHeadersId(
			long formativeAssessmentsColumnHeadersId) {
		this.formativeAssessmentsColumnHeadersId = formativeAssessmentsColumnHeadersId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "formative_assessment_id")
	public FormativeAssessments getFormativeAssessments() {
		return formativeAssessments;
	}

	public void setFormativeAssessments(FormativeAssessments formativeAssessments) {
		this.formativeAssessments = formativeAssessments;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "column_header_id")
	public ColumnHeaders getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(ColumnHeaders columnHeaders) {
		this.columnHeaders = columnHeaders;
	}
	
	

}
