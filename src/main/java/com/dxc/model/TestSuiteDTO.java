package com.dxc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "testsuite")
public class TestSuiteDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "TestSuiteName")
	private String name;
	
	@OneToMany(mappedBy = "testSuite", cascade = CascadeType.ALL)
	@JsonIgnore
	List<TestCaseDTO> testCases;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "projectid", nullable = false)
	@JsonIgnore
	private ProjectDTO project;
	
	@Column(name = "RunDate")
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

	public List<TestCaseDTO> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCaseDTO> testCases) {
		this.testCases = testCases;
	}



	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
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
