package com.benjamin.erp.page;

import org.apache.wicket.model.Model;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.benjamin.erp.expand.BasicWebPage;
import com.benjamin.erp.page.user.IndexDataPanel;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class UserInfoPage extends BasicWebPage {

	@Override
	public Model<String> getTitle() {
		return new Model<>("用户列表");
	}

	@Override
	protected void onInitialize() {
		IndexDataPanel indexDataPanel = new IndexDataPanel("panel", getBreadCrumbBar());
		getBreadCrumbBar().setActive(indexDataPanel);
		add(indexDataPanel);
		super.onInitialize();
		
		
	}

}
