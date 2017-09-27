package com.hhly;

import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 短信发送平台工具类
 * 
 * @author Hebe
 * @createtime 2016年12月9日
 */
@Component
@ConfigurationProperties(prefix = "sms")
@Data
public class SendSMS {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendSMS.class);
	
	private String url;
	private String username;
	private String password;
	
	/**
	 * 注册短信发送服务队列
	 * @return
	 */
	@Bean(name = "SMSMessage")
	public Queue smsQueue() {
		System.err.println("注册短信发送服务队列");
		return new Queue("user.sms");
	}
	
	/**
	 * 发送短信
	 * @param username	用户名,由机构ID+:+用户登录名组成.如10001:admin
	 * @param password	用户登录密码
	 * @param from			发送手机号码	
	 * @param phone接收手机号码,支持多个.号码间用,号分开.最大一次不能超过100个号码
	 * @param content			短信内容
	 * @param presendTime	 短信发送时间,可为空.默认为系统当前时间
	 * @param isVoice			是否语音短信是否语音(0表示普通短信,1表示语音短信)|重听次数|重拨次数|是否回复(0表示不回复,1表示回复)如:isVoice=”1|1|2|0” 即:语音短信,重听次数1,重拨次数2,不回复
	 * @return
	 * @throws Exception
	 */
	public String sendMessage(String phone, String content) {
		String from = "";
		String presendTime = "";
		String result = "";
		String isVoice = "0";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url, "clusterSend"));

			result = (String) call.invoke(new Object[] { username, password, from, phone, content, presendTime, isVoice });
			
			LOGGER.warn("短信发送成功(SUCCESS)------->手机号:[" + phone + "],内容:[" + content + "]");
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.warn("短信发送失败(FAIL)------->手机号:[" + phone + "],内容:[" + content + "]");
		}
		return result;
	}
}