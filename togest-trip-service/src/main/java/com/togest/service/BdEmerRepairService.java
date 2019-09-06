package com.togest.service;

import java.util.List;

import com.togest.domain.Page;
import com.togest.domain.dto.BdEmerRepairDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.upgrade.ICrudCommonService;

public interface BdEmerRepairService extends ICrudCommonService<BdEmerRepairDTO> {

	BdEmerRepairDTO get(String id, int status);
	
	List<BdEmerRepairDTO> findEmerRepairList(BdInfoQueryFilter entity);

	Page<BdEmerRepairDTO> findEmerRepairPage(Page<BdEmerRepairDTO> page, BdInfoQueryFilter entity);

}
