package com.togest.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Defect1CDTO;
import com.togest.request.CQueryFilter;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;

public interface Defect1CDao extends SixCDao<Defect1CDTO, DefectC1Response, DefectC1Form> {

	public List<DefectC1Form> findC1FormForNotice(CQueryFilter entity);

	public List<DefectC1Form> findC1DefectByIds(@Param("ids") List<String> ids);

	public List<DefectC1Form> findC1FormBySectionNotice(@Param("noticeSectionIds") List<String> noticeSectionIds,
			@Param("sectionId") String sectionId);

	public List<Map<String, String>> findDefectFormList(CQueryFilter entity);

	public List<String> handleDefectStart();

	public List<String> handleDefectAudit();

	public List<String> handleDefectReceive();

}
