
package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stem_paths")
public class StemPaths implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer stemPathId;
	private String stemPathDesc;
	public StemPaths() {
	}

	public StemPaths(Integer stemPathId, String stemPathDesc) {
		this.stemPathId = stemPathId;
		this.stemPathDesc = stemPathDesc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_path_id", unique = true, nullable = false)
	public Integer getStemPathId() {
		return this.stemPathId;
	}

	public void setStemPathId(Integer stemPathId) {
		this.stemPathId = stemPathId;
	}

	@Column(name = "stem_path_desc",length = 500)
	public String getStemPathDesc() {
		return this.stemPathDesc;
	}

	public void setStemPathDesc(String stemPathDesc) {
		this.stemPathDesc = stemPathDesc;
	}
	
	
	
}
