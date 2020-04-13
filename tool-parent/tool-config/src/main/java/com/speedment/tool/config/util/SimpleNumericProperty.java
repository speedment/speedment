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
package com.speedment.tool.config.util;


import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Emil Forslund
 */
public final class SimpleNumericProperty implements NumericProperty {
    
    private final Property<Number> wrapped;
    
    public SimpleNumericProperty() {
        this.wrapped = new SimpleObjectProperty<>();
    }
    
    public SimpleNumericProperty(Number initialValue) {
        this.wrapped = new SimpleObjectProperty<>(initialValue);
    }

    @Override
    public IntegerProperty asIntegerProperty() {
        final IntegerProperty property = new SimpleIntegerProperty();
        property.bindBidirectional(this);
        return property;
    }

    @Override
    public DoubleProperty asDoubleProperty() {
        final DoubleProperty property = new SimpleDoubleProperty();
        property.bindBidirectional(this);
        return property;
    }

    @Override
    public LongProperty asLongProperty() {
        final LongProperty property = new SimpleLongProperty();
        property.bindBidirectional(this);
        return property;
    }

    @Override
    public FloatProperty asFloatProperty() {
        final FloatProperty property = new SimpleFloatProperty();
        property.bindBidirectional(this);
        return property;
    }

    @Override
    public void bind(ObservableValue<? extends Number> observable) {
        wrapped.bind(observable);
    }

    @Override
    public void unbind() {
        wrapped.unbind();
    }

    @Override
    public boolean isBound() {
        return wrapped.isBound();
    }

    @Override
    public void bindBidirectional(Property<Number> other) {
        wrapped.bindBidirectional(other);
    }

    @Override
    public void unbindBidirectional(Property<Number> other) {
        wrapped.unbindBidirectional(other);
    }

    @Override
    public Object getBean() {
        return wrapped.getBean();
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        wrapped.addListener(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        wrapped.removeListener(listener);
    }

    @Override
    public Number getValue() {
        return wrapped.getValue();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        wrapped.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        wrapped.removeListener(listener);
    }

    @Override
    public void setValue(Number value) {
        wrapped.setValue(value);
    }
}