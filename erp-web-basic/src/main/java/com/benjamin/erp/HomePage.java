package com.benjamin.erp;


import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;
import org.wicketstuff.shiro.component.ShiroConfigInfoPanel;

import com.benjamin.erp.expand.BasicWebPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class HomePage extends BasicWebPage {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected void onInitialize() {
		super.extendsBasic = true;
		super.onInitialize();
	}

	@Override
	public Model<String> getTitle() {
		return new Model<>("1111");
	}
	

}
