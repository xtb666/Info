package com.togest.domain;

import com.togest.domain.upgrade.DataCommonEntity;



public class WireHeightLineMin extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //线别
    private String lineId;
    //允许的最低值
    private Integer minValue;
    //线路名称
    private String lineName;
    
    private String workAreaId;
    
    private String sectionId;
    
    protected Boolean isChild = false;

    /**
     * 设置：线别
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取：线别
     */
    public String getLineId() {
        return lineId;
    }
    /**
     * 设置：允许的最低值
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * 获取：允许的最低值
     */
    public Integer getMinValue() {
        return minValue;
    }

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(String workAreaId) {
		this.workAreaId = workAreaId;
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
}
