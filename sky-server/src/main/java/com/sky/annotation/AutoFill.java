package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于字段自动填充
 */
@Target(ElementType.METHOD)//这个注解表示这个注解只能加在方法上面
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Retention 元注解：指定注解的生命周期（保留策略）
 * RetentionPolicy有三个取值：
 * 1. SOURCE：注解仅保留在源代码中，编译时被丢弃（如@Override）
 * 2. CLASS：注解保留到class文件中，但JVM运行时不保留（默认策略）
 * 3. RUNTIME：注解保留到运行时，可以通过反射机制读取（这里使用的就是这个）
 * 
 * 这里使用RUNTIME是因为：
 * 我们需要在程序运行时通过反射获取该注解的信息，从而实现AOP切面的动态处理
 */
public @interface AutoFill {
    //指定数据库操作类型，因为公共字段只有update insert
    OperationType value();
}
