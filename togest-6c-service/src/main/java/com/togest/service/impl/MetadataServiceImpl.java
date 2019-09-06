package com.togest.service.impl;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.MetadataDao;
import com.togest.dao.NoticeDefectDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.IdGen;
import com.togest.domain.Metadata;
import com.togest.domain.NoticeDefect;
import com.togest.domain.Page;
import com.togest.service.CrudService;
import com.togest.service.MetadataService;
import com.togest.utils.PageUtils;

import feign.Param;

@Service
public class MetadataServiceImpl extends CrudService<MetadataDao, Metadata> implements MetadataService {
	
	@Override
	@DictAggregation
	public Page<Metadata> findPage(Page<Metadata> page, Metadata entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findList(entity));
	}


	@Override
	public int deleteFalses(String id, String deleteBy, String deleteIp) {

		if (StringUtil.isBlank(id)) {
			return 0;
		}
		List<String> ids = Arrays.asList(id.split(","));
		return dao.deleteFalses(ids, deleteBy,
				deleteIp);
	}


	@Override
	public int save(Metadata entity) {
		if(StringUtil.isNotEmpty(entity.getId())){
			dao.update(entity);
		}else{
			entity.setId(IdGen.uuid());
			dao.insert(entity);
		}
		return 1;
	}
}
