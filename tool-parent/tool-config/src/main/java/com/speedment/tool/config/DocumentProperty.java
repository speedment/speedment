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
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.*;
import java.util.stream.Stream;

/**
 * A special interface marking implementations of {@link Document} that can be
 * observed for changes. To observe a specific attribute, call the appropriate
 * method for selecting that field.
 * <p>
 * The following methods exist:
 * <ul>
 *     <li>{@link #stringPropertyOf(String, Supplier)}
 *     <li>{@link #integerPropertyOf(String, IntSupplier)}
 *     <li>{@link #longPropertyOf(String, LongSupplier)}
 *     <li>{@link #doublePropertyOf(String, DoubleSupplier)}
 *     <li>{@link #floatPropertyOf(String, FloatSupplier)}
 * </ul>
 * <p>
 * To get an observable view of a specific child type, call 
 * {@link #observableListOf(String)}, or if you want all 
 * children as they have been exposed so far, call {@link #childrenProperty()}.
 * <p>
 * As with all JavaFX components, the state of this property might not be
 * updated immediately. It is therefore important to use the appropriate property
 * getter methods to keep notified about changes to this document.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public interface DocumentProperty extends Document,
        HasMainInterface,
        HasId,
        HasName,
        Observable {
    
    /**
     * Wraps the specified String value in a property so that changes to it can
     * be observed. Any changes to the returned property will be reflected back
     * to the raw map.
     * 
     * @param key      the key
     * @param ifEmpty  a supplier for the initial value should the key not 
     *                 already exist
     * @return         the specified attribute wrapped in a {@code Property}
     */
    StringProperty stringPropertyOf(String key, Supplier<String> ifEmpty);
    
    /**
     * Wraps the specified int value in a property so that changes to it can
     * be observed. Any changes to the returned property will be reflected back
     * to the raw map.
     * 
     * @param key      the key
     * @param ifEmpty  a supplier for the initial value should the key not 
     *                 already exist
     * @return         the specified attribute wrapped in a {@code Property}
     */
    IntegerProperty integerPropertyOf(String key, IntSupplier ifEmpty);
    
    /**
     * Wraps the specified long value in a property so that changes to it can
     * be observed. Any changes to the returned property will be reflected back
     * to the raw map.
     * 
     * @param key      the key
     * @param ifEmpty  a supplier for the initial value should the key not 
     *                 already exist
     * @return         the specified attribute wrapped in a {@code Property}
     */
    LongProperty longPropertyOf(String key, LongSupplier ifEmpty);
    
    /**
     * Wraps the specified double value in a property so that changes to it can
     * be observed. Any changes to the returned property will be reflected back
     * to the raw map.
     * 
     * @param key      the key
     * @param ifEmpty  a supplier for the initial value should the key not 
     *                 already exist
     * @return         the specified attribute wrapped in a {@code Property}
     */
    DoubleProperty doublePropertyOf(String key, DoubleSupplier ifEmpty);
    
    /**
     * Wraps the specified float value in a property so that changes to it can
     * be observed. Any changes to the returned property will be reflected back
     * to the raw map.
     * 
     * @param key      the key
     * @param ifEmpty  a supplier for the initial value should the key not 
     *                 already exist
     * @return         the specified attribute wrapped in a {@code Property}
     */
    FloatProperty floatPropertyOf(String key, FloatSupplier ifEmpty);
    
    /**
     * Wraps the specified boolean value in a property so that changes to it can
     * be observed. Any changes to the returned property will be reflected back
     * to the raw map.
     * 
     * @param key      the key
     * @param ifEmpty  a supplier for the initial value should the key not 
     *                 already exist
     * @return         the specified attribute wrapped in a {@code Property}
     */
    BooleanProperty booleanPropertyOf(String key, BooleanSupplier ifEmpty);
    
    /**
     * Returns an observable list of all the child documents under a specified
     * key.
     * 
     * @param <T>  result type
     * @param key  the key to look at
     * @return     an observable list of the documents under that key
     */
    <T extends DocumentProperty> ObservableList<T> observableListOf(String key);

    /**
     * Returns a list of all children instantiated so far. Note that this might
     * be somewhat different from the API in the {@link Document} interface.
     * 
     * @return  a stream of the children
     */
    @Override
    Stream<? extends DocumentProperty> children();
    
    /**
     * Returns an unmodifiable map of all the children that this component has
     * made visible so far. The {@code ObservableList ObservableLists} contained 
     * in the map will automatically reflect the children belonging to this 
     * document, viewed using the constructor supplied to the 
     * {@link #observableListOf(String)} method when the child key
     * was first viewed. If a child key has not been requested before when
     * this method is called, the map might not have all child types. These will 
     * be added as soon as they have been requested, notifying any change 
     * listeners observing this map.
     * 
     * @return  all view of all children made visible so far
     */
    ObservableMap<String, ObservableList<DocumentProperty>> childrenProperty();
    
    /**
     * Mark this component and all components above it as invalidated so that
     * any events observing the tree will know the state has changed.
     */
    void invalidate();
}