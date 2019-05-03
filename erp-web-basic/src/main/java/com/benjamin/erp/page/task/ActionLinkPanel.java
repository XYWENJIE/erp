package com.benjamin.erp.page.task;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.benjamin.erp.application.ErpSigInSession;

public class ActionLinkPanel extends Panel {
	
	private static final long serialVersionUID = 1L;

	private Logger logger = LogManager.getLogger();
	
	@SpringBean
	private TaskService taskService;
	
	private String taskId;
	
	private TaskEntity taskEntity;

	public ActionLinkPanel(String id,TaskEntity taskEntity,String taskId) {
		super(id);
		this.taskId = taskId;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Link<Void>("claim") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				ErpSigInSession erpSigInSession = (ErpSigInSession)getSession();
				logger.info("username:{}签收任务",erpSigInSession.getUsername());
				taskService.claim(taskId, erpSigInSession.getUsername());
				setResponsePage(getClaimSuccessPage());
			}
			
		});
		
		PageParameters parameters = new PageParameters();
		parameters.set("taskId", taskId);
		add(new BookmarkablePageLink<>("details", DetailsPage.class,parameters));
		// add(new Link<Void>("detail") {

		// 	private static final long serialVersionUID = 1L;

		// 	@Override
		// 	public void onClick() {
		// 		DetailsPage detailsPage = new DetailsPage(new TaskEntity(taskId));
		// 		logger.info("打开任务详情:{}",taskId);
		// 		setResponsePage(detailsPage);
		// 	}
			
		// });
	}
	
	public Class<? extends WebPage> getClaimSuccessPage() {
		return TaskTablePage.class;
	}

}
