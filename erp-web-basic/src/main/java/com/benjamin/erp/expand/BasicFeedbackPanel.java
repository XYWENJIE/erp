package com.benjamin.erp.expand;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class BasicFeedbackPanel extends FeedbackPanel{

	private static final long serialVersionUID = 1L;

	public BasicFeedbackPanel(String id) {
		super(id);
	}

	public BasicFeedbackPanel(String id,int minimumErrorLevel) {
		
		super(id, new ErrorLevelFeedbackMessageFilter(minimumErrorLevel));
		WebMarkupContainer markupContainer = (WebMarkupContainer)get("feedbackul");
		AttributeModifier attributeModifier = null;
		String styleClass = "alert alert-dismissible ";
		switch (minimumErrorLevel) {
		case FeedbackMessage.ERROR:
			attributeModifier = new AttributeModifier("class", styleClass+"alert-danger");
			break;
		default:
			break;
		}
		markupContainer.add(attributeModifier);
	}

	

}
