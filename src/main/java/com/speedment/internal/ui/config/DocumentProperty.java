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
package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.config.trait.HasUiVisibleProperties;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * A special interface marking implementations of {@link Document} that can be
 * observed for changes. To observe a specific attribute, call the appropriate
 * method for selecting that field.
 * <p>
 * The following methods exist:
 * <ul>
 *     <li>{@link #stringPropertyOf(String, Supplier)}
 *     <li>{@link #integerPropertyOf(String, Supplier)}
 *     <li>{@link #longPropertyOf(String, Supplier)}
 *     <li>{@link #doublePropertyOf(String, Supplier)}
 *     <li>{@link #objectPropertyOf(String, Class, Supplier)}
 * </ul>
 * <p>
 * To get an observable view of a specific child type, call 
 * {@link #observableListOf(String, Class, Supplier)}, or if you want all 
 * children as they have been exposed so far, call {@link #childrenProperty()}.
 * <p>
 * As with all JavaFX componenets, the state of this property might not be
 * updated immediatly. It is therefore important to use the appropriate property
 * getter methods to keep notified about changes to this document.
 * 
 * @author Emil Forslund
 */
public interface DocumentProperty extends Document, HasUiVisibleProperties, HasMainInterface, Observable {
    
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
    
    <T> ObjectProperty<T> objectPropertyOf(String key, Class<T> type, Supplier<T> ifEmpty);
    
    /**
     * Returns an observable list of all the child documents under a specified
     * key. 
     * <p>
     * The implementation of the document is governed by the 
     * {@link #createDocument(java.lang.String, java.util.Map) createDocument} 
     * method. The specified {@code type} parameter must therefore match the
     * implementation created by 
     * {@link #createDocument(java.lang.String, java.util.Map) createDocument}.
     * 
     * @param <P>          the type of this class
     * @param <T>          the document type
     * @param key          the key to look at
     * @param constructor  the constructor to use to create the children views
     * @return             an observable list of the documents under that key
     * 
     * @throws SpeedmentException  if the specified {@code type} is not the same
     *                             type as {@link #createDocument(java.lang.String, java.util.Map) createDocument}
     *                             generated.
     */
    <P extends DocumentProperty, T extends DocumentProperty> ObservableList<T> observableListOf(String key, BiFunction<P, Map<String, Object>, T> constructor) throws SpeedmentException;

    /**
     * Returns a list of all children instantiated using 
     * {@link #createDocument(String, Map) createDocument} in alphabetical order 
     * based on the key they belong to. The internal order will be the order 
     * they have in the list.
     * 
     * @return a stream of the children
     */
    @Override
    Stream<? extends DocumentProperty> children();
    
    /**
     * Returns an unmodifiable map of all the children that this component has
     * made visible so far. The {@code ObservableList ObservableLists} contained 
     * in the map will automatically reflect the children belonging to this 
     * document, viewed using the constructor supplied to the 
     * {@link #observableListOf(String, BiFunction)} method when the child key
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