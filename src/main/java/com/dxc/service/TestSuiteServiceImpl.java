package com.dxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.dao.TestSuiteDAO;
import com.dxc.model.TestSuiteDTO;

@Service
public class TestSuiteServiceImpl implements TestSuiteService {
	
	TestSuiteDAO testSuiteDao;
	
	public TestSuiteDAO getTestSuiteDao() {
		return testSuiteDao;
	}
	
	@Autowired
	public void setTestSuiteDao(TestSuiteDAO testSuiteDao) {
		this.testSuiteDao = testSuiteDao;
	}

	@Override
	public void saveOrUpdate(TestSuiteDTO testSuite) {
		testSuiteDao.saveOrUpdate(testSuite);
	}

	@Override
	public List<TestSuiteDTO> getAll() {
		return testSuiteDao.getAll();
	}

}
