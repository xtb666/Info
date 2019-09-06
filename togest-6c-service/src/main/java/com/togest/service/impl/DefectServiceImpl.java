package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.resquest.ProcessInstanceDelRequest;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.code.Encodes;
import com.togest.common.util.json.JSONUtil;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectRepeatCountDao;
import com.togest.dao.DefectRepeatHistoryDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Defect;
import com.togest.domain.Defect1CDTO;
import com.togest.domain.DefectAssortmentDTO;
import com.togest.domain.DefectBasicDetails;
import com.togest.domain.DefectDTO;
import com.togest.domain.DefectFlow;
import com.togest.domain.DefectRepeatCount;
import com.togest.domain.DefectStatistical;
import com.togest.domain.DefectStatisticalHandle;
import com.togest.domain.Page;
import com.togest.domain.SimplePage;
import com.togest.request.CQueryFilter;
import com.togest.request.DefectAddField;
import com.togest.request.DefectRepeatRequest;
import com.togest.response.DefectFormDTO;
import com.togest.response.DefectRepeatData;
import com.togest.service.Defect1CService;
import com.togest.service.DefectAssortmentService;
import com.togest.service.DefectFlowService;
import com.togest.service.DefectService;
import com.togest.util.HttpURLUtil;
import com.togest.utils.PageUtils;

@Service
public class DefectServiceImpl implements DefectService {

	@Autowired
	private DefectDao defectDao;

	@Autowired
	private DefectRepeatCountDao defectRepeatCountDao;
	@Autowired
	private DefectRepeatHistoryDao defectRepeatHistoryDao;
	@Autowired
	private DefectFlowService defectFlowService;
	@Autowired
	private WorkflowFeignService workflowFeignService;
	@Autowired
	private DefectAssortmentService defectAssortmentService;
	@Autowired
	private Defect1CService defect1CService;
	private Logger log = LoggerFactory.getLogger(DefectServiceImpl.class);

	private static final Double GLBDISTANCE2 = 0.02;
	private static final Double GLBDISTANCE3 = 0.03;
	private static final Double GLBDISTANCE4 = 0.04;
	private static final Double GLBDISTANCE5 = 0.05;
	private static final Double GLBDISTANCE6 = 0.1;
	private static final Double GLBDISTANCE7 = 0.15;
	private static final Double GLBDISTANCE8 = 0.2;
	private static final Integer NOT_SHOW = 0;
	private static final Integer IS_SHOW = 1;

	@Override
	@DictAggregation
	public List<DefectDTO> getHistoryDefect(CQueryFilter entity) {

		return defectDao.getHistoryDefect(entity);
	}

	@Override
	@DictAggregation
	public Page<DefectDTO> getHistoryDefect(Page page, CQueryFilter entity) {
		PageUtils.setPage(page);
		entity.setPage(page);
		return PageUtils.buildPage(defectDao.getHistoryDefect(entity));
	}

	public void sendAuditDefectData(String url, List<String> defectIds, String confirmPerson, Date confirmDate) {
		try {
			List<Defect> list = defectDao.getByKeys(defectIds);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("defect", list);
			// params.put("confirmPerson", confirmPerson);
			// params.put("confirmDate", confirmDate);
			// HttpReqeust.httpPostforString(url, params);
			String rs = HttpURLUtil.getDataByUrl(url, Encodes.urlEncode(JSONUtil.objectToJson(params)));
			System.out.println(rs + "-----------------------------------1111111111");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("6C缺陷同步失败：" + defectIds);
			e.printStackTrace();

		}

	}

