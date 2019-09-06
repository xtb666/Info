package com.togest.response;

import java.io.Serializable;

public class DefectResponse implements Serializable {

	protected String id;//缺陷id
	protected String name;//支柱名
	protected String flowId;//流程id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
}
