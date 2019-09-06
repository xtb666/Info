package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.PointHisotryData5CDTO;

public interface PointHisotryData5CDao {
	
    public int insert(PointHisotryData5CDTO entity);
	
	public int update(PointHisotryData5CDTO entity);
	
	public int delete(String id);
	
	public int deletes(@Param("ids")List<String> ids);
	
	public PointHisotryData5CDTO getByKey(String id);
	
	public PointHisotryData5CDTO getByEntity(PointHisotryData5CDTO entity);
	
	public List<PointHisotryData5CDTO> findList(PointHisotryData5CDTO entity);

}
