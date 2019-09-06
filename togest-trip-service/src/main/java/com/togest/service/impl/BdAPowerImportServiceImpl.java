package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdAPowerDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdAPowerImportService;
import com.togest.service.BdAPowerService;

@Service
public class BdAPowerImportServiceImpl extends ConfigExcelImportServiceImpl<BdAPowerDTO>
		implements BdAPowerImportService {
	
	@Autowired
	private BdAPowerService bdAPowerService;
	
	@Override
	public void save(List<QualitityData<BdAPowerDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdAPowerService::save);	
		}
		
	}	
	
    
}
