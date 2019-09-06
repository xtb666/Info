package com.togest.domain;

import com.togest.domain.upgrade.DataCommonEntity;



public class EquPosition extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //关联设备
    private String equId;
    //编码
    private String code;
    //设备位置名
    private String name;
    //C类型
    private String systemId;
    private String systemName;
    //备注
    private String remark;

    /**
     * 设置：关联设备
     */
    public void setEquId(String equId) {
        this.equId = equId;
    }

    /**
     * 获取：关联设备
     */
    public String getEquId() {
        return equId;
    }
    /**
     * 设置：编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取：编码
     */
    public String getCode() {
        return code;
    }
    /**
     * 设置：设备位置名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：设备位置名
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：C类型
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * 获取：C类型
     */
    public String getSystemId() {
        return systemId;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
