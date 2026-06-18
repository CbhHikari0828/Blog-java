package com.alex.weblog.common.aspect;

import com.alex.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;



@Aspect
@Slf4j
@Component
public class ApiOperationLogAspect {


    @Pointcut("@annotation(com.alex.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog(){}


    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            MDC.put("traceId", UUID.randomUUID().toString());
            // 请求开始时间
            long startTime = System.currentTimeMillis();

            // 获取类名方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            // 获取请求入参
            Object[] args = joinPoint.getArgs();
            // Json 转 String
            String argsJsonStr = Arrays.stream(args).map(JsonUtil::toJsonString).collect(Collectors.joining(","));
            // 功能性描述
            String description = getApiOperationLogDescription(joinPoint);

            // 打印请求相关参数
            log.info("====== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} =================================== ",
                    description, argsJsonStr, className, methodName);
            // 执行切点方法
            Object result = joinPoint.proceed();
            // 执行耗时
            long executionTime = System.currentTimeMillis() - startTime;
            // 打印出参等相关信息
            log.info("====== 请求结束: [{}], 耗时: {}ms, 出参: {} =================================== ",
                    description, executionTime, JsonUtil.toJsonString(result));
            return result;

        }finally {
            MDC.clear();
        }
    }


    /**
     * 获取注解描述信息
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        // 1.获取 Signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 2.获取被注解的 Method
        Method method = signature.getMethod();
        // 3.获取 ApiOperationLog
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);
        // 4.获取返回 Description
        return apiOperationLog.description();
    }
}
