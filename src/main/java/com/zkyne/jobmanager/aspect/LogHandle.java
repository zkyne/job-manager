package com.zkyne.jobmanager.aspect;

import java.lang.annotation.*;

/**
 * @ClassName: LogHandle
 * @Description:
 * @Author: zkyne
 * @Date: 2018/6/22 11:15
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogHandle {
    String description() default "";
}
