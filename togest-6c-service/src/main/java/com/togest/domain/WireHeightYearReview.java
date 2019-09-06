package com.togest.domain;

import com.togest.domain.upgrade.DataCommonEntity;



public class WireHeightYearReview extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //工区
    protected String workAreaId;
    //线别
    protected String lineId;
    //行别
    protected String direction;
    //站区
    protected String psaId;
    //桥隧
    protected String tunnelId;
    //支柱
    protected String pillarId;
    //公里标
    protected String glb;
    //年度
    protected Integer year;
    //年度复核轨面高差
    protected Integer yearReviewRailHeightValue;
    
    protected Boolean isChild = false;
    
    protected String sectionId;

    /**
     * 设置：工区
     */
    public void setWorkAreaId(String workAreaId) {
        this.workAreaId = workAreaId;
    }

    /**
     * 获取：工区
     */
    public String getWorkAreaId() {
        return workAreaId;
    }
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
    /**
     * 设置：支柱
     */
    public void setPillarId(String pillarId) {
        this.pillarId = pillarId;
    }

    /**
     * 获取：支柱
     */
    public String getPillarId() {
        return pillarId;
    }
    /**
     * 设置：公里标
     */
    public void setGlb(String glb) {
        this.glb = glb;
    }

    /**
     * 获取：公里标
     */
    public String getGlb() {
        return glb;
    }
    /**
     * 设置：年度复核轨面高差
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 获取：年度复核轨面高差
     */
    public Integer getYear() {
        return year;
    }
    /**
     * 设置：年度复核轨面高差
     */
    public void setYearReviewRailHeightValue(Integer yearReviewRailHeightValue) {
        this.yearReviewRailHeightValue = yearReviewRailHeightValue;
    }

    /**
     * 获取：年度复核轨面高差
     */
    public Integer getYearReviewRailHeightValue() {
        return yearReviewRailHeightValue;
    }

	public Boolean getIsChild() {
		return isChild;
	}

	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
}
