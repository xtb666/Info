package com.togest.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.togest.dao.C2PicIndexDao;
import com.togest.dict.util.DictionaryUtil;
import com.togest.domain.C2InfoDTO;
import com.togest.domain.C2PicIndexDTO;
import com.togest.domain.DictionaryItemDTO;
import com.togest.service.C2InfoService;
import com.togest.service.C2PicIndexService;
import com.togest.service.CrudService;
import com.togest.service.DictionaryService;
import com.togest.util.AcessDBUtils;
import com.togest.util.PicIndexUtil;
import com.togest.util.XMLLoader;

@Service
public class C2PicIndexServiceImpl extends CrudService<C2PicIndexDao, C2PicIndexDTO> implements C2PicIndexService {

	@Autowired
	private C2InfoService c2infoService;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private DictionaryService dictionaryService;

	public void deleteFalses(String ids, String deleteBy, String deleteIp) {
		if (StringUtil.isNotBlank(ids)) {
			dao.deleteFalses(Arrays.asList(ids.split(",")), deleteBy, deleteIp);
		}
	}

	@Transactional
	public void analyzeData(String xmlLocation, String mdbLocation, String packageName, String packagePath,
			String table, String sectionId, String source, String infoId) {
		Map<String, String> map = XMLLoader.xmlParse(xmlLocation);
		C2InfoDTO entity = null;
		if (StringUtil.isBlank(infoId)) {
			Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
					.findDictItemsByDictName(new String[] { "direction" }).getData();
			Map<String, Map<String, String>> dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
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

			String systemId = DefectSystem.defectC2.getStatus();

			if (StringUtil.isBlank(lineId) || StringUtil.isBlank(direction) || StringUtil.isBlank(checkDateStr)) {
				Shift.fatal(StatusCode.FAIL, "参数数据为空");
			}
			Date checkDate = PicIndexUtil.processDate(PicIndexUtil.convertDate(checkDateStr));
			entity = new C2InfoDTO();
			entity.setCheckDate(checkDate);
			entity.setSystemId(systemId);
			entity.setLineId(lineId);
			entity.setDirection(direction);
		} else {
			entity = c2infoService.get(infoId);
			if (StringUtil.isEmpty(entity))
				entity = new C2InfoDTO();
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
		entity.setDataType(1);
		c2infoService.save(entity);

		/*
		 * List<Map<String, String>> list = AcessDBUtils.readFileACCESS(
		 * mdbLocation, table); if (StringUtil.isNotEmpty(list)) { for
		 * (Map<String, String> rs : list) { C2PicIndexDTO index = new
		 * C2PicIndexDTO(); index.setPillarNum(rs.get("SETLOC"));
		 * index.setKm(PicIndexUtil.convertGlb(rs.get("KM")));
		 * index.setStation(rs.get("ST"));
		 * index.setPanoramisStartFrame(Integer.valueOf(rs
		 * .get("PANORAMIS_START_FRAME")));
		 * index.setPanoramisEndFrame(Integer.valueOf(rs
		 * .get("PANORAMIS_END_FRAME")));
		 * index.setPanoramisStartPath(rs.get("PANORAMIS_START_PATH")
		 * .replaceAll("\\\\", "/"));
		 * index.setPanoramisEndPath(rs.get("PANORAMIS_END_PATH")
		 * .replaceAll("\\\\", "/"));
		 * index.setPartStartPath(rs.get("PART_START_PATH").replaceAll(
		 * "\\\\", "/"));
		 * index.setPartEndPath(rs.get("PART_END_PATH").replaceAll("\\\\",
		 * "/")); index.setPartStartFrame(Integer.valueOf(rs
		 * .get("PART_START_FRAME")));
		 * index.setPartEndFrame(Integer.valueOf(rs.get("PART_END_FRAME")));
		 * index.setInfoId(entity.getId()); save(index); } }
		 */

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = AcessDBUtils.getConn(mdbLocation);
			ps = conn.prepareStatement("select * from " + table);
			rs = ps.executeQuery();
			while (rs.next()) {
				C2PicIndexDTO index = new C2PicIndexDTO();
				String pillarNum = rs.getString("SETLOC");
				index.setPillarNum(StringUtil.isNotBlank(pillarNum) ? pillarNum.trim() : null);
				index.setKm(rs.getDouble("KM"));
				String st = rs.getString("ST");
				index.setStation(StringUtil.isNotBlank(st) ? st.trim() : null);
				index.setPanoramisStartFrame(rs.getInt("PANORAMIS_START_FRAME"));
				index.setPanoramisEndFrame(rs.getInt("PANORAMIS_END_FRAME"));
				String panoramisStartPath = rs.getString("PANORAMIS_START_PATH").replaceAll("\\\\", "/");
				index.setPanoramisStartPath(
						StringUtil.isNotBlank(panoramisStartPath) ? panoramisStartPath.trim() : null);
				String panoramisEndPath = rs.getString("PANORAMIS_END_PATH").replaceAll("\\\\", "/");
				index.setPanoramisEndPath(StringUtil.isNotBlank(panoramisEndPath) ? panoramisEndPath.trim() : null);
				String partStartPath = rs.getString("PART_START_PATH").replaceAll("\\\\", "/");
				index.setPartStartPath(StringUtil.isNotBlank(partStartPath) ? partStartPath.trim() : null);
				String PartEndPath = rs.getString("PART_END_PATH").replaceAll("\\\\", "/");
				index.setPartEndPath(StringUtil.isNotBlank(PartEndPath) ? PartEndPath.trim() : null);
				index.setPartStartFrame(rs.getInt("PART_START_FRAME"));
				index.setPartEndFrame(rs.getInt("PART_END_FRAME"));
				index.setInfoId(entity.getId());
				save(index);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL, e.getMessage());
		} finally {
			AcessDBUtils.close(conn, ps, rs);
		}

	}

	public void updateBatch(C2PicIndexDTO entity) {
		if (entity != null && StringUtil.isNotBlank(entity.getId())) {
			List<String> ids = Arrays.asList(entity.getId().split(","));
			dao.updateBatch(ids, entity);
		}
	}
}
