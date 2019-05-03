package com.benjamin.erp.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import com.benjamin.erp.domain.RoleInfo;
import com.benjamin.erp.expand.BasicWebPage;

public class RolePage extends BasicWebPage {

	@Override
	public Model<String> getTitle() {
		return new Model<String>("角色管理");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		List<RoleInfo> roleInfos = new ArrayList<RoleInfo>();
		List<IColumn<RoleInfo, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<>(Model.of("名称"), "name"));
		columns.add(new PropertyColumn<>(Model.of("编码"), "encoder"));
		ListDataProvider<RoleInfo> dataProvider = new ListDataProvider<>(roleInfos);
 		DataTable<RoleInfo, String> dataTable = new DataTable<>("dataTable", columns, dataProvider, 10);
 		dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
 		add(dataTable);
	}

}
