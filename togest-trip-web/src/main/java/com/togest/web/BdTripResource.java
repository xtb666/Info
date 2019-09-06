package com.togest.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.code.util.PolymerizeDataService;
import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.domain.BdCorresStandDetail;
import com.togest.domain.Page;
import com.togest.domain.dto.BdCorresStandDTO;
import com.togest.domain.dto.BdTripInformationDTO;
import com.togest.domain.dto.BdTripVideoMenuDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.service.BdCorresStandDetailService;
import com.togest.service.BdCorresStandService;
import com.togest.service.BdTripInformationService;
import com.togest.service.BdTripVideoMenuService;
import com.togest.util.ResourcesCodeUtil;
import com.togest.util.StatusCode;

import io.swagger.annotations.ApiOperation;

@RestController
public class BdTripResource {
    @Autowired
    private BdTripInformationService bdTripInformationService;
    @Autowired
    private BdCorresStandService bdCorresStandService;
    @Autowired
    private BdCorresStandDetailService bdCorresStandDetailService;
    @Autowired
    private BdTripVideoMenuService bdTripVideoMenuService;
    @Autowired
    private PolymerizeDataService polymerizeDataService;
    private static final int START_NUM = 0;
    private static final int END_NUM = 25;
    
    @RequestMapping(value = "bdTrip/corresStand/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看故标对应列表")
    public RestfulResponse<Page<BdCorresStandDetail>> corresStandList(BdTripInformationDTO dto) throws Exception {
    	Page<BdCorresStandDetail> pg = new Page<>();
    	if(StringUtil.isBlank(dto.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdTripInformationDTO entity = bdTripInformationService.get(dto.getId());
        if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        List<BdCorresStandDetail> allList = new ArrayList<>();
        if(StringUtil.isNotBlank(entity.getStandardDistance()) || StringUtil.isNotBlank(entity.getKiloDistance())) {
        	 BdCorresStandDTO params = new BdCorresStandDTO();
             params.setLineId(entity.getLineId());
             params.setPavilionId(entity.getPavilionId());
             params.setPsPdId(entity.getPsPdId());
     		List<BdCorresStandDTO> list = bdCorresStandService.findList(params);
     		if(!CollectionUtils.isEmpty(list)) {
     			List<BdCorresStandDetail> standDetailList = bdCorresStandDetailService.
     					findListByDistance(list.get(0).getId(), entity.getStandardDistance(), entity.getKiloDistance());
     			if(!CollectionUtils.isEmpty(standDetailList)) {
     				BdCorresStandDetail bdCorresStandDetail = standDetailList.get(0); //最近的公里标
     				allList = bdCorresStandDetailService.findListByDistanceAndGlb(bdCorresStandDetail.getId(), list.get(0).getId(), entity.getStandardDistance(), entity.getKiloDistance());
     			}
     		}
     		if(StringUtil.isNotEmpty(allList)){
     			pg.setList(allList);
     			pg.setTotal((long)allList.size());
                polymerizeDataService.setNamesByIds(allList, ResourcesCodeUtil.BDCORRESSTANDDETAIL_RESOURCESCODE);
            }
        }
        return new RestfulResponse<Page<BdCorresStandDetail>>(pg);
    }
    
    @RequestMapping(value = "bdTrip/videoMenu/lists", method = RequestMethod.GET)
    @ApiOperation(value = "查看管内综合视频 对应列表")
    public RestfulResponse<Page<BdTripVideoMenuDTO>> videoMenuList(BdTripInformationDTO dto) throws Exception {
    	Page<BdTripVideoMenuDTO> pg = new Page<>();
    	if(StringUtil.isBlank(dto.getId())){
    		Shift.fatal(StatusCode.INVALID_PARAMS_CONVERSION);
    	}
    	BdTripInformationDTO entity = bdTripInformationService.get(dto.getId());
        if(StringUtil.isEmpty(entity)){
        	Shift.fatal(StatusCode.ID_DATA_EMPTY);
        }
        if(StringUtil.isNotBlank(entity.getStandardDistance()) || StringUtil.isNotBlank(entity.getKiloDistance())) {
        	 BdCorresStandDTO params = new BdCorresStandDTO();
             params.setLineId(entity.getLineId());
             params.setPavilionId(entity.getPavilionId());
             params.setPsPdId(entity.getPsPdId());
     		List<BdCorresStandDTO> list = bdCorresStandService.findList(params);
     		if(!CollectionUtils.isEmpty(list)) {
     			List<BdCorresStandDetail> standDetailList = bdCorresStandDetailService.
     					findListByDistance(list.get(0).getId(), entity.getStandardDistance(), entity.getKiloDistance());
     			if(!CollectionUtils.isEmpty(standDetailList)) {
     				BdCorresStandDetail bdCorresStandDetail = standDetailList.get(0); //最近的公里标
     				if(StringUtil.isNotBlank(bdCorresStandDetail.getGlb())) {
     					List<BdTripVideoMenuDTO> allList = new ArrayList<>();
     		     		List<BdTripVideoMenuDTO> videoMenuDTOList = bdTripVideoMenuService.findListByGlb(entity.getLineId(), bdCorresStandDetail.getGlb(), bdCorresStandDetail.getDistance());
     		     		if(!CollectionUtils.isEmpty(videoMenuDTOList)) {
     		 				allList = bdTripVideoMenuService.findListByDistanceAndGlb(videoMenuDTOList.get(0).getId(), entity.getLineId(), bdCorresStandDetail.getGlb(), bdCorresStandDetail.getDistance());
     		 			}
     		     		if(StringUtil.isNotEmpty(allList)){
//     		                polymerizeDataService.setNamesByIds(allList, ResourcesCodeUtil.BDTRIPINFORMATION_RESOURCESCODE);
     		                pg.setList(allList);
     		                pg.setTotal((long)allList.size());
     		            }
     				}
     			}
     		}
        }
        return new RestfulResponse<Page<BdTripVideoMenuDTO>>(pg);
    }
    
    @RequestMapping(value = "bdTrip/svgImage", method = RequestMethod.GET)
	@ApiOperation(value = "svg图信息")
	public RestfulResponse<String> svgImage(String id){
		String svgImageStr = bdTripInformationService.svgImage(id);
		return new RestfulResponse<String>(svgImageStr);
	}
}
