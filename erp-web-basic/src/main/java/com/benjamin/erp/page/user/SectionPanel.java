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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.benjamin.erp.domain.Section;
import com.benjamin.erp.repository.SectionRepository;

public class SectionPanel extends BreadCrumbPanel {

	public SectionPanel(String id, IBreadCrumbModel breadCrumbModel) {
		super(id, breadCrumbModel);
	}


	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private SectionRepository sectionRepository;

	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new BreadCrumbPanelLink("addSectionLink",getBreadCrumbModel(),AddSectionPanel.class));
		
		
		List<Section> list = sectionRepository.findAll();
		
		ListDataProvider<Section> dataProvider = new ListDataProvider<Section>(list);
		
		List<IColumn<Section, String>> columns = new ArrayList<IColumn<Section,String>>();
		columns.add(new PropertyColumn<>(Model.of("名称"), "name"));
		columns.add(new PropertyColumn<>(Model.of("排序"), "orderValue"));
		
		DataTable<Section, String> dataTable = new DataTable<Section, String>("dataTable",columns,dataProvider,10);
		dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
		add(dataTable);
	}


	@Override
	public IModel<String> getTitle() {
		return new Model<>("部门管理");
	}

}
