package com.gec.system.annocation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface log {
    public String type() default "";
}
