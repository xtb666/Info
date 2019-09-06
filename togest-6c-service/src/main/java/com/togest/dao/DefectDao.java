package com.togest.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.togest.domain.Defect;
import com.togest.domain.DefectBasicDetails;
import com.togest.domain.DefectDTO;
import com.togest.domain.DefectStatisticalHandle;
import com.togest.domain.TaskFlowResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectAddField;
import com.togest.request.DefectRepeatRequest;
import com.togest.response.DefectFormDTO;
import com.togest.response.DefectRepeatData;

public interface DefectDao {

	public int insert(Defect entity);
	public int insertBatch(@Param("list") List<Defect> list);
	
	public int insertBatchV2(@Param("list") List<DefectFormDTO> list);

	public Defect checkRepeat(Defect defect);

	public List<DefectDTO> defectRepeatJudge(DefectRepeatRequest defect);

	public Defect findDefect(CQueryFilter entity);
	public List<Defect> findDefects(CQueryFilter entity);

	public int update(Defect entity);

	public List<Defect> importRepeat(CQueryFilter entity);

	public Defect defectRepeat(CQueryFilter entity);

	public int deleteFalses(@Param("ids") List<String> ids, @Param("deleteBy") String deleteBy,
			@Param("deleteIp") String deleteIp);

	public int updateDefectStatus(@Param("defectStatus") String defectStatus, @Param("id") String id);

	public int updateDefectStatusBatch(@Param("ids") List<String> ids, @Param("defectStatus") String defectStatus);

	public List<TaskFlowResponse> getTaskFlowResponseByIds(@Param("ids") List<String> ids);

	public List<DefectDTO> getHistoryDefect(CQueryFilter entity);

	public List<Defect> getByKeys(@Param("ids") List<String> ids);

	public Defect getByKey(String id);

	public List<Defect> getNoticeDefect(CQueryFilter entity);

	public List<Defect> getByInfoIds(@Param("infoIds") List<String> infoIds);

	public List<Defect> getByPlanIds(@Param("planIds") List<String> planIds);

	public List<String> getIdsByCheck1CIds(@Param("checkIds") List<String> checkIds);

	public List<String> getIdsByPlanIds(@Param("planIds") List<String> planIds);

	public List<Defect> findDefectRepeat();

	public void updateDefectRepeatCount(@Param("defectRepeatCountId") String defectRepeatCountId,
			@Param("repeatCount") Integer repeatCount,@Param("count1") Integer count1,
			@Param("id") String id);

	public void updateRepeatCount(@Param("repeatCount") Integer repeatCount, @Param("id") String id);

	public List<DefectRepeatData> findDefectRepeatData(CQueryFilter entity);

	public List<DefectRepeatRequest> checkRepeatCondition(@Param("flag") Boolean flag);

	public List<DefectStatisticalHandle> findAllDefectOfFrom();

	public List<DefectStatisticalHandle> findReformDefectOfFrom();

	public List<String> findWorkShopNameList();

	public void updateDefectName(@Param("id") String id, @Param("defectName") String defectName);

	public DefectDTO getDefectById(@Param("id") String id);

	public void updateInfoId(@Param("ids") List<String> ids, @Param("infoId") String infoId);

	public Integer changeTypicalDefectByIds(@Param("ids") String ids, @Param("typicalDefect") String typicalDefect);
	
	public List<DefectBasicDetails> findDefectBasicDetails(CQueryFilter entity);
	
	/////////////
	public List<DefectDTO> getRepeatDefect(DefectRepeatRequest entity);
	public void updateDefectIsShow(@Param("isShow") Integer isShow,
			@Param("ids") List<String> ids);
	public List<Defect> defectRepeatJudge(CQueryFilter entity);
	
	public List<Map<String,Object>> mySqlProcessList();
	
	public List<DefectFormDTO> findDefectFormPage(CQueryFilter entity);
	
	public Integer findDefectFormCount(CQueryFilter entity);
	
	public void updateDefectData(DefectAddField entity);

}
