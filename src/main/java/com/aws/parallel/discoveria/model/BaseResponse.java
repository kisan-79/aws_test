package com.aws.parallel.discoveria.model;

import java.util.ArrayList;
import java.util.List;

public class BaseResponse {
	
	private String status;
	private List<String> data = new ArrayList<>();
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
	
	
}
