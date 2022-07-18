package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "read_reg_page_range")
public class ReadRegPageRange implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long readRegPageRangeId;
	private int pagesFrom;
	private int pagesTo;
	private int range;
	
	public ReadRegPageRange() {
		super();
	}

	public ReadRegPageRange(long readRegPageRangeId, int pagesFrom, int pagesTo, int range) {
		super();
		this.readRegPageRangeId = readRegPageRangeId;
		this.pagesFrom = pagesFrom;
		this.pagesTo = pagesTo;
		this.range = range;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "read_reg_page_range_id", nullable = false, length = 20)
	public long getReadRegPageRangeId() {
		return readRegPageRangeId;
	}
	
	public void setReadRegPageRangeId(long readRegPageRangeId) {
		this.readRegPageRangeId = readRegPageRangeId;
	}


	@Column(name = "pages_from", length = 11)
	public int getPagesFrom() {
		return pagesFrom;
	}

	public void setPagesFrom(int pagesFrom) {
		this.pagesFrom = pagesFrom;
	}

	@Column(name = "pages_to", length = 11)
	public int getPagesTo() {
		return pagesTo;
	}

	public void setPagesTo(int pagesTo) {
		this.pagesTo = pagesTo;
	}

	@Column(name = "range", length = 11)
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}	
}
