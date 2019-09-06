package com.togest.request;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.domain.MileageStatisticsDTO;

public class MileageStatisticsRequest extends MileageStatisticsDTO{

	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
