package com.benjamin.erp.service;

import java.math.BigDecimal;


import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionService {

    private static final long serialVersionUID = 1L;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public FunctionService() {
    	logger.info("服务实例化");
    }

    @SpringBean
    private LoanService loanService;
    


    public int tenderBorrower(String username, String password, Integer borrowerId, BigDecimal amount) {
        logger.info("接收用户请求服务校验请求方是否合规");
        // TODO 校验用户和密码
        this.loanService.tenderBorrowerRequest(username, borrowerId, amount);
        return 1;
    }
}