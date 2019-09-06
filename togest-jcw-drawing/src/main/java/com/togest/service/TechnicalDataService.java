package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.domain.BatchTechnicalData;
import com.togest.domain.TechnicalDataDTO;
import com.togest.request.TechnicalDataRequest;

public interface TechnicalDataService extends ICrudService<TechnicalDataDTO> {
	/**
	 * 通过资料类型查找资料数据集合
	 * 
	 * @param type
	 * @return
	 */
	public List<TechnicalDataDTO> getList(String type);

	/**
	 * 通过资料类型和分类ids查找资料数据集合
	 * 
	 * @param type
	 * @param assortmentIds
	 * @return
	 */
	public List<TechnicalDataDTO> getList(String type, String assortmentIds);

	/**
	 * 通过资料类型、分类ids、名称模糊查找资料数据集合
	 * 
	 * @param type
	 * @param assortmentIds
	 * @param name
	 * @return
	 */
	public List<TechnicalDataDTO> getList(String type, String assortmentIds,
			String name);
	
	public Map<String, List<Map<String, Object>>> editTechnicalDataDataConfig(
			TechnicalDataDTO entity, String systemResourcesCode, String dictName, int editTag);
	
	public void batchSave(BatchTechnicalData data);
	
	public List<TechnicalDataDTO> getListByTechnicalDataRequest(
			TechnicalDataRequest entity);
}
