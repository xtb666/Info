package com.togest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.Defect2CDao;
import com.togest.dao.DefectCheckHandleDao;
import com.togest.dao.DefectReformHandleDao;
import com.togest.domain.Defect2CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;
import com.togest.request.DefectC2Request;
import com.togest.response.DefectC2Form;
import com.togest.response.DefectC2Response;
import com.togest.service.Defect2CService;

@Service
public class Defect2CServiceImpl extends SixCServiceImpl<Defect2CDao, Defect2CDTO, DefectC2Response, DefectC2Form>
		implements Defect2CService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Defect2CServiceImpl.class);

	@Autowired
	private DefectCheckHandleDao checkHandleDao;
	@Autowired
	private DefectReformHandleDao reformHandleDao;

	@Override
	public int updateDefectC2Request(DefectC2Request entity) {
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
