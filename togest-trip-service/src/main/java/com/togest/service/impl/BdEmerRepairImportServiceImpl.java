package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.domain.BdEmerRepair;
import com.togest.domain.dto.BdEmerRepairDTO;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.BdEmerRepairImportService;
import com.togest.service.BdEmerRepairService;

@Service
public class BdEmerRepairImportServiceImpl extends ConfigExcelImportServiceImpl<BdEmerRepairDTO>
		implements BdEmerRepairImportService {
	
	@Autowired
	private BdEmerRepairService bdEmerRepairService;
	
	@Override
	public void save(List<QualitityData<BdEmerRepairDTO>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdEmerRepairService::save);	
		}
		
	}	
	
    
}
