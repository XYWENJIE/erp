package com.benjamin.erp.page;

import com.benjamin.erp.application.ErpSigInSession;
import com.benjamin.erp.domain.UserInfo;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

@MountPath("login")
public class LoginPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private TextField<String> username;
	
	private PasswordTextField passwrod;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new SignInForm("signInForm"));
		add(new FeedbackPanel("freedback"));
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		logger.info("加载资源");
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("bootstrap/current/css/bootstrap.min.css")));
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("font-awesome/current/css/font-awesome.min.css")));
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("ionicons/current/css/ionicons.min.css")));
		response.render(CssHeaderItem.forReference(new PackageResourceReference(LoginPage.class, "AdminLTE.min.css")));
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference("icheck/current/skins/square/blue.css")));
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("jquery/current/jquery.min.js")));
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("bootstrap/current/js/bootstrap.min.js")));
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("icheck/current/icheck.js")));
		response.render(OnDomReadyHeaderItem.forScript("$('input').iCheck({checkboxClass:'icheckbox_square-blue',radioClass:'iradio_square-blue',increaseArea:'20%'});"));
		logger.info("加载资源完成");
	}
	
	public final class SignInForm extends Form<Void>{

		private static final long serialVersionUID = 1L;
		
		private UserInfo loginInfo = new UserInfo();
		
		public SignInForm(String id) {
			super(id);
			add(username = new TextField<>("username",new PropertyModel<>(loginInfo, "username")));
			add(passwrod = new PasswordTextField("password",new PropertyModel<>(loginInfo, "password")));
		}
		
		@Override
		protected void onSubmit() {
			ErpSigInSession erpSigInSession = (ErpSigInSession)getSession();
			if(erpSigInSession.signIn(loginInfo.getUsername(), loginInfo.getPassword())) {
				onSignInSucceeded();
			}
			logger.info("登录失败！");
		}
		
	}
	
	public void onSignInSucceeded() {
		continueToOriginalDestination();
		
		setResponsePage(getApplication().getHomePage());
		
	}

}
