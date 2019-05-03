package com.benjamin.erp.service;

import org.activiti.engine.delegate.DelegateExecution;

import com.benjamin.erp.domain.BorrowerInfo;

public interface LoanService {
	
	void submitAuditLoan(BorrowerInfo borrowerInfo,String username);
	
	void auditLoan(DelegateExecution execution);

}