	public void sendCancelDefectData(String url, List<String> defectIds, String cancelPerson, Date cancelDate,
			String comment) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("defect", defectIds);
			params.put("cancelPerson", cancelPerson);
			params.put("cancelDate", cancelDate);
			params.put("comment", comment);
			// HttpReqeust.httpPostforString(url, params);
			String rs = HttpURLUtil.getDataByUrl(url, Encodes.urlEncode(JSONUtil.objectToJson(params)));
			System.out.println(rs + "-----------------------------------33333333333333");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("6C缺陷销号同步失败：" + defectIds);
			e.printStackTrace();
		}

	}

	public void deleteFalse(List<String> defectIds, String deleteBy, String deleteIp) {
		if (StringUtil.isNotEmpty(defectIds)) {
			defectDao.deleteFalses(defectIds, deleteBy, deleteIp);
			defectRepeatHistoryDao.delete(defectIds);
			List<ProcessInstanceDelRequest> lists = new ArrayList<>();
			List<DefectFlow> flows = defectFlowService.getBykeys(defectIds);
			if (StringUtil.isNotEmpty(flows)) {
				for (DefectFlow defectFlow : flows) {
					ProcessInstanceDelRequest processInstanceDelRequest = new ProcessInstanceDelRequest();
					processInstanceDelRequest.setProcessInstanceId(defectFlow.getProcessInstanceId());
					processInstanceDelRequest.setDeleteReason(deleteBy);
					lists.add(processInstanceDelRequest);
				}
				if (StringUtil.isNotEmpty(lists)) {
					workflowFeignService.deleteTask(lists);
				}
			}
		}
	}

	// @Scheduled(cron = "0 */10 * * * ?")
	public void defectRepeatData() {
		List<DefectRepeatRequest> defectRepeatRequests = new ArrayList<>();
		List<DefectRepeatRequest> dr = defectDao.checkRepeatCondition(false);
		if (StringUtil.isNotEmpty(dr)) {
			defectRepeatRequests.addAll(dr);
		}
		List<DefectRepeatRequest> dr1 = defectDao.checkRepeatCondition(true);
		if (StringUtil.isNotEmpty(dr1)) {
			defectRepeatRequests.addAll(dr1);
		}
		if (StringUtil.isNotEmpty(defectRepeatRequests)) {
			for (DefectRepeatRequest defectRepeatRequest : defectRepeatRequests) {
				defectRepeatData(defectRepeatRequest);
			}
		}
	}

	public void defectRepeatData(DefectRepeatRequest entity) {
		if (StringUtil.isEmpty(entity.getCheckDateYear())) {
			return;
		}
		CQueryFilter c = new CQueryFilter();
		c.setCheckDate(DateUtils.parse(entity.getCheckDateYear(), "yyyy"));
		c.setLineId(entity.getLineId());
		c.setDefectType(entity.getDefectType());
		c.setDirection(entity.getDirection());
		c.setSystemId(entity.getSystemId());

		if (StringUtil.isNotBlank(entity.getDefectDataCategory())
				&& StringUtil.isNotBlank(entity.getDefectDataLevel())) {
			c.setDefectDataCategory(entity.getDefectDataCategory());
			c.setDefectDataLevel(entity.getDefectDataLevel());
			// c.setBeginCheckDate(DateUtils.parse("2018-08-10",
			// DateUtils.DATE_FORMAT_YMD));
		} else {
			c.setDefectLevel(entity.getDefectLevel());
		}
		// c.setDefectAssortmentId(entity.getDefectAssortmentId());

		if (!(DefectSystem.defectC1.getStatus().equals(entity.getSystemId())
				|| DefectSystem.defectC3.getStatus().equals(entity.getSystemId()))) {
			c.setPillarId(entity.getPillarId());
		}
		List<DefectRepeatData> defectRepeatDatas = defectDao.findDefectRepeatData(c);
		if (StringUtil.isNotEmpty(defectRepeatDatas)) {
			if (DefectSystem.defectC1.getStatus().equals(entity.getSystemId())
					|| DefectSystem.defectC3.getStatus().equals(entity.getSystemId())) {
				Map<String, DefectRepeatCount> map2 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE2);
				Map<String, DefectRepeatCount> map3 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE3);
				Map<String, DefectRepeatCount> map4 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE4);
				Map<String, DefectRepeatCount> map5 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE5);
				Map<String, DefectRepeatCount> map6 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE6);
				Map<String, DefectRepeatCount> map7 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE7);
				Map<String, DefectRepeatCount> map8 = updateRepeatCountByGlb(defectRepeatDatas, GLBDISTANCE8);
				if (StringUtil.isNotEmpty(map2)) {
					List<DefectRepeatCount> list = new ArrayList<>();
					for (String key : map2.keySet()) {
						DefectRepeatCount drc = map2.get(key);
						drc.setCountb(map3.get(key).getCount());
						drc.setCountc(map4.get(key).getCount());
						drc.setCountd(map5.get(key).getCount());
						drc.setCounte(map6.get(key).getCount());
						drc.setCountf(map7.get(key).getCount());
						drc.setCountg(map8.get(key).getCount());
						drc.setCount2(map3.get(key).getCount1());
						drc.setCount3(map4.get(key).getCount1());
						drc.setCount4(map5.get(key).getCount1());
						drc.setCount5(map6.get(key).getCount1());
						drc.setCount6(map7.get(key).getCount1());
						drc.setCount7(map8.get(key).getCount1());
						drc.setDefectId2(map3.get(key).getDefectId1());
						drc.setDefectId3(map4.get(key).getDefectId1());
						drc.setDefectId4(map5.get(key).getDefectId1());
						drc.setDefectId5(map6.get(key).getDefectId1());
						drc.setDefectId6(map7.get(key).getDefectId1());
						drc.setDefectId7(map8.get(key).getDefectId1());
						drc.setDefectRepeatIdsb(map3.get(key).getDefectRepeatIds());
						drc.setDefectRepeatIdsc(map4.get(key).getDefectRepeatIds());
						drc.setDefectRepeatIdsd(map5.get(key).getDefectRepeatIds());
						drc.setDefectRepeatIdse(map6.get(key).getDefectRepeatIds());
						drc.setDefectRepeatIdsf(map7.get(key).getDefectRepeatIds());
						drc.setDefectRepeatIdsg(map8.get(key).getDefectRepeatIds());
						list.add(drc);
					}
					updateRepeatCount(list);
				}
			} else {
				Map<String, DefectRepeatCount> map2 = updateRepeatCountData(defectRepeatDatas);
				if (StringUtil.isNotEmpty(map2)) {
					List<DefectRepeatCount> list = new ArrayList<>();
					for (String key : map2.keySet()) {
						list.add(map2.get(key));
					}
					updateRepeatCount(list);
				}
			}
		}
	}

	private void updateRepeatCount(List<DefectRepeatCount> list) {
		if (StringUtil.isNotEmpty(list)) {
			List<String> ids = CollectionUtils.distinctExtractToList(list, "id", String.class);
			List<DefectRepeatCount> DefectRepeatCounts = defectRepeatCountDao.getByKeys(ids);
			Map<String, DefectRepeatCount> map = new HashMap<String, DefectRepeatCount>();
			if (StringUtil.isNotEmpty(DefectRepeatCounts)) {
				for (DefectRepeatCount defectRepeatCount : DefectRepeatCounts) {
					map.put(defectRepeatCount.getId(), defectRepeatCount);
				}
			}
			for (DefectRepeatCount defectRepeatCount : list) {
				DefectRepeatCount d = map.get(defectRepeatCount.getId());
				if (d == null) {
					defectRepeatCountDao.insert(defectRepeatCount);
				} else {
					if (defectRepeatCount.getCount() != d.getCount()
					    	|| defectRepeatCount.getCountb() != d.getCountb()
					    	|| defectRepeatCount.getCountc() != d.getCountc()
					    	|| defectRepeatCount.getCountd() != d.getCountd()
					    	|| defectRepeatCount.getCounte() != d.getCounte()
					    	|| defectRepeatCount.getCountf() != d.getCountf()
					    	|| defectRepeatCount.getCountg() != d.getCountg()																																			
							|| defectRepeatCount.getCount1() != d.getCount1()
							|| defectRepeatCount.getCount2() != d.getCount2()
							|| defectRepeatCount.getCount3() != d.getCount3()
							|| defectRepeatCount.getCount4() != d.getCount4()
							|| defectRepeatCount.getCount5() != d.getCount5()
							|| defectRepeatCount.getCount6() != d.getCount6()
							|| defectRepeatCount.getCount7() != d.getCount7()
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIds() != null
									&& !defectRepeatCount.getDefectRepeatIds().equals(d.getDefectRepeatIds()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIdsb() != null
									&& !defectRepeatCount.getDefectRepeatIdsb().equals(d.getDefectRepeatIdsb()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIdsc() != null
									&& !defectRepeatCount.getDefectRepeatIdsc().equals(d.getDefectRepeatIdsc()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIdsd() != null
									&& !defectRepeatCount.getDefectRepeatIdsd().equals(d.getDefectRepeatIdsd()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIdse() != null
									&& !defectRepeatCount.getDefectRepeatIdse().equals(d.getDefectRepeatIdse()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIdsf() != null
									&& !defectRepeatCount.getDefectRepeatIdsf().equals(d.getDefectRepeatIdsf()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectRepeatIdsg() != null
									&& !defectRepeatCount.getDefectRepeatIdsg().equals(d.getDefectRepeatIdsg()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId1() != null
									&& !defectRepeatCount.getDefectId1().equals(d.getDefectId1()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId2() != null
									&& !defectRepeatCount.getDefectId2().equals(d.getDefectId2()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId3() != null
									&& !defectRepeatCount.getDefectId3().equals(d.getDefectId3()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId4() != null
									&& !defectRepeatCount.getDefectId4().equals(d.getDefectId4()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId5() != null
									&& !defectRepeatCount.getDefectId5().equals(d.getDefectId5()))
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId6() != null
									&& !defectRepeatCount.getDefectId6().equals(d.getDefectId6()))																											
							|| (defectRepeatCount != null && d != null && defectRepeatCount.getDefectId7() != null
									&& !defectRepeatCount.getDefectId7().equals(d.getDefectId7()))) {
						defectRepeatCountDao.updateRepeatCount(defectRepeatCount);
					}
				}
			}
		}

	}

	private Map<String, DefectRepeatCount> updateRepeatCountByGlb(List<DefectRepeatData> list, Double glbDistance) {
		Map<String, DefectRepeatCount> map = new HashMap<String, DefectRepeatCount>();
		if (StringUtil.isNotEmpty(list)) {

			checkDateSort(list, false);
			List<List<DefectRepeatData>> DefectRepeatDataLists = repeatCountByGlb(list, glbDistance);
			if (StringUtil.isNotEmpty(DefectRepeatDataLists)) {
				for (List<DefectRepeatData> DefectRepeatDataList : DefectRepeatDataLists) {
					map.putAll(updateRepeatCountData(DefectRepeatDataList));
				}
			}
		}
		return map;

	}

	private Map<String, DefectRepeatCount> updateRepeatCountData(List<DefectRepeatData> list) {
		Map<String, DefectRepeatCount> map = new HashMap<String, DefectRepeatCount>();
		if (StringUtil.isNotEmpty(list)) {
			checkDateSort(list, true);
			List<String> defectIds = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				DefectRepeatData defectRepeatData = list.get(i);
				defectIds.add(defectRepeatData.getId());
				DefectRepeatCount drc = new DefectRepeatCount();
				drc.setId(defectRepeatData.getId());
				if (i == 0) {
					drc.setCount(i);
				} else {
					drc.setCount(i + 1);
				}
				if (list.size() == 1) {
					drc.setCount1(0);
				} else {
					drc.setCount1(list.size());
				}
				drc.setDefectId1(list.get(list.size() - 1).getId());
				drc.setDefectRepeatIds(CollectionUtils.convertToString(defectIds, ","));
				map.put(defectRepeatData.getId(), drc);
			}
		}
		return map;
	}

	private List<List<DefectRepeatData>> repeatCountByGlb(List<DefectRepeatData> list, Double glbDistance) {
		Double temp = null;
		List<List<DefectRepeatData>> DefectRepeatDataLists = new ArrayList<>();
		List<DefectRepeatData> DefectRepeatDatas = null;
		for (int i = 0; i < list.size(); i++) {
			DefectRepeatData defectRepeatData = list.get(i);
			if (temp == null) {
				temp = defectRepeatData.getDefectGlb();
				DefectRepeatDatas = new ArrayList<>();

			}
			if (defectRepeatData.getDefectGlb() >= temp && defectRepeatData.getDefectGlb() <= (temp + glbDistance)) {
				DefectRepeatDatas.add(defectRepeatData);
			} else {
				DefectRepeatDataLists.add(DefectRepeatDatas);
				temp = defectRepeatData.getDefectGlb();
				DefectRepeatDatas = new ArrayList<>();
				DefectRepeatDatas.add(defectRepeatData);
			}
			if (i == (list.size() - 1)) {
				DefectRepeatDataLists.add(DefectRepeatDatas);
			}
		}
		return DefectRepeatDataLists;

	}

	private void checkDateSort(List<DefectRepeatData> list, boolean checkFlag) {
		if (checkFlag) {
			list.sort((DefectRepeatData h1, DefectRepeatData h2) -> h1.getCheckDate().compareTo(h2.getCheckDate()));
		} else {
			list.sort((DefectRepeatData h1, DefectRepeatData h2) -> h1.getDefectGlb().compareTo(h2.getDefectGlb()));
		}

	}

	@Override
	public List<Map<String, Object>> defectStatisticalForm() {
		List<Map<String, Object>> list = new ArrayList<>();
		List<DefectStatisticalHandle> bDefect = defectDao.findAllDefectOfFrom();
		List<DefectStatisticalHandle> aDefect = defectDao.findReformDefectOfFrom();
		Map<String, DefectStatistical> returnFrom = new LinkedHashMap<String, DefectStatistical>();
		String workName;
		DefectStatistical dsh;
		DefectStatistical sum = new DefectStatistical();
		returnFrom.put("sum", sum);
		for (DefectStatisticalHandle ad : aDefect) {
			Map<String, Object> map = new HashMap<>();
			workName = ad.getwN();
			if (!returnFrom.containsKey(workName)) {
				dsh = new DefectStatistical();
				dsh.setwI(ad.getWsI());
			} else {
				dsh = returnFrom.get(workName);
			}
			if (DefectSystem.defectC1.getStatus().equals(ad.getsI())) {
				dsh.setC1(String.valueOf(ad.getCount()));
				dsh.setARDP(ad.getCount());
				sum.setC1(String.valueOf(Integer.valueOf(sum.getC1()) + ad.getCount()));
			}
			if (DefectSystem.defectC2.getStatus().equals(ad.getsI())) {
				dsh.setC2(String.valueOf(ad.getCount()));
				dsh.setARDP(ad.getCount());
				sum.setC2(String.valueOf(Integer.valueOf(sum.getC2()) + ad.getCount()));
			}
			if (DefectSystem.defectC3.getStatus().equals(ad.getsI())) {
				dsh.setC3(String.valueOf(ad.getCount()));
				dsh.setARDP(ad.getCount());
				sum.setC3(String.valueOf(Integer.valueOf(sum.getC3()) + ad.getCount()));
			}
			if (DefectSystem.defectC4.getStatus().equals(ad.getsI())) {
				dsh.setC4(String.valueOf(ad.getCount()));
				dsh.setARDP(ad.getCount());
				sum.setC4(String.valueOf(Integer.valueOf(sum.getC4()) + ad.getCount()));
			}
			if (DefectSystem.defectC5.getStatus().equals(ad.getsI())) {
				dsh.setC5(String.valueOf(ad.getCount()));
				dsh.setARDP(ad.getCount());
				sum.setC5(String.valueOf(Integer.valueOf(sum.getC5()) + ad.getCount()));
			}
			if (DefectSystem.defectC6.getStatus().equals(ad.getsI())) {
				dsh.setC6(String.valueOf(ad.getCount()));
				dsh.setARDP(ad.getCount());
				sum.setC6(String.valueOf(Integer.valueOf(sum.getC6()) + ad.getCount()));
			}
			if ("1".equals(ad.getdL())) {
				dsh.setL1C(ad.getCount());
				sum.setL1C(ad.getCount());
			}
			if ("2".equals(ad.getdL())) {
				dsh.setL2C(ad.getCount());
				sum.setL2C(ad.getCount());
			}

			if (!returnFrom.containsKey(workName)) {
				returnFrom.put(workName, dsh);
			}
		}

		int c1Sum = 0;
		int c2Sum = 0;
		int c3Sum = 0;
		int c4Sum = 0;
		int c5Sum = 0;
		int c6Sum = 0;

		for (DefectStatisticalHandle bd : bDefect) {
			workName = bd.getwN();
			if (workName == "sum") {
				continue;
			}

			if (!returnFrom.containsKey(workName)) {
				dsh = new DefectStatistical();
				dsh.setwI(bd.getWsI());
			} else {
				dsh = returnFrom.get(workName);
			}

			if (DefectSystem.defectC1.getStatus().equals(bd.getsI())) {
				dsh.setC1(dsh.getC1() + "/" + bd.getCount());
				dsh.setBRDP(bd.getCount());
				c1Sum += bd.getCount();
			}
			if (DefectSystem.defectC2.getStatus().equals(bd.getsI())) {
				dsh.setC2(dsh.getC2() + "/" + bd.getCount());
				dsh.setBRDP(bd.getCount());
				c1Sum += bd.getCount();
			}
			if (DefectSystem.defectC3.getStatus().equals(bd.getsI())) {
				dsh.setC3(dsh.getC3() + "/" + bd.getCount());
				dsh.setBRDP(bd.getCount());
				c1Sum += bd.getCount();
			}
			if (DefectSystem.defectC4.getStatus().equals(bd.getsI())) {
				dsh.setC4(dsh.getC4() + "/" + bd.getCount());
				dsh.setBRDP(bd.getCount());
				c1Sum += bd.getCount();
			}
			if (DefectSystem.defectC5.getStatus().equals(bd.getsI())) {
				dsh.setC5(dsh.getC5() + "/" + bd.getCount());
				dsh.setBRDP(bd.getCount());
				c1Sum += bd.getCount();
			}
			if (DefectSystem.defectC6.getStatus().equals(bd.getsI())) {
				dsh.setC6(dsh.getC6() + "/" + bd.getCount());
				dsh.setBRDP(bd.getCount());
				c1Sum += bd.getCount();
			}
			if ("1".equals(bd.getdL())) {
				dsh.setL1D(bd.getCount());
			}
			if ("2".equals(bd.getdL())) {
				dsh.setL2D(bd.getCount());
			}
			if (!returnFrom.containsKey(workName)) {
				returnFrom.put(workName, dsh);
			}
		}

		int c1A = Integer.valueOf(sum.getC1());
		int c2A = Integer.valueOf(sum.getC2());
		int c3A = Integer.valueOf(sum.getC3());
		int c4A = Integer.valueOf(sum.getC4());
		int c5A = Integer.valueOf(sum.getC5());
		int c6A = Integer.valueOf(sum.getC6());

		sum.setARDP(c1A + c2A + c3A + c4A + c5A + c6A);
		sum.setBRDP(c1Sum + c2Sum + c3Sum + c4Sum + c5Sum + c6Sum);

		sum.setC1(c1A + "/" + c1Sum);
		sum.setC2(c2A + "/" + c2Sum);
		sum.setC3(c3A + "/" + c3Sum);
		sum.setC4(c4A + "/" + c4Sum);
		sum.setC5(c5A + "/" + c5Sum);
		sum.setC6(c6A + "/" + c6Sum);

		String str = "/0";
		for (Map.Entry<String, DefectStatistical> dss : returnFrom.entrySet()) {
			DefectStatistical ds = dss.getValue();
			String dsk = dss.getKey();
			if (!ds.getC1().contains("/"))
				ds.setC1(ds.getC1() + str);
			if (!ds.getC2().contains("/"))
				ds.setC2(ds.getC2() + str);
			if (!ds.getC3().contains("/"))
				ds.setC3(ds.getC3() + str);
			if (!ds.getC4().contains("/"))
				ds.setC4(ds.getC4() + str);
			if (!ds.getC5().contains("/"))
				ds.setC5(ds.getC5() + str);
			if (!ds.getC6().contains("/"))
				ds.setC6(ds.getC6() + str);
			if (!ds.getRDD().contains("/"))
				ds.setRDD(ds.getRDD() + str);
			if (!ds.getRDDP().contains("/"))
				ds.setRDDP(ds.getRDDP() + str);
			ds.setL1(ds.getL1C() + "/" + ds.getL1D());
			ds.setL2(ds.getL2D() + "/" + ds.getL2D());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(dsk, ds.getwI());
			map.put("rowData", ds);
			list.add(map);
		}

		return list;
	}

	@DictAggregation
	public List<DefectDTO> handleDefectData(CQueryFilter entity) {
		List<DefectDTO> lists = new ArrayList<DefectDTO>();
		List<DefectDTO> defects = defectDao.getHistoryDefect(entity);
		Map<String, List<DefectDTO>> maps = new HashMap<>();
		// 缺陷分组
		if (StringUtil.isNotEmpty(defects)) {
			defects.forEach(defect -> {
				String key = defect.getSystemId() + defect.getLineId() + defect.getDirection() + defect.getDefectType()
						+ defect.getDefectLevel();
				if (!maps.containsKey(key)) {
					maps.put(key, new ArrayList<DefectDTO>());
				}
				maps.get(key).add(defect);
			});
		}

		if (StringUtil.isNotEmpty(maps)) {
			maps.forEach((key, values) -> {
				List<List<DefectDTO>> defectLists = handleDefectData(values, entity.getGlbDistance());
				defectLists.forEach(tempList -> lists.addAll(setDefectCount(tempList, entity.getCount())));
			});
		}
		return lists;

	}

	private void dataSort(List<DefectDTO> list, boolean checkFlag) {
		if (checkFlag) {
			list.sort((d1, d2) -> {
				if (StringUtil.isNotEmpty(d1.getDefectGlb()) || StringUtil.isNotEmpty(d2.getDefectGlb())) {
					return 0;
				} else {
					return d1.getDefectGlb().compareTo(d2.getDefectGlb());
				}
			});
		} else {
			list.sort((d1, d2) -> {
				if (StringUtil.isNotEmpty(d1.getCheckDate()) || StringUtil.isNotEmpty(d2.getCheckDate())) {
					return 0;
				} else {
					return d1.getCheckDate().compareTo(d2.getCheckDate());
				}
			});
		}
	}

	private List<List<DefectDTO>> handleDefectData(List<DefectDTO> list, Double glbDistance) {
		List<List<DefectDTO>> lists = new ArrayList<List<DefectDTO>>();
		if (StringUtil.isEmpty(list)) {
			return lists;
		}
		// 根据公里标排序
		dataSort(list, true);
		List<DefectDTO> tempList = null;
		Double temp = null;
		for (int i = 0; i < list.size(); i++) {
			DefectDTO defect = list.get(i);
			if (temp == null) {
				temp = defect.getDefectGlb();
				tempList = new ArrayList<>();
			}
			if (StringUtil.isNotEmpty(defect.getDefectGlb())) {
				if (defect.getDefectGlb() >= temp && defect.getDefectGlb() <= (temp + glbDistance)) {
					tempList.add(defect);
				} else {
					lists.add(tempList);
					temp = defect.getDefectGlb();
					tempList = new ArrayList<>();
					tempList.add(defect);
				}
			}
			if (i == (list.size() - 1)) {
				lists.add(tempList);
			}
		}
		return lists;
	}

	private List<DefectDTO> setDefectCount(List<DefectDTO> list, int count) {
		List<DefectDTO> lists = new ArrayList<DefectDTO>();
		if (StringUtil.isEmpty(list)) {
			return lists;
		}
		// 根据日期排序
		dataSort(list, false);
		for (int i = 0; i < list.size(); i++) {
			DefectDTO defect = list.get(i);
			if (i == 0) {
				defect.setRepeatCount(i);
			} else {
				defect.setRepeatCount(i + 1);
			}
			if (defect.getRepeatCount() >= count) {
				lists.add(defect);
			}
		}
		return lists;
	}

	@Override
	public Integer changeTypicalDefectByIds(String ids, String typicalDefect) {
		return defectDao.changeTypicalDefectByIds(ids, typicalDefect);
	}

	@Override
	@DictAggregation
	public Page<DefectBasicDetails> findDefectBasicDetails(Page page, CQueryFilter entity) {
		PageUtils.setPage(page);
		entity.setPage(page);
		return PageUtils.buildPage(defectDao.findDefectBasicDetails(entity));
	}

	@Override
	@DictAggregation
	public List<DefectBasicDetails> findDefectBasicDetails(CQueryFilter entity) {
		return defectDao.findDefectBasicDetails(entity);
	}

	@Override
	public Map<String, List<DefectDTO>> getHistoryDefectOfStatus(List<DefectDTO> list) {
		Map<String, List<DefectDTO>> map = new LinkedHashMap<>();
		map.put("未销号",
				list.stream().filter(defectDTO -> !DefectStatus.Cancel.getStatus().equals(defectDTO.getDefectStatus()))
						.collect(Collectors.toList()));
		map.put("已销号",
				list.stream().filter(defectDTO -> DefectStatus.Cancel.getStatus().equals(defectDTO.getDefectStatus()))
						.collect(Collectors.toList()));
		return map;
	}

	// 比较数据重复判断条件是否修改
	private boolean compareDataValue(DefectDTO defect, Defect repeatData) {
		if (StringUtil.isEmpty(defect) || StringUtil.isEmpty(repeatData)) {
			return false;
		}
		if (!defect.getLineId().equals(repeatData.getLineId())
				|| (defect.getDirection() != null && !defect.getDirection().equals(repeatData.getDirection()))
				|| (defect.getPsaId() != null && !defect.getPsaId().equals(repeatData.getPsaId()))
				|| (defect.getTunnelId() != null && !defect.getTunnelId().equals(repeatData.getTunnelId()))
				|| (defect.getPillarId() != null && !defect.getPillarId().equals(repeatData.getPillarId()))
				|| (defect.getSystemId() != null && !defect.getSystemId().equals(repeatData.getSystemId()))
				|| (defect.getDefectType() != null && !defect.getDefectType().equals(repeatData.getDefectType()))
				|| (defect.getDefectLevel() != null && !defect.getDefectLevel().equals(repeatData.getDefectLevel()))
				|| (defect.getEquName() != null && !defect.getEquName().equals(repeatData.getEquName())
						&& DefectSystem.defectC2.getStatus().equals(repeatData.getSystemId()))) {
			return true;
		} else {
			return false;
		}

	}

	// 绥德重复次数处理
	public void handleSuiDeRepeatDefect(DefectRepeatRequest entity) {
		// 判断是否重复（以日期进行排序）
		List<DefectDTO> defects = defectDao.getRepeatDefect(entity);
		if (StringUtil.isNotEmpty(defects)) {
			// 获取已关联数据并按时间排序
			List<DefectDTO> datas = defects.stream()
					.filter(data -> StringUtil.isNotBlank(data.getDefectRepeatCountId()))
					.sorted((h1, h2) -> h1.getCheckDate().compareTo(h2.getCheckDate())).collect(Collectors.toList());
			if (StringUtil.isEmpty(datas)) {
				// 新增数据找到重复未处理的数据
				DefectDTO defect = defects.get(0);
				defects.remove(0);
				DefectRepeatCount defectRepeatCount = defectRepeatCountDao.getByKey(defect.getId());
				if (StringUtil.isEmpty(defectRepeatCount)) {
					defectRepeatCount = new DefectRepeatCount();
				}
				if (StringUtil.isNotEmpty(defects)) {
					defectRepeatCount.setDefectRepeatIds(CollectionUtils
							.convertToString(CollectionUtils.distinctExtractToList(defects, "id", String.class), ","));
				} else {
					defectRepeatCount.setDefectRepeatIds("");
				}
				defectRepeatCount.setCount(defects.size());
				if (StringUtil.isBlank(defectRepeatCount.getId())) {
					defectRepeatCount.setId(defect.getId());
					defectRepeatCountDao.insert(defectRepeatCount);
				} else {
					defectRepeatCountDao.updateRepeatCount(defectRepeatCount);
				}
				defectDao.updateDefectRepeatCount(defectRepeatCount.getId(),defectRepeatCount.getCount(),defectRepeatCount.getCount1(), defect.getId());
				if (StringUtil.isNotEmpty(defects)) {
					defectDao.updateDefectIsShow(NOT_SHOW,
							CollectionUtils.distinctExtractToList(defects, "id", String.class));
				}

			} else {
				DefectDTO defectDTO = datas.get(0);
				DefectRepeatCount defectRepeatData = defectRepeatCountDao.getByKey(defectDTO.getDefectRepeatCountId());
				Set<String> set = new HashSet<String>();
				set.addAll(CollectionUtils.distinctExtractToList(defects, "id", String.class));
				// 处理已有关联重复数据
				datas.forEach(data -> {
					DefectRepeatCount defectRepeatCount = defectRepeatCountDao.getByKey(data.getDefectRepeatCountId());
					String defectRepeatIds = defectRepeatCount.getDefectRepeatIds();
					if (StringUtil.isNotBlank(defectRepeatIds)) {
						List<Defect> repeatDatas = defectDao.getByKeys(Arrays.asList(defectRepeatIds.split(",")));
						repeatDatas.sort((h1, h2) -> h1.getCheckDate().compareTo(h2.getCheckDate()));
						Defect repeatData = repeatDatas.get(0);
						// 判断重复条件是否被修改
						if (compareDataValue(data, repeatData)) {
							repeatDatas.remove(0);
							DefectRepeatCount tempDefectRepeatCount = defectRepeatCountDao.getByKey(repeatData.getId());
							if (StringUtil.isEmpty(tempDefectRepeatCount)) {
								tempDefectRepeatCount = new DefectRepeatCount();
							}
							if (StringUtil.isNotEmpty(repeatDatas)) {
								tempDefectRepeatCount.setDefectRepeatIds(CollectionUtils.convertToString(
										CollectionUtils.distinctExtractToList(repeatDatas, "id", String.class), ","));
							} else {
								tempDefectRepeatCount.setDefectRepeatIds("");
							}
							tempDefectRepeatCount.setCount(repeatDatas.size());
							if (StringUtil.isBlank(tempDefectRepeatCount.getId())) {
								tempDefectRepeatCount.setId(repeatData.getId());
								defectRepeatCountDao.insert(tempDefectRepeatCount);
							} else {
								defectRepeatCountDao.updateRepeatCount(tempDefectRepeatCount);
							}
							defectDao.updateDefectRepeatCount(tempDefectRepeatCount.getId(),tempDefectRepeatCount.getCount(),tempDefectRepeatCount.getCount1(), repeatData.getId());
							defectDao.updateDefectIsShow(IS_SHOW, Arrays.asList(repeatData.getId()));
						} else {
							set.addAll(Arrays.asList(defectRepeatIds.split(",")));
						}
						if (!data.getId().equals(defectDTO.getId())) {
							defectDao.updateDefectRepeatCount(null,0,0, data.getId());
							defectRepeatCountDao.deleteByKeys(Arrays.asList(data.getId()));
						}
					}
				});
				// 移除主关联
				set.remove(defectDTO.getId());
				defectRepeatData.setCount(set.size());
				if (StringUtil.isNotEmpty(set)) {
					defectRepeatData.setDefectRepeatIds(CollectionUtils.convertToString(set, ","));
				} else {
					defectRepeatData.setDefectRepeatIds("");
				}
				defectRepeatCountDao.updateRepeatCount(defectRepeatData);
				defectDao.updateDefectRepeatCount(defectRepeatData.getId(),defectRepeatData.getCount(),defectRepeatData.getCount1(), defectDTO.getId());
				if (StringUtil.isNotEmpty(set)) {
					defectDao.updateDefectIsShow(NOT_SHOW, ListUtils.newArrayList(set));
				}

			}
		}
	}

	// 2018-09-13 修改重复次数需求
	public void defectRepeatJudge(DefectRepeatRequest entity) {

		List<DefectDTO> defects = defectDao.defectRepeatJudge(entity);
		if (StringUtil.isNotEmpty(defects)) {
			Set<String> set = new HashSet<String>();
			set.addAll(CollectionUtils.distinctExtractToList(defects, "id", String.class));
			DefectRepeatCount defectRepeatCount = new DefectRepeatCount();
			defectRepeatCount.setDefectRepeatIds(StringUtil.collectionToDelimitedString(set, ","));
			defectRepeatCount.setCount(set.size());
			defectRepeatCount.setId(entity.getId());
			DefectRepeatCount tempDefectRepeatCount = defectRepeatCountDao.getByKey(entity.getId());
			if (StringUtil.isEmpty(tempDefectRepeatCount)) {
				defectRepeatCountDao.insert(defectRepeatCount);
			} else {
				defectRepeatCountDao.updateRepeatCount(defectRepeatCount);
			}
			defectDao.updateDefectIsShow(NOT_SHOW, ListUtils.newArrayList(set));
		}

	}

	@Override
	public Integer defectToAssortment(CQueryFilter entity) {
		List<DefectAssortmentDTO> dasList = defectAssortmentService.findList(null);

		List<Defect> list = defectDao.findDefects(entity);
		List<Defect1CDTO> d1cList = defect1CService.findList(entity);
		List<Defect> list2 = list.stream()
				.filter(d -> StringUtil.isNotBlank(d.getSystemId()) && (!"C1".equals(d.getSystemId())))
				.collect(Collectors.toList());

		defectAssortmentService.handleDefectData(list2, dasList);
		defectAssortmentService.handleC1DefectData(d1cList, dasList);
		for (Defect d : list2) {
			if (StringUtil.isNotBlank(d.getDefectAssortmentId()))
				defectDao.update(d);
		}

		for (Defect d : d1cList) {
			if (StringUtil.isNotBlank(d.getDefectAssortmentId()))
				defectDao.update(d);
		}
		return null;
	}

	@DictAggregation
	@Override
	public Page<DefectFormDTO> findDefectFormPage(Page page, CQueryFilter entity) {
		if (entity != null && "0".equals(entity.getDefectType())) {
			entity.setDefectType(null);
		}
		Page<DefectFormDTO> pg = new Page<DefectFormDTO>();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		SimplePage sp = new SimplePage();
		sp.setPageNo(pageNo);
		sp.setPageSize(pageSize);
		sp.setStartNum((pageNo - 1) * pageSize);
		sp.setEndNum(pageNo * pageSize);
		entity.setSimplePage(sp);
		int total = defectDao.findDefectFormCount(entity);
		List<DefectFormDTO> list = defectDao.findDefectFormPage(entity);
		pg.setList(list);
		pg.setTotal((long) total);
		pg.setPageNo(sp.getPageNo());
		pg.setPageSize(sp.getPageSize());
		return pg;
	}

	@Override
	public void updateDefectData(Object obj) {
		//更新添加字段数据
		DefectAddField defectAddField = new DefectAddField();
		try {
			BeanUtils.copyProperties(defectAddField, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		defectDao.updateDefectData(defectAddField);
	}

}