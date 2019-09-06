package com.togest.domain;

import com.togest.domain.upgrade.DataCommonEntity;
import com.togest.util.Data;


/**
 * <p>Title: BdAPower.java</p>
 * <p>Description: 越区供电方案</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.togest.com/</p>
 * @author 江政昌
 * @date 2018年11月8日下午4:00:31
 * @version 1.0
 */
public class BdAPower extends Data {
	private static final long serialVersionUID = 1L;
    //线路ID
    protected String lineId;
    //附件ID
    protected String fileId;
    //变电所ID
    protected String pavilionId;
    //供电臂ID
    protected String psPdId;
    
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
    /**
     * 设置：变电所ID
     */
    public void setPavilionId(String pavilionId) {
        this.pavilionId = pavilionId;
    }

    /**
     * 获取：变电所ID
     */
    public String getPavilionId() {
        return pavilionId;
    }
    /**
     * 设置：供电臂ID
     */
    public void setPsPdId(String psPdId) {
        this.psPdId = psPdId;
    }

    /**
     * 获取：供电臂ID
     */
    public String getPsPdId() {
        return psPdId;
    }
}
