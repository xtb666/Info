package com.togest.request;

import com.togest.domain.SimplePage;
import com.togest.util.Data;

public class BdInfoQueryFilter extends Data {
	
	protected static final long serialVersionUID = 1L;
	
	protected SimplePage page; //PageUtis时用到
	protected String lineId; //线路id
	protected String fileId; //附件id
	protected String pavilionId;//变电所id
	protected String psPdId; //供电臂id
    protected String psaId; //区间站场id
	//线路名称
	protected String lineName; 
	//附件名称
	protected String fileName; 
	 //变电所名称
	protected String pavilionName;
	//供电臂名称
	protected String psPdName;  
	//站区名称
	protected String psaName;
	 //名称
	protected String name;
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
	//公里标起点
	protected String startGlb;
    //公里标终点
	protected String endGlb;
    //供电范围
	protected String powerSupplyRange;
    //CT变比
	protected String ctRatio;
    //PT变比
	protected String ptRatio;
    //阻抗I段电阻(二次值)
	protected String impedanceResistance;
	
	//时间
	protected String time;
    //当地天气
	protected String localWeather;
    //保护名称
	protected String protect;
    //故障类型
	protected String faultType;
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
    protected String coincidenceGate;
    //复送时间
    protected String sendTime;
    //供电臂内运行车辆数
    protected String armRunNum;
    //F线重合成功后了解动车组是否有异常
    protected String flineInfluence;
    //F线重合成功后影响行车信息
    protected String flineInfo;
    //外来信息
    protected String foreignInfor;
    //接触网故障点
    protected String jcwFaultPoint;
    //通知行调限速要求
    protected String speedLimitRequir;
    //行别
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
    
    //类别
    protected String kind;
    //公里标
    protected String glb;
    //间距
    protected String distance;
	
