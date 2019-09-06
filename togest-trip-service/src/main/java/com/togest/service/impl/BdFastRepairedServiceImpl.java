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
import com.togest.dao.BdFastRepairedDao;
import com.togest.domain.Page;
import com.togest.domain.dto.BdFastRepairedDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdFastRepairedService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.FileUtils;
import com.togest.util.MessageUtils;
import com.togest.utils.PageUtils;

@Service
public class BdFastRepairedServiceImpl extends CrudCommonService<BdFastRepairedDao, BdFastRepairedDTO>
		implements BdFastRepairedService {

	@Autowired
	private ImportClient importClient;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private CheckLPSService checkLPSService;
	
	@Autowired
	private FileUtils<BdFastRepairedDTO> fileUtils;
	
	@Override
	public BdFastRepairedDTO get(String id) {
		BdFastRepairedDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity);
		return entity;
	}
	
	@Override
	public BdFastRepairedDTO get(String id, int status) {
		BdFastRepairedDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity, status);
		return entity;
	}
	
	@Override
	public BdFastRepairedDTO get(BdFastRepairedDTO entity) {
		 BdFastRepairedDTO BdFastRepairedDTO = super.get(entity);
		 fileUtils.getFileBlobDTOByIds(BdFastRepairedDTO);
		 return BdFastRepairedDTO;
	}
	
	@Override
	public List<BdFastRepairedDTO> findFastReaireList(BdInfoQueryFilter entity) {
		List<BdFastRepairedDTO> list = dao.findFastReaireList(entity);
		if(StringUtil.isNotEmpty(list)) {
			list.forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
		}
		return list;
	}

	@Override
	public Page<BdFastRepairedDTO> findFastReairePage(Page<BdFastRepairedDTO> page, BdInfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<BdFastRepairedDTO> pg = PageUtils.buildPage(dao.findFastReaireList(entity));
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
				List<BdFastRepairedDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
						BdFastRepairedDTO entity = dataList.get(i);
						String lineName = entity.getLineName();
						boolean fg = checkLPSService.check(entity, lineMap, lineName, i, errorMsg, 1, MessageUtils.TIPS_NOT_FIND_LINE, MessageUtils.TIPS_NOT_INPUT_LINE);
						checkLPSService.isNotFind(fg, isNotFindLineSet, lineName);
						if (!fg) {
							if(checkRepeat(entity)) {
								repeatCount ++; 
								repeatStr.append("第" + (i + 2) + "行," + "此抢修路径图已经存在: "
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
	
	private List<BdFastRepairedDTO> setData(List<Map<String, Object>> result) {
		List<BdFastRepairedDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdFastRepairedDTO entity = new BdFastRepairedDTO();
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
	
	private boolean checkRepeat(BdFastRepairedDTO entity) {
		BdFastRepairedDTO queryFilter = new BdFastRepairedDTO();
		queryFilter.setLineId(entity.getLineId());
 		List<BdFastRepairedDTO> BdFastRepairedDTOList = findList(queryFilter);
		return !CollectionUtils.isEmpty(BdFastRepairedDTOList) ? true : false;
	}
}