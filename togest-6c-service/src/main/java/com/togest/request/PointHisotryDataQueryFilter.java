package com.togest.request;

import java.util.Date;

import com.togest.util.Data;

public class PointHisotryDataQueryFilter extends Data {

	private Date startCheckTime;
	private Date endCheckTime;
	private String pointId;

	public Date getStartCheckTime() {
		return startCheckTime;
	}

	public void setStartCheckTime(Date startCheckTime) {
		this.startCheckTime = startCheckTime;
	}

	public Date getEndCheckTime() {
		return endCheckTime;
	}

	public void setEndCheckTime(Date endCheckTime) {
		this.endCheckTime = endCheckTime;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

}
