package com.dxc.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dxc.model.ProjectDTO;
@Repository
@Transactional
public class ProjectDAOImpl implements ProjectDAO {
	
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(ProjectDTO project) {
		getSessionFactory().getCurrentSession().saveOrUpdate(project);
	}

	@Override
	public List<ProjectDTO> getAll() {
		return getSessionFactory().getCurrentSession().createQuery("from ProjectDTO").list();
	}

}
