package com.wh.core.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//作用在类上面
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewId {
    int value();
}
