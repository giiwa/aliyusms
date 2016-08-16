package org.giiwa.aliyusms.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.core.bean.X;
import org.giiwa.core.conf.Global;
import org.giiwa.core.json.JSON;
import org.giiwa.framework.bean.OpLog;
import org.giiwa.framework.noti.Sms;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliyuSms implements Sms.ISender {

  static final Log           log         = LogFactory.getLog(AliyuSms.class);

  static Map<String, String> codes       = new HashMap<String, String>();

  static long                lastupdated = 0;

  public static void reset() {
    Map<String, String> m1 = new HashMap<String, String>();

    String s = Global.getString("aliyu.templatecode", X.EMPTY);
    String[] ss = s.split(";");
    for (String s1 : ss) {
      String[] s2 = s1.split("=");
      if (s2.length > 1) {
        m1.put(s2[0].trim(), s2[1].trim());
      }
    }
    codes = m1;
    lastupdated = System.currentTimeMillis();
  }

  /**
   * giiwa appkey
   */
  public final static String url = "http://gw.api.taobao.com/router/rest";
  // public static String appkey = "23365917";
  // public static String secret = "6270908d7574497bcbbdd11b23e6bcf3";

  @Override
  public boolean send(String mobile, JSON json) {

    try {

      String sign = json.containsKey("sign") ? json.getString("sign") : Global.getString("aliyu.sign", X.EMPTY);
      String templateCode = json.containsKey("templatecode") ? json.getString("templatecode") : X.EMPTY;
      if (codes.size() == 0 || System.currentTimeMillis() - lastupdated > X.AMINUTE) {
        reset();
      }

      if (!X.isEmpty(templateCode)) {
        if (!codes.containsKey(templateCode)) {
          OpLog.error("sms", "[" + templateCode + "] missed", null);
        }

        templateCode = codes.get(templateCode);
      }
      String appkey = Global.getString("aliyu.appkey", X.EMPTY);
      String secret = Global.getString("aliyu.secret", X.EMPTY);
      TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
      AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

      req.setSmsType("normal");
      req.setSmsFreeSignName(sign);
      req.setSmsParamString(json.toString());
      req.setRecNum(mobile);
      req.setSmsTemplateCode(templateCode);

      AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

      if (log.isDebugEnabled())
        log.debug("aliyu sendsms to [" + mobile + "], result=" + rsp.getResult() + ", content=" + json);
      return rsp.getResult() != null && rsp.getResult().getSuccess();

    } catch (Exception e) {
      log.error("sendsms error, mobile=" + mobile + ", json=" + json, e);
    }

    return false;
  }

}
