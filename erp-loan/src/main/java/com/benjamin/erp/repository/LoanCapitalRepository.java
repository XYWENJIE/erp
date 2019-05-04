package com.benjamin.erp.repository;

import com.benjamin.erp.domain.LoanCapital;
import com.benjamin.erp.domain.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanCapitalRepository extends JpaRepository<LoanCapital, Integer> {

    LoanCapital findByUserInfo(UserInfo userInfo);
    
}