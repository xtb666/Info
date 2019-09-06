package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.CheckTrainDTO;
import com.togest.domain.Page;

public interface CheckTrainService {

	public int save(CheckTrainDTO entity);
	
	public List<CheckTrainDTO> findList(CheckTrainDTO entity);
	
	public Page<CheckTrainDTO> findPages(Page page,CheckTrainDTO entity);

	public int deleteFalses(String id, String deleteBy, String remoteHost);

	public CheckTrainDTO get(String id);
	
	public String getByTrainNo(String trainNo);
	
	public Map<String, Object> importData(String filename, InputStream inputStream, String templetId,String createBy,String sectionId);
}
