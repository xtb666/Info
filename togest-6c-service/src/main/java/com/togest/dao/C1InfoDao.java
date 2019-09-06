package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.C1Info;
import com.togest.domain.C1InfoDTO;
import com.togest.request.InfoQueryFilter;

public interface C1InfoDao extends CrudCommonDao<C1InfoDTO> {

//	public C1Info getByKey(String id);
//	
	public int insert(C1Info entity);
//	
	public int update(C1Info entity);
//	
//	public List<C1Info> findC1InfoList(PlanQueryFilter entity);
	
	public List<C1InfoDTO> findLists(InfoQueryFilter entity);
	
	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
				@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteFalsesByPlanBaseId(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy, 
			@Param("deleteIp") String deleteIp, @Param("deleteDate") Date deleteDate);
	public int deleteByKey(@Param("ids") String ids);
}
