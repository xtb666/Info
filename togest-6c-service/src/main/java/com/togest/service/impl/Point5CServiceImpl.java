package com.togest.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.togest.common.annotation.Shift;
import com.togest.common.util.string.StringUtil;
import com.togest.dao.Point5CDao;
import com.togest.dict.annotation.DictAggregation;
import com.togest.domain.Page;
import com.togest.domain.Point5CDTO;
import com.togest.service.Point5CService;
import com.togest.utils.PageUtils;

@Service
public class Point5CServiceImpl implements Point5CService {

	@Autowired
	private Point5CDao dao;

	@Override
	public int save(Point5CDTO entity) {
		if (entity != null
				&& hasPoint(entity.getLineId(), entity.getStationId(),
						entity.getInstallPillarId(), entity.getId())) {
			Shift.fatal(StatusCode.POINT_EXISTENT);
		}
		if (entity.getIsNewRecord()) {
			entity.preInsert();
			return dao.insert(entity);
		} else {
			return dao.update(entity);
		}
	}

	@Override
	@DictAggregation
	public Point5CDTO get(String id) {

		return dao.getByKey(id);
	}

	@Override
	@DictAggregation
	public Point5CDTO get(Point5CDTO entity) {

		return dao.getByEntity(entity);
	}

	@Override
	@DictAggregation
	public List<Point5CDTO> findList(Point5CDTO entity) {

		return dao.findList(entity);
	}

	@Override
	@DictAggregation
	public Page<Point5CDTO> findPage(Page page, Point5CDTO entity) {

		PageUtils.setPage(page);
		return PageUtils.buildPage(dao.findList(entity));
	}

	@Override
	public int delete(String id) {
		return dao.delete(id);
	}

	public int deletes(String id) {
		if (StringUtil.isNotBlank(id)) {
			return dao.deletes(Arrays.asList(id.split(",")));
		}
		return 0;

	}

	public boolean hasPoint(String lineId, String stationId,
			String installPillarId, String id) {
		Point5CDTO dto = new Point5CDTO();
		dto.setLineId(lineId);
		dto.setStationId(stationId);
		dto.setInstallPillarId(installPillarId);
		Point5CDTO entity = dao.getByEntity(dto);
		if (entity == null || entity.getId().equals(id)) {
			return false;
		}
		return true;
	}

}
