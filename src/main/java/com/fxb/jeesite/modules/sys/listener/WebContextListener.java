package com.fxb.jeesite.modules.sys.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.fxb.jeesite.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		StringBuilder runMessage = new StringBuilder();
		runMessage.append("     _   _       __   _   _   _  __    __ __    __  _   _   \n" +
				"    | | | |     |  \\ | | | | | | \\ \\  / / \\ \\  / / | | / /  \n" +
				"    | | | |     |   \\| | | | | |  \\ \\/ /   \\ \\/ /  | |/ /   \n" +
				" _  | | | |     | |\\   | | | | |   }  {     \\  /   | |\\ \\   \n" +
				"| |_| | | |___  | | \\  | | |_| |  / /\\ \\    / /    | | \\ \\  \n" +
				"\\_____/ |_____| |_|  \\_| \\_____/ /_/  \\_\\  /_/     |_|  \\_\\ ");

		System.out.println(runMessage.toString());


		return super.initWebApplicationContext(servletContext);
	}
}
