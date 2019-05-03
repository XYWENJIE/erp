package com.benjamin.erp.page.user;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.benjamin.erp.domain.EmployeeInfo;
import com.benjamin.erp.domain.EmployeeInfo.InServiceStatus;
import com.benjamin.erp.expand.BasicFeedbackPanel;
import com.benjamin.erp.page.SuccessPage;
import com.benjamin.erp.domain.Section;
import com.benjamin.erp.domain.UserInfo;
import com.benjamin.erp.repository.UserInfoRepository;
import com.benjamin.erp.repository.SectionRepository;

public class AddUserPanel extends BreadCrumbPanel{
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private SectionRepository sectionRepository;
	
	@SpringBean
	private UserInfoRepository loginInfoRepository;
	
	

	public AddUserPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IModel<String> getTitle() {
		return new Model<>("新增用户");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new UserInfoForm("userInfoForm"));
	}
	
	private class UserInfoForm extends Form<UserInfo>{

		private static final long serialVersionUID = 1L;
		
		private UserInfo loginInfo = new UserInfo();

		public UserInfoForm(String id) {
			super(id);
		}
		
		@Override
		protected void onInitialize() {
			super.onInitialize();
			
			add(new BasicFeedbackPanel("feedback",FeedbackMessage.ERROR));
			
			List<Section> sections = sectionRepository.findAll();
			
			InServiceStatus[] inServiceStatus = EmployeeInfo.InServiceStatus.values();
			
			add(new RequiredTextField<>("username",new PropertyModel<>(loginInfo, "username")));
			add(new DropDownChoice<>("sections",new PropertyModel<>(loginInfo, "employeeInfo.section"),sections,new SectionChoiceRenderer()));
			add(new TextField<>("phone",new PropertyModel<>(loginInfo, "employeeInfo.phone")));
			add(new PasswordTextField("password",new PropertyModel<>(loginInfo, "password")));
			add(new NumberTextField<BigDecimal>("MaxYouFui",new PropertyModel<>(loginInfo, "employeeInfo.maxYouFui")));
			add(new NumberTextField<BigDecimal>("minDiscount",new PropertyModel<>(loginInfo, "employeeInfo.minDiscount")));
			
			add(new DropDownChoice<>("inServiceStatus",new PropertyModel<>(loginInfo, "employeeInfo.inServiceStatus"),Arrays.asList(inServiceStatus),new EnumChoiceRenderer<>(this)));
			add(new NumberTextField<BigDecimal>("minRepairDiscount", new PropertyModel<>(loginInfo, "employeeInfo.minRepairDiscount")));
			add(new EmailTextField("email",new PropertyModel<>(loginInfo, "employeeInfo.email")));
			add(new DropDownChoice<>("gender",new PropertyModel<>(loginInfo, "employeeInfo.gender"),Arrays.asList(EmployeeInfo.Gender.values()),new EnumChoiceRenderer<>(this)));
		}
		
		@Override
		protected void onSubmit() {
			loginInfoRepository.save(loginInfo);
			
			setResponsePage(SuccessPage.class);
		}
		
	}
	
	private class SectionChoiceRenderer extends ChoiceRenderer<Section>{

		private static final long serialVersionUID = 1L;

		@Override
		public Object getDisplayValue(Section object) {
			return object.getName();
		}

		@Override
		public String getIdValue(Section object, int index) {
			return object.getId().toString();
		}
		
	}

}
