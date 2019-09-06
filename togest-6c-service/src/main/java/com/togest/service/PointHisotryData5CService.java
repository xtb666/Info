package com.togest.service;

import java.io.InputStream;
import java.util.List;

import com.togest.domain.Page;
import com.togest.domain.PointHisotryData5CDTO;

public interface PointHisotryData5CService {
	public int save(PointHisotryData5CDTO entity);

	public int delete(String id);

	public int deletes(String ids);

	public PointHisotryData5CDTO get(String id);

	public PointHisotryData5CDTO get(PointHisotryData5CDTO entity);

	public List<PointHisotryData5CDTO> findList(PointHisotryData5CDTO entity);

	public Page<PointHisotryData5CDTO> findList(Page page,
			PointHisotryData5CDTO entity);
	
	public void analyzeZipData(String fileName, InputStream input,String createBy);
}
