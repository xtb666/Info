package com.togest.service;

import java.io.InputStream;
import java.util.Map;

import com.togest.domain.WireHeightLineMin;
import com.togest.service.upgrade.ICrudCommonService;

public interface WireHeightLineMinService extends ICrudCommonService<WireHeightLineMin> {

	Map<String, Object> importData(String fileName, InputStream inputStream, String templetId, Object object,
			Map<String, String> propMap);

}
