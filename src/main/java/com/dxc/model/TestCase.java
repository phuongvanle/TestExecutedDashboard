package com.dxc.model;

import java.util.Date;

public class TestCase {

	private int id;
	private String name;
	private int status;
	public static final int Failed = 1, Passed = 2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
