package com.dxc.dao;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.dxc.model.ProjectDTO;

@ContextConfiguration(locations = "classpath:dispatcher-servlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectDAOImplTest {
	
	@Autowired
	private ProjectDAO projectDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Transactional
	@Rollback(true)
	public void test() {
		ProjectDTO p  = projectDao.getProject("SuitProject001");
		junit.framework.Assert.assertEquals(1, p.getId());
	}

}
