package com.togest.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.WireHeightDataDTO;

public interface WireHeightDataDao extends CrudCommonDao<WireHeightDataDTO> {

	void insertBatch(@Param("list") List<WireHeightDataDTO> list);

	List<WireHeightDataDTO> findDataAnaliayByDateList(@Param("entity") WireHeightDataDTO entity);

	List<Map<String,String>> findNewLastAnchorPointList(@Param("pillarIdList") List<String> pillarIdList);
	
	WireHeightDataDTO findUpWireHeightData(@Param("entity") WireHeightDataDTO wireHeightDataDTO);

	List<WireHeightDataDTO> findUpWireHeightDataList(@Param("entityList") List<WireHeightDataDTO> wireHeightDataDTOList);

	List<WireHeightDataDTO> history(WireHeightDataDTO wireHeightDataDTO);

	List<WireHeightDataDTO> checkRepeat(@Param("entity") WireHeightDataDTO wireHeightDataDTO);

}
