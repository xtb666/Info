package com.togest.service.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.LineFeignService;
import com.togest.code.client.ImportClient;
import com.togest.code.util.ExcelImportUtil;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.file.StreamUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.common.util.xls.ImportExcel;
import com.togest.config.DefectConstants;
import com.togest.config.DefectSystem;
import com.togest.dao.MileageStatisticsDao;
import com.togest.domain.CheckTrainDTO;
import com.togest.domain.IdGen;
import com.togest.domain.MileageStatisticsDTO;
import com.togest.service.CheckTrainService;
import com.togest.service.CrudService;
import com.togest.service.MileageStatisticsService;

@Service
public class MileageStatisticsServiceImpl extends CrudService<MileageStatisticsDao, MileageStatisticsDTO>
		implements MileageStatisticsService {

	@Autowired
	MileageStatisticsDao dao;
	@Autowired
	protected ImportClient importService;
	@Autowired
	protected DeptFeignService deptFeignService;
	@Autowired
	protected LineFeignService lineService;
	@Autowired
	protected CheckTrainService checkTrainService;

	@Override
	public int deleteFalses(Map<String, Object> map) {
		return dao.deleteFalses(map);
	}

	@Override
	@Transactional
	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String createBy,
			String createIp) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder errorStr = new StringBuilder();
		StringBuilder repeatMsg = new StringBuilder();
		List<String> properties = Arrays.asList(
				new String[] { "juName", "sectionName", "equNum", "lineName", "string", "mileage", "alarmNum" });
		List<Map<String, Object>> list = null;
		Map<String, String> checkTrainMap = new HashMap<String, String>();
		CheckTrainDTO checkTrainDTO = new CheckTrainDTO();
		checkTrainDTO.setSystemId(DefectSystem.defectC3.getStatus());
		List<CheckTrainDTO> checkTrainList = checkTrainService.findList(checkTrainDTO);
		if (StringUtil.isNotEmpty(checkTrainList)) {
			for (CheckTrainDTO checkTrain : checkTrainList) {
				checkTrainMap.put(checkTrain.getTrainNum(), checkTrain.getId());
			}
		}

		try {
			byte[] bt = StreamUtils.InputStreamTOByte(inputStream);
			list = importService.importBaseData(properties, originalFilename, StreamUtils.byteTOInputStream(bt), 3);
			if (StringUtil.isNotEmpty(list)) {
				ImportExcel ei = new ImportExcel(originalFilename, StreamUtils.byteTOInputStream(bt), 0, 0);
				Object statisticsDate = ExcelImportUtil.getValue(ei, 1, properties.size() - 1);
				// 处理sectionName 和 lineName
				Map<String, String> sectionMap = new HashMap<String, String>();
				Map<String, String> lineMap = new HashMap<String, String>();
				List<String> sectionNames = CollectionUtils.distinctExtractToList(list, properties.get(1),
						String.class);
				List<String> lineNames = CollectionUtils.distinctExtractToList(list, properties.get(3), String.class);
				if (StringUtil.isNotEmpty(sectionNames)) {
					List<Map<String, String>> tempDept = deptFeignService
							.getIdByName(CollectionUtils.convertToString(sectionNames, ","), null).getData();
					if (StringUtil.isNotEmpty(tempDept)) {
						for (Map<String, String> map2 : tempDept) {
							sectionMap.put(map2.get("name"), map2.get("id"));
						}
					}
				}
				if (StringUtil.isNotEmpty(lineNames)) {
					List<Map<String, String>> tempDept = lineService
							.getIdByNames(CollectionUtils.convertToString(lineNames, ",")).getData();
					if (StringUtil.isNotEmpty(tempDept)) {
						for (Map<String, String> map2 : tempDept) {
							lineMap.put(map2.get("name"), map2.get("id"));
						}
					}
				}

				for (int i = 0; i < list.size(); i++) {
					boolean flag = false;
					Map<String, Object> obj = list.get(i);
					if (StringUtil.isEmpty(obj)) {
						continue;
					}
					MileageStatisticsDTO mileageStatisticsDTO = new MileageStatisticsDTO();
					if (StringUtil.isNotEmpty(statisticsDate)) {
						mileageStatisticsDTO.setStatisticsDate(
								DateUtils.stringToDate(statisticsDate.toString().trim().substring(0, 10)));
					}
					Object sectionName = obj.get(properties.get(1));
					String sectionId = null;
					if (StringUtil.isNotEmpty(sectionName)) {// 线路
						if (sectionName.toString().indexOf("局小结") != -1) {
							break;
						}
						sectionId = sectionMap.get(sectionName.toString().trim());
						if (StringUtil.isNotBlank(sectionId)) {
							mileageStatisticsDTO.setSectionId(sectionId);
						} else {
							errorStr.append("第" + (i + 4) + "行段：" + sectionName + "不存在<br/>");
							flag = true;
						}
					} else {
						errorStr.append("第" + (i + 4) + "行存在段没有填写段信息<br/>");
						flag = true;
					}

					Object equNum = obj.get(properties.get(2));
					if (StringUtil.isNotEmpty(equNum)) {
						if (equNum.toString().indexOf("段小结") != -1) {
							continue;
						}
						String equNumId = checkTrainMap.get(equNum.toString().trim());
						if (StringUtil.isEmpty(equNumId)) {
							errorStr.append("第" + (i + 4) + "行设备号" + equNum + "不存在<br/>");
							flag = true;
						} else {
							mileageStatisticsDTO.setEquNum(equNum.toString());
						}
					} else {
						errorStr.append("第" + (i + 4) + "行存在设备号没有填写设备号信息<br/>");
						flag = true;
					}

					Object lineName = obj.get(properties.get(3));
					String lineId = null;
					if (StringUtil.isNotEmpty(lineName)) {
						if (lineName.toString().indexOf("设备小结") != -1) {
							continue;
						}
						lineId = lineMap.get(lineName.toString().trim());
						if (StringUtil.isNotBlank(lineId)) {
							mileageStatisticsDTO.setLineId(lineId);
						} else {
							errorStr.append("第" + (i + 4) + "行线路：" + lineName + "不存在<br/>");
							flag = true;
						}
					} else {
						errorStr.append("第" + (i + 4) + "行存在线路没有填写线路信息<br/>");
						flag = true;
					}

					Object mileage = obj.get(properties.get(5));
					if (StringUtil.isNotEmpty(mileage)) {
						mileageStatisticsDTO.setMileage(Double.valueOf(mileage.toString().trim()));
					} else {
						errorStr.append("第" + (i + 4) + "行存在行驶里程数没有填写行驶里程数信息<br/>");
						flag = true;
					}

					Object alarmNum = obj.get(properties.get(6));
					if (StringUtil.isNotEmpty(alarmNum)) {
						mileageStatisticsDTO.setAlarmNum(Integer.valueOf(alarmNum.toString().trim()));
					} else {
						errorStr.append("第" + (i + 4) + "行存在报警数没有填写报警数信息<br/>");
						flag = true;
					}

					if (!flag) {
						// 没有日期，没做重复处理 ----TODO
						MileageStatisticsDTO entity = dao.getByEntity(mileageStatisticsDTO);
						if (StringUtil.isNotEmpty(entity)) {
							repeatCount++;
							repeatMsg.append((i + 4) + ",");
						} else {
							mileageStatisticsDTO.setId(IdGen.uuid());
							mileageStatisticsDTO.setCreateBy(createBy);
							mileageStatisticsDTO.setCreateDate(new Date());
							mileageStatisticsDTO.setCreateIp(createIp);
							dao.insert(mileageStatisticsDTO);
							count++;
						}

					}
				}
			} else {
				errorStr.append("解析数据为空<br/>");
			}
			map.put(DefectConstants.IMPORT_COUNT, count);
			map.put(DefectConstants.IMPORT_ERROR_MSG, errorStr.toString());
			map.put(DefectConstants.IMPORT_REPEAT, repeatCount);
			map.put(DefectConstants.IMPORT_REPEAT_MSG, repeatMsg);
		} catch (Exception e) {
			e.printStackTrace();
			errorStr.append(e.getMessage());
			/// return null;
		}
		return map;
	}
}
