package com.togest.domain.init;

import com.togest.domain.upgrade.DataCommonEntity;



public class PTripDepartmentAlias extends DataCommonEntity {

	private static final long serialVersionUID = 1L;

    //部门id
    private String deptId;
    //名称
    private String name;

    /**
     * 设置：部门id
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取：部门id
     */
    public String getDeptId() {
        return deptId;
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
