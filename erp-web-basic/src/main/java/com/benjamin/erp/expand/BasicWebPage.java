package com.benjamin.erp.expand;

import org.apache.wicket.extensions.breadcrumb.BreadCrumbBar;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

public abstract class BasicWebPage extends WebPage {
	
	protected String pageTitle = "Insert title here";
	
	protected boolean extendsBasic = true;
	
	private BreadCrumbBar breadCrumbBar = new BreadCrumbBar("breadCrumbBar");

	
	public BasicWebPage() {
		
	}
	
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("/webjars/bootstrap/current/css/bootstrap.css")));
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("/webjars/font-awesome/current/css/font-awesome.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasicWebPage.class, "AdminLTE.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasicWebPage.class, "skins/_all-skins.min.css")));
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("/webjars/jquery/current/jquery.js")));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(BasicWebPage.class, "adminlte.js")));
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("bootstrap/current/js/bootstrap.js")));
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		if(extendsBasic) {
			Model<String> pageTitleModel = getTitle();
			if(pageTitleModel == null){
				pageTitleModel = new Model<>(pageTitle);
			}
			add(new Label("pageTitle",pageTitleModel));
			add(new HeaderPanel("headerPanel"));
			add(new SidebarPanel("sidebarPanel",this.getClass()));
		}
		add(new Label("titleName",getTitle()));
		
		add(breadCrumbBar);
	}
	
	public abstract Model<String> getTitle();

	public BreadCrumbBar getBreadCrumbBar() {
		return breadCrumbBar;
	}
	
	
	
}
