package com.togest.service;

import java.util.List;

import com.togest.domain.Defect3CDTO;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC3Request;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;

public interface Defect3CService extends SixCService<Defect3CDTO,DefectC3Response,DefectC3Form> {
	
	/*public int save(Defect3C entity);
	
	public List<Defect3CDTO> getListRegisterDefect(CQueryFilter entity);*/

//	public DefectC3Response getC3ResponseByKey(String id);
//	
//	public List<DefectC3Response> findC3ResponseAllList();
//	
//	public List<DefectC3Response> findC3ResponseList(CQueryFilter entity);
//	
//	public Page<DefectC3Response> findC3ResponsePage(Page page,CQueryFilter entity);
//	
//	public Page<DefectC3Form> findC3Form(Page page,CQueryFilter entity);
	
	public Page<DefectC3Form> findC3FormForNotice(Page page,CQueryFilter entity);
	
	public List<DefectC3Form> findC3DefectByIds(List<String> ids);
	
	public int updateDefectC3Request(DefectC3Request entity);

	public List<DefectC3Form> findC3FormBySectionNotice(List<String> noticeIds, String sectionId);
}
