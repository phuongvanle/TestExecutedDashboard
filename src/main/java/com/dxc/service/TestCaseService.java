package com.dxc.service;

import java.util.List;

import com.dxc.model.TestCaseDTO;

public interface TestCaseService {
	
	
	/**
	 * this method save or update test case to database
	 * @param testCase
	 */
	public void saveOrUpdate(TestCaseDTO testCase);
	
	/**
	 * This method get all test case in database
	 * @return
	 */
	public List<TestCaseDTO> getAll();
	
	/**
	 * 
	 */
	
	public List<TestCaseDTO> getTestCaseFourWeek(String projectName);

}
