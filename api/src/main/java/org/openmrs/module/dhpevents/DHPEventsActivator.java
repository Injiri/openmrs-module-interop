/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dhpevents;

import lombok.extern.slf4j.Slf4j;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.DaemonToken;
import org.openmrs.module.DaemonTokenAware;
import org.openmrs.module.dhpevents.api.DHPEventManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DHPEventsActivator extends BaseModuleActivator implements ApplicationContextAware, DaemonTokenAware {
	
	@Autowired
	private DHPEventManager eventManager;
	
	private static ApplicationContext applicationContext;
	
	private static DaemonToken daemonToken;
	
	/**
	 * @see #started()
	 */
	public void started() {
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
		//Events DHP events
		this.eventManager.setDaemonToken(daemonToken);
		this.eventManager.enableEvents();
		log.info("Started DHP Events");
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		//Disable all DHP Events
		this.eventManager.setDaemonToken(daemonToken);
		this.eventManager.disableEvents();
		log.info("Shutdown DHP Events");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DHPEventsActivator.applicationContext = applicationContext;
	}
	
	@Override
	public void setDaemonToken(DaemonToken token) {
		DHPEventsActivator.daemonToken = token;
	}
}
