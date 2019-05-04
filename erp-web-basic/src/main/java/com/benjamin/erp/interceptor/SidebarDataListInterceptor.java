package com.benjamin.erp.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.benjamin.erp.expand.CommoneUnit;
import com.benjamin.erp.expand.SidebarDataList;

public class SidebarDataListInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		List<SidebarDataList> sidebarDataList = CommoneUnit.getSidebarDataLists();
		if(sidebarDataList != null) {
			modelAndView.addObject("sidebarDataLists",sidebarDataList);	
		}
	}

}
