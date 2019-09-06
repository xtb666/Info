package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdSubstationRangeDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdSubstationRangeImportService;
import com.togest.service.BdSubstationRangeService;

@Service
public class BdSubstationRangeImportServiceImpl extends ConfigExcelImportServiceImpl<BdSubstationRangeDTO>
		implements BdSubstationRangeImportService {
	
	@Autowired
	private BdSubstationRangeService bdSubstationRangeService;
	
	@Override
	public void save(List<QualitityData<BdSubstationRangeDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdSubstationRangeService::save);	
		}
		
	}	
	
    
}
