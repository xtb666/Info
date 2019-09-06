package com.togest.domain.init;

import com.togest.domain.upgrade.DataCommonEntity;



public class PTripPavilionAlias extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //变电所id
    private String pavilionId;
    //名称
    private String name;

    /**
     * 设置：变电所id
     */
    public void setPavilionId(String pavilionId) {
        this.pavilionId = pavilionId;
    }

    /**
     * 获取：变电所id
     */
    public String getPavilionId() {
        return pavilionId;
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
