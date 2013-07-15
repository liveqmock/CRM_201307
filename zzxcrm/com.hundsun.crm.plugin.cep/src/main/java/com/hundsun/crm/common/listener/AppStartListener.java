
package com.hundsun.crm.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Web应用加载监听器，最要保存了Spring上下文的引用
 * @author tongsh
 *
 */
public class AppStartListener implements ServletContextListener {

	private static ApplicationContext springContext;
	private static ServletContext _context = null;

	public AppStartListener() {
		super();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		springContext = null;
		_context = null;
	}

	public void contextInitialized(ServletContextEvent event) {
		_context = event.getServletContext();
		springContext = WebApplicationContextUtils
				.getWebApplicationContext(_context);
	}

	public static void setApplicationContext(ApplicationContext context) {
		if (context == null)
			throw new RuntimeException("上下文不能为空！");
		springContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		if (springContext == null)
			springContext = WebApplicationContextUtils
					.getWebApplicationContext(_context);
		return springContext;
	}

	public static ServletContext getServletContext() {
		return _context;
	}

}
