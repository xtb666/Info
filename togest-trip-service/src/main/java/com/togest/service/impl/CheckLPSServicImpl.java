package com.togest.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.init.PTripLineAlias;
import com.togest.domain.init.PTripPavilionAlias;
import com.togest.domain.init.PTripSupplyAlias;
import com.togest.domain.init.PTripSupplyPowerSectionAlias;
import com.togest.service.CheckLPSService;
import com.togest.service.init.PTripLineAliasService;
import com.togest.service.init.PTripPavilionAliasService;
import com.togest.service.init.PTripSupplyAliasService;
import com.togest.service.init.PTripSupplyPowerSectionAliasService;
import com.togest.util.Data;

@Service
public class CheckLPSServicImpl implements CheckLPSService {
	@Autowired
	private PTripLineAliasService pTripLineAliasService;
	@Autowired
	private PTripSupplyPowerSectionAliasService pTripSupplyPowerSectionAliasService;
	@Autowired
	private PTripPavilionAliasService pTripPavilionAliasService;
	@Autowired 
	PTripSupplyAliasService PTripSupplyAliasService;
	 
	@Override
	public boolean check(Data entity, Map<String, String> map, String name, int i, StringBuilder errorMsg, int status,
			String notFindTips, String notInputTips) {
		boolean fg = false;
		if(StringUtil.isNotBlank(name)) {
			String id  = map.get(name);
			if(StringUtil.isBlank(id)) {
				if(!checkAlias(entity, name, status)) {
					errorMsg.append("第" + (i + 2) + "" + notFindTips + "!<br/>");
					fg = true;
				}
			}else {
				if(status == 1) {
					entity.setLineId(id);
				}else if(status == 2) {
					entity.setPsPdId(id);
				}else if(status == 3) {
					entity.setPavilionId(id);
				}
			}
		}else {
			errorMsg.append("第" + (i + 2) + "" + notInputTips + "!<br/>");
			fg = true;
		}
		return fg;
	}
	
	@Override
	public boolean checkAlias(Data entity, String name, int status) {
		if(status == 1) {
			PTripLineAlias paramT = new PTripLineAlias();
			paramT.setDelFlag(0);
			paramT.setName(name);
			List<PTripLineAlias> lineAliasaList = pTripLineAliasService.findList(paramT);
			if(!CollectionUtils.isEmpty(lineAliasaList)) {
				entity.setLineId(lineAliasaList.get(0).getLineId());
				return true;
			}
		}else if(status == 2){
			PTripSupplyPowerSectionAlias paramT = new PTripSupplyPowerSectionAlias();
			paramT.setDelFlag(0);
			paramT.setName(name);
			List<PTripSupplyPowerSectionAlias> psPdAliasaList = pTripSupplyPowerSectionAliasService.findList(paramT);
			if(!CollectionUtils.isEmpty(psPdAliasaList)) {
				entity.setPsPdId(psPdAliasaList.get(0).getSupplyPowerSectionId());
				return true;
			}
		}else if(status == 3) {
			PTripPavilionAlias paramT = new PTripPavilionAlias();
			paramT.setDelFlag(0);
			paramT.setName(name);
			List<PTripPavilionAlias> pavilionAliasaList = pTripPavilionAliasService.findList(paramT);
			if(!CollectionUtils.isEmpty(pavilionAliasaList)) {
				entity.setPavilionId(pavilionAliasaList.get(0).getPavilionId());
				return true;
			}
		}else if(status == 4) {
			PTripSupplyAlias paramT = new PTripSupplyAlias();
			paramT.setDelFlag(0);
			paramT.setName(name);
			List<PTripSupplyAlias> supplyAliasaList = PTripSupplyAliasService.findList(paramT);
			if(!CollectionUtils.isEmpty(supplyAliasaList)) {
				entity.setPsaId(supplyAliasaList.get(0).getPsaId());
				return true;
			}
		}
		return false;
	}

	@Override
	public void isNotFind(boolean fg, Set<String> isNotFindSet, String name) {
		if(fg) {
			isNotFindSet.add(name);
		}
	}
}
