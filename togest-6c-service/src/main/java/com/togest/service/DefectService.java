package com.togest.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.togest.domain.DefectBasicDetails;
import com.togest.domain.DefectDTO;
import com.togest.domain.Page;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectAddField;
import com.togest.request.DefectRepeatRequest;
import com.togest.response.DefectFormDTO;

public interface DefectService {
	public List<DefectDTO> getHistoryDefect(CQueryFilter entity);

	public Page<DefectDTO> getHistoryDefect(Page page, CQueryFilter entity);

	public void sendAuditDefectData(String url, List<String> defectIds,
			String confirmPerson, Date confirmDate);

	public void sendCancelDefectData(String url, List<String> defectIds,
			String cancelPerson, Date cancelDate,String comment);
	
	public void deleteFalse(List<String> defectIds,String deleteBy,String deleteIp);
	
	public void defectRepeatData();
	public void defectRepeatData(DefectRepeatRequest entity);
	//处理绥德重复次数
	public void handleSuiDeRepeatDefect(DefectRepeatRequest entity);

	public List<Map<String, Object>> defectStatisticalForm();
	
	public List<DefectDTO> handleDefectData(CQueryFilter entity);

	public Integer changeTypicalDefectByIds(String ids, String typicalDefect);

	Page<DefectBasicDetails> findDefectBasicDetails(Page page, CQueryFilter entity);
	
	List<DefectBasicDetails> findDefectBasicDetails(CQueryFilter entity);

	public Map<String, List<DefectDTO>> getHistoryDefectOfStatus(List<DefectDTO> list);

	public Integer defectToAssortment(CQueryFilter entity);

	public void defectRepeatJudge(DefectRepeatRequest entity);
	
	public Page<DefectFormDTO> findDefectFormPage(Page page,CQueryFilter entity);
	
	public void updateDefectData(Object obj);

}
