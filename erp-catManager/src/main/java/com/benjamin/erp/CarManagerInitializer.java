package com.benjamin.erp;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

import com.benjamin.erp.expand.CommoneUnit;
import com.benjamin.erp.expand.SidebarDataItem;
import com.benjamin.erp.expand.SidebarDataList;
import com.benjamin.erp.page.DriverLicensePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CarManagerInitializer implements IInitializer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void init(Application application) {
		logger.info("初始化CarManager模块.....");
		List<SidebarDataList> sidebarDataLists = CommoneUnit.getSidebarDataLists();
		SidebarDataList sidebarDataList = new SidebarDataList("fa fa-dashboard","汽车证件管理");
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("驾驶证管理",DriverLicensePage.class));
		sidebarDataLists.add(sidebarDataList);
	}

	@Override
	public void destroy(Application application) {
	}

}
