package com.dxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.dao.TestCaseDAO;
import com.dxc.model.TestCaseDTO;

@Service
public class TestCaseServiceImpl implements TestCaseService {
	
	TestCaseDAO testCaseDao;
	
	public TestCaseDAO getTestCaseDao() {
		return testCaseDao;
	}

	@Autowired
	public void setTestCaseDao(TestCaseDAO testCaseDao) {
		this.testCaseDao = testCaseDao;
	}

	@Override
	public void saveOrUpdate(TestCaseDTO testCase) {
		testCaseDao.saveOrUpdate(testCase);
	}

	@Override
	public List<TestCaseDTO> getAll() {
		return testCaseDao.getAll();
	}

	@Override
	public List<TestCaseDTO> getTestCaseFourWeek(String projectName) {
		return testCaseDao.getDataFourWeek(projectName);
	}
}
