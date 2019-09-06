package com.togest.domain;

import java.util.List;

import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.response.DefectResponse;

public class NoticeDTO extends Notice<NoticeDTO> {

	protected List<NoticeSectionDTO> noticeSections;
	protected List<Naming> namings;
	protected List<DefectResponse> defects;
	protected String defectIds;
	protected String checkIds;
	protected String flowId;
	protected String noticeStatusName;
	protected String issuePersonName;
	protected String responsiblePersonName;
	private String SystemName;
	
	protected List<Check1CDTO> check1C;

	protected Integer noticeDay = 7;

	public Integer getNoticeDay() {
		if (StringUtil.isNotEmpty(issueDate) && StringUtil.isNotEmpty(rectifyDate)) {
			return DateUtils.daysBetween(issueDate, rectifyDate);
		}

		return noticeDay;
	}

	public void setNoticeDay(Integer noticeDay) {
		this.noticeDay = noticeDay;
	}


	public List<Check1CDTO> getCheck1C() {
		return check1C;
	}

	public void setCheck1C(List<Check1CDTO> check1c) {
		check1C = check1c;
	}

	public String getCheckIds() {
		return checkIds;
	}

	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}

	public List<NoticeSectionDTO> getNoticeSections() {
		return noticeSections;
	}

	public void setNoticeSections(List<NoticeSectionDTO> noticeSections) {
		this.noticeSections = noticeSections;
	}

	public List<Naming> getNamings() {
		return namings;
	}

	public void setNamings(List<Naming> namings) {
		this.namings = namings;
	}

	public String getDefectIds() {
		return defectIds;
	}

	public void setDefectIds(String defectIds) {
		this.defectIds = defectIds;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getNoticeStatusName() {
		return noticeStatusName;
	}

	public void setNoticeStatusName(String noticeStatusName) {
		this.noticeStatusName = noticeStatusName;
	}

	public String getIssuePersonName() {
		return issuePersonName;
	}

	public void setIssuePersonName(String issuePersonName) {
		this.issuePersonName = issuePersonName;
	}

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public List<DefectResponse> getDefects() {
		return defects;
	}

	public void setDefects(List<DefectResponse> defects) {
		this.defects = defects;
	}

	public String getSystemName() {
		return SystemName;
	}

	public void setSystemName(String systemName) {
		SystemName = systemName;
	}

}
