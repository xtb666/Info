package com.togest.domain;

import com.togest.util.Data;


/**
 * <p>Title: BdSubstationRange.java</p>
 * <p>Description: 供电范围</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年11月8日下午3:11:44
 * @version 1.0
 */
public class BdSubstationRange extends Data {

	private static final long serialVersionUID = 1L;

    //线路ID
	protected String lineId;
    //附件ID
	protected String fileId;
    //变电所ID
	protected String pavilionId;
    //供电臂ID
	protected String psPdId;
    //公里标起点
	protected String startGlb;
    //公里标终点
	protected String endGlb;
    //供电范围
	protected String powerSupplyRange;

    /**
     * 设置：线路ID
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取：线路ID
     */
    public String getLineId() {
        return lineId;
    }
    /**
     * 设置：附件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取：附件ID
     */
    public String getFileId() {
        return fileId;
    }
    /**
     * 设置：变电所ID
     */
    public void setPavilionId(String pavilionId) {
        this.pavilionId = pavilionId;
    }

    /**
     * 获取：变电所ID
     */
    public String getPavilionId() {
        return pavilionId;
    }
    /**
     * 设置：供电臂ID
     */
    public void setPsPdId(String psPdId) {
        this.psPdId = psPdId;
    }

    /**
     * 获取：供电臂ID
     */
    public String getPsPdId() {
        return psPdId;
    }
    /**
     * 设置：公里标起点
     */
    public void setStartGlb(String startGlb) {
        this.startGlb = startGlb;
    }

    /**
     * 获取：公里标起点
     */
    public String getStartGlb() {
        return startGlb;
    }
    /**
     * 设置：公里标终点
     */
    public void setEndGlb(String endGlb) {
        this.endGlb = endGlb;
    }

    /**
     * 获取：公里标终点
     */
    public String getEndGlb() {
        return endGlb;
    }
    /**
     * 设置：供电范围
     */
    public void setPowerSupplyRange(String powerSupplyRange) {
        this.powerSupplyRange = powerSupplyRange;
    }

    /**
     * 获取：供电范围
     */
    public String getPowerSupplyRange() {
        return powerSupplyRange;
    }
}
