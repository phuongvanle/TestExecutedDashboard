package com.dxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.dao.ProjectDAO;
import com.dxc.model.ProjectDTO;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	ProjectDAO p;
	
	public ProjectDAO getP() {
		return p;
	}
	
	@Autowired
	public void setP(ProjectDAO p) {
		this.p = p;
	}

	@Override
	public void saveOrUpdate(ProjectDTO project) {
		p.saveOrUpdate(project);
	}

	@Override
	public List<ProjectDTO> getAll() {
		return p.getAll();
	}

}
