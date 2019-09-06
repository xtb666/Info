package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdTripVideoMenuDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdTripVideoMenuImportService;
import com.togest.service.BdTripVideoMenuService;

@Service
public class BdTripVideoMenuImportServiceImpl extends ConfigExcelImportServiceImpl<BdTripVideoMenuDTO>
		implements BdTripVideoMenuImportService {
	
	@Autowired
	private BdTripVideoMenuService bdTripVideoMenuService;
	
	@Override
	public void save(List<QualitityData<BdTripVideoMenuDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdTripVideoMenuService::save);	
		}
		
	}	
	
    
}
