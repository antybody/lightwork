package com.baosight.iwater.define;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.baosight.iwater.system.cacheManager.controller.Task;

/**
 * 定时任务运行（反射出来的类）
 * @Description
 * @author whb
 */
@DisallowConcurrentExecution
public class QuartzJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("缓存刷新任务运行开始-------- start --------"); 
		try {
			//ScheduleJob任务运行时具体参数，可自定义
			Task task =(Task) context.getMergedJobDataMap().get("scheduleJob");
			task.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("缓存刷新任务运行结束-------- end --------"); 
	}
}