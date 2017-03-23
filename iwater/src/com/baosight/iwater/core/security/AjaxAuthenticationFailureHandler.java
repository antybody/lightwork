package com.baosight.iwater.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.fasterxml.jackson.core.JsonProcessingException;
public  class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler  {  
	
    public AjaxAuthenticationFailureHandler() {  
    }  
  

	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setHeader("Content-Type", "application/json;charset=UTF-8");  
        State st = new BaseState();
         
        try {  
        	response.getWriter().write(st.addState(AppInfo.NO_USER,null));             
        } catch (JsonProcessingException ex) {  
            throw new HttpMessageNotWritableException("无法获取的JSON对象: " + ex.getMessage(), ex);  
        }  
		
	}  
}