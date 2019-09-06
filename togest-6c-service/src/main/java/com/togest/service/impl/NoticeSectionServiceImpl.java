package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.togest.clien.sevice.DeptFeignService;
import com.togest.clien.sevice.UserFeignService;
import com.togest.clien.sevice.WorkflowFeignService;
import com.togest.client.resquest.ProcessInstanceDelRequest;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.Constants;
import com.togest.config.NoticeStatus;
import com.togest.dao.NoticeSectionDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.NoticeDTO;
import com.togest.domain.NoticeSectionDTO;
import com.togest.domain.Page;
import com.togest.model.resposne.RestfulResponse;
import com.togest.request.NoticeQueryFilter;
import com.togest.response.DefectC1Form;
import com.togest.response.statistics.NoticeFlowCountData;
import com.togest.service.CrudService;
import com.togest.service.NoticeDefectService;
import com.togest.service.NoticeSectionService;
import com.togest.utils.PageUtils;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class NoticeSectionServiceImpl extends CrudService<NoticeSectionDao, NoticeSectionDTO>
	implements NoticeSectionService {

	@Autowired
	private NoticeDefectService noticeDefectService;
	@Autowired
	private DeptFeignService deptFeignService;
	@Autowired
	private UserFeignService userFeignService;
	@Autowired
	private WorkflowFeignService workflowFeignService;
	
	@Override
	@Transactional
	public int updateStatus(String ids, String status) {
		if(StringUtil.isBlank(ids)) {
			return 0;
		}
		return dao.updateStatus(Arrays.asList(ids.split(",")), status);
	}

	@Override
	@DictAggregation
	public List<NoticeSectionDTO> getByNoticeId(String noticeId) {
		List<NoticeSectionDTO> list = dao.getByNoticeId(noticeId);
		setPersonAndSectionName(list);
		return list;
	}

	@Override
	@DictAggregation
	public NoticeSectionDTO getDetails(String id) {
		NoticeSectionDTO entity = dao.getNoticeSectionDetail(id);
		if(StringUtil.isNotEmpty(entity)) {
			List<NoticeSectionDTO> list = new ArrayList<NoticeSectionDTO>();
			list.add(entity);
			setPersonAndSectionName(list);
		}
		return entity;
	}
	
	@Override
	@DictAggregation
	public Page<NoticeSectionDTO> findNoticeSection(Page page, NoticeQueryFilter entity) {
		PageUtils.setPage(page);
		List<NoticeSectionDTO> list = dao.findNoticeSection(entity);
		setPersonAndSectionName(list);
		return PageUtils.buildPage(list);
	}

	/**
	 * 当删除还未下发的处级通知书时调用
	 * 真删段级通知书以及关联缺陷中间表数据
	 */
	@Override
	@Transactional
	public int deleteByNoticeId(String ids) {
		if(StringUtil.isBlank(ids)) {
			return 0;
		}
		List<String> noticeIds = Arrays.asList(ids.split(","));
		List<String> noticeSectionIds = dao.getIdByNoticeId(noticeIds);
		dao.deleteByNoticeId(noticeIds);
		if(StringUtil.isNotEmpty(noticeSectionIds)) {
			noticeDefectService.deleteByNoticeSectionId(noticeSectionIds);
		}
		return noticeSectionIds.size();
	}
	
	@Override
	@Transactional
	public int deleteFalse(String ids, String deleteIp, String deleteBy) {
		if(StringUtil.isBlank(ids)) {
			return 0;
		}
		List<String> idList = Arrays.asList(ids.split(","));
		List<String> processInstanceIds = dao.getProcessInstanceIds(idList);
		int i = dao.deleteFalse(idList, deleteIp, deleteBy, new Date());
		if(StringUtil.isNotEmpty(processInstanceIds)) {
			List<ProcessInstanceDelRequest> tasks = new ArrayList<ProcessInstanceDelRequest>();
			for (String id : processInstanceIds) {
				ProcessInstanceDelRequest entity = new ProcessInstanceDelRequest();
				entity.setProcessInstanceId(id);
				entity.setDeleteReason(deleteBy);
				tasks.add(entity);
			}
			workflowFeignService.deleteTask(tasks);
		}
		return i;
	}

	@Override
	public boolean needUpdateStatus(String id, boolean flag) {
		boolean needUpdate = true;
		List<String> statusList = dao.findStatusList(id);
		for (String status : statusList) {
			if(flag) {//签收
				if(!(NoticeStatus.Feedback.getStatus().equals(status)
						||NoticeStatus.Cancel.getStatus().equals(status))) {
					needUpdate = false;
				}
			} else {//反馈
				if(!NoticeStatus.Cancel.getStatus().equals(status)) {
					needUpdate = false;
				}
			}
		}
		return needUpdate;
	}

	@Override
	public NoticeSectionDTO get(String id) {
		NoticeSectionDTO entity = super.get(id);
		if(StringUtil.isNotEmpty(entity)) {
			List<NoticeSectionDTO> list = new ArrayList<NoticeSectionDTO>();
			list.add(entity);
			setPersonAndSectionName(list);
		}
		/*if(StringUtil.isNotEmpty(entity)) {
			List<String> idList = new ArrayList<String>();
			String id1 = entity.getReceiptPerson();
			String id2 = entity.getFeedbackPerson();
			if(StringUtil.isNotBlank(id1)&&!idList.contains(id1)) {
				idList.add(id1);
			}
			if(StringUtil.isNotBlank(id2)&&!idList.contains(id2)) {
				idList.add(id2);
			}
			if(idList.size() > 0) {
				Map<String, String> map = getName(StringUtils.join(idList, ","), Constants.PERSON);
				if(map.containsKey(id1)) {
					entity.setReceiptPersonName(map.get(id1));
				}
				if(map.containsKey(id2)) {
					entity.setFeedbackPersonName(map.get(id2));
				}
			}
		}*/
		return entity;
	}
	
	@Override
	public Map<String, String> getName(String ids, String flag) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtil.isNotBlank(ids)&&StringUtil.isNotBlank(flag)) {
			RestfulResponse<List<Map<String, String>>> name = null;
			if(Constants.DEPT.equals(flag)) {
				name = deptFeignService.getNameById(ids.toString());
			} else if(Constants.PERSON.equals(flag)) {
				name = userFeignService.getNameByIds(ids);
			}
			if(name != null) {
				List<Map<String, String>> data = name.getData();
				if(StringUtil.isNotEmpty(data)) {
					for (Map<String, String> nameMap : data) {
						map.put(nameMap.get("id"), nameMap.get("name"));
					}
				}
			}
		}
		return map;
	}
	
	public void setPersonAndSectionName(List<NoticeSectionDTO> list) {
		List<String> deptIdList = new ArrayList<String>();
		List<String> pIdList = new ArrayList<String>();
		if(StringUtil.isNotEmpty(list)) {
			for (NoticeSectionDTO noticeSection : list) {
				NoticeDTO notice = noticeSection.getNotice();
				String sectionId = noticeSection.getSectionId();
				if(StringUtil.isNotBlank(sectionId)&&!deptIdList.contains(sectionId)) {
					deptIdList.add(sectionId);
				}
				String id1 = noticeSection.getReceiptPerson();
				String id2 = noticeSection.getFeedbackPerson();
				String id3 = null;
				String id4 = null;
				if(StringUtil.isNotEmpty(notice)) {
					id3 = notice.getIssuePerson();
					id4 = notice.getResponsiblePerson();
				}
				if(StringUtil.isNotBlank(id1)&&!pIdList.contains(id1)) {
					pIdList.add(id1);
				}
				if(StringUtil.isNotBlank(id2)&&!pIdList.contains(id2)) {
					pIdList.add(id2);
				}
				if(StringUtil.isNotBlank(id3)&&!pIdList.contains(id3)) {
					pIdList.add(id3);
				}
				if(StringUtil.isNotBlank(id4)&&!pIdList.contains(id4)) {
					pIdList.add(id4);
				}
			}
			if(deptIdList.size() > 0) {
				Map<String, String> deptMap = getName(StringUtils.join(deptIdList, ","), Constants.DEPT);
				for (NoticeSectionDTO noticeSection : list) {
					String sectionId = noticeSection.getSectionId();
					if(StringUtil.isNotBlank(sectionId)&&deptMap.containsKey(sectionId)) {
						noticeSection.setSectionName(deptMap.get(sectionId));
					}
				}
			}
			if(pIdList.size() > 0) {
				Map<String, String> pMap = getName(StringUtils.join(pIdList, ","), Constants.PERSON);
				for (NoticeSectionDTO noticeSection : list) {
					NoticeDTO notice = noticeSection.getNotice();
					String id1 = noticeSection.getReceiptPerson();
					String id2 = noticeSection.getFeedbackPerson();
					String id3 = null;
					String id4 = null;
					if(StringUtil.isNotEmpty(notice)) {
						id3 = notice.getIssuePerson();
						id4 = notice.getResponsiblePerson();
					}
					if(StringUtil.isNotBlank(id1)&&pMap.containsKey(id1)) {
						noticeSection.setReceiptPersonName(pMap.get(id1));
					}
					if(StringUtil.isNotBlank(id2)&&pMap.containsKey(id2)) {
						noticeSection.setFeedbackPersonName(pMap.get(id2));
					}
					if(StringUtil.isNotBlank(id3)&&pMap.containsKey(id3)) {
						notice.setIssuePersonName(pMap.get(id3));
					}
					if(StringUtil.isNotBlank(id4)&&pMap.containsKey(id4)) {
						notice.setResponsiblePersonName(pMap.get(id4));
					}
				}
			}
		}
	}

	@Override
	public String getNoticeIdByNoticeSectionId(String id) {
		return dao.getNoticeIdByNoticeSectionId(id);
	}

	@Override
	@Transactional
	public int deleteFalseByNoticeId(List<String> noticeIds, String deleteIp, String deleteBy, Date deleteDate) {
		
		List<String> idList = dao.getIdByNoticeId(noticeIds);
		int i = dao.deleteFalseByNoticeId(noticeIds, deleteIp, deleteBy, deleteDate);
		if(StringUtil.isNotEmpty(idList)) {
			List<String> processInstanceIds = dao.getProcessInstanceIds(idList);
			if(StringUtil.isNotEmpty(processInstanceIds)) {
				List<ProcessInstanceDelRequest> tasks = new ArrayList<ProcessInstanceDelRequest>();
				for (String id : processInstanceIds) {
					ProcessInstanceDelRequest entity = new ProcessInstanceDelRequest();
					entity.setProcessInstanceId(id);
					entity.setDeleteReason(deleteBy);
					tasks.add(entity);
				}
				workflowFeignService.deleteTask(tasks);
			}
		}
		return i;
	}

	@Override
	@Transactional
	public int updateFeedbackInfo(String ids, String feedbackPerson, String feedbackContent, Date feedbackDate) {
		if(StringUtil.isBlank(ids)) {
			return 0;
		}
		return dao.updateFeedbackInfo(Arrays.asList(ids.split(",")), feedbackPerson, feedbackContent, feedbackDate);
	}

	@Override
	public NoticeFlowCountData findNoticeFlowCount(NoticeQueryFilter n1) {

		return dao.findNoticeFlowCount(n1);
	}
}
