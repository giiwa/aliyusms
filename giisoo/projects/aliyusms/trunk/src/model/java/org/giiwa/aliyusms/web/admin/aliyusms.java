package org.giiwa.aliyusms.web.admin;

import org.giiwa.aliyusms.web.AliyuSms;
import org.giiwa.app.web.admin.setting;
import org.giiwa.core.bean.X;
import org.giiwa.core.conf.Global;

/**
 * web api: /admin/setting/[method]/sync
 * <p>
 * used to set syncing configuration
 * 
 * @author joe
 *
 */
public class aliyusms extends setting {

  @Override
  public void reset() {
  }

  @Override
  public void set() {

    Global.setConfig("aliyu.sign", this.getString("aliyu_sign"));
    Global.setConfig("aliyu.appkey", this.getString("aliyu_appkey"));
    Global.setConfig("aliyu.secret", this.getString("aliyu_secret"));
    Global.setConfig("aliyu.templatecode", this.getString("aliyu_templatecode"));

    this.set(X.MESSAGE, "修改成功！");

    get();
  }

  @Override
  public void get() {

    // public final static String url =
    // "http://gw.api.taobao.com/router/rest";
    // public static String appkey = "23365917";
    // public static String secret = "6270908d7574497bcbbdd11b23e6bcf3";

    this.set("aliyu_url", AliyuSms.url);
    this.set("aliyu_sign", Global.s("aliyu.sign", null));
    this.set("aliyu_appkey", Global.s("aliyu.appkey", null));
    this.set("aliyu_secret", Global.s("aliyu.secret", null));
    this.set("aliyu_templatecode", Global.s("aliyu.templatecode", null));

    this.set("page", "/admin/setting.aliyusms.html");
  }

}
