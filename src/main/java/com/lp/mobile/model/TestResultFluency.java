package com.lp.mobile.model;


public class TestResultFluency {
	
	private Integer wcpm;
	private Float accuracy;
	private Integer retell;
	private String fAudioPath;
	private String fRetellPath;
	
	public TestResultFluency() {
	}	

	public TestResultFluency(Integer wcpm, Float accuracy, Integer retell) {
		this.wcpm = wcpm;
		this.accuracy = accuracy;
		this.retell = retell;
	}

	public Integer getWcpm() {
		return wcpm;
	}

	public void setWcpm(Integer wcpm) {
		this.wcpm = wcpm;
	}

	public Float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Float accuracy) {
		this.accuracy = accuracy;
	}

	public Integer getRetell() {
		return retell;
	}

	public void setRetell(Integer retell) {
		this.retell = retell;
	}	
	public String getFAudioPath() {
		return fAudioPath;
	}

	public void setFAudioPath(String fAudioPath) {
		this.fAudioPath = fAudioPath;
	}	
	public String getFRetellPath() {
		return fRetellPath;
	}

	public void setFRetellPath(String fRetellPath) {
		this.fRetellPath = fRetellPath;
	}	
	
}
