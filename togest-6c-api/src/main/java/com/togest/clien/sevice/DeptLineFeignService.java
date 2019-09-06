package com.togest.clien.sevice;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togest.client.response.DeptLineDTO;
import com.togest.client.response.DeptRange;
import com.togest.client.response.DetectResult;
import com.togest.client.response.SectionMileage;
import com.togest.common.annotation.RequestLogging;
import com.togest.model.resposne.RestfulResponse;

@FeignClient(value = "jcwBase-service", fallback = DeptLineErrorService.class)
public interface DeptLineFeignService {
	
	@RequestMapping(value = "lines/deptLines/sectionAndDetectMileage", method = RequestMethod.GET)
	public RestfulResponse<List<DetectResult>> getSectionIdAndDetectMileage(@RequestParam ("lineId") String lineId,
			@RequestParam("startKm") Double startKm, @RequestParam("endKm") Double endKm);
	
	@RequestLogging
	@RequestMapping(value = "lines/deptLines/startKmAndEndKm", method = RequestMethod.GET)
	public RestfulResponse<DeptRange> getStartKmAndEndKm(@RequestParam("lineId") String lineId,
			@RequestParam("sectionId") String sectionId);
	
	@RequestLogging
	@RequestMapping(value = "lines/deptLines/sectionMileage", method = RequestMethod.GET)
	public RestfulResponse<List<SectionMileage>> getSectionMileage(@RequestParam("lineId") String lineId, 
			@RequestParam("direction") String direction);
	
	@RequestMapping(value = "lines/deptLines/deptMileage", method = RequestMethod.GET)
	public RestfulResponse<List<SectionMileage>> getDeptMileage(@RequestParam("lineId") String lineId, 
			@RequestParam("type") String type, @RequestParam("changeFlag") String changeFlag);
	
	@RequestLogging
	@RequestMapping(value = "lines/deptLines/findDeptLine", method = RequestMethod.GET)
	public RestfulResponse<DeptLineDTO> findDeptLineDTO(@RequestParam("lineId") String lineId, 
			@RequestParam("defectGlb") Double defectGlb,
			@RequestParam("direction") String direction);
	
	@RequestLogging
	@RequestMapping(value = "lines/deptLines/lists", method = RequestMethod.GET)
	public RestfulResponse<List<SectionMileage>> getSectionMileage(@RequestParam("plId") String plId, 
			@RequestParam("direction") String direction,@RequestParam("type")String type);
	
}
