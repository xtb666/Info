package com.togest.domain;

import java.util.List;

import com.togest.response.DefectResponse;

public class NoticeSectionDTO extends NoticeSection<NoticeSectionDTO> {

	protected List<DefectResponse> defects;
	protected NoticeDTO notice;
	protected String sectionName;
	protected String flowId;
	protected String statusName;
	protected String receiptPersonName;
	protected String feedbackPersonName;
	
	public NoticeDTO getNotice() {
		return notice;
	}
	public void setNotice(NoticeDTO notice) {
		this.notice = notice;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getReceiptPersonName() {
		return receiptPersonName;
	}
	public void setReceiptPersonName(String receiptPersonName) {
		this.receiptPersonName = receiptPersonName;
	}
	public List<DefectResponse> getDefects() {
		return defects;
	}
	public void setDefects(List<DefectResponse> defects) {
		this.defects = defects;
	}
	public String getFeedbackPersonName() {
		return feedbackPersonName;
	}
	public void setFeedbackPersonName(String feedbackPersonName) {
		this.feedbackPersonName = feedbackPersonName;
	}
	
}
