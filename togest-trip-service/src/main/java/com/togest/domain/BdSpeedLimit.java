package com.togest.domain;

import com.togest.util.Data;


/**
 * <p>Title: BdSpeedLimit.java</p>
 * <p>Description: 封锁、限速、降弓</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年11月8日下午3:11:31
 * @version 1.0
 */
public class BdSpeedLimit extends Data {

	private static final long serialVersionUID = 1L;

    //名称
	protected String name;
    //附件ID
	protected String fileId;

    /**
     * 设置：名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：名称
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：附件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取：附件ID
     */
    public String getFileId() {
        return fileId;
    }
}
