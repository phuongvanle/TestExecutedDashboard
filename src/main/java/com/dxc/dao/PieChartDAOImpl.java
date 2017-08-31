package com.dxc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.model.PieChartDTO;
@Repository
@Transactional
public class PieChartDAOImpl implements PieChartDAO {
	
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<PieChartDTO> getAll(String projectName) {
		String hql = "from PieChartDTO pie where pie.project in (select id from ProjectDTO p where p.name = :name)";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("name", projectName);
		return query.list();
	}
	@Override
	public void saveOrUpdate(PieChartDTO pieChartDTO) {
		getSessionFactory().getCurrentSession().saveOrUpdate(pieChartDTO);
	}

}
