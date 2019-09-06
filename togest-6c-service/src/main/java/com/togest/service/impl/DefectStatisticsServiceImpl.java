package com.togest.service.impl;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.clien.sevice.DeptFeignService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.PathUtil;
import com.togest.common.util.file.FileUtil;
import com.togest.common.util.file.WordUtil;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectStatus;
import com.togest.config.DefectSystem;
import com.togest.dao.DefectStatisticsDao;
import com.togest.domain.IdGen;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.StatisticsQueryFilter;
import com.togest.response.DeptRange;
import com.togest.response.statistics.CTypeStatistics;
import com.togest.response.statistics.DefectDataStatistics;
import com.togest.response.statistics.DefectDay;
import com.togest.response.statistics.DefectDistributionTrend;
import com.togest.response.statistics.DefectDistributionYearOnYearData;
import com.togest.response.statistics.DefectFromOfStatistics;
import com.togest.response.statistics.DefectFromStatistics;
import com.togest.response.statistics.DefectInfo;
import com.togest.response.statistics.DefectLevelStatistics;
import com.togest.response.statistics.DefectReform;
import com.togest.response.statistics.DefectStateData;
import com.togest.response.statistics.DefectStatusStatistics;
import com.togest.response.statistics.DefectTopDefect;
import com.togest.response.statistics.DefectTypeStatistics;
import com.togest.response.statistics.DetectMileageStatistics;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.HomeStatistics;
import com.togest.response.statistics.LineStatistics;
import com.togest.response.statistics.MoMAndYoY;
import com.togest.response.statistics.PieChartInfo;
import com.togest.response.statistics.RectifyRate;
import com.togest.response.statistics.StatisticNum;
import com.togest.response.statistics.StatisticRange;
import com.togest.response.statistics.StatisticsHeader;
import com.togest.response.statistics.StatisticsRow;
import com.togest.service.DefectStatisticsService;
import com.togest.service.DeptLineService;
import com.togest.util.JfreeChartUtil;

@Service
public class DefectStatisticsServiceImpl implements DefectStatisticsService {

	@Autowired
	private DefectStatisticsDao dao;
	@Autowired
	private DeptLineService deptLineService;
	@Autowired
	private DeptFeignService deptFeignService;

	@Override
	public List<StatisticNum> findAllLineNum(CQueryFilter entity) {
		return dao.findAllLineNum(entity);
	}

	@Override
	public List<StatisticNum> findAllDefectTypeNum(CQueryFilter entity) {
		return dao.findAllDefectTypeNum(entity);
	}

	@Override
	public HomeStatistics findHomeStatistics() {
		HomeStatistics homeStatistics = new HomeStatistics();

		// homeStatistics.setUntreatedDefect(dao.untreatedDefect(null));
		// homeStatistics.setCurrentDefect(dao.currentDefect(null));
		// homeStatistics.setDefectLevels(dao.defectLevelStatistics(null));
		// homeStatistics.setcTypes(dao.cTypeStatistics(new CQueryFilter()));
		return homeStatistics;
	}

	@Override
	public HomeStatistics findHomeStatisticsParam(CQueryFilter entity) {
		HomeStatistics homeStatistics = new HomeStatistics();

		homeStatistics.setUntreatedDefect(dao.untreatedDefect(entity));
		homeStatistics.setCurrentDefect(dao.currentDefect(entity));
		homeStatistics.setDefectLevels(dao.defectLevelStatistics(entity));
		homeStatistics.setcTypes(dao.cTypeStatistics(entity));
		return homeStatistics;
	}

	@Override
	public List<LineStatistics> findLineStatistics(CQueryFilter entity) {
		return dao.findLineStatistics(entity);
	}

	@Override
	public List<DefectTypeStatistics> findDefectTypeStatistics(CQueryFilter entity) {
		return dao.findDefectTypeStatistics(entity);
	}

	@Override
	public List<DefectLevelStatistics> findDefectLevelStatistics(CQueryFilter entity) {
		return dao.findDefectLevelStatistics(entity);
	}

	public List<DefectDataStatistics> findDefectDataStatistics(CQueryFilter entity) {
		List<DefectDataStatistics> list = new ArrayList<DefectDataStatistics>();

		List<DefectLevelStatistics> defectLevelStatistics = dao.findDefectLevelStatistics(entity);

		List<DefectTypeStatistics> defectTypeStatistics = dao.findDefectTypeStatistics(entity);

		List<LineStatistics> lineStatistics = dao.findLineStatistics(entity);

		Map<String, List<DefectLevelStatistics>> defectLevelStatisticsMap = new HashMap<>();
		Map<String, List<DefectTypeStatistics>> defectTypeStatisticsMap = new HashMap<>();
		Map<String, List<LineStatistics>> lineStatisticsMap = new HashMap<>();

		if (defectLevelStatistics != null) {
			defectLevelStatisticsMap = defectLevelStatistics.stream()
					.collect(Collectors.groupingBy(DefectLevelStatistics::getSystemId));
		}
		if (defectTypeStatistics != null) {
			defectTypeStatisticsMap = defectTypeStatistics.stream()
					.collect(Collectors.groupingBy(DefectTypeStatistics::getSystemId));
		}
		if (lineStatistics != null) {
			lineStatisticsMap = lineStatistics.stream().collect(Collectors.groupingBy(LineStatistics::getSystemId));
		}
		for (int i = 1; i < 7; i++) {
			DefectDataStatistics c = new DefectDataStatistics();
			c.setSystemCode("C" + i);
			if (defectLevelStatisticsMap != null) {
				c.setDefectLevelStatistics(defectLevelStatisticsMap.get(c.getSystemCode()));
			}
			if (defectTypeStatisticsMap != null) {
				c.setDefectTypeStatistics(defectTypeStatisticsMap.get(c.getSystemCode()));
			}
			if (lineStatisticsMap != null) {
				c.setLineStatistics(lineStatisticsMap.get(c.getSystemCode()));
			}
			list.add(c);
		}
		return list;
	}

	@Override
	public Map<String, Object> defectEveryDay(CQueryFilter entity) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> dayList = new ArrayList<Integer>();
		List<DefectDay> list = dao.findEveryDay(entity);
		if (entity.getCheckDate() == null) {// 是范围
			int begin = Integer.parseInt(sdf.format(entity.getBeginCheckDate()));
			int end = Integer.parseInt(sdf.format(entity.getEndCheckDate()));
			// int day = end-begin+1;
			for (DefectDay defectDay : list) {
				dayList.add(defectDay.getDay());
			}

			for (int i = begin; i <= end; i++) {
				if (!dayList.contains(i)) {
					DefectDay defectDay = new DefectDay();
					defectDay.setDay(i);
					defectDay.setFindNum(0);
					defectDay.setCanceledNum(0);
					list.add(defectDay);
				}
			}
			Collections.sort(list);
		} else {
			if (StringUtil.isEmpty(list)) {
				DefectDay defectDay = new DefectDay();
				defectDay.setDay(Integer.parseInt(sdf.format(entity.getCheckDate())));
				defectDay.setFindNum(0);
				defectDay.setCanceledNum(0);
				list.add(defectDay);
			}
		}

