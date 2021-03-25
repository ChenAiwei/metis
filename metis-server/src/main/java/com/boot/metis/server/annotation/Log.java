package com.boot.metis.server.annotation;

import com.boot.metis.common.enums.LogEnum;

import java.lang.annotation.*;

/**
 * @Author：chenaiwei
 * @Description：Log
 * @CreateDate：2020/7/13 9:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Log {
	String option() default "";
	LogEnum type();
}
