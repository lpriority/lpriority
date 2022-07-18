package com.lp.model;

// Generated May 28, 2015 3:44:36 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * GroupPerformanceTemp generated by hbm2java
 */
@Entity
@Table(name = "group_performance_temp")
public class GroupPerformanceTemp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private GroupPerformanceTempId id;
	@JsonManagedReference
	private PerformanceGroupStudents performanceGroupStudents;
	@JsonManagedReference
	private AssignedPtasks assignedPtasks;
	private String writingAreaStatus;
	private String imageAreaStatus;
	private String calculationAreaStatus;
	private Long dim1Value;
	private Long dim2Value;
	private Long dim3Value;
	private Long dim4Value;
	private Long total;
	private String chatLoginStatus;
	private String permissionStatus;
	

	public GroupPerformanceTemp() {
	}

	public GroupPerformanceTemp(GroupPerformanceTempId id,
			PerformanceGroupStudents performanceGroupStudents,AssignedPtasks assignedPtasks) {
		this.id = id;
		this.performanceGroupStudents = performanceGroupStudents;
		this.assignedPtasks = assignedPtasks;
	}

	public GroupPerformanceTemp(GroupPerformanceTempId id,
			PerformanceGroupStudents performanceGroupStudents,
			AssignedPtasks assignedPtasks, String writingAreaStatus,
			String imageAreaStatus, String calculationAreaStatus,
			Long dim1Value, Long dim2Value, Long dim3Value, Long dim4Value,
			Long total, String chatLoginStatus, String permissionStatus) {
		this.id = id;
		this.performanceGroupStudents = performanceGroupStudents;
		this.assignedPtasks = assignedPtasks;
		this.writingAreaStatus = writingAreaStatus;
		this.imageAreaStatus = imageAreaStatus;
		this.calculationAreaStatus = calculationAreaStatus;
		this.dim1Value = dim1Value;
		this.dim2Value = dim2Value;
		this.dim3Value = dim3Value;
		this.dim4Value = dim4Value;
		this.total = total;
		this.chatLoginStatus = chatLoginStatus;
		this.permissionStatus = permissionStatus;
	}
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "performanceGroupStudentsId", column = @Column(name = "performance_group_students_id", nullable = false)),
			@AttributeOverride(name = "assignedTaskId", column = @Column(name = "assigned_task_id", nullable = false)) })
	public GroupPerformanceTempId getId() {
		return this.id;
	}

	public void setId(GroupPerformanceTempId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "performance_group_students_id", nullable = false,  insertable = false, updatable = false)
	public PerformanceGroupStudents getPerformanceGroupStudents() {
		return this.performanceGroupStudents;
	}

	public void setPerformanceGroupStudents(PerformanceGroupStudents performanceGroupStudents) {
		this.performanceGroupStudents = performanceGroupStudents;
	}	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assigned_task_id", nullable = false, insertable = false, updatable = false)
	public AssignedPtasks getAssignedPtasks() {
		return assignedPtasks;
	}

	public void setAssignedPtasks(AssignedPtasks assignedPtasks) {
		this.assignedPtasks = assignedPtasks;
	}

	@Column(name = "writing_area_status", length = 20)
	public String getWritingAreaStatus() {
		return this.writingAreaStatus;
	}

	public void setWritingAreaStatus(String writingAreaStatus) {
		this.writingAreaStatus = writingAreaStatus;
	}

	@Column(name = "image_area_status", length = 20)
	public String getImageAreaStatus() {
		return this.imageAreaStatus;
	}

	public void setImageAreaStatus(String imageAreaStatus) {
		this.imageAreaStatus = imageAreaStatus;
	}

	@Column(name = "calculation_area_status", length = 20)
	public String getCalculationAreaStatus() {
		return this.calculationAreaStatus;
	}

	public void setCalculationAreaStatus(String calculationAreaStatus) {
		this.calculationAreaStatus = calculationAreaStatus;
	}

	@Column(name = "dim1_value")
	public Long getDim1Value() {
		return this.dim1Value;
	}

	public void setDim1Value(Long dim1Value) {
		this.dim1Value = dim1Value;
	}

	@Column(name = "dim2_value")
	public Long getDim2Value() {
		return this.dim2Value;
	}

	public void setDim2Value(Long dim2Value) {
		this.dim2Value = dim2Value;
	}

	@Column(name = "dim3_value")
	public Long getDim3Value() {
		return this.dim3Value;
	}

	public void setDim3Value(Long dim3Value) {
		this.dim3Value = dim3Value;
	}

	@Column(name = "dim4_value")
	public Long getDim4Value() {
		return this.dim4Value;
	}

	public void setDim4Value(Long dim4Value) {
		this.dim4Value = dim4Value;
	}

	@Column(name = "total")
	public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Column(name = "chat_login_status")
	public String getChatLoginStatus() {
		return this.chatLoginStatus;
	}

	public void setChatLoginStatus(String chatLoginStatus) {
		this.chatLoginStatus = chatLoginStatus;
	}
	
	@Column(name = "permission_status")
	public String getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(String permissionStatus) {
		this.permissionStatus = permissionStatus;
	}
}