package com.benjamin.erp.page.task;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import com.benjamin.erp.expand.BasicWebPage;

@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class TaskTablePage extends BasicWebPage {
	
	@SpringBean
	private TaskService taskService;

	@Override
	public Model<String> getTitle() {
		// TODO Auto-generated method stub
		return new Model<>("任务列表");
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		List<IColumn<TaskEntity, String>> columns = this.creareColumns();
		IDataProvider<TaskEntity> dataProvider = this.getDataProvider();
		DataTable<TaskEntity, String> dataTable = new DataTable<TaskEntity, String>("dataTable",columns,dataProvider,10);
		dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
		add(dataTable);
	}

	
	protected List<IColumn<TaskEntity, String>> creareColumns(){
		List<IColumn<TaskEntity, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<TaskEntity, String>(Model.of("编号"),"id"));
		columns.add(new PropertyColumn<>(Model.of("名称"), "name"));
		columns.add(new PropertyColumn<>(Model.of("签收者"), "assignee"));
		columns.add(new PropertyColumn<>(Model.of("创建时间"), "createTime"));
		columns.add(new AbstractColumn<TaskEntity, String>(Model.of("操作")) {

			@Override
			public void populateItem(Item<ICellPopulator<TaskEntity>> cellItem, String componentId,
					IModel<TaskEntity> rowModel) {
				cellItem.add(new ActionLinkPanel(componentId,rowModel.getObject(),rowModel.getObject().getId()));
			}
		});
		return columns;
	}
	
	protected IDataProvider<TaskEntity> getDataProvider(){
		List<Task> tasks =  this.taskService.createTaskQuery().list();
		List<TaskEntity> taskEntities = new ArrayList<>();
		for(Task task : tasks) {
			taskEntities.add((TaskEntity)task);
		}
		IDataProvider<TaskEntity> dataProvider = new ListDataProvider<TaskEntity>(taskEntities);
		return dataProvider;
	}
}
