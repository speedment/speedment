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
package com.speedment.ui.config.trait;

import com.speedment.Speedment;
import com.speedment.config.db.trait.HasName;
import com.speedment.exception.SpeedmentException;
import com.speedment.ui.config.DocumentProperty;
import com.speedment.ui.config.db.StringPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public interface HasNameProperty extends DocumentProperty {

    default StringProperty nameProperty() {
        return stringPropertyOf(HasName.NAME, DocumentProperty.super::getName);
    }

    @Override
    default String getName() throws SpeedmentException {
        return nameProperty().get();
    }

    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            new StringPropertyItem(
                nameProperty(), 
                "Database Name", 
                "The name of the persisted entity in the database. This should only be modified if the database has been changed!"
            )
        );
    }
}