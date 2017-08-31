package com.dxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.dao.AreaGraphChartDAO;
import com.dxc.model.AreaGraphChartDTO;
@Service
public class AreaGraphChartServiceImpl implements AreaGraphChartService {
	
	AreaGraphChartDAO dao;
	
	public AreaGraphChartDAO getDao() {
		return dao;
	}
	
	@Autowired
	public void setDao(AreaGraphChartDAO dao) {
		this.dao = dao;
	}

	@Override
	public void saveOrUpdate(AreaGraphChartDTO areGraphDTO) {
		dao.saveOrUpdate(areGraphDTO);
	}

	@Override
	public List<AreaGraphChartDTO> getAll(String projectName) {
		return dao.getAll(projectName);
	}

}
