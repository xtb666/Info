package com.togest.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.LineFeignService;
import com.togest.clien.sevice.SupplyArmFeignService;
import com.togest.client.response.Pillar;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.PillarDao;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.DefectTypeDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.LineDTO;
import com.togest.domain.WireHeightControlHistoryDTO;
import com.togest.domain.WireHeightDataDTO;
import com.togest.domain.WireHeightLineMin;
import com.togest.service.DefectTypeService;
import com.togest.service.DictionaryService;
import com.togest.service.WireHeightLineMinService;

@Component
public class DataUtil {
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private DeptFeignService deptFeignService;
	@Autowired
	private SupplyArmFeignService supplyArmFeignService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private DefectTypeService defectTypeService;
	@Autowired
	private PillarDao pillarDao;
	@Autowired
	private WireHeightLineMinService wireHeightLineMinService;
	
	private static final String SPEED_TYPE_GAO_SU ="8";
	public static final String DYM_TYPE_ONE = "1";
	public static final String DYM_TYPE_TWO = "2";
	
	public static final Map<String,LineDTO> lineDTOMap = new ConcurrentHashMap<>();
	public static final Map<String,Integer> lineMinMap = new ConcurrentHashMap<>();
	
	/*管内各线允许最低导高值*/
	static {
		lineMinMap.put("鹰厦线", 5330);
		lineMinMap.put("峰福线", 5600);
		lineMinMap.put("衢九线", 5700);
		lineMinMap.put("沪昆线", 6200);
		lineMinMap.put("昌福线", 6250);
		lineMinMap.put("沪昆高速线", 5150);
		lineMinMap.put("合福高速线", 5150);
	}
	
	//导入结果转为实体对象
	public static <T> List<T> setData(List<Map<String, Object>> result, Class<T> cla) throws InstantiationException, IllegalAccessException {
		List<T> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			T entity = cla.newInstance();
			Map<String, Object> obj = result.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(entity, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(entity);
		}
		return lists;
	}
	
