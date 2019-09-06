package com.togest.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.authority.annotation.DataControlPut;
import com.togest.code.client.ExportClient;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.file.FileDownload;
import com.togest.common.util.string.StringUtil;
import com.togest.config.DefectStatus;
import com.togest.domain.DefectBasicDetails;
import com.togest.domain.DefectDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.CQueryFilter;
import com.togest.request.StatisticsQueryFilter;
import com.togest.response.DefectFormDTO;
import com.togest.response.statistics.DefectDataStatistics;
import com.togest.response.statistics.DefectDistributionYearOnYearData;
import com.togest.response.statistics.DefectFromOfStatistics;
import com.togest.response.statistics.DefectStateData;
import com.togest.response.statistics.FlowCountData;
import com.togest.response.statistics.HomeStatistics;
import com.togest.response.statistics.MoMAndYoY;
import com.togest.response.statistics.PieChartInfo;
import com.togest.response.statistics.RectifyRate;
import com.togest.response.statistics.StatisticNum;
import com.togest.response.statistics.StatisticRange;
import com.togest.scheduled.service.ScheduledTasks;
import com.togest.service.DefectService;
import com.togest.service.DefectStatisticsService;
import com.togest.util.TokenUtil;

import io.swagger.annotations.ApiOperation;

@RestController
public class DefectResource {

