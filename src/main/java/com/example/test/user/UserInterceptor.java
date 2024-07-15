package com.example.test.user;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor{
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception{
		log.info("preHandler().....");
		
		HttpSession session = request.getSession();
		String sPath = request.getServletPath();
		log.info("sPath:{}", sPath);
		log.info("username:{}", session.getAttribute("username"));
		
		if(sPath.equals("/user/logout") || sPath.equals("/user/info") || sPath.equals("/user/info/updateOK") || sPath.equals("/user/info/delete") || sPath.equals("/user/info/deleteOK") || sPath.equals("/user/info/update")
		|| sPath.equals("/meeting") || sPath.equals("/meeting/insert") || sPath.equals("/meeting/insertOK") || sPath.equals("/meeting/register")  || sPath.equals("/meeting/registerOK") || sPath.equals("/date")
				|| sPath.equals("/date/register") || sPath.equals("/date/registerOK") || sPath.equals("/date/selectOne") || sPath.equals("/date/delete") || sPath.equals("/date/deleteOK") || sPath.equals("/date/selectAll") || sPath.equals("/menu")
		) {
			if(session.getAttribute("username")==null) {
				response.sendRedirect("/user/login");
				return false;
			}
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		log.info("postHandle()....");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
}
