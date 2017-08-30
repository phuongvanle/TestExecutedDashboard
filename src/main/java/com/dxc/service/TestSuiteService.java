package com.dxc.service;

import java.util.List;

import com.dxc.dao.TestSuiteDAO;
import com.dxc.model.TestSuiteDTO;

public interface TestSuiteService {
	
	
	public void saveOrUpdate(TestSuiteDTO testSuite);
	
	public List<TestSuiteDTO> getAll();
	
	

}
