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
package com.speedment.tool.config.trait;


import com.speedment.runtime.config.trait.HasNullable;
import com.speedment.runtime.config.trait.HasNullableUtil;
import com.speedment.tool.config.DocumentProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

/**
 * A trait for all {@link DocumentProperty document properties} that has a
 * nullable flag.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public interface HasNullableProperty extends DocumentProperty, HasNullable {

    /**
     * Returns a property that holds a value showing if the column can contain
     * {@code NULL} values. This property should only be edited when and if the
     * database has been modified.
     * 
     * @return  a {@code BooleanProperty} of the 
     *          {@link HasNullable#isNullable()}-state of this column
     */
    default BooleanProperty nullableProperty() {
        return booleanPropertyOf(HasNullableUtil.NULLABLE, HasNullable.super::isNullable);
    }

    @Override
    default boolean isNullable() {
        return nullableProperty().get();
    }
    
    /**
     * Returns a property of the current implementation used by this column.
     * <p>
     * <em>Warning!</em> The returned object can be both read and written to,
     * but note that it may be garbage collected unless the caller saves a
     * reference to it somewhere.
     * 
     * @return  a property of the nullable implementation.
     */
    default ObjectProperty<ImplementAs> nullableImplementationProperty() {
        final ImplementAs initial = HasNullable.super.getNullableImplementation();
        final StringProperty implName = stringPropertyOf(HasNullableUtil.NULLABLE_IMPLEMENTATION, initial::name);
        final ObjectProperty<ImplementAs> property = new SimpleObjectProperty<>(initial);
        
        Bindings.bindBidirectional(implName, property, new StringConverter<ImplementAs>() {
            @Override
            public String toString(ImplementAs object) {
                if (object == null) return null;
                else return object.name();
            }

            @Override
            public ImplementAs fromString(String string) {
                if (string == null) return null;
                else return ImplementAs.valueOf(string);
            }
        });
        
        return property;
    }

    @Override
    default ImplementAs getNullableImplementation() {
        return nullableImplementationProperty().get();
    }
}