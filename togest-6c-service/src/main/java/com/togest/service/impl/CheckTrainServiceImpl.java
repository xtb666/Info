package com.togest.service.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.code.client.ImportClient;
import com.togest.common.annotation.Shift;
import com.togest.common.util.ObjectUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.CheckTrainDao;
import com.togest.domain.CheckSystemDTO;
import com.togest.domain.CheckTrainDTO;
import com.togest.domain.Page;
import com.togest.response.ParseDataResponse;
import com.togest.service.CheckSystemService;
import com.togest.service.CheckTrainService;
import com.togest.utils.PageUtils;
@Service
public class CheckTrainServiceImpl implements CheckTrainService{
	@Autowired
	private CheckTrainDao dao;
	@Autowired
	protected ImportClient importClient;
	@Autowired
	private CheckSystemService checkSystemService;
	
	public int save(CheckTrainDTO entity){
		if(StringUtil.isBlank(entity.getTrainNum())){
			Shift.fatal(StatusCode.TRAINNUM_EMPTY);
		}else if(checkTrainNumRepeat(entity.getTrainNum(), entity.getId())){
			Shift.fatal(StatusCode.TRAINNUM_REPEAT);
		}
		if(entity.getIsNewRecord()){
			entity.preInsert();
			return dao.insert(entity);
		}else{
			return dao.update(entity);
		}
	}
	
	public List<CheckTrainDTO> findList(CheckTrainDTO entity){
		return dao.findList(entity);
	}
	
	public Page<CheckTrainDTO> findPages(Page page,CheckTrainDTO entity){
		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findList(entity));
	}
	
	@Override
	public int deleteFalses(String ids, String deleteBy, String deleteIp) {
		if(StringUtil.isNotBlank(ids)){
			List<String> list = Arrays.asList(ids.split(","));
			int i = dao.deleteFalses(list, deleteBy, deleteIp, new Date());
			return i;
		}
		return 0;
	}

	@Override
	public CheckTrainDTO get(String id) {
		return dao.getByKey(id);
	}
	
	public boolean checkTrainNumRepeat(String trainNum,String id){
		CheckTrainDTO dto = new CheckTrainDTO();
		dto.setTrainNum(trainNum);
		CheckTrainDTO entity = dao.getByEntity(dto);
		if (entity == null || entity.getId().equals(id)) {
			return false;
		}
		return true;	
	}

	@Override
	public String getByTrainNo(String trainNo) {
		CheckTrainDTO dto = new CheckTrainDTO();
		dto.setTrainNum(trainNo);
		CheckTrainDTO entity = dao.getByEntity(dto);
		if(entity!=null){
			return entity.getId();
		}
		return null;
	}
	
	public Map<String, Object> importData(String filename, InputStream inputStream, String templetId,String createBy,String  sectionId){
		Map<String, Object> map = new HashMap<String,Object>();
		int count = 0;
		int repeat = 0;
		StringBuilder repeatStr = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();
		try {
			List<Map<String, Object>> result = importClient.analyzeExcelData(filename, inputStream,templetId);
			Map<String, String> checkSystemMap = new HashMap<String,String>();
			List<CheckSystemDTO> checkSystems = checkSystemService.findAllList();
			if(StringUtil.isNotEmpty(checkSystems)){
				for (CheckSystemDTO checkSystemDTO : checkSystems) {
					checkSystemMap.put(checkSystemDTO.getName(), checkSystemDTO.getId());
				}
			}
			if(StringUtil.isNotEmpty(result)){
				List<CheckTrainDTO> dataList = ObjectUtils.mapToObject(result, CheckTrainDTO.class);
				for (int i = 0; i < dataList.size(); i++) {
					boolean fg = true;
					CheckTrainDTO entity = dataList.get(i);
					String systemName = entity.getSystemName();
					if(StringUtil.isNotBlank(systemName)){
						String systemId = checkSystemMap.get(systemName);
						if(StringUtil.isNotBlank(systemId)){
							entity.setSystemId(systemId);
						}else{
							fg = false;
							errorMsg.append("第"+(i+2)+"行，检测装置"+systemName+"未找到！</br>");
						}
					}else{
						fg = false;
						errorMsg.append("第"+(i+2)+"行，检测装置为空！</br>");
					}
					String trainNum = entity.getTrainNum();
					if(StringUtil.isNotBlank(trainNum)){
						
					}else{
						fg = false;
						errorMsg.append("第"+(i+2)+"行，车辆编号为空！</br>");
					}
					if(fg){
						if(!checkTrainNumRepeat(trainNum, null)){
							entity.setCreateBy(createBy);
							entity.setCreateDate(new Date());
							entity.setSectionId(sectionId);
							save(entity);
							count++;
						}else{
							repeat++;
							repeatStr.append((i+2)+",");
						}
					}
					
				}
			}
		} catch (Exception e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
		}
		
		map.put("count", count);
		map.put("errorMsg", errorMsg.toString());
		map.put("repeat", repeat);
		map.put("repeatMsg", repeatStr.toString());
		return map;
	}
	
	
	
}
