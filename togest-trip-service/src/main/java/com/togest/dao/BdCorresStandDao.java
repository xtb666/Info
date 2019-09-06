package com.togest.dao;

import java.util.List;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.request.BdInfoQueryFilter;


public interface BdCorresStandDao extends CrudCommonDao<BdCorresStandDTO> {

	List<BdCorresStandDTO> findCorresStandList(BdInfoQueryFilter entity);


}
