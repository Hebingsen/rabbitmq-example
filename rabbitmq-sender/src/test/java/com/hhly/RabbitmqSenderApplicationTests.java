package com.hhly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSenderApplicationTests {

	@Autowired
	Sender sender;

	@Test
	public void testSend() {
		sender.sendStringMessage(" Hello World ");
		System.err.println("普通消息发送完毕");

		sender.sendObjectMessage(new User().setId(1L).setName("hebs").setSex("man"));
		System.err.println("对象消息发送完毕");

	}

	/**
	 * 第一个参数是交换机名称,第二个参数是发送的key,第三个参数是内容,
	 * RabbitMQ将会根据第二个参数去寻找有没有匹配此规则的队列,如果有,则把消息给它,
	 * 如果有不止一个,则把消息分发给匹配的队列(每个队列都有消息!),显然在我们的测试中,
	 * 参数2匹配了两个队列,因此消息将会被发放到这两个队列中,而监听这两个队列的监听器都将收到消息!
	 * 那么如果把参数2改为topic.messages呢?显然只会匹配到一个队列,那么process2方法对应的监听器收到消息!
	 */
	@Test
	public void testSendByExchange() {
		sender.sendMsgToExchangeQueue("Hello World Rabbit Topic Exchange");
	}
	
	/**
	 * Fanout Exchange形式又叫广播形式,因此我们发送到路由器的消息会使得绑定到该路由器的每一个Queue接收到消息,
	 * 这个时候就算指定了Key,或者规则(即上文中convertAndSend方法的参数2),
	 * 也会被忽略!那么直接上代码,发送端配置如下:
	 */
	@Test
	public void testSendByFanoutExchange() {
		sender.sendMsgToFanoutExchangeQueue("Hello World Rabbit Fanout Exchange");
	}
	
	
	

}
