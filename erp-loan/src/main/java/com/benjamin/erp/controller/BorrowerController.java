package com.benjamin.erp.controller;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.benjamin.erp.domain.BorrowerInfo;
import com.benjamin.erp.service.LoanService;

@Controller
@RequestMapping("/borrower")
public class BorrowerController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoanService loanService;
	
	@GetMapping("/release")
	public ModelAndView toRelease(@ModelAttribute("borrowerInfo") BorrowerInfo borrowerInfo) {
		ModelAndView modelAndView = new ModelAndView("borrower/release");
		return modelAndView;
	}
	
	@PostMapping("/release")
	public ModelAndView doRelease(@Validated BorrowerInfo borrowerInfo, BindingResult bindingResult) {
		logger.info("检查表单校验是否合规，表单错误数量:{}",bindingResult.getErrorCount());
		ValidatorFactory factory = new Validation().buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BorrowerInfo>> constraintViolations = validator.validate(borrowerInfo);
		logger.info("{}",constraintViolations.size());
		if(bindingResult.hasErrors()) {
			logger.info("检查到表单错误！");
			return toRelease(borrowerInfo);
		}
		this.loanService.submitAuditLoan(borrowerInfo, "admin");
		return new ModelAndView("success");
	}

}
