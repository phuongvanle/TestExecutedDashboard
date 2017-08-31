package com.dxc.util;

import java.util.Comparator;

import com.dxc.model.TestCaseDTO;

public class ComparatorWithDate implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		int com = 0;
		int year1 = Integer.parseInt(o1.substring(0, 4));
		int month1 = Integer.parseInt(o1.substring(4, 6));
		int day1 = Integer.parseInt(o1.substring(6, 8));
		int year2 = Integer.parseInt(o2.substring(0, 4));
		int month2 = Integer.parseInt(o2.substring(4, 6));
		int day2 = Integer.parseInt(o2.substring(6, 8));
		if (o1 != null && o1 != "") {
			com = year1 - year2;
			if (com == 0) {
				com = month1 - month2;
			}
			
			if (com == 0) {
				com = day1 - day2;
			}
		} else if (o2 != null && o2 != "") {
			com = -1 * (year2 - year1);
			if (com == 0) {
				com = -1 * (month2 - month1);
			}
			
			if (com == 0) {
				com = -1 * (day2 - day1);
			}
		}
		
		
		return com;
	}

	
	
	

}
