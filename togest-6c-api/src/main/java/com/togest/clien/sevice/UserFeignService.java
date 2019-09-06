package com.togest.clien.sevice;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "base-service", fallback = UserErrorFeignService.class)
public interface UserFeignService {

	@RequestMapping(value = "user/ids/names", method = RequestMethod.GET)
	public RestfulResponse<List<Map<String, String>>> getNameByIds(@RequestParam("id") String id);
}
