package com.hhly;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列发送方配置-->添加Queue队列对象
 * 
 * @author hebe
 *
 */
@Configuration
public class SenderConfig {

	/**
	 * 配置Queue(消息队列).那注意由于采用的是Direct模式,需要在配置Queue的时候,指定一个键,使其和交换机绑定.
	 * 
	 * @return
	 */
	@Bean
	public Queue queue() {
		return new Queue("queue");
	}

	/**
	 * topic转发模式队列:message
	 */
	@Bean(name = "message")
	public Queue queueMessage() {
		return new Queue("topic.message");
	}

	/**
	 * topic转发模式队列:messages
	 * 
	 * @return
	 */
	@Bean(name = "messages")
	public Queue queueMessages() {
		return new Queue("topic.messages");
	}

	/**
	 * 创建一个交换机对象
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("exchange");
	}

	/**
	 * 交换机绑定消息队列message
	 */
	@Bean
	public Binding exchangeBindMessageQueue(TopicExchange topicExchange, @Qualifier("message") Queue queueMessage) {
		Binding binding = BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
		return binding;
	}

	/**
	 * 交换机绑定消息队列messages
	 */
	@Bean
	public Binding exchangeBindMessagesQueue(TopicExchange topicExchange, @Qualifier("messages") Queue queueMessages) {
		// Binding binding =
		// BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");//*表示一个词,#表示零个或多个词
		Binding binding = BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.messages");// *表示一个词,#表示零个或多个词
		return binding;
	}

	/**
	 * 创建fanout.A队列
	 */
	@Bean(name = "MessageA")
	public Queue fanoutAQueue() {
		return new Queue("fanout.A");
	}

	/**
	 * 创建fanout.A队列
	 */
	@Bean(name = "MessageB")
	public Queue fanoutBQueue() {
		return new Queue("fanout.B");
	}

	/**
	 * 创建fanout.A队列
	 */
	@Bean(name = "MessageC")
	public Queue fanoutCQueue() {
		return new Queue("fanout.C");
	}

	/**
	 * 创建交换机(路由器)
	 */
	@Bean(name = "fanoutExchange")
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");
	}

	/***
	 * 绑定队列A,B,C到交换机中去
	 */
	@Bean
	public Binding bindQueueA(@Qualifier("MessageA") Queue queueA, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queueA).to(fanoutExchange);
	}

	@Bean
	public Binding bindQueueB(@Qualifier("MessageB") Queue queueB, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queueB).to(fanoutExchange);
	}

	@Bean
	public Binding bindQueueC(@Qualifier("MessageC") Queue queueC, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queueC).to(fanoutExchange);
	}

}
