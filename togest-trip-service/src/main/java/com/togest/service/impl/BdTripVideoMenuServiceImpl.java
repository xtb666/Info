package com.togest.service.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.NumberUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.BdTripVideoMenuDao;
import com.togest.domain.Page;
import com.togest.domain.dto.BdTripVideoMenuDTO;
import com.togest.request.BdInfoQueryFilter;
import com.togest.service.BdTripInformationService;
import com.togest.service.BdTripVideoMenuService;
import com.togest.service.CheckLPSService;
import com.togest.service.upgrade.CrudCommonService;
import com.togest.util.MessageUtils;
import com.togest.util.PatternUtils;
import com.togest.util.SDBC;
import com.togest.utils.PageUtils;

@Service
public class BdTripVideoMenuServiceImpl extends CrudCommonService<BdTripVideoMenuDao, BdTripVideoMenuDTO>
		implements BdTripVideoMenuService {

	@Autowired
	private ImportClient importClient;
	@Autowired
	private LineFeignService lineFeignService;
	@Autowired
	private CheckLPSService checkLPSService;
	@Autowired
	private BdTripInformationService bdTripInformationService;
	
	@Override
	public int save(BdTripVideoMenuDTO entity, StringBuilder sb, int i) {
		StringBuilder str = new StringBuilder("");
		try {
			if(StringUtil.isNotBlank(entity.getDistance())) {
				entity.setDistance(PatternUtils.keepThreeXS(entity.getDistance()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(null == sb) {
				Shift.fatal(com.togest.util.StatusCode.SPAN_NOT_A_NUMBER);
			}else {
				str.append("第" + (i + 2) + "行" + com.togest.util.StatusCode.SPAN_NOT_A_NUMBER.getMessage() + "!<br/>");
			}
		}
		try {
			if(StringUtil.isNotBlank(entity.getGlb())) {
				String glb = SDBC.toDBC(entity.getGlb());
				glb = PatternUtils.kilometerStandard(glb);
				entity.setGlb(PatternUtils.keepThreeXS(glb));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(null == sb) {
				Shift.fatal(com.togest.util.StatusCode.GLB_NOT_A_NUMBER);
			}else {
				str.append("第" + (i + 2) + "行" + com.togest.util.StatusCode.GLB_NOT_A_NUMBER.getMessage() + "!<br/>");
			}
		}
		if(null != sb && str.toString().length() > 0) {
			sb.append(str.toString());
			return -1;
		}
		return super.save(entity);
	}
	
	@Override
	public List<BdTripVideoMenuDTO> findTripVideoMenuList(BdInfoQueryFilter entity) {
		List<BdTripVideoMenuDTO> list = dao.findTripVideoMenuList(entity);
//		if(!CollectionUtils.isEmpty(list)) {
//			list.forEach(x ->{
//				x.setGlb(PatternUtils.returnGlb(x.getGlb()));
//			});
//		}
		return list;
	}

	@Override
	public Page<BdTripVideoMenuDTO> findTripVideoMenuPage(Page<BdTripVideoMenuDTO> page, BdInfoQueryFilter entity) {
		bdTripInformationService.checkGlb(entity);
		PageUtils.setPage(page);
		Page<BdTripVideoMenuDTO> pg = PageUtils.buildPage(dao.findTripVideoMenuList(entity));
//		if(!CollectionUtils.isEmpty(pg.getList())) {
//			pg.getList().forEach(x ->{
//				x.setGlb(PatternUtils.returnGlb(x.getGlb()));
//			});
//		}
		return pg;
	}

	@Override
	public List<BdTripVideoMenuDTO> findListByGlb(String lineId, String glb, String distance) {
		BdTripVideoMenuDTO bdTripVideoMenuDTO = new BdTripVideoMenuDTO();
		bdTripVideoMenuDTO.setLineId(lineId);
		bdTripVideoMenuDTO.setGlb(glb);
		bdTripVideoMenuDTO.setDistance(distance);
		List<BdTripVideoMenuDTO> list = dao.findListByGlb(bdTripVideoMenuDTO);
		returnGlb(list);
		return list;
	}

	@Override
	public List<BdTripVideoMenuDTO> findListByDistanceAndGlb(String id, String lineId, String glb, String distance) {
		BdTripVideoMenuDTO bdTripVideoMenuDTO = new BdTripVideoMenuDTO();
		bdTripVideoMenuDTO.setId(id);
		bdTripVideoMenuDTO.setLineId(lineId);
		bdTripVideoMenuDTO.setGlb(glb);
		bdTripVideoMenuDTO.setDistance(distance);
		List<BdTripVideoMenuDTO> list = dao.findListByDistanceAndGlb(bdTripVideoMenuDTO);
		returnGlb(list);
		return list;
	}
	
	private void returnGlb(List<BdTripVideoMenuDTO> list) {
		if(!CollectionUtils.isEmpty(list)) {
			list.forEach(x ->{
				x.setGlb(PatternUtils.returnGlb(x.getGlb()));
			});
		}
	}
	
	@Override
	public Map<String, Object> importDataByConfig(String fileName, InputStream inputStream, String templetId,
			String sectionId, Map<String, Object> propMap) {
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
				List<BdTripVideoMenuDTO> dataList = setData(result);
				for (int i = 0; i < dataList.size(); i++) {
						BdTripVideoMenuDTO entity = dataList.get(i);
						String lineName = entity.getLineName();
						boolean fg = checkLPSService.check(entity, lineMap, lineName, i, errorMsg, 1, MessageUtils.TIPS_NOT_FIND_LINE, MessageUtils.TIPS_NOT_INPUT_LINE);
						checkLPSService.isNotFind(fg, isNotFindLineSet, lineName);
						if (!fg) {
							if(save(entity, errorMsg, i)>0) {
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
	
	private List<BdTripVideoMenuDTO> setData(List<Map<String, Object>> result) {
		List<BdTripVideoMenuDTO> lists = ListUtils.newArrayList();
		for (int i = 0; i < result.size(); i++) {
			BdTripVideoMenuDTO entity = new BdTripVideoMenuDTO();
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
	
	private boolean checkRepeat(BdTripVideoMenuDTO entity) {
		BdTripVideoMenuDTO queryFilter = new BdTripVideoMenuDTO();
		queryFilter.setLineId(entity.getLineId());
 		List<BdTripVideoMenuDTO> BdTripVideoMenuDTOList = findList(queryFilter);
		return !CollectionUtils.isEmpty(BdTripVideoMenuDTOList) ? true : false;
	}
}
