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
@Table(name = "phonic_sections")
public class PhonicSections implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private long phonicSectionId;
	private String name;
	@JsonManagedReference
	private Language language;
	
	
	public PhonicSections() {
		super();
	}
	public PhonicSections(long phonicSectionId, String name) {
		super();
		this.phonicSectionId = phonicSectionId;
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "phonic_section_id", unique = true, nullable = false)
	public long getPhonicSectionId() {
		return phonicSectionId;
	}
	public void setPhonicSectionId(long phonicSectionId) {
		this.phonicSectionId = phonicSectionId;
	}
	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_id")
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}


}
