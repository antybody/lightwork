package com.baosight.iwater.core.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class InvalidateSession {
	@RequestMapping(value = "/public/security")  
    @ResponseBody  
    public String invalidateSession(HttpServletRequest reqeust,HttpServletResponse response) {  
		//判断是否是ajax请求
		/*  String ajaxHeader = reqeust.getHeader("X-Requested-With");  
            boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);  
            if (isAjax) {  
                return "invalidSession";  
            } else {  */
                try {  
                    response.sendRedirect("http://localhost:8080/iwater/login.html");  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
           // }  
        return "";  
    }  
}
