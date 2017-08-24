package com.dxc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author training
 *
 */
public class FitnessUtil {
	
	
	
	/**
	 * Get plain text from url 
	 * @param url
	 * @return Plain text html
	 */
	public static void readHtmlFromTestHistory(String path) {
		try {
			Document doc = Jsoup.connect(path).get();
			Elements table = doc.select("table");
			ArrayList<String> downServer = new ArrayList<String>();
			Elements rows = table.select("tr");
			for (int i = 1; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements cols = row.select("td");
				downServer.add(cols.get(i).text());
			}
			for (String string : downServer) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String url = "http://localhost:8080/FrontPage.SuitProject001.SuiteIntegrationTesting?testHistory";
		readHtmlFromTestHistory(url);
	}

}
