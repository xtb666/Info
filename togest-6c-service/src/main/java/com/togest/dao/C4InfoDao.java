package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.C4Info;
import com.togest.domain.C4InfoDTO;
import com.togest.request.CheckQueryFilter;
import com.togest.request.InfoQueryFilter;

public interface C4InfoDao extends CrudCommonDao<C4InfoDTO>{

	public C4InfoDTO getByKey(String id);
	
	public int insert(C4Info entity);
	
	public int update(C4Info entity);
	
	public int updateDefectStatus(@Param("id") String id, @Param("defectdataStatus") String defectdataStatus);
	
	public C4InfoDTO findC4InfoDTO(@Param("checkDate") Date checkDate, @Param("lineId") String lineId,
			@Param("direction") String direction, @Param("systemId") String systemId,
			@Param("sectionId") String sectionId);
	
	public List<C4InfoDTO> findC4InfoDTOList(InfoQueryFilter entity);
	
	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	
	public int deleteFalsesByPlanBaseId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	
	public int deleteFalsesByPlanExecuteId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteByKey(@Param("ids") String ids);

}
