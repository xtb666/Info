package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdSpeedLimitDao;
import com.togest.domain.Page;
import com.togest.domain.dto.BdSpeedLimitDTO;
import com.togest.domain.dto.BdSpeedLimitDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdSpeedLimitService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.FileUtils;
import com.togest.utils.PageUtils;

@Service
public class BdSpeedLimitServiceImpl extends CrudCommonService<BdSpeedLimitDao, BdSpeedLimitDTO>
		implements BdSpeedLimitService {

	@Autowired
	private FileUtils<BdSpeedLimitDTO> fileUtils;
	
	@Override
	public BdSpeedLimitDTO get(String id) {
		BdSpeedLimitDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity);
		return entity;
	}
	
	@Override
	public BdSpeedLimitDTO get(String id, int status) {
		BdSpeedLimitDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity, status);
		return entity;
	}
	
	@Override
	public BdSpeedLimitDTO get(BdSpeedLimitDTO entity) {
		 BdSpeedLimitDTO bdSpeedLimitDTO = super.get(entity);
		 fileUtils.getFileBlobDTOByIds(bdSpeedLimitDTO);
		 return bdSpeedLimitDTO;
	}
	
	@Override
	public List<BdSpeedLimitDTO> findSpeedLimitList(BdInfoQueryFilter entity) {
		List<BdSpeedLimitDTO> list = dao.findSpeedLimitList(entity);
		if(StringUtil.isNotEmpty(list)) {
			list.forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
		}
		return list;
	}

	@Override
	public Page<BdSpeedLimitDTO> findSpeedLimitPage(Page<BdSpeedLimitDTO> page, BdInfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<BdSpeedLimitDTO> pg = PageUtils.buildPage(dao.findSpeedLimitList(entity));
		if(StringUtil.isNotEmpty(pg.getList())){
	    	pg.getList().forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
        }
		return pg;
	}
}
