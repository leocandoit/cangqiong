package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充
 */
@Aspect //表示切面
@Component//表示是一个bean，交给springboot来管理
@Slf4j //方便记录日志
public class AutoFillAspect {
    //需要定义 1.切入点 2.通知
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)") //表示对哪些类的哪些方法进行拦截，应该拦截的是update insert的方法，别的不需要
    public void autoFillPointCut(){
    }

    /**
     * 定义前置通知
     * TODO
     */
    @Before("autoFillPointCut()") //定义在什么方法前
    public void autoFill(JoinPoint joinPoint){//joinPoint是什么？？
        log.info("开始测试公共字段自动填充");
        //先获取当前被拦截的方法的数据库操作类型，是枚举类型中哪个 ？？反射
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType operationType = autoFill.value();//获得数据库操作类型
        //获取被拦截方法的参数，实体对象
        Object[] args= joinPoint.getArgs();//获得了所有的参数
        if(args==null || args.length == 0){//防止空指针
            return;
        }
        Object obj = args[0];
        //为实体对象的属性来统一赋值，通过反射来赋值
        LocalDateTime localDateTime = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        if(operationType==OperationType.INSERT){
            //为4个字段赋值，通过反射赋值
            try {
                Method setCreateTime = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);//？？TODO
                Method setCreateUser = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateTime = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUser = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                //通过反射为对象属性赋值
                setCreateTime.invoke(obj,localDateTime);
                setCreateUser.invoke(obj,currentId);
                setUpdateTime.invoke(obj,localDateTime);
                setUpdateUser.invoke(obj,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(operationType==OperationType.UPDATE){
            //为2个字段赋值，通过反射赋值
            try {
                Method setUpdateTime = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUser = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                //通过反射为对象属性赋值
                setUpdateTime.invoke(obj,localDateTime);
                setUpdateUser.invoke(obj,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
