package com.togest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.togest.request.CQueryFilter;
import com.togest.request.StatisticsQueryFilter;
import com.togest.response.statistics.CTypeStatistics;
import com.togest.response.statistics.DefectDay;
import com.togest.response.statistics.DefectDistributionTrend;
import com.togest.response.statistics.DefectFromStatistics;
import com.togest.response.statistics.DefectLevelStatistics;
import com.togest.response.statistics.DefectReform;
import com.togest.response.statistics.DefectStateData;
import com.togest.response.statistics.DefectStatusStatistics;
import com.togest.response.statistics.DefectTopDefect;
import com.togest.response.statistics.DefectTypeStatistics;
import com.togest.response.statistics.DetectMileageStatistics;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.LineStatistics;
import com.togest.response.statistics.MoMAndYoY;
import com.togest.response.statistics.PieChartInfo;
import com.togest.response.statistics.RectifyRate;
import com.togest.response.statistics.StatisticNum;
import com.togest.response.statistics.StatisticRange;

public interface DefectStatisticsDao {
	// 线路分类(已确认但未销号 和 全部的)
	public List<LineStatistics> findLineStatistics(CQueryFilter entity);

	// 缺陷类型分类(已确认但未销号)
	public List<DefectTypeStatistics> findDefectTypeStatistics(CQueryFilter entity);

	// 缺陷等级分类(已确认但未销号)
	public List<DefectLevelStatistics> findDefectLevelStatistics(CQueryFilter entity);

	// 已确认但未整改
	public Integer untreatedDefect(String systemId);

	// 已确认但未销号
	public Integer currentDefect(String systemId);

	// 缺陷数量
	public Integer defectNum(CQueryFilter entity);

	public List<DefectLevelStatistics> defectLevelStatistics(String systemId);

	public List<CTypeStatistics> cTypeStatistics(CQueryFilter entity);

	public List<DetectMileageStatistics> detectMileageStatistics(CQueryFilter entity);

	// 检测里程 - 段别
	public List<PieChartInfo> sectionDetectMileageFor1C(CQueryFilter entity);

	// 检测里程 - 段别
	public List<PieChartInfo> sectionDetectMileage(CQueryFilter entity);

	// 检测里程 - 线别
	public List<PieChartInfo> lineDetectMileageFor1C(CQueryFilter entity);

	// 检测里程 - 线别
	public List<PieChartInfo> lineDetectMileage(CQueryFilter entity);

	// 缺陷类型 - 数量
	public List<PieChartInfo> defectType(CQueryFilter entity);

	// 缺陷数量 - 线别
	public List<PieChartInfo> defectLine(CQueryFilter entity);

	// 缺陷数量 - 段别
	public List<PieChartInfo> defectSection(CQueryFilter entity);

	// 缺陷整改情况
	public List<DefectReform> defectReformList(CQueryFilter entity);

	// 缺陷类型名 1234C
	public List<String> findHeaderNames(String systemId);

	// 缺陷类型对应的统计数值 1234C
	public List<Integer> findRowCounts(CQueryFilter entity);

	// 综合分析 线路 - 未处理数量 - 已处理数量
	public List<StatisticNum> findAllLineNum(CQueryFilter entity);

	// 综合分析 缺陷类型 - 未处理数量 - 已处理数量
	public List<StatisticNum> findAllDefectTypeNum(CQueryFilter entity);

	// 单位区段缺陷数
	public List<Integer> getStatisticRangeNum(@Param("entityList") List<StatisticRange> entityList,
			@Param("beginCheckDate") Date beginCheckDate, @Param("endCheckDate") Date endCheckDate,
			@Param("systemId") String systemId, @Param("lineId") String lineId, @Param("direction") String direction,
			@Param("workShopId") String workShopId);

	// 时间范围内每一天发现数 - 每一天发现的缺陷的销号数
	public List<DefectDay> findEveryDay(CQueryFilter entity);

	// 本月缺陷数量 环比同比统计 分段
	public List<MoMAndYoY> defectMAndYBySection(StatisticsQueryFilter entity);

	// 本月缺陷数量 环比同比统计 分线
	public List<MoMAndYoY> defectMAndYByLine(StatisticsQueryFilter entity);

	// 本月缺陷整改率 环比同比统计 分段
	public List<RectifyRate> defectRectifyRateBySection(StatisticsQueryFilter entity);

	// 本月缺陷整改率 环比同比统计 分线
	public List<RectifyRate> defectRectifyRateByLine(StatisticsQueryFilter entity);
	
	//
	public List<PieChartInfo> defectDataStatistical(StatisticsQueryFilter entity);

	public List<DefectStatusStatistics> defectStatisticalDistributionsFromResponse(StatisticsQueryFilter entity);
	
	public List<DefectDistributionTrend> defectDistributionTrendYearFromResponse(StatisticsQueryFilter entity);
	
	public List<DefectDistributionTrend> defectDistributionTrendDayFromResponse(StatisticsQueryFilter entity);

	public List<DefectStateData> defectStatisticalState(CQueryFilter entity);
	
	public List<DefectFromStatistics> defectFromByStatistics(CQueryFilter entity);
	
	public List<DefectTopDefect> defectFromByTopDefect(CQueryFilter entity);
	
	public List<FlowCountData> defectFlowCount(CQueryFilter entity);
	
	public List<DefectFromStatistics> DefectInformationDetail(CQueryFilter entity);

	public Integer untreatedDefect(CQueryFilter entity);

	public Integer currentDefect(CQueryFilter entity);

	public List<DefectLevelStatistics> defectLevelStatistics(CQueryFilter entity);
}

