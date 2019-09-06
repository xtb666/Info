package com.togest.dao;

import java.util.List;

import com.togest.domain.Defect5CDTO;
import com.togest.response.DefectC5Form;
import com.togest.response.DefectC5Response;

public interface Defect5CDao extends SixCDao<Defect5CDTO, DefectC5Response, DefectC5Form> {

	public List<Defect5CDTO> getListByPointId(String pointId);

}
