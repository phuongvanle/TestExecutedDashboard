package com.dxc.service;

import java.util.List;

import com.dxc.model.AreaGraphChartDTO;

public interface AreaGraphChartService {
	
	public void saveOrUpdate(AreaGraphChartDTO areGraphDTO);
	
	public List<AreaGraphChartDTO> getAll(String projectName);

}
