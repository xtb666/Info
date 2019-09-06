package com.togest.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.togest.common.util.string.StringUtil;
import com.togest.dao.CInfoDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.CInfo;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;
import com.togest.response.statistics.DefectFromStatistics;
import com.togest.response.statistics.InfoCAndSize;
import com.togest.service.CInfoService;
import com.togest.utils.PageUtils;

@Service
public class CInfoServiceImpl extends BaseInfoServiceImpl<CInfoDao, CInfo> implements CInfoService {

	@Override
	@DictAggregation
	public Page<CInfo> findPages(Page page, InfoQueryFilter entity) {
		PageUtils.setPage(page);
		Page<CInfo> pg = PageUtils.buildPage(dao.findLists(entity));
		return pg;
	}

	@Override
	public List<Object> findCAndSize(InfoQueryFilter entity) {
		List<InfoCAndSize> list = dao.findCAndSize(entity);
		Map<String, List<InfoCAndSize>> map = list.stream().filter(icas->StringUtil.isNotBlank(icas.getLineId())
				&&StringUtil.isNotBlank(icas.getLineName())).collect(Collectors.groupingBy(InfoCAndSize::getLineId));
		List<Object> l = new LinkedList<>();
		map.forEach((key,value)->{
			Map<String,String> map1 = new HashMap<>();
			map1.put("lineId", key);
			value.forEach(icas->{
				map1.put("lineName", icas.getLineName());
			    if(StringUtil.isNotBlank(icas.getSystemId())){
			    	double value1 =new BigDecimal(icas.getSize()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				map1.put(icas.getSystemId(), icas.getNum()+"份/"+value1+"GB");
			    }
			});
			l.add(map1);
		});
		Map<String,String> map1 = new HashMap<>();
		
		map1.put("lineId", "合计");
		map1.put("lineName", "合计");
		int sumC1 = list.stream().filter(icas->"C1".equals(icas.getSystemId())).mapToInt(InfoCAndSize::getNum).sum();
		int sumC2 = list.stream().filter(icas->"C2".equals(icas.getSystemId())).mapToInt(InfoCAndSize::getNum).sum();
		int sumC4 = list.stream().filter(icas->"C4".equals(icas.getSystemId())).mapToInt(InfoCAndSize::getNum).sum();
		double sizeC1 = list.stream().filter(icas->"C1".equals(icas.getSystemId())).mapToDouble(InfoCAndSize::getSize).sum();
		double sizeC2 = list.stream().filter(icas->"C2".equals(icas.getSystemId())).mapToDouble(InfoCAndSize::getSize).sum();
		double sizeC4 = list.stream().filter(icas->"C4".equals(icas.getSystemId())).mapToDouble(InfoCAndSize::getSize).sum();
		map1.put("C1", sumC1+"份/"+new BigDecimal(sizeC1).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"GB");
		map1.put("C2", sumC2+"份/"+new BigDecimal(sizeC2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"GB");
		map1.put("C4", sumC4+"份/"+new BigDecimal(sizeC4).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"GB");
		l.add(map1);
		return l;
	}

	@Override
	public List<Object> findYAndMon(InfoQueryFilter entity) {
		List<InfoCAndSize> list = dao.findYAndMon(entity);
		
		Map<String, List<InfoCAndSize>> map = list.stream().filter(icas->StringUtil.isNotEmpty(icas.getYear())
				&&StringUtil.isNotEmpty(icas.getMon())).collect(Collectors.groupingBy(InfoCAndSize::getYear));
		List<Object> l = new LinkedList<>();
		map.forEach((key,value)->{
			Map<String,String> map1 = new HashMap<>();
			map1.put("year", key);
			value.forEach(icas->{
				if(StringUtil.isNotBlank(icas.getMon()))
				map1.put(icas.getMon(),""+icas.getNum());
			});
			l.add(map1);
		});
	
		return l;
	}
}
