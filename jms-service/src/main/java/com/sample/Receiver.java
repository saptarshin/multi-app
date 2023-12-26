package com.sample;

import java.nio.charset.StandardCharsets;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

	@JmsListener(destination = "${regulatory.client.queue.first}", containerFactory = "firstQueueFactory")
	public void receiveMessageFromFirstQueue(Message message) throws JMSException {
		try {			
			ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
			byte[] byteArray = textMessage.getContent().getData();
			String messageText = new String(byteArray, StandardCharsets.UTF_8);
			log.info("Received message with JMSMessageID: {}. Message is = {}", message.getJMSMessageID(), messageText);
		}
		catch (Exception ex) {
			log.info("Unable to process message with JMSMessageID = {}", message.getJMSMessageID());
			log.error("Error occurred while processing is ", ex);
		}
	}
	
	
	@JmsListener(destination = "${regulatory.client.queue.second}", containerFactory = "secondQueueFactory")
	public void receiveMessageFromSecondQueue(Message message) throws JMSException {
		try {			
			ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
			byte[] byteArray = textMessage.getContent().getData();
			String messageText = new String(byteArray, StandardCharsets.UTF_8);
			log.info("Received message with JMSMessageID: {}. Message is = {}", message.getJMSMessageID(), messageText);
		}
		catch (Exception ex) {
			log.info("Unable to process message with JMSMessageID = {}", message.getJMSMessageID());
			log.error("Error occurred while processing is ", ex);
		}
	}

}
