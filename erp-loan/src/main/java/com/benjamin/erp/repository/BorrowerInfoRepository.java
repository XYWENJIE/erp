package com.benjamin.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.benjamin.erp.domain.BorrowerInfo;

public interface BorrowerInfoRepository extends JpaRepository<BorrowerInfo, Integer> {

}
