package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdTLineDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdTLineImportService;
import com.togest.service.BdTLineService;

@Service
public class BdTLineImportServiceImpl extends ConfigExcelImportServiceImpl<BdTLineDTO>
		implements BdTLineImportService {
	
	@Autowired
	private BdTLineService bdTLineService;
	
	@Override
	public void save(List<QualitityData<BdTLineDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdTLineService::save);	
		}
		
	}	
	
    
}
