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

	@CrossOrigin
	@RequestMapping(value = "/showall/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<List<TestSuiteDTO>> showAllTestSuite(@PathVariable String projectName) {
		String contextPath = "http://localhost:8083";
		return FitnessUtil.createAllTestSuiteRunWithDate(contextPath, projectName);
	}

	@CrossOrigin
	@RequestMapping(value = "/pieChartData/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<PieChartDTO> getDatePieChart(@PathVariable String projectName) {
		return pieService.getAll(projectName);
	}

	@CrossOrigin
	@RequestMapping(value = "/areaChartData/{projectName}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<AreaGraphChartDTO> getAreaGraphChartData(@PathVariable String projectName) {
		return  areaChartService.getAll(projectName);
	}




}
