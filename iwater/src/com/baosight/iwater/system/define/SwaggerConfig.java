package com.baosight.iwater.system.define;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;  
import com.mangofactory.swagger.models.dto.ApiInfo;  
import com.mangofactory.swagger.plugin.EnableSwagger;  
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;  

@Configuration  
@EnableWebMvc
@EnableSwagger  
public class SwaggerConfig {  
	
private SpringSwaggerConfig springSwaggerConfig;  

@Autowired  
public void setSpringSwaggerConfig(SpringSwaggerConfig springs) {  
	
	this.springSwaggerConfig = springs;  
}  

@Bean  
public SwaggerSpringMvcPlugin customImplementation()  
{     
	return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");//������֧������ƥ��ģ�ֻ�����������˲ſ�����ҳ�濴����  
}  

private ApiInfo apiInfo() {  
	ApiInfo apiInfo = new ApiInfo("API 管理列表",
			"My API Description",
			"My  API terms of service",
			"My  API Contact Email",
			"My  API Licence Type",
            "My  API License URL");  
	return apiInfo;  
  }  
}
