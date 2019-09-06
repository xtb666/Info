package com.togest.service;

import java.util.List;

import com.togest.domain.Page;
import com.togest.domain.Point5CDTO;

public interface Point5CService {

	public int save(Point5CDTO entity);

	public Point5CDTO get(String id);
	
	public int delete(String id);
	
	public int deletes(String id);
	
	public Point5CDTO get(Point5CDTO entity);

	public List<Point5CDTO> findList(Point5CDTO entity);
	
	public Page<Point5CDTO> findPage(Page page,Point5CDTO entity);
}
