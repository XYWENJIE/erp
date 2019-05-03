package com.benjamin.erp.page;

import com.benjamin.erp.application.ErpSigInSession;
import com.benjamin.erp.domain.BorrowerInfo;
import com.benjamin.erp.expand.BasicWebPage;
import com.benjamin.erp.service.LoanService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ReleaseBorrowerPage extends BasicWebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private LoanService loanService;
	
	private Logger logger = LogManager.getLogger();
	
	
	BorrowerInfo borrowerInfo = new BorrowerInfo();

    @Override
    public Model<String> getTitle() {
        return new Model<>("发布借款标");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ReleaseBorrowerForm("releaseBorrowerForm",new Model<>(borrowerInfo)));
    }

    private class ReleaseBorrowerForm extends Form<BorrowerInfo>{

		private static final long serialVersionUID = 1L;

		public ReleaseBorrowerForm(String id, IModel<BorrowerInfo> model) {
            super(id, model);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            add(new RequiredTextField<String>("name",new PropertyModel<>(borrowerInfo,"name")));
            add(new RequiredTextField<>("totalLoanAmount",new PropertyModel<>(borrowerInfo, "totalLoanAmount")));
        }
        
        @Override
        	protected void onSubmit() {
        		logger.info("调用贷款服务功能，Spring提供");
        		ErpSigInSession erpSigInSession =  (ErpSigInSession)getSession();
        		loanService.submitAuditLoan(borrowerInfo, erpSigInSession.getUsername());
        		setResponsePage(SuccessPage.class);
        	}
    }
}
