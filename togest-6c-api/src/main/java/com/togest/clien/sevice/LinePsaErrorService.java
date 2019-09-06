package com.togest.clien.sevice;

import org.springframework.stereotype.Component;

import com.togest.client.response.LinePsaDTO;
import com.togest.model.resposne.RestfulResponse;

@Component
public class LinePsaErrorService implements LinePsaFeignService {

	@Override
	public RestfulResponse<LinePsaDTO> getLine(String plId, String psaId,String direction) {
		// TODO Auto-generated method stub
		return null;
	}
}
