package org.giiwa.aliyusms.web.admin;

import org.giiwa.app.web.admin.setting;
import org.giiwa.core.bean.X;
import org.giiwa.core.conf.ConfigGlobal;
import org.giiwa.framework.sync.SyncTask;

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

        this.set(X.MESSAGE, "修改成功！");

        get();
    }

    @Override
    public void get() {

        this.set("page", "/admin/setting.aliyusms.html");
    }

}
