package com.athene.api.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fe on 16/9/18.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiGroup {

    public String description();

    public String owner();

    public boolean needAuth() default  true;

    public String version() default "1.0.0";

    public String group() default "";

}
