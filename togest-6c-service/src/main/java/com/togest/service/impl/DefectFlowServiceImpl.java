package com.togest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.DefectFlowDao;
import com.togest.domain.DefectFlow;
import com.togest.service.DefectFlowService;
import com.togest.service.DefectService;

@Service
public class DefectFlowServiceImpl implements DefectFlowService {

	@Autowired
	private DefectFlowDao dao;
	@Autowired
	private DefectService defectService;

	@Override
	public int insert(DefectFlow entity) {
		return dao.insert(entity);
	}

	@Override
	public DefectFlow getByKey(String id) {
		return dao.getByKey(id);
	}

	@Override
	public int update(DefectFlow entity) {
		return dao.update(entity);
	}

	@Override
	public List<DefectFlow> getBykeys(List<String> ids) {
		return dao.getBykeys(ids);
	}

	public void insert(List<Map<String, String>> flowLists) {
		if (StringUtil.isNotEmpty(flowLists)) {
			for (Map<String, String> flowMap : flowLists) {
				DefectFlow df = new DefectFlow();
				df.setId(flowMap.get("bussinessKey"));
				df.setProcessInstanceId(flowMap.get("processInstanceId"));
				df.setFormKey(flowMap.get("formKey"));
				df.setTaskName(flowMap.get("taskName"));
				df.setTaskId(flowMap.get("taskId"));
				this.insert(df);
				defectService.updateDefectData(df);
			}
		}
	}

	@Override
	public void update(List<Map<String, String>> flowLists) {
		if (StringUtil.isNotEmpty(flowLists)) {
			for (Map<String, String> map : flowLists) {
				DefectFlow df = new DefectFlow();
				if (StringUtil.isNotBlank(map.get("processInstanceId"))) {
					df.setProcessInstanceId(map.get("processInstanceId"));
				}
				df.setFormKey(map.get("formKey"));
				df.setTaskName(map.get("taskName"));
				df.setTaskId(map.get("taskId"));
				df.setId(map.get("bussinessKey"));
				this.update(df);
				defectService.updateDefectData(df);
			}
		}
	}

	@Override
	public List<DefectFlow> getByProcessInstanceIds(List<String> processInstanceIds) {
		// TODO Auto-generated method stub
		return dao.getByProcessInstanceIds(processInstanceIds);
	}

}
