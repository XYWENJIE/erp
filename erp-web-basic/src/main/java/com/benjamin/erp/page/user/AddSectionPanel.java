package com.benjamin.erp.page.user;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.benjamin.erp.domain.Section;
import com.benjamin.erp.page.SuccessPage;
import com.benjamin.erp.repository.SectionRepository;

public class AddSectionPanel extends BreadCrumbPanel{
	
	@SpringBean
	private SectionRepository sectionRepository;

	public AddSectionPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IModel<String> getTitle() {
		// TODO Auto-generated method stub
		return new Model<>("添加部门");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new SectionForm("sectionForm"));
		
	}
	
	private class SectionForm extends Form<Section>{
		
		private Section section = new Section();

		public SectionForm(String id) {
			super(id);
			add(new RequiredTextField<>("name",new PropertyModel<>(section, "name")));
		}
		
		@Override
		protected void onSubmit() {
			sectionRepository.save(section);
			setResponsePage(SuccessPage.class);
		}
		
	}

}
