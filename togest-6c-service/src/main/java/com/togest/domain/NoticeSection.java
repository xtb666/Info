package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;

public class NoticeSection<T> extends BaseEntity<T> {
	
	protected String id;
	protected String sectionId;
	@DictMark(dictName="notice_status",primaryKey="code")
	protected String status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date sendDate;
	protected String sendPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date receiptDate;
	protected String receiptPerson;
	protected Integer overtime = 0;
	protected String feedbackContent;
	protected String feedbackPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date feedbackDate;
	protected String noticeId;
	protected int delFlag = 0;
	@JsonIgnore
	protected String deleteIp;
	@JsonIgnore
	protected String deleteBy;
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date deleteDate;
	
	
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendPerson() {
		return sendPerson;
	}
	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getReceiptPerson() {
		return receiptPerson;
	}
	public void setReceiptPerson(String receiptPerson) {
		this.receiptPerson = receiptPerson;
	}
	public Integer getOvertime() {
		return overtime;
	}
	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public String getFeedbackPerson() {
		return feedbackPerson;
	}
	public void setFeedbackPerson(String feedbackPerson) {
		this.feedbackPerson = feedbackPerson;
	}
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public String getDeleteIp() {
		return deleteIp;
	}
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

}
