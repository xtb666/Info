package com.togest.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.togest.common.util.CollectionUtils;
import com.togest.config.DefectStatus;
import com.togest.domain.DefectCheckHandle;
import com.togest.domain.DefectReformHandle;
import com.togest.request.CQueryFilter;
import com.togest.response.DefectC1Form;
import com.togest.service.Defect1CService;
import com.togest.service.DefectCheckHandleService;
import com.togest.service.DefectReformHandleService;
import com.togest.service.DefectSysncQuestionService;

/**
 * <p>Title: YTSIXCResource</p>
 * <p>Description: 6C历史缺陷同步到问题库</p>
 * <p>Company: 北京太格</p> 
 * @author	江政昌
 * @date	2018年10月21日下午3:22:25
 * @version 1.0
 * 
 * reviewRectification方法同步
 */
@RequestMapping("yt/SIXC")
@RestController
public class YTSIXCResource {

	@Autowired
	private Defect1CService c1Service;
	@Autowired
	private DefectSysncQuestionService defectSysncQuestionService;
	@Autowired
	private DefectCheckHandleService defectCheckHandleService;
	@Autowired
	private DefectReformHandleService defectReformHandleService;
	
	public static final String YING_TAN_GONG_DIAN_DUAN_SECTION_ID = "3ceeec04f1264f1290ba8763279a5de0"; //鹰潭段 sectionId
//	public static final String NAN_CHANG_GONG_DIAN_DUAN_SECTION_ID = "16516b9988bb470cb5225825997e9562";
	int Number = 100; //每50个缺陷提交一次
	public static final String level1 = "1"; //一级缺陷
	public static final String level2 = "2"; //二级缺陷
	//同步6C 复核整改缺陷
	@RequestMapping(value = "defect/reviewRectification/init", method = RequestMethod.GET)
	public void reviewRectification() {
		CQueryFilter c = new CQueryFilter();
		c.setDefectStatus(DefectStatus.ReviewRectification.getStatus() + "," + DefectStatus.CHECKHANDLE.getStatus());
		c.setSectionId(YING_TAN_GONG_DIAN_DUAN_SECTION_ID);
		c.setDefectLevel(level1);
		List<DefectC1Form> list = c1Service.findDefectForm(c);
		commonSysncDefectData(list);
	}
	
	//同步6C 整改验证
	@RequestMapping(value = "defect/rectificationVerification/init", method = RequestMethod.GET)
	public void rectificationVerification() {
		CQueryFilter c = new CQueryFilter();
		c.setDefectStatus(DefectStatus.RectificationVerification.getStatus());
		c.setSectionId(YING_TAN_GONG_DIAN_DUAN_SECTION_ID);
		c.setDefectLevel(level1);
		List<DefectC1Form> list = c1Service.findDefectForm(c);
		if(!CollectionUtils.isEmpty(list)){
			List<String> totalList = commonSysncDefectData(list);
			if(!CollectionUtils.isEmpty(totalList)){
				for(String defectId : totalList){
					DefectReformHandle defectReformHandle = defectReformHandleService.getReformByKey(defectId);
					DefectCheckHandle defectCheckHandle = defectCheckHandleService.getCheckByKey(defectId);
					defectSysncQuestionService.sysncDefectReformHandleData(defectCheckHandle, defectReformHandle);
				}
			}
		}
	}
	
	//同步6C 延迟审核缺陷
	@RequestMapping(value = "defect/delay/init", method = RequestMethod.GET)
	public void DELAY() {
		CQueryFilter c = new CQueryFilter();
		c.setDefectStatus(DefectStatus.DELAY.getStatus());
		c.setSectionId(YING_TAN_GONG_DIAN_DUAN_SECTION_ID);
		c.setDefectLevel(level1);
		List<DefectC1Form> list = c1Service.findDefectForm(c);
		commonSysncDefectData(list);
	}
	
	//同步6C 二级缺陷
	@RequestMapping(value = "defect/register/level2/init", method = RequestMethod.GET)
	public void register() {
		CQueryFilter c = new CQueryFilter();
		c.setDefectStatus(DefectStatus.DefectRegister.getStatus());
		c.setSectionId(YING_TAN_GONG_DIAN_DUAN_SECTION_ID);
		c.setDefectLevel(level2);
		List<DefectC1Form> list = c1Service.findDefectForm(c);
		commonSysncDefectData(list);
	}
	
	private List<String> commonSysncDefectData(List<DefectC1Form> list) {
		List<String> totalList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(list)){
			List<String> idList = new ArrayList<>();
			for(int i=0; i<= list.size() -1 ; i++){
				totalList.add(list.get(i).getId());
				idList.add(list.get(i).getId());
				if(i!=0 && i % Number == Number - 1 ){
					defectSysncQuestionService.sysncDefectData(idList);
					idList.clear();
				}else if(i == list.size() - 1){
					defectSysncQuestionService.sysncDefectData(idList);
					idList.clear();
				}
			}
		}
		return totalList;
	}
}
