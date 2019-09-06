package com.togest.domain;

import com.togest.common.util.string.StringUtil;

public class WireHeightDataDTO extends WireHeightData {

	public static final String SYSTEM_1C = "C1"; //默认导高系统
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//线路名称
	private String lineName; 
	//车间名称
	private String workShopName;
	//工区名称
	private String workAreaName;
	// 行别
	private String directionName;
	// 站区名称
	private String psaName;
	//段名称
	private String sectionName;
	//缺陷类型
	private String defectTypeName;
	//缺陷等级
	private String defectLevelName;
	//桥隧
	private String tunnelName;
	//股道
	private String track;
	//股道
	private String trackName;
	//支柱号（杆号）
	private String pillarNum;
	//支柱名称（杆名）
	private String pillarName;
	
	//上月检测导高
    private Integer upMonthAnchorPoint;
    //本次与上次检测变化量
    private Integer changeValue;
    //两相邻点高差
    private Integer adjacentHeightValue;
    //计划调整量
    private Integer planAdjustValue;
	//导高标准值
	private Integer standarLineHeight;
	//年度复核轨面高差
    private Integer yearReviewRailHeightValue;
    //轨面变化量
    private Integer railChangValue;
    //可抬(+)/需落(-)道量
    private Integer liftableValue;
    //需落道起值
    private Integer needStartValue;
    //需落道止值
    private Integer needEndValue;
    //是否重复出现
    private Integer isRepeat;
    //是否
    @SuppressWarnings("unused")
	private String repeatStr;
    //是否
    @SuppressWarnings("unused")
	private String reviewExistStr;
    
    private String isRepeatName;
    private String reviewDefectLevelName;
    private boolean fg;
    
    private String startKm;
    private String endKm;
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getWorkShopName() {
		return workShopName;
	}
	public void setWorkShopName(String workShopName) {
		this.workShopName = workShopName;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getDefectTypeName() {
		return defectTypeName;
	}
	public void setDefectTypeName(String defectTypeName) {
		this.defectTypeName = defectTypeName;
	}
	public String getDefectLevelName() {
		return defectLevelName;
	}
	public void setDefectLevelName(String defectLevelName) {
		this.defectLevelName = defectLevelName;
	}
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getPillarNum() {
		return pillarNum;
	}
	public void setPillarNum(String pillarNum) {
		this.pillarNum = pillarNum;
	}
	public String getPillarName() {
		return pillarName;
	}
	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}
	
	public Integer getStandarLineHeight() {
		return standarLineHeight;
	}
	public void setStandarLineHeight(Integer standarLineHeight) {
		this.standarLineHeight = standarLineHeight;
	}
	/**
     * 设置：本次与上次检测变化量
     */
    public void setChangeValue(Integer changeValue) {
        this.changeValue = changeValue;
    }

    /**
     * 获取：本次与上次检测变化量
     */
    public Integer getChangeValue() {
        return changeValue;
    }
    
    /**
     * 获取：现场复核值
     */
    public Integer getReviewValue() {
        return reviewValue;
    }
    /**
     * 设置：两相邻点高差
     */
    public void setAdjacentHeightValue(Integer adjacentHeightValue) {
        this.adjacentHeightValue = adjacentHeightValue;
    }

    /**
     * 获取：两相邻点高差
     */
    public Integer getAdjacentHeightValue() {
        return adjacentHeightValue;
    }
    
    /**
     * 设置：计划调整量
     */
    public void setPlanAdjustValue(Integer planAdjustValue) {
        this.planAdjustValue = planAdjustValue;
    }

