package com.benjamin.erp.page.user;

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

import com.benjamin.erp.domain.UserInfo;

public class IndexDataPanel extends BreadCrumbPanel {

	public IndexDataPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IModel<String> getTitle() {
		return new Model<>("用户列表");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new BreadCrumbPanelLink("addUserLink", getBreadCrumbModel(), AddUserPanel.class));

		List<IColumn<UserInfo, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<>(Model.of("编号"), "id"));
		columns.add(new PropertyColumn<>(Model.of("用户"), "username"));
		columns.add(new PropertyColumn<>(Model.of("角色"), "username"));
		columns.add(new PropertyColumn<>(Model.of("部门"), "username"));
		columns.add(new PropertyColumn<>(Model.of("电话"), "username"));
		columns.add(new PropertyColumn<>(Model.of("邮箱"), "roleInfo.name"));
		columns.add(new PropertyColumn<>(Model.of("在职状态"), "roleInfo.name"));
		columns.add(new PropertyColumn<>(Model.of("登录时间"), "roleInfo.name"));
		columns.add(new PropertyColumn<>(Model.of("用户类型"), "roleInfo.name"));
		columns.add(new PropertyColumn<>(Model.of("性别"), "roleInfo.name"));
		columns.add(new PropertyColumn<>(Model.of("可查看配件"), "roleInfo.name"));
		ListDataProvider<UserInfo> dataProvider = new ListDataProvider<>();
		DataTable<UserInfo, String> dataTable = new DataTable<UserInfo, String>("dataTable", columns, dataProvider,
				10);
		dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
		add(dataTable);
	}

}
