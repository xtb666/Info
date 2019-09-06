package com.togest;

public interface WireHeightConsts {

	enum StandarLineHieghtEnum{
		STANDAR_LINE_HEIGHT_UP_DEFAULT("上限值", 6480),
		STANDAR_LINE_HEIGHT_DOWN_DEFAULT("下限值", 6370);
		
		private String name;
		private Integer defaultValue;
		
		StandarLineHieghtEnum(String name, Integer defaultValue) {
			this.name = name;
			this.defaultValue = defaultValue;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(Integer defaultValue) {
			this.defaultValue = defaultValue;
		}
	}
}
