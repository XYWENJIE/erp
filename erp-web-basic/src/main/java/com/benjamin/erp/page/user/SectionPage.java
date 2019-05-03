package com.benjamin.erp.page.user;

import org.apache.wicket.model.Model;

import com.benjamin.erp.expand.BasicWebPage;

public class SectionPage extends BasicWebPage {

	private static final long serialVersionUID = 1L;

	@Override
	public Model<String> getTitle() {
		return new Model<>("部门管理");
	}
	
	@Override
	protected void onInitialize() {
		SectionPanel sectionPanel = new SectionPanel("panel",getBreadCrumbBar());
		add(sectionPanel);
		getBreadCrumbBar().setActive(sectionPanel);
		super.onInitialize();
	}

}
