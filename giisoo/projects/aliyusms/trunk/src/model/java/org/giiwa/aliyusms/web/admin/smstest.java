package org.giiwa.aliyusms.web.admin;

import org.giiwa.core.bean.X;
import org.giiwa.framework.noti.Sms;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

import net.sf.json.JSONObject;

public class smstest extends Model {

  @Path(login = true)
  public void index() {
    String mobile = this.getString("mobile");
    String sign = this.getString("sign");
    String code = this.getString("code");
    String content = this.getHtml("content");
    this.set("mobile", mobile);
    this.set("sign", sign);
    this.set("code", code);
    this.set("content", content);

    if (method.isPost()) {
      JSONObject jo = new JSONObject();
      jo.putAll(JSONObject.fromObject(content));
      if (!X.isEmpty(sign)) {
        jo.put("sign", sign);
      }
      if (!X.isEmpty(code)) {
        log.debug("code=" + code + ",jo=" + jo);
        jo.put("templatecode", code);
      }
      if (Sms.send(mobile, jo)) {
        this.set(X.MESSAGE, lang.get("send.success"));
      } else {
        this.set(X.MESSAGE, lang.get("send.failed"));
      }
    }

    this.show("/admin/sms.test.html");

  }

}
