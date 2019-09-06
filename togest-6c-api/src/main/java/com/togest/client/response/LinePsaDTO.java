package com.togest.client.response;

import java.io.Serializable;

public class LinePsaDTO implements Serializable {
	
	protected String id;
	protected String plId;
	protected String psaId;
	protected Double startKm;
	protected Double endKm;
	protected String psaName;
	protected String plName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlId() {
		return plId;
	}
	public void setPlId(String plId) {
		this.plId = plId;
	}
	public String getPsaId() {
		return psaId;
	}
	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}
	public Double getStartKm() {
		return startKm;
	}
	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}
	public Double getEndKm() {
		return endKm;
	}
	public void setEndKm(Double endKm) {
		this.endKm = endKm;
	}
	public String getPsaName() {
		return psaName;
	}
	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	public String getPlName() {
		return plName;
	}
	public void setPlName(String plName) {
		this.plName = plName;
	}
}
