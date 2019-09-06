package com.togest.clien.sevice;

import java.util.List;

import org.springframework.stereotype.Component;

import com.togest.client.response.DeptLineDTO;
import com.togest.client.response.DeptRange;
import com.togest.client.response.DetectResult;
import com.togest.client.response.SectionMileage;
import com.togest.model.resposne.RestfulResponse;

@Component
public class DeptLineErrorService implements DeptLineFeignService{

	@Override
	public RestfulResponse<List<DetectResult>> getSectionIdAndDetectMileage(String lineId, Double startKm,
			Double endKm) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<DeptRange> getStartKmAndEndKm(String lineId, String sectionId) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<List<SectionMileage>> getSectionMileage(String lineId, String direction) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<DeptLineDTO> findDeptLineDTO(String lineId, Double defectGlb, String direction) {
		this.exception();
		return null;
	}
	public void exception(){
		throw new RuntimeException("调用基础数据异常：jcwBase-service");
	}

	@Override
	public RestfulResponse<List<SectionMileage>> getSectionMileage(String plId, String direction, String type) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<List<SectionMileage>> getDeptMileage(String lineId, String type, String changeFlag) {
		this.exception();
		return null;
	}

	
}
