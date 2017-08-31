package com.dxc.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.model.AreaGraphChartDTO;
@Repository
@Transactional
public class AreaGraphChartDaoImpl implements AreaGraphChartDAO {
	
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(AreaGraphChartDTO areGraphDTO) {
		getSessionFactory().getCurrentSession().saveOrUpdate(areGraphDTO);
	}

	@Override
	public List<AreaGraphChartDTO> getAll(String projectName) {
		String hql = "from AreaGraphChartDTO a where a.project in (select id from ProjectDTO p where p.name = :name)";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("name", projectName);
		return query.list();
	}

}
