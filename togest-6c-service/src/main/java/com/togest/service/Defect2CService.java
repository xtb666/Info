package com.togest.service;

import com.togest.domain.Defect2CDTO;
import com.togest.request.DefectC2Request;
import com.togest.response.DefectC2Form;
import com.togest.response.DefectC2Response;

public interface Defect2CService extends SixCService<Defect2CDTO,DefectC2Response,DefectC2Form> {
	
	/*public int save(Defect2C entity);
	
	public List<Defect2CDTO> getListRegisterDefect(CQueryFilter entity);
*/
//	public DefectC2Response getC2ResponseByKey(String id);
//	
//	public List<DefectC2Response> findC2ResponseAllList();
//	
//	public List<DefectC2Response> findC2ResponseList(CQueryFilter entity);
//	
//	public Page<DefectC2Response> findC2ResponsePage(Page page,CQueryFilter entity);
//	
//	public Page<DefectC2Form> findC2Form(Page page,CQueryFilter entity);
	
	public int updateDefectC2Request(DefectC2Request entity);
}
