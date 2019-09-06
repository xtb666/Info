package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.service.JcwServiceClient;
import com.togest.clien.service.LineFeignService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.common.util.xls.ImportExcel;
import com.togest.dao.BdCorresStandDao;
import com.togest.domain.BdCorresStandDetail;
import com.togest.domain.Page;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdCorresStandDetailService;
import com.togest.service.BdCorresStandService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.MessageUtils;
import com.togest.utils.PageUtils;

@Service
public class BdCorresStandServiceImpl extends CrudCommonService<BdCorresStandDao, BdCorresStandDTO>
		implements BdCorresStandService {
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private JcwServiceClient jcwServiceClient;
	@Autowired
	private CheckLPSService checkLPSService;
	@Autowired
	private BdCorresStandDetailService bdCorresStandDetailService;
	
	private  static final Logger LOG = LoggerFactory.getLogger(BdCorresStandServiceImpl.class);
	
	@Override
	public List<BdCorresStandDTO> findCorresStandList(BdInfoQueryFilter entity) {
		return dao.findCorresStandList(entity);
	}

	@Override
	public Page<BdCorresStandDTO> findCorresStandPage(Page<BdCorresStandDTO> page, BdInfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<BdCorresStandDTO> pg = PageUtils.buildPage(dao.findCorresStandList(entity));
		if(!CollectionUtils.isEmpty(pg.getList())) {
			pg.getList().forEach(x ->{
				x.setBreakerNumber(StringUtil.isNotBlank(x.getBreakerNumber()) ? x.getBreakerNumber() : "");
				x.setFeederNumber(StringUtil.isNotBlank(x.getFeederNumber()) ? x.getFeederNumber() : "");
				x.setCurrentRatio(StringUtil.isNotBlank(x.getCurrentRatio()) ? x.getCurrentRatio() : "");
				x.setVoltageRatio(StringUtil.isNotBlank(x.getVoltageRatio()) ? x.getVoltageRatio() : "");
				x.setArmLength(StringUtil.isNotBlank(x.getArmLength()) ? x.getArmLength() : "");
				x.setLineLength(StringUtil.isNotBlank(x.getLineLength()) ? x.getLineLength() : "");
				x.setNetworkLength(StringUtil.isNotBlank(x.getNetworkLength()) ? x.getNetworkLength() : "");
				x.setUnitPowerLine(StringUtil.isNotBlank(x.getUnitPowerLine()) ? x.getUnitPowerLine() : "");
				x.setUnitNetworkLine(StringUtil.isNotBlank(x.getUnitNetworkLine()) ? x.getUnitNetworkLine() : "");
				x.setTotalReactance1(StringUtil.isNotBlank(x.getTotalReactance1()) ? x.getTotalReactance1() : "");
				x.setTotalReactance2(StringUtil.isNotBlank(x.getTotalReactance2()) ? x.getTotalReactance2() : "");
				x.setTotalReactance3(StringUtil.isNotBlank(x.getTotalReactance3()) ? x.getTotalReactance3() : "");
			});
		}
		return pg;
	}

	@Transactional
	@Override
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream,
			String templetId, String sectionId, Map<String, Object> propMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		Set<String> isNotFindLineSet = new HashSet<>();
		Set<String> isNotFindPsPdSet = new HashSet<>();
		Set<String> isNotFindPavilionSet = new HashSet<>();
		
		try {
			ImportExcel ei = new ImportExcel(fileName, inputStream, 0, 0);
			int lastDataRowNum = ei.getLastDataRowNum();
			LOG.info("共计总行数：" + lastDataRowNum);
			if(lastDataRowNum <= 8 ) {
				errorMsg.append("数据为空,请修改后上传");
			}
			String lineName = ei.getRow(1).getCell(1).getStringCellValue();
			String pavilionName = ei.getRow(1).getCell(3).getStringCellValue();
			String psPdName = ei.getRow(1).getCell(5).getStringCellValue();
			//断路器编号
			String breakerNumber = ei.getRow(2).getCell(1).getStringCellValue();
		    //馈线编号
		     String feederNumber = ei.getRow(2).getCell(4).getStringCellValue();
		    //电流比
		     String currentRatio = ei.getRow(3).getCell(1).getStringCellValue();
		    //电压比
		     String voltageRatio = ei.getRow(3).getCell(4).getStringCellValue();
		    //供电臂长度（公里）
		     String armLength = ei.getRow(4).getCell(1).getStringCellValue();
		    //供电线长度（公里）
		     String lineLength = ei.getRow(4).getCell(4).getStringCellValue();
		    //接触网长度（公里）
		     String networkLength = ei.getRow(4).getCell(6).getStringCellValue();
		    //供电线单位电抗（Ω）
		     String unitPowerLine = ei.getRow(5).getCell(1).getStringCellValue();
		    //接触网单位电抗（Ω）
		     String unitNetworkLine = ei.getRow(5).getCell(4).getStringCellValue();
		    //Ⅰ段总电抗（Ω）
		     String totalReactance1 = ei.getRow(6).getCell(1).getStringCellValue();
		    //Ⅱ段总电抗（Ω）
		     String totalReactance2 = ei.getRow(6).getCell(3).getStringCellValue();
		    //Ⅲ段总电抗（Ω）
		     String totalReactance3 = ei.getRow(6).getCell(5).getStringCellValue();
		     Map<String, String> lineMap = new HashMap<String, String>();
			 Map<String,String> psPdMap = new HashMap<>();
			 Map<String,String> pavilionMap = new HashMap<>();
		     if(StringUtil.isNotEmpty(lineName)) {
					List<Map<String, String>> lineList = lineFeignService
							.getIdByNames(lineName).getData();
					if (StringUtil.isNotEmpty(lineList)) {
						for (Map<String, String> map2 : lineList) {
							lineMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
						}
					}
			 }
			 if(StringUtil.isNotEmpty(psPdName)) {
				List<Map<String, String>> psPdList = jcwServiceClient.getIdByNames(psPdName).getData();
				if (StringUtil.isNotEmpty(psPdList)) {
					for (Map<String, String> map2 : psPdList) {
						psPdMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			 }
			 if(StringUtil.isNotEmpty(pavilionName)) {
				List<Map<String, String>> pavilionList = jcwServiceClient.getPavilionsIdByNames(pavilionName).getData();
				if (StringUtil.isNotEmpty(pavilionList)) {
					for (Map<String, String> map2 : pavilionList) {
						pavilionMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			 }
		     
		     BdCorresStandDTO entity = new BdCorresStandDTO
		    		 (null, null, null, lineName, pavilionName, psPdName, breakerNumber, feederNumber, currentRatio, voltageRatio, armLength, 
		    				 lineLength, networkLength, unitPowerLine, unitNetworkLine, totalReactance1, totalReactance2, totalReactance3);
		     boolean fg1 = checkLPSService.check(entity, lineMap, lineName, 0, errorMsg, 1, MessageUtils.TIPS_NOT_FIND_LINE, MessageUtils.TIPS_NOT_INPUT_LINE);
		     boolean fg2 = checkLPSService.check(entity, psPdMap, psPdName, 0, errorMsg, 2, MessageUtils.TIPS_NOT_FIND_PSPD, MessageUtils.TIPS_NOT_INPUT_PSPD);
		     boolean fg3 = checkLPSService.check(entity, pavilionMap, pavilionName, 0, errorMsg, 3, MessageUtils.TIPS_NOT_FIND_PAVILION, MessageUtils.TIPS_NOT_INPUT_PAVILION);
		     checkLPSService.isNotFind(fg1, isNotFindLineSet, lineName);
			 checkLPSService.isNotFind(fg2, isNotFindPsPdSet, psPdName);
			 checkLPSService.isNotFind(fg3, isNotFindPavilionSet, pavilionName);
			 
		     List<BdCorresStandDetail> standDetailList = null;
		     if(lastDataRowNum >= 9) {
		    	 standDetailList = new ArrayList<>();
		    	 for (int i = 8; i <= lastDataRowNum -1 ; i++) {
					Row row = ei.getRow(i);
					int rowNum = row.getRowNum();
					if(rowNum <7 ) {
						Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
					}
				    Object psaName = ei.getCellValue(i, 0);
				    Object pillar = ei.getCellValue(i, 1);
				    Object glb = ei.getCellValue(i, 2);
				    Object span = ei.getCellValue(i, 3);
				    Object distance = ei.getCellValue(i, 4);
				    Object issInductance = ei.getCellValue(i, 5);
				    Object note = ei.getCellValue(i, 6);
				    BdCorresStandDetail standDetail = 
				    		new BdCorresStandDetail(null, null,objectToString(psaName), objectToString(pillar), 
				    				objectToString(glb), objectToString(span), objectToString(distance),
				    				objectToString(issInductance), objectToString(note), true);
				    standDetailList.add(standDetail);
				}
		     }
		     if(!fg1 && !fg2 && !fg3) {
				if(checkRepeat(entity)) {
					repeatCount ++; 
					repeatStr.append("第" + (0 + 2) + "行," + "此故标对应已经存在: "
							+  "线路:"     + lineName 
							+  ",供电臂:"  + psPdName 
							+  ",变电所 :"  + pavilionName + "<br/>");
				}else {
					save(entity);
					count ++;
					if(!CollectionUtils.isEmpty(standDetailList)) {
						standDetailList.forEach(x -> {
							x.preInsert();
							x.setStandId(entity.getId());
						});
						bdCorresStandDetailService.insertBatch(standDetailList);
					}
				}
			}
		} catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		map.put("errorMsg", errorMsg.toString());
		map.put("repeatCount", repeatCount);
		map.put("repeatMsg", repeatStr.toString());
		map.put("isNotFindLineSet", isNotFindLineSet);
		map.put("isNotFindPsPdSet", isNotFindPsPdSet);
		map.put("isNotFindPavilionSet", isNotFindPavilionSet);
		return map;
	}
	
	private List<BdCorresStandDTO> setData(List<Map<String, Object>> result) {
		List<BdCorresStandDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdCorresStandDTO bdCorresStandDTO = new BdCorresStandDTO();
			Map<String, Object> obj = result.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(bdCorresStandDTO, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(bdCorresStandDTO);
		}
		return lists;
	}
	
	private boolean checkRepeat(BdCorresStandDTO entity) {
		BdCorresStandDTO queryFilter = new BdCorresStandDTO();
		queryFilter.setLineId(entity.getLineId());
		queryFilter.setPavilionId(entity.getPavilionId());
		queryFilter.setPsPdId(entity.getPsPdId());
 		List<BdCorresStandDTO> BdCorresStandDTOList = findList(queryFilter);
		return !CollectionUtils.isEmpty(BdCorresStandDTOList) ? true : false;
	}
	
	public String objectToString(Object obj){
		if(null != obj) {
			return String.valueOf(obj);
		}
		return null;
	}
}
