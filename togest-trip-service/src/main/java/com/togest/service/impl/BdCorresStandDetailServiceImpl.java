package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;

import com.togest.clien.service.SupplyArmFeignService;
import com.togest.code.client.ImportClient;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdCorresStandDetailDao;
import com.togest.domain.BdCorresStandDetail;
import com.togest.domain.Page;
import com.togest.service.BdCorresStandDetailService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.MessageUtils;
import com.togest.util.SDBC;

@Service
public class BdCorresStandDetailServiceImpl extends CrudCommonService<BdCorresStandDetailDao, BdCorresStandDetail>
		implements BdCorresStandDetailService {
	@Autowired
	private ImportClient importClient;
	@Autowired
	private SupplyArmFeignService supplyArmFeignService;
	@Autowired
	private CheckLPSService checkLPSService;
	
	@Transactional
	public int save(BdCorresStandDetail entity) {
		checkGlb(Arrays.asList(entity));
		if(entity.check()) {
			if (entity.getIsNewRecord()) {
				entity.preInsert();
				return dao.insert(entity);
			}else {
				entity.preUpdate();
				return dao.update(entity);
			}
		}
		return -1;
	}
	
	@Override
	public void insertBatch(List<BdCorresStandDetail> list) {
		 checkGlb(list);
		 dao.insertBatch(list);
	}

	private void checkGlb(List<BdCorresStandDetail> list) {
		if(!CollectionUtils.isEmpty(list)) {
			list.forEach(entity ->{
				if(StringUtil.isNotBlank(entity.getGlb())) {
					entity.setGlb(SDBC.toDBC(entity.getGlb()));
				}
				if(StringUtil.isNotBlank(entity.getDistance())) {
					entity.setDistance(SDBC.toDBC(entity.getDistance()));
				}
			});
		}
	}
	@Override
	public List<BdCorresStandDetail> findList(BdCorresStandDetail entity) {
		checkGlb(Arrays.asList(entity));
		return super.findList(entity);
	}
	
	@Override
	public Page<BdCorresStandDetail> findPage(Page<BdCorresStandDetail> page, BdCorresStandDetail entity) {
		checkGlb(Arrays.asList(entity));
		return super.findPage(page, entity);
	}
	
	@Override
	public List<BdCorresStandDetail> findListByDistance(String id, String distance, String glb) {
		BdCorresStandDetail detailParam = new BdCorresStandDetail();
		detailParam.setStandId(id);
		detailParam.setDistance(distance);
		detailParam.setGlb(glb);
		return dao.findListByDistance(detailParam);
	}

	@Override
	public List<BdCorresStandDetail> findListByDistanceAndGlb(String id, String standId, String distance, String glb) {
		BdCorresStandDetail detailParam = new BdCorresStandDetail();
		detailParam.setId(id);
		detailParam.setStandId(standId);
		detailParam.setDistance(distance);
		detailParam.setGlb(glb);
		return dao.findListByDistanceAndGlb(detailParam);
	}

	@Override
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId,
			String standId, String sectionId, Map<String, Object> propMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		int repeatCount = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		Set<String> isNotFindpsaSet = new HashSet<>();
		
		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(fileName, inputStream, templetId);
			// 线路
			Map<String, String> psaMap = new HashMap<String, String>();
			
			List<String> psaNames = CollectionUtils.distinctExtractToList(result, "psaName", String.class);
			
			if (StringUtil.isNotEmpty(psaNames)) {
				List<Map<String, String>> psaList = supplyArmFeignService
						.getIdByNames(CollectionUtils.convertToString(psaNames, ",")).getData();
				if (StringUtil.isNotEmpty(psaList)) {
					for (Map<String, String> map2 : psaList) {
						psaMap.put(map2.get(MessageUtils.NAME), map2.get(MessageUtils.ID));
					}
				}
			}
			
			if (StringUtil.isNotEmpty(result)) {
				List<BdCorresStandDetail> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
						BdCorresStandDetail entity = dataList.get(i);
						entity.setStandId(standId);
						String psaName = entity.getPsaName();
						boolean fg1 = checkLPSService.check(entity, psaMap, psaName, i, errorMsg, 4, 
								MessageUtils.TIPS_NOT_FIND_SUPPLY_ARM, MessageUtils.TIPS_NOT_INPUT_SUPPLY_ARM);
						
						isNotFind(fg1, isNotFindpsaSet, psaName);
						if (!fg1) {
								save(entity);
								count ++;
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
		map.put("isNotFindpsaSet", isNotFindpsaSet);
		return map;
	}

	private List<BdCorresStandDetail> setData(List<Map<String, Object>> result) {
		List<BdCorresStandDetail> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdCorresStandDetail BdCorresStandDetail = new BdCorresStandDetail();
			Map<String, Object> obj = result.get(i);
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			try {
				BeanUtils.populate(BdCorresStandDetail, obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(BdCorresStandDetail);
		}
		return lists;
	}
	
	private void isNotFind(boolean fg, Set<String> isNotFindSet, String name) {
		if(fg) {
			isNotFindSet.add(name);
		}
	}
}
