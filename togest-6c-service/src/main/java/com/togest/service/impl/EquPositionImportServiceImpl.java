package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.domain.EquPosition;
import com.togest.service.EquPositionService;
import com.togest.service.EquPositionImportService;
import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.response.SystemResoucesResponse;

@Service
public class EquPositionImportServiceImpl extends ConfigExcelImportServiceImpl<EquPosition>
		implements EquPositionImportService {
	
	@Autowired
	private EquPositionService equPositionService;
	
	@Override
	public void save(List<QualitityData<EquPosition>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			for (QualitityData<EquPosition> qualitityData : dataList) {
				if(qualitityData.isStatus()){
					equPositionService.save(qualitityData.getData());
				}
			}
		}
		
	}	
	
    
}
