package org.benjamin.erp.test;


import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.benjamin.erp.HomePage;
import com.benjamin.erp.WicketBasicTest;
import com.benjamin.erp.application.ErpWebApplication;
import com.benjamin.erp.config.SpringApplicationContext;
import com.benjamin.erp.page.AuditLoanPage;
import com.benjamin.erp.page.LoginPage;
import com.benjamin.erp.page.ReleaseBorrowerPage;
import com.benjamin.erp.page.SuccessPage;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { SpringApplicationContext.class })
public class BorrowerTest extends WicketBasicTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ErpWebApplication erpWebApplication;

	private ApplicationContext applicationContext;

	private WicketTester wicketTester;

	@Before
	public void wicketBefore() {
		wicketTester = new WicketTester(erpWebApplication);
		//this.loginTest();
	}
	
	@After
	public void wicketAfter() {
		wicketTester.destroy();
	}

	private void loginTest() {
		this.wicketTester.startPage(LoginPage.class);
		this.wicketTester.assertRenderedPage(LoginPage.class);
		FormTester formTester = this.wicketTester.newFormTester("signInForm");
		formTester.setValue("username", "admin");
		formTester.setValue("password", "admin");
		formTester.submit();
		this.wicketTester.assertRenderedPage(HomePage.class);
	}

	// 发布借款
	@Test
	public void loan_001_tenderBorrower() {
		this.wicketTester.startPage(ReleaseBorrowerPage.class);
		this.wicketTester.assertRenderedPage(ReleaseBorrowerPage.class);
		logger.info("填写表单");
		FormTester formTester = this.wicketTester.newFormTester("releaseBorrowerForm");
		formTester.setValue("name", "个人贷款10001");
		formTester.setValue("totalLoanAmount", "100");
		formTester.submit();
		this.wicketTester.assertRenderedPage(SuccessPage.class);
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
		wicketTester.getRequest().setMethod("POST");
		wicketTester.executeUrl("./restfull/loan/tenderBorrower");
		String json = wicketTester.getLastResponseAsString();
		logger.info("返回测试值");
		logger.info(json);
	}

}