	//根据线路名称获取线路id
	public Map<String, String> getLineIdByNames(List<String> lineNames) {
		Map<String, String> lineMap = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(lineNames)) {
			List<Map<String, String>> lineList = lineFeignService
					.getIdByNames(CollectionUtils.convertToString(lineNames, ",")).getData();
			if (StringUtil.isNotEmpty(lineList)) {
				for (Map<String, String> map2 : lineList) {
					lineMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
				}
			}
		}
		return lineMap;
	}
	
	//根据部门名称获取部门id
	public Map<String, String> getDeptIdByNames(List<String> deptNames) {
		Map<String, String> deptMap = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(deptNames)) {
			List<Map<String, String>> deptList = deptFeignService
					.getIdByName(CollectionUtils.convertToString(deptNames, ","), null).getData();
			if (StringUtil.isNotEmpty(deptList)) {
				for (Map<String, String> map2 : deptList) {
					deptMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
				}
			}
		}
		return deptMap;
	}
	
	//根据站区名称获取站区id
	public Map<String, String> getPsaIdByNames(List<String> psaNames) {
		Map<String, String> psaMap = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(psaNames)) {
			List<Map<String, String>> psaNameList = supplyArmFeignService
					.getIdByNames(CollectionUtils.convertToString(psaNames, ",")).getData();
			if (StringUtil.isNotEmpty(psaNameList)) {
				for (Map<String, String> map2 : psaNameList) {
					psaMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
				}
			}
		}
		return psaMap;
	}
	
	//根据站到行别
	public Map<String, String> findDirectionByDictName() {
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService.
				findDictItemsByDictName(new String[] { "direction" }).getData();
		Map<String, Map<String, String>> dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		Map<String, String> directionMap = dictMap.get("direction");
		return directionMap;
	}
	
	//根据系统类型查询缺陷类型
	public Map<String, String> getListBySystemCode(String systemId) {
		Map<String, String> defectTypeMap = new HashMap<>();
		List<DefectTypeDTO> defectTypeList = defectTypeService.getListBySystemCode(systemId);
		if(!defectTypeList.isEmpty()) {
			defectTypeList.forEach(x ->{
				defectTypeMap.put(x.getName(), x.getId());
			});
		}
		return defectTypeMap;
	}
	
	//缺陷等级
	public Map<String, String> getDefectLevelByDictName(String dictName) {
		Map<String, String> defectLevelMap = new HashMap<>();
		List<DictionaryItemDTO> dictionaryItemDTOList = OptionalUtils.map(dictionaryService.getDictItemsListByDictName(dictName));
		if(!dictionaryItemDTOList.isEmpty()) {
			dictionaryItemDTOList.forEach(x ->{
				defectLevelMap.put(x.getCode(), x.getCode());
			});
		}
		return defectLevelMap;
	}
	
	//查找支柱
	public Map<Integer, Pillar> handlePillar(List<Integer> pillarIndex, List<Pillar> pillarList,
			Map<Integer, Pillar> pillarMap) {
		Map<String, List<Pillar>> tempPillarMap = new HashMap<String, List<Pillar>>();
		Map<String, List<Pillar>> tempMap = new HashMap<String, List<Pillar>>();
		Map<String, List<Pillar>> temp = pillarList.stream().collect(Collectors.groupingBy(Pillar::getPlId));
		temp.forEach((key, value) -> {
			Map<String, List<Pillar>> temp1 = value.stream().collect(Collectors.groupingBy(Pillar::getDirection));
			temp1.forEach((key1, value1) -> tempMap.put(key + "_" + key1, value1));
		});
		tempMap.forEach((key, value) -> {
			String[] keys = key.split("_");
			DoubleSummaryStatistics data = value.stream().collect(Collectors.summarizingDouble(d -> d.getStartKm()));
			Pillar pillar = new Pillar();
			pillar.setStartKm(data.getMin() - 0.5);
			pillar.setEndKm(data.getMax() + 0.5);
			pillar.setPlId(keys[0]);
			pillar.setDirection(keys[1]);
			List<Pillar> list = pillarDao.findPillarDataByGlb(pillar, "1");
			tempPillarMap.put(key, list);
		});

		for (int i = 0; i < pillarList.size(); i++) {
			Pillar pillar = pillarList.get(i);
			List<Pillar> list = tempPillarMap.get(pillar.getPlId() + "_" + pillar.getDirection());
			if (StringUtil.isEmpty(list)) {
				pillarMap.put(pillarIndex.get(i), null);
			} else {
				list = list.stream().filter(x->{
					//增加站区和支柱名称过滤(缺陷没有过滤)
					if(StringUtil.isNotBlank(pillar.getName()) && StringUtil.isNotBlank(pillar.getPsaId())) {
						return pillar.getName().equals(x.getName()) && pillar.getPsaId().equals(x.getPsaId());
					}
					return true;
				})
				.sorted((y1, y2) -> y1.getStartKm().compareTo(y2.getStartKm()))
						.collect(Collectors.toList());
				if(!CollectionUtils.isEmpty(list)) {
					Pillar minPillar = list.get(0);
					Pillar maxPillar = list.get(list.size() - 1);
					if (pillar.getStartKm() < minPillar.getStartKm() - 0.5
							|| pillar.getStartKm() > maxPillar.getStartKm() + 0.5) {
						pillarMap.put(pillarIndex.get(i), null);
					} else if (pillar.getStartKm() > minPillar.getStartKm() - 0.5
							&& pillar.getStartKm() < minPillar.getStartKm()) {
						pillarMap.put(pillarIndex.get(i), minPillar);
					} else if (pillar.getStartKm() < maxPillar.getStartKm() + 0.5
							&& pillar.getStartKm() > maxPillar.getStartKm()) {
						pillarMap.put(pillarIndex.get(i), maxPillar);
					} else {
						Pillar pillar1 = null;
						Pillar pillar2 = null;
						boolean fg1 = false;
						boolean fg2 = false;
						for (Pillar entity : list) {
							if (entity.getStartKm() <= pillar.getStartKm()) {
								pillar1 = entity;
								fg1 = true;
							}
							if (entity.getStartKm() >= pillar.getStartKm()) {
								pillar2 = entity;
								fg2 = true;
							}
							if (fg1 && fg2) {
								break;
							}
						}
						if (fg1 && fg2) {
							Double d1 = pillar.getStartKm() - pillar1.getStartKm();
							Double d2 = pillar2.getStartKm() - pillar.getStartKm();
							if (d1 >= d2) {
								pillarMap.put(pillarIndex.get(i), pillar2);
							} else {
								pillarMap.put(pillarIndex.get(i), pillar1);
							}
						}
					}
				}
			}

		}
		return pillarMap;
	}
	
	//计算数据
	public void caCulate(WireHeightDataDTO entity, Map<String, WireHeightControlHistoryDTO> wireHeightLineHeightDataMap, String dymType) {
		WireHeightControlHistoryDTO wireHeightControlHistoryDTO = findWireHeightContrlHistoryDTO(entity, wireHeightLineHeightDataMap);
		findReviewValue(entity, dymType);
		findDymCheckValue(entity, wireHeightControlHistoryDTO);
	}
	
	private WireHeightControlHistoryDTO findWireHeightContrlHistoryDTO(WireHeightDataDTO entity, Map<String, WireHeightControlHistoryDTO> wireHeightLineHeightDataMap) {
		WireHeightControlHistoryDTO wireHeightControlHistoryDTO = null;
		if(StringUtil.isNotBlank(entity.getWireHeightControlHistoryId())) {
			wireHeightControlHistoryDTO = entity.getWireHeightControlHistoryDTO();
		}else {
			wireHeightControlHistoryDTO = wireHeightLineHeightDataMap.get(entity.getPillarId());
			entity.setWireHeightControlHistoryId(wireHeightControlHistoryDTO.getId());
		}
		return wireHeightControlHistoryDTO;
	}
	
	private void findReviewValue(WireHeightDataDTO entity, String dymType) {
		if(DYM_TYPE_TWO.equals(dymType)) {
			entity.setDymCheckValue(entity.getSpanMin());
		}else {
			entity.setDymCheckValue(entity.getAnchorPoint());
		}
		if(null != entity.getDealDate() && null != entity.getStaticCheckDate()) {
			if(entity.getDealDate().compareTo(entity.getStaticCheckDate()) >= 0) {
				entity.setReviewValue(entity.getAdjustWireHeight());
			}
		}
	}
	
	public void findDymCheckValue(WireHeightDataDTO entity,WireHeightControlHistoryDTO wireHeightControlHistoryDTO) {
		LineDTO lineDTO = lineDTOMap.get(entity.getLineId());
		if(null == lineDTO) {
			lineDTO = OptionalUtils.map(lineFeignService.getLine(entity.getLineId()));
			if(null != lineDTO) {
				lineDTOMap.put(entity.getLineId(), lineDTO);
			}
		}
		if(null != lineDTO) {
			if(SPEED_TYPE_GAO_SU.equals(lineDTO.getSpeedType())) {
				findDefectLevelByGaoSu(entity, entity.getDymCheckValue(), wireHeightControlHistoryDTO, 1);
				findDefectLevelByGaoSu(entity, entity.getReviewValue(), wireHeightControlHistoryDTO, 2);
			}else {
				findDefectLevelByPuSu(entity, entity.getDymCheckValue(), wireHeightControlHistoryDTO, 1);
				findDefectLevelByPuSu(entity, entity.getReviewValue(), wireHeightControlHistoryDTO, 2);
			}
		}
	}

	//设置静态检测值(原因分析 普速、高速)
	private void findDefectLevelByGaoSu(WireHeightDataDTO entity, Integer checkValue, 
			WireHeightControlHistoryDTO wireHeightControlHistoryDTO, int status) {
		if(null != checkValue && checkValue > 0) {
			Integer defectLevel = null;
			StringBuilder staticCheckValueStr = new StringBuilder("因符合条款【高速铁路接触网动态检测评价标准-");
			if(checkValue >= MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_ONE) {
				defectLevel = 1;
				staticCheckValueStr.append("【1.H≥6600】】").append("，因此该处为一级缺陷");
			}else {
				WireHeightLineMin wireHeightLineMinFilter = new WireHeightLineMin();
				wireHeightLineMinFilter.setDelFlag(0);
				wireHeightLineMinFilter.setLineId(entity.getLineId());
				List<WireHeightLineMin> wireHeightLineMinList = wireHeightLineMinService.findList(wireHeightLineMinFilter);
				if(CollectionUtils.isEmpty(wireHeightLineMinList)) {
					Integer lineMinValue = lineMinMap.get(entity.getLineName());
					if(null != lineMinValue) {
						if(checkValue < lineMinValue) {
							defectLevel = 1;
							staticCheckValueStr.append("【2.H＜该区段允许的最低值】】").append("，因此该处为一级缺陷");
						}
					}
				}else if(checkValue < wireHeightLineMinList.get(0).getMinValue()) {
					defectLevel = 1;
					staticCheckValueStr.append("【2.H＜该区段允许的最低值】】").append("，因此该处为一级缺陷");
				}
				if(null == defectLevel ) {
					if(checkValue >= MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_TWO
							&& checkValue <= MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_THREE) {
						defectLevel = 1;
						staticCheckValueStr.append("【1.6500≤H＜6600】】").append("，因此该处为一级缺陷");
					}
				}
				if(null == defectLevel ) {
					if(null != wireHeightControlHistoryDTO) {
						Integer standarLineHeight = wireHeightControlHistoryDTO.getStandarLineHeight();
						if(checkValue >= standarLineHeight + MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_FOUR) {
							defectLevel = 1;
							staticCheckValueStr.append("【2.H≥标准值+150】】").append("，因此该处为一级缺陷");
						}
						if(null == defectLevel ) {
							if(checkValue >= standarLineHeight - MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_FIVE) {
								defectLevel = 2;
								staticCheckValueStr.append("【3.H＜标准值-100】】").append("，因此该处为二级缺陷");
							}
						}
						if(null == defectLevel ) {
							if(checkValue >= standarLineHeight + MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_ONE 
									&& checkValue <= standarLineHeight + MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_TWO) {
								defectLevel = 2;
								staticCheckValueStr.append("【1.标准值+100≤H＜标准值+150】】").append("，因此该处为二级缺陷");
							}
						}
						if(null == defectLevel ) {
							if(checkValue >= standarLineHeight - MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_THREE  
									&& checkValue <= standarLineHeight - MessageUtils.DymCheck.DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_FOUR) {
								defectLevel = 2;
								staticCheckValueStr.append("【1.标准值-100≤H＜标准值－50】】").append("，因此该处为二级缺陷");
							}
						}
					}
				}
			}
			if(status == 1) {
				entity.setDefectLevel(defectLevel != null ? String.valueOf(defectLevel) : null);
			}else if(status == 2) {
				entity.setReviewDefectLevel(defectLevel != null ? String.valueOf(defectLevel) : null);
				if(null != defectLevel) {
					entity.setStaticCheckValue(staticCheckValueStr.toString());
					entity.setIsReviewExist(1);
				}else {
					entity.setStaticCheckValue(null);
					entity.setIsReviewExist(0);
				}
			}
		}
	}

	private void findDefectLevelByPuSu(WireHeightDataDTO entity, Integer checkValue, 
			WireHeightControlHistoryDTO wireHeightControlHistoryDTO, int status) {
		if(null != checkValue) {
			Integer defectLevel = null;
			StringBuilder staticCheckValueStr = new StringBuilder("因符合条款【高速铁路接触网动态检测评价标准-");
			if(checkValue >= MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_ONE) {
				defectLevel = 1;
				staticCheckValueStr.append("【1.H≥6600】】").append("，因此该处为一级缺陷");
			}else {
				WireHeightLineMin wireHeightLineMinFilter = new WireHeightLineMin();
				wireHeightLineMinFilter.setDelFlag(0);
				wireHeightLineMinFilter.setLineId(entity.getLineId());
				List<WireHeightLineMin> wireHeightLineMinList = wireHeightLineMinService.findList(wireHeightLineMinFilter);
				if(CollectionUtils.isEmpty(wireHeightLineMinList)) {
					Integer lineMinValue = lineMinMap.get(entity.getLineName());
					if(null != lineMinValue) {
						if(checkValue < lineMinValue) {
							defectLevel = 1;
							staticCheckValueStr.append("【2.H＜该区段允许的最低值】】").append("，因此该处为一级缺陷");
						}
					}
				}else if(checkValue < wireHeightLineMinList.get(0).getMinValue()) {
					defectLevel = 1;
					staticCheckValueStr.append("【2.H＜该区段允许的最低值】】").append("，因此该处为一级缺陷");
				}
				if(null == defectLevel ) {
					if(checkValue >= MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_TWO
							&& checkValue <= MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_THREE) {
						defectLevel = 1;
						staticCheckValueStr.append("【1.6500≤H＜6600】】").append("，因此该处为一级缺陷");
					}
				}
				if(null == defectLevel ) {
					if(null != wireHeightControlHistoryDTO) {
						Integer standarLineHeight = wireHeightControlHistoryDTO.getStandarLineHeight();
						if(checkValue >= standarLineHeight + MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_FOUR) {
							defectLevel = 1;
							staticCheckValueStr.append("【2.H≥标准值+250】】").append("，因此该处为一级缺陷");
						}
						if(null == defectLevel) {
							if(checkValue >= standarLineHeight - MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_FIVE) {
								defectLevel = 1;
							staticCheckValueStr.append("【3.H＜标准值-150】】").append("，因此该处为一级缺陷");
							}
						}
						if(null == defectLevel) {
							if(checkValue >= standarLineHeight + MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_ONE 
									&& checkValue < standarLineHeight + MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_TWO) {
								defectLevel = 2;
								staticCheckValueStr.append("【1.标准值+150≤H＜标准值+250】】").append("，因此该处为二级缺陷");
							}
						}
						if(null == defectLevel) {
							if(checkValue >= standarLineHeight - MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_THREE  
									&& checkValue < standarLineHeight - MessageUtils.DymCheck.DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_FOUR) {
								defectLevel = 2;
								staticCheckValueStr.append("【2.标准值-150≤H＜标准值－100】】").append("，因此该处为二级缺陷");
							}
						}
					}
				}
			}
			if(status == 1) {
				entity.setDefectLevel(defectLevel != null ? String.valueOf(defectLevel) : null);
			}else if(status == 2) {
				entity.setReviewDefectLevel(defectLevel != null ? String.valueOf(defectLevel) : null);
				if(null != defectLevel) {
					entity.setStaticCheckValue(staticCheckValueStr.toString());
					entity.setIsReviewExist(1);
				}else {
					entity.setStaticCheckValue(null);
					entity.setIsReviewExist(0);
				}
			}
		}
	}

	/**
	 *  1、可抬(+)/需落(-)道量 = 调整后导高-下限值（默认6370）
  	    2、需落道起值 = 调整后导高-下限值>0?0:调整后导高-下限值
  		3、需落道止值 = 调整后导高-上限值(默认6480)
  	    4、轨面变化量 =本次复核轨面高差-年度复核轨面高差
  		5、计划调整量 = 标准导高 - 最新测量值
  		6、两相邻点高差
  		7、获取上次是否有缺陷，这次是否重复缺陷
	 */
	public void findNowAndUpWireHeightData(List<WireHeightDataDTO> wireHeightDataList, Map<String, List<WireHeightDataDTO>> upWireHeightDataMap, boolean isExport) {
		for(int i= 0; i<wireHeightDataList.size(); i++) {
			WireHeightDataDTO entity = wireHeightDataList.get(i);
			WireHeightControlHistoryDTO wireHeightControlHistoryDTO = entity.getWireHeightControlHistoryDTO();
			entity.setStandarLineHeight(wireHeightControlHistoryDTO.getStandarLineHeight());
			// 1、2、3、4
			findLiftAndNeedAbleValue(entity, wireHeightControlHistoryDTO);
			// 5、6
			if(isExport) {
				if(null != entity.getReviewValue()) {
					entity.setPlanAdjustValue(entity.getStandarLineHeight() - entity.getReviewValue());
					if(i< wireHeightDataList.size()-1) {
						WireHeightDataDTO downWireHeightDataDTO = wireHeightDataList.get(i+1);
						if(null != downWireHeightDataDTO.getReviewValue()) {
							entity.setAdjacentHeightValue(entity.getReviewValue() - downWireHeightDataDTO.getReviewValue());
						}
					}
				}
			}
			//7
			if(upWireHeightDataMap != null && upWireHeightDataMap.size() > 0) {
				List<WireHeightDataDTO> pillarWireHeightDataList = upWireHeightDataMap.get(entity.getPillarId());
				if(!CollectionUtils.isEmpty(pillarWireHeightDataList)) {
					for(WireHeightDataDTO upWireHeightDataDTO : pillarWireHeightDataList) {
						if(entity.getCheckDate().compareTo(upWireHeightDataDTO.getCheckDate()) > 0) {
							if(null != upWireHeightDataDTO.getAnchorPoint() && null != entity.getAnchorPoint()) {
								entity.setUpMonthAnchorPoint(upWireHeightDataDTO.getAnchorPoint());
								entity.setChangeValue(entity.getAnchorPoint() - entity.getUpMonthAnchorPoint());
							}
							if(isExport) {
								if(StringUtil.isNotBlank(entity.getDefectLevel())) {
									if(StringUtil.isNotBlank(upWireHeightDataDTO.getDefectLevel())) {
										entity.setIsRepeat(1);
									}else {
										entity.setIsRepeat(0);
									}
								}else {
									entity.setIsRepeat(0);
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	public void findLiftAndNeedAbleValue(WireHeightDataDTO entity, WireHeightControlHistoryDTO wireHeightControlHistoryDTO) {
		if(null != entity.getAdjustWireHeight()) {
			Integer liftableValue = entity.getAdjustWireHeight() - wireHeightControlHistoryDTO.getStandarLineHeightDown();
			Integer needStartValue = entity.getAdjustWireHeight() - wireHeightControlHistoryDTO.getStandarLineHeightDown();
			Integer needEndValue = entity.getAdjustWireHeight() - wireHeightControlHistoryDTO.getStandarLineHeightUp();
			entity.setLiftableValue(liftableValue < 0 ? 0 : liftableValue);
			entity.setNeedStartValue(needStartValue > 0 ? 0 : needStartValue);
			entity.setNeedEndValue(needEndValue);
		}
		if(null != entity.getCurrentReviewRailHeightValue() && null != entity.getYearReviewRailHeightValue()) {
			entity.setRailChangValue(entity.getCurrentReviewRailHeightValue() - entity.getYearReviewRailHeightValue());
		}
	}
}
