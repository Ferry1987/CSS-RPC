package com.css.com.css.rpc.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceContainer {
    public String value() default "";
}
