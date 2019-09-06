package com.togest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.DataDrawingDao;
import com.togest.domain.BatchDrawingData;
import com.togest.domain.DataDrawingDTO;
import com.togest.request.DataDrawingRequest;
import com.togest.service.CrudService;
import com.togest.service.DataDrawingService;
import com.togest.service.SystemPropertyResourcesService;
import com.togest.web.StatusCode;

@Service
public class DataDrawingServiceImpl extends CrudService<DataDrawingDao, DataDrawingDTO> implements DataDrawingService {
	@Autowired
	private SystemPropertyResourcesService systemPropertyResourcesService;

	public List<DataDrawingDTO> getDataDrawingList(String type) {
		List<DataDrawingDTO> list = null;
		if (StringUtil.isNotBlank(type)) {
			list = dao.getListByTypeOrDeptIdsOrAssortmentIdsOrName(type, null, null, null);
		}
		return list;
	}

	public List<DataDrawingDTO> getDataDrawingList(String type, String deptIds) {
		List<DataDrawingDTO> list = null;
		if (StringUtil.isNotBlank(type)) {
			list = dao.getListByTypeOrDeptIdsOrAssortmentIdsOrName(type, deptIds, null, null);
		}
		return list;
	}

	public List<DataDrawingDTO> getDataDrawingList(String type, String deptIds, String assortmentIds) {
		List<DataDrawingDTO> list = dao.getListByTypeOrDeptIdsOrAssortmentIdsOrName(type, deptIds, assortmentIds, null);

		return list;
	}

	public List<DataDrawingDTO> getDataDrawingList(String type, String deptIds, String assortmentIds, String name) {
		List<DataDrawingDTO> list = dao.getListByTypeOrDeptIdsOrAssortmentIdsOrName(type, deptIds, assortmentIds, name);

		return list;
	}

	@Override
	public Map<String, List<Map<String, Object>>> editDataDrawingDataConfig(DataDrawingDTO entity,
			String systemResourcesCode, String dictName, int editTag) {
		return systemPropertyResourcesService.editDataConfig((Map<String, Object>) ObjectUtils.objectToMap(entity),
				systemResourcesCode, dictName, editTag);
	}

	public void batchSave(BatchDrawingData data) {
		if (data != null) {
			String fileIds = data.getFileIds();
			if (StringUtil.isNotBlank(fileIds)) {
				String[] fileId = fileIds.split(",");
				for (int i = 0; i < fileId.length; i++) {
					DataDrawingDTO entity = new DataDrawingDTO();
					entity.setDeptId(data.getDeptId());
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
		DataDrawingDTO entity = new DataDrawingDTO();
		entity.setCode(code);
		DataDrawingDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkNameUniqe(String name, String id) {
		DataDrawingDTO entity = new DataDrawingDTO();
		entity.setName(name);
		DataDrawingDTO dto = dao.getByEntity(entity);
		if (dto == null || dto.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int save(DataDrawingDTO entity) {
		if (entity != null) {
			if (!checkCodeUniqe(entity.getCode(), entity.getId())) {
				Shift.fatal(StatusCode.CODE_REPEAT);
			} else if (StringUtil.isNotBlank(entity.getName()) && !checkNameUniqe(entity.getName(), entity.getId())) {
				Shift.fatal(StatusCode.NAME_REPEAT);
			}
		}

		return super.save(entity);
	}

	@Override
	public List<DataDrawingDTO> getListByDataDrawingRequest(DataDrawingRequest entity) {
		return dao.getListByDataDrawingRequest(entity);
	}

}
