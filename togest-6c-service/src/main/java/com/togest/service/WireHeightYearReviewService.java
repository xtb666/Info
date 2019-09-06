package com.togest.service;

import java.io.InputStream;
import java.util.Map;

import com.togest.domain.WireHeightYearReviewDTO;
import com.togest.service.upgrade.ICrudCommonService;

public interface WireHeightYearReviewService extends ICrudCommonService<WireHeightYearReviewDTO> {

	Map<String, Object> importData(String fileName, InputStream inputStream, String templetId, Object object,
			Map<String, Object> propMap);

}
