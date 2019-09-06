package com.togest.domain;

public class NoticeDefect extends BaseEntity<NoticeDefect> {
	
	protected String id;
	protected String noticeSectionId;
	protected String defectId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoticeSectionId() {
		return noticeSectionId;
	}
	public void setNoticeSectionId(String noticeSectionId) {
		this.noticeSectionId = noticeSectionId;
	}
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	
}
