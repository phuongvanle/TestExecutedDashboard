package com.dxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.dao.PieChartDAO;
import com.dxc.model.PieChartDTO;
@Service
public class PieChartServiceImpl implements PieChartService {

	PieChartDAO dao;
	
	
	public PieChartDAO getDao() {
		return dao;
	}
	
	@Autowired
	public void setDao(PieChartDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<PieChartDTO> getAll(String projectName) {
		return dao.getAll(projectName);
	}

	@Override
	public void saveOrUpdate(PieChartDTO pieChartDTO) {
		dao.saveOrUpdate(pieChartDTO);
	}

}
