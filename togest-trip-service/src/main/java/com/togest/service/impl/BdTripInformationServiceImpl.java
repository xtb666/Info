package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.service.JcwServiceClient;
import com.togest.clien.service.LineFeignService;
import com.togest.code.client.ImportClient;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdTripInformationDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.BdCorresStandDetail;
import com.togest.domain.Page;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.domain.dto.BdTLineDTO;
import com.togest.domain.dto.BdTripInformationDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdCorresStandDetailService;
import com.togest.service.BdCorresStandService;
import com.togest.service.BdTLineService;
import com.togest.service.BdTripInformationService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.MessageUtils;
import com.togest.util.MoneyUtils;
import com.togest.util.PatternUtils;
import com.togest.util.SDBC;
import com.togest.utils.PageUtils;

@Service
public class BdTripInformationServiceImpl extends CrudCommonService<BdTripInformationDao, BdTripInformationDTO>
		implements BdTripInformationService {

	@Autowired
	private ImportClient importClient;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private JcwServiceClient jcwServiceClient;
	@Autowired
	private CheckLPSService checkLPSService;
	@Autowired
	private BdTLineService bdTLineService;
	@Autowired
	private BdCorresStandService bdCorresStandService;
	@Autowired
	private BdCorresStandDetailService bdCorresStandDetailService;
	
	public int save(BdTripInformationDTO entity) {
		if(null == entity.getMark()) {
			entity.setMark("0");
		}
		checkGlb(Arrays.asList(entity));
		return super.save(entity);
	}
	
	private void checkGlb(List<BdTripInformationDTO> list) {
		if(!CollectionUtils.isEmpty(list)) {
			list.stream().filter(entity -> StringUtil.isNotBlank(entity.getKiloDistance())).forEach(x ->{
				x.setKiloDistance(SDBC.toDBC(x.getKiloDistance()));
			});
		}
	}
	
	public void checkGlb(BdInfoQueryFilter entity) {
		if(StringUtil.isNotBlank(entity.getKiloDistance())) {
			entity.setKiloDistance(SDBC.toDBC(entity.getKiloDistance()));
		}
		if(StringUtil.isNotBlank(entity.getGlb())) {
			entity.setGlb(SDBC.toDBC(entity.getGlb()));
		}
	}
	
	@Override
	@DictAggregation
	public BdTripInformationDTO get(String id) {
		return super.get(id);
	}

	@Override
	@DictAggregation
	public List<BdTripInformationDTO> findTripInformationList(BdInfoQueryFilter entity) {
		return dao.findTripInformationList(entity);
	}

	@Override
	@DictAggregation
	public Page<BdTripInformationDTO> findTripInformationPage(Page<BdTripInformationDTO> page,
			BdInfoQueryFilter entity) {
		checkGlb(entity);
		PageUtils.setPage(page);
		Page<BdTripInformationDTO> pg = PageUtils.buildPage(dao.findTripInformationList(entity));
		return pg;
	}
	
	/**
	 * 故障处置流程图
	 */
	@Override
	public String svgImage(String id) {
		StringBuffer svgImageStr = new StringBuffer("");
		if(StringUtil.isNotBlank(id)) {
			
			BdTripInformationDTO bdTripInformationDTO = get(id);
			if(null == bdTripInformationDTO) {
				Shift.fatal(StatusCode.ID_DATA_EMPTY);
			}
			String lineId = bdTripInformationDTO.getLineId(); 
			String pavilionId = bdTripInformationDTO.getPavilionId(); 	
			String psPdId = bdTripInformationDTO.getPsPdId(); 
			String protect = bdTripInformationDTO.getProtect(); 
			String tlineVoltage = bdTripInformationDTO.getTlineVoltage();
			String tlineCurrent = bdTripInformationDTO.getTlineCurrent();
//			String flineVoltage = bdTripInformationDTO.getFlineVoltage();
			String impedanceAngle = bdTripInformationDTO.getImpedanceAngle();
			String distance = StringUtil.isNotBlank(bdTripInformationDTO.getStandardDistance()) ? bdTripInformationDTO.getStandardDistance() : bdTripInformationDTO.getKiloDistance(); 
			String coincidenceGate = bdTripInformationDTO.getCoincidenceGate();
			String flineInfluence = bdTripInformationDTO.getFlineInfluence();
			String flineInfo = bdTripInformationDTO.getFlineInfo();
			String faultType = bdTripInformationDTO.getFaultType();
			String armRunNum = bdTripInformationDTO.getArmRunNum();
			String dzResistance = StringUtil.isBlank(bdTripInformationDTO.getDzResistance()) ? "0" : bdTripInformationDTO.getDzResistance();
			String composeVoltage = bdTripInformationDTO.getComposeVoltage();
			svgImageStr.append("rt_15,link28,XL16,rt_17,link27,XL18,rt_19,link29,XL20,rt_21,link30,XL22,rt_23,link26,");
			String[] ct_bd_array = new String[] {"6","5","7","8"};
			String[] jcw_array = new String[] {"1","3","4","9","2"};
			List<String> ctBdList = Arrays.asList(ct_bd_array);
			List<String> jcwList = Arrays.asList(jcw_array);
			if(ctBdList.contains(protect)) {
				svgImageStr.append("XL_24,281,link_4,link_5,link_6,link_7,link_8,rt4,rt5,rt6,rt7,rt8,");
			}else if(jcwList.contains(protect)) {
				svgImageStr.append("XL25,4,rt_26,link25,");
				String tR = "1";
				String tF = "3";
				String fR = "2";
				String tS = "4";
				BdTLineDTO lineParam = new BdTLineDTO(lineId,pavilionId,psPdId);
				BdTLineDTO bdTLineDTO = bdTLineService.get(lineParam);
				if(null != bdTLineDTO) {
					//如果故障类型为At供电时过负荷条件(部分)如下
					boolean conditionOne = MoneyUtils.sub(tlineVoltage, "20") >= 0.00; ////T线电压大于等于20kv
//					boolean conditionTwo = MoneyUtils.sub(MoneyUtils.add(tlineVoltage, flineVoltage, 10), "40") >= 0.00; ////合成电压
					boolean conditionThree = MoneyUtils.sub(impedanceAngle, "0") > 0.00  && MoneyUtils.sub(impedanceAngle, "20")< 0.00; //阻抗角小于等于30度
					boolean conditionSix = MoneyUtils.sub(tlineCurrent, MoneyUtils.sub(bdTLineDTO.getTlineCurrent(), "100", 10)) >= 0.00 ;
					boolean conditionSeven = MoneyUtils.sub(tlineCurrent, MoneyUtils.add(bdTLineDTO.getTlineCurrent(), "100", 10)) <= 0.00 ; 
//					boolean conditionEight = "20002".equals(lineId) || "10037".equals(lineId) ;
					boolean conditionNine = tR.equals(faultType); //At供电时的条件为此三种故障类型
					boolean conditionTen = MoneyUtils.sub(tlineVoltage, "17") > 0.00 && MoneyUtils.sub(tlineVoltage, "19") < 0.00 ; //AT供电时，合福高铁过流保护时T线电压满足条件
					boolean conditionEleven = MoneyUtils.sub(composeVoltage, "40") >= 0.00 ; //AT供电时，(沪昆高铁)合成电压大于等于40KV
					boolean conditionTwelve = (MoneyUtils.sub(composeVoltage, "38") > 0.00 && MoneyUtils.sub(composeVoltage, "42") < 0.00 ); //AT供电时，(沪昆高铁)合成电压满足条件
					//如果故障类型为直供则过负荷条件如下：
					boolean conditionA = tS.equals(faultType); //故障类型为直供
					boolean conditionB = MoneyUtils.sub(tlineVoltage, "19") > 0.00; //T线电压19kv以上
					boolean conditionC = MoneyUtils.sub(tlineCurrent, bdTLineDTO.getTlineCurrent()) >= 0.00 ; //T线电流大于或等于保护整定值
					boolean conditionD = "2".equals(protect); //保护名称为过流
					boolean conditionE = ("3".equals(protect) || "4".equals(protect)) ; //保护名称为阻抗I段或阻抗II段
//					boolean conditionF = MoneyUtils.sub(impedanceAngle, "40") <= 0.00 ; //阻抗角小于等于40度
					boolean conditionG = StringUtil.isNotBlank(armRunNum) ; //供电臂内有车运行
					if(StringUtil.isBlank(bdTLineDTO.getCtRatio()) || "0".equals(bdTLineDTO.getCtRatio().trim())) {
						bdTLineDTO.setCtRatio("0");
					}
					if(StringUtil.isBlank(bdTLineDTO.getPtRatio()) || "0".equals(bdTLineDTO.getPtRatio().trim())) {
						bdTLineDTO.setPtRatio("1");
					}
					String resistanceVal1 = MoneyUtils.div(MoneyUtils.multi(dzResistance, bdTLineDTO.getCtRatio(), 10), bdTLineDTO.getPtRatio(), 3); //合福高铁过负荷判断条件---阻抗整定值
					boolean conditionH = (MoneyUtils.sub(resistanceVal1, MoneyUtils.sub(bdTLineDTO.getImpedanceResistance(), "5", 10)) >= 0.00  //合福高铁阻抗动作时电阻判断条件
							&& MoneyUtils.sub(resistanceVal1, MoneyUtils.add(bdTLineDTO.getImpedanceResistance(), "5", 10)) <= 0.00);
					String resistanceVal2 = MoneyUtils.div(MoneyUtils.multi(dzResistance, bdTLineDTO.getCtRatio(), 10), MoneyUtils.multi(bdTLineDTO.getPtRatio(), "2", 10), 3); //沪昆高铁过负荷判断条件---阻抗整定值
					boolean conditionI = (MoneyUtils.sub(resistanceVal2, MoneyUtils.sub(bdTLineDTO.getImpedanceResistance(), "5", 10)) >= 0.00 
							&& MoneyUtils.sub(resistanceVal2, MoneyUtils.add(bdTLineDTO.getImpedanceResistance(), "5", 10)) <= 0.00) ;
					boolean conditionJ = ("2".equals(protect) || "1".equals(protect)); //保护动作为过流或速断
					boolean conditionK = MoneyUtils.sub(impedanceAngle, "0") > 0.00  && MoneyUtils.sub(impedanceAngle, "50")< 0.00; //阻抗角不小于50度
					svgImageStr.append("XL28,26,rt_29,link22,");
					//((AT供电(沪昆高铁)||(合福高铁))||(直供))
					if((conditionNine && conditionThree &&
							(((("20002".equals(lineId) && conditionOne &&conditionH) 
									|| ("10037".equals(lineId) && conditionEleven && conditionI)) &&conditionE)
									||((("20002".equals(lineId) && conditionTen)
											||("10037".equals(lineId) && conditionTwelve)) && conditionD && conditionC)))
							||(conditionA && ((conditionJ && conditionC)||(conditionE &&conditionSix && conditionSeven))
									&&conditionB && conditionK &&conditionG)){
						//条件均满足则为过负荷跳闸
						svgImageStr.append("XL31,30,33,rt_34,link23,");
					}else{//不同时满足上述五项条件则为故障跳闸
						svgImageStr.append("45,47,XL30,rt_35,link9,");
						if(fR.equals(faultType)){//判断为F线故障
							svgImageStr.append("XL36,57,58,rt_37,link8,");
							if("1".equals(coincidenceGate)){//重合闸成功
								svgImageStr.append("XL38,177,rt_39,link7,") ;
								if("2".equals(flineInfluence)){//无异常
									svgImageStr.append("XL40,188,rt_41,link5,XL42,rt_43,link3,XL44,190,rt_45,link1,");
								}else{//有异常
									svgImageStr.append("XL46,rt_47,link6,rt_49,link4,XL50,rt_51,link2,189,");
									if("1".equals(flineInfo)){//有影响
										svgImageStr.append("XL53,191,rt_54,link10,XL52,");
									}else{
										svgImageStr.append("XL48,192,");
									}
								}
							}else{//重合闸失败，进入流程图1
								svgImageStr.append("XL55,178,link_1,rt1,");
							}
						}else if(tR.equals(faultType) || tF.equals(faultType) || tS.equalsIgnoreCase(faultType)){//判断为T线故障
							svgImageStr.append("XL56,170,176,rt_57,link11,");
							if("1".equals(coincidenceGate)){//重合闸成功,进入流程图2
								svgImageStr.append("XL58,179,link_2,rt2,");     
							}else{//重合闸失败
								svgImageStr.append("XL59,180,rt_60,link12,") ;
								BdCorresStandDTO standParam = new BdCorresStandDTO(lineId, pavilionId, psPdId);
								BdCorresStandDTO bdCorresStandDTO = bdCorresStandService.get(standParam);
								if(null != bdCorresStandDTO){
									StandarDistanceAndGlb distanceArr = new StandarDistanceAndGlb();
									if(null != distance && distance.equals(bdTripInformationDTO.getStandardDistance())){
										distanceArr.setDistance(bdTripInformationDTO.getStandardDistance());
									}else{
										distanceArr.setGlb(bdTripInformationDTO.getKiloDistance());
									}
									
									BdCorresStandDetail condition1 = new BdCorresStandDetail(bdCorresStandDTO.getId(), "变电所上网点");
									int res = kiloDistance(condition1, distanceArr);
									if(res == 1){//变电所上网点
										svgImageStr.append("duan3,duan1,bd,193,196,rt_63,link13,");
									}else{
										BdCorresStandDetail condition2 =  new BdCorresStandDetail(bdCorresStandDTO.getId(), "AT所上网点") ; 
										res = kiloDistance(condition2, distanceArr) ;
										if(res == 1){//AT所上网点
											svgImageStr.append("at,193,195,rt_64,link14,");
										}else{
											BdCorresStandDetail condition3 =new BdCorresStandDetail(bdCorresStandDTO.getId(), "分区所上网点") ;
											res = kiloDistance(condition3, distanceArr) ;
											if(res == 1){//分区所上网点
												svgImageStr.append("duan3,duan2,fq,193,194,rt_65,link15,");
											}else{//否则JCW故障,指向流程图3
												svgImageStr.append("XL61,181,link_3,rt3,");
											}
										}
									}
								}else{
									svgImageStr.append("XL61,181,link_3,rt3,");
								}
							}
						}
					}
				}
			}else {//既不变电故障也不是JCW故障
				svgImageStr.append("XL25,4,rt_26,link25,51,XL27,link_9,link_10,link_11,link_12,link_13,link_14,rt9,rt10,rt11,rt12,rt13,rt14,");
			}
		}
		return svgImageStr.toString();
	}
	
	public int kiloDistance(BdCorresStandDetail bdCorresStandDetail, StandarDistanceAndGlb distanceAndGlb){ 
		List<BdCorresStandDetail> standDetailList = bdCorresStandDetailService.findList(bdCorresStandDetail);
		if(!CollectionUtils.isEmpty(standDetailList)){
			String noteGlb = PatternUtils.kilometerStandard(standDetailList.get(0).getGlb()) ;
			String noteDistance = standDetailList.get(0).getDistance();
			int isSubstation = 0 ;
			if(StringUtil.isNotBlank(distanceAndGlb.getGlb())){
				String checkDistance = PatternUtils.kilometerStandardToNumber(distanceAndGlb.getGlb()) ;
				noteGlb = PatternUtils.kilometerStandardToNumber(noteGlb) ;
				String minKilo = MoneyUtils.sub(noteGlb, "1", 10);
				String maxKilo = MoneyUtils.add(noteGlb, "1", 10);
				if(MoneyUtils.sub(checkDistance, minKilo) >= 0.00 && MoneyUtils.sub(checkDistance, maxKilo) <= 0.00) {
					isSubstation = 1;
				}
			}else{
				String checkDistance = distanceAndGlb.getDistance();
				String minKilo = MoneyUtils.sub(noteDistance, "1", 10);
				String maxKilo = MoneyUtils.add(noteDistance, "1", 10);
				if(MoneyUtils.sub(checkDistance, minKilo) >= 0.00 && MoneyUtils.sub(checkDistance, maxKilo) <= 0.00) {
					isSubstation = 1;
				}
			}
			return isSubstation ;	
		}else{
			return 2 ;
		}	
}

	@Override
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId,
			String sectionId, Map<String, Object> propMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		Set<String> isNotFindLineSet = new HashSet<>();
		Set<String> isNotFindPsPdSet = new HashSet<>();
		Set<String> isNotFindPavilionSet = new HashSet<>();

		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			Map<String, String> lineMap = new HashMap<String, String>();
			Map<String,String> psPdMap = new HashMap<>();
			Map<String,String> pavilionMap = new HashMap<>();
			
			List<String> lineNames = CollectionUtils.distinctExtractToList(result, "lineName", String.class);
			List<String> psPdNames = CollectionUtils.distinctExtractToList(result, "psPdName", String.class);
			List<String> pavilionNames = CollectionUtils.distinctExtractToList(result, "pavilionName", String.class);
			
			if (StringUtil.isNotEmpty(lineNames)) {
				List<Map<String, String>> lineList = lineFeignService
						.getIdByNames(CollectionUtils.convertToString(lineNames, ",")).getData();
				if (StringUtil.isNotEmpty(lineList)) {
					for (Map<String, String> map2 : lineList) {
						lineMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			if (StringUtil.isNotEmpty(psPdNames)) {
				List<Map<String, String>> psPdList = jcwServiceClient
						.getIdByNames(CollectionUtils.convertToString(psPdNames, ",")).getData();
				if (StringUtil.isNotEmpty(psPdList)) {
					for (Map<String, String> map2 : psPdList) {
						psPdMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			if (StringUtil.isNotEmpty(pavilionNames)) {
				List<Map<String, String>> pavilionList = jcwServiceClient
						.getPavilionsIdByNames(CollectionUtils.convertToString(pavilionNames, ",")).getData();
				if (StringUtil.isNotEmpty(pavilionList)) {
					for (Map<String, String> map2 : pavilionList) {
						pavilionMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			
			if (StringUtil.isNotEmpty(result)) {
				List<BdTripInformationDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
						BdTripInformationDTO entity = dataList.get(i);
						String lineName = entity.getLineName();
						String psPdName = entity.getPsPdName();
						String pavilionName = entity.getPavilionName();
						
						boolean fg1 = checkLPSService.check(entity, lineMap, lineName, i, errorMsg, 1, MessageUtils.TIPS_NOT_FIND_LINE, MessageUtils.TIPS_NOT_INPUT_LINE);
						boolean fg2 = checkLPSService.check(entity, psPdMap, psPdName, i, errorMsg, 2, MessageUtils.TIPS_NOT_FIND_PSPD, MessageUtils.TIPS_NOT_INPUT_PSPD);
						boolean fg3 = checkLPSService.check(entity, pavilionMap, pavilionName, i, errorMsg, 3, MessageUtils.TIPS_NOT_FIND_PAVILION, MessageUtils.TIPS_NOT_INPUT_PAVILION);
						checkLPSService.isNotFind(fg1, isNotFindLineSet, lineName);
						checkLPSService.isNotFind(fg2, isNotFindPsPdSet, psPdName);
						checkLPSService.isNotFind(fg3, isNotFindPavilionSet, pavilionName);
						
						if (!fg1 && !fg2 && !fg3) {
							if(checkRepeat(entity)) {
								repeatCount ++; 
								repeatStr.append("第" + (i + 2) + "行," + "此综合管理信息已经存在: "
										+  "线别:"     + lineName 
										+  ",供电臂:"  + psPdName 
										+  ",变电所 :"  + pavilionName + "<br/>");
							}else {
								save(entity);
								count ++;
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
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		map.put("repeatCount", repeatCount);
		map.put("repeatMsg", repeatStr.toString());
		map.put("isNotFindLineSet", isNotFindLineSet);
		map.put("isNotFindPsPdSet", isNotFindPsPdSet);
		map.put("isNotFindPavilionSet", isNotFindPavilionSet);
		return map;
	}

	private List<BdTripInformationDTO> setData(List<Map<String, Object>> result) {
		List<BdTripInformationDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdTripInformationDTO BdTripInformationDTO = new BdTripInformationDTO();
			Map<String, Object> obj = result.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(BdTripInformationDTO, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(BdTripInformationDTO);
		}
		return lists;
	}
	
	private boolean checkRepeat(BdTripInformationDTO entity) {
		BdTripInformationDTO queryFilter = new BdTripInformationDTO();
		queryFilter.setLineId(entity.getLineId());
		queryFilter.setPavilionId(entity.getPavilionId());
		queryFilter.setPsPdId(entity.getPsPdId());
		queryFilter.setTime(entity.getTime());
 		List<BdTripInformationDTO> BdTripInformationDTOList = findList(queryFilter);
		return !CollectionUtils.isEmpty(BdTripInformationDTOList) ? true : false;
	}
	
	private class StandarDistanceAndGlb{
		private String distance = "";
		private String glb = "";
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public String getGlb() {
			return glb;
		}
		public void setGlb(String glb) {
			this.glb = glb;
		}
	}
}
