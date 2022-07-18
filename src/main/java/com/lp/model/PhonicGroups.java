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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "phonic_groups")
public class PhonicGroups implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private long groupId;
	@JsonManagedReference
	private PhonicSections phonicSections;
	private String title;
	private String question;
	private long groupOrder;
	private String status;
	private String directions;
	@JsonBackReference
	private List<BpstGroups> bpstGroups =  new ArrayList<>();
	
	public PhonicGroups() {
		super();
	}
	public PhonicGroups(long groupId, PhonicSections phonicSections, String title,
			String question, String status,String directions,List<BpstGroups> bpstGroups) {
		super();
		this.groupId = groupId;
		this.phonicSections = phonicSections;
		this.title = title;
		this.question = question;
		this.status = status;
		this.directions = directions;
		this.bpstGroups = bpstGroups;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "group_id", unique = true, nullable = false)
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "phonic_section_id")
	public PhonicSections getPhonicSections() {
		return phonicSections;
	}
		
	public void setPhonicSections(PhonicSections phonicSections) {
		this.phonicSections = phonicSections;
	}
	
	@Column(name = "title", nullable = false, length = 40)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "question", nullable = false, length = 100)
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	@Column(name = "group_order")
	public long getGroupOrder() {
		return groupOrder;
	}
	public void setGroupOrder(long groupOrder) {
		this.groupOrder = groupOrder;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "directions")
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "phonicGroups")
	public List<BpstGroups> getBpstGroups() {
		return bpstGroups;
	}
	public void setBpstGroups(List<BpstGroups> bpstGroups) {
		this.bpstGroups = bpstGroups;
	}
	
	

}
