package com.benjamin.erp.application;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

import com.benjamin.erp.HomePage;
import com.benjamin.erp.page.LoginPage;
import com.benjamin.erp.page.UnauthorizedPage;

import de.agilecoders.wicket.webjars.WicketWebjars;

public class ErpWebApplication extends WebApplication implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public Class<? extends Page> getHomePage() {
		// TODO Auto-generated method stub
		return HomePage.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new ErpSigInSession(request);
	}

	@Override
	protected void init() {
		WicketWebjars.install(this);
		getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), "WEB-INF/"));

		// Wicket注解启动
		new AnnotatedMountScanner().scanPackage("com.benjamin.erp.page").mount(this);
		// APache Shrio 配置
		AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
		getSecuritySettings().setAuthorizationStrategy(authz);
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				new ShiroUnauthorizedComponentListener(LoginPage.class, UnauthorizedPage.class, authz));

		// Spring 配置
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext, true));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
