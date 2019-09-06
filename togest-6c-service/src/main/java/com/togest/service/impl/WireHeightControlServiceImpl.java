package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.client.response.Pillar;
import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.WireHeightControlDao;
import com.togest.domain.WireHeightControlDTO;
import com.togest.domain.WireHeightControlHistoryDTO;
import com.togest.service.WireHeightControlHistoryService;
import com.togest.service.WireHeightControlService;
import com.togest.service.WireHeightDataService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.DataUtil;
import com.togest.util.MessageUtils;

@Service
public class WireHeightControlServiceImpl extends CrudCommonService<WireHeightControlDao, WireHeightControlDTO>
		implements WireHeightControlService {
	@Autowired
	private WireHeightControlHistoryService wireHeightControlHistoryService;
	@Autowired
	private ImportClient importClient;
	@Autowired
	private WireHeightDataService wireHeightDataService;
	@Autowired
	private DataUtil dataUtil;

	@Override
	public int save(WireHeightControlDTO entity){
		if(StringUtil.isNotBlank(entity.getPillarId())) {
			if(StringUtil.isNotBlank(entity.getId())) {
				checkSaveOrUpdate(entity);
				super.save(entity);
				int version = 1;
				WireHeightControlHistoryDTO maxVersionHistory = wireHeightControlHistoryService.findHistoryByMaxVersion(entity.getId());
				if(null != maxVersionHistory) {
					maxVersionHistory.setIsNew(0);
					wireHeightControlHistoryService.save(maxVersionHistory);
					version = maxVersionHistory.getVersion() + 1;
				}
				WireHeightControlHistoryDTO wireHeightControlHistoryDTO = new WireHeightControlHistoryDTO();
				try {
					BeanUtils.copyProperties(wireHeightControlHistoryDTO, entity);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				wireHeightControlHistoryDTO.setId(null);
				wireHeightControlHistoryDTO.setControlId(entity.getId());
				wireHeightControlHistoryDTO.setIsNew(1);
				wireHeightControlHistoryDTO.setVersion(version);
				return wireHeightControlHistoryService.save(wireHeightControlHistoryDTO);
			}else {
				int version = 1;
				List<WireHeightControlDTO> list = findWireHeightControlList(entity);
				if(!CollectionUtils.isEmpty(list)) {
					WireHeightControlDTO wireHeightControlDTO = list.get(0);
					entity.setId(wireHeightControlDTO.getId());
					checkSaveOrUpdate(entity);
					entity.preUpdate();
					dao.update(entity);
				}else {
					checkSaveOrUpdate(entity);
					entity.preInsert();
					dao.insert(entity);
				}
				WireHeightControlHistoryDTO maxVersionHistory = wireHeightControlHistoryService.findHistoryByMaxVersion(entity.getId());
				if(null != maxVersionHistory) {
					maxVersionHistory.setIsNew(0);
					wireHeightControlHistoryService.save(maxVersionHistory);
					version = maxVersionHistory.getVersion() + 1;
				}
				WireHeightControlHistoryDTO wireHeightControlHistoryDTO = new WireHeightControlHistoryDTO();
				try {
					BeanUtils.copyProperties(wireHeightControlHistoryDTO, entity);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				wireHeightControlHistoryDTO.setId(null);
				wireHeightControlHistoryDTO.setControlId(entity.getId());
				wireHeightControlHistoryDTO.setIsNew(1);
				wireHeightControlHistoryDTO.setVersion(version);
				return wireHeightControlHistoryService.save(wireHeightControlHistoryDTO);
			}
		}
		return 0;
	}

	private void checkSaveOrUpdate(WireHeightControlDTO entity) {
		entity.setRecordDate(new Date());
		entity.setLastModifyDate(new Date());
	}

	private List<WireHeightControlDTO> findWireHeightControlList(WireHeightControlDTO entity) {
		WireHeightControlDTO WireHeightControlDTO = new WireHeightControlDTO();
		WireHeightControlDTO.setPillarId(entity.getPillarId());
		WireHeightControlDTO.setDelFlag(0);
		return dao.findList(WireHeightControlDTO);
	}

	@Override
	public Map<String, Object> importData(String fileName, InputStream inputStream, String templetId, Object object,
			Map<String, Object> propMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();

		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			
			List<String> lineNames = CollectionUtils.distinctExtractToList(result, "lineName", String.class);
			List<String> workAreaNames = CollectionUtils.distinctExtractToList(result, "workAreaName", String.class);
			List<String> psaNames = CollectionUtils.distinctExtractToList(result, "psaName", String.class);
			
			Map<String, String> lineMap = dataUtil.getLineIdByNames(lineNames);
			Map<String, String> deptMap= dataUtil.getDeptIdByNames(workAreaNames);
			Map<String, String> psaMap = dataUtil.getPsaIdByNames(psaNames);
			Map<String, String> directionMap = dataUtil.findDirectionByDictName();
			
			List<Pillar> pillarList = new ArrayList<Pillar>();
			List<Integer> indexList = new ArrayList<Integer>();
			Map<Integer, Pillar> pillarMap = new HashMap<Integer, Pillar>();
			if (StringUtil.isNotEmpty(result)) {
				List<WireHeightControlDTO> dataList = DataUtil.setData(result, WireHeightControlDTO.class);
				for (int i = 0; i < dataList.size(); i++) {
						WireHeightControlDTO entity = dataList.get(i);
						check(entity, lineMap, entity.getLineName(), 1);
						check(entity, deptMap, entity.getWorkAreaName(), 2);
						check(entity, psaMap, entity.getPsaName(), 4);
						check(entity, directionMap, entity.getDirectionName(), 5);
						pillar(entity, i, pillarList, indexList);
				}
				dataUtil.handlePillar(indexList, pillarList, pillarMap);
				for (int i = 0; i < dataList.size(); i++) {
					WireHeightControlDTO entity = dataList.get(i);
					if(null != pillarMap.get(i)) {
						entity.setPillarId(pillarMap.get(i).getId());
					}
					boolean fg = checkTips(entity, (i+2), errorMsg);
					if(!fg) {
						if(save(entity) > 0) {
							count ++;
						}
					}
				}
			}else {
				errorMsg.append("数据为空,请修改后上传");
			}
		}
		catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		map.put("successMsg", "成功"+ count +"条数据");
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
//		map.put("repeatCount", repeatCount);
//		map.put("repeatMsg", repeatStr.toString());
		return map;
	}

	private void check(WireHeightControlDTO entity, Map<String, String> map, String name, int status) {
		if(StringUtil.isNotBlank(name)) {
			String id  = map.get(name);
			if(status == 1) {
				entity.setLineId(id);
			}else if(status == 2) {
				entity.setWorkAreaId(id);
			}else if(status == 4) {
				entity.setPsaId(id);
			}else if(status == 5) {
				entity.setDirection(id);
			}
		}
	}
	
	private void pillar(WireHeightControlDTO entity, int i, List<Pillar> pillarList, List<Integer> indexList) {
		Pillar query = new Pillar();
		if(StringUtil.isNotBlank(entity.getLineId()) && StringUtil.isNotBlank(entity.getDirection()) 
				&& StringUtil.isNotBlank(entity.getGlb())) {
			query.setPlId(entity.getLineId());
			query.setDirection(entity.getDirection());
			query.setPsaId(entity.getPsaId());
			query.setStartKm(glbStandard(entity.getGlb()));
			query.setName(entity.getPillarName());
			pillarList.add(query);
			indexList.add(i);
		}
	}
	
	private boolean checkTips(WireHeightControlDTO entity, int i, StringBuilder errorMsg) {
		boolean fg = false;
		//线路
		if(StringUtil.isBlank(entity.getLineName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_LINE + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getLineId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_LINE + "!<br/>");
			fg = true;
		}
		//工区
		if(StringUtil.isBlank(entity.getWorkAreaName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_WORK_AREA + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getWorkAreaId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_WORK_AREA + "!<br/>");
			fg = true;
		}
		//站区
		if(StringUtil.isBlank(entity.getPsaName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_SUPPLY_ARM + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getPsaId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_SUPPLY_ARM + "!<br/>");
			fg = true;
		}
		//行别
		if(StringUtil.isBlank(entity.getDirectionName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_DIRECTION + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getDirection())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_DIRECTION + "!<br/>");
			fg = true;
		}
		//支柱信息
		if(StringUtil.isBlank(entity.getPillarId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_PILLAR + "!<br/>");
			fg = true;
		}
		//校验导高标准值
		if(null == entity.getStandarLineHeight()) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_STANDAR_LINE_HEIGHT + "!<br/>");
			fg = true;
		}
		//上限值
		if(null == entity.getStandarLineHeightUp()) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_STANDAR_LINEHEIGHT_UP + "!<br/>");
			fg = true;
		}
		//下限值
		if(null == entity.getStandarLineHeightDown()) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_STANDAR_LINEHEIGHT_DOWN + "!<br/>");
			fg = true;
		}
		return fg;
	}
	
	public static double glbStandard(String str) {
		if (StringUtil.isNotBlank(str)) {
			str = str.replace("K", "").replace("k", "").replace("+", ".");
			return Double.parseDouble(str);
		}
		return -1;
	}

	
}
