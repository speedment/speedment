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
import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil
 */
public interface HasEnabledProperty extends DocumentProperty, HasEnabled {
    
    default BooleanProperty enabledProperty() {
        return booleanPropertyOf(HasEnabled.ENABLED, HasEnabled.super::isEnabled);
    }
    
    @Override
    default boolean isEnabled() {
        return enabledProperty().get();
    }
    
    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            new BooleanPropertyItem(
                enabledProperty(), 
                "Enabled", 
                "True if this node should be included in the code generation."
            )
        );
    }
}