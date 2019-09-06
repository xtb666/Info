package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdFastRepairedDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdFastRepairedDao extends CrudCommonDao<BdFastRepairedDTO> {

	List<BdFastRepairedDTO> findFastReaireList(BdInfoQueryFilter entity);
}
