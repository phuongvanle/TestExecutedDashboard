package com.dxc.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.model.TestCaseDTO;

@Repository
@Transactional
public class TestCaseDAOImpl implements TestCaseDAO {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(TestCaseDTO tc) {
		getSessionFactory().getCurrentSession().saveOrUpdate(tc);
	}

	@Override
	public List<TestCaseDTO> getAll() {
		return getSessionFactory().getCurrentSession().createQuery("from testcase").list();
	}

}
