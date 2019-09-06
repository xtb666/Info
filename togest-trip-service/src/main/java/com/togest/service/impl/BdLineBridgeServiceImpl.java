package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.service.LineFeignService;
import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdLineBridgeDao;
import com.togest.domain.Page;
import com.togest.domain.dto.BdLineBridgeDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdLineBridgeService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.FileUtils;
import com.togest.util.MessageUtils;
import com.togest.utils.PageUtils;

@Service
public class BdLineBridgeServiceImpl extends CrudCommonService<BdLineBridgeDao, BdLineBridgeDTO>
		implements BdLineBridgeService {

	@Autowired
	private ImportClient importClient;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private CheckLPSService checkLPSService;
	@Autowired
	private FileUtils<BdLineBridgeDTO> fileUtils;
	
	@Override
	public BdLineBridgeDTO get(String id) {
		BdLineBridgeDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity);
		return entity;
	}
	
	@Override
	public BdLineBridgeDTO get(String id, int status) {
		BdLineBridgeDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity, status);
		return entity;
	}
	
	@Override
	public List<BdLineBridgeDTO> findLineBridgeList(BdInfoQueryFilter entity) {
		List<BdLineBridgeDTO> list = dao.findLineBridgeList(entity);
		if(StringUtil.isNotEmpty(list)) {
			list.forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
		}
		return list;
	}

	@Override
	public Page<BdLineBridgeDTO> findLineBridgePage(Page<BdLineBridgeDTO> page, BdInfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<BdLineBridgeDTO> pg = PageUtils.buildPage(dao.findLineBridgeList(entity));
		if(StringUtil.isNotEmpty(pg.getList())){
	    	pg.getList().forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
        }
		return pg;
	}

	@Override
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId,
			String sectionId, Map<String, Object> propMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		Set<String> isNotFindLineSet = new HashSet<>();

		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			Map<String, String> lineMap = new HashMap<String, String>();
			List<String> lineNames = CollectionUtils.distinctExtractToList(result, "lineName", String.class);
			if (StringUtil.isNotEmpty(lineNames)) {
				List<Map<String, String>> lineList = lineFeignService
						.getIdByNames(CollectionUtils.convertToString(lineNames, ",")).getData();
				if (StringUtil.isNotEmpty(lineList)) {
					for (Map<String, String> map2 : lineList) {
						lineMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			
			if (StringUtil.isNotEmpty(result)) {
				List<BdLineBridgeDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
						BdLineBridgeDTO entity = dataList.get(i);
						String lineName = entity.getLineName();
						boolean fg = checkLPSService.check(entity, lineMap, lineName, i, errorMsg, 1, MessageUtils.TIPS_NOT_FIND_LINE, MessageUtils.TIPS_NOT_INPUT_LINE);
						checkLPSService.isNotFind(fg, isNotFindLineSet, lineName);
						if (!fg) {
							if(checkRepeat(entity)) {
								repeatCount ++; 
								repeatStr.append("第" + (i + 2) + "行," + "此上跨线、桥分布已经存在: "
										+  "线别:"     + lineName + "<br/>");
							}else {
								save(entity);
								count ++;
							}
						}
				}
			}else {
				errorMsg.append("数据为空,请修改后上传");
			}
		} catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		map.put("repeatCount", repeatCount);
		map.put("repeatMsg", repeatStr.toString());
		map.put("isNotFindLineSet", isNotFindLineSet);
		return map;
	}
	
	private List<BdLineBridgeDTO> setData(List<Map<String, Object>> result) {
		List<BdLineBridgeDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdLineBridgeDTO entity = new BdLineBridgeDTO();
			Map<String, Object> obj = result.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(entity, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(entity);
		}
		return lists;
	}
	
	private boolean checkRepeat(BdLineBridgeDTO entity) {
		BdLineBridgeDTO queryFilter = new BdLineBridgeDTO();
		queryFilter.setLineId(entity.getLineId());
 		List<BdLineBridgeDTO> BdLineBridgeDTOList = findList(queryFilter);
		return !CollectionUtils.isEmpty(BdLineBridgeDTOList) ? true : false;
	}
}
