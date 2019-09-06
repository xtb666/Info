package com.togest.service;

import java.util.List;
import java.util.Map;

import com.togest.request.CQueryFilter;
import com.togest.request.StatisticsQueryFilter;
import com.togest.response.statistics.DefectDataStatistics;
import com.togest.response.statistics.DefectDistributionTrend;
import com.togest.response.statistics.DefectDistributionYearOnYearData;
import com.togest.response.statistics.DefectFromOfStatistics;
import com.togest.response.statistics.DefectLevelStatistics;
import com.togest.response.statistics.DefectStateData;
import com.togest.response.statistics.DefectTypeStatistics;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.HomeStatistics;
import com.togest.response.statistics.LineStatistics;
import com.togest.response.statistics.MoMAndYoY;
import com.togest.response.statistics.PieChartInfo;
import com.togest.response.statistics.RectifyRate;
import com.togest.response.statistics.StatisticNum;
import com.togest.response.statistics.StatisticRange;


public interface DefectStatisticsService {

	public List<LineStatistics> findLineStatistics(CQueryFilter entity);
	
	public List<DefectTypeStatistics> findDefectTypeStatistics(CQueryFilter entity);
	
	public List<DefectLevelStatistics> findDefectLevelStatistics(CQueryFilter entity);
	
	public HomeStatistics findHomeStatistics();
	
	public HomeStatistics findHomeStatisticsParam(CQueryFilter c6);
	
	public List<DefectDataStatistics> findDefectDataStatistics(CQueryFilter entity);
	
	public byte[] getAnalysisReport(CQueryFilter filter);
	
	//综合分析 线路 - 未处理数量 - 已处理数量
	public List<StatisticNum> findAllLineNum(CQueryFilter entity);
	//综合分析 缺陷类型 - 未处理数量 - 已处理数量
	public List<StatisticNum> findAllDefectTypeNum(CQueryFilter entity);
	
	public Map<String, List<StatisticRange>> findStatisticRange(CQueryFilter entity);
	
	public Map<String, Object> defectEveryDay(CQueryFilter entity);
	//本月缺陷数量 环比同比统计 分段
	public List<MoMAndYoY> defectMAndYBySection(StatisticsQueryFilter query);
	//本月缺陷数量 环比同比统计 分线
	public List<MoMAndYoY> defectMAndYByLine(StatisticsQueryFilter query);
	//本月缺陷整改率 环比同比统计 分段
	public List<RectifyRate> defectRectifyRateBySection(StatisticsQueryFilter entity);
	//本月缺陷整改率 环比同比统计 分线
	public List<RectifyRate> defectRectifyRateByLine(StatisticsQueryFilter entity);
	
	public List<PieChartInfo> defectDataStatistical(StatisticsQueryFilter entity);
	//缺陷统计分布:新发现,整改中,已整改,已销号
	public List<Map<String, String>> defectStatisticalDistributionsFromResponse(StatisticsQueryFilter entity);

	public List<DefectDistributionTrend> defectDistributionTrendFromResponse(StatisticsQueryFilter query);

	public DefectDistributionYearOnYearData defectDistributionTrendYearOnYearDataFromResponse(StatisticsQueryFilter query);
	
	public DefectStateData defectStatisticalState(CQueryFilter entity);

	public List<DefectFromOfStatistics> defectFromByStatistics(CQueryFilter entity);

	public List<FlowCountData> defectFlowCount(CQueryFilter entity);

	public Map<String, Object> DefectInformationDetail(CQueryFilter entity);

}
