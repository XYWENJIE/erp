package com.benjamin.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.benjamin.erp.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	
	UserInfo findByUsername(String username);

}
