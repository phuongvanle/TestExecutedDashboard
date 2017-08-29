package com.dxc.model;

import java.util.Date;

public class RunTestSuite {

	private int id;

	private TestSuiteDTO testSuite;

	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestSuiteDTO getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuiteDTO testSuite) {
		this.testSuite = testSuite;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
