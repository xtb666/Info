package com.togest.service.init.impl;

import org.springframework.stereotype.Service;

import com.togest.dao.init.PTripDepartmentAliasDao;
import com.togest.domain.init.PTripDepartmentAlias;
import com.togest.service.init.PTripDepartmentAliasService;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class PTripDepartmentAliasServiceImpl extends CrudCommonService<PTripDepartmentAliasDao, PTripDepartmentAlias>
		implements PTripDepartmentAliasService {

}
