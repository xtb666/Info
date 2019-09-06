package com.togest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.dao.upgrade.CrudCommonDao;
import com.togest.domain.WireHeightYearReviewDTO;
public interface WireHeightYearReviewDao extends CrudCommonDao<WireHeightYearReviewDTO> {

	void insertBatch(@Param("list") List<WireHeightYearReviewDTO> batchInsertList);

	List<WireHeightYearReviewDTO> checkRepeat(@Param("entity") WireHeightYearReviewDTO wireHeightYearReviewDTO);


}
