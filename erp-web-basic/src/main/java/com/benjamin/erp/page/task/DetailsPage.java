package com.benjamin.erp.page.task;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.benjamin.erp.application.ErpSigInSession;
import com.benjamin.erp.expand.BasicWebPage;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("task/details/#{taskId}")
public class DetailsPage extends BasicWebPage {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private TaskEntity taskEntity;

    private String taskId;
    
    @SpringBean
    private TaskService taskService;

    @SpringBean
    private FormService formService;
    
    @SpringBean
    private IdentityService identityService;

    private WebMarkupContainer commentsWebContainer;

    private List<Comment> commentList = new ArrayList<>();

    private FormPanel formPanel;

    @Override
    public Model<String> getTitle() {
        return new Model<>("任务详情");
    }

    public DetailsPage(PageParameters pageParameters){
       this.taskId = pageParameters.get("taskId").toString();
    }

    public DetailsPage(TaskEntity taskEntity){
        this.taskEntity = taskEntity;
        this.taskId = taskEntity.getId();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        logger.info("查询任务的评论！");
        commentList = this.taskService.getTaskComments(taskId);
        commentsWebContainer = new WebMarkupContainer("commentsWebContainer");
        commentsWebContainer.setOutputMarkupId(true);
        add(commentsWebContainer);
        RefreshingView<CommentEntity> refreshingView = new RefreshingView<CommentEntity>("commentList") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<CommentEntity> item) {
				ErpSigInSession erpSigInSession = (ErpSigInSession)getSession();
				if(!erpSigInSession.getUsername().equals(item.getModelObject().getUserId())) {
					item.add(new AttributeModifier("class", "direct-chat-msg right"));
				}
				item.add(new Label("username",item.getModelObject().getUserId()));
				item.add(new Label("time",item.getModelObject().getTime()));
				item.add(new Label("message",item.getModelObject().getFullMessage()));
			}
			
			@Override
			protected Iterator<IModel<CommentEntity>> getItemModels() {
				List<IModel<CommentEntity>> models = new ArrayList<IModel<CommentEntity>>();
				for(Comment comment : commentList) {
					models.add(new Model<CommentEntity>((CommentEntity)comment));
				}
				return models.iterator();
			}
		};
		commentsWebContainer.add(refreshingView);
        add(new TaskDetailsForm("taskDetailsForm"));
        
        add(new CommemtForm("commentForm"));
    }

    public class TaskDetailsForm extends Form<FormProperty>{

        private static final long serialVersionUID = 1L;
        
        public TaskDetailsForm(String id){
            super(id);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();
            TaskFormData taskFormData = formService.getTaskFormData(taskId);
            List<FormProperty> formProperties = taskFormData.getFormProperties();
            for(FormProperty formProperty : formProperties){
                
            }
            String formKey = taskFormData.getFormKey();
            formKey = "com.benjamin.erp.page.task.BorrowDetailsTaskPanel";
            try{
                logger.info("Java类反射构建对象,外部表单要求构造方法必须有：ID和TaskId，taskId的值是{}",taskId);
                Class<?> panelClass = Class.forName(formKey);
                Constructor<?> constructor = panelClass.getConstructor(String.class,String.class);
                formPanel = (FormPanel)constructor.newInstance("formKey",taskId);
                add(formPanel);
            }catch(Exception e){
                logger.error("反射构建失败：{}",e.getMessage(),e);
                error("类构建反射失败！");
                throw new WicketRuntimeException(e.getMessage(),e);
                
            }
            
        }

        @Override
        protected void onSubmit() {
            Map<String,Object> variables = new HashMap<>();
            variables.putAll(formPanel.getVariables());
            taskService.complete(taskId, variables);
        }
    }
    
    private class CommemtForm extends Form<Void>{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
        private String message;
        
        private TextField<String> messageTextField;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public CommemtForm(String id) {
			super(id);
		}
		
		@Override
		protected void onInitialize() {
			super.onInitialize();
            add(messageTextField = new TextField<String>("message",PropertyModel.of(this, "message")));
            messageTextField.setOutputMarkupId(true);
			add(new AjaxButton("submitMessage") {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void onSubmit(AjaxRequestTarget target) {
					ErpSigInSession erpSigInSession = (ErpSigInSession)getSession();
					identityService.setAuthenticatedUserId(erpSigInSession.getUsername());
					Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
                    Comment comment = taskService.addComment(taskId, task.getProcessInstanceId(), message);
                    commentList.add(0,comment);
					target.add(commentsWebContainer);
                    target.add(messageTextField);
                    target.appendJavaScript("document.getElementById('"+messageTextField.getMarkupId()+"').focus()");
				}
			});
		}
    	
    }



    
    
}