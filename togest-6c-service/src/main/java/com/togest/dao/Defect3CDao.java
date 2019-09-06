package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Defect3CDTO;
import com.togest.request.CQueryFilter;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;

public interface Defect3CDao extends SixCDao<Defect3CDTO, DefectC3Response, DefectC3Form> {

	public List<DefectC3Form> findC3FormForNotice(CQueryFilter entity);

	public List<DefectC3Form> findC3DefectByIds(@Param("ids") List<String> ids);
	public void updateDefectValue(@Param("id") String id,@Param("defectValue") Integer defectValue);
	public List<DefectC3Form> findC3DefectByDefectTypeIds(@Param("defectTypeIds") List<String> defectTypeIds);

	public List<DefectC3Form> findC3FormBySectionNotice(List<String> noticeSectionIds, String sectionId);
}
