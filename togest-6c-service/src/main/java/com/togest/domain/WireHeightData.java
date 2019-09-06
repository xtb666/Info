package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.dict.annotation.DictMark;
import com.togest.domain.upgrade.DataCommonEntity;



public class WireHeightData extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //6C类型
    protected String systemId;
    //线路
    protected String lineId;
    //段
    protected String sectionId;
    //车间
    protected String workShopId;
    //工区
    protected String workAreaId;
    
    protected Boolean isChild = false;
    //行别
    @DictMark(dictName = "direction", itemName = "directionName")
    protected String direction;
    //站区
    protected String psaId;
    // 桥隧
    protected String tunnelId;
    //支柱id
    protected String pillarId;
    //检测日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	protected Date checkDate;
    //公里标
    protected String glb;
    //速度
    protected Double speed;
    //导高定位点
    protected Integer anchorPoint;
    //导高跨内最高
    protected Integer spanMax;
    //导高跨内最低
    protected Integer spanMin;
    //最新测量值(含复核)
    protected Integer reviewValue;
    //最新测量值来源
    protected String reviewValueSource;
    //调整后导高
    protected Integer adjustWireHeight;
    //本次复核轨面高差
    protected Integer currentReviewRailHeightValue;
    //本次调整后接触网还可上调余量
    protected Integer jcwAdjustUpValue;
    //缺陷类别
    protected String defectType;
    //缺陷等级
    @DictMark(dictName="defect_grade", primaryKey="code")
    protected String defectLevel;
    //复核后缺陷等级
    @DictMark(dictName="defect_grade", primaryKey="code")
    protected String reviewDefectLevel;
    //缺陷处设备属性
    protected String defectEquProperty;
    //复核缺陷是否存在 1存在  0不存在
    protected Integer isReviewExist;
    //动态检测值
    protected Integer dymCheckValue;
    //静态检测值(原因分析)
    protected String staticCheckValue;
    //静态检测值日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	protected Date staticCheckDate;
    //整治结果
    protected Integer passResult;
    //缺陷处理操作人
    protected String defectDealPerson;
    //处理日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	protected Date dealDate;
    //反馈期限
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
    protected Date feedbackLimitDate;
    //是否超时限整改 1超时  0不超时
    protected Integer isOverTimeReview;
    
    //关联导高标准值历史记录id
  	protected String wireHeightControlHistoryId;
  	protected WireHeightControlHistoryDTO wireHeightControlHistoryDTO;

    /**
     * 设置：6C类型
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * 获取：6C类型
     */
    public String getSystemId() {
        return systemId;
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
     * 设置：段
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * 获取：段
     */
    public String getSectionId() {
        return sectionId;
    }
    /**
     * 设置：车间
     */
    public void setWorkShopId(String workShopId) {
        this.workShopId = workShopId;
    }

    /**
     * 获取：车间
     */
    public String getWorkShopId() {
        return workShopId;
    }
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
     * 设置：支柱id
     */
	public void setPillarId(String pillarId) {
		this.pillarId = pillarId;
	}

    /**
     * 获取：支柱id
     */
    public String getPillarId() {
		return pillarId;
	}

    /**
     * 设置：检测日期
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }


	/**
     * 获取：检测日期
     */
    public Date getCheckDate() {
        return checkDate;
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
     * 设置：速度
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * 获取：速度
     */
    public Double getSpeed() {
        return speed;
    }
    /**
     * 设置：导高定位点
     */
    public void setAnchorPoint(Integer anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    /**
     * 获取：导高定位点
     */
    public Integer getAnchorPoint() {
        return anchorPoint;
    }
    /**
     * 设置：导高跨内最高
     */
    public void setSpanMax(Integer spanMax) {
        this.spanMax = spanMax;
    }

    /**
     * 获取：导高跨内最高
     */
    public Integer getSpanMax() {
        return spanMax;
    }
    /**
     * 设置：导高跨内最低
     */
    public void setSpanMin(Integer spanMin) {
        this.spanMin = spanMin;
    }

    /**
     * 获取：导高跨内最低
     */
    public Integer getSpanMin() {
        return spanMin;
    }
    
    /**
     * 设置：现场复核值
     */
    public void setReviewValue(Integer reviewValue) {
        this.reviewValue = reviewValue;
    }

    
    /**
     * 设置：调整后导高
     */
    public void setAdjustWireHeight(Integer adjustWireHeight) {
        this.adjustWireHeight = adjustWireHeight;
    }

    /**
     * 获取：调整后导高
     */
    public Integer getAdjustWireHeight() {
        return adjustWireHeight;
    }
    /**
     * 设置：本次复核轨面高差
     */
    public void setCurrentReviewRailHeightValue(Integer currentReviewRailHeightValue) {
        this.currentReviewRailHeightValue = currentReviewRailHeightValue;
    }

    /**
     * 获取：本次复核轨面高差
     */
    public Integer getCurrentReviewRailHeightValue() {
        return currentReviewRailHeightValue;
    }
    
    /**
     * 设置：本次调整后接触网还可上调余量
     */
    public void setJcwAdjustUpValue(Integer jcwAdjustUpValue) {
        this.jcwAdjustUpValue = jcwAdjustUpValue;
    }

    /**
     * 获取：本次调整后接触网还可上调余量
     */
    public Integer getJcwAdjustUpValue() {
        return jcwAdjustUpValue;
    }
    /**
     * 设置：缺陷类别
     */
    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    /**
     * 获取：缺陷类别
     */
    public String getDefectType() {
        return defectType;
    }
    /**
     * 设置：缺陷等级
     */
    public void setDefectLevel(String defectLevel) {
        this.defectLevel = defectLevel;
    }

    /**
     * 获取：缺陷等级
     */
    public String getDefectLevel() {
        return defectLevel;
    }
    /**
     * 设置：复核后缺陷等级
     */
    public void setReviewDefectLevel(String reviewDefectLevel) {
        this.reviewDefectLevel = reviewDefectLevel;
    }

    /**
     * 获取：复核后缺陷等级
     */
    public String getReviewDefectLevel() {
        return reviewDefectLevel;
    }
    /**
     * 设置：缺陷处设备属性
     */
    public void setDefectEquProperty(String defectEquProperty) {
        this.defectEquProperty = defectEquProperty;
    }

    /**
     * 获取：缺陷处设备属性
     */
    public String getDefectEquProperty() {
        return defectEquProperty;
    }
    /**
     * 设置：复核缺陷是否存在
     */
    public void setIsReviewExist(Integer isReviewExist) {
        this.isReviewExist = isReviewExist;
    }

    /**
     * 获取：复核缺陷是否存在
     */
    public Integer getIsReviewExist() {
        return isReviewExist;
    }
    /**
     * 设置：动态检测值
     */
    public void setDymCheckValue(Integer dymCheckValue) {
        this.dymCheckValue = dymCheckValue;
    }

    /**
     * 获取：动态检测值
     */
    public Integer getDymCheckValue() {
        return dymCheckValue;
    }
    /**
     * 设置：静态检测值日期
     */
    public void setStaticCheckDate(Date staticCheckDate) {
        this.staticCheckDate = staticCheckDate;
    }

    /**
     * 获取：静态检测值日期
     */
    public Date getStaticCheckDate() {
        return staticCheckDate;
    }
    /**
     * 设置：整治结果
     */
    public void setPassResult(Integer passResult) {
        this.passResult = passResult;
    }

    /**
     * 获取：整治结果
     */
    public Integer getPassResult() {
        return passResult;
    }
    /**
     * 设置：缺陷处理操作人
     */
    public void setDefectDealPerson(String defectDealPerson) {
        this.defectDealPerson = defectDealPerson;
    }

    /**
     * 获取：缺陷处理操作人
     */
    public String getDefectDealPerson() {
        return defectDealPerson;
    }
    /**
     * 设置：处理日期
     */
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    /**
     * 获取：处理日期
     */
    public Date getDealDate() {
        return dealDate;
    }
    /**
     * 设置：反馈期限
     */
    public void setFeedbackLimitDate(Date feedbackLimitDate) {
        this.feedbackLimitDate = feedbackLimitDate;
    }

    /**
     * 获取：反馈期限
     */
    public Date getFeedbackLimitDate() {
        return feedbackLimitDate;
    }
    /**
     * 设置：是否超时限整改
     */
    public void setIsOverTimeReview(Integer isOverTimeReview) {
        this.isOverTimeReview = isOverTimeReview;
    }

    /**
     * 获取：是否超时限整改
     */
    public Integer getIsOverTimeReview() {
        return isOverTimeReview;
    }

	public String getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	public Boolean getIsChild() {
		return isChild;
	}

	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}
	/**
     * 设置：静态检测值(原因分析)
     */
    public void setStaticCheckValue(String staticCheckValue) {
        this.staticCheckValue = staticCheckValue;
    }

    /**
     * 获取：静态检测值(原因分析)
     */
    public String getStaticCheckValue() {
        return staticCheckValue;
    }

	public String getReviewValueSource() {
		return reviewValueSource;
	}

	public void setReviewValueSource(String reviewValueSource) {
		this.reviewValueSource = reviewValueSource;
	}

	public String getWireHeightControlHistoryId() {
		return wireHeightControlHistoryId;
	}

	public void setWireHeightControlHistoryId(String wireHeightControlHistoryId) {
		this.wireHeightControlHistoryId = wireHeightControlHistoryId;
	}

	public WireHeightControlHistoryDTO getWireHeightControlHistoryDTO() {
		return wireHeightControlHistoryDTO;
	}

	public void setWireHeightControlHistoryDTO(WireHeightControlHistoryDTO wireHeightControlHistoryDTO) {
		this.wireHeightControlHistoryDTO = wireHeightControlHistoryDTO;
	}

	public Integer getReviewValue() {
		return reviewValue;
	}
    
}
