package com.benjamin.erp.expand;

import java.util.ArrayList;
import java.util.List;

public class CommoneUnit {
	
	private static List<SidebarDataList> sidebarDataLists = new ArrayList<SidebarDataList>();

	public static List<SidebarDataList> getSidebarDataLists() {
		return sidebarDataLists;
	}

	public static void setSidebarDataLists(List<SidebarDataList> sidebarDataLists) {
		CommoneUnit.sidebarDataLists = sidebarDataLists;
	}

}
