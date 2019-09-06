package com.togest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togest.common.annotation.Shift;
import com.togest.common.util.CollectionUtils;
import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;
import com.togest.config.Constants;
import com.togest.config.NoticeStatus;
import com.togest.dao.DefectDao;
import com.togest.dao.NoticeDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Defect;
import com.togest.domain.NoticeDTO;
import com.togest.domain.NoticeDefect;
import com.togest.domain.NoticeSectionDTO;
import com.togest.domain.Page;
import com.togest.request.NoticeQueryFilter;
import com.togest.response.DefectC1Form;
import com.togest.response.DefectC1Response;
import com.togest.response.DefectC3Form;
import com.togest.response.DefectC3Response;
import com.togest.response.statistics.NoticeFlowCountData;
import com.togest.service.Check1CSectionService;
import com.togest.service.Check1CService;
import com.togest.service.CrudService;
import com.togest.service.Defect1CService;
import com.togest.service.Defect3CService;
import com.togest.service.NoticeDefectService;
import com.togest.service.NoticeSectionService;
import com.togest.service.NoticeService;
import com.togest.service.PlanService;
import com.togest.utils.PageUtils;

@Service
public class NoticeServiceImpl extends CrudService<NoticeDao, NoticeDTO> implements NoticeService {

	@Autowired
	private DefectDao defectDao;
	@Autowired
	private NoticeSectionService noticeSectionService;
	@Autowired
	private NoticeDefectService noticeDefectService;
	@Autowired
	private Defect1CService defect1CService;
	@Autowired
	private Check1CService check1CService;
	@Autowired
	private Check1CSectionService check1CSectionService;
	@Autowired
	private Defect3CService defect3CService;
	//@Autowired
	//private PlanService planService;

	@Value("${my.defectTimeout.oneLevel}")
	private Integer oneLevel;

	private static final String ONEDEFECTLEVEL = "1";
	
	/**
	 * 自动生成的通知书编号
	 */
	@Override
	public Map<String, Object> getLatestNumber() {
		Map<String, Object> map = new HashMap<>();
		String year = String.valueOf(DateUtils.getSingleYear());
		map.put("noticeNumberYear", year);
		map.put("noticeNumber", Optional.ofNullable(dao.getNoticeNumber(year)).map(n -> n+1).orElse(1));
		return map;
	}

