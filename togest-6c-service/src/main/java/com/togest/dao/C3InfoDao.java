package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.C1InfoDTO;
import com.togest.domain.C3Info;
import com.togest.domain.C3InfoDTO;
import com.togest.request.CheckQueryFilter;
import com.togest.request.InfoQueryFilter;

public interface C3InfoDao extends CrudCommonDao<C3InfoDTO> {

	public C3InfoDTO getByKey(String id);
	
	public int insert(C3Info entity);
	
	public int update(C3Info entity);
	
	public C3InfoDTO findC3InfoDTO(@Param("checkDate") Date checkDate, @Param("lineId") String lineId,
			@Param("direction") String direction, @Param("systemId") String systemId,
			@Param("sectionId") String sectionId);
	
	public List<C3InfoDTO> findC3InfoDTOList(InfoQueryFilter entity);
	
	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteByKey(@Param("ids") String ids);

}
