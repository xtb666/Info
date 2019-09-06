package com.togest.service;

import java.util.List;
import java.util.Map;

public interface DefectConfService {

	public Map<String, List<Map<String, Object>>>  handleEnclosure(String resouceCode,Object obj);
	
}
