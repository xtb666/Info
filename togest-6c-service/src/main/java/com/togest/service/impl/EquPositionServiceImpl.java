package com.togest.service.impl;

import org.springframework.stereotype.Service;

import com.togest.dao.EquPositionDao;
import com.togest.domain.EquPosition;
import com.togest.service.EquPositionService;
import com.togest.common.util.string.StringUtil;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class EquPositionServiceImpl extends CrudCommonService<EquPositionDao, EquPosition>
		implements EquPositionService {

}
