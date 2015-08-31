/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.codegen.lang.models.constants;

import com.speedment.internal.codegen.lang.models.AnnotationUsage;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.implementation.GenericImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Constant implementations of the {@link Type} interface that can be used to
 * reference standard java types. If a modifying method is called on any of
 * these objects, the model will be copied before performing the operation.
 * This makes sure the original state of the constant is never changed.
 * 
 * @author Emil Forslund
 */
public enum DefaultType implements Type {

		BYTE_PRIMITIVE(byte.class),
		SHORT_PRIMITIVE(short.class),
		INT_PRIMITIVE(int.class),
		LONG_PRIMITIVE(long.class),
		FLOAT_PRIMITIVE(float.class),
		DOUBLE_PRIMITIVE(double.class),
		BOOLEAN_PRIMITIVE(boolean.class),
		CHAR_PRIMITIVE(char.class),
		BYTE(Byte.class),
		SHORT(Short.class),
		INT(Integer.class),
		LONG(Long.class),
		FLOAT(Float.class),
		DOUBLE(Double.class),
		BOOLEAN(Boolean.class),
		CHARACTER(Character.class),
		STRING(String.class),
		OBJECT(Object.class),
		VOID("void"),
		WILDCARD("?"),
		LIST(List.class),
		SET(Set.class),
		MAP(Map.class),
		QUEUE(Queue.class),
		STACK(Stack.class),
		OPTIONAL(Optional.class),
		ENTRY(HashMap.Entry.class),
		FUNCTION(Function.class),
		PREDICATE(Predicate.class),
		CONSUMER(Consumer.class),
        SUPPLIER(Supplier.class);
      
    /**
     * Generates a {@link Type} to represent a java standard {@link List} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type list(Type innerType) {
		return LIST.add(new GenericImpl().add(requireNonNull(innerType)));
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Set} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
	public static Type set(Type innerType) {
		return SET.add(new GenericImpl().add(requireNonNull(innerType)));
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
		return MAP.add(new GenericImpl().add(requireNonNull(innerTypeA))).add(new GenericImpl(requireNonNull(innerTypeB)));
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Queue} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
	public static Type queue(Type innerType) {
		return QUEUE.add(new GenericImpl().add(requireNonNull(innerType)));
	}
	
    /**
     * Generates a {@link Type} to represent a java standard {@link Stack} with
     * a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type stack(Type innerType) {
        return STACK.add(new GenericImpl().add(requireNonNull(innerType)));
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Optional} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type optional(Type innerType) {
        return OPTIONAL.add(new GenericImpl().add(requireNonNull(innerType)));
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
        return ENTRY.add(new GenericImpl().add(requireNonNull(innerTypeA)).add(requireNonNull(innerTypeB)));
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
        return FUNCTION.add(new GenericImpl().add(requireNonNull(innerTypeA)).add(requireNonNull(innerTypeB)));
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Predicate} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type predicate(Type innerType) {
        return PREDICATE.add(new GenericImpl().add(requireNonNull(innerType)));
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Consumer} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type consumer(Type innerType) {
        return CONSUMER.add(new GenericImpl().add(requireNonNull(innerType)));
    }
    
    /**
     * Generates a {@link Type} to represent a java standard {@link Supplier} 
     * with a generic type variable.
     * 
     * @param innerType  the type variable
     * @return           the resulting type
     */
    public static Type supplier(Type innerType) {
        return SUPPLIER.add(new GenericImpl().add(requireNonNull(innerType)));
    }
    
    /**
     * Checks if the specified {@link Type} represents a <code>void</code> 
     * return type.
     * 
     * @param type  the type to check
     * @return      <code>true</code> if the type is void, else <code>false</code>
     */
    public static boolean isVoid(Type type) {
        return type == null 
            || VOID.equals(type) 
            || "void".equals(type.getName());
    }
        
    private final Class<?> javaImpl;
    private final String typeName;
    
    /**
     * Constructs the Type based on the type name.
     * 
     * @param typeName  the name
     */
    private DefaultType(String typeName) {
        this.javaImpl = null;
        this.typeName = typeName;
    }
    
    /**
     * Constructs the Type based on the java implementation class. The type name
     * will be calculated using {@link Class#getName()}.
     * 
     * @param typeName  the name
     */
    private DefaultType(Class<?> javaImpl) {
        this.javaImpl = javaImpl;
        this.typeName = javaImpl.getName();
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
    public Type setName(String name) {
        return copy().setName(requireNonNull(name));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
    public Type setJavaImpl(Class<?> javaImpl) {
        return copy().setJavaImpl(javaImpl);
    }
    
    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
    public Type setArrayDimension(int arrayDimension) {
        return copy().setArrayDimension(arrayDimension);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
    public Type add(Generic generic) {
        return copy().add(requireNonNull(generic));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
    public Type add(AnnotationUsage annotation) {
        return copy().add(requireNonNull(annotation));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return typeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Class<?>> getJavaImpl() {
        return Optional.ofNullable(javaImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getArrayDimension() {
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Generic> getGenerics() {
        return new ArrayList<>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnnotationUsage> getAnnotations() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type copy() {
        if (javaImpl == null) {
            return Type.of(typeName);
        } else {
            return Type.of(javaImpl);
        }
    }
}