package com.dxc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dxc.model.TestCase;
import com.dxc.model.TestSuite;

/**
 * @author phuongit
 *
 */
public class FitnessUtil {

	/**
	 * Read all TestSuited in a project
	 * @param String project name
	 * @param String contextPath
	 * @return List TestSuited
	 */
	
	public static List<String> getTestSuiteOfProject(String contextPath, String projectName) {
		List<String> listTestSuite = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect(contextPath + "/" + "FrontPage."+ projectName).get();
			Elements table = doc.select("table");
			Elements td = table.select("td a");
			for (Element element : td) {
				String href = element.attr("href");
				String name = href.substring(href.lastIndexOf(".")+1);
				listTestSuite.add(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listTestSuite;
		
	}
	
	/**
	 * get all test case and status at date
	 * @param context path
	 * @param project name
	 * @param date
	 * @param testsuite
	 * @return list name test case and status
	 * 
	 */
	
	public static Properties getTestCaseOfTestSuite(String contextPath, String projectName,String testSuite, String date) {
		Properties props = new Properties();
		try {
				String path = contextPath + "/FrontPage." + projectName + "." + testSuite + "?pageHistory&resultDate="+date;
				Document doc = Jsoup.connect(path).get();
				Element table = doc.select("table").last();
				Elements trs = table.select("tr:contains(TestCase)");
				for (Element element : trs) {
					String status = element.attr("class");
					String name = element.select("a").text();
					props.put(name.substring(name.lastIndexOf(".")+1), status);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
		
	}
	
	
	/**
	 * @author phuongit
	 * This method get list all date on a time of run test suite
	 * 
	 *@param contextPath
	 *@param project Name
	 *@return list of String 
	 */
	public static List<String> getDateRunTestSuite(String contextPath, String projectName, String nameTestSuite) {
		List<String> listDate = new ArrayList<String>();
		try {
				String path = contextPath + "/FrontPage." + projectName + "." + nameTestSuite + "?pageHistory";
				Document doc = Jsoup.connect(path).get();
				Elements table = doc.select("table");
				Elements eleA = table.select("td a[href]");
				for (Element element : eleA) {
					String href = element.attr("href");
					String strDate = href.substring(href.indexOf("=")+1);
					listDate.add(strDate);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listDate;
		
	}
	

	/**
	 * @author phuongit
	 * This method get status of testcase which run in date
	 * @param context path
	 * @param project name
	 * @param test suite name
	 * @param date 
	 * @param test case name
	 * @return integer with Passed or Failed
	 */
	
	public static int getTestCaseStatusByDate(String contextPath, String projectName, String testSuiteName, String date, String testCaseName) {
		String path = contextPath + "/FrontPage." + projectName + "." + testSuiteName + "?pageHistory&resultDate=" + date;
		int status = 0;
		try {
			Document doc = Jsoup.connect(path).get();
			Element table = doc.select("table").last();
			Elements tr = table.select("tr:contains("+testCaseName+")");
			String text = tr.attr("class");
			status = ("pass".equals(text)) ? TestCase.Passed : TestCase.Failed;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
		
		
	}
	/**
	 * This method create a testcase with 
	 * @param contextPath
	 * @param project name
	 * @param testsuitename
	 * @param date run
	 * @param testcasename
	 * @return one test case
	 */
	public static TestCase createTestCase(String contextPath, String projectName,String testSuiteName, String date, String testCaseName) {
		TestCase testCase = new TestCase();
//		testCase.set
		int status = getTestCaseStatusByDate(contextPath, projectName, testSuiteName, date, testCaseName);
//		testCase.setId("tc" + date);
		testCase.setName(testCaseName);
		testCase.setStatus(status);
		return testCase;
	}
	
	/**
	 * This method create a test suite 
	 * 
	 * @param contextPath 
	 * @param projectName
	 * @param testSuiteName
	 * @param dateRun
	 * @param list testcase
	 * @return a test suite
	 * 
	 */
	
	public static TestSuite createTestSuite(String contextPath, String projectName, String testSuiteName, String date, List<TestCase> testCases) {
		TestSuite testSuite = new TestSuite();
//		testSuite.setId();
		testSuite.setName(testSuiteName);
		testSuite.setDate(date);
		testSuite.setTestCases(testCases);
		return testSuite;
	}
	
	/**
	 * 
	 * @param contextPath
	 * @param projectName
	 * @param date
	 * @param testSuite
	 * @return list of testcase
	 */
	
	public static List<TestCase> createListTestCase(String contextPath, String projectName, String date, String testSuite) {
		List<TestCase> list = new ArrayList<TestCase>();
		Properties props = getTestCaseOfTestSuite(contextPath, projectName, testSuite, date);
		Enumeration enumer = props.keys();
		while (enumer.hasMoreElements()) {
			String nameTestCase = (String) enumer.nextElement();
			int status;
			String strStatus = (String) props.get(nameTestCase);
			switch (strStatus) {
			case "pass":
				status = 2;
				break;
			case "fail":
				status = 1;
				break;
			default:
				status = 0;
				break;
			}
			TestCase testCase = new TestCase();
			testCase.setName(nameTestCase);
			testCase.setStatus(status);
			testCase.setDate(date);
			list.add(testCase);
		}
		return list;
	}
	
	/**
	 * 
	 * @param contextPath
	 * @param projectName
	 * @param testSuiteName
	 * @return The list result when run testSuiteName by date 
	 */
	public static List<TestSuite> createTestSuiteRunWithDate(String contextPath, String projectName, String testSuiteName) {
		List<TestSuite> listTestSuite = new ArrayList<TestSuite>();
		List<String> dates = getDateRunTestSuite(contextPath,projectName, testSuiteName);
		List<TestCase> testCases = null;
		TestSuite testSuite = null;
		for (String date : dates) {
			testCases = createListTestCase(contextPath, projectName, date, testSuiteName);
			testSuite = createTestSuite(contextPath, projectName, testSuiteName, date, testCases);
			listTestSuite.add(testSuite);
		}
		return listTestSuite;
	}
	
	public static List<List<TestSuite>> createAllTestSuiteRunWithDate(String contextPath, String projectName) {
		List<String> testSuites = getTestSuiteOfProject(contextPath, projectName);
		List<List<TestSuite>> listTestSuiteAll = new ArrayList<>();
		for (String testSuiteName : testSuites) {
			List<TestSuite> listTestSuite = createTestSuiteRunWithDate(contextPath, projectName, testSuiteName);
			listTestSuiteAll.add(listTestSuite);
		}
		return listTestSuiteAll;
		
		
	}
	
	
	public static void main(String[] args) {
		String url = "http://localhost:8082/FrontPage.SuitProject001.SuiteIntegrationTesting?pageHistory";
//		readHtmlFrompPageHistory(url);
//		getDateRunTestSuite("http://localhost:8083", "SuitProject001");
//		getTestCaseStatusByDate("http://localhost:8083","SuitProject001", "SuiteIntegrationTesting","20170824215920","TestCaseAbc1");
//		TestCase testCase = createTestCase("http://localhost:8083", "SuitProject001", "SuiteIntegrationTesting", "20170824215920", "TestCaseAbc1");
//		System.out.println(testCase);
//		Properties props = getTestCaseOfTestSuite("http://localhost:8083","SuitProject001","SuiteIntegrationTesting","20170824215920");
//		Enumeration enumer = props.keys();
//		while (enumer.hasMoreElements()) {
//			String key = (String) enumer.nextElement();
//			System.out.println(key + ":" + props.get(key));
//			
//		}
		String contextPath = "http://localhost:8083";
		String projectName = "SuitProject001";
		String testSuiteName = "SuiteIntegrationTesting";
		List<TestCase> testCases = null;
		TestSuite testSuite = null;
		List<TestSuite> listTestSuite = createTestSuiteRunWithDate(contextPath, projectName, testSuiteName);
		List<List<TestSuite>> list = createAllTestSuiteRunWithDate(contextPath, projectName);
		for (List<TestSuite> list1 : list) {
			for (TestSuite testSuite2 : list1) {
				System.out.println(testSuite2);
			}
		}
		
		
	}

}
