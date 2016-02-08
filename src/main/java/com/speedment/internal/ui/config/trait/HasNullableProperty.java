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
package com.speedment.internal.ui.config.trait;

import com.speedment.Speedment;
import com.speedment.config.db.trait.HasNullable;
import static com.speedment.config.db.trait.HasNullable.NULLABLE;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public interface HasNullableProperty extends DocumentProperty, HasNullable {

    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            new BooleanPropertyItem(
                nullableProperty(),
                "Is Nullable",
                "If this node can hold 'null'-values or not."
            )
        );
    }
    
    default BooleanProperty nullableProperty() {
        return booleanPropertyOf(NULLABLE, HasNullable.super::isNullable);
    }

    @Override
    default boolean isNullable() {
        return nullableProperty().get();
    }
}