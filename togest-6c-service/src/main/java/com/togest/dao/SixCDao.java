package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.request.CQueryFilter;
import com.togest.response.DefectForm;
import com.togest.response.DefectPackageResponse;

public interface SixCDao<T,K extends DefectPackageResponse<T>,H extends DefectForm<T>> {

	public T getByKey(String id);

	public List<T> findAllList();

	public int insert(T entity);
	public int insertBatch(@Param("list") List<T> list);

	public int update(T entity);

	public List<T> findList(CQueryFilter entity);
	
	public K getDefectPackageResponseByKey(String id);
	
	public List<K> getDefectPackageResponseByKeys(@Param("ids") List<String> ids);
	
	public List<K> findDefectPackageResponseAllList();
	
	public List<K> findDefectPackageResponseList(CQueryFilter entity);
	
	public List<H> findDefectForm(CQueryFilter entity);

	public List<H> findDefectFormPage(CQueryFilter entity);
	
	public Integer findDefectFormCount(CQueryFilter entity);
	
	public List<H> findDefectFormByKeys(@Param("ids") List<String> ids);
	
	public List<H> findDefectRepeatForm(CQueryFilter entity);
	
	
}
