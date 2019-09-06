package com.togest.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.togest.common.util.json.JsonMapper;
import com.togest.config.BussinessType;
import com.togest.domain.TaskPublisher;
import com.togest.request.DefectRepeatRequest;
import com.togest.scheduled.service.DefectRepeatTaskEvent;
import com.togest.service.DefectService;

@Component
public class DefectRepeatTaskListener implements ApplicationListener<DefectRepeatTaskEvent>{

	@Autowired
	private DefectService defectService;
	@Value("${my.repeatDefect.isSuiDe}")
	private Boolean isSuiDe = false;
	
	@Override
	public void onApplicationEvent(DefectRepeatTaskEvent event) {
		if(BussinessType.DefectTask.getStatus().equals(event.getSource().getBusinessType())){
			TaskPublisher taskPublisher = event.getSource();
			DefectRepeatRequest request = (DefectRepeatRequest) JsonMapper.fromJsonString(
					taskPublisher.getPayload(), DefectRepeatRequest.class);
			if(isSuiDe){
				defectService.defectRepeatJudge(request);
				//defectService.handleSuiDeRepeatDefect(request);
			}else{
				defectService.defectRepeatData(request);
			}
			
		}
		
	}
	


}
