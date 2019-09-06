package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdAPowerDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdAPowerDao extends CrudCommonDao<BdAPowerDTO> {

	List<BdAPowerDTO> findAPowerList(BdInfoQueryFilter entity);


}
