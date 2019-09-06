package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.DefectReformHandle;

public interface DefectReformHandleDao {

	public DefectReformHandle getByKey(String id);

	public List<DefectReformHandle> findAllList();
	
	public List<DefectReformHandle> findList();
	
	public int insert(DefectReformHandle entity);
	
	public int update(DefectReformHandle entity);

	public int deleteReformByIds(@Param("ids")List<String> ids);
}
