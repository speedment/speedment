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
package com.speedment.common.codegen.constant;


import java.lang.reflect.Type;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Constant implementations of the {@link Type} interface that can be used to
 * reference standard java types. If a modifying method is called on any of
 * these objects, the model will be copied before performing the operation.
 * This makes sure the original state of the constant is never changed.
 * 
 * @author Emil Forslund
 */
public final class DefaultType {

    private DefaultType() {}

    public static final Type WILDCARD = SimpleType.create("?");

    /**
     * Returns a new {@link SimpleParameterizedType} with the specified type
     * parameters.
     *
     * @param type            the base type
     * @param typeParameters  all its type parameters
     * @return                the new generic type
     *
     * @since 2.5
     */
    public static Type genericType(Type type, Type... typeParameters) {
        return SimpleParameterizedType.create(type, typeParameters);
    }

    /**
     * Returns a new {@link SimpleParameterizedType} with the specified type
     * parameters.
     *
     * @param type            the base type
     * @param typeParameters  all its type parameters
     * @return                the new generic type
     *
     * @since 2.5
     */
    public static Type genericType(Type type, String... typeParameters) {
        return SimpleParameterizedType.create(type,
            Stream.of(typeParameters).map(SimpleType::create).toArray(Type[]::new));
    }

    /**
     * Generates a {@link Type} to represent a java standard {@link Class} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type classOf(Type innerType) {
        return SimpleParameterizedType.create(Class.class, innerType);
	}
      
    /**
     * Generates a {@link Type} to represent a java standard {@link List} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type list(Type innerType) {
		return SimpleParameterizedType.create(List.class, innerType);
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Set} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
	public static Type set(Type innerType) {
		return SimpleParameterizedType.create(Set.class, innerType);
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Map} with
     * generic type variables.
     * 
     * @param innerTypeA  the first type variable
     * @param innerTypeB  the second type variable
     * @return            the resulting type
     */
	public static Type map(Type innerTypeA, Type innerTypeB) {
		return SimpleParameterizedType.create(Map.class, innerTypeA, innerTypeB);
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Queue} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
	public static Type queue(Type innerType) {
		return SimpleParameterizedType.create(Queue.class, innerType);
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Stack} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type stack(Type innerType) {
        return SimpleParameterizedType.create(Stack.class, innerType);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Optional} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type optional(Type innerType) {
        return SimpleParameterizedType.create(Optional.class, innerType);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard 
     * {@link java.util.HashMap.Entry Entry} with generic type variables.
     * 
     * @param innerTypeA  the first type variable
     * @param innerTypeB  the second type variable
     * @return           the resulting type
     */
    public static Type entry(Type innerTypeA, Type innerTypeB) {
        return SimpleParameterizedType.create(Map.Entry.class, innerTypeA, innerTypeB);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Function} 
     * with generic type variables.
     * 
     * @param innerTypeA  the first type variable
     * @param innerTypeB  the second type variable
     * @return            the resulting type
     */
    public static Type function(Type innerTypeA, Type innerTypeB) {
        return SimpleParameterizedType.create(Function.class, innerTypeA, innerTypeB);
    }

    /**
     * Generates a {@link Type} to represent a java standard {@link BiFunction}
     * with generic type variables.
     *
     * @param innerTypeA  the first type variable
     * @param innerTypeB  the second type variable
     * @param innerTypeC  the third type variable
     * @return            the resulting type
     *
     * @since 2.5
     */
    public static Type bifunction(Type innerTypeA, Type innerTypeB, Type innerTypeC) {
        return SimpleParameterizedType.create(BiFunction.class, innerTypeA, innerTypeB, innerTypeC);
    }

    /**
     * Generates a {@link Type} to represent a java standard {@link IntFunction}
     * with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type intFunction(Type innerType) {
        return SimpleParameterizedType.create(IntFunction.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link LongFunction} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type longFunction(Type innerType) {
        return SimpleParameterizedType.create(LongFunction.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link DoubleFunction} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type doubleFunction(Type innerType) {
        return SimpleParameterizedType.create(DoubleFunction.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link ToIntFunction} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type toIntFunction(Type innerType) {
        return SimpleParameterizedType.create(ToIntFunction.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link ToLongFunction} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type toLongFunction(Type innerType) {
        return SimpleParameterizedType.create(ToLongFunction.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link ToDoubleFunction} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type toDoubleFunction(Type innerType) {
        return SimpleParameterizedType.create(ToDoubleFunction.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link UnaryOperator} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type unaryOperator(Type innerType) {
        return SimpleParameterizedType.create(UnaryOperator.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard
     * {@link BinaryOperator} with generic type variables.
     *
     * @param innerType  the inner type
     * @return           the resulting type
     *
     * @since 2.5
     */
    public static Type binaryOperator(Type innerType) {
        return SimpleParameterizedType.create(BinaryOperator.class, innerType);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Predicate} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type predicate(Type innerType) {
        return SimpleParameterizedType.create(Predicate.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard {@link BiPredicate}
     * with a generic type variable.
     *
     * @param innerTypeA  the first type variable
     * @param innerTypeB  the second type variable
     * @return            the resulting type
     *
     * @since 2.5
     */
    public static Type bipredicate(Type innerTypeA, Type innerTypeB) {
        return SimpleParameterizedType.create(BiPredicate.class, innerTypeA, innerTypeB);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Consumer} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type consumer(Type innerType) {
        return SimpleParameterizedType.create(Consumer.class, innerType);
    }

    /**
     * Generates a {@link Type} to represent a java standard {@link Consumer}
     * with a generic type variable.
     *
     * @param innerTypeA  the first type variable
     * @param innerTypeB  the second type variable
     * @return            the resulting type
     *
     * @since 2.5
     */
    public static Type biconsumer(Type innerTypeA, Type innerTypeB) {
        return SimpleParameterizedType.create(BiConsumer.class, innerTypeA, innerTypeB);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Supplier} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type supplier(Type innerType) {
        return SimpleParameterizedType.create(Supplier.class, innerType);
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Stream} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type stream(Type innerType) {
        return SimpleParameterizedType.create(Stream.class, innerType);
    }
    
    /**
     * Returns {@code true} if the specified type is a primitive type. Wrapper
     * types does <em>not</em> count as primitives.
     * 
     * @param type  the type to check
     * @return      {@code true} if it is primitive, else {@code false}
     */
    public static boolean isPrimitive(Type type) {
        return WRAPPERS.containsKey(type.getTypeName());
    }

    /**
     * Returns {@code true} if the specified type is a wrapper type, else
     * {@code false}.
     *
     * @param type  the type to check
     * @return      {@code true} if it is a wrapper, else {@code false}
     *
     * @since 2.5
     */
    public static boolean isWrapper(Type type) {
        return WRAPPERS.values().stream()
            .map(Class::getTypeName)
            .anyMatch(type.getTypeName()::equals);
    }
    
    /**
     * Returns the corresponding wrapper type for the specified primitive type.
     * If the type is not primitive, an exception is thrown.
     * 
     * @param primitiveType  the primitive type
     * @return               the corresponding wrapper type
     */
    public static Class<?> wrapperFor(Type primitiveType) {
        return requireNonNull(WRAPPERS.get(primitiveType.getTypeName()),
            "No wrapper found for type '" + primitiveType.getTypeName() + "'."
        );
    }
    
    /**
     * Returns a stream of all the primitive types in the java language.
     * 
     * @return  stream of types
     */
    public static Stream<Type> primitiveTypes() {
        return WRAPPERS.keySet().stream().map(SimpleType::create);
    }
    
    /**
     * Returns a stream of all the wrapper types in the java language.
     * 
     * @return  stream of types
     */
    public static Stream<Type> wrapperTypes() {
        return WRAPPERS.values().stream().map(Type.class::cast);
    }
    
    private static final Map<String, Class<?>> WRAPPERS;
    static {
        final Map<String, Class<?>> temp = new HashMap<>();
        
        temp.put("byte",    Byte.class);
        temp.put("short",   Short.class);
        temp.put("int",     Integer.class);
        temp.put("long",    Long.class);
        temp.put("float",   Float.class);
        temp.put("double",  Double.class);
        temp.put("boolean", Boolean.class);
        temp.put("char", Character.class);
        
        WRAPPERS = Collections.unmodifiableMap(temp);
    }
    

}