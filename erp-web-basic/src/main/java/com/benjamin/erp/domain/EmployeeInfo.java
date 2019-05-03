package com.benjamin.erp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_EMPLOYEE_INFO")
public class EmployeeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String phone;
	
	private String userType;
	
	private RoleInfo roleInfo;
	
	private Section section;
	
	private boolean inServiceStatus;
	
	private String email;
	
	private Gender gender;
	
	private BigDecimal maxYouFui;
	
	@Column(name = "min_discount")
	private BigDecimal minDiscount;
	
	@Column(name = "min_repair_discount")
	private BigDecimal minRepairDiscount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public boolean isInServiceStatus() {
		return inServiceStatus;
	}

	public void setInServiceStatus(boolean inServiceStatus) {
		this.inServiceStatus = inServiceStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public BigDecimal getMinDiscount() {
		return minDiscount;
	}

	public void setMinDiscount(BigDecimal minDiscount) {
		this.minDiscount = minDiscount;
	}

	public BigDecimal getMinRepairDiscount() {
		return minRepairDiscount;
	}

	public void setMinRepairDiscount(BigDecimal minRepairDiscount) {
		this.minRepairDiscount = minRepairDiscount;
	}

	public BigDecimal getMaxYouFui() {
		return maxYouFui;
	}

	public void setMaxYouFui(BigDecimal maxYouFui) {
		this.maxYouFui = maxYouFui;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public enum InServiceStatus{
		IN_SERVICE,LEAVE,FREEZE
	}
	
	public enum Gender{
		MEN,WOMEN
	}

}
