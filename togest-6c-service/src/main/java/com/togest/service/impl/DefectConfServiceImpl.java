package com.togest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.client.MetadataConfigClient;
import com.togest.code.util.MetadataUtil;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.BaseEntity;
import com.togest.response.SystemResoucesResponse;
import com.togest.service.DefectConfService;

@Service
public class DefectConfServiceImpl implements DefectConfService {
	
	@Autowired
	private MetadataConfigClient metadataConfigClient;
	@Autowired
	private MetadataUtil metadataUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<Map<String, Object>>> handleEnclosure(String resouceCode,Object obj) {
		if(StringUtil.isEmpty(obj)||StringUtil.isBlank(resouceCode)){
			return null;
		}
		SystemResoucesResponse response = OptionalUtils.map(metadataConfigClient.getSystemResources(resouceCode));
		Map<String, Object> map =(Map<String, Object>) ObjectUtils.objectToHashMap(obj);
		Map<String, Object> temp = new HashMap<String, Object>();
		map.forEach((key,value)->{
			if (StringUtil.isNotEmpty(value) && value instanceof BaseEntity) {
				Map<String, Object> map1 =(Map<String, Object>) ObjectUtils.objectToHashMap(value);
				map1.forEach((key1,value1)->temp.put(key+"."+key1, value1));
			}
		});
		if(StringUtil.isNotEmpty(temp)){
			map.putAll(temp);
		}
		return metadataUtil.enclosure(map, response.getProps());
	}

}
