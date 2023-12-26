package com.sample.reporting;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableRetry
public class AppAsyncConfig {

	@Bean("taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(4);
		threadPoolTaskExecutor.setMaxPoolSize(10);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}
	
	@Bean 
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


}
