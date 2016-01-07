/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.ui.config;

import com.speedment.util.OptionalBoolean;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableMap;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractDocumentProperty implements DocumentProperty {
    
    private final ObservableMap<String, Object> config;
    private final transient Map<String, Property<?>> properties;
    private final transient AtomicBoolean silenced;

    protected AbstractDocumentProperty(Map<String, Object> data) {
        this.config     = observableMap(data);
        this.properties = new ConcurrentHashMap<>();
        this.silenced   = new AtomicBoolean(false);
        
        this.config.addListener((MapChangeListener.Change<? extends String, ? extends Object> change) -> {
            if (!silenced.get()) {
                @SuppressWarnings("unchecked")
                final Property<Object> p = (Property<Object>) properties.get(change.getKey());

                if (p != null) {
                    if (change.wasAdded()) {
                        p.setValue(change.getValueAdded());
                    }
                }
            }
        });
    }

    @Override
    public final Map<String, Object> getData() {
        return config;
    }
    
    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(config.get(key));
    }
    
    @Override
    public OptionalBoolean getAsBoolean(String key) {
        return OptionalBoolean.ofNullable((Boolean) config.get(key));
    }

    @Override
    public OptionalLong getAsLong(String key) {
        final Long value = (Long) config.get(key);
        return value == null ? OptionalLong.empty() : OptionalLong.of(value);
    }

    @Override
    public OptionalDouble getAsDouble(String key) {
        final Double value = (Double) config.get(key);
        return value == null ? OptionalDouble.empty() : OptionalDouble.of(value);
    }

    @Override
    public OptionalInt getAsInt(String key) {
        final Integer value = (Integer) config.get(key);
        return value == null ? OptionalInt.empty() : OptionalInt.of(value);
    }
    
    @Override
    public Optional<String> getAsString(String key) {
        return get(key).map(String.class::cast);
    }

    @Override
    public void put(String key, Object value) {
        config.put(key, value);
    }
    
    @Override
    public final StringProperty stringPropertyOf(String key) {
        return (StringProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleStringProperty(getAsString(k).orElse(null))));
    }
    
    @Override
    public final IntegerProperty integerPropertyOf(String key) {
        return (IntegerProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleIntegerProperty(getAsInt(k).orElse(0))));
    }
    
    @Override
    public final LongProperty longPropertyOf(String key) {
        return (LongProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleLongProperty(getAsLong(k).orElse(0L))));
    }
    
    @Override
    public final DoubleProperty doublePropertyOf(String key) {
        return (DoubleProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleDoubleProperty(getAsDouble(k).orElse(0d))));
    }
    
    @Override
    public final BooleanProperty booleanPropertyOf(String key) {
        return (BooleanProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleBooleanProperty(getAsBoolean(k).orElse(false))));
    }
    
    @Override
    public final <T> ObjectProperty<T> objectPropertyOf(String key, Class<T> type) {
        return (ObjectProperty<T>) properties.computeIfAbsent(key, k -> prepare(k, new SimpleObjectProperty<>(type.cast(get(k).orElse(null)))));
    }
    
    private <T, P extends Property<T>> P prepare(String key, P property) {
        property.addListener((ObservableValue<? extends T> observable, T oldValue, T newValue) -> {
            synchronized(silenced) {
                silenced.set(true);
                config.put(key, newValue);
                silenced.set(false);
            }
        });
        
        return property;
    }
}