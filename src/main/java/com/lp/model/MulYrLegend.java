
package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "mulyr_legend")
public class MulYrLegend implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long mulYrLegendId;
	private String mulYrLegendName;
	private String mulYrLegendColor;
		
	public MulYrLegend() {
	}

	public MulYrLegend(long mulYrLegendId) {
		this.mulYrLegendId = mulYrLegendId;
	}

	public MulYrLegend(long mulYrLegendId, String mulYrLegendName,String mulYrLegendColor) {
		this.mulYrLegendId = mulYrLegendId;
		this.mulYrLegendName = mulYrLegendName;
		this.mulYrLegendColor=mulYrLegendColor;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mulyr_legend_id", unique = true, nullable = false)
	public long getMulYrLegendId() {
		return this.mulYrLegendId;
	}

	public void setMulYrLegendId(long mulYrLegendId) {
		this.mulYrLegendId = mulYrLegendId;
	}

	@Column(name = "mulyr_legend_name", length = 40)
	public String getMulYrLegendName() {
		return this.mulYrLegendName;
	}

	public void setMulYrLegendName(String mulYrLegendName) {
		this.mulYrLegendName = mulYrLegendName;
	}

	@Column(name = "mulyr_legend_color")
	public String getMulYrLegendColor() {
		return mulYrLegendColor;
	}

	public void setMulYrLegendColor(String mulYrLegendColor) {
		this.mulYrLegendColor = mulYrLegendColor;
	}
	
}
