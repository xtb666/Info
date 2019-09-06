package com.togest.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.togest.common.config.SwaggerApiInfo;
import com.togest.common.config.SwaggerTemplate;
import com.togest.web.StatusCode;


import springfox.documentation.builders.ResponseMessageBuilder;

import springfox.documentation.service.ResponseMessage;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends SwaggerTemplate{

	  @Bean
	    public SwaggerApiInfo info() {
		  SwaggerApiInfo info=new SwaggerApiInfo();
	    	info.setVersion("v1.0");
	    	info.setTitle("/jcw/drawing/");
	    	info.setStatusList(extractStatusCodes());
	        return info;
	    }

	    private List<ResponseMessage> extractStatusCodes() {
	        final LinkedList<ResponseMessage> list = new LinkedList<>();
	        for (StatusCode statusCodes : StatusCode.values()) {
	            final ResponseMessageBuilder builder = new ResponseMessageBuilder();
	            final ResponseMessage message = builder
	                    .code(statusCodes.getErrorCode())
	                    .message(statusCodes.getMessage())
	                    .build();
	            list.add(message);
	        }
	        return list;
	    }

}
