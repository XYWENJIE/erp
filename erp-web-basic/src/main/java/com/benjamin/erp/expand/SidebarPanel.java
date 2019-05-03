package com.benjamin.erp.expand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SidebarPanel extends Panel {
	
	private static final long serialVersionUID = 1L;

	private Logger logger = LogManager.getLogger();
	
	private List<SidebarDataList> sidebarDataLists;
	
	private Class<? extends Page> currentPageClass;
	
	private boolean tab = false;

	public SidebarPanel(String id,Class<? extends Page> currentPageClass) {
		super(id);
		this.sidebarDataLists = CommoneUnit.getSidebarDataLists();
		this.currentPageClass = currentPageClass;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		List<IModel<SidebarDataList>> models = new ArrayList<IModel<SidebarDataList>>();
		logger.info("导航列表：{}",sidebarDataLists);
		Collections.sort(sidebarDataLists,Collections.reverseOrder());
		logger.info("重新排序导航列表：{}", sidebarDataLists);
		for(SidebarDataList dataList : sidebarDataLists) {
			models.add(new Model<>(dataList));
		}
		add(new RefreshingView<SidebarDataList>("sidebarDataList") {

			@Override
			protected Iterator<IModel<SidebarDataList>> getItemModels() {
				return models.iterator();
			}

			@Override
			protected void populateItem(Item<SidebarDataList> item) {
				logger.debug("开始迭代当前的List菜单组件页面");
				final Item<SidebarDataList> listComponent = item;
				item.add(new Label("name",item.getModelObject().getName()));
				item.add(new RefreshingView<SidebarDataItem>("dataItems") {

					@Override
					protected Iterator<IModel<SidebarDataItem>> getItemModels() {
						List<IModel<SidebarDataItem>> list = new ArrayList<>();
						for(SidebarDataItem sidebarDataItem : item.getModelObject().getSidebarDataItems()) {
							list.add(new Model<>(sidebarDataItem));
						}
						return list.iterator();
					}

					@Override
					protected void populateItem(Item<SidebarDataItem> item) {
						Link<Void> link = new Link<Void>("hrefLink") {
							
							@Override
							public void onClick() {
								setResponsePage(item.getModelObject().getPagePath());
							}
						};
						link.add(new Label("itemName",item.getModelObject().getName()));
						item.add(link);
						Class<? extends Page> pagePath = item.getModelObject().getPagePath();
						if(pagePath.getName().equals(currentPageClass.getName())) {
							logger.debug("当前选项《{}》已经激活，并标记List菜单组件激活",item.getModelObject().getName());
							item.add(new AttributeModifier("class","active"));
							listComponent.add(new AttributeModifier("class", "active treeview"));
							tab = true;
						}
					}
					
				});
				logger.debug("结束迭代当前的List菜单组件");
			}
		});
	}

}
