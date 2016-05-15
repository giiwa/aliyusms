package org.giiwa.aliyusms.web;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.giiwa.framework.bean.OpLog;

import net.sf.json.JSONObject;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliyuSms {

	public static String url = "http://gw.api.taobao.com/router/rest";
	public static String appkey = "23332773";
	public static String secret = "768520953976bac7fc1be026dc08bd9e";

	/*
	 * 阿里大鱼短信接口
	 */
	public static JSONObject sendsms(String sign, String mobile, String templateCode, JSONObject json) {
		JSONObject logjson = new JSONObject();
		logjson.put("channel", "ALI");
		try {

			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend("quyun");
			req.setSmsType("normal");
			req.setSmsFreeSignName(sign);
			req.setSmsParamString(json.toString());
			req.setRecNum(mobile);
			req.setSmsTemplateCode(templateCode);
			AlibabaAliqinFcSmsNumSendResponse rsp;
			rsp = client.execute(req);
			LogFactory.getLog(AliyuSms.class).info("-------AliyuSms-------sendsms=" + rsp.getBody());
			if (null != rsp.getResult() && rsp.getResult().getSuccess() && rsp.getResult().getSuccess()) {
				logjson.put("status", 0);
			} else {
				logjson.put("status", 1);
				logjson.put("error", rsp.getSubCode());
			}

		} catch (ApiException e) {
			OpLog.error("sms", "AliyuSms exception", e.getMessage());
			logjson.put("status", 1);
			logjson.put("error", e);
		} catch (Exception e) {
			OpLog.error("sms", "AliyuSms exception", e.getMessage());
			logjson.put("status", 1);
			logjson.put("error", e);
		}

		String content = getMessage(templateCode, json);
		logjson.put("mobile", mobile);
		logjson.put("message", content);

		return logjson;

	}

	/*
	 * 阿里大鱼短信模板转换短信内容
	 */
	public static String getMessage(String templateCode, JSONObject json) {
		String templateContent = templateCode;
		try {
			Map<String, String> map = SMSTemplate.getMessageTemplate();
			templateContent = map.get(templateCode);
			for (Iterator iter = json.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String value = json.getString(key);
				templateContent = templateContent.replace("${" + key + "}", value);
			}

		} catch (Exception e) {
			LogFactory.getLog(AliyuSms.class).info("-------getMessage-------error");
		}
		return templateContent;
	}

	public static void main(String[] args) {
		String sign = "趣孕";
		String mobile = "18810475972";
		String templateCode = "SMS_6746036";
		JSONObject json = new JSONObject();
		json.put("name", "药给力");
		json.put("code", "eqweqw12");
		JSONObject jsons = sendsms(sign, mobile, templateCode, json);
		System.out.println("---jsons---" + jsons.toString());

	}

}
