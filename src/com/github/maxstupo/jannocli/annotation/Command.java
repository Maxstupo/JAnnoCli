package com.github.maxstupo.jannocli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation for defining root commands and sub-commands.
 * 
 * @author Maxstupo
 */
@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /** The keyword of this command. Used within the generated help. */
    String value() default "";

    /**
     * If true this command will be hidden within the generated help. (Note: The command will still be visible within the "sub-commands" section for
     * specific command help.)
     */
    boolean hidden() default false;

    /** The aliases of this command. */
    String[] aliases() default "";

    /** The formal name of this command. Used within the generated help. */
    String name() default "";

    /** The description of this command. Used within the generated help. */
    String description() default "";

}
