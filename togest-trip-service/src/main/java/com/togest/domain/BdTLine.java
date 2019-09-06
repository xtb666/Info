package com.togest.domain;

import com.togest.util.Data;


/**
 * <p>Title: BdTLine.java</p>
 * <p>Description: 供电臂T线电流标准</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年11月8日下午3:12:08
 * @version 1.0
 */
public class BdTLine extends Data {

	protected static final long serialVersionUID = 1L;

    //线路ID
    protected String lineId;
    //变电所ID
    protected String pavilionId;
    //供电臂ID
    protected String psPdId;
    //T线电流
    protected String tlineCurrent;
    //CT变比
    protected String ctRatio;
    //PT变比
    protected String ptRatio;
    //阻抗I段电阻(二次值)
    protected String impedanceResistance;

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
     * 设置：T线电流
     */
    public void setTlineCurrent(String tlineCurrent) {
		this.tlineCurrent = tlineCurrent;
	}
    /**
     * 获取：T线电流
     */
    public String getTlineCurrent() {
		return tlineCurrent;
	}
    
    /**
     * 设置：CT变比
     */
    public void setCtRatio(String ctRatio) {
        this.ctRatio = ctRatio;
    }
	/**
     * 获取：CT变比
     */
    public String getCtRatio() {
        return ctRatio;
    }
    /**
     * 设置：PT变比
     */
    public void setPtRatio(String ptRatio) {
        this.ptRatio = ptRatio;
    }

    /**
     * 获取：PT变比
     */
    public String getPtRatio() {
        return ptRatio;
    }
    /**
     * 设置：阻抗I段电阻(二次值)
     */
    public void setImpedanceResistance(String impedanceResistance) {
        this.impedanceResistance = impedanceResistance;
    }

    /**
     * 获取：阻抗I段电阻(二次值)
     */
    public String getImpedanceResistance() {
        return impedanceResistance;
    }
}
