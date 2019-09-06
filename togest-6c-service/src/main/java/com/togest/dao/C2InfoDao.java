package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.C2Info;
import com.togest.domain.C2InfoDTO;
import com.togest.request.CheckQueryFilter;
import com.togest.request.InfoQueryFilter;

public interface C2InfoDao extends CrudCommonDao<C2InfoDTO> {

	public C2InfoDTO getByKey(String id);
	
	public int insert(C2Info entity);
	
	public int update(C2Info entity);
	
	public int updateDefectStatus(@Param("id") String id, @Param("defectdataStatus") String defectdataStatus);
	
	public C2InfoDTO findC2InfoDTO(@Param("checkDate") Date checkDate, @Param("lineId") String lineId,
			@Param("direction") String direction, @Param("systemId") String systemId,
			@Param("sectionId") String sectionId);
	
	public List<C2InfoDTO> findC2InfoDTOList(InfoQueryFilter entity);
	
	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteFalsesByPlanBaseId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteFalsesByPlanExecuteId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteByKey(@Param("ids") String ids);


}
