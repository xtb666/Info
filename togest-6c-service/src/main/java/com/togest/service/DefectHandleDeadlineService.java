package com.togest.service;

import com.togest.domain.DefectHandleDeadline;
import com.togest.service.upgrade.ICrudCommonService;

public interface DefectHandleDeadlineService extends ICrudCommonService<DefectHandleDeadline> {

	public DefectHandleDeadline getByEntity(String defectDataLevel, String defectDataCategory);
}
