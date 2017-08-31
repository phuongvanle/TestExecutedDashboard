package com.dxc.dao;

import java.util.List;

import com.dxc.model.PieChartDTO;

public interface PieChartDAO {
	
	public void saveOrUpdate(PieChartDTO pieChartDTO);
	
	public List<PieChartDTO> getAll(String projectName);
}
