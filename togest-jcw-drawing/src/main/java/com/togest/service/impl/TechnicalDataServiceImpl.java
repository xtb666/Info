package com.togest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.TechnicalDataDao;
import com.togest.domain.BatchTechnicalData;
import com.togest.domain.TechnicalDataDTO;
import com.togest.request.TechnicalDataRequest;
import com.togest.service.CrudService;
import com.togest.service.SystemPropertyResourcesService;
import com.togest.service.TechnicalDataService;
import com.togest.web.StatusCode;

@Service
public class TechnicalDataServiceImpl extends
		CrudService<TechnicalDataDao, TechnicalDataDTO> implements
		TechnicalDataService {
	@Autowired
	private SystemPropertyResourcesService systemPropertyResourcesService;

	public List<TechnicalDataDTO> getList(String type) {
		List<TechnicalDataDTO> list = null;
		if (StringUtil.isNotBlank(type)) {
			list = dao.getListByTypeOrAssortmentIdsOrName(type, null, null);
		}
		return list;

	}

	public List<TechnicalDataDTO> getList(String type, String assortmentIds) {
		List<TechnicalDataDTO> list = dao.getListByTypeOrAssortmentIdsOrName(
				type, assortmentIds, null);

		return list;
	}

	public List<TechnicalDataDTO> getList(String type, String assortmentIds,
			String name) {
		List<TechnicalDataDTO> list = dao.getListByTypeOrAssortmentIdsOrName(
				type, assortmentIds, name);

		return list;
	}

	@Override
	public Map<String, List<Map<String, Object>>> editTechnicalDataDataConfig(
			TechnicalDataDTO entity, String systemResourcesCode,
			String dictName, int editTag) {
		// TODO Auto-generated method stub
		return systemPropertyResourcesService.editDataConfig(
				(Map<String, Object>) ObjectUtils.objectToMap(entity),
				systemResourcesCode, dictName, editTag);
	}

	public void batchSave(BatchTechnicalData data) {
		if (data != null) {
			String fileIds = data.getFileIds();
			if (StringUtil.isNotBlank(fileIds)) {
				String[] fileId = fileIds.split(",");
				for (int i = 0; i < fileId.length; i++) {
					TechnicalDataDTO entity = new TechnicalDataDTO();
					entity.setName(data.getName() + (data.getStartNum() + i));
					entity.setAssortmentId(data.getAssortmentId());
					entity.setUploadDate(data.getUploadDate());
					entity.setType(data.getType());
					entity.setVersion(data.getVersion());
					entity.setFileId(fileId[i]);
					this.save(entity);
				}
			}
		}
	}

	public boolean checkCodeUniqe(String code, String id) {
		TechnicalDataDTO entity = new TechnicalDataDTO();
		entity.setCode(code);
		TechnicalDataDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkNameUniqe(String name, String id) {
		TechnicalDataDTO entity = new TechnicalDataDTO();
		entity.setName(name);
		TechnicalDataDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int save(TechnicalDataDTO entity) {
		if (entity != null) {
			if (!checkCodeUniqe(entity.getCode(), entity.getId())) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			} else if (!checkNameUniqe(entity.getName(), entity.getId())) {
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
		}

		return super.save(entity);
	}

	@Override
	public List<TechnicalDataDTO> getListByTechnicalDataRequest(
			TechnicalDataRequest entity) {
		return dao.getListByTechnicalDataRequest(entity);
	}
}
