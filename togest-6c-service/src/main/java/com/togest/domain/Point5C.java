package com.togest.domain;

import java.util.Date;

import com.togest.dict.annotation.DictMark;

public class Point5C<T> extends BaseEntity<T>{
    protected String id;

    protected String lineId;

    protected String sectionId;

    protected String stationId;

    protected String installPillarId;

    protected Double installGlb;

    protected Date installDate;

    protected String pointName;

    protected String code;
    @DictMark(dictName="track")
    protected String track;
    @DictMark(dictName="direction")
    protected String direction;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getInstallPillarId() {
		return installPillarId;
	}

	public void setInstallPillarId(String installPillarId) {
		this.installPillarId = installPillarId;
	}

	public Double getInstallGlb() {
		return installGlb;
	}

	public void setInstallGlb(Double installGlb) {
		this.installGlb = installGlb;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}