	@Autowired
	private DefectStatisticsService statisticsService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private ScheduledTasks scheduledTasks;
	@Autowired
	protected ExportClient exportService;

	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/homeStatistics", method = RequestMethod.GET)
	@ApiOperation(value = "6C首页缺陷统计数据")
	public RestfulResponse<HomeStatistics> findDefectLevelStatistics(
			HttpServletRequest request, @ModelAttribute CQueryFilter c6) {
		//String code = DataScope6CCode.HISTORY_DEFECT.getStatus();//
		//DataRightUtil.look(request, code, c6);
		HomeStatistics homeStatistics = statisticsService.findHomeStatisticsParam(c6);
		return new RestfulResponse<HomeStatistics>(homeStatistics);
	}

	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/dataStatistics", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷统计数据")
	public RestfulResponse<List<DefectDataStatistics>> findDefectDataStatistics(
			HttpServletRequest request, @ModelAttribute CQueryFilter entity) {
		//String code = DataScope6CCode.HISTORY_DEFECT.getStatus();
		//DataRightUtil.look(request, code, entity);
		List<DefectDataStatistics> list = statisticsService
				.findDefectDataStatistics(entity);
		return new RestfulResponse<List<DefectDataStatistics>>(list);
	}

	@RequestMapping(value = "defect/analysisReport", method = RequestMethod.GET)
	@ApiOperation(value = "动态分析报告")
	public ResponseEntity getAnalysisReport(HttpServletRequest request,
			@ModelAttribute CQueryFilter entity) {
		byte[] data = statisticsService.getAnalysisReport(entity);
		if (data == null) {
			Shift.fatal(StatusCode.FAIL);
		}
		ResponseEntity re = null;
		try {
			re = FileDownload.fileDownload(data, "动态分析报告.doc");
		} catch (Exception e) {
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/history", method = RequestMethod.GET)
	@ApiOperation(value = "历史缺陷数据")
	public RestfulResponse<Page<DefectFormDTO>> getHistoryDefect(HttpServletRequest request,Page page,
			CQueryFilter entity) {
//		if(entity!=null&&StringUtil.isBlank(entity.getDefectStatus())){
//			entity.setDefectStatus(DefectStatus.ShopReception.getStatus() + ","
//					+ DefectStatus.ReviewRectification.getStatus() + ","
//					+ DefectStatus.RectificationVerification.getStatus() + ","
//					+ DefectStatus.DELAY.getStatus() + ","
//					+ DefectStatus.Cancel.getStatus());
//		}
		//String code = DataScope6CCode.HISTORY_DEFECT.getStatus();//
		//DataRightUtil.look(request, code, entity);
		Page<DefectFormDTO> pg = defectService.findDefectFormPage(page, entity);
		
		return new RestfulResponse<Page<DefectFormDTO>>(pg);
	}
	
	@RequestMapping(value = "defect/allLine", method = RequestMethod.GET)
	@ApiOperation(value = "综合分析-线路")
	public RestfulResponse<List<StatisticNum>> findAllLineNum(
			HttpServletRequest request, @ModelAttribute CQueryFilter entity) {
		List<StatisticNum> list = statisticsService.findAllLineNum(entity);
		return new RestfulResponse<List<StatisticNum>>(list);
	}
	
	@RequestMapping(value = "defect/allDefect", method = RequestMethod.GET)
	@ApiOperation(value = "综合分析-缺陷类型")
	public RestfulResponse<List<StatisticNum>> findAllDefectTypeNum(
			HttpServletRequest request, @ModelAttribute CQueryFilter entity) {
		List<StatisticNum> list = statisticsService.findAllDefectTypeNum(entity);
		return new RestfulResponse<List<StatisticNum>>(list);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/data", method = RequestMethod.GET)
	@ApiOperation(value = "综合分析-缺陷类型")
	public RestfulResponse<List<PieChartInfo>> defectDataStatistical(
			HttpServletRequest request, @ModelAttribute StatisticsQueryFilter entity) {
		List<PieChartInfo> list = statisticsService.defectDataStatistical(entity);
		return new RestfulResponse<List<PieChartInfo>>(list);
	}
	
	@RequestMapping(value = "defect/statisticRange", method = RequestMethod.GET)
	@ApiOperation(value = "综合分析-单位公里范围内缺陷数")
	public RestfulResponse<Map<String, List<StatisticRange>>> findStatisticRange(
			HttpServletRequest request, @ModelAttribute CQueryFilter entity) {
		Map<String, List<StatisticRange>> map = statisticsService.findStatisticRange(entity);
		return new RestfulResponse<Map<String, List<StatisticRange>>>(map);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/everyDay", method = RequestMethod.GET)
	@ApiOperation(value = "每日缺陷发现数-销号数")
	public RestfulResponse<Map<String, Object>> defectEveryDay(
			HttpServletRequest request, @ModelAttribute CQueryFilter entity) {
		//String code = DataScope6CCode.HISTORY_DEFECT.getStatus();
		//DataRightUtil.look(request, code, entity);
		Map<String, Object> map = statisticsService.defectEveryDay(entity);
		return new RestfulResponse<Map<String, Object>>(map);
	}
	
	@RequestMapping(value = "defect/scheduledTasks", method = RequestMethod.GET)
	@ApiOperation(value = "缺陷定时任务")
	public RestfulResponse<Boolean> scheduledTasks() {
		scheduledTasks.task();
		return new RestfulResponse<Boolean>(true);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/momYoy/section", method = RequestMethod.GET)
	@ApiOperation(value = "本月缺陷环比同比数-分段")
	public RestfulResponse<List<MoMAndYoY>> defectMAndYBySection(HttpServletRequest request, 
			@ModelAttribute StatisticsQueryFilter query) {

//		if(StringUtil.isBlank(code)) {
//			code = DataScope6CCode.HISTORY_DEFECT.getStatus();
//		}
		//DataRightUtil.look(request, code, query);
		List<MoMAndYoY> list = statisticsService.defectMAndYBySection(query);
		return new RestfulResponse<List<MoMAndYoY>>(list);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/momYoy/line", method = RequestMethod.GET)
	@ApiOperation(value = "本月缺陷环比同比数-分线")
	public RestfulResponse<List<MoMAndYoY>> defectMAndYByLine(HttpServletRequest request,
			@ModelAttribute StatisticsQueryFilter query) {

//		if(StringUtil.isBlank(code)) {
//			code = DataScope6CCode.HISTORY_DEFECT.getStatus();
//		}
		//DataRightUtil.look(request, code, query);
		List<MoMAndYoY> list = statisticsService.defectMAndYByLine(query);
		return new RestfulResponse<List<MoMAndYoY>>(list);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/rectifyRate/section", method = RequestMethod.GET)
	@ApiOperation(value = "本月缺陷环比同比整改率-分段")
	public RestfulResponse<List<RectifyRate>> defectRectifyRateBySection(HttpServletRequest request, 
			@ModelAttribute StatisticsQueryFilter query) {

//		if(StringUtil.isBlank(code)) {
//			code = DataScope6CCode.HISTORY_DEFECT.getStatus();
//		}
		//DataRightUtil.look(request, code, query);
		List<RectifyRate> list = statisticsService.defectRectifyRateBySection(query);
		return new RestfulResponse<List<RectifyRate>>(list);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/rectifyRate/line", method = RequestMethod.GET)
	@ApiOperation(value = "本月缺陷环比同比整改率-分线")
	public RestfulResponse<List<RectifyRate>> defectRectifyRateByLine(HttpServletRequest request, 
			@ModelAttribute StatisticsQueryFilter query) {
		/*String sectionId = TokenUtil.getUser(request).getSectionId();
		if(StringUtil.isNotBlank(sectionId)) {
			query.setSectionId(sectionId);
		}*/
//		if(StringUtil.isBlank(code)) {
//			code = DataScope6CCode.HISTORY_DEFECT.getStatus();
//		}
		//DataRightUtil.look(request, code, query);
		List<RectifyRate> list = statisticsService.defectRectifyRateByLine(query);
		return new RestfulResponse<List<RectifyRate>>(list);
	}
	
	@RequestMapping(value = "defect/defectRepeatData", method = RequestMethod.GET)
	@ApiOperation(value = "修改缺陷重复次数")
	public RestfulResponse<Boolean> defectRepeatData() {
		defectService.defectRepeatData();
		return new RestfulResponse<Boolean>(true);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectDistributeFrom", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷分布报表")
	public RestfulResponse<List<Map<String, Object>>> defectDistributeFromResponse() {
		List<Map<String, Object>> defectStatisticalForm = defectService.defectStatisticalForm();
		return  new RestfulResponse<List<Map<String, Object>>>(defectStatisticalForm);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectStatisticalDistributionsFromResponse", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷统计分布报表")
	public RestfulResponse<List<Map<String, String>>> defectStatisticalDistributionsFromResponse(HttpServletRequest request, 
			@ModelAttribute StatisticsQueryFilter query) {
		List<Map<String, String>> defectStatisticalForm = statisticsService.defectStatisticalDistributionsFromResponse(query);
		return  new RestfulResponse<List<Map<String, String>>>(defectStatisticalForm);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectDistributionTrendFromResponse", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷趋势分布报表")
	public RestfulResponse<DefectDistributionYearOnYearData> defectDistributionTrendFromResponse(HttpServletRequest request, 
			@ModelAttribute StatisticsQueryFilter query) {
		DefectDistributionYearOnYearData ddb = statisticsService.defectDistributionTrendYearOnYearDataFromResponse(query);
		return  new RestfulResponse<DefectDistributionYearOnYearData>(ddb);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectDistanceData", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷间距搜索重复次数")
	public RestfulResponse<List<DefectDTO>> handleDefectData(HttpServletRequest request, CQueryFilter entity) {
		List<DefectDTO> list = defectService.handleDefectData(entity);
		return  new RestfulResponse<List<DefectDTO>>(list);
	}
	
	@RequestMapping(value = "defect/changeTypicalDefectByIds", method = RequestMethod.GET)
	@ApiOperation(value = "批量修改典型缺陷状态")
	public RestfulResponse<Integer> changeTypicalDefectByIds(HttpServletRequest request, String ids, String typicalDefect) {
		Integer n = defectService.changeTypicalDefectByIds(ids,typicalDefect);
		return  new RestfulResponse<Integer>(n);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectStatisticalState", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷统计")
	public RestfulResponse<DefectStateData> defectStatisticalState(HttpServletRequest request, CQueryFilter entity) {
		DefectStateData dsd = statisticsService.defectStatisticalState(entity);
		return  new RestfulResponse<DefectStateData>(dsd);
	}	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectFromByStatistics", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷查询统计")
	public RestfulResponse<List<DefectFromOfStatistics>> defectFromByStatistics(HttpServletRequest request, CQueryFilter entity) {
		List<DefectFromOfStatistics> list = statisticsService.defectFromByStatistics(entity);
		return  new RestfulResponse<List<DefectFromOfStatistics>>(list);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/defectFlowCount", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷流程统计")
	public RestfulResponse<List<FlowCountData>> defectFlowCount(HttpServletRequest request, CQueryFilter entity) {
		List<FlowCountData> list = statisticsService.defectFlowCount(entity);
		return  new RestfulResponse<List<FlowCountData>>(list);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/DefectInformationDetail", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷详情图表")
	public RestfulResponse<Map<String,Object>> DefectInformationDetail(HttpServletRequest request, CQueryFilter entity) {
		Map<String,Object> map = statisticsService.DefectInformationDetail(entity);
		return  new RestfulResponse<Map<String,Object>>(map);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/findDefectBasicDetails", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷基础详情表")
	public RestfulResponse<Page<DefectBasicDetails>> findDefectBasicDetails(HttpServletRequest request, Page page,CQueryFilter entity) {
		Page<DefectBasicDetails> pg = defectService.findDefectBasicDetails(page,entity);
		return  new RestfulResponse<Page<DefectBasicDetails>>(pg);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/findDefectBasicDetailsList", method = RequestMethod.GET)
	@ApiOperation(value = "返回缺陷基础详情表列")
	public RestfulResponse<List<DefectBasicDetails>> findDefectBasicDetailsList(HttpServletRequest request, Page page,CQueryFilter entity) {
		List<DefectBasicDetails> pg = defectService.findDefectBasicDetails(entity);
		return  new RestfulResponse<List<DefectBasicDetails>>(pg);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/historyOfStatus", method = RequestMethod.GET)
	@ApiOperation(value = "历史缺陷数据")
	public RestfulResponse<Map<String,List<DefectDTO>>> getHistoryDefectOfStatus(HttpServletRequest request,
			CQueryFilter entity) {
		/*if(entity!=null&&StringUtil.isBlank(entity.getDefectStatus())){
			entity.setDefectStatus(DefectStatus.ShopReception.getStatus() + ","
					+ DefectStatus.ReviewRectification.getStatus() + ","
					+ DefectStatus.RectificationVerification.getStatus() + ","
					+ DefectStatus.DELAY.getStatus() + ","
					+ DefectStatus.Cancel.getStatus());
		}*/
		List<DefectDTO> list = defectService.getHistoryDefect(entity);
		Map<String,List<DefectDTO>> map = defectService.getHistoryDefectOfStatus(list);
		return new RestfulResponse<Map<String,List<DefectDTO>>>(map);
	}
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/delete", method = RequestMethod.POST)
	@ApiOperation(value = "缺陷删除")
	public RestfulResponse<Boolean> registerDeleteDefect(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		if (TokenUtil.isToken(TokenUtil.getToken(request))) {
			List<String> idList = Arrays.asList(id.split(","));
			defectService.deleteFalse(idList,TokenUtil.getUserTokenData(request, "id"),request.getRemoteHost());
		} else {
			Shift.fatal(StatusCode.FAIL);
		}
		return new RestfulResponse<Boolean>(true);
	}
	
	@DataControlPut(authCode="LOOK_HISTORY_DEFECTS_NEX",deptFieldName="did",isToken=true,isDataControl=true)
	@RequestMapping(value = "defect/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出缺陷")
	public ResponseEntity importDefect(HttpServletRequest request, @ModelAttribute CQueryFilter entity, String templetId) {
		
		List<DefectBasicDetails> list = defectService.findDefectBasicDetails(entity);
		
		ResponseEntity re = null;
		try {
			List<Map<String,Object>> listMap = new ArrayList<>();
			list.forEach(d->
				listMap.add((Map<String, Object>) ObjectUtils.objectToHashMap(d)));
			byte[] bt = exportService.exportDataByMapTemplet(listMap, templetId);
			re = FileDownload.fileDownload(bt, "缺陷导出数据.xls");
		} catch (Exception e) {
			e.printStackTrace();
			Shift.fatal(StatusCode.FAIL);
		}
		return re;
	}
	@RequestMapping(value = "defect/defectToAssortment", method = RequestMethod.GET)
	@ApiOperation(value = "")
	public RestfulResponse<Integer> defectToAssortment(HttpServletRequest request, @ModelAttribute CQueryFilter entity) {
		  Integer i = defectService.defectToAssortment(entity);
		return  new RestfulResponse<Integer>(i);
	}
	
}
