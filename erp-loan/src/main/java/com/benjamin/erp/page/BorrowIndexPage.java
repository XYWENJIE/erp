package com.benjamin.erp.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.benjamin.erp.domain.BorrowerInfo;
import com.benjamin.erp.expand.BasicWebPage;
import com.benjamin.erp.repository.BorrowerInfoRepository;

public class BorrowIndexPage extends BasicWebPage {
	
	@SpringBean
	private BorrowerInfoRepository borrowerInfoRepository;

	@Override
	public Model<String> getTitle() {
		return new Model<>("借款列表");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		List<IColumn<BorrowerInfo, String>> columns = new ArrayList<IColumn<BorrowerInfo,String>>();
		columns.add(new PropertyColumn<>(Model.of("编号"), "id"));
		columns.add(new PropertyColumn<>(Model.of("名称"), "name"));
		columns.add(new PropertyColumn<>(Model.of("状态"), "status"));
		columns.add(new PropertyColumn<>(Model.of("借款金额"), "totalLoanAmount"));
		
		List<BorrowerInfo> borrowerInfos = borrowerInfoRepository.findAll();
		ListDataProvider<BorrowerInfo> dataProvider = new ListDataProvider<BorrowerInfo>(borrowerInfos);
		DataTable<BorrowerInfo, String> dataTable = new DataTable<BorrowerInfo, String>("dataTable",columns,dataProvider,10);
		dataTable.addTopToolbar(new HeadersToolbar(dataTable,null));
		add(dataTable);
	}

}
