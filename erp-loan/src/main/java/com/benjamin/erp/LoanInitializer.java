package com.benjamin.erp;

import com.benjamin.erp.activiti.AuditLoanTask;
import com.benjamin.erp.application.ErpWebApplication;
import com.benjamin.erp.expand.CommoneUnit;
import com.benjamin.erp.expand.SidebarDataItem;
import com.benjamin.erp.expand.SidebarDataList;
import com.benjamin.erp.page.AuditLoanPage;
import com.benjamin.erp.page.BorrowIndexPage;
import com.benjamin.erp.page.ReleaseBorrowerPage;
import com.benjamin.erp.page.loan.capital.RechargeListPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.context.ApplicationContext;

public class LoanInitializer implements IInitializer {

    private Logger logger = LogManager.getLogger();
    
    @SpringBean
    private RepositoryService repositoryService;

    @Override
    public void init(Application application) {
        logger.info("初始化Loan模块.....");
        ErpWebApplication erpWebApplication = (ErpWebApplication)application;
        ApplicationContext applicationContext = erpWebApplication.getApplicationContext();
        this.repositoryService = applicationContext.getBean("repositoryService",RepositoryService.class);
        List<String> list = this.repositoryService.getDeploymentResourceNames("flow/auditLoan.bpmn");
        logger.debug("查询到的List数量:{}",list.size());
        if(list.isEmpty()) {
        	this.repositoryService.createDeployment().addClasspathResource("flow/auditLoan.bpmn").deploy();	
        }
        
        AuditLoanTask auditLoanTask = applicationContext.getBean("auditLoanTask",AuditLoanTask.class);
        SpringProcessEngineConfiguration configuration = applicationContext.getBean("processEngineConfiguration",SpringProcessEngineConfiguration.class);
//        Map<Object, Object> beans = new HashMap<>();
//        beans.put("auditLoanTask", auditLoanTask);
//        configuration.setBeans(beans);
        SidebarDataList sidebarDataList = new SidebarDataList("","借款管理");
        SidebarDataItem sidebarDataItem = new SidebarDataItem("发布借款", ReleaseBorrowerPage.class);
        sidebarDataList.getSidebarDataItems().add(sidebarDataItem);
        
        sidebarDataItem = new SidebarDataItem("审核借款", AuditLoanPage.class);
        sidebarDataList.getSidebarDataItems().add(sidebarDataItem);
        
        sidebarDataItem = new SidebarDataItem("借款标列表", BorrowIndexPage.class);
        sidebarDataList.getSidebarDataItems().add(sidebarDataItem);
        
        CommoneUnit.getSidebarDataLists().add(sidebarDataList);
        
        sidebarDataList = new SidebarDataList("", "借款资产管理");
        sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("充值列表", RechargeListPage.class));
    }

    @Override
    public void destroy(Application application) {

    }
}
