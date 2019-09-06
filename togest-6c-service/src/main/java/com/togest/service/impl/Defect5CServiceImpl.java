package com.togest.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.sevice.PillarFeginService;
import com.togest.client.response.Pillar;
import com.togest.common.annotation.Shift;
import com.togest.common.util.DateUtils;
import com.togest.common.util.PathUtil;
import com.togest.common.util.file.FileCopyUtils;
import com.togest.common.util.file.FileUtil;
import com.togest.common.util.file.StreamUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectSystem;
import com.togest.dao.Defect5CDao;
import com.togest.dao.DefectCheckHandleDao;
import com.togest.dao.DefectReformHandleDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Defect5CDTO;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;
import com.togest.domain.DictionaryItemDTO;
import com.togest.domain.FileBlobDTO;
import com.togest.domain.IdGen;
import com.togest.domain.Point5CDTO;
import com.togest.file.client.FileClient;
import com.togest.request.DefectC5Request;
import com.togest.response.DefectC5Form;
import com.togest.response.DefectC5Response;
import com.togest.service.CheckTrainService;
import com.togest.service.Defect5CService;
import com.togest.service.DictionaryService;
import com.togest.service.Point5CService;
import com.togest.service.PointHisotryData5CService;
import com.togest.util.XMLLoader;

