package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.BdDStation;
import com.togest.domain.dto.BdDStationDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdDStationImportService;
import com.togest.service.BdDStationService;

@Service
public class BdDStationImportServiceImpl extends ConfigExcelImportServiceImpl<BdDStationDTO>
		implements BdDStationImportService {
	
	@Autowired
	private BdDStationService bdDStationService;
	
	@Override
	public void save(List<QualitityData<BdDStationDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdDStationService::save);	
		}
		
	}	
	
    
}
