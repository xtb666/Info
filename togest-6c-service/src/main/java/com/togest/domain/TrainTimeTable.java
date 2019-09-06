package com.togest.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import com.togest.domain.upgrade.DataCommonEntity;



public class TrainTimeTable extends DataCommonEntity {

	private static final long serialVersionUID = 1L;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date trainDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTrainDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTrainDate;
    //线路
    private String lineId;
    private String lineName;
    //始发站
    private String startStation;
    //始发时间
    private String startTime;
    //终到站
    private String endStation;
    //终到时间
    private String endTime;
    //车次
    private String trainNumber;
    private String sectionId;
    //单组
    private String singleGroup;
    //重联
    private String reconnection;
    //备注
    private String remark;

    public Date getTrainDate() {
		return trainDate;
	}

	public void setTrainDate(Date trainDate) {
		this.trainDate = trainDate;
	}

	public Date getStartTrainDate() {
		return startTrainDate;
	}

	public void setStartTrainDate(Date startTrainDate) {
		this.startTrainDate = startTrainDate;
	}

	public Date getEndTrainDate() {
		return endTrainDate;
	}

	public void setEndTrainDate(Date endTrainDate) {
		this.endTrainDate = endTrainDate;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
     * 设置：线路
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取：线路
     */
    public String getLineId() {
        return lineId;
    }
    /**
     * 设置：始发站
     */
    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    /**
     * 获取：始发站
     */
    public String getStartStation() {
        return startStation;
    }
    /**
     * 设置：始发时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取：始发时间
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     * 设置：终到站
     */
    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    /**
     * 获取：终到站
     */
    public String getEndStation() {
        return endStation;
    }
    /**
     * 设置：终到时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取：终到时间
     */
    public String getEndTime() {
        return endTime;
    }
    /**
     * 设置：车次
     */
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    /**
     * 获取：车次
     */
    public String getTrainNumber() {
        return trainNumber;
    }
    /**
     * 设置：单组
     */
    public void setSingleGroup(String singleGroup) {
        this.singleGroup = singleGroup;
    }

    /**
     * 获取：单组
     */
    public String getSingleGroup() {
        return singleGroup;
    }
    /**
     * 设置：重联
     */
    public void setReconnection(String reconnection) {
        this.reconnection = reconnection;
    }

    /**
     * 获取：重联
     */
    public String getReconnection() {
        return reconnection;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }
}
