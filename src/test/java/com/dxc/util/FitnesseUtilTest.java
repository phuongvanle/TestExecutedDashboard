package com.dxc.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FitnesseUtilTest {
	List<String> list;
	ComparatorWithDate comparator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		list = new ArrayList<>();
		comparator = new ComparatorWithDate();
		list.add("20170801");
		list.add("20170805");
		list.add("20170803");
		list.add("20170809");
		list.add("20170802");
		list.add("20170831");
		Collections.sort(list, comparator);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		assertEquals("20170802", list.get(1));
	}
	
	@Test
	public void test1() {
		assertEquals("20170803", FitnessUtil.buildDateInFourWeek("20170831"));
	}
	
	@Test
	public void test3() {
		assertEquals(2, FitnessUtil.removeOutOfFourWeek("20170806", list));
		assertEquals("20170831", list.get(0));
	}

}
