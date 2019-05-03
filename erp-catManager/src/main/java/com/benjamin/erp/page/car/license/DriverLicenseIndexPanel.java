package com.benjamin.erp.page.car.license;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.breadcrumb.IBreadCrumbModel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanel;
import org.apache.wicket.extensions.breadcrumb.panel.BreadCrumbPanelLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.benjamin.erp.domain.DriverLicense;

public class DriverLicenseIndexPanel extends BreadCrumbPanel{

	public DriverLicenseIndexPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
	}

	@Override
	public IModel<String> getTitle() {
		return null;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new BreadCrumbPanelLink("addDriverLicense", getBreadCrumbModel(), AddDriveLicenseImagePanel.class));
		
		List<IColumn<DriverLicense, String>> columns = new ArrayList<IColumn<DriverLicense,String>>();
		columns.add(new PropertyColumn<>(Model.of("编号"), "id"));
		columns.add(new PropertyColumn<>(Model.of("姓名"), "name"));
		columns.add(new PropertyColumn<>(Model.of("地址"), "address"));
		ListDataProvider<DriverLicense> dataProvider = new ListDataProvider<DriverLicense>();
		DataTable<DriverLicense, String> dataTable = new DataTable<DriverLicense, String>("dataTable",columns,dataProvider,10);
		dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
		add(dataTable);
	}

}
