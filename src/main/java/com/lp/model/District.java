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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
* District generated by hbm2java
*/
@Entity
@Table(name = "district")
public class District implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long districtId;
	@JsonManagedReference
	private States states;
	private String districtName;
	private long noSchools;
	private String logoLink;
	private String address;
	private String city;
	private String phoneNumber;
	private String faxNumber;
	@Transient
	private String countryId;
	@Transient
	private String stateId;

	public District() {
	}

	public District(String districtName, String address, String city) {
		this.districtName = districtName;
		this.address = address;
		this.city = city;
	}

	public District(States states,String districtName,
			long noSchools, String logoLink, String address, String city,
			String phoneNumber, String faxNumber) {
		this.states = states;
		this.districtName = districtName;
		this.noSchools = noSchools;
		this.logoLink = logoLink;
		this.address = address;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "district_id", unique = true, nullable = false)
	public long getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public States getStates() {
		return this.states;
	}

	public void setStates(States states) {
		this.states = states;
	}

	@Column(name = "district_name", nullable = false, length = 30)
	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Column(name = "no_schools")
	public long getNoSchools() {
		return this.noSchools;
	}

	public void setNoSchools(long noSchools) {
		this.noSchools = noSchools;
	}

	@Column(name = "logo_link", length = 50)
	public String getLogoLink() {
		return this.logoLink;
	}

	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}

	@Column(name = "address", nullable = false, length = 30)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", nullable = false, length = 30)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "phone_number", length = 30)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "fax_number", length = 30)
	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	@Transient
	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	@Transient
	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

}