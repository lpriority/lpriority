package com.lp.mobile.model;
import java.util.ArrayList;
import java.util.List;

public class StarScoresReports {
	
	/*private String caasppType;
	private String trimesterName;*/
	private long caasppTypeId;
	private long trimesterId;
	private Float starScore;
	private List<String> starStrategies=new ArrayList<String>();
	
	
	public StarScoresReports() {
	}	

	 public StarScoresReports(long caasppTypeId, long trimesterId, Float starScore,List<String> starStrategies) {
		this.caasppTypeId = caasppTypeId;
		this.trimesterId = trimesterId;
		this.starScore = starScore;
		this.starStrategies=starStrategies;
	}

	public long getCaasppTypeId() {
		return caasppTypeId;
	}

	public void setCaasppTypeId(long caasppTypeId) {
		this.caasppTypeId = caasppTypeId;
	}

	public long getTrimesterId() {
		return trimesterId;
	}

	public void setTrimesterId(long trimesterId) {
		this.trimesterId = trimesterId;
	}

	public Float getStarScore() {
		return starScore;
	}

	public void setStarScore(Float starScore) {
		this.starScore = starScore;
	}	
	public List<String> getStarStrategies() {
		return starStrategies;
	}

	public void setStarStrategies(List<String> starStrategies) {
		this.starStrategies = starStrategies;
	}
	
}
