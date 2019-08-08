package com.speedment.common.injector.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a field that should be set automatically using dependency
 * injection, and if no implementations of the field type are available, set it
 * to {@code null}.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectOrNull {}