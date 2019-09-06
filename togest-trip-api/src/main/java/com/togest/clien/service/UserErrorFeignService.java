package com.togest.clien.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.togest.model.resposne.RestfulResponse;

@Component
public class UserErrorFeignService implements UserFeignService{

	@Override
	public RestfulResponse<List<Map<String, String>>> getNameByIds(String id) {
		return null;
	}

	
}
