package com.togest.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;

public class Notice<T> extends BaseEntity<T> {

	protected String id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date sendDate;
	protected String sendPerson;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date issueDate;
	protected String issuePerson;
	protected String responsiblePerson;
	protected String noticeNumberYear;
	protected String noticeNumberStr;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date rectifyDate;
	protected String rectifyContent;
	protected Integer noticeNumber;
	@DictMark(dictName="notice_status2",primaryKey="code")
	protected String noticeStatus;
	protected String systemId;
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
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
	public String getNoticeNumberYear() {
		return noticeNumberYear;
	}
	public void setNoticeNumberYear(String noticeNumberYear) {
		this.noticeNumberYear = noticeNumberYear;
	}
	public String getNoticeNumberStr() {
		return noticeNumberStr;
	}
	public void setNoticeNumberStr(String noticeNumberStr) {
		this.noticeNumberStr = noticeNumberStr;
	}
	public Date getRectifyDate() {
		return rectifyDate;
	}
	public void setRectifyDate(Date rectifyDate) {
		this.rectifyDate = rectifyDate;
	}
	public String getRectifyContent() {
		return rectifyContent;
	}
	public void setRectifyContent(String rectifyContent) {
		this.rectifyContent = rectifyContent;
	}
	public Integer getNoticeNumber() {
		return noticeNumber;
	}
	public void setNoticeNumber(Integer noticeNumber) {
		this.noticeNumber = noticeNumber;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
