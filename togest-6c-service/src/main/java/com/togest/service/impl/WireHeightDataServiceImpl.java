package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.PillarFeginService;
import com.togest.client.response.Pillar;
import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.function.FunctionUtil;
import com.togest.common.util.function.OptionalUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.PillarDao;
import com.togest.dao.WireHeightDataDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Naming;
import com.togest.domain.Page;
import com.togest.domain.PillarInfoDTO;
import com.togest.domain.PillarInfoSelectorV2DTO;
import com.togest.domain.WireHeightControlHistoryDTO;
import com.togest.domain.WireHeightDataDTO;
import com.togest.response.PillarInfoSelectorV2;
import com.togest.service.WireHeightControlHistoryService;
import com.togest.service.WireHeightDataService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.DataUtil;
import com.togest.util.MessageUtils;
import com.togest.utils.PageUtils;

@Service
public class WireHeightDataServiceImpl extends CrudCommonService<WireHeightDataDao, WireHeightDataDTO>
		implements WireHeightDataService {
	@Autowired
	private DataUtil dataUtil;
	@Autowired
	private DeptFeignService deptFeignService;
	@Autowired
	private ImportClient importClient;
	@Autowired
	private PillarDao pillarDao;
	@Autowired
	private PillarFeginService pillarFeginService;
	@Autowired
	private WireHeightControlHistoryService wireHeightDataHistoryService;
	public static final String SYSTEM_1C = "C1"; //默认导高系统
	
	private static final Lock lock = new ReentrantLock();
	
	private static final Logger LOG = LoggerFactory.getLogger(WireHeightDataServiceImpl.class);
	
    @Override
    public int save(WireHeightDataDTO entity, int status) {
    	entity.check();
    	if(StringUtil.isNotBlank(entity.getWorkAreaId()) && StringUtil.isBlank(entity.getWorkShopId())) {
    		Naming nameing = OptionalUtils.map(deptFeignService.getParentDept(entity.getWorkAreaId()));
    		if(null != nameing) {
    			entity.setWorkShopId(nameing.getId());
    		}
    	}
    	if(1 == status) {
    		checkRepeat(entity);
    	}
    	return super.save(entity);
    }
    
    @Override
    @DictAggregation
    public List<WireHeightDataDTO> findList(WireHeightDataDTO entity) {
    	List<WireHeightDataDTO> wireHeightDataDTOList = super.findList(entity);
    	checkList(wireHeightDataDTOList);
    	return wireHeightDataDTOList;
    }
    
    @Override
    @DictAggregation
    public Page<WireHeightDataDTO> findPage(Page<WireHeightDataDTO> page, WireHeightDataDTO entity) {
    	PageUtils.setPage(page);
		Page<WireHeightDataDTO> pg = PageUtils.buildPage(dao.findList(entity));
		checkList(pg.getList());
    	return pg;
    }

	private void checkList(List<WireHeightDataDTO> wireHeightDataDTOList) {
		if(!CollectionUtils.isEmpty(wireHeightDataDTOList)) {
			Map<String, List<WireHeightDataDTO>> upWireHeightDataMap = findUpWireHeightDataList(wireHeightDataDTOList);
 		    dataUtil.findNowAndUpWireHeightData(wireHeightDataDTOList, upWireHeightDataMap, true);
    	}
	}
	
	@Override
	public WireHeightDataDTO findUpWireHeightData(WireHeightDataDTO wireHeightDataDTO) {
		return dao.findUpWireHeightData(wireHeightDataDTO);
	}
	
	@Override
	public Map<String,List<WireHeightDataDTO>> findUpWireHeightDataList(List<WireHeightDataDTO> wireHeightDataDTOList) {
		Map<String, List<WireHeightDataDTO>> upWireHeightDataMap = new HashMap<>();
		List<WireHeightDataDTO> upWireHeightDataList = dao.findUpWireHeightDataList(wireHeightDataDTOList);
		if(!CollectionUtils.isEmpty(upWireHeightDataList)) {
			upWireHeightDataMap = upWireHeightDataList.stream().filter(x->StringUtil.isNotBlank(x.getPillarId()))
					.collect(Collectors.groupingBy(WireHeightDataDTO::getPillarId));
			upWireHeightDataMap.forEach((key,value)->{
				value.sort((v1, v2)-> v2.getCheckDate().compareTo(v1.getCheckDate()));
			});
		}
		return upWireHeightDataMap;
	}

	
	private Map<String, WireHeightControlHistoryDTO> findLastNewWireHeightByPillars(List<String> pillarIdList) {
		Map<String, WireHeightControlHistoryDTO> wireHeightDataMap = new HashMap<>();
		List<WireHeightControlHistoryDTO> wireHeightDataHistoryDTOList = wireHeightDataHistoryService.findLastNewWireHeightByPillars(pillarIdList);
		if(!CollectionUtils.isEmpty(wireHeightDataHistoryDTOList)) {
			wireHeightDataHistoryDTOList.stream().filter(x->StringUtil.isNotBlank(x.getPillarId())).forEach(x->{
				wireHeightDataMap.put(x.getPillarId(), x);
			});
		}
		return wireHeightDataMap;
	}
    
	@Override
	public Map<String, Object> importData(String fileName, InputStream inputStream, String templetId,
			String sectionId, Map<String, String> propMap, String systemId, String dymType) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();

		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			if (StringUtil.isNotEmpty(result)) {
				List<String> lineNames = CollectionUtils.distinctExtractToList(result, "lineName", String.class);
				List<String> workAreaNames = CollectionUtils.distinctExtractToList(result, "workAreaName", String.class);
				List<String> workShopNames = CollectionUtils.distinctExtractToList(result, "workShopName", String.class);
				List<String> sectionNames = CollectionUtils.distinctExtractToList(result, "sectionName", String.class);
				workAreaNames.addAll(workShopNames);
				workAreaNames.addAll(sectionNames);
				List<String> psaNames = CollectionUtils.distinctExtractToList(result, "psaName", String.class);
				
				Map<String, String> lineMap = dataUtil.getLineIdByNames(lineNames);
				Map<String, String> deptMap= dataUtil.getDeptIdByNames(workAreaNames);
				Map<String, String> psaMap = dataUtil.getPsaIdByNames(psaNames);
				Map<String, String> directionMap = dataUtil.findDirectionByDictName();
				Map<String, String> defectTypeMap = dataUtil.getListBySystemCode(systemId);
//				Map<String, String> defectLevelMap = dataUtil.getDefectLevelByDictName(MessageUtils.DEFECT_GRADE);
				
				List<Pillar> pillarList = new ArrayList<Pillar>();
				List<Integer> indexList = new ArrayList<Integer>();
				Map<Integer, Pillar> pillarMap = new HashMap<Integer, Pillar>();
				List<WireHeightDataDTO> dataList = DataUtil.setData(result, WireHeightDataDTO.class);
				for (int i = 0; i < dataList.size(); i++) {
						WireHeightDataDTO entity = dataList.get(i);
						String defectTypeName = entity.getDefectTypeName();
						checkDefect(entity, defectTypeMap, defectTypeName, 1);
						check(entity, lineMap, entity.getLineName(), 1);
						check(entity, deptMap, entity.getWorkAreaName(), 2);
						check(entity, deptMap, entity.getWorkShopName(), 3);
						check(entity, psaMap, entity.getPsaName(), 4);
						check(entity, directionMap, entity.getDirectionName(), 5);
						check(entity, deptMap, entity.getSectionName(), 6);
						pillar(entity, i, pillarList, indexList);
				}
				dataUtil.handlePillar(indexList, pillarList, pillarMap);
				List<String> pillarIdList = pillarMap.values().stream().filter(pillar ->StringUtil.isNotEmpty(pillar)).map(Pillar::getId).collect(Collectors.toList());
				Map<String, WireHeightControlHistoryDTO> wireHeightLineHeightDataMap = findLastNewWireHeightByPillars(pillarIdList);
				for (int i = 0; i < dataList.size(); i++) {
					WireHeightDataDTO entity = dataList.get(i);
					if(StringUtil.isBlank(systemId)) {
						systemId = SYSTEM_1C;
					}
					entity.setSystemId(systemId);
					if(null != pillarMap.get(i)) {
						entity.setPillarId(pillarMap.get(i).getId());
						entity.setTunnelId(pillarMap.get(i).getTunnelId());
					}
					boolean fg = checkTips(entity, (i+3), errorMsg, propMap, wireHeightLineHeightDataMap);
					if(!fg) {
						try {
							lock.lock();
							if(checkRepeat(entity)) {
								dataUtil.caCulate(entity, wireHeightLineHeightDataMap, dymType);
								if(save(entity, 2)>0){
									repeatCount ++; 
								}
							}else {
								dataUtil.caCulate(entity, wireHeightLineHeightDataMap, dymType);
								if(save(entity, 2)>0) {
									count ++;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally {
							lock.unlock();
						}
					}
				}
			}else {
				errorMsg.append("数据为空,请修改后上传");
			}
		} catch (Exception e) {
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
//		map.put("repeatCount", repeatCount);
//		map.put("repeatMsg", repeatStr.toString());
		return map;
	}
    
	@Override
	public Map<String,Object> findDataAnaliayByDateList(WireHeightDataDTO entity) {
 		Map<String,Object> map = new HashMap<>();
 		List<Map<String,Object>> bridgeData = new ArrayList<Map<String,Object>>();
 		List<String> xdata = new ArrayList<>();
		List<WireHeightDataDTO> list = dao.findDataAnaliayByDateList(entity);
        if(StringUtil.isNotEmpty(list)){
           List<Object> standHeights = new ArrayList<>(); //标准值
           List<Object> upMonthAnchorPointList = new ArrayList<>(); //上月检测导高
           List<Object> anchorPointList = new ArrayList<>(); //本次测量值
           List<Object> nowDatas = new ArrayList<>();
           List<String> pillarIdList = new ArrayList<>();
           List<Map<String, String>> tunnelMapList = null;
           List<Object> yearReviewValues = new ArrayList<>();
           List<Object> railChangValues = new ArrayList<>();
           List<Object> liftableValues = new ArrayList<>();
           List<String> glbs = new ArrayList<>();
           Map<String, List<WireHeightDataDTO>> upWireHeightDataMap = findUpWireHeightDataList(list);
           dataUtil.findNowAndUpWireHeightData(list, upWireHeightDataMap, false);
           list.stream().forEach(y ->{
        		 pillarIdList.add(y.getPillarId());
        		 xdata.add(y.getPillarNum());
 	        	 anchorPointList.add(y.getAnchorPoint());
 	        	 upMonthAnchorPointList.add(y.getUpMonthAnchorPoint());
 	        	 yearReviewValues.add(y.getReviewValue());
 	        	 railChangValues.add(y.getRailChangValue());
 	        	 liftableValues.add(y.getLiftableValue());
 	        	 standHeights.add(y.getStandarLineHeight());
 	        	 glbs.add(y.getGlb());
           });
           
           List<Map<String, String>> newLastList = dao.findNewLastAnchorPointList(pillarIdList);
           Map<String, String> newLastMap = FunctionUtil.getHashMap(newLastList, "pillar_id", "anchor_point");
		   pillarIdList.forEach(pillarId ->{
			   nowDatas.add(newLastMap.get(pillarId));
           });
			
           tunnelMapList = pillarDao.findPillarMaxAndMinNumByIds(pillarIdList);
           if(!CollectionUtils.isEmpty(tunnelMapList)) {
        	   List<Object> maxMinList = new ArrayList<>();
        	   tunnelMapList.forEach(x ->{
        		   maxMinList.add(x.get("minNum"));
        		   maxMinList.add(x.get("maxNum"));
        		   bridgeData.add(toMap(maxMinList, x.get("name")));
        	   });
           }
           
           map.put("pillarIdList", pillarIdList);
           map.put("pillarNums", xdata);
           map.put("anchorPoints", anchorPointList);
           map.put("upMonthAnchorPoints", upMonthAnchorPointList);
           map.put("nowDatas", nowDatas);
           map.put("yearReviewValues", yearReviewValues);
           map.put("railChangValues", railChangValues);
           map.put("liftableValues", liftableValues);
           map.put("standHeights", standHeights);
           map.put("bridgeData", bridgeData);
           map.put("glbs", glbs);
        }
 		return map;
	}
	
	@Override
	public List<PillarInfoSelectorV2DTO> pillarSelector(PillarInfoDTO paramPillarInfoDTO){
		List<PillarInfoSelectorV2DTO> pillarSelectors = new ArrayList<>();
		if(null != paramPillarInfoDTO) {
			LOG.debug("支柱悬着请求参数: {}", paramPillarInfoDTO.toString());
			List<PillarInfoSelectorV2> pillarInfoList = 
					OptionalUtils.map(pillarFeginService.pillarSelector2(paramPillarInfoDTO.getDeptId(),paramPillarInfoDTO.getPlId(),
							paramPillarInfoDTO.getPsaId(),paramPillarInfoDTO.getDirection(),
							paramPillarInfoDTO.getTunnelId(),paramPillarInfoDTO.getTrack()));
			if(!CollectionUtils.isEmpty(pillarInfoList)) {
				LOG.debug("支柱记录数: {},", pillarInfoList.size());
				List<String> pillarIdList = new ArrayList<>();
				Map<String,PillarInfoSelectorV2> pillarMap = new HashMap<>();
				pillarInfoList.forEach(x->{
					pillarMap.put(x.getId(), x);
					pillarIdList.add(x.getId());
				});
				
				Map<String,WireHeightControlHistoryDTO> wireHeightDataMap = findLastNewWireHeightByPillars(pillarIdList);
				pillarInfoList.forEach(x->{
					PillarInfoSelectorV2DTO pillarInfoSelectorV2DTO = new PillarInfoSelectorV2DTO();
					try {
						BeanUtils.copyProperties(pillarInfoSelectorV2DTO, x);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					WireHeightControlHistoryDTO wireHeightDataHistoryDTO = wireHeightDataMap.get(x.getId());
					if(null != wireHeightDataHistoryDTO) {
						pillarInfoSelectorV2DTO.setStandarLineHeight(wireHeightDataHistoryDTO.getStandarLineHeight());
						pillarInfoSelectorV2DTO.setStandarLineHeightUp(wireHeightDataHistoryDTO.getStandarLineHeightUp());
						pillarInfoSelectorV2DTO.setStandarLineHeightDown(wireHeightDataHistoryDTO.getStandarLineHeightDown());
					}
					pillarSelectors.add(pillarInfoSelectorV2DTO);
				});
			}
		}
		return pillarSelectors;
	}
	
	@Override
	public Map<String, Object> history(WireHeightDataDTO wireHeightDataDTO) {
		Map<String,Object> historyMap = new HashMap<>();
		List<WireHeightDataDTO> historyList = dao.history(wireHeightDataDTO);
		List<Date> dates = new ArrayList<>();
		List<Integer> anchorPoints = new ArrayList<>();
		List<Integer> railChangValues = new ArrayList<>();
		List<Integer> liftableValues = new ArrayList<>();
		if(!CollectionUtils.isEmpty(historyList)) {
			historyList.forEach(x ->{
				dataUtil.findLiftAndNeedAbleValue(x, x.getWireHeightControlHistoryDTO());
				dates.add(x.getCheckDate());
				anchorPoints.add(x.getAnchorPoint());
				railChangValues.add(x.getRailChangValue());
				liftableValues.add(x.getLiftableValue());
			});
		}
		historyMap.put("dates", dates);
		historyMap.put("anchorPoints", anchorPoints);
		historyMap.put("railChangValues", railChangValues);
		historyMap.put("liftableValues", liftableValues);
		return historyMap;
	}
	

	private boolean checkRepeat(WireHeightDataDTO entity) {
		WireHeightDataDTO wireHeightDataDTO = new WireHeightDataDTO();
		wireHeightDataDTO.setCheckDate(entity.getCheckDate());
		wireHeightDataDTO.setPillarId(entity.getPillarId());
		List<WireHeightDataDTO> wireHeightDataDTOList = dao.checkRepeat(wireHeightDataDTO);
		WireHeightDataDTO dto = !CollectionUtils.isEmpty(wireHeightDataDTOList) ? wireHeightDataDTOList.get(0) : null;
		if ((dto == null) || (dto.getId().equals(entity.getId()))) {
		     return false;
		}else {
			entity.setId(dto.getId());
			entity.setWireHeightControlHistoryId(dto.getWireHeightControlHistoryId());
			entity.setWireHeightControlHistoryDTO(dto.getWireHeightControlHistoryDTO());
			return true;
		}
	}

	private void check(WireHeightDataDTO entity, Map<String, String> map, String name, int status) {
		if(StringUtil.isNotBlank(name)) {
			String id  = map.get(name);
			if(status == 1) {
				entity.setLineId(id);
			}else if(status == 2) {
				entity.setWorkAreaId(id);
			}else if(status == 3) {
				entity.setWorkShopId(id);
			}else if(status == 4) {
				entity.setPsaId(id);
			}else if(status == 5) {
				entity.setDirection(id);
			}else if(status == 6) {
				entity.setSectionId(id);
			}
		}
	}
	
	private boolean checkTips(WireHeightDataDTO entity, int i, StringBuilder errorMsg, 
			Map<String,String> prop, Map<String, WireHeightControlHistoryDTO> wireHeightDataMap) {
		boolean fg = false;
		//线路
		if(StringUtil.isBlank(entity.getLineName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_LINE + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getLineId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_LINE + "!<br/>");
			fg = true;
		}
		if(StringUtil.isEmpty(entity.getCheckDate())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_CHECK_DATE + "!<br/>");
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
		//车间
		if(StringUtil.isBlank(entity.getWorkShopName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_WORK_SHOP + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getWorkShopId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_WORK_SHOP + "!<br/>");
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
		//段信息
		if(StringUtil.isBlank(entity.getSectionId())) {
			entity.setSectionId(prop.get("sectionId"));
			List<Map<String, String>> list = OptionalUtils.map(deptFeignService.getNameById(entity.getSectionId()));
			if(!CollectionUtils.isEmpty(list)) {
				entity.setSectionName(list.get(0).get("name"));
			}
		}
		//支柱信息
		if(StringUtil.isBlank(entity.getPillarId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_PILLAR + "!<br/>");
			fg = true;
		}else {
			if(null == wireHeightDataMap.get(entity.getPillarId())) {
				errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_STAND_LINE_HEIGHT + "!<br/>");
				fg = true;
			}
		}
		//静态检测值日期 
		if(null != entity.getStaticCheckDate()) {
			if(null == entity.getReviewValue()) {
				errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_REVIEW_VALUE + "!<br/>");
				fg = true;
			}
			if(null == entity.getCurrentReviewRailHeightValue()) {
				errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_CURRENT_REVIEW_RAIL_HEIGHT_VALUE + "!<br/>");
				fg = true;
			}
		}
		//处理日期
		if(null != entity.getDealDate()) {
			if(null == entity.getAdjustWireHeight()) {
				errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_ADJUST_WIRE_HEIGHT + "!<br/>");
				fg = true;
			}
		}
		
		return fg;
	}
	
	private void checkDefect(WireHeightDataDTO entity, Map<String, String> map, String name, int status) {
		if(null != map && map.size() >0 && StringUtil.isNotBlank(name)) {
			String m = map.get(name);
			if(null != m) {
				if(status == 1) {
					entity.setDefectType(m);
				}else if(status == 2) {
					entity.setDefectLevel(m);
				}
			}
		}
	}
	
	public void pillar(WireHeightDataDTO entity, int i, List<Pillar> pillarList,  List<Integer> indexList) {
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

	private Map<String, Object> toMap(List<Object> list, String name) {
    	Map<String, Object> map = null;
    	if(!CollectionUtils.isEmpty(list)) {
    		map = new HashMap<>();
    		map.put("name", name);
    		map.put("data", list);
    	}
		return map;
	}
	
	public static double glbStandard(String str) {
		if (StringUtil.isNotBlank(str)) {
			str = str.replace("K", "").replace("k", "").replace("+", ".");
			return Double.parseDouble(str);
		}
		return -1;
	}
}