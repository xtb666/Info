package com.togest.service;

import java.util.List;

import com.togest.domain.CInfo;
import com.togest.domain.Page;
import com.togest.request.InfoQueryFilter;
import com.togest.response.statistics.InfoCAndSize;

public interface CInfoService extends BaseInfoService<CInfo> {

	List<Object> findCAndSize(InfoQueryFilter entity);

	Page<CInfo> findPages(Page page, InfoQueryFilter entity);

	List<Object> findYAndMon(InfoQueryFilter entity);

}
