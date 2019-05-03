package com.benjamin.erp.expand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SidebarDataList implements Comparable<SidebarDataList>,Serializable{
	
	private static final long serialVersionUID = 1L;

	private String icon;
	
	private String name;

	private Integer priority;

	private List<SidebarDataItem> sidebarDataItems = new ArrayList<SidebarDataItem>();
	
	public SidebarDataList(String icon,String name,Integer priority) {
		this.icon = icon;
		this.name = name;
		this.priority = priority;
	}

	public SidebarDataList(String icon,String name){
		this(icon,name,50);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SidebarDataItem> getSidebarDataItems() {
		return sidebarDataItems;
	}

	public void setSidebarDataItems(List<SidebarDataItem> sidebarDataItems) {
		this.sidebarDataItems = sidebarDataItems;
	}

	public Integer getPriority(){
		return priority;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	@Override
	public int compareTo(SidebarDataList sidebarDataList) {
		return this.priority - sidebarDataList.getPriority();
	}
	

}
