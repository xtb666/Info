package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdCorresStandImportService;
import com.togest.service.BdCorresStandService;

@Service
public class BdCorresStandImportServiceImpl extends ConfigExcelImportServiceImpl<BdCorresStandDTO>
		implements BdCorresStandImportService {
	
	@Autowired
	private BdCorresStandService bdCorresStandService;
	
	@Override
	public void save(List<QualitityData<BdCorresStandDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdCorresStandService::save);	
		}
		
	}	
	
    
}
