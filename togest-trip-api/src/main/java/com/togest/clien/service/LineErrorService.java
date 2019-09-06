package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.togest.model.resposne.RestfulResponse;

@Component
public class LineErrorService implements LineFeignService{

	@Override
	public RestfulResponse<String> getIdByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<String>> getPsaNamesByLineId(String lineId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> getNameByIds(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> getIdByNames(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
