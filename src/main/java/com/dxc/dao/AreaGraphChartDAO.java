package com.dxc.dao;

import java.util.List;

import com.dxc.model.AreaGraphChartDTO;

public interface AreaGraphChartDAO {
	
	public void saveOrUpdate(AreaGraphChartDTO areGraphDTO);
	
	public List<AreaGraphChartDTO> getAll(String projectName);

}
