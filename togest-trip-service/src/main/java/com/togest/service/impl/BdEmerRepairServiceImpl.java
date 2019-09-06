package com.togest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdEmerRepairDao;
import com.togest.domain.Page;
import com.togest.domain.dto.BdEmerRepairDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdEmerRepairService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.FileUtils;
import com.togest.utils.PageUtils;

@Service
public class BdEmerRepairServiceImpl extends CrudCommonService<BdEmerRepairDao, BdEmerRepairDTO>
		implements BdEmerRepairService {

	@Autowired
	private FileUtils<BdEmerRepairDTO> fileUtils;
	
	@Override
	public BdEmerRepairDTO get(String id) {
		BdEmerRepairDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity);
		return entity;
	}
	
	@Override
	public BdEmerRepairDTO get(String id, int status) {
		BdEmerRepairDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity, status);
		return entity;
	}
	
	@Override
	public BdEmerRepairDTO get(BdEmerRepairDTO entity) {
		 BdEmerRepairDTO bdEmerRepairDTO = super.get(entity);
		 fileUtils.getFileBlobDTOByIds(bdEmerRepairDTO);
		 return bdEmerRepairDTO;
	}
	
	@Override
	public List<BdEmerRepairDTO> findEmerRepairList(BdInfoQueryFilter entity) {
		List<BdEmerRepairDTO> list = dao.findEmerRepairList(entity);
		if(StringUtil.isNotEmpty(list)) {
			list.forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
		}
		return list;
	}

	@Override
	public Page<BdEmerRepairDTO> findEmerRepairPage(Page<BdEmerRepairDTO> page, BdInfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<BdEmerRepairDTO> pg = PageUtils.buildPage(dao.findEmerRepairList(entity));
		if(StringUtil.isNotEmpty(pg.getList())){
	    	pg.getList().forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
        }
		return pg;
	}

}
