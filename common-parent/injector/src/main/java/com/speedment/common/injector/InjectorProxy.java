package com.speedment.common.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * An InjectorProxy can be used to create/manipulate
 * instances on behalf of the actual injector. This can
 * be useful when using the Java Platform Module System
 * to keep down the number of required {@code exports}
 * in the module-info.java file.
 *
 * @author per
 * @since 3.2.0
 */
public interface InjectorProxy {

    /**
     * Returns if this InjectorProxy shall be used
     * with regards to the provided {@code clazz}.
     * <p>
     * This feature can be used to control which proxies
     * shall be used for which classes. E.g. one
     * InjectorProxy can be used to instantiate classes
     * from the domain system.
     *
     * @param clazz to check
     * @return if this InjectorProxy shall be used
     *         with regards to the provided {@code clazz}
     */
    boolean isApplicable(Class<?> clazz);

    /**
     * Sets the provided {@code field} in the provided
     * {@code instance} to the provided {@code value}.
     *
     * @param field to set
     * @param instance the instance whose {@code field} should be modified
     * @param value the new value for the {@code field} of {@code instance}
     *
     * @throws IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @throws IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @throws NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @throws ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @throws SecurityException if the request is denied by the security manager
     *
     * throws InaccessibleObjectException if access cannot be enabled
     */
    void set(Field field, Object instance, Object value) throws IllegalAccessException;

    /**
     * Uses the constructor represented by this {@code Constructor} object to
     * create and initialize a new instance of the constructor's
     * declaring class, with the specified initialization parameters.
     * Individual parameters are automatically unwrapped to match
     * primitive formal parameters, and both primitive and reference
     * parameters are subject to method invocation conversions as necessary.
     *
     * <p>If the number of formal parameters required by the underlying constructor
     * is 0, the supplied {@code initargs} array may be of length 0 or null.
     *
     * <p>If the constructor's declaring class is an inner class in a
     * non-static context, the first argument to the constructor needs
     * to be the enclosing instance; see section 15.9.3 of
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>If the required access and argument checks succeed and the
     * instantiation will proceed, the constructor's declaring class
     * is initialized if it has not already been initialized.
     *
     * <p>If the constructor completes normally, returns the newly
     * created and initialized instance.
     *
     * @param constructor to use when creating a new instance
     * @param initargs array of objects to be passed as arguments to
     * the constructor call; values of primitive types are wrapped in
     * a wrapper object of the appropriate type (e.g. a {@code float}
     * in a {@link Float Float})
     *
     * @return a new object created by calling the constructor
     * this object represents
     *
     * @exception IllegalAccessException    if this {@code Constructor} object
     *              is enforcing Java language access control and the underlying
     *              constructor is inaccessible.
     * @exception IllegalArgumentException  if the number of actual
     *              and formal parameters differ; if an unwrapping
     *              conversion for primitive arguments fails; or if,
     *              after possible unwrapping, a parameter value
     *              cannot be converted to the corresponding formal
     *              parameter type by a method invocation conversion; if
     *              this constructor pertains to an enum type.
     * @exception InstantiationException    if the class that declares the
     *              underlying constructor represents an abstract class.
     * @exception InvocationTargetException if the underlying constructor
     *              throws an exception.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     */
    <T> T newInstance(Constructor<T> constructor, Object... initargs)
        throws InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * Returns a {@code Predicate} that will evaluate to true iff
     * tested with a class that lies in the same package as the
     * provided {@code classInRootPackage} or a package below the
     * provided {@code classInRootPackage}.
     *
     * @param classInRootPackage as a reference to the root package
     * @return a {@code Predicate} that will evaluate to true iff
     *         tested with a class that lies in the same package as the
     *         provided {@code classInRootPackage} or a package below the
     *         provided {@code classInRootPackage}
     */
    static Predicate<? super Class<?>> samePackageOrBelow(Class<?> classInRootPackage) {
        requireNonNull(classInRootPackage);
        return samePackageOrBelow(classInRootPackage.getPackage().getName());
    }

    /**
     * Returns a {@code Predicate} that will evaluate to true iff
     * tested with a class that lies in the same package as the
     * provided {@code rootPackage} or a package below the
     * provided {@code rootPackage}.
     *
     * @param rootPackage as a reference to the root package
     * @return a {@code Predicate} that will evaluate to true iff
     *         tested with a class that lies in the same package as the
     *         provided {@code rootPackage} or a package below the
     *         provided {@code rootPackage}
     */
    static Predicate<? super Class<?>> samePackageOrBelow(String rootPackage) {
        requireNonNull(rootPackage);
        return c -> c.getName().startsWith(rootPackage);
    }

}
