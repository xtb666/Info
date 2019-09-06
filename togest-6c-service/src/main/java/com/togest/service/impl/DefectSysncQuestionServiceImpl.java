package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.sevice.JcwNcFeignService;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectHandleInfoDao;
import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectFlow;
import com.togest.domain.DefectHandleInfo;
import com.togest.domain.DefectReformHandle;
import com.togest.service.DefectFlowService;
import com.togest.service.DefectSysncQuestionService;

@Service
public class DefectSysncQuestionServiceImpl implements DefectSysncQuestionService {

	@Autowired
	private DefectDao dao;
	@Autowired
	private DefectHandleInfoDao defectHandleInfoDao;
	@Autowired
	private DefectFlowService defectFlowService;
	@Autowired
	private JcwNcFeignService jcwNcFeignService;
	
	private static final String REVIEW_CONCLUSION = "查无异常";
	private static final String DEFECT_LEVEL2 = "2";
	
	//向问题库同步缺陷
	public void sysncDefectData(List<String> list) {
		if(!CollectionUtils.isEmpty(list))
		{
			List<DefectHandleInfo> defectHandleInfoList = defectHandleInfoDao.getByKeys(list);
			HashMap<String, Object> defecHandleMap = new HashMap<>();
			if(!CollectionUtils.isEmpty(defectHandleInfoList)){
				defectHandleInfoList.forEach(x->{
					defecHandleMap.put(x.getId(), x.getPlanCompleteDate());
				});
			}
			Map<String, Object> defectFlowMap = new HashMap<>();
			List<DefectFlow> defectFlowList = defectFlowService.getBykeys(list);
			if(!CollectionUtils.isEmpty(defectFlowList)){
				for(DefectFlow defectFlow : defectFlowList){
					defectFlowMap.put(defectFlow.getId(), defectFlow.getProcessInstanceId());
				}
			}
			List<Map<String, Object>> mapList = CollectionUtils.newArrayList();
			List<Defect> defectList = dao.getByKeys(list);
			if(!CollectionUtils.isEmpty(defectList)){
				for(Defect defect : defectList){
					Map<String,Object> map = new HashMap<>();
					map.put("dataId", defect.getId());
					map.put("findDate", defect.getCheckDate());
					map.put("lineId", defect.getLineId());
					map.put("direction", defect.getDirection());
					map.put("workShopId", defect.getWorkShopId());
					map.put("deptId", defect.getWorkAreaId());
					map.put("psaId", defect.getPsaId());
					map.put("pillarId", defect.getPillarId());
					map.put("pillarName", defect.getPillarName());
					map.put("equId", defect.getPillarId());
					map.put("equName", defect.getPillarName() == null ? null : (defect.getPillarName()+"支柱"));
					map.put("defectType", defect.getDefectType());
					map.put("defectTypeName", defect.getDefectTypeName());
					map.put("defectLevel", defect.getDefectLevel() == null ? defect.getDefectDataLevel() : defect.getDefectLevel());
					map.put("defectGlb", defect.getDefectGlb());
					map.put("defectStatus", "1");
					map.put("defectDes", defect.getDescription());
					map.put("defectSource", "8");
					map.put("defectSourceWay", defect.getSystemId() == null ? null : defect.getSystemId().replace("C", ""));
					map.put("equCategoryId", "PillarInfo");
					map.put("startGlb", defect.getDefectGlb());
					map.put("processInstanceId", defectFlowMap.get(defect.getId()));
					map.put("planHandelDate", defecHandleMap.get(defect.getId()));
					map.put("sectionId", defect.getSectionId());
					mapList.add(map);
				}
				jcwNcFeignService.sysncDefectData(mapList);
			}
		}
	}
	
	//向问题库同步复合整改缺陷
	public void sysncDefectReformHandleData(DefectCheckHandle defectCheckHandle, DefectReformHandle defectReformHandle) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		if(defectCheckHandle != null){
			if(REVIEW_CONCLUSION.equals(defectCheckHandle.getReviewConclusion())){
				map.put("handlePerson", defectCheckHandle.getReviewPerson());
				map.put("handleDate", defectCheckHandle.getReviewDate());
				map.put("handleScheme", defectCheckHandle.getReviewConclusion());
				map.put("handlePhoto", defectCheckHandle.getReviewPhoto());
				map.put("handleResult", 0);
				map.put("defectStatus", "2");
			}else{
				map.put("handleResult", 1);
			}
		}
		if(StringUtil.isNotEmpty(defectReformHandle)
				&& StringUtil.isNotBlank(defectReformHandle.getId())){
			map.put("handlePerson", defectReformHandle.getHandlePerson());
			map.put("handleDate", defectReformHandle.getHandleDate());
			map.put("handleScheme", defectReformHandle.getHandleScheme());
			map.put("handlePhoto", defectReformHandle.getHandlePhoto());
			map.put("defectStatus", "2");
		}
		map.put("dataId", defectCheckHandle.getId());
		list.add(map);
		jcwNcFeignService.sysncDefectReformHandleData(list);
	}

	//缺陷管理 二级缺陷导入  同步到问题库 
	@Override
	public void sysncDefectImportData(Defect entity) {
		if(entity != null){
			List<Defect> defectList = Arrays.asList(entity);
			sysncDefectImportData(defectList);
		}
	}

	//检测数据管理 缺陷导入
	@Override
	public <T extends Defect> void sysncDefectImportData(List<T> defectLists) {
		if(!CollectionUtils.isEmpty(defectLists)){
			List<String> idList = new ArrayList<>();
			defectLists.forEach(defectCDTO -> {
				if(DEFECT_LEVEL2.equals(defectCDTO.getDefectLevel()) || 
						DEFECT_LEVEL2.equals(defectCDTO.getDefectDataLevel())){
					if(StringUtil.isNotBlank(defectCDTO.getId())){
						idList.add(defectCDTO.getId());
					}
				}
			});
			if(!CollectionUtils.isEmpty(idList)){
				sysncDefectData(idList);
			}
		}
	}
}
