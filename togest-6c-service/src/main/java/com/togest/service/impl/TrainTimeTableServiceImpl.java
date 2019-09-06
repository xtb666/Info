package com.togest.service.impl;

import org.springframework.stereotype.Service;

import com.togest.dao.TrainTimeTableDao;
import com.togest.domain.TrainTimeTable;
import com.togest.service.TrainTimeTableService;
import com.togest.common.util.string.StringUtil;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class TrainTimeTableServiceImpl extends CrudCommonService<TrainTimeTableDao, TrainTimeTable>
		implements TrainTimeTableService {

}
