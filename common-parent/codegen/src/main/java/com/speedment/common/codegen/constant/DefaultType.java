/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
    
    public static Type WILDCARD = SimpleType.create("?");
        
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
        return WRAPPERS.keySet().contains(type.getTypeName());
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
    
    private final static Map<String, Class<?>> WRAPPERS;
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
    
    /**
     * Utility classes should never be instantiated.
     */
    private DefaultType() {
        throw new RuntimeException(
            "This class should never be instantiated."
        );
    }
}