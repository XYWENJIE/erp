package com.benjamin.erp.page.car.license;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benjamin.erp.Gender;
import com.benjamin.erp.domain.DriverLicense;
import com.benjamin.erp.domain.ResourceInfo;

public class AddDriverLicensePanel extends BreadCrumbPanel{
	
	private DriverLicense driverLicense = new DriverLicense();
	
	private FileUploadField fileUploadField;
	
	private List<ResourceInfo> resourceInfos = new ArrayList<>();

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public AddDriverLicensePanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
	}
	
	public AddDriverLicensePanel(String id, IBreadCrumbModel breadCrumbModel,FileUploadField fileUploader) {
		super(id,breadCrumbModel);
		this.fileUploadField = fileUploader;
		List<FileUpload> fileUploads = fileUploader.getFileUploads();
		for(FileUpload fileUpload : fileUploads) {
			byte[] binaryData = fileUpload.getBytes();
			String base64 = Base64.encodeBase64String(binaryData);
			logger.info("计算图片的Base64结果，{}",base64 != null);
			ResourceInfo resourceInfo = new ResourceInfo();
			resourceInfo.setBase64(base64);
			resourceInfos.add(resourceInfo);
		}
		
	}

	@Override
	public IModel<String> getTitle() {
		return new Model<>("录入驾驶证信息");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new DriverLicenseForm("driverLicenseForm"));
	}
	
	private class DriverLicenseForm extends Form<DriverLicense>{

		public DriverLicenseForm(String id) {
			super(id);
		}
		
		@Override
		protected void onInitialize() {
			super.onInitialize();
			
			RefreshingView<ResourceInfo> imageList = new RefreshingView<ResourceInfo>("imageList") {
				
				@Override
				protected void populateItem(Item<ResourceInfo> item) {
					Image image = new Image("image", new Model<>());
					image.add(new AttributeModifier("src", "data:image/gif;base64,"+item.getModelObject().getBase64()));
					item.add(image);
				}
				
				@Override
				protected Iterator<IModel<ResourceInfo>> getItemModels() {
					List<IModel<ResourceInfo>> models = new ArrayList<IModel<ResourceInfo>>();
					for(ResourceInfo resourceInfo : resourceInfos) {
						models.add(new Model<>(resourceInfo));
					}
					return models.iterator();
				}
			};
			add(imageList);
			add(new RequiredTextField<>("name",new PropertyModel<>(driverLicense, "name")));
			add(new TextField<>("number", new PropertyModel<>(driverLicense,"number")));
			add(new TextField<>("address",new PropertyModel<>(driverLicense,"address")));
			add(new TextField<>("dateofFirstIssue",new PropertyModel<>(driverLicense,"dateofFirstIssue")));
			add(new TextField<>("vehicleType",new PropertyModel<>(driverLicense,"vehicleType")));
			
			DropDownChoice<Gender> dropDownChoice = new DropDownChoice<>("gender",new PropertyModel<>(driverLicense, "sex"),Arrays.asList(Gender.values()),new EnumChoiceRenderer<>(this));
			add(dropDownChoice);
			
		}
	}

}
