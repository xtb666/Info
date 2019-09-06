package com.togest.request;

import com.togest.common.util.string.StringUtil;
/**
 * <p>Title: Const.java</p>
 * <p>Description: 7000-8000转换字典配置</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年12月11日上午10:15:30
 * @version 1.0
 */
public interface Const {
	
	enum FaultTypeEnum{
		TR("137984165141", "137984194040", "T-R"),
		FR("137984165141", "137984210099", "F-R"),
		TF("137984165141", "137984224205", "T-F"),
		ZG("137984165141", "137984241036", "直供");
		
		private String gid;
		private String oid;
		private String name;
		
		FaultTypeEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(FaultTypeEnum faultTypeEnum : FaultTypeEnum.values()) {
					if(o.indexOf(faultTypeEnum.getOid()) != -1){
						return faultTypeEnum.getName();
					}
				}
			}
			return "";
		}
	}
	
	enum ProtectNameEnum{
		SD("137983346192", "137983481182", "速断"),
		GL("137983346192", "137983497145", "过流"),
		ONE_DUAN("137983346192", "137983514024", "距离Ⅰ段"),
		TWO_DUAN("137983346192", "137983528153", "距离Ⅱ段"),
		QZ("137983346192", "137983550056", "轻、重瓦斯"),
		CD("137983346192", "137983837108", "差动"),
		JXSY("137983346192", "137983942171", "进线失压"),
		ZBDY("137983346192", "137983967095", "主变低压电流"),
		DLZL("137983346192", "137983986067", "电流增量"),
		QT("137983346192", "137984008092", "其他");
		
		private String gid;
		private String oid;
		private String name;
		
		ProtectNameEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(ProtectNameEnum protectName : ProtectNameEnum.values()) {
					if(o.indexOf(protectName.getOid()) != -1){
						return protectName.getName();
					}
				}
			}
			return "";
		}
	}
	
	enum LocalWeatheEnum{
		DF("137981996201", "137982404153", "大风"),
		LY("137981996201", "137982430059", "雷雨"),
		YIN("137981996201", "137982579145", "阴"),
		YU("137981996201", "137982599074", "雨"),
		JING("137981996201", "137982608051", "晴");
		
		private String gid;
		private String oid;
		private String name;
		
		LocalWeatheEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(LocalWeatheEnum localWeather : LocalWeatheEnum.values()) {
					if(o.indexOf(localWeather.getOid()) != -1){
						return localWeather.getName();
					}
				}
			}
			return "";
		}
	}
	
	enum CoincidenceGateEnum{
		CG("137984369063", "137984463155", "成功"),
		SB("137984369063", "137984475075", "失败");
		
		private String gid;
		private String oid;
		private String name;
		
		CoincidenceGateEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(CoincidenceGateEnum coincidenceGate : CoincidenceGateEnum.values()) {
					if(o.indexOf(coincidenceGate.getOid()) != -1){
						return coincidenceGate.getName();
					}
				}
			}
			return "";
		}
	}
	
	enum LineSeparateEnum{
		SX("139110161035183", "139111823584071", "上行"),
		XX("139110161035183", "139117336294035", "下行"),
		DX("139110161035183", "139117357177018", "单线");
		
		private String gid;
		private String oid;
		private String name;
		
		LineSeparateEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(LineSeparateEnum lineSeparate : LineSeparateEnum.values()) {
					if(o.indexOf(lineSeparate.getOid()) != -1){
						return lineSeparate.getName();
					}
				}
			}
			return "";
		}
	}
	
	enum FlineInfoEnum{
		YYX("137999236116", "137999264121", "有影响"),
		WYX("137999236116", "137999274150", "无影响");
		
		private String gid;
		private String oid;
		private String name;
		
		FlineInfoEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(FlineInfoEnum flineInfo : FlineInfoEnum.values()) {
					if(o.indexOf(flineInfo.getOid()) != -1){
						return flineInfo.getName();
					}
				}
			}
			return "";
		}
	}
	
	enum FlineInfluenceEnum{
		EXCEPTION("137984618001", "137984728051", "有异常"),
		NO_EXCEPTION("137984618001", "137984739042", "无异常");
		
		private String gid;
		private String oid;
		private String name;
		
		FlineInfluenceEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(FlineInfluenceEnum flifneInfluence : FlineInfluenceEnum.values()) {
					if(o.indexOf(flifneInfluence.getOid()) != -1){
						return flifneInfluence.getName();
					}
				}
			}
			return "";
		}
	}

	enum SnoteEnum{
		BDS_SWD("137985151113", "137985679087", "变电所上网点"),
		FQS_SWD("137985151113", "137985694168", "分区所上网点"),
		AT_SWD("137985151113", "137985711164", "AT所上网点");
		
		private String gid;
		private String oid;
		private String name;
		
		SnoteEnum(String gid, String oid, String name) {
			this.gid = gid;
			this.oid = oid;
			this.name = name;
		}
		
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		public String getOid() {
			return oid;
		}
		public void setOid(String oid) {
			this.oid = oid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public static String getName(String o) {
			if(StringUtil.isNotBlank(o)) {
				for(SnoteEnum snote : SnoteEnum.values()) {
					if(o.indexOf(snote.getOid()) != -1){
						return snote.getName();
					}
				}
			}
			return "";
		}
	}

}
