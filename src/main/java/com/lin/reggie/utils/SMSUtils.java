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

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
//	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
//		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
//		IAcsClient client = new DefaultAcsClient(profile);
//
//		SendSmsRequest request = new SendSmsRequest();
//		request.setSysRegionId("cn-hangzhou");
//		request.setPhoneNumbers(phoneNumbers);
//		request.setSignName(signName);
//		request.setTemplateCode(templateCode);
//		request.setTemplateParam("{\"code\":\""+param+"\"}");
//		try {
//			SendSmsResponse response = client.getAcsResponse(request);
//			System.out.println("短信发送成功");
//		}catch (ClientException e) {
//			e.printStackTrace();
//		}
//	}
//	public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
//		com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
//				// 必填，您的 AccessKey ID
//				.setAccessKeyId(accessKeyId)
//				// 必填，您的 AccessKey Secret
//				.setAccessKeySecret(accessKeySecret);
//		// 访问的域名
//		config.endpoint = "dysmsapi.aliyuncs.com";
//		return new com.aliyun.dysmsapi20170525.Client(config);
//	}
//
//	public static void main(String[] args_) throws Exception {
//		java.util.List<String> args = java.util.Arrays.asList(args_);
//		// 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
//		// 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
//		com.aliyun.dysmsapi20170525.Client client = Sample.createClient(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
//		com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
//				.setSignName("阿里云短信测试")
//				.setTemplateCode("SMS_154950909")
//				.setPhoneNumbers("13556450492")
//				.setTemplateParam("{\"code\":\"1234\"}");
//		com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
//		try {
//			// 复制代码运行请自行打印 API 的返回值
//			client.sendSmsWithOptions(sendSmsRequest, runtime);
//		} catch (TeaException error) {
//			// 如有需要，请打印 error
//			com.aliyun.teautil.Common.assertAsString(error.message);
//		} catch (Exception _error) {
//			TeaException error = new TeaException(_error.getMessage(), _error);
//			// 如有需要，请打印 error
//			com.aliyun.teautil.Common.assertAsString(error.message);
//		}
//	}
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
