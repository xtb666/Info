package com.togest.clien.sevice;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.client.response.Pillar;
import com.togest.domain.PillarInfoDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.PillarInfoSelectorV2;

@FeignClient(value = "jcwEqu-service", fallback = PillarFeginErrorServiceImpl.class)
public interface PillarFeginService {

	@RequestMapping(value = "/pillars/min", method = RequestMethod.GET)
	public RestfulResponse<Pillar> getMin(@RequestParam("lineId")String lineId, @RequestParam("direction") String direction, 
				@RequestParam("glb") Double glb);
	
	@RequestMapping(value = "/pillars/max", method = RequestMethod.GET)
	public RestfulResponse<Pillar> getMax(@RequestParam("lineId")String lineId, @RequestParam("direction") String direction,
				@RequestParam("glb") Double glb);
	
	@RequestMapping(value = "/pillars/defect", method = RequestMethod.GET)
	public RestfulResponse<Pillar> getPillarForDefect(@RequestParam("deptId") String deptId, @RequestParam("lineId") String lineId, 
				@RequestParam("direction") String direction, @RequestParam("name") String name);
	
	@RequestMapping(value = "/pillars/id", method = RequestMethod.GET)
	public RestfulResponse<Pillar> getPillar(@RequestParam("id") String id);
	
	@RequestMapping(value = "/pillars/findPillarByGlb", method = RequestMethod.POST)
	public RestfulResponse<List<Pillar>> findPillarByGlb(@RequestBody List<Pillar> entityList);
	
	@RequestMapping(value = "/pillars/findPillarByName", method = RequestMethod.POST)
	public RestfulResponse<List<Pillar>> findPillarByName(@RequestBody List<Pillar> entityList);
	
	@RequestMapping(value = "/pillars/wireheight/selector", method = RequestMethod.GET)
	public RestfulResponse<List<PillarInfoSelectorV2>> pillarSelector2(@RequestParam("deptId") String deptId, @RequestParam("plId") String plId, 
			@RequestParam("psaId") String psaId, @RequestParam("direction") String direction, @RequestParam("tunnelId") String tunnelId, @RequestParam("track") String track);
}
