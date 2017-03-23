package com.baosight.iwater.core.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;


public class LightWorkLoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public LightWorkLoginUrlEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        if ("XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"))){
        	response.setHeader("Content-Type", "text/html;charset=UTF-8");  
            State st = new BaseState();            
            response.getWriter().write(st.addState(AppInfo.SESSTION_LOST,null)); 
 
        } else
            super.commence(request, response, authException);
    }
}
