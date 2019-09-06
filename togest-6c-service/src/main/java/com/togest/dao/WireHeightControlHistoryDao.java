package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.WireHeightControlHistoryDTO;


public interface WireHeightControlHistoryDao extends CrudCommonDao<WireHeightControlHistoryDTO> {

	List<WireHeightControlHistoryDTO> findLastNewWireHeightByPillars(@Param("pillarIdList") List<String> pillarIdList);

	List<WireHeightControlHistoryDTO> checkRepeat(@Param("entity") WireHeightControlHistoryDTO wireHeightControlHistoryDTO);

	Integer findMaxVersion(@Param("entity") WireHeightControlHistoryDTO entity);

	WireHeightControlHistoryDTO findHistoryByMaxVersion(@Param("controlId") String controlId);
}
