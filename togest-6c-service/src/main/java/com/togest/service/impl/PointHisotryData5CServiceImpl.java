package com.togest.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.common.util.DateUtils;
import com.togest.common.util.PathUtil;
import com.togest.common.util.file.FileCopyUtils;
import com.togest.common.util.file.FileUtil;
import com.togest.common.util.file.StreamUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.PointHisotryData5CDao;
import com.togest.domain.FileBlobDTO;
import com.togest.domain.IdGen;
import com.togest.domain.Page;
import com.togest.domain.Point5CDTO;
import com.togest.domain.PointHisotryData5CDTO;
import com.togest.file.client.FileClient;
import com.togest.service.Point5CService;
import com.togest.service.PointHisotryData5CService;
import com.togest.util.XMLLoader;
import com.togest.utils.PageUtils;

@Service
public class PointHisotryData5CServiceImpl implements PointHisotryData5CService {

	@Autowired
	private PointHisotryData5CDao dao;
	@Autowired
	private Point5CService point5CService;
	@Autowired
	private FileClient fileSystemFeignService;

	@Override
	public int save(PointHisotryData5CDTO entity) {

		if (entity.getIsNewRecord()) {
			entity.preInsert();
			return dao.insert(entity);
		} else {
			return dao.update(entity);
		}
	}

	@Override
	public PointHisotryData5CDTO get(String id) {

		return dao.getByKey(id);
	}

	@Override
	public PointHisotryData5CDTO get(PointHisotryData5CDTO entity) {

		return dao.getByEntity(entity);
	}

	@Override
	public List<PointHisotryData5CDTO> findList(PointHisotryData5CDTO entity) {

		return dao.findList(entity);
	}

	@Override
	public Page<PointHisotryData5CDTO> findList(Page page,
			PointHisotryData5CDTO entity) {

		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findList(entity));
	}

	@Override
	public int delete(String id) {

		return dao.delete(id);
	}

	@Override
	public int deletes(String ids) {

		if (StringUtil.isNotBlank(ids)) {
			return dao.deletes(Arrays.asList(ids.split(",")));
		}
		return 0;
	}

	@Override
	public void analyzeZipData(String fileName, InputStream input,
			String createBy) {
		try {
			if (StringUtil.isNotBlank(fileName)) {
				if (fileName.endsWith(".zip")) {
					byte[] bt = StreamUtils.InputStreamTOByte(input);
					File targetFile = new File(PathUtil.getSystempPath()
							+ File.separator + IdGen.uuid()
							+ FileUtil.getFileExtByFileName(fileName));
					FileCopyUtils.copy(bt, targetFile);
					ZipFile zf = new ZipFile(targetFile);
					InputStream in = new BufferedInputStream(
							new FileInputStream(targetFile));
					ZipInputStream zin = new ZipInputStream(in);
					ZipEntry ze = null;
					FileBlobDTO blob = new FileBlobDTO();
					blob.setRealName(fileName);
					blob.setData(bt);
					String primitiveDataFile = fileSystemFeignService
							.uploadFileByClient(blob).getData();
					PointHisotryData5CDTO dto = new PointHisotryData5CDTO();
					dto.setPrimitiveDataFile(primitiveDataFile);
					boolean fg = true;
					String pantographPhotos = "";
					String skatePhotos = "";
					String locomotivePhotos = "";
					while ((ze = zin.getNextEntry()) != null) {
						if (ze.isDirectory()) {
						} else {

							String zipName = ze.getName();
							long size = ze.getSize();
							if (size > 0) {
								if (zipName.endsWith(".xml")) {
									Map<String, String> map = XMLLoader
											.xmlParse(zf.getInputStream(ze));
									fg = setPointHisotryData(dto, map);

								} else {
									FileBlobDTO fileBlob = new FileBlobDTO();
									fileBlob.setRealName(zipName
											.substring(zipName.lastIndexOf("/") + 1));
									fileBlob.setData(StreamUtils
											.InputStreamTOByte(zf
													.getInputStream(ze)));
									String fileId = fileSystemFeignService
											.uploadFileByClient(fileBlob)
											.getData();
									if (StringUtil.isNotBlank(fileId)) {
										String regex1 = "^.+_.+_1_.+$";
										String regex2 = "^.+_.+_2_.+$";
										String regex3 = "^.+_.+_3_.+$";
										if (zipName.matches(regex1)) {
											pantographPhotos = pantographPhotos
													+ "," + fileId;
											// dto.setPantographPhoto(fileId);
										} else if (zipName.matches(regex2)) {
											skatePhotos = skatePhotos + ","
													+ fileId;
											// dto.setSkatePhoto(fileId);
										} else if (zipName.matches(regex3)) {
											locomotivePhotos = locomotivePhotos
													+ "," + fileId;
											// dto.setLocomotivePhoto(fileId);
										}
									}

								}

							}

						}
					}

					if (fg) {
						if (StringUtil.isNotBlank(pantographPhotos)) {
							dto.setPantographPhoto(pantographPhotos.substring(
									1, pantographPhotos.length()));
						}
						if (StringUtil.isNotBlank(skatePhotos)) {
							dto.setSkatePhoto(skatePhotos.substring(1,
									skatePhotos.length()));
						}

						if (StringUtil.isNotBlank(locomotivePhotos)) {
							dto.setLocomotivePhoto(locomotivePhotos.substring(
									1, locomotivePhotos.length()));
						}
						save(dto);
					}
					zin.closeEntry();
					targetFile.delete();
					if (!fg) {
						Shift.fatal(StatusCode.FAIL);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}

	}

	private boolean setPointHisotryData(PointHisotryData5CDTO entity,
			Map<String, String> map) {
		boolean fg = true;
		String checkDate = map.get("date");

		if (StringUtil.isNotBlank(checkDate)) {
			entity.setCheckTime(DateUtils.parseDatetime(checkDate,
					DateUtils.DATE_FORMAT_DEFAULT));
		}
		String cameraNo = map.get("cameraNo");
		entity.setCameraNo(cameraNo);
		String trainNo = map.get("trainNo");
		entity.setTrainNo(trainNo);
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
		}
		return fg;

	}

}
