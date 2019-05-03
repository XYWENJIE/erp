package com.benjamin.erp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.benjamin.erp.domain.UserInfo;
import com.benjamin.erp.repository.UserInfoRepository;

public class Realm extends AuthorizingRealm {
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private UserInfoRepository loginInfoRepository;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		logger.info("使用当前用户名：{}",token.getUsername());
		UserInfo loginInfo = this.loginInfoRepository.findByUsername(token.getUsername());
		if(loginInfo != null) {
			logger.info("账户查询成功，下一步校验密码");
			return new SimpleAuthenticationInfo(loginInfo.getUsername(),loginInfo.getPassword(),getName());
		}else {
			logger.warn("登录失败");
			throw new UnsupportedTokenException("当前账户不存在");
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
