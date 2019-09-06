package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Point5CDTO;

public interface Point5CDao {

	public int insert(Point5CDTO entity);
	
	public int update(Point5CDTO entity);
	
	public int delete(String id);

	public int deletes(@Param("ids")List<String> ids);
	
	public Point5CDTO getByKey(String id);
	
	public Point5CDTO getByEntity(Point5CDTO entity);
	
	public List<Point5CDTO> findList(Point5CDTO entity);
}
