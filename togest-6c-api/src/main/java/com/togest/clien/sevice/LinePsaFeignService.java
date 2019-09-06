package com.togest.clien.sevice;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.client.response.LinePsaDTO;
import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "jcwBase-service", fallback = LinePsaErrorService.class)
public interface LinePsaFeignService {

	@RequestMapping(value = "linePsa", method = RequestMethod.GET)
	public RestfulResponse<LinePsaDTO> getLine(@RequestParam("plId") String plId, @RequestParam("psaId") String psaId,@RequestParam("direction") String direction);
}
