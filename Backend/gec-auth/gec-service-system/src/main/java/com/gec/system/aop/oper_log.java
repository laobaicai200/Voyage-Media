package com.gec.system.aop;

import com.gec.model.system.SysOperLog;
import com.gec.system.annocation.log;
import com.gec.system.service.SysOperLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class oper_log {

    private SysOperLog sysOperLog = new SysOperLog();

    @Autowired
    private SysOperLogService sysOperLogService;

    @Pointcut("@annotation(com.gec.system.annocation.log)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("切面执行了");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        // 获取方法
        Method method = methodSignature.getMethod();
        if (method != null) {
            log logAnnotation = method.getAnnotation(log.class);
            if (logAnnotation != null) {
                sysOperLog.setOperatorType(logAnnotation.type());
                sysOperLog.setTitle(logAnnotation.type());
            }
        }

        // 获取方法名称
        String methodName = signature.getName();
        System.out.println(methodName);

        // 通过工具类获取request请求信息
        RequestAttributes atr = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) atr;
        HttpServletRequest request = attributes.getRequest();

        // 获取URL
        String url = request.getRequestURI().toString();
        System.out.println(url);

        // 获取IP
        String ip = request.getRemoteAddr();
        // 获取当前用户
        String username = getUsernameFromSecurityContext();
        sysOperLog.setOperName(username);
        sysOperLog.setDeptName(username);
        // 操作时间
        Date date = new Date();
        System.out.println(date);

        // 写入数据库
        sysOperLog.setOperIp(ip);
        sysOperLog.setOperUrl(url);
        sysOperLog.setOperTime(date);
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setMethod(methodName);
        sysOperLog.setStatus(0);
        sysOperLogService.save(sysOperLog);
        // 继续执行目标方法
        return joinPoint.proceed();
    }

    private String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
