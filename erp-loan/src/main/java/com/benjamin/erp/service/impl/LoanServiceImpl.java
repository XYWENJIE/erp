package com.benjamin.erp.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.benjamin.erp.activiti.AuditLoanTask;
import com.benjamin.erp.domain.BorrowerInfo;
import com.benjamin.erp.domain.BorrowerTender;
import com.benjamin.erp.domain.LoanCapital;
import com.benjamin.erp.domain.BorrowerInfo.Status;
import com.benjamin.erp.expand.CommoneUnit;
import com.benjamin.erp.expand.SidebarDataItem;
import com.benjamin.erp.expand.SidebarDataList;
import com.benjamin.erp.page.AuditLoanPage;
import com.benjamin.erp.page.BorrowIndexPage;
import com.benjamin.erp.page.ReleaseBorrowerPage;
import com.benjamin.erp.page.loan.capital.RechargeListPage;
import com.benjamin.erp.domain.UserInfo;
import com.benjamin.erp.repository.BorrowerInfoRepository;
import com.benjamin.erp.repository.BorrowerTenderRepository;
import com.benjamin.erp.repository.LoanCapitalRepository;
import com.benjamin.erp.repository.UserInfoRepository;
import com.benjamin.erp.service.LoanService;


@Service("loanService")
@Transactional
public class LoanServiceImpl implements LoanService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private BorrowerInfoRepository borrowerInfoRepository;
	
	@Autowired
	private BorrowerTenderRepository borrowerTenderRepository;
	
	@Autowired
	private LoanCapitalRepository loanCapotalRepository;
	
	@Autowired
	private AuditLoanTask auditLoanTask;
	
	@PostConstruct
	@Override
	public void init() {
		logger.info("初始化Loan模块.....");
		logger.info("初始化导航信息列表");
		SidebarDataList sidebarDataList = new SidebarDataList("","借款管理");
        SidebarDataItem sidebarDataItem = new SidebarDataItem("发布借款","/borrower/release");
        sidebarDataList.getSidebarDataItems().add(sidebarDataItem);
        
        sidebarDataItem = new SidebarDataItem("审核借款", AuditLoanPage.class);
        sidebarDataList.getSidebarDataItems().add(sidebarDataItem);
        
        sidebarDataItem = new SidebarDataItem("借款标列表", BorrowIndexPage.class);
        sidebarDataList.getSidebarDataItems().add(sidebarDataItem);
        
        CommoneUnit.getSidebarDataLists().add(sidebarDataList);
        
        sidebarDataList = new SidebarDataList("", "借款资产管理");
        sidebarDataList.getSidebarDataItems().add(new SidebarDataItem("充值列表", RechargeListPage.class));
        
        logger.info("初始化JBPM工作流");
        
        List<String> list = this.repositoryService.getDeploymentResourceNames("flow/auditLoan.bpmn");
        logger.debug("查询到的List数量:{}",list.size());
        if(list.isEmpty()) {
        	this.repositoryService.createDeployment().addClasspathResource("flow/auditLoan.bpmn").deploy();	
        }
	}

	@Override
	public void submitAuditLoan(BorrowerInfo borrowerInfo,String username) {
		logger.info("通过Username："+username+"来查询创建用户对象信息");
		this.identityService.setAuthenticatedUserId(username);
		UserInfo userInfo = this.userInfoRepository.findByUsername(username);
		logger.info("查询成功，对象Hash:"+userInfo);
		borrowerInfo.setCreateUserInfo(userInfo);
		borrowerInfo.setStatus(Status.DRAFT);
		this.borrowerInfoRepository.save(borrowerInfo);
		Assert.notNull(borrowerInfo.getCreateUserInfo(), "系统出现异常：创建借款标的用户不能为NULL");
		String processDefinitionKey = "auditLoan";
		logger.debug("流程运行,auditLoanTask:{}"+auditLoanTask);
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("borrowInfo", borrowerInfo);
		//variables.put("auditLoanTask", auditLoanTask);
		this.runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);
	}

	@Override
	public void auditLoan(DelegateExecution execution) {
		BorrowerInfo borrowerInfo = execution.getVariable("borrowInfo",BorrowerInfo.class);
		borrowerInfo.setStatus(Status.BIDDING);
		borrowerInfoRepository.save(borrowerInfo);
	}

	@Override
	public void tenderBorrowerRequest(String username,Integer borrowerId, BigDecimal amount) {
		// 投资流程 先检查投资者是否有钱。
		// 检查投资借款表的剩余资金是否合格
		UserInfo userInfo = this.userInfoRepository.findByUsername(username);
		LoanCapital loanCapital = this.loanCapotalRepository.findByUserInfo(userInfo);
		if(loanCapital.getActivityAmount().compareTo(amount) != 1){
			throw new IllegalArgumentException("当前用户没有足够的资金");
		}
		BorrowerInfo borrowerInfo = this.borrowerInfoRepository.findOne(borrowerId);
		if(borrowerInfo.getAlreadyFinancedAmount().compareTo(amount) == 1){
			throw new IllegalArgumentException("当前投资超出借款标的融资金额数量的上限");
		}
		BorrowerTender borrowerTender = new BorrowerTender();
		borrowerTender.setUserInfo(userInfo);
		borrowerTender.setAmount(amount);
		borrowerTender.setBorrowerInfo(borrowerInfo);
		
		this.borrowerTenderRepository.save(borrowerTender);
	}

	@Override
	public void tenderBorrowerResponse() {
		
	}

}
