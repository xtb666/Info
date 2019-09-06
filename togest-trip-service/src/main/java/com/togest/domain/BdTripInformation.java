package com.togest.domain;

import com.togest.dict.annotation.DictMark;
import com.togest.util.Data;



public class BdTripInformation extends Data {

	protected static final long serialVersionUID = 1L;

	//线路ID
    protected String lineId;
    //变电所ID
    protected String pavilionId;
    //供电臂ID
    protected String psPdId;
    //时间
    protected String time;
    //当地天气
    @DictMark(dictName="local_weather", primaryKey="code")
    protected String localWeather;
    //断路器编号
    protected String breakerNumber;
    //保护名称
    @DictMark(dictName="protect_name", primaryKey="code")
    protected String protect;
    //故障类型
    @DictMark(dictName="fault_type", primaryKey="code")
    protected String faultType;
    //T线电压（KV）
    protected String tlineVoltage;
    //F线电压（KV）
    protected String flineVoltage;
    //T线电流（A）
    protected String tlineCurrent;
    //F线电流（A）
    protected String flineCurrent;
    //阻抗角（度）
    protected String impedanceAngle;
    //故标距离
    protected String standardDistance;
    //重合闸情况
    @DictMark(dictName="coincidence_gate", primaryKey="code")
    protected String coincidenceGate;
    //复送时间
    protected String sendTime;
    //供电臂内运行车辆数
    protected String armRunNum;
    //F线重合成功后了解动车组是否有异常
    @DictMark(dictName="f_line_influence", primaryKey="code")
    protected String flineInfluence;
    //F线重合成功后影响行车信息
    @DictMark(dictName="f_line_info", primaryKey="code")
    protected String flineInfo;
    //外来信息
    protected String foreignInfor;
    //接触网故障点
    protected String jcwFaultPoint;
    //通知行调限速要求
    protected String speedLimitRequir;
    //行别
    @DictMark(dictName = "direction", itemName = "directionName")
    protected String direction;
    //停电范围
    protected String powerCutRange;
    //
    protected String kiloDistance;
    //
    protected String mark;
    //合成电压
    protected String composeVoltage;
    //合成电流
    protected String composeCurrent;
    //动作阻抗
    protected String dzResistance;

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
     * 设置：时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取：时间
     */
    public String getTime() {
        return time;
    }
    /**
     * 设置：当地天气
     */
    public void setLocalWeather(String localWeather) {
        this.localWeather = localWeather;
    }

