package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.togest.domain.SupplyArmDTO;
import com.togest.domain.SupplyPowerSectionDTO;
import com.togest.model.resposne.RestfulResponse;
@Component
public class JcwServiceErrorClient implements JcwServiceClient{

	@Override
	public RestfulResponse<List<Map<String, String>>> getIdByNames(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> getPavilionsIdByNames(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
