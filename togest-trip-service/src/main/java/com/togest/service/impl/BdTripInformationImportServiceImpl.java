package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdTripInformationDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdTripInformationImportService;
import com.togest.service.BdTripInformationService;

@Service
public class BdTripInformationImportServiceImpl extends ConfigExcelImportServiceImpl<BdTripInformationDTO>
		implements BdTripInformationImportService {
	
	@Autowired
	private BdTripInformationService bdTripInformationService;
	
	@Override
	public void save(List<QualitityData<BdTripInformationDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdTripInformationService::save);	
		}
		
	}	
	
    
}
