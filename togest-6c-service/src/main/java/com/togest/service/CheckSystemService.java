package com.togest.service;

import com.togest.domain.CheckSystemDTO;

public interface CheckSystemService extends ICrudService<CheckSystemDTO>{
	public int deleteFalses(String id, String deleteBy, String deleteIp);
}
