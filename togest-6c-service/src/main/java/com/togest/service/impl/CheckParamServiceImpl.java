package com.togest.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.CheckParamDao;
import com.togest.domain.CheckParamDTO;
import com.togest.service.CheckParamService;
import com.togest.service.CrudService;

@Service
public class CheckParamServiceImpl extends
		CrudService<CheckParamDao, CheckParamDTO> implements CheckParamService {

	@Override
	public int deleteFalses(String id, String deleteBy, String deleteIp) {

		if (StringUtil.isBlank(id)) {
			return 0;
		}
		return dao.deleteFalses(Arrays.asList(id.split(",")), deleteBy,
				deleteIp);
	}
	
	public boolean checkCodeUniqe(String code,String id){
		CheckParamDTO entity = new CheckParamDTO();
		entity.setCode(code);
		CheckParamDTO dto = dao.getByEntity(entity);
		if(dto==null||dto.getId().equals(id)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkNameUniqe(String name,String id){
		CheckParamDTO entity = new CheckParamDTO();
		entity.setName(name);
		CheckParamDTO dto = dao.getByEntity(entity);
		if(dto==null||dto.getId().equals(id)){
			return true;
		}else{
			return false;
		}
	}
	
	
	@Override
	public int save(CheckParamDTO entity) {
		if(entity!=null){
			if (!checkCodeUniqe(entity.getCode(),entity.getId())) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			}else if(!checkNameUniqe(entity.getName(),entity.getId())){
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
		}
		
		return super.save(entity);
	}

}