    /**
     * 获取：计划调整量
     */
    public Integer getPlanAdjustValue() {
        return planAdjustValue;
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
    /**
     * 设置：轨面变化量
     */
    public void setRailChangValue(Integer railChangValue) {
        this.railChangValue = railChangValue;
    }

    /**
     * 获取：轨面变化量
     */
    public Integer getRailChangValue() {
        return railChangValue;
    }
    /**
     * 设置：可抬(+)/需落(-)道量
     */
    public void setLiftableValue(Integer liftableValue) {
        this.liftableValue = liftableValue;
    }

    /**
     * 获取：可抬(+)/需落(-)道量
     */
    public Integer getLiftableValue() {
        return liftableValue;
    }
    /**
     * 设置：需落道起值
     */
    public void setNeedStartValue(Integer needStartValue) {
        this.needStartValue = needStartValue;
    }

    /**
     * 获取：需落道起值
     */
    public Integer getNeedStartValue() {
        return needStartValue;
    }
    /**
     * 设置：需落道止值
     */
    public void setNeedEndValue(Integer needEndValue) {
        this.needEndValue = needEndValue;
    }

    /**
     * 获取：需落道止值
     */
    public Integer getNeedEndValue() {
        return needEndValue;
    }
    
    public Integer getUpMonthAnchorPoint() {
		return upMonthAnchorPoint;
	}

	public void setUpMonthAnchorPoint(Integer upMonthAnchorPoint) {
		this.upMonthAnchorPoint = upMonthAnchorPoint;
	}
    /**
     * 设置：是否重复出现
     */
    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    /**
     * 获取：是否重复出现
     */
    public Integer getIsRepeat() {
        return isRepeat;
    }

	public boolean isFg() {
		return fg;
	}
	public void setFg(boolean fg) {
		this.fg = fg;
	}
	
	public String getStartKm() {
		return startKm;
	}
	public void setStartKm(String startKm) {
		this.startKm = startKm;
	}
	public String getEndKm() {
		return endKm;
	}
	public void setEndKm(String endKm) {
		this.endKm = endKm;
	}
	public String getIsRepeatName() {
		return isRepeatName;
	}
	public void setIsRepeatName(String isRepeatName) {
		this.isRepeatName = isRepeatName;
	}
	public String getReviewDefectLevelName() {
		return reviewDefectLevelName;
	}
	public void setReviewDefectLevelName(String reviewDefectLevelName) {
		this.reviewDefectLevelName = reviewDefectLevelName;
	}
	public String getRepeatStr() {
		return null != isRepeat ? (isRepeat.intValue() == 1 ? "是" : "否") : null;
	}
	public String getReviewExistStr() {
		return null != isReviewExist ? (isReviewExist.intValue() == 1 ? "是" : "否") : null;
	}
	public String getOverTimeReviewStr() {
		return null != isOverTimeReview ? (isOverTimeReview.intValue() == 1 ? "是" : "否") : null;
	}
	//保存之前校验数据
	public void check() {
		if(StringUtil.isBlank(this.getSystemId())) {
			this.setSystemId(SYSTEM_1C);
		}
		if(null != this.getCheckDate()) {
			this.setFeedbackLimitDate(org.apache.commons.lang3.time.DateUtils.addDays(this.getCheckDate(), 8));
		}
		if(this.getReviewValue() != null && this.getReviewValue() <= 0) {
			this.setReviewValue(null);
		}
		if(this.getAdjustWireHeight() != null && this.getAdjustWireHeight() <= 0) {
			this.setAdjustWireHeight(null);
		}
		if(this.getCurrentReviewRailHeightValue() != null && this.getCurrentReviewRailHeightValue() <= 0) {
			this.setCurrentReviewRailHeightValue(null);
		}
		if(this.getJcwAdjustUpValue() != null && this.getJcwAdjustUpValue() <= 0) {
			this.setJcwAdjustUpValue(null);
		}
		if(null != this.getDealDate() && null != this.getFeedbackLimitDate()) {
			if(this.getDealDate().compareTo(this.getFeedbackLimitDate())  > 0){
				this.setIsOverTimeReview(1);
			}else {
				this.setIsOverTimeReview(0);
			}
		}else {
			this.setIsOverTimeReview(null);
		}
		if(this.getAnchorPoint() != null && this.getAnchorPoint() <= 0) {
			this.setAnchorPoint(null);
		}
		if(this.getSpanMax() != null && this.getSpanMax() <= 0) {
			this.setSpanMax(null);
		}
		if(this.getSpanMin() != null && this.getSpanMin() <= 0) {
			this.setSpanMin(null);
		}
		if(this.getSpeed() != null && this.getSpeed() <= 0) {
			this.setSpeed(null);
		}
		this.setPassResult(this.getAdjustWireHeight());
	}
}
