package com.togest.domain.init;

import com.togest.domain.upgrade.DataCommonEntity;



public class PTripLineAlias extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //线路id
    private String lineId;
    //名称
    private String name;

    /**
     * 设置：线路id
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取：线路id
     */
    public String getLineId() {
        return lineId;
    }
    /**
     * 设置：名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：名称
     */
    public String getName() {
        return name;
    }
}
