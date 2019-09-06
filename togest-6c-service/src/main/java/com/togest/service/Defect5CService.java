package com.togest.service;

import java.io.InputStream;
import java.util.List;

import com.togest.domain.Defect5CDTO;
import com.togest.request.DefectC5Request;
import com.togest.response.DefectC5Form;
import com.togest.response.DefectC5Response;

public interface Defect5CService extends SixCService<Defect5CDTO,DefectC5Response,DefectC5Form>{

	/*public int save(Defect5CDTO entity);
	
	public List<Defect5CDTO> getListRegisterDefect(CQueryFilter entity);*/

//	public DefectC5Response getC5ResponseByKey(String id);
//	
//	public List<DefectC5Response> findC5ResponseAllList();
//	
//	public List<DefectC5Response> findC5ResponseList(CQueryFilter entity);
//	
//	public Page<DefectC5Response> findC5ResponsePage(Page page,CQueryFilter entity);
	
	public List<Defect5CDTO> getListByPointId(String pointId);
	
	public void analyzeZipData(String fileName, InputStream input,String createBy);
	
	public void import5CData(String fileName, InputStream input, String createBy,String tag);
	
	public int updateDefectC5Request(DefectC5Request entity);
}
