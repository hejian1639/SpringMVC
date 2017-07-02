package org.spring.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.spring.service.SpringBeanUtils;
import org.spring.service.HelloWorldService;

/**
 * 程序启动部署时,启动事务引擎
 *
 */
public class ContextStartListener implements ServletContextListener {
    

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		HelloWorldService service = (HelloWorldService) SpringBeanUtils.getBeanByName("helloWorldService");
        System.out.println(service.getHelloMessage()); 
		
        String param = event.getServletContext().getInitParameter("contextConfigLocation");
        System.out.println("param: "+ param); 
	    System.out.println("web.root:"+ System.getProperty("springMVC.root")); 
	}
}
