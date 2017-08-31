package com.dxc.dao;

import java.util.List;

import com.dxc.model.TestCaseDTO;

public interface TestCaseDAO {
	
	/**
	 * This function save or update a testcase to database
	 * @param tc
	 */
	public void saveOrUpdate(TestCaseDTO testCase);
	
	/**
	 * This function get all testcase in database
	 * @return
	 */
	public List<TestCaseDTO> getAll();
	
	/**
	 * 
	 */
	
	public List<TestCaseDTO> getDataFourWeek(String projectName);
	
	
	public List<TestCaseDTO> getTestCaseOfProject(String idProject);
	
}
