package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import org.aspectj.lang.reflect.MethodSignature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    //切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFillBefore(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("AutoFillPointCut start now!!!");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//方法上的注解对象
        OperationType operationType=autoFill.value();//数据库操作类型
        Object[] args = joinPoint.getArgs();
        Object entity=args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentid= BaseContext.getCurrentId();
        if(operationType==OperationType.INSERT){
            Method setCreateTime=entity.getClass().getDeclaredMethod("setCreateTime",LocalDateTime.class);
            Method setCreateUser=entity.getClass().getDeclaredMethod("setCreateUser",Long.class);
            Method setUpdateTime=entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
            Method setUpdateUser=entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);

            setCreateTime.invoke(entity,now);
            setCreateUser.invoke(entity,currentid);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentid);
        }else if(operationType==OperationType.UPDATE){
            Method setUpdateTime=entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
            Method setUpdateUser=entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);
            setUpdateUser.invoke(entity,currentid);
            setUpdateTime.invoke(entity,now);
        }
    }
}
