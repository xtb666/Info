package com.togest.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.util.string.StringUtil;
import com.togest.config.InfoStatus;
import com.togest.dao.CInfoDao;
import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.CInfo;
import com.togest.service.BaseInfoService;
import com.togest.service.upgrade.CrudCommonService;

public class BaseInfoServiceImpl<D extends CrudCommonDao<T>, T extends CInfo> extends CrudCommonService<D, T>
		implements BaseInfoService<T> {
	@Autowired
	protected CInfoDao cInfoDao;
	@Autowired
	protected D dao;
	
	@Transactional
	@Override
	public int save(T entity) {
		int i = 0;
		if(StringUtil.isBlank(entity.getId())){
			entity.preInsert();
			entity.setDefectDataStatus(InfoStatus.UploadCompleted.getStatus().toString());
			entity.setRawdataStatus(InfoStatus.UploadCompleted.getStatus().toString());
			entity.setAnalyStatus(InfoStatus.UploadCompleted.getStatus().toString());
			dao.insert(entity);
			cInfoDao.insert((CInfo)entity);
		}else{
			dao.update(entity);
			cInfoDao.update((CInfo)entity);
		}
		return i;
	}

	@Transactional
	@Override
	public int deleteFalses(String ids, String deleteBy, String deleteIp) {
		if(StringUtil.isNotBlank(ids)){
			Date date = new Date();
			List<String> list = Arrays.asList(ids.split(","));
			cInfoDao.deleteFalses(list, deleteBy, deleteIp, date);
			int i = dao.deleteByKey(ids);      
			return i;
		}
		return 0;
	}

}
