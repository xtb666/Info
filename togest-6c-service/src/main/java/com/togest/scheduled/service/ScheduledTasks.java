package com.togest.scheduled.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.togest.common.util.string.StringUtil;
import com.togest.config.BussinessType;
import com.togest.dao.DefectDao;
import com.togest.dao.DefectHandleInfoDao;
import com.togest.dao.NoticeSectionDao;
import com.togest.domain.DefectHandleInfo;
import com.togest.domain.ExpireTask;
import com.togest.request.DefectAddField;
import com.togest.service.ExpireTaskService;

@Configuration
@EnableScheduling
public class ScheduledTasks {

	@Autowired
	private ExpireTaskService expireTaskService;
	@Autowired
	private DefectHandleInfoDao defectHandleInfoDao;
	@Autowired
	private NoticeSectionDao noticeSectionDao;
	@Autowired
	private DefectDao defectDao;

	// 每5秒执行一次
	@Scheduled(cron = "0 0 0 * * ?")
	public void scheduler() {
		this.task();
	}

	public void task() {
		
		List<ExpireTask> list = expireTaskService.findAllList();
		
		if(StringUtil.isNotEmpty(list)){
			List<String> defectList = new ArrayList<String>();
			List<String> defectCheckList = new ArrayList<String>();
			List<String> planList = new ArrayList<String>();
			List<String> noticeList = new ArrayList<String>();
			Date date = new Date();
			for (ExpireTask expireTask : list) {
				Date expireDate = expireTask.getExpireDate();
				String bussinessType = expireTask.getBussinessType();
				if (date.getTime() >= expireDate.getTime()) {
					if(BussinessType.Defect.getStatus().equals(bussinessType)){
						defectList.add(expireTask.getDataId());
					}
					else if(BussinessType.Plan.getStatus().equals(bussinessType)){
						planList.add(expireTask.getDataId());
					}
					else if(BussinessType.Notice.getStatus().equals(bussinessType)){
						noticeList.add(expireTask.getDataId());
					}else if(BussinessType.DefectCheck.getStatus().equals(bussinessType)){
						defectCheckList.add(expireTask.getDataId());
					}
				}
			}
			
			if (StringUtil.isNotEmpty(defectList)) {
				DefectHandleInfo info = new DefectHandleInfo();
				info.setIsTimeouted(1);
				defectHandleInfoDao.updateBatch(defectList, info);
				for (String defectId : defectList) {
					DefectAddField defectAddField = new DefectAddField();
					defectAddField.setIsTimeouted(1);
					defectAddField.setId(defectId);
					defectDao.updateDefectData(defectAddField);
				}
			}
			if(StringUtil.isNotEmpty(defectCheckList)){
				DefectHandleInfo info = new DefectHandleInfo();
				info.setCheckTimeouted(1);
				defectHandleInfoDao.updateBatch(defectCheckList, info);
				for (String defectId : defectList) {
					DefectAddField defectAddField = new DefectAddField();
					defectAddField.setCheckTimeouted(1);
					defectAddField.setId(defectId);
					defectDao.updateDefectData(defectAddField);
				}
			}
			
			if(StringUtil.isNotEmpty(planList)){
				
			}
			if(StringUtil.isNotEmpty(noticeList)){
				noticeSectionDao.updateOvertime(noticeList);
			}
		}

	}
}