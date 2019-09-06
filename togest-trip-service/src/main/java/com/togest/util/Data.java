package com.togest.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.togest.domain.FileBlob;
import com.togest.domain.FileBlobDTO;
import com.togest.domain.upgrade.DataCommonEntity;

public  class Data extends DataCommonEntity implements Serializable{
	protected String sectionId;
	protected String deptId;
	protected String did;
	protected String authCode;
	protected Boolean isChild = false;
	protected List<String> dids;
	protected String fileId;
	protected String fileName; //附件名称
	protected List<FileBlobDTO> files = new ArrayList<>();
	protected String lineId;
    protected String pavilionId;
    protected String psPdId;
    protected String psaId;
	
	public List<String> getDids() {
		return dids;
	}
	public void setDids(List<String> dids) {
		this.dids = dids;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public Boolean getIsChild() {
		return isChild;
	}
	public void setIsChild(Boolean isChild) {
		this.isChild = isChild;
	}
	public List<FileBlobDTO> getFiles() {
		return files;
	}
	public void setFiles(List<FileBlobDTO> files) {
		this.files = files;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getPavilionId() {
		return pavilionId;
	}
	public void setPavilionId(String pavilionId) {
		this.pavilionId = pavilionId;
	}
	public String getPsPdId() {
		return psPdId;
	}
	public void setPsPdId(String psPdId) {
		this.psPdId = psPdId;
	}
	public String getPsaId() {
		return psaId;
	}
	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}
}