		/*
		 * int year = DateUtils.getSingleYear(); int month =
		 * DateUtils.getSingleMonth(); Calendar calendar =
		 * Calendar.getInstance(); calendar.set(Calendar.HOUR_OF_DAY, 0);
		 * calendar.set(Calendar.MINUTE, 0); calendar.set(Calendar.SECOND, 0);
		 * calendar.set(Calendar.MILLISECOND, 0); calendar.set(Calendar.YEAR,
		 * year); calendar.set(Calendar.MONTH, month-1);
		 * calendar.set(Calendar.DAY_OF_MONTH,
		 * calendar.getMinimum(Calendar.DATE)); Date firstDay =
		 * calendar.getTime(); calendar.set(Calendar.DAY_OF_MONTH,
		 * calendar.getMaximum(Calendar.DATE)); Date endDay =
		 * calendar.getTime();
		 * 
		 * //System.out.println(DateUtils.daysBetween(firstDay, endDay));
		 * if(month == 1) {//上个月考虑跨年 calendar.set(Calendar.YEAR, year-1);
		 * calendar.set(Calendar.MONTH, 11); } else {
		 * calendar.set(Calendar.MONTH, month-2); }
		 * calendar.set(Calendar.DAY_OF_MONTH,
		 * calendar.getMinimum(Calendar.DATE)); Date lastFirstDay =
		 * calendar.getTime(); calendar.set(Calendar.DAY_OF_MONTH,
		 * calendar.getMaximum(Calendar.DATE)); Date lastEndDay =
		 * calendar.getTime();
		 */

		CQueryFilter query = new CQueryFilter();
		query.setSectionId(entity.getSectionId());
		query.setSystemId(entity.getSystemId());
		query.setIsConfirmed(1);
		if (entity.getCheckDate() != null) {
			query.setCheckDate(entity.getCheckDate());
		} else {
			query.setBeginCheckDate(entity.getBeginCheckDate());
			query.setEndCheckDate(entity.getEndCheckDate());
		}
		Integer num = dao.defectNum(query);
		query.setIsCanceled(1);
		Integer canceled = dao.defectNum(query);

