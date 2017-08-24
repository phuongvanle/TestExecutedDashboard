package com.dxc.model;

import java.util.List;

public class TestSuite {
	private int id;
	private String name;
	List<TestCase> testCases;
	
	
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
	public List<TestCase> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
	
	

}
