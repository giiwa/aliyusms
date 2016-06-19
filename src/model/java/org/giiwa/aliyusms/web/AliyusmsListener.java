/*
 *   giiwa, a java web foramewrok.
 *   Copyright (C) <2014>  <giiwa.org>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.giiwa.aliyusms.web;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.aliyusms.web.admin.aliyusms;
import org.giiwa.app.web.admin.setting;
import org.giiwa.framework.noti.Sms;
import org.giiwa.framework.web.IListener;
import org.giiwa.framework.web.Module;

/**
 * lifelistener, it will be called when the module start
 * 
 * @author joe
 *
 */
public class AliyusmsListener implements IListener {

	static Log log = LogFactory.getLog(AliyusmsListener.class);

	/**
	 * be called when starting
	 */
	public void onStart(final Configuration conf, final Module module) {
		log.warn("aliyusms is starting");

		// setting
		setting.register("aliyusms", aliyusms.class);

		// sms sender
		Sms.register(0, new AliyuSms());
	}

	public void onStop() {
	}

	/**
	 * test and install the database part
	 */
	public void upgrade(Configuration conf, Module module) {
		log.debug("installing");
	}

	public void uninstall(Configuration conf, Module module) {
	}

}
