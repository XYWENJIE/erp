package com.benjamin.erp;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

import com.benjamin.erp.expand.CommoneUnit;
import com.benjamin.erp.expand.SidebarDataItem;
import com.benjamin.erp.expand.SidebarDataList;
import com.benjamin.erp.page.RolePage;
import com.benjamin.erp.page.UserInfoPage;
import com.benjamin.erp.page.task.TaskTablePage;
import com.benjamin.erp.page.user.SectionPage;

public class BasicInitializer implements IInitializer {
	
	private Logger logger = LogManager.getLogger();

	@Override
	public void init(Application application) {
		logger.info("初始化Basic模块.....");
		List<SidebarDataList> sidebarDataLists = CommoneUnit.getSidebarDataLists();
		SidebarDataList sidebarDataList = new SidebarDataList("fa fa-dashboard","系统管理",20);
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("用户列表",UserInfoPage.class));
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("角色管理", RolePage.class));
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("部门管理", SectionPage.class));
		sidebarDataLists.add(sidebarDataList);
		
		sidebarDataList = new SidebarDataList("fa fa-dashboard","任务管理",30);
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("任务列表",TaskTablePage.class));
		sidebarDataLists.add(sidebarDataList);
	}

	@Override
	public void destroy(Application application) {
		// TODO Auto-generated method stub

	}

}
