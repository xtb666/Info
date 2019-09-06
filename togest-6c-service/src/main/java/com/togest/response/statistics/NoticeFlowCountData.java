package com.togest.response.statistics;

import java.io.Serializable;

public class NoticeFlowCountData implements Serializable {

	protected Integer num = 0;
	protected Integer sendNum = 0;
	protected Integer sendedNum = 0;
	protected Integer cancelNum = 0;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getSendedNum() {
		return sendedNum;
	}
	public void setSendedNum(Integer sendedNum) {
		this.sendedNum = sendedNum;
	}
	public Integer getCancelNum() {
		return cancelNum;
	}
	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}
	public Integer getSendNum() {
		return sendNum;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	
	
}
