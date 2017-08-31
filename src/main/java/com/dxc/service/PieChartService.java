package com.dxc.service;

import java.util.List;

import com.dxc.model.PieChartDTO;

public interface PieChartService {
	
	
	public void saveOrUpdate(PieChartDTO pieChartDTO);
	
	public List<PieChartDTO> getAll(String projectName);

}
