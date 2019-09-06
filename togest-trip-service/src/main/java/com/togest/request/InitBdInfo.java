package com.togest.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.togest.domain.upgrade.DataCommonEntity;

public class InitBdInfo extends DataCommonEntity{
	
	protected static final long serialVersionUID = 1L;
	
	//数据id
    protected Long dataId;
    //线别
    protected Long line;
    //附件
    protected String attach;
    
    //添加时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")   
	protected Date addTime;
    //添加人ID
    protected Long addUid;
    //添加IP
    protected String addIp;
    //修改时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")   
	protected Date updateTime;
    //修改人员ID
    protected Long updateUid;
    //是否假删
    protected Integer isDelete;
    //变电所
    protected Long substation;
    //变电所名称
    protected String substationName;
    //供电臂
    protected Long arm;
    //供电臂名称
    protected String armName;
    
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

    //数据id
    protected Long slaveId;
    //区间或站场
    protected String psaName;
    //杆号
    protected String pillar;
    //公里标
    protected String kilometer;
    //跨距（m）
    protected String span;
    //距所距离（公里）
    protected String distance;
    //电抗值
    protected String issInductance;
    //
    protected String note;
	
    //CT变比
    protected String ctRatio;
    //PT变比
    protected String ptRatio;
    //阻抗I段电阻(二次值)
    protected String impedanceResistance;
    
    protected String name;
    
    //时间
    protected String time;
    //当地天气
    protected String localWeather;
    //断路器编号
    protected String breakerNumber;
    //保护名称
    protected String protect;
    //故障类型
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
    protected String lineSeparate;
    //合成电压
    protected String composeVoltage;
    //合成电流
    protected String composeCurrent;
    //动作阻抗
    protected String dzResistance;
    
    //
    protected Long fileId;
    //
    protected String fileName;
    //
    protected String realName;
    //
    protected String fileExt;
    //
    protected Integer fileSize;
    //
    protected String fileUrl;
    //
    protected String fileSwf;
    
    protected Long plId;
    //线别名称
    protected String plLine;
    //线别类型
    protected Long lineType;
    //对应p_option中的速度级别的值
    protected Long speedType;
    //经纬度
    protected String plCoordinates;
    //线别排序
    protected Integer plSort;
    
    //供电臂id
    protected Long psaId;
    //供电臂标题
    protected String psaTitle;
    //分类:供电臂；区间 ；站场
    protected Long psaType;
    //排序
    protected Integer psaSort;
    
    //公里标起点
    protected String startKilometer;
    //公里标终点
    protected String endKilometer;
    //供电范围
    protected String substationRange;
    
    //类别
    protected String kind;
    //站场
    protected String station;
    //备注
    protected String mark2; 
    
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public Long getLine() {
		return line;
	}
	public void setLine(Long line) {
		this.line = line;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Long getAddUid() {
		return addUid;
	}
	public void setAddUid(Long addUid) {
		this.addUid = addUid;
	}
	public String getAddIp() {
		return addIp;
	}
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(Long updateUid) {
		this.updateUid = updateUid;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Long getSubstation() {
		return substation;
	}
	public void setSubstation(Long substation) {
		this.substation = substation;
	}
	public String getSubstationName() {
		return substationName;
	}
	public void setSubstationName(String substationName) {
		this.substationName = substationName;
	}
	public Long getArm() {
		return arm;
	}
	public void setArm(Long arm) {
		this.arm = arm;
	}
	public String getArmName() {
		return armName;
	}
	public void setArmName(String armName) {
		this.armName = armName;
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
	public Long getSlaveId() {
		return slaveId;
	}
	public void setSlaveId(Long slaveId) {
		this.slaveId = slaveId;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	public String getPillar() {
		return pillar;
	}
	public void setPillar(String pillar) {
		this.pillar = pillar;
	}
	public String getKilometer() {
		return kilometer;
	}
	public void setKilometer(String kilometer) {
		this.kilometer = kilometer;
	}
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getIssInductance() {
		return issInductance;
	}
	public void setIssInductance(String issInductance) {
		this.issInductance = issInductance;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileSwf() {
		return fileSwf;
	}
	public void setFileSwf(String fileSwf) {
		this.fileSwf = fileSwf;
	}
	public Long getPlId() {
		return plId;
	}
	public void setPlId(Long plId) {
		this.plId = plId;
	}
	public String getPlLine() {
		return plLine;
	}
	public void setPlLine(String plLine) {
		this.plLine = plLine;
	}
	public Long getLineType() {
		return lineType;
	}
	public void setLineType(Long lineType) {
		this.lineType = lineType;
	}
	public Long getSpeedType() {
		return speedType;
	}
	public void setSpeedType(Long speedType) {
		this.speedType = speedType;
	}
	public String getPlCoordinates() {
		return plCoordinates;
	}
	public void setPlCoordinates(String plCoordinates) {
		this.plCoordinates = plCoordinates;
	}
	public Integer getPlSort() {
		return plSort;
	}
	public void setPlSort(Integer plSort) {
		this.plSort = plSort;
	}
	public Long getPsaId() {
		return psaId;
	}
	public void setPsaId(Long psaId) {
		this.psaId = psaId;
	}
	public String getPsaTitle() {
		return psaTitle;
	}
	public void setPsaTitle(String psaTitle) {
		this.psaTitle = psaTitle;
	}
	public Long getPsaType() {
		return psaType;
	}
	public void setPsaType(Long psaType) {
		this.psaType = psaType;
	}
	public Integer getPsaSort() {
		return psaSort;
	}
	public void setPsaSort(Integer psaSort) {
		this.psaSort = psaSort;
	}
	public String getStartKilometer() {
		return startKilometer;
	}
	public void setStartKilometer(String startKilometer) {
		this.startKilometer = startKilometer;
	}
	public String getEndKilometer() {
		return endKilometer;
	}
	public void setEndKilometer(String endKilometer) {
		this.endKilometer = endKilometer;
	}
	public String getSubstationRange() {
		return substationRange;
	}
	public void setSubstationRange(String substationRange) {
		this.substationRange = substationRange;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getMark2() {
		return mark2;
	}
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}
	public String getLineSeparate() {
		return lineSeparate;
	}
	public void setLineSeparate(String lineSeparate) {
		this.lineSeparate = lineSeparate;
	}
}