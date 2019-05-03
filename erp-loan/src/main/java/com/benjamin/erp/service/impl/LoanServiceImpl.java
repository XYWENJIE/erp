package com.benjamin.erp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.benjamin.erp.activiti.AuditLoanTask;
import com.benjamin.erp.domain.BorrowerInfo;
import com.benjamin.erp.domain.BorrowerInfo.Status;
import com.benjamin.erp.domain.UserInfo;
import com.benjamin.erp.repository.BorrowerInfoRepository;
import com.benjamin.erp.repository.UserInfoRepository;
import com.benjamin.erp.service.LoanService;


@Service("loanService")
@Transactional
public class LoanServiceImpl implements LoanService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private BorrowerInfoRepository borrowerInfoRepository;
	
	@Autowired
	private AuditLoanTask auditLoanTask;

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

}
