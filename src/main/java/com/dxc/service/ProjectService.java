package com.dxc.service;

import java.util.List;

import com.dxc.model.ProjectDTO;

public interface ProjectService {
	
	public void saveOrUpdate(ProjectDTO project);
	
	public List<ProjectDTO> getAll();

}
