package com.togest.service;

import com.togest.domain.CheckParamDTO;

public interface CheckParamService extends ICrudService<CheckParamDTO> {
	public int deleteFalses(String id, String deleteBy, String deleteIp);
}
