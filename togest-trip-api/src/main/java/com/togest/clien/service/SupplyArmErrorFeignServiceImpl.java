package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.togest.model.resposne.RestfulResponse;
@Component
public class SupplyArmErrorFeignServiceImpl implements SupplyArmFeignService{

	@Override
	public RestfulResponse<List<Map<String, String>>> getIdByNames(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> findByCondition(String lineId, String direction,
			String startStationName, String endStationName, int type) {
		// TODO Auto-generated method stub
		return null;
	}

}
