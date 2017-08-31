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
import com.dxc.model.AreaGraphChartDTO;
import com.dxc.model.PieChartDTO;
import com.dxc.model.ProjectDTO;
import com.dxc.model.TestCaseDTO;
import com.dxc.model.TestSuiteDTO;
import com.dxc.service.AreaGraphChartService;
import com.dxc.service.PieChartService;
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
	@Autowired
	PieChartService pieService;
	@Autowired
	AreaGraphChartService areaChartService;

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/showall/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<List<TestSuiteDTO>> showAllTestSuite(@PathVariable String projectName) {
		String contextPath = "http://localhost:8083";
		return FitnessUtil.createAllTestSuiteRunWithDate(contextPath, projectName);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/pieChartData/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<PieChartDTO> getDatePieChart(@PathVariable String projectName) {
		return pieService.getAll(projectName);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/areaChartData/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<AreaGraphChartDTO> getAreaGraphChartData(@PathVariable String projectName) {
		return  areaChartService.getAll(projectName);
	}

	@RequestMapping(value = "/areaChartDatabase/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<AreaGraphChartDTO> getAreaGraphFromDatabase(@PathVariable String projectName) {
		List<AreaGraphChartDTO> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			AreaGraphChartDTO are = new AreaGraphChartDTO();
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
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/pieChartDataTest/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<PieChartDTO> showTestSuite(@PathVariable String projectName) {
		List<PieChartDTO> result = new ArrayList<>();
		for (Object object : testCaseService.getTestCaseFourWeek(projectName)) {
			Object[] o = (Object[]) object;
			int quanlity = Integer.parseInt(String.valueOf(o[0]));
			String status = String.valueOf(o[1]);
			PieChartDTO pie = new PieChartDTO();
			pie.setStatus(status);
			pie.setQuantity(quanlity);
			result.add(pie);
		}
		return result;
	}

}
