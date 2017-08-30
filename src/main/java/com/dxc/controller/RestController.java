package com.dxc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dxc.dao.TestSuiteDAO;
import com.dxc.model.AreaGraphChartModel;
import com.dxc.model.PieChartModel;
import com.dxc.model.ProjectDTO;
import com.dxc.model.TestCaseDTO;
import com.dxc.model.TestSuiteDTO;
import com.dxc.service.ProjectService;
import com.dxc.service.TestCaseService;
import com.dxc.service.TestSuiteService;
import com.dxc.util.FitnessUtil;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	TestSuiteService testSuiteService;
	@Autowired
	TestCaseService testCaseService;
	@Autowired
	ProjectService projectService;

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/showall/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<List<TestSuiteDTO>> showAllTestSuite(@PathVariable String projectName) {
		String contextPath = "http://localhost:8083";
		return FitnessUtil.createAllTestSuiteRunWithDate(contextPath, projectName);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/pieChartData/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<PieChartModel> getDatePieChart(@PathVariable String projectName) {
		return FitnessUtil.createPieChartData("http://localhost:8083", projectName);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/areaChartData/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<AreaGraphChartModel> getAreaGraphChartData(@PathVariable String projectName) {
		return FitnessUtil.createAreaGraphData("http://localhost:8083", projectName);
	}

	@RequestMapping(value = "/areaChartDatabase/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<AreaGraphChartModel> getAreaGraphFromDatabase(@PathVariable String projectName) {
		List<AreaGraphChartModel> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			AreaGraphChartModel are = new AreaGraphChartModel();
			are.setDate(new Date());
			are.setQuanlity(10);
			are.setStatus("Failed");
			list.add(are);
		}
		return list;
	}

	@RequestMapping("/createTestSuite/{projectName}")
	public void index(@PathVariable String projectName) {
		List<List<TestSuiteDTO>> listTestSuite = FitnessUtil.createAllTestSuiteRunWithDate("http://localhost:8083",
				projectName);
		for (List<TestSuiteDTO> list : listTestSuite) {
			for (TestSuiteDTO testSuiteDTO : list) {
				testSuiteService.saveOrUpdate(testSuiteDTO);
				for (TestCaseDTO testCase : testSuiteDTO.getTestCases()) {
					testCaseService.saveOrUpdate(testCase);
				}
			}
		}
	}

	@RequestMapping("/createData")
	public void createData() {
		List<ProjectDTO> projects = FitnessUtil.createProjects("http://localhost:8083");
		for (ProjectDTO projectDTO : projects) {
			projectService.saveOrUpdate(projectDTO);
			for (TestSuiteDTO testSuiteDTO : projectDTO.getTestSuites()) {
				testSuiteService.saveOrUpdate(testSuiteDTO);
				for (TestCaseDTO testCase : testSuiteDTO.getTestCases()) {
					testCaseService.saveOrUpdate(testCase);
				}
			}
		}
	}

	@RequestMapping(value = "/showTestCase/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<TestCaseDTO> showTestSuite(@PathVariable String projectName) {
		return testCaseService.getTestCaseFourWeek();
	}

}
