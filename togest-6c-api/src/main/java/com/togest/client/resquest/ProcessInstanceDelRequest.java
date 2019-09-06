package com.togest.client.resquest;

import java.io.Serializable;

public class ProcessInstanceDelRequest implements Serializable {

	private String processInstanceId;
	private String deleteReason;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

}
