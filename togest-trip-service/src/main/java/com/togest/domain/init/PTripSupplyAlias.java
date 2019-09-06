package com.togest.domain.init;

import com.togest.domain.upgrade.DataCommonEntity;



public class PTripSupplyAlias extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //站区、区间id
    private String psaId;
    //站区、区间名称
    private String name;

    /**
     * 设置：站区、区间id
     */
    public void setPsaId(String psaId) {
        this.psaId = psaId;
    }

    /**
     * 获取：站区、区间id
     */
    public String getPsaId() {
        return psaId;
    }
    /**
     * 设置：站区、区间名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：站区、区间名称
     */
    public String getName() {
        return name;
    }
}
