package com.togest.request;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import com.togest.common.util.string.StringUtil;
import com.togest.util.Data;

public class NoticeQueryFilter extends Data {
	
	protected String id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date issueDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginIssueDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endIssueDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date checkDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCheckDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date sendDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date receiptDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginReceiptDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endReceiptDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date feedbackDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginFeedbackDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endFeedbackDate;
	
	protected Integer overtime;
	protected String issuePerson;
	protected String responsiblePerson;
	protected String sendPerson;
	protected String receiptPerson;
	protected String feedbackPerson;
	protected String defectLevel;
	protected String defectStatus;
	protected String systemId;
	protected String noticeStatus;
	protected String status;
	protected String number;//通知书编号
	protected String noticeNumberYear;
	protected String noticeNumberStr;
	@JsonIgnore
	protected List<String> statusList;
	@JsonIgnore
	protected List<String> noticeStatusList;

	
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
	public String getNoticeNumberYear() {
		String str = this.getNumber();
		if (StringUtil.isNotBlank(str)) {
			String trim = str.trim();
			if(trim.length()==9) {
				return trim.substring(0, 4);
			} else {
				return str;
			}
		}
		return noticeNumberYear;
	}
	public void setNoticeNumberYear(String noticeNumberYear) {
		this.noticeNumberYear = noticeNumberYear;
	}
	public String getNoticeNumberStr() {
		String str = this.getNumber();
		if (StringUtil.isNotBlank(str)) {
			String trim = str.trim();
			if(trim.length()==9) {
				return trim.substring(5, 8);
			} else {
				return str;
			}
		}
		return noticeNumberStr;
	}
	public void setNoticeNumberStr(String noticeNumberStr) {
		this.noticeNumberStr = noticeNumberStr;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<String> getStatusList() {
		if (StringUtil.isNotBlank(this.getStatus())) {
			return Arrays.asList(this.getStatus().split(","));
		}
		return statusList;
	}
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	public List<String> getNoticeStatusList() {
		if (StringUtil.isNotBlank(this.getNoticeStatus())) {
			return Arrays.asList(this.getNoticeStatus().split(","));
		}
		return noticeStatusList;
	}
	public void setNoticeStatusList(List<String> noticeStatusList) {
		this.noticeStatusList = noticeStatusList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Date getBeginIssueDate() {
		return beginIssueDate;
	}
	public void setBeginIssueDate(Date beginIssueDate) {
		this.beginIssueDate = beginIssueDate;
	}
	public Date getEndIssueDate() {
		return endIssueDate;
	}
	public void setEndIssueDate(Date endIssueDate) {
		this.endIssueDate = endIssueDate;
	}
	public String getDefectLevel() {
		return defectLevel;
	}
	public void setDefectLevel(String defectLevel) {
		this.defectLevel = defectLevel;
	}
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getIssuePerson() {
		return issuePerson;
	}
	public void setIssuePerson(String issuePerson) {
		this.issuePerson = issuePerson;
	}
	public String getResponsiblePerson() {
		return responsiblePerson;
	}
	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBeginReceiptDate() {
		return beginReceiptDate;
	}
	public void setBeginReceiptDate(Date beginReceiptDate) {
		this.beginReceiptDate = beginReceiptDate;
	}
	public Date getEndReceiptDate() {
		return endReceiptDate;
	}
	public void setEndReceiptDate(Date endReceiptDate) {
		this.endReceiptDate = endReceiptDate;
	}
	public Date getBeginCheckDate() {
		return beginCheckDate;
	}
	public void setBeginCheckDate(Date beginCheckDate) {
		this.beginCheckDate = beginCheckDate;
	}
	public Date getEndCheckDate() {
		return endCheckDate;
	}
	public void setEndCheckDate(Date endCheckDate) {
		this.endCheckDate = endCheckDate;
	}
	public Date getBeginFeedbackDate() {
		return beginFeedbackDate;
	}
	public void setBeginFeedbackDate(Date beginFeedbackDate) {
		this.beginFeedbackDate = beginFeedbackDate;
	}
	public Date getEndFeedbackDate() {
		return endFeedbackDate;
	}
	public void setEndFeedbackDate(Date endFeedbackDate) {
		this.endFeedbackDate = endFeedbackDate;
	}
	public Integer getOvertime() {
		return overtime;
	}
	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}
	
}
