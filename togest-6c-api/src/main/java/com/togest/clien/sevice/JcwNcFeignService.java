package com.togest.clien.sevice;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jcwProduceNc-service", fallback = JcwNcErrorService.class)
public interface JcwNcFeignService {

	@RequestMapping(value = "jcwQuestion/sysncDefectData", method = RequestMethod.POST)
	void sysncDefectData(@RequestBody List<Map<String, Object>> defectList);

	@RequestMapping(value = "jcwQuestion/sysncDefectReformHandleData", method = RequestMethod.POST)
	void sysncDefectReformHandleData(@RequestBody List<Map<String, Object>> map);
	
}
