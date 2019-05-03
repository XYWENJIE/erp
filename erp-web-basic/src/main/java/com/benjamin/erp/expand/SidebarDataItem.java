package com.benjamin.erp.expand;

import java.io.Serializable;

import org.apache.wicket.Page;


public class SidebarDataItem implements Serializable{
	
	public SidebarDataItem(String name,Class<? extends Page> pagePath) {
		this.name = name;
		this.pagePath = pagePath;
	}


	private String name;
	
	private Class<? extends Page> pagePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<? extends Page> getPagePath() {
		return pagePath;
	}

	public void setPagePath(Class<? extends Page> pagePath) {
		this.pagePath = pagePath;
	}

	
	
	

}
