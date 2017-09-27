package com.hhly;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "user.sms")
public class SendSmsHandler {
	
	@Autowired
	private SendSMS sendSms;
	
	/**
	 * 短信队列处理方法
	 */
	@RabbitHandler
	public void process(@Payload String phone) {
		System.err.println("开始调用第三方短信平台服务");
		//调用短信发送服务接口
		sendSms.sendMessage("18122743751", "使用RabbitMQ消息队列发送短信");
		System.err.println("短信服务发送成功");
	}
	
	
	
}