	public SimplePage getPage() {
		return page;
	}
	public void setPage(SimplePage page) {
		this.page = page;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPavilionId() {
		return pavilionId;
	}
	public void setPavilionId(String pavilionId) {
		this.pavilionId = pavilionId;
	}
	public String getPsPdId() {
		return psPdId;
	}
	public void setPsPdId(String psPdId) {
		this.psPdId = psPdId;
	}
	public String getPavilionName() {
		return pavilionName;
	}
	public void setPavilionName(String pavilionName) {
		this.pavilionName = pavilionName;
	}
	public String getPsPdName() {
		return psPdName;
	}
	public void setPsPdName(String psPdName) {
		this.psPdName = psPdName;
	}
	
	public String getPsaId() {
		return psaId;
	}
	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}
	public String getFeederNumber() {
		return feederNumber;
	}
	public void setFeederNumber(String feederNumber) {
		this.feederNumber = feederNumber;
	}
	public String getCurrentRatio() {
		return currentRatio;
	}
	public void setCurrentRatio(String currentRatio) {
		this.currentRatio = currentRatio;
	}
	public String getVoltageRatio() {
		return voltageRatio;
	}
	public void setVoltageRatio(String voltageRatio) {
		this.voltageRatio = voltageRatio;
	}
	public String getArmLength() {
		return armLength;
	}
	public void setArmLength(String armLength) {
		this.armLength = armLength;
	}
	public String getLineLength() {
		return lineLength;
	}
	public void setLineLength(String lineLength) {
		this.lineLength = lineLength;
	}
	public String getNetworkLength() {
		return networkLength;
	}
	public void setNetworkLength(String networkLength) {
		this.networkLength = networkLength;
	}
	public String getUnitPowerLine() {
		return unitPowerLine;
	}
	public void setUnitPowerLine(String unitPowerLine) {
		this.unitPowerLine = unitPowerLine;
	}
	public String getUnitNetworkLine() {
		return unitNetworkLine;
	}
	public void setUnitNetworkLine(String unitNetworkLine) {
		this.unitNetworkLine = unitNetworkLine;
	}
	public String getTotalReactance1() {
		return totalReactance1;
	}
	public void setTotalReactance1(String totalReactance1) {
		this.totalReactance1 = totalReactance1;
	}
	public String getTotalReactance2() {
		return totalReactance2;
	}
	public void setTotalReactance2(String totalReactance2) {
		this.totalReactance2 = totalReactance2;
	}
	public String getTotalReactance3() {
		return totalReactance3;
	}
	public void setTotalReactance3(String totalReactance3) {
		this.totalReactance3 = totalReactance3;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getGlb() {
		return glb;
	}
	public void setGlb(String glb) {
		this.glb = glb;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartGlb() {
		return startGlb;
	}
	public void setStartGlb(String startGlb) {
		this.startGlb = startGlb;
	}
	public String getEndGlb() {
		return endGlb;
	}
	public void setEndGlb(String endGlb) {
		this.endGlb = endGlb;
	}
	public String getPowerSupplyRange() {
		return powerSupplyRange;
	}
	public String getCtRatio() {
		return ctRatio;
	}
	public void setCtRatio(String ctRatio) {
		this.ctRatio = ctRatio;
	}
	public String getPtRatio() {
		return ptRatio;
	}
	public void setPtRatio(String ptRatio) {
		this.ptRatio = ptRatio;
	}
	public String getImpedanceResistance() {
		return impedanceResistance;
	}
	public void setImpedanceResistance(String impedanceResistance) {
		this.impedanceResistance = impedanceResistance;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocalWeather() {
		return localWeather;
	}
	public void setLocalWeather(String localWeather) {
		this.localWeather = localWeather;
	}
	public String getBreakerNumber() {
		return breakerNumber;
	}
	public void setBreakerNumber(String breakerNumber) {
		this.breakerNumber = breakerNumber;
	}
	public String getProtect() {
		return protect;
	}
	public void setProtect(String protect) {
		this.protect = protect;
	}
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	
	public String getImpedanceAngle() {
		return impedanceAngle;
	}
	public void setImpedanceAngle(String impedanceAngle) {
		this.impedanceAngle = impedanceAngle;
	}
	public String getStandardDistance() {
		return standardDistance;
	}
	public void setStandardDistance(String standardDistance) {
		this.standardDistance = standardDistance;
	}
	public String getCoincidenceGate() {
		return coincidenceGate;
	}
	public void setCoincidenceGate(String coincidenceGate) {
		this.coincidenceGate = coincidenceGate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getArmRunNum() {
		return armRunNum;
	}
	public void setArmRunNum(String armRunNum) {
		this.armRunNum = armRunNum;
	}
	public String getTlineVoltage() {
		return tlineVoltage;
	}
	public void setTlineVoltage(String tlineVoltage) {
		this.tlineVoltage = tlineVoltage;
	}
	public String getFlineVoltage() {
		return flineVoltage;
	}
	public void setFlineVoltage(String flineVoltage) {
		this.flineVoltage = flineVoltage;
	}
	public String getTlineCurrent() {
		return tlineCurrent;
	}
	public void setTlineCurrent(String tlineCurrent) {
		this.tlineCurrent = tlineCurrent;
	}
	public String getFlineCurrent() {
		return flineCurrent;
	}
	public void setFlineCurrent(String flineCurrent) {
		this.flineCurrent = flineCurrent;
	}
	public String getFlineInfluence() {
		return flineInfluence;
	}
	public void setFlineInfluence(String flineInfluence) {
		this.flineInfluence = flineInfluence;
	}
	public String getFlineInfo() {
		return flineInfo;
	}
	public void setFlineInfo(String flineInfo) {
		this.flineInfo = flineInfo;
	}
	public void setPowerSupplyRange(String powerSupplyRange) {
		this.powerSupplyRange = powerSupplyRange;
	}
	public String getForeignInfor() {
		return foreignInfor;
	}
	public void setForeignInfor(String foreignInfor) {
		this.foreignInfor = foreignInfor;
	}
	public String getJcwFaultPoint() {
		return jcwFaultPoint;
	}
	public void setJcwFaultPoint(String jcwFaultPoint) {
		this.jcwFaultPoint = jcwFaultPoint;
	}
	public String getSpeedLimitRequir() {
		return speedLimitRequir;
	}
	public void setSpeedLimitRequir(String speedLimitRequir) {
		this.speedLimitRequir = speedLimitRequir;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPowerCutRange() {
		return powerCutRange;
	}
	public void setPowerCutRange(String powerCutRange) {
		this.powerCutRange = powerCutRange;
	}
	public String getKiloDistance() {
		return kiloDistance;
	}
	public void setKiloDistance(String kiloDistance) {
		this.kiloDistance = kiloDistance;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getComposeVoltage() {
		return composeVoltage;
	}
	public void setComposeVoltage(String composeVoltage) {
		this.composeVoltage = composeVoltage;
	}
	public String getComposeCurrent() {
		return composeCurrent;
	}
	public void setComposeCurrent(String composeCurrent) {
		this.composeCurrent = composeCurrent;
	}
	public String getDzResistance() {
		return dzResistance;
	}
	public void setDzResistance(String dzResistance) {
		this.dzResistance = dzResistance;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
}