@Service
public class Defect5CServiceImpl extends SixCServiceImpl<Defect5CDao, Defect5CDTO, DefectC5Response, DefectC5Form>
		implements Defect5CService {

	@Autowired
	private Defect5CDao dao;
	@Autowired
	private Point5CService point5CService;
	@Autowired
	private CheckTrainService checkTrainService;
	@Autowired
	private FileClient fileSystemFeignService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private PillarFeginService pillarFeginService;
	@Autowired
	private PointHisotryData5CService pointHisotryData5CService;
	@Autowired
	private DefectCheckHandleDao checkHandleDao;
	@Autowired
	private DefectReformHandleDao reformHandleDao;

	private static final String defectTag = "3";
	private static final Logger LOGGER = LoggerFactory.getLogger(Defect5CServiceImpl.class);

	@Override
	@DictAggregation
	public List<Defect5CDTO> getListByPointId(String pointId) {

		return dao.getListByPointId(pointId);
	}

	public void analyzeZipData(String fileName, InputStream input, String createBy) {

		try {
			if (StringUtil.isNotBlank(fileName)) {
				if (fileName.endsWith(".zip")) {
					byte[] bt = StreamUtils.InputStreamTOByte(input);
					File targetFile = new File(PathUtil.getSystempPath() + File.separator + IdGen.uuid()
							+ FileUtil.getFileExtByFileName(fileName));
					FileCopyUtils.copy(bt, targetFile);
					ZipFile zf = new ZipFile(targetFile);
					InputStream in = new BufferedInputStream(new FileInputStream(targetFile));
					ZipInputStream zin = new ZipInputStream(in);
					ZipEntry ze = null;
					FileBlobDTO blob = new FileBlobDTO();
					blob.setRealName(fileName);
					blob.setData(bt);
					String primitiveDataFile = fileSystemFeignService.uploadFileByClient(blob).getData();
					Defect5CDTO dto = new Defect5CDTO();
					dto.setPrimitiveDataFile(primitiveDataFile);
					dto.setCreateBy(createBy);
					dto.setCreateDate(new Date());
					String pantographPhotos = "";
					String skatePhotos = "";
					String locomotivePhotos = "";
					boolean fg = true;
					while ((ze = zin.getNextEntry()) != null) {
						if (ze.isDirectory()) {
						} else {

							String zipName = ze.getName();
							long size = ze.getSize();
							if (size > 0) {
								if (zipName.endsWith(".xml")) {
									Map<String, String> map = XMLLoader.xmlParse(zf.getInputStream(ze));
									fg = setDefectData(dto, map);

								} else {
									FileBlobDTO fileBlob = new FileBlobDTO();
									fileBlob.setRealName(zipName.substring(zipName.lastIndexOf("/") + 1));
									fileBlob.setData(StreamUtils.InputStreamTOByte(zf.getInputStream(ze)));
									String fileId = fileSystemFeignService.uploadFileByClient(fileBlob).getData();
									if (StringUtil.isNotBlank(fileId)) {
										String regex1 = "^.+_.+_1_.+$";
										String regex2 = "^.+_.+_2_.+$";
										String regex3 = "^.+_.+_3_.+$";
										if (zipName.matches(regex1)) {
											pantographPhotos = pantographPhotos + "," + fileId;
											// dto.setPantographPhoto(fileId);
										} else if (zipName.matches(regex2)) {
											skatePhotos = skatePhotos + "," + fileId;
											// dto.setSkatePhoto(fileId);
										} else if (zipName.matches(regex3)) {
											locomotivePhotos = locomotivePhotos + "," + fileId;
											// dto.setLocomotivePhoto(fileId);
										}
									}

								}

							}

						}
					}
					if (fg) {
						if (StringUtil.isNotBlank(pantographPhotos)) {
							dto.setPantographPhoto(pantographPhotos.substring(1, pantographPhotos.length()));
						}
						if (StringUtil.isNotBlank(skatePhotos)) {
							dto.setSkatePhoto(skatePhotos.substring(1, skatePhotos.length()));
						}

						if (StringUtil.isNotBlank(locomotivePhotos)) {
							dto.setLocomotivePhoto(locomotivePhotos.substring(1, locomotivePhotos.length()));
						}
						dto.setSystemId(DefectSystem.defectC5.getStatus());
						String str = defectRepeat(dto);
						if (StringUtil.isBlank(str)) {
							dto.setDefectName(dto.getDefectName() + " (" + dto.getPillarName() + "支柱) "
									+ getKGlb(dto.getDefectGlb()));
							save(dto);
						} else {
							Shift.fatal(StatusCode.DEFECT_EXISTENT);
						}

					}
					zin.closeEntry();
					targetFile.delete();
					if (!fg) {
						Shift.fatal(StatusCode.POINT_RELATION_ERROR);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}

	}

	public boolean setDefectData(Defect5CDTO entity, Map<String, String> map) {
		boolean fg = true;
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "track" }).getData();
		String trainNo = map.get("trainNo");
		if (StringUtil.isNotBlank(trainNo)) {
			String trainId = checkTrainService.getByTrainNo(trainNo);
			entity.setTrainId(trainId);
		}

		String defectTypeName = map.get("type");
		if (StringUtil.isNotBlank(defectTypeName)) {
			/*
			 * List<DefectTypeDTO> list = defectTypeService
			 * .getListBySystemCode(DefectSystem.defectC5.getStatus()); String
			 * defectType = null; if (StringUtil.isNotEmpty(list)) { for
			 * (DefectTypeDTO defectTypeDTO : list) { if
			 * (defectTypeName.equals(defectTypeDTO.getName())) { defectType =
			 * defectTypeDTO.getId(); break; } } }
			 */
			entity.setDefectType(defectTypeName);
		}

		String checkDate = map.get("date");
		if (StringUtil.isNotBlank(checkDate)) {
			entity.setCheckDate(DateUtils.parseDatetime(checkDate, DateUtils.DATE_FORMAT_DEFAULT));
		}

		String trackName = map.get("location");
		if (StringUtil.isNotBlank(trackName)) {
			String trackNo = null;
			List<DictionaryItemDTO> dicts = tempMap.get("track");
			if (StringUtil.isNotEmpty(dicts)) {
				for (DictionaryItemDTO dictionaryItemDTO : dicts) {
					if (trackName.equals(dictionaryItemDTO.getName())) {
						trackNo = dictionaryItemDTO.getId();
						break;
					}
				}
			}
			entity.setTrackNo(trackNo);
		}

		String lineName = map.get("lineName");
		String st = map.get("st");
		String poleNo = map.get("PoleNo");
		Point5CDTO point5C = new Point5CDTO();
		point5C.setLineName(lineName);
		point5C.setInstallPillarName(poleNo);
		point5C.setStationName(st);
		Point5CDTO dto = point5CService.get(point5C);
		if (StringUtil.isEmpty(dto)) {
			fg = false;
		} else {
			entity.setPointId(dto.getId());
			entity.setLineId(dto.getLineId());
			entity.setPsaId(dto.getStationId());
			entity.setPillarId(dto.getInstallPillarId());
			entity.setSectionId(dto.getSectionId());
			entity.setPillarName(poleNo);
			Pillar pillar = pillarFeginService.getPillar(dto.getInstallPillarId()).getData();
			if (pillar != null) {
				entity.setWorkAreaId(pillar.getDeptId());
				entity.setDirection(pillar.getDirection());
			}

		}

		/*
		 * if (StringUtil.isNotBlank(lineName)) { String lineId =
		 * lineService.getIdByName(lineName); entity.setLineId(lineId); }
		 * 
		 * if (StringUtil.isNotBlank(st)) { String psaId =
		 * supplyArmService.getIdByName(st); entity.setPsaId(psaId); }
		 * 
		 * if (StringUtil.isNotBlank(poleNo)) {
		 * 
		 * }
		 */

		String glb = map.get("km");
		if (StringUtil.isNotBlank(glb)) {
			entity.setDefectGlb(Double.valueOf(glb));
		}
		return fg;

	}

	public void import5CData(String fileName, InputStream input, String createBy, String tag) {
		if (StringUtil.isNotBlank(fileName)) {
			if (fileName.endsWith(".zip")) {
				if (defectTag.equals(tag)) {
					analyzeZipData(fileName, input, createBy);
				} else {
					pointHisotryData5CService.analyzeZipData(fileName, input, createBy);
				}
			}
		}
	}

	public void saveDefectData(Map<String, String> map) {
		if (StringUtil.isEmpty(map)) {
			Shift.fatal(StatusCode.FAIL);
		}
		Defect5CDTO entity = new Defect5CDTO();
		entity.setCreateBy(map.get("createBy"));
		entity.setCreateDate(new Date());
		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService
				.findDictItemsByDictName(new String[] { "track" }).getData();
		String trainNo = map.get("trainNo");
		if (StringUtil.isNotBlank(trainNo)) {
			String trainId = checkTrainService.getByTrainNo(trainNo);
			entity.setTrainId(trainId);
		}

		String defectTypeName = map.get("type");
		if (StringUtil.isNotBlank(defectTypeName)) {
			/*
			 * List<DefectTypeDTO> list = defectTypeService
			 * .getListBySystemCode(DefectSystem.defectC5.getStatus()); String
			 * defectType = null; if (StringUtil.isNotEmpty(list)) { for
			 * (DefectTypeDTO defectTypeDTO : list) { if
			 * (defectTypeName.equals(defectTypeDTO.getName())) { defectType =
			 * defectTypeDTO.getId(); break; } } }
			 */
			entity.setDefectType(defectTypeName);
		}

		String checkDate = map.get("date");
		if (StringUtil.isNotBlank(checkDate)) {
			entity.setCheckDate(DateUtils.parseDatetime(checkDate, DateUtils.DATE_FORMAT_DEFAULT));
		}

		String trackName = map.get("location");
		if (StringUtil.isNotBlank(trackName)) {
			String trackNo = null;
			List<DictionaryItemDTO> dicts = tempMap.get("track");
			if (StringUtil.isNotEmpty(dicts)) {
				for (DictionaryItemDTO dictionaryItemDTO : dicts) {
					if (trackName.equals(dictionaryItemDTO.getName())) {
						trackNo = dictionaryItemDTO.getId();
						break;
					}
				}
			}
			entity.setTrackNo(trackNo);
		}

		String lineName = map.get("lineName");
		String st = map.get("st");
		String poleNo = map.get("PoleNo");
		Point5CDTO point5C = new Point5CDTO();
		point5C.setLineName(lineName);
		point5C.setInstallPillarName(poleNo);
		point5C.setStationName(st);
		Point5CDTO dto = point5CService.get(point5C);
		if (StringUtil.isEmpty(dto)) {

		} else {
			entity.setPointId(dto.getId());
			entity.setLineId(dto.getLineId());
			entity.setPsaId(dto.getStationId());
			entity.setPillarId(dto.getInstallPillarId());
			entity.setSectionId(dto.getSectionId());
			Pillar pillar = pillarFeginService.getPillar(dto.getInstallPillarId()).getData();
			if (pillar != null) {
				entity.setWorkAreaId(pillar.getDeptId());
				entity.setDirection(pillar.getDirection());
			}

		}

		/*
		 * if (StringUtil.isNotBlank(lineName)) { String lineId =
		 * lineService.getIdByName(lineName); entity.setLineId(lineId); }
		 * 
		 * if (StringUtil.isNotBlank(st)) { String psaId =
		 * supplyArmService.getIdByName(st); entity.setPsaId(psaId); }
		 * 
		 * if (StringUtil.isNotBlank(poleNo)) {
		 * 
		 * }
		 */

		String glb = map.get("km");
		if (StringUtil.isNotBlank(glb)) {
			entity.setDefectGlb(Double.valueOf(glb));
		}
	}

	@Override
	public int updateDefectC5Request(DefectC5Request entity) {

		DefectCheckHandle checkHandle = entity.getDefectCheckHandle();
		if (StringUtil.isNotEmpty(checkHandle)) {
			checkHandle.setId(entity.getId());
			checkHandleDao.update(checkHandle);
		}
		DefectReformHandle reformHandle = entity.getDefectReformHandle();
		if (StringUtil.isNotEmpty(reformHandle)) {
			reformHandle.setId(entity.getId());
			reformHandleDao.update(reformHandle);
		}
		return save(entity);
	}

}
