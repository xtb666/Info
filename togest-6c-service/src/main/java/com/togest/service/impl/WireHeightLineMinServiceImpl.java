package com.togest.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.WireHeightLineMinDao;
import com.togest.domain.WireHeightLineMin;
import com.togest.service.WireHeightLineMinService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.DataUtil;
import com.togest.util.MessageUtils;

@Service
public class WireHeightLineMinServiceImpl extends CrudCommonService<WireHeightLineMinDao, WireHeightLineMin>
		implements WireHeightLineMinService {

	@Autowired
	private ImportClient importClient;
	@Autowired
	private DataUtil dataUtil;
	
	@Override
	public int save(WireHeightLineMin entity) {
		checkRepeat(entity);
		return super.save(entity);
	}
	
	
	@Override
	public Map<String, Object> importData(String fileName, InputStream inputStream, String templetId, Object object,
			Map<String, String> propMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		StringBuilder errorMsg = new StringBuilder();

		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			if (StringUtil.isNotEmpty(result)) {
				List<String> lineNames = CollectionUtils.distinctExtractToList(result, "lineName", String.class);
				Map<String, String> lineMap = dataUtil.getLineIdByNames(lineNames);
				List<WireHeightLineMin> dataList = DataUtil.setData(result, WireHeightLineMin.class);
				for(int i = 0; i < dataList.size(); i++) {
					WireHeightLineMin entity = dataList.get(i);
					String lineId = lineMap.get(entity.getLineName());
					entity.setLineId(lineId);
					boolean fg = checkTips(entity, i+2, errorMsg, propMap);
					if(!fg) {
						save(entity);
						count++;
					}
				}
			}
		}catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		return map;
	}

	private boolean checkRepeat(WireHeightLineMin entity) {
		WireHeightLineMin wireHeightLineMin = new WireHeightLineMin();
		wireHeightLineMin.setLineId(entity.getLineId());
		wireHeightLineMin.setDelFlag(0);
		List<WireHeightLineMin> wireHeightLineMinList = findList(wireHeightLineMin);
		WireHeightLineMin dto = !CollectionUtils.isEmpty(wireHeightLineMinList) ? wireHeightLineMinList.get(0) : null;
		if (dto == null) {
		     return false;
		}else {
			entity.setId(dto.getId());
			return true;
		}
	}

	private boolean checkTips(WireHeightLineMin entity, int i, StringBuilder errorMsg, Map<String,String> propMap) {
		boolean fg = false;
		//线路
		if(StringUtil.isBlank(entity.getLineName())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_INPUT_LINE + "!<br/>");
			fg = true;
		}else if(StringUtil.isBlank(entity.getLineId())) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_LINE + "!<br/>");
			fg = true;
		}
		if(null == entity.getMinValue() || entity.getMinValue() <= 0) {
			errorMsg.append("第" + (i) + "" + MessageUtils.TIPS_NOT_FIND_LINE_MIN_VALUE + "!<br/>");
			fg = true;
		}
		return fg;
	}
}
