package com.togest.clien.sevice;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.togest.domain.Naming;
import com.togest.model.resposne.RestfulResponse;
@Component
public class DeptFeignErrorServiceImpl implements DeptFeignService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeptFeignErrorServiceImpl.class);

	@Override
	public RestfulResponse<List<Map<String, String>>> getNameById(String id) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> getIdByName(String name,String sectionId) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<Naming> getParentDept(String id) {
		this.exception();
		return null;
	}
	public void exception(){
		throw new RuntimeException("调用基础数据异常：base-service");
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> getParentDepts(String ids) {
		this.exception();
		return null;
	}

	@Override
	public RestfulResponse<String> checkDeptType(String id, String code) {
		LOGGER.error("调用基础数据失败：base-service, checkDeptType: id --- "+id+", code --- "+code);
		return null;
	}

	@Override
	public RestfulResponse<List<Map<String, String>>> getDeptByDeptTypeCode(String deptTypeCode) {
		LOGGER.error("调用基础数据失败：base-service, getDeptByDeptTypeCode: deptTypeCode --- "+deptTypeCode);
		return null;
	}

	@Override
	public RestfulResponse<Map<String, Object>> getDeptMsg(String id) {
		LOGGER.error("调用基础数据失败：base-service, getDeptMsg: id --- "+id);
		return null;
	}

	
	
}
