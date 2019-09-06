package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.domain.BdLineBridge;
import com.togest.domain.dto.BdLineBridgeDTO;
import com.togest.service.BdLineBridgeService;
import com.togest.service.BdLineBridgeImportService;
import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.response.SystemResoucesResponse;

@Service
public class BdLineBridgeImportServiceImpl extends ConfigExcelImportServiceImpl<BdLineBridgeDTO>
		implements BdLineBridgeImportService {
	
	@Autowired
	private BdLineBridgeService bdLineBridgeService;
	
	@Override
	public void save(List<QualitityData<BdLineBridgeDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdLineBridgeService::save);	
		}
		
	}	
	
    
}
