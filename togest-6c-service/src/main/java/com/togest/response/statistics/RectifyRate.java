package com.togest.response.statistics;

import java.io.Serializable;

public class RectifyRate extends MoMAndYoY implements Serializable {

	protected Integer reformed = 0;//整改本月数量
	protected Integer reformedMom = 0;//环比整改数量(上月) 
	protected Integer reformedYoy = 0;//同比缺陷总数(去年同月)
	
	public Integer getReformed() {
		return reformed;
	}
	public void setReformed(Integer reformed) {
		this.reformed = reformed;
	}
	public Integer getReformedMom() {
		return reformedMom;
	}
	public void setReformedMom(Integer reformedMom) {
		this.reformedMom = reformedMom;
	}
	public Integer getReformedYoy() {
		return reformedYoy;
	}
	public void setReformedYoy(Integer reformedYoy) {
		this.reformedYoy = reformedYoy;
	}

}
