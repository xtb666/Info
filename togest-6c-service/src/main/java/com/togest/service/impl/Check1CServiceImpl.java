package com.togest.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.DeptLineFeignService;
import com.togest.client.response.Pillar;
import com.togest.client.response.SectionMileage;
import com.togest.code.client.ImportClient;
import com.togest.code.client.ImportConfigClient;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectConstants;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.dao.Check1CDao;
import com.togest.dao.Check1CSectionDao;
import com.togest.dao.Defect1CDao;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectHandleInfoDao;
import com.togest.dao.DefectRepeatHistoryDao;
import com.togest.dao.PillarDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.Check1C;
import com.togest.domain.Check1CDTO;
import com.togest.domain.Check1CSectionDTO;
import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectHandleInfo;
import com.togest.domain.DefectRepeatHistory;
import com.togest.domain.DefectTypeDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.ImportExcelConfigDTO;
import com.togest.domain.Page;
import com.togest.domain.SimplePage;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.CheckQueryFilter;
import com.togest.response.DefectFormDTO;
import com.togest.service.Check1CService;
import com.togest.service.Defect1CService;
import com.togest.service.DefectAssortmentService;
import com.togest.service.DefectService;
import com.togest.service.DefectSysncQuestionService;
import com.togest.service.DefectTypeService;
import com.togest.service.DictionaryService;
import com.togest.service.LineService;
import com.togest.utils.PageUtils;

