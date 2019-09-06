package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.domain.Defect1CDTO;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectC1Request;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;

public interface Defect1CService extends SixCService<Defect1CDTO,DefectC1Response,DefectC1Form> {

//	public DefectC1Response getC1ResponseByKey(String id);
//	
//	public List<DefectC1Response> getC1ResponseByKeys(List<String> ids);
//	
//	public List<DefectC1Response> findC1ResponseAllList();
//	
//	public List<DefectC1Response> findC1ResponseList(CQueryFilter entity);
//	
//	public Page<DefectC1Response> findC1ResponsePage(Page page,CQueryFilter entity);
//	
//	public Page<DefectC1Form> findC1Form(Page page,CQueryFilter entity);
	
	public Page<DefectC1Form> findC1FormForNotice(Page page,CQueryFilter entity);
	
	public List<DefectC1Form> findC1DefectByIds(List<String> ids);
	
	public int updateDefectC1Request(DefectC1Request entity);
	
	public List<DefectC1Form> findC1FormBySectionNotice(List<String> noticeSectionIds, String sectionId);

	public void auditRegister(CQueryFilter entity, String userId, String userName, String sectionId);

	public void handleDefectStart();
	
	public void handleDefectAudit();
	
	public void handleDefectReceive();

	public void receiveRegister(CQueryFilter entity, String userId, String userName, String sectionId);

	public void rectificationRegister(CQueryFilter entity, String userId, String userName, String sectionId);

}
