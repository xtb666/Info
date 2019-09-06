package com.togest.service;

import java.io.InputStream;
import java.util.Map;

import com.togest.domain.WireHeightControlDTO;
import com.togest.service.upgrade.ICrudCommonService;

public interface WireHeightControlService extends ICrudCommonService<WireHeightControlDTO> {

	int save(WireHeightControlDTO entity);

	Map<String, Object> importData(String fileName, InputStream inputStream, String templetId, Object object,
			Map<String, Object> propMap);


}
