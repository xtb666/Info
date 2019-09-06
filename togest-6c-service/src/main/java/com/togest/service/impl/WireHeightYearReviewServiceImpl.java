package com.togest.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.client.response.Pillar;
import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.WireHeightYearReviewDao;
import com.togest.domain.WireHeightControlDTO;
import com.togest.domain.WireHeightDataDTO;
import com.togest.domain.WireHeightYearReviewDTO;
import com.togest.service.WireHeightYearReviewService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.DataUtil;
import com.togest.util.MessageUtils;

@Service
public class WireHeightYearReviewServiceImpl extends CrudCommonService<WireHeightYearReviewDao, WireHeightYearReviewDTO>
		implements WireHeightYearReviewService {

	@Autowired
	private ImportClient importClient;
	@Autowired
	private DataUtil dataUtil;
	
	@Override
	public int save(WireHeightYearReviewDTO entity){
		checkRepeat(entity);
		return super.save(entity);
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
			Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
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
				List<WireHeightYearReviewDTO> batchInsertList = new ArrayList<>();
				List<WireHeightYearReviewDTO> dataList = DataUtil.setData(result, WireHeightYearReviewDTO.class);
				for (int i = 0; i < dataList.size(); i++) {
					WireHeightYearReviewDTO entity = dataList.get(i);
					check(entity, lineMap, entity.getLineName(), 1);
					check(entity, deptMap, entity.getWorkAreaName(), 2);
					check(entity, psaMap, entity.getPsaName(), 4);
					check(entity, directionMap, entity.getDirectionName(), 5);
					pillar(entity, i, pillarList, indexList);
				}
				dataUtil.handlePillar(indexList, pillarList, pillarMap);
				
				for (int i = 0; i < dataList.size(); i++) {
					WireHeightYearReviewDTO entity = dataList.get(i);
					if(null != pillarMap.get(i)) {
						entity.setPillarId(pillarMap.get(i).getId());
					}
					boolean fg = checkTips(entity, (i+2), errorMsg);
					entity.setFg(fg);
				}
				
				Map<String,List<WireHeightYearReviewDTO>> listMap = new HashMap<>();
				Map<String, List<WireHeightYearReviewDTO>> pMap = dataList.stream().filter(entity -> !entity.isFg()).collect(Collectors.groupingBy(WireHeightYearReviewDTO::getPillarId));
				pMap.forEach((key,value)->{
					Map<Integer, List<WireHeightYearReviewDTO>> yearMap = value.stream().collect(Collectors.groupingBy(WireHeightYearReviewDTO::getYear));
					yearMap.forEach((key2,value2) ->{
						listMap.put(key+"_"+key2, value2);
					});
				});
				
				for (int i = 0; i < dataList.size(); i++) {
					WireHeightYearReviewDTO entity = dataList.get(i);
					if(!entity.isFg()) {
						List<WireHeightYearReviewDTO> list = listMap.get(entity.getPillarId() + "_" + entity.getYear());
						if(CollectionUtils.isEmpty(list) || list.size() == 1) {
							if (!checkRepeat(entity)) {
								entity.preInsert();
								batchInsertList.add(entity);
							}else {
								entity.preUpdate();
								dao.update(entity);
								repeatCount++;
							}
						}else {
							if (!checkRepeat(entity)) {
								entity.preInsert();
								dao.insert(entity);
								count++;
							}else {
								entity.preUpdate();
								dao.update(entity);
								repeatCount++;
							}
						}
					}
				}
				
				if(!CollectionUtils.isEmpty(batchInsertList)) {
					dao.insertBatch(batchInsertList);
					count += batchInsertList.size();
				}
			}else {
				errorMsg.append("数据为空,请修改后上传");
			}
		}
		catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		StringBuilder successMsg = new StringBuilder("");
		if(count > 0) {
			successMsg.append("成功"+ count +"条数据");
		}
		if(repeatCount > 0) {
			if(count > 0) {
				successMsg.append(",").append("修改" + repeatCount + "条数据");
			}else {
				successMsg.append("修改" + repeatCount + "条数据");
			}
		}
		map.put("successMsg", successMsg.toString());
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		map.put("repeatCount", repeatCount);
//		map.put("repeatMsg", repeatStr.toString());
		return map;
	}

	private boolean checkRepeat(WireHeightYearReviewDTO entity) {
		WireHeightYearReviewDTO wireHeightYearReviewDTO = new WireHeightYearReviewDTO();
		wireHeightYearReviewDTO.setYear(entity.getYear());
		wireHeightYearReviewDTO.setPillarId(entity.getPillarId());
		List<WireHeightYearReviewDTO> wireHeightYearReviewDTOList = dao.checkRepeat(wireHeightYearReviewDTO);
		WireHeightYearReviewDTO dto = !CollectionUtils.isEmpty(wireHeightYearReviewDTOList) ? wireHeightYearReviewDTOList.get(0) : null;
		if ((dto == null)) {
		     return false;
		}else {
			entity.setId(dto.getId());
			return true;
		}
	}

	private void check(WireHeightYearReviewDTO entity, Map<String, String> map, String name, int status) {
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
	
	private void pillar(WireHeightYearReviewDTO entity, int i, List<Pillar> pillarList, List<Integer> indexList) {
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
	
	private boolean checkTips(WireHeightYearReviewDTO entity, int i, StringBuilder errorMsg) {
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
		//年度信息
		if(null == entity.getYear()) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_YEAR + "!<br/>");
			fg = true;
		}
		//年度复核轨面高差信息
		if(null == entity.getYearReviewRailHeightValue()) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_YEAR_REVIEW_RAIL_HEIGHT_VALUE + "!<br/>");
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
