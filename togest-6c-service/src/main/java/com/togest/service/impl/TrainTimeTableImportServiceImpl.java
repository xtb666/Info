package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.domain.TrainTimeTable;
import com.togest.service.TrainTimeTableService;
import com.togest.service.TrainTimeTableImportService;
import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.response.SystemResoucesResponse;

@Service
public class TrainTimeTableImportServiceImpl extends ConfigExcelImportServiceImpl<TrainTimeTable>
		implements TrainTimeTableImportService {
	
	@Autowired
	private TrainTimeTableService trainTimeTableService;
	
	@Override
	public void save(List<QualitityData<TrainTimeTable>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			for (QualitityData<TrainTimeTable> qualitityData : dataList) {
				if(qualitityData.isStatus()){
					trainTimeTableService.save(qualitityData.getData());
				}
			}
		}
		
	}	
	
    
}
