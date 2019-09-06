package com.togest.domain;

import com.togest.util.Data;



public class BdTripVideoMenu extends Data {

	private static final long serialVersionUID = 1L;

    //线路id
    protected String lineId;
    //区间站场id
    protected String psaId;
    //站区名称
    protected String psaName; 
    //类别
    protected String kind;
    //公里标
    protected String glb;
    //间距
    protected String distance;
    //备注（设备区域）
    protected String mark;

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
     * 设置：类别
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * 获取：类别
     */
    public String getKind() {
        return kind;
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
     * 设置：间距
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * 获取：间距
     */
    public String getDistance() {
        return distance;
    }
    /**
     * 设置：备注（设备区域）
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * 获取：备注（设备区域）
     */
    public String getMark() {
        return mark;
    }
    /**
     * 设置：区间站场id
     */
    public void setPsaId(String psaId) {
        this.psaId = psaId;
    }

    /**
     * 获取：区间站场id
     */
    public String getPsaId() {
        return psaId;
    }
    
    public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
}
