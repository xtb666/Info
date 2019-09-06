package com.togest.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.togest.authority.annotation.DataControlPut;
import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.MileageStatisticsDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.MileageStatisticsRequest;
import com.togest.service.MileageStatisticsService;
import com.togest.util.TokenUtil;

import io.swagger.annotations.ApiOperation;

@RestController
public class MileageStatisticsResource {
	
	@Autowired
	MileageStatisticsService mileaStaticsService;
	@DataControlPut(authCode="LOOK_MILEAGE_STATISTICS")
	@RequestMapping(value="mileageStatics/lists",method =RequestMethod.GET)
	@ApiOperation(value = "获取所有里程数统计数据信息")
	public RestfulResponse<List<MileageStatisticsDTO>> getList(MileageStatisticsRequest mileageStatisticsRequest){
		List<MileageStatisticsDTO> list = mileaStaticsService.findList(mileageStatisticsRequest);
		return new RestfulResponse<List<MileageStatisticsDTO>>(list);
	}
	@DataControlPut(authCode="LOOK_MILEAGE_STATISTICS")
	@RequestMapping(value="mileageStatics/pages",method =RequestMethod.GET)
	@ApiOperation(value = "获取所有里程数统计分页数据信息")
	public RestfulResponse<Page<MileageStatisticsDTO>> getPageList(Page<MileageStatisticsDTO> page,MileageStatisticsRequest mileageStatisticsRequest){
		page = mileaStaticsService.findPage(page, mileageStatisticsRequest);
		return new RestfulResponse<Page<MileageStatisticsDTO>>(page);
	}
	
	@RequestMapping(value="mileageStatics",method=RequestMethod.GET)
	@ApiOperation(value = "获取里程数统计数据信息")
	public RestfulResponse<MileageStatisticsDTO> getInfo(String id){
		if(StringUtil.isBlank(id)){
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		MileageStatisticsDTO mileageStatisticsDTO = mileaStaticsService.get(id);
		return new RestfulResponse<MileageStatisticsDTO>(mileageStatisticsDTO);
	}
	
	@RequestMapping(value="mileageStatics",method=RequestMethod.POST)
	@ApiOperation(value = "新增里程数统计数据")
	public RestfulResponse<Integer> save(HttpServletRequest request,
			MileageStatisticsDTO mileageStatisticsDTO){
		mileageStatisticsDTO.setCreateBy(TokenUtil.getUserId(request));
		mileageStatisticsDTO.setCreateDate(new Date());
		mileageStatisticsDTO.setCreateIp(request.getRemoteHost());
		int flag = mileaStaticsService.save(mileageStatisticsDTO);
		return new RestfulResponse<Integer>(flag);
	}
	
	@RequestMapping(value="mileageStatics",method=RequestMethod.PUT)
	@ApiOperation(value = "更新里程数统计数据")
	public RestfulResponse<Integer> update(HttpServletRequest request,
			MileageStatisticsDTO mileageStatisticsDTO){
		mileageStatisticsDTO.setUpdateBy(TokenUtil.getUserId(request));
		mileageStatisticsDTO.setUpdateDate(new Date());
		mileageStatisticsDTO.setUpdateIp(request.getRemoteHost());
		int flag = mileaStaticsService.save(mileageStatisticsDTO);
		return new RestfulResponse<Integer>(flag);
	}

	@RequestMapping(value="mileageStatics",method=RequestMethod.DELETE)
	@ApiOperation(value = "批量删除里程数统计数据")
	public RestfulResponse<Integer> deleteFalse(HttpServletRequest request,
			String ids){
		if(StringUtil.isBlank(ids)){
			Shift.fatal(StatusCode.DATA_CONDITION_EMPTY);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = Arrays.asList(ids.split(","));
		map.put("ids", list);
		map.put("deleteBy", TokenUtil.getUserId(request));
		map.put("deleteDate", new Date());
		map.put("deleteIp", request.getRemoteHost());
		int flag = mileaStaticsService.deleteFalses(map);
		return new RestfulResponse<Integer>(flag);
	}
	
	@RequestMapping(value="mileageStatics/import",method=RequestMethod.POST)
	@ApiOperation(value = "里程数统计数据导入")
	public RestfulResponse<Map<String, Object>> deleteFalse(HttpServletRequest request, MultipartFile file,
			String templetId,String fileName){
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (file != null) {
			try {
				if (StringUtil.isBlank(fileName)) {
					fileName = file.getOriginalFilename();
				}
				map = mileaStaticsService.analyzeExcelData(fileName, file
						.getInputStream(), TokenUtil.getUserId(request), request.getRemoteHost());
			} catch (Exception e) {
				Shift.fatal(StatusCode.FAIL);
			}
		}
		
		return new RestfulResponse<Map<String, Object>>(map);
	}
}
