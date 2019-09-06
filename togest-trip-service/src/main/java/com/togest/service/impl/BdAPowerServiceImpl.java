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

import com.togest.clien.service.JcwServiceClient;
import com.togest.clien.service.LineFeignService;
import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdAPowerDao;
import com.togest.domain.Page;
import com.togest.domain.dto.BdAPowerDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdAPowerService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.FileUtils;
import com.togest.util.MessageUtils;
import com.togest.utils.PageUtils;

@Service
public class BdAPowerServiceImpl extends CrudCommonService<BdAPowerDao, BdAPowerDTO>
		implements BdAPowerService {
	@Autowired
	private ImportClient importClient;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private JcwServiceClient jcwServiceClient;
	@Autowired
	private CheckLPSService checkLPSService;
	@Autowired
	private FileUtils<BdAPowerDTO> fileUtils;
	
	@Override
	public BdAPowerDTO get(String id) {
		BdAPowerDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity);
		return entity;
	}
	
	public BdAPowerDTO get(String id, int status) {
		BdAPowerDTO entity = super.get(id);
		fileUtils.getFileBlobDTOByIds(entity, status);
		return entity;
	}
	
	@Override
	public BdAPowerDTO get(BdAPowerDTO entity) {
		 BdAPowerDTO bdAPowerDTO = super.get(entity);
		 fileUtils.getFileBlobDTOByIds(bdAPowerDTO);
		 return bdAPowerDTO;
	}
	
	@Override
	public List<BdAPowerDTO> findAPowerList(BdInfoQueryFilter entity) {
		List<BdAPowerDTO> list = dao.findAPowerList(entity);
		if(StringUtil.isNotEmpty(list)) {
			list.forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
		}
		return list;
	}

	@Override
	public Page<BdAPowerDTO> findAPowerPage(Page<BdAPowerDTO> page, BdInfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<BdAPowerDTO> pg = PageUtils.buildPage(dao.findAPowerList(entity));
		if(StringUtil.isNotEmpty(pg.getList())){
	    	pg.getList().forEach(x ->{
	    		fileUtils.getFileBlobDTOByIds(x);
	    	});
        }
		return pg;
	}

	@Override
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream,
			String templetId, String sectionId, Map<String, Object> propMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		Set<String> isNotFindLineSet = new HashSet<>();
		Set<String> isNotFindPsPdSet = new HashSet<>();
		Set<String> isNotFindPavilionSet = new HashSet<>();

//		Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
//		Map<String, List<DictionaryItemDTO>> tempMap = dictionaryService.
//				findDictItemsByDictName(new String[] { "direction" }).getData();
//		dictMap = DictionaryUtil.convertAllDictionaryMap(tempMap, false);
		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			// 线路
			Map<String, String> lineMap = new HashMap<String, String>();
			Map<String,String> psPdMap = new HashMap<>();
			Map<String,String> pavilionMap = new HashMap<>();
			
			List<String> lineNames = CollectionUtils.distinctExtractToList(result, "lineName", String.class);
			List<String> psPdNames = CollectionUtils.distinctExtractToList(result, "psPdName", String.class);
			List<String> pavilionNames = CollectionUtils.distinctExtractToList(result, "pavilionName", String.class);
			
			if (StringUtil.isNotEmpty(lineNames)) {
				List<Map<String, String>> lineList = lineFeignService
						.getIdByNames(CollectionUtils.convertToString(lineNames, ",")).getData();
				if (StringUtil.isNotEmpty(lineList)) {
					for (Map<String, String> map2 : lineList) {
						lineMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			if (StringUtil.isNotEmpty(psPdNames)) {
				List<Map<String, String>> psPdList = jcwServiceClient
						.getIdByNames(CollectionUtils.convertToString(psPdNames, ",")).getData();
				if (StringUtil.isNotEmpty(psPdList)) {
					for (Map<String, String> map2 : psPdList) {
						psPdMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			if (StringUtil.isNotEmpty(pavilionNames)) {
				List<Map<String, String>> pavilionList = jcwServiceClient
						.getPavilionsIdByNames(CollectionUtils.convertToString(pavilionNames, ",")).getData();
				if (StringUtil.isNotEmpty(pavilionList)) {
					for (Map<String, String> map2 : pavilionList) {
						pavilionMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			
			if (StringUtil.isNotEmpty(result)) {
				List<BdAPowerDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
						BdAPowerDTO entity = dataList.get(i);
						String lineName = entity.getLineName();
						String psPdName = entity.getPsPdName();
						String pavilionName = entity.getPavilionName();
						
						boolean fg1 = checkLPSService.check(entity, lineMap, lineName, i, errorMsg, 1, MessageUtils.TIPS_NOT_FIND_LINE, MessageUtils.TIPS_NOT_INPUT_LINE);
						boolean fg2 = checkLPSService.check(entity, psPdMap, psPdName, i, errorMsg, 2, MessageUtils.TIPS_NOT_FIND_PSPD, MessageUtils.TIPS_NOT_INPUT_PSPD);
						boolean fg3 = checkLPSService.check(entity, pavilionMap, pavilionName, i, errorMsg, 3, MessageUtils.TIPS_NOT_FIND_PAVILION, MessageUtils.TIPS_NOT_INPUT_PAVILION);
						checkLPSService.isNotFind(fg1, isNotFindLineSet, lineName);
						checkLPSService.isNotFind(fg2, isNotFindPsPdSet, psPdName);
						checkLPSService.isNotFind(fg3, isNotFindPavilionSet, pavilionName);
						
						if (!fg1 && !fg2 && !fg3) {
							if(checkRepeat(entity)) {
								repeatCount ++; 
								repeatStr.append("第" + (i + 2) + "行," + "此越区供电方案已经存在: "
										+  "线别:"     + lineName 
										+  ",供电臂:"  + psPdName 
										+  ",变电所 :"  + pavilionName + "<br/>");
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
		map.put("isNotFindPsPdSet", isNotFindPsPdSet);
		map.put("isNotFindPavilionSet", isNotFindPavilionSet);
		return map;
	}

	private List<BdAPowerDTO> setData(List<Map<String, Object>> result) {
		List<BdAPowerDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdAPowerDTO bdAPowerDTO = new BdAPowerDTO();
			Map<String, Object> obj = result.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(bdAPowerDTO, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(bdAPowerDTO);
		}
		return lists;
	}
	
	private boolean checkRepeat(BdAPowerDTO entity) {
		BdAPowerDTO queryFilter = new BdAPowerDTO();
		queryFilter.setLineId(entity.getLineId());
		queryFilter.setPavilionId(entity.getPavilionId());
		queryFilter.setPsPdId(entity.getPsPdId());
 		List<BdAPowerDTO> bdAPowerDTOList = findList(queryFilter);
		return !CollectionUtils.isEmpty(bdAPowerDTOList) ? true : false;
	}
}