package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.response.Pillar;
import com.togest.client.resquest.ProcessInstanceDelRequest;
import com.togest.code.client.ExportClient;
import com.togest.code.client.ImportClient;
import com.togest.code.client.ImportConfigClient;
import com.togest.common.annotation.Shift;
import com.togest.common.util.DateUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.ReflectHelper;
import com.togest.common.util.file.FileUtil;
import com.togest.common.util.file.ImageUtil;
import com.togest.common.util.file.StreamUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.common.util.xls.MyPictureData;
import com.togest.config.BussinessType;
import com.togest.config.DataStatus;
import com.togest.config.DefectConstants;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.config.GroupWares;
import com.togest.dao.C2InfoDao;
import com.togest.dao.C4InfoDao;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectHandleInfoDao;
import com.togest.dao.DefectHistoryDao;
import com.togest.dao.DefectRepeatCountDao;
import com.togest.dao.DefectRepeatHistoryDao;
import com.togest.dao.PillarDao;
import com.togest.dao.PlanDetailDao;
import com.togest.dao.SixCDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.BaseEntity;
import com.togest.domain.CheckTrainDTO;
import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectAssortmentDTO;
import com.togest.domain.DefectFlow;
import com.togest.domain.DefectHandleInfo;
import com.togest.domain.DefectHistory;
import com.togest.domain.DefectRepeatCount;
import com.togest.domain.DefectRepeatHistory;
import com.togest.domain.DefectTypeDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.FileBlobDTO;
import com.togest.domain.LinePsaName;
import com.togest.domain.Page;
import com.togest.domain.PlanDetailDTO;
import com.togest.domain.SimplePage;
import com.togest.file.client.FileClient;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectRepeatRequest;
import com.togest.response.DefectForm;
import com.togest.response.DefectPackageResponse;
import com.togest.response.ParseDataResponse;
import com.togest.response.ParseDataResponse.PropertyData;
import com.togest.response.ParseDataResponse.RowData;
import com.togest.scheduled.service.DefectRepeatTaskEvent;
import com.togest.service.CheckTrainService;
import com.togest.service.DefectAssortmentService;
import com.togest.service.DefectFlowService;
import com.togest.service.DefectSysncQuestionService;
import com.togest.service.DefectTypeService;
import com.togest.service.DictionaryService;
import com.togest.service.FileHandleService;
import com.togest.service.LineService;
import com.togest.service.MetadataService;
import com.togest.service.SixCService;
import com.togest.service.SupplyArmService;
import com.togest.service.TaskPublisherService;
import com.togest.utils.PageUtils;

