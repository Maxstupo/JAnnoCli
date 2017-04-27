package com.github.maxstupo.jannocli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Maxstupo
 *
 */
@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String value() default "";

    String[] aliases() default "";

    String name() default "";

    String description() default "";

}
