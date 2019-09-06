package com.togest.request;

import java.io.Serializable;
import java.util.List;

import com.togest.domain.PlanBaseDTO;
import com.togest.domain.PlanDetailDTO;
import com.togest.domain.PlanExecuteDTO;

public class C1PlanRequest implements Serializable{

	private PlanBaseDTO planBase;
	
	private List<PlanDetailDTO> planDetail;
	
	private PlanExecuteDTO planExecute;
	
	public PlanBaseDTO getPlanBase() {
		return planBase;
	}

	public void setPlanBase(PlanBaseDTO planBase) {
		this.planBase = planBase;
	}

	public List<PlanDetailDTO> getPlanDetail() {
		return planDetail;
	}

	public void setPlanDetail(List<PlanDetailDTO> planDetail) {
		this.planDetail = planDetail;
	}

	public PlanExecuteDTO getPlanExecute() {
		return planExecute;
	}

	public void setPlanExecute(PlanExecuteDTO planExecute) {
		this.planExecute = planExecute;
	}
	
	
}
