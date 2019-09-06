package com.togest.service;

import java.io.InputStream;
import java.util.Map;

import com.togest.domain.MileageStatisticsDTO;
public interface MileageStatisticsService extends  ICrudService<MileageStatisticsDTO>{
	
	public int deleteFalses(Map<String, Object> map);
	
	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String createBy,
			String createIp);
}
