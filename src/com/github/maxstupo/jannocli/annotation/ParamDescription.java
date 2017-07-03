package com.github.maxstupo.jannocli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows for parameters to have descriptions.
 * 
 * @author Maxstupo
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamDescription {

    /** An array of parameter descriptions in a sequential order. */
    String[] value();

}
