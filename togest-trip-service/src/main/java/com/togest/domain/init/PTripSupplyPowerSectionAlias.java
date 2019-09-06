package com.togest.domain.init;

import com.togest.domain.upgrade.DataCommonEntity;



public class PTripSupplyPowerSectionAlias extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //供电臂id
    private String supplyPowerSectionId;
    //供电臂名称
    private String name;

    /**
     * 设置：供电臂id
     */
    public void setSupplyPowerSectionId(String supplyPowerSectionId) {
        this.supplyPowerSectionId = supplyPowerSectionId;
    }

    /**
     * 获取：供电臂id
     */
    public String getSupplyPowerSectionId() {
        return supplyPowerSectionId;
    }
    /**
     * 设置：供电臂名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：供电臂名称
     */
    public String getName() {
        return name;
    }
}
