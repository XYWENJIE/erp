package com.benjamin.erp.activiti;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.delegate.JavaDelegateInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.benjamin.erp.domain.BorrowerInfo;
import com.benjamin.erp.domain.BorrowerInfo.Status;
import com.benjamin.erp.repository.BorrowerInfoRepository;

@Component("auditLoanTask")
public class AuditLoanTask implements JavaDelegate,Serializable{
	

	private static final long serialVersionUID = -5859016023980495225L;
	
	@Autowired
	private BorrowerInfoRepository borrowerInfoRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		BorrowerInfo borrowerInfo = execution.getVariable("borrowInfo",BorrowerInfo.class);
		borrowerInfo.setStatus(Status.BIDDING);
		borrowerInfoRepository.save(borrowerInfo);
	}

}
