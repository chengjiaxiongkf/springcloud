package com.common.beanconfig;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/13 21:52
 */
@Slf4j
@Aspect
@Component
public class AopAllMethodConfig {
    @Pointcut("execution(* com.*.controller.*Controller.*(..))")//切入点描述 这个是controller包的切入点
    public void controllerLog(){}//签名，可以理解成这个切入点的一个名称

    /**
     * 所有controller方法【执行前】切入一些日志打印
     * @param joinPoint
     */
    @Before("controllerLog()") //在切入点的方法run之前要干的
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("AOP Before........");
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        // 记录下请求内容
        log.info("################URL : " + request.getRequestURL().toString());
        log.info("################HTTP_METHOD : " + request.getMethod());
        log.info("################IP : " + request.getRemoteAddr());
        log.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //log.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        //log.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    }

    /**
     * 所有controller方法【执行后】切入一些日志打印
     * @param joinPoint
     */
    @After("controllerLog()") //在切入点的方法run之前要干的
    public void logAfterController(JoinPoint joinPoint) {
        log.info("AOP After........");
    }

    /**
     * 所有controller方法【环绕切入】一些日志打印
     * @param proceedingJoinPoint
     */
    @Around("controllerLog()") //在切入点的方法run之前要干的
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        log.info("AOP Around After........");
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("AOP Around After........");
        return object;
    }

    /**
     * 所有controller方法【正常执行返回后】一些日志打印
     * @param joinPoint
     * @param returnOb
     */
    @AfterReturning(returning = "returnOb",pointcut = "controllerLog()") //在切入点的方法run之前要干的
    public void logAfterReturningController(JoinPoint joinPoint,Object returnOb) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        log.info("AOP AfterReturning........");
        log.info("返回参数:"+returnOb);
    }

    /**
     * 所有controller方法【抛出异常后】一些日志打印
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(throwing = "ex",pointcut = "controllerLog()") //在切入点的方法run之前要干的
    public void logAfterThrowingController(JoinPoint joinPoint,Exception ex) {
        log.info("AOP AfterThrowing........");
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);
    }
}
