package com.benjamin.erp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TAB_BORROWER_INFO")
public class BorrowerInfo implements Serializable {

    private static final long serialVersionUID = -5328439434233825806L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "create_user_id")
    private UserInfo createUserInfo;
    
    @Column(name = "total_loan_amount")
    private BigDecimal totalLoanAmount;

    @Column(name = "already_financed_amount")
    private BigDecimal alreadyFinancedAmount;
    
    @Version
    private Long version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public BigDecimal getTotalLoanAmount() {
		return totalLoanAmount;
	}

	public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public BigDecimal getAlreadyFinancedAmount() {
		return alreadyFinancedAmount;
	}

	public void setAlreadyFinancedAmount(BigDecimal alreadyFinancedAmount) {
		this.alreadyFinancedAmount = alreadyFinancedAmount;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public enum Status{
		DRAFT,BIDDING
	}
    
}
