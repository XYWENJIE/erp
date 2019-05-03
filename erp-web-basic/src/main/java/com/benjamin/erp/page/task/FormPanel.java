package com.benjamin.erp.page.task;

import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;

public abstract class FormPanel extends Panel{

	public FormPanel(String id) {
		super(id);
	}
	
	public abstract Map<String,Object> getVariables();

}
