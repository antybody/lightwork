package com.baosight.iwater.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {  
	private String successAdminUrl = "index.html";
	private String successNormalUrl = "normal.html";
    public AjaxAuthenticationSuccessHandler() {  
    }  
  
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,  
            Authentication authentication) throws IOException, ServletException {  
  
    	String user_info = request.getSession(true).getAttribute("USER_INFO").toString();
    	response.setHeader("Content-Type", "application/json;charset=UTF-8");  
        State st = new BaseState();
         
        try {  
        	if(user_info.equals("admin"))
        		st.addInfo("page", this.successAdminUrl);
        	else
        		st.addInfo("page", this.successNormalUrl);
        	
        	 response.getWriter().write(st.addState(AppInfo.SUCCESS,null));
             
        } catch (JsonProcessingException ex) {  
            throw new HttpMessageNotWritableException("无法获取的JSON对象: " + ex.getMessage(), ex);  
        }  
    }  
}