package com.benjamin.erp.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.webjars.RequireJS;

import com.benjamin.erp.expand.CommoneUnit;
import com.benjamin.erp.expand.SidebarDataItem;
import com.benjamin.erp.expand.SidebarDataList;
import com.benjamin.erp.page.RolePage;
import com.benjamin.erp.page.UserInfoPage;
import com.benjamin.erp.page.task.TaskTablePage;
import com.benjamin.erp.page.user.SectionPage;

@Controller
@RequestMapping("/")
public class IndexControl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostConstruct
	public void initBinder() {
		logger.info("初始化Basic模块");
		List<SidebarDataList> sidebarDataLists = CommoneUnit.getSidebarDataLists();
		SidebarDataList sidebarDataList = new SidebarDataList("fa fa-dashboard","系统管理",20);
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("用户列表","/user/index"));
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("角色管理", RolePage.class));
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("部门管理", SectionPage.class));
		sidebarDataLists.add(sidebarDataList);
		
		sidebarDataList = new SidebarDataList("fa fa-dashboard","任务管理",30);
		sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("任务列表",TaskTablePage.class));
		sidebarDataLists.add(sidebarDataList);
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@ResponseBody
	@RequestMapping(value="/webjarsjs",produces = "application/javascript")
	public String webjars() {
		return RequireJS.getSetupJavaScript("/webjars");
		
	}

}