	/**
	 * 
	 * 检查通知书编号是否重复
	 * 重复返回true
	 */
	@Override
	public boolean isNumberRepeat(String year, Integer number,String id) {
		String noticeId = dao.checkNumberRepeat(year, number);
		if(StringUtil.isBlank(noticeId)||noticeId.equals(id)){
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public int save(NoticeDTO entity) {
		/**
		 * 页面编辑notice_number_year和notice_number字段
		 * notice_number_str字段后台维护
		 */
		
		if(isNumberRepeat(entity.getNoticeNumberYear(), entity.getNoticeNumber(),entity.getId())){
			Shift.fatal(StatusCode.NOTICE_NUM_EXIT);
		}
		entity.setNoticeNumberStr(String.format("%0" + 3 + "d", entity.getNoticeNumber()));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(entity.getIssueDate());
		calendar.add(Calendar.DATE, entity.getNoticeDay());
		entity.setRectifyDate(calendar.getTime());
		if(StringUtil.isBlank(entity.getId())){
			entity.setNoticeStatus(NoticeStatus.Issued.getStatus());
		}
		super.save(entity);
		generateNoticeCheck(entity);
		return 1;
	}

	public void generateNoticeCheck(NoticeDTO entity) {
		String noticeId = entity.getId();
		if (StringUtil.isBlank(noticeId)) {
			return;
		}
		/*String checkIds = entity.getCheckIds();
		if (StringUtil.isNotBlank(checkIds)) {
			List<String> checkIdList = Arrays.asList(checkIds.split(","));
			for (String checkId : checkIdList) {
				dao.insertNoticeCheck(noticeId, checkId);
			}
			
			List<Defect> defectList = defectDao.getByPlanIds(checkIdList);
			if (StringUtil.isNotEmpty(defectList)) {
				List<Defect> defects = new ArrayList<Defect>();
				for (int i = 0; i < defectList.size(); i++) {
					Defect defect = defectList.get(i);
					if (ONEDEFECTLEVEL.equals(defect.getDefectLevel())) {
						defects.add(defect);
					}
				}
				if(StringUtil.isNotEmpty(defects)){
					handleDefectDate(defects, noticeId);
				}
			}
		}*/

		noticeSectionService.deleteByNoticeId(noticeId);
		dao.deleteNoticeCheck(noticeId, null);
		String checkIds = entity.getCheckIds();
		if (StringUtil.isNotBlank(checkIds)) {
			List<String> checkIdList = Arrays.asList(checkIds.split(","));
			for (String checkId : checkIdList) {
				dao.insertNoticeCheck(noticeId, checkId);
			}
			List<String> checkSectionIds = check1CSectionService.getByCheckIds(checkIdList);
			if(StringUtil.isNotEmpty(checkSectionIds)){
				List<Defect> defectList = defectDao.getByInfoIds(checkSectionIds);
				if (StringUtil.isNotEmpty(defectList)) {
					List<Defect> defects = new ArrayList<Defect>();
					for (int i = 0; i < defectList.size(); i++) {
						Defect defect = defectList.get(i);
						if (ONEDEFECTLEVEL.equals(defect.getDefectLevel())) {
							defects.add(defect);
						}
					}
					if(StringUtil.isNotEmpty(defects)){
						handleDefectDate(defects, noticeId);
					}
					
				}
			}
		}
		String defectIds = entity.getDefectIds();
		if(StringUtil.isNotBlank(defectIds)){
			List<Defect> defectList =  defectDao.getByKeys(Arrays.asList(defectIds.split(",")));
			if(StringUtil.isNotEmpty(defectList)){
				handleDefectDate(defectList, noticeId);
			}
		}
	}

	public void handleDefectDate(List<Defect> defectList, String noticeId) {
		if (StringUtil.isNotEmpty(defectList)) {
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			for (Defect defect : defectList) {
				String sectionId = defect.getSectionId();
				String defectId = defect.getId();
				if (map.containsKey(sectionId)) {
					List<String> defects = map.get(sectionId);
					defects.add(defectId);
				} else {
					List<String> defects = new ArrayList<String>();
					defects.add(defectId);
					map.put(sectionId, defects);
				}
			}
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				String sectionId = entry.getKey();
				NoticeSectionDTO noticeSection = new NoticeSectionDTO();
				noticeSection.setSectionId(sectionId);
				noticeSection.setNoticeId(noticeId);
				noticeSectionService.save(noticeSection);
				String noticeSectionId = noticeSection.getId();
				List<String> defects = entry.getValue();
				for (String defectId : defects) {
					NoticeDefect noticeDefect = new NoticeDefect();
					noticeDefect.setDefectId(defectId);
					noticeDefect.setNoticeSectionId(noticeSectionId);
					noticeDefectService.save(noticeDefect);
				}
			}
		}
	}

	@Override
	@Transactional
	public int generateSectionNotice(NoticeDTO entity, Boolean newFlag) {
		String id = entity.getId();
		if (StringUtil.isBlank(id)) {
			return 0;
		}
		if (!newFlag) {
			noticeSectionService.deleteByNoticeId(id);
		}
		List<Defect> defectList = defectDao.getByKeys(Arrays.asList(entity.getDefectIds().split(",")));
		handleDefectDate(defectList, id);
		return 1;
	}

	@Override
	@DictAggregation
	public NoticeDTO getNoticeDetails(String id) {
		NoticeDTO notice = dao.getNoticeDetails(id);
		if (StringUtil.isNotEmpty(notice)) {
			List<String> idList = new ArrayList<String>();
			String id1 = notice.getIssuePerson();
			String id2 = notice.getResponsiblePerson();
			if (StringUtil.isNotBlank(id1) && !idList.contains(id1)) {
				idList.add(id1);
			}
			if (StringUtil.isNotBlank(id2) && !idList.contains(id2)) {
				idList.add(id2);
			}
			if (idList.size() > 0) {
				Map<String, String> map = noticeSectionService.getName(StringUtils.join(idList, ","), Constants.PERSON);
				if (StringUtil.isNotBlank(id1) && map.containsKey(id1)) {
					notice.setIssuePersonName(map.get(id1));
				}
				if (StringUtil.isNotBlank(id2) && map.containsKey(id2)) {
					notice.setResponsiblePersonName(map.get(id2));
				}
			}

			List<NoticeSectionDTO> noticeSections = notice.getNoticeSections();
			if (StringUtil.isNotEmpty(noticeSections)) {
				StringBuilder ids = new StringBuilder();
				for (NoticeSectionDTO noticeSection : noticeSections) {
					String sectionId = noticeSection.getSectionId();
					if (StringUtil.isNotBlank(sectionId)) {
						ids.append(sectionId).append(",");
					}
				}
				if (ids.length() > 0) {
					Map<String, String> map = noticeSectionService.getName(ids.toString(), Constants.DEPT);
					for (NoticeSectionDTO noticeSection : noticeSections) {
						String sectionId = noticeSection.getSectionId();
						if (StringUtil.isNotBlank(sectionId) && map.containsKey(sectionId)) {
							noticeSection.setSectionName(map.get(sectionId));
						}
					}
				}
			}
		}
		return notice;
	}

	@Override
	@Transactional
	public int updateNoticeStatus(String ids, String noticeStatus) {
		if (StringUtil.isBlank(ids)) {
			return 0;
		}
		return dao.updateNoticeStatus(Arrays.asList(ids.split(",")), noticeStatus);
	}

	@Override
	@Transactional
	public int delete(String ids, String deleteIp, String deleteBy) {
		if (StringUtil.isBlank(ids)) {
			return 0;
		}
		List<String> noticeIds = Arrays.asList(ids.split(","));
		noticeSectionService.deleteByNoticeId(ids);
		dao.deleteNoticeChecks(noticeIds);
		int i = dao.deleteFalse(noticeIds, deleteIp, deleteBy, new Date());
		return i;
	}

	@Override
	@Transactional
	public int deleteFalse(String ids, String deleteIp, String deleteBy) {
		Date date = new Date();
		List<String> noticeIds = Arrays.asList(ids.split(","));
		noticeSectionService.deleteFalseByNoticeId(noticeIds, deleteIp, deleteBy, date);
		dao.deleteNoticeChecks(noticeIds);
		int i = dao.deleteFalse(noticeIds, deleteIp, deleteBy, date);
		return i;
	}

	@Override
	@DictAggregation
	public Page<NoticeDTO> findNotice(Page page, NoticeQueryFilter entity) {
		PageUtils.setPage(page);
		List<NoticeDTO> list = dao.findNotice(entity);
		List<String> idList = new ArrayList<String>();
		if (StringUtil.isNotEmpty(list)) {
			for (NoticeDTO notice : list) {
				String id1 = notice.getIssuePerson();
				String id2 = notice.getResponsiblePerson();
				if (StringUtil.isNotBlank(id1) && !idList.contains(id1)) {
					idList.add(id1);
				}
				if (StringUtil.isNotBlank(id2) && !idList.contains(id2)) {
					idList.add(id2);
				}
			}
			if (idList.size() > 0) {
				Map<String, String> map = noticeSectionService.getName(StringUtils.join(idList, ","), Constants.PERSON);
				for (NoticeDTO notice : list) {
					String id1 = notice.getIssuePerson();
					String id2 = notice.getResponsiblePerson();
					if (StringUtil.isNotBlank(id1) && map.containsKey(id1)) {
						notice.setIssuePersonName(map.get(id1));
					}
					if (StringUtil.isNotBlank(id2) && map.containsKey(id2)) {
						notice.setResponsiblePersonName(map.get(id2));
					}
				}
			}
		}
		return PageUtils.buildPage(list);
	}

	@Override
	public NoticeDTO get(String id) {
		NoticeDTO entity = dao.getById(id);
		List<Map<String, String>> list = dao.findNoticeCheck(id, null);
		if(StringUtil.isNotEmpty(list)){
			List<String> checkIds = CollectionUtils.distinctExtractToList(list, "checkId", String.class);
			if(StringUtil.isNotEmpty(checkIds)){
				entity.setCheck1C(check1CService.getByKeys(checkIds));
				//entity.setPlans(planService.getByKeys(checkIds));
			}
			
		}
		return entity;
	}

	@DictAggregation
	public List<DefectC1Form> findDefectByNotice(String noticeId, String sectionId) {
		List<DefectC1Form> defectList = null;
		if (StringUtil.isNotBlank(noticeId)) {
			List<String> noticeIds = new ArrayList<String>();
			if (StringUtil.isNotBlank(sectionId)) {
				noticeIds.add(noticeId);
			} else {
				List<NoticeSectionDTO> noticeSectionList = noticeSectionService.getByNoticeId(noticeId);
				if (StringUtil.isNotEmpty(noticeSectionList)) {
					noticeIds.addAll(CollectionUtils.distinctExtractToList(noticeSectionList, "id", String.class));
				}
			}
			if (StringUtil.isNotEmpty(noticeIds)) {
				defectList = defect1CService.findC1FormBySectionNotice(noticeIds, sectionId);
			}
		}
		return defectList;

	}
	@DictAggregation
	public List<DefectC3Form> find3CDefectByNotice(String noticeId, String sectionId) {
		List<DefectC3Form> defectList = null;
		if (StringUtil.isNotBlank(noticeId)) {
			List<String> noticeIds = new ArrayList<String>();
			if (StringUtil.isNotBlank(sectionId)) {
				noticeIds.add(noticeId);
			} else {
				List<NoticeSectionDTO> noticeSectionList = noticeSectionService.getByNoticeId(noticeId);
				if (StringUtil.isNotEmpty(noticeSectionList)) {
					noticeIds.addAll(CollectionUtils.distinctExtractToList(noticeSectionList, "id", String.class));
				}
			}
			if (StringUtil.isNotEmpty(noticeIds)) {
				defectList = defect3CService.findC3FormBySectionNotice(noticeIds, sectionId);
			}
		}
		return defectList;
		
	}
	@DictAggregation
	public List<DefectC3Form> findDefectByNoticeId(String noticeId, String sectionId) {
		List<DefectC3Form> defectList = null;
		if (StringUtil.isNotBlank(noticeId)) {
			List<String> noticeIds = new ArrayList<String>();
			if (StringUtil.isNotBlank(sectionId)) {
				noticeIds.add(noticeId);
			} else {
				List<NoticeSectionDTO> noticeSectionList = noticeSectionService.getByNoticeId(noticeId);
				if (StringUtil.isNotEmpty(noticeSectionList)) {
					noticeIds.addAll(CollectionUtils.distinctExtractToList(noticeSectionList, "id", String.class));
				}
			}
			if (StringUtil.isNotEmpty(noticeIds)) {
				List<String> defectIds = noticeDefectService.getDefectIdsByNoticeSectionIds(noticeIds);
				if(StringUtil.isNotEmpty(defectIds)){
					defectList = defect3CService.findC3DefectByIds(defectIds);
				}
			}
		}
		return defectList;
		
	}
	
	@DictAggregation
	public List<DefectC1Response> findDefectResponseByNotice(String noticeId, String sectionId){
		List<DefectC1Form> list = findDefectByNotice(noticeId, sectionId);
		if(StringUtil.isNotEmpty(list)){
			List<String> defectIds = CollectionUtils.distinctExtractToList(list, "id", String.class);
			if(StringUtil.isNotEmpty(defectIds)){
				return defect1CService.getDefectPackageResponseByKeys(defectIds);
			}
		}
		
		return null;
	}
	@DictAggregation
	public List<DefectC3Response> find3CDefectResponseByNotice(String noticeId, String sectionId){
		List<DefectC3Form> list = find3CDefectByNotice(noticeId, sectionId);
		if(StringUtil.isNotEmpty(list)){
			List<String> defectIds = CollectionUtils.distinctExtractToList(list, "id", String.class);
			if(StringUtil.isNotEmpty(defectIds)){
				return defect3CService.getDefectPackageResponseByKeys(defectIds);
			}
		}
		
		return null;
	}

}
