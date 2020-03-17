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
package com.speedment.tool.config;

import com.speedment.common.function.FloatSupplier;
import com.speedment.common.function.OptionalBoolean;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.Document;
import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.util.NumericProperty;
import com.speedment.tool.config.util.SimpleNumericProperty;
import static java.util.Collections.newSetFromMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.*;
import java.util.stream.Stream;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import static javafx.collections.FXCollections.observableList;
import static javafx.collections.FXCollections.observableMap;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @param <THIS>  the type of the implementing class
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public abstract class AbstractDocumentProperty<THIS extends AbstractDocumentProperty<? super THIS>> 
    implements DocumentProperty {
 
    private final Map<String, Object> config;
    private final ObservableMap<String, Property<?>> properties;
    private final ObservableMap<String, ObservableList<AbstractDocumentProperty<?>>> children;
    
    /**
     * Invalidation listeners required by the {@code Observable} interface.
     */
    private final Set<InvalidationListener> listeners;
    
    protected AbstractDocumentProperty() {
        this.config     = new ConcurrentHashMap<>();
        this.properties = observableMap(new ConcurrentHashMap<>());
        this.children   = observableMap(new ConcurrentHashMap<>());
        this.listeners  = newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public final Map<String, Object> getData() {
        return config;
    }

    /**
     * {@inheritDoc}
     * @deprecated Not supported for observables
     */
    @Override
    @Deprecated // Deprecated to use but will not be removed from the API
    public final void put(String key, Object val) {
        throw new UnsupportedOperationException(
            "Observable config documents does not support the put()-operation " +
            "directly. Instead you should request the appropriate property or " +
            "observable list for the specific key and modify it."
        );
    }

    @Override
    public final Optional<Object> get(String key) {
        @SuppressWarnings("unchecked")
        final Property<Object> prop = (Property<Object>) properties.get(key);
        if (prop == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(prop.getValue());
        }
    }

    @Override
    public final OptionalBoolean getAsBoolean(String key) {
        final BooleanProperty prop = (BooleanProperty) properties.get(key);
        if (prop == null) {
            return OptionalBoolean.empty();
        } else {
            return OptionalBoolean.ofNullable(prop.getValue());
        }
    }

    @Override
    public final OptionalLong getAsLong(String key) {
        final LongProperty prop = (LongProperty) properties.get(key);
        if (prop == null) {
            return OptionalLong.empty();
        } else {
            return OptionalUtil.ofNullable(prop.getValue());
        }
    }

    @Override
    public final OptionalDouble getAsDouble(String key) {
        final DoubleProperty prop = (DoubleProperty) properties.get(key);
        if (prop == null) {
            return OptionalDouble.empty();
        } else {
            return OptionalUtil.ofNullable(prop.getValue());
        }
    }

    @Override
    public final OptionalInt getAsInt(String key) {
        final IntegerProperty prop = (IntegerProperty) properties.get(key);
        if (prop == null) {
            return OptionalInt.empty();
        } else {
            return OptionalUtil.ofNullable(prop.getValue());
        }
    }

    @Override
    public final Optional<String> getAsString(String key) {
        final StringProperty prop = (StringProperty) properties.get(key);
        if (prop == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(prop.getValue());
        }
    }
    
    @Override
    public final StringProperty stringPropertyOf(String key, Supplier<String> ifEmpty) {
        return (StringProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleStringProperty(), ifEmpty.get()));
    }
    
    @Override
    public final BooleanProperty booleanPropertyOf(String key, BooleanSupplier ifEmpty) {
        return (BooleanProperty) properties.computeIfAbsent(key, k -> prepare(k, new SimpleBooleanProperty(), ifEmpty.getAsBoolean()));
    }
    
    @Override
    public final IntegerProperty integerPropertyOf(String key, IntSupplier ifEmpty) {
        return numericPropertyOf(key, ifEmpty::getAsInt, NumericProperty::asIntegerProperty);
    }

    @Override
    public final LongProperty longPropertyOf(String key, LongSupplier ifEmpty) {
        return numericPropertyOf(key, ifEmpty::getAsLong, NumericProperty::asLongProperty);
    }

    @Override
    public final DoubleProperty doublePropertyOf(String key, DoubleSupplier ifEmpty) {
        return numericPropertyOf(key, ifEmpty::getAsDouble, NumericProperty::asDoubleProperty);
    }
    
    @Override
    public final FloatProperty floatPropertyOf(String key, FloatSupplier ifEmpty) {
        return numericPropertyOf(key, ifEmpty::getAsFloat, NumericProperty::asFloatProperty);
    }
    
    private <T extends Property<? extends Number>> T numericPropertyOf(String key, Supplier<? extends Number> ifEmpty, Function<NumericProperty, T> wrapper) {
        final NumericProperty property = (NumericProperty) properties
            .computeIfAbsent(key, k -> 
                prepare(k, new SimpleNumericProperty(), ifEmpty.get())
            );
        
        return wrapper.apply(property);
    }

    @Override
    public final <T extends DocumentProperty> ObservableList<T> observableListOf(String key) {
        @SuppressWarnings("unchecked")
        final ObservableList<T> list = (ObservableList<T>)
            children.computeIfAbsent(key, k -> 
                addListeners(k, observableList(new CopyOnWriteArrayList<>()))
            );
        
        return list;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final ObservableMap<String, ObservableList<DocumentProperty>> childrenProperty() {
        return
            (ObservableMap<String, ObservableList<DocumentProperty>>) 
            (ObservableMap<String, ?>)
            children
        ;
    }

    @Override
    public final Stream<? extends DocumentProperty> children() {
        return MapStream.of(children)
            .sortedByKey(Comparator.naturalOrder())
            .flatMapValue(ObservableList::stream)
            .values();
    }

    /**
     * {@inheritDoc}
     * @deprecated Not supported for observables
     */
    @Override
    @Deprecated // Deprecated to use but will be retained in the API
    public final <P extends Document, T extends Document> Stream<T> 
    children(String key, BiFunction<P, Map<String, Object>, T> constructor) {
       throw new UnsupportedOperationException("children() shall not be called from a Property");
    }

    @Override
    public final void invalidate() {
        listeners.forEach(l -> l.invalidated(this));
        getParent().map(DocumentProperty.class::cast)
            .ifPresent(DocumentProperty::invalidate);
    }

    @Override
    public final void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public final void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }
    
    /**
     * An overridable method used to get the full key path with the specified 
     * trail. This is used to locate the appropriate constructor.
     * 
     * @param key  the key to end with (can be null)
     * @return     the constructor
     */
    protected abstract List<String> keyPathEndingWith(String key);
    
    /**
     * Creates a new child on the specified key with the specified data and 
     * returns it. This method can be overriden by subclasses to create better
     * implementations.
     * <p>
     * <b>Warning!</b> This method is only intended to be called internally and does
     * not properly configure created children in the responsive model.
     * 
     * @param documentPropertyComponent  the documentPropertyComponent instance
     * @param key                        the key to create the child on
     * @return                           the created child
     */
    protected final DocumentProperty createChild(DocumentPropertyComponent documentPropertyComponent, String key) {
        return documentPropertyComponent
            .getConstructor(keyPathEndingWith(key))
            .create(this);
    }
    
    /**
     * Adds a listener to the specified property so that changes to it are
     * reflected down to the source map.
     * 
     * @param <T>           the type of the property
     * @param key           the key of the property
     * @param property      the property to listen to
     * @param initialValue  the initial value of this property
     * @return              the same property but with listener attached
     */
    private <T> Property<T> prepare(String key, Property<T> property, T initialValue) {
        final ChangeListener<T> change = (ob, oldValue, newValue) -> {
            if (newValue != null) {
                config.put(key, newValue);
            } else {
                config.remove(key);
            }
            
            invalidate();
        };
        
        property.setValue(initialValue);
        property.addListener(change);
        change.changed(property, null, initialValue);
        return property;
    }
    
    /**
     * Adds a listener to the specified list so that changes to it are reflected
     * down to the source map.
     * 
     * @param key   the key where the list is located
     * @param list  the list to add listeners to
     * @return      the same list but with listener attached
     */
    private ObservableList<AbstractDocumentProperty<?>> addListeners(String key, ObservableList<AbstractDocumentProperty<?>> list) {
        // When an observable children list under a specific key is
        // modified, the new children must be inserted into the source
        // equivalent as well.
        list.addListener((ListChangeListener.Change<? extends DocumentProperty> listChange) -> {
            while (listChange.next()) {
                if (listChange.wasAdded()) {

                    // Find or create a children list in the source map
                    // for the specified key
                    @SuppressWarnings("unchecked")
                    final List<Map<String, Object>> source = 
                        (List<Map<String, Object>>) config.computeIfAbsent(
                            key, k -> new CopyOnWriteArrayList<>()
                        );

                    // Add a reference to the map for every child
                    listChange.getAddedSubList().stream()
                        .map(DocumentProperty::getData) // Exactly the same map as is used in the property
                        .forEachOrdered(source::add);
                }
                
                if (listChange.wasRemoved()) {
                    @SuppressWarnings("unchecked")
                    final List<Map<String, Object>> source = 
                        (List<Map<String, Object>>) config.computeIfAbsent(
                            key, k -> new CopyOnWriteArrayList<>()
                        );
                    
                    listChange.getRemoved().stream()
                        .map(DocumentProperty::getData)
                        .forEach(removed -> 
                            source.removeIf(e -> e == removed) // Identity
                        );
                }
            }
            
            invalidate();
        });
        
        return list;
    }
}