		query.setIsCanceled(null);
		if (entity.getCheckDate() != null) {
			query.setCheckDate(entity.getLastCheckDate());
		} else {
			query.setBeginCheckDate(entity.getLastBeginCheckDate());
			query.setEndCheckDate(entity.getLastEndCheckDate());
		}
		Integer lastNum = dao.defectNum(query);
		query.setIsCanceled(1);
		Integer lastCanceled = dao.defectNum(query);
		map.put("list", list);
		map.put("num", num);
		map.put("lastNum", lastNum);
		map.put("canceled", canceled);
		map.put("lastCanceled", lastCanceled);
		return map;
	}

	@Override
	public byte[] getAnalysisReport(CQueryFilter filter) {

		CQueryFilter tFilter = new CQueryFilter();
		tFilter.setNeedCanceledIsNull(false);
		Map<String, Object> map = new HashMap<>();
		List<PieChartInfo> pieChartInfoList = new ArrayList<>();
		DefectInfo c1Defect = new DefectInfo();
		DefectInfo c2Defect = new DefectInfo();
		DefectInfo c3Defect = new DefectInfo();
		DefectInfo c4Defect = new DefectInfo();
		String time = "2017-11";
		String[] timeSplit = time.split("-");
		if (timeSplit.length != 2) {

		}
		Integer year = Integer.parseInt(timeSplit[0]);
		Integer month = Integer.parseInt(timeSplit[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
		Date firstDayOfMonth = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DATE));
		Date lastDayOfMonth = calendar.getTime();
		/*
		 * tFilter.setBeginCheckDate(firstDayOfMonth);
		 * tFilter.setEndCheckDate(lastDayOfMonth);
		 */
		// 当前日期
		map.put("year", String.valueOf(DateUtils.getSingleYear()));
		map.put("month", String.valueOf(DateUtils.getSingleMonth()));
		map.put("day", String.valueOf(DateUtils.getSingleDate()));

		// 传参日期区间(查询范围)
		map.put("period", getPeriod(firstDayOfMonth, lastDayOfMonth));

		// 6C缺陷里程
		List<DetectMileageStatistics> detectMileage = dao.detectMileageStatistics(tFilter);
		map.put("6CDefectMileage", detectMileage);
		if (StringUtil.isNotEmpty(detectMileage)) {
			pieChartInfoList.clear();
			for (DetectMileageStatistics entity : detectMileage) {
				if (DefectSystem.defectC1.getStatus().contains(entity.getSystemId())) {
					c1Defect.setSystemId(DefectSystem.defectC1.getStatus());
					c1Defect.setDetectMileage(entity.getDetectMileage());
					c1Defect.setcName(entity.getcName());
				}
				if (DefectSystem.defectC2.getStatus().contains(entity.getSystemId())) {
					c2Defect.setSystemId(DefectSystem.defectC2.getStatus());
					c2Defect.setDetectMileage(entity.getDetectMileage());
					c2Defect.setcName(entity.getcName());
				}
				if (DefectSystem.defectC3.getStatus().contains(entity.getSystemId())) {
					c3Defect.setSystemId(DefectSystem.defectC3.getStatus());
					c3Defect.setDetectMileage(entity.getDetectMileage());
					c3Defect.setcName(entity.getcName());
				}
				if (DefectSystem.defectC4.getStatus().contains(entity.getSystemId())) {
					c4Defect.setSystemId(DefectSystem.defectC4.getStatus());
					c4Defect.setDetectMileage(entity.getDetectMileage());
					c4Defect.setcName(entity.getcName());
				}
				PieChartInfo pieChartInfo = new PieChartInfo();
				pieChartInfo.setName(entity.getcName());
				pieChartInfo.setNum(entity.getDetectMileage());
				pieChartInfoList.add(pieChartInfo);
			}
		}
		String imageCDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "缺陷里程分布图", pieChartInfoList);
		map.put("imageCDetectMileage", imageCDetectMileage);

		// 6C缺陷数量
		List<CTypeStatistics> cType = dao.cTypeStatistics(tFilter);
		map.put("6CDefectCount", cType);

		if (StringUtil.isNotEmpty(cType)) {
			pieChartInfoList.clear();
			for (CTypeStatistics entity : cType) {
				PieChartInfo pieChartInfo = new PieChartInfo();
				pieChartInfo.setName(entity.getcName());
				pieChartInfo.setNum(entity.getNum().doubleValue());
				pieChartInfoList.add(pieChartInfo);
			}
		}
		String imageCType = JfreeChartUtil.getPieChart(IdGen.uuid(), "缺陷数量分布图", pieChartInfoList);
		map.put("imageCType", imageCType);

		// 每个C缺陷数、缺陷类型、检测里程段别分布
		tFilter.setSystemId(DefectSystem.defectC1.getStatus());
		List<DefectLevelStatistics> c1Level = dao.findDefectLevelStatistics(tFilter);
		List<DefectTypeStatistics> c1Type = dao.findDefectTypeStatistics(tFilter);
		List<DefectReform> defectReform1C = dao.defectReformList(tFilter);
		setPercent(defectReform1C);
		setProperty(c1Defect, tFilter);
		map.put("c1Level", c1Level);
		map.put("c1Type", c1Type);
		map.put("defectReform1C", defectReform1C);

		List<PieChartInfo> section1CDetectMileage = dao.sectionDetectMileageFor1C(tFilter);
		String image1CSectionDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "1C检测里程按段别汇总",
				section1CDetectMileage);
		List<PieChartInfo> line1CDetectMileage = dao.lineDetectMileageFor1C(tFilter);
		String image1CLineDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "1C检测里程线路分布", line1CDetectMileage);
		List<PieChartInfo> defect1CType = dao.defectType(tFilter);
		String image1CDefectType = JfreeChartUtil.getPieChart(IdGen.uuid(), "1C缺陷分类统计", defect1CType);
		List<PieChartInfo> defectLine1C = dao.defectLine(tFilter);
		String image1CDefectLine = JfreeChartUtil.getPieChart(IdGen.uuid(), "1C缺陷数量线路分布", defectLine1C);
		map.put("image1CSectionDetectMileage", image1CSectionDetectMileage);
		map.put("image1CLineDetectMileage", image1CLineDetectMileage);
		map.put("image1CDefectType", image1CDefectType);
		map.put("image1CDefectLine", image1CDefectLine);

		StatisticsHeader c1Header = new StatisticsHeader();// 1C缺陷分线 分段表头
		c1Header.setNames(dao.findHeaderNames(DefectSystem.defectC1.getStatus()));
		List<StatisticsRow> c1Rows = new ArrayList<>();// 1C缺陷分线行
		List<StatisticsRow> c1Rows2 = new ArrayList<>();// 1C缺陷分段行
		setTable(tFilter, c1Rows, c1Rows2, line1CDetectMileage, section1CDetectMileage);
		map.put("c1Header", c1Header);
		map.put("c1Rows", c1Rows);
		map.put("c1Rows2", c1Rows2);

		///////////////////////////////////////////////////

		tFilter.setSystemId(DefectSystem.defectC2.getStatus());
		List<DefectLevelStatistics> c2Level = dao.findDefectLevelStatistics(tFilter);
		List<DefectTypeStatistics> c2Type = dao.findDefectTypeStatistics(tFilter);
		List<DefectReform> defectReform2C = dao.defectReformList(tFilter);
		setPercent(defectReform2C);
		setProperty(c2Defect, tFilter);
		map.put("c2Level", c2Level);
		map.put("c2Type", c2Type);
		map.put("defectReform2C", defectReform2C);

		List<PieChartInfo> line2CDetectMileage = dao.lineDetectMileage(tFilter);
		List<PieChartInfo> section2CDetectMileage = dao.sectionDetectMileage(tFilter);
		String image2CSectionDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "2C检测里程按段别汇总",
				section2CDetectMileage);
		List<PieChartInfo> defect2CType = dao.defectType(tFilter);
		String image2CDefectType = JfreeChartUtil.getPieChart(IdGen.uuid(), "2C缺陷分类统计", defect2CType);
		List<PieChartInfo> defectLine2C = dao.defectLine(tFilter);
		String image2CDefectLine = JfreeChartUtil.getPieChart(IdGen.uuid(), "2C缺陷数量线路分布", defectLine2C);
		map.put("image2CSectionDetectMileage", image2CSectionDetectMileage);
		map.put("image2CDefectType", image2CDefectType);
		map.put("image2CDefectLine", image2CDefectLine);

		StatisticsHeader c2Header = new StatisticsHeader();
		c2Header.setNames(dao.findHeaderNames(DefectSystem.defectC2.getStatus()));
		List<StatisticsRow> c2Rows = new ArrayList<>();
		List<StatisticsRow> c2Rows2 = new ArrayList<>();
		setTable(tFilter, c2Rows, c2Rows2, line2CDetectMileage, section2CDetectMileage);
		map.put("c2Header", c2Header);
		map.put("c2Rows", c2Rows);
		map.put("c2Rows2", c2Rows2);

		tFilter.setSystemId(DefectSystem.defectC3.getStatus());
		List<DefectLevelStatistics> c3Level = dao.findDefectLevelStatistics(tFilter);
		List<DefectTypeStatistics> c3Type = dao.findDefectTypeStatistics(tFilter);
		List<DefectReform> defectReform3C = dao.defectReformList(tFilter);
		setPercent(defectReform3C);
		setProperty(c3Defect, tFilter);
		map.put("c3Level", c3Level);
		map.put("c3Type", c3Type);
		map.put("defectReform3C", defectReform3C);

		List<PieChartInfo> section3CDetectMileage = dao.sectionDetectMileage(tFilter);
		String image3CSectionDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "3C检测里程按段别汇总",
				section3CDetectMileage);
		List<PieChartInfo> line3CDetectMileage = dao.lineDetectMileage(tFilter);
		String image3CLineDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "3C检测里程线路分布", line3CDetectMileage);
		List<PieChartInfo> defect3CType = dao.defectType(tFilter);
		String image3CDefectType = JfreeChartUtil.getPieChart(IdGen.uuid(), "3C缺陷分类统计", defect3CType);
		List<PieChartInfo> defectLine3C = dao.defectLine(tFilter);
		String image3CDefectLine = JfreeChartUtil.getPieChart(IdGen.uuid(), "3C缺陷数量线路分布", defectLine3C);
		map.put("image3CSectionDetectMileage", image3CSectionDetectMileage);
		map.put("image3CLineDetectMileage", image3CLineDetectMileage);
		map.put("image3CDefectType", image3CDefectType);
		map.put("image3CDefectLine", image3CDefectLine);

		StatisticsHeader c3Header = new StatisticsHeader();
		c3Header.setNames(dao.findHeaderNames(DefectSystem.defectC3.getStatus()));
		map.put("c3Header", c3Header);
		List<StatisticsRow> c3Rows = new ArrayList<>();
		List<StatisticsRow> c3Rows2 = new ArrayList<>();
		setTable(tFilter, c3Rows, c3Rows2, line3CDetectMileage, section3CDetectMileage);
		map.put("c3Rows", c3Rows);
		map.put("c3Rows2", c3Rows2);

		///////////////////////////////////////////////////////////////////

		tFilter.setSystemId(DefectSystem.defectC4.getStatus());
		List<DefectLevelStatistics> c4Level = dao.findDefectLevelStatistics(tFilter);
		List<DefectTypeStatistics> c4Type = dao.findDefectTypeStatistics(tFilter);
		List<DefectReform> defectReform4C = dao.defectReformList(tFilter);
		setPercent(defectReform4C);
		setProperty(c4Defect, tFilter);
		map.put("c4Level", c4Level);
		map.put("c4Type", c4Type);
		map.put("defectReform4C", defectReform4C);

		List<PieChartInfo> section4CDetectMileage = dao.sectionDetectMileage(tFilter);
		String image4CSectionDetectMileage = JfreeChartUtil.getPieChart(IdGen.uuid(), "4C检测里程按段别汇总",
				section4CDetectMileage);
		List<PieChartInfo> defectSection = dao.defectSection(tFilter);
		String image4CDefectSection = JfreeChartUtil.getPieChart(IdGen.uuid(), "4C缺陷数量段别分布", defectSection);
		List<PieChartInfo> defect4CType = dao.defectType(tFilter);
		String image4CDefectType = JfreeChartUtil.getPieChart(IdGen.uuid(), "4C缺陷分类统计", defect4CType);
		List<PieChartInfo> defectLine4C = dao.defectLine(tFilter);
		String image4CDefectLine = JfreeChartUtil.getPieChart(IdGen.uuid(), "4C缺陷数量线路分布", defectLine4C);
		List<PieChartInfo> line4CDetectMileage = dao.lineDetectMileage(tFilter);
		map.put("image4CSectionDetectMileage", image4CSectionDetectMileage);
		map.put("image4CDefectSection", image4CDefectSection);
		map.put("image4CDefectType", image4CDefectType);
		map.put("image4CDefectLine", image4CDefectLine);

		StatisticsHeader c4Header = new StatisticsHeader();
		c4Header.setNames(dao.findHeaderNames(DefectSystem.defectC4.getStatus()));
		List<StatisticsRow> c4Rows = new ArrayList<>();
		List<StatisticsRow> c4Rows2 = new ArrayList<>();
		setTable(tFilter, c4Rows, c4Rows2, line4CDetectMileage, section4CDetectMileage);
		map.put("c4Header", c4Header);
		map.put("c4Rows", c4Rows);
		map.put("c4Rows2", c4Rows2);

		map.put("c1Defect", c1Defect);
		map.put("c2Defect", c2Defect);
		map.put("c3Defect", c3Defect);
		map.put("c4Defect", c4Defect);

		File target = new File(PathUtil.getDirectoryPath() + File.separator + "temp");
		if (!target.exists()) {
			target.mkdirs();
		}
		File file = new File(target, "动态分析报告.docx");
		byte[] data = null;
		try {
			WordUtil.createWordFileByFtl(PathUtil.getDirectoryPath() + File.separator + "template" + File.separator,
					"动态分析报告.ftl", file.getAbsolutePath(), map);
			data = FileUtil.readFile(file);
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
		return data;
	}

	public void setTable(CQueryFilter tFilter, List<StatisticsRow> cRows, List<StatisticsRow> cRows2,
			List<PieChartInfo> lineDetectMileage, List<PieChartInfo> sectionDetectMileage) {// 处理表格

		Double detectMileageTotal = 0d;
		Integer sumTotal = 0;
		StatisticsRow lastRow = new StatisticsRow();
		StatisticsRow lastRow2 = new StatisticsRow();

		for (PieChartInfo entity : lineDetectMileage) {
			if (StringUtil.isNotEmpty(entity)) {
				StatisticsRow statisticsRow = new StatisticsRow();
				statisticsRow.setName(entity.getName());// 线路名
				Double num = entity.getNum();
				detectMileageTotal += num;
				statisticsRow.setDetectMileage(num);// 线路检测里程
				tFilter.setLineId(entity.getId());
				List<Integer> findRowCounts = dao.findRowCounts(tFilter);
				int sum = 0;
				for (Integer integer : findRowCounts) {// 计算小计
					sum += integer;
				}
				sumTotal += sum;
				statisticsRow.setNums(findRowCounts);
				statisticsRow.setSubtotal(sum);
				cRows.add(statisticsRow);
			}
		}
		lastRow.setName("合计");
		lastRow.setDetectMileage(detectMileageTotal);
		lastRow.setSubtotal(sumTotal);
		tFilter.setLineId(null);
		lastRow.setNums(dao.findRowCounts(tFilter));
		cRows.add(lastRow);

		detectMileageTotal = 0d;
		for (PieChartInfo entity : sectionDetectMileage) {
			if (StringUtil.isNotEmpty(entity)) {
				StatisticsRow statisticsRow = new StatisticsRow();
				statisticsRow.setName(entity.getName());// 段名
				Double num = entity.getNum();
				detectMileageTotal += num;
				statisticsRow.setDetectMileage(num);// 段检测里程
				tFilter.setSectionId(entity.getId());
				List<Integer> findRowCounts = dao.findRowCounts(tFilter);
				int sum = 0;
				for (Integer integer : findRowCounts) {// 计算小计
					sum += integer;
				}
				sumTotal += sum;
				statisticsRow.setNums(findRowCounts);
				statisticsRow.setSubtotal(sum);
				cRows2.add(statisticsRow);
			}
		}
		lastRow2.setName("合计");
		lastRow2.setDetectMileage(detectMileageTotal);
		lastRow2.setSubtotal(sumTotal);
		tFilter.setSectionId(null);
		lastRow2.setNums(dao.findRowCounts(tFilter));
		cRows2.add(lastRow2);
	}

	public void setPercent(List<DefectReform> defectReformList) {// 百分比
		System.out.println("list.size  " + defectReformList.size());
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);

		for (DefectReform entity : defectReformList) {

			if (entity.getReformNum() == null) {// 月整改数量
				entity.setReformNum(0);
			}
			if (entity.getTotalNum() == null) {// 月缺陷总数
				entity.setTotalNum(0);
				entity.setMonthPercent("0%");
			} else {
				Double reformNumD = entity.getReformNum().doubleValue();
				Double totalNumD = entity.getTotalNum().doubleValue();
				String percent = numberFormat.format(reformNumD / totalNumD);
				entity.setMonthPercent(percent + "%");

			}
			if (entity.getLeftNum() == null) {// 遗留整改数量
				entity.setLeftNum(0);
			}
			if (entity.getLeftTotalNum() == null) {// 总遗留量
				entity.setLeftTotalNum(0);
			}
			if ((entity.getReformNum() + entity.getLeftNum() + entity.getLeftTotalNum()) == 0) {
				entity.setTotalPercent("0%");
			} else {
				Double reformNumD = entity.getReformNum().doubleValue();
				Double leftNumD = entity.getLeftNum().doubleValue();
				Double leftTotalNumD = entity.getLeftTotalNum().doubleValue();
				String percent = numberFormat.format((reformNumD + leftNumD) / (reformNumD + leftNumD + leftTotalNumD));
				entity.setTotalPercent(percent + "%");
			}
		}
	}

	public String getPeriod(Date begin, Date end) {

		StringBuilder period = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		int month = calendar.get(Calendar.MONTH) + 1;
		period.append(calendar.get(Calendar.YEAR) + "年");
		period.append(month + "月");
		period.append(calendar.get(Calendar.DATE) + "日至");
		calendar.setTime(end);
		month = calendar.get(Calendar.MONTH) + 1;
		period.append(calendar.get(Calendar.YEAR) + "年");
		period.append(month + "月");
		period.append(calendar.get(Calendar.DATE) + "日");
		return period.toString();
	}

	public void setProperty(DefectInfo entity, CQueryFilter tFilter) {

		tFilter.setIsConfirmed(1);
		Integer totalNum = dao.defectNum(tFilter);// 缺陷总数
		tFilter.setIsReformed(1);
		Integer rectificationNum = dao.defectNum(tFilter);// 已整改
		Integer untreatedNum = totalNum - rectificationNum;// 已确认未整改
		tFilter.setSpeedType("8");
		tFilter.setIsReformed(null);
		Integer highSpeedNum = dao.defectNum(tFilter);// 高速缺陷总数
		Integer generalSpeedNum = totalNum - highSpeedNum;// 普速缺陷总数
		tFilter.setIsReformed(1);
		Integer highSpeedRectificationNum = dao.defectNum(tFilter);// 已经整改的高速缺陷数量(含未销号)
		Integer generalSpeedRectificationNum = rectificationNum - highSpeedRectificationNum;// 已经整改的普速缺陷数量(含未销号)
		entity.setTotalNum(totalNum);
		entity.setUntreatedNum(untreatedNum);
		entity.setRectificationNum(generalSpeedRectificationNum);
		entity.setHighSpeedNum(highSpeedNum);
		entity.setGeneralSpeedNum(generalSpeedNum);
		entity.setHighSpeedRectificationNum(highSpeedRectificationNum);
		entity.setGeneralSpeedRectificationNum(generalSpeedRectificationNum);
		tFilter.setSpeedType(null);
		tFilter.setIsReformed(null);
	}

	@Override
	public Map<String, List<StatisticRange>> findStatisticRange(CQueryFilter entity) {
		Map<String, List<StatisticRange>> map = new HashMap<String, List<StatisticRange>>();
		Double start = null;
		Double end = null;
		boolean haveDirection = false;
		Date beginCheckDate = entity.getBeginCheckDate();
		Date endCheckDate = entity.getEndCheckDate();
		String systemId = entity.getSystemId();
		Integer kilometre = entity.getKilometre();
		String lineId = entity.getLineId();
		String workShopId = entity.getWorkShopId();
		String direction = entity.getDirection();
		RestfulResponse<String> checkDeptType = deptFeignService.checkDeptType(workShopId, "2");
		if (checkDeptType == null || StringUtil.isBlank(checkDeptType.getData())) {// 部门类型不是车间
			Shift.fatal(StatusCode.DEPT_TYPE_ERROR);
		}
		if (StringUtil.isNotBlank(direction)) {
			haveDirection = true;
		}
		List<DeptRange> shopRangeList = deptLineService.getWorkShopRange(lineId, workShopId);
		if (StringUtil.isEmpty(shopRangeList)) {// 车间下无工区或没有配管辖范围
			Shift.fatal(StatusCode.WORK_SHOP_RANGE_EMPTY);
		}
		for (DeptRange deptRange : shopRangeList) {
			List<StatisticRange> list = new ArrayList<StatisticRange>();
			String directionName = deptRange.getDirectionName();
			String directionSelect = deptRange.getDirection();
			Double startKm = deptRange.getStartKm();
			Double endKm = deptRange.getEndKm();
			if (haveDirection && !direction.equals(directionSelect)) {
				continue;
			}
			if (StringUtil.isEmpty(startKm) || StringUtil.isEmpty(endKm)) {
				Shift.fatal(StatusCode.PARAM_DATA_EMPTY);
			}
			Double num = endKm - startKm;
			int count = num.intValue() / kilometre;
			int remainder = num.intValue() % kilometre;
			if (count == 0) {// 不足一个单位区段
				StatisticRange statisticRange = new StatisticRange();
				statisticRange.setStartKm(startKm);
				statisticRange.setEndKm(endKm);
				list.add(statisticRange);
			} else {
				for (int i = 0; i < count; i++) {
					StatisticRange statisticRange = new StatisticRange();
					start = startKm + kilometre * i;
					end = start + kilometre;
					statisticRange.setStartKm(start);
					statisticRange.setEndKm(end);
					list.add(statisticRange);
				}
				if (remainder != 0) {
					StatisticRange statisticRange = new StatisticRange();
					start = end;
					end += remainder;
					statisticRange.setStartKm(start);
					statisticRange.setEndKm(end);
					list.add(statisticRange);
				}
			}
			List<Integer> numList = dao.getStatisticRangeNum(list, beginCheckDate, endCheckDate, systemId, lineId,
					directionSelect, workShopId);
			if (StringUtil.isEmpty(numList) || numList.size() != list.size()) {
				Shift.fatal(StatusCode.FAIL);
			}
			for (int i = 0; i < numList.size(); i++) {
				StatisticRange statisticRange = list.get(i);
				statisticRange.setNum(numList.get(i));
			}
			map.put(directionName, list);
		}
		return map;
	}

	@Override
	public List<MoMAndYoY> defectMAndYBySection(StatisticsQueryFilter query) {
		StatisticsQueryFilter timeFrame = getTimeFrame(query);
		List<MoMAndYoY> list = dao.defectMAndYBySection(timeFrame);
		if (StringUtil.isBlank(timeFrame.getSectionId())) {
			removeMY(list);
		}
		return list;
	}

	@Override
	public List<MoMAndYoY> defectMAndYByLine(StatisticsQueryFilter query) {
		StatisticsQueryFilter timeFrame = getTimeFrame(query);
		List<MoMAndYoY> list = dao.defectMAndYByLine(timeFrame);
		if (list == null) {
			list = new ArrayList<>();
			list.add(new MoMAndYoY());
		}
		removeMY(list);
		return list;
	}

	@Override
	public List<RectifyRate> defectRectifyRateBySection(StatisticsQueryFilter query) {
		StatisticsQueryFilter timeFrame = getTimeFrame(query);
		List<RectifyRate> list = dao.defectRectifyRateBySection(timeFrame);
		if (StringUtil.isBlank(timeFrame.getSectionId())) {
			removeRectifyRate(list);
		}
		return list;
	}

	@Override
	public List<RectifyRate> defectRectifyRateByLine(StatisticsQueryFilter query) {
		StatisticsQueryFilter timeFrame = getTimeFrame(query);
		List<RectifyRate> list = dao.defectRectifyRateByLine(timeFrame);
		if (list == null) {
			list = new ArrayList<>();
			list.add(new RectifyRate());
		}
		removeRectifyRate(list);
		return list;
	}

	public void removeRectifyRate(List<RectifyRate> list) {
		if (StringUtil.isNotEmpty(list)) {
			List<RectifyRate> remove = new ArrayList<RectifyRate>();
			for (RectifyRate entity : list) {
				boolean flag = entity != null && entity.getNum() == 0 && entity.getMom() == 0 && entity.getYoy() == 0
						&& entity.getReformed() == 0 && entity.getReformedMom() == 0 & entity.getReformedYoy() == 0;
				if (flag) {
					remove.add(entity);
				}
			}
			list.removeAll(remove);
		}
	}

	public void removeMY(List<MoMAndYoY> list) {
		if (StringUtil.isNotEmpty(list)) {
			List<MoMAndYoY> remove = new ArrayList<MoMAndYoY>();
			for (MoMAndYoY entity : list) {
				boolean flag = entity != null && entity.getNum() == 0 && entity.getMom() == 0 && entity.getYoy() == 0;
				if (flag) {
					remove.add(entity);
				}
			}
			list.removeAll(remove);
		}
	}

	// 自动设置本月-环比-同比时间
	public StatisticsQueryFilter getTimeFrame(StatisticsQueryFilter query) {

		if (StringUtil.isEmpty(query)) {
			query = new StatisticsQueryFilter();
		}
		int year = DateUtils.getSingleYear();
		int month = DateUtils.getSingleMonth();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
		Date thisStart = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DATE));
		Date thisEnd = calendar.getTime();
		// 本月
		query.setThisStart(thisStart);
		query.setThisEnd(thisEnd);

		calendar.set(Calendar.YEAR, year - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
		Date yoyStart = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DATE));
		Date yoyEnd = calendar.getTime();
		// 同比(上年同月)
		query.setYoyStart(yoyStart);
		query.setYoyEnd(yoyEnd);

		if (month == 1) {// 考虑上月跨年
			calendar.set(Calendar.YEAR, year - 1);
			calendar.set(Calendar.MONTH, 11);
		} else {
			calendar.set(Calendar.MONTH, month - 2);
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
		Date momStart = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DATE));
		Date momEnd = calendar.getTime();
		// 环比(上月)
		query.setMomStart(momStart);
		query.setMomEnd(momEnd);
		return query;
	}

	@Override
	public List<PieChartInfo> defectDataStatistical(StatisticsQueryFilter entity) {
		List<PieChartInfo> dds = dao.defectDataStatistical(entity);
		List<PieChartInfo> rr = new ArrayList<>();
		List<String> ids = CollectionUtils.distinctExtractToList(dds, "id", String.class);
		for (String id2 : ids) {
			PieChartInfo p = new PieChartInfo();
			p.setId(id2);
			for (PieChartInfo p1 : dds) {
				if (id2.equals(p1.getId())) {
					p.setName(p1.getName());
					p.setNum(p.getNum() + p1.getNum());
					if ((!DefectStatus.Cancel.getStatus().equals(p1.getDefectStatus()))
							&& StringUtil.isNotEmpty(p1.getDefectStatus())) {
						p.setNumNoCancel(p.getNumNoCancel() + p1.getNum());
						if ("1".equals(p1.getDefectLevel())) {
							p.setNumLevel1(p.getNumLevel1() + p1.getNum());
							if (DefectStatus.RectificationVerification.getStatus().equals(p1.getDefectStatus())) {
								p.setNumReformedLevel1(p.getNumReformedLevel1() + p1.getNum());
							}
						}
						if ("2".equals(p1.getDefectLevel())) {
							p.setNumLevel2(p.getNumLevel2() + p1.getNum());
							if (DefectStatus.RectificationVerification.getStatus().equals(p1.getDefectStatus())) {
								p.setNumReformedLevel2(p.getNumReformedLevel2() + p1.getNum());
							}
						}
					} else if (DefectStatus.Cancel.getStatus().equals(p1.getDefectStatus())) {
						p.setNumCancel(p.getNumCancel() + p1.getNum());
					}
				}
			}
			rr.add(p);
		}
		return rr;
	}

	@Override
	public List<Map<String, String>> defectStatisticalDistributionsFromResponse(StatisticsQueryFilter entity) {
		List<Map<String, String>> response = new ArrayList<>();
		List<DefectStatusStatistics> d = dao.defectStatisticalDistributionsFromResponse(entity);
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		for (DefectStatusStatistics dd : d) {
			if (DefectStatus.DefectRegister.getStatus().equals(dd.getStatus())
					|| DefectStatus.DefectAudit.getStatus().equals(dd.getStatus()))
				one = Integer.valueOf(dd.getNumber()) + one;
			if (DefectStatus.ShopReception.getStatus().equals(dd.getStatus())
					|| DefectStatus.ReviewRectification.getStatus().equals(dd.getStatus())
					|| DefectStatus.DELAY.getStatus().equals(dd.getStatus())
					|| DefectStatus.CHECKHANDLE.getStatus().equals(dd.getStatus()))
				two = Integer.valueOf(dd.getNumber()) + two;
			if (DefectStatus.RectificationVerification.getStatus().equals(dd.getStatus()))
				three = Integer.valueOf(dd.getNumber()) + three;
			if (DefectStatus.Cancel.getStatus().equals(dd.getStatus()))
				four = Integer.valueOf(dd.getNumber()) + four;
		}

		for (int i = 1; i <= 5; i++) {
			Map<String, String> map = new HashMap<>();
			String str = null;
			int num = 0;
			switch (i) {
			case 1:
				str = "新发现缺陷";
				num = one;
				break;
			case 2:
				str = "整改中缺陷";
				num = two;
				break;
			case 3:
				str = "已整改缺陷";
				num = three;
				break;
			case 4:
				str = "已确认缺陷";
				num = two + three;
				break;
			case 5:
				str = "已销号缺陷";
				num = four;
				break;
			}
			map.put("name", str);
			map.put("count", String.valueOf(num));
			response.add(map);
		}
		return response;
	}

	@Override
	public List<DefectDistributionTrend> defectDistributionTrendFromResponse(StatisticsQueryFilter query) {
		List<DefectDistributionTrend> ddts = null;
		if (StringUtil.isNotEmpty(query.getDORY())) {
			String str = query.getDORY();
			if (StringUtil.isBlank(query.getType())) {
				query.setType(null);
			}
			if ("y".equals(str)) {
				ddts = dao.defectDistributionTrendYearFromResponse(query);
			} else {
				ddts = dao.defectDistributionTrendDayFromResponse(query);
			}

			if (query.getType() != null) {
				ddts = ddts.stream()
						.filter(defectDistributionTrend -> StringUtil.isNotEmpty(defectDistributionTrend.getId())
								&& StringUtil.isNotEmpty(defectDistributionTrend.getName()))
						.collect(Collectors.toList());
			}
			if (ddts == null || ddts.size() == 0) {
				ddts = new ArrayList<>();
				DefectDay dd = new DefectDay();
				dd.setDay(null);
				List<DefectDay> data = new ArrayList<>();
				data.add(dd);
				DefectDistributionTrend defectDistributionTrend = new DefectDistributionTrend();
				defectDistributionTrend.setId("");
				defectDistributionTrend.setName("");
				defectDistributionTrend.setData(data);
				ddts.add(defectDistributionTrend);
			}
			if (query.getType() == null) {
				List<DefectDistributionTrend> newList = new ArrayList<>();
				DefectDistributionTrend dd = new DefectDistributionTrend();
				List<DefectDay> ddList = new ArrayList<>();
				for (DefectDistributionTrend ddb : ddts) {
					if (ddb.getData() != null) {
						ddList.addAll(ddb.getData());
					}
				}
				dd.setData(ddList);
				newList.add(dd);
				ddts = newList;
			}
			Integer endDay = 1;
			Integer startDay = 1;
			Calendar cS = Calendar.getInstance();
			Calendar cE = Calendar.getInstance();
			cS.setTime(query.getThisStart());
			cE.setTime(query.getThisEnd());
			switch (str) {
			case "d":
				break;
			case "w":
				startDay = cS.get(Calendar.DAY_OF_MONTH);
				endDay = cE.get(Calendar.DAY_OF_MONTH);
				break;
			case "m":
				endDay = cE.get(Calendar.DAY_OF_MONTH);
				break;
			case "y":
				endDay = 12;
				break;
			}

			if ("w".equals(str) && endDay < startDay) {
				List<Integer> weekDay = new ArrayList<>();
				for (Integer i = startDay; i <= (startDay + 7 - endDay - 1); i++) {
					weekDay.add(i);
				}
				for (Integer i = 1; i <= endDay; i++) {
					weekDay.add(i);
				}
				for (DefectDistributionTrend ddt : ddts) {
					if (ddts.size() > 1 && ddt.getId() != null && ddt.getName() == null) {
						ddts.remove(ddt);
						continue;
					}
					List<DefectDay> dds = ddt.getData();

					/*
					 * if(dds == null) dds = new ArrayList<>();
					 */
					List<Integer> dayList = CollectionUtils.distinctExtractToList(ddt.getData(), "day", Integer.class);
					List<DefectDay> data = new ArrayList<>();
					for (Integer i : weekDay) {
						if (!dayList.contains(i)) {
							DefectDay dd = new DefectDay();
							dd.setDay(i);
							dd.setFindNum(0);
							data.add(dd);
						} else {
							for (DefectDay dd : dds) {
								if (i.equals(dd.getDay())) {
									data.add(dd);
								}
							}
						}
					}
					ddt.setData(data);
				}
			} else {
				for (DefectDistributionTrend ddt : ddts) {
					if ((!"y".equals(str)) && ddts.size() > 1 && ddt.getId() != null && ddt.getName() == null) {
						ddts.remove(ddt);
						continue;
					}
					List<DefectDay> dds = ddt.getData();
					List<Integer> dayList = CollectionUtils.distinctExtractToList(dds, "day", Integer.class);
					if (dayList == null) {
						dayList = new ArrayList<>();
					}
					List<DefectDay> data = new ArrayList<>();
					for (Integer i = startDay; i <= endDay; i++) {
						if (!dayList.contains(i)) {
							DefectDay dd = new DefectDay();
							dd.setDay(i);
							dd.setFindNum(0);
							data.add(dd);
						} else {
							for (DefectDay dd : dds) {
								if (i.equals(dd.getDay())) {
									data.add(dd);
								}
							}
						}
					}
					ddt.setData(data);
				}
			}
		}

		return ddts;
	}

	@Override
	public DefectDistributionYearOnYearData defectDistributionTrendYearOnYearDataFromResponse(
			StatisticsQueryFilter query) {
		String str = query.getDORY();

		List<DefectDistributionTrend> ddtsN = null;
		List<DefectDistributionTrend> ddtsA = null;
		List<DefectDistributionTrend> ddtsY = null;

		ddtsN = defectDistributionTrendFromResponse(query);

		Date thisStart = query.getThisStart();
		Date thisEnd = query.getThisEnd();
		Calendar cS = Calendar.getInstance();
		cS.setTime(thisStart);
		Calendar cE = Calendar.getInstance();
		cE.setTime(thisEnd);

		switch (str) {
		case "d":
			cS.add(Calendar.DATE, -1);
			cE.add(Calendar.DATE, -1);
			break;
		case "w":
			cS.add(Calendar.DATE, -7);
			cE.add(Calendar.DATE, -7);
			break;
		case "m":
			cS.add(Calendar.MONTH, -1);
			cE.add(Calendar.MONTH, -1);
			break;
		case "y":
			cS.add(Calendar.YEAR, -1);
			cE.add(Calendar.YEAR, -1);
			break;
		}
		query.setThisStart(cS.getTime());
		query.setThisEnd(cE.getTime());

		ddtsA = defectDistributionTrendFromResponse(query);

		Long nowNum = 0L;
		Long lastNumR = 0L;
		Long lastNumY = 0L;
		for (DefectDistributionTrend dn : ddtsN) {
			nowNum = dn.getNum() + nowNum;
			for (DefectDistributionTrend da : ddtsA) {
				if (StringUtil.isEmpty(dn.getId()) || StringUtil.isEmpty(da.getId())) {
					if (StringUtil.isEmpty(dn.getId()) && StringUtil.isEmpty(da.getId()))
						dn.setNumLastTime(da.getNum());
				} else if (dn.getId().equals(da.getId())) {
					dn.setNumLastTime(da.getNum());
				}
			}
		}
		for (DefectDistributionTrend dn : ddtsA) {
			lastNumR = dn.getNum() + lastNumR;
		}
		DefectDistributionYearOnYearData ddb = new DefectDistributionYearOnYearData();
		ddb.setNumNow(nowNum.toString());
		ddb.setNumLastRR(lastNumR.toString());
		ddb.setDdbList(ddtsN);
		cS.setTime(thisStart);
		cE.setTime(thisEnd);
		if ("m".equals(str)) {
			cS.add(Calendar.MONTH, -12);
			cE.add(Calendar.MONTH, -12);
		} else {
			cS.add(Calendar.YEAR, -1);
			cE.add(Calendar.YEAR, -1);
		}
		query.setThisStart(cS.getTime());
		query.setThisEnd(cE.getTime());

		ddtsY = defectDistributionTrendFromResponse(query);

		for (DefectDistributionTrend dn : ddtsY) {
			lastNumY = dn.getNum() + lastNumY;
		}
		ddb.setNumLastYY(lastNumY.toString());
		return ddb;
	}

	@Override
	public DefectStateData defectStatisticalState(CQueryFilter entity) {
		List<DefectStateData> dsdList = dao.defectStatisticalState(entity);
		DefectStateData theDsd = new DefectStateData();
		theDsd.setTdNum(dsdList.stream().filter(defectStateData -> "1".equals(defectStateData.getTypicalDefect()))
				.collect(Collectors.summingInt(DefectStateData::getNum)));
		theDsd.setNewFindDSNum(dsdList.stream().filter(
				defectStateData -> DefectStatus.DefectRegister.getStatus().equals(defectStateData.getDefectStatus())
						|| DefectStatus.DefectAudit.getStatus().equals(defectStateData.getDefectStatus()))
				.collect(Collectors.summingInt(DefectStateData::getNum)));
		theDsd.setReformDSNum(dsdList.stream().filter(
				defectStateData -> DefectStatus.ShopReception.getStatus().equals(defectStateData.getDefectStatus())
						|| DefectStatus.ReviewRectification.getStatus().equals(defectStateData.getDefectStatus())
						|| DefectStatus.DELAY.getStatus().equals(defectStateData.getDefectStatus())
						|| DefectStatus.CHECKHANDLE.getStatus().equals(defectStateData.getDefectStatus()))
				.collect(Collectors.summingInt(DefectStateData::getNum)));
		theDsd.setCancelDSNum(dsdList.stream()
				.filter(defectStateData -> DefectStatus.Cancel.getStatus().equals(defectStateData.getDefectStatus()))
				.collect(Collectors.summingInt(DefectStateData::getNum)));
		theDsd.setDefectNum(dsdList.stream().collect(Collectors.summingInt(DefectStateData::getNum)));
		return theDsd;
	}

	@Override
	public List<DefectFromOfStatistics> defectFromByStatistics(CQueryFilter entity) {
		List<DefectFromOfStatistics> dfosList = new ArrayList<>();
		List<DefectFromStatistics> dfbsList = dao.defectFromByStatistics(entity);
		List<DefectTopDefect> dfbtList = dao.defectFromByTopDefect(entity);
		Map<String, List<DefectTopDefect>> mapDefectTypeList = dfbtList.stream()
				.filter(defectTopDefect -> StringUtil.isNotEmpty(defectTopDefect.getWorkShopName())
						&& StringUtil.isNotEmpty(defectTopDefect.getDefectTypeName()))
				.collect(Collectors.groupingBy(DefectTopDefect::getWorkShopName));
		Map<String, List<DefectFromStatistics>> map = dfbsList.stream()
				.filter(defectFromStatistics -> StringUtil.isNotEmpty(defectFromStatistics.getWorkShopName())
						&& StringUtil.isNotEmpty(defectFromStatistics.getWorkShopId()))
				.collect(Collectors.groupingBy(DefectFromStatistics::getWorkShopName));
		map.forEach((key, value) -> {
			DefectFromOfStatistics dfos = new DefectFromOfStatistics();
			dfos.setWorkShopName(key);
			if (StringUtil.isNotBlank(value.get(0).getWorkShopId())) {
				dfos.setWorkShopId(value.get(0).getWorkShopId());
			}
			dfos.setNumAll(value.stream().mapToInt(DefectFromStatistics::getNum).sum());
			dfos.setNumDToday(value.stream().mapToInt(DefectFromStatistics::getNumToday).sum());
			int i = 0;
			List<DefectTopDefect> list = mapDefectTypeList.get(key);
			if (list != null) {
				for (DefectTopDefect d : list) {
					if (StringUtil.isEmpty(d.getDefectTypeName()))
						continue;
					if (i == 0) {
						dfos.setDefectTopThree(d.getDefectTypeName() + ":" + d.getNum());
					} else {
						dfos.setDefectTopThree(
								dfos.getDefectTopThree() + "," + d.getDefectTypeName() + ":" + d.getNum());
					}
					i += 1;
					if (i > 2)
						break;
				}
			}
			dfos.setNumDLevel1(
					value.stream().filter(defectFromStatics -> "1".equals(defectFromStatics.getDefectLevel()))
							.mapToInt(DefectFromStatistics::getNum).sum());
			dfos.setNumDLevel2(
					value.stream().filter(defectFromStatics -> "2".equals(defectFromStatics.getDefectLevel()))
							.mapToInt(DefectFromStatistics::getNum).sum());
			dfos.setNumDFindToday(value.stream()
					.filter(defectFromStatics -> DefectStatus.DefectRegister.getStatus()
							.equals(defectFromStatics.getDefectStatus())
							|| DefectStatus.DefectAudit.getStatus().equals(defectFromStatics.getDefectStatus()))
					.mapToInt(DefectFromStatistics::getNum).sum());
			dfos.setNumDProcessing(value.stream()
					.filter(defectFromStatics -> DefectStatus.ShopReception.getStatus()
							.equals(defectFromStatics.getDefectStatus())
							|| DefectStatus.ReviewRectification.getStatus().equals(defectFromStatics.getDefectStatus())
							|| DefectStatus.DELAY.getStatus().equals(defectFromStatics.getDefectStatus())
							|| DefectStatus.RectificationVerification.getStatus()
									.equals(defectFromStatics.getDefectStatus())
							|| DefectStatus.CHECKHANDLE.getStatus().equals(defectFromStatics.getDefectStatus()))
					.mapToInt(DefectFromStatistics::getNum).sum());
			dfos.setNumDCanceled(value.stream().filter(
					defectFromStatics -> DefectStatus.Cancel.getStatus().equals(defectFromStatics.getDefectStatus()))
					.mapToInt(DefectFromStatistics::getNum).sum());
			dfos.setNumNow(dfos.getNumDFindToday() + dfos.getNumDProcessing());
			dfosList.add(dfos);
		});
		return dfosList;
	}

	@Override
	public List<FlowCountData> defectFlowCount(CQueryFilter entity) {
		Integer num = 0;
		List<FlowCountData> defectFlowCountList = dao.defectFlowCount(entity);
		Map<String, List<FlowCountData>> map = defectFlowCountList.stream()
				.filter(flowCountData -> StringUtil.isNotEmpty(flowCountData.getStatus()))
				.collect(Collectors.groupingBy(FlowCountData::getStatus));
		List<FlowCountData> list = map.get("8");
		if (ListUtils.isNotEmpty(list)) {
			FlowCountData flowCountData = list.get(0);
			num = Integer.valueOf(flowCountData.getNum());

		}
		List<FlowCountData> List = new ArrayList<>();
		for (Integer i = 1; i <= 7; i++) {
			if (map.containsKey(i.toString())) {
				List<FlowCountData> list2 = map.get(i.toString());
				FlowCountData flowCountData = list2.get(0);
				if (i == 4) {
					flowCountData.setNum(String.valueOf(Integer.valueOf(flowCountData.getNum()) + num));
				}
				List.addAll(list2);
			} else {

				FlowCountData fcd = new FlowCountData();
				if (i == 4) {
					fcd.setNum(String.valueOf(Integer.valueOf(fcd.getNum()) + num));
				}
				fcd.setStatus(i.toString());
				List.add(fcd);
			}
		}
		List.forEach(dfc -> {
			String str = dfc.getStatus();
			if (DefectStatus.DefectRegister.getStatus().equals(str)) {
				dfc.setName("新登记");
			} else if (DefectStatus.DefectAudit.getStatus().equals(str)) {
				dfc.setName("缺陷审核");
			} else if (DefectStatus.ShopReception.getStatus().equals(str)) {
				dfc.setName("车间接收");
			} else if (DefectStatus.ReviewRectification.getStatus().equals(str)
					|| DefectStatus.CHECKHANDLE.getStatus().equals(str)) {
				dfc.setName("复核整改");
			} else if (DefectStatus.RectificationVerification.getStatus().equals(str)) {
				dfc.setName("整改验证");
			} else if (DefectStatus.Cancel.getStatus().equals(str)) {
				dfc.setName("已销号");
			} else if (DefectStatus.DELAY.getStatus().equals(str)) {
				dfc.setName("延期审核");
			}
		});
		return List;
	}

	@Override
	public Map<String, Object> DefectInformationDetail(CQueryFilter entity) {
		Map<String, Object> map = new LinkedHashMap<>();
		List<DefectFromStatistics> defectInformationDetailList = dao.DefectInformationDetail(entity);

		Map<String, List<DefectFromStatistics>> levelMap = defectInformationDetailList.stream()
				.filter(DefectFromStatistics -> StringUtil.isNotEmpty(DefectFromStatistics.getDefectLevel()))
				.collect(Collectors.groupingBy(DefectFromStatistics::getDefectLevel));
		Map<String, List<DefectFromStatistics>> defectTypeMap = defectInformationDetailList.stream()
				.filter(DefectFromStatistics -> StringUtil.isNotEmpty(DefectFromStatistics.getDefectType()))
				.collect(Collectors.groupingBy(DefectFromStatistics::getDefectType));
		Map<String, List<DefectFromStatistics>> workShopMap = defectInformationDetailList.stream()
				.filter(DefectFromStatistics -> StringUtil.isNotEmpty(DefectFromStatistics.getWorkShopName()))
				.collect(Collectors.groupingBy(DefectFromStatistics::getWorkShopName));

		List<Map<String, Object>> lList = new ArrayList<>();
		List<Map<String, Object>> dList = new ArrayList<>();
		List<Map<String, Object>> wList = new ArrayList<>();
		List<Map<String, Object>> reList = new ArrayList<>();
		levelMap.forEach((key, value) -> {
			Map<String, Object> lMap = new HashMap<>();
			String str = null;
			if ("1".equals(key)) {
				str = "一级缺陷";
			}
			if ("2".equals(key)) {
				str = "二级缺陷";
			}

			lMap.put("name", str);
			lMap.put("num", value.stream().mapToInt(DefectFromStatistics::getNum).sum());
			lList.add(lMap);
		});
		defectTypeMap.forEach((key, value) -> {
			Map<String, Object> dMap = new LinkedHashMap<>();
			dMap.put("name", key);
			dMap.put("num", value.stream().mapToInt(DefectFromStatistics::getNum).sum());
			dList.add(dMap);
		});
		workShopMap.forEach((key, value) -> {
			Map<String, Object> wMap = new LinkedHashMap<>();
			wMap.put("name", key);
			wMap.put("num", value.stream().mapToInt(DefectFromStatistics::getNum).sum());
			wList.add(wMap);
		});
		Map<String, Object> reMap = new LinkedHashMap<>();
		reMap.put("name", "缺陷总数");
		reMap.put("num", defectInformationDetailList.stream().mapToInt(DefectFromStatistics::getNum).sum());
		reList.add(reMap);
		reMap = new LinkedHashMap<>();
		reMap.put("name", "销号缺陷总数");
		reMap.put("num", defectInformationDetailList.stream().mapToInt(DefectFromStatistics::getNumCancel).sum());
		reList.add(reMap);

		List<Map<String, Object>> list2 = new ArrayList<>();
		Map<String, Object> map2 = new LinkedHashMap<>();
		map2.put("name", "缺陷等级");
		map2.put("data", lList);
		list2.add(map2);
		map2 = new LinkedHashMap<>();
		map2.put("name", "缺陷类型");
		map2.put("data", dList);
		list2.add(map2);
		map2 = new LinkedHashMap<>();
		map2.put("name", "缺陷车间分布");
		map2.put("data", wList);
		list2.add(map2);
		map2 = new LinkedHashMap<>();
		map2.put("name", "整改率数据");
		map2.put("data", reList);
		list2.add(map2);
		map2 = new LinkedHashMap<>();
		map.put("data", list2);
		return map;
	}

}
