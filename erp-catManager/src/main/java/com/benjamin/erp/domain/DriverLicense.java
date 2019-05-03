package com.benjamin.erp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.benjamin.erp.Gender;

@Entity
@Table(name = "TAB_DRIVER_LICENSE")
public class DriverLicense implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Gender sex;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "date_of_first_issue")
	private Date dateofFirstIssue;
	
	@Column(name = "vehicle_type")
	private String vehicleType;
	
	@ManyToOne
	@JoinColumn(name = "prositive_page_resource_id")
	private ResourceInfo positivePage;
	
	@ManyToOne
	@JoinColumn(name = "auxiliary_page_resource_id")
	private ResourceInfo auxiliaryPage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateofFirstIssue() {
		return dateofFirstIssue;
	}

	public void setDateofFirstIssue(Date dateofFirstIssue) {
		this.dateofFirstIssue = dateofFirstIssue;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public ResourceInfo getPositivePage() {
		return positivePage;
	}

	public void setPositivePage(ResourceInfo positivePage) {
		this.positivePage = positivePage;
	}

	public ResourceInfo getAuxiliaryPage() {
		return auxiliaryPage;
	}

	public void setAuxiliaryPage(ResourceInfo auxiliaryPage) {
		this.auxiliaryPage = auxiliaryPage;
	}

	
}
