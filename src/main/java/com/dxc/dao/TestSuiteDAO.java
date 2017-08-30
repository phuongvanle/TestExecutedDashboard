package com.dxc.dao;

import java.util.List;

import com.dxc.model.TestSuiteDTO;

public interface TestSuiteDAO {
	
	public void saveOrUpdate(TestSuiteDTO testSuite);
	
	public List<TestSuiteDTO> getAll();
	
}
