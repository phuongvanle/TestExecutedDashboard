package com.dxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "testcase")
public class TestCaseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int Failed = 1, Passed = 2, Unknown = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "TestCaseName")
	private String name;
	@Column(name = "TestCaseStatus")
	private int status;
	@Column(name = "RunDate")
	private String date;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="TestSuite_id", nullable = false)
	private TestSuiteDTO testSuite;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	public TestSuiteDTO getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuiteDTO testSuite) {
		this.testSuite = testSuite;
	}

	@Override
	public String toString() {
		return "TestCase [id=" + id + ", name=" + name + ", status=" + status + ", date=" + date + "]";
	}
	
	
}
