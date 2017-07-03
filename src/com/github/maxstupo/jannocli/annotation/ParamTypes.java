package com.github.maxstupo.jannocli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows for parameters to have defined types.
 * 
 * @author Maxstupo
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamTypes {

    /**
     * An array of parameter types in a sequential order. (Supported types are {@link Integer}, {@link Float}, {@link Long}, {@link Boolean},
     * {@link String} and enums)
     */
    Class<?>[] value();
}
