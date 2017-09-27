package com.hhly;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receive {

	@RabbitListener(queues = "queue")
	public void receiveMessage(String msg) {
		System.err.println("接受到消息:" + msg);
	}
	
	@RabbitHandler
	public void process(Map map) {
		System.err.println("@RabbitHandler注解下的方法接受到消息 : " + map);
	}
	
	

	@RabbitListener(queues = "queue")
	public void receiveObject(User user) {
		System.err.println("接受到对象消息:" + user);
	}

	@RabbitListener(queues = "topic.message")
	public void queueMessage(String msg) {
		System.err.println("topic.message队列接受到消息:" + msg);
	}

	@RabbitListener(queues = "topic.messages")
	public void queueMessage2(String msg) {
		System.err.println("topic.messages队列接受到消息:" + msg);
	}

	@RabbitListener(queues = "fanout.A")
	public void fanoutAMessage(String msg) {
		System.err.println("fanout.A队列接受到消息:" + msg);
	}

	@RabbitListener(queues = "fanout.B")
	public void fanoutBMessage(String msg) {
		System.err.println("fanout.B队列接受到消息:" + msg);
	}

	@RabbitListener(queues = "fanout.C")
	public void fanoutCMessage(String msg) {
		System.err.println("fanout.C队列接受到消息:" + msg);
	}

}
