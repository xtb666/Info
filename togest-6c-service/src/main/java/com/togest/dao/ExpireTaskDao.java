package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.ExpireTask;

public interface ExpireTaskDao {

	public ExpireTask getByKey(String id);
	
	public ExpireTask getByDataId( @Param("dataId")String dataId, @Param("bussinessType")String bussinessType);
	
	public void updateExpireDate(@Param("expireDate")Date expireDate, @Param("dataId")String dataId,@Param("bussinessType")String bussinessType);

	public List<ExpireTask> findAllList();

	public List<ExpireTask> findList(ExpireTask entity);

	public int insert(ExpireTask entity);

	public int update(ExpireTask entity);

	public int deleteByKey(String id);

	public int deleteByKeys(@Param("ids") List<String> ids);

	public int deleteByDataIds(@Param("dataIds") List<String> dataIds);
}
