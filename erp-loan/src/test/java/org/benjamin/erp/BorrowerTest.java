package org.benjamin.erp;


import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.benjamin.erp.config.SpringApplicationContext;
import com.benjamin.erp.config.SpringServletContext;
import com.benjamin.erp.page.AuditLoanPage;
import org.springframework.web.servlet.FlashMap;

@SpringJUnitWebConfig(classes = {SpringApplicationContext.class,SpringServletContext.class})
public class BorrowerTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MockMvc mockMvc;

	private WicketTester wicketTester;
	
	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	public void wicketBefore() {
		//wicketTester = new WicketTester(erpWebApplication);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		//this.loginTest();
	}
	
	@AfterEach
	public void wicketAfter() {
		//wicketTester.destroy();
	}

	private void loginTest() {
//		this.wicketTester.startPage(LoginPage.class);
//		this.wicketTester.assertRenderedPage(LoginPage.class);
//		FormTester formTester = this.wicketTester.newFormTester("signInForm");
//		formTester.setValue("username", "admin");
//		formTester.setValue("password", "admin");
//		formTester.submit();
//		this.wicketTester.assertRenderedPage(HomePage.class);
	}

	// 发布借款
	@Test
	@Order(1)
	void tenderBorrower() {
		try {
			logger.info("开始访问/borrower/index");
			this.mockMvc.perform(MockMvcRequestBuilders.get("/borrower/release")).andExpect(MockMvcResultMatchers.status().isOk());
			logger.info("访问成功返回200");
			MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/borrower/release"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			logger.info(mvcResult.getResponse().getRedirectedUrl());
			logger.info(mvcResult.getFlashMap().toString());
			//logger.info(flashMap.toString());
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@Test
	public void loan_002_auditoBorrower() {
		this.wicketTester.startPage(AuditLoanPage.class);
		this.wicketTester.assertRenderedPage(AuditLoanPage.class);
		logger.info("审核借款");
		this.wicketTester.clickLink("dataTable:body:rows:1:cells:5:cell:claim");
		logger.info("审核签收完毕，开始进入查看详情并审核");
		this.wicketTester.clickLink("dataTable:body:rows:1:cells:5:cell:details");
		logger.info("进入详情页面,测试发送一条评论");
		FormTester formTester = this.wicketTester.newFormTester("commentForm");
		formTester.setValue("message", "测试借款标评论！");
		formTester.submit("submitMessage");
		logger.info("发送评论成功！");
		formTester = this.wicketTester.newFormTester("taskDetailsForm");
		formTester.select("formKey:verifyPass", 1);
		formTester.submit();
	}

	@Test
	public void loan_003_tenderBorrower(){
		logger.info("开始投资服务接口");
		//wicketTester.getRequest().setMethod("POST");
		wicketTester.executeUrl("./restfull/loan/tenderBorrower");
		String json = wicketTester.getLastResponseAsString();
		logger.info("返回测试值");
		logger.info(json);
	}

}