public class SixCServiceImpl<D extends SixCDao<T, K, H>, T extends Defect, K extends DefectPackageResponse<T>, H extends DefectForm<T>>
		implements SixCService<T, K, H> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SixCServiceImpl.class);

	@Autowired
	protected D dao;
	@Autowired
	private DefectDao defectDao;
	@Autowired
	private DefectRepeatCountDao defectRepeatCountDao;
	@Autowired
	private DefectHistoryDao defectHistoryDao;
	@Autowired
	private DefectRepeatHistoryDao defectRepeatHistoryDao;
	@Autowired
	private C2InfoDao c2InfoDao;
	@Autowired
	private C4InfoDao c4InfoDao;
	@Autowired
	private DefectFlowService defectFlowService;
	@Autowired
	private FileClient fileSystemService;
	@Autowired
	private FileHandleService fileHandleService;
	@Autowired
	private ExportClient exportService;
	@Autowired
	private ImportClient importService;
	@Autowired
	private ImportConfigClient importConfigClient;
	@Autowired
	private WorkflowFeignService workflowFeignService;
	@Autowired
	private DeptFeignService deptFeignService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private LineService lineService;
	@Autowired
	private SupplyArmService supplyArmService;
	@Autowired
	protected DefectTypeService defectTypeService;
	@Autowired
	private PillarDao pillarDao;
	@Autowired
	private CheckTrainService checkTrainService;
	@Autowired
	private PlanDetailDao planDetailDao;
	@Autowired
	private TaskPublisherService taskPublisherService;
	// @Autowired
	// private PillarFeginService pillarFeginService;
	@Autowired
	private DefectHandleInfoDao defectHandleInfoDao;
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private DefectAssortmentService defectAssortmentService;
	@Autowired
	private DefectSysncQuestionService defectSysncQuestionService;

	@Value("${my.defect.handleInfo}")
	private Boolean handleInfo = false;
	@Value("${my.defect.isDefectAssortment}")
	private Boolean isDefectAssortment = false;
	@Value("${my.repeatDefect.isSuiDe}")
	private Boolean isSuiDe = false;

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String SPEEDTYPE = "speedType";
	private static final String PARENTID = "parentId";
	private static final String TUNNELNAME = "tunnelName";
	private static final String DICT_PRE = "busy_";
	private static final String EQU_NAME = "defect_2c_equ_name";
	private static final String PILLARNAME = "pillarName";
	private static final Integer ISCONFIRMED = 1;
	private static final Double GLB_DISTANCE = 0.02;

	static {
		TaskPublisherServiceImpl.register(BussinessType.DefectTask.getStatus(), new DefectRepeatTaskEvent(null));

	}

	@Override
	public Map<String, Object> pareseData(String originalFilename, InputStream inputStream, String templetId,
			String createBy, String sectionId, String systemId, Class<? extends Defect> clazz, String infoId) {
		long t = System.currentTimeMillis();
		String planId = null;
		if (StringUtil.isNotBlank(infoId)) {
			if (DefectSystem.defectC2.getStatus().equals(systemId)) {
				c2InfoDao.updateDefectStatus(infoId, DataStatus.AlreadyUploaded.getStatus());
			} else if (DefectSystem.defectC4.getStatus().equals(systemId)) {
				c4InfoDao.updateDefectStatus(infoId, DataStatus.AlreadyUploaded.getStatus());
			}
			PlanDetailDTO planDetail = planDetailDao.getByKey(infoId);
			if (planDetail != null) {
				planId = planDetail.getPlanBaseId();
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int index = 0;
		int repeat = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		ParseDataResponse pareseData = null;
		Map<String, List<DictionaryItemDTO>> tempMap = new HashMap<String, List<DictionaryItemDTO>>();
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, Map<String, String>> lineMap = new HashMap<String, Map<String, String>>();
		Map<String, String> psaMap = new HashMap<String, String>();
		Map<String, String> checkTrainMap = new HashMap<String, String>();
		Map<String, Map<String, String>> workAreaMap = new HashMap<String, Map<String, String>>();
		// Map<String, String> workShopMap = new HashMap<String, String>();
		List<DefectAssortmentDTO> defectAssortments = defectAssortmentService.getBySystemId(systemId);
		List<DefectTypeDTO> defectTypeList = defectTypeService.getListBySystemCode(systemId);
		CheckTrainDTO checkTrainDTO = new CheckTrainDTO();
		checkTrainDTO.setSystemId(systemId);
		List<CheckTrainDTO> checkTrainList = checkTrainService.findList(checkTrainDTO);
		if (StringUtil.isNotEmpty(checkTrainList)) {
			for (CheckTrainDTO checkTrain : checkTrainList) {
				checkTrainMap.put(checkTrain.getTrainNum(), checkTrain.getId());
			}
		}
		List<Defect> defectList = new ArrayList<>();
		List<StringBuilder> errorMsgList = new ArrayList<>();
		List<Boolean> errorFlagList = new ArrayList<>();
		boolean c1c3Flag = DefectSystem.defectC1.getStatus().equals(systemId)
				|| DefectSystem.defectC3.getStatus().equals(systemId);
		try {
			byte[] bt = StreamUtils.InputStreamTOByte(inputStream);

			pareseData = importService.pareseData(originalFilename, StreamUtils.byteTOInputStream(bt), templetId, 0);
			errorMsg.append(pareseData.getMsg());
			List<RowData> rowDataList = pareseData.getRowDataList();

			List<String> dictList = new ArrayList<String>();
			List<String> lineList = new ArrayList<String>();
			List<String> psaList = new ArrayList<String>();
			List<String> deptList = new ArrayList<String>();
			List<Pillar> pillarList = new ArrayList<Pillar>();
			List<Integer> indexList = new ArrayList<Integer>();
			List<Pillar> pillarData = new ArrayList<Pillar>();
			List<LinePsaName> linePsaList = new ArrayList<LinePsaName>();
			List<FileBlobDTO> fileBlobList = new ArrayList<FileBlobDTO>();
			// 导入数据上传到文件服务器中
			// FileBlobDTO f = new FileBlobDTO();
			// f.setRealName(originalFilename);
			// f.setFileExt(
			// FileUtil.getFileExtByFileName(f.getRealName()));
			// f.setFileName(
			// StringUtil.getPrimaryKey() + "." + f.getFileExt());
			// fileHandleService.upload(f,
			// StreamUtils.byteTOInputStream(bt));
			// f.preInsert();
			// fileBlobList.add(f);
			//
			// Metadata metadata = new Metadata();
			// metadata.setCode(MetadataType.DefectData.getStatus());
			// metadata.setType(MetadataType.DefectData.getStatus());
			// metadata.setSystemId(systemId);
			// metadata.setUploader(createBy);
			// metadata.setUploadDate(new Date());
			// metadata.setFileId(f.getId());
			//
			// metadataService.save(metadata);

			// List<FileBatchData> fileBatchDataList = new
			// ArrayList<FileBatchData>();
			LOGGER.error("缺陷数据总数量 --- " + rowDataList.size());
			LOGGER.error("上传人ID --- " + createBy);
			long s0 = System.currentTimeMillis();
			Map<String, List<String>> photoMaps = new HashMap<String, List<String>>();
			for (int i = 0; i < rowDataList.size(); i++) {// 处理组件
				RowData rowData = rowDataList.get(i);
				LinePsaName linePsa = new LinePsaName();
				List<PropertyData> propertyDataList = rowData.getPropertyDataList();
				for (int j = 0; j < propertyDataList.size(); j++) {
					PropertyData propertyData = propertyDataList.get(j);
					String groupWare = propertyData.getGroupWare();
					String dictName = propertyData.getDictName();
					Object data = propertyData.getData();
					String mappingName = propertyData.getMappingName();
					if (GroupWares.DICT.equals(groupWare)) {
						if (StringUtil.isNotBlank(dictName) && (!dictList.contains(dictName))) {
							dictList.add(dictName);
						}
					}
					// 数据为空时继续执行
					if (StringUtil.isEmpty(data)) {
						continue;
					}
					if (StringUtil.isNotBlank(groupWare)
							&& StringUtil.toUpperCase(groupWare).contains(StringUtil.toUpperCase(GroupWares.LINE))) {
						linePsa.setLineName(data.toString());
						if (!lineList.contains(data.toString()) && StringUtil.isNotEmpty(data)) {
							lineList.add(data.toString());
						}
					}
					if (StringUtil.isNotBlank(groupWare)
							&& StringUtil.toUpperCase(groupWare).contains(StringUtil.toUpperCase(GroupWares.STATION))) {
						linePsa.setPsaName(data.toString());
						if (!psaList.contains(data.toString()) && StringUtil.isNotEmpty(data)) {
							psaList.add(data.toString());
						}
					}
					if (StringUtil.isNotBlank(groupWare)
							&& StringUtil.toUpperCase(groupWare).contains(StringUtil.toUpperCase(GroupWares.DEPT))) {
						if (!deptList.contains(data.toString()) && StringUtil.isNotEmpty(data)) {
							deptList.add(data.toString());
						}
					}

				}
				linePsaList.add(linePsa);
			}
			long s1 = System.currentTimeMillis();
			LOGGER.error("处理组件用时 --- " + (s1 - s0));
			if (dictList.size() > 0) {// 查字典
				String[] dictStr = new String[dictList.size()];
				dictList.toArray(dictStr);
				RestfulResponse<Map<String, List<DictionaryItemDTO>>> findDictItemsByDictName = dictionaryService
						.findDictItemsByDictName(dictStr);
				if (findDictItemsByDictName != null) {
					tempMap = findDictItemsByDictName.getData();
					dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
					for (String key : tempMap.keySet()) {
						List<DictionaryItemDTO> tempDicts = tempMap.get(key);
						if ((EQU_NAME.equals(key) || key.startsWith(DICT_PRE)) && StringUtil.isNotEmpty(tempDicts)) {
							Map<String, String> m = new HashMap<String, String>();
							tempDicts.forEach(action -> m.put(action.getName(), action.getCode()));
							dictMap.put(key, m);
						}
					}
				}
			}
			if (lineList.size() > 0) {// 查线路
				StringBuilder names = new StringBuilder();
				for (String lineName : lineList) {
					names.append(lineName).append(",");
				}
				List<Map<String, String>> lineIdsNames = lineService.getIdsByNames(names.toString());
				if (StringUtil.isNotEmpty(lineIdsNames)) {
					for (Map<String, String> line : lineIdsNames) {
						lineMap.put(line.get(NAME), line);
					}
				}
			}
			if (psaList.size() > 0) {// 查站区
				StringBuilder names = new StringBuilder();
				for (String psaName : psaList) {
					names.append(psaName).append(",");
				}
				List<Map<String, String>> psaIdsNames = supplyArmService.getIdsByNames(names.toString());
				if (StringUtil.isNotEmpty(psaIdsNames)) {
					for (Map<String, String> psa : psaIdsNames) {
						psaMap.put(psa.get(NAME), psa.get(ID));
					}
				}
			}
			List<Map<String, String>> psaIdList = lineService.findByLinePsa(linePsaList);
			if (deptList.size() > 0) {// 查部门
				StringBuilder names = new StringBuilder();
				// StringBuilder workAreaIds = new StringBuilder();
				for (String deptName : deptList) {
					names.append(deptName).append(",");
				}
				RestfulResponse<List<Map<String, String>>> idByName = deptFeignService.getIdByName(names.toString(),
						null);
				if (idByName != null) {
					List<Map<String, String>> deptIdsNames = idByName.getData();
					if (StringUtil.isNotEmpty(deptIdsNames)) {
						for (Map<String, String> dept : deptIdsNames) {// 工区
							workAreaMap.put(dept.get(NAME), dept);
							// workAreaIds.append(dept.get(ID)).append(",");
						}
					}
				}

				// RestfulResponse<List<Map<String, String>>> parentDepts =
				// deptFeignService
				// .getParentDepts(workAreaIds.toString());
				// if (parentDepts != null) {
				// List<Map<String, String>> parentIds = parentDepts.getData();
				// if (StringUtil.isNotEmpty(parentIds)) {
				// for (Map<String, String> parent : parentIds) {// 车间
				// workShopMap.put(parent.get(ID),
				// parent.get("parentId"));
				// }
				// }
				// }
			}
			long s2 = System.currentTimeMillis();
			LOGGER.error("服务查询数据用时 --- " + (s2 - s1));
			// //////////////////////////////////////////
			for (int i = 0; i < rowDataList.size(); i++) {// 一行数据
				index = i + 2;
				boolean errorFlag = false;
				StringBuilder errorStr = new StringBuilder();
				Defect entity = clazz.newInstance();
				RowData rowData = rowDataList.get(i);
				List<PropertyData> propertyDataList = rowData.getPropertyDataList();
				for (int j = 0; j < propertyDataList.size(); j++) {
					PropertyData propertyData = propertyDataList.get(j);
					String customName = propertyData.getCustomName(); // 名称
					String mappingName = propertyData.getMappingName();
					String groupWare = propertyData.getGroupWare();
					String dictName = propertyData.getDictName();
					Integer requireTag = propertyData.getRequireTag();
					Object data = propertyData.getData();
					Field field = ReflectHelper.getAccessibleField(entity, mappingName);
					if (requireTag == 1 && StringUtil.isEmpty(data)) {
						errorStr.append("第" + index + "行没有填写" + customName + ", 此项数据当前配置为必填<br/>");
						errorFlag = true;
						// errorFlagList.set(i, errorFlag);
					} else if (StringUtil.isNotEmpty(data)) {

						if (GroupWares.DATE.equals(groupWare)) {// 日期
							field.set(entity, data);
						}

						if (GroupWares.DICT.equals(groupWare) && !("defect_type".equals(propertyData.getFieldName()))) {// 字典
							String dictId = null;
							Map<String, String> dict = dictMap.get(dictName);
							if (StringUtil.isNotEmpty(dict)) {
								dictId = dict.get(data.toString());
								if (StringUtil.isNotBlank(dictId)) {
									field.set(entity, dictId);
								} else {
									errorStr.append("第" + index + "行填写的" + customName + "不存在<br/>");
									errorFlag = true;
									// errorFlagList.set(i, errorFlag);
								}
							} else {
								errorStr.append("第" + index + "行填写的" + customName + "无法获取数据字典信息<br/>");
								errorFlag = true;
								// errorFlagList.set(i, errorFlag);
							}
						}

						if (StringUtil.isNotBlank(groupWare) && StringUtil.toUpperCase(groupWare)
								.contains(StringUtil.toUpperCase(GroupWares.LINE))) {// 线路
							Map<String, String> tempLine = lineMap.get(data.toString());
							if (StringUtil.isNotEmpty(tempLine)) {
								field.set(entity, tempLine.get(ID));
								entity.setSpeedType(tempLine.get(SPEEDTYPE));
								// ReflectHelper.setFieldValue(entity,
								// SPEEDTYPE,
								// tempLine.get(SPEEDTYPE));

							} else {
								errorStr.append("第" + index + "行填写的" + customName + "不存在<br/>");
								errorFlag = true;
								// errorFlagList.set(i, errorFlag);
							}

						}

						if (StringUtil.isNotBlank(groupWare) && StringUtil.toUpperCase(groupWare)
								.contains(StringUtil.toUpperCase(GroupWares.STATION))) {// 站区
							Map<String, String> psaIdMap = psaIdList.get(i);
							String psaId = psaIdMap.get(ID);
							if (StringUtil.isNotBlank(psaId)) {
								field.set(entity, psaId);
							} else {

								if (!DefectSystem.defectC3.getStatus().equals(systemId)) {
									errorStr.append("第" + index + "行填写线路下找不到站区：" + data.toString() + "<br/>");
									errorFlag = true;
								}
								// errorFlag = true;
								// errorFlagList.set(i, errorFlag);
							}
						}
						if (GroupWares.TRAIN.equals(groupWare)) {// 车辆编号
							String trainId = checkTrainMap.get(data.toString().trim());
							if (StringUtil.isNotBlank(trainId)) {
								field.set(entity, trainId);
							} else {
								errorStr.append("第" + index + "行填写的" + data.toString() + "未找到<br/>");
								errorFlag = true;
							}
						}

						if (StringUtil.isNotBlank(groupWare) && StringUtil.toUpperCase(groupWare)
								.contains(StringUtil.toUpperCase(GroupWares.DEPT))) {// 部门
							Map<String, String> tempWorkAreaMap = workAreaMap.get(data.toString());
							if (StringUtil.isNotEmpty(tempWorkAreaMap)) {
								field.set(entity, tempWorkAreaMap.get(ID));
								ReflectHelper.setFieldValue(entity, "workShopId", tempWorkAreaMap.get(PARENTID));
							} else {
								errorStr.append("第" + index + "行填写的" + customName + "不存在<br/>");
								errorFlag = true;
								// errorFlagList.set(i, errorFlag);
							}
						}
						if (StringUtil.isNotBlank(groupWare) && StringUtil.toUpperCase(groupWare)
								.contains(StringUtil.toUpperCase(GroupWares.TUNNEL))) {
							ReflectHelper.setFieldValue(entity, TUNNELNAME, data.toString());
						}
						if (GroupWares.TEXT.equals(groupWare) || GroupWares.NUMBER.equals(groupWare)
								|| groupWare.startsWith(GroupWares.PILLAR)) {
							field.set(entity, data);
						}

						if (!errorFlag && GroupWares.PHOTO.equals(groupWare)) {// 图片

							if (StringUtil.isNotEmpty(data)) {
								if (data instanceof List) {
									List<MyPictureData> pics = (List<MyPictureData>) data;
									if (StringUtil.isNotEmpty(pics)) {
										List<String> fileIds = new ArrayList<String>();
										for (MyPictureData myPictureData : pics) {
											FileBlobDTO fileBlobDTO = new FileBlobDTO();
											if (StringUtil.isBlank(myPictureData.getFileName())) {
												fileBlobDTO.setRealName("photo.png");
											} else {
												fileBlobDTO.setRealName(myPictureData.getFileName());
											}
											fileBlobDTO.setFileExt(
													FileUtil.getFileExtByFileName(fileBlobDTO.getRealName()));
											fileBlobDTO.setFileName(
													StringUtil.getPrimaryKey() + "." + fileBlobDTO.getFileExt());
											fileHandleService.upload(fileBlobDTO, ImageUtil
													.compressPictureByQality(myPictureData.getPictureData(), 0.5f));
											fileBlobDTO.preInsert();
											fileIds.add(fileBlobDTO.getId());
											fileBlobList.add(fileBlobDTO);
										}

										photoMaps.put(mappingName + i, fileIds);
										// FileBatchData fileBatchData = new
										// FileBatchData();
										// List<FileDataDTO> fileDataList = new
										// ArrayList<FileDataDTO>();
										// for (MyPictureData myPictureData :
										// pics) {
										// FileDataDTO fileDataDTO = new
										// FileDataDTO();
										// fileDataDTO.setData(myPictureData.getPictureData());
										// if
										// (StringUtil.isBlank(myPictureData.getFileName()))
										// {
										// fileDataDTO.setRealName("photo.png");
										// } else {
										// fileDataDTO.setRealName(myPictureData.getFileName());
										// }
										// fileDataList.add(fileDataDTO);
										// }
										// fileBatchData.setKey(mappingName +
										// i);
										// fileBatchData.setFileDatas(fileDataList);
										// fileBatchDataList.add(fileBatchData);
									}
								}
							}

						}
					}

				}
				defectList.add(entity);
				errorFlagList.add(errorFlag);
				errorMsgList.add(errorStr);
			}

			// 处理图片数据
			// if (StringUtil.isNotEmpty(fileBatchDataList)) {
			// RestfulResponse<Map<String, List<String>>> fileDataList =
			// fileSystemService
			// .uploadByClient(fileBatchDataList);
			// if (StringUtil.isNotEmpty(fileDataList)) {
			// photoMaps = fileDataList.getData();
			// }
			// }
			if (StringUtil.isNotEmpty(fileBlobList)) {
				fileSystemService.inserts(fileBlobList);
			}
			// ///////////////////////////////////////////////////////

			for (int i = 0; i < rowDataList.size(); i++) {// 处理业务
				RowData rowData = rowDataList.get(i);
				List<PropertyData> propertyDataList = rowData.getPropertyDataList();
				Defect entity = defectList.get(i);
				StringBuilder errorStr = errorMsgList.get(i);
				Pillar query = new Pillar();
				String checkDate = null;
				String lineName = null;
				String psaName = null;
				String directionName = null;
				Boolean errorFlag = errorFlagList.get(i);
				for (int j = 0; j < propertyDataList.size(); j++) {
					PropertyData propertyData = propertyDataList.get(j);
					String fieldName = propertyData.getFieldName();
					String customName = propertyData.getCustomName(); // 名称
					String displayName = propertyData.getDisplayName();
					String groupWare = propertyData.getGroupWare();
					String mappingName = propertyData.getMappingName();
					Integer requireTag = propertyData.getRequireTag();
					Object data = propertyData.getData();
					if (!(requireTag == 1 && StringUtil.isEmpty(data))) {
						if (GroupWares.DATE.equals(groupWare)) {// 日期
							if (StringUtil.isNotEmpty(entity.getCheckDate())) {
								checkDate = new SimpleDateFormat("yyyy-MM-dd").format(entity.getCheckDate());
							}
						}
						if (StringUtil.isNotBlank(groupWare) && StringUtil.toUpperCase(groupWare)
								.contains(StringUtil.toUpperCase(GroupWares.LINE))) {// 线路
							lineName = data.toString();
						}
						if (StringUtil.isNotBlank(groupWare) && StringUtil.toUpperCase(groupWare)
								.contains(StringUtil.toUpperCase(GroupWares.STATION))) {// 站区
							psaName = data.toString();
						}
						if (GroupWares.DICT.equals(groupWare)) {// 字典
							if ("directionName".equals(displayName)) {
								directionName = data.toString();
							}
							String dictId = null;
							if ("defect_level".equals(fieldName)) {
								List<DictionaryItemDTO> list = tempMap.get("defect_grade");
								if (list != null) {
									for (DictionaryItemDTO dictionaryItemDTO : list) {
										if (data.toString().equals(dictionaryItemDTO.getName())) {
											dictId = dictionaryItemDTO.getCode();
											break;
										}
									}
									if (StringUtil.isNotBlank(dictId)) {
										entity.setDefectLevel(dictId);
									} else {
										errorStr.append("第" + (i + 2) + "行填写的" + customName + "不存在<br/>");
										errorFlag = true;
										errorFlagList.set(i, errorFlag);
									}
								} else {
									errorStr.append("第" + (i + 2) + "行填写的" + customName + "无法获取数据字典信息<br/>");
									errorFlag = true;
									errorFlagList.set(i, errorFlag);
								}
							}
						}

						if (GroupWares.TEXT.equals(groupWare) || StringUtil.isNotBlank(groupWare) && StringUtil
								.toUpperCase(groupWare).contains(StringUtil.toUpperCase(GroupWares.DEFECTTYPE))) {// text
							if ("defect_type".equals(fieldName)) {
								String defectType = null;
								if (defectTypeList != null) {
									for (DefectTypeDTO defectTypeDTO : defectTypeList) {
										if (data.toString().equals(defectTypeDTO.getName())) {
											defectType = defectTypeDTO.getId();
											break;
										}
									}
									if (StringUtil.isNotBlank(defectType)) {
										entity.setDefectType(defectType);
									} else {
										errorStr.append("第" + (i + 2) + "行填写的" + customName + "不存在<br/>");
										errorFlag = true;
										errorFlagList.set(i, errorFlag);
									}
								} else {
									errorStr.append("第" + (i + 2) + "行填写的" + customName + "无法获取信息<br/>");
									errorFlag = true;
									errorFlagList.set(i, errorFlag);
								}
							}
							if ("defect_type_detail".equals(fieldName)) {
								String defectTypeDetail = null;
								if (defectTypeList != null) {
									for (DefectTypeDTO defectTypeDTO : defectTypeList) {
										if (data.toString().equals(defectTypeDTO.getName())) {
											defectTypeDetail = defectTypeDTO.getId();
											break;
										}
									}
									if (StringUtil.isNotBlank(defectTypeDetail)) {
										entity.setDefectTypeDetail(defectTypeDetail);
									} else {
										errorStr.append("第" + (i + 2) + "行填写的" + customName + "不存在<br/>");
										errorFlag = true;
										errorFlagList.set(i, errorFlag);
									}
								} else {
									errorStr.append("第" + (i + 2) + "行填写的" + customName + "无法获取信息<br/>");
									errorFlag = true;
									errorFlagList.set(i, errorFlag);
								}
							}
						}
						if (GroupWares.PHOTO.equals(groupWare)) {
							List<String> picIds = photoMaps.get(mappingName + i);
							if (StringUtil.isNotEmpty(picIds)) {
								ReflectHelper.setFieldValue(entity, mappingName,
										StringUtil.collectionToDelimitedString(picIds, ","));
							}
						}
					}
					errorFlagList.set(i, errorFlag);
				}
				if (StringUtil.isNotBlank(psaName)) {
					entity.setDefectName(lineName + " " + psaName + " " + directionName + " " + checkDate);
				} else {
					entity.setDefectName(lineName + " " + directionName + " " + checkDate);
				}
				Double defectGlb = entity.getDefectGlb();
				String name = entity.getPillarId();
				String lineId = entity.getLineId();
				String direction = entity.getDirection();
				String psaId = entity.getPsaId();
				Object tunnelName = PropertyUtils.getProperty(entity, TUNNELNAME);
				if (c1c3Flag && StringUtil.isEmpty(defectGlb)) {
					// 公里标为空
				} else if (lineId != null && direction != null
						&& (psaId != null || c1c3Flag)) {
					query.setPlId(lineId);
					query.setDirection(direction);
					query.setPsaId(psaId);
					if (StringUtil.isNotEmpty(tunnelName)) {
						query.setTunnelName(tunnelName.toString());
					}
					if (c1c3Flag) {
						query.setStartKm(defectGlb);
					} else {
						query.setName(name);
					}
					if (c1c3Flag) {
						query.setPsaId(null);
					}
					pillarList.add(query);
					indexList.add(i);
				} else {
					// errorStr.append("第" + (i + 2) + "行存在有误信息无法关联支柱<br/>");
					errorFlag = true;
					errorFlagList.set(i, errorFlag);
				}
			}
			long s3 = System.currentTimeMillis();
			LOGGER.error("处理数据和上传图片用时 --- " + (s3 - s2));

			Map<Integer, Pillar> pillarMap = new HashMap<Integer, Pillar>();
			Map<String, List<Pillar>> pillarListMaps = new HashMap<String, List<Pillar>>();
			if (StringUtil.isNotEmpty(pillarList)) {
				if (c1c3Flag) {
					// for (int i = 0; i < pillarList.size(); i++) {
					// Pillar pillar =
					// pillarDao.findPillarByGlb(pillarList.get(i));
					// pillarMap.put(indexList.get(i), pillar);
					// }
					pillarMap = handlePillar(indexList, pillarList, pillarMap);
					// pillarData = pillarDao.findPillarByGlb(pillarList);
					// RestfulResponse<List<Pillar>> findPillarByGlb =
					// pillarFeginService.findPillarByGlb(pillarList);
					// if (findPillarByGlb != null) {
					// pillarData = findPillarByGlb.getData();
					// }
				} else {

					pillarData = pillarDao.findPillarByName(pillarList);
					// RestfulResponse<List<Pillar>> findPillarByName =
					// pillarFeginService.findPillarByName(pillarList);
					// if (findPillarByName != null) {
					// pillarData = findPillarByName.getData();
					// }
				}
				if (StringUtil.isNotEmpty(pillarData)) {
					for (Pillar pillar : pillarData) {
						if (StringUtil.isNotEmpty(pillar)) {
							String key = pillar.getPlId() + pillar.getPsaId() + pillar.getDirection()
									+ pillar.getTunnelName() + pillar.getName();
							if (!pillarListMaps.containsKey(key)) {
								pillarListMaps.put(key, new ArrayList<Pillar>());
							}
							List<Pillar> pillars = pillarListMaps.get(key);
							if (StringUtil.isNotEmpty(pillars)) {
								boolean hh = true;
								for (Pillar tem : pillars) {
									if (pillar.getId().equals(tem.getId())) {
										hh = false;
										break;
									}
								}
								if (hh) {
									pillarListMaps.get(key).add(pillar);
								}
							} else {
								pillarListMaps.get(key).add(pillar);
							}

						}
					}
					// if(StringUtil.isNotEmpty(indexList)&&pillarData.size()==indexList.size()){
					// for (int i = 0; i < pillarData.size(); i++) {
					// pillarMap.put(indexList.get(i), pillarData.get(i));
					// }
					// }
				}
			}
			long s4 = System.currentTimeMillis();
			LOGGER.error("查询支柱数据用时 --- " + (s4 - s3));
			for (int i = 0; i < rowDataList.size(); i++) {
				StringBuilder errorStr = errorMsgList.get(i);
				Defect entity = defectList.get(i);
				Boolean errorFlag = errorFlagList.get(i);

				boolean ff = false;
				Pillar pillar = null;
				if (StringUtil.isNotEmpty(pillarMap) && !pillarMap.isEmpty()) {
					pillar = pillarMap.get(i);
				} else if (StringUtil.isNotEmpty(pillarListMaps)) {
					String key = entity.getLineId() + entity.getPsaId() + entity.getDirection()
							+ PropertyUtils.getProperty(entity, TUNNELNAME) + entity.getPillarId();
					// System.out.println(key);
					List<Pillar> tempLists = pillarListMaps.get(key);
					if (StringUtil.isNotEmpty(tempLists)) {
						pillar = tempLists.get(0);
						// if (tempLists.size() == 1) {
						// pillar = tempLists.get(0);
						// } else {
						// errorStr.append("第" + (i + 2) +
						// "行通过填写信息找到支柱数据存在重复<br/>");
						// errorFlag = true;
						// ff = true;
						// errorFlagList.set(i, errorFlag);
						// }
					}

				} else {
					errorStr.append("第" + (i + 2) + "行通过填写信息无法找到支柱<br/>");
					errorFlag = true;
					ff = true;
					errorFlagList.set(i, errorFlag);
				}

				if (StringUtil.isNotEmpty(pillar)) {
					if (c1c3Flag) {
						entity.setPillarId(pillar.getId());
					} else {
						entity.setPillarId(pillar.getId());
						if (!DefectSystem.defectC3.getStatus().equals(systemId)) {
							entity.setDefectGlb(pillar.getStartKm());
						}
					}
					if (DefectSystem.defectC3.getStatus().equals(systemId)) {
						entity.setPsaId(pillar.getPsaId());
						// entity.setWorkAreaId(pillar.getDeptId());
					}
					String defectName = entity.getDefectName();
					if (StringUtil.isNotBlank(pillar.getName())) {
						defectName = entity.getDefectName() + " (" + pillar.getName() + "支柱) "
								+ getKGlb(entity.getDefectGlb());
					} else {
						defectName = entity.getDefectName() + getKGlb(entity.getDefectGlb());
					}

					if (StringUtil.isBlank(entity.getWorkAreaId())) {
						entity.setWorkAreaId(pillar.getDeptId());
					}
					if (StringUtil.isNotBlank(entity.getWorkAreaId())
							&& !entity.getWorkAreaId().equals(pillar.getDeptId())) {
						errorStr.append("第" + (i + 2) + "行填写的工区数据找到支柱上的工区不一致<br/>");
						errorFlag = true;
						errorFlagList.set(i, errorFlag);
					}
					PropertyUtils.setProperty(entity, PILLARNAME, pillar.getName());
					entity.setLongitude(pillar.getLongitude());
					entity.setLatitude(pillar.getLatitude());
					entity.setSectionId(pillar.getSectionId());
					entity.setTunnelId(pillar.getTunnelId());
					entity.setDefectName(defectName);
				} else {
					if (!ff) {
						errorStr.append("第" + (i + 2) + "行通过填写信息无法找到支柱<br/>");
						errorFlag = true;
						errorFlagList.set(i, errorFlag);
					}

				}

				// if (pillarMap.containsKey(i)) {
				// pillar = pillarMap.get(i);
				// if (StringUtil.isNotEmpty(pillar)) {
				// if (c1c3Flag) {
				// entity.setPillarId(pillar.getId());
				// } else {
				// entity.setPillarId(pillar.getId());
				// if (!DefectSystem.defectC3.getStatus().equals(systemId)) {
				// entity.setDefectGlb(pillar.getStartKm());
				// }
				// }
				// if (DefectSystem.defectC3.getStatus().equals(systemId)) {
				// entity.setPsaId(pillar.getPsaId());
				// entity.setSectionId(pillar.getSectionId());
				// entity.setWorkAreaId(pillar.getDeptId());
				// }
				// String defectName = entity.getDefectName();
				// if (StringUtil.isNotBlank(pillar.getName())) {
				// defectName = entity.getDefectName() + " (" + pillar.getName()
				// + "支柱) "
				// + getKGlb(entity.getDefectGlb());
				// } else {
				// defectName = entity.getDefectName() +
				// getKGlb(entity.getDefectGlb());
				// }
				// entity.setTunnelId(pillar.getTunnelId());
				// entity.setDefectName(defectName);
				// } else {
				// errorStr.append("第" + (i + 2) + "行通过填写信息无法找到支柱<br/>");
				// errorFlag = true;
				// errorFlagList.set(i, errorFlag);
				// }
				// } else {
				// errorStr.append("第" + (i + 2) +
				// "行通过填写信息无法找到支柱或者支柱数据存在重复<br/>");
				// errorFlag = true;
				// errorFlagList.set(i, errorFlag);
				// }
				errorMsg.append(errorStr);
				if (errorFlag) {
					continue;
				}
				if (!c1c3Flag && isDefectAssortment) {
					if (DefectSystem.defectC1.getStatus().equals(entity.getSystemId())) {
						defectAssortmentService.handleC1DefectData(Arrays.asList((Defect1CDTO) entity),
								defectAssortments);
					} else {
						defectAssortmentService.handleDefectData(Arrays.asList(entity), defectAssortments);
					}
					if (StringUtil.isBlank(entity.getDefectAssortmentId())) {
						errorMsg.append("第" + (i + 2) + "行无法关联到标准缺陷分类（请核对缺陷类型、缺陷值或缺陷描述）<br/>");
						errorFlag = true;
						errorFlagList.set(i, errorFlag);
						continue;
					}
				}
				entity.setSystemId(systemId);
				if (importRepeat(entity)) {
					repeat++;
					if (repeat == 1) {
						repeatStr.append(i + 2);
					} else {
						repeatStr.append(",").append(i + 2);
					}
					continue;
				}
				if (StringUtil.isBlank(entity.getSectionId())) {
					entity.setSectionId(sectionId);
				}
				entity.setCreateBy(createBy);
				// Defect findDefect = findDefect(entity);
				if (StringUtil.isNotBlank(infoId)) {
					entity.setInfoId(infoId);
					entity.setPlanId(planId);
				}
				save((T) entity);
				defectSysncQuestionService.sysncDefectImportData(entity);
				// repeatControl(entity, findDefect);
				defectHistoryControl(entity);
				count++;
			}
		} catch (Exception e) {
			LOGGER.error("SixCServiceImpl Exception", e);
			errorMsg.append(e.getMessage());
			e.printStackTrace();
			// Shift.fatal(StatusCode.FAIL);
		}
		map.put(DefectConstants.IMPORT_COUNT, count);
		map.put(DefectConstants.IMPORT_ERROR_MSG, errorMsg.toString());
		map.put(DefectConstants.IMPORT_REPEAT, repeat);
		map.put(DefectConstants.IMPORT_REPEAT_MSG, repeatStr.toString());
		long t1 = System.currentTimeMillis();
		LOGGER.error("保存缺陷数据：" + count);
		LOGGER.error("导入缺陷总时间：" + (t1 - t));
		return map;
	}

	@Override
	@Transactional
	public int deleteFalses(T entity) {
		int i = 0;
		if (StringUtil.isNotBlank(entity.getId())) {
			List<String> list = Arrays.asList(entity.getId().split(","));
			deleteFalses(list, entity.getDeleteBy(), entity.getDeleteIp());
		}
		return i;
	}

	@Override
	public int deleteFalses(List<String> defectIds, String userId, String deleteIp) {
		int i = defectDao.deleteFalses(defectIds, userId, deleteIp);
		defectRepeatHistoryDao.delete(defectIds);
		List<ProcessInstanceDelRequest> lists = new ArrayList<>();
		List<DefectFlow> flows = defectFlowService.getBykeys(defectIds);
		if (StringUtil.isNotEmpty(flows)) {
			for (DefectFlow defectFlow : flows) {
				ProcessInstanceDelRequest processInstanceDelRequest = new ProcessInstanceDelRequest();
				processInstanceDelRequest.setProcessInstanceId(defectFlow.getProcessInstanceId());
				processInstanceDelRequest.setDeleteReason(userId);
				lists.add(processInstanceDelRequest);
			}
			if (StringUtil.isNotEmpty(lists)) {
				workflowFeignService.deleteTask(lists);
			}
		}
		if (isSuiDe) {
			handleRepeatData(defectIds, userId, deleteIp);
		}
		return i;
	}

	public void handleRepeatData(List<String> defectIds, String userId, String deleteIp) {
		List<DefectRepeatCount> defectRepeats = defectRepeatCountDao.getByKeys(defectIds);
		Set<String> set = new HashSet<String>();
		defectRepeats.forEach(defectRepeat -> {
			String defectRepeatIds = defectRepeat.getDefectRepeatIds();
			if (StringUtil.isNotBlank(defectRepeatIds)) {
				set.addAll(Arrays.asList(defectRepeatIds.split(",")));
			}
		});
		if (StringUtil.isNotEmpty(set)) {
			defectDao.deleteFalses(ListUtils.newArrayList(set), userId, deleteIp);
		}
	}

	@Override
	public void setImgValue(Map<String, Object> value, String userId) {
		for (Entry<String, Object> entry : value.entrySet()) {
			if (entry.getValue() != null && entry.getValue() instanceof Collection) {
				List list = (List) entry.getValue();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) instanceof MyPictureData) {
						MyPictureData data = (MyPictureData) list.get(i);
						FileBlobDTO blob = new FileBlobDTO();
						blob.setData(data.getPictureData());
						blob.setRealName(data.getFileName());
						if (StringUtil.isBlank(blob.getRealName())) {
							blob.setRealName("photo.png");
						}
						blob.setCreateBy(userId);
						String fileId = fileSystemService.uploadFileByClient(blob).getData();
						if (StringUtil.isBlank(fileId)) {
							continue;
						}
						if (i == list.size() - 1) {
							sb.append(fileId);
						} else {
							sb.append(fileId).append(",");
						}
					}
				}
				if (StringUtil.isNotBlank(sb.toString())) {
					value.put(entry.getKey(), sb.toString());
				} else {
					value.put(entry.getKey(), null);
				}
			}
		}
	}

	public boolean importRepeat(Defect defect) {

		defect.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		CQueryFilter cQueryFilter = new CQueryFilter();
		try {
			BeanUtils.copyProperties(cQueryFilter, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		if (!isSuiDe) {
			cQueryFilter.setCheckDate(processDate(cQueryFilter.getCheckDate()));
		} else {
			cQueryFilter.setCheckDate(null);
		}
		if (StringUtil.isNotEmpty(defectDao.importRepeat(cQueryFilter)))
			return true;
		return false;
	}

	public String defectRepeat(Defect defect) {

		Defect queryRepeat = null;
		defect.setDefectStatus(DefectStatus.Cancel.getStatus());
		CQueryFilter cQueryFilter = new CQueryFilter();
		try {
			BeanUtils.copyProperties(cQueryFilter, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		queryRepeat = defectDao.defectRepeat(cQueryFilter);
		if (queryRepeat == null) {
			return "";
		}
		return queryRepeat.getId();
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

	public void repeatControl(Defect defect, Defect findDefect) {
		DefectRepeatHistory defectRepeatHistory = new DefectRepeatHistory();
		try {
			BeanUtils.copyProperties(defectRepeatHistory, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}

		if (StringUtil.isEmpty(findDefect)) {// 无重复
			LOGGER.error("无重复, 缺陷id : " + defect.getId());
			defectRepeatHistory.setDefectId(defect.getId());
		} else {
			LOGGER.error("重复, 关联缺陷id : " + findDefect.getId());
			defectRepeatHistory.setDefectId(findDefect.getId());
		}
		defectRepeatHistoryDao.insert(defectRepeatHistory);
	}

	public String getKGlb(Double glb) {
		if (glb == null)
			return "此支柱暂缺失公里标信息";
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

	public Pillar getArchives(Pillar min, Pillar max, double glb) {
		if (min != null && max != null) {
			double a = glb - min.getStartKm();
			double b = glb - max.getStartKm();
			return Math.abs(a) - Math.abs(b) > 0 ? max : min;
		} else if (min == null && max != null) {
			return max;
		} else if (max == null && min != null) {
			return min;
		}
		return null;
	}

	public void defectHistoryControl(Defect defect) {
		DefectHistory defectHistory = new DefectHistory();
		List<DefectHistory> list = new ArrayList<>();
		try {
			BeanUtils.copyProperties(defectHistory, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		list = defectHistoryDao.getByEntity(defectHistory);// 此时查询条件 杆ID 缺陷类型
															// C类型
		int size = list.size();

		if (size == 0) {// 第一次出现 新增
			defectHistory.preInsert();
			defectHistory.setCount(1);
			defectHistoryDao.insert(defectHistory);
		} else if (size == 1) {// 已经存在 更新count
			defectHistoryDao.updateCount(list.get(0).getId());
		}
	}

	@Override
	public byte[] exportData(List entityList, String templetId) {
		byte[] bt = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtil.isEmpty(entityList)) {
			return bt;
		}
		Map<String, Object> map = null;
		try {
			int i = 1;
			for (Object entity : entityList) {
				map = new HashMap<String, Object>();
				Map<String, Object> temp = (Map<String, Object>) ObjectUtils.objectToHashMap(entity);
				if (temp != null) {
					for (String key : temp.keySet()) {
						Object obj = temp.get(key);
						if (obj != null && obj instanceof BaseEntity) {
							Map<String, Object> temp1 = (Map<String, Object>) ObjectUtils.objectToHashMap(obj);
							if (temp1 != null) {
								Map<String, Object> temp2 = new HashMap<String, Object>();
								for (String key1 : temp1.keySet()) {
									temp2.put(key + "." + key1, temp1.get(key1));
								}
								map.putAll(temp2);
							}
							// temp.remove(key);
						}
					}
					map.putAll(temp);
					map.put("orderNum", i);
					i++;
				}
				list.add(map);
			}
			// addImgData(list, templetId);
			// changeExhibition(list);
			bt = exportService.exportDataByMapLocalTemplet(list, templetId, fileHandleService);
		} catch (Exception e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return bt;
	}

	public Date processDate(Date date) {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
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
	public T get(String id) {
		return dao.getByKey(id);
	}

	@Override
	@DictAggregation
	public List<T> findAllList() {
		return dao.findAllList();
	}

	@Override
	public Map<String, Object> analyzeExcelData(String originalFilename, InputStream inputStream, String templetId,
			String createBy, String sectionId) {
		return null;
	}

	@Override
	@Transactional
	public int save(T entity) {
		{
			int i;
			if (entity != null && StringUtil.isNotBlank(entity.getWorkAreaId())
					&& StringUtil.isBlank(entity.getWorkShopId())) {
				String workShopId = deptFeignService.getParentDept(entity.getWorkAreaId()).getData().getId();
				entity.setWorkShopId(workShopId);
			}
			// 处理缺陷分类标准
			if (isDefectAssortment && !DefectSystem.defectC3.getStatus().equals(entity.getSystemId())
					&& !DefectSystem.defectC1.getStatus().equals(entity.getSystemId())
					&& (entity != null && StringUtil.isBlank(entity.getDefectAssortmentId())
							|| !entity.getIsNewRecord())) {
				if (!(StringUtil.isNotEmpty(entity.getManualFlag()) && entity.getManualFlag() == 1)) {
					if (DefectSystem.defectC1.getStatus().equals(entity.getSystemId())) {
						if (StringUtil.isBlank(entity.getDefectLevel()))
							defectAssortmentService.handleC1DefectData(Arrays.asList((Defect1CDTO) entity));
					} else {
						if ((DefectSystem.defectC2.getStatus().equals(entity.getSystemId())
								&& DefectSystem.defectC4.getStatus().equals(entity.getSystemId())
								&& StringUtil.isBlank(entity.getDefectDataLevel())
								&& StringUtil.isBlank(entity.getDefectDataCategory()))
								|| (DefectSystem.defectC3.getStatus().equals(entity.getSystemId()))
										&& StringUtil.isBlank(entity.getDefectLevel()))
							defectAssortmentService.handleDefectData(Arrays.asList(entity));
					}
				}
			}

			if (isSuiDe && !suiDeRepeatDefect(entity)) {
				Shift.fatal(StatusCode.DATA_REPEAT);
			}
			if (entity != null && entity.getIsNewRecord()) {
				entity.preInsert();
				entity.setDefectStatus(DefectStatus.DefectRegister.getStatus());
				// checkRepeatData(entity, GLB_DISTANCE);
				defectDao.insert(entity);
				if (handleInfo) {
					DefectHandleInfo info = new DefectHandleInfo();
					info.setId(entity.getId());
					info.setIsConfirmed(ISCONFIRMED);
					defectHandleInfoDao.insert(info);
				}
				i = dao.insert(entity);
			} else {
				// checkRepeatData(entity, GLB_DISTANCE);
				defectDao.update(entity);
				i = dao.update(entity);
			}
			if (entity.getCheckDate() != null) {
				setDefectRepeatRequest(entity);
			}
			return i;
		}
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void fetchWordToPdfTaskPublisher() {
		taskPublisherService.fetchTaskPublisherToService(FetchingNewTaskStrategy.SINGLETON);
		// mySqlProcessList();
	}

	public void mySqlProcessList() {
		List<Map<String, Object>> list = defectDao.mySqlProcessList();
		if (StringUtil.isNotEmpty(list)) {
			LOGGER.error(list.toString());
		}
	}

	public void setDefectRepeatRequest(T entity) {
		try {
			DefectRepeatRequest defectRepeatRequest = new DefectRepeatRequest();
			defectRepeatRequest.setCheckDateYear(DateUtils.format(entity.getCheckDate(), "yyyy"));
			BeanUtils.copyProperties(defectRepeatRequest, entity);
			taskPublisherService.insertTaskPublisher(entity.getId(), defectRepeatRequest,
					BussinessType.DefectTask.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDefectRepeatRequest(List<T> list) {
		if (StringUtil.isNotEmpty(list)) {
			Map<String, DefectRepeatRequest> map = new HashMap<>();
			list.forEach(entity -> {
				String checkDateYear = DateUtils.format(entity.getCheckDate(), "yyyy");
				String key = checkDateYear + entity.getLineId() + entity.getDirection() + entity.getDefectType()
						+ entity.getSystemId() + entity.getPillarId();
				if (!map.containsKey(key)) {
					DefectRepeatRequest defectRepeatRequest = new DefectRepeatRequest();
					defectRepeatRequest.setCheckDateYear(checkDateYear);
					try {
						BeanUtils.copyProperties(defectRepeatRequest, entity);
					} catch (Exception e) {
						e.printStackTrace();
					}
					map.put(key, defectRepeatRequest);
				}
			});
			if (StringUtil.isNotEmpty(map)) {
				map.forEach((key, value) -> taskPublisherService.insertTaskPublisher(value.getId(), value,
						BussinessType.DefectTask.getStatus()));
			}
		}
	}

	@Override
	@DictAggregation
	public List<T> findList(CQueryFilter entity) {
		return dao.findList(entity);
	}

	@Override
	@DictAggregation
	public Page<T> findPage(Page page, CQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findList(entity));
	}

	@Override
	@DictAggregation
	public K getDefectPackageResponseByKey(String id) {
		return dao.getDefectPackageResponseByKey(id);
	}

	@Override
	@DictAggregation
	public List<K> getDefectPackageResponseByKeys(List<String> ids) {

		return dao.getDefectPackageResponseByKeys(ids);
	}

	@Override
	@DictAggregation
	public List<K> findDefectPackageResponseAllList() {
		return dao.findDefectPackageResponseAllList();
	}

	@Override
	@DictAggregation
	public List<K> findDefectPackageResponseList(CQueryFilter entity) {
		List<K> list = dao.findDefectPackageResponseList(entity);
		return list;
	}

	@Override
	@DictAggregation
	public Page<K> findDefectPackageResponsePage(Page page, CQueryFilter entity) {
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		PageUtils.setPage(page);
		Page<K> pg = PageUtils.buildPage(dao.findDefectPackageResponseList(entity));
		return pg;
	}

	@Override
	@DictAggregation
	public Page<H> findDefectForm(Page page, CQueryFilter entity) {
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		PageUtils.setPage(page);
		Page<H> pg = PageUtils.buildPage(dao.findDefectForm(entity));
		return pg;
	}

	@Override
	@DictAggregation
	public Page<H> findDefectRepeatForm(Page page, CQueryFilter entity) {
		String id = entity.getId();
		Page<H> pg = new Page<H>();
		if (StringUtil.isNotBlank(id)) {
			DefectRepeatCount defectRepeatCount = defectRepeatCountDao.getByKey(id);
			if (StringUtil.isNotEmpty(defectRepeatCount)) {
				String defectIds = defectRepeatCount.getDefectRepeatIds();
				if (StringUtil.isNotBlank(defectIds)) {
					PageUtils.setPage(page);
					pg = PageUtils.buildPage(dao.findDefectFormByKeys(Arrays.asList(defectIds.split(","))));
				}
			}
		}
		return pg;
	}

	@Override
	@DictAggregation
	public List<K> findDefectPackageRepeatResponseList(CQueryFilter entity) {
		String id = entity.getId();
		List<K> list = null;
		if (StringUtil.isNotBlank(id)) {
			DefectRepeatCount defectRepeatCount = defectRepeatCountDao.getByKey(id);
			String defectIds = defectRepeatCount.getDefectRepeatIds();
			list = dao.getDefectPackageResponseByKeys(Arrays.asList(defectIds.split(",")));
		}

		return list;
	}

	// 处理支柱数据
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
				list.sort((c1, c2) -> c1.getStartKm().compareTo(c2.getStartKm()));
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
		return pillarMap;
	}

	public boolean suiDeRepeatDefect(Defect defect) {

		CQueryFilter cQueryFilter = new CQueryFilter();
		try {
			BeanUtils.copyProperties(cQueryFilter, defect);
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		cQueryFilter.setCheckDate(null);
		List<Defect> defects = defectDao.importRepeat(cQueryFilter);
		if (StringUtil.isEmpty(defects)) {
			return true;
		} else if (defects.size() == 1 && defects.get(0).getId().equals(defect.getId())) {
			return true;
		}

		return false;
	}
	@Override
	@DictAggregation
	public List<H> findDefectForm(CQueryFilter entity) {
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		List<H> list = dao.findDefectForm(entity);
		return list;
	}
	
	@Override
	@DictAggregation
	public Page<H> findDefectFormPage(Page page, CQueryFilter entity) {
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		Page<H> pg = new Page<H>();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		SimplePage sp = new SimplePage();
		sp.setPageNo(pageNo);
		sp.setPageSize(pageSize);
		sp.setStartNum((pageNo - 1) * pageSize);
		sp.setEndNum(pageNo * pageSize);
		entity.setSimplePage(sp);
		int total = dao.findDefectFormCount(entity);
		List<H> list = dao.findDefectFormPage(entity);
		pg.setList(list);
		pg.setTotal((long) total);
		pg.setPageNo(sp.getPageNo());
		pg.setPageSize(sp.getPageSize());
		return pg;
	}

}
