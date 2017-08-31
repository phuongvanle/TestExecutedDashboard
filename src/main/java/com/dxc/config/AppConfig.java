package com.dxc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dxc.model.AreaGraphChartDTO;
import com.dxc.model.PieChartDTO;
import com.dxc.model.ProjectDTO;
import com.dxc.service.AreaGraphChartService;
import com.dxc.service.PieChartService;
import com.dxc.service.ProjectService;
import com.dxc.util.FitnessUtil;

@Configuration
@Component
@EnableScheduling
public class AppConfig {
	
	@Autowired
	PieChartService pieChartService;
	@Autowired
	AreaGraphChartService areaChartService;
	@Autowired
	ProjectService projectService;
	
	@Scheduled(fixedDelay = 60000*10)
	public void updateDataToDatabase() {
		updateFromFitnesse("http://localhost:8083");
	}
	
	public void updateFromFitnesse(String url) {
		List<String> names = FitnessUtil.getProjectNames(url);
		List<AreaGraphChartDTO> listArea = null;
		List<PieChartDTO> listPie = null;
		List<ProjectDTO> listProject = new ArrayList<>();
		for (String name : names) {
			ProjectDTO pro = FitnessUtil.createProject(name);
			listProject.add(pro);
		}
		for (ProjectDTO projectDTO : listProject) {
			listArea = FitnessUtil.createAreaGraphData(url, projectDTO.getName());
			listPie = FitnessUtil.createPieChartData(url, projectDTO.getName());
			
			for (AreaGraphChartDTO areaGraph : listArea) {
				areaGraph.setProject(projectDTO);
				areaChartService.saveOrUpdate(areaGraph);
			}
			for (PieChartDTO pieChart : listPie) {
				pieChart.setProject(projectDTO);
				pieChartService.saveOrUpdate(pieChart);
			}
			projectDTO.setListAreaChart(listArea);
			projectDTO.setListPieChart(listPie);
			projectService.saveOrUpdate(projectDTO);
		}
	}
	
	
}
