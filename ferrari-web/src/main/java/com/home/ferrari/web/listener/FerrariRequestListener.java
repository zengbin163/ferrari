package com.home.ferrari.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import com.home.ferrari.plugin.session.SessionContainer;

public class FerrariRequestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		SessionContainer.setSession(null);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		SessionContainer.setSession(null);
	}

}
