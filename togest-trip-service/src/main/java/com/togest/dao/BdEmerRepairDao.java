package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdEmerRepairDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdEmerRepairDao extends CrudCommonDao<BdEmerRepairDTO> {

	List<BdEmerRepairDTO> findEmerRepairList(BdInfoQueryFilter entity);


}
