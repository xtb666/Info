package com.togest.service.init.impl;

import org.springframework.stereotype.Service;

import com.togest.dao.init.PTripSupplyAliasDao;
import com.togest.domain.init.PTripSupplyAlias;
import com.togest.service.init.PTripSupplyAliasService;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class PTripSupplyAliasServiceImpl extends CrudCommonService<PTripSupplyAliasDao, PTripSupplyAlias>
		implements PTripSupplyAliasService {

}
