package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "asstype_readtype_relation")
/*@DataTransferObject(type = "bean", javascript = "country")*/
public class AsstypeReadtypeRelation implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long asstypeReadtypeRelationId;
	@JsonManagedReference
	private AssignmentType assignmentType;
	@JsonManagedReference
	private ReadingTypes readingTypes;

	public AsstypeReadtypeRelation() {
	}

	public AsstypeReadtypeRelation(long asstypeReadtypeRelationId,
			AssignmentType assignmentType, ReadingTypes readingTypes) {
		this.asstypeReadtypeRelationId = asstypeReadtypeRelationId;
		this.assignmentType = assignmentType;
		this.readingTypes = readingTypes;
	}

	@RemoteProperty
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "asstype_readtype_relation_id", unique = true, nullable = false)
	public long getAsstypeReadtypeRelationId() {
		return this.asstypeReadtypeRelationId;
	}

	public void setAsstypeReadtypeRelationId(long asstypeReadtypeRelationId) {
		this.asstypeReadtypeRelationId = asstypeReadtypeRelationId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assignment_type_id")
	public AssignmentType getAssignmentType() {
		return this.assignmentType;
	}

	public void setAssignmentType(AssignmentType assignmentType) {
		this.assignmentType = assignmentType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reading_types_id")
	public ReadingTypes getReadingTypes() {
		return this.readingTypes;
	}

	public void setReadingTypes(ReadingTypes readingTypes) {
		this.readingTypes = readingTypes;
	}
}