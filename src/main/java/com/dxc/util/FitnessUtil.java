package com.dxc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dxc.model.TestCase;
import com.dxc.model.TestSuite;

/**
 * @author training
 *
 */
public class FitnessUtil {

	/**
	 * Get plain text from url
	 * 
	 * @param url
	 * @return Plain text html
	 */
	public static TestSuite readHtmlFrompPageHistory(String path) {
		TestSuite testSuite;
		try {
			Document doc = Jsoup.connect(path).get();
			Elements tables = doc.select("table");
			Elements ols = doc.select("ol.breadcrumb li");
			Elements tdWithClassDataField = tables.select("td.date_field a");
			String nameProject, nameTestSuite;
			for (Element element : tdWithClassDataField) {
				String href = element.attr("href");
				System.out.println(href);
				nameProject = href.substring(href.indexOf(".")+1, href.lastIndexOf("."));
				nameTestSuite = href.substring(href.lastIndexOf(".")+1, href.indexOf("?"));
				String textDate = href.substring(href.indexOf("=")+1);
//				System.out.println(nameProject);
//				System.out.println(nameTestSuite);
//				System.out.println(textDate);
				readHtmlFromTestHistory(nameProject, nameTestSuite, textDate);
				
				
			}
			
			System.out.println(tdWithClassDataField);
//			String nameTestSuite = ols.select("a[href]").last().text();
			testSuite = new TestSuite();
			testSuite.setId(1);
//			testSuite.setName(nameTestSuite);
			System.out.println(ols.select("a[href]").last().text());
			for (int i = 0; i < tables.size(); i++) {
				final Element table = tables.get(i);
				Elements th01 = table.select("tbody tr td.pass_count");
				Elements th0 = table.select("tbody tr td.pass_count");
				Elements th1 = table.select("tbody tr td.fail_count");
//				System.out.println(th0);
//				System.out.println(th1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<TestCase> readHtmlFromTestHistory(String projectName, String testSuiteName, String textDate) {
		
		String path = "http://localhost:8082/FrontPage.";
		path += projectName;
		path += ".";
		path += testSuiteName;
		path += "?";
		path += "pageHistory&resultDate=";
		path += textDate;
		System.out.println(path);
		List<TestCase> listTest = new ArrayList<TestCase>();
		TestCase testCase = new TestCase();
		ArrayList<Element> eles = new ArrayList<Element>();
		
		try {
			Document doc = Jsoup.connect(path).get();
			Element table = doc.select("table").last();
			System.out.println("===================");
			System.out.println(table);
			System.out.println("======================");
			Elements as = table.select("a[href]");
			for (Element element : as) {
				String href = element.attr("href");
//				String nameProject = href.substring(href.indexOf(".")+1, href.lastIndexOf("."));
				String testCaseName = href.substring(href.lastIndexOf(".")+1, href.indexOf("?"));
//				String textDate = href.substring(href.indexOf("=")+1);
//				System.out.println("Project " + nameProject);
				System.out.println(testCaseName);
//				System.out.println(href);
			}
//			System.out.println(as);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		TestSuite testSuite = readHtmlFrompPageHistory("http://localhost:8082/FrontPage.SuitProject001.SuiteIntegrationTesting?pageHistory");
//		try {
//			Document doc = Jsoup.connect("http://localhost:8082/FrontPage.SuitProject001.SuiteIntegrationTesting?testHistory").get();
//			Elements table = doc.select("table");
//			Elements tr = table.select("tr");
////			System.out.println(tr);
//			int i = 1;
//			for (Element element : tr) {
//				Element a = element.select("a[href*=TestCase]").first();
//				if (a != null) {
//					
//					String name = a.text().substring(a.text().lastIndexOf("."), a.text().length());
//					testCase.setId(i);
//					testCase.setName(name);
////					testCase.setStatus(status);
//					i++;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return null;
	}
	public static void main(String[] args) {
		String url = "http://localhost:8082/FrontPage.SuitProject001.SuiteIntegrationTesting?pageHistory";
		readHtmlFrompPageHistory(url);
	
	}

}
