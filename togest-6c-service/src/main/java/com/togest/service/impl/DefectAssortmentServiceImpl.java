package com.togest.service.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.code.client.ImportClient;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectSystem;
import com.togest.dao.DefectAssortmentDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectAssortmentDTO;
import com.togest.domain.DefectTypeDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.EquPosition;
import com.togest.domain.Page;
import com.togest.service.CrudService;
import com.togest.service.DefectAssortmentService;
import com.togest.service.DefectTypeService;
import com.togest.service.DictionaryService;
import com.togest.service.EquPositionService;
import com.togest.util.ExpressionUtil;

@Service
public class DefectAssortmentServiceImpl extends CrudService<DefectAssortmentDao, DefectAssortmentDTO>
		implements DefectAssortmentService {

	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private EquPositionService equPositionService;
	@Autowired
	private ImportClient importService;
	@Autowired
	protected DefectTypeService defectTypeService;

	@Override
	public int deleteFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			return dao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp, new Date());
		}
		return 0;
	}

	@DictAggregation
	public DefectAssortmentDTO get(String id) {

		return super.get(id);
	}

	@DictAggregation
	public List<DefectAssortmentDTO> getBySystemId(String systemId) {
		DefectAssortmentDTO entity = new DefectAssortmentDTO();
		entity.setSystemId(systemId);
		return findList(entity);
	}

	@DictAggregation
	public List<DefectAssortmentDTO> findList(DefectAssortmentDTO entity) {

		return super.findList(entity);
	}

	@DictAggregation
	public Page<DefectAssortmentDTO> findPage(Page<DefectAssortmentDTO> page, DefectAssortmentDTO entity) {

		return super.findPage(page, entity);
	}

	public void handleDefectData(List<Defect> list, List<DefectAssortmentDTO> defectAssortments) {

		if (StringUtil.isNotEmpty(list) && StringUtil.isNotEmpty(defectAssortments)) {
			// 更据缺陷类型分组
			Map<String, List<DefectAssortmentDTO>> maps = defectAssortments.stream()
					.filter(defectAssortment -> StringUtil.isNotBlank(defectAssortment.getDefectTypeId()))
					.collect(Collectors.groupingBy(DefectAssortmentDTO::getDefectTypeId));
			list.forEach(defect -> {
				String defectType = defect.getDefectType();
				if (StringUtil.isNotBlank(defectType)) {
					List<DefectAssortmentDTO> tempList = maps.get(defectType);
					if (StringUtil.isNotEmpty(tempList)) {
						String description = defect.getDescription();
						if (StringUtil.isNotBlank(description)) {
							tempList.forEach(defectAssortment -> {
								if (description.contains(defectAssortment.getDefectDescribe())) {
									// 设置缺陷数据
									setData(defect, defectAssortment);
								}
							});
						}
					}
				}
			});

		}

	}

	public void handleDefectData(List<Defect> list) {
		if (StringUtil.isNotEmpty(list)) {
			String systemId = list.get(0).getSystemId();
			List<DefectAssortmentDTO> defectAssortments = getBySystemId(systemId);
			if (StringUtil.isNotEmpty(defectAssortments)) {
				handleDefectData(list, defectAssortments);
			}

		}
	}

	public void handleC1DefectData(List<Defect1CDTO> list, List<DefectAssortmentDTO> defectAssortments) {

		if (StringUtil.isNotEmpty(list) && StringUtil.isNotEmpty(defectAssortments)) {
			// 更据缺陷类型分组
			Map<String, List<DefectAssortmentDTO>> maps = defectAssortments.stream()
					.filter(defectAssortment -> StringUtil.isNotBlank(defectAssortment.getDefectTypeId()))
					.collect(Collectors.groupingBy(DefectAssortmentDTO::getDefectTypeId));
			list.forEach(defect -> {
				String defectType = defect.getDefectType();
				if (StringUtil.isNotBlank(defectType)) {
					List<DefectAssortmentDTO> tempList = maps.get(defectType);
					if (StringUtil.isNotEmpty(tempList)) {
						// 处理1C不等式判断
						tempList.forEach(defectAssortment -> {
							String expression = defectAssortment.getExpression();
							if (StringUtil.isNotBlank(expression)) {
								String dataVariable = defectAssortment.getDataVariable();
								Double defectValue = defect.getDefectValue();
								String speedType = defect.getSpeedType();
								if (StringUtil.isNotEmpty(defectValue) && StringUtil.isNotBlank(dataVariable)
										&& StringUtil.isNotBlank(speedType)) {
									Double maxSpeed = defectAssortment.getMaxSpeed();
									Double minSpeed = defectAssortment.getMinSpeed();
									Double speed = defect.getSpeed();
									boolean fg = speedType.equals(defectAssortment.getSpeedType()) && ExpressionUtil
											.calculator(expression.replaceAll(dataVariable, defectValue + ""));
									if (fg) {
										if (StringUtil.isNotEmpty(speed) && StringUtil.isNotEmpty(maxSpeed)
												&& StringUtil.isNotEmpty(minSpeed) && maxSpeed != 0.0
												&& minSpeed != 0.0) {
											if (speed >= minSpeed && speed <= maxSpeed) {
												setData(defect, defectAssortment);
											}
										} else if (StringUtil.isNotEmpty(speed) && StringUtil.isNotEmpty(maxSpeed)
												&& maxSpeed != 0.0) {
											if (speed <= maxSpeed) {
												setData(defect, defectAssortment);
											}
										} else if (StringUtil.isNotEmpty(speed) && StringUtil.isNotEmpty(minSpeed)
												&& minSpeed != 0.0) {
											if (speed >= minSpeed) {
												setData(defect, defectAssortment);
											}
										} else {
											setData(defect, defectAssortment);

										}
									}

								}
							} else {
								String description = defect.getDescription();
								if (StringUtil.isNotBlank(description)
										&& description.contains(defectAssortment.getDefectDescribe())) {
									setData(defect, defectAssortment);
								}
							}
						});
					}
				}
			});

		}

	}

	public void setData(Defect defect, DefectAssortmentDTO defectAssortment) {
		// 设置缺陷数据
		defect.setDefectLevel(null);
		defect.setDefectDataCategory(defectAssortment.getDefectDataCategory());
		if(StringUtil.isBlank(defect.getEquPosition())){
			defect.setEquPosition(defectAssortment.getEquName());
			defect.setManualFlag(1);
		}
		defect.setDefectAssortmentId(defectAssortment.getId());
		defect.setDefectDataLevel(defectAssortment.getDefectDataLevel());
	}

	public void handleC1DefectData(List<Defect1CDTO> list) {
		if (StringUtil.isNotEmpty(list)) {
			List<DefectAssortmentDTO> defectAssortments = getBySystemId(DefectSystem.defectC1.getStatus());
			if (StringUtil.isNotEmpty(defectAssortments)) {
				handleC1DefectData(list, defectAssortments);
			}

		}
	}

	@Transactional
	@Override
	public Map<String, Object> importData(String fileName, InputStream inputStream, String sys, String templetId,
			String createBy, String sectionId) {
		Map<String, Object> map = new HashMap<String, Object>();

		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		int count = 0;
		int countAll = 0;
		int countRepeat = 0;
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "busy_defect_data_level", "speed_type" }).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		List<DictionaryItemDTO> dict = tempMap.get("busy_defect_data_level");
		if (StringUtil.isNotEmpty(dict)) {
			Map<String, String> temp = new HashMap<>();
			dict.forEach(action -> temp.put(action.getName(), action.getCode()));
			dictMap.put("busy_defect_data_level", temp);
		}
		List<EquPosition> epList = equPositionService.findAllList();

		Map<String, List<DefectTypeDTO>> defectTypeMap = new HashMap<>();
		try {
			List<Map<String, Object>> result = importService.analyzeExcelData(fileName, inputStream, templetId);
			if (StringUtil.isNotEmpty(result)) {
				List<DefectAssortmentDTO> list = setData(result);
				for (int i = 0; i < list.size(); i++) {
					DefectAssortmentDTO dad = list.get(i);
					boolean fg = false;
					String speedTypeName = dad.getSpeedTypeName();
					if (StringUtil.isNotBlank(speedTypeName)) {
						String speedType = dictMap.get("speed_type").get(speedTypeName);
						if (StringUtil.isNotBlank(speedType)) {
							dad.setSpeedType(speedType);
						} else {
							errorMsg.append("第" + (i + 2) + "行" + "速度等级未找到!<br/>");
							fg = true;
						}

					}

					String systemName = dad.getSystemName();
					if (StringUtil.isBlank(systemName)) {
						errorMsg.append("第" + (i + 2) + "行" + "C类型为空!<br/>");
						fg = true;
					} else {
						Pattern p1 = Pattern.compile("([123456]C)");
						Matcher matcher = p1.matcher(systemName);
						if (!matcher.find()) {
							errorMsg.append("第" + (i + 2) + "行" + "C类型填写有误!<br/>");
							fg = true;
						}
						Pattern p2 = Pattern.compile("([123456])");
						Matcher matcher2 = p2.matcher(systemName);
						String systemId = null;
						if (matcher2.find()) {
							systemId = "C" + matcher2.group();

							dad.setSystemId(systemId);
							if (!defectTypeMap.containsKey(systemId)) {
								List<DefectTypeDTO> defectTypeList = defectTypeService.getListBySystemCode(systemId);
								defectTypeMap.put(systemId, defectTypeList);
							}
						}
					}

					// 设备位置
					String equName = dad.getEquName();
					if (StringUtil.isBlank(equName)) {
						errorMsg.append("第" + (i + 2) + "行" + "设备位置为空!<br/>");
						fg = true;
					} else {
						for (EquPosition ep : epList) {
							if (equName.equals(ep.getName()) && ep.getSystemId() != null
									&& ep.getSystemId().equals(dad.getSystemId())) {
								dad.setEquId(ep.getId());
								break;
							}
						}
						if (StringUtil.isBlank(dad.getEquId())) {
							errorMsg.append("第" + (i + 2) + "行" + "设备位置不存在!<br/>");
							fg = true;
						}
					}

					String defectTypeName = dad.getDefectTypeName();
					if (StringUtil.isBlank(defectTypeName)) {
						errorMsg.append("第" + (i + 2) + "行" + "缺陷类型为空!<br/>");
						fg = true;
					} else {
						String defectTypeId = null;
						List<DefectTypeDTO> defectTypeList = defectTypeMap.get(dad.getSystemId());
						if (defectTypeList != null) {
							for (DefectTypeDTO defectTypeDTO : defectTypeList) {
								if (defectTypeName.equals(defectTypeDTO.getName())) {
									defectTypeId = defectTypeDTO.getId();
									break;
								}
							}
							if (StringUtil.isNotBlank(defectTypeId)) {
								dad.setDefectTypeId(defectTypeId);
							} else {
								errorMsg.append("第" + (i + 2) + "行" + "缺陷类型不存在!<br/>");
								fg = true;
							}
						}
					}

					String defectDescribe = dad.getDefectDescribe();
					if (StringUtil.isBlank(defectDescribe)) {
						errorMsg.append("第" + (i + 2) + "行" + "缺陷描述为空!<br/>");
						fg = true;
					}

					String defectDataLevelName = dad.getDefectDataLevelName();
					if (StringUtil.isBlank(defectDataLevelName)) {
						errorMsg.append("第" + (i + 2) + "行" + "缺陷类别和缺陷数据等级为空!<br/>");
						fg = true;
					} else {
						Pattern pp = Pattern.compile("([ⅠⅡⅢ])");
						Matcher matcher = pp.matcher(defectDataLevelName);
						if (matcher.find()) {
							if (defectDataLevelName.contains("Ⅰ"))
								;
							defectDataLevelName = defectDataLevelName.replace("Ⅰ", "I");

							if (defectDataLevelName.contains("Ⅱ"))
								;
							defectDataLevelName = defectDataLevelName.replace("Ⅱ", "II");

							if (defectDataLevelName.contains("Ⅲ"))
								;
							defectDataLevelName = defectDataLevelName.replace("Ⅲ", "III");
						}

						Pattern p2 = Pattern.compile("([I]+级)");
						Pattern p1 = Pattern.compile("([ABC])");

						String cate = null;
						Matcher matcher1 = p1.matcher(defectDataLevelName);
						if (!matcher1.find()) {
							errorMsg.append("第" + (i + 2) + "行" + "缺陷类别为空!<br/>");
							fg = true;
						} else {
							cate = matcher1.group();
							dad.setDefectDataCategory(cate);
						}
						String level = null;
						Matcher matcher2 = p2.matcher(defectDataLevelName);
						if (!matcher2.find()) {
							errorMsg.append("第" + (i + 2) + "行" + "缺陷数据等级为空!<br/>");
							fg = true;
						} else {
							String str2 = matcher2.group();
							Map<String, String> map2 = dictMap.get("busy_defect_data_level");
							String str = map2.get(str2);
							if (StringUtil.isNotBlank(str)) {
								level = str;
								dad.setDefectDataLevel(level);
							} else {
								errorMsg.append("第" + (i + 2) + "条缺陷等级不存在<br/>");
								fg = true;
							}
						}
					}

					if (!fg) {
						DefectAssortmentDTO entity = checkDefectAssortmenRepeat(dad);
						if (StringUtil.isNotEmpty(entity)) {
							repeatStr.append((i + 2) + ",");

							countRepeat++;
						} else {
							dad.preInsert();
							dao.insert(dad);
							count++;
						}
					}
					countAll++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		map.put("count", count);
		map.put("countAll", countAll);
		map.put("errorMsg", errorMsg.toString());
		map.put("countRepeat", countRepeat);
		map.put("repeatMsg", repeatStr.toString());
		return map;
	}

	public List<DefectAssortmentDTO> setData(List<Map<String, Object>> list) {
		List<DefectAssortmentDTO> lists = ListUtils.newArrayList();
		list.forEach(l -> {
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			DefectAssortmentDTO dad = new DefectAssortmentDTO();
			Map<String, Object> obj = l;
			try {
				BeanUtils.populate(dad, obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lists.add(dad);

		});
		return lists;
	}

	public DefectAssortmentDTO checkDefectAssortmenRepeat(DefectAssortmentDTO entity) {
		DefectAssortmentDTO dad = new DefectAssortmentDTO();

		dad.setDefectTypeId(entity.getDefectTypeId());
		dad.setDefectDescribe(entity.getDefectDescribe());
		dad.setDefectDataLevel(entity.getDefectDataLevel());
		dad.setDefectDataCategory(entity.getDefectDataCategory());
		dad.setSystemId(entity.getSystemId());
		if (StringUtil.isNotBlank(entity.getSpeedType())) {
			dad.setSpeedType(entity.getSpeedType());
		}
		return dao.getByEntity(dad);
	}
}
