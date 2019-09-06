package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectRepeatCount;

public interface DefectRepeatCountDao {

	public void insert(DefectRepeatCount entity);
	
	public void deleteByKeys(@Param("ids") List<String> id);

	public void updateRepeatCount(DefectRepeatCount entity);

	public List<DefectRepeatCount> findList(DefectRepeatCount entity);

	public DefectRepeatCount getByKey(String id);

	public DefectRepeatCount getByKey2(@Param("id") String id,@Param("glbDistance") Double glbDistance);
	
	public List<DefectRepeatCount> getByKeys(@Param("ids") List<String> id);

}
