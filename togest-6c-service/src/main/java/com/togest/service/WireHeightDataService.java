package com.togest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.togest.domain.PillarInfoDTO;
import com.togest.domain.PillarInfoSelectorV2DTO;
import com.togest.domain.WireHeightDataDTO;
import com.togest.service.upgrade.ICrudCommonService;

public interface WireHeightDataService extends ICrudCommonService<WireHeightDataDTO> {

	public int save(WireHeightDataDTO entity, int status);
	
	public Map<String, Object> importData(String fileName, InputStream inputStream, String templetId,
			String sectionId, Map<String, String> propMap, String systemId, String dymType);

	public Map<String,Object> findDataAnaliayByDateList(WireHeightDataDTO entity);

	public List<PillarInfoSelectorV2DTO> pillarSelector(PillarInfoDTO pillarInfoDTO);

	public Map<String, Object> history(WireHeightDataDTO wireHeightDataDTO);
	
	public WireHeightDataDTO findUpWireHeightData(WireHeightDataDTO wireHeightDTO);
	
	public Map<String,List<WireHeightDataDTO>> findUpWireHeightDataList(List<WireHeightDataDTO> wireHeightDataDTOList);
}
