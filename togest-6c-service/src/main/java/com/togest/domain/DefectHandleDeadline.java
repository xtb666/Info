package com.togest.domain;

import com.togest.dict.annotation.DictMark;
import com.togest.domain.upgrade.DataCommonEntity;



public class DefectHandleDeadline extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //缺陷数据等级
	@DictMark(dictName="busy_defect_data_level",primaryKey="code")
    private String defectDataLevel;
    private String defectDataLevelName;
    //缺陷类别(字典)
    private String defectDataCategory;
    //复核期限
    private Integer reviewDeadline;
    //整改期限
    private Integer reformDeadline;

    /**
     * 设置：缺陷数据等级
     */
    public void setDefectDataLevel(String defectDataLevel) {
        this.defectDataLevel = defectDataLevel;
    }

    /**
     * 获取：缺陷数据等级
     */
    public String getDefectDataLevel() {
        return defectDataLevel;
    }
    
    public String getDefectDataLevelName() {
		return defectDataLevelName;
	}

	public void setDefectDataLevelName(String defectDataLevelName) {
		this.defectDataLevelName = defectDataLevelName;
	}

	/**
     * 设置：缺陷类别(字典)
     */
    public void setDefectDataCategory(String defectDataCategory) {
        this.defectDataCategory = defectDataCategory;
    }

    /**
     * 获取：缺陷类别(字典)
     */
    public String getDefectDataCategory() {
        return defectDataCategory;
    }

	public Integer getReviewDeadline() {
		return reviewDeadline;
	}

	public void setReviewDeadline(Integer reviewDeadline) {
		this.reviewDeadline = reviewDeadline;
	}

	public Integer getReformDeadline() {
		return reformDeadline;
	}

	public void setReformDeadline(Integer reformDeadline) {
		this.reformDeadline = reformDeadline;
	}

}
