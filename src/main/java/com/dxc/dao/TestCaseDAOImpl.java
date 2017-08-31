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
		return getSessionFactory().getCurrentSession().createQuery("from TestCaseDTO").list();
	}

	@Override
	public List<TestCaseDTO> getDataFourWeek(String projectName) {
		String hql = "SELECT count(tc.id), tc.TestCaseStatus from testcase tc where tc.RunDate BETWEEN DATE_SUB(current_date(), INTERVAL 28 DAY) AND current_date() GROUP BY tc.TestCaseStatus";
//		String sql = "SELECT RunDate, TestCaseStatus FROM testcase WHERE RunDate BETWEEN DATE_SUB(current_date(), INTERVAL 28 DAY) AND current_date()";
		return getSessionFactory().getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List<TestCaseDTO> getTestCaseOfProject(String idProject) {
		// TODO Auto-generated method stub
		return null;
	}

}
