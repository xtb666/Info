package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.domain.BdFastRepaired;
import com.togest.domain.dto.BdFastRepairedDTO;
import com.togest.service.BdFastRepairedService;
import com.togest.service.BdFastRepairedImportService;
import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.response.SystemResoucesResponse;

@Service
public class BdFastRepairedImportServiceImpl extends ConfigExcelImportServiceImpl<BdFastRepairedDTO>
		implements BdFastRepairedImportService {
	
	@Autowired
	private BdFastRepairedService bdFastRepairedService;
	
	@Override
	public void save(List<QualitityData<BdFastRepairedDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdFastRepairedService::save);	
		}
		
	}	
	
    
}
