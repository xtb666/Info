package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.domain.BdCorresStandDetail;
import com.togest.service.BdCorresStandDetailService;
import com.togest.service.BdCorresStandDetailImportService;
import com.togest.code.util.ConfigExcelImportServiceImpl;
import com.togest.code.util.annotation.QualitityData;
import com.togest.response.SystemResoucesResponse;

@Service
public class BdCorresStandDetailImportServiceImpl extends ConfigExcelImportServiceImpl<BdCorresStandDetail>
		implements BdCorresStandDetailImportService {
	
	@Autowired
	private BdCorresStandDetailService bdCorresStandDetailService;
	
	@Override
	public void save(List<QualitityData<BdCorresStandDetail>> dataList, SystemResoucesResponse resouces) {
		if(dataList!=null){
			dataList.stream()
				.filter(QualitityData::isStatus)
				.map(QualitityData::getData)
				.forEach(bdCorresStandDetailService::save);	
		}
		
	}	
	
    
}
