package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdConstructPositionDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdConstructPositionImportService;
import com.togest.service.BdConstructPositionService;

@Service
public class BdConstructPositionImportServiceImpl extends ConfigExcelImportServiceImpl<BdConstructPositionDTO>
		implements BdConstructPositionImportService {
	
	@Autowired
	private BdConstructPositionService bdConstructPositionService;
	
	@Override
	public void save(List<QualitityData<BdConstructPositionDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdConstructPositionService::save);	
		}
		
	}	
	
    
}
