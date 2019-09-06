package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Check1C;
import com.togest.domain.Check1CDTO;
import com.togest.request.CheckQueryFilter;

public interface Check1CDao {

	public int insert(Check1C entity);
	
	public Check1CDTO getByKey(String id);
	
	public List<Check1CDTO> getByKeys(@Param("ids") List<String> ids);
	
	public int update(Check1C entity);
	
	public List<Check1CDTO> findCheckList(CheckQueryFilter entity);
	
	public List<Check1CDTO> findCheckNoticeList(CheckQueryFilter entity);
	public int findCheckNoticeListCounts(CheckQueryFilter entity);
	
	public Check1CDTO check1CRepeat(@Param("checkDate") Date checkDate, @Param("lineId") String lineId, 
				@Param("direction") String direction,@Param("trainId") String trainId);
	
	public int deleteFalses(@Param("ids") List<String> ids,
				@Param("deleteBy") String deleteBy,
				@Param("deleteIp") String deleteIp,
				@Param("deleteDate") Date deleteDate);
}
