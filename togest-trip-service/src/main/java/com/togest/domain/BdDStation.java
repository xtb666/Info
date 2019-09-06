package com.togest.domain;

import com.togest.util.Data;


/**
 * <p>Title: BdDStation.java</p>
 * <p>Description: 各线站细图</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年11月8日下午3:10:19
 * @version 1.0
 */
public class BdDStation extends Data {

	private static final long serialVersionUID = 1L;

    //线路ID
	protected String lineId;
    //附件ID
	protected String fileId;

    /**
     * 设置：线路ID
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 获取：线路ID
     */
    public String getLineId() {
        return lineId;
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