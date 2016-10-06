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

import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Value;

import javax.annotation.Generated;
import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.speedment.common.codegen.constant.DefaultValue.string;
import static java.util.Objects.requireNonNull;

/**
 * An enumeration with default constants for the {@link AnnotationUsage}
 * interface. If any of the setter methods are called, the model will be cloned
 * into a non-enum value before the value is changed in the clone. This makes
 * it possible to safely set values in the constants without changing their
 * actual state.
 * 
 * @author Emil Forslund
 */
public enum DefaultAnnotationUsage implements AnnotationUsage {
    
    OVERRIDE    (Override.class),
    DOCUMENTED  (Documented.class),
    INHERITED   (Inherited.class),
    NATIVE      (Native.class),
    REPEATABLE  (Repeatable.class),
    RETENTION   (Retention.class),
    TARGET      (Target.class),
    GENERATED   (Generated.class),
    DEPRECATED  (Deprecated.class),
    SUPPRESS_WARNINGS_UNCHECKED (
        SuppressWarnings.class, string("unchecked")
    );
    
    private final Type type;
	private final Value<?> value;
	
    /**
     * Constructs the AnnotationUsage based on a {@link Type}.
     * 
     * @param type  the type
     */
	DefaultAnnotationUsage(Type type) {
		this (type, null);
	}
    
    /**
     * Constructs the AnnotationUsage based on a {@link Type} and a {@link Value}.
     * 
     * @param type  the type
     */
    DefaultAnnotationUsage(Type type, Value<?> value) {
		this.type	= requireNonNull(type);
		this.value	= value;
	}
    
    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
	public AnnotationUsage set(Type type) {
		return copy().set(requireNonNull(type));
	}
	
    /**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
	@Override
	public AnnotationUsage set(Value<?> val) {
		return copy().set(val);
	}
    
	/**
     * {@inheritDoc}
     * <p>
     * Since this is a constant, the model will first be copied and the
     * operation will then be performed on the copy.
     */
    @Override
	public AnnotationUsage put(String key, Value<?> val) {
		return copy().put(requireNonNull(key), val);
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
	public Type getType() {
		return type;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Value<?>> getValue() {
		return Optional.ofNullable(value).map(Value::copy);
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public List<Map.Entry<String, Value<?>>> getValues() {
		return new ArrayList<>();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public AnnotationUsage copy() {
        final AnnotationUsage copy = AnnotationUsage.of(getType());
        getValue().ifPresent(copy::set);
        return copy;
	}
}