package com.benjamin.erp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyApplicationConfiguration extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected Class<?>[] getRootConfigClasses() {
		logger.info("实例化RootConfigClasses");
		return new Class<?>[] {SpringApplicationContext.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		logger.info("实例化ServletConfigClasses");
		return new Class<?>[] {SpringServletContext.class};
	}

	@Override
	protected String[] getServletMappings() {
		logger.info("实例化ServletMappings");
		return new String[] {"/"};
	}

}