@Service
public class Check1CServiceImpl implements Check1CService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Check1CServiceImpl.class);

	@Autowired
	private DefectDao defectDao;
	@Autowired
	private Defect1CDao defectC1Dao;
	@Autowired
	private DefectHandleInfoDao defectHandleInfoDao;
	@Autowired
	private Check1CDao dao;
	@Autowired
	private DefectRepeatHistoryDao defectRepeatHistoryDao;
	@Autowired
	private Check1CSectionDao checkSectionDao;
	@Autowired
	private ImportClient importService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ImportConfigClient importConfigClient;
	@Autowired
	private LineService lineService;
	@Autowired
	private DeptLineFeignService deptLineFeignService;
	@Autowired
	private Defect1CService defect1CService;
	@Autowired
	private PillarDao pillarDao;
	@Autowired
	private DefectTypeService defectTypeService;
	@Autowired
	private DeptFeignService deptFeignService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private DefectAssortmentService defectAssortmentService;
	@Autowired
	private DefectSysncQuestionService defectSysncQuestionService;
	
	//@Value("${my.defect.isDefectAssortment}")
	//private Boolean isDefectAssortment = false;

	private static final String ID = "id";
	private static final String LINEID = "lineId";
	private static final String LINENAME = "lineName";
	private static final String SECTIONID = "sectionId";
	private static final String DEPTID = "deptId";
	private static final String DIRECTION = "direction";
	private static final String CHECKDATE = "checkDate";
	private static final String NAME = "name";
	private static final String PARENTID = "parentId";
	private static final String WORKAREATYPE = "1";
	private static final String SPEEDTYPE = "speedType";
	private static final String CHECK1C = "Check1C";
	private static final String C1CHECKDEFECT = "C1CheckDefect";
	private static final String[] DICTNAMES = new String[] { "direction", "speed_type", "defect_grade" };
	private static final Map<String, String> deptMap = new HashMap<String, String>();
	private static final String DEPARTMENT_CHANGE_FLAG = "1";

	@Override
	public int save(List<Check1CDTO> check1cList) {
		Date date = new Date();
		for (Check1CDTO check1c : check1cList) {
			check1c.preInsert();

			Integer count = check1c.getCount();
			BigDecimal big = new BigDecimal(check1c.getAverageSpeed() / count);
			check1c.setAverageSpeed(big.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
			check1c.setDetectMileage(check1c.getQualifiedMileage() + check1c.getUnqualifiedMileage());
			check1c.setCreateDate(date);
			check1c.setUnqualifiedDetail(check1c.getUnqualifiedList().stream().collect(Collectors.joining(", ")));
			dao.insert(check1c);
			Map<String, Check1CSectionDTO> checkSectionMap = check1c.getCheckSectionMap();
			for (Check1CSectionDTO entity : checkSectionMap.values()) {
				Integer count2 = entity.getCount();
				BigDecimal big2 = new BigDecimal(entity.getAverageSpeed() / count2);
				entity.preInsert();
				if (StringUtil.isBlank(entity.getWorkShopId()) && StringUtil.isNotBlank(entity.getDeptId())) {
					if (deptMap.containsKey(entity.getDeptId())) {
						entity.setWorkShopId(deptMap.get(entity.getDeptId()));
					} else {
						String workShopId = deptFeignService.getParentDept(entity.getDeptId()).getData().getId();
						deptMap.put(entity.getDeptId(), workShopId);
						entity.setWorkShopId(workShopId);
					}
				}
				entity.setCheckId(check1c.getId());
				entity.setCreateBy(check1c.getCreateBy());
				entity.setCreateIp(check1c.getCreateIp());
				entity.setAverageSpeed(big2.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
				entity.setDetectMileage(entity.getQualifiedMileage() + entity.getUnqualifiedMileage());
				entity.setUnqualifiedDetail(entity.getUnqualifiedList().stream().collect(Collectors.joining(", ")));
				checkSectionDao.insert(entity);
			}

		}
		return check1cList.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String createBy,
			String createIp, String planId, String trainId, String infoId) {// 处级

		long start, end = 0l;
		float time = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = false;
		Boolean erroeFlag = false;
		int checkCount = 0;
		int checkRepeat = 0;
		int defectCount = 0;
		int defectRepeat = 0;
		List<Integer> defectRepeatIndex = new ArrayList<>();
		StringBuilder defectError = new StringBuilder();
		StringBuilder checkRepeatStr = new StringBuilder();
		StringBuilder checkErrorStr = new StringBuilder();
		List<Defect1CDTO> defects = new ArrayList<>();
		List<List<Map<String, Object>>> list = null;
		List<Map<String, Object>> checkList = null;
		List<Map<String, Object>> defectList = null;
		Map<String, Check1CDTO> checkMap = new HashMap<>();
		// Map<String, List<SectionMileage>> sectionMileageMap = new
		// HashMap<String, List<SectionMileage>>();
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService.findDictItemsByDictName(DICTNAMES).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		try {
			LOGGER.error("开始查询模板");
			List<ImportExcelConfigDTO> checkConfig = importConfigClient.getImportExcelConfigResponseByData(CHECK1C)
					.getData();
			List<ImportExcelConfigDTO> defectConfig = importConfigClient
					.getImportExcelConfigResponseByData(C1CHECKDEFECT).getData();
			List<String> importExcelConfigIds = new ArrayList<>();
			if (StringUtil.isNotEmpty(checkConfig)) {
				importExcelConfigIds.add(defectConfig.get(0).getId());
			} else {
				checkErrorStr.append("检测作业解析模板未找到！");
			}
			if (StringUtil.isNotEmpty(defectConfig)) {
				importExcelConfigIds.add(checkConfig.get(0).getId());
			} else {
				checkErrorStr.append("缺陷解析模板未找到！");
			}
			list = importService.analyzeExcelData(originalFilename, inputStream, importExcelConfigIds);
			boolean haveDefect = true, haveCheckInfo = true;
			if (StringUtil.isEmpty(list)) {
				checkErrorStr.append("解析数据为空");
				haveDefect = false;
				haveCheckInfo = false;
			} else {
				LOGGER.error("解析模板数量 --- " + list.size());
				haveDefect = StringUtil.isNotEmpty(list.get(0));
				if (list.size() > 1)
					haveCheckInfo = StringUtil.isNotEmpty(list.get(1));
			}

			if (haveCheckInfo) {
				defectList = list.get(0);
				checkList = list.get(1);
				if (StringUtil.isEmpty(defectList) && StringUtil.isEmpty(checkList)) {
					checkErrorStr.append("解析数据为空");
					erroeFlag = true;
				}
				Map<String, List<SectionMileage>> deptMileages = new HashMap<String, List<SectionMileage>>();
				Map<String, String> lineMap = new HashMap<String, String>();
				// 处理线路、管辖范围
				setData(checkList, lineMap, deptMileages);
				for (int i = 0; i < checkList.size(); i++) {
					if (checkList.get(i) == null || checkList.get(i).isEmpty()) {
						continue;
					}
					Check1CDTO checkDTO = new Check1CDTO();
					Map<String, Object> obj = checkList.get(i);
					BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
					BeanUtils.populate(checkDTO, obj);
					// checkDTO.setTrainId(trainId);
					boolean fg = false;
					handleCheckSectionProperty(checkDTO, lineMap, dictMap.get(DICTNAMES[0]), deptMileages, checkMap, fg,
							checkErrorStr, i + 2);
					if (fg == true) {
						flag = true;
					}
				}

				if (!flag) {
					List<Check1CDTO> check1CList = new ArrayList<>();
					for (Check1CDTO entity : checkMap.values()) {
						boolean fg = true;
						Date checkDate = entity.getCheckDate();
						String strDate = DateUtils.format(checkDate, DateUtils.DATE_FORMAT_YMD);
						String lineId = entity.getLineId();
						String direction = entity.getDirection();
						Check1CDTO check1cRepeat = dao.check1CRepeat(processDate(checkDate), lineId, direction,
								trainId);
						if (StringUtil.isNotEmpty(check1cRepeat)) {
							fg = false;
							LOGGER.error("检测作业发现重复");
							checkRepeat++;
							checkRepeatStr.append("此记录已经存在: " + strDate + "-" + check1cRepeat.getLineName() + "<br/>");
						}
						if(fg){
							entity.setCreateBy(createBy);
							entity.setCreateIp(createIp);
							entity.setPlanId(planId);
							entity.setTrainId(trainId);
							check1CList.add(entity);
						}
					}
					if (StringUtil.isNotEmpty(check1CList)) {// 保存
						checkCount = save(check1CList);
					}
				}
			}
			// LOGGER.error("缺陷是否存在 : " + haveDefect);
			if (haveDefect) {
				long defectStart = System.currentTimeMillis();
				for (int i = 0; i < defectList.size(); i++) {
					Map<String, Object> obj = defectList.get(i);
					if (StringUtil.isEmpty(obj)) {
						continue;
					}
					Defect1CDTO defect = new Defect1CDTO();
					BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
					BeanUtils.populate(defect, obj);
					defects.add(defect);
				}
				LOGGER.error("缺陷数量 : " + defects.size());
				List<StringBuilder> errorMsgList = new ArrayList<>();
				List<Boolean> errorFlagList = new ArrayList<>();
				setDefectProperty(defects, errorMsgList, errorFlagList, dictMap, tempMap);
				//处理缺陷分类等级
//				if(isDefectAssortment){	
//					defectAssortmentService.handleC1DefectData(defects);
//				}
				start = System.currentTimeMillis();
				// 保存
				int index = 0;
				List<Defect1CDTO> defectLists = new ArrayList<>();
				List<DefectHandleInfo> defectHandleInfoLists = new ArrayList<>();
				List<DefectRepeatHistory> defectRepeatHistoryLists = new ArrayList<>();
				long s0 = System.currentTimeMillis();
				for (int i = 0; i < defects.size(); i++) {
					index = i + 1;
					Defect1CDTO defect = defects.get(i);
					StringBuilder errorMsg = errorMsgList.get(i);
					Boolean errorFlag = errorFlagList.get(i);
					defectError.append(errorMsg);
					
//					if (isDefectAssortment && StringUtil.isBlank(defect.getDefectAssortmentId())) {
//						defectError.append("第" + index + "行无法关联到标准缺陷分类（请核对缺陷类型、缺陷值或缺陷描述）<br/>");
//						errorFlag = true;
//						errorFlagList.set(i, errorFlag);
//						continue;
//					}
					if (errorFlag) {
						continue;
					}
					defect.setSystemId(DefectSystem.defectC1.getStatus());
					if (importRepeat(defect)) {
						LOGGER.error("第" + index + "行缺陷发现重复");
						defectRepeat++;
						defectRepeatIndex.add(index);
						continue;
					}
					defect.setCreateBy(createBy);
					defect.setPlanId(planId);
					defect.setTrainId(trainId);
					// defect.setInfoId(infoId);
					//Defect findDefect = findDefect(defect);
					//处理缺陷
					defect.preInsert();
					defect.setDefectStatus(DefectStatus.DefectRegister.getStatus());
					defectLists.add(defect);
					
					DefectHandleInfo info = new DefectHandleInfo();
					info.setId(defect.getId());
					info.setIsConfirmed(1);
					defectHandleInfoLists.add(info);
					
					//DefectRepeatHistory defectRepeatHistory = repeatDataControl(defect, findDefect);
					//defectRepeatHistoryLists.add(defectRepeatHistory);
					//defect1CService.save(defect);
					//repeatControl(defect, findDefect);
					defectCount++;
				}
				long s1 = System.currentTimeMillis();
				LOGGER.error("缺陷重复查询 --- " + (s1-s0));
				if(StringUtil.isNotEmpty(defectLists)){
					
					int count = defectLists.size();
					int index1 = count%500>0?count/500+1:count/500;
					for (int i = 0; i < index1; i++) {
						int count1 = (i+1)*500>count?count:(i+1)*500;
						List<Defect1CDTO> s = defectLists.subList(i*500, count1);
						List<Defect> temp = new ArrayList<>();
						temp.addAll(s);
						//defectAssortmentService.handleC1DefectData(s);
						//defectDao.insertBatch(temp);
						List<DefectFormDTO> tempV = new ArrayList<DefectFormDTO>();
						for (Defect1CDTO defect1CDTO : s) {
							DefectFormDTO defectFormDTO = new DefectFormDTO();
							try {
								BeanUtils.copyProperties(defectFormDTO, defect1CDTO);
							} catch (Exception e) {
								e.printStackTrace();
							}
							tempV.add(defectFormDTO);
						}
						defectDao.insertBatchV2(tempV);
						defectC1Dao.insertBatch(s);
					}
					defect1CService.setDefectRepeatRequest(defectLists);
					
				}
				long s2 = System.currentTimeMillis();
				LOGGER.error("缺陷批量保存 --- " + (s2-s1));
				if(StringUtil.isNotEmpty(defectHandleInfoLists)){
					int count = defectHandleInfoLists.size();
					int index1 = count%500>0?count/500+1:count/500;
					for (int i = 0; i < index1; i++) {
						int count1 = (i+1)*500>count?count:(i+1)*500;
						defectHandleInfoDao.insertBatch(defectHandleInfoLists.subList(i*500, count1));
					}
					
				}
				long s3 = System.currentTimeMillis();
				LOGGER.error("缺陷handinfo批量保存 --- " + (s3-s2) );
				defectSysncQuestionService.sysncDefectImportData(defectLists);
//				if(StringUtil.isNotEmpty(defectRepeatHistoryLists)){
//					int count = defectRepeatHistoryLists.size();
//					int index1 = count%500>0?count/500+1:count/500;
//					for (int i = 0; i < index1; i++) {
//						int count1 = (i+1)*500>count?count:(i+1)*500;
//						defectRepeatHistoryDao.insertBatch(defectRepeatHistoryLists.subList(i*500, count1));
//					}
//				}
				long s4 = System.currentTimeMillis();
				LOGGER.error("缺陷重复历史批量保存 --- " + (s4-s3));
				
				end = System.currentTimeMillis();
				time = end - start;
				LOGGER.error("[缺陷导入]保存 --- " + time / 1000);
				time = end - defectStart;
				LOGGER.error("[缺陷导入]缺陷部分全部用时 --- " + time / 1000);
			}
		} catch (Exception e) {
			checkErrorStr.append(e.getCause());
			LOGGER.error("1C缺陷及每公里扣分数导入异常", e);
			e.printStackTrace();
		}
		map.put(DefectConstants.IMPORT_CHECK_COUNT, checkCount);
		map.put(DefectConstants.IMPORT_CHECK_REPEAT, checkRepeat);
		map.put(DefectConstants.IMPORT_CHECK_ERROR_MSG, checkErrorStr.toString());
		map.put(DefectConstants.IMPORT_CHECK_REPEAT_MSG, checkRepeatStr.toString());
		map.put(DefectConstants.IMPORT_DEFECT_COUNT, defectCount);
		map.put(DefectConstants.IMPORT_DEFECT_REPEAT, defectRepeat);
		map.put(DefectConstants.IMPORT_DEFECT_ERROR_MSG, defectError.toString());
		map.put(DefectConstants.IMPORT_DEFECT_REPEAT_MSG, CollectionUtils.convertToString(defectRepeatIndex, ","));
		return map;
	}

	private void setData(List<Map<String, Object>> result, Map<String, String> lineMap,
			Map<String, List<SectionMileage>> deptMileages) {
		Set<String> lineIds = new HashSet<String>();
		if (StringUtil.isNotEmpty(result)) {
			Set<String> lineSet = result.stream().map(m -> m.get(LINENAME).toString()).collect(Collectors.toSet());
			if (lineSet.size() > 0) {
				List<Map<String, String>> lineList = lineService
						.getIdsByNames(CollectionUtils.convertToString(lineSet, ","));
				if (StringUtil.isNotEmpty(lineList))
					lineList.forEach(map2 -> {
						lineMap.put(map2.get(NAME), map2.get(ID));
						lineIds.add(map2.get(ID));
					});
			}
		}
		if (StringUtil.isNotEmpty(lineIds)) {
			RestfulResponse<List<SectionMileage>> response = deptLineFeignService
					.getDeptMileage(CollectionUtils.convertToString(lineIds, ","), WORKAREATYPE, "1");
			if (StringUtil.isNotEmpty(response)) {
				List<SectionMileage> tempLists = response.getData();
				if (StringUtil.isNotEmpty(tempLists)) {
					tempLists.forEach(sectionMileage -> {
						if (!deptMileages.containsKey(sectionMileage.getLineId() + sectionMileage.getDirection())) {
							deptMileages.put(sectionMileage.getLineId() + sectionMileage.getDirection(),
									new ArrayList<SectionMileage>());
						}
						deptMileages.get(sectionMileage.getLineId() + sectionMileage.getDirection())
								.add(sectionMileage);
					});
				}
			}
		}
	}


	// 处理缺陷
	public void setDefectProperty(List<Defect1CDTO> defects, List<StringBuilder> errorMsgList,
			List<Boolean> errorFlagList, Map<String, Map<String, String>> dictMap,
			Map<String, List<DictionaryItemDTO>> tempMap) {
		long start, end = 0l;
		float time = 0;
		// 支柱查询
		List<Integer> pillarIndex = new ArrayList<>();
		List<Pillar> pillarList = new ArrayList<>();
		// 全部的线路名
		List<String> lineList = new ArrayList<>();
		// 线路信息
		Map<String, Map<String, String>> lineMap = new HashMap<>();
		// 缺陷信息
		Map<String, String> defectTypeMap = new HashMap<>();
		// 检测作业
		List<Check1CSectionDTO> checkQueryList = new ArrayList<>();
		Map<String, String> checkIdMap = new HashMap<>();

		start = System.currentTimeMillis();
		// 查询线路
		defects.forEach(defect -> {
			String lineName = defect.getLineName();
			if (StringUtil.isNotBlank(lineName) && !lineList.contains(lineName)) {
				lineList.add(lineName);
			}
		});
		if (StringUtil.isNotEmpty(lineList)) {
			List<Map<String, String>> lineIdsNames = lineService
					.getIdsByNames(CollectionUtils.convertToString(lineList, ","));
			if (StringUtil.isNotEmpty(lineIdsNames)) {
				lineIdsNames.forEach(line -> lineMap.put(line.get(NAME), line));
			}
		}
		// 全部的缺陷类型(1C)
		List<DefectTypeDTO> defectTypeList = defectTypeService.getListBySystemCode(DefectSystem.defectC1.getStatus());
		if (StringUtil.isNotEmpty(defectTypeList)) {
			defectTypeList.forEach(defectType -> defectTypeMap.put(defectType.getName(), defectType.getId()));
		}
		// 字典项抽取及转换
		Map<String, String> directionMap = dictMap.get("direction");
		List<DictionaryItemDTO> defectGradeList = tempMap.get("defect_grade");
		Map<String, String> defectGradeMap = new HashMap<>();
		if (StringUtil.isNotEmpty(defectGradeList)) {
			defectGradeList
					.forEach(dictionaryItem -> defectGradeMap.put(dictionaryItem.getName(), dictionaryItem.getCode()));
		}
		// 查询工区车间对应关系
		Map<String, String> deptMap = new HashMap<>();
		RestfulResponse<List<Map<String, String>>> deptByDeptTypeCode = deptFeignService.getDeptByDeptTypeCode("3");
		if (StringUtil.isNotEmpty(deptByDeptTypeCode)) {
			List<Map<String, String>> deptData = deptByDeptTypeCode.getData();
			if (StringUtil.isNotEmpty(deptData)) {
				deptData.forEach(dMap -> deptMap.put(dMap.get(ID), dMap.get(PARENTID)));
			}
		}
		end = System.currentTimeMillis();
		time = end - start;
		LOGGER.error("[缺陷导入]查询线路, 缺陷类型, 工区车间对应关系及字典项抽取及转换 --- " + time / 1000);

		start = System.currentTimeMillis();
		// 设置基础信息
		int index = 0;
		for (int i = 0; i < defects.size(); i++) {
			index = i + 1;
			Defect1CDTO defect = defects.get(i);
			boolean errorFlag = false;
			StringBuilder errorMsg = new StringBuilder();

			// 检测日期
			Date checkDate = defect.getCheckDate();
			if (StringUtil.isEmpty(checkDate)) {
				errorMsg.append("第" + index + "行没有填写检测日期<br/>");
				errorFlag = true;
			}
			// 缺陷公里标
			Double defectGlb = defect.getDefectGlb();
			if (StringUtil.isEmpty(defectGlb)) {
				errorMsg.append("第" + index + "行没有填写缺陷公里标<br/>");
				errorFlag = true;
			}
			// 线路
			String lineName = defect.getLineName();
			String lineId = null;
			if (StringUtil.isNotBlank(lineName)) {
				if (lineMap.containsKey(lineName)) {
					Map<String, String> line = lineMap.get(lineName);
					lineId = line.get(ID);
					defect.setLineId(lineId);
					defect.setSpeedType(line.get(SPEEDTYPE));
				} else {
					errorMsg.append("第" + index + "行填写线路不存在<br/>");
					errorFlag = true;
				}
			} else {
				errorMsg.append("第" + index + "行没有填写线路<br/>");
				errorFlag = true;
			}
			// 行别
			String directionName = defect.getDirectionName();
			String direction = null;
			if (StringUtil.isNotBlank(directionName)) {// 行别
				direction = StringUtil.isNotEmpty(directionMap) ? directionMap.get(directionName) : null;
				if (StringUtil.isNotBlank(direction)) {
					defect.setDirection(direction);
				} else {
					errorMsg.append("第" + index + "行填写行别不存在<br/>");
					errorFlag = true;
				}
			} else {
				errorMsg.append("第" + index + "行没有填写行别<br/>");
				errorFlag = true;
			}
			// 缺陷类型
			String defectTypeName = defect.getDefectTypeName();
			if (StringUtil.isNotBlank(defectTypeName)) {// 缺陷类型
				if (defectTypeMap.containsKey(defectTypeName)) {
					defect.setDefectType(defectTypeMap.get(defectTypeName));
				} else {
					errorMsg.append("第" + index + "行填写缺陷类型不存在<br/>");
					errorFlag = true;
				}
			} else {
				errorMsg.append("第" + index + "行没有填写缺陷类型<br/>");
				errorFlag = true;
			}
			// 缺陷等级
			String defectLevelName = defect.getDefectLevelName();
			if (StringUtil.isNotBlank(defectLevelName)) {// 缺陷等级
				if (defectGradeMap.containsKey(defectLevelName)) {
					defect.setDefectLevel(defectGradeMap.get(defectLevelName));
				} else {
					errorMsg.append("第" + index + "行填写缺陷等级不存在<br/>");
					errorFlag = true;
				}
			} else {
				//errorMsg.append("第" + index + "行没有填写缺陷等级<br/>");
				//errorFlag = true;
			}
			boolean pillarFlag = StringUtil.isNotBlank(lineId) && StringUtil.isNotBlank(direction)
					&& StringUtil.isNotEmpty(defectGlb);
			if (pillarFlag) {
				Pillar query = new Pillar();
				query.setPlId(lineId);
				query.setDirection(direction);
				query.setStartKm(defectGlb);
				pillarList.add(query);
				pillarIndex.add(i);
			} else {
				errorMsg.append("第" + index + "行存在有误信息无法关联支柱<br/>");
				errorFlag = true;
			}
			errorMsgList.add(errorMsg);
			errorFlagList.add(errorFlag);
		}
		end = System.currentTimeMillis();
		time = end - start;
		LOGGER.error("[缺陷导入]设置基础信息 --- " + time / 1000);

		start = System.currentTimeMillis();
		// 支柱查询
		Map<Integer, Pillar> pillarMap = new HashMap<Integer, Pillar>();
		if (StringUtil.isNotEmpty(pillarList)) {
			LOGGER.error("[缺陷导入]支出查询数量 --- " + pillarList.size());
			long s1 = System.currentTimeMillis();
			pillarMap = defect1CService.handlePillar(pillarIndex, pillarList, pillarMap);
			long s2 = System.currentTimeMillis();
			LOGGER.error("[缺陷导入]支柱范围查询 --- " + ((s2-s1) / 1000));
//			for (int i = 0; i < pillarList.size(); i++) {
//				Pillar pillarData = pillarDao.findPillarByGlb(pillarList.get(i));
//				pillarMap.put(pillarIndex.get(i), pillarData);
//			}
		}
		end = System.currentTimeMillis();
		time = end - start;
		LOGGER.error("[缺陷导入]支柱查询 --- " + time / 1000);

		start = System.currentTimeMillis();
		// 设置支柱-车间-检测作业查询条件
		for (int i = 0; i < defects.size(); i++) {
			index = i + 1;
			Defect1CDTO defect = defects.get(i);
			StringBuilder errorMsg = errorMsgList.get(i);
			Boolean errorFlag = errorFlagList.get(i);

			String workAreaId = null;
			String sectionId = null;
			String psaName = null;
			String pillarName = null;
			if (pillarMap.containsKey(i)) { // 支柱
				Pillar pillar = pillarMap.get(i);
				if (StringUtil.isNotEmpty(pillar)) {
					workAreaId = pillar.getDeptId();
					sectionId = pillar.getSectionId();
					psaName = pillar.getPsaName();
					pillarName = pillar.getName();
					defect.setPillarId(pillar.getId());
					defect.setPsaId(pillar.getPsaId());
					defect.setWorkAreaId(workAreaId);
					defect.setSectionId(sectionId);
					defect.setPillarName(pillarName);
				} else {
					errorMsg.append("第" + index + "行通过填写信息无法找到支柱<br/>");
					errorFlag = true;
				}
			}
			if (StringUtil.isNotBlank(workAreaId) && deptMap.containsKey(workAreaId)) { // 车间
				defect.setWorkShopId(deptMap.get(workAreaId));
			} else {
				errorMsg.append("第" + index + "行通过填写信息无法找到对应车间信息<br/>");
				errorFlag = true;
			}
			/*
			 * defect.setDefectName(defect.getLineName() + " " + psaName + " " +
			 * defect.getDirectionName() + " " +
			 * DateUtils.format(defect.getCheckDate(), "yyyy-MM-dd") + " (" +
			 * pillarName + "支柱) " + getKGlb(defect.getDefectGlb()));
			 */

			String lineId = defect.getLineId();
			String direction = defect.getDirection();
			String deptId = defect.getWorkAreaId();
			Date checkDate = defect.getCheckDate();
			boolean checkQuery = !errorFlag && StringUtil.isNotBlank(lineId) && StringUtil.isNotBlank(direction)
					&& StringUtil.isNotBlank(sectionId) && StringUtil.isNotEmpty(checkDate);
			if (checkQuery) {
				defect.setDefectName(defect.getLineName() + " " + psaName + " " + defect.getDirectionName() + " "
						+ DateUtils.format(checkDate, "yyyy-MM-dd") + " (" + pillarName + "支柱) "
						+ getKGlb(defect.getDefectGlb()));
				Check1CSectionDTO checkSection = new Check1CSectionDTO();
				checkSection.setLineId(lineId);
				checkSection.setDeptId(deptId);
				checkSection.setCheckDate(processDate(checkDate));
				checkSection.setDirection(direction);
				checkSection.setSectionId(sectionId);
				checkQueryList.add(checkSection);
			} else {
				errorFlag = true;
			}

			errorFlagList.set(i, errorFlag);
		}
		end = System.currentTimeMillis();
		time = end - start;
		LOGGER.error("[缺陷导入]设置支柱, 车间 --- " + time / 1000);

		start = System.currentTimeMillis();
		if (StringUtil.isNotEmpty(checkQueryList)) {// 批量查询检测作业
			List<Map<String, String>> findCheckSectionIds = checkSectionDao.findCheckSectionIds(checkQueryList);
			if (StringUtil.isNotEmpty(findCheckSectionIds)) {
				findCheckSectionIds.forEach(cMap -> {
					checkIdMap.put(cMap.get(CHECKDATE) + cMap.get(LINEID) + cMap.get(DIRECTION) + cMap.get(SECTIONID)
							+ cMap.get(DEPTID), cMap.get(ID));
				});
			}
		}
		end = System.currentTimeMillis();
		time = end - start;
		LOGGER.error("[缺陷导入]批量查询检测作业 --- " + time / 1000);
		start = System.currentTimeMillis();
		for (int i = 0; i < defects.size(); i++) {
			index = i + 1;
			Defect1CDTO defect = defects.get(i);
			StringBuilder errorMsg = errorMsgList.get(i);
			Boolean errorFlag = errorFlagList.get(i);
			String key = DateUtils.format(defect.getCheckDate(), DateUtils.DATE_FORMAT_YMD) + defect.getLineId()
					+ defect.getDirection() + defect.getSectionId() + defect.getWorkAreaId();
			if (checkIdMap.containsKey(key)) {
				String checkSectionId = checkIdMap.get(key);
				if (StringUtil.isNotBlank(checkSectionId)) {
					defect.setInfoId(checkSectionId);
				} else {
					errorMsg.append("第" + index + "行找不到匹配的检测作业<br/>");
					errorFlag = true;
				}
			} else {
				errorMsg.append("第" + index + "行找不到匹配的检测作业<br/>");
				errorFlag = true;
			}
			errorFlagList.set(i, errorFlag);
		}
		end = System.currentTimeMillis();
		time = end - start;
		LOGGER.error("[缺陷导入]设置检测作业 --- " + time / 1000);

	}

	@Transactional
	@Override
	public int deleteFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			Date date = new Date();
			List<String> list = Arrays.asList(ids.split(","));
			List<String> defectIds = defectDao.getIdsByCheck1CIds(list);
			defectService.deleteFalse(defectIds, deleteBy, deleteIp);
			int i = dao.deleteFalses(list, deleteBy, deleteIp, date);
			checkSectionDao.deleteFalsesByCheckIds(list, deleteBy, deleteIp, date);
			return i;
		}
		return 0;
	}

	@Override
	public int update(Check1C entity) {
		entity.setUpdateDate(new Date());
		dao.update(entity);
		return 1;
	}

	@DictAggregation
	@Override
	public Page<Check1CDTO> findCheckListPages(Page page, CheckQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findCheckList(entity));
	}

	@DictAggregation
	@Override
	public Check1CDTO get(String id) {
		return dao.getByKey(id);
	}

	public boolean importRepeat(Defect defect) {

		defect.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		CQueryFilter cQueryFilter = new CQueryFilter();
		try {
			BeanUtils.copyProperties(cQueryFilter, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		cQueryFilter.setCheckDate(processDate(cQueryFilter.getCheckDate()));
		if (StringUtil.isNotEmpty(defectDao.importRepeat(cQueryFilter)))
			return true;
		return false;
	}

	public void repeatControl(Defect defect, Defect findDefect) {
		DefectRepeatHistory defectRepeatHistory = new DefectRepeatHistory();
		try {
			BeanUtils.copyProperties(defectRepeatHistory, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}

		if (StringUtil.isEmpty(findDefect)) {// 无重复
			defectRepeatHistory.setDefectId(defect.getId());
		} else {
			defectRepeatHistory.setDefectId(findDefect.getId());
		}
		defectRepeatHistoryDao.insert(defectRepeatHistory);
	}
	public DefectRepeatHistory repeatDataControl(Defect defect, Defect findDefect) {
		DefectRepeatHistory defectRepeatHistory = new DefectRepeatHistory();
		try {
			BeanUtils.copyProperties(defectRepeatHistory, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		
		if (StringUtil.isEmpty(findDefect)) {// 无重复
			defectRepeatHistory.setDefectId(defect.getId());
		} else {
			defectRepeatHistory.setDefectId(findDefect.getId());
		}
		return defectRepeatHistory;
		
	}

	public String getKGlb(Double glb) {
		if (StringUtil.isEmpty(glb))
			return "";
		String str = null;
		String[] split = Double.toString(glb).split("\\.");
		if (!split[0].equals("0")) {// 小于1不需要K
			if (split[1].equals("0")) {// 小数点后为0不需要+
				str = "K" + split[0];
			} else {
				str = "K" + split[0] + "+" + split[1];
			}
		} else {
			str = split[1];
		}
		return str;
	}

	public Defect findDefect(Defect defect) {

		CQueryFilter cQueryFilter = new CQueryFilter();
		try {
			BeanUtils.copyProperties(cQueryFilter, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return defectDao.findDefect(cQueryFilter);
	}

	public Date processDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	@Override
	@DictAggregation
	public List<Check1CDTO> getByKeys(List<String> ids) {
		return dao.getByKeys(ids);
	}

	@Override
	@DictAggregation
	public Page<Check1CDTO> check1CNoticePages(Page page, CheckQueryFilter entity) {
		Page<Check1CDTO> pg = new Page<Check1CDTO>();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		SimplePage sp = new SimplePage();
		sp.setPageNo(pageNo);
		sp.setPageSize(pageSize);
		sp.setStartNum((pageNo - 1) * pageSize);
		sp.setEndNum(pageNo * pageSize);
		entity.setPage(sp);
		int total = dao.findCheckNoticeListCounts(entity);
		List<Check1CDTO> list = dao.findCheckNoticeList(entity);
		pg.setList(list);
		pg.setTotal((long) total);
		pg.setPageNo(sp.getPageNo());
		pg.setPageSize(sp.getPageSize());
		return pg;
	}

	@Override
	public Map<String, Object> analyzeSectionData(String originalFilename, InputStream inputStream, String createBy,
			String createIp, String sectionId, String planId, String trainId, String infoId) {// 段级
		long start, end = 0l;
		float time = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = false;
		Boolean erroeFlag = false;// 是否存在错误数据
		int checkCount = 0;
		int checkRepeat = 0;
		int defectCount = 0;
		int defectRepeat = 0;
		List<Integer> defectRepeatIndex = new ArrayList<>();
		StringBuilder defectError = new StringBuilder();
		StringBuilder checkRepeatStr = new StringBuilder();
		StringBuilder checkErrorStr = new StringBuilder();
		List<Defect1CDTO> defects = new ArrayList<>();
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService.findDictItemsByDictName(DICTNAMES).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		List<List<Map<String, Object>>> list = null;
		List<Map<String, Object>> checkList = null;
		List<Map<String, Object>> defectList = null;
		Map<String, Check1CDTO> checkMap = new HashMap<>();
		try {
			LOGGER.error("开始查询模板");
			List<ImportExcelConfigDTO> checkConfig = importConfigClient.getImportExcelConfigResponseByData(CHECK1C)
					.getData();
			List<ImportExcelConfigDTO> defectConfig = importConfigClient
					.getImportExcelConfigResponseByData(C1CHECKDEFECT).getData();
			List<String> importExcelConfigIds = new ArrayList<>();
			importExcelConfigIds.add(defectConfig.get(0).getId());
			importExcelConfigIds.add(checkConfig.get(0).getId());
			list = importService.analyzeExcelData(originalFilename, inputStream, importExcelConfigIds);
			if (StringUtil.isNotEmpty(list)) {
				LOGGER.error("解析模板数量 --- " + list.size());
			} else {
				checkErrorStr.append("解析数据为空");
			}
			if (list.size() == 2) {
				defectList = list.get(0);
				checkList = list.get(1);
				if (StringUtil.isEmpty(defectList) && StringUtil.isEmpty(checkList)) {
					checkErrorStr.append("解析数据为空");
					erroeFlag = true;
				}
				Map<String, List<SectionMileage>> deptMileages = new HashMap<String, List<SectionMileage>>();
				Map<String, String> lineMap = new HashMap<String, String>();
				// 处理线路、管辖范围
				setData(checkList, lineMap, deptMileages);
				for (int i = 0; i < checkList.size(); i++) {
					if (checkList.get(i) == null || checkList.get(i).isEmpty()) {
						continue;
					}
					Check1CDTO checkDTO = new Check1CDTO();
					Map<String, Object> obj = checkList.get(i);
					BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
					BeanUtils.populate(checkDTO, obj);
					// checkDTO.setTrainId(trainId);
					boolean fg = false;
					handleCheckSectionProperty(checkDTO, lineMap, dictMap.get(DICTNAMES[0]), deptMileages, checkMap, fg,
							checkErrorStr, i + 2);

					if (erroeFlag == true) {
						flag = true;
					}
				}
				if (!flag) {
					List<Check1CDTO> check1CList = new ArrayList<>();
					for (Check1CDTO entity : checkMap.values()) {
						Date checkDate = entity.getCheckDate();
						String strDate = DateUtils.format(checkDate, DateUtils.DATE_FORMAT_YMD);
						String lineId = entity.getLineId();
						String direction = entity.getDirection();
						Check1CDTO check1cRepeat = dao.check1CRepeat(processDate(checkDate), lineId, direction,
								trainId);
						if (StringUtil.isNotEmpty(check1cRepeat)) {
							erroeFlag = true;
							checkRepeat++;
							checkRepeatStr.append("此记录已经存在: " + strDate + "-" + check1cRepeat.getLineName() + "<br/>");
						}
						entity.setCreateBy(createBy);
						entity.setCreateIp(createIp);
						entity.setIsShow(0);

						Check1CSectionDTO check1cSection = new Check1CSectionDTO();
						check1cSection.setSectionId(sectionId);
						check1cSection.setCount(entity.getCount());
						check1cSection.setAverageSpeed(entity.getAverageSpeed());
						check1cSection.setGoodMileage(entity.getGoodMileage());
						check1cSection.setQualifiedMileage(entity.getQualifiedMileage());
						check1cSection.setUnqualifiedMileage(entity.getUnqualifiedMileage());
						check1cSection.setPoints(entity.getPoints());
						entity.getCheckSectionMap().put(sectionId, check1cSection);
						entity.setTrainId(trainId);
						check1CList.add(entity);
					}
					if (!erroeFlag) {// 保存
						checkCount = save(check1CList);
					}
				}
			} else {
				defectList = list.get(0);
				if (StringUtil.isEmpty(defectList)) {
					checkErrorStr.append("解析数据为空");
					erroeFlag = true;
				}
			}
			// 处理缺陷
			if (list.size() == 1 || (!erroeFlag && (list.size() == 2))) {
				long defectStart = System.currentTimeMillis();
				for (int i = 0; i < defectList.size(); i++) {
					Map<String, Object> obj = defectList.get(i);
					if (StringUtil.isEmpty(obj)) {
						continue;
					}
					Defect1CDTO defect = new Defect1CDTO();
					BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
					BeanUtils.populate(defect, obj);
					defects.add(defect);
				}
				LOGGER.error("缺陷数量 : " + defects.size());
				List<StringBuilder> errorMsgList = new ArrayList<>();
				List<Boolean> errorFlagList = new ArrayList<>();
				setDefectProperty(defects, errorMsgList, errorFlagList, dictMap, tempMap);
				//处理缺陷分类等级
//				if(isDefectAssortment){	
//					defectAssortmentService.handleC1DefectData(defects);
//				}
				start = System.currentTimeMillis();
				// 保存
				int index = 0;
				List<Defect1CDTO> defectLists = new ArrayList<>();
				List<DefectHandleInfo> defectHandleInfoLists = new ArrayList<>();
				//List<DefectRepeatHistory> defectRepeatHistoryLists = new ArrayList<>();
				for (int i = 0; i < defects.size(); i++) {
					index = i + 1;
					Defect1CDTO defect = defects.get(i);
					StringBuilder errorMsg = errorMsgList.get(i);
					Boolean errorFlag = errorFlagList.get(i);
					defectError.append(errorMsg);
					
//					if (isDefectAssortment && StringUtil.isBlank(defect.getDefectAssortmentId())) {
//						defectError.append("第" + index + "行无法关联到标准缺陷分类（请核对缺陷类型、缺陷值或缺陷描述）<br/>");
//						errorFlag = true;
//						errorFlagList.set(i, errorFlag);
//						continue;
//					}
					if (errorFlag) {
						continue;
					}
					defect.setSystemId(DefectSystem.defectC1.getStatus());
					if (importRepeat(defect)) {
						LOGGER.error("第" + index + "行缺陷发现重复");
						defectRepeat++;
						defectRepeatIndex.add(index);
						continue;
					}
					defect.setCreateBy(createBy);
					defect.setPlanId(planId);
					defect.setTrainId(trainId);
					// defect.setInfoId(infoId);
					//Defect findDefect = findDefect(defect);
					//处理缺陷
					defect.preInsert();
					defect.setDefectStatus(DefectStatus.DefectRegister.getStatus());
					defectLists.add(defect);
					
					DefectHandleInfo info = new DefectHandleInfo();
					info.setId(defect.getId());
					info.setIsConfirmed(1);
					defectHandleInfoLists.add(info);
					
					//DefectRepeatHistory defectRepeatHistory = repeatDataControl(defect, findDefect);
					//defectRepeatHistoryLists.add(defectRepeatHistory);
					//defect1CService.save(defect);
					//repeatControl(defect, findDefect);
					defectCount++;
				}
				if(StringUtil.isNotEmpty(defectLists)){
					List<Defect> temp = new ArrayList<>();
					temp.addAll(defectLists);
					//defectAssortmentService.handleC1DefectData(defectLists);
					//defectDao.insertBatch(temp);
					List<DefectFormDTO> tempV = new ArrayList<DefectFormDTO>();
					for (Defect1CDTO defect1CDTO : defectLists) {
						DefectFormDTO defectFormDTO = new DefectFormDTO();
						try {
							BeanUtils.copyProperties(defectFormDTO, defect1CDTO);
						} catch (Exception e) {
							e.printStackTrace();
						}
						tempV.add(defectFormDTO);
					}
					defectDao.insertBatchV2(tempV);
					
					defectC1Dao.insertBatch(defectLists);
					defect1CService.setDefectRepeatRequest(defectLists);
				}
				if(StringUtil.isNotEmpty(defectHandleInfoLists)){
					defectHandleInfoDao.insertBatch(defectHandleInfoLists);
				}
//				if(StringUtil.isNotEmpty(defectRepeatHistoryLists)){
//					defectRepeatHistoryDao.insertBatch(defectRepeatHistoryLists);
//				}
				end = System.currentTimeMillis();
				time = end - start;
				LOGGER.error("[缺陷导入]保存 --- " + time / 1000);
				time = end - defectStart;
				LOGGER.error("[缺陷导入]缺陷部分全部用时 --- " + time / 1000);
			}
		} catch (Exception e) {
			checkErrorStr.append(e.getMessage());
			LOGGER.error("Check1CSection Exception", e);
			e.printStackTrace();
		}
		map.put(DefectConstants.IMPORT_CHECK_COUNT, checkCount);
		map.put(DefectConstants.IMPORT_CHECK_REPEAT, checkRepeat);
		map.put(DefectConstants.IMPORT_CHECK_ERROR_MSG, checkErrorStr.toString());
		map.put(DefectConstants.IMPORT_CHECK_REPEAT_MSG, checkRepeatStr.toString());
		map.put(DefectConstants.IMPORT_DEFECT_COUNT, defectCount);
		map.put(DefectConstants.IMPORT_DEFECT_REPEAT, defectRepeat);
		map.put(DefectConstants.IMPORT_DEFECT_ERROR_MSG, defectError.toString());
		map.put(DefectConstants.IMPORT_DEFECT_REPEAT_MSG, CollectionUtils.convertToString(defectRepeatIndex, ","));
		return map;
	}

	// 修正质量评价报表段数据

	public Map<String, Object> handleCheckSectionData(String originalFilename, InputStream inputStream,
			String templateId, String trainId, String sectionId, String createBy) {
		Map<String, Object> map = new HashMap<>();
		StringBuilder errorMsg = new StringBuilder();
		Map<String, String> dictMap = new HashMap<String, String>();
		Map<String, Check1CDTO> checkMap = new HashMap<>();
		List<DictionaryItemDTO> dicts = dictionaryService.getDictItemsListByDictName(DICTNAMES[0]).getData();
		if (StringUtil.isNotEmpty(dicts)) {
			for (DictionaryItemDTO dictionaryItemDTO : dicts) {
				dictMap.put(dictionaryItemDTO.getName(), dictionaryItemDTO.getId());
			}
		}
		int count = 0;
		try {
			List<Map<String, Object>> list = importService.analyzeExcelData(originalFilename, inputStream, templateId);
			if (StringUtil.isNotEmpty(list)) {
				Map<String, List<SectionMileage>> deptMileages = new HashMap<String, List<SectionMileage>>();
				Map<String, String> lineMap = new HashMap<String, String>();
				// 处理线路、管辖范围
				setData(list, lineMap, deptMileages);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> obj = list.get(i);
					if (StringUtil.isEmpty(obj)) {
						continue;
					}
					boolean fg = false;
					Check1CDTO check1CDTO = new Check1CDTO();
					BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
					BeanUtils.populate(check1CDTO, obj);
					check1CDTO.setTrainId(trainId);
					check1CDTO.setCreateBy(createBy);
					handleCheckSectionProperty(check1CDTO, lineMap, dictMap, deptMileages, checkMap, fg, errorMsg,
							(i + 2));
				}
				if (StringUtil.isNotEmpty(checkMap)) {
					checkMap.forEach((key, value) -> handleCheckSectionData(value));
					count = checkMap.size();
				}

			} else {
				errorMsg.append("解析数据为空");
			}
		} catch (Exception e) {
			errorMsg.append(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put(DefectConstants.IMPORT_CHECK_COUNT, count);
		map.put(DefectConstants.IMPORT_CHECK_ERROR_MSG, errorMsg);
		return map;
	}

	private void handleCheckSectionData(Check1CDTO entity) {
		Check1CDTO dto = dao.check1CRepeat(entity.getCheckDate(), entity.getLineId(), entity.getDirection(),
				entity.getTrainId());
		if (StringUtil.isNotEmpty(dto)) {
			List<String> checkSectionIds = checkSectionDao.getByCheckIds(Arrays.asList(dto.getId()));
			List<String> defectIds = defectDao.getIdsByCheck1CIds(Arrays.asList(dto.getId()));
			Map<String, List<String>> defectMap = new HashMap<String, List<String>>();
			if (StringUtil.isNotEmpty(defectIds)) {
				List<Defect> defects = defectDao.getByKeys(defectIds);
				defects.forEach(defect -> {
					if (!defectMap.containsKey(defect.getWorkAreaId())) {
						defectMap.put(defect.getWorkAreaId(), new ArrayList<String>());
					}
					defectMap.get(defect.getWorkAreaId()).add(defect.getId());
				});
			}
			if (StringUtil.isNotEmpty(checkSectionIds)) {
				Map<String, Check1CSectionDTO> checkSectionMap = entity.getCheckSectionMap();
				for (Check1CSectionDTO check1CSectionDTO : checkSectionMap.values()) {
					Integer count2 = check1CSectionDTO.getCount();
					BigDecimal big2 = new BigDecimal(check1CSectionDTO.getAverageSpeed() / count2);
					check1CSectionDTO.preInsert();
					if (StringUtil.isBlank(check1CSectionDTO.getWorkShopId())
							&& StringUtil.isNotBlank(check1CSectionDTO.getDeptId())) {
						if (deptMap.containsKey(check1CSectionDTO.getDeptId())) {
							check1CSectionDTO.setWorkShopId(deptMap.get(check1CSectionDTO.getDeptId()));
						} else {
							String workShopId = deptFeignService.getParentDept(check1CSectionDTO.getDeptId()).getData()
									.getId();
							deptMap.put(check1CSectionDTO.getDeptId(), workShopId);
							check1CSectionDTO.setWorkShopId(workShopId);
						}
					}
					List<String> defectId = defectMap.get(check1CSectionDTO.getDeptId());
					if (StringUtil.isNotEmpty(defectId)) {
						defectDao.updateInfoId(defectId, check1CSectionDTO.getId());
						defectMap.remove(check1CSectionDTO.getDeptId());
					}
					check1CSectionDTO.setCheckId(dto.getId());
					check1CSectionDTO.setCreateBy(entity.getCreateBy());
					check1CSectionDTO.setAverageSpeed(big2.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
					check1CSectionDTO.setDetectMileage(
							check1CSectionDTO.getQualifiedMileage() + check1CSectionDTO.getUnqualifiedMileage());
					check1CSectionDTO.setUnqualifiedDetail(
							check1CSectionDTO.getUnqualifiedList().stream().collect(Collectors.joining(", ")));
					checkSectionDao.insert(check1CSectionDTO);
				}
				checkSectionDao.deleteFalses(checkSectionIds, entity.getCreateBy(), null, new Date());
			}

			LOGGER.error("缺陷数据未关联上段检测作业：", defectMap);
		}
	}

	private void handleCheckSectionProperty(Check1CDTO check1CDTO, Map<String, String> lineMap,
			Map<String, String> dictMap, Map<String, List<SectionMileage>> deptMileages,
			Map<String, Check1CDTO> checkMap, boolean fg, StringBuilder errorMsg, int row) {
		String lineId = null;
		String lineName = check1CDTO.getLineName();
		String direction = null;
		String directionName = check1CDTO.getDirectionName();
		// 处理线路
		if (StringUtil.isNotBlank(lineName)) {
			lineId = lineMap.get(lineName);
			if (StringUtil.isNotBlank(lineId)) {
				check1CDTO.setLineId(lineId);
			} else {
				fg = true;
				errorMsg.append("每公里扣分数统计的第" + row + "行，线路:" + lineName + "未找到<br/>");
			}
		} else {
			fg = true;
			errorMsg.append("每公里扣分数统计的第" + row + "行，线路为空<br/>");
		}

		// 处理行别
		if (StringUtil.isNotBlank(directionName)) {
			direction = dictMap.get(directionName);
			if (StringUtil.isNotBlank(direction)) {
				check1CDTO.setDirection(direction);
			} else {
				fg = true;
				errorMsg.append("每公里扣分数统计的第" + row + "行，行别:" + directionName + "未找到<br/>");
			}
		} else {
			fg = true;
			errorMsg.append("每公里扣分数统计的第" + row + "行，行别为空<br/>");
		}

		setCheckSectionData(check1CDTO, deptMileages, checkMap, fg, errorMsg, row);
	}

	private void setCheckSectionData(Check1CDTO check1CDTO, Map<String, List<SectionMileage>> deptMileages,
			Map<String, Check1CDTO> checkMap, boolean fg, StringBuilder errorMsg, int row) {
		boolean checkIsNew = false;
		Check1CDTO check = new Check1CDTO();
		check.setCreateBy(check1CDTO.getCreateBy());
		check.setTrainId(check1CDTO.getTrainId());
		List<SectionMileage> data = null;
		String mileage = check1CDTO.getMileage();
		Double speed = check1CDTO.getSpeed();
		Double points = check1CDTO.getPoints();
		Double startKm = null;
		Double endKm = null;
		Double Kilometre = null;
		String lineId = check1CDTO.getLineId();
		String direction = check1CDTO.getDirection();
		Date checkDate = check1CDTO.getCheckDate();
		String checkDateStr = null;
		if (StringUtil.isNotEmpty(checkDate)) {
			checkDateStr = DateUtils.format(checkDate, DateUtils.DATE_FORMAT_YMD);
		} else {
			fg = true;
			errorMsg.append("每公里扣分数统计的第" + row + "行，检测日期为空<br/>");
		}

		// 处理里程
		if (StringUtil.isNotBlank(mileage)) {
			String[] split = mileage.split("-");
			if (split != null && split.length == 2) {
				Double splitNum1 = Double.valueOf(split[0].trim());
				Double splitNum2 = Double.valueOf(split[1].trim());
				if (splitNum1 > splitNum2) {
					startKm = splitNum2;
					endKm = splitNum1;
				} else {
					startKm = splitNum1;
					endKm = splitNum2;
				}
				Kilometre = endKm - startKm;
			} else {
				fg = true;
				errorMsg.append("每公里扣分数统计的第" + row + "行，里程：" + mileage + "错误<br/>");
			}
		} else {
			fg = true;
			errorMsg.append("每公里扣分数统计的第" + row + "行，里程为空<br/>");
		}

		if (!fg) {

			if (checkMap.containsKey(checkDateStr + lineId + direction)) {// 存在
				check = checkMap.get(checkDateStr + lineId + direction);
				check.setCount(check.getCount() + 1);
			} else {// 不存在
				checkIsNew = true;
				check.setLineId(lineId);
				check.setDirection(direction);
				check.setCount(1);
				checkMap.put(checkDateStr + lineId + direction, check);
			}
			if (checkIsNew) {
				check.setCheckDate(checkDate);
			}
			if (deptMileages.containsKey(lineId + direction)) {
				data = deptMileages.get(lineId + direction);
			}
			if (StringUtil.isNotEmpty(data)) {
				String deptId1 = null;
				String deptId2 = null;
				boolean flag = true;
				for (int i = 0; i < data.size(); i++) {
					SectionMileage sectionMileage = data.get(i);
					Double deptStart = sectionMileage.getStartKm();
					Double deptEnd = sectionMileage.getEndKm();
					if (startKm > deptEnd || endKm < deptStart) { // 无交集
						continue;
					}
					flag = false;
					if (startKm >= deptStart && endKm <= deptEnd) {// 完全包含
						deptId1 = sectionMileage.getDeptId();
						Check1CSectionDTO checkSection = new Check1CSectionDTO();
						if (check.getCheckSectionMap().containsKey(deptId1)) {
							checkSection = check.getCheckSectionMap().get(deptId1);
							checkSection.setCount(checkSection.getCount() + 1);
						} else {
							checkSection.setCount(1);
							checkSection.setDeptId(deptId1);
							checkSection.setSectionId(sectionMileage.getSectionId());
							check.getCheckSectionMap().put(deptId1, checkSection);
						}
						if (StringUtil.isNotEmpty(speed)) {// 为空在后面追加提示
															// 避免重复提示
							checkSection.setAverageSpeed(checkSection.getAverageSpeed() + speed);
						}
						if (StringUtil.isNotEmpty(points)) {
							if (points >= 40) {// 不合格
								checkSection.getUnqualifiedList().add(mileage);
								checkSection.setPoints(checkSection.getPoints() + points);
								checkSection.setUnqualifiedMileage(checkSection.getUnqualifiedMileage() + Kilometre);
							} else {// 合格
								checkSection.setQualifiedPoints(checkSection.getQualifiedPoints() + points);
								checkSection.setQualifiedMileage(checkSection.getQualifiedMileage() + Kilometre);
								if (points < 10) {// 合格且优良
									checkSection.setGoodPoints(checkSection.getGoodPoints() + points);
									checkSection.setGoodMileage(checkSection.getGoodMileage() + Kilometre);
								}
							}
						}
					} else if ((startKm > deptStart && startKm < deptEnd) && endKm > deptEnd) {
						// 跨段的1Km分配各自的公里数
						if (data.size() == (i + 1)) {
							errorMsg.append("每公里扣分数统计的第" + row + "行检测里程跨段, 但是获取段信息有误");
							fg = true;
							break;
						}
						Double num1 = deptEnd - startKm;
						Double num2 = endKm - deptEnd;
						deptId1 = sectionMileage.getDeptId();
						deptId2 = data.get(i + 1).getDeptId();
						Check1CSectionDTO checkSection1 = new Check1CSectionDTO();
						Check1CSectionDTO checkSection2 = new Check1CSectionDTO();
						if (check.getCheckSectionMap().containsKey(deptId1)) {
							checkSection1 = check.getCheckSectionMap().get(deptId1);
							checkSection1.setCount(checkSection1.getCount() + 1);
						} else {
							checkSection1.setCount(1);
							checkSection1.setDeptId(deptId1);
							checkSection1.setSectionId(sectionMileage.getSectionId());
							check.getCheckSectionMap().put(deptId1, checkSection1);
						}
						if (check.getCheckSectionMap().containsKey(deptId2)) {
							checkSection2 = check.getCheckSectionMap().get(deptId2);
							checkSection2.setCount(checkSection2.getCount() + 1);
						} else {
							checkSection2.setCount(1);
							checkSection2.setSectionId(data.get(i + 1).getSectionId());
							checkSection2.setDeptId(deptId2);
							check.getCheckSectionMap().put(deptId2, checkSection2);
						}
						if (StringUtil.isNotEmpty(speed)) {
							checkSection1.setAverageSpeed(checkSection1.getAverageSpeed() + speed);
							checkSection2.setAverageSpeed(checkSection2.getAverageSpeed() + speed);
						}
						if (StringUtil.isNotEmpty(points)) {// 跨段扣分数各分一半
							Double halfPoints = points / 2;
							if (points >= 40) {// 不合格
								checkSection1.getUnqualifiedList().add(mileage);
								checkSection2.getUnqualifiedList().add(mileage);
								checkSection1.setPoints(checkSection1.getPoints() + halfPoints);
								checkSection1.setUnqualifiedMileage(checkSection1.getUnqualifiedMileage() + num1);
								checkSection2.setPoints(checkSection2.getPoints() + halfPoints);
								checkSection2.setUnqualifiedMileage(checkSection2.getUnqualifiedMileage() + num2);
							} else {// 合格
								checkSection1.setQualifiedPoints(checkSection1.getQualifiedPoints() + halfPoints);
								checkSection1.setQualifiedMileage(checkSection1.getQualifiedMileage() + num1);
								checkSection2.setQualifiedPoints(checkSection2.getQualifiedPoints() + halfPoints);
								checkSection2.setQualifiedMileage(checkSection2.getQualifiedMileage() + num2);
								if (points < 10) {// 合格且优良
									checkSection1.setGoodPoints(checkSection1.getGoodPoints() + halfPoints);
									checkSection1.setGoodMileage(checkSection1.getGoodMileage() + num1);
									checkSection2.setGoodPoints(checkSection2.getGoodPoints() + halfPoints);
									checkSection2.setGoodMileage(checkSection2.getGoodMileage() + num2);
								}
							}
						}
					}
				}
				if (flag) {
					errorMsg.append("每公里扣分数统计的第" + row + "行线路下管辖范围：" + mileage + " 未找到<br/>");
					fg = true;
				}
			} else {
				errorMsg.append("每公里扣分数统计的第" + row + "行线路下无法找到部门信息<br/>");
				fg = true;
			}
			if (StringUtil.isNotEmpty(points)) {
				if (points >= 40) {// 不合格
					check.getUnqualifiedList().add(mileage);
					check.setPoints(check.getPoints() + points);
					check.setUnqualifiedMileage(check.getUnqualifiedMileage() + Kilometre);
				} else {// 合格
					check.setQualifiedPoints(check.getQualifiedPoints() + points);
					check.setQualifiedMileage(check.getQualifiedMileage() + Kilometre);
					if (points < 10) {// 合格且优良
						check.setGoodPoints(check.getGoodPoints() + points);
						check.setGoodMileage(check.getGoodMileage() + Kilometre);
					}
				}
			} else {
				errorMsg.append("每公里扣分数统计的第" + row + "行没有填写扣分数<br/>");
				fg = true;
			}
			if (StringUtil.isNotEmpty(speed)) {// 保存时算平均速度
				check.setAverageSpeed(check.getAverageSpeed() + speed);
			} else {
				errorMsg.append("每公里扣分数统计的第" + row + "行没有填写速度<br/>");
				fg = true;
			}

		}

	}
}
