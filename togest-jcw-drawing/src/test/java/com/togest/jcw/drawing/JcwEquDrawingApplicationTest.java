package com.togest.jcw.drawing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.togest.JcwEquDrawingApplication;
import com.togest.domain.DataDrawingDTO;
import com.togest.service.DataDrawingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={JcwEquDrawingApplication.class,JcwEquDrawingApplicationTest.class})
public class JcwEquDrawingApplicationTest {
	
	@Autowired
	private DataDrawingService dataDrawingService;
	
	@Test
	public void test() throws JsonProcessingException{
		DataDrawingDTO entity = new DataDrawingDTO();
		entity.setAssortmentId("f5451021aa0f4d95a699eda37c9886c0");
		entity.setCode("334323");
		entity.setDeptId("06b9a4db5e5c4ef2ad49baf329567da6");
		entity.setFileId("");
		entity.setName("图纸名称");
		entity.setPavilionId("");
		entity.setLineId("10013");
		dataDrawingService.save(entity );
	}
}
