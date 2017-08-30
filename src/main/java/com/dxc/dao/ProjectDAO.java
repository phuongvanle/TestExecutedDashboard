package com.dxc.dao;

import java.util.List;

import com.dxc.model.ProjectDTO;

public interface ProjectDAO {
	
	public void saveOrUpdate(ProjectDTO project);
	
	public List<ProjectDTO> getAll();

}
