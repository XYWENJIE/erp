package com.benjamin.erp.expand;

import java.io.Serializable;

import org.apache.wicket.Page;


public class SidebarDataItem implements Serializable{
	
	public SidebarDataItem(String name,Class<? extends Page> pagePath) {
		this.name = name;
		this.pagePath = pagePath;
	}
	
	public SidebarDataItem(String name,String contentPath) {
		this.name = name;
		this.contentPath = contentPath;
	}


	private String name;
	
	private Class<? extends Page> pagePath;
	
	private String contentPath;

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

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	
	
	

}
