package com.togest.request;

import com.togest.domain.PlanExecuteDTO;

public class RePlanTaskRequest extends ReTaskRequest{

	
	private PlanExecuteDTO planExecute;

	public PlanExecuteDTO getPlanExecute() {
		return planExecute;
	}

	public void setPlanExecute(PlanExecuteDTO planExecute) {
		this.planExecute = planExecute;
	}

	
	
}
