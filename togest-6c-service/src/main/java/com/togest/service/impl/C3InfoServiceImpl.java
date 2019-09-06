package com.togest.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.PillarFeginService;
import com.togest.client.response.Pillar;
import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.dao.C3InfoDao;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.C3Info;
import com.togest.domain.C3InfoDTO;
import com.togest.domain.Defect3CDTO;
import com.togest.domain.DefectTypeDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.FileBlobDTO;
import com.togest.domain.Page;
import com.togest.file.client.FileClient;
import com.togest.request.FileForm;
import com.togest.request.InfoQueryFilter;
import com.togest.service.C3InfoService;
import com.togest.service.CheckTrainService;
import com.togest.service.Defect3CService;
import com.togest.service.DefectTypeService;
import com.togest.service.DictionaryService;
import com.togest.service.LineService;
import com.togest.service.SupplyArmService;
import com.togest.util.PicIndexUtil;
import com.togest.util.XMLLoader;
import com.togest.utils.PageUtils;

@Service
public class C3InfoServiceImpl  extends BaseInfoServiceImpl<C3InfoDao, C3InfoDTO> implements C3InfoService {
	@Autowired
	private C3InfoDao dao;
	@Autowired
	private Defect3CService defect3CService;
	@Autowired
	private DefectTypeService defectTypeService;
	@Autowired
	private CheckTrainService checkTrainService;
	@Autowired
	private LineService lineService;
	@Autowired
	private SupplyArmService supplyArmService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private PillarFeginService pillarFeginService;
	@Autowired
	private FileClient fileSystemService;
	
	
	@Transactional
	@Override
	public int update(C3Info entity) {
		Date date = new Date();
		entity.setUpdateDate(date);
		return dao.update(entity);
	}

	
	public Map<String, String> saveXML(File src, Map<String, FileForm> imgMap,
			String createBy) {
		Map<String, String> maps = new HashMap<String, String>();
		boolean fg = true;
		Defect3CDTO defect = new Defect3CDTO();
		StringBuilder msg = new StringBuilder();
		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(
						new String[] { "direction", "speed_type", "track",
								"defect_grade" }).getData();
		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		List<DefectTypeDTO> defectTypeList = defectTypeService
				.getListBySystemCode(DefectSystem.defectC3.getStatus());
		Map<String, String> map = XMLLoader.xmlParse(src.getAbsolutePath());
		if (StringUtil.isNotEmpty(map)) {
			// 缺陷类型
			String defectTypeName = map.get("type");
			if (StringUtil.isNotBlank(defectTypeName)) {
				for (DefectTypeDTO defectTypeDTO : defectTypeList) {
					if (defectTypeName.equals(defectTypeDTO.getName())) {
						defect.setDefectType(defectTypeDTO.getId());
						break;
					}
				}
			} else {
				msg.append("缺陷类型数据为空 <br/>");
				fg = false;
			}
			// 线路
			String lineName = map.get("lineName");
			String lineId = null;
			if (StringUtil.isNotBlank(lineName)) {
				lineId = lineService.getIdByName(lineName.trim());
				if (StringUtil.isNotBlank(lineId)) {
					defect.setLineId(lineId);

				} else {
					msg.append("找不到线路：" + lineName + "<br/>");
					fg = false;
				}
			} else {
				msg.append("线路数据为空 <br/>");
				fg = false;
			}

			// 行别
			String directionName = map.get("direction");
			String direction = null;
			if (StringUtil.isNotBlank(directionName)) {
				Map<String, String> directionMap = dictMap.get("direction");
				if (directionMap != null) {
					direction = directionMap.get(directionName.trim());
				}
				if (StringUtil.isNotBlank(direction)) {
					defect.setDirection(direction);
				} else {
					msg.append("找不到行别：" + directionName + "<br/>");
					fg = false;
				}
			} else {
				msg.append("没有行别信息<br/>");
				fg = false;
			}
			// 公里标
			String km = map.get("km");
			if (StringUtil.isNotBlank(km)) {
				defect.setDefectGlb(PicIndexUtil.convertGlb(km));
			}
			String sectionId = null;
			// 支柱号
			if (StringUtil.isNotBlank(defect.getLineId())
					&& StringUtil.isNotBlank(defect.getDirection())
					&& StringUtil.isNotEmpty(defect.getDefectGlb())) {
				Pillar minPillar = pillarFeginService.getMin(
						defect.getLineId(), defect.getDirection(),
						defect.getDefectGlb()).getData();
				Pillar maxPillar = pillarFeginService.getMax(
						defect.getLineId(), defect.getDirection(),
						defect.getDefectGlb()).getData();
				Pillar pillar = (minPillar == null ? maxPillar : minPillar);
				if (StringUtil.isNotEmpty(pillar)) {
					sectionId = pillar.getSectionId();
					defect.setPillarId(pillar.getId());
					defect.setWorkAreaId(pillar.getDeptId());
				} else {
					msg.append("线路：" + lineName + ",行别：" + direction + ",公里标："
							+ km + "处找不到支柱号！<br/>");
					fg = false;
				}
			} else {
				msg.append("没有支柱号<br/>");
				fg = false;
			}
			// 机车号
			String trainNo = map.get("trainNo");
			if (StringUtil.isNotBlank(trainNo)) {// 机车编号
				String trainId = checkTrainService.getByTrainNo(trainNo.trim());
				if (StringUtil.isNotBlank(trainId)) {
					defect.setTrainId(trainId);

				} else {
					msg.append("找不到机车编号<br/>");
					fg = false;
				}
			} else {
				msg.append("没有机车编号信息<br/>");
				fg = false;
			}

			// 弓位
			String seat = map.get("seat");
			if (StringUtil.isNotBlank(seat)) {
				if (seat.length() < 3) {
					seat = "000".substring(0, 3 - seat.length()) + seat;
				}
				if (!seat.matches("\\d{2}车")) {
					msg.append("弓位:" + seat + "信息错误！必须是2位数字+车！不足加零补位！<br/>");
					fg = false;
				} else {
					defect.setSeat(seat);
				}
			} else {
				msg.append("没有弓位信息<br/>");
				fg = false;
			}
			// 检测日期
			Date date = null;
			String checkDate = map.get("date");
			if (StringUtil.isNotBlank(checkDate)) {
				date = PicIndexUtil.convertDate(checkDate);
				if (StringUtil.isNotEmpty(date)) {
					defect.setCheckDate(date);
					defect.setCheckTime(DateUtils.format(date, "HH:mm:ss"));
				} else {
					msg.append("检测日期:" + checkDate + "格式错误！正确格式：年月日时分秒！<br/>");
					fg = false;
				}

			} else {
				msg.append("没有检测日期信息<br/>");
				fg = false;
			}
			// 车速
			String speed = map.get("speed");
			if (StringUtil.isNotBlank(speed)) {
				defect.setSpeed(Double.valueOf(speed));
			}
			// 站-区间
			String st = map.get("st");
			if (StringUtil.isNotBlank(st)) {
				List<String> psaList = lineService.getPsaNamesByLineId(defect
						.getLineId());
				if (!psaList.contains(st.trim())) {
					msg.append("在线路" + lineName + "下找不到站区：" + st + "<br/>");
					fg = false;
				}
				String psaId = supplyArmService.getIdByName(st.trim());
				if (StringUtil.isNotBlank(psaId)) {
					defect.setPsaId(psaId);

				} else {
					msg.append("第站区：" + st + "不存在<br/>");
					fg = false;
				}

			} else {
				msg.append("没有站区信息<br/>");
				fg = false;
			}

			String longitude = map.get("longitude");// 坐标经度
			if (StringUtil.isNotBlank(longitude)) {
				defect.setLongitude(Double.valueOf(longitude));
			}

			String latitude = map.get("latitude");// 坐标纬度
			if (StringUtil.isNotBlank(latitude)) {
				defect.setLatitude(Double.valueOf(latitude));
			}

			String netT = map.get("net_t");// 接触网温度
			if (StringUtil.isNotBlank(netT)) {
				defect.setNetT(Integer.valueOf(netT));
			}

			String envT = map.get("env_t");// 环境温度
			if (StringUtil.isNotBlank(envT)) {
				defect.setEnvT(Integer.valueOf(envT));
			}
			String height = map.get("height");// 接触线高度
			if (StringUtil.isNotBlank(height)) {
				defect.setHeight(Integer.valueOf(height));
			}
			String stagger = map.get("STAGGER");// 接触线拉出值
			if (StringUtil.isNotBlank(stagger)) {
				defect.setStagger(Integer.valueOf(stagger));
			}
			// 可见光
			FileForm kjg = imgMap.get("kjg");
			if (StringUtil.isNotEmpty(kjg)) {
				FileBlobDTO blob = new FileBlobDTO();
				blob.setData(kjg.getData());
				blob.setRealName(kjg.getOriginalName());
				blob.setCreateBy(createBy);
				String fileId = fileSystemService.uploadFileByClient(blob)
						.getData();
				if (StringUtil.isNotBlank(fileId)) {
					defect.setVisibleLightPhoto(fileId);
				}
			}else{
				
			}
			// 红外线
			FileForm hw = imgMap.get("hw");
			if (StringUtil.isNotEmpty(hw)) {
				FileBlobDTO blob = new FileBlobDTO();
				blob.setData(hw.getData());
				blob.setRealName(hw.getOriginalName());
				blob.setCreateBy(createBy);
				String fileId = fileSystemService.uploadFileByClient(blob)
						.getData();
				if (StringUtil.isNotBlank(fileId)) {
					defect.setInfraRedPhoto(fileId);
				}
			}else{
				
			}
			if (fg) {
				/*C3InfoDTO findC3InfoDTO = dao.findC3InfoDTO(processDate(date), lineId, direction, 
						DefectSystem.defectC3.getStatus(), sectionId);
				if(StringUtil.isNotEmpty(findC3InfoDTO)) {
					defect.setInfoId(findC3InfoDTO.getId());
				}*/
				defect.setSystemId(DefectSystem.defectC3.getStatus());
				defect.setDefectStatus(DefectStatus.DefectRegister.getStatus());
				defect.setCreateBy(createBy);
				defect.setSectionId(sectionId);
				defect.setCreateDate(new Date());
				defect3CService.save(defect);
			}
		}
		maps.put("errorMsg", msg.toString());
		return maps;
	}

	@Override
	public Map<String, String> saveZip(InputStream input, String fileName,
			String createBy) {
		
		return null;
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
	public C3InfoDTO findC3InfoDTO(Date checkDate, String lineId, String direction, String systemId, String sectionId) {
		return dao.findC3InfoDTO(checkDate, lineId, direction, systemId, sectionId);
	}

	@Override
	public C3InfoDTO get(String id) {
		return dao.getByKey(id);
	}

	@Override
	public Page<C3InfoDTO> findC3InfoPages(Page page, InfoQueryFilter entity) {
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findC3InfoDTOList(entity));
	}

}
