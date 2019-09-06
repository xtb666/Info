package com.togest.clien.sevice;

import java.util.List;

import org.springframework.stereotype.Component;

import com.togest.client.response.Pillar;
import com.togest.domain.PillarInfoDTO;
import com.togest.model.resposne.RestfulResponse;
import com.togest.response.PillarInfoSelectorV2;

@Component
public class PillarFeginErrorServiceImpl implements PillarFeginService {

	@Override
	public RestfulResponse<Pillar> getMin(String lineId, String direction, Double glb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<Pillar> getMax(String lineId, String direction, Double glb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<Pillar> getPillarForDefect(String deptId, String lineId, String direction, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<Pillar> getPillar(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<Pillar>> findPillarByGlb(List<Pillar> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<Pillar>> findPillarByName(List<Pillar> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestfulResponse<List<PillarInfoSelectorV2>> pillarSelector2(String deptId, String plId, String psaId,
			String direction, String tunnelId, String track) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
