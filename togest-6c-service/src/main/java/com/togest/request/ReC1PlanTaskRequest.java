package com.togest.request;

import com.togest.domain.PlanExecuteDTO;

public class ReC1PlanTaskRequest extends ReTaskRequest{

	
	private PlanExecuteDTO planExecute;

	public PlanExecuteDTO getPlanExecute() {
		return planExecute;
	}

	public void setPlanExecute(PlanExecuteDTO planExecute) {
		this.planExecute = planExecute;
	}

	
	
}
