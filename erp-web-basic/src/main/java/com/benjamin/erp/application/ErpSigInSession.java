package com.benjamin.erp.application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.benjamin.erp.domain.UserInfo;

public class ErpSigInSession extends AuthenticatedWebSession{
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	private String username;

	public ErpSigInSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean authenticate(String username, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try {
			subject.login(token);
			this.username = username;
			return true;
		}catch(CredentialsException e) {
			logger.warn(e.getMessage());
			error("密码错误！");
		}catch(AuthenticationException e) {
			logger.error(e.getMessage(),e);
			error(e.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public Roles getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsername() {
		return username;
	}
	

}
