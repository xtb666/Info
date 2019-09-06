package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.CheckTrainDTO;

public interface CheckTrainDao {

	public CheckTrainDTO getByKey(String id);

	public CheckTrainDTO getByEntity(CheckTrainDTO entity);

	public int insert(CheckTrainDTO entity);

	public int update(CheckTrainDTO entity);

	public List<CheckTrainDTO> findList(CheckTrainDTO entity);

	public int deleteFalses(@Param("ids") List<String> ids,
			@Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp,
			@Param("deleteDate") Date deleteDate);

}
