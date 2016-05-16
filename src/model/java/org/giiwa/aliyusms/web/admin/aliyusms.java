package org.giiwa.aliyusms.web.admin;

import org.giiwa.aliyusms.web.AliyuSms;
import org.giiwa.app.web.admin.setting;
import org.giiwa.core.bean.X;
import org.giiwa.core.conf.ConfigGlobal;

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

		ConfigGlobal.setConfig("aliyu.appkey", this.getString("aliyu_appkey"));
		ConfigGlobal.setConfig("aliyu.secret", this.getString("aliyu_secret"));
		ConfigGlobal.setConfig("aliyu.templatecode", this.getString("aliyu_templatecode"));

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
		this.set("aliyu_appkey", ConfigGlobal.s("aliyu.appkey", null));
		this.set("aliyu_secret", ConfigGlobal.s("aliyu.secret", null));
		this.set("aliyu_templatecode", ConfigGlobal.s("aliyu.templatecode", null));

		this.set("page", "/admin/setting.aliyusms.html");
	}

}