    /**
     * 获取：当地天气
     */
    public String getLocalWeather() {
        return localWeather;
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
    
    public String getProtect() {
		return protect;
	}

	public void setProtect(String protect) {
		this.protect = protect;
	}

	/**
     * 设置：故障类型
     */
    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    /**
     * 获取：故障类型
     */
    public String getFaultType() {
        return faultType;
    }
    /**
     * 设置：T线电压（KV）
     */
    public void setTlineVoltage(String tlineVoltage) {
        this.tlineVoltage = tlineVoltage;
    }

    /**
     * 获取：T线电压（KV）
     */
    public String getTlineVoltage() {
        return tlineVoltage;
    }
    /**
     * 设置：F线电压（KV）
     */
    public void setFlineVoltage(String flineVoltage) {
        this.flineVoltage = flineVoltage;
    }

    /**
     * 获取：F线电压（KV）
     */
    public String getFlineVoltage() {
        return flineVoltage;
    }
    /**
     * 设置：T线电流（A）
     */
    public void setTlineCurrent(String tlineCurrent) {
        this.tlineCurrent = tlineCurrent;
    }

    /**
     * 获取：T线电流（A）
     */
    public String getTlineCurrent() {
        return tlineCurrent;
    }
    /**
     * 设置：F线电流（A）
     */
    public void setFlineCurrent(String flineCurrent) {
        this.flineCurrent = flineCurrent;
    }

    /**
     * 获取：F线电流（A）
     */
    public String getFlineCurrent() {
        return flineCurrent;
    }
    /**
     * 设置：阻抗角（度）
     */
    public void setImpedanceAngle(String impedanceAngle) {
        this.impedanceAngle = impedanceAngle;
    }

    /**
     * 获取：阻抗角（度）
     */
    public String getImpedanceAngle() {
        return impedanceAngle;
    }
    /**
     * 设置：故标距离
     */
    public void setStandardDistance(String standardDistance) {
        this.standardDistance = standardDistance;
    }

    /**
     * 获取：故标距离
     */
    public String getStandardDistance() {
        return standardDistance;
    }
    /**
     * 设置：重合闸情况
     */
    public void setCoincidenceGate(String coincidenceGate) {
        this.coincidenceGate = coincidenceGate;
    }

    /**
     * 获取：重合闸情况
     */
    public String getCoincidenceGate() {
        return coincidenceGate;
    }
    /**
     * 设置：复送时间
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取：复送时间
     */
    public String getSendTime() {
        return sendTime;
    }
    /**
     * 设置：供电臂内运行车辆数
     */
    public void setArmRunNum(String armRunNum) {
        this.armRunNum = armRunNum;
    }

    /**
     * 获取：供电臂内运行车辆数
     */
    public String getArmRunNum() {
        return armRunNum;
    }
    /**
     * 设置：F线重合成功后了解动车组是否有异常
     */
    public void setFlineInfluence(String flineInfluence) {
        this.flineInfluence = flineInfluence;
    }

    /**
     * 获取：F线重合成功后了解动车组是否有异常
     */
    public String getFlineInfluence() {
        return flineInfluence;
    }
    /**
     * 设置：F线重合成功后影响行车信息
     */
    public void setFlineInfo(String flineInfo) {
        this.flineInfo = flineInfo;
    }

    /**
     * 获取：F线重合成功后影响行车信息
     */
    public String getFlineInfo() {
        return flineInfo;
    }
    /**
     * 设置：外来信息
     */
    public void setForeignInfor(String foreignInfor) {
        this.foreignInfor = foreignInfor;
    }

    /**
     * 获取：外来信息
     */
    public String getForeignInfor() {
        return foreignInfor;
    }
    /**
     * 设置：接触网故障点
     */
    public void setJcwFaultPoint(String jcwFaultPoint) {
        this.jcwFaultPoint = jcwFaultPoint;
    }

    /**
     * 获取：接触网故障点
     */
    public String getJcwFaultPoint() {
        return jcwFaultPoint;
    }
    /**
     * 设置：通知行调限速要求
     */
    public void setSpeedLimitRequir(String speedLimitRequir) {
        this.speedLimitRequir = speedLimitRequir;
    }

    /**
     * 获取：通知行调限速要求
     */
    public String getSpeedLimitRequir() {
        return speedLimitRequir;
    }
    /**
     * 设置：行别
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 获取：行别
     */
    public String getDirection() {
        return direction;
    }
    /**
     * 设置：停电范围
     */
    public void setPowerCutRange(String powerCutRange) {
        this.powerCutRange = powerCutRange;
    }

    /**
     * 获取：停电范围
     */
    public String getPowerCutRange() {
        return powerCutRange;
    }
    /**
     * 设置：
     */
    public void setKiloDistance(String kiloDistance) {
        this.kiloDistance = kiloDistance;
    }

    /**
     * 获取：
     */
    public String getKiloDistance() {
        return kiloDistance;
    }
    /**
     * 设置：
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * 获取：
     */
    public String getMark() {
        return mark;
    }
    /**
     * 设置：合成电压
     */
    public void setComposeVoltage(String composeVoltage) {
        this.composeVoltage = composeVoltage;
    }

    /**
     * 获取：合成电压
     */
    public String getComposeVoltage() {
        return composeVoltage;
    }
    /**
     * 设置：合成电流
     */
    public void setComposeCurrent(String composeCurrent) {
        this.composeCurrent = composeCurrent;
    }

    /**
     * 获取：合成电流
     */
    public String getComposeCurrent() {
        return composeCurrent;
    }
    /**
     * 设置：动作阻抗
     */
    public void setDzResistance(String dzResistance) {
        this.dzResistance = dzResistance;
    }

    /**
     * 获取：动作阻抗
     */
    public String getDzResistance() {
        return dzResistance;
    }
}
