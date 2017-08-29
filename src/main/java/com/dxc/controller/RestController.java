package com.dxc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dxc.model.AreaGraphChartModel;
import com.dxc.model.PieChartModel;
import com.dxc.model.TestSuiteDTO;
import com.dxc.util.FitnessUtil;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	
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
}
