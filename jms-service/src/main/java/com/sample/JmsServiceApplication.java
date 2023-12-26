package com.sample;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@SpringBootApplication
@EnableJms
public class JmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsServiceApplication.class, args);
	}
	
	
	@Bean
    public JmsListenerContainerFactory firstQueueFactory(ConnectionFactory connectionFactory,
        DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConcurrency("1");
        configurer.configure(factory, connectionFactory);
        return factory;
    }
	
	@Bean
    public JmsListenerContainerFactory secondQueueFactory(ConnectionFactory connectionFactory,
        DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConcurrency("1");
        configurer.configure(factory, connectionFactory);
        return factory;
    }


}
