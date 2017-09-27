package com.hhly;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
	AmqpTemplate amqpTemplate;
	
	/**
	 * 发送消息到指定队列中去
	 */
	public void sendStringMessage(String msg) {
		amqpTemplate.convertAndSend("queue",msg);
	}
	
	/**
	 * 发送对象到指定队列去
	 */
	public void sendObjectMessage(User user) {
		amqpTemplate.convertAndSend("queue",user);
	}
	
	/**
	 * 发送消息到交换机绑定的队列去
	 */
	public void sendMsgToExchangeQueue(String msg) {
		amqpTemplate.convertAndSend("exchange", "topic.message", msg);
	}
	
	
	/**
	 * 发送消息到fanout交换机绑定的队列去
	 */
	public void sendMsgToFanoutExchangeQueue(String msg) {
		amqpTemplate.convertAndSend("fanoutExchange", "", msg);
	}
	
	
	
	
	

}
