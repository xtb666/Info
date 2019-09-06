package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectAssortmentDTO;

public interface DefectAssortmentService extends ICrudService<DefectAssortmentDTO> {

	public int deleteFalses(String ids, String deleteBy, String deleteIp);
	
	public List<DefectAssortmentDTO> getBySystemId(String systemId);

	public void handleDefectData(List<Defect> list);

	public void handleC1DefectData(List<Defect1CDTO> list);

	public void handleDefectData(List<Defect> list, List<DefectAssortmentDTO> defectAssortments);

	public void handleC1DefectData(List<Defect1CDTO> list, List<DefectAssortmentDTO> defectAssortments);

	public Map<String, Object> importData(String fileName, InputStream inputStream, String systemId, String templetId,
			String createBy, String sectionId);
}
