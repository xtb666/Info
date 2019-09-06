package com.togest.response.statistics;

import java.io.Serializable;

public class MoMAndYoY implements Serializable{

	protected String name;//段名称 or 线路名
	protected Integer num = 0;//当前月
	protected Integer mom = 0;//环比(上月) 
	protected Integer yoy = 0;//同比(去年同月)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getMom() {
		return mom;
	}
	public void setMom(Integer mom) {
		this.mom = mom;
	}
	public Integer getYoy() {
		return yoy;
	}
	public void setYoy(Integer yoy) {
		this.yoy = yoy;
	}

}
