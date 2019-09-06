package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.domain.BdSpeedLimit;
import com.togest.domain.dto.BdSpeedLimitDTO;
import com.togest.service.BdSpeedLimitService;
import com.togest.service.BdSpeedLimitImportService;
import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.response.SystemResoucesResponse;

@Service
public class BdSpeedLimitImportServiceImpl extends ConfigExcelImportServiceImpl<BdSpeedLimitDTO>
		implements BdSpeedLimitImportService {
	
	@Autowired
	private BdSpeedLimitService bdSpeedLimitService;
	
	@Override
	public void save(List<QualitityData<BdSpeedLimitDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdSpeedLimitService::save);	
		}
		
	}	
	
    
}
