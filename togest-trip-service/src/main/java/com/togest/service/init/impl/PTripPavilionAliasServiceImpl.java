package com.togest.service.init.impl;

import org.springframework.stereotype.Service;

import com.togest.dao.init.PTripPavilionAliasDao;
import com.togest.domain.init.PTripPavilionAlias;
import com.togest.service.init.PTripPavilionAliasService;
import com.togest.service.upgrade.CrudCommonService;

@Service
public class PTripPavilionAliasServiceImpl extends CrudCommonService<PTripPavilionAliasDao, PTripPavilionAlias>
		implements PTripPavilionAliasService {

}
