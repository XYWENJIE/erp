package com.benjamin.erp.page.car.license;

import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.benjamin.erp.expand.BasicModalWindows;

public class AddDriveLicenseImagePanel extends BreadCrumbPanel{

	private static final long serialVersionUID = 1L;

	public AddDriveLicenseImagePanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IModel<String> getTitle() {
		// TODO Auto-generated method stub
		return new Model<>("上传驾驶证");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		DriverLicesePositive driverLicesePositive = new DriverLicesePositive("driverLicesePositive");
		driverLicesePositive.add(new UploadProgressBar("progress", driverLicesePositive));
		driverLicesePositive.setMultiPart(true);
		add(driverLicesePositive);
		
		final BasicModalWindows modalWindows = new BasicModalWindows("modalWindow");
		
		
		modalWindows.setContent(new DriverLicenseUploaderPanel(modalWindows.getContentId()));
		modalWindows.setTitle("Modal window");
//		add(new AjaxLink<Void>("showShortMsgBox") {
//
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				modalWindows.show(target);
//			}
//		});
		
		add(modalWindows);
	}
	
	public class DriverLicesePositive extends Form<Void>{
		
		private FileUploadField updates;

		public FileUploadField getUpdates() {
			return updates;
		}

		public DriverLicesePositive(String id) {
			super(id);
			
			add(updates = new FileUploadField("fileInput"));
		}
		
		@Override
		protected void onSubmit() {
			
			activate((componentId,breadCrumbModel) -> new AddDriverLicensePanel(componentId, breadCrumbModel,updates));
		}
		
	}
	
	

}
