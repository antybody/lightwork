package com.baosight.iwater.system.log;
import javax.annotation.Resource; 
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;    
import org.aspectj.lang.annotation.*; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;    
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.log.pojo.Log;
import com.baosight.iwater.system.log.service.impl.LogServiceImpl;
import com.baosight.iwater.system.login.pojo.User;

import java.lang.reflect.Method;  
import java.text.SimpleDateFormat;
import java.util.Date;
/**  
 * 切点类  
 * @author antybody  
 * @since 2017-01-23  
 * @version 1.0  
 */    
@Aspect    
@Component   
public class SystemLogAspect {
	//注入Service用于把日志保存数据库    
    @Resource    
     private LogServiceImpl logService;    
    
    //本地异常日志记录对象    
    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class); 

    //Service层切点    
    @Pointcut("@annotation(com.baosight.iwater.system.log.SystemServiceLog)")    
     public  void serviceAspect() {    
    }    
    
    //Controller层切点    
    @Pointcut("@annotation(com.baosight.iwater.system.log.SystemControllerLog)")    
     public  void controllerAspect() {    
    }    
    
    /**  
     * 前置通知 用于拦截Controller层记录用户的操作  
     *  
     * @param joinPoint 切点  
     */    
    @Before("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) {    
    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //读取session中的用户    
        String user = (String) session.getAttribute("USER_INFO");  
        //请求的IP    
        String ip = request.getRemoteAddr();    
         try {    
            //*========控制台输出=========*//    
            System.out.println("=====前置通知开始=====");    
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));    
            System.out.println("请求人:" + user);    
            System.out.println("请求IP:" + ip);    
            String logdesc = getControllerMethodDescription(joinPoint);
            //*========数据库日志=========*//    
            Log log = new Log();
            log.setLog_method((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setLog_desc(logdesc);
            log.setLog_user(user);
            log.setLog_IP(ip);
            log.setAdd_date(Common.getTime());
            log.setLog_type("0");
            log.setUi_id(Common.getUUID());
            
            //如果是进入日志管理，则不记录日志
            if(!logdesc.contains("日志")){
            	logService.add(log);    
            }
            
            System.out.println("=====前置通知结束=====");    
        }  catch (Exception e) {    
            //记录本地异常日志    
            logger.error("==前置通知异常==");    
            logger.error("异常信息:{}", e.getMessage());    
        }    
    }
    
    /**  
     * 异常通知 用于拦截service层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")    
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //读取session中的用户    
        String user = (String) session.getAttribute("user_name");    
        //获取请求ip    
        String ip = request.getRemoteAddr();    
        //获取用户请求方法的参数并序列化为JSON格式字符串    
        String params = "";    
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
                params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";    
            }    
        }    
         try {    
              /*========控制台输出=========*/    
            System.out.println("=====异常通知开始=====");    
            System.out.println("异常代码:" + e.getClass().getName());    
            System.out.println("异常信息:" + e.getMessage());    
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));    
            System.out.println("请求人:" + user);    
            System.out.println("请求IP:" + ip);    
            System.out.println("请求参数:" + params);    
               /*==========数据库日志=========*/    
            Log log = new Log();
            log.setLog_method((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setLog_desc(getControllerMethodDescription(joinPoint));
            log.setLog_user(user);
            log.setLog_IP(ip);
            log.setAdd_date(Common.getTime());
            log.setLog_type("1");
            log.setUi_id(Common.getUUID());
//            Log log = new Log();    
//            log.setDescription(getServiceMthodDescription(joinPoint));    
//            log.setExceptionCode(e.getClass().getName());    
//            log.setType("1");    
//            log.setExceptionDetail(e.getMessage());    
//            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
//            log.setParams(params);    
//            log.setCreateBy(user);    
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            log.setCreateDate(df.format(new Date()));      
//            log.setRequestIp(ip);    
            //保存数据库    
//           logService.add(log);    
            System.out.println("=====异常通知结束=====");    
        }  catch (Exception ex) {    
            //记录本地异常日志    
            logger.error("==异常通知异常==");    
            logger.error("异常信息:{}", ex.getMessage());    
        }    
         /*==========记录本地异常日志==========*/    
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);    
    
    }    
    
    /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemControllerLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    } 
     /**  
      * 获取注解中对方法的描述信息 用于service层注解  
      *  
      * @param joinPoint 切点  
      * @return 方法描述  
      * @throws Exception  
      */    
      public  static String getServiceMthodDescription(JoinPoint joinPoint)    
              throws Exception {    
         String targetName = joinPoint.getTarget().getClass().getName();    
         String methodName = joinPoint.getSignature().getName();    
         Object[] arguments = joinPoint.getArgs();    
         Class targetClass = Class.forName(targetName);    
         Method[] methods = targetClass.getMethods();    
         String description = "";    
          for (Method method : methods) {    
              if (method.getName().equals(methodName)) {    
                 Class[] clazzs = method.getParameterTypes();    
                  if (clazzs.length == arguments.length) {    
                      description = method.getAnnotation(SystemServiceLog.class).description();    
                      break;    
                 }    
             }    
         }    
          return description;    
     }   
 
}
