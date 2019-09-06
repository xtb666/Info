package com.togest.domain;

import com.togest.util.Data;

/**
 * <p>Title: BdCorresStand.java</p>
 * <p>Description: 故标对应</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年11月8日下午4:01:05
 * @version 1.0
 */
public class BdCorresStand extends Data {

	protected static final long serialVersionUID = 1L;

    //线路ID
    protected String lineId;
    //变电所ID
    protected String pavilionId;
    //供电臂ID
    protected String psPdId;
    //断路器编号
    protected String breakerNumber;
    //馈线编号
    protected String feederNumber;
    //电流比
    protected String currentRatio;
    //电压比
    protected String voltageRatio;
    //供电臂长度（公里）
    protected String armLength;
    //供电线长度（公里）
    protected String lineLength;
    //接触网长度（公里）
    protected String networkLength;
    //供电线单位电抗（Ω）
    protected String unitPowerLine;
    //接触网单位电抗（Ω）
    protected String unitNetworkLine;
    //Ⅰ段总电抗（Ω）
    protected String totalReactance1;
    //Ⅱ段总电抗（Ω）
    protected String totalReactance2;
    //Ⅲ段总电抗（Ω）
    protected String totalReactance3;

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
     * 设置：断路器编号
     */
    public void setBreakerNumber(String breakerNumber) {
        this.breakerNumber = breakerNumber;
    }

    /**
     * 获取：断路器编号
     */
    public String getBreakerNumber() {
        return breakerNumber;
    }
    /**
     * 设置：馈线编号
     */
    public void setFeederNumber(String feederNumber) {
        this.feederNumber = feederNumber;
    }

    /**
     * 获取：馈线编号
     */
    public String getFeederNumber() {
        return feederNumber;
    }
    /**
     * 设置：电流比
     */
    public void setCurrentRatio(String currentRatio) {
        this.currentRatio = currentRatio;
    }

    /**
     * 获取：电流比
     */
    public String getCurrentRatio() {
        return currentRatio;
    }
    /**
     * 设置：电压比
     */
    public void setVoltageRatio(String voltageRatio) {
        this.voltageRatio = voltageRatio;
    }

    /**
     * 获取：电压比
     */
    public String getVoltageRatio() {
        return voltageRatio;
    }
    /**
     * 设置：供电臂长度（公里）
     */
    public void setArmLength(String armLength) {
        this.armLength = armLength;
    }

    /**
     * 获取：供电臂长度（公里）
     */
    public String getArmLength() {
        return armLength;
    }
    /**
     * 设置：供电线长度（公里）
     */
    public void setLineLength(String lineLength) {
        this.lineLength = lineLength;
    }

    /**
     * 获取：供电线长度（公里）
     */
    public String getLineLength() {
        return lineLength;
    }
    /**
     * 设置：接触网长度（公里）
     */
    public void setNetworkLength(String networkLength) {
        this.networkLength = networkLength;
    }

    /**
     * 获取：接触网长度（公里）
     */
    public String getNetworkLength() {
        return networkLength;
    }
    /**
     * 设置：供电线单位电抗（Ω）
     */
    public void setUnitPowerLine(String unitPowerLine) {
        this.unitPowerLine = unitPowerLine;
    }

    /**
     * 获取：供电线单位电抗（Ω）
     */
    public String getUnitPowerLine() {
        return unitPowerLine;
    }
    /**
     * 设置：接触网单位电抗（Ω）
     */
    public void setUnitNetworkLine(String unitNetworkLine) {
        this.unitNetworkLine = unitNetworkLine;
    }

    /**
     * 获取：接触网单位电抗（Ω）
     */
    public String getUnitNetworkLine() {
        return unitNetworkLine;
    }
    /**
     * 设置：Ⅰ段总电抗（Ω）
     */
    public void setTotalReactance1(String totalReactance1) {
        this.totalReactance1 = totalReactance1;
    }

    /**
     * 获取：Ⅰ段总电抗（Ω）
     */
    public String getTotalReactance1() {
        return totalReactance1;
    }
    /**
     * 设置：Ⅱ段总电抗（Ω）
     */
    public void setTotalReactance2(String totalReactance2) {
        this.totalReactance2 = totalReactance2;
    }

    /**
     * 获取：Ⅱ段总电抗（Ω）
     */
    public String getTotalReactance2() {
        return totalReactance2;
    }
    /**
     * 设置：Ⅲ段总电抗（Ω）
     */
    public void setTotalReactance3(String totalReactance3) {
        this.totalReactance3 = totalReactance3;
    }

    /**
     * 获取：Ⅲ段总电抗（Ω）
     */
    public String getTotalReactance3() {
        return totalReactance3;
    }
}
