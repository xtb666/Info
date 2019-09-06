package com.togest.clien.sevice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class JcwNcErrorService implements JcwNcFeignService{

	@Override
	public void sysncDefectData(List<Map<String, Object>> mapList) {
		this.exception();
	}

	@Override
	public void sysncDefectReformHandleData(List<Map<String, Object>> map) {
		this.exception();
	}
	
	public void exception(){
		throw new RuntimeException("调用基础数据异常：jcwProduceNc-service");
	}

}
