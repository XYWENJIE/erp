package com.benjamin.erp.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.benjamin.erp.page.task.TaskTablePage;

public class AuditLoanPage extends TaskTablePage {
	
	@SpringBean
	private TaskService taskService;
	
	

	private static final long serialVersionUID = 1L;

	@Override
	public Model<String> getTitle() {
		return new Model<>("审核借款");
	}



	@Override
	protected IDataProvider<TaskEntity> getDataProvider() {
		return new AuditDataProvider();
	}
	
	
	private class AuditDataProvider implements IDataProvider<TaskEntity>{

		private static final long serialVersionUID = 1L;

		@Override
		public Iterator<? extends TaskEntity> iterator(long first, long count) {
			List<Task> taskEntities = taskService.createTaskQuery().taskDefinitionKey("auditLoanTask").listPage((int)first, (int)count);
			List<TaskEntity> entities = new ArrayList<TaskEntity>();
			for(Task task : taskEntities) {
				entities.add((TaskEntity)task);
			}
			return entities.iterator();
		}

		@Override
		public long size() {
			return taskService.createTaskQuery().count();
		}

		@Override
		public IModel<TaskEntity> model(TaskEntity object) {
			return new Model<>(object);
		}
		
	}
	

}
