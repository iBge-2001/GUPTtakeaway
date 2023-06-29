package com.lin.reggie.utils;

//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 短信发送工具类
 */
@Component
public class SMSUtils {


	@Value("${spring.mail.username}")
	private  String MAIL_SENDER ; //邮件发送者

	@Autowired
	private  JavaMailSender javaMailSender;//注入QQ发送邮件的bean

	/**
	 * 发送验证码到邮箱to
	 * @param to
	 * @param code
	 */
	public  void sendSimpleMail(String to, String code) {
		 Logger logger = LoggerFactory.getLogger(SMSUtils.class);
		try {
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom(MAIL_SENDER);//发送者
			mailMessage.setTo(to);//接收者
			mailMessage.setSubject("点餐验证码");//邮件标题
			mailMessage.setText(code);//邮件内容
			javaMailSender.send(mailMessage);//发送邮箱
		} catch (Exception e) {
			logger.error("邮件发送失败", e.getMessage());
		}
	}
}
