package com.togest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.Defect4CDao;
import com.togest.dao.DefectCheckHandleDao;
import com.togest.dao.DefectReformHandleDao;
import com.togest.domain.Defect4CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;
import com.togest.request.DefectC4Request;
import com.togest.response.DefectC4Form;
import com.togest.response.DefectC4Response;
import com.togest.service.Defect4CService;

@Service
public class Defect4CServiceImpl extends SixCServiceImpl<Defect4CDao, Defect4CDTO, DefectC4Response, DefectC4Form>
		implements Defect4CService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Defect4CServiceImpl.class);

	@Autowired
	private DefectCheckHandleDao checkHandleDao;
	@Autowired
	private DefectReformHandleDao reformHandleDao;

	@Override
	public int updateDefectC4Request(DefectC4Request entity) {
		DefectCheckHandle checkHandle = entity.getDefectCheckHandle();
		if (StringUtil.isNotEmpty(checkHandle)) {
			checkHandle.setId(entity.getId());
			checkHandleDao.update(checkHandle);
		}
		DefectReformHandle reformHandle = entity.getDefectReformHandle();
		if (StringUtil.isNotEmpty(reformHandle)) {
			reformHandle.setId(entity.getId());
			reformHandleDao.update(reformHandle);
		}
		return save(entity);
	}

}
