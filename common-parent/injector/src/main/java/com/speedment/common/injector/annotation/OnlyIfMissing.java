package com.speedment.common.injector.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a secondary constructor that should be used when the dependency
 * injector creates instances if and only if all the classes specified as its
 * value lacks implementations in the context of the injector. It is not enough
 * that the types listed have not been created yet, or that they have not yet
 * reached a particular state. They must be completely unspecified for the
 * annotated constructor to be used.
 * <p>
 * Note that this annotation is not a replacement for the {@link Inject}
 * annotation, but rather a complement.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
@Inherited
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyIfMissing {

    /**
     * Only if <em>all</em> of the classes given by this field are missing
     * should the annotated constructor be used to create an instance of the
     * class.
     *
     * @return  all the classes that must be missing for the annotated
     *          constructor to be invoked
     */
    Class<?>[] value();

}
