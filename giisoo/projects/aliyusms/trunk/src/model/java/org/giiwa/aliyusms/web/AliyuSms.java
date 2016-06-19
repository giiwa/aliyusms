package org.giiwa.aliyusms.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.core.bean.X;
import org.giiwa.core.conf.Global;
import org.giiwa.framework.noti.Sms;

import net.sf.json.JSONObject;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliyuSms implements Sms.ISender {

  static final Log           log = LogFactory.getLog(AliyuSms.class);

  /**
   * giiwa 的appkey
   */
  public final static String url = "http://gw.api.taobao.com/router/rest";
  // public static String appkey = "23365917";
  // public static String secret = "6270908d7574497bcbbdd11b23e6bcf3";

  @Override
  public boolean send(String mobile, JSONObject json) {

    try {

      String sign = json.containsKey("sign") ? json.getString("sign") : Global.s("aliyu.sign", X.EMPTY);
      String templateCode = json.containsKey("templatecode") ? json.getString("templatecode") : X.EMPTY;
      if (!X.isEmpty(templateCode)) {
        String s = Global.s("aliyu.templatecode", X.EMPTY);
        String[] ss = s.split(";");
        for (String s1 : ss) {
          String[] s2 = s1.split("=");
          if (s2.length == 2 && X.isSame(templateCode, s2[0].trim())) {
            templateCode = s2[1].trim();
            break;
          }
        }
      }
      String appkey = Global.s("aliyu.appkey", X.EMPTY);
      String secret = Global.s("aliyu.secret", X.EMPTY);
      TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
      AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
      // req.setExtend("quyun");
      // req.setSmsType("normal");
      req.setSmsFreeSignName(sign);
      req.setSmsParamString(json.toString());
      req.setRecNum(mobile);
      req.setSmsTemplateCode(templateCode);

      AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

      return rsp.getResult() != null && rsp.getResult().getSuccess();

    } catch (Exception e) {
      log.error("sendsms error, mobile=" + mobile + ", json=" + json, e);
    }

    return false;
  }

}
