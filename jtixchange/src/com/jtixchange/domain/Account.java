package com.jtixchange.domain;

import java.io.Serializable;


public class Account implements Serializable {

	/* Private Fields */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String status;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	private String favouriteCategoryId;
	private String languagePreference;
	private boolean listOption;
	private boolean bannerOption;
	private String bannerName;
	private String signOnType;

	/* JavaBeans Properties */

	public String getAddress1() {
		return this.address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public String getBannerName() {
		return this.bannerName;
	}

	public String getCity() {
		return this.city;
	}

	public String getCountry() {
		return this.country;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFavouriteCategoryId() {
		return this.favouriteCategoryId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLanguagePreference() {
		return this.languagePreference;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getSignOnType() {
		return this.signOnType;
	}

	public String getState() {
		return this.state;
	}

	public String getStatus() {
		return this.status;
	}

	public String getUsername() {
		return this.username;
	}

	public String getZip() {
		return this.zip;
	}

	public boolean isBannerOption() {
		return this.bannerOption;
	}

	public boolean isListOption() {
		return this.listOption;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public void setBannerOption(boolean bannerOption) {
		this.bannerOption = bannerOption;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFavouriteCategoryId(String favouriteCategoryId) {
		this.favouriteCategoryId = favouriteCategoryId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setListOption(boolean listOption) {
		this.listOption = listOption;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSignOnType(String signOnType) {
		this.signOnType = signOnType;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
