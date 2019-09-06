package com.togest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.togest.common.aspect.RequestLoggingAspect;

/**
 * @author Zhao Junjian
 */
@Configuration
public class AopConfiguration {

	@Bean
	public RequestLoggingAspect hibernateValidatorAspect() {
		final int order = Byte.MAX_VALUE + 2;
		return new RequestLoggingAspect(order);
	}

}
