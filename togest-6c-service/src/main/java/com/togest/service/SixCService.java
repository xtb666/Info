package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.client.response.Pillar;
import com.togest.domain.Defect;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.response.DefectForm;
import com.togest.response.DefectFormDTO;
import com.togest.response.DefectPackageResponse;

public interface SixCService<T,K extends DefectPackageResponse<T>,H extends DefectForm<T>> {

	public T get(String id);

	public int deleteFalses(T entity);

	public int save(T entity);

	public List<T> findAllList();

	public List<T> findList(CQueryFilter entity);
	
	public Page<T> findPage(Page page, CQueryFilter entity);

	public Map<String, Object> analyzeExcelData(String originalFilename,
			InputStream inputStream, String templetId, String createBy,
			String sectionId);
	
	public Map<String, Object> pareseData(String originalFilename,
			InputStream inputStream, String templetId, String createBy,
			String sectionId, String systemId, Class<? extends Defect> clazz,
			String infoId);

	public void setImgValue(Map<String, Object> value, String userId);
	
	public byte[] exportData(List entityList, String templetId);
	
	public int deleteFalses(List<String> defectIds, String userId, String deleteIp);
	
	public K getDefectPackageResponseByKey(String id);
	
	public List<K> getDefectPackageResponseByKeys(List<String> ids);
	
	public List<K> findDefectPackageResponseAllList();
	
	public List<K> findDefectPackageResponseList(CQueryFilter entity);
	
	public Page<K> findDefectPackageResponsePage(Page page,CQueryFilter entity);
	
	public Page<H> findDefectForm(Page page,CQueryFilter entity);
	
	public Page<H> findDefectFormPage(Page page,CQueryFilter entity);

	public Page<H> findDefectRepeatForm(Page page,CQueryFilter entity);
	public List<K> findDefectPackageRepeatResponseList(CQueryFilter entity);
	
	public void setDefectRepeatRequest(List<T> list);
	
	public Map<Integer, Pillar> handlePillar(List<Integer> pillarIndex, List<Pillar> pillarList,
			Map<Integer, Pillar> pillarMap);
	
	public List<H> findDefectForm(CQueryFilter entity);
	
	public Page<H> findDefectRepeatForm2(Page page, CQueryFilter entity) ;
	
	public Page<DefectFormDTO> findDefectFormPageV2(Page page,CQueryFilter entity);
}
