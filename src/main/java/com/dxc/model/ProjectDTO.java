package com.dxc.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "projects")
public class ProjectDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "ProjectName", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TestSuiteDTO> testSuites;

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

	public List<TestSuiteDTO> getTestSuites() {
		return testSuites;
	}

	public void setTestSuites(List<TestSuiteDTO> testSuites) {
		this.testSuites = testSuites;
	}
}
