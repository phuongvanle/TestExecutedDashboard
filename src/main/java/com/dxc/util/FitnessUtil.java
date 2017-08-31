package com.dxc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.dxc.model.AreaGraphChartDTO;
import com.dxc.model.PieChartDTO;
import com.dxc.model.ProjectDTO;
import com.dxc.model.TestCaseDTO;
import com.dxc.model.TestSuiteDTO;
import com.dxc.service.AreaGraphChartService;
import com.dxc.service.PieChartService;

/**
 * @author phuongit
 *
 */

public class FitnessUtil {
	
	@Autowired
	static PieChartService pieChartService;
	@Autowired
	static AreaGraphChartService areaGraphChartService;
	
	public static final long CONTANT_ONE_DAY = 24*60*60*1000;
	
	
	public static List<String> getProjectNames(String url) {
		List<String> list = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(url + "/" + "FrontPage").get();
			Elements table = doc.select("table");
			for (Element element : table) {
				Elements td = element.select("td a");
				String href = td.attr("href");
				String result = href.substring(href.indexOf(".")+1, href.length());
				list.add(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
		
	}

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
			status = ("pass".equals(text)) ? TestCaseDTO.Passed : TestCaseDTO.Failed;
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
	public static TestCaseDTO createTestCase(String contextPath, String projectName,String testSuiteName, String date, String testCaseName) {
		TestCaseDTO testCase = new TestCaseDTO();
		int status = getTestCaseStatusByDate(contextPath, projectName, testSuiteName, date, testCaseName);
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
	
	public static TestSuiteDTO createTestSuite(String contextPath, String projectName, String testSuiteName, String date, List<TestCaseDTO> testCases) {
		TestSuiteDTO testSuite = new TestSuiteDTO();
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
	
	public static List<TestCaseDTO> createListTestCase(String contextPath, String projectName, String date, String testSuite) {
		List<TestCaseDTO> list = new ArrayList<TestCaseDTO>();
		Properties props = getTestCaseOfTestSuite(contextPath, projectName, testSuite, date);
		Enumeration enumer = props.keys();
		while (enumer.hasMoreElements()) {
			String nameTestCase = (String) enumer.nextElement();
			int status;
			String strStatus = (String) props.get(nameTestCase);
			switch (strStatus) {
			case "pass":
				status = TestCaseDTO.Passed;
				break;
			case "fail":
				status = TestCaseDTO.Failed;
				break;
			default:
				status = TestCaseDTO.Unknown;
				break;
			}
			TestCaseDTO testCase = new TestCaseDTO();
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
	public static List<TestSuiteDTO> createTestSuiteRunWithDate(String contextPath, String projectName, String testSuiteName) {
		List<TestSuiteDTO> listTestSuite = new ArrayList<TestSuiteDTO>();
		List<String> dates = getDateRunTestSuite(contextPath,projectName, testSuiteName);
		ComparatorWithDate comparator = new ComparatorWithDate();
		Collections.sort(dates, comparator);
		List<String> listDateUniqueValid = removeOutOfFourWeek(buildDateInFourWeek(dates.get(dates.size()-1)), dates);
		List<TestCaseDTO> testCases = null;
		TestSuiteDTO testSuite = null;
		for (String date : listDateUniqueValid) {
			testCases = createListTestCase(contextPath, projectName, date, testSuiteName);
			testSuite = createTestSuite(contextPath, projectName, testSuiteName, date, testCases);
			for (TestCaseDTO testCaseDTO : testCases) {
				testCaseDTO.setTestSuite(testSuite);
			}
			listTestSuite.add(testSuite);
		}
		return listTestSuite;
	}
	
	public static List<List<TestSuiteDTO>> createAllTestSuiteRunWithDate(String contextPath, String projectName) {
		List<String> testSuites = getTestSuiteOfProject(contextPath, projectName);
		List<List<TestSuiteDTO>> listTestSuiteAll = new ArrayList<>();
		for (String testSuiteName : testSuites) {
			List<TestSuiteDTO> listTestSuite = createTestSuiteRunWithDate(contextPath, projectName, testSuiteName);
			listTestSuiteAll.add(listTestSuite);
		}
		return listTestSuiteAll;
	}
	
	
	public static ProjectDTO createProject(String projectname) {
		ProjectDTO pro = new ProjectDTO();
		pro.setName(projectname);
		pro.setTestSuites(new ArrayList<TestSuiteDTO>());
		return pro;
	}
	
	public static List<ProjectDTO> createProjects(String url) {
		List<ProjectDTO> projects = new ArrayList<>();
		List<String> names = getProjectNames(url);
		for (String name : names) {
			ProjectDTO project = createProject(name);
			List<String> testSuiteOfName = getTestSuiteOfProject(url, name);
			for (String nameTestSuite : testSuiteOfName) {
				List<List<TestSuiteDTO>> listTestSuite = createAllTestSuiteRunWithDate(url, name);
				for (List<TestSuiteDTO> list : listTestSuite) {
					for (TestSuiteDTO testSuiteDTO : list) {
						testSuiteDTO.setProject(project);
						project.getTestSuites().add(testSuiteDTO);
					}
				}
			}
			projects.add(project);
		}
		return projects;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param contextPath
	 * @param projectName
	 * @return list piechartmodel
	 */
	public static List<PieChartDTO> createPieChartData(String contextPath, String projectName) {
		List<List<TestSuiteDTO>> list = createAllTestSuiteRunWithDate(contextPath, projectName);
		List<PieChartDTO> listPieChart = new ArrayList<>();
		int pass = 0;
		int fail = 0;
		int error = 0;
		for (List<TestSuiteDTO> listFirst : list) {
			for (TestSuiteDTO testSuite : listFirst) {
				for (TestCaseDTO testCase : testSuite.getTestCases()) {
					if (TestCaseDTO.Passed == testCase.getStatus()) {
						pass ++;
					}
					if (TestCaseDTO.Failed == testCase.getStatus()) {
						fail++;
					}
					if (TestCaseDTO.Unknown == testCase.getStatus()) {
						error++;
					}
				}
			}
		}
		PieChartDTO data1 = new PieChartDTO();
		data1.setStatus("Passed");
		data1.setQuantity(pass);
		PieChartDTO data2 = new PieChartDTO();
		data2.setStatus("Failed");
		data2.setQuantity(fail);
		PieChartDTO data3 = new PieChartDTO();
		data3.setStatus("Error");
		data3.setQuantity(error);
		listPieChart.add(data1);
		listPieChart.add(data2);
		listPieChart.add(data3);
		return listPieChart;
	}
	
	
	
	
	public static List<AreaGraphChartDTO> createAreaGraphData(String contextPath, String projectName) {
		List<List<TestSuiteDTO>> listAll = createAllTestSuiteRunWithDate(contextPath, projectName);
		List<AreaGraphChartDTO> listAreaChart = new ArrayList<>();
		List<TestSuiteDTO> listTSuiteByDate = new ArrayList<>();
		List<String> listDateUnique = new ArrayList<>();
		for (List<TestSuiteDTO> listTestSuite : listAll) {
			for (TestSuiteDTO testSuite : listTestSuite) {
				if (!listDateUnique.contains(testSuite.getDate().substring(0, testSuite.getDate().length()-6))) {
					listDateUnique.add(testSuite.getDate().substring(0, testSuite.getDate().length()-6));
				}
			}
		}
		
		ComparatorWithDate comparator = new ComparatorWithDate();
		Collections.sort(listDateUnique, comparator);
		List<String> listDateUniqueValid = removeOutOfFourWeek(buildDateInFourWeek(listDateUnique.get(listDateUnique.size()-1)), listDateUnique);
		
		
		for (int i = 0; i < listDateUniqueValid.size(); i++) {
			AreaGraphChartDTO areaChartModelPassed = new AreaGraphChartDTO();
			AreaGraphChartDTO areaChartModelFailed = new AreaGraphChartDTO();
			int quanlityFailed = 0;
			int quanlityPassed = 0;
			for (List<TestSuiteDTO> listTestSuite : listAll) {
				for (TestSuiteDTO testSuite : listTestSuite) {
					for (TestCaseDTO testCase : testSuite.getTestCases()) {
						if (listDateUniqueValid.get(i).equalsIgnoreCase(testCase.getDate().substring(0, testSuite.getDate().length()-6))) {
							if (TestCaseDTO.Passed == testCase.getStatus()) {
								areaChartModelPassed.setDate(buildTimeOrder(listDateUniqueValid.get(i)));
								areaChartModelPassed.setStatus("Passed");
								quanlityPassed++;
								areaChartModelPassed.setQuanlity(quanlityPassed);
							}
							if (TestCaseDTO.Failed == testCase.getStatus()) {
								quanlityFailed++;
								areaChartModelFailed.setDate(buildTimeOrder(listDateUniqueValid.get(i)));
								areaChartModelFailed.setStatus("Failed");
								areaChartModelFailed.setQuanlity(quanlityFailed);
							}
						}
						
					}
					
				}
			}
			listAreaChart.add(areaChartModelPassed);
			listAreaChart.add(areaChartModelFailed);
		}
		return listAreaChart;
	}
	
	
	/**
	 * this method automatic update data to database
	 * @param days
	 * @return
	 */
	
	
	public static Date buildTimeOrder(String days){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(days);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static void main(String[] args) {
		String contextPath = "http://localhost:8083";
		String projectName = "SuitProject001";
		String testSuiteName = "SuiteIntegrationTesting";
		List<TestCaseDTO> testCases = null;
		
		
		getProjectNames(contextPath);
		
		
	}

	public static String buildDateInFourWeek(String date) {
		Calendar recentDate = Calendar.getInstance();
		int year1 = Integer.parseInt(date.substring(0, 4));
		int month1 = Integer.parseInt(date.substring(4, 6));
		int day1 = Integer.parseInt(date.substring(6, 8));
		recentDate.set(year1, month1-1, day1);
		recentDate.add(Calendar.WEEK_OF_YEAR, -4);
		Date res = recentDate.getTime();
		String format = new SimpleDateFormat("yyyyMMdd").format(res);
		return format;
	}

	public static List<String> removeOutOfFourWeek(String date, List<String> list) {
		int index = 0;
		ComparatorWithDate comparator = new ComparatorWithDate();
		if (list.contains(date)) {
			 index = list.indexOf(date);
			 for (int i = list.size(); i >= 0; i--) {
					if (i < index) {
						list.remove(i);
					}
				}
		} else {
			list.add(date);
			Collections.sort(list, comparator);
			index = list.indexOf(date);
			for (int i = list.size(); i >= 0; i--) {
				if (i <= index) {
					list.remove(i);
				}
			}
		}
		
		return list;
	}

	

}
