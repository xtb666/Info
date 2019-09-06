package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;
import com.togest.domain.upgrade.DataCommonEntity;



public class WireHeightControl extends DataCommonEntity {

	private static final long serialVersionUID = 1L;
	
	protected String sectionId;
	//部门
	protected String workAreaId;
	protected Boolean isChild = false;
    //线路
    protected String lineId;
    //行别
    @DictMark(dictName = "direction", itemName = "directionName")
    protected String direction;
    //站区
    protected String psaId;
    //桥隧
    protected String tunnelId;
    //支柱
    protected String pillarId;
    //公里标
    protected String glb;
    //日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date recordDate;
    //最后修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date lastModifyDate;
    //导高标准值
    protected Integer standarLineHeight;
    //上限值
    protected Integer standarLineHeightUp;
    //下限值
    protected Integer standarLineHeightDown;

    public String getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
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
     * 设置：行别
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 获取：行别
     */
    public String getDirection() {
        return direction;
    }
    /**
     * 设置：站区
     */
    public void setPsaId(String psaId) {
        this.psaId = psaId;
    }

    /**
     * 获取：站区
     */
    public String getPsaId() {
        return psaId;
    }
    /**
     * 设置：桥隧
     */
    public void setTunnelId(String tunnelId) {
        this.tunnelId = tunnelId;
    }

    /**
     * 获取：桥隧
     */
    public String getTunnelId() {
        return tunnelId;
    }
	public Integer getStandarLineHeight() {
		return standarLineHeight;
	}

	public void setStandarLineHeight(Integer standarLineHeight) {
		this.standarLineHeight = standarLineHeight;
	}

	public String getPillarId() {
		return pillarId;
	}

	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
	}

	public Integer getStandarLineHeightUp() {
		return standarLineHeightUp;
	}

	public void setStandarLineHeightUp(Integer standarLineHeightUp) {
		this.standarLineHeightUp = standarLineHeightUp;
	}

	public Integer getStandarLineHeightDown() {
		return standarLineHeightDown;
	}

	public void setStandarLineHeightDown(Integer standarLineHeightDown) {
		this.standarLineHeightDown = standarLineHeightDown;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Boolean getIsChild() {
		return isChild;
	}

	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}

	public String getGlb() {
		return glb;
	}
	
	public void setGlb(String glb) {
		this.glb = glb;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
}
