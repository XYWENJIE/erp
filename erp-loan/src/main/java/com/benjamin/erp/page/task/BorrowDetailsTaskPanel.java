package com.benjamin.erp.page.task;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.benjamin.erp.domain.BorrowerInfo;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BorrowDetailsTaskPanel extends FormPanel {

	private static final long serialVersionUID = 1L;

	private Logger logger = LogManager.getLogger();

	private BorrowerInfo borrowerInfo;

	private Boolean verifyPass;

	@SpringBean
	private TaskService taskService;

	private TaskEntity taskEntity;

	private String taskId;

	public BorrowDetailsTaskPanel(String id, String taskId) {
		super(id);
		this.taskId = taskId;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		borrowerInfo = this.taskService.getVariable(taskId, "borrowInfo",BorrowerInfo.class);
		logger.info("查询到任务提交数据：{}",borrowerInfo);
		add(new TextField<String>("name",PropertyModel.of(borrowerInfo, "name")));
		add(new TextField<BigDecimal>("totalLoanAmount",PropertyModel.of(borrowerInfo, "totalLoanAmount")));
		List<Boolean> choices = Arrays.asList(Boolean.FALSE,Boolean.TRUE);
		add(new DropDownChoice<>("verifyPass",PropertyModel.of(this, "verifyPass"),choices));
	}

	@Override
	public Map<String, Object> getVariables() {
		Map<String,Object> variables = new HashMap<>();
		variables.put("verifyPass", verifyPass);
		return variables;
	}

}
