package com.benjamin.erp.page;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.Model;

import com.benjamin.erp.expand.BasicWebPage;
import com.benjamin.erp.page.car.license.DriverLicenseIndexPanel;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

public class DriverLicensePage extends BasicWebPage {

	@Override
	public Model<String> getTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("bootstrap-fileinput/current/js/fileinput.min.js")));
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("bootstrap-fileinput/current/css/fileinput.min.css")));
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		DriverLicenseIndexPanel driverLicenseIndex = new DriverLicenseIndexPanel("panel", getBreadCrumbBar());
		add(driverLicenseIndex);
		getBreadCrumbBar().setActive(driverLicenseIndex);
		
	}

}
