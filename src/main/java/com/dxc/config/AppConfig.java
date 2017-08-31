package com.dxc.config;

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
import com.dxc.service.AreaGraphChartService;
import com.dxc.service.PieChartService;
import com.dxc.util.FitnessUtil;

@Configuration
@Component
@EnableScheduling
public class AppConfig {
	
	@Autowired
	PieChartService pieChartService;
	@Autowired
	AreaGraphChartService areaChartService;
	
	@Scheduled(fixedDelay = 60000*10)
	public void updateDataToDatabase() {
		updateFromFitnesse("http://localhost:8083");
	}
	
	public void updateFromFitnesse(String url) {
		List<String> names = FitnessUtil.getProjectNames(url);
		List<AreaGraphChartDTO> listArea = null;
		List<PieChartDTO> listPie = null;
		for (String name : names) {
			listArea = FitnessUtil.createAreaGraphData(url, name);
			listPie = FitnessUtil.createPieChartData(url, name);
		}
		
		for (PieChartDTO pieChartDTO : listPie) {
			pieChartService.saveOrUpdate(pieChartDTO);
		}
		
		for (AreaGraphChartDTO pieChartDTO : listArea) {
			areaChartService.saveOrUpdate(pieChartDTO);
		}
		
	}
	
	
}
