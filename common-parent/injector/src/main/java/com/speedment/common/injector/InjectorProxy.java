/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * @param <T> type of object to create
     * @param constructor to use when creating a new instance
     * @param args array of objects to be passed as arguments to
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
    <T> T newInstance(Constructor<T> constructor, Object... args)
        throws InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * Invokes the underlying method represented by this {@code Method}
     * object, on the specified object with the specified parameters.
     * Individual parameters are automatically unwrapped to match
     * primitive formal parameters, and both primitive and reference
     * parameters are subject to method invocation conversions as
     * necessary.
     *
     * <p>If the underlying method is static, then the specified {@code obj}
     * argument is ignored. It may be null.
     *
     * <p>If the number of formal parameters required by the underlying method is
     * 0, the supplied {@code args} array may be of length 0 or null.
     *
     * <p>If the underlying method is an instance method, it is invoked
     * using dynamic method lookup as documented in The Java Language
     * Specification, section 15.12.4.4; in particular,
     * overriding based on the runtime type of the target object may occur.
     *
     * <p>If the underlying method is static, the class that declared
     * the method is initialized if it has not already been initialized.
     *
     * <p>If the method completes normally, the value it returns is
     * returned to the caller of invoke; if the value has a primitive
     * type, it is first appropriately wrapped in an object. However,
     * if the value has the type of an array of a primitive type, the
     * elements of the array are <i>not</i> wrapped in objects; in
     * other words, an array of primitive type is returned.  If the
     * underlying method return type is void, the invocation returns
     * null.
     *
     * @param method the method to invoke
     * @param obj  the object the underlying method is invoked from
     * @param args the arguments used for the method call
     * @return the result of dispatching the method represented by
     * this object on {@code obj} with parameters
     * {@code args}
     *
     * @exception IllegalAccessException    if this {@code Method} object
     *              is enforcing Java language access control and the underlying
     *              method is inaccessible.
     * @exception IllegalArgumentException  if the method is an
     *              instance method and the specified object argument
     *              is not an instance of the class or interface
     *              declaring the underlying method (or of a subclass
     *              or implementor thereof); if the number of actual
     *              and formal parameters differ; if an unwrapping
     *              conversion for primitive arguments fails; or if,
     *              after possible unwrapping, a parameter value
     *              cannot be converted to the corresponding formal
     *              parameter type by a method invocation conversion.
     * @exception InvocationTargetException if the underlying method
     *              throws an exception.
     * @exception NullPointerException      if the specified object is null
     *              and the method is an instance method.
     * @exception ExceptionInInitializerError if the initialization
     * provoked by this method fails.
     */
    Object invoke(Method method, Object obj, Object... args) throws IllegalAccessException, InvocationTargetException;

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
        return samePackageOrBelow(classInRootPackage.getPackage().getName(), false);
    }

    /**
     * Returns a {@code Predicate} that will evaluate to true iff
     * tested with a class that lies in the same package as the
     * provided {@code classInRootPackage} or a package below the
     * provided {@code classInRootPackage}.
     * <p>
     * If the provided {@code excludeInternalPackages}  is true, then package
     * names that contain the word ".internal." will not be deemed
     * to be applicable.
     *
     * @param classInRootPackage as a reference to the root package
     * @param excludeInternalPackages if internal packages shall be excluded
     * @return a {@code Predicate} that will evaluate to true iff
     *         tested with a class that lies in the same package as the
     *         provided {@code classInRootPackage} or a package below the
     *         provided {@code classInRootPackage}
     */
    static Predicate<? super Class<?>> samePackageOrBelow(Class<?> classInRootPackage, boolean excludeInternalPackages) {
        requireNonNull(classInRootPackage);
        return samePackageOrBelow(classInRootPackage.getPackage().getName(), excludeInternalPackages);
    }

    /**
     * Returns a {@code Predicate} that will evaluate to true iff
     * tested with a class that lies in the same package as the
     * provided {@code rootPackage} or a package below the
     * provided {@code rootPackage}.
     * <p>
     * If the provided {@code excludeInternalPackages}  is true, then package
     * names that contain the word ".internal." will not be deemed
     * to be applicable.
     *
     * @param rootPackage as a reference to the root package
     * @param excludeInternalPackages if internal packages shall be excluded
     * @return a {@code Predicate} that will evaluate to true iff
     *         tested with a class that lies in the same package as the
     *         provided {@code rootPackage} or a package below the
     *         provided {@code rootPackage}
     */
    static Predicate<? super Class<?>> samePackageOrBelow(String rootPackage, boolean excludeInternalPackages) {
        requireNonNull(rootPackage);
        return clazz -> {
            final String className = clazz.getName();
            if (excludeInternalPackages && className.contains(".internal.")) {
                return false;
            }
            return className.startsWith(rootPackage);
        };
    }

}
