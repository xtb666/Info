package com.togest.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.sevice.LineFeignService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DataStatus;
import com.togest.config.DefectSystem;
import com.togest.dao.C4PicIndexDao;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.C4InfoDTO;
import com.togest.domain.C4PicIndexDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.Naming;
import com.togest.service.C4InfoService;
import com.togest.service.C4PicIndexService;
import com.togest.service.CrudService;
import com.togest.service.DictionaryService;
import com.togest.util.AcessDBUtils;
import com.togest.util.PicIndexUtil;
import com.togest.util.XMLLoader;

@Service
public class C4PicIndexServiceImpl extends
		CrudService<C4PicIndexDao, C4PicIndexDTO> implements C4PicIndexService {
	@Autowired
	private C4InfoService c4infoService;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private DictionaryService dictionaryService;

	@Transactional
	public void analyzeData(String xmlLocation, String mdbLocation,
			String packageName, String packagePath, String table,
			String sectionId, String source, String infoId) {
		C4InfoDTO entity = null;
		Map<String, String> map = XMLLoader.xmlParse(xmlLocation);
		if (StringUtil.isBlank(infoId)) {
			Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
					.findDictItemsByDictName(new String[] { "direction" })
					.getData();
			Map<String, Map<String, String>> dictMap = DictionaryUtil
					.convertAllDictionaryMap(tempMap, false);
			String lineName = map.get("lineName");
			String lineId = null;
			if (StringUtil.isNotBlank(lineName)) {
				lineId = lineFeignService.getIdByName(lineName).getData();
			}
			String directionName = map.get("direction");
			String direction = null;
			if (StringUtil.isNotBlank(directionName)) {
				direction = dictMap.get("direction").get(directionName);
			}

			String checkDateStr = map.get("date");

			String systemId = DefectSystem.defectC4.getStatus();

			if (StringUtil.isBlank(lineId) || StringUtil.isBlank(direction)
					|| StringUtil.isBlank(checkDateStr)) {
				Shift.fatal(StatusCode.FAIL, "参数数据为空");
			}
			Date checkDate = PicIndexUtil.processDate(PicIndexUtil
					.convertDate(checkDateStr));
			entity= new C4InfoDTO();
			entity.setCheckDate(checkDate);
			entity.setSystemId(systemId);
			entity.setLineId(lineId);
			entity.setDirection(direction);
		} else {
			entity = c4infoService.get(infoId);
		}
		entity.setSource(source);
		entity.setPackageName(packageName);
		entity.setPackagePath(packagePath);
		entity.setStartTime(PicIndexUtil.convertDate(map.get("startTime")));
		entity.setEndTime(PicIndexUtil.convertDate(map.get("endTime")));
		entity.setStartKm(PicIndexUtil.convertGlb(map.get("startKm")));
		entity.setEndKm(PicIndexUtil.convertGlb(map.get("endKm")));
		entity.setStartPoleno(map.get("startPoleNo"));
		entity.setEndPoleno(map.get("endPoleNo"));
		entity.setRawdataStatus(DataStatus.AlreadyUploaded.getStatus());
		c4infoService.save(entity);

		/*
		 * for (int i = 1; i < 20; i++) { String cameraX = "camera" + i + "X";
		 * String cameraY = "camera" + i + "Y"; C4Camera c4Camera = new
		 * C4Camera(); c4Camera.setCameraX(map.get(cameraX) == null ? null :
		 * Double .valueOf(map.get(cameraX)));
		 * c4Camera.setCameraY(map.get(cameraY) == null ? null : Double
		 * .valueOf(map.get(cameraY))); c4Camera.setInfoId(p.getId());
		 * cameraService.save(c4Camera); }
		 */
		List<Map<String, String>> list = AcessDBUtils.readFileACCESSPages(
				mdbLocation, table);
		if (StringUtil.isNotEmpty(list)) {
			for (int index = 0; index < list.size(); index++) {
				Map<String, String> rs = list.get(index);
				C4PicIndexDTO c4PicIndex = new C4PicIndexDTO();
				// c4PicIndex.setDefectDateStr(rs.get("DETECTDATE"));
				c4PicIndex.setPillarNum(rs.get("SETLOC"));
				try {
					c4PicIndex.setKm(PicIndexUtil.convertGlb(rs.get("KM")));
					c4PicIndex.setSpeed(rs.get("SPEED"));
				} catch (Exception e) {

				}
				c4PicIndex.setStation(rs.get("ST"));
				c4PicIndex.setPath(rs.get("PATH").replaceAll("\\\\", "/"));
				c4PicIndex.setCameraNo(getCanmerNo(c4PicIndex.getPath()));
				c4PicIndex.setInfoId(entity.getId());
				c4PicIndex.setSort(list.size() - index);
				save(c4PicIndex);
			}
		}

	}

	private String getCanmerNo(String path) {
		path = path.substring(path.length() - 6, path.length() - 4);
		return path;
	}

	public List<Naming> getCanmerNoByInfoId(String infoId) {
		return dao.getCanmerNoByInfoId(infoId);
	}

	public List<Naming> getStationByInfoId(String infoId) {
		return dao.getStationByInfoId(infoId);
	}

	@Override
	public void updateBatch(C4PicIndexDTO entity) {
		if (entity != null && StringUtil.isNotBlank(entity.getId())) {
			List<String> ids = Arrays.asList(entity.getId().split(","));
			dao.updateBatch(ids, entity);
		}

	}

	@Override
	public void deleteFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			dao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp);
		}
	}

}
