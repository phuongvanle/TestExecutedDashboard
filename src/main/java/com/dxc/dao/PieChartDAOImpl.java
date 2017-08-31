package com.dxc.dao;

import java.util.List;

import javax.transaction.Transactional;

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
	public List<PieChartDTO> getAll() {
		return null;
	}
	@Override
	public void saveOrUpdate(PieChartDTO pieChartDTO) {
		getSessionFactory().getCurrentSession().saveOrUpdate(pieChartDTO);
	}

}
