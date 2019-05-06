package com.benjamin.erp.service;

import java.math.BigDecimal;

import org.activiti.engine.delegate.DelegateExecution;

import com.benjamin.erp.domain.BorrowerInfo;

public interface LoanService {
	
	public void init();
	
	void submitAuditLoan(BorrowerInfo borrowerInfo,String username);
	
	void auditLoan(DelegateExecution execution);
	
	void tenderBorrowerRequest(String username,Integer borrowerId,BigDecimal amount);

	void tenderBorrowerResponse();

}
