package com.hhly;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private SendSMS sendSMS;
	
	/**
	 * 普通方式直接调用第三方短信平台服务发送短信(串行方式)
	 */
	@GetMapping("/send")
	public void sendSMS(String phone) {
		sendSMS.sendMessage(phone, "love to you");
	}
	
	/**
	 * 通过消息队列的方式调用第三方短信平台服务发送短信(并行方式,推荐使用)
	 * @param phone
	 * @return
	 */
	@GetMapping("/send-sms")
	public Object sendSms(String phone) {
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("code", 1);
		result.put("msg", "验证码发送成功");
		
		//调用短信服务队列发送验证码
		rabbitTemplate.convertAndSend("user.sms", phone);
		return "验证码发送成功";
	}
	
	
	
}
