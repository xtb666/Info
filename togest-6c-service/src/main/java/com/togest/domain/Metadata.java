package com.togest.domain;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.togest.dict.annotation.DictMark;


public class Metadata extends BaseEntity<Metadata> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
    //标题
    private String name;
    //文件类型
    private String type;
    //文件编码
    @DictMark(dictName = "busy_metadata_type", primaryKey = "code")
    private String code;
    private String codeName;
    //C类型
    private String systemId;
    //上传者
    private String uploader;
    //上传时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;
    //文件
    private String data;
    private String fileId;
    private String fileName;
    //备注
    private String remark;

    private int delFlag = 0;
    
    private String createBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String createIp;
    private String updateBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    private String updateIp;
    private String deleteBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deleteDate;
    private String deleteIp;
    private String sort;
	@JsonIgnore
    private String orderBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date beginCheckDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date endCheckDate;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
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

	public String getDeleteIp() {
		return deleteIp;
	}

	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
     * 设置：标题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：标题
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：文件类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：文件类型
     */
    public String getType() {
        return type;
    }
    /**
     * 设置：文件编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取：文件编码
     */
    public String getCode() {
        return code;
    }
    /**
     * 设置：上传者
     */
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    /**
     * 获取：上传者
     */
    public String getUploader() {
        return uploader;
    }
    /**
     * 设置：上传时间
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * 获取：上传时间
     */
    public Date getUploadDate() {
        return uploadDate;
    }
    /**
     * 设置：文件
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取：文件
     */
    public String getData() {
        return data;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
