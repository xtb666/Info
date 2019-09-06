package com.togest.service.init.impl;

import org.springframework.stereotype.Service;

import com.togest.dao.init.PTripLineAliasDao;
import com.togest.domain.init.PTripLineAlias;
import com.togest.service.init.PTripLineAliasService;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class PTripLineAliasServiceImpl extends CrudCommonService<PTripLineAliasDao, PTripLineAlias>
		implements PTripLineAliasService {

}
