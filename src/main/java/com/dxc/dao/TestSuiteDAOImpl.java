package com.dxc.dao;

import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.model.TestSuiteDTO;

@Repository
@Transactional
public class TestSuiteDAOImpl implements TestSuiteDAO {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(TestSuiteDTO testSuite) {
		getSessionFactory().getCurrentSession().saveOrUpdate(testSuite);
	}

	@Override
	public List<TestSuiteDTO> getAll() {
		return getSessionFactory().getCurrentSession().createQuery("from testsuite").list();
	}

}
