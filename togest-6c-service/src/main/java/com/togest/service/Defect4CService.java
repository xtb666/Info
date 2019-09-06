package com.togest.service;

import java.util.List;

import com.togest.domain.Defect4CDTO;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC4Request;
import com.togest.response.DefectC4Form;
import com.togest.response.DefectC4Response;

public interface Defect4CService extends SixCService<Defect4CDTO,DefectC4Response,DefectC4Form> {
	
	/*public int save(Defect4C entity);
	
	public List<Defect4CDTO> getListRegisterDefect(CQueryFilter entity);*/

//	public DefectC4Response getC4ResponseByKey(String id);
//	
//	public List<DefectC4Response> findC4ResponseAllList();
//	
//	public List<DefectC4Response> findC4ResponseList(CQueryFilter entity);
//	
//	public Page<DefectC4Response> findC4ResponsePage(Page page,CQueryFilter entity);
//	
//	public Page<DefectC4Form> findC4Form(Page page,CQueryFilter entity);
	
	public int updateDefectC4Request(DefectC4Request entity);
}
