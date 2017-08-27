package com.dxc.model;

import java.util.Date;
import java.util.List;

public class TestSuite {

	private int id;

	private String name;

	List<TestCase> testCases;

	private String date;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ");
		sb.append(this.id);
		sb.append(" Name TestSuite: ");
		sb.append(this.name);
		sb.append(" Run Date: ");
		sb.append(this.date);
		sb.append(" {\n");
		for (int i = 0; i < this.testCases.size(); i++) {
			sb.append(testCases.get(i).toString());
			sb.append("\n");
		}
		sb.append("}");
		return sb.toString();
	}
	
	

}
