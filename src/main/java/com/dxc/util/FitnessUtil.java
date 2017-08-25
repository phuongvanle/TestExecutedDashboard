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
	 * 
	 * @param url
	 * @return Plain text html
	 */
	public static void readHtmlFromTestHistory(String path) {
		try {
			Document doc = Jsoup.connect(path).get();
			Elements tables = doc.select("table");
			for (int i = 0; i < tables.size(); i++) {
				final Element table = tables.get(i);
				Elements th0 = table.select("tbody tr th");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "http://localhost:8081/FrontPage.SuitProject001.SuiteIntegrationTesting?pageHistory";
		readHtmlFromTestHistory(url);
	